package com.linky.heaven.mvp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import com.linky.heaven.MovieApp;
import com.linky.heaven.dagger.compoment.ApplicationComponent;
import com.linky.heaven.dagger.module.ActivityModule;

/**
 * Created by linky on 16-2-3.
 */
public class BaseActivity extends RxFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        this.getApplicationComponent().inject(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((MovieApp) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    /**
     * 添加Fragment
     */
    protected void replaceFragment(int container_id, Fragment f) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(container_id, f);
        ft.commitAllowingStateLoss();
    }
}
