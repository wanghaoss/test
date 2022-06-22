package com.example.new_androidclient.hazard;

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
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityHazardBinding;
import com.example.new_androidclient.hazard.adapter.HazardListAdapter;
import com.example.new_androidclient.hazard.bean.HazardListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 隐患待办列表
 */
@Route(path = RouteString.HazardListActivity)
public class HazardListActivity extends BaseActivity {

    List<HazardListBean> listBeans = new ArrayList<>();
    ActivityHazardBinding binding;
    HazardListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hazard);

        adapter = new HazardListAdapter(listBeans, BR.hazardListBean, this::jump);
        binding.hazardListRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.hazardListRecycler.setAdapter(adapter);
    }

    private void jump(View view, int position) {
        String route = "";
        String status = listBeans.get(position).getStatus();
        if (status.isEmpty()) {
            ToastUtil.show(mContext, "请仔细检查隐患状态");
            return;
        }
        if (status.contains("排查中") || status.contains("已下达")) {
            route = RouteString.HazardDetailActivity;
        } else if (status.contains("已整改") || status.contains("整改验收拒绝")) {
            //   route = RouteString.HazardVerificationDetailActivity;   //验收详情
            route = RouteString.HazardVerificationListActivity; // 验收列表，用于一个一个保存或者验收
        } else if (status.contains("整改验收待审核") || status.contains("整改验收待审批")) {
            route = "";
            ARouter.getInstance().build(RouteString.HazardVerificationListActivity).withInt("planId", listBeans.get(position).getId())
                    .withBoolean("isSign", true).navigation();
        } else if (status.contains("已排查，待检查单位负责人签字") || status.contains("待受检单位负责人签字")) {
            route = RouteString.HazardNotificationSignActivity;
        } else if (status.contains("已接收，待分析") || status.contains("已通知") || status.contains("重新隐患分析")) {
            //   route = RouteString.AnalysisActivity;
            route = RouteString.HazardAnalysisNewActivity;
        } else if (status.contains("待编制整改计划") || status.contains("已分析")) {
            route = RouteString.HazardPlanActivity;
        } else if (status.contains("整改计划待审核") || status.contains("整改计划待审批")) {
            route = RouteString.HazardPlanSignActivity;
        } else if (status.contains("整改中") || status.contains("重新整改")) {
            route = RouteString.HazardGovernmentActivity;
        } else if (status.contains("隐患分析待审核") || status.contains("隐患分析待审批")) {
            route = "";
            ARouter.getInstance().build(RouteString.HazardAnalysisNewActivity).withInt("planId", listBeans.get(position).getId())
                    .withBoolean("isSign", true).navigation();
        }
        if (!route.isEmpty()) {
            ARouter.getInstance().build(route).withInt("planId", listBeans.get(position).getId()).navigation();
        }
    }

    private void getList() {
        RetrofitUtil.getApi().getHazardList()
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardListBean>>(mContext, true, "正在获取列表") {
                    @Override
                    public void onSuccess(List<HazardListBean> bean) {
                        listBeans.clear();
//                        for (int i = 0; i < bean.size(); i++) {
//                            if (
//                                    bean.get(i).getStatus().equals("已接收，待分析")
////                                    bean.get(i).getStatus().equals("排查中") ||
////                                    bean.get(i).getStatus().equals("已下达") ||
////                                    bean.get(i).getStatus().equals("已整改")
////                                    bean.get(i).getStatus().equals("已排查，待检查单位负责人签字") ||
////                                    bean.get(i).getStatus().equals("待受检单位负责人签字") ||
////                                    bean.get(i).getStatus().equals("待编制整改计划")
////                                    bean.get(i).getStatus().equals("整改计划待审核") ||
////                                    bean.get(i).getStatus().equals("整改计划待审批") ||
////                                    bean.get(i).getStatus().equals("整改中")
//                            ) {
//                                listBeans.add(bean.get(i));
//                            }
//                        }
                        listBeans.addAll(bean);
                        if (listBeans.size() == 0) {
                            binding.hazardListRecycler.setVisibility(View.GONE);
                            binding.nodata.setVisibility(View.VISIBLE);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        listBeans.clear();
        getList();
    }
}

