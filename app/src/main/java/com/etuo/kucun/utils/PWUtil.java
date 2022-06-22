package com.etuo.kucun.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.etuo.kucun.model.PalletInfoListModel;

/**
 * Created by wzb on 2018/7/2.
 */

public abstract class PWUtil implements  PopupWindow.OnDismissListener {
    protected Activity activity;
    protected LayoutInflater inflater;
    protected View locationView;//popupwindow的归属view
    protected PopupWindow popupWindow;//弹窗对象
    //标记当前窗口是否正在显示，避免连续点击弹窗(尤其是针对不同商品)引起数据紊乱
    protected boolean isShowing = false;

    protected void showPW(FragmentActivity activity, PalletInfoListModel singleData, View locationView, int from_type) {//子类根据需要重载此方法
    }

    protected View createPW(Activity activity, int layoutId) {
        this.activity = activity;
        if (inflater == null) {
            inflater = LayoutInflater.from(activity);
        }
        View rootView = inflater.inflate(layoutId, null);
        popupWindow = new PopupWindow(rootView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,true);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(android.R.style.Widget_Holo_PopupWindow);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOnDismissListener(this);
        return rootView;
    }

    /**
     * 设置页面的透明度
     *
     * @param bgAlpha 1表示背景完全透明
     */
    protected void setBackgroundAlpha(Activity activity, float bgAlpha) {
        if (activity == null)
            return;
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1f) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        isShowing = false;//窗口关闭时恢复标记值
        setBackgroundAlpha(activity, 1f);
    }

    /**
     * 选择切换城市的监听
     * @param homeFragment
     */

}
