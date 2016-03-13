package com.linky.heaven.mvp.presentation;

import android.view.LayoutInflater;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.List;

import com.linky.heaven.R;
import com.linky.heaven.callback.DefaultSubscriber;
import com.linky.heaven.dagger.compoment.DaggerMovieComponent;
import com.linky.heaven.dagger.module.MovieModule;
import com.linky.heaven.dao.orm.MovieBean;
import com.linky.heaven.domain.usecase.MovieUsecase;
import com.linky.heaven.mvp.BaseFragmentPresenter;
import com.linky.heaven.mvp.views.MovieView;
import com.linky.heaven.support.share.Share;
import com.linky.heaven.support.utils.ClipboardUtil;
import com.linky.heaven.support.utils.ResUtils;
import com.linky.heaven.support.utils.ToastUtils;
import com.linky.heaven.support.view.DialogView;
import rx.Subscriber;

/**
 * Created by Linky on 16-2-3.
 *
 */
public class MovieFragment extends BaseFragmentPresenter<MovieView> {

    private MovieUsecase mMovieUsecase;

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public void onBindView() {
        mMovieUsecase = DaggerMovieComponent.builder()
                .applicationComponent(getApplicationComponent())
                .movieModule(new MovieModule())
                .build().movieUsecase();

        bv.setOnActionListener(mOnActionListener);

        bv.showProgress(ResUtils.getString(R.string.toast_loading_newest_movie));
        getMovieFromCloud();
    }

    private MovieView.OnActionListener mOnActionListener = new MovieView.OnActionListener() {
        @Override
        public void onItemClick(String url) {

            mMovieUsecase.getMovieDownloadLink(url).subscribe(new Subscriber<List<String>>() {
                @Override
                public void onCompleted() {
                    Logger.i("onCompleted");
                    bv.hideProgress();
                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.showToast(e.getMessage());
                }

                @Override
                public void onNext(List<String> strings) {
                    // 弹出新的 ListView，展示下载链接，同时提供一个按钮，表示是否发送给朋友；

                    TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.download_url_item_layout, null);

                    StringBuilder sb = new StringBuilder();
                    for (String str : strings) {
                        sb.append(str).append("\n");
                    }
                    tv.setText(sb.toString());

                    DialogView dialogView = new DialogView(getActivity());
                    dialogView.setView(tv);
                    dialogView.setTitle(R.string.label_download_url);
                    dialogView.setPositiveButton(R.string.label_share_with_friends, new DefaultSubscriber<Boolean>() {
                        @Override
                        public void onNext(Boolean aBoolean) {
                            Logger.d(sb.toString());

                            Share.shareToQQ(getActivity(),
                                    strings.get(0),     // 影片名字
                                    strings.get(1),     // 影片地址
                                    url,                // target url
                                    "http://img3.douban.com/pview/event_poster/raw/public/c4e908e2dd6a0a0.jpg");

                            // 复制到粘贴板
                            copyToClipBoard(strings.get(1));
                            ToastUtils.showToast(R.string.toast_copy_to_clipboard);
                            dialogView.dismiss();
                        }
                    });
                    dialogView.setNegativeButton(R.string.label_copy_to_clipboard, new DefaultSubscriber<Boolean>(){
                        @Override
                        public void onNext(Boolean aBoolean) {
                            super.onNext(aBoolean);

                            // 复制到粘贴板
                            copyToClipBoard(strings.get(1));
                            ToastUtils.showToast(R.string.toast_can_download_with_thunder);
                        }
                    });
                    dialogView.create().show();
                }

            });
        }
    };

    private void copyToClipBoard(String s) {
        ClipboardUtil.copyToClipBoard(getActivity(), s);
    }

    private void getMovieFromCloud() {
        mMovieUsecase.getMoviesFromCloud()

                .subscribe(new Subscriber<List<MovieBean>>() {
                    @Override
                    public void onCompleted() {
                        Logger.i("onCompleted");
                        bv.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast(e.getMessage());
                        bv.hideProgress();
                    }

                    @Override
                    public void onNext(List<MovieBean> movieBeans) {
                        bv.setContents(movieBeans);
                        bv.showContent();
                    }
                });
    }

    @Override
    protected void afterBindData() {

    }

    @Override
    public Class<MovieView> getViewClass() {
        return MovieView.class;
    }
}
