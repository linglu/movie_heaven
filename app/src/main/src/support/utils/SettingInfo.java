package com.linky.heaven.support.utils;

/**
 * Created by linky on 16-2-4.
 */
public class SettingInfo {

    private static final String NAME = "SettingInfo";

    private static final String LAST_UPDATE_TIME = "LAST_UPDATE_TIME";

    public static void setLastUpdateTime(long timeMillies) {
        PreUtils.setLongPref(NAME, LAST_UPDATE_TIME, timeMillies);
    }

    public static long getLastUpdateTime() {
        return PreUtils.getLongPref(NAME, LAST_UPDATE_TIME, 0);
    }

    // 分享相关
    public static final String QQ_OPEN_ID = "QQ_OPEN_ID";
    public static final String QQ_ACCESS_TOKEN = "QQ_ACCESS_TOKEN";
    public static final String QQ_EXPIRE = "QQ_EXPIRE";

    /**
     * 获取 OpenId
     */
    public static String getQqOpenId() {
        return PreUtils.getStringPref(NAME, QQ_OPEN_ID, "");
    }

    /**
     * 设置 OpenId
     */
    public static void setQqOpenId(String openId) {
        PreUtils.setStringPref(NAME, QQ_ACCESS_TOKEN, openId);
    }

    /**
     * 获取 AccessToken
     */
    public static String getQqAccessToken() {
        return PreUtils.getStringPref(NAME, QQ_ACCESS_TOKEN, "");
    }

    /**
     * 设置 AccessToken
     */
    public static void setQqAccessToken(String accessToken) {
        PreUtils.setStringPref(NAME, QQ_ACCESS_TOKEN, accessToken);
    }

    /**
     * 获取有效时间
     */
    public static String getQqExpires() {
        return PreUtils.getStringPref(NAME, QQ_EXPIRE, "0");
    }

    /**
     * 设置有效时间
     */
    public static void setQqExpire(String expire) {
        PreUtils.setStringPref(NAME, QQ_EXPIRE, expire);
    }
}
