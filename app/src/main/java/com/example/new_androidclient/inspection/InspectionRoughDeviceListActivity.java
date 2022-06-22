package com.example.new_androidclient.inspection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityInspectionRoughDeviceListBinding;
import com.example.new_androidclient.inspection.adapter.InspectionRoughDeviceListAdapter;
import com.example.new_androidclient.inspection.bean.InspectionDeviceListBean;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouteString.InspectionRoughDeviceListActivity)
public class InspectionRoughDeviceListActivity extends BaseActivity {
    private ActivityInspectionRoughDeviceListBinding binding;
    private InspectionRoughDeviceListAdapter adapter;
    private List<InspectionDeviceListBean> list = new ArrayList<>();
    private int resultCode = 2;
    @Autowired()
    int areaId;

    @Autowired()
    int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_rough_device_list);
        adapter = new InspectionRoughDeviceListAdapter(list, BR.inspection_rough_device_list_bean, this::jump);
        binding.inspectionRoughDeviceListRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.inspectionRoughDeviceListRecycler.setAdapter(adapter);
        getData();
    }

    private void jump(View view, int position) {
        Intent intent = new Intent();
        intent.putExtra("id", list.get(position).getId());
        intent.putExtra("deviceName", list.get(position).getDeviceName());
        setResult(resultCode, intent);
        finish();
    }

    private void getData() {
        RetrofitUtil.getApi().getTaskDeviceList(taskId, areaId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<InspectionDeviceListBean>>(mContext, true, "正在获取设备列表") {
                    @Override
                    public void onSuccess(List<InspectionDeviceListBean> bean) {
                        if (bean.size() > 0) {
                            list.addAll(bean);
                            adapter.notifyDataSetChanged();
                        } else {
                            binding.inspectionRoughDeviceListRecycler.setVisibility(View.GONE);
                            binding.inspectionRoughDeviceListNodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }
}
