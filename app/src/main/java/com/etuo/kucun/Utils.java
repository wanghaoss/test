package com.etuo.kucun;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utils {
    public static final String TAG = "PushDemoActivity";
    public static String logStringCache = "";

    // 获取ApiKey
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "error " + e.getMessage());
        }
        return apiKey;
    }

    public static List<String> getTagsList(String originalText) {
        if (TextUtils.isEmpty(originalText)) {
            return null;
        }
        List<String> tags = new ArrayList<String>();
        int indexOfComma = originalText.indexOf(',');
        String tag;
        while (indexOfComma != -1) {
            tag = originalText.substring(0, indexOfComma);
            tags.add(tag);

            originalText = originalText.substring(indexOfComma + 1);
            indexOfComma = originalText.indexOf(',');
        }

        tags.add(originalText);
        return tags;
    }

    public static String getLogText(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getString("log_text", "");
    }

    public static void setLogText(Context context, String text) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString("log_text", text);
        editor.commit();
    }

    /**
     *  开始定位
     */
    public final static int MSG_LOCATION_START = 0;
    /**
     * 定位完成
     */
    public final static int MSG_LOCATION_FINISH = 1;
    /**
     * 停止定位
     */
    public final static int MSG_LOCATION_STOP= 2;

    public final static String KEY_URL = "URL";
    public final static String URL_H5LOCATION = "file:///android_asset/sdkLoc.html";
//    /**
//     * 根据定位结果返回定位信息的字符串
//     * @param location
//     * @return
//     */
//    public synchronized static String getLocationStr(AMapLocation location){
//        if(null == location){
//            return null;
//        }
//        StringBuffer sb = new StringBuffer();
//        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
//        if(location.getErrorCode() == 0){
//            sb.append("定位成功" + "\n");
//            sb.append("定位类型: " + location.getLocationType() + "\n");
//            sb.append("经    度    : " + location.getLongitude() + "\n");
//            sb.append("纬    度    : " + location.getLatitude() + "\n");
//            sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
//            sb.append("提供者    : " + location.getProvider() + "\n");
//
//            sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
//            sb.append("角    度    : " + location.getBearing() + "\n");
//            // 获取当前提供定位服务的卫星个数
//            sb.append("星    数    : " + location.getSatellites() + "\n");
//            sb.append("国    家    : " + location.getCountry() + "\n");
//            sb.append("省            : " + location.getProvince() + "\n");
//            sb.append("市            : " + location.getCity() + "\n");
//            sb.append("城市编码 : " + location.getCityCode() + "\n");
//            sb.append("区            : " + location.getDistrict() + "\n");
//            sb.append("区域 码   : " + location.getAdCode() + "\n");
//            sb.append("地    址    : " + location.getAddress() + "\n");
//            sb.append("兴趣点    : " + location.getPoiName() + "\n");
//            //定位完成的时间
//            sb.append("定位时间: " + formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
//        } else {
//            //定位失败
//            sb.append("定位失败" + "\n");
//            sb.append("错误码:" + location.getErrorCode() + "\n");
//            sb.append("错误信息:" + location.getErrorInfo() + "\n");
//            sb.append("错误描述:" + location.getLocationDetail() + "\n");
//        }
//        //定位之后的回调时间
//        sb.append("回调时间: " + formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");
//        return sb.toString();
//    }

    private static SimpleDateFormat sdf = null;
    public  static String formatUTC(long l, String strPattern) {
        if (TextUtils.isEmpty(strPattern)) {
            strPattern = "yyyy-MM-dd HH:mm:ss";
        }
        if (sdf == null) {
            try {
                sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
            } catch (Throwable e) {
            }
        } else {
            sdf.applyPattern(strPattern);
        }
        return sdf == null ? "NULL" : sdf.format(l);
    }

    /**
     * 获取app的名称
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        String appName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            appName =  context.getResources().getString(labelRes);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return appName;
    }
    /**
     * 获取控件的高度或者宽度  isHeight=true则为测量该控件的高度，isHeight=false则为测量该控件的宽度
     * @param view
     * @param isHeight
     * @return
     */
    public static int getViewHeight(View view, boolean isHeight){
        int result;
        if(view==null)return 0;
        if(isHeight){
            int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            view.measure(h,0);
            result =view.getMeasuredHeight();
        }else{
            int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            view.measure(0,w);
            result =view.getMeasuredWidth();
        }
        return result;
    }
    public static  double getStatusBarHeight(Context context){
        double statusBarHeight = Math.ceil(25 * context.getResources().getDisplayMetrics().density);
        return statusBarHeight;
    }

}
