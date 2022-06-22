package com.example.new_androidclient.inspection.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityInspectionRoughDeviceListItemBinding;
import com.example.new_androidclient.inspection.bean.InspectionDeviceListBean;

import java.util.List;

public class InspectionRoughDeviceListAdapter extends BaseAdapter<InspectionDeviceListBean, ActivityInspectionRoughDeviceListItemBinding> {
    public InspectionRoughDeviceListAdapter(List<InspectionDeviceListBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inspection_rough_device_list_item;
    }
}
