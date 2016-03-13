package com.linky.heaven.support.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.linky.heaven.callback.DefaultSubscriber;

/**
 * Created by linky on 16-2-16.
 * 询问对话框
 */
public final class DialogView {

    private AlertDialog.Builder mBuilder;
    private AlertDialog mDialog;

    public DialogView(Context context) {
        mBuilder = new AlertDialog.Builder(context);
    }

    public void setView(View view) {
        mBuilder.setView(view);
    }

    public void setTitle(int title) {
        mBuilder.setTitle(title);
    }

    public void dismiss() {
        mDialog.dismiss();
        mDialog = null;
    }

    public AlertDialog create() {
        mDialog = mBuilder.create();
        return mDialog;
    }

    public void setPositiveButton(int action, DefaultSubscriber<Boolean> subscriber) {
        mBuilder.setPositiveButton(action,  (dialogInterface, i) -> {
            subscriber.onNext(true);
        });
    }

    public void setNegativeButton(int action, DefaultSubscriber<Boolean> subscriber) {
        mBuilder.setNegativeButton(action, (dialogInterface, i) -> {
            subscriber.onNext(true);
            mDialog.dismiss();
        });
    }
}
