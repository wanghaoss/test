/*
 * File Name: StringUtil.java
 * 2013-10-28
 */
package com.etuo.kucun.utils;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * 字符串工具类
 */
public class StringUtil {

    private static final int NUMBER_START = 48;

    /**
     * This method provider the function that replace the value in the content
     * with the specified parameters.
     *
     * @param content the string value come from string.xml.
     *                <p>
     *                eg.<code>content="aaa {0} bbb {1} ccc{2} ddd."</code>
     * @param args    the parameters replace the value(<code>{0}</code>) in content.
     * @return result.toString()
     */
    public static String getStringWithParams(String content, Object[] args) {
        StringBuffer result = new StringBuffer();
        for (int i = 0, j = content.length(); i < j; i++) {
            char temp = content.charAt(i);
            if (temp == '{') {
                int index = content.charAt(i + 1) - NUMBER_START;
                if (index >= 0 && index < args.length) {
                    result.append(args[index].toString());
                    i += 2;
                } else {
                    result.append(temp);
                }
            } else {
                result.append(temp);
            }
        }
        return result.toString();
    }

    public static String getStringWithParams(Context context, int id,
                                             Object[] args) {
        return getStringWithParams(context.getString(id), args);
    }

    public static boolean isBlank(String string) {
        if (string == null || "".equals(string.trim())) {
            return true;
        }

        return false;
    }

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    public static String avoidNull(String string) {
        return isBlank(string) ? "" : string;
    }

    public static String toUpperCase(String string) {
        return avoidNull(string).toUpperCase();
    }

    public static String toLowerCase(String string) {
        return avoidNull(string).toLowerCase();
    }

    public static String trim(String string) {
        return avoidNull(string).trim();
    }

    public static boolean isEmpty(String string) {
        if (string != null && string.trim().length() > 0) {
            return false;
        }
        return true;
    }

    public static String getImgPath(String imgPath) {
        if (TextUtils.isEmpty(imgPath) || imgPath.trim().length() == 0)
            return "null.png";
        return imgPath;
    }


    public static String numToChinese(String str) {
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };
        String result = "";
        int n = str.length();
        for (int i = 0; i < n; i++) {
            int num = str.charAt(i) - '0';
            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
            System.out.println("  "+result);
        }
        System.out.println(result);
        return result;
    }

    public static int String2int (String string) {
        if (TextUtils.isEmpty(string))
            return 0;
        return Integer.parseInt(string);
    }

    /**
     *  内容匹配  数组不越界
     * @param list
     * @param content
     * @return
     */

    public static int checkString(ArrayList<String> list, String content) {

        int positon = 0;
        if (StringUtil.isEmpty(content)){
            return positon;
        }
        positon = list.indexOf(content);
        if (-1 == positon) {
            positon = 0;
        }
        return positon;

    }

    /**
     * 押金
     * @param type
     * @return
     */
    public static String getSecurityMoney(String type){

        if ("2".equals(type)){//集装箱
            return "元/箱";
        }else {
            return "元/片";
        }

    }

    /**
     * 租金
     * @param type
     * @return
     */
    public static String getRentMoney(String type){

        if ("2".equals(type)){//集装箱
            return "元/箱/天";
        }else {
            return "元/片/次";
        }

    }

    /**
     *
     * @param PhoneNum 明文电话号码
     * @return 带星号的电话号码
     */
    public static String getStringEncryptByPhoneNum(String PhoneNum){


        if(TextUtils.isEmpty(PhoneNum) || PhoneNum.length() <= 6 ){
            return PhoneNum;
        }
        StringBuilder sb  =new StringBuilder();
        for (int i = 0; i < PhoneNum.length(); i++) {
            char c = PhoneNum.charAt(i);
            if (i >= 3 && i <= 6) {
                sb.append('*');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();


    }



    /**
     * 16进制转换成为string类型字符串
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 字符串转16进制字符串
     *
     * @param strPart
     * @return
     */
    public static String string2HexString(String strPart) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(strHex);
        }
        return hexString.toString();
    }
    /**
     *
     * @param bankNum 分区
     * @return 起始位bit
     */

    public static int getStartBitByBank(String bankNum){

        int starBitNum = 0;
        switch (bankNum){
            case "UII":
                starBitNum = 32;
                break;
            case "TID":
                starBitNum = 0;
                break;
            case "USER":
                starBitNum = 0;

                break;

        }

        return starBitNum;

    }

    /**
     *
     * @param bankNum 分区
     * @return 起始位 word
     */

    public static int getStartWordByBank(String bankNum){

        int starWordNum = 0;
        switch (bankNum){
            case "UII":
                starWordNum = 2;
                break;
            case "TID":
                starWordNum = 0;
                break;
            case "USER":
                starWordNum = 0;

                break;

        }

        return starWordNum;

    }

    /**
     * 根据字符串获取 bit长度
     * @param hexStr
     * @return
     */
    public static int getBitLengthFilter(String hexStr){
        if (!StringUtil.isEmpty(hexStr)){

            return hexStr.length() * 4;
        }

        return 0;
    }

    /**
     * 根据字符串获取 word长度
     * @param s
     * @return
     */
    public static int getWordLength(String s) {
        if (StringUtil.isEmpty(s)){
            return 0;
        }
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255) {
                length++;
            } else {
                length += 2;
            }
        }


        if (length%2 == 0){
            return  length/2;
        }else {
            return  length/2 +1;
        }

    }

    /**
     *
     * @param AreaName 分区
     * @param goodsNo 货物编号
     * @param goodsModel 货物规格
     * @param goodsNum 货物数量
     * @param goodsWeight 货物重量
     *                    "#"作为结束字符串
     * @return
     */
    public static String getWriteData(String AreaName,String goodsNo,String goodsModel,String goodsNum,String goodsWeight){

        return AreaName+"|" +goodsNo+"|"+goodsModel+"|"+goodsNum+"|"+goodsWeight+"#";
    }


    /**
     * 读取数据进行拆分
     * @param readData
     * @return
     */
    public static String[] getReadData(String readData){

        String[] strs = readData.split("\\|");

        if (strs != null){
            return strs;
        }

        return null;
    }

    /**
     * 采购验收 验证托盘货物的正确性
     * @param goodsNo
     * @param goodsModel
     * @param goodsNum
     * @param goodsWeight
     * @param readData
     * "#"作为结束字符串
     * @return
     */

    public static boolean isTrueBuyCheck(String goodsNo,String goodsModel,String goodsNum,String goodsWeight,String readData){

        if (StringUtil.isEmpty(goodsNo) || StringUtil.isEmpty(goodsModel) ||
                StringUtil.isEmpty(goodsNum) ||StringUtil.isEmpty(goodsWeight) ||StringUtil.isEmpty(readData) ){
            return false;
        }

        LogUtil.d("匹配原始数据 : " + readData);
        if (readData.contains("#" )){//假如存在结束字符串  截取字符串前面的数据
            readData = readData.substring(0,readData.indexOf("#"));

            LogUtil.d("存在 # ,截取之后字符 : " + readData);
        }
        String[] getRead = getReadData(readData);

        if (null != getRead && getRead.length>=5){
            LogUtil.d("分割之后数据  : " + getRead[0]  + "   " + getRead[1]  + "  " + getRead[2]  + "  " + getRead[3]  + "   "+getRead[4]  );

            if (goodsNo.equals(getRead[1])&& goodsModel.equals(getRead[2]) && goodsNum.equals(getRead[3])&&
                    goodsWeight.equals(getRead[4])){
                return true;

            }else {
                return false;
            }
        }else {
            return false;
        }

    }



}
