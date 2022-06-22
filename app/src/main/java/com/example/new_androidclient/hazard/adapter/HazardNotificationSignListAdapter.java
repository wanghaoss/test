package com.example.new_androidclient.hazard.adapter;

import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityHazardNotificationSignListItemBinding;
import com.example.new_androidclient.hazard.bean.HazardNotificationSignListBean;

import java.util.List;

public class HazardNotificationSignListAdapter extends BaseAdapter<HazardNotificationSignListBean, ActivityHazardNotificationSignListItemBinding> {
    public HazardNotificationSignListAdapter(List<HazardNotificationSignListBean> list, int brId, onItemClickListener listener) {
        super(list, brId, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hazard_notification_sign_list_item;
    }
}
