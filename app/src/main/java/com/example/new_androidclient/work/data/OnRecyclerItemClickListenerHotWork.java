package com.example.new_androidclient.work.data;


import com.example.new_androidclient.work.bean.HotWorkBean;
import com.example.new_androidclient.work.bean.WorkIngToDoTwoBean;

import java.util.List;

//点击List的Item的接口
public interface OnRecyclerItemClickListenerHotWork {
    //RecyclerView的点击事件，将信息回调给view
    void onItemClick(int Position, List dataBeanList);
}
