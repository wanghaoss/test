package com.example.new_androidclient.inspection.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.InspectionDeviceRoughSelectDeviceListItemBinding;
import com.example.new_androidclient.inspection.bean.InspectionDeviceRoughSelectDeviceListBean;

import java.util.List;

public class InspectionDeviceRoughSelectDeviceListAdapter extends BaseAdapter<InspectionDeviceRoughSelectDeviceListBean, InspectionDeviceRoughSelectDeviceListItemBinding> {

    public InspectionDeviceRoughSelectDeviceListAdapter(List<InspectionDeviceRoughSelectDeviceListBean> list, int brId, BaseAdapter.onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.inspection_device_rough_select_device_list_item;
    }
}

