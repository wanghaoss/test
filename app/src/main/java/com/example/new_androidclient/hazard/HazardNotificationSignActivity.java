package com.example.new_androidclient.hazard;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.DataConverterUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityHazardNotificationSignBinding;
import com.example.new_androidclient.hazard.adapter.HazardNotificationSignListAdapter;
import com.example.new_androidclient.hazard.bean.HazardNotificationSignInfoBean;
import com.example.new_androidclient.hazard.bean.HazardNotificationSignListBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 隐患-整改通知
 */
@Route(path = RouteString.HazardNotificationSignActivity)
public class HazardNotificationSignActivity extends BaseActivity {

    private ActivityHazardNotificationSignBinding binding;
    private Listener listener = new Listener();
    private HazardNotificationSignListAdapter adapter;

    private List<HazardNotificationSignListBean> list = new ArrayList<>();
    @Autowired()
    int planId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hazard_notification_sign);
        binding.setListener(listener);
        binding.hazardNotificationSignRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HazardNotificationSignListAdapter(list, BR.hazard_notification_sign_list_bean, this::jump);
        binding.hazardNotificationSignRecycler.setAdapter(adapter);
        getDetail();
        getList();
    }

    private void jump(View view, int i) {

    }

    private void getList() {
        RetrofitUtil.getApi().selectNoticeList(planId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardNotificationSignListBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<HazardNotificationSignListBean> bean) {
                        if (bean != null) {
                            list.clear();
                            list.addAll(bean);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void getDetail() {
        RetrofitUtil.getApi().selectNoticeDetail(planId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<HazardNotificationSignInfoBean>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(HazardNotificationSignInfoBean bean) {
                        if (bean != null) {
                            if (bean.getInvestigatedHeadSign() == null || bean.getInvestigatedHeadSign().isEmpty()) {
                                bean.setInvestigatedHeadSign("");
                            }

                            if (bean.getInvestigationHeadSign() == null || bean.getInvestigationHeadSign().isEmpty()) {
                                bean.setInvestigationHeadSign("");
                                binding.hazardNotificationSignInvestigatedHeadSignEdit.setEnabled(false);
                                binding.hazardNotificationSignInvestigatedHeadSignBtn.setClickable(false);

                            } else {
                                binding.hazardNotificationSignInvestigationHeadSignEdit.setEnabled(false);
                                binding.hazardNotificationSignInvestigationHeadSignBtn.setClickable(false);
                            }
                            binding.setInfoBean(bean);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void updateStatus(String status) {
        RetrofitUtil.getApi().updateStatus(planId, status)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "修改状态中") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("保存成功")) {
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

    public class Listener {
        public void investigationHeadSign() {
            String name = binding.hazardNotificationSignInvestigationHeadSignEdit.getText().toString();
            if (name.isEmpty()) {
                ToastUtil.show(mContext, "请填写完整");
                return;
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("investigationHeadSign", name);
                map.put("id", planId);
                RetrofitUtil.getApi().updateNoticeSign(DataConverterUtil.map_to_body(map))
                        .compose(new SchedulerTransformer<>())
                        .subscribe(new DialogObserver<String>(mContext, true, "提交数据中") {
                            @Override
                            public void onSuccess(String bean) {
                                if (bean.equals("保存成功")) {
                                    updateStatus("22");
                                } else {
                                    ToastUtil.show(mContext, bean);
                                }
                            }

                            @Override
                            public void onFailure(String err) {
                                ToastUtil.show(mContext, err);
                            }
                        });

            }
        }

        public void investigatedHeadSign() {
            String name = binding.hazardNotificationSignInvestigatedHeadSignEdit.getText().toString();
            if (name.isEmpty()) {
                ToastUtil.show(mContext, "请填写完整");
                return;
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("investigatedHeadSign", name);
                map.put("id", planId);
                RetrofitUtil.getApi().updateNoticeSign(DataConverterUtil.map_to_body(map))
                        .compose(new SchedulerTransformer<>())
                        .subscribe(new DialogObserver<String>(mContext, true, "提交数据中") {
                            @Override
                            public void onSuccess(String bean) {
                                if (bean.equals("保存成功")) {
                                    updateStatus("30");
                                } else {
                                    ToastUtil.show(mContext, bean);
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
}

