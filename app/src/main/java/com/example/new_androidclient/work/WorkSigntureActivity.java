package com.example.new_androidclient.work;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityWorkSigntureBinding;
import com.example.new_androidclient.wait_to_handle.bean.DefaultPicBean;

import java.util.List;

/**
 * zq
 * 查看作业票
 * 查看待办列表-签字-查看作业票审核审批
 */
@Route(path = RouteString.WorkSigntureActivity)
public class WorkSigntureActivity extends BaseActivity {
    private ActivityWorkSigntureBinding binding;
    private Listener listener = new Listener();
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;

    @Autowired
    int applicationId;

//    @Autowired
//    String sign; //03+04审批 05审核 08+09+10关闭签字 11取消签字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_signture);
        binding.setListener(listener);
        getPdfUrl();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("正在生成作业证，请稍等");
//        if (sign.equals("03") || sign.equals("04")) { //需要审核
//            binding.agree.setText("审核");
//        } else {    //需要审批
//            binding.agree.setText("审批");
//        }

        binding.title.getLinearLayout_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getPdfUrl() {
        RetrofitUtil.getApi().findDocListByBusinessIdAndType(applicationId, "74", "DH")
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<DefaultPicBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<DefaultPicBean> bean) {
                        if (bean != null && bean.size() != 0) {
                            setWebView(bean.get(0).getDocUrl());
                        } else {
                            ToastUtil.show(mContext, "生成作业票异常");
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


    private void setWebView(String url) {
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setSupportZoom(true);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setDisplayZoomControls(true);
        binding.webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        binding.webView.loadUrl("file:///android_asset/pdf.html?" + url);
        binding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                progressDialog.dismiss();
            }

        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.webView.getSettings().setJavaScriptEnabled(false);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void signName(int flag, String msg) {  //审核 审批
        RetrofitUtil.getApi().actSubmit(applicationId, "DH", flag, msg)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(String bean) {
                        ToastUtil.show(mContext, "处理成功");
                        //跳转许可会签页面
//                        ARouter.getInstance().build(RouteString.WorkSigntureActivity)
//                                .withInt("applicationId", applicationId)
//                                .withString("sign", status)
//                                .navigation();

                        finish();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


    public class Listener {
        public void sign() {
            alertDialog = new AlertDialog.Builder(WorkSigntureActivity.this).setPositiveButton("同意", null).setNegativeButton("拒绝", null).create();
            alertDialog.setTitle("请填写意见");

            Window win = alertDialog.getWindow();
            win.getDecorView().setPadding(10, 20, 10, 20);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);

            View view = getLayoutInflater().inflate(R.layout.hazard_plan_sign_dialog, null);
            final EditText msg_edit = view.findViewById(R.id.hazard_plan_sign_dialog_edittext);
            alertDialog.setView(view);//设置login_layout为对话提示框
            alertDialog.setCancelable(true);//设置为不可取消
            alertDialog.setOnShowListener(mDialog -> {
                Button positionButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                positionButton.setOnClickListener(v -> {
                    signName(1, msg_edit.getText().toString().trim());
                });
                negativeButton.setOnClickListener(v -> {
                    if (msg_edit.getText().toString().trim().isEmpty()) {
                        ToastUtil.show(mContext, "请填写意见");
                    } else {
                        signName(0, msg_edit.getText().toString().trim());
                    }
                });
            });
            alertDialog.show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return false;
    }
}
