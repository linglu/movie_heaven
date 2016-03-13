package com.linky.heaven.support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linky.heaven.R;
import com.linky.heaven.bean.BaseTypeBean;
import com.linky.heaven.support.utils.ResUtils;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linky on 16-3-6.
 * 目录选择器
 */
public class OptionsSelectorView extends FrameLayout {

    private View mCheckedView;
    private List<BaseTypeBean> mOptions;

    public OptionsSelectorView(Context context) {
        this(context, null);
    }

    public OptionsSelectorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OptionsSelectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.option_selector_layout, this, true);
        LinearLayout llOptionContainer = (LinearLayout) view.findViewById(R.id.ll_option_container);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OptionsSelectorView);
        int count = a.getIndexCount();

        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(0);
            switch (attr) {
                case R.styleable.OptionsSelectorView_data:
                    CharSequence[] array = a.getTextArray(attr);
                    mOptions = new ArrayList<>(array.length);
                    for (int j = 0; j < array.length; j++) {
                        BaseTypeBean typeBean = new BaseTypeBean();
                        typeBean.id = j;
                        typeBean.name = (String) array[j];
                        mOptions.add(typeBean);
                    }
                    break;
                default:
                    break;
            }
        }

        a.recycle();
        Assert.assertNotNull(mOptions);
        for (BaseTypeBean bean : mOptions) {
            View itemView = createOptionViews(context, llOptionContainer);
            TextView tv = (TextView) itemView.findViewById(R.id.tv_option_name);
            tv.setText(bean.name);
            itemView.setOnClickListener(v -> {
                View preCheckedView = mCheckedView;
                mCheckedView = v;

                unCheckView(preCheckedView);
                checkView(mCheckedView);

                if (mListener != null) {
                    mListener.onOptionClick(v, bean);
                }
            });
            llOptionContainer.addView(itemView);
        }

        if (llOptionContainer.getChildCount() > 0) {
            mCheckedView = llOptionContainer.getChildAt(0);
            checkView(mCheckedView);
        }
    }

    public void checkView(View view) {
        TextView tvName = (TextView) view.findViewById(R.id.tv_option_name);
        tvName.setTextColor(ResUtils.getColor(R.color.accent));
        view.findViewById(R.id.v_indicator).setBackgroundColor(ResUtils.getColor(R.color.accent));
    }

    private void unCheckView(View view) {
        TextView tvName = (TextView) view.findViewById(R.id.tv_option_name);
        tvName.setTextColor(ResUtils.getColor(android.R.color.black));
        view.findViewById(R.id.v_indicator).setBackgroundColor(ResUtils.getColor(android.R.color.transparent));
    }

    private View createOptionViews(Context context, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.option_item_view, parent, false);
    }

    public OnOptionClickListener mListener;
    public void setOnOptionListener(OnOptionClickListener listener) {
        mListener = listener;
    }
    public interface OnOptionClickListener {
        void onOptionClick(View v, BaseTypeBean typeBean);
    }
}
