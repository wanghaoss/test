package com.etuo.kucun.widget.pickerview.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeHelper {

    public static final String FORMAT1 = "yyyyMMdd_HHmmss";
    public static final String FORMAT2 = "HH:mm";
    public static final String FORMAT3 = "MM-dd HH:mm";
    public static final String FORMAT4 = "yyyy/MM/dd";
    public static final String FORMAT5 = "yyyy/MM/dd HH:mm";
    public static final String FORMAT6 = "yyyy年MM月";
    public static final String FORMAT8 = "yyyy-MM-dd HH:mm";
    public static final String FORMAT9 = "yyyy-MM-dd";
    public static final String FORMAT10 = "yyyy年MM月 HH:mm";
    public static final String FORMAT11 = "MM/dd HH:mm";
    public static final String FORMAT12 = "yyyy年MM月dd日";
    public static final String FORMAT13 = "HH:mm:ss";
    public static final String FORMAT14 = "yyyy-MM";
    public static final String FORMAT15 = "MM-dd";
    public static final String FORMAT16 = "mm:ss";
    public static final String FORMAT17 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT18 = "yyyy";

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTimestamp(String pattern) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(new SimpleDateFormat(pattern).format(new Date()));
        return buffer.toString();
    }

    public static long getTimerstampForType(String formats, String dateString) {
        SimpleDateFormat format = new SimpleDateFormat(formats, Locale.CHINESE);
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date.getTime();

    }

    /**
     * 获得时间字符串
     *
     * @param millis
     * @return
     */
    public static String getDateStringString(long millis) {
        Date date = new Date(millis);
        String dateString = new SimpleDateFormat(FORMAT5, Locale.CHINESE).format(date);
        return dateString;
    }

    /**
     * 根据格式获得时间字符串
     *
     * @param millis
     * @return
     */
    public static String getDateStringString(long millis, String formats) {
        Date date = new Date(millis);
        String dateString = new SimpleDateFormat(formats, Locale.CHINESE).format(date);
        return dateString;
    }

    public static String getTimeFromMillis(String type, long time) {
        SimpleDateFormat formatter = new SimpleDateFormat(type);
        Date curDate = new Date(time);
        return formatter.format(curDate);
    }

    public static String getTimeFromDate(String type, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(type);
        return formatter.format(date);
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT5);
        Date curDate = new Date();
        return formatter.format(curDate);
    }

    public static String getCurrentTime(String type) {
        SimpleDateFormat formatter = new SimpleDateFormat(type);
        Date curDate = new Date();
        return formatter.format(curDate);
    }

    public static String getBeforeTime(String type, int beforeDAY) {
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(calendar.DAY_OF_YEAR, -beforeDAY);  //设置为前beforeMonth月
        dBefore = calendar.getTime();   //得到前3月的时间
        SimpleDateFormat formatter = new SimpleDateFormat(type);
        return formatter.format(dBefore);
    }

    public static String getYesterdayTime(String type) {
        SimpleDateFormat formatter = new SimpleDateFormat(type);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return formatter.format(cal.getTime());
    }


    public static String getTimeToShow(Date date) {
        if (date == null) {
            return null;
        }
        if (isToday(date)) {
            return new SimpleDateFormat(FORMAT2, Locale.CHINESE).format(date);
        } else {
            return new SimpleDateFormat(FORMAT3, Locale.CHINESE).format(date);
        }
    }

    /**
     * 格式化时间
     *
     * @param date
     * @return
     */
    public static boolean isToday(Date date) {
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance(); // 今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        current.setTime(date);
        if (current.after(today)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断时间是不是今天
     *
     * @param time
     * @return
     */
    public static boolean isToday(long time) {
        String now = getCurrentTime(FORMAT4);
        String date = getTimeFromMillis(FORMAT4, time);
        return now.equals(date);
    }

    /**
     * 判断时间是不是今天
     *
     * @param time
     * @return
     */
    public static boolean isThisDay(long currentTime, long time) {
        String now = getTimeFromMillis(FORMAT4, currentTime);
        String date = getTimeFromMillis(FORMAT4, time);
        return now.equals(date);
    }

    /**
     * 判断是否是两分钟之内
     *
     * @param sendTime 消息的发送时间
     * @return
     */
    public static boolean coludRecall(long sendTime) {
        long currentTime = new Date().getTime();
        if (currentTime - sendTime <= 120000)   // 120000 = 2分钟
            return true;
        else
            return false;
    }
    /**
     * 判断是否是一分钟之内
     *
     * @param sendTime 消息的发送时间
     * @return
     */
    public static boolean inOneMin(long sendTime) {
        long currentTime = new Date().getTime();
        if (currentTime - sendTime <= 60000)   // 120000 = 2分钟
            return true;
        else
            return false;
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param date 传入的 时间
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(Date date) {
        try {
            Calendar pre = Calendar.getInstance();
            Date predate = new Date(System.currentTimeMillis());
            pre.setTime(predate);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
                int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                        - pre.get(Calendar.DAY_OF_YEAR);

                if (diffDay == 0) {
                    return true;
                }
            }
        } catch (Exception ex) {

        }
        return false;
    }

    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param date 传入的 时间
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsYesterday(Date date) {
        try {
            Calendar pre = Calendar.getInstance();
            Date predate = new Date(System.currentTimeMillis());
            pre.setTime(predate);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
                int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                        - pre.get(Calendar.DAY_OF_YEAR);

                if (diffDay == -1) {
                    return true;
                }
            }
        } catch (Exception ex) {

        }
        return false;
    }

    /**
     * 判断是否为明天(效率比较高)
     *
     * @param date 传入的 时间
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsTomorrow(Date date) {
        try {
            Calendar pre = Calendar.getInstance();
            Date predate = new Date(System.currentTimeMillis());
            pre.setTime(predate);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
                int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                        - pre.get(Calendar.DAY_OF_YEAR);

                if (diffDay == 1) {
                    return true;
                }
            }
        } catch (Exception ex) {

        }
        return false;
    }


    /**
     * 当月第一天
     *
     * @param date 传入的 时间
     * @return Date
     * @throws ParseException
     */
    public static Date getFirstDay(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
//        // 获取前月的最后一天
//        cale = Calendar.getInstance();
//        cale.add(Calendar.MONTH, 1);
//        cale.set(Calendar.DAY_OF_MONTH, 0);
//        lastday = format.format(cale.getTime());
//        System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);


        return cale.getTime();
    }

    /**
     * 当月最后一天
     *
     * @param date 传入的 时间
     * @return Date
     * @throws ParseException
     */
    public static Date getLastDay(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return cale.getTime();
    }

    public static String getWeek(String value) {
        String week = "";
        switch (value) {
            case "MONDAY":
                week = "星期一";
                break;
            case "SATURDAY":
                week = "星期六";
                break;
            case "THURSDAY":
                week = "星期四";
                break;
            case "TUESDAY":
                week = "星期二";
                break;
            case "SUNDAY":
                week = "星期日";
                break;
            case "FRIDAY":
                week = "星期五";
                break;
            case "WEDNESDAY":
                week = "星期三";
                break;
        }
        return week;
    }

    public static String getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(date);
        } catch (Exception e) {
//            LogHelper.e("获取星期几", e);
        }
        String week = "";
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    public static String getWeek() {
        Calendar c = Calendar.getInstance();
        String week = "";
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    public static String getWeek(long time) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTimeInMillis(time);
        } catch (Exception e) {
//            LogHelper.e("获取星期几", e);
        }
        String week = "";
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * 毫秒转换成时分
     */
    public static String getTimeMin(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strMinute + ":" + strSecond;

    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
     public static Date strToDateLong(String strDate,String type) {
       SimpleDateFormat formatter = new SimpleDateFormat(type);
       ParsePosition pos = new ParsePosition(0);
          Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
         }

    /**
     * 将字符串转位日期类型
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
}
