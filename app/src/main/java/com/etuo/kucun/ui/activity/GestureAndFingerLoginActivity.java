package com.etuo.kucun.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.etuo.kucun.FrameworkApp;
import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseActivity;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.StringUtil;
import com.etuo.kucun.view.GestureAndFingerprintView.GestureLoginView;
import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;

import java.util.List;


/**
 * 校验手势和指纹密码
 * 
 * @author yhn
 * @version 1.0.0
 * @since 2018/06/12
 */

public class GestureAndFingerLoginActivity extends BaseActivity implements GestureLoginView.GestureCallBack,View.OnClickListener {

	private GestureLoginView gestureView;
	private TextView gesture_loginTV;
	private int gestureFlg = -1; // 1表示修改密码 2表示关闭手势校验一下 3表示从闪屏页跳入

	private TextView tv_forget_gesture_pwd;
	private LinearLayout l_verify_finger;//指纹显示


	//指纹
	private FingerprintIdentify mFingerprintIdentify;

	//指纹识别弹窗提示
	private Dialog dialog;

	private boolean isClick = false; //是否可点击

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture_finger_login);
		gestureFlg = getIntent().getIntExtra("gestureFlg", -1);
		initView();

		if (PreferenceCache.getFingerFlg()){
			initFinger();
		}


	}

	/**
	 * 指纹相关
	 */
	private void initFinger() {
		mFingerprintIdentify = new FingerprintIdentify(this);
		//弹出dialog，自动弹出
		dialog = new Dialog(GestureAndFingerLoginActivity.this, R.style.Dialog);
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

	}

	private void initView() {


		tv_forget_gesture_pwd = (TextView) findViewById(R.id.tv_forget_gesture_pwd);
		gestureView = (GestureLoginView) findViewById(R.id.gesture1);
		gesture_loginTV= (TextView) findViewById(R.id.gesture_loginTV);
		l_verify_finger = (LinearLayout) findViewById(R.id.l_verify_finger);
		gestureView.setGestureCallBack(this);
		gestureView.clearCacheLogin();
		tv_forget_gesture_pwd.setOnClickListener(this);
		String pNumber= PreferenceCache.getUsername();


		gesture_loginTV.setText("欢迎您，"+ StringUtil.getStringEncryptByPhoneNum(pNumber));
		//添加了手势解锁
		if (PreferenceCache.isGesture()){

			gestureView.setVisibility(View.VISIBLE);
			l_verify_finger.setVisibility(View.GONE);
		}else {
			gestureView.setVisibility(View.GONE);
			l_verify_finger.setVisibility(View.VISIBLE);
			l_verify_finger.setOnClickListener(this);
		}



	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_forget_gesture_pwd:
				// 使用密码登录
				Intent intent = new Intent(this, LoginActivity.class);
				FrameworkApp.forgetGesturePwd = 1;

				PreferenceCache.clearAllUserPwd();

				startActivity(intent);
				finish();
				break;
			case R.id.l_verify_finger:
				if (!isClick){
					if (!dialog.isShowing()){
						initVerify();
						dialog.show();
					}
				}else {
					ShowToast.showToast("验证失败,指纹暂被锁定,请用其它方式登录", GestureAndFingerLoginActivity.this);
				}


				break;
		}

	}

	@Override
	public void gestureVerifySuccessListener(int stateFlag, List<GestureLoginView.GestureBean> data, boolean success) {
		if (success) {
			if (gestureFlg == 1) {
				Intent intent = new Intent(this, GestureSetActivity.class);
				startActivity(intent);
				finish();
			}else if (gestureFlg == 2) {
				finish();
			} else {
				startActivity(new Intent(GestureAndFingerLoginActivity.this,
						MainActivity.class));
				finish();
			}
		}
	}


	private void initVerify() {
		mFingerprintIdentify.startIdentify(5, new BaseFingerprint.FingerprintIdentifyListener() {
			@Override
			public void onSucceed() {


				LogUtil.d("onSucceed   :  " +"dialog.isShowing  :  " +dialog.isShowing()
						+ "  PreferenceCache.getFingerFlg() :  " +  PreferenceCache.getFingerFlg()  );
				if (PreferenceCache.getFingerFlg()){

					if (dialog.isShowing()){
						Intent intent = new Intent(GestureAndFingerLoginActivity.this,MainActivity.class);
						startActivity(intent);
						finish();
					}

				}

			}

			@Override
			public void onNotMatch(int availableTimes) {
				LogUtil.d("onNotMatch   :  " +"dialog.isShowing  :  " +dialog.isShowing()
						+ "  PreferenceCache.getFingerFlg() :  " +  PreferenceCache.getFingerFlg()  );

				if (PreferenceCache.getFingerFlg()){
					if (dialog.isShowing()){
						ShowToast.showToast("验证失败，您还有"+availableTimes+"次机会", GestureAndFingerLoginActivity.this);
					}
				}





			}

			@Override
			public void onFailed(boolean isDeviceLocked) {
				LogUtil.d("onFailed   :  " +"dialog.isShowing  :  " +dialog.isShowing()
						+ "  PreferenceCache.getFingerFlg() :  " +  PreferenceCache.getFingerFlg()  );

				if (PreferenceCache.getFingerFlg()){
					if (dialog.isShowing()){
						isClick=true;
						ShowToast.showToast("验证失败,指纹暂被锁定", GestureAndFingerLoginActivity.this);
						dialog.dismiss();
					}
				}





			}

			@Override
			public void onStartFailedByDeviceLocked() {
				LogUtil.d("onStartFailedByDeviceLocked   :  " +"dialog.isShowing  :  " +dialog.isShowing()
						+ "  PreferenceCache.getFingerFlg() :  " +  PreferenceCache.getFingerFlg()  );
				if (PreferenceCache.getFingerFlg()){
					if (dialog.isShowing()){
						isClick=true;
						ShowToast.showToast("验证失败,指纹已被锁定", GestureAndFingerLoginActivity.this);
					}
				}



			}
		});
	}


}
