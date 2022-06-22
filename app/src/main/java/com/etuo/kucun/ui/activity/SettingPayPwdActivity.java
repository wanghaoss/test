package com.etuo.kucun.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseHeaderBarActivity;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.StringUtil;
import com.etuo.kucun.utils.ToolsHelper;
import com.etuo.kucun.widget.PayPsdInputView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yhn on 2018/8/8.
 * <p>
 * 设置支付密码
 */

public class SettingPayPwdActivity extends BaseHeaderBarActivity {
    @BindView(R.id.my_pay_pwd_et)
    PayPsdInputView mMyPayPwdEt;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_hide_massage)
    TextView mTvHideMassage;
    @BindView(R.id.ok_bt)
    Button mOkBt;

    private String firstPwd;// 第一次输入的密码
    private String secondPwd;
    private boolean isFirst = true;// 第一次输入密码
    private  boolean isOk = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_setting_pay_pwd);
        ButterKnife.bind(this);
        setHeaderTitle("设置账户密码");
        mOkBt.setBackground(getResources().getDrawable(R.drawable.gray_btn_bg));
        initData();
    }

    private void initData() {

        mMyPayPwdEt.setComparePassword(new PayPsdInputView.onPasswordListener() {

            @Override
            public void onDifference(String oldPsd, String newPsd) {
                // TODO: 2018/1/22  和上次输入的密码不一致  做相应的业务逻辑处理

                LogUtil.d("两次密码输入不同 : "+ oldPsd + "!=" + newPsd, Toast.LENGTH_SHORT);
                Toast.makeText(SettingPayPwdActivity.this, "两次密码输入不同,请重新输入" , Toast.LENGTH_SHORT).show();
                mMyPayPwdEt.setComparePassword("");
                mMyPayPwdEt.cleanPsd();
                isFirst = true;
                mTvTitleName.setText("设置六位数字的账户支付密码");
                mTvHideMassage.setVisibility(View.VISIBLE);
                mOkBt.setVisibility(View.GONE);
            }

            @Override
            public void onEqual(String psd) {
                // TODO: 2017/5/7 两次输入密码相同，那就去进行支付楼
                isOk = true;
                mOkBt.setBackground(getResources().getDrawable(R.drawable.blue_btn_bg));
                LogUtil.d("密码相同 : "+psd);
//                mMyPayPwdEt.setComparePassword("");
//                mMyPayPwdEt.cleanPsd();
            }

            @Override
            public void inputFinished(String inputPsd) {
                // TODO: 2018/1/3 输完逻辑

                LogUtil.d("输入完毕 : "+inputPsd);
                if (isOkInputpwd(inputPsd)){
                    if (isFirst){
                        isFirst = false;
                        mMyPayPwdEt.cleanPsd();
                        mMyPayPwdEt.setComparePassword(inputPsd);
                        mTvTitleName.setText("请在再次输入");
                        mTvHideMassage.setVisibility(View.INVISIBLE);
                        mOkBt.setVisibility(View.VISIBLE);

                    }

                }else {
                    mMyPayPwdEt.setComparePassword("");
                }

            }
        });
    }


    /**
     * 确认
     */
    @OnClick(R.id.ok_bt)
    public void onViewClicked() {
        if (isOk){
            showToast("设置成功");
            finish();
        }else {
            showToast("请输入六位数字密码");
        }

    }

    private boolean isOkInputpwd(String pwd){
        if (StringUtil.isEmpty(pwd)){
            showToast("请输入六位数字密码");
            return  false;
        }

        if (pwd.length()!=6){
            showToast("请输入六位数字密码");
            return false;
        }
        if (ToolsHelper.isOrderNumeric(pwd) || ToolsHelper.isOrderNumeric_(pwd)){
            showToast("支付密码不能是连续的数字");
            return false;
        }
        if (ToolsHelper.equalStr(pwd) ){
            showToast("支付密码不能是重复的数字");
            return false;
        }
        return true;
    }
}
