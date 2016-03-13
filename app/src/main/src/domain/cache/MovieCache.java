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
    public static Map<String, List<String>> downloadUrlsMap = new HashMap<>();
}
