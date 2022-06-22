package com.example.new_androidclient.device_management.Adapter;


import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;

import java.util.List;

public class ContractorAdapter extends BaseAdapter {

    public ContractorAdapter(List list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.contractor_item;
    }
}

