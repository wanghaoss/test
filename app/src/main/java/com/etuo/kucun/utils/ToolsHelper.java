package com.etuo.kucun.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by Hming on 2018/7/2.
 * 工具类
 */
public class ToolsHelper {

    public static boolean isNull(String str) {
        if (null == str || "".equals(str) || " ".equals(str)
                || "null".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 把单个英文字母或者字符串转换成数字ASCII码
     *
     * @param input
     * @return
     */
    public static int character2ASCII(String input) {
        char[] temp = input.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char each : temp) {
            builder.append((int) each);
        }
        String result = builder.toString();
        return Integer.parseInt(result);
    }

    public static String returnNull(String str) {

        if (isNull(str)) {
            return "";
        }
        return str;
    }

    /**
     * 判断密码是否全是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断密码是否全是由26个英文字母组成的字符串
     *
     * @param str
     * @return
     */
    public static boolean isAlphabet(String str) {
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String isNullString(String str) {
        if (null == str || "".equals(str) || " ".equals(str)
                || "null".equals(str)) {
            return "";
        }
        return str;
    }

    public static void showInfo(Context context, String info) {

        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static void showInfoForShort(Context context, String info) {

        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    /**
     * 将String转int
     *
     * @param msg
     * @return String为空返回0
     */
    public static int stringTOint(String msg) {

        return isNull(msg) ? 0 : Integer.parseInt(msg);

    }

    /**
     * 去除String空格
     *
     * @param msg
     * @return String
     */
    public static String removeSpace(String msg) {

        return msg.replaceAll(" ", "+");

    }

    /**
     * 将String转double
     *
     * @param msg
     * @return String为空返回0
     */
    public static double stringTOdouble(String msg) {

        return isNull(msg) ? 0 : Double.parseDouble(msg);

    }

    /**
     * 将String转float
     *
     * @param msg
     * @return String为空返回0
     */
    public static float stringTOfloat(String msg) {

        return isNull(msg) ? 0 : Float.parseFloat(msg);

    }

    /**
     * 将String转int
     *
     * @param msg
     * @return String为空返回0
     */
    public static int stringTOInteger(String msg) {

        return isNull(msg) ? 0 : Integer.parseInt(msg);

    }

    /**
     * 将String转long
     *
     * @param msg
     * @return String为空返回0
     */
    public static long stringTOlong(String msg) {

        return isNull(msg) ? 0 : Long.parseLong(msg);

    }

    /**
     * 根据给定字符串输出boolean
     *
     * @param str 给定字符串
     * @return 如果输入"true" 返回ture否则返回false
     */
    public static boolean isTrue(String str) {

        boolean isTrue = false;

        if (!isNull(str) && str.equals("TRUE")) {
            isTrue = true;
        }

        return isTrue;
    }

    /**
     * 根据给定字符串输出boolean
     *
     * @param str 给定字符串
     * @return 如果输入"true" 返回ture否则返回false
     */
    public static boolean isFalse(String str) {

        boolean isTrue = true;

        if (!isNull(str) && str.equals("false")) {
            isTrue = false;
        }

        return isTrue;
    }

    public static boolean isboolIP(String ipAddress) {
        String ip = "http://(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\:([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-5]{2}[0-3][0-5])";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    public static boolean isboolPort(String port) {
        // 端口号验�? 1 ~ 65535
        String regex = "^([1-9]|[1-9]\\d{1,3}|[1-6][0-5][0-5][0-3][0-5])$";
        return Pattern.matches(regex, port);
    }

    /**
     * 加密
     */
    public static String encrypt(String data, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keyspec);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     */
    public static String desEncrypt(String data, String key) {
        try {
            byte[] encrypted1 = Base64.decode(data.getBytes(), Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, keyspec);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * text 全文 sentence 关键字
     */
    public static boolean isContain(String text, String sentence) {

        char[] c1 = text.toCharArray();
        char[] c2 = sentence.toCharArray();

        if (ToolsHelper.isNull(sentence)) {
            return true;
        }

        if (text.equals(sentence)) {
            return true;
        }

        if (c1.length <= c2.length) {
            return false;
        }

        for (int i = 0; i < c1.length - c2.length + 1; i++) {
            if (c1[i] == c2[0]) {
                if (text.substring(i, i + c2.length).equals(sentence)) {
                    return true;
                }

            }

        }

        return false;
    }

    /**
     * 验证电话号码
     */
    public static boolean validatePhone(String phone) {
//        Pattern p = Pattern
//                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//
//        Matcher m = p.matcher(phone);
        return isMobileNO(null, phone);
    }

    /**
     * 验证邮箱
     */
    public static boolean validateEmail(String email) {
//        String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(email);
//        if (matcher.matches()) {
//            return true;
//        } else {
//            return false;
//        }
        return isEmail(null, email);
    }

    /**
     * 验证手机号码
     *
     * @param context,mobiles
     * @return true 手机格式正确 false 不正确
     */

    public static boolean isMobileNO(Context context, String mobiles) {
        boolean flag = false;

        if (ToolsHelper.isNull(mobiles)) {
            if (context != null) {
                showInfo(context, "手机号码不能为空");
            }
            return flag;
        }

        try {
//            Pattern p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^(4,\\D)])|(18[0,2-9])|(17[(0,6-8)]))\\d{8}$");
            Pattern p = Pattern.compile("[1][34578]\\d{9}");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }

        if (!flag) {
            if (context != null) {
                showInfo(context, "手机号码不正确");
            }
        }

        return flag;
    }

    /**
     * 验证邮箱地址是否正确
     *
     * @param context,email
     * @return true 邮箱格式正确 false 不正确
     */
    public static boolean isEmail(Context context, String email) {
        boolean flag = false;

        if (ToolsHelper.isNull(email)) {
            if (context != null)
                ToolsHelper.showInfo(context, "邮箱不能为空");
            return flag;
        }

        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        if (!flag) {
            if (context != null)
//                ToolsHelper.showInfo(context, context.getString(R.string.toolshelper_email_wrong));
                ToolsHelper.showInfo(context, "邮箱格式不正确");
        }
        return flag;
    }

    /**
     * 验证 英文和数字
     */
    public static boolean isEngOrNum(String value) {
        boolean flag;
        try {

            Pattern pattern = Pattern.compile("^[0-9a-zA-Z]+$");
            Matcher matcher = pattern.matcher(value);
            flag = matcher.matches();
        } catch (Exception Ex) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证字符串是否为数字
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 将对象转换成String
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        return obj == null ? "" : String.valueOf(obj).toString();
    }

    /**
     * 将对象转换成boolean
     *
     * @param str
     * @return
     */
    public static boolean toBoolean(String str) {
        return str == null ? false : Boolean.valueOf(str);
    }

    /**
     * 将对象转换成Int
     *
     * @param obj
     * @return
     */
    public static int toInt(Object obj) {
        return obj == null ? 0 : Integer.valueOf(toString(obj)).intValue();
    }


    /**

    public static int getIdByUser(String user) {
        int id = 0;
        if (MucManager.getInstance().isMuc(user)) {
            id = MucManager.getInstance().getid(user);
            id = id + (int) getmax(id);
        } else if (ContactsManager.getInstance().isChat(user)) {
            id = ContactsManager.getInstance().getId(user);
            id = id + 2 * (int) getmax(id);
        } else if (PublicAccountManager.getInstance().isPublicAccount(user)) {
            id = PublicAccountManager.getInstance().getId(user);
            id = id + 3 * (int) getmax(id);
        }
        return id;
    }

    public static double getmax(int i) {
        return Math.pow(10, String.valueOf(i).toString().trim().length());
    }

    /**
     * 改变文字颜色(用于搜索)
     *
     * @param text     显示的内容
     * @param textView 显示的控件
     * @param keyword  查询的关键字
     */
    public static void changeColor(String text, TextView textView, String keyword) {
        int changeTextColor;
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#0c9932"));//墨绿色

        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        if (null == keyword) {
            textView.setText(text);
            return;
        }

        changeTextColor = text.indexOf(keyword);
        if (changeTextColor != -1) {
            builder.setSpan(redSpan, changeTextColor, changeTextColor + keyword.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(builder);
        } else
            textView.setText(text);
    }

    /**
     * 三角形斜边的长度
     *
     * @param v1 直角边
     * @param v2 直角边
     * @return
     */
    public static double getBevel(double v1, double v2) {
        return Math.sqrt(v1 * v1 + v2 * v2);
    }

    /**
     * 获取绝对值
     *
     * @param v1
     * @return
     */
    public static double getBevel(double v1) {
        return Math.abs(v1);
    }


    static double DEF_PI = 3.14159265359; // PI
    static double DEF_2PI = 6.28318530712; // 2*PI
    static double DEF_PI180 = 0.01745329252; // PI/180.0
    static double DEF_R = 6370693.5; // radius of earth

    /**
     * 百度地图两点间的距离(短距离)
     *
     * @param lon1
     * @param lat1
     * @param lon2
     * @param lat2
     * @return
     */
    public static double GetShortDistance(double lon1, double lat1, double lon2, double lat2) {
        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
            dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
            dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    /**
     * 百度地图两点间的距离(长距离)
     *
     * @param lon1
     * @param lat1
     * @param lon2
     * @param lat2
     * @return
     */
    public double GetLongDistance(double lon1, double lat1, double lon2, double lat2) {
        double ew1, ns1, ew2, ns2;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 求大圆劣弧与球心所夹的角(弧度)
        distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);
        // 调整到[-1..1]范围内，避免溢出
        if (distance > 1.0)
            distance = 1.0;
        else if (distance < -1.0)
            distance = -1.0;
        // 求大圆劣弧长度
        distance = DEF_R * Math.acos(distance);
        return distance;
    }

    /*******************判断支付密码是否连续 重复*********************/

    /**
     * 不能全是相同的数字或者字母（如：000000、111111、aaaaaa）
     * @param str str.length()>0
     * @return 全部相同返回true
     */
    public static boolean equalStr(String numOrStr){
        boolean flag = true;
        char str = numOrStr.charAt(0);
        for (int i = 0; i < numOrStr.length(); i++) {
            if (str != numOrStr.charAt(i)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 不能是连续的数字--递增（如：123456、12345678）
     * @param numOrStr
     * @return 连续数字返回true
     */
    public static boolean isOrderNumeric(String numOrStr){
        boolean flag = true;//如果全是连续数字返回true
        boolean isNumeric = true;//如果全是数字返回true
        for (int i = 0; i < numOrStr.length(); i++) {
            if (!Character.isDigit(numOrStr.charAt(i))) {
                isNumeric = false;
                break;
            }
        }
        if (isNumeric) {//如果全是数字则执行是否连续数字判断
            for (int i = 0; i < numOrStr.length(); i++) {
                if (i > 0) {//判断如123456
                    int num = Integer.parseInt(numOrStr.charAt(i)+"");
                    int num_ = Integer.parseInt(numOrStr.charAt(i-1)+"")+1;
                    if (num != num_) {
                        flag = false;
                        break;
                    }
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }
    /**
     * 不能是连续的数字--递减（如：987654、876543）
     * @param numOrStr
     * @return 连续数字返回true
     */
    public static boolean isOrderNumeric_(String numOrStr){
        boolean flag = true;//如果全是连续数字返回true
        boolean isNumeric = true;//如果全是数字返回true
        for (int i = 0; i < numOrStr.length(); i++) {
            if (!Character.isDigit(numOrStr.charAt(i))) {
                isNumeric = false;
                break;
            }
        }
        if (isNumeric) {//如果全是数字则执行是否连续数字判断
            for (int i = 0; i < numOrStr.length(); i++) {
                if (i > 0) {//判断如654321
                    int num = Integer.parseInt(numOrStr.charAt(i)+"");
                    int num_ = Integer.parseInt(numOrStr.charAt(i-1)+"")-1;
                    if (num != num_) {
                        flag = false;
                        break;
                    }
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }
    /**
     * 是否为浮点数？double或float类型。
     *
     * @param str 传入的字符串。
     * @return 是浮点数返回true, 否则返回false。
     */
    public static boolean isDoubleOrFloat(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

}
