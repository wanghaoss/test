package com.example.new_androidclient.device_management.Data;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.new_androidclient.R;

/**
 * Created by yu on 2020/9/12.
 * 10:49 AM
 * 弹窗
 */

public class AccountSecurityUnBindDialog extends Dialog implements View.OnClickListener {

    private OnCloseListener listener;
    private Context mContext;
    private TextView confirmTView;
    private TextView closeTView;
    private TextView titleTView;
    private TextView contentTView;
    private EditText contentEdit;
    private String type;
    private String tel;
    private String value;

    public AccountSecurityUnBindDialog(Context context, int themeResId, String type, String tel, OnCloseListener listener) {
        super(context, themeResId);
        this.listener = listener;
        this.mContext = context;
        this.type = type;
        this.tel = tel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_account_security_unbind);
        setCanceledOnTouchOutside(false);
        intView();
    }

    private void intView() {
        confirmTView = (TextView) findViewById(R.id.confirmTView); //确定按钮
        closeTView = (TextView) findViewById(R.id.closeTView); //取消按钮
        titleTView = (TextView) findViewById(R.id.titleTView); //标题
        contentTView = (TextView) findViewById(R.id.contentTView); //显示内容
        contentEdit = (EditText) findViewById(R.id.contentEdit); //输入框

        if ("1".equals(type)){
            titleTView.setText(tel);
            contentTView.setVisibility(View.VISIBLE);
            contentTView.setText("确认审核本临时计划吗？");

            contentEdit.setVisibility(View.GONE);
        }

        confirmTView.setOnClickListener(this);
        closeTView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.closeTView:
                if (listener != null) {
                    listener.onClose(this);
                }
                break;
            case R.id.confirmTView:
                if (listener != null) {
                    listener.onConfirm(this);
                }
                break;
        }
    }

    public interface OnCloseListener {
        void onConfirm(Dialog dialog);

        void onClose(Dialog dialog);

    }

    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            dismiss();
        }
        return false;
    }

}

