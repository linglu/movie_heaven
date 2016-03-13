package com.linky.heaven;

import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import com.linky.heaven.mvp.BaseActivity;
import com.linky.heaven.mvp.presentation.MovieFragment;

public class MainActivity extends BaseActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 配置友盟参数；
        initUmentParams();

        MovieFragment movieFragment = MovieFragment.newInstance();
        replaceFragment(R.id.fl_container, movieFragment);
    }

    private void initUmentParams() {
        UmengUpdateAgent.setUpdateOnlyWifi(false);  // 非 wifi 环境下也能检测；
        UmengUpdateAgent.update(this);

        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int statusCode, UpdateResponse updateResponse) {
                Logger.d("statusCode : " + statusCode +
                    " updateResponse : " + (updateResponse == null ? "null" : updateResponse.updateLog));
            }
        });

//        UmengUpdateAgent.silentUpdate(this);        // 静默更新；
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
