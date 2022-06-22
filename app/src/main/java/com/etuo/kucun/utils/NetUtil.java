package com.etuo.kucun.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.etuo.kucun.FrameworkApp;
import com.etuo.kucun.R;

/**
 * 网络状态工具类
 */
public class NetUtil {
    /**
     * 没有连接网络
     */
    private static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    private static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    private static final int NETWORK_WIFI = 1;

    /**
     * 获取网络状态
     * 返回-1为不可用状态 其他为可用
     */
    public static int getNetWorkState(Context context) {

        if (context == null) {
            context = FrameworkApp.getAppContext();
        }
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;
            }
        } else {
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }

    public static void checkNetWorkState(Context context) {
        if (NetUtil.getNetWorkState(context) == -1) {
            ShowToast.toastTime(context, context.getResources().getString(R.string.net_error), 3);
            return;
        }
    }
}
