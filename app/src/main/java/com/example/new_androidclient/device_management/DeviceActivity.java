package com.example.new_androidclient.device_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient .BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.main.MainActivityAdapter;
import com.example.new_androidclient.main.bean.HomeListBean;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * 计划管理
 **/
@Route(path = RouteString.DeviceActivity)
public class DeviceActivity extends BaseActivity {
    List<HomeListBean> listBeans = new ArrayList<>();
    RecyclerView recyclerView;

    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        recyclerView = findViewById(R.id.main_activity_recycler_view);
        initList();
        openBlueTooth();
        MainActivityAdapter activityAdapter = new MainActivityAdapter(listBeans,
                BR.homeListBean, (view, position) -> onItemClick(position));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(activityAdapter);

    }

    private void initList() {

        String[] name = {"计划管理"};
        int[] image = {
                R.drawable.plan_management};

        for (int i = 0; i < name.length; i++) {
            HomeListBean homeListBean = new HomeListBean();
            homeListBean.name = name[i];
            homeListBean.image = image[i];
            listBeans.add(homeListBean);
        }
    }

    private void openBlueTooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
    }

    public void onItemClick(int position) {
        String str = "";
        switch (position) {
            case 0:
                str = RouteString.DevicePlanManagementActivity;
                break;
        }
        if (str != "") {
            ARouter.getInstance().build(str).navigation();
        }
    }
}
