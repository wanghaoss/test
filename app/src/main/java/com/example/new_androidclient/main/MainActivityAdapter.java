package com.example.new_androidclient.main;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.MainActivityItemBinding;
import com.example.new_androidclient.main.bean.HomeListBean;

import java.util.List;

public class MainActivityAdapter extends BaseAdapter<HomeListBean, MainActivityItemBinding> {


    public MainActivityAdapter(List<HomeListBean> list, int brId, BaseAdapter.onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_item;
    }

}
