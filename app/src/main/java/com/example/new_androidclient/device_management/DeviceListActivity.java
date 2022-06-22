package com.example.new_androidclient.device_management;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityDeviceListBinding;
import com.example.new_androidclient.device_management.Adapter.DeviceListAdapter;
import com.example.new_androidclient.hazard.HazardAnalysisDeviceSelectActivity;
import com.example.new_androidclient.hazard.bean.HazardAnalysisDeviceListBean;

import java.util.ArrayList;
import java.util.List;
/**
 * 设备管理列表
 * */

@Route(path = RouteString.DeviceListActivity)
public class DeviceListActivity extends BaseActivity {
    ActivityDeviceListBinding binding;
    DeviceListAdapter adapter;
    List<HazardAnalysisDeviceListBean> deviceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_device_list);

        adapter = new DeviceListAdapter(deviceList, BR.hazardAnalysisDeviceListBean,null);
        binding.hazardListRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.hazardListRecycler.setAdapter(adapter);
    }

    private void getDeviceList() {
        RetrofitUtil.getApi().obtainDeviceList(1,100)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardAnalysisDeviceListBean>>(mContext, true, "正在获取数据"){

                    @Override
                    public void onSuccess(List<HazardAnalysisDeviceListBean> bean) {
                        if (bean.size() > 0) {
                            deviceList.clear();
                            deviceList.addAll(bean);
                            binding.hazardListRecycler.setVisibility(View.VISIBLE);
                            binding.nodata.setVisibility(View.GONE);
                        } else {
                            binding.hazardListRecycler.setVisibility(View.GONE);
                            binding.nodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext,err);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDeviceList();
    }
}
