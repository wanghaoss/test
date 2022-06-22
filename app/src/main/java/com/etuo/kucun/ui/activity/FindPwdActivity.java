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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseHeaderBarActivity;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.callback.DialogCallback;
import com.etuo.kucun.callback.JsonCallback;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.utils.NetUtil;
import com.etuo.kucun.utils.RSAUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.StringUtil;
import com.etuo.kucun.utils.UrlTools;
import com.etuo.kucun.widget.ClearEditText;
import com.lzy.okgo.OkGo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.etuo.kucun.utils.UrlTools.BACKPASSWORD;

/**
 * Created by yhn on 2018/7/16.
 * <p>
 * 忘记密码
 */

public class FindPwdActivity extends BaseHeaderBarActivity {
    @BindView(R.id.find_account_et)
    ClearEditText mFindAccountEt;
    @BindView(R.id.find_pwd_verify_et)
    ClearEditText mFindPwdVerifyEt;
    @BindView(R.id.find_pwd_verify_btn)
    Button mFindPwdVerifyBtn;
    @BindView(R.id.login_pwd_et)
    ClearEditText mLoginPwdEt;
    @BindView(R.id.login_pwd_cb)
    CheckBox mLoginPwdCb;
    @BindView(R.id.login_bt)
    Button mLoginBt;

    private Intent intent;
    private Timer timer;
    private TimerTask timerTask;
    private int count = 60;
    private static final int MSG_SUCCESS = 1;
    private static final int MSG_FAILURE = 2;
    private boolean isPostCode = false;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SUCCESS:
                    mFindPwdVerifyBtn.setClickable(false);
                    int count = (Integer) msg.obj;
                    mFindPwdVerifyBtn.setText(count + "'后重发");
                    break;
                case MSG_FAILURE:
                    isPostCode = false;
                    mFindPwdVerifyBtn.setClickable(true);
                    mFindPwdVerifyBtn.setText("重新获取验证码");
                    mFindPwdVerifyBtn.setFocusable(true);
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
        setContentView(R.layout.activity_find_pwd);
        ButterKnife.bind(this);
        setHeaderTitle("忘记密码");

        initListener();
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
        count = 60;
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

    @OnClick({R.id.find_pwd_verify_btn, R.id.login_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.find_pwd_verify_btn: // 获取验证码

                getCode();
                break;
            case R.id.login_bt: //确认修改

                if (checkRegistered()) {
                    backPwd();
                }
                break;
        }
    }

    private Activity getActivity() {
        return FindPwdActivity.this;
    }


    /**
     * 找回密码
     */
    private void backPwd() {

        try {
            OkGo.post(UrlTools.getInterfaceUrl(BACKPASSWORD))
                    .tag(this)
                    .params("phoneNumber", URLEncoder.encode(RSAUtil.rsaEncrypt(mFindAccountEt.getText().toString().trim()),"UTF-8"))
                    .params("newPassword",URLEncoder.encode(RSAUtil.rsaEncrypt(mLoginPwdEt.getText().toString().trim()),"UTF-8"))
                    .params("code", URLEncoder.encode(RSAUtil.rsaEncrypt(mFindPwdVerifyEt.getText().toString().trim()),"UTF-8"))
//                    .params("phoneNumber", mFindAccountEt.getText().toString().trim())
//                    .params("newPassword", mLoginPwdEt.getText().toString().trim())//新密码
//                    .params("code", mFindPwdVerifyEt.getText().toString().trim())//验证码
                    .execute(new DialogCallback<LzyResponse<String>>(getActivity(), "加载中...") {
                        @Override
                        public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {
                            ShowToast.toastTime(getActivity(), responseData.message, 3);
    
                            PreferenceCache.putToken("");
                            ShowToast.tCustom(getActivity(), "密码重置成功,请重新登录");
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
                            startActivity(intent);
    
                            finish();
                        }
    
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            ShowToast.toastTime(getActivity(), e.getMessage().toString(), 3);
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验输入内容
     */
    private boolean checkRegistered() {
        String mobile = mFindAccountEt.getText().toString().trim();
        if (StringUtil.isEmpty(mobile)) {
            ShowToast.toastTime(getActivity(), "请输入手机号", 3);
            mFindAccountEt.requestFocus();
            return false;
        }
        if (mobile.length() != 11) {
            ShowToast.toastTime(getActivity(), "手机号码不正确", 3);
            mFindAccountEt.requestFocus();
            return false;
        }
        String code = mFindPwdVerifyEt.getText().toString().trim();
        if (StringUtil.isEmpty(code)) {
            ShowToast.toastTime(getActivity(), "请输入验证码", 3);
            mFindPwdVerifyEt.requestFocus();
            return false;
        }

        String pwd = mLoginPwdEt.getText().toString().trim();

        if (StringUtil.isEmpty(pwd)) {
            ShowToast.toastTime(getActivity(), "请输入新密码", 3);
            mLoginPwdEt.requestFocus();
            return false;
        }
        if (pwd.length() < 6) {
            ShowToast.toastTime(getActivity(), "密码至少是6位", 3);
            mLoginPwdEt.requestFocus();
            return false;
        }


        return true;
    }

    /**
     * 获取验证码
     */

    private void getCode() {
        if (StringUtil.isEmpty(mFindAccountEt.getText().toString().trim())) {
            ShowToast.toastTime(getActivity(), "请输入手机号", 3);
            mFindAccountEt.requestFocus();
            return;
        }

        String phone = mFindAccountEt.getText().toString().trim();
        if (phone.length() != 11 || !"1".equals(phone.substring(0, 1))) {
            ShowToast.toastTime(getActivity(), "请输入正确手机号", 3);
            mFindAccountEt.requestFocus();
            return;
        }

        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }

        try {
            OkGo.post(UrlTools.getInterfaceUrl(UrlTools.CODE))
                    .tag(this)
                    .params("phoneNumber",URLEncoder.encode(RSAUtil.rsaEncrypt(mFindAccountEt.getText().toString().trim()),"UTF-8"))
                    //.params("phoneNumber", mFindAccountEt.getText().toString().trim())//手机号
                    .params("verifyType", ExtraConfig.TypeCode.CHECK_TYPE_BACK_PW)//验证码类型
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

}

