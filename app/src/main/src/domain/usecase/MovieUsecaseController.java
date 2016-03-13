package com.linky.heaven.domain.usecase;

import java.util.List;

import com.linky.heaven.dao.orm.MovieBean;
import com.linky.heaven.domain.api.MovieAPI;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by linky on 16-2-3.
 *
 */
public class MovieUsecaseController implements MovieUsecase {

    private final MovieAPI mMovieAPI;

    public MovieUsecaseController(MovieAPI movieAPI) {
        this.mMovieAPI = movieAPI;
    }

    @Override
    public Observable<List<MovieBean>> getMoviesFromCloud() {
        return mMovieAPI.getMoviesFromCloud()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<String>> getMovieDownloadLink(String url) {
        return mMovieAPI.getMovieDownloadLink(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
