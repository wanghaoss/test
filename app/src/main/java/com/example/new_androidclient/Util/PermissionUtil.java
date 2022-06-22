package com.example.new_androidclient.Util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.atomic.AtomicBoolean;

@SuppressLint("CheckResult")
@SuppressWarnings("ResultOfMethodCallIgnored")
public class PermissionUtil {
    public interface permissionListener {
        void listener();
    }

    public static permissionListener permissionListener;

    public static void checkLoginPermission(FragmentActivity activity, permissionListener permissionListener) {
        PermissionUtil.permissionListener = permissionListener;
        new RxPermissions(activity)
                .request(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .subscribe(granted -> {
                    permissionListener.listener();
//                    if (granted) {
//                        permissionListener.listener();
//
//                    } else {
//                        Toast.makeText(activity, "请授权", Toast.LENGTH_SHORT).show();
//
//                    }
                });
    }

    //InspectionTakePhotoActivity调用的
    public static void checkCameraAndReadPermission(FragmentActivity activity) {
        new RxPermissions(activity)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (!granted) {
                        ToastUtil.show(activity, "请授权");
                        activity.finish();
                    }
                });
    }

    //AreaDistinguishActivity调用的
    public static boolean checkCameraPermission(FragmentActivity activity) {
        AtomicBoolean per = new AtomicBoolean(false);
        new RxPermissions(activity)
                .request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        per.set(true);
                    } else {
                        ToastUtil.show(activity, "请授权");
                        per.set(false);
                    }
                });
        return per.get();
    }

    public static boolean checkBlueToothPermission(FragmentActivity activity) {
        AtomicBoolean per = new AtomicBoolean(false);
        new RxPermissions(activity)
                .request(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        per.set(true);
                    } else {
                        per.set(false);
                    }
                });
        return per.get();
    }

    public static boolean checkHazardPlanUploadFilePermission(FragmentActivity activity) {
        AtomicBoolean per = new AtomicBoolean(false);
        new RxPermissions(activity)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        per.set(true);
                    } else {
                        per.set(false);
                    }
                });
        return per.get();
    }
}
