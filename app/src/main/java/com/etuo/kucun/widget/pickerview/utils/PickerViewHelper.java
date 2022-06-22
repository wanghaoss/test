package com.etuo.kucun.widget.pickerview.utils;

import android.content.Context;
import android.view.KeyEvent;


import com.etuo.kucun.widget.pickerview.DoubleDateView;
import com.etuo.kucun.widget.pickerview.OptionsPickerView;
import com.etuo.kucun.widget.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Date;

public class PickerViewHelper {


    public static TimePickerView pvTime;

    public static OptionsPickerView pvOptions;

    public static DoubleDateView doubleDateView;

    /**
     * 日期选择
     *
     * @param context              上下文
     * @param type                 日期展示类型
     * @param date                 时间
     * @param onTimeSelectListener 回调
     */
    public static void showDate(Context context, TimePickerView.Type type, Date date, final OnTimeSelectListener onTimeSelectListener) {
        pvTime = new TimePickerView(context, type);
        pvTime.setTime(date);
        pvTime.setCyclic(true);
        pvTime.setCancelable(true);
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                onTimeSelectListener.onTimeSelect(date);
            }
        });

//        pvTime.dismiss();

//        pvTime.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK
//                        && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    pvTime.dismiss();
//                }
//                return false;
//            }
//        });
        pvTime.show();
    }

    /**
     * 日期选择
     *
     * @param context              上下文
     * @param type                 日期展示类型
     * @param date                 时间
     * @param onTimeSelectListener 回调
     */
    public static void showTwoDate(Context context, TimePickerView.Type type, Date date, final DoubleDateView.OnTimeSelectListener onTimeSelectListener
    ) {
        doubleDateView = new DoubleDateView(context,date);
        doubleDateView.setTime(date);
        doubleDateView.setCyclic(true);
        doubleDateView.setCancelable(true);
        doubleDateView.setOnTimeSelectListener(new DoubleDateView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date1, Date date2) {
                onTimeSelectListener.onTimeSelect(date1, date2);
            }

        });
        doubleDateView.show();
    }

    /**
     * 条件选择
     *
     * @param context                 上下文
     * @param option                  默认显示的内容
     * @param optionsItems            数据集合
     * @param onOptionsSelectListener 回调
     */
    public static void showOptions(Context context, ArrayList<String> optionsItems, String option, final OnOptionsSelectListener onOptionsSelectListener) {
        pvOptions = new OptionsPickerView(context);
        pvOptions.setPicker(optionsItems);
        pvOptions.setTitle("");
        pvOptions.setCyclic(false);
        pvOptions.setCancelable(true);
        pvOptions.setSelectOptions(optionsItems.indexOf(option));

        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                onOptionsSelectListener.onOptionsSelect(options1);
            }
        });
        pvOptions.show();
    }

    /**
     * 两级联动条件选择
     *
     * @param context                 上下文
     * @param option                  默认显示的内容
     * @param optionsItems1           数据集合
     * @param optionsItems2           数据集合
     * @param onOptionsTwoLevelSelectListener 回调
     */
    public static void showOptionsTwoLevel(Context context, ArrayList<String> optionsItems1, ArrayList<ArrayList<String>> optionsItems2, String option, final OnOptionsTwoLevelSelectListener onOptionsTwoLevelSelectListener) {
        pvOptions = new OptionsPickerView(context);
        pvOptions.setPicker(optionsItems1, optionsItems2, true);
        pvOptions.setTitle("");
        pvOptions.setCyclic(false);
        pvOptions.setCancelable(true);
        pvOptions.setSelectOptions(optionsItems1.indexOf(option));

        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                onOptionsTwoLevelSelectListener.onOptionsSelect(options1,option2);
            }
        });
        pvOptions.show();
    }

    public interface OnOptionsSelectListener {
        void onOptionsSelect(int options);
    }
    public interface OnOptionsTwoLevelSelectListener {
        void onOptionsSelect(int options1, int options2);
    }
    public interface OnTimeSelectListener {
        void onTimeSelect(Date date);
    }

    /**
     * 在activity的onkeydown添加view的关闭方法
     * if (PickerViewHelper.onKeyDown(keyCode, event)) return true;
     *
     * @param keyCode
     * @param event
     * @return
     */
    public static boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (pvTime != null && pvTime.isShowing()) {
                pvTime.dismiss();
                pvTime = null;
                return true;
            }

            if (pvOptions != null && pvOptions.isShowing()) {
                pvOptions.dismiss();
                pvOptions = null;
                return true;
            }
        }
        return false;
    }
}
