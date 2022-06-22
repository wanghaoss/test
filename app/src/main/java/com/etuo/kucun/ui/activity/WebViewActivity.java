package com.etuo.kucun.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etuo.kucun.FrameworkApp;
import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseActivity;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.utils.AdFilterTool;
import com.etuo.kucun.utils.AppUtils;
import com.etuo.kucun.utils.CRequestUtil;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.StatusBarUtil;
import com.etuo.kucun.utils.StringUtil;
import com.etuo.kucun.utils.StringUtils;
import com.lzy.okgo.OkGo;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

/**
 * ================================================
 *
 * @author :Administrator
 * @version :
 * @date :2019/2/22/15:17
 * @ProjectNameDescribe :WebViewActivity H5网页界面
 * 修订历史：
 * ================================================
 */
public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 申请存储权限
     **/
    private static final int REQUEST_PERMISSION_STORAGE = 0x01;
    /**
     * 权限申请自定义码 (相机)
     **/
    private final static int GET_PERMISSION_REQUEST = 100;
    /**
     * 权限申请自定义码 (相机),扫描银行卡
     **/
    private final static int GET_PERMISSION_REQUEST_BANK = 101;
    /**
     * 接收扫描银行卡的结果
     **/
    public final static int GET_BANK_CARD = 0x012;
    /**
     * 进程弹窗
     **/
    private ProgressDialog dialog;
    /**
     * 调用相机
     **/
    private String TYPE_PHOTO = "type_photo";
    /**
     * 银行卡扫描
     **/
    private String TYPE_BANK = "type_bank";

    private WebView webView;
    /**
     * 头部状态栏
     **/
    private RelativeLayout rl_general_header_bar;
    /**
     * 返回键
     **/
    private RelativeLayout btnPrev;
    /**
     * 扫描二维码
     **/
    private final static int SCANNIN_GREQUEST_CODE = 1;
    /**
     * 扫描二维码成功提交后,刷新web
     **/
    private final static int SCANNIN_GREQUEST_CODE_Refresh = 201;
    private String LoadingUrl;
    private String intent_W;
    private String intent_From;
    /**
     * 入库计划的stockType
     **/
    private String stockType;
    /**
     * 入库计划的托盘型号
     **/
    private String palletModel;
    /**
     * 当前界面url
     **/
    private String CurrentUrl;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private ValueCallback<Uri> mUploadCallbackBelow;
    private static final int REQUEST_CODE = 0x1234;
    private Uri imageUri;
    /**
     * 头部标题
     **/
    private TextView tv_header_title;


    private String title;


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    hideLoadingDialog();
                    break;
                case 1:
                    hideLoadingDialog();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_web);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_blue), 0);
        checkSDCardPermission();
        initView();
    }

    /**
     * 检查SD卡权限
     */
    protected void checkSDCardPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
        }
    }

    /**
     * 获取传来的intent值
     **/
    private void getIntentExtra() {
        LoadingUrl = getIntent().getStringExtra("LoadingUrl");
        intent_W = getIntent().getStringExtra(ExtraConfig.TypeCode.FROM_INT_LOGIN);
        intent_From = getIntent().getStringExtra(ExtraConfig.TypeCode.FROM_INTTENT);
        title = getIntent().getStringExtra("title");
        LogUtil.d("LoadingUrl : " + LoadingUrl);
        LogUtil.d("intent_From : " + intent_From);
    }


    @SuppressLint("SetJavaScriptEnabled")
    protected void initView() {
        getIntentExtra();


        webView = (WebView) findViewById(R.id.pubic_web_2);
        rl_general_header_bar = (RelativeLayout) findViewById(R.id.rl_general_header_bar);
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        btnPrev = (RelativeLayout) findViewById(R.id.btn_prev);

        if (!StringUtil.isEmpty(title)){
            rl_general_header_bar.setVisibility(View.VISIBLE);
            tv_header_title.setText(title+"");
        }else {
            rl_general_header_bar.setVisibility(View.GONE);
        }
        btnPrev.setOnClickListener(this);
        initDialog();
        if (StringUtils.stringThreeNotEmpty(LoadingUrl)) {
            webView.loadUrl(LoadingUrl);
        }
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 支持通过JS打开新窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setSupportMultipleWindows(true);
        settings.setSupportZoom(true);
        //优先不加载缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //settings.setAppCacheEnabled(false);
        //必须进行这个设置 打开DOM存储API
        settings.setDomStorageEnabled(true);
        // settings.setAppCachePath(this.getCacheDir().getAbsolutePath());
        // settings.setDatabaseEnabled(true);
        // settings.setDatabasePath(this.getCacheDir().getAbsolutePath());
        // 不支持缩放
        settings.setBuiltInZoomControls(false);
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        // {
        // settings.setDisplayZoomControls(false); // 不显示缩放按钮
        // }
        // 无限缩放
        settings.setUseWideViewPort(true);
        // 初始加载时，是web页面自适应屏幕
        settings.setLoadWithOverviewMode(true);

        int screenDensity = getResources().getDisplayMetrics().densityDpi;
        WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
        switch (screenDensity) {
            case DisplayMetrics.DENSITY_LOW:
                zoomDensity = WebSettings.ZoomDensity.CLOSE;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                zoomDensity = WebSettings.ZoomDensity.FAR;
                break;
            default:
                break;
        }
        settings.setDefaultZoom(zoomDensity);
        webView.requestFocus();
        webView.requestFocusFromTouch();
        webView.addJavascriptInterface(new JavaScriptClass(), "javaMethod");
        webView.setWebChromeClient(new WebChromeClient() {
            /**
             * 8(Android 2.2) <= API <= 10(Android 2.3)回调此方法
             */
            private void openFileChooser(ValueCallback<Uri> uploadMsg) {
                Log.e("WangJ", "运行方法 openFileChooser-1");
                // (2)该方法回调时说明版本API < 21，此时将结果赋值给 mUploadCallbackBelow，使之 != null
                mUploadCallbackBelow = uploadMsg;
                getPermissions(TYPE_PHOTO);
            }

            /**
             * 11(Android 3.0) <= API <= 15(Android 4.0.3)回调此方法
             */
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                Log.e("WangJ", "运行方法 openFileChooser-2 (acceptType: " + acceptType + ")");
                // 这里我们就不区分input的参数了，直接用拍照
                openFileChooser(uploadMsg);
            }

            /**
             * 16(Android 4.1.2) <= API <= 20(Android 4.4W.2)回调此方法
             */
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.e("WangJ", "运行方法 openFileChooser-3 (acceptType: " + acceptType + "; capture: " + capture + ")");
                // 这里我们就不区分input的参数了，直接用拍照
                openFileChooser(uploadMsg);
            }

            /**
             * API >= 21(Android 5.0.1)回调此方法
             */
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                Log.e("WangJ", "运行方法 onShowFileChooser");
                // (1)该方法回调时说明版本API >= 21，此时将结果赋值给 mUploadCallbackAboveL，使之 != null
                mUploadCallbackAboveL = filePathCallback;
                //takePhoto();
                getPermissions(TYPE_PHOTO);
                return true;
            }
        });


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //首页
                if (url.contains("index.html")) {
                    finish();
                    //出库
                } else if (url.contains("/wicket/page")) {
                    ShowToast.tCustom(WebViewActivity.this, "系统繁忙,请稍后重新登录");
                    PreferenceCache.putToken("");
                    Intent intent = new Intent(WebViewActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url,
                                               boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (dialog == null) {
                    dialog = new ProgressDialog(WebViewActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setMessage("加载中...");
                }
                if (!isFinishing()) {
                    dialog.show();
                }
                handler.sendEmptyMessageDelayed(0, 8000);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LogUtil.d("title : " + view.getTitle() + " urlinfo : ");
                CurrentUrl = url;
//                    if (!TextUtils.isEmpty(intent_From) && intent_From.equals(ExtraConfig.TypeCode.IMAGE_INTENT)){
//                    // 设置头标题
//                    tv_header_title.setText("易托");
//                    rl_general_header_bar.setVisibility(View.VISIBLE);
//
//                }else {
//                    rl_general_header_bar.setVisibility(View.GONE);
//                }
                hideLoadingDialog();
            }

            /**
             * 过滤广告 代码
             * @param view
             * @param url
             * @return
             */
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                url = url.toLowerCase();
                if (!AdFilterTool.hasAd(WebViewActivity.this, url)) {
                    return super.shouldInterceptRequest(view, url);
                } else {
                    LogUtil.d("debug_url", "找到 广告链接 直接屏蔽 " + url);
                    return new WebResourceResponse(null, null, null);
                }

            }
        });
    }


    /**
     * 获取相机权限
     */
    private void getPermissions(String type) {
        LogUtil.d("type : " + type);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //具有权限
                takePhoto();
            } else {
                //不具有获取权限，需要进行权限申请
                if (type.equals(TYPE_PHOTO)) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA}, GET_PERMISSION_REQUEST);
                }
            }
        } else {
            if (type.equals(TYPE_PHOTO)) {
                takePhoto();
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //关闭
            case R.id.btn_prev:
                if (!TextUtils.isEmpty(intent_From) && intent_From.equals(ExtraConfig.TypeCode.IMAGE_INTENT)) {
                    finish();
                }
                break;
            default:
                break;
        }
    }


    /******************调用相册**调用相机************************/

    private void takePhoto() {
        // 指定拍照存储位置的方式调起相机
        String filePath = Environment.getExternalStorageDirectory() + File.separator
                + Environment.DIRECTORY_PICTURES + File.separator;
        String fileName = "IMG_" + DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        imageUri = Uri.fromFile(new File(filePath + fileName));

//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, REQUEST_CODE);


        // 选择图片（不包括相机拍照）,则不用成功后发刷新图库的广播
//        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//        i.addCategory(Intent.CATEGORY_OPENABLE);
//        i.setType("image/*");
//        startActivityForResult(Intent.createChooser(i, "Image Chooser"), REQUEST_CODE);

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        Intent photo = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent chooserIntent = Intent.createChooser(photo, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});

        startActivityForResult(chooserIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d("扫码结果 111: " + " requestCode : " + requestCode + " resultCode : " + resultCode);
        switch (requestCode) {
            case REQUEST_CODE:
                // 经过上边(1)、(2)两个赋值操作，此处即可根据其值是否为空来决定采用哪种处理方法
                if (mUploadCallbackBelow != null) {
                    chooseBelow(resultCode, data);
                } else if (mUploadCallbackAboveL != null) {
                    chooseAbove(resultCode, data);
                } else {
                    Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
                }
                break;
            // 刷新界面
            case SCANNIN_GREQUEST_CODE_Refresh:
                if (resultCode == RESULT_OK) {
                    webView.reload();
                    //刷新
                    LogUtil.d("刷新 : CurrentUrl:  " + CurrentUrl);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Android API < 21(Android 5.0)版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private void chooseBelow(int resultCode, Intent data) {
        Log.e("WangJ", "返回调用方法--chooseBelow");

        if (RESULT_OK == resultCode) {
            updatePhotos();

            if (data != null) {
                // 这里是针对文件路径处理
                Uri uri = data.getData();
                if (uri != null) {
                    Log.e("WangJ", "系统返回URI：" + uri.toString());
                    mUploadCallbackBelow.onReceiveValue(uri);
                } else {
                    mUploadCallbackBelow.onReceiveValue(null);
                }
            } else {
                // 以指定图像存储路径的方式调起相机，成功后返回data为空
                Log.e("WangJ", "自定义结果：" + imageUri.toString());
                mUploadCallbackBelow.onReceiveValue(imageUri);
            }
        } else {
            mUploadCallbackBelow.onReceiveValue(null);
        }
        mUploadCallbackBelow = null;
    }

    /**
     * Android API >= 21(Android 5.0) 版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private void chooseAbove(int resultCode, Intent data) {
        Log.e("WangJ", "返回调用方法--chooseAbove");

        if (RESULT_OK == resultCode) {
            updatePhotos();

            if (data != null) {
                // 这里是针对从文件中选图片的处理
                Uri[] results;
                Uri uriData = data.getData();
                if (uriData != null) {
                    results = new Uri[]{uriData};
                    for (Uri uri : results) {
                        Log.e("WangJ", "系统返回URI：" + uri.toString());
                    }
                    mUploadCallbackAboveL.onReceiveValue(results);
                } else {
                    mUploadCallbackAboveL.onReceiveValue(null);
                }
            } else {
                Log.e("WangJ", "自定义结果：" + imageUri.toString());
                mUploadCallbackAboveL.onReceiveValue(new Uri[]{imageUri});
            }
        } else {
            mUploadCallbackAboveL.onReceiveValue(null);
        }
        mUploadCallbackAboveL = null;
    }

    private void updatePhotos() {
        // 该广播即使多发（即选取照片成功时也发送）也没有关系，只是唤醒系统刷新媒体文件
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(imageUri);
        sendBroadcast(intent);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoadingDialog();
        OkGo.getInstance().cancelTag(this);
    }


    private void initDialog() {
        dialog = new ProgressDialog(WebViewActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("加载中...");
    }

    /**
     * 隐藏进度条
     */
    protected void hideLoadingDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 发送短信(掉起发短信页面)
     *
     * @param tel     电话号码
     * @param content 短息内容
     */
    private void sendMessage(String tel, String content) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(tel)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + tel));
            intent.putExtra("sms_body", content);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {

        LogUtil.d("bbbb : " + "back ");
        if (webView != null) {

//            if (check_url_is_yeePay()){
//                finish();
//                return;
//            }
            //如果h5页面可能返回，跳转到上个页面
            if (webView.canGoBack()) {
                LogUtil.d("bbbb : " + "back  true ");
                webView.goBack();
            } else {
                //不能返回上个页面，直接finish当前Activity
                finish();
            }
        } else {
            finish();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //申请存储权限
            case REQUEST_PERMISSION_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LogUtil.d("获取权限");
                } else {
                    ShowToast.tCustom(WebViewActivity.this, "权限被禁止！");
                }
                break;
            //权限申请自定义码(相机)
            case GET_PERMISSION_REQUEST:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    getPermissions(TYPE_PHOTO);
                } else {
                    // 被禁止授权
                    Toast.makeText(this, "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
                }
                LogUtil.d("MQL", "onRequestPermissionsResult");
                break;
            //权限申请自定义码 (相机),扫描银行卡
            case GET_PERMISSION_REQUEST_BANK:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    getPermissions(TYPE_BANK);
                } else {
                    // 被禁止授权
                    Toast.makeText(this, "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
                }

                break;
            default:
                break;
        }
    }


    /**
     * 交互方法
     **/
    private class JavaScriptClass {


//        @JavascriptInterface
//        public void back() {
//            finish();
//        }
//

        /**
         * 入库 添加
         *
         * @param orderId
         * @param recordId
         * @param companyId
         */
        @JavascriptInterface
        public void inventory(String orderId, String recordId, String companyId) {

            LogUtil.d("checkBillId : " + orderId + "   recordId  : " + recordId + "  companyId " + companyId);
            Intent intent = new Intent(WebViewActivity.this, NewMipcaActivityCapture.class);
//            if (intent_From.equals("web_intent_in")) {
            intent.putExtra(ExtraConfig.TypeCode.FROM_INTTENT, ExtraConfig.TypeCode.WEB_INTENT_BY_IN);
//            } else if (intent_From.equals("web_intent_shou")) {
//                intent.putExtra(ExtraConfig.TypeCode.FROM_INTTENT, ExtraConfig.TypeCode.WEB_INTENT_BY_SHOU);
//            }
            //SCAN_QUERY_TYPE
            intent.putExtra(ExtraConfig.TypeCode.SCAN_QUERY_TYPE, "1");
            intent.putExtra(ExtraConfig.TypeCode.SCAN_COMPANYID, companyId);
            intent.putExtra(ExtraConfig.TypeCode.SCAN_OREDER_ID, orderId);
            intent.putExtra(ExtraConfig.TypeCode.SCAN_RECORD_ID, recordId);
            intent.putExtra(ExtraConfig.TypeCode.INTENT_STORK_TYPE, stockType);
            intent.putExtra(ExtraConfig.TypeCode.INTENT_PALLET_MODEL, palletModel);
            startActivityForResult(intent, SCANNIN_GREQUEST_CODE_Refresh);
        }



    }





    @Override
    protected void onResume() {
        super.onResume();
    }

}
