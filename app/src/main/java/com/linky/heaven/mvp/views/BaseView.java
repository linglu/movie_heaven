package com.linky.heaven.mvp.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by linky on 16-2-3.
 * MVP 模式中，V 的基类
 */
public interface BaseView {
    void init(LayoutInflater inflater, ViewGroup container);
    View getView();
}
