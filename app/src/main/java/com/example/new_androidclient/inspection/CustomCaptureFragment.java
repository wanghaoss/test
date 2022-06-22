package com.example.new_androidclient.inspection;

import android.graphics.Bitmap;

import com.google.zxing.Result;
import com.uuzuche.lib_zxing.activity.CaptureFragment;

import java.lang.reflect.Field;

/**
 * CustomCaptureActivity 和 CustomCaptureFragment两个文件是因为巡检扫描二维码时会有声音提示，
 * 为了不把所有二维码识别的文件都下载下来，就创建了两个文件
 */
public class CustomCaptureFragment extends CaptureFragment {
    @Override
    public void handleDecode(Result result, Bitmap barcode) {
        try {
            Field playBeep = CaptureFragment.class.getDeclaredField("playBeep");
            playBeep.setAccessible(true);
            playBeep.set(this, false);

            Field vibrate = CaptureFragment.class.getDeclaredField("vibrate");
            vibrate.setAccessible(true);
            vibrate.set(this, false);

        } catch (Exception e) {
        }
        super.handleDecode(result, barcode);
    }
}