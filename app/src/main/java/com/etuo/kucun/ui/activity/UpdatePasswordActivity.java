package com.etuo.kucun.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseHeaderBarActivity;
import com.etuo.kucun.callback.DialogCallback;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.utils.NetUtil;
import com.etuo.kucun.utils.RSAUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.StringUtil;
import com.etuo.kucun.utils.UrlTools;
import com.lzy.okgo.OkGo;

import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Response;

import static com.etuo.kucun.utils.UrlTools.SETTINGPASSWORD;


/**
 * 修改密码页面
 *
 * @author Liujinxin
 * @version 1.0.0
 * @since 2017/09/06
 */

public class UpdatePasswordActivity extends BaseHeaderBarActivity {

    //原密码
    private EditText update_password_oldET;

    private CheckBox update_password_hide_oldCB;
    //新密码
    private EditText update_password_newET;

    private CheckBox update_password_hide_newCB;


    private Button update_password_button;

    private LinearLayout new_password_rightLL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        setHeaderTitle("修改密码");
        initView();
    }

    public void initView() {
        update_password_button = (Button) findViewById(R.id.update_password_button);
        update_password_oldET = (EditText) findViewById(R.id.update_password_oldET);
        update_password_newET = (EditText) findViewById(R.id.update_password_newET);
        new_password_rightLL = (LinearLayout) findViewById(R.id.new_password_rightLL);
        update_password_hide_oldCB = (CheckBox) findViewById(R.id.update_password_hide_oldCB);
        update_password_hide_newCB = (CheckBox) findViewById(R.id.update_password_hide_newCB);
        update_password_button.setOnClickListener(this);
        update_password_hide_oldCB.setOnCheckedChangeListener(checkBox_old);
        update_password_hide_newCB.setOnCheckedChangeListener(checkBox_new);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.update_password_button:
                if (checkPassword()) {
                    UpdatePassword();
                }
                break;


        }
    }

    private void UpdatePassword() {

        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }
        try {
            OkGo.post(UrlTools.getInterfaceUrl(SETTINGPASSWORD))
                    .tag(this)
                    .params("token", PreferenceCache.getToken())
                    .params("oldPassword", URLEncoder.encode(RSAUtil.rsaEncrypt(update_password_oldET.getText().toString().trim()),"UTF-8"))
                    .params("newPassword",URLEncoder.encode(RSAUtil.rsaEncrypt(update_password_newET.getText().toString().trim()),"UTF-8"))
//                    .params("oldPassword", update_password_oldET.getText().toString().trim())//旧密码
//                    .params("newPassword", update_password_newET.getText().toString().trim())//新密码

                    .execute(new DialogCallback<LzyResponse<String>>(getActivity(), "加载中...") {
                        @Override
                        public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {
                            ShowToast.toastTime(getActivity(), responseData.message, 3);

//                            ShowToast.tCustom(getActivity(), "用户名密码已修改,请重新登录");
//                            PreferenceCache.putToken("");
//                            Intent intent = new Intent(getActivity(), LoginActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
//
//                            startActivity(intent);


                            finish();
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

    private boolean checkPassword() {
        if (StringUtil.isEmpty(update_password_oldET.getText().toString().trim())) {
            ShowToast.toastTime(getActivity(), "请输入原密码", 3);
            update_password_oldET.requestFocus();
            return false;
        }

        if (StringUtil.isEmpty(update_password_newET.getText().toString().trim())) {
            ShowToast.toastTime(getActivity(), "请输入新密码", 3);
            update_password_newET.requestFocus();
            return false;
        }

        return true;
    }

    private Activity getActivity() {
        return this;
    }

    private CheckBox.OnCheckedChangeListener checkBox_old = new CheckBox.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {

            if (update_password_hide_oldCB.isChecked()) {

                // 文本正常显示

                update_password_oldET
                        .setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                Editable etable = update_password_oldET.getText();

                Selection.setSelection(etable, etable.length());
            } else {

                // 文本以密码形式显示

                update_password_oldET.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                // 下面两行代码实现: 输入框光标一直在输入文本后面

                Editable etable = update_password_oldET.getText();

                Selection.setSelection(etable, etable.length());

            }

        }

    };

    private CheckBox.OnCheckedChangeListener checkBox_new = new CheckBox.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {

            if (update_password_hide_newCB.isChecked()) {

                // 文本正常显示

                update_password_newET
                        .setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                Editable etable = update_password_newET.getText();

                Selection.setSelection(etable, etable.length());
            } else {

                // 文本以密码形式显示

                update_password_newET.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                // 下面两行代码实现: 输入框光标一直在输入文本后面

                Editable etable = update_password_newET.getText();

                Selection.setSelection(etable, etable.length());

            }

        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

}
