package com.linky.heaven.mvp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import com.linky.heaven.mvp.views.BaseItemView;

/**
 * Created by linky on 16-3-6.
 */
public abstract class SimplePresenterAdapter<E, V extends BaseItemView> extends BaseAdapter {

    protected V vu;
    private List<E> data;

    public SimplePresenterAdapter(List<E> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public void setData(List<E> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public E getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            try {
                vu = getVuClass().newInstance();
                vu.init(inflater, parent);
                convertView = vu.getView();
                convertView.setTag(vu);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            vu = (V) convertView.getTag();
        }

        if (convertView != null) {
            onBindListItemView(position);
        }

        return convertView;
    }

    protected void onBindListItemView(int position) {
        vu.bindViewData(position, getItemId(position));
    }

    protected abstract Class<V> getVuClass();
}
