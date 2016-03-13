package com.linky.heaven.support.share;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import com.linky.heaven.MovieApp;
import com.linky.heaven.R;
import com.linky.heaven.callback.BaseUiListener;
import com.linky.heaven.support.utils.ResUtils;
import com.linky.heaven.support.utils.SettingInfo;

/**
 * Created by linky on 16-2-16.
 */
public class Share {

    private final static String QQ_APP_ID = "1105177620";

    private static Tencent mTencent = Tencent.createInstance(QQ_APP_ID, MovieApp.getAppContext());;

    public void login(Activity activity, IUiListener iUiListener) {
        mTencent.setOpenId(SettingInfo.getQqOpenId());
        mTencent.setAccessToken(SettingInfo.getQqAccessToken(), SettingInfo.getQqExpires());
        mTencent.login(activity, "all", iUiListener);
    }

    /**
     * 分享链接到 QQ
     * @param activity  activity
     * @param title     要分享的标题
     * @param summary   要分享的摘要
     * @param targetUrl http://www.qq.com/news/1.html
     * @param imageUrl  http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif
     */
    public static void shareToQQ(Activity activity, String title, String summary, String targetUrl, String imageUrl) {

        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);  // 默认分享图文
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, ResUtils.getString(R.string.app_name));

        mTencent.shareToQQ(activity, params, new BaseUiListener());
    }

}
