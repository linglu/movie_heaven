package com.linky.heaven.dagger.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import com.linky.heaven.dagger.PerActivity;

/**
 * Created by linky on 16-2-3.
 */
@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity act) {
        activity = act;
    }

    @Provides
    @PerActivity
    Activity providersActivity() {
        return this.activity;
    }
}
