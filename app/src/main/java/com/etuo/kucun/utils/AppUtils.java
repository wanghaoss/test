package com.etuo.kucun.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.etuo.kucun.FrameworkApp;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.ui.activity.LoginActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;


/**
 * 跟App相关的辅助类
 */
public class AppUtils {

    private AppUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    public static boolean isEmulator() {
        return (Build.MODEL.equalsIgnoreCase("sdk"))
                || (Build.MODEL.equalsIgnoreCase("google_sdk"));
    }

    //返回SIM卡提供商的国家代码
    //检测是否为手机
    public static boolean isPhone() {
        android.telephony.TelephonyManager telephony = (android.telephony.TelephonyManager) FrameworkApp.getAppContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony.getPhoneType() == android.telephony.TelephonyManager.PHONE_TYPE_NONE) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获得Imsi
     *
     * @return
     */
    public static String getImsi() {
        String ret = null;
        android.telephony.TelephonyManager tm = null;
        try {
            tm = (android.telephony.TelephonyManager) FrameworkApp.getAppContext().getSystemService(
                    Context.TELEPHONY_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tm != null) {
            ret = tm.getSubscriberId();
        }

        return ret;
    }

    //手机唯一标识码
    public static String getImei() {
        String ret = null;
        android.telephony.TelephonyManager tm = null;
        try {
            tm = (android.telephony.TelephonyManager) FrameworkApp.getAppContext().getSystemService(
                    Context.TELEPHONY_SERVICE);
            if (tm != null) {
                ret = tm.getDeviceId();
            }
            if (!TextUtils.isDigitsOnly(ret)) {
                ret = convertMEIDToIMEI(ret);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    //把meid装换为imei
    private static String convertMEIDToIMEI(String MEID) {
        String ret = null;
        try {
            BigInteger src1 = new BigInteger(hexString2binaryString(MEID), 2);
            StringBuffer sbf = new StringBuffer(src1.toString());

            if (sbf.length() < 15) {
                for (int i = sbf.length(); i < 15; i++) {
                    sbf.append(i + 1);
                }
                ret = sbf.toString();
            }
            if (sbf.length() > 15) {
                ret = sbf.substring(0, 15);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }


    // mac ip 地址
    public static String getMacAddress() {
        String ret = null;
        android.net.wifi.WifiManager wifi = null;
        try {
            wifi = (android.net.wifi.WifiManager) FrameworkApp.getAppContext().getSystemService(
                    Context.WIFI_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (wifi != null) {
            ret = wifi.getConnectionInfo().getMacAddress();
        }
        return ret;
    }

    /**
     * sim 序列号 (ICCID)
     *
     * @param
     * @return
     */
    public static String getSimSerialNumber() {
        String ret = null;
        android.telephony.TelephonyManager tm = null;
        try {
            tm = (android.telephony.TelephonyManager) FrameworkApp.getAppContext().getSystemService(
                    Context.TELEPHONY_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tm != null) {
            ret = tm.getSimSerialNumber();

        }
        return ret;
    }


    /**
     * sim 运营商
     *
     * @param
     * @return
     */
    public static String getSimOperatorName() {
        String ret = null;
        android.telephony.TelephonyManager tm = null;
        try {
            tm = (android.telephony.TelephonyManager) FrameworkApp.getAppContext().getSystemService(
                    Context.TELEPHONY_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tm != null) {
            ret = tm.getSimOperatorName();
        }
        return ret;
    }

    public static String getSimOperator() {
        String ret = null;
        android.telephony.TelephonyManager tm = null;
        try {
            tm = (android.telephony.TelephonyManager) FrameworkApp.getAppContext().getSystemService(
                    Context.TELEPHONY_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tm != null) {
            ret = tm.getSimOperator();
        }
        return ret;
    }

    //获取用户的id
    public static int getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        int apiKey = -1;
        if (context == null || metaKey == null) {
            return -1;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getInt(metaKey);
            }
        } catch (NameNotFoundException e) {

        }
        return apiKey;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取文件的MD5值
     *
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {

        if (!file.isFile()||null==file) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        Log.i("eeeesss", bigInt + "");
        String md5 = getMd5_32(bigInt.toString(16));
        return md5;
    }
    //判断MD5够不够32位

    public static String getMd5_32(String md5) {
        String newMd5 = null;
        if (null != md5 && md5.length() < 32) {
            for (int i = 0; i < 32 - md5.length(); i++) {
                md5 = "0" + md5;
            }
        }
        newMd5 = md5;

        return newMd5;

    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //listAdapter.getQuantity()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高 www.it165.net
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 获取剪贴板最新的内容
     * @param context
     * @return
     */
    public static   String getClipData(Context context){

        String CharSequenceData = "";

        ClipboardManager clipboard = (ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
        // 获取剪贴板的剪贴数据集
        ClipData clipData = clipboard.getPrimaryClip();

        if (clipData != null && clipData.getItemCount() > 0) {
            // 从数据集中获取（粘贴）第一条文本数据
            CharSequence text = clipData.getItemAt(0).getText();
            if (!TextUtils.isEmpty(text)){
                CharSequenceData =  text.toString();
            }

        }

        return CharSequenceData;
    }

    public static  void intentLogin(Context context,String flag_from){

        if (context == null){
            context = FrameworkApp.getAppContext();
        }
        ShowToast.tCustom(context, "用户名密码过期,请重新登录");
        PreferenceCache.putToken("");
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
        LogUtil.d("Main_intent_w : " + flag_from);
        intent.putExtra(ExtraConfig.TypeCode.FROM_INT_LOGIN,flag_from);
        context.startActivity(intent);
    }



    /**
     * 保存到内存卡
     *
     * @param bitName
     * @param mBitmap
     */
    public static void saveBitmapForSdCard(String local_path,Activity context, String bitName, Bitmap mBitmap) {
//        String local_path = context.Parser.getSdCard() + Const.PATH + Const.IMGAGE_PATH;
        File appDir = new File(local_path);
        //判断不存在就创建
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //创建file对象
        File f = new File(local_path +"/"+ bitName + ".png");
        try {
            //创建
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //原封不动的保存在内存卡上
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    f.getAbsolutePath(), bitName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        String path = Environment.getExternalStorageDirectory().getPath();
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        Toast.makeText(context,"保存成功,请到相册查看",Toast.LENGTH_SHORT).show();
    }


    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
