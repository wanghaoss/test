package com.example.new_androidclient.hazard;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.example.new_androidclient.databinding.ActivityHazardTablePlanListBinding;
import com.example.new_androidclient.hazard.adapter.HazardTablePlanListAdapter;
import com.example.new_androidclient.hazard.bean.HazardTablePlanListBean;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouteString.HazardTablePlanListActivity)
public class HazardTablePlanListActivity extends BaseActivity {
    private HazardTablePlanListAdapter adapter;
    private ActivityHazardTablePlanListBinding binding;

    private List<HazardTablePlanListBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hazard_table_plan_list);
        binding.hazardTablePlanListRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HazardTablePlanListAdapter(list, BR.hazard_table_plan_list, new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ARouter.getInstance().build(RouteString.HazardTablePlanDetailActivity)
                        .withInt("planId", list.get(position).getId()).navigation();
            }
        });
        binding.hazardTablePlanListRecycler.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getList();
    }

    private void getList() {
        RetrofitUtil.getApi().selectInspectPlan()
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardTablePlanListBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<HazardTablePlanListBean> bean) {
                        list.clear();
                        if (bean.size() > 0) {
                            //完成状态筛
//                            for (int i = 0; i < bean.size(); i++) {
//                                if (bean.get(i).getStatus().equals("0") ||
//                                        bean.get(i).getStatus().equals("1")||
//                                        bean.get(i).getStatus().equals("2")) {
//                                    list.add(bean.get(i));
//
//                                }
//                            }
                            list.addAll(bean);
                        }
                        adapter.notifyDataSetChanged();
                        if(list.size() ==0){
                            binding.hazardTablePlanListRecycler.setVisibility(View.GONE);
                            binding.hazardTablePlanListNodata.setVisibility(View.VISIBLE);
                        }else{
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
