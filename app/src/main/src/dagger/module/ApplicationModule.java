package com.linky.heaven.dagger.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.linky.heaven.MovieApp;
import com.linky.heaven.dao.orm.DaoSession;
import com.linky.heaven.domain.api.MovieAPI;
import com.linky.heaven.domain.api.MovieNetSource;

/**
 * Created by linky on 16-2-3.
 */
@Module
@Singleton
public class ApplicationModule {

    private final MovieApp mMovieApp;

    public ApplicationModule(MovieApp movieApp) {
        mMovieApp = movieApp;
    }

    @Provides
    @Singleton
    public Context providerContext() {
        return this.mMovieApp;
    }

    @Provides
    @Singleton
    public DaoSession providerDaoSession() {
        return this.mMovieApp.getDaoSession();
    }

    @Provides
    public MovieAPI providerMovieAPI(DaoSession daoSession) {
        return new MovieNetSource(daoSession.getMovieBeanDao());
    }
}
