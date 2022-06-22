package com.etuo.kucun.ui.activity;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseActivity;
import com.etuo.kucun.utils.AdFilterTool;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.StringUtils;
import com.lzy.okgo.OkGo;

/**
 * ================================================
 *
 * @author :Administrator
 * @version :
 * @date :2019/2/22/15:57
 * @ProjectNameDescribe :WebServiceViewActivity  (怀疑废弃,未看到别处调用)
 * 修订历史：
 * ================================================
 */

public class WebServiceViewActivity extends BaseActivity implements View.OnClickListener {

    private ProgressDialog dialog;
    private WebView webView;
    /**
     * 头部状态栏
     **/
    private RelativeLayout rl_general_header_bar;
    /**
     * 返回键
     **/
    private RelativeLayout btnPrev;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private String LoadingUrl;
    private String intent_W;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_web_service);

        initView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void initView() {
        title = getIntent().getStringExtra("title");
        LoadingUrl = getIntent().getStringExtra("LoadingUrl");
        LogUtil.d("LoadingUrl : " + LoadingUrl);
        webView = (WebView) findViewById(R.id.pubic_web_2);
        rl_general_header_bar = (RelativeLayout) findViewById(R.id.rl_general_header_bar);
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        btnPrev = (RelativeLayout) findViewById(R.id.btn_prev);
        btnPrev.setOnClickListener(this);
        initDialog();
        tv_header_title.setText(title+"");
        if (StringUtils.stringThreeNotEmpty(LoadingUrl)) {
            webView.loadUrl(LoadingUrl);
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setSupportMultipleWindows(true);
        settings.setSupportZoom(true);
        //优先不加载缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置支持DomStorage,必须设置
        settings.setDomStorageEnabled(true);
        // 不支持缩放
        settings.setBuiltInZoomControls(false);
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
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


                LogUtil.d("back _ url : " + url);

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
                    dialog = new ProgressDialog(WebServiceViewActivity.this);
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


                LogUtil.d("title : " + view.getTitle());
                CurrentUrl = url;

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
                if (!AdFilterTool.hasAd(WebServiceViewActivity.this, url)) {
                    return super.shouldInterceptRequest(view, url);
                } else {

                    LogUtil.d("debug_url", "ddddddddd找到 广告链接 直接屏蔽 " + url);
                    return new WebResourceResponse(null, null, null);
                }


            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //关闭
            case R.id.btn_prev:
                finish();
                break;
            default:
                break;
        }
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
        dialog = new ProgressDialog(WebServiceViewActivity.this);
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


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //拦截返回键
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //判断触摸UP事件才会进行返回事件处理
            if (event.getAction() == KeyEvent.ACTION_UP) {
                onBackPressed();
            }
            //只要是返回事件，直接返回true，表示消费掉
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (webView != null) {


            //如果h5页面可能返回，跳转到上个页面
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                //不能返回上个页面，直接finish当前Activity
                finish();
            }
        } else {
            finish();
        }
    }


}
