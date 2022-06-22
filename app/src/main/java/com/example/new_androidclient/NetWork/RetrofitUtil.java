package com.example.new_androidclient.NetWork;

import java.util.logging.Level;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private String BASE_URL = Api.gao;
    private static Api apiInterface;

    public static Api getApi() {
        if (apiInterface == null) {
            synchronized (RetrofitUtil.class) {
                apiInterface = new RetrofitUtil().getRetrofit();
            }
            return apiInterface;
        }
        return apiInterface;
    }

    public Api getRetrofit() {
        Api userInterface = initRetrofit(initClient()).create(Api.class);
        return userInterface;
    }

    private Retrofit initRetrofit(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    private OkHttpClient initClient(){
        LogInterceptor logInterceptor = new LogInterceptor("RetrofitUtil");
        logInterceptor.setPrintLevel(LogInterceptor.Level.BODY);
        logInterceptor.setColorLevel(Level.WARNING);

        return new OkHttpClient().newBuilder()
                .addInterceptor(new TokenInterceptor())
                .addInterceptor(logInterceptor)
                .build();
    }
}


