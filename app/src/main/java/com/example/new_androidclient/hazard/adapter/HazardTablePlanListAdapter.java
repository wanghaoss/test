package com.example.new_androidclient.hazard.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.HazardTablePlanListItemBinding;
import com.example.new_androidclient.hazard.bean.HazardTablePlanListBean;

import java.util.List;

public class HazardTablePlanListAdapter extends BaseAdapter<HazardTablePlanListBean, HazardTablePlanListItemBinding> {
    public HazardTablePlanListAdapter(List<HazardTablePlanListBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.hazard_table_plan_list_item;
    }
}

