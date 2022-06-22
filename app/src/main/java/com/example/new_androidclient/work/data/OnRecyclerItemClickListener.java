package com.example.new_androidclient.work.data;


import com.example.new_androidclient.work.bean.WorkNeedDoBean;
import com.example.new_androidclient.work.bean.WorkingToDoBean;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.List;

//点击List的Item的接口
public interface OnRecyclerItemClickListener {
    //RecyclerView的点击事件，将信息回调给view
    void onItemClick(int Position, List<WorkingToDoBean> dataBeanList);
}
