package com.etuo.kucun.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etuo.kucun.FrameworkApp;
import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseHeaderBarActivity;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.callback.JsonCallback;
import com.etuo.kucun.model.VersionModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.utils.AppUtils;
import com.etuo.kucun.utils.DataCleanManager;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.NetUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.StringUtils;
import com.etuo.kucun.utils.UpVersionUtil;
import com.etuo.kucun.utils.UrlTools;
import com.etuo.kucun.utils.permission.EasyPermissions;
import com.lzy.okgo.OkGo;
import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.etuo.kucun.utils.UrlTools.LOG_OUT;
import static com.etuo.kucun.utils.UrlTools.UPDATEVERSION;


/**
 * ================================================
 *
 * @author :yhn
 * @version :
 * @date :2018/8/1
 * @ProjectNameDescribe :设置页面
 * 修订历史：
 * ================================================
 */
public class SettingActivity extends BaseHeaderBarActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.setting_update_pwdRL)
    RelativeLayout mSettingUpdatePwdRL;
    @BindView(R.id.tv_cache_num)
    TextView mTvCacheNum;
    @BindView(R.id.rl_clear_cache)
    RelativeLayout mRlClearCache;
    @BindView(R.id.setting_update_new_version)
    RelativeLayout mSettingUpdateNewVersion;
    @BindView(R.id.exit_login_btn)
    Button mExitLoginBtn;
    @BindView(R.id.tv_version_num)
    TextView mTvVersionNum;
    @BindView(R.id.rl_exit_login)
    RelativeLayout mRlExitLogin;
    @BindView(R.id.fingerprint_openTB)
    CheckBox mFingerprintOpenTB;
    @BindView(R.id.password_openTB)
    CheckBox mPasswordOpenTB;
    @BindView(R.id.setting_update_gesture_pwdRL)
    RelativeLayout mSettingUpdateGesturePwdRL;

    /**
     * 检测存储的次数
     **/
    private int checkNum = 0;
    private VersionModel versionMode;
    /**
     * 申请存储权限
     **/
    private static final int REQUEST_PERMISSION_STORAGE = 0x01;

    private ProgressDialog cdialog;


    private FingerprintIdentify mFingerprintIdentify;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setHeaderTitle("设置");
//        calculateCacheSize();
        initView();
        initData();
        isCheck();

    }
    private Activity getActivity() {
        return SettingActivity.this;
    }

    @OnClick({R.id.setting_update_pwdRL, R.id.rl_clear_cache, R.id.setting_update_new_version, R.id.rl_exit_login,
            R.id.password_openTB, R.id.setting_update_gesture_pwdRL})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            // 修改密码
            case R.id.setting_update_pwdRL:
                    startActivity(new Intent(this, UpdatePasswordActivity.class));
                break;
            //清除缓存
            case R.id.rl_clear_cache:
                showDialog("正在清理");
                clearCache();
                break;
            // 检测新版本
            case R.id.setting_update_new_version:
                    getVersionData();
                break;
            //退出
            case R.id.rl_exit_login:
                    final Dialog dialog = new Dialog(getActivity(), R.style.MyDialogStyle);
                    dialog.setContentView(R.layout.dialog_exit);
                    dialog.findViewById(R.id.dialog_cancleRL).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode,
                                             KeyEvent event) {
                            return false;
                        }
                    });
                    dialog.findViewById(R.id.dialog_goRL).setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    loginOut();
                                }
                            });
                    dialog.show();
                break;
            case R.id.password_openTB:
                    if (mPasswordOpenTB.isChecked()) {
                        intent = new Intent(SettingActivity.this, GestureSetActivity.class);
                        startActivityForResult(intent, ExtraConfig.RequestCode.REQUEST_CODE_FOR_GESTURE);
                    } else {
                        intent = new Intent(SettingActivity.this, GestureCheckActivity.class);
                        intent.putExtra(ExtraConfig.IntentType.KET_GESTURE_FLAG, 2);
                        startActivityForResult(intent, ExtraConfig.RequestCode.REQUEST_CODE_FOR_GESTURE_DOWN);
                    }
                break;
            // 修改 手势密码
            case R.id.setting_update_gesture_pwdRL:
                    intent = new Intent(this, GestureCheckActivity.class);
                    intent.putExtra(ExtraConfig.IntentType.KET_GESTURE_FLAG, 1);
                    startActivity(intent);
                break;
            default:
                break;

        }
    }


    @SuppressLint("SetTextI18n")
    private void initView() {
        mTvVersionNum.setText("v" + AppUtils.getVersionName(this));
        mFingerprintIdentify = new FingerprintIdentify(this);
    }

    private void initData() {
        if (PreferenceCache.isGesture()) {
            mPasswordOpenTB.setChecked(true);
            mSettingUpdateGesturePwdRL.setVisibility(View.VISIBLE);
        } else {
            mPasswordOpenTB.setChecked(false);
            mSettingUpdateGesturePwdRL.setVisibility(View.GONE);
        }
        if (PreferenceCache.getFingerFlg()) {
            mFingerprintOpenTB.setChecked(true);
        } else {
            mFingerprintOpenTB.setChecked(false);
        }
    }

    /**
     * 消息通知的开关
     */
    public void isCheck() {
        mFingerprintOpenTB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mFingerprintIdentify.isHardwareEnable()) {
                    //指纹可用
                    if (mFingerprintIdentify.isFingerprintEnable()) {
                        if (PreferenceCache.getFingerFlg()) {
                            //取消指纹
                            ShowToast.showToast("指纹验证功能已取消", SettingActivity.this);
                            PreferenceCache.putFingerFlg(false);
                            if (PreferenceCache.getFingerFlg()) {
                                mFingerprintOpenTB.setChecked(true);
                            } else {
                                mFingerprintOpenTB.setChecked(false);
                            }
                        } else {
                            //打开指纹

                            //弹出dialog，自动弹出
                            dialog = new Dialog(SettingActivity.this, R.style.Dialog);
                            dialog.setContentView(R.layout.item_dialog);
                            dialog.setCancelable(false);
                            dialog.show();
                            TextView tv = dialog.findViewById(R.id.tv_cancel);
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    PreferenceCache.putFingerFlg(false);
                                    if (PreferenceCache.getFingerFlg()) {
                                        mFingerprintOpenTB.setChecked(true);
                                    } else {
                                        mFingerprintOpenTB.setChecked(false);
                                    }
                                }
                            });
                            if (dialog.isShowing()) {
                                initVerify();
                            }
                        }
                    } else {
                        ShowToast.showToast("请先去录入指纹", SettingActivity.this);
                        if (PreferenceCache.getFingerFlg()) {
                            mFingerprintOpenTB.setChecked(true);
                        } else {
                            mFingerprintOpenTB.setChecked(false);
                        }
                    }
                } else {
                    ShowToast.showToast("您的设备不支持指纹", SettingActivity.this);
                    if (PreferenceCache.getFingerFlg()) {
                        mFingerprintOpenTB.setChecked(true);
                    } else {
                        mFingerprintOpenTB.setChecked(false);
                    }
                }
            }
        });
    }


    private void initVerify() {
        mFingerprintIdentify.startIdentify(4, new BaseFingerprint.FingerprintIdentifyListener() {
            @Override
            public void onSucceed() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                ShowToast.showToast("指纹验证功能已打开", SettingActivity.this);
                PreferenceCache.putFingerFlg(true);
                if (PreferenceCache.getFingerFlg()) {
                    mFingerprintOpenTB.setChecked(true);
                } else {
                    mFingerprintOpenTB.setChecked(false);
                }
            }

            @Override
            public void onNotMatch(int availableTimes) {
                ShowToast.showToast("验证失败，您还有" + availableTimes + "次机会", SettingActivity.this);
            }

            @Override
            public void onFailed(boolean isDeviceLocked) {
                ShowToast.showToast("验证失败,指纹密码无法打开", SettingActivity.this);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                PreferenceCache.putFingerFlg(false);
                if (PreferenceCache.getFingerFlg()) {
                    mFingerprintOpenTB.setChecked(true);
                } else {
                    mFingerprintOpenTB.setChecked(false);
                }
            }

            @Override
            public void onStartFailedByDeviceLocked() {
                ShowToast.showToast("验证失败，指纹已被锁定", SettingActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d("requestCode : " + requestCode);
        if (requestCode == ExtraConfig.RequestCode.REQUEST_CODE_FOR_GESTURE) {
            if (PreferenceCache.getGestureFlag()) {
                mPasswordOpenTB.setChecked(true);
                PreferenceCache.putIsGesture(true);
                mSettingUpdateGesturePwdRL.setVisibility(View.VISIBLE);

            } else {
                mPasswordOpenTB.setChecked(false);
                PreferenceCache.putIsGesture(false);
                mSettingUpdateGesturePwdRL.setVisibility(View.GONE);
            }
        } else if (requestCode == ExtraConfig.RequestCode.REQUEST_CODE_FOR_GESTURE_DOWN && resultCode == RESULT_OK) {
            if (PreferenceCache.getGestureFlag()) {
                mPasswordOpenTB.setChecked(false);
                PreferenceCache.putIsGesture(false);
                mSettingUpdateGesturePwdRL.setVisibility(View.GONE);
            }

        } else {
            if (PreferenceCache.getGestureFlag() && PreferenceCache.isGesture()) {
                mPasswordOpenTB.setChecked(true);
                PreferenceCache.putIsGesture(true);
                mSettingUpdateGesturePwdRL.setVisibility(View.VISIBLE);
            } else {
                mPasswordOpenTB.setChecked(false);
                PreferenceCache.putIsGesture(false);
                mSettingUpdateGesturePwdRL.setVisibility(View.GONE);
            }
        }


    }

    /******************************清除缓存 start********************************/


    private void showDialog(String msg) {


        cdialog = new ProgressDialog(getActivity());
        cdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cdialog.setCanceledOnTouchOutside(false);
        cdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        cdialog.setMessage(msg);

        if (cdialog != null && !cdialog.isShowing()) {
            cdialog.show();
        }

    }

    /**
     * 查看 缓存
     */
    public void calculateCacheSize() {

        CaculateCacheSizeRunnable runnable = new CaculateCacheSizeRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
    }


    private class CaculateCacheSizeRunnable implements Runnable {

        @Override
        public void run() {

            try {

                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {


                        String size = null;
                        try {
                            size = DataCleanManager.getTotalCacheSize(getActivity());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        LogUtil.d(" 缓存 : " + size);

                        mTvCacheNum.setText(size);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 清除缓存
     */

    private void clearCache() {
        ClearCacheRunnable runnable = new ClearCacheRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
    }


    public class ClearCacheRunnable implements Runnable {

        @Override
        public void run() {

            DataCleanManager.clearAllCache(getActivity());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    //已在主线程中，可以更新UI
                    calculateCacheSize();

                    if (cdialog != null && cdialog.isShowing()) {

                        LogUtil.d("qqqqqqqqq : ");
                        cdialog.dismiss();
                    }
                    ShowToast.tCustom(getActivity(), "清除成功");
                }
            });
        }
    }

    /***************************清除缓存 end*****************************/
    /**
     * 退出登录
     **/
    public void loginOut() {
        OkGo.get(UrlTools.getInterfaceUrl(LOG_OUT))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .execute(new JsonCallback<LzyResponse<VersionModel>>() {
                    @Override
                    public void onSuccess(final LzyResponse<VersionModel> responseData, Call call, Response response) {
                        if ("401".equals(responseData.status)) {
                            PreferenceCache.clearAllUserPwd();
                            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                            //关掉所要到的界面中间的activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        PreferenceCache.clearAllUserPwd();
                        LogUtil.d("关闭指纹手势等" + PreferenceCache.getGestureFlag() + "" + PreferenceCache.isGesture() + PreferenceCache.getFingerFlg() + "");
                        //关掉所要到的界面中间的activity在异常的回调JSONCALLBACK有处理
                        ShowToast.toastTime(getActivity(), StringUtils.NullToStr(e.getMessage()), 5);
                    }
                });

    }

    /*********************************更新**************************/
    /**
     * 获取版本信息
     **/
    public void getVersionData() {
        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }
        try {
            OkGo.get(UrlTools.getInterfaceUrl(UPDATEVERSION))
                    .tag(this)
                    .execute(new JsonCallback<LzyResponse<VersionModel>>() {
                        @Override
                        public void onSuccess(final LzyResponse<VersionModel> responseData, Call call, Response response) {
                            versionMode = responseData.bean;
                            int versioncode = AppUtils.getVersionCode(FrameworkApp.getAppContext());
                            int getVersionCode = Integer.parseInt(!TextUtils.isEmpty(versionMode.getAndroidCode()) ? versionMode.getAndroidCode() : "0");
                            // 更新版本
                            if (getVersionCode > versioncode) {
                                checkVersion();
                            } else {
                                ShowToast.showToast("已是最新版本!", getActivity());
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            ShowToast.toastTime(getActivity(), e.getMessage(), 5);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测存储权限和版本号
     */
    private void checkVersion() {

        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new UpVersionUtil(getActivity()).showVersionDialog(versionMode);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
