package com.linky.heaven.dagger.compoment;

import dagger.Component;
import com.linky.heaven.dagger.PerActivity;
import com.linky.heaven.dagger.module.MovieModule;
import com.linky.heaven.domain.usecase.MovieUsecase;

/**
 * Created by linky on 16-2-3.
 */
@PerActivity
@Component(modules = MovieModule.class, dependencies = ApplicationComponent.class)
public interface MovieComponent {

    MovieUsecase movieUsecase();
}
