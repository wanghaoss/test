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
import com.example.new_androidclient.databinding.ActivityHazardVerificationListBinding;
import com.example.new_androidclient.hazard.adapter.HazardVerificationListAdapter;
import com.example.new_androidclient.hazard.bean.HazardVerificationDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * zq
 * 验收列表
 **/
@Route(path = RouteString.HazardVerificationListActivity)
public class HazardVerificationListActivity extends BaseActivity {
    private ActivityHazardVerificationListBinding binding;
    private List<HazardVerificationDetailBean> list = new ArrayList<>();
    private HazardVerificationListAdapter adapter;
    private Listener listener = new Listener();

    @Autowired()
    int planId;

    @Autowired
    boolean isSign = false; //是否是审核审批

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hazard_verification_list);
        binding.setListener(listener);
        adapter = new HazardVerificationListAdapter(list, BR.hazard_ver_list, new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (list.get(position).getStatus().equals("52")) { //已完成
                    ToastUtil.show(mContext, "该隐患已验收完成");
                } else if (list.get(position).getStatus().equals("50") || list.get(position).getStatus().equals("51")) {  // 50审核  51审批
                    ARouter.getInstance().build(RouteString.HazardVerificationDetailActivity)
                            .withBoolean("isSign", true)
                            .withObject("object", list.get(position)).navigation();
                } else {
                    ARouter.getInstance().build(RouteString.HazardVerificationDetailActivity)
                            .withObject("object", list.get(position)).navigation();
                }

            }
        });
        binding.hazardVerListRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.hazardVerListRecycler.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getList();
    }

    private void getList() {
        RetrofitUtil.getApi().getVerHazardDetailById_a(planId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardVerificationDetailBean>>(mContext, true, "正在获取隐患详情") {
                    @Override
                    public void onSuccess(List<HazardVerificationDetailBean> bean) {
                        list.clear();
                        list.addAll(bean);
                        adapter.notifyDataSetChanged();
                        if (bean.size() == 0) {
                            binding.nodata.setVisibility(View.VISIBLE);
                            binding.hazardVerListRecycler.setVisibility(View.GONE);
                            return;
                        }
                        if (isSign) {  //是审核审批状态，把提交按钮隐藏
                            binding.submit.setVisibility(View.GONE);
                        } else {
                            boolean isAllFinish = bean.stream().allMatch(a -> a.getAcceptanceDesc() != null);
                            if (isAllFinish) {
                                binding.submit.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    public class Listener {
        public void submit() {
            RetrofitUtil.getApi().hiRectifyPlanCheckAndAcceptSubmit(planId)
                    .compose(new SchedulerTransformer<>())
                    .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                        @Override
                        public void onSuccess(String bean) {
                            if (bean.equals("提交成功")) {
                                ToastUtil.show(mContext, bean);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(String err) {
                            ToastUtil.show(mContext, err);
                        }
                    });
        }
    }

}
