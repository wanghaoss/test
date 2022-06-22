package com.example.new_androidclient.hazard.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityHazardPlanSignItemBinding;
import com.example.new_androidclient.hazard.bean.HazardPlanListBean;

import java.util.List;

public class HazardPlanSignAdapter extends BaseAdapter<HazardPlanListBean, ActivityHazardPlanSignItemBinding> {
    public HazardPlanSignAdapter(List<HazardPlanListBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hazard_plan_sign_item;
    }
}
