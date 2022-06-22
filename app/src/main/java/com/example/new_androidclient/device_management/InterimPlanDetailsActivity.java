package com.example.new_androidclient.device_management;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.EventBusMessage.EventBusMessage;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.DateTimePickDialogUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.device_management.Data.AccountSecurityUnBindDialog;
import com.example.new_androidclient.device_management.Data.ObjectUtils;
import com.example.new_androidclient.device_management.bean.InterimPlanDetailsBean;
import com.example.new_androidclient.device_management.bean.UpDataPlanBean;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 临时工作列表详情
 **/
@Route(path = RouteString.InterimPlanDetailsActivity)
public class InterimPlanDetailsActivity extends BaseActivity implements View.OnClickListener {

    DateTimePickDialogUtil dateTimePicKDialog;
    SimpleDateFormat simpleDateFormat;
    @BindView(R.id.device1)
    TextView device1;
    @BindView(R.id.device2)
    TextView device2;
    @BindView(R.id.device3)
    TextView device3;
    @BindView(R.id.device4)
    TextView device4;
    @BindView(R.id.device5)
    TextView device5;
    @BindView(R.id.device6)
    TextView device6;
    @BindView(R.id.device7)
    TextView device7;
    @BindView(R.id.device8)
    TextView device8;
    @BindView(R.id.device9)
    TextView device9;

    @BindView(R.id.starting_time)
    TextView startingTime;
    @BindView(R.id.start_time_layout)
    RelativeLayout startTimeLayout;
    @BindView(R.id.completion_time)
    TextView completionTime;
    @BindView(R.id.start_no_time_layout)
    RelativeLayout startNoTimeLayout;

    @BindView(R.id.planNameEdit)
    EditText planNameEdit;
    @BindView(R.id.workGenreText)
    TextView workGenreText;
    @BindView(R.id.workGenreLayout)
    RelativeLayout workGenreLayout;
    @BindView(R.id.wordEdit)
    EditText wordEdit;
    @BindView(R.id.projectLeaderText)
    TextView projectLeaderText;
    @BindView(R.id.projectLeaderLayout)
    RelativeLayout projectLeaderLayout;
    @BindView(R.id.contractorText)
    TextView contractorText;
    @BindView(R.id.contractorLayout)
    RelativeLayout contractorLayout;
    @BindView(R.id.workLeaderText)
    TextView workLeaderText;
    @BindView(R.id.workLeaderLayout)
    RelativeLayout workLeaderLayout;
    @BindView(R.id.budgetValueEdit)
    EditText budgetValueEdit;
    @BindView(R.id.workStatus)
    TextView workStatus;
    @BindView(R.id.handlerText)
    TextView handlerText;
    @BindView(R.id.handler)
    RelativeLayout handler;
    @BindView(R.id.reasonsText)
    TextView reasonsText;
    @BindView(R.id.workText)
    TextView workText;
    @BindView(R.id.device_no)
    LinearLayout deviceNo;
    @BindView(R.id.agree)
    Button agree;
    @BindView(R.id.noAgree)
    Button noAgree;
    @BindView(R.id.buttonLayout)
    LinearLayout buttonLayout;
    @BindView(R.id.device9Layout)
    RelativeLayout device9Layout;
    @BindView(R.id.plan)
    LinearLayout plan;
    @BindView(R.id.project)
    LinearLayout project;
    @BindView(R.id.handlerText1)
    TextView handlerText1;
    @BindView(R.id.handler1)
    RelativeLayout handler1;

    private String time;
    private int temporaryId;
    String value;

    UpDataPlanBean valueBean = new UpDataPlanBean();

    int dptId;

    int nameId;

    int nameId2;

    int projectLeader;
    int workContractor;

    String name;

    String contractorName;
    String workPeopleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interim_plan_details);
        ButterKnife.bind(this);
        startTimeLayout.setOnClickListener(this);
        startNoTimeLayout.setOnClickListener(this);
        noAgree.setOnClickListener(this);
        agree.setOnClickListener(this);
        workGenreLayout.setOnClickListener(this);
        projectLeaderLayout.setOnClickListener(this);
        workLeaderLayout.setOnClickListener(this);
        contractorLayout.setOnClickListener(this);
        getView();
        getSelectTemporaryPlanInfoView();
    }

    private void getView() {
        temporaryId = getIntent().getIntExtra("temporaryId", temporaryId);

    }

    private void getSelectTemporaryPlanInfoView() {
        RetrofitUtil.getApi().selectTemporaryPlanInfo(temporaryId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<InterimPlanDetailsBean>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(InterimPlanDetailsBean bean) {
                        if (bean != null) {
                            setDeviceValue(bean);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    public void getUpdatePlanStatus(String vlaues){
        RetrofitUtil.getApi().updatePlanStatus(temporaryId,vlaues)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据"){

                    @Override
                    public void onSuccess(String bean) {
                        EventBus.getDefault().post(EventBusMessage.getInstance("CHANGE"));
                    }

                    @Override
                    public void onFailure(String err) {

                    }
                });
    }

    //弹框
    public void showDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_account_security_unbind, null);
        TextView cancel =view.findViewById(R.id.confirmTView);
        TextView sure =view.findViewById(R.id.closeTView);
        final EditText edittext =view.findViewById(R.id.contentEdit);
        TextView titleTView = view.findViewById(R.id.titleTView);
        final Dialog dialog= builder.create();

        titleTView.setText("拒绝原因");

        dialog.show();
        dialog.getWindow().setContentView(view);
        //使editText可以唤起软键盘
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = edittext.getText().toString();
                getUpdatePlanStatus(value);
                dialog.dismiss();

                finish();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_time_layout:
                showDatePicker();
                break;
            case R.id.start_no_time_layout:
                showCompletionDatePicker();
                break;
            case R.id.workGenreLayout:
                Intent intent = new Intent(mContext,NewActivity.class);
                intent.putExtra("type","1");
                startActivityForResult(intent,1);
                break;
            case R.id.projectLeaderLayout:
                Intent intent1 = new Intent(mContext,NewActivity.class);
                intent1.putExtra("type","2");
                startActivityForResult(intent1,2);
                break;
            case R.id.contractorLayout:
                Intent intent2 = new Intent(mContext,NewActivity.class);
                intent2.putExtra("type","3");
                startActivityForResult(intent2,3);
                break;
            case R.id.workLeaderLayout:
                Intent intent3 = new Intent(mContext,NewActivity.class);
                intent3.putExtra("type","4");
                startActivityForResult(intent3,4);
                break;
            case R.id.noAgree:
                showDialog();
                break;
            case R.id.agree:
                try {
                    setUpDatePlan();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }

//    !planNameEdit.getText().equals("空") && planNameEdit.getText() != null &&
//            !workLeaderText.getText().equals("空") && workLeaderText.getText() != null &&
//            !wordEdit.getText().equals("空") && wordEdit.getText() != null &&
//            !startingTime.getText().equals("空") && startingTime.getText() != null &&
//            !completionTime.getText().equals("空") && completionTime.getText() != null &&
//            !projectLeaderText.getText().equals("空") && projectLeaderText != null &&
//            !contractorText.getText().equals("空") && contractorText.getText() != null &&
//            !workLeaderText.getText().equals("空") && workLeaderText.getText() != null &&
//            !budgetValueEdit.getText().equals("空") && budgetValueEdit.getText() != null




    private void setUpDatePlan() throws Exception {
            if (planNameEdit.getText().equals("空") || TextUtils.isEmpty(planNameEdit.getText())){
                ToastUtil.show(mContext,"计划名称不能为空！");
            }else {
                valueBean.setTemporaryName(planNameEdit.getText().toString());
            }

            if (workGenreText.getText().equals("空") || TextUtils.isEmpty(workGenreText.getText())){
                ToastUtil.show(mContext,"工单类别不能为空！");
            }else {
                if (workGenreText.getText().equals("抢修")){
                    valueBean.setPlanExtType("029");
                }else {
                    valueBean.setPlanExtType("030");
                }
            }

            if (wordEdit.getText().equals("空") || TextUtils.isEmpty(wordEdit.getText())){
                ToastUtil.show(mContext,"工程量描述不能为空！");
            }else {
                valueBean.setDescription(wordEdit.getText().toString());
            }

            if (startingTime.getText().equals("空") || TextUtils.isEmpty(startingTime.getText())){
                ToastUtil.show(mContext,"开始时间不能为空！");
            }else {
                String startingTime1 = startingTime.getText().toString();
//        Date startTime = format.parse(startingTime1);
//        String start = format.format(startTime);
                valueBean.setPlanStartTime(startingTime1);
            }

            if (completionTime.getText().equals("空") || TextUtils.isEmpty(completionTime.getText())){
                ToastUtil.show(mContext,"结束时间不能为空！");
            }else {
                String completion_time = completionTime.getText().toString();
//        Date completion_time1 = format.parse(completion_time);
//        String completion = format.format(completion_time1);
                valueBean.setPlanEndTime(completion_time);
            }

            if (projectLeaderText.getText().equals("空") || TextUtils.isEmpty(projectLeaderText.getText())){
                ToastUtil.show(mContext,"项目负责人不能为空！");
            }else {
                if (nameId != 0){
                    valueBean.setProjectLeader(nameId);
                }else {
                    valueBean.setProjectLeader(projectLeader);
                }
            }

            if (contractorText.getText().equals("空") || TextUtils.isEmpty(contractorText.getText())){
                ToastUtil.show(mContext,"作业承包商不能为空！");
            }else {
                if (nameId2 != 0){
                    valueBean.setWorkContractor(nameId2);
                }else {
                    valueBean.setWorkContractor(workContractor);
                }
            }

            if (workLeaderText.getText().equals("空") || TextUtils.isEmpty(workLeaderText.getText())){
                ToastUtil.show(mContext,"作业负责人不能为空！");
            }else {
                valueBean.setWorkLeaderName(workLeaderText.getText().toString());
            }

            if (budgetValueEdit.getText().equals("请输入设备编码") || TextUtils.isEmpty(budgetValueEdit.getText())){
                ToastUtil.show(mContext,"预算值不能为空！");
            }else {
                float value = Float.parseFloat(budgetValueEdit.getText().toString());
                valueBean.setBudget(value);
            }

            valueBean.setTemporaryId(temporaryId);

            boolean ret = ObjectUtils.checkFieldAllNull(valueBean);
            if (true == ret){
                updatePlan();
            }
    }

    private void updatePlan() {
        RetrofitUtil.getApi().upDatePlan(valueBean)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交参数"){
                    @Override
                    public void onSuccess(String bean) {
                        showAgreeDialog("1","提示");
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext,err);
                    }
                });
    }

    private void showAgreeDialog(String type,String tel) {
        new AccountSecurityUnBindDialog(mContext, R.style.dialog, type, tel, new AccountSecurityUnBindDialog.OnCloseListener() {

            @Override
            public void onConfirm(Dialog dialog) {
                addWork();
                dialog.dismiss();
            }

            @Override
            public void onClose(Dialog dialog) {
                dialog.dismiss();
            }

        }).show();
    }

    private void addWork() {
        RetrofitUtil.getApi().addWork(temporaryId,dptId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("计划下达成功")) {
                            ToastUtil.show(mContext, bean);
                            EventBus.getDefault().post(EventBusMessage.getInstance("VALUE"));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


    //临时计划详情设置值
    public void setDeviceValue(InterimPlanDetailsBean bean) {

        if (bean.getWorkContractor() != null){
            projectLeader = bean.getProjectLeader();
        }

        if (bean.getWorkContractor() != null){
            workContractor = bean.getWorkContractor();
        }

        dptId = bean.getDptId();

        device1.setText(bean.getFactoryName());
        device2.setText(bean.getDptName());
        device3.setText(bean.getInstName());
        device4.setText(bean.getUnitName());
        device5.setText(bean.getAreaName());
        device6.setText(bean.getDeviceName());
        device7.setText(bean.getTagNo());
        device8.setText(bean.getDeviceNo());
        if ("待下达".equals(bean.getPlanStatus())) {
            if (bean.getProblemDesc()==null) {
                device9.setText("空");
            } else {
                device9.setText(bean.getProblemDesc().toString());
            }

            if ("null".equals(bean.getTemporaryName())){
                planNameEdit.setText("空");
            }else {
                planNameEdit.setText(bean.getTemporaryName());
            }

            if (bean.getPlanExtType() == null){
                workGenreText.setText("空");
            }else {
                workGenreText.setText(bean.getPlanExtType().toString());
            }

            if (bean.getDescription() == null){
                wordEdit.setText("空");
            }else {
                wordEdit.setText(bean.getDescription().toString());
            }

            if (bean.getProjectLeaderName() == null){
                projectLeaderText.setText("空");
            }else {
                projectLeaderText.setText(bean.getProjectLeaderName().toString());
            }

            if (bean.getWorkContractorName() == null){
                contractorText.setText("空");
            }else {
                contractorText.setText(bean.getWorkContractorName().toString());
            }

            if (bean.getWorkLeaderName() == null){
                workLeaderText.setText("空");
            }else {
                workLeaderText.setText(bean.getWorkLeaderName().toString());
            }

            if (bean.getBudget() == null){
                budgetValueEdit.setText("空");
            }else {
                budgetValueEdit.setText(bean.getBudget().toString());
            }

            workStatus.setText(bean.getPlanStatus());


        } else if ("已下达".equals(bean.getPlanStatus())) {

            planNameEdit.setEnabled(false);
            wordEdit.setEnabled(false);
            budgetValueEdit.setEnabled(false);
            workGenreLayout.setEnabled(false);
            startTimeLayout.setEnabled(false);
            startNoTimeLayout.setEnabled(false);
            projectLeaderLayout.setEnabled(false);
            contractorLayout.setEnabled(false);
            workLeaderLayout.setEnabled(false);

            device9Layout.setVisibility(View.GONE);

            if ("null".equals(bean.getTemporaryName())) {
                planNameEdit.setText("空");
            } else {
                planNameEdit.setText(bean.getTemporaryName());
            }

            if (bean.getPlanExtType()==null) {
                workGenreText.setText("空");
            } else {
                workGenreText.setText(bean.getPlanExtType().toString());
            }

            if (bean.getDescription()==null) {
                wordEdit.setText("空");
            } else {
                wordEdit.setText(bean.getDescription().toString());
            }

            if (bean.getPlanStartTime() == null) {
                startingTime.setText("空");
            } else {
                startingTime.setText(bean.getPlanStartTime().toString());
            }

            if (bean.getPlanEndTime() == null) {
                completionTime.setText("空");
            } else {
                completionTime.setText(bean.getPlanEndTime().toString());
            }

            if (bean.getProjectLeaderName()==null) {
                projectLeaderText.setText("空");
            } else {
                projectLeaderText.setText(bean.getProjectLeaderName().toString());
            }

            if (bean.getWorkContractorName()==null) {
                contractorText.setText("空");
            } else {
                contractorText.setText(bean.getWorkContractorName().toString());
            }

            if (bean.getWorkLeaderName()==null) {
                workLeaderText.setText("空");
            } else {
                workLeaderText.setText(bean.getWorkLeaderName().toString());
            }

            if (bean.getBudget()==null) {
                budgetValueEdit.setText("空");
            } else {
                budgetValueEdit.setText(bean.getBudget().toString());
            }


            if (bean.getHandleUserName()==null) {
                handler.setVisibility(View.GONE);
            } else {
                handler.setVisibility(View.VISIBLE);
                handlerText.setText(bean.getHandleUserName().toString());
            }

            if ("null".equals(bean.getPlanStatus())) {
                workStatus.setText("空");
            } else {
                workStatus.setText(bean.getPlanStatus());
            }

            buttonLayout.setVisibility(View.GONE);
        } else if ("未下达".equals(bean.getPlanStatus())) {

            planNameEdit.setEnabled(false);
            wordEdit.setEnabled(false);
            budgetValueEdit.setEnabled(false);
            workGenreLayout.setEnabled(false);
            startTimeLayout.setEnabled(false);
            startNoTimeLayout.setEnabled(false);
            projectLeaderLayout.setEnabled(false);
            contractorLayout.setEnabled(false);
            workLeaderLayout.setEnabled(false);

            if (bean.getProblemDesc()==null) {
                device9.setText("空");
            } else {
                device9.setText(bean.getProblemDesc().toString());
            }

            plan.setVisibility(View.GONE);
            project.setVisibility(View.GONE);
            deviceNo.setVisibility(View.VISIBLE);

            if (bean.getHandleUserName()==null) {
                handlerText1.setText("空");
            } else {
                handlerText1.setText(bean.getHandleUserName().toString());
            }

            if (bean.getRejectReason()==null) {
                reasonsText.setText("空");
            } else {
                reasonsText.setText(bean.getRejectReason().toString());
            }

            if ("null".equals(bean.getPlanStatus())) {
                workText.setText("空");
            } else {
                workText.setText(bean.getPlanStatus());
            }

            buttonLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 开始日期选择
     */
    @SuppressLint("SimpleDateFormat")
    public void showDatePicker() {
        time = startingTime.getText().toString();
        if (time.length() > 0 && !"请选择起始时间".equals(time)) {
            String str[] = time.split("-");
            Date date;
            simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            date = new Date(System.currentTimeMillis());
            dateTimePicKDialog = new DateTimePickDialogUtil(
                    this, simpleDateFormat.format(date), simpleDateFormat.format(date));

            AlertDialog dialog = dateTimePicKDialog.dateTimePicKDialog(startingTime);
            dialog.show();
        } else {
            Date date;
            simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            date = new Date(System.currentTimeMillis());
            dateTimePicKDialog = new DateTimePickDialogUtil(
                    this, simpleDateFormat.format(date), simpleDateFormat.format(date));

            AlertDialog dialog = dateTimePicKDialog.dateTimePicKDialog(startingTime);
            dialog.show();
        }


//        ShowDatePick showDatePick = new ShowDatePick();
//        showDatePick.showDatePickerDialog(mContext,2,startingTime,);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            name = data.getStringExtra("name");
            nameId = data.getIntExtra("nameId",nameId);
            nameId2 = data.getIntExtra("nameId2",nameId2);
            contractorName = data.getStringExtra("contractorName");
            workPeopleName = data.getStringExtra("workPeopleName");
        }
        if (1 == resultCode && requestCode == 1){
            workGenreText.setText("抢修");
        }else if(2 == resultCode && requestCode == 1){
            workGenreText.setText("未抢修");
        }

        if (3 == resultCode && requestCode == 2){
            projectLeaderText.setText(name);
        }

        if (4 == resultCode && 3 == requestCode){
            contractorText.setText(contractorName);
        }

        if (4 == requestCode && 5 == resultCode){
            workLeaderText.setText(workPeopleName);
        }
    }

    /**
     * 结束日期选择
     */
    @SuppressLint("SimpleDateFormat")
    public void showCompletionDatePicker() {
        time = completionTime.getText().toString();
        if (time.length() > 0 && !"请选择起始时间".equals(time)) {
            String str[] = time.split("-");
            Date date;
            simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            date = new Date(System.currentTimeMillis());
            dateTimePicKDialog = new DateTimePickDialogUtil(
                    this, simpleDateFormat.format(date), simpleDateFormat.format(date));

            AlertDialog dialog = dateTimePicKDialog.dateTimePicKDialog(completionTime, Integer.valueOf(str[0]), Integer.valueOf(str[1]), Integer.valueOf(str[2]));
            dialog.show();
        } else {
            Date date;
            simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            date = new Date(System.currentTimeMillis());
            dateTimePicKDialog = new DateTimePickDialogUtil(
                    this, simpleDateFormat.format(date), simpleDateFormat.format(date));

            AlertDialog dialog = dateTimePicKDialog.dateTimePicKDialog(completionTime);
            dialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
