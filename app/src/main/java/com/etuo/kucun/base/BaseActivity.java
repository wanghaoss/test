package com.etuo.kucun.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.etuo.kucun.R;
import com.etuo.kucun.manager.ActivityStackManager;
import com.etuo.kucun.utils.DeviceUtil;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.NoFastClickUtils;
import com.etuo.kucun.utils.StatusBarUtil;
import com.etuo.kucun.widget.CustomClipHintLoading;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * ================================================
 *
 * @author :haining.yang
 * @version :
 * @date :2018/7/2/16:02
 * @ProjectNameDescribe :BaseActivity共通界面
 * 修订历史：
 * ================================================
 */
public class BaseActivity extends FragmentActivity {
    protected Context mContext;
    protected final String TAG = this.getClass().getSimpleName();
    private CustomClipHintLoading dialog;// 加载

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_blue), 0);
        ActivityStackManager.getInstance().pushOneActivity(this);
        mContext = this;

//        isHaveFRID();

    }


    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getInstance().popOneActivity(this);
    }

    public static View getContentView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION,
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
        if (Build.VERSION.SDK_INT >= 23
                && getApplicationInfo().targetSdkVersion >= 23) {
            if (isNeedCheck) {
                checkPermissions(needPermissions);
            }
        }
    }

    /**
     * @param permissions
     * @since 2.5.0
     */
    private void checkPermissions(String... permissions) {
        try {
            if (Build.VERSION.SDK_INT >= 23
                    && getApplicationInfo().targetSdkVersion >= 23) {
                List<String> needRequestPermissionList = findDeniedPermissions(permissions);
                if (null != needRequestPermissionList
                        && needRequestPermissionList.size() > 0) {
                    String[] array = needRequestPermissionList.toArray(new String[needRequestPermissionList.size()]);
                    Method method = getClass().getMethod("requestPermissions", String[].class,
                            int.class);

                    method.invoke(this, array, PERMISSON_REQUESTCODE);
                }
            }
        } catch (Throwable ignored) {
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
        List<String> needRequestPermissionList = new ArrayList<String>();
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M
                && getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.M) {
            try {
                for (String perm : permissions) {
                    Method checkSelfMethod = getClass().getMethod("checkSelfPermission", String.class);
                    Method shouldShowRequestPermissionRationaleMethod = getClass().getMethod("shouldShowRequestPermissionRationale",
                            String.class);
                    if ((Integer) checkSelfMethod.invoke(this, perm) != PackageManager.PERMISSION_GRANTED
                            || (Boolean) shouldShowRequestPermissionRationaleMethod.invoke(this, perm)) {
                        needRequestPermissionList.add(perm);
                    }
                }
            } catch (Throwable ignored) {

            }
        }
        return needRequestPermissionList;
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

    @Override
    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
    /**
     * 1点击空白位置 隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
            // 判断连续点击事件时间差
            if (NoFastClickUtils.isFastClick()){
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 2根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 3获取InputMethodManager，隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(im).hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 显示进度条
     */
    protected void showLoadingDialog(String hint) {
        if (dialog == null) {
            dialog = new CustomClipHintLoading(this,hint);
        }
        if (!this.isFinishing() && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 隐藏进度条
     */
    protected void hideLoadingDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    protected boolean isLoadingDialogShowing(){
        if (dialog != null) {
            return dialog.isShowing();
        } else {
            return false;
        }
    }


}
