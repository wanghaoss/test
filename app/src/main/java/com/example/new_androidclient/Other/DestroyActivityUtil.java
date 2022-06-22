package com.example.new_androidclient.Other;

import android.app.Activity;

import com.example.new_androidclient.Util.LogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DestroyActivityUtil {
    private static Map<String, Activity> destoryMap = new HashMap<>();

    //将Activity添加到队列中
    public static void addDestoryActivityToMap(Activity activity, String activityName) {
        destoryMap.put(activityName, activity);
    }

    //根据名字销毁制定Activity
    public static void destoryActivity(String activityName) {
        Set<String> keySet = destoryMap.keySet();
        LogUtil.i(String.valueOf(keySet.size()));
        if (keySet.size() > 0) {
            for (String key : keySet) {
                if (activityName.equals(key)) {
                    destoryMap.get(key).finish();
                }
            }
        }
    }

}
