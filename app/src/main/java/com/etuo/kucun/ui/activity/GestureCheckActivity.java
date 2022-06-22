package com.etuo.kucun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.etuo.kucun.FrameworkApp;
import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseHeaderBarActivity;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.utils.StringUtil;
import com.etuo.kucun.view.GestureAndFingerprintView.GestureView;

import java.util.List;

/**
 * 校验手势密码
 * 
 * @author Liujinxin
 * @version 1.0.0
 * @since 2018/03/8
 */

public class GestureCheckActivity extends BaseHeaderBarActivity implements
		GestureView.GestureCallBack {

	private GestureView gestureView;
	private TextView gesture_loginTV;
	private int gestureFlg = -1; // 1表示修改密码 2表示关闭手势校验一下 3表示从闪屏页跳入

	private TextView tv_forget_gesture_pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture_check);

		setHeaderTitle("手势密码");
		gesture_loginTV= (TextView) findViewById(R.id.gesture_loginTV);
		gestureFlg = getIntent().getIntExtra(ExtraConfig.IntentType.KET_GESTURE_FLAG, -1);
		gestureView = (GestureView) findViewById(R.id.gesture1);
		gestureView.setGestureCallBack(this);
		gestureView.clearCacheLogin();
		initView();
	}

	private void initView() {
		tv_forget_gesture_pwd = (TextView) findViewById(R.id.tv_forget_gesture_pwd);

		tv_forget_gesture_pwd.setOnClickListener(this);

		String pNumber= PreferenceCache.getUsername();

		gesture_loginTV.setText("欢迎您，"+ StringUtil.getStringEncryptByPhoneNum(pNumber));
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
			case R.id.tv_forget_gesture_pwd:
				// 密码登录
				Intent intent = new Intent(this, LoginActivity.class);
				FrameworkApp.forgetGesturePwd = 1;
				startActivity(intent);
				finish();
				break;
		}
	}

	@Override
	public void gestureVerifySuccessListener(int stateFlag,
                                             List<GestureView.GestureBean> data, boolean success) {
		if (success) {
			if (gestureFlg == 1) {
				Intent intent = new Intent(this, GestureSetActivity.class);
				startActivity(intent);
				finish();
			}else if (gestureFlg == 2) {
				setResult(RESULT_OK);
				finish();
			} else {
				startActivity(new Intent(GestureCheckActivity.this,MainActivity.class));
				finish();
			}
		}
	}

}
