package com.linky.heaven.support.utils;

import android.content.Context;
import android.widget.Toast;

import com.linky.heaven.MovieApp;

/**
 * Created by linky on 16-2-4.
 */
public class ToastUtils {

    public static void showToast(int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
    }

    public static Context getContext() {
        return MovieApp.mMovieApp.getApplicationContext();
    }
}
