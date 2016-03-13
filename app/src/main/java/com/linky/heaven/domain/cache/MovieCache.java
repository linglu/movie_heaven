package com.linky.heaven.domain.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linky.heaven.dao.orm.MovieBean;

/**
 * Created by linky on 16-2-4.
 */
public class MovieCache {
    public static List<MovieBean> movies = new ArrayList<>();

    // 喜剧
    public static List<MovieBean> ComedyMovies = new ArrayList<>();

    // 爱情
    public static List<MovieBean> LoveMovies = new ArrayList<>();

    // 悬疑
    public static List<MovieBean> SuspendMovies = new ArrayList<>();

    // 恐怖
    public static List<MovieBean> TerrorMovies = new ArrayList<>();

    // 动作
    public static List<MovieBean> ActionMovies = new ArrayList<>();

    // 惊悚
    public static List<MovieBean> ThrillerMovies = new ArrayList<>();

    // 玄幻
    public static List<MovieBean> FantasyMovies = new ArrayList<>();

    // 剧情
    public static List<MovieBean> DramaMovies = new ArrayList<>();

    // 其他
    public static List<MovieBean> OtherMovies = new ArrayList<>();

    public static Map<String, List<String>> downloadUrlsMap = new HashMap<>();
}
