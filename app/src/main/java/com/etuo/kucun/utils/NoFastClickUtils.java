package com.etuo.kucun.utils;

/**
 * ================================================
 *
 * @author：Vip 版    本：V 1.0.0
 * 创建日期：2019/04/28
 * 描    述：每次点击button的时候，获取当前的时间，然后对比上一次的时间，两者的差值如果小于某个规定的时间，则判断为快速点击。
 * 修订历史：
 * ================================================
 */
public class NoFastClickUtils {
    /**
     * 上次点击的时间
     **/
    private static long lastClickTime = 0;
    /**
     * 时间间隔
     **/
    private static int spaceTime = 400;

    public static boolean isFastClick() {
        //当前系统时间
        long currentTime = System.currentTimeMillis();

        boolean isAllowClick;//是否允许点击

        isAllowClick = currentTime - lastClickTime <= spaceTime;

        lastClickTime = currentTime;

        return isAllowClick;

    }

}
