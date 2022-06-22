package com.etuo.kucun.utils;


import android.util.Log;

import com.etuo.kucun.BuildConfig;


/**
 * Created with IntelliJ IDEA.
 * User: JaveZh
 * Date: 13-12-4
 * Time: 上午9:58
 * To change this template use File | Settings | File Templates.
 */
public class LogUtil {
    public static final String TAG = "ZyTec-LOG-TAG:";




    public static void v(Throwable t) {
        log(Log.VERBOSE, t, null);
    }

    public static void v(Object s1, Object... args) {
        log(Log.VERBOSE, null, s1, args);
    }

    public static void v(Throwable t, Object s1, Object... args) {
        log(Log.VERBOSE, t, s1, args);
    }

    public static void d(Throwable t) {
        log(Log.DEBUG, t, null);
    }

    public static void d(Object s1, Object... args) {


        log(Log.DEBUG, null, s1, args);
    }

    public static void d(Throwable t, Object s1, Object... args) {
        log(Log.DEBUG, t, s1, args);
    }

    public static void i(Throwable t) {
        log(Log.INFO, t, null);
    }

    public static void i(Object s1, Object... args) {
        log(Log.INFO, null, s1, args);
    }

    public static void i(Throwable t, Object s1, Object... args) {
        log(Log.INFO, t, s1, args);
    }

    public static void w(Throwable t) {
        log(Log.WARN, t, null);
    }

    public static void w(Object s1, Object... args) {
        log(Log.WARN, null, s1, args);
    }

    public static void w(Throwable t, Object s1, Object... args) {
        log(Log.WARN, t, s1, args);
    }

    public static void e(Throwable t) {
        log(Log.ERROR, t, null);
    }

    public static void e(Object s1, Object... args) {
        log(Log.ERROR, null, s1, args);
    }

    public static void e(Throwable t, Object s1, Object... args) {
        log(Log.ERROR, t, s1, args);
    }

    private static void log(final int pType, final Throwable t, final Object s1,
                            final Object... args) {
        if (BuildConfig.DEBUG) {
            final StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];

            final String fullClassName = stackTraceElement.getClassName();
            final String content = stackTraceElement.getFileName();
            final String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            final int lineNumber = stackTraceElement.getLineNumber();
            final String method = stackTraceElement.getMethodName();


            final String tag = TAG + className ;

            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(method);
            stringBuilder.append("(").append(content).append(":").append(lineNumber).append(")");
//            stringBuilder.append("(): ");

            if (s1 != null) {
                final String message = (args == null) ? s1.toString() : s1.toString() + "  " + Ob2String(args);

                stringBuilder.append(message);
            }

            switch (pType) {
                case Log.VERBOSE:
                    if (t != null) {
                        Log.v(tag, stringBuilder.toString(), t);
                    } else {
                        Log.v(tag, stringBuilder.toString());
                    }
                    break;

                case Log.DEBUG:
                    if (t != null) {
                        Log.d(tag, stringBuilder.toString(), t);
                    } else {


                        Log.d(tag, stringBuilder.toString());
                    }
                    break;

                case Log.INFO:
                    if (t != null) {
                        Log.i(tag, stringBuilder.toString(), t);
                    } else {
                        Log.i(tag, stringBuilder.toString());
                    }
                    break;

                case Log.WARN:
                    if (t != null) {
                        Log.w(tag, stringBuilder.toString(), t);
                    } else {
                        Log.w(tag, stringBuilder.toString());
                    }
                    break;

                case Log.ERROR:
                    if (t != null) {
                        Log.e(tag, stringBuilder.toString(), t);
                    } else {
                        Log.e(tag, stringBuilder.toString());
                    }
                    break;
            }
        }
    }


    private static String Ob2String(Object... objects){
        String obString = "";
        if (objects != null && objects.length>0){
            for (int i = 0; i < objects.length; i++){
                obString  = objects[i].toString() + obString+ "  ";
            }

        }

        return obString;
    }

    public static void i() {
        i("....");
    }

    public static void d() {
        d("....");
    }

    public static void e() {
        e("....");
    }

    public static void v() {
        v("....");
    }

    public static void w() {
        w("....");
    }
}