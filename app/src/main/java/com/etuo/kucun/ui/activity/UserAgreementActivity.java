package com.etuo.kucun.ui.activity;

import android.os.Bundle;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseHeaderBarActivity;

import butterknife.ButterKnife;

/**
 * ================================================
 *
 * @author :Administrator
 * @version :
 * @date :2019/10/24/11:02
 * @ProjectNameDescribe :用户协议（腾讯审核非要用）
 * 修订历史：
 * ================================================
 */
public class UserAgreementActivity extends BaseHeaderBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        ButterKnife.bind(this);
        setHeaderTitle("用户协议");
    }
}
