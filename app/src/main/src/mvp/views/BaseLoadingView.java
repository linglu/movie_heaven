package com.linky.heaven.mvp.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.linky.heaven.R;

/**
 * Created by linky on 16-2-3.
 */
public class BaseLoadingView implements BaseView {

    private Button mBtnAction;
    private View mProgressView;
    private FrameLayout mFrameLayout;
    private TextView mTvProgressHint;
    private View mRootView;

    public void init(LayoutInflater inflater, ViewGroup parent) {
        mRootView = inflater.inflate(R.layout.base_load_view, parent, false);
        initViews();
    }

    private void initViews() {
        mBtnAction = (Button) mRootView.findViewById(R.id.btn_action);
        mProgressView = mRootView.findViewById(R.id.rl_progress_view);
//        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.pb_progress);
        mFrameLayout = (FrameLayout) mRootView.findViewById(R.id.fl_container);
        mTvProgressHint = (TextView) mRootView.findViewById(R.id.tv_progress_hint);
    }

    @Override
    public View getView() {
        return mRootView;
    }

    public void setContentView(View view) {
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(view);
    }

    public void showProgress() {
        mProgressView.setVisibility(View.VISIBLE);
        mTvProgressHint.setText("");
        mBtnAction.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.GONE);
//        mRootView.setBackgroundColor(Color.parseColor("#e0000000"));    // 半透明
    }

    public void showProgress(String hint) {
        mProgressView.setVisibility(View.VISIBLE);
        mTvProgressHint.setText(hint);
        mBtnAction.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.GONE);
//        mRootView.setBackgroundColor(Color.parseColor("#e0000000"));    // 半透明
    }

    public void hideProgress() {
        mProgressView.setVisibility(View.GONE);
//        mRootView.setBackgroundColor(ResUtils.getColor(android.R.color.transparent));    // 全透明
    }

    public void showContent() {
        mProgressView.setVisibility(View.GONE);
        mBtnAction.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.VISIBLE);
    }
}
