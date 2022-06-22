package com.example.new_androidclient.work;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.EventBusMessage.EventBusMessage;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityWorkNeedDoBinding;
import com.example.new_androidclient.work.Adapter.WorkNeedDoOneAdapter;
import com.example.new_androidclient.work.Adapter.WorkNeedDoTwoAdapter;
import com.example.new_androidclient.work.bean.WorkIngToDoTwoBean;
import com.example.new_androidclient.work.bean.WorkNeedDoBean;
import com.example.new_androidclient.work.bean.WorkingToDoBean;
import com.example.new_androidclient.work.data.OnRecyclerItemClickListener;
import com.example.new_androidclient.work.data.OnRecyclerItemClickListenerTwo;
import com.google.gson.internal.LinkedTreeMap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * wh
 * 作业待办列表
 **/
@Route(path = RouteString.WorkNeedDoActivity)
public class WorkNeedDoActivity extends BaseActivity {

    ActivityWorkNeedDoBinding binding;
    WorkNeedDoOneAdapter adapter;
    WorkNeedDoTwoAdapter twoAdapter;
    int type;
    int planWorkId;
    int applicationId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_need_do);

        type = getIntent().getIntExtra("type",type);
    }

    private void getView() {
        if (type == 1 || type == 2 || type == 3 || type == 4){
            RetrofitUtil.getApi().selectWorkingToDo(type)
                    .compose(new SchedulerTransformer<>())
                    .subscribe(new DialogObserver<List<WorkingToDoBean>>(mContext, true, "正在获取数据") {
                        @Override
                        public void onSuccess(List<WorkingToDoBean> bean) {
                            if (bean.size() > 0) {
                                setView(bean);
                            }else {
                                binding.nodata.setVisibility(View.VISIBLE);
                                binding.workNeedDo.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(String err) {
                            ToastUtil.show(mContext, err);
                        }
                    });
        }

        if (type == 5 || type == 6 || type == 7){
            RetrofitUtil.getApi().selectWorkingToDoTwo(type)
                    .compose(new SchedulerTransformer<>())
                    .subscribe(new DialogObserver<List<WorkIngToDoTwoBean>>(mContext, true, "正在获取数据") {
                        @Override
                        public void onSuccess(List<WorkIngToDoTwoBean> bean) {
                            if (bean.size() > 0) {
                                setSmallStateView(bean);
                            }else {
                                binding.nodata.setVisibility(View.VISIBLE);
                                binding.workNeedDo.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(String err) {
                            ToastUtil.show(mContext, err);
                        }
                    });
        }

    }

    //设置核查之后的值
    private void setSmallStateView(List<WorkIngToDoTwoBean> bean) {

        if (bean.size() == 0) {
            binding.nodata.setVisibility(View.VISIBLE);
            binding.workNeedDo.setVisibility(View.GONE);
        }

        if (type == 5 || type == 6 || type == 7) {

            twoAdapter = new WorkNeedDoTwoAdapter(bean, this);
            binding.workNeedDo.setLayoutManager(new LinearLayoutManager(this));
            binding.workNeedDo.setAdapter(twoAdapter);

            twoAdapter.setRecyclerItemClickListener((Position, dataBeanList) -> {
                String path = "";

                int id = dataBeanList.get(Position).getId();

                int applicationId = dataBeanList.get(Position).getApplicationId();

                String status = dataBeanList.get(Position).getStatus(); //小状态

                String ticketFlag = dataBeanList.get(Position).getTicketFlag();

                if (status.equals("01") || status.equals("02")) { //01未签字  02签字中
                    path = RouteString.WorkAddSignActivity;
                } else if (status.equals("03")) { //交底签字
                    path = RouteString.CountersignActivity;
                }else if (status.equals("04")){ //会签
                    path = RouteString.PermitCountersignActivity;
                }else if (status.equals("07") && type == 7) {  //作业关闭
                    path = RouteString.WorkCloseActivity;
                }else if (status.equals("07")){ //作业监督
                    path = RouteString.WorkControlActivity;
                }else if (status.equals("08") || status.equals("09") || status.equals("10") || status.equals("11") || status.equals("12")) {  //8+9+10作业关闭签字 11作业取消 12关闭或取消签一部分
                    path = RouteString.WorkAddSignActivity;
                }else {
                    return;
                }

                if (!path.isEmpty()) {
                    ARouter.getInstance().build(path)
                            .withInt("id", id)
                            .withInt("applicationId", applicationId)
                            .withString("ticketFlag",ticketFlag)
                            .withString("sign", status)  //审核需要
                            .withString("status", status)  //workAddSignActivity需要, 判断几个人签字
                            .navigation();
                }
            });
        }
    }
    //设置核查之前的值
    private void setView(List<WorkingToDoBean> list) {

        if (list.size() == 0) {
            binding.nodata.setVisibility(View.VISIBLE);
            binding.workNeedDo.setVisibility(View.GONE);
        }

        if (type == 1 || type == 2 || type == 3 || type == 4) {

            adapter = new WorkNeedDoOneAdapter(list,1, mContext);
            binding.workNeedDo.setLayoutManager(new LinearLayoutManager(this));
            binding.workNeedDo.setAdapter(adapter);

            adapter.setRecyclerItemClickListener(new OnRecyclerItemClickListener() {
                @Override
                public void onItemClick(int Position, List<WorkingToDoBean> bean) {
                    String path = "";
                    planWorkId = list.get(Position).getPlanWorkId();

                    applicationId = list.get(Position).getId();

                    String dhStatus = list.get(Position).getDhStatus();

                    String status = list.get(Position).getStatus();

                    String statusName = list.get(Position).getStatusName();
                    if (status.equals("20")) {  //现场踏勘/JSA
                        path = "";
                        ARouter.getInstance().build(RouteString.WorkRegionActivity)
                                .withInt("planWorkId", planWorkId)
                                .withInt("applicationId", applicationId)
                                .withString("type","tk")
                                .navigation();
                    } else if (status.equals("21")) { //作业申请
                        path = RouteString.WorkApplicationActivity;
                    } else if (status.equals("30")) {//现场核查
                        path = "";
                        ARouter.getInstance().build(RouteString.WorkRegionActivity)
                                .withInt("planWorkId", planWorkId)
                                .withInt("applicationId", applicationId)
                                .withString("dhStatus",dhStatus)
                                .withString("type","hc")
                                .navigation();
                    }else if (statusName.equals("安全措施确认签字") && type == 4){ //现场核查的4人签字
                        path = "";
                        ARouter.getInstance().build(RouteString.WorkAddSignActivity)
                                .withInt("applicationId", applicationId)
                                .withString("status",dhStatus)
                                .withString("type","hc")
                                .navigation();
                    }else if (status.equals("22")) { //作业预约
                        path = RouteString.WorkAppointmentActivity;
                    } else if (status.equals("23")) { // 22作业预约包含作业审核 23作业审批
                        path = "";
                        ARouter.getInstance().build(RouteString.WorkAppointmentActivity)
                                .withInt("planWorkId", planWorkId)
                                .withInt("applicationId", applicationId)
                                .withBoolean("isSign", true)
                                .navigation();
                    } else {
                        return;
                    }

                    if (!path.isEmpty()) {
                        ARouter.getInstance().build(path)
                                .withInt("planWorkId", planWorkId)
                                .withInt("applicationId", applicationId)
                                .navigation();
                    }
                }
            });
        }
    }

    //完成作业状态修改，刷新接口
    @Subscribe()
    public void onEvent(EventBusMessage message) {
        getView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
