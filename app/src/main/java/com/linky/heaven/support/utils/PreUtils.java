package com.linky.heaven.support.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.linky.heaven.MovieApp;

/**
 * Created by linky on 16-2-4.
 */
public class PreUtils {

    private static Context appContext = MovieApp.getAppContext();

    //长整形
    public static long getLongPref(String filename, String key, long value) {
        SharedPreferences prefs = appContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return prefs.getLong(key, value);
    }

    public static void setLongPref(String filename, String key, long value) {
        SharedPreferences prefs = appContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putLong(key, value);
        ed.apply();
    }

    //字符串
    public static String getStringPref(String filename, String key, String def) {
        SharedPreferences prefs = appContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return prefs.getString(key, def);
    }

    public static void setStringPref(String filename, String key, String value) {
        SharedPreferences prefs = appContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString(key, value);
        ed.apply();
    }

}
