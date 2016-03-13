package com.linky.heaven;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.orhanobut.logger.Logger;

import com.linky.heaven.dagger.compoment.ApplicationComponent;
import com.linky.heaven.dagger.compoment.DaggerApplicationComponent;
import com.linky.heaven.dagger.module.ApplicationModule;
import com.linky.heaven.dao.orm.DaoMaster;
import com.linky.heaven.dao.orm.DaoSession;

/**
 * Created by Linky on 16-2-3.
 * Application
 */
public class MovieApp extends Application {

    private final static boolean DEVELOPER_MODE = false;
    private final static String DBNAME = "movie.db";

    public static MovieApp mMovieApp;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        if (DEVELOPER_MODE) {
            // StrictMode
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .detectCustomSlowCalls()
                    .penaltyLog()
                    .penaltyDialog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }

        if (BuildConfig.DEBUG) {
            initLogger();
        }

        super.onCreate();
        mMovieApp = this;
        initializeInjector();
    }

    /**
     * only show the message :
     * Logger.init().setMethodCount(0).hideThreadInfo();
     */
    private void initLogger() {

        // only show the message :
        Logger.init("movieLog").setMethodCount(0).hideThreadInfo();

//        Logger.init("movieLog")
//                .setMethodCount(2)
//                .hideThreadInfo()
//                .setLogLevel(LogLevel.FULL)
//                .setMethodOffset(1);

    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    // 获取 DaoMaster
    public DaoMaster getDaoMaster() {
        if (mDaoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DBNAME, null);
            mDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return mDaoMaster;
    }

    // 获取 DaoSession
    public DaoSession getDaoSession() {
        if (mDaoSession == null) {
            mDaoSession = getDaoMaster().newSession();
        }
        return mDaoSession;
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    public static Context getAppContext() {
        return mMovieApp.getApplicationContext();
    }
}
