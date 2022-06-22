package com.example.new_androidclient.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.PermissionUtil;
import com.example.new_androidclient.Util.SPUtil;

import java.lang.ref.WeakReference;

import cn.jpush.android.api.JPushInterface;

import static com.example.new_androidclient.Other.SPString.Token;

public class LoadingActivity extends BaseActivity {
    private MyHandler myHandler;
    private final int TO_LOGINACTIVITY = 1;
    private final int TO_MAINACTIVITY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        new Handler(getMainLooper()).post(new MyRunnable(LoadingActivity.this));
        myHandler = new MyHandler(LoadingActivity.this);

    }

    class MyRunnable implements Runnable {
        private WeakReference<LoadingActivity> weakReference;

        MyRunnable(LoadingActivity loadingActivity) {
            weakReference = new WeakReference<>(loadingActivity);
        }

        @SuppressLint("CheckResult")
        @Override
        public void run() {
            final LoadingActivity activity = weakReference.get();
            PermissionUtil.checkLoginPermission(activity, LoadingActivity.this::delayTwoSecondsSendMessage );
        }
    }

    private void delayTwoSecondsSendMessage() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String RegId = JPushInterface.getRegistrationID(getApplicationContext());
            Message msg = myHandler.obtainMessage();
            if (TextUtils.isEmpty(SPUtil.getData(Token, "").toString())) {
                msg.what = TO_LOGINACTIVITY;
            } else {
                msg.what = TO_MAINACTIVITY;
            }

            myHandler.handleMessage(msg);
        }).start();
    }


    class MyHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public MyHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }



        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mWeakReference.get();
            if (activity != null) {
                if (msg.what == TO_LOGINACTIVITY) {
                    ARouter.getInstance().build(RouteString.LoginActivity).navigation();
                    activity.finish();
                }
                if (msg.what == TO_MAINACTIVITY) {
                    ARouter.getInstance().build(RouteString.MainActivity).navigation();
                    activity.finish();

                }
            }

        }
    }
}
