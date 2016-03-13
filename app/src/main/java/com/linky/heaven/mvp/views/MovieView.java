package com.linky.heaven.mvp.views;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.linky.heaven.R;
import com.linky.heaven.bean.BaseTypeBean;
import com.linky.heaven.dao.orm.MovieBean;
import com.linky.heaven.mvp.adapter.MovieListAdapter;
import com.linky.heaven.support.view.OptionsSelectorView;

import java.util.List;

/**
 * Created by linky on 16-2-3.
 *
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
        initAdapter();
    }

    private void initViews(ViewGroup view) {
        mLvMovie = (ListView) view.findViewById(R.id.lv_movie);
        OptionsSelectorView movieTypesView = (OptionsSelectorView) view.findViewById(R.id.option_selector_view);
        movieTypesView.setOnOptionListener((v, typeBean) -> {
            mOnActionListener.onOptionClick(typeBean);
        });
    }

    private void initAdapter() {
        mMovieAdapter = new MovieListAdapter();
        mLvMovie.setAdapter(mMovieAdapter);
        mLvMovie.setOnItemClickListener((adapterView, v, i, l) -> {
            if (mOnActionListener != null) {
                mOnActionListener.onItemClick(mMovieAdapter.getItem(i).getParse_page_url());
            }
        });
    }

    public void setContents(List<MovieBean> movieBeanList) {
        mMovieAdapter.setContents(movieBeanList);
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.mOnActionListener = onActionListener;
    }

    public interface OnActionListener {
        void onItemClick(String url);
        void onOptionClick(BaseTypeBean bean);
    }
}
