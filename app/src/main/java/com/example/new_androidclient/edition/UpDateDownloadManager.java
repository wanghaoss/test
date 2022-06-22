package com.example.new_androidclient.edition;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.AccountSecurityUnBindDialog;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.edition.bean.EditionBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class UpDateDownloadManager {

    private static Context mContext;
    private ProgressBar mProgressBar;
    private Dialog mDownloadDialog;

    private String mSavePath;
    private int mProgress = 0;

    private boolean mIsCancel = false;

    private static final int DOWNLOADING = 1;
    private static final int DOWNLOAD_FINISH = 2;

    private EditionBean mBean;
    String versionName;

    public UpDateDownloadManager(Context context, EditionBean bean) {
        mContext = context;
        mBean = bean;
    }

    public void downApk() {
            isUpdate();
    }

    /**
     * 开启安装APK权限(适配8.0)
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + mContext.getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        mContext.startActivity(intent);
        showNoticeDialog();
    }

    @SuppressLint("HandlerLeak")
    private Handler mUpdateProgressHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DOWNLOADING:
                    // 设置进度条
                    mProgressBar.setProgress(mProgress);
                    break;
                case DOWNLOAD_FINISH:
                    // 隐藏当前下载对话框
                    mDownloadDialog.dismiss();
                    // 安装 APK 文件
                    try {
                        installAPK();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        };
    };

    /*
     * 与本地版本比较判断是否需要更新
     */
    protected void isUpdate() {
        PackageManager manager = mContext.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(),0);
            versionName = info.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (mBean != null) {
            Double value = Double.valueOf(mBean.getVer());
            int nameValue = value.intValue();
            Double versionValue = Double.valueOf(versionName);
            int versionNameValue = versionValue.intValue();

            String version = String.valueOf(versionName);
            if (!version.equals(mBean.getVer()) && nameValue > versionNameValue) {
                showNoticeDialog();
            } else {
                new AccountSecurityUnBindDialog(mContext, R.style.dialog, "2", "提示", new AccountSecurityUnBindDialog.OnCloseListener() {

                    @Override
                    public void onConfirm(Dialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onClose(Dialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
            }
        }
    }

    /*
     * 有更新时显示提示对话框
     */
    protected void showNoticeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        String message = "软件有更新，要下载安装吗？";
        builder.setMessage(message);

        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= 26) {
                    boolean b = mContext.getPackageManager().canRequestPackageInstalls();
                    if (b) {
                        // 隐藏当前对话框
                        dialog.dismiss();
                        // 显示下载对话框
                        showDownloadDialog();
                    } else {
                        //请求安装未知应用来源的权限
                        startInstallPermissionSettingActivity();
                    }
                } else {
                    // 隐藏当前对话框
                    dialog.dismiss();
                    // 显示下载对话框
                    showDownloadDialog();
                }
            }
        });

        builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 隐藏当前对话框
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    /*
     * 显示正在下载对话框
     */
    public void showDownloadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("下载中");
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.id_progress);
        builder.setView(view);

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 隐藏当前对话框
                dialog.dismiss();
                // 设置下载状态为取消
                mIsCancel = true;
            }
        });

        mDownloadDialog = builder.create();

        //设置点击屏幕不消失
        mDownloadDialog .setCanceledOnTouchOutside(false);
        //设置点击返回键不消失
        mDownloadDialog .setCancelable(false);

        mDownloadDialog.show();

        // 下载文件
        downloadAPK();
    }

    /*
     * 开启新线程下载文件
     */
    private void downloadAPK() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
                        mSavePath = sdPath + Environment.DIRECTORY_DOWNLOADS;

                        Log.d("下载到路径:", mSavePath);

                        File dir = new File(mSavePath);
                        if (!dir.exists())
                            dir.mkdir();

                        // 下载文件
                        if (mBean.getAppUrl() != null && !TextUtils.isEmpty(mBean.getAppUrl())) {
                            HttpURLConnection conn = (HttpURLConnection) new URL(mBean.getAppUrl()).openConnection();
                            conn.connect();
                            InputStream is = conn.getInputStream();
                            int length = conn.getContentLength();
//                        HttpURLConnection conn = (HttpURLConnection) new URL("com.example.new_androidclient").openConnection();

                        File apkFile = new File(mSavePath, "zhuning-isim.apk");

                        Log.d("apkFile:", String.valueOf(apkFile));

                        FileOutputStream fos = new FileOutputStream(apkFile);
                        Runtime.getRuntime().exec("chmod 622 " + apkFile);
                        int count = 0;
                        byte[] buffer = new byte[1024];
                        while (!mIsCancel) {
                            int numread = is.read(buffer);
                            count += numread;
                            // 计算进度条的当前位置
                            mProgress = (int) (-((float) count / length) * 100);
                            // 更新进度条
                            mUpdateProgressHandler.sendEmptyMessage(DOWNLOADING);

                            // 下载完成
                            if (numread < 0) {
                                mUpdateProgressHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }else {
                            Looper.prepare();
                            ToastUtil.show(mContext,"下载异常，请重试！");
                            mDownloadDialog.dismiss();
                            Looper.loop();
                        }
                    }
                }catch(Exception e){
                    ToastUtil.show(mContext,"下载异常");
                    mDownloadDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
     * 下载到本地后执行安装
     */
    protected void installAPK() throws IOException {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //由于没有在Activity环境下启动Activity,所以设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        File apkFile = new File(mSavePath, "zhuning-isim.apk");
        Runtime.getRuntime().exec("chmod 622 " + apkFile);
        if (!apkFile.exists())
            return;

        Uri contentUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            contentUri = FileProvider.getUriForFile(mContext, "com.example.new_androidclient.inspection.MyFileProvider", apkFile);
        } else {
            contentUri = Uri.fromFile(apkFile);
        }
        intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        // 查询所有符合 intent 跳转目标应用类型的应用，注意此方法必须放置在 setDataAndType 方法之后
        List<ResolveInfo> resolveLists = mContext.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        // 然后全部授权
        for (ResolveInfo resolveInfo : resolveLists){
            String packageName = resolveInfo.activityInfo.packageName;
            mContext.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
//	    安装完成后，启动app
        mContext.startActivity(intent);

    }
}
