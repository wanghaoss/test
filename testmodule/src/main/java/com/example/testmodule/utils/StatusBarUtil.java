package com.example.testmodule.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/6/27.
 */

public class StatusBarUtil {
    /**
     * 设置可指定颜色的状态栏
     *
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity, int color) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            ViewGroup viewGroup = (ViewGroup) window.getDecorView();
            View statusBarView = new View(activity);
            statusBarView.setBackgroundColor(color);
            //获取状态栏高度
            int height = getStatusBarHeight(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            //状态栏占位控件加入到decorView，另外需要通过Theme设置fitsSystemWindows为true才能真正的占位
            viewGroup.addView(statusBarView, lp);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param activity
     * @return
     */
    private static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        Resources res = activity.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
