package com.example.new_androidclient.work;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.new_androidclient.databinding.ActivityWorkDHAppointmentDetailBinding;
import com.example.new_androidclient.work.bean.HotWorkBean;
import com.example.new_androidclient.work.bean.NameBean;
import com.example.new_androidclient.work.bean.WorkDHApplicationBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;

import org.jaaksi.pickerview.picker.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.jaaksi.pickerview.picker.TimePicker.TYPE_ALL;

/**
 * zq
 * 动火预约详情页面，在基本预约页面后显示关于动火的页面
 * 页面跳转顺序 WorkAppointmentActivity - WorkDHAppointmentDetailActivity - WorkDHAppointmentActivity
 **/

@Route(path = RouteString.WorkDHAppointmentDetailActivity)
public class WorkDHAppointmentDetailActivity extends BaseActivity {
    private ActivityWorkDHAppointmentDetailBinding binding;
    private WorkDHApplicationBean dhBean;
    private WorkDHApplicationBean uploadBean = new WorkDHApplicationBean();
    private List<WorkApplicationLineLayout> itemList = new ArrayList<>();
    private String[] nameList = {"动火级别", "作业计划开始时间", "作业计划结束时间", "升级管理", "选择可燃气介质", "动火作业安全措施方案"};
    private int[] nameTypeList = {2, 2, 2, 2, 2, 2};

    private Listener listener = new Listener();

    private int dh = 7;
    private int selectBlindPlate = 2;

    List<NameBean> analysisList;

    private String analysisName;
    private int analysisId;

    private String splicingAnalysisName = null;
    private String splicingAnalysisId;

    String time;

    @Autowired
    int applicationId;

    @Autowired
    Boolean isSign = false;  //是不是审核

    @Autowired
    int lineNum; //预约基本页面WorkAppointmentActivity的行数，用于本页面完成后WorkAppointmentActivity的某个作业预约改成已完成


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_d_h_appointment_detail);
        binding.setListener(listener);
        getDHDetail();
        getHotView();
        if (isSign) {
            binding.nextDevice.setVisibility(View.GONE);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        time = simpleDateFormat.format(date);
    }

    //查询选中的介质
    private void getHotView() {
        RetrofitUtil.getApi().selectWorkingDhAnalysis(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HotWorkBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<HotWorkBean> beans) {
                        if (beans != null && beans.size() > 0) {
                            StringBuffer buf = new StringBuffer();
                            StringBuffer bufId = new StringBuffer();
                            for (int i = 0; i < beans.size(); i++) {
                                analysisName = beans.get(i).getAnalysisItem();
                                analysisId = beans.get(i).getMediumId();

                                buf.append(analysisName).append(",");
                                if (buf.length() > 0) {
                                    splicingAnalysisName = buf.substring(0, buf.length() - 1);
                                }
                                bufId.append(analysisId).append(",");
                                if (bufId.length() > 0) {
                                    splicingAnalysisId = bufId.substring(0, bufId.length() - 1);
                                }
                            }

                            for (int i = 0; i < itemList.size(); i++) {
                                itemList.get(4).setNameText_text(splicingAnalysisName);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //获取动火数据
    private void getDHDetail() {
        RetrofitUtil.getApi().selectApplicationDh(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<WorkDHApplicationBean>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(WorkDHApplicationBean bean) {
                        if (bean != null) {
                            dhBean = bean;
                            for (int i = 0; i < nameList.length; i++) {
                                addView(i);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void addView(int position) {
        WorkApplicationLineLayout layout = new WorkApplicationLineLayout(mContext, position);
        layout.init(nameTypeList[position]);
        layout.setNameText(nameList[position]);

        switch (position) {
            case 0:  //动火级别
                layout.setNameText_text(dhBean.getFireLevel());
                uploadBean.setFireLevel(dhBean.getFireLevel());
                break;
            case 1:  //作业计划开始时间
                layout.setNameText_text(dhBean.getWorkingStartTime());
                uploadBean.setWorkingStartTime(dhBean.getWorkingStartTime() + ":00");
                break;
            case 2:  //作业计划结束时间
                layout.setNameText_text(dhBean.getWorkingEndTime());
                uploadBean.setWorkingEndTime(dhBean.getWorkingEndTime() + ":00");
                break;
            case 3:  //是否升级管理
                layout.setNameText_text(dhBean.getIsUpgradeMgt());
                break;
            case 4:  //介质
                if (splicingAnalysisName == null || splicingAnalysisName.equals("")){
                    layout.setNameText_text("请选择");
                }else {
                    layout.setNameText_text(splicingAnalysisName);
                }
                break;
            case 5:  //动火作业安全措施方案
                if (isSign) {
                    layout.setNameText_text("请查看");
                } else {
                    layout.setNameText_text("请填写");
                }

                break;
        }

        layout.setOnClickListener(v -> {
            if(layout.getPos() != 4 && isSign){
                return;
            }
            switch (layout.getPos()) {
                case 0:  //动火级别
                    fireLevel();
                    break;
                case 1:  //作业计划开始时间
                    pickTime(true,time);
                    break;
                case 2:  //作业计划结束时间
                    if (itemList.get(1).getNameText_text().equals("请选择")) {
                        ToastUtil.show(mContext,"请先选择开始时间");
                    }else {
                        pickTime(false, itemList.get(1).getNameText_text() + ":00");
                    }
                    break;
                case 3:  //升级管理
                    isUpgradeMgt();
                    break;
                case 4:  //分析项目
                    AnalysisProject();
                    break;
                case 5:  //动火作业安全措施方案
                    dh();
                    break;
            }
        });

        binding.linear.addView(layout);
        itemList.add(layout);
    }

    private void AnalysisProject() {
        Intent intent = new Intent(mContext,AnalysisProjectActivity.class);
        startActivityForResult(intent,111);
    }

    private void isUpgradeMgt() {   //升级管理(0否，1是)
        new WorkConditionDialog(mContext, "4", (value, type1, position, dialog) -> {
            itemList.get(3).setNameText_text(value);
            if (value.equals("是")) { // (0否，1是)
                uploadBean.setIsUpgradeMgt("1");
            } else {
                uploadBean.setIsUpgradeMgt("0");
            }
            dialog.dismiss();
        }).show();
    }

    private void dh() {  //动火作业
        ARouter.getInstance().build(RouteString.WorkDHAppointmentActivity)
                .withInt("applicationId", applicationId)
                .withInt("status", 1)
                .withBoolean("isSign", isSign)
                .withString("workType", "DH")
                .navigation(this, dh);  //动火作业
    }

    private void fireLevel() {  //动火级别
        new WorkConditionDialog(mContext, "5", (value, type1, position, dialog) -> {
            itemList.get(0).setNameText_text(value);
            uploadBean.setFireLevel(value);// 0特级  1一级  2二级
            dialog.dismiss();
        }).show();
    }

    //提交动火信息
    private void update() {
        uploadBean.setApplicationId(applicationId);
        uploadBean.setId(dhBean.getId());
        RetrofitUtil.getApi().addApplicationDhAnalysis(uploadBean,splicingAnalysisId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String bean) {
                        setResult(lineNum);
                        finish();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void pickTime(boolean isStartTime, String startTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        TimePicker mTimePicker = null;
        if (isStartTime) { //如果是开始时间
            try {
                mTimePicker = new TimePicker.Builder(this, TYPE_ALL, (picker, date) -> {
                    itemList.get(1).setNameText_text(formatter.format(date));
                    uploadBean.setWorkingStartTime(formatter.format(date) + ":00");
                    if (!itemList.get(2).getNameText_text().equals("请选择")) {
                        try {
                            if (formatter.parse(itemList.get(2).getNameText_text()).getTime() <= date.getTime()) {
                                itemList.get(2).setNameText_text("请选择");
                                uploadBean.setWorkingEndTime(null);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }).setRangDate(Objects.requireNonNull(formatter.parse(startTime)).getTime(), 1893563460000L).create();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else { //结束时间
            try {
                mTimePicker = new TimePicker.Builder(this, TYPE_ALL, (picker, date) -> {
                    // LogUtil.i(formatter.format(date));
                    itemList.get(2).setNameText_text(formatter.format(date));
                    uploadBean.setWorkingEndTime(formatter.format(date) + ":00");
                }).setRangDate(Objects.requireNonNull(formatter.parse(startTime)).getTime(), 1893563460000L).create();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        mTimePicker.show();
    }


    public class Listener {
        public void submit() {
            if (!itemList.get(0).getNameText_text().equals("请选择") || !itemList.get(4).getNameText_text().equals("请选择")) {
                update();
            } else {
                ToastUtil.show(mContext, "请选择动火级别");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == 111) {
            if (data != null) {
                analysisList = (List<NameBean>) data.getSerializableExtra("analysisList");
                StringBuffer buf = new StringBuffer();
                StringBuffer bufId = new StringBuffer();
                for (int i = 0; i < analysisList.size(); i++) {
                    analysisName = analysisList.get(i).getName();
                    analysisId = analysisList.get(i).getNameId();

                    buf.append(analysisName).append(",");
                    if (buf.length() > 0) {
                        splicingAnalysisName = buf.substring(0, buf.length() - 1);
                    }
                    bufId.append(analysisId).append(",");
                    if (bufId.length() > 0) {
                        splicingAnalysisId = bufId.substring(0, bufId.length() - 1);
                    }
                }

                for (int i = 0; i < itemList.size(); i++) {
                    itemList.get(4).setNameText_text(splicingAnalysisName);
                }
            }
        }
    }
}
