package com.etuo.kucun.ui.activity;

import android.os.Bundle;


import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseHeaderBarActivity;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.view.GestureAndFingerprintView.GestureSetView;
import com.etuo.kucun.view.GestureAndFingerprintView.GestureView;

import java.util.List;

/**
 * 设置手势密码
 * 
 * @author Liujinxin
 * @version 1.0.0
 * @since 2018/03/8
 */

public class GestureSetActivity extends BaseHeaderBarActivity implements GestureSetView.GestureCallBack{

	private GestureSetView gestureView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture_set);

		setHeaderTitle("设置手势密码");
		gestureView = (GestureSetView) findViewById(R.id.gesture);
		gestureView.setGestureCallBack(this);
		PreferenceCache.putGestureFlag(false);
		// 不调用这个方法会造成第二次启动程序直接进入手势识别而不是手势设置
		gestureView.clearCache();
		gestureView.setMinPointNums(5);
	}

	@Override
	public void gestureVerifySuccessListener(int stateFlag, List<GestureSetView.GestureBean> data, boolean success) {
		if (stateFlag == GestureView.STATE_LOGIN) {
			PreferenceCache.putGestureFlag(true);
			if (!PreferenceCache.isGesture()){
				PreferenceCache.putIsGesture(true);
			}
			setResult(RESULT_OK);
			finish();
		}
	}
}
