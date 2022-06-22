package com.etuo.kucun.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.request.BaseRequest;
import com.etuo.kucun.R;
import com.etuo.kucun.TuoPanConfig;
import com.etuo.kucun.model.VersionModel;
import com.etuo.kucun.view.AppUpdateProgressDialog;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by yhn on 2018/8/10.
 */

public class UpVersionUtil {

    public Context mContext;
    // 提示版本信息更新
    private String isForce_update = "-1"; // 是否更新
    private String dowmLoadurl = ""; //下载链接
    private boolean isDownload = false;
    private AppUpdateProgressDialog dialog = null;
    private float mProgress = 0;//下载进度
    private final int mMaxProgress = 100;//百分比

    private OnClickBtItem onClickBtItem;

    public UpVersionUtil(Context context) {
        mContext = context;
    }

    public void showVersionDialog(final VersionModel model) {


        isForce_update = model.getAndroidForceUpdate();
        dowmLoadurl = model.getAndroidDownloadLink();

//        dowmLoadurl = "http://d2.921zs.com/download/u/6/1484300916_5903.apk";

        final Dialog dialog = new Dialog(mContext, R.style.MyDialogStyle);
        dialog.setContentView(R.layout.dialog_version_update);
        TextView dialog_version_titleTV = (TextView) dialog.findViewById(R.id.dialog_version_titleTV);
        TextView dialog_version_contentTV = (TextView) dialog.findViewById(R.id.dialog_version_contentTV);
        TextView dialog_closeTV = (TextView) dialog.findViewById(R.id.dialog_closeTV);
        final TextView dialog_download = (TextView) dialog.findViewById(R.id.dialog_download);

        RelativeLayout dialog_version_cancelRL = (RelativeLayout) dialog.findViewById(R.id.dialog_version_cancelRL);
        RelativeLayout dialog_version_updateRL = (RelativeLayout) dialog.findViewById(R.id.dialog_version_updateRL);

        //标题
        dialog_version_titleTV.setText("【V" + model.getAndroid() + "更新提示】");

        //更新信息
        dialog_version_contentTV.setText(model.getAndroidUpdateMessage());

        if (!TextUtils.isEmpty(isForce_update) && isForce_update.equals("1")) { // 强制更新

            dialog_download.setText("立即下载");
            dialog_closeTV.setText("退出程序");
        } else if (!TextUtils.isEmpty(isForce_update) && isForce_update.equals("0")) { // 不强制更新

            dialog_download.setText("立即下载");
            dialog_closeTV.setText("忽略");

        }

        dialog_version_cancelRL.setOnClickListener(new View.OnClickListener() { // 取消 (退出程序)
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!TextUtils.isEmpty(isForce_update) && isForce_update.equals("1")) {
                    System.exit(0);
                    onClickBtItem.myOrderCloseClick();
                } else {
                    onClickBtItem.myOrderNextClick();
                }

            }
        });
        dialog_version_updateRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 下载
                if (isDownload) {
                    return;
                }
                if (!TextUtils.isEmpty(dowmLoadurl)) {
                    showDownloadingDialog();
                    //开始下载APK
                    fileDownload(dowmLoadurl);

                }

                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(false);

    }

    public void OnClickBtItem(OnClickBtItem onClickBtItem) {
        this.onClickBtItem = onClickBtItem;
    }

    //接口回调
    public interface OnClickBtItem {
        void myOrderNextClick();

        void myOrderCloseClick();


    }

    /**
     * 显示下载进度条dialog
     */
    private void showDownloadingDialog() {
        dialog = new AppUpdateProgressDialog(mContext);
        //正在下载时，不可关闭dialog
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    Toast.makeText(mContext, "正在下载请稍后", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            }
        });
        dialog.show();
    }


    /**
     * 下载
     *
     * @param url
     */
    public void fileDownload(String url) {
        OkGo.get(url)//
                .tag(this)//文件下载至 ZhongMinXinGuang/ZMXG.apk
                .execute(new FileCallback(TuoPanConfig.CACHE_ROOT_NAME + TuoPanConfig.APK_NAME) {
                    @Override
                    public void onBefore(BaseRequest request) {

                        isDownload = true;

                        ShowToast.toastTime(mContext, "正在下载中", 5);
                    }

                    @Override
                    public void onSuccess(File file, Call call, Response response) {

                        isDownload = false;
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        installApk(mContext, file);

                        LogUtil.d("file", "file : " + file.toString());
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        System.out.println("downloadProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);
                        mProgress = progress * 100;
                        Message msg = myHandler.obtainMessage();
                        msg.what = 100;
                        msg.obj = mProgress;
                        myHandler.sendMessage(msg);

                    }

                    @Override
                    public void onError(Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(call, response, e);
                        isDownload = false;
                        if (e != null) {
                            ShowToast.toastTime(mContext, "下载出错" + StringUtils.NullToStr(e.getMessage()), 5);
                        }
                    }
                });
    }

    /**
     * @Description 安装APK
     */
    public void installApk(Context context, File file) {

        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            // 声明需要的临时的权限
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // 第二个参数，即第一步中配置的authorities
            Uri contentUri = FileProvider.getUriForFile(context, TuoPanConfig.fileproviderName, file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }

//        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
//            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
//            Uri apkUri = FileProvider.getUriForFile(context, TuoPanConfig.fileproviderName, file);
//            //添加这一句表示对目标应用临时授权该Uri所代表的文件
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//        } else {
//            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//        }
        context.startActivity(intent);
    }

    @SuppressLint("HandlerLeak")
    private
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case mMaxProgress:
                    float progress = (float) msg.obj;
                    dialog.setProgress((int) progress);

                    LogUtil.d(" 下载完成 进度  : " + progress);
                    if (mMaxProgress == progress) {

                        LogUtil.d(" 下载完成  : " + progress);
                        dialog.dismiss();
//                        Toast.makeText(MainActivity.this,"下载完成，跳转到安装界面",Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };


}
