package com.example.new_androidclient.device_management.Data;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

public class ObjectUtils {
    public static boolean checkFieldAllNull(Object object) throws Exception {
        for (Field f : object.getClass().getDeclaredFields()) {
            System.out.println("name:" + f.getName());
            f.setAccessible(true);
            if (Modifier.isFinal(f.getModifiers()) && Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            if (!isEmpty(f.get(object))) {
                return false;
            }
            f.setAccessible(false);
        }
        //父类public属性
        for (Field f : object.getClass().getFields()) {
            System.out.println("name:" + f.getName());
            f.setAccessible(true);
            if (Modifier.isFinal(f.getModifiers()) && Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            if (!isEmpty(f.get(object))) {
                return false;
            }
            f.setAccessible(false);
        }
        return true;
    }

    private static boolean isEmpty(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof String && (object.toString().equals(""))) {
            return false;
        }
        if (object instanceof Collection && ((Collection) object).isEmpty()) {
            return false;
        }
        if (object instanceof Map && ((Map) object).isEmpty()) {
            return false;
        }
        if (object instanceof Object[] && ((Object[]) object).length == 0) {
            return false;
        }
        return true;
    }

}
