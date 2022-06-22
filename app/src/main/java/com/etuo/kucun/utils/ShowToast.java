package com.etuo.kucun.utils;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.etuo.kucun.R;

import java.util.Timer;
import java.util.TimerTask;

public class ShowToast {


	/**
	 * 2019/2/17仿ios,toast居中带背景(短)
	 **/
	public static void showShortToast(Context context, String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		View view = toast.getView();
		view.setBackgroundResource(R.drawable.dialog_bg);
		view.setPadding(50, 40, 50, 40);
		toast.setView(view);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 2019/2/17仿ios,toast居中带背景(短)
	 **/
	public static void showShortToast(Context context, int msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		View view = toast.getView();
		view.setBackgroundResource(R.drawable.dialog_bg);
		view.setPadding(50, 40, 50, 40);
		toast.setView(view);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 2019/2/17仿ios,toast居中带背景(长)
	 **/
	public static void showLongToast(Context context, String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		View view = toast.getView();
		view.setBackgroundResource(R.drawable.dialog_bg);
		view.setPadding(50, 40, 50, 40);
		toast.setView(view);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	public static void showLongToast(Context context, int resId) {
		Toast toast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
		View view = toast.getView();
		view.setBackgroundResource(R.drawable.dialog_bg);
		view.setPadding(50, 40, 50, 40);
		toast.setView(view);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 2019/2/17仿ios,toast居中带背景(自定义)
	 **/
	public static void showDurationToast(Context context, String msg, int duration) {
		Toast toast = Toast.makeText(context, msg, duration);
		View view = toast.getView();
		view.setBackgroundResource(R.drawable.dialog_bg);
		view.setPadding(50, 40, 50, 40);
		toast.setView(view);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 吐司，短时间
	 * 
	 * @param context
	 * @param resId
	 */
	public static void toastShortTime(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}

	public static void toastShortTime(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 吐司 ，长时间
	 * 
	 * @param context
	 * @param resId
	 */
	public static void toastLongTime(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
	}

	public static void toastLongTime(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	/**
	 * 自定义吐司时间
	 * 
	 * @param context
	 * @param resId
	 * @param duration
	 */
	public static void toastTime(Context context, int resId, int duration) {
		Toast.makeText(context, resId, duration).show();
	}

	public static void toastTime(Context context, String text, int duration) {
		Toast.makeText(context, text, duration).show();
	}

	/**
	 * 解决UI线程冲突
	 * 
	 * @param resId
	 * @param context
	 */
	public static void showToast(final int resId, final Context context) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}).start();
	}

	public static void showToast(final String text, final Context context) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}).start();
	}

	private static Toast mToast;
	public  static void  tCustom(Context context, String msg){

		if (StringUtil.isEmpty(msg)){
			return;
		}
		if (mToast == null) {
			mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT );
		} else {
			mToast.setText(msg);
		}
		mToast.show();
	}
	public static void showMyToast(final Toast toast, final int cnt) {
		final Timer timer =new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				toast.show();
			}
		},0,3000);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				toast.cancel();
				timer.cancel();
			}
		}, cnt );
	}




}
