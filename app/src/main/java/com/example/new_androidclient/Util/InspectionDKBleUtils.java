package com.example.new_androidclient.Util;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ronds.eam.lib_sensor.BleClient;

public class InspectionDKBleUtils {
  public static boolean checkBle(@NonNull Context context) {
    if (!BleClient.getInstance().isSupportBle()) {
      Toast.makeText(context, "不支持 ble", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (!BleClient.getInstance().isBluetoothEnabled()) {
      Toast.makeText(context, "蓝牙未打开", Toast.LENGTH_SHORT).show();
    }
    if (!BleClient.getInstance().isLocationEnable(context)) {
      Toast.makeText(context, "定位未打开", Toast.LENGTH_SHORT).show();
    }
    return checkBlePermission(context);
  }

  @TargetApi(23)
  private static boolean checkBlePermission(@NonNull Context context) {
    if (context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        || context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      Toast.makeText(context, "没有定位权限", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (context.checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
        || context.checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
      Toast.makeText(context, "没有蓝牙权限", Toast.LENGTH_SHORT).show();
      return false;
    }
    return true;
  }
}
