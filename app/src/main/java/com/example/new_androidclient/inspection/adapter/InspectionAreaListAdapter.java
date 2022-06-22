package com.example.new_androidclient.inspection.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityInspectionAreaListItemBinding;
import com.example.new_androidclient.inspection.bean.InspectionAreaBean;

import java.util.List;

public class InspectionAreaListAdapter extends BaseAdapter<InspectionAreaBean, ActivityInspectionAreaListItemBinding> {
    public InspectionAreaListAdapter(List<InspectionAreaBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inspection_area_list_item;
    }
}
