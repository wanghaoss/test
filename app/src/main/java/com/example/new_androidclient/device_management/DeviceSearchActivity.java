package com.example.new_androidclient.device_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityDeviceSearchBinding;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
/**
 * 设备管理搜索
 * */
@Route(path = RouteString.DeviceSearchActivity)
public class DeviceSearchActivity extends AppCompatActivity {
    ActivityDeviceSearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_device_list);
        binding.dev1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String[] languages = getResources().getStringArray(R.array.languages);
//                Toast.makeText(DeviceSearchActivity.this, "你点击的是:"+languages[i],100).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
