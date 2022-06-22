package com.example.new_androidclient.work;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

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
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.WorkApplicationLineLayout;
import com.example.new_androidclient.databinding.ActivityWorkAppointmentBinding;
import com.example.new_androidclient.work.bean.WorkApplicationBaseDetailBean;
import com.example.new_androidclient.work.bean.WorkApplicationBean;
import com.example.new_androidclient.work.bean.WorkDHApplicationBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * zq
 * 作业预约基本信息页面
 * 页面跳转顺序 WorkAppointmentActivity - WorkDHAppointmentDetailActivity - WorkDHAppointmentActivity
 */
@Route(path = RouteString.WorkAppointmentActivity)
public class WorkAppointmentActivity extends BaseActivity {
    private WorkApplicationBaseDetailBean detailBean;
    private ActivityWorkAppointmentBinding binding;
    private Listener listener = new Listener();
    private WorkApplicationBean uploadBean = new WorkApplicationBean();

    private List<WorkApplicationLineLayout> itemList = new ArrayList<>();

    //    private String[] NameList = {"项目名称", "申请单位", "作业负责人", "装置单元", "区域位号", "计划作业时间", "预计作业人数", "动火作业安全措施方案",
//            "动火级别", "升级管理", "是否能量隔离"}; //是否能量隔离原来在本页面，后调整到作业申请时第一次填写，本页面能修改，本页"工艺隔离及盲板抽堵清单"项先注释
//    private int[] NameTypeList = {1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2};
    private String[] NameArray = {"项目名称", "申请单位", "作业负责人", "装置单元", "区域位号", "预计作业人数", "是否能量隔离"};
    private List<String> nameList = new ArrayList<>();
    private List<Integer> nameTypeList = new ArrayList<>();
    private Integer[] NameTypeArray = {1, 1, 1, 1, 1, 1, 2};
    private String[] workType = {"动火"};
    private String[] workTypeRoute = {RouteString.WorkDHAppointmentDetailActivity};

    private AlertDialog dialog;

    @Autowired
    boolean isSign;

    @Autowired
    int applicationId;

    @Autowired
    int planWorkId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_appointment);
        binding.setListener(listener);
        if (isSign) {  //如果是审批
            binding.submit.setVisibility(View.GONE);
            binding.agree.setVisibility(View.VISIBLE);
        }
        getDetail();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Collections.addAll(nameList, NameArray);
        Collections.addAll(nameTypeList, NameTypeArray);
    }

    private void getDetail() {
        RetrofitUtil.getApi().selectApplication(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<WorkApplicationBaseDetailBean>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(WorkApplicationBaseDetailBean bean) {
                        detailBean = bean;
                        binding.linear.removeAllViews();
                        itemList.clear();
                        if (detailBean.getWorkTypeName().contains(",")) {
                            String[] temp = detailBean.getWorkTypeName().split(",");
                            Collections.addAll(nameList, temp);
                            for (int i = 0; i < temp.length; i++) {
                                nameTypeList.add(2);
                            }
                        } else {
                            nameList.add(detailBean.getWorkTypeName());
                            nameTypeList.add(2);
                        }
                        setItemView();
                        //  getDHDetail();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void setItemView() {
        for (int i = 0; i < nameList.size(); i++) {
            addView(i);
        }

    }

    private void addView(int position) {
        WorkApplicationLineLayout layout = new WorkApplicationLineLayout(mContext, position);
        layout.init(nameTypeList.get(position));
        layout.setNameText(nameList.get(position));

        if (position == 0) {  //项目名称
            layout.setNameText_text(detailBean.getSheetName());
        } else if (position == 1) {  //申请单位
            layout.setNameText_text(detailBean.getDeptName());
        } else if (position == 2) {  //作业负责人
            layout.setNameText_text(detailBean.getWorkLeaderName());
        } else if (position == 3) {  //装置单元
            layout.setNameText_text(detailBean.getInstName() + " " + detailBean.getUnitName());
        } else if (position == 4) {  //区域位号
            layout.setNameText_text(detailBean.getAreaName() + " " + detailBean.getTagNo());
        } else if (position == 5) {  //作业人数
            layout.setNameText_text(detailBean.getWorkersNum());
        } else if (position == 6) { //是否能量隔离
            layout.setNameText_text(detailBean.getEnergyIsolation());
            if (detailBean.getEnergyIsolation().equals("是")) {
                uploadBean.setEnergyIsolation("1");
            } else {
                uploadBean.setEnergyIsolation("0");
            }
        } else {
            if (isSign) {
                layout.setNameText_text("请查看");
            } else {
                layout.setNameText_text("请填写");
            }
//        } else if (position == 11) {  //盲板清单  暂时注释，先不在页面显示
//            if (isSign) {
//                layout.setNameText_text("请查看");
//            } else {
//                layout.setNameText_text("请填写");
//            }
        }


        layout.setOnClickListener(v -> {
            if (!isSign) { //不是审批
//                if (layout.getPos() == 6) { //升级管理(0否，1是)
//                    isUpgradeMgt();
//                } else
                if (layout.getPos() == 6) { //是否能量隔离
                    energyIsolation();
                } else {
                    for (int i = 0; i < workType.length; i++) {
                        if (itemList.get(layout.getPos()).getName().getText().toString().contains(workType[i])) {
                            ARouter.getInstance().build(workTypeRoute[i])
                                    .withInt("lineNum", layout.getPos())
                                    .withInt("applicationId", applicationId)
                                    .navigation(this, layout.getPos());
                            break;
                        }
                    }
                }
            } else {  //是审批
                for (int i = 0; i < workType.length; i++) {
                    if (itemList.get(layout.getPos()).getName().getText().toString().contains(workType[i])) {
                        ARouter.getInstance().build(workTypeRoute[i])
                                .withInt("lineNum", layout.getPos())
                                .withInt("applicationId", applicationId)
                                .withBoolean("isSign", isSign)
                                .navigation(this, layout.getPos());
                        break;
                    }
                }
            }
//                    if (layout.getPos() == 11) { //盲板清单  暂时注释，先不在页面显示
//                        selectBlindPlate();
//                    }
        });
        binding.linear.addView(layout);
        itemList.add(layout);
    }

//        private void selectBlindPlate () {  //盲板清单 暂时注释，先不在页面显示
//            ARouter.getInstance().build(RouteString.WorkBlindPlate)
//                    .withInt("applicationId", applicationId)
//                    .withString("type", "DH")
//                    .withBoolean("isSign", isSign)
//                    .navigation(this, selectBlindPlate);
//        }

//        @Override
//        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
//            super.onActivityResult(requestCode, resultCode, data);
//            if (resultCode == selectBlindPlate) {  //盲板清单 暂时注释，先不在页面显示
//                if (itemList.size() == 12) {
//                    itemList.get(11).setNameText_text("已填写");
//                }
//            }
//        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        itemList.get(resultCode).setNameText_text("已填写");
    }

    private void energyIsolation() {  //是否能量隔离
        new WorkConditionDialog(mContext, "4", (value, type1, position, dialog) -> {
            itemList.get(6).setNameText_text(value);

            if (value.equals("是")) { // (0否，1是)
                uploadBean.setEnergyIsolation("1");
//                if (itemList.size() < 12) { 工艺清单先注释
//                    addView(NameList.length - 1);
//                }
            } else {
                uploadBean.setEnergyIsolation("0");
//                if (itemList.size() == NameList.length) {  //说明显示了盲板清单项
//                    binding.linear.removeView(itemList.get(NameList.length - 1));工艺清单先注释
//                    itemList.remove(NameList.length - 1);
//                }
            }
            dialog.dismiss();
        }).show();
    }


    private boolean checkAllFinish() {
        for (int i = 6; i < itemList.size(); i++) {
            if (itemList.get(i).getNameText_text().equals("请选择") || itemList.get(i).getNameText_text().equals("请填写")) {
                ToastUtil.show(mContext, "请填写完整");
                return false;
            }
        }
        return true;
    }

    private void updateApplicationStatus(String status, String msg) {
        RetrofitUtil.getApi().audit(applicationId, status, msg)  //23 作业审批  25审核拒绝
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

    //提交预约基本信息
    private void update() {
        uploadBean.setId(applicationId);
        uploadBean.setPlanWorkId(planWorkId);
        RetrofitUtil.getApi().addApplication(uploadBean)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String bean) {
                        ToastUtil.show(mContext, "提交成功，请审核");
                        binding.agree.setVisibility(View.VISIBLE);
                        binding.submit.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void showDialog() {
        dialog = new AlertDialog.Builder(this).setPositiveButton("同意", null).setNegativeButton("拒绝", null).create();
        dialog.setTitle("请填写意见");

        Window win = dialog.getWindow();
        win.getDecorView().setPadding(10, 20, 10, 20);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

        View view = getLayoutInflater().inflate(R.layout.hazard_plan_sign_dialog, null);
        final EditText msg_edit = view.findViewById(R.id.hazard_plan_sign_dialog_edittext);
        dialog.setView(view);//设置login_layout为对话提示框
        dialog.setCancelable(true);//设置为不可取消
        dialog.setOnShowListener(mDialog -> {
            Button positionButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            positionButton.setOnClickListener(v -> {
                if (isSign) {  //如果是审批
                    updateApplicationStatus("30", msg_edit.getText().toString().trim());  //30审批通过
                    dialog.dismiss();
                } else {
                    updateApplicationStatus("23", msg_edit.getText().toString().trim());  //23审核通过
                    dialog.dismiss();
                }

            });
            negativeButton.setOnClickListener(v -> {
                if (msg_edit.getText().toString().trim().isEmpty()) {
                    ToastUtil.show(mContext, "请填写意见");
                } else {
                    if (isSign) {  //如果是审批
                        updateApplicationStatus("26", msg_edit.getText().toString().trim());  //26审批拒绝
                        dialog.dismiss();
                    } else {
                        updateApplicationStatus("25", msg_edit.getText().toString().trim());  //25审核拒绝
                        dialog.dismiss();
                    }

                }
            });
        });
        dialog.show();
    }

    public class Listener {
        public void submit() {
            if (checkAllFinish()) {
                update();
            }
        }

        public void agree() {
            showDialog();
        }
    }

}
