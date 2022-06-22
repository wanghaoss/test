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
import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityInspectionDeviceRoughSelectDeviceListBinding;
import com.example.new_androidclient.inspection.adapter.InspectionDeviceRoughSelectDeviceListAdapter;
import com.example.new_androidclient.inspection.bean.InspectionDeviceRoughSelectDeviceListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 计划巡检-宏观巡检，巡检项关联设备时，搜索设备列表
 */

@Route(path = RouteString.InspectionDeviceRoughAndAreaSelectDeviceListActivity)
public class InspectionDeviceRoughAndAreaSelectDeviceListActivity extends BaseActivity {

    private List<InspectionDeviceRoughSelectDeviceListBean> list = new ArrayList<>();
    private InspectionDeviceRoughSelectDeviceListAdapter adapter;
    private ActivityInspectionDeviceRoughSelectDeviceListBinding binding;

    @Autowired
    String name = "";

    @Autowired
    String tag = "";

    @Autowired
    int areaId;

    @Autowired
    int from; //1宏观巡检 2区域巡检

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_device_rough_select_device_list);
        adapter = new InspectionDeviceRoughSelectDeviceListAdapter(list, BR.inspection_rough_select_device_list_bean, new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("deviceId", list.get(position).getDeviceId());
                setResult(from, intent);//1宏观巡检 2区域巡检
                finish();
            }
        });
        binding.inspectionDeviceRoughSelectDeviceListRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.inspectionDeviceRoughSelectDeviceListRecycler.setAdapter(adapter);
        getList();
    }

    private void getList() {
        RetrofitUtil.getApi().findAreaDevice(1, 500, areaId, name, tag)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<InspectionDeviceRoughSelectDeviceListBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<InspectionDeviceRoughSelectDeviceListBean> bean) {
                        list.clear();
                        if (bean.size() > 0) {
                            list.addAll(bean);
                            adapter.notifyDataSetChanged();
                            binding.inspectionDeviceRoughSelectDeviceListRecycler.setVisibility(View.VISIBLE);
                            binding.nodata.setVisibility(View.GONE);
                        } else {
                            binding.inspectionDeviceRoughSelectDeviceListRecycler.setVisibility(View.GONE);
                            binding.nodata.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });

    }
}
