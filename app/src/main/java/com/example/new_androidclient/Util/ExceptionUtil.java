package com.example.new_androidclient.Util;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

public class ExceptionUtil {
    public static String exceptionHandler(Throwable e){
        String msg = "未知错误或数据解析错误";
        if(e instanceof SocketTimeoutException){
            msg = "网络请求超时";
        }else if(e instanceof ConnectException){
            msg = "java.net.ConnectException: Failed to connect";
        }else if(e instanceof HttpException){
            HttpException httpException = (HttpException) e;
            msg = convertStatusCode(httpException);
        }
        return msg;
    }

    private static String convertStatusCode(HttpException httpException){
        String msg;
        if (httpException.code() >= 500 && httpException.code() < 600) {
            msg = "服务器处理请求出错";
        } else if (httpException.code() >= 400 && httpException.code() < 500) {
            msg = "服务器无法处理请求";
        } else if (httpException.code() >= 300 && httpException.code() < 400) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
