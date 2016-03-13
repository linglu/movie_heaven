package com.linky.heaven.support.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.linky.heaven.MovieApp;

/**
 * Created by linky on 16-2-16.
 */
public class ResUtils {

    private static Context getContext() {
        return MovieApp.getAppContext();
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static String getString(int strId) {
        return getContext().getString(strId);
    }

    public static String getString(int strId, Object... formatArgs) {
        return getContext().getString(strId, formatArgs);
    }

    public static String[] getStringArray(int arrayId) {
        return getResources().getStringArray(arrayId);
    }

    public static int getColor(int colorId) {
        return ContextCompat.getColor(getContext(), colorId);
    }

    public static Drawable getDrawable(int imageId) {
        return ContextCompat.getDrawable(getContext(), imageId);
    }
}
