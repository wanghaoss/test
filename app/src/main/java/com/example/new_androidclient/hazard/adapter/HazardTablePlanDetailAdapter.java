package com.example.new_androidclient.hazard.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityHazardPlanDetailItemBinding;
import com.example.new_androidclient.hazard.bean.HazardTablePlanDetailBean;

import java.util.List;

public class HazardTablePlanDetailAdapter extends BaseAdapter<HazardTablePlanDetailBean, ActivityHazardPlanDetailItemBinding> {
    public HazardTablePlanDetailAdapter(List<HazardTablePlanDetailBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hazard_plan_detail_item;
    }
}

