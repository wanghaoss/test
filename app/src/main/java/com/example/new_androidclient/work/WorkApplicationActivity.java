package com.example.new_androidclient.work;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import androidx.annotation.Nullable;
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
import com.example.new_androidclient.customize_view.WorkApplicationLineLayout;
import com.example.new_androidclient.customize_view.WorkApplicationTypeCheckBox;
import com.example.new_androidclient.databinding.ActivityWorkApplicationBinding;
import com.example.new_androidclient.work.bean.WorkApplicationBaseDetailBean;
import com.example.new_androidclient.work.bean.WorkApplicationBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * zq
 * 作业申请主页面
 **/
@Route(path = RouteString.WorkApplicationActivity)
public class WorkApplicationActivity extends BaseActivity {
    private List<WorkApplicationLineLayout> itemList = new ArrayList<>();
    private List<WorkApplicationTypeCheckBox> workTypeLayoutList = new ArrayList<>();
    private ActivityWorkApplicationBinding binding;
    private WorkApplicationBaseDetailBean detailBean;
    private Listener listener = new Listener();
    private WorkApplicationBean bean = new WorkApplicationBean();//提交数据
    private List<String> checkBoxRouteList = new ArrayList<>(); //跳转的特殊作业类型页面route


    private String[] NameList = {"项目名称", "工单编号", "申请单位", "装置单元", "区域位号", "计划作业时间", "属地项目负责人", "作业人数", "质安环部安全员", "是否能量隔离"};
    private int[] NameTypeList = {1, 1, 1, 1, 1, 1, 1, 3, 2, 2};

    private String[] workTypeList = {"动火作业", "高处作业", "动土作业", "受限作业", "吊装作业", "管线打开作业", "临时用电作业","断路作业"};
    private String[] workTypeCodeList = {"DH", "GC", "DT", "SX", "DZ", "DK", "LD", "DL"};
    private String[] workTypeRouteList = {
            RouteString.WorkDHApplicationActivity,//动火作业
            RouteString.WorkGCApplicationActivity, //高处作业
            RouteString.WorkDTApplicationActivity, //动土作业
            RouteString.WorkSXApplicationActivity, //受限作业
            RouteString.WorkDZApplicationActivity, //吊装作业
            RouteString.WorkDKApplicationActivity, //管线作业
            RouteString.WorkLDApplicationActivity, //临时用电作业
            RouteString.WorkDLApplicationActivity, //断路作业
    };
    @Autowired
    int applicationId;

    @Autowired
    int planWorkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_application);
        binding.setListener(listener);
        getDetail();
    }

    private void setItemView() {
        for (int i = 0; i < NameList.length; i++) {
            addView(i);
        }
        for (int i = 0; i < workTypeList.length; i++) {
            addTypeView(i, workTypeCodeList[i], detailBean.getWorkType());
        }
    }

    private void addView(int position) {
        WorkApplicationLineLayout layout = new WorkApplicationLineLayout(mContext, position);
        layout.init(NameTypeList[position]);
        layout.setNameText(NameList[position]);
        switch (position) {
            case 0:  //项目名称
                layout.setNameText_text(detailBean.getSheetName());
                break;
            case 1:  //工单编号
                layout.setNameText_text(detailBean.getSheetNo());
                break;
            case 2:  //申请单位
                layout.setNameText_text(detailBean.getApplicationDept());
                break;
            case 3:  //装置单元
                layout.setNameText_text(detailBean.getInstName() + " " + detailBean.getUnitName());
                break;
            case 4:  //区域位号
                layout.setNameText_text(detailBean.getAreaName() + " " + detailBean.getTagNo());
                break;
            case 5:  //计划作业时间
                layout.setNameText_text(detailBean.getPlanStartDate() + "-" + detailBean.getPlanEndDate());
                break;
            case 6:  //属地项目负责人
                layout.setNameText_text(detailBean.getProjectLeaderName());
                break;
            case 7:  //作业人数
                layout.setEditText_text(detailBean.getWorkersNum());
                layout.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 8:  //治安环部安全员
                layout.setNameText_text(detailBean.getSafetyOfficerName());
                bean.setSafetyOfficer(Integer.valueOf(detailBean.getSafetyOfficer()));
                break;
            case 9:  //是否能量隔离
                layout.setNameText_text(detailBean.getEnergyIsolation());
                bean.setEnergyIsolation(detailBean.getEnergyIsolation());
                break;
        }

        layout.setOnClickListener(v -> {
            if (layout.getType() == 2) {
                switch (layout.getPos()) {
                    case 8:
                        ARouter.getInstance().build(RouteString.HazardAnalysisPersonSelectActivity)
                                .navigation(WorkApplicationActivity.this, 2);
                        break;
                    case 9:
                        EnergyIsolation();
                        break;
                }
            }
        });

        itemList.add(layout);
        binding.linearLayout.addView(layout);
    }

    private void EnergyIsolation() {
        new WorkConditionDialog(mContext, "4", (value, type1, position, dialog) -> {
            itemList.get(9).setNameText_text(value);
            if (value.equals("是")) { // (0否，1是)
                bean.setEnergyIsolation("1");
            } else {
                bean.setEnergyIsolation("0");
            }
            dialog.dismiss();
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            //bean里面设置安全员, 然后删除下面提交方法里的safetyLeader
            String userId = data.getStringExtra("userId");
            String name = data.getStringExtra("name");
            bean.setSafetyOfficer(Integer.valueOf(userId));
            itemList.get(8).setNameText_text(name);
        }
    }

    private void addTypeView(int position, String code, String workTypes) {
        WorkApplicationTypeCheckBox checkBox = new WorkApplicationTypeCheckBox(mContext, position, code);
        checkBox.setText(workTypeList[position]);

        if (workTypes != null) {
            if (workTypes.contains(checkBox.getCode())) {
                checkBox.setChecked(true);
            }
        }

        binding.checkboxLinear.addView(checkBox);
        workTypeLayoutList.add(checkBox);
    }

    private void getDetail() {
        RetrofitUtil.getApi().selectApplication(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<WorkApplicationBaseDetailBean>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(WorkApplicationBaseDetailBean bean) {
                        detailBean = bean;
                        setItemView();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //获取作业类型接口，因与获取基本信息接口异步，不方便处理页面checkbox选择问题，所以作业类型已在页面固定写死
//    private void getWorkType() {
//        RetrofitUtil.getApi().selectWorkingType()
//                .compose(new SchedulerTransformer<>())
//                .subscribe(new DialogObserver<List<WorkApplicationTypeBean>>(mContext, true, "正在获取数据") {
//                    @Override
//                    public void onSuccess(List<WorkApplicationTypeBean> bean) {
//                        workTypeCodeList.clear();
//                        workTypeList.clear();
//
//                        if (bean.size() > 0) {
//                            for (int i = 0; i < bean.size(); i++) {
//                                workTypeList.add(bean.get(i).getName());
//                                workTypeCodeList.add(bean.get(i).getCode());
//                            }
//                            setWorkTypeView();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String err) {
//                        ToastUtil.show(mContext, err);
//                    }
//                });
//    }

    private boolean checkAllFinish() {
        if (itemList.get(7).getEditText().getText().toString().isEmpty()) {
            ToastUtil.show(mContext, "请填写作业人数");
            return false;
        }
        if (itemList.get(8).getNameText_text().equals("请选择")) {
            ToastUtil.show(mContext, "请选择治安环部安全员");
            return false;
        }

        if (itemList.get(9).getNameText_text().equals("请选择")) {
            ToastUtil.show(mContext, "请选择是否能量隔离");
            return false;
        }


        int tempNum = 0;
        for (int i = 0; i < workTypeLayoutList.size(); i++) {
            if (workTypeLayoutList.get(i).isChecked()) {
                tempNum += 1;
            }
        }
        if (tempNum == 0) {
            ToastUtil.show(mContext, "请选择作业类型");
            return false;
        }
        return true;
    }

    private void submit() {
        bean.setPlanWorkId(planWorkId);
        bean.setId(applicationId);
        bean.setWorkersNum(Integer.valueOf(itemList.get(7).getEditText().getText().toString()));
        checkBoxRouteList.clear();
        StringBuilder type = new StringBuilder();
        for (int i = 0; i < workTypeLayoutList.size(); i++) {
            if (workTypeLayoutList.get(i).isChecked()) {
                checkBoxRouteList.add(workTypeRouteList[i]);
                if (!workTypeLayoutList.get(i).getCode().isEmpty()) {
                    type.append(workTypeLayoutList.get(i).getCode()).append(",");
                }
            }
        }
        bean.setWorkType(type.substring(0, type.length() - 1));

        RetrofitUtil.getApi().addApplication(bean)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String bean) {
                        ARouter.getInstance().build(checkBoxRouteList.get(0))
                                .withObject("checkBoxRouteList", checkBoxRouteList)  //选择的作业类型list
                                .withInt("ActivityIndex", 0)  //
                                .withInt("applicationId", applicationId)
                                .withInt("planWorkId", planWorkId)
                                .navigation();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    public class Listener {
        public void submitData() {
            if (checkAllFinish()) {
                submit();
            }
        }
    }


}
