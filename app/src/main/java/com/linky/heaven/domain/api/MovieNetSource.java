package com.linky.heaven.domain.api;

import com.linky.heaven.R;
import com.linky.heaven.bean.MovieTypeBean;
import com.linky.heaven.dao.config.MovieURL;
import com.linky.heaven.dao.orm.MovieBean;
import com.linky.heaven.dao.orm.MovieBeanDao;
import com.linky.heaven.domain.cache.MovieCache;
import com.linky.heaven.support.utils.Network;
import com.linky.heaven.support.utils.ResUtils;
import com.linky.heaven.support.utils.SettingInfo;
import com.linky.heaven.support.utils.TimeUtils;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by linky on 16-2-4.
 *
 */
public class MovieNetSource implements MovieAPI {

    private final MovieBeanDao mMovieBeanDao;

    public MovieNetSource(MovieBeanDao movieBeanDao) {
        this.mMovieBeanDao = movieBeanDao;
    }

    @Override
    public Observable<List<MovieBean>> getMoviesFromCloud() {
        return Observable.create(subscriber -> {

            // 清空缓存数据
            MovieCache.movies.clear();

            long now = System.currentTimeMillis();
            long lastTime = SettingInfo.getLastUpdateTime();
            boolean isSameDay = TimeUtils.isSameDay(now, lastTime);

            // 如果是同一天
            if (isSameDay) {
                MovieCache.movies = mMovieBeanDao.loadAll();
                classifyMovies(MovieCache.movies);
            }

            if (MovieCache.movies.isEmpty()) {
                try {

                    if (!Network.isConnected()) {
                        subscriber.onError(new Exception(ResUtils.getString(R.string.toast_network_unavailable)));
                    } else {
                        MovieCache.movies = parseMovieHeaven();
                        classifyMovies(MovieCache.movies);
                    }

                } catch (ParserException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                    return;
                }

                // 保存到数据库
                mMovieBeanDao.deleteAll();
                mMovieBeanDao.insertOrReplaceInTx(MovieCache.movies);

                SettingInfo.setLastUpdateTime(now);
            }

            subscriber.onNext(MovieCache.movies);
            subscriber.onCompleted();

        });
    }

    /**
     * 对电影进行分类缓存
     */
    private void classifyMovies(List<MovieBean> movies) {
        Observable.from(movies).forEach(movieBean -> {
            Integer index = movieBean.getType();
            switch (index) {
                case MovieTypeBean.COMEDY_INDEX:     // 喜剧
                    MovieCache.ComedyMovies.add(movieBean);
                    break;
                case MovieTypeBean.LOVE_INDEX:       // 爱情
                    MovieCache.LoveMovies.add(movieBean);
                    break;
                case MovieTypeBean.SUSPEND_INDEX:    // 悬疑
                    MovieCache.SuspendMovies.add(movieBean);
                    break;
                case MovieTypeBean.TERROR_INDEX:     // 恐怖
                    MovieCache.TerrorMovies.add(movieBean);
                    break;
                case MovieTypeBean.ACTION_INDEX:     // 动作
                    MovieCache.ActionMovies.add(movieBean);
                    break;
                case MovieTypeBean.THRILLER_INDEX:   // 惊悚
                    MovieCache.ThrillerMovies.add(movieBean);
                    break;
                case MovieTypeBean.FANTASY_INDEX:    // 奇幻
                    MovieCache.FantasyMovies.add(movieBean);
                    break;
                case MovieTypeBean.DRAMA_INDEX:      // 剧情
                    MovieCache.DramaMovies.add(movieBean);
                    break;
                default:
                    MovieCache.OtherMovies.add(movieBean);
                    break;
            }
        });
    }

    @Override
    public Observable<List<String>> getMovieDownloadLink(String url) {
        return Observable.create(subscriber -> {

                List<String> downloadLinks = MovieCache.downloadUrlsMap.get(url);
                if (downloadLinks != null) {
                    subscriber.onNext(downloadLinks);
                    subscriber.onCompleted();
                } else {
                    try {

                        if (!Network.isConnected()) {
                            subscriber.onError(new Exception(ResUtils.getString(R.string.toast_network_unavailable)));
                        } else {

                            MovieCache.downloadUrlsMap.put(url, parseDownloadUrl(url));
                            subscriber.onNext(MovieCache.downloadUrlsMap.get(url));
                            subscriber.onCompleted();
                        }

                    } catch (ParserException e) {
                        e.printStackTrace();
                        subscriber.onError(e);
                    }
            }
        });
    }

    /**
     * 解析获取下载链接
     *
     * @throws ParserException
     */
    private static List<String> parseDownloadUrl(String url) throws ParserException {

        List<String> urls = new ArrayList<>();

        Parser parser = new Parser(url);
        parser.setEncoding("UTF-8");

        // 自定义过滤器
        NodeFilter linkFilter = node -> {
            return node.getText().startsWith("a href=\"ftp://")
                    || node.getText().startsWith("title");
        };

        NodeList nodes = parser.extractAllNodesThatMatch(linkFilter);

        // 过滤影片名称
        Node node = nodes.elementAt(0);
        Node child;
        if (node != null) {
            child = node.getFirstChild();
            if (child != null) {
                String titleString = child.getText();
                int start = titleString.indexOf("《");
                int end = titleString.indexOf("》");
                String title = titleString.substring(start + 1, end);
                urls.add(title);
            }
        }

        // 过滤下载链接地址
        for (int i = 1; i < nodes.size(); i++) {
            node = nodes.elementAt(i);
            if (node != null) {
                child = node.getFirstChild();
                if (child != null) {
                    urls.add(child.getText());
                }
            }
        }
        return urls;
    }

    /**
     * 解析电影天堂首页
     *
     * @throws ParserException
     */
    private List<MovieBean> parseMovieHeaven() throws ParserException {

        ArrayList<MovieBean> movies = new ArrayList<>();

        // 创建 html parser 对象，并指定要访问网页的 URL 和编码格式
        Parser parser = new Parser(MovieURL.URL);
        parser.setEncoding("UTF-8");

        // 自定义过滤器
        NodeFilter movieLink = node -> {
            return node.getText().startsWith("a href=\'/html");
        };

        // 使用过滤器过滤
        NodeList nodes = parser.extractAllNodesThatMatch(movieLink);
        for (int i = 0; i < nodes.size(); i++) {
            MovieBean movieBean = new MovieBean();
            Node node = nodes.elementAt(i);
            String url = getNodeUrl(node);
            Node child = node.getFirstChild();
            if (child != null) {
                String text = child.getText();
                String movieName;
                if (text.matches("^20..年.+")) {
                    movieName = text.substring(5);
                } else {
                    movieName = text;
                }
                movieBean.setName(movieName);
                movieBean.setType(getMovieType(movieName));
                movieBean.setParse_page_url(MovieURL.URL + url);
                movies.add(movieBean);
            }
        }
        return movies;
    }

    /**
     * 根据名称获取电影类型
     */
    private int getMovieType(String name) {
        if (name.contains(MovieTypeBean.COMEDY_NAME)) {
            return MovieTypeBean.COMEDY_INDEX;
        } else if (name.contains(MovieTypeBean.LOVE_NAME)) {
            return MovieTypeBean.LOVE_INDEX;
        } else if (name.contains(MovieTypeBean.SUSPEND_NAME)) {
            return MovieTypeBean.SUSPEND_INDEX;
        } else if (name.contains(MovieTypeBean.TERROR_NAME)) {
            return MovieTypeBean.TERROR_INDEX;
        } else if (name.contains(MovieTypeBean.ACTION_NAME)) {
            return MovieTypeBean.ACTION_INDEX;
        } else if (name.contains(MovieTypeBean.THRILLER_NAME)) {
            return MovieTypeBean.THRILLER_INDEX;
        } else if (name.contains(MovieTypeBean.FANTASY_NAME)) {
            return MovieTypeBean.FANTASY_INDEX;
        } else if (name.contains(MovieTypeBean.DRAMA_NAME)) {
            return MovieTypeBean.DRAMA_INDEX;
        } else {
            return MovieTypeBean.OTHERS_INDEX;
        }
    }

    private String getNodeUrl(Node node) {
        String text = node.getText();
        int firstIndex = text.indexOf("'");
        int lastIndex = text.lastIndexOf("'");
        return text.substring(firstIndex + 1, lastIndex);
    }
}
