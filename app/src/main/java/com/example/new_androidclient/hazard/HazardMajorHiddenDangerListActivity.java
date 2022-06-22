package com.example.new_androidclient.hazard;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityHazardMajorHiddenDangerListBinding;
import com.example.new_androidclient.hazard.adapter.HazardMajorHiddenDangerListAdapter;
import com.example.new_androidclient.hazard.adapter.HazardTablePlanListAdapter;
import com.example.new_androidclient.hazard.bean.HazardMajorHiddenDangerListBean;
import com.example.new_androidclient.hazard.bean.HazardTablePlanListBean;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouteString.HazardMajorHiddenDangerListActivity)
public class HazardMajorHiddenDangerListActivity extends BaseActivity {
    private List<HazardMajorHiddenDangerListBean> list = new ArrayList<>();
    private ActivityHazardMajorHiddenDangerListBinding binding;
    private HazardMajorHiddenDangerListAdapter adapter;

    @Autowired
    int planId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hazard_major_hidden_danger_list);
        binding.hazardTablePlanListRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HazardMajorHiddenDangerListAdapter(list, BR.hazard_major_hidden_danger_list,
                (view, position) -> {});
        binding.hazardTablePlanListRecycler.setAdapter(adapter);
        getList();
    }

    private void getList() {
        RetrofitUtil.getApi().majorHiddenDanger(planId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardMajorHiddenDangerListBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<HazardMajorHiddenDangerListBean> bean) {
                        list.clear();
                        if (bean.size() > 0) {
                            list.addAll(bean);
                        }
                        adapter.notifyDataSetChanged();
                        if (list.size() == 0) {
                            binding.hazardTablePlanListRecycler.setVisibility(View.GONE);
                            binding.hazardTablePlanListNodata.setVisibility(View.VISIBLE);
                        } else {
                            binding.hazardTablePlanListRecycler.setVisibility(View.VISIBLE);
                            binding.hazardTablePlanListNodata.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }
}
