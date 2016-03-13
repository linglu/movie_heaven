package com.linky.heaven.support.utils;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;

import com.orhanobut.logger.Logger;

import com.linky.heaven.R;

/**
 * Created by linky on 16-2-18.
 */
public class PathUtil {

    public static String getPath(Resources r) {
        Uri uri =  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.mipmap.ic_launcher) + "/"
                + r.getResourceTypeName(R.mipmap.ic_launcher) + "/"
                + r.getResourceEntryName(R.mipmap.ic_launcher));

        Logger.d(" path : " + uri.getPath());
        Logger.d(" path : " + uri.getEncodedPath());
        Logger.d(" path : " + uri.getPathSegments());
        Logger.d(" path : " + uri.getLastPathSegment());
        return uri.getPath();
    }
}
