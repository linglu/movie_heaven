package com.linky.heaven.mvp.views;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import com.linky.heaven.R;
import com.linky.heaven.dao.orm.MovieBean;
import com.linky.heaven.mvp.adapter.MovieListAdapter;

/**
 * Created by linky on 16-2-3.
 */
public class MovieView extends BaseLoadingView {

    private ListView mLvMovie;
    private MovieListAdapter mMovieAdapter;
    private OnActionListener mOnActionListener;

    @Override
    public void init(LayoutInflater inflater, ViewGroup parent) {
        super.init(inflater, parent);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.movie_layout, parent, false);
        super.setContentView(view);

        initViews(view);

        mMovieAdapter = new MovieListAdapter();
        mLvMovie.setAdapter(mMovieAdapter);
        mLvMovie.setOnItemClickListener((adapterView, v, i, l) -> {
            if (mOnActionListener != null) {
                mOnActionListener.onItemClick(mMovieAdapter.getItem(i).getDownload_url());
            }
        });
    }

    private void initViews(ViewGroup view) {
        mLvMovie = (ListView) view.findViewById(R.id.lv_movie);
    }

    public void setContents(List<MovieBean> movieBeanList) {
        mMovieAdapter.setContents(movieBeanList);
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.mOnActionListener = onActionListener;
    }

    public interface OnActionListener {
        void onItemClick(String url);
    }
}
