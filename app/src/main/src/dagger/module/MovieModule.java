package com.linky.heaven.dagger.module;

import dagger.Module;
import dagger.Provides;
import com.linky.heaven.dagger.PerActivity;
import com.linky.heaven.domain.api.MovieAPI;
import com.linky.heaven.domain.usecase.MovieUsecase;
import com.linky.heaven.domain.usecase.MovieUsecaseController;

/**
 * Created by linky on 16-2-3.
 */
@Module
public class MovieModule {

    @Provides
    @PerActivity
    MovieUsecase providerMovieUsecase(MovieAPI movieAPI) {
        return new MovieUsecaseController(movieAPI);
    }
}
