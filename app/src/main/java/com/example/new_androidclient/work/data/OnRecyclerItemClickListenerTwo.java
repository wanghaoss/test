package com.example.new_androidclient.work.data;


import com.example.new_androidclient.work.bean.WorkIngToDoTwoBean;
import com.example.new_androidclient.work.bean.WorkNeedDoBean;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.List;

//点击List的Item的接口
public interface OnRecyclerItemClickListenerTwo {
    //RecyclerView的点击事件，将信息回调给view
    void onItemClick(int Position, List<WorkIngToDoTwoBean> dataBeanList);
}
