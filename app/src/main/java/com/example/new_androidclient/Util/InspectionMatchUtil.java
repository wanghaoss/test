package com.example.new_androidclient.Util;

import com.example.new_androidclient.Other.Constants;
import com.example.new_androidclient.inspection.bean.InspectionDeviceDetailBean;

import java.util.List;

public class InspectionMatchUtil {

    //用户填写的值与标准值对比，返回对比结果
    public static String match(int position, String content, String condition, List<InspectionDeviceDetailBean> list) {
        boolean flag;// match
        String msg = "";
        if(!content.isEmpty()){
            if(content.substring(0,1).contains(".")){
                content = "0" + content;
            }
        }
        if(null == condition || condition.isEmpty()){
            return "对比条件异常";
        }
        Double specNumber = Double.valueOf(content); //输入值
        String assetSpecData = list.get(position).getAssetspecData();  //标准值
        switch (condition) {
            case Constants.EQUALS_SYMBOL:
                // =
                Double matchNum1 = Double.valueOf(assetSpecData);
                flag = specNumber.equals(matchNum1);
                if (flag) {
                    msg = "正常";
                } else {
                    // 不等 判断大小
                    msg = specNumber > matchNum1 ? "大于标准值" : "小于标准值";
                }
                break;
            case Constants.GREATER_THAN_SYMBOL:
                // >
                Double matchNum2 = Double.valueOf(assetSpecData);
                flag = specNumber > matchNum2;
                if (flag) {
                    msg = "正常";
                } else {
                    msg = specNumber.equals(matchNum2) ? "小于标准值" : "小于标准值";
                }
                break;

            case Constants.GREATER_THAN_OR_EQUAL_TO_SYMBOL:
                // >=
                Double matchNum3 = Double.valueOf(assetSpecData);
                flag = specNumber >= matchNum3;

                msg = flag ? "正常" : "小于标准值";
                break;

            case Constants.LESS_THAN_SYMBOL:
                // <
                Double matchNum4 = Double.valueOf(assetSpecData);
                flag = specNumber < matchNum4;
                if (flag) {
                    msg = "正常";
                } else {
                    msg = specNumber > matchNum4 ? "大于标准值" : "大于标准值";
                }
                break;

            case Constants.LESS_THAN_OR_EQUAL_TO_SYMBOL:
                // <=
                Double matchNum5 = Double.valueOf(assetSpecData);
                flag = specNumber <= matchNum5;
                msg = flag ? "正常" : "大于标准值";
                break;

            case Constants.LEFT_CLOSE_RIGHT_OPEN:
                // [ )
                String[] arr6 = assetSpecData.split(Constants.COMMA_E);
                if (arr6.length != 2) {
                    msg = "对比异常";
                }
                Double matchNum61 = new Double(arr6[0]);
                Double matchNum62 = new Double(arr6[1]);
                flag = specNumber >= matchNum61 && specNumber < matchNum62;
                if (flag) {
                    msg = "正常";
                } else {
                    msg = matchNum61 > specNumber ? "小于标准值" : "大于标准值";
                }
                break;
            case Constants.LEFT_OPEN_RIGHT_CLOSE:
                // ( ]
                String[] arr7 = assetSpecData.split(Constants.COMMA_E);
                if (arr7.length != 2) {
                    msg = "对比异常";
                }
                Double matchNum71 = new Double(arr7[0]);
                Double matchNum72 = new Double(arr7[1]);
                flag = specNumber > matchNum71 && specNumber <= matchNum72;
                if (flag) {
                    msg = "正常";
                } else {
                    msg = matchNum72 < specNumber ? "大于标准值" : "小于标准值";
                }
                break;
            case Constants.LEFT_OPEN_RIGHT_OPEN:
                // ( )
                String[] arr8 = assetSpecData.split(Constants.COMMA_E);
                if (arr8.length != 2) {
                    msg = "对比异常";
                }
                Double matchNum81 = new Double(arr8[0]);
                Double matchNum82 = new Double(arr8[1]);
                flag = specNumber > matchNum81 && specNumber < matchNum82;
                if (flag) {
                    msg = "正常";
                } else {
                    msg = matchNum82 <= specNumber ? "大于标准值" : "小于标准值";
                }
                break;
            case Constants.LEFT_CLOSE_RIGHT_CLOSE:
                // [ ]
                String[] arr9 = assetSpecData.split(Constants.COMMA_E);
                if (arr9.length != 2) {
                    msg = "对比异常";
                }
                Double matchNum91 = new Double(arr9[0]);
                Double matchNum92 = new Double(arr9[1]);
                flag = specNumber >= matchNum91 && specNumber <= matchNum92;
                if (flag) {
                    msg = "正常";
                } else {
                    msg = matchNum92 < specNumber ? "大于标准值" : "小于标准值";
                }
                break;
            default:
                msg = "对比异常";
        }
        return msg;
    }

}
