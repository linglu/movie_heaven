package com.linky.heaven.domain.api;

import java.util.List;

import com.linky.heaven.dao.orm.MovieBean;
import rx.Observable;

/**
 * Created by linky on 16-2-4.
 */
public interface MovieAPI {

    Observable<List<MovieBean>> getMoviesFromCloud();
    Observable<List<String>> getMovieDownloadLink(String url);
}
