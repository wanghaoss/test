package com.etuo.kucun.utils;

import android.content.Context;
import android.content.res.Resources;

import com.etuo.kucun.R;

/**
 * ================================================
 *
 * @author :Administrator
 * @version :V 1.0.0
 * @date :2018/7/6
 * @ProjectNameDescribe :ADFilterTool屏蔽广告工具类
 * 修订历史：
 * ================================================
 */
public class AdFilterTool {
    /**
     * @param context
     * @param url
     * @return true 为广告链接，false 为正常连接
     */
    public static boolean hasAd(Context context, String url) {
        Resources res = context.getResources();
        String[] adUrls = res.getStringArray(R.array.adBlockUrl);
        for (String adUrl : adUrls) {
            if (url.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }

}
