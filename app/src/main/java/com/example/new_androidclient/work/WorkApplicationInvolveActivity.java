package com.example.new_androidclient.work;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.WorkApplicationInvolveItemLayout;
import com.example.new_androidclient.customize_view.WorkApplicationLineLayout;
import com.example.new_androidclient.databinding.ActivityWorkApplicationInvolveBinding;
import com.example.new_androidclient.work.bean.WorkApplicationBean;
import com.example.new_androidclient.work.bean.WorkApplicationInvolveListBean;
import com.example.new_androidclient.work.bean.WorkCheckOnsite;
import com.example.new_androidclient.work.data.WorkConditionDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * zq
 * 作业涉及事项选择（各特殊作业申请信息填写完成后的选择页面
 */

@Route(path = RouteString.WorkApplicationInvolveActivity)
public class WorkApplicationInvolveActivity extends BaseActivity {
    private List<WorkApplicationInvolveListBean> involveList = new ArrayList<>();
    private ActivityWorkApplicationInvolveBinding binding;
    private List<WorkApplicationInvolveItemLayout> itemList = new ArrayList<>();
    private Listener listener = new Listener();
    private WorkApplicationBean uploadApplicationBean = new WorkApplicationBean(); //最后两条需要调用上传基本信息接口
    private List<WorkCheckOnsite> uploadInvolveList = new ArrayList<>();

    @Autowired
    int applicationId;

    @Autowired
    int planWorkId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_application_involve);
        binding.setListener(listener);
        getList();
    }

    private void getList() {
        RetrofitUtil.getApi().selectCheckOnsiteConfig(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<WorkApplicationInvolveListBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<WorkApplicationInvolveListBean> bean) {
                        involveList.clear();
                        itemList.clear();

                        if (bean.size() > 0) {
                            involveList = bean;
                            addInvolveItem();
                        }
                        for (int i = 0; i < involveList.size(); i++) {
                            addView(i);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void addInvolveItem() {
        String[] temp = {"施工组织设计及施工方案通过审批", "安全作业方案通过审批"};
        for (int i = 0; i < 2; i++) {
            WorkApplicationInvolveListBean bean = new WorkApplicationInvolveListBean();
            bean.setItemResult("1");
            bean.setItemName(temp[i]);
            involveList.add(bean);
        }
    }

    private void addView(int position) {
        WorkApplicationInvolveItemLayout layout = new WorkApplicationInvolveItemLayout(mContext, position);
        layout.init();
        layout.setNameStr(involveList.get(position).getItemName());
        if (involveList.get(position).getItemResult().equals("1")) {
            layout.getLeftButton().setChecked(true);
        } else if (involveList.get(position).getItemResult().equals("0")) {
            layout.getRightButton().setChecked(true);
        } else {
            layout.getLeftButton().setChecked(true);
        }

        layout.getLeftButton().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    involveList.get(position).setItemResult("1");
                }
            }
        });

        layout.getRightButton().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    involveList.get(position).setItemResult("0");
                }
            }
        });


        binding.linear.addView(layout);
        itemList.add(layout);

    }

    private void handleData() {
        uploadInvolveList.clear();
        for (int i = 0; i < involveList.size() - 2; i++) {
            WorkCheckOnsite bean = new WorkCheckOnsite();
            bean.setId(involveList.get(i).getWid());
            bean.setApplicationId(applicationId);
            bean.setItemName(involveList.get(i).getCheckName());
            bean.setItemId(involveList.get(i).getId());
            bean.setItemType(involveList.get(i).getItemType());
            bean.setItemLable(involveList.get(i).getItemLable());
            bean.setItemResult(involveList.get(i).getItemResult());
            uploadInvolveList.add(bean);
        }

        uploadApplicationBean.setIsSafetyApproved(involveList.get(involveList.size() - 1).getItemResult());
        uploadApplicationBean.setIsConstructionApproved(involveList.get(involveList.size() - 2).getItemResult());
        uploadApplicationBean.setId(applicationId);
        uploadApplicationBean.setPlanWorkId(planWorkId);

        submitApplicationBaseData();
    }

    private void submitApplicationBaseData() {
        RetrofitUtil.getApi().addApplication(uploadApplicationBean)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String bean) {
                        submitApplicationInvolveData();

                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void submitApplicationInvolveData() {
        RetrofitUtil.getApi().addCheckOnsite(uploadInvolveList)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("保存成功")) {
                            //   ARouter.getInstance().build(RouteString.WorkNeedDoActivity).navigation();
                            updateApplicationStatus();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void updateApplicationStatus() {
        RetrofitUtil.getApi().updateApplicationStatus(applicationId, "22")  //22 作业预约
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String bean) {
                        ARouter.getInstance().build(RouteString.WorkNeedDoActivity).navigation();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    public class Listener {
        public void submit() {
            handleData();
        }
    }
}
