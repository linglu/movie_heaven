package com.linky.heaven.support.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by linky on 16-2-17.
 */
public class ClipboardUtil {

    public static void copyToClipBoard(Context context, String content) {
        ClipboardManager cbm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cbm.setPrimaryClip(ClipData.newPlainText("url", content));
    }
}
