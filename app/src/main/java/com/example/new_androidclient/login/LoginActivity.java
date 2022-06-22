package com.example.new_androidclient.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.App;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.DataConverterUtil;
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityMainBinding;
import com.example.new_androidclient.login.bean.User;
import com.example.new_androidclient.login.bean.UserBean;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

import static com.example.new_androidclient.Other.SPString.RecordUserName;
import static com.example.new_androidclient.Other.SPString.RecordUserName_token;
import static com.example.new_androidclient.Other.SPString.RecordUserPwd_token;
import static com.example.new_androidclient.Other.SPString.Token;
import static com.example.new_androidclient.Other.SPString.UserId;
import static com.example.new_androidclient.Other.SPString.UserName;

@Route(path = RouteString.LoginActivity)
public class LoginActivity extends BaseActivity {
    private final String USERNAME = "username";
    private final String PWD = "password";
    private ActivityMainBinding activityMainBinding;
    private User user = new User();
    private String RegId;

    private final int nfc = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user.setName(SPUtil.getData(RecordUserName, "").toString());
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setUser(user);
        activityMainBinding.setLoginActivity(this);
        activityMainBinding.setListener(new Listener());
        if (TextUtils.isEmpty(user.getName())) {
            activityMainBinding.checkboxRecordName.setChecked(false);
        }
        initJPush();
        activityMainBinding.rfidLinear.setOnClickListener(v -> {
            ARouter.getInstance().build(RouteString.NFCActivity)
                    .withInt("code", nfc)
                    .withInt("module", 1)
                    .navigation(this, nfc);
        });
    }

    private void request(Map<String, String> map) {
        RetrofitUtil.getApi().Login(DataConverterUtil.map_to_body(map))
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<UserBean>(mContext, true, "正在登录") {
                    @Override
                    public void onSuccess(UserBean bean) {
                        if (bean != null) {
                            SPUtil.putData(Token, bean.getToken());
                            SPUtil.putData(UserId, bean.getUserId());
                            SPUtil.putData(UserName, bean.getLastName() + bean.getFirstName());

                            SPUtil.putData(RecordUserPwd_token, map.get(USERNAME));
                            SPUtil.putData(RecordUserName_token, map.get(PWD));

                            ARouter.getInstance().build(RouteString.MainActivity).navigation(mContext, new NavCallback() {
                                @Override
                                public void onArrival(Postcard postcard) {
                                    finish();
                                }
                            });
                        } else {
                            ToastUtil.show(mContext, "UserBean is null");
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                        recordUserName("");
                        LogUtil.i("onFailure:  " + err);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == nfc) {
            String uid = data.getStringExtra("uid");
            login(false, uid);
        }
    }

    private void recordUserName(String username) {
        SPUtil.putData(RecordUserName, username);
        SPUtil.putData(RecordUserName_token, username);
        SPUtil.putData(RecordUserPwd_token, username);
    }

    private void initJPush() {
        JPushInterface.init(getApplicationContext());
        RegId = JPushInterface.getRegistrationID(App.getInstance());
    }

    //刷卡密码写死 SK
    private void login(Boolean isPwdLogin, String uid) {
        Map<String, String> map = new HashMap<>();
        if (isPwdLogin) {
            if (!TextUtils.isEmpty(user.getName()) && !TextUtils.isEmpty(user.getPwd())) {
                map.put(USERNAME, user.getName());
                map.put(PWD, user.getPwd());
            } else {
                ToastUtil.show(mContext, "请输入账号或密码");
            }
        } else {
            map.put(USERNAME, uid);
            map.put(PWD, "SK");
        }

        RegId = JPushInterface.getRegistrationID(App.getInstance());
        if (RegId == null) {
            RegId = "";
        }
        map.put("phoneKey", RegId);
        map.put("phoneType", "1");

        request(map);

        if (activityMainBinding.checkboxRecordName.isChecked()) {
            recordUserName(map.get(USERNAME));
        } else {
            recordUserName("");
        }
    }

    public class Listener {
        public void pwdLogin() {
            login(true, "");
        }
    }
}


