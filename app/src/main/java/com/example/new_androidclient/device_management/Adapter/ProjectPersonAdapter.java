package com.example.new_androidclient.device_management.Adapter;


import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;

import java.util.List;

public class ProjectPersonAdapter extends BaseAdapter {

    public ProjectPersonAdapter(List list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_activity_item;
    }
}

