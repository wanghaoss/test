package com.example.new_androidclient.Util;

import android.app.ProgressDialog;
import android.content.Context;

public class SimpleDialogUtil {
    static ProgressDialog waitingDialog;

    public static void simpleWaitDialog(Context context, String title, String message) {
        /* 等待Dialog具有屏蔽其他控件的交互能力
         * @setCancelable 为使屏幕不可点击，设置为不可取消(false)
         * 下载等事件完成后，主动调用函数关闭该Dialog
         */
        waitingDialog = new ProgressDialog(context);
        waitingDialog.setTitle(title);
        waitingDialog.setMessage(message);
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
    }
    public static void cancelDialog(){
        if(waitingDialog.isShowing()){

        }
        waitingDialog.dismiss();
    }
}
