package com.etuo.kucun;

import android.os.Environment;

public class TuoPanConfig {

    /**
     * 本地存储的根路径
     */
    public static final String EXT_STORAGE_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * 本地存储根目录名
     */
    public static final String CACHE_ROOT_NAME = "ETuo_kc_C";

    /**
     * 本地存储缓存根目录名
     */
    public static final String CACHE_ROOT_CACHE_NAME = "cache";

    /**
     * apk安装包名称
     */
    public static final String APK_NAME = "ETuo_kc.apk";

    /**
     * 本地存储图片根目录名
     */
    public static final String CACHE_PIC_ROOT_NAME = "ETuoKCPicture";

    public static final String ACTION_BASE_PREFIX = "ETuo_Kc_C.action.";
    //安装的 file 名
    public static final  String fileproviderName = "com.etuo.kucun.fileprovider";

    /**
     * 原设备scan快捷键
     */
    public static final int KEY_SCAN_OLD = 139;

    /**
     * 原设备扳机键
     */
    public static final int KEY_SEND_OLD = 280;

    /**
     * H600设备scan快捷键 左侧
     * //131 实体扳机键  135 左侧scan  134 右侧scan
     */
    public static final int KEY_SCAN_H600_LEFT = 135;

    /**
     * H600设备scan快捷键 右侧
     */
    public static final int KEY_SCAN_H600_RIGHT = 134;

    /**
     * H600设备扳机键
     */
    public static final int KEY_SEND_H600 = 131;

    public static final String MODELTYPE_H600 = "H600";

    public static final String MODELTYPE_HC720 = "HC720";

}
