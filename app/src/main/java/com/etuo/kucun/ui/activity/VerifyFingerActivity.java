package com.etuo.kucun.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.etuo.kucun.FrameworkApp;
import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseActivity;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.utils.ShowToast;
import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;


public class VerifyFingerActivity extends BaseActivity implements View.OnClickListener{
    private ImageView imageView;
    private FingerprintIdentify mFingerprintIdentify;
    private TextView tv_hand_login;
    private Dialog dialog;
    private boolean isClick;
    private TextView tv_mian_login;
    private ImageView iv_finger_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_finger);
        mFingerprintIdentify = new FingerprintIdentify(this);

        //弹出dialog，自动弹出
        dialog = new Dialog(VerifyFingerActivity.this, R.style.Dialog);
        dialog.setContentView(R.layout.item_dialog);
        dialog.setCancelable(false);
        dialog.show();
        TextView tv = (TextView) dialog.findViewById(R.id.tv_cancel);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        if (dialog.isShowing()){
            initVerify();
        }
        tv_hand_login = (TextView) findViewById(R.id.tv_hand_login);
        if (PreferenceCache.isGesture()){
            tv_hand_login.setVisibility(View.VISIBLE);
        }else{
            tv_hand_login.setVisibility(View.GONE);
        }

        tv_hand_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),GestureLoginActivity.class);
                //等于3为认证成功
                intent.putExtra("gestureFlg",3);
                startActivity(intent);
                finish();
            }
        });
        imageView = (ImageView) findViewById(R.id.iv_verify_finger);
        imageView.setOnClickListener(this);
        tv_mian_login = (TextView) findViewById(R.id.tv_mian_login);
        tv_mian_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_verify_finger:
                if (!isClick){
                    if (!dialog.isShowing()){
                        dialog.show();
                    }
                }
                break;
            case R.id.tv_mian_login:
                // 使用密码登录
                Intent intent = new Intent(this, LoginActivity.class);
                FrameworkApp.forgetGesturePwd = 1;
                PreferenceCache.clearAllUserPwd();
                startActivity(intent);
                finish();
                break;
        }
    }
    private void initVerify() {
        mFingerprintIdentify.startIdentify(4, new BaseFingerprint.FingerprintIdentifyListener() {
            @Override
            public void onSucceed() {
                Intent intent = new Intent(VerifyFingerActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onNotMatch(int availableTimes) {


                ShowToast.showToast("验证失败，您还有"+availableTimes+"次机会", VerifyFingerActivity.this);
            }

            @Override
            public void onFailed(boolean isDeviceLocked) {
                ShowToast.showToast("验证失败指纹暂被锁定", VerifyFingerActivity.this);

                isClick=true;
                if (dialog.isShowing()){
                    dialog.dismiss();
                }


            }

            @Override
            public void onStartFailedByDeviceLocked() {
                ShowToast.showToast("验证失败，指纹已被锁定", VerifyFingerActivity.this);

            }
        });
    }
}
