package com.example.new_androidclient.device_management.Adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ItemDeviceListBinding;
import com.example.new_androidclient.hazard.bean.HazardAnalysisDeviceListBean;

import java.util.List;

public class DeviceListAdapter extends BaseAdapter<HazardAnalysisDeviceListBean, ItemDeviceListBinding> {

    public DeviceListAdapter(List<HazardAnalysisDeviceListBean> list, int brId,onItemClickListener listener) {
        super(list, brId,null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_device_list;
    }
}
