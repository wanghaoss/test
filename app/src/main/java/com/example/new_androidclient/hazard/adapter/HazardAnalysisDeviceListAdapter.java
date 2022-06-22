package com.example.new_androidclient.hazard.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityHazardAnalysisDeviceListItemBinding;
import com.example.new_androidclient.hazard.bean.HazardAnalysisDeviceListBean;

import java.util.List;

public class HazardAnalysisDeviceListAdapter extends BaseAdapter<HazardAnalysisDeviceListBean, ActivityHazardAnalysisDeviceListItemBinding> {
    public HazardAnalysisDeviceListAdapter(List<HazardAnalysisDeviceListBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hazard_analysis_device_list_item;
    }
}
