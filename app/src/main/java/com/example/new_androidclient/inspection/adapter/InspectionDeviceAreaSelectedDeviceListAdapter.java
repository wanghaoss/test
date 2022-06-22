package com.example.new_androidclient.inspection.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityInspectionDeviceRoughSelectBinding;
import com.example.new_androidclient.databinding.InspectionDeviceAreaSelectedDeviceListItemBinding;
import com.example.new_androidclient.inspection.bean.AreaResultDevice;
import com.example.new_androidclient.inspection.bean.ItemResultDevice;

import java.util.List;

/**
 * 计划巡检-宏观巡检, 点击某项时跳转到设备搜索页， 下方是已关联设备的列表，用本页adapter
 */

public class InspectionDeviceAreaSelectedDeviceListAdapter extends BaseAdapter<AreaResultDevice, InspectionDeviceAreaSelectedDeviceListItemBinding> {

    public InspectionDeviceAreaSelectedDeviceListAdapter(List<AreaResultDevice> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.inspection_device_area_selected_device_list_item;
    }
}


