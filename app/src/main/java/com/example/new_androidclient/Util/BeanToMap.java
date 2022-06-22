package com.example.new_androidclient.Util;

import java.lang.reflect.Field;
import java.util.Map;

public class BeanToMap {

    public static Map<String, String> reflect(Object e, Map<String, String> map) throws Exception {
        Class cls = e.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);
            System.out.println("属性名:" + f.getName() + " 属性值:" + f.get(e));
            map.put(f.getName(), String.valueOf(f.get(e)));
        }
        return map;
    }
}
