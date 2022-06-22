package com.etuo.kucun.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.etuo.kucun.R;


/**
 * @author yhn
 * @version V1.0
 * @Description:自定义进度条 提示
 * @date 2020/02/24 15:49
 */
public class CustomClipHintLoading extends ProgressDialog {
	private AnimationDrawable mAnimation;
	private Context mContext;


	private TextView mTextView;

	private String hite;
	public CustomClipHintLoading(Context context,String hite) {
		super(context, R.style.dialog);
		this.mContext = context;
		this.hite = hite;
		// 设置在窗口的边界之外时，该对话框是否被取消(true-取消)
		setOnOutSide(false);
	}

	// 设置在窗口的边界之外时，该对话框是否被取消(true-取消)
	public void setOnOutSide(boolean state) {
		setCanceledOnTouchOutside(state);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_progress_hint_dialog);
//		mTextView = findViewById(R.id.tv_hide);
//		mTextView.setText(hite);

	}

}
