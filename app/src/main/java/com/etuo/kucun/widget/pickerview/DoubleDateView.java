package com.etuo.kucun.widget.pickerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.etuo.kucun.R;
import com.etuo.kucun.widget.pickerview.listener.OnScollListener;
import com.etuo.kucun.widget.pickerview.utils.TimeHelper;
import com.etuo.kucun.widget.pickerview.utils.ToolsHelper;
import com.etuo.kucun.widget.pickerview.view.BasePickerView;
import com.etuo.kucun.widget.pickerview.view.WheelTime;

import java.util.Calendar;
import java.util.Date;


/**
 * 时间选择器
 * Created by Sai on 15/11/22.
 */
public class DoubleDateView extends BasePickerView implements View.OnClickListener, OnScollListener {


    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN, YEAR_MONTH, MONTH_DAY_HOUR_30MIN
    }// 四种选择模式，年月日时分，年月日，时分，月日时分,30分钟

    WheelTime wheelTime;
    private View btnSubmit, btnCancel, startdate_llty, enddate_llyt;
    private TextView enddate_hint, startdate_hint, startdate_tv, enddate_tv;
    private TextView tvTitle;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private static final String TAG_STARTDATE = "start";
    private static final String TAG_ENDDATE = "end";
    private OnTimeSelectListener timeSelectListener;
    private OnSingleTimeSelectListener singleTimeSelectListener;
    private int swichSelcted = 1;//  开始时间：1  ；截止时间 ：2；
    private Context mContext;


    public DoubleDateView(Context context, Date date) {
        super(context);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.pickerview_time_twodate, contentContainer);
        // -----确定和取消按钮
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        startdate_llty = findViewById(R.id.startdate_llyt);
        startdate_llty.setTag(TAG_STARTDATE);
        startdate_llty.setOnClickListener(this);
        enddate_llyt = findViewById(R.id.enddate_llyt);
        enddate_llyt.setTag(TAG_ENDDATE);
        enddate_llyt.setOnClickListener(this);

        enddate_tv = (TextView) findViewById(R.id.enddate_tv);
        enddate_hint = (TextView) findViewById(R.id.enddate_hint);
        startdate_hint = (TextView) findViewById(R.id.startdate_hint);
        startdate_tv = (TextView) findViewById(R.id.startdate_tv);

        startdate_tv.setText(TimeHelper.getDateStringString(date.getTime(), TimeHelper.FORMAT12));
        enddate_tv.setText(TimeHelper.getDateStringString(date.getTime(), TimeHelper.FORMAT12));

        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        // ----时间转轮
        final View timepickerview = findViewById(R.id.timepicker);
        wheelTime = new WheelTime(timepickerview, TimePickerView.Type.YEAR_MONTH_DAY, this);

        //默认选中当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);

        return;
    }


    /**
     * 设置可以选择的时间范围
     * 要在setTime之前调用才有效果
     *
     * @param startYear 开始年份
     * @param endYear   结束年份
     */
    public void setRange(int startYear, int endYear) {
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);
    }

    /**
     * 设置选中时间
     *
     * @param date 时间
     */
    public void setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);
    }

//    /**
//     * 指定选中的时间，显示选择器
//     *
//     * @param date
//     */
//    public void show(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        if (date == null)
//            calendar.setTimeInMillis(System.currentTimeMillis());
//        else
//            calendar.setTime(date);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int hours = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//        wheelTime.setPicker(year, month, day, hours, minute);
//        show();
//    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        switch (tag) {
            case TAG_CANCEL:
                dismiss();
                break;
            case TAG_SUBMIT:
                if (timeSelectListener != null) {
                    String startdate = startdate_tv.getText().toString();
                    String enddate = enddate_tv.getText().toString();
                    Date date1 = new Date(TimeHelper.getTimerstampForType(TimeHelper.FORMAT12, startdate));
                    Date date2 = new Date(TimeHelper.getTimerstampForType(TimeHelper.FORMAT12, enddate));
                    if (date1.after(date2)) {
                        ToolsHelper.showInfo(mContext, "开始时间必须在截止时间之前");
                        return;
                    }
                    timeSelectListener.onTimeSelect(date1, date2);
                }
                dismiss();
                break;
            case TAG_STARTDATE:
                startdate_hint.setTextColor(mContext.getResources().getColor(R.color.white));
                startdate_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                startdate_llty.setBackgroundColor(mContext.getResources().getColor(R.color.doucument_green));

                enddate_hint.setTextColor(mContext.getResources().getColor(R.color.doucument_gray));
                enddate_tv.setTextColor(mContext.getResources().getColor(R.color.doucument_gray));
                enddate_llyt.setBackgroundColor(mContext.getResources().getColor(R.color.white));

                swichSelcted = 1;
                break;
            case TAG_ENDDATE:
                startdate_hint.setTextColor(mContext.getResources().getColor(R.color.doucument_gray));
                startdate_tv.setTextColor(mContext.getResources().getColor(R.color.doucument_gray));
                startdate_llty.setBackgroundColor(mContext.getResources().getColor(R.color.white));

                enddate_hint.setTextColor(mContext.getResources().getColor(R.color.white));
                enddate_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                enddate_llyt.setBackgroundColor(mContext.getResources().getColor(R.color.doucument_green));

                swichSelcted = 2;
                break;
        }

    }


    public interface OnTimeSelectListener {
        void onTimeSelect(Date date1, Date date2);
    }

    public interface OnSingleTimeSelectListener {
        void onTimeSelect(int k, Date date);
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

    public void setSingleTimeSelectListener(OnSingleTimeSelectListener singleTimeSelectListener) {
        this.singleTimeSelectListener = singleTimeSelectListener;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void OnIScollListener(String date) {
        long date1 = TimeHelper.getTimerstampForType(TimeHelper.FORMAT8, date);
        switch (swichSelcted) {
            case 1:
                startdate_tv.setText(TimeHelper.getDateStringString(date1, TimeHelper.FORMAT12));
                break;
            case 2:
                enddate_tv.setText(TimeHelper.getDateStringString(date1, TimeHelper.FORMAT12));
                break;
        }
    }

}
