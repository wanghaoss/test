package com.example.new_androidclient.hazard.adapter;


import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityHazardAnalysisPersonSelectBinding;
import com.example.new_androidclient.hazard.bean.HazardAnalysisPersonBean;

import java.util.List;

public class HazardAnalysisPersonListAdapter extends BaseAdapter<HazardAnalysisPersonBean, ActivityHazardAnalysisPersonSelectBinding> {


    public HazardAnalysisPersonListAdapter(List<HazardAnalysisPersonBean> list, int brId, BaseAdapter.onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_analysis_person_list_item;
    }




}

