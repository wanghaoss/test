package com.etuo.kucun.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseHeaderBarActivity;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.callback.DialogCallback;
import com.etuo.kucun.callback.JsonCallback;
import com.etuo.kucun.model.TokenModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.utils.AppUtils;
import com.etuo.kucun.utils.NetUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.StringUtil;
import com.etuo.kucun.utils.UrlTools;
import com.etuo.kucun.widget.ClearEditText;
import com.lzy.okgo.OkGo;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by yhn on 2018/7/16.
 * 注册
 */

public class RegistActivity extends BaseHeaderBarActivity {

    @BindView(R.id.login_account_et)
    ClearEditText mLoginAccountEt;
    @BindView(R.id.find_pwd_verify_et)
    ClearEditText mFindPwdVerifyEt;
    @BindView(R.id.get_check_num_btn)
    Button mGetCheckNumBtn;
    @BindView(R.id.login_pwd_et)
    ClearEditText mLoginPwdEt;
    @BindView(R.id.login_pwd_cb)
    CheckBox mLoginPwdCb;
    @BindView(R.id.login_remember_cb)
    CheckBox mLoginRememberCb;
    @BindView(R.id.regist_protocol)
    TextView mRegistProtocol;
    @BindView(R.id.login_bt)
    Button mLoginBt;
    @BindView(R.id.regist_login_tv)
    TextView mRegistLoginTv;

    private Timer timer;
    private TimerTask timerTask;
    private int count = 120;
    private static final int MSG_SUCCESS = 1;
    private static final int MSG_FAILURE = 2;

    private boolean isPostCode = false;
    private boolean isHaveRead = false;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SUCCESS:
                    mGetCheckNumBtn.setClickable(false);
                    int count = (Integer) msg.obj;
                    mGetCheckNumBtn.setText(count + "'后重发");
                    break;
                case MSG_FAILURE:
                    isPostCode = false;
                    mGetCheckNumBtn.setClickable(true);
                    mGetCheckNumBtn.setText("重新获取验证码");
                    mGetCheckNumBtn.setFocusable(true);
                    timerTask.cancel();
                    timer.cancel();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        setHeaderTitle("注册");
        initListener();
        init();
    }

    private void init() {


    }

    private void initListener() {
        isHaveRead = mLoginRememberCb.isChecked();

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
                isHaveRead = isChecked;
            }
        });
    }


    /**
     * 开始计时
     */
    public void startCount() {
        if (!isPostCode) {
            isPostCode = true;
        } else {
            return;
        }
        timer = new Timer();
        count = 120;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (count > 0) {
                    Message message = new Message();
                    message.what = MSG_SUCCESS;
                    message.obj = count;
                    mHandler.sendMessage(message);
                } else {
                    mHandler.sendEmptyMessage(MSG_FAILURE);
                }
                count--;
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private Activity getActivity() {
        return RegistActivity.this;
    }

    @OnClick({R.id.get_check_num_btn, R.id.regist_protocol, R.id.login_bt, R.id.regist_login_tv})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {

            case R.id.get_check_num_btn: // 获取验证码

                getCode();

                break;
            case R.id.regist_protocol:// 服务协议
                //http://47.95.42.132/userWap/regist/agreement.html

                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("LoadingUrl", UrlTools.RegistInfoLink);
                startActivity(intent);

                break;
            case R.id.login_bt: // 注册
                if (checkRegistered()) {
                    checkCode();
                }
                break;
            case R.id.regist_login_tv://立即登录
                intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }

    /**
     * 验证验证码
     */
    private void checkCode() {

        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }

        OkGo.get(UrlTools.getInterfaceUrl(UrlTools.CHECK_VERIFY_CODE))
                .tag(this)
                .params("phoneNumber", mLoginAccountEt.getText().toString().trim())//手机号
                .params("verifyCode", mFindPwdVerifyEt.getText().toString().trim())//
                .params("codeType", ExtraConfig.TypeCode.CHECK_TYPE_REGIST)
                .execute(new DialogCallback<LzyResponse<String>>(getActivity(), "") {
                    @Override
                    public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {

                        String getDate = responseData.data;

                        Log.d("regist_", getDate);
                        RegistUser();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.toastTime(getActivity(), e.getMessage().toString(), 3);
                    }
                });

    }

    /**
     * 注册
     */
    private void RegistUser() {

        OkGo.post(UrlTools.getInterfaceUrl(UrlTools.REGIST))
                .tag(this)
                .params("phoneNumber", mLoginAccountEt.getText().toString().trim())//手机号
                .params("passWord", mLoginPwdEt.getText().toString().trim())//登录密码
                .params("inviteCode", mFindPwdVerifyEt.getText().toString().trim()) // 验证码
                .params("source", "01") //安卓传"01",IOS传"02",WAP传"03"

                .params("imei", AppUtils.getImei())//

                .execute(new DialogCallback<LzyResponse<TokenModel>>(getActivity(), "") {
                    @Override
                    public void onSuccess(LzyResponse<TokenModel> responseData, Call call, Response response) {
                        PreferenceCache.putUsername(mLoginAccountEt.getText().toString().trim());// 返回成功
                        PreferenceCache.putToken(responseData.data.getToken().toString());
                        PreferenceCache.putIsFirstLogin(true);

                        Intent intent = new Intent(getActivity(), SuccessActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        ShowToast.toastTime(getActivity(), e.getMessage().toString(), 3);
                    }
                });
    }

    /**
     * 校验输入内容
     */
    private boolean checkRegistered() {
        String mobile = mLoginAccountEt.getText().toString().trim();
        if (StringUtil.isEmpty(mobile)) {
            ShowToast.toastTime(getActivity(), "请输入手机号", 3);
            mLoginAccountEt.requestFocus();
            return false;
        }
        if (mobile.length() != 11) {
            ShowToast.toastTime(getActivity(), "手机号码不正确", 3);
            mLoginAccountEt.requestFocus();
            return false;
        }
        String code = mFindPwdVerifyEt.getText().toString().trim();
        if (StringUtil.isEmpty(code)) {
            ShowToast.toastTime(getActivity(), "请输入验证码", 3);
            mFindPwdVerifyEt.requestFocus();
            return false;
        }
//        if (code.length() != 6) {
//            ShowToast.toastTime(getActivity(), "验证码不正确", 3);
//            mFindPwdVerifyEt.requestFocus();
//            return false;
//        }
        String pwd = mLoginPwdEt.getText().toString().trim();

        if (StringUtil.isEmpty(pwd)) {
            ShowToast.toastTime(getActivity(), "请输入密码", 3);
            mLoginPwdEt.requestFocus();
            return false;
        }
        if (pwd.length() < 6) {
            ShowToast.toastTime(getActivity(), "密码至少是6位", 3);
            mLoginPwdEt.requestFocus();
            return false;
        }
        if (!isHaveRead) {
            ShowToast.toastTime(getActivity(), "请选择同意平台服务条款", 3);
            return false;
        }

        return true;
    }

    /**
     * 获取验证码
     */

    private void getCode() {
        if (StringUtil.isEmpty(mLoginAccountEt.getText().toString().trim())) {
            ShowToast.toastTime(getActivity(), "请输入手机号", 3);
            mLoginAccountEt.requestFocus();
            return;
        }

        String phone = mLoginAccountEt.getText().toString().trim();
        if (phone.length() != 11 || !"1".equals(phone.substring(0, 1))) {
            ShowToast.toastTime(getActivity(), "请输入正确手机号", 3);
            mLoginAccountEt.requestFocus();
            return;
        }

        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }
        try {
            OkGo.get(UrlTools.getInterfaceUrl(UrlTools.CODE))
                    .tag(this)
                    .params("phoneNumber", mLoginAccountEt.getText().toString().trim())//手机号
                    .params("verifyType", ExtraConfig.TypeCode.CHECK_TYPE_REGIST)//验证码类型
                    .execute(new JsonCallback<LzyResponse<String>>() {
                        @Override
                        public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {
                            startCount();
                            ShowToast.toastTime(getActivity(), responseData.message.toString(), 3);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            ShowToast.toastTime(getActivity(), e.getMessage().toString(), 3);
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
