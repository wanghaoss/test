package com.etuo.kucun.utils;

import android.app.Activity;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.AdapterView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Hming on 2018/7/6.
 */

public class ViewUtilsHelp {
    public static void OnClickListener(View view, int resID, View.OnClickListener listener) {
        injectOnClick(new ViewFinder(view), new int[]{resID}, listener);

    }

    public static void OnClickListener(View view, int[] resID, View.OnClickListener listener) {
        injectOnClick(new ViewFinder(view), resID, listener);

    }

    public static void OnClickListener(Activity activity, int resID, View.OnClickListener listener) {
        injectOnClick(new ViewFinder(activity), new int[]{resID}, listener);

    }

    public static void OnClickListener(Activity activity, int[] resID, View.OnClickListener listener) {
        injectOnClick(new ViewFinder(activity), resID, listener);

    }

    public static void OnClickListener(PreferenceActivity preferenceActivity, int resID, View.OnClickListener
            listener) {
        injectOnClick(new ViewFinder(preferenceActivity), new int[]{resID}, listener);

    }

    public static void OnClickListener(PreferenceActivity preferenceActivity, int[] resID, View.OnClickListener
            listener) {
        injectOnClick(new ViewFinder(preferenceActivity), resID, listener);

    }

    private static void injectOnClick(ViewFinder finder, int[] resID, View.OnClickListener listener) {

        for (int index = 0; index < resID.length; index++) {
            finder.findViewById(resID[index]).setOnClickListener(listener);
        }

    }


    public static void OnItemClickListener(View view, int[] resID, AdapterView.OnItemClickListener listener) {
        injectOnItemClickListener(new ViewFinder(view), resID, listener);

    }

    public static void OnItemClickListener(View view, int resID, AdapterView.OnItemClickListener listener) {
        injectOnItemClickListener(new ViewFinder(view), new int[]{resID}, listener);

    }

    public static void OnItemClickListener(Activity activity, int[] resID, AdapterView.OnItemClickListener listener) {
        injectOnItemClickListener(new ViewFinder(activity), resID, listener);

    }

    public static void OnItemClickListener(Activity activity, int resID, AdapterView.OnItemClickListener listener) {
        injectOnItemClickListener(new ViewFinder(activity), new int[]{resID}, listener);

    }

    public static void OnItemClickListener(PreferenceActivity preferenceActivity, int[] resID, AdapterView
            .OnItemClickListener listener) {
        injectOnItemClickListener(new ViewFinder(preferenceActivity), resID, listener);

    }

    public static void OnItemClickListener(PreferenceActivity preferenceActivity, int resID, AdapterView
            .OnItemClickListener listener) {
        injectOnItemClickListener(new ViewFinder(preferenceActivity), new int[]{resID}, listener);

    }

    private static void injectOnItemClickListener(ViewFinder finder, int[] resID, AdapterView.OnItemClickListener
            listener) {

        for (int index = 0; index < resID.length; index++) {
            try {
                View e = finder.findViewById(resID[index]);
                Method setEventListenerMethod = e.getClass().getMethod("setOnItemClickListener", new
                        Class[]{AdapterView.OnItemClickListener.class});

                setEventListenerMethod.invoke(e, new Object[]{listener});

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }
}
