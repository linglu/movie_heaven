package com.linky.heaven.mvp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.linky.heaven.mvp.views.BaseView;

/**
 * Created by cwong on 3/10/15.
 */
public abstract class BasePresenterAdapter<V extends BaseView> extends BaseAdapter {

    protected V bv;

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            try {
                bv = getVuClass().newInstance();
                bv.init(inflater, parent);
                convertView = bv.getView();
                convertView.setTag(bv);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            bv = (V) convertView.getTag();
        }
        if (convertView != null) {
            onBindListItemVu(position);
        }
        return convertView;
    }

    protected abstract void onBindListItemVu(int position);

    protected abstract Class<V> getVuClass();

}