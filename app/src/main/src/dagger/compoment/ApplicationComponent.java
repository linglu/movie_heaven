package com.linky.heaven.dagger.compoment;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import com.linky.heaven.dagger.module.ApplicationModule;
import com.linky.heaven.dao.orm.DaoSession;
import com.linky.heaven.domain.api.MovieAPI;
import com.linky.heaven.mvp.BaseActivity;

/**
 * Created by linky on 16-2-3.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    // Exposed to sub_graph
    Context context();

    DaoSession daoSession();

    MovieAPI movieApi();
}
