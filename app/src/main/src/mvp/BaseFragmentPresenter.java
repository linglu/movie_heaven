package com.linky.heaven.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linky.heaven.mvp.views.BaseView;

/**
 * Created by Linky on 16-2-3.
 *
 */
public abstract class BaseFragmentPresenter<V extends BaseView> extends BaseFragment {

    protected V bv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            bv = getViewClass().newInstance();
            bv.init(inflater, container);
            onBindView();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return bv.getView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        afterBindData();
    }

    protected abstract void afterBindData();
    public abstract void onBindView();
    public abstract Class<V> getViewClass();
}
