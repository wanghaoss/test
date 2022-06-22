package com.etuo.kucun.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.etuo.kucun.FrameworkApp;
import com.etuo.kucun.R;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.callback.JsonCallback;
import com.etuo.kucun.model.VersionModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.utils.AppUtils;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.NetUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.UpVersionUtil;
import com.etuo.kucun.utils.UrlTools;
import com.etuo.kucun.utils.permission.EasyPermissions;
import com.lzy.okgo.OkGo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.etuo.kucun.utils.UrlTools.UPDATEVERSION;

/**
 * 欢迎页
 *
 * @author Liujinxin
 * @version 2.0.0
 * @since 2018/03/8
 */
public class SplashActivity extends Activity implements EasyPermissions.PermissionCallbacks, UpVersionUtil.OnClickBtItem {

    private int checkNum = 0;// 检测存储的次数
    private VersionModel versionMode;
    private static final int REQUEST_PERMISSION_STORAGE = 0x01; //申请存储权限

    private UpVersionUtil mUpVersionUtil;

    private int TIMES = 2000;// ms

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= 23
                && getApplicationInfo().targetSdkVersion >= 23) {
            if (isNeedCheck) {
                checkPermissions(needPermissions);
            }
        } else {
            getVersionData();
        }


    }


    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };

    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * @param permissions
     * @since 2.5.0
     */
    private void checkPermissions(String... permissions) {
        try {
            if (Build.VERSION.SDK_INT >= 23
                    && getApplicationInfo().targetSdkVersion >= 23) {
                List<String> needRequestPermissonList = findDeniedPermissions(permissions);
                if (null != needRequestPermissonList
                        && needRequestPermissonList.size() > 0) {
                    String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                    Method method = getClass().getMethod("requestPermissions", new Class[]{String[].class,
                            int.class});
                    TIMES = 1000;
                    method.invoke(this, array, PERMISSON_REQUESTCODE);
                } else {
                    TIMES = 2000;
                    getVersionData();
                }
            }
        } catch (Throwable e) {

        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        if (Build.VERSION.SDK_INT >= 23
                && getApplicationInfo().targetSdkVersion >= 23) {
            try {
                for (String perm : permissions) {
                    Method checkSelfMethod = getClass().getMethod("checkSelfPermission", String.class);
                    Method shouldShowRequestPermissionRationaleMethod = getClass().getMethod("shouldShowRequestPermissionRationale",
                            String.class);
                    if ((Integer) checkSelfMethod.invoke(this, perm) != PackageManager.PERMISSION_GRANTED
                            || (Boolean) shouldShowRequestPermissionRationaleMethod.invoke(this, perm)) {
                        needRequestPermissonList.add(perm);
                    }
                }
            } catch (Throwable e) {

            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否所有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, paramArrayOfInt, this);
        if (requestCode == PERMISSON_REQUESTCODE) {


//            if (!verifyPermissions(paramArrayOfInt)) {
//                showMissingPermissionDialog();
//                isNeedCheck = false;
//            }

            getVersionData();
        }
    }

    private void goMainActivity() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {


                if (PreferenceCache.isGesture() || PreferenceCache.getFingerFlg()) {
                    Intent intent = new Intent(SplashActivity.this, GestureAndFingerLoginActivity.class);
                    intent.putExtra(ExtraConfig.IntentType.KET_GESTURE_FLAG, 3);
                    startActivity(intent);
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, TIMES);
    }


    private Activity getActivity() {
        return SplashActivity.this;
    }


    /*********************************更新**************************/
    //获取版本信息
    public void getVersionData() {
        if (NetUtil.getNetWorkState(getActivity()) == -1) {

            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }

        try {
            OkGo.get(UrlTools.getInterfaceUrl(UPDATEVERSION))
                    .tag(this)//
                    .execute(new JsonCallback<LzyResponse<VersionModel>>() {
                        @Override
                        public void onSuccess(final LzyResponse<VersionModel> responseData, Call call, Response response) {
                            versionMode = responseData.bean;
                            int VersionCode = AppUtils.getVersionCode(FrameworkApp.getAppContext());
                            int getVersionCode = Integer.parseInt(!TextUtils.isEmpty(versionMode.getAndroidCode()) ? versionMode.getAndroidCode() : "0");
                            if (getVersionCode > VersionCode) { // 更新版本
                                checkVersion();
                                TIMES = 500;
                            } else {
//                                ShowToast.showToast("已是最新版本!",getActivity());

                                LogUtil.d("已是最新版本");
                                TIMES = 1000;
                                goMainActivity();
                            }

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            ShowToast.toastTime(getActivity(), e.getMessage().toString(), 5);
                            LogUtil.d("eeeee", e.getMessage());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();

            LogUtil.d("eeeee", "请求异常222");
        }
    }

    /**
     * 检测存储权限和版本号
     */
    private void checkVersion() {

        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            mUpVersionUtil = new UpVersionUtil(getActivity());
            mUpVersionUtil.showVersionDialog(versionMode);
            mUpVersionUtil.OnClickBtItem(this);
//            new UpVersionUtil(getActivity()).showVersionDialog(versionMode);
        } else {
            EasyPermissions.requestPermissions(this, "使用手机存储权限", REQUEST_PERMISSION_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }


    /**
     * 请求权限成功。
     * 可以弹窗显示结果，也可执行具体需要的逻辑操作
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        if (requestCode == REQUEST_PERMISSION_STORAGE) {
            checkVersion();
        }

    }

    /**
     * 请求权限失败
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        if (requestCode == REQUEST_PERMISSION_STORAGE) {
            checkNum++;
            if (checkNum < 5) {
                checkVersion();
            } else {
                ShowToast.tCustom(getActivity(), "存储权限被禁止!");
            }
        }

    }

    // 忽略
    @Override
    public void myOrderNextClick() {
        goMainActivity();
    }

    // 关闭
    @Override
    public void myOrderCloseClick() {
        LogUtil.d("退出程序");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
