package com.example.new_androidclient.hazard.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityMajorHiddenDangerListItemBinding;
import com.example.new_androidclient.hazard.bean.HazardMajorHiddenDangerListBean;

import java.util.List;

public class HazardMajorHiddenDangerListAdapter extends BaseAdapter<HazardMajorHiddenDangerListBean, ActivityMajorHiddenDangerListItemBinding> {

    public HazardMajorHiddenDangerListAdapter(List<HazardMajorHiddenDangerListBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_major_hidden_danger_list_item;
    }
}

