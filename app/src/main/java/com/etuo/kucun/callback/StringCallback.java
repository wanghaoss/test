package com.etuo.kucun.callback;

import android.content.Intent;
import android.util.Log;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.etuo.kucun.FrameworkApp;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.ui.activity.LoginActivity;

import okhttp3.Response;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/9/11
 * 描    述：返回字符串类型的数据
 * 修订历史：
 * ================================================
 */
public abstract class StringCallback extends AbsCallback<String> {

    @Override
    public String convertSuccess(Response response) throws Exception {


        Log.d("Stringcode","  code  " + response.code()+"   ");

        if (response.code() == 888 ){ // token guoqi
            PreferenceCache.clearAllUserPwd();
            Intent intent = new Intent(FrameworkApp.getAppContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//关掉所要到的界面中间的activity
            FrameworkApp.getAppContext().startActivity(intent);

            throw new IllegalStateException("您的登录信息已过期,请重新登录");
        }

        String s = StringConvert.create().convertSuccess(response);
        response.close();
        return s;
    }
}