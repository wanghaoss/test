package com.example.new_androidclient.jpush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.Util.DataConverterUtil;
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class PushMessageReceiver extends JPushMessageReceiver {
    private static final String TAG = "PushMessageReceiver";

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        LogUtil.i(TAG, "[onMessage] " + customMessage);
        //    processCustomMessage(context,customMessage);
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        LogUtil.i(TAG, "[onNotifyMessageOpened] " + message);
        try {
            //打开自定义的Activity
//            Intent i = new Intent(context, MainActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString(JPushInterface.EXTRA_NOTIFICATION_TITLE,message.notificationTitle);
//            bundle.putString(JPushInterface.EXTRA_ALERT,message.notificationContent);
//            i.putExtras(bundle);
//      原来就注释的      //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//            context.startActivity(i);
            if (message.notificationExtras != null) {
                Map<String, Object> map = DataConverterUtil.jsonToMap((message.notificationExtras));
                String moduleName = (String) map.get("modular"); //判断推送所属模块
                if (moduleName != null && moduleName.contains("隐患")) {
                    toHazard(map, context);
                }

            }

        } catch (Throwable throwable) {
            LogUtil.i(throwable.toString());
            ToastUtil.show(context, throwable.toString());
        }
    }

    private void toHazard(Map<String, Object> map, Context context) {
        String route = "";
        String status = "";
        int planId = Integer.parseInt(map.get("formId").toString());
        try {
            status = Objects.requireNonNull(map.get("status")).toString();
        } catch (NullPointerException e) {
            ToastUtil.show(context, e.toString());
        }
        //不跳转到具体页面， 跳转到列表
        if (status.contains("排查中") || status.contains("已下达")) {
            route = RouteString.HazardDetailActivity;
        } else if (status.contains("已整改") || status.contains("整改验收拒绝")) {
            //   route = RouteString.HazardVerificationDetailActivity;   //验收详情
            route = RouteString.HazardVerificationListActivity; // 验收列表，用于一个一个保存或者验收
        } else if (status.contains("整改验收待审核") || status.contains("整改验收待审批")) {
            route = "";   //跳转到验收详情
            ARouter.getInstance().build(RouteString.HazardVerificationDetailActivity).withInt("idFromPush", planId)
                    .withBoolean("isSign", true)
                    .withBoolean("fromPush", true)
                    .navigation();
        } else if (status.contains("已排查，待检查单位负责人签字") || status.contains("待受检单位负责人签字")) {
            route = RouteString.HazardNotificationSignActivity;
        } else if (status.contains("已接收，待分析") || status.contains("已通知") || status.contains("重新隐患分析")) {
            //   route = RouteString.AnalysisActivity;
            route = RouteString.HazardAnalysisNewActivity;
        } else if (status.contains("待编制整改计划") || status.contains("已分析")) {
            route = RouteString.HazardPlanActivity;
        } else if (status.contains("整改计划待审核") || status.contains("整改计划待审批")) {
            route = RouteString.HazardPlanSignActivity;
        } else if (status.contains("整改中") || status.contains("重新整改")) {
            route = RouteString.HazardGovernmentActivity;
        } else if (status.contains("隐患分析待审核") || status.contains("隐患分析待审批")) {
            route = "";
            ARouter.getInstance().build(RouteString.HazardAnalysisNewActivity).withInt("planId", planId)
                    .withBoolean("isSign", true).navigation();
        } else if (status.contains("1") || status.contains("2") || status.contains("4")) { //1待审核 2待审批 4驳回重编
            route = RouteString.HazardTablePlanDetailActivity;
        }else if (status.contains("重大隐患消息推送") ) { //1待审核 2待审批 4驳回重编
            route = RouteString.HazardMajorHiddenDangerListActivity;
        }
        if (!route.isEmpty()) {
            ARouter.getInstance().build(route).withInt("planId", planId).navigation();
        }
    }

    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        LogUtil.i(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮");
        String nActionExtra = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);

        //开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
        if (nActionExtra == null) {
            LogUtil.i(TAG, "ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
            return;
        }
        if (nActionExtra.equals("my_extra1")) {
            LogUtil.i(TAG, "[onMultiActionClicked] 用户点击通知栏按钮一");
        } else if (nActionExtra.equals("my_extra2")) {
            LogUtil.i(TAG, "[onMultiActionClicked] 用户点击通知栏按钮二");
        } else if (nActionExtra.equals("my_extra3")) {
            LogUtil.i(TAG, "[onMultiActionClicked] 用户点击通知栏按钮三");
        } else {
            LogUtil.i(TAG, "[onMultiActionClicked] 用户点击通知栏按钮未定义");
        }
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        LogUtil.i(TAG, "[onNotifyMessageArrived] " + message);
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        LogUtil.i(TAG, "[onNotifyMessageDismiss] " + message);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        LogUtil.i(TAG, "[onRegister] " + registrationId);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        LogUtil.i(TAG, "[onConnected] " + isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        LogUtil.i(TAG, "[onCommandResult] " + cmdMessage);
    }

//    @Override
//    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
//        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context,jPushMessage);
//        super.onTagOperatorResult(context, jPushMessage);
//    }
//    @Override
//    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage){
//        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context,jPushMessage);
//        super.onCheckTagOperatorResult(context, jPushMessage);
//    }
//    @Override
//    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
//        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context,jPushMessage);
//        super.onAliasOperatorResult(context, jPushMessage);
//    }
//
//    @Override
//    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
//        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context,jPushMessage);
//        super.onMobileNumberOperatorResult(context, jPushMessage);
//    }

    //send msg to MainActivity
//    private void processCustomMessage(Context context, CustomMessage customMessage) {
//        if (MainActivity.isForeground) {
//            String message = customMessage.message;
//            String extras = customMessage.extra;
//            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (extraJson.length() > 0) {
//                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//
//                }
//
//            }
//            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//        }
//    }

    @Override
    public void onNotificationSettingsCheck(Context context, boolean isOn, int source) {
        super.onNotificationSettingsCheck(context, isOn, source);
        LogUtil.i(TAG, "[onNotificationSettingsCheck] isOn:" + isOn + ",source:" + source);
    }

}
