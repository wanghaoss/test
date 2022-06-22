package com.example.new_androidclient.hazard;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.KeyEvent;

import com.example.new_androidclient.Util.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class HazardPicObserver implements Observer<Bitmap> {
    private Context mContext;
    private ProgressDialog progressDialog;
    private Disposable mDisposable;

    public HazardPicObserver(Context mContext, boolean mShowDialog) {
        this(mContext, mShowDialog, "");
    }

    public HazardPicObserver(Context mContext, boolean mShowDialog, String title) {
        this.mContext = mContext;
        if (progressDialog == null && mShowDialog) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle(title);
            progressDialog.setOnKeyListener((dialog, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finishDisposable();
                }
                return false;
            });
        }
    }
    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        if (!checkNet(mContext)) {
            ToastUtil.show(mContext, "请连接网络");
            finishDisposable();
        } else {
            if(progressDialog != null)
                progressDialog.show();

        }
    }

    @Override
    public void onNext(Bitmap bitmap) {
        hideDialog();
        onSuccess(bitmap);
        finishDisposable();
    }

    @Override
    public void onError(Throwable e) {
        hideDialog();
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(Bitmap bean);

    public abstract void onFailure(String err);

    private void finishDisposable() {
        if (mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
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
