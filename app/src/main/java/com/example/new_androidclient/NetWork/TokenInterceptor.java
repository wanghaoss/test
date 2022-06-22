package com.example.new_androidclient.NetWork;

import com.example.new_androidclient.Other.App;
import com.example.new_androidclient.Util.DataConverterUtil;
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.login.bean.UserBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;

import static com.example.new_androidclient.Other.SPString.RecordUserName_token;
import static com.example.new_androidclient.Other.SPString.RecordUserPwd_token;
import static com.example.new_androidclient.Other.SPString.Token;

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request new_request = chain.request().newBuilder()
                .header("Authorization", SPUtil.getData(Token, " ").toString())
                .header("Content-Type", "application/json; charset=utf-8")
                .method(request.method(), request.body())
                .build();

        Response proceed = chain.proceed(new_request);
        okhttp3.MediaType mediaType = proceed.body().contentType();

        //如果token过期 再去重新请求token 然后设置token的请求头 重新发起请求 用户无感
        String content = proceed.body().string();
        if (isTokenExpired(content)) {
            String newToken = getNewToken();
            SPUtil.putData(Token, newToken);
            //使用新的Token，创建新的请求
            Request newRequest = chain.request().newBuilder()
                    .header("Authorization", newToken)
                    .header("Content-Type", "application/json; charset=utf-8")
                    .method(request.method(), request.body())
                    .build();
            return chain.proceed(newRequest);
        }
        return proceed.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();

    }

    private boolean isTokenExpired(String resultStr) {
        BaseResponse requestCode = new Gson().fromJson(resultStr, BaseResponse.class);
        //err==3  token过期
        if (requestCode.getCode() == 401) {
            LogUtil.i("----------------------------Token登录过期了");
            return true;
        }

        return false;
    }

    private String getNewToken() {
        Map<String, String> map = new HashMap<>();
        map.put("username", (String) SPUtil.getData(RecordUserPwd_token, ""));
        map.put("password", (String) SPUtil.getData(RecordUserName_token, ""));
        map.put("phoneType", "1");
        String RegId = JPushInterface.getRegistrationID(App.getInstance());
        if (RegId == null) {
            RegId = "";
        }
        map.put("phoneKey", RegId);

        Api service = RetrofitUtil.getApi();
        Call<BaseResponse<UserBean>> call = service.Login_token(DataConverterUtil.map_to_body(map));

        //要用retrofit的同步方式
        String newToken = null;
        try {
            newToken = call.execute().body().getData().getToken();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newToken;
    }


}
