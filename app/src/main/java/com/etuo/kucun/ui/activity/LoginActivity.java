package com.etuo.kucun.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseActivity;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.callback.DialogCallback;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.NetUtil;
import com.etuo.kucun.utils.RSAUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.StatusBarUtil;
import com.etuo.kucun.utils.StringUtil;
import com.etuo.kucun.utils.UrlTools;
import com.etuo.kucun.view.VerificationCodeView;
import com.etuo.kucun.widget.ClearEditText;
import com.lzy.okgo.OkGo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Response;

import static com.etuo.kucun.utils.UrlTools.LOGIN;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.login_back_img)
    ImageView mLoginBackImg;
    @BindView(R.id.login_account_et)
    ClearEditText mLoginAccountEt;
    @BindView(R.id.login_pwd_et)
    ClearEditText mLoginPwdEt;
    @BindView(R.id.login_pwd_cb)
    CheckBox mLoginPwdCb;
    @BindView(R.id.login_identifying_et)
    ClearEditText mLoginIdentifyingEt;
    @BindView(R.id.login_identifying_img)
    VerificationCodeView mLoginIdentifyingImg;
    @BindView(R.id.login_remember_cb)
    CheckBox mLoginRememberCb;
    @BindView(R.id.login_bt)
    Button mLoginBt;
    @BindView(R.id.login_forget_tv)
    TextView mLoginForgetTv;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.regist_protocol)
    TextView registProtocol;
    @BindView(R.id.user_agreement)
    TextView userAgreement;


    private Intent intent;

    private String intent_w = "";// 要加载哪个页


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
        initListener();
        initData();
    }

    /**
     *
     */
    private void initData() {
        intent_w = getIntent().getStringExtra(ExtraConfig.TypeCode.FROM_INT_LOGIN);
        LogUtil.d("Main_intent_w :  " + intent_w);

//        if (PreferenceCache.isSaveStatus()){
//            mLoginRememberCb.setChecked(true);
        //mLoginAccountEt.setText(PreferenceCache.getUsername());
//        mLoginAccountEt.setText("13352347268");

        mLoginAccountEt.setText("18562885680");
        mLoginPwdEt.setText("a111111");

//        }else {
//            mLoginRememberCb.setChecked(false);
//            mLoginAccountEt.setText("");
//        }
    }


    private Activity getActivity() {
        return LoginActivity.this;
    }

    private void getLogin() {

        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }

        try {
            OkGo.post(UrlTools.getInterfaceUrl(LOGIN))
                    .tag(this)
                    .params("username", URLEncoder.encode(RSAUtil.rsaEncrypt(mLoginAccountEt.getText().toString().trim()), "UTF-8"))
                    .params("password", URLEncoder.encode(RSAUtil.rsaEncrypt(mLoginPwdEt.getText().toString().trim()), "UTF-8"))
//
                    .params("tagEnd", "2") //安卓传"2",IOS传"3"
                    //                    .params("imei", Des.encode(AppUtils.getImei()) )//
                    //                    .params("loginIp",AppUtils.getMacAddress())//
                    //TODO  初始化获取设备码以后,传到极光,此次应该能拿到极光的值,传给java后端
                    .params("registrationId", JPushInterface.getRegistrationID(this))
                    .execute(new DialogCallback<LzyResponse<String>>(getActivity(), "登录中...") {
                        @Override
                        public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {

                            PreferenceCache.clearFailPwdInfo();
                            PreferenceCache.putToken(responseData.token);

                            //                            if (PreferenceCache.isSaveStatus()){
                            PreferenceCache.putUsername(mLoginAccountEt.getText().toString().trim());

                            //                            }
                            PreferenceCache.putUserId(responseData.userId);
                            PreferenceCache.putCompanyId(responseData.companyId);
                            PreferenceCache.putIsFirstLogin(true);
                            Intent intent = new Intent(getActivity(), MainActivity2.class);
                            intent.putExtra(ExtraConfig.TypeCode.FROM_INT_LOGIN, intent_w);
                            startActivity(intent);
                            getActivity().finish();

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            ShowToast.tCustom(getActivity(), e.getMessage());

                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void initListener() {


        mLoginPwdCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (mLoginPwdCb.isChecked()) {

                    // 文本正常显示

                    mLoginPwdEt
                            .setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    Editable etable = mLoginPwdEt.getText();

                    Selection.setSelection(etable, etable.length());
                } else {

                    // 文本以密码形式显示

                    mLoginPwdEt.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    // 下面两行代码实现: 输入框光标一直在输入文本后面

                    Editable etable = mLoginPwdEt.getText();

                    Selection.setSelection(etable, etable.length());

                }
            }
        });

        mLoginRememberCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mLoginRememberCb.isChecked()) {//选择了
                    PreferenceCache.putSaveStatus(true);
                } else {
                    PreferenceCache.putSaveStatus(false);
                }
            }
        });
    }

    @OnClick({R.id.login_back_img, R.id.login_identifying_img, R.id.login_bt, R.id.login_forget_tv, R.id.regist_protocol, R.id.user_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_back_img:
                finish();
                break;
            // 验证码
            case R.id.login_identifying_img:
                mLoginIdentifyingEt.setText("");
                mLoginIdentifyingImg.refreshCode();
                break;
            //登录
            case R.id.login_bt:
                if (checkLogin()) {
                    getLogin();
                }
                break;
            //忘记密码
            case R.id.login_forget_tv:
                intent = new Intent(mContext, FindPwdActivity.class);
                startActivity(intent);
                break;
            // 隐私条款
            case R.id.regist_protocol:
                intent = new Intent(getActivity(), WebServiceViewActivity.class);
                intent.putExtra("LoadingUrl", UrlTools.getInterfaceUrl(UrlTools.privacyAgreementPageLink));
                intent.putExtra("title", "隐私政策");
                startActivity(intent);
                break;
            // 用户协议
            case R.id.user_agreement:
                intent = new Intent(mContext, UserAgreementActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//如果返回键按下
            //此处写退向后台的处理
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean checkLogin() {
        if (StringUtil.isEmpty(mLoginAccountEt.getText().toString().trim())) {
            showToast("请输入手机号");
            mLoginAccountEt.requestFocus();
            return false;
        }
        String phone = mLoginAccountEt.getText().toString().trim();
        if (phone.length() != 11 || !"1".equals(phone.substring(0, 1))) {
            showToast("请输入正确手机号");

            mLoginAccountEt.requestFocus();
            return false;
        }
        if (StringUtil.isEmpty(mLoginPwdEt.getText().toString().trim())) {
            showToast("请输入密码");

            mLoginPwdEt.requestFocus();
            return false;
        }

//        if (StringUtil.isEmpty(mLoginIdentifyingEt.getText().toString().trim())) {
//            showToast("请输入验证码");
//
//            mLoginIdentifyingEt.requestFocus();
//            return false;
//        }
//        String input = mLoginIdentifyingEt.getText().toString().trim().toLowerCase();
//        String code = mLoginIdentifyingImg.getvCode().toLowerCase();
//
//
//        LogUtil.d("验证码 : " + code);
//        if (!input.equals(code)) {
//            showToast("请输入正确的验证码");
//
//            mLoginIdentifyingEt.requestFocus();
//            return false;
//        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

}