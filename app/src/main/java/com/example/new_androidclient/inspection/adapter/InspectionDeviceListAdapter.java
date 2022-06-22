package com.example.new_androidclient.inspection.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityInspectionDeviceListItemBinding;
import com.example.new_androidclient.inspection.bean.InspectionDeviceListBean;

import java.util.List;

public class InspectionDeviceListAdapter extends BaseAdapter<InspectionDeviceListBean, ActivityInspectionDeviceListItemBinding> {

    public InspectionDeviceListAdapter(List<InspectionDeviceListBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inspection_device_list_item;
    }
}



