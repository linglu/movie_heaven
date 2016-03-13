package com.linky.heaven.callback;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import com.linky.heaven.support.utils.ToastUtils;

/**
 * Created by linky on 16-2-16.
 */
public class BaseUiListener implements IUiListener {

    @Override
    public void onComplete(Object response) {
        if (null == response) {
            ToastUtils.showToast("返回为空 登录失败");
            return;
        }
        JSONObject jsonResponse = (JSONObject) response;
        if (jsonResponse.length() == 0) {
            ToastUtils.showToast("返回为空 登录失败");
            return;
        }

        ToastUtils.showToast(response.toString() + "登录成功");
        doComplete((JSONObject)response);
    }

    protected void doComplete(JSONObject values) {

    }

    @Override
    public void onError(UiError e) {
        ToastUtils.showToast("onError: " + e.errorDetail);
    }

    @Override
    public void onCancel() {
        ToastUtils.showToast( "onCancel: ");
    }
}
