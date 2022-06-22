package com.example.new_androidclient.hazard.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.HazardListItemBinding;
import com.example.new_androidclient.hazard.bean.HazardListBean;

import java.util.List;

public class HazardListAdapter extends BaseAdapter<HazardListBean, HazardListItemBinding> {


    public HazardListAdapter(List<HazardListBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.hazard_list_item;
    }




}
