package com.linky.heaven.mvp.adapter;

import java.util.ArrayList;
import java.util.List;

import com.linky.heaven.dao.orm.MovieBean;
import com.linky.heaven.mvp.views.MovieItemView;

/**
 * Created by linky on 16-2-4.
 */
public class MovieListAdapter extends BasePresenterAdapter<MovieItemView> {

    private List<MovieBean> mMovieBeans = new ArrayList<>();

    public void setContents(List<MovieBean> beans) {
        mMovieBeans = beans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMovieBeans.size();
    }

    @Override
    public MovieBean getItem(int i) {
        return mMovieBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    @Override
    protected void onBindListItemVu(int position) {
        MovieBean movie = mMovieBeans.get(position);
        bv.setMovieName(movie.getName());
    }

    @Override
    protected Class<MovieItemView> getVuClass() {
        return MovieItemView.class;
    }
}
