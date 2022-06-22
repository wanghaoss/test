package com.example.new_androidclient.Util;

import com.alibaba.fastjson.JSON;
import com.example.new_androidclient.NetWork.BaseResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DataConverterUtil {
    public static Map bean_to_Map(BaseResponse bean) {
        return JSON.parseObject(JSON.toJSONString(bean), Map.class);
    }

    public static BaseResponse map_to_Bean(Map map, BaseResponse bean) {
        return JSON.parseObject(JSON.toJSONString(map), bean.getClass());
    }

    public static RequestBody map_to_body(Map map) {
        return getRequestJsonBody(map_to_String(map));
    }

    public static String map_to_String(Map map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public static RequestBody getRequestJsonBody(String jsonObject) {
        return FormBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject);
    }

    public static Map<String, Object> jsonToMap(String content) {
        content = content.trim();
        Map<String, Object> result = new HashMap<>();
        try {
            if (content.charAt(0) == '[') {
                JSONArray jsonArray = new JSONArray(content);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Object value = jsonArray.get(i);
                    if (value instanceof JSONArray || value instanceof JSONObject) {
                        result.put(i + "", jsonToMap(value.toString().trim()));
                    } else {
                        result.put(i + "", jsonArray.getString(i));
                    }
                }
            } else if (content.charAt(0) == '{') {
                JSONObject jsonObject = new JSONObject(content);
                Iterator<String> iterator = jsonObject.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    Object value = jsonObject.get(key);
                    if (value instanceof JSONArray || value instanceof JSONObject) {
                        result.put(key, jsonToMap(value.toString().trim()));
                    } else {
                        result.put(key, value.toString().trim());
                    }
                }
            } else {
                LogUtil.e("DataConverterUtil转换异常");
            }
        } catch (JSONException e) {
            LogUtil.e(e.toString());
            result = null;
        }
        return result;
    }
}
