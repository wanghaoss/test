package com.etuo.kucun.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import com.etuo.kucun.TuoPanConfig;
import com.rscja.deviceapi.RFIDWithUHF;

import java.io.File;


public class DeviceUtil {
	public static String getDeviceId(Context context){
		return getTelephonyManager(context).getDeviceId();
	}


	public static String getAndroidId(Context context){
		return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}
	public static String getMobileModel(){
		return android.os.Build.MODEL;
	}
	public static String getSimSerialNumber(Context context){
		return getTelephonyManager(context).getSimSerialNumber();
	}

	public static String getPhoneSubscriberId(Context context){
		return getTelephonyManager(context).getSubscriberId();
	}

	public static String getPhoneNumber(Context context){
		return getTelephonyManager(context).getLine1Number();
	}

	private static TelephonyManager getTelephonyManager(Context context){
		return (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	}

	private static WifiManager getWifiManager(Context context){
		return (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
	}

	public static void install(Context context, File file){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	public static String getVesionName(Context context){
		PackageInfo info;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			throw new RuntimeException(e);
		}
		LogUtil.e("-----"+info.versionName+"||||||||"+context.getPackageName()+"---------");
		return info.versionName;
	}

	public static int getVersionCode(Context context){
		PackageInfo info;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			throw new RuntimeException(e);
		}
		return info.versionCode;
	}

	public static boolean isHaveFRID(){


		String mobileModel = DeviceUtil.mobileType();

		LogUtil.d("mobileModel : "+ mobileModel );

		if ("HC720".equals(mobileModel)){
			return true;
		}

		try {
			RFIDWithUHF mReader = RFIDWithUHF.getInstance();

			if (mReader != null){
				return true;
			}
		} catch (Exception ex) {
			LogUtil.d("Exception 没有硬件支持 初始化失败: "+ ex.toString() );
			return false;
		}


		return false;
	}


	/**
	 *  ESUR-H600
	 *
	 * @return
	 */
	public static String mobileType(){
		String mobileModel = DeviceUtil.getMobileModel();

		LogUtil.d("mobileModel : "+ mobileModel );
		if ("ESUR-H600".equals(mobileModel)){

			return TuoPanConfig.MODELTYPE_H600;

		} else {
			return TuoPanConfig.MODELTYPE_HC720;
		}


	}
}
