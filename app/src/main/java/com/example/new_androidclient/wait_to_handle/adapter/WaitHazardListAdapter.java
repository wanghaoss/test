package com.example.new_androidclient.wait_to_handle.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityWaitHazardListItemBinding;
import com.example.new_androidclient.wait_to_handle.bean.WaitHazardListBean;

import java.util.List;

public class WaitHazardListAdapter extends BaseAdapter<WaitHazardListBean, ActivityWaitHazardListItemBinding> {
    public WaitHazardListAdapter(List<WaitHazardListBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wait_hazard_list_item;
    }
}
