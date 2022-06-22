package com.example.new_androidclient.device_management;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.EventBusMessage.EventBusMessage;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityInterimPlanActivtyBinding;
import com.example.new_androidclient.device_management.Adapter.InterimPlanAdapter;
import com.example.new_androidclient.device_management.bean.DevicePlanListBean;
import com.example.new_androidclient.device_management.Data.OnRecyclerItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 临时计划列表
 **/
@Route(path = RouteString.InterimPlanActivity)
public class InterimPlanActivity extends BaseActivity {

    ActivityInterimPlanActivtyBinding binding;
    InterimPlanAdapter adapter;
    List<DevicePlanListBean> list = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_interim_plan_activty);
        adapter = new InterimPlanAdapter(list,this);
        binding.deviceInterimListRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.deviceInterimListRecycler.setAdapter(adapter);
        setView();
    }

    private void setView() {
        adapter.setRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int Position, List<DevicePlanListBean> dataBeanList) {
                Intent intent = new Intent(mContext,InterimPlanDetailsActivity.class);
                intent.putExtra("temporaryId",list.get(Position).getTemporaryId());
                startActivity(intent);
            }
        });
    }

    //获取临时计划接口
    private void getView() {
        RetrofitUtil.getApi().findDevicePlanList()
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<DevicePlanListBean>>(mContext, true, "正在获取数据"){

                    @Override
                    public void onSuccess(List<DevicePlanListBean> bean) {
                        if (bean.size() > 0) {
                            list.clear();
                            list.addAll(bean);
                            adapter.notifyDataSetChanged();
                            binding.deviceInterimListRecycler.setVisibility(View.VISIBLE);
                            binding.nodata.setVisibility(View.GONE);
                            stopLockTask();
                        } else {
                            binding.deviceInterimListRecycler.setVisibility(View.GONE);
                            binding.nodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext,err);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        getView();
    }

    @Subscribe()
    public void onEvent(EventBusMessage message){
        if (message.equals("CHANGE")){
            getView();
        }

        if (message.equals("VALUE")){
            getView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
