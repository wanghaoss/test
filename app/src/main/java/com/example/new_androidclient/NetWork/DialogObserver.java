package com.example.new_androidclient.NetWork;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.KeyEvent;

import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.ToastUtil;

import io.reactivex.disposables.Disposable;

public abstract class DialogObserver<T> extends BaseObserver<T> {
    private Context mContext;
    private ProgressDialog progressDialog;
    private Disposable mDisposable;

    private static long lastClickTime = -1;

    public DialogObserver(Context mContext, boolean mShowDialog) {
        this(mContext, mShowDialog, "");
    }

    public DialogObserver(Context mContext, boolean mShowDialog, String title) {
        this.mContext = mContext;
        if (progressDialog == null && mShowDialog) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle(title);
            progressDialog.setOnKeyListener((dialog, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finishDisposable("DialogObserver");
                }
                return false;
            });
        }
    }

    @Override
    public void onComplete() {

        super.onComplete();
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        mDisposable = d;
//        if(isFastClick()){
//            finishDisposable("isFastClick");
//        }

        if (!checkNet(mContext)) {
            ToastUtil.show(mContext, "请连接网络");
            finishDisposable("onSubscribe");
        } else {
            if (progressDialog != null)
                progressDialog.show();
        }

    }

    @Override
    public void onNext(BaseResponse<T> tBaseResponse) {
        super.onNext(tBaseResponse);
        hideDialog();
        finishDisposable("onNext");
    }

    @Override
    public void onError(Throwable e) {
        hideDialog();
        super.onError(e);
    }

    private void finishDisposable(String msg) {
        mDisposable.dispose();
        if (mDisposable.isDisposed()) {
            hideDialog();
        //    LogUtil.i(msg);
        }
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;

    }

    private boolean isFastClick() {
        boolean flag;
        long curClickTime = System.currentTimeMillis();
        if (curClickTime - lastClickTime > 2000){
            flag = false;
        }else{
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    private boolean checkNet(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo netInfo = cm.getActiveNetworkInfo();

                if (netInfo != null && netInfo.isAvailable()
                        && netInfo.isConnected()) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
