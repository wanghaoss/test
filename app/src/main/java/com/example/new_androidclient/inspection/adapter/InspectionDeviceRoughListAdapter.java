package com.example.new_androidclient.inspection.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityInspectionDeviceRoughListItemBinding;
import com.example.new_androidclient.inspection.bean.InspectionAreaBean;
import com.example.new_androidclient.inspection.bean.TaskAreaItemResult;

import java.util.List;

public class InspectionDeviceRoughListAdapter extends BaseAdapter<InspectionAreaBean, ActivityInspectionDeviceRoughListItemBinding> {

    public InspectionDeviceRoughListAdapter(List<InspectionAreaBean> list, int brId, BaseAdapter.onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inspection_device_rough_list_item;
    }
}

