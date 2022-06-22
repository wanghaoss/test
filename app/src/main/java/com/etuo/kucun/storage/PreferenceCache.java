package com.etuo.kucun.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.etuo.kucun.FrameworkApp;

import cn.jpush.android.api.JPushInterface;


/**
 * 缓存应用程序的配置信息和必要的业务数据
 *
 * @author lenovo
 */
public class PreferenceCache {
    public static final String PF_AUTO_LOGIN = "auton_login"; // 自动登录
    public static final String PF_PHONE_NUM = "phone_number"; // 自动登录
    public static final String PF_TOKEN = "token"; // token
    public static final String PF_COMPANYID = "companyId"; // companyId
    public static final String PF_ISFirstLOGIN = "is_firstLogin";//是否第一次登录
    public static final String PF_USERNAME = "username";// 保存登录的用户名
    public static final String PF_LOGINDATE = "login_date"; // 登录日期

    public static final String PF_SAVE_STATUS = "save_loginStatus";//记住用户是否是选中状态

    public static final String PF_GESTURE = "gesture_status";//手势是否开启状态
    public static final String GESTURE_TIME = "gesture_time"; // 手势密码输入错误超过5次时间
    public static final String GESTURE_FLG = "gesture_flg"; // 判断是否设置有手势密码
    public static final String PF_PHONE_PASS = "phone_password"; // 自动登录密码
    public static final String PF_ISFirstOPEN = "is_firstOpen";//是否第一次打开


    public static final String LONGITUDE = "longitude";// 经度

    public static final String LATITUDE = "latitude";// 维度

    public static final String ADDRESS_MODEL = "address_model";// 存详细地址 object 用的时候时候需要转化

    public static final String STATE_DATA = "STATE_DATA";// 手势密码的状态值
    public static final String FINGEER_FLG = "finger_flg";//存储指纹flg

    public static final String FAIL_PWD_NUM = "fail_pwd_num";// 密码输错的次数

    public static final String Fail_current_time = "Fail_current_time";// 失败五次的时候,记录时间

    public static final String USER_ID = "userId";

    public static final String MASSAGE_URL = "massageUrl";//消息列表url
    private static SharedPreferences getSharedPreferences() {
        FrameworkApp app = (FrameworkApp) FrameworkApp.getAppContext();
        return app.getSharedPreferences("TUOPAN", Context.MODE_PRIVATE);
    }

    /**
     * 记录输错时的时间
     *
     * @param time
     */
    public static void putFailTime(long time) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putLong(Fail_current_time, time);
        editor.commit();
    }


    public static long getFailTime() {
        //
        return getSharedPreferences().getLong(Fail_current_time, 0);
    }

    /**
     * 记录输错的次数
     *
     * @param num
     */
    public static void putFailPwdNum(int num) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putInt(FAIL_PWD_NUM, num);
        editor.commit();
    }


    public static int getFailPwdNum() {
        //
        return getSharedPreferences().getInt(FAIL_PWD_NUM, 0);
    }

    public static void clearFailPwdInfo() {

        putFailPwdNum(0);
        putFailTime(0);
    }

    /**
     * 消息列表的url
     * @param massageUrl
     */
    public static void putMassageUrl(String massageUrl){
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putString(MASSAGE_URL, massageUrl);
        editor.commit();
    }

    public static String getMassageUrl() {
        //
        return getSharedPreferences().getString(MASSAGE_URL, "");
    }
    /**
     * 记录极光推送用的userId
     * @param userId
     */
    public static void putUserId(String userId){
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    public static String getUserId() {
        //
        return getSharedPreferences().getString(USER_ID, "");
    }
    
    /**
     * 记录Token
     *
     * @param token
     */

    public static void putToken(String token) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putString(PF_TOKEN, token);
        editor.commit();
    }

    public static String getToken() {
        //
        return getSharedPreferences().getString(PF_TOKEN, "");
    }

    /**
     * 保存公司ID
     * @param companyId
     */

    public static void putCompanyId(String companyId ){

        SharedPreferences pref = getSharedPreferences();
        Editor editor = pref.edit();
        editor.putString(PF_COMPANYID, companyId);
        editor.commit();

    }

    /**
     * 获取公司ID
     * @return
     */
    public static String getCompanyId() {
        //
        return getSharedPreferences().getString(PF_COMPANYID, "");
    }

    public static void putUsername(String username) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putString(PF_USERNAME, username);

        editor.commit();
    }

    public static String getUsername() {
        return getSharedPreferences().getString(PF_USERNAME, "");
    }

    public static void putLoginDate(String loginDate) {
        SharedPreferences pref = getSharedPreferences();
        Editor editor = pref.edit();
        editor.putString(PF_LOGINDATE, loginDate);
        editor.commit();
    }

    public static String getLoginDate() {
        return getSharedPreferences().getString(PF_LOGINDATE, "");
    }

    public static boolean isFirstLogin() {
        return getSharedPreferences().getBoolean(PF_ISFirstLOGIN, false);
    }

    public static void putIsFirstLogin(boolean isFirstLogin) {
        SharedPreferences pref = getSharedPreferences();
        Editor editor = pref.edit();
        editor.putBoolean(PF_ISFirstLOGIN, isFirstLogin);
        editor.commit();
    }

    public static boolean isFirstOpen() {
        return getSharedPreferences().getBoolean(PF_ISFirstOPEN, false);
    }

    public static void putIsFirstOpen(boolean isFirstOpen) {
        SharedPreferences pref = getSharedPreferences();
        Editor editor = pref.edit();
        editor.putBoolean(PF_ISFirstOPEN, isFirstOpen);
        editor.commit();
    }

    public static boolean isGesture() {
        return getSharedPreferences().getBoolean(PF_GESTURE, false);
    }

    public static void putIsGesture(boolean isFirstLogin) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putBoolean(PF_GESTURE, isFirstLogin);
        editor.commit();
    }

    public static void putGestureTime(long time) {
        SharedPreferences pref = getSharedPreferences();
        Editor editor = pref.edit();
        editor.putLong(GESTURE_TIME, time);
        editor.commit();
    }

    public static long getGestureTime() {
        return getSharedPreferences()
                .getLong(GESTURE_TIME, 0);
    }

    public static void putGestureFlag(boolean flg) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putBoolean(GESTURE_FLG, flg);
        editor.commit();
    }

    public static boolean getGestureFlag() {
        return getSharedPreferences()
                .getBoolean(GESTURE_FLG, false);
    }

    public static void putPhoneNum(String phoneNum) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putString(PF_PHONE_NUM, phoneNum);
        editor.commit();
    }

    public static String getPhoneNum() {
        return getSharedPreferences().getString(PF_PHONE_NUM, "");
    }

    public static String getPhonePass() {
        return getSharedPreferences().getString(PF_PHONE_PASS, "");
    }

    public static void putPhonePass(String phonePas) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putString(PF_PHONE_PASS, phonePas);
        editor.commit();
    }


    public static void putAutoLogin(boolean isAutonLogin) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putBoolean(PF_AUTO_LOGIN, isAutonLogin);
        editor.commit();
    }

    public static boolean isAutoLogin() {
        return getSharedPreferences().getBoolean(PF_AUTO_LOGIN, true);
    }

    public static boolean isSaveStatus() {
        return getSharedPreferences().getBoolean(PF_SAVE_STATUS, false);
    }

    public static void putSaveStatus(boolean isSaveStatus) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putBoolean(PF_SAVE_STATUS, isSaveStatus);
        editor.commit();
    }

    /**
     * 获取经度
     *
     * @param longitude
     */
    public static void putLongitude(String longitude) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putString(LONGITUDE, longitude);
        editor.commit();
    }

    public static String getLongitude() {
        return getSharedPreferences().getString(LONGITUDE, "");
    }


    /**
     * 获取维度
     *
     * @param latitude
     */
    public static void putLatitude(String latitude) {
        SharedPreferences pref = getSharedPreferences();
        Editor editor = pref.edit();
        editor.putString(LATITUDE, latitude);
        editor.commit();
    }

    public static String getLatitude() {
        return getSharedPreferences().getString(LATITUDE, "");
    }


    /**
     * 获取 详细的地址
     *
     * @param address
     */
    public static void putAddress(String address) {
        SharedPreferences pref = getSharedPreferences();
        Editor editor = pref.edit();
        editor.putString(ADDRESS_MODEL, address);
        editor.commit();
    }

    public static String getAddress() {
        return getSharedPreferences().getString(ADDRESS_MODEL, "");
    }



    /**
     * 设置指纹密码
     *
     * @param flg
     */
    public static void putFingerFlg(boolean flg) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putBoolean(FINGEER_FLG, flg);
        editor.commit();
    }

    public static boolean getFingerFlg() {
        return getSharedPreferences()
                .getBoolean(FINGEER_FLG, false);
    }

    /**
     * 清除用户密码,指纹密码,token,手势密码等
     * 2019.2.17删除alias
     */

    public static void clearAllUserPwd() {
        PreferenceCache.putIsFirstLogin(false);
        PreferenceCache.putToken("");
        PreferenceCache.putUserId("");
        JPushInterface.deleteAlias(FrameworkApp.getAppContext(),100);
        PreferenceCache.putGestureFlag(false);
        PreferenceCache.putIsGesture(false);
        PreferenceCache.putFingerFlg(false);
    }
}