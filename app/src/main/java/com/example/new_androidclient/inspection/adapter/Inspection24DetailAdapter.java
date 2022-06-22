package com.example.new_androidclient.inspection.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityInspection24DetailItemBinding;
import com.example.new_androidclient.inspection.Inspection24ProblemDetailActivity;

import java.util.List;

public class Inspection24DetailAdapter extends BaseAdapter<Inspection24ProblemDetailActivity.DetailBean, ActivityInspection24DetailItemBinding> {
    public Inspection24DetailAdapter(List<Inspection24ProblemDetailActivity.DetailBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inspection_24_detail_item;
    }
}
