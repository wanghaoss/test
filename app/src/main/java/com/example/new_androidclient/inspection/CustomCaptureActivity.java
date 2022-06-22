package com.example.new_androidclient.inspection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;


/**
 * CustomCaptureActivity 和 CustomCaptureFragment两个文件是因为巡检扫描二维码时会有声音提示，
 * 为了不把所有二维码识别的文件都下载下来，就创建了两个文件
 */
public class CustomCaptureActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        CustomCaptureFragment captureFragment = new CustomCaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            CustomCaptureActivity.this.setResult(RESULT_OK, resultIntent);
            CustomCaptureActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            CustomCaptureActivity.this.setResult(RESULT_OK, resultIntent);
            CustomCaptureActivity.this.finish();
        }
    };
}