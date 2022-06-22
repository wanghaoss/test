package com.example.new_androidclient.wait_to_handle;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityWaitMainBinding;
import com.example.new_androidclient.wait_to_handle.adapter.WaitMainAdapter;
import com.example.new_androidclient.wait_to_handle.bean.WaitMainBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 待办-main模块页面
 */
@Route(path = RouteString.WaitMainActivity)
public class WaitMainActivity extends BaseActivity {
    private ActivityWaitMainBinding binding;
    private WaitMainAdapter adapter;
    private List<WaitMainBean> nameList = new ArrayList<>();

    private String[] name = {"巡检管理", "隐患管理"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wait_main);
        binding.waitActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WaitMainAdapter(nameList, BR.wait_main, new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String str = "";
                switch (position) {
                    case 0:  //巡检
                        str = RouteString.WaitInspectionListActivity;
                        break;
                    case 1:  //隐患
                        str = RouteString.WaitHazardListActivity;
                        break;
                }
                if (str != "") {
                    ARouter.getInstance().build(str).navigation();
                }
            }
        });
        binding.waitActivityRecyclerView.setAdapter(adapter);
        initData();
    }
    private void initData(){
        for (int i = 0; i < name.length; i++) {
            WaitMainBean newBean = new WaitMainBean();
            newBean.setName(name[i]);
            nameList.add(newBean);
        }

        adapter.notifyDataSetChanged();
    }
}
