package com.example.new_androidclient.Base;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Util.ScreenAdapter;

@SuppressLint("SourceLockedOrientationActivity")
public class BaseActivity extends AppCompatActivity {
    public String TAG;
    protected Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   TAG = this.getClass().getSimpleName();
        TAG = "zzqq";
        mContext = this;
        ARouter.getInstance().inject(this);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

    }

}
