package com.example.new_androidclient.hazard.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityHazardVerListItemBinding;
import com.example.new_androidclient.hazard.bean.HazardVerificationDetailBean;

import java.util.List;

public class HazardVerificationListAdapter extends BaseAdapter<HazardVerificationDetailBean, ActivityHazardVerListItemBinding> {
    public HazardVerificationListAdapter(List<HazardVerificationDetailBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hazard_ver_list_item;
    }
}
