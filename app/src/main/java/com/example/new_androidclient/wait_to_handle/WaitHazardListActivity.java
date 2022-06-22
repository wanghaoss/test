package com.example.new_androidclient.wait_to_handle;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityWaitHazardListBinding;
import com.example.new_androidclient.wait_to_handle.adapter.WaitHazardListAdapter;
import com.example.new_androidclient.wait_to_handle.bean.WaitHazardListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 待办-隐患列表
 */
@Route(path = RouteString.WaitHazardListActivity)
public class WaitHazardListActivity extends BaseActivity {
    private List<WaitHazardListBean> allList = new ArrayList<>();
    private List<WaitHazardListBean> nowList = new ArrayList<>();//待办有很多，现阶段已经实现的
    private ActivityWaitHazardListBinding binding;
    private WaitHazardListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wait_hazard_list);
        adapter = new WaitHazardListAdapter(nowList, BR.wait_main_list, this::jump);
        binding.waitMainRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.waitMainRecycler.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getList();
    }

    private void jump(View view, int position) {
        String path = "";
        switch (nowList.get(position).getModelName()) {
            case "隐患整改计划":
                path = RouteString.HazardPlanSignActivity;
                break;
        }
        ARouter.getInstance().build(path)
                .withInt("planId", nowList.get(position).getFormId())
                .navigation();
    }


    private void getList() {
        RetrofitUtil.getApi().getUserTasks()
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<WaitHazardListBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<WaitHazardListBean> bean) {
                        if (bean.size() > 0) {
                            allList.clear();
                            allList.addAll(bean);
                            binding.nodata.setVisibility(View.GONE);
                            binding.waitMainRecycler.setVisibility(View.VISIBLE);
                            initList();
                        } else {
                            binding.nodata.setVisibility(View.VISIBLE);
                            binding.waitMainRecycler.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {

                    }
                });
    }

    private void initList() {
        nowList.clear();
        for (int i = 0; i < allList.size(); i++) {
            if (allList.get(i).getModelName().equals("隐患整改计划")) {
                nowList.add(allList.get(i));
            }
        }
        if (nowList.size() > 0) {
            binding.nodata.setVisibility(View.GONE);
            binding.waitMainRecycler.setVisibility(View.VISIBLE);
        } else {
            binding.nodata.setVisibility(View.VISIBLE);
            binding.waitMainRecycler.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }
}