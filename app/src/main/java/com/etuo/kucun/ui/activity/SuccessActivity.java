package com.etuo.kucun.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseActivity;
import com.etuo.kucun.base.ExtraConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yhn on 2018/7/17.
 */


public class SuccessActivity extends BaseActivity {


    @BindView(R.id.successful_imageView)
    ImageView mSuccessfulImageView;
    @BindView(R.id.successful_tv)
    TextView mSuccessfulTv;
    @BindView(R.id.login_bt)
    Button mLoginBt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.login_bt)
    public void onViewClicked() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(ExtraConfig.TypeCode.FROM_INTTENT, ExtraConfig.TypeCode.MY_FRAGMENT);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}