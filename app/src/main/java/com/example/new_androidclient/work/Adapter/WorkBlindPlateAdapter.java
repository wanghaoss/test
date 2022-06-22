package com.example.new_androidclient.work.Adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.WorkBlindPlateListItemBinding;
import com.example.new_androidclient.work.bean.WorkBlindPlateBean;

import java.util.List;


public class WorkBlindPlateAdapter extends BaseAdapter<WorkBlindPlateBean, WorkBlindPlateListItemBinding> {
    public WorkBlindPlateAdapter(List<WorkBlindPlateBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.work_blind_plate_list_item;
    }
}