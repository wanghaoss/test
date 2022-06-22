package com.example.new_androidclient.inspection.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityInspection24ListItemBinding;
import com.example.new_androidclient.inspection.bean.Inspection24Bean;

import java.util.List;

public class Inspection24ListAdapter extends BaseAdapter<Inspection24Bean, ActivityInspection24ListItemBinding> {

    public Inspection24ListAdapter(List<Inspection24Bean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inspection_24_list_item;
    }
}