package com.example.new_androidclient.inspection.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityInspectionBluetoothItemBinding;
import com.example.new_androidclient.inspection.bean.InspectionBlueToothDeviceBean;

import java.util.List;

public class InspectionBlueToothAdapter extends BaseAdapter<InspectionBlueToothDeviceBean, ActivityInspectionBluetoothItemBinding> {
    public InspectionBlueToothAdapter(List<InspectionBlueToothDeviceBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inspection_bluetooth_item;
    }
}
