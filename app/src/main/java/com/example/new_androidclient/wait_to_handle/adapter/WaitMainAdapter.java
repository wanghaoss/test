package com.example.new_androidclient.wait_to_handle.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityWaitMainBinding;
import com.example.new_androidclient.wait_to_handle.bean.WaitMainBean;

import java.util.List;

public class WaitMainAdapter extends BaseAdapter<WaitMainBean, ActivityWaitMainBinding> {
    public WaitMainAdapter(List<WaitMainBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.wait_main_item;
    }
}
