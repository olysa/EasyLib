package com.dingfang.org.example.okhttp.callback;

import android.app.Activity;
import android.view.Window;

import com.dingfang.org.easylib.view.progress.HHProgressAlertDialog;
import com.lzy.okgo.request.base.Request;

/**
 * 普通网络请求专用 请求中回调
 * Created by zuoqing on 2017/10/11.
 */

public abstract class DialogCallback<T> extends JsonCallback<T> {

    private HHProgressAlertDialog progressAlertDialog;

    private void initDialog(Activity activity) {
        progressAlertDialog = new HHProgressAlertDialog(activity);
        progressAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressAlertDialog.setCanceledOnTouchOutside(false);//空白点击取消弹窗
        progressAlertDialog.setCancelable(true); //设置返回键可以取消请求
    }

    public DialogCallback(Activity activity) {
        super();
        initDialog(activity);
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        if (progressAlertDialog != null && !progressAlertDialog.isShowing()) {
            progressAlertDialog.show();
        }
    }

    @Override
    public void onFinish() {
        //网络请求结束后关闭对话框
        if (progressAlertDialog != null && progressAlertDialog.isShowing()) {
            progressAlertDialog.dismiss();
        }
    }
}
