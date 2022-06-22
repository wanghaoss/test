package com.example.new_androidclient.device_management.Data;


import com.example.new_androidclient.device_management.bean.DevicePlanListBean;

import java.util.List;

//点击List的Item的接口
public interface OnRecyclerItemClickListener {
    //RecyclerView的点击事件，将信息回调给view
    void onItemClick(int Position, List<DevicePlanListBean> dataBeanList);
}
