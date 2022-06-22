package com.etuo.kucun.callback;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.etuo.kucun.widget.CustomClipLoading;
import com.lzy.okgo.request.BaseRequest;
import com.etuo.kucun.utils.StringUtil;

public abstract class DialogCallback<T> extends JsonCallback<T> {

    private CustomClipLoading dialog;

    private void initDialog(Activity activity,String title) {
        if (!StringUtil.isEmpty(title)){
//            dialog = new ProgressDialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//
            dialog = new CustomClipLoading(activity);
        }

    }

    public DialogCallback(Activity activity,String loadingText) {
        super();
        initDialog(activity,loadingText);
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //网络请求前显示对话框
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onAfter(@Nullable T t, @Nullable Exception e) {
        super.onAfter(t, e);
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
