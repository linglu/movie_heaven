package com.linky.heaven.mvp;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.linky.heaven.MovieApp;
import com.linky.heaven.dagger.compoment.ApplicationComponent;

/**
 * Created by linky on 16-2-4.
 */
public class BaseFragment extends Fragment {

    private Activity mAct;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAct = (BaseActivity) context;
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((MovieApp) mAct.getApplication()).getApplicationComponent();
    }

}
