package com.linky.heaven.domain.usecase;

import java.util.List;

import com.linky.heaven.dao.orm.MovieBean;
import rx.Observable;

/**
 * Created by linky on 16-2-3.
 */
public interface MovieUsecase {

    Observable<List<MovieBean>> getMoviesFromCloud();
    Observable<List<String>> getMovieDownloadLink(String url);
}
