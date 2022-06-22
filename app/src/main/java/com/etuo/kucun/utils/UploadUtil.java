package com.etuo.kucun.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class UploadUtil {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 1000; // 超时时间
    private static final String CHARSET = "UTF-8"; // 设置编码
    static String result = null;
    String ent_name=null;
    /**
     * 上传文件到服务器
     * @param file 需要上传的文件
     * @param RequestURL 请求的rul
     * @return 返回响应的内容
     */
    public static int uploadFile(File file, String RequestURL) {
        int res=0;
        String BOUNDARY = "--------" + UUID.randomUUID().toString().replaceAll("-", ""); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        InputStream is = null;
        DataOutputStream dos = null;
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="+BOUNDARY);
 
            if (file != null) {
                dos = new DataOutputStream(conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名
                 */
 
                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(file.getName()).append("\"").append(LINE_END);
                sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                sb.append(LINE_END);
                
                dos.write(sb.toString().getBytes());
                
                is = new FileInputStream(file);
                int len = 0;
                byte[] bytes = new byte[1024];
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                
                dos.write(LINE_END.getBytes());

                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
	                
            	if (is != null ) is.close();
            	if (dos != null ) dos.close();

            	/**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                res = conn.getResponseCode();
                if (res == 200) {
                    Log.e(TAG, "request success");
                    InputStream input = conn.getInputStream();
                    StringBuffer sb1 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                    result = sb1.toString();
                    try {
                        JSONObject dataJson=new JSONObject(result);
                        String ent_name =dataJson.getString("result");
                        result=ent_name.replaceAll("\\\\", "\\\\\\\\");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Log.e("log", "request error");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
    public static String getResult(){
    	return result;
    }
}




