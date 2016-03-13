package com.linky.heaven.mvp.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linky.heaven.R;

/**
 * Created by linky on 16-2-4.
 */
public class MovieItemView implements BaseView {

    private View mRootView;
    private TextView mTvMovieName;

    @Override
    public void init(LayoutInflater inflater, ViewGroup parent) {
        mRootView = inflater.inflate(R.layout.movie_item_layout, parent, false);
        mTvMovieName = (TextView) mRootView.findViewById(R.id.tv_movie_name);
    }

    public void setMovieName(String name) {
        mTvMovieName.setText(name);
    }

    @Override
    public View getView() {
        return mRootView;
    }
}
