package com.example.new_androidclient.work;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;

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
import com.example.new_androidclient.databinding.ActivityWorkLDApplicationBinding;
import com.example.new_androidclient.work.bean.WorkApplicationBaseDetailBean;
import com.example.new_androidclient.work.bean.WorkDKApplicationBean;
import com.example.new_androidclient.work.bean.WorkLDApplicationBean;

import org.jaaksi.pickerview.picker.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.jaaksi.pickerview.picker.TimePicker.TYPE_ALL;

/**
 * zq
 * 临时用电申请
 **/

@Route(path = RouteString.WorkLDApplicationActivity)
public class WorkLDApplicationActivity extends BaseActivity {
    private ActivityWorkLDApplicationBinding binding;
    private WorkLDApplicationBean uploadBean;
    private WorkApplicationBaseDetailBean baseDetailBean;

    private List<WorkApplicationLineLayout> itemList = new ArrayList<>();
    private Listener listener = new Listener();

    private int riskIdentify = 1;
    private int workingGuardianName = 2;
    private int workingVoltage = 3;
    private int isSafetyMeans = 4;

    private List<String> workingVoltageList = new ArrayList<>();
    private List<String> riskIdentifyList = new ArrayList<>();

    //用于处理本页面的index，thisActivityIndex最开始时和ActivityIndex相等，
    //若下一页操作则使用thisActivityIndex，用FC于处理返回页面时index异常的问题
    private int thisActivityIndex;

    @Autowired
    int ActivityIndex;

    @Autowired
    int applicationId;

    @Autowired
    int planWorkId;

    @Autowired
    List<String> checkBoxRouteList;

    private String[] NameList = {"作业地点", "工作电压", "电源接入点",
            "许可用电功率(KW)", "风险辨识结果",
            "作业监护人", "安全措施",
            "作业开始时间", "作业结束时间"};
    private int[] NameTypeList = {1, 2, 3,
            3, 2,
            2, 2, 2,
            2, 2};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_l_d_application);
        binding.setListener(listener);
        getBaseDetail();
    }

    private void getBaseDetail() {
        RetrofitUtil.getApi().selectApplication(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<WorkApplicationBaseDetailBean>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(WorkApplicationBaseDetailBean bean) {
                        baseDetailBean = bean;
                        getDetail();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void getDetail() {
        RetrofitUtil.getApi().selectLdByApplicationId(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<WorkLDApplicationBean>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(WorkLDApplicationBean bean) {
                        binding.linearLayout.removeAllViews();
                        itemList.clear();
                        uploadBean = bean;
                        uploadBean.setApplicationId(applicationId);
                        for (int i = 0; i < NameList.length; i++) {
                            addItemView(i);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void addItemView(int position) {
        WorkApplicationLineLayout layout = new WorkApplicationLineLayout(mContext, position);
        layout.init(NameTypeList[position]);
        layout.setNameText(NameList[position]);

        switch (position) {
            case 0:
                layout.setNameText_text(baseDetailBean.getAreaName() + " " + baseDetailBean.getTagNo()); //作业地点
                break;
            case 1:
                layout.setNameText_text(uploadBean.getWorkingVoltage()); //工作电压
                break;
            case 2:
                layout.setEditText_text(uploadBean.getPowerAccessPoint()); //电源接入点
                break;
            case 3:
                layout.setEditText_text(uploadBean.getPermitKw()); //许可用电功率
                layout.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 4:
                layout.setNameText_text(uploadBean.getRiskIdentify()); //风险辨识结果
                break;
            case 5:
                layout.setNameText_text(uploadBean.getWorkingGuardianName());//作业监护人
                break;
            case 6:
                layout.setNameText_text("请选择");//安全措施
                break;
            case 7:
                layout.setNameText_text(uploadBean.getWorkingStartTime());//开始时间
                break;
            case 8:
                layout.setNameText_text(uploadBean.getWorkingEndTime());//结束时间
                break;
        }

        layout.setOnClickListener(v -> {
            if (layout.getPos() == 5) { //作业监护人
                ARouter.getInstance().build(RouteString.HazardAnalysisPersonSelectActivity)
                        .navigation(WorkLDApplicationActivity.this, workingGuardianName);
            } else if (layout.getPos() == 1) {//工作电压
                workingVoltage();
            } else if (layout.getPos() == 4) {//风险辨识结果
                riskIdentify();
            } else if (layout.getPos() == 6) {//安全措施
                IsSafetyMeans();
            } else if (layout.getPos() == 7) { //开始时间
                pickTime(true, "");
            } else if (layout.getPos() == 8) { //结束时间
                if (itemList.get(7).getNameText_text().equals("请选择")) {
                    ToastUtil.show(mContext, "请选择开始时间");
                } else {
                    pickTime(false, itemList.get(7).getNameText_text());
                }
            }
        });

        binding.linearLayout.addView(layout);
        itemList.add(layout);
    }
    private void IsSafetyMeans() {
        ARouter.getInstance().build(RouteString.WorkDHAppointmentActivity)
                .withInt("planWorkId", planWorkId)
                .withInt("applicationId", applicationId)
                .withString("workType", "LD")
                .navigation(this, isSafetyMeans);  //安全措施
    }

    private void riskIdentify() {
        riskIdentifyList.clear();
        String[] temp = {"火灾", "容器爆炸", "其他爆炸", "触电", "起重伤害", "灼烫", "中毒和窒息",
                "物体打击", "机械伤害", "坍塌", "高处坠落", "车辆伤害", "淹溺", "影响抢险应急", "其他伤害"};
        Collections.addAll(riskIdentifyList, temp);
        ARouter.getInstance().build(RouteString.WorkApplicationCheckListActivity)
                .withObject("list", riskIdentifyList)
                .withString("checkedStr", uploadBean.getRiskIdentify())
                .withInt("resultCode", riskIdentify)
                .navigation(this, riskIdentify);  //风险识别
    }

    private void workingVoltage() {
        workingVoltageList.clear();
        String[] temp = {"380V", "220V", "36V", "24V", "12V"};
        Collections.addAll(workingVoltageList, temp);
        ARouter.getInstance().build(RouteString.WorkApplicationCheckListActivity)
                .withObject("list", workingVoltageList)
                .withString("checkedStr", uploadBean.getWorkingVoltage())
                .withInt("resultCode", workingVoltage)
                .navigation(this, workingVoltage);  //工作电压
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == workingGuardianName) {  //作业监护人
            String userId = data.getStringExtra("userId");
            String name = data.getStringExtra("name");
            itemList.get(5).setNameText_text(name);
            uploadBean.setWorkingGuardian(Integer.valueOf(userId));
            uploadBean.setWorkingGuardianName(name);
        }

        if (resultCode == workingVoltage) { //工作电压
            String str = data.getStringExtra("data");
            str = str.substring(0, str.length() - 1);
            if (str.isEmpty()) {
                ToastUtil.show(mContext, "数据为空");
                return;
            }
            itemList.get(1).setNameText_text(str);
            uploadBean.setWorkingVoltage(str);
        }
        if (resultCode == riskIdentify) { //风险辨识
            String str = data.getStringExtra("data");
            str = str.substring(0, str.length() - 1);
            if (str.isEmpty()) {
                ToastUtil.show(mContext, "数据为空");
                return;
            }
            itemList.get(4).setNameText_text(str);
            uploadBean.setRiskIdentify(str);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void pickTime(boolean isStartTime, String startTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        TimePicker mTimePicker = null;
        if (isStartTime) { //如果是开始时间
            mTimePicker = new TimePicker.Builder(this, TYPE_ALL, (picker, date) -> {
                itemList.get(7).setNameText_text(formatter.format(date));
                uploadBean.setWorkingStartTime(formatter.format(date) + ":00");
                if (!itemList.get(8).getNameText_text().equals("请选择")) {
                    try {
                        if (formatter.parse(itemList.get(8).getNameText_text()).getTime() <= date.getTime()) {
                            itemList.get(8).setNameText_text("请选择");
                            uploadBean.setWorkingEndTime(null);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }).setRangDate(new Date().getTime(), 1893563460000L).create();
        } else { //结束时间
            try {
                mTimePicker = new TimePicker.Builder(this, TYPE_ALL, (picker, date) -> {
                    // LogUtil.i(formatter.format(date));
                    itemList.get(8).setNameText_text(formatter.format(date));
                    uploadBean.setWorkingEndTime(formatter.format(date) + ":00");
                }).setRangDate(formatter.parse(startTime).getTime(), 1893563460000L).create();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        mTimePicker.show();
    }

    private boolean checkAllFinish() {
        String temp = "请选择";

        uploadBean.setPowerAccessPoint(itemList.get(2).getEditText_text());
        uploadBean.setPermitKw(itemList.get(3).getEditText_text());
        if (uploadBean.getPowerAccessPoint().isEmpty() || uploadBean.getPermitKw().isEmpty()) {
            ToastUtil.show(mContext, "请填写完成");
            return false;
        }

        if (uploadBean.getWorkingGuardianName().equals(temp) ||
                uploadBean.getPowerAccessPoint().equals(temp) ||
                uploadBean.getPermitKw().equals(temp) ||
                uploadBean.getRiskIdentify().equals(temp) ||
                uploadBean.getWorkingStartTime().equals(temp) ||
                uploadBean.getWorkingEndTime().equals(temp)) {
            ToastUtil.show(mContext, "请填写完成");
            return false;
        }
        return true;
    }

    private void submit() {
        thisActivityIndex = ActivityIndex;
        RetrofitUtil.getApi().addApplicationLd(uploadBean)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String bean) {
                        uploadBean.setId(Integer.valueOf(bean));
                        String route = "";
                        if (thisActivityIndex < checkBoxRouteList.size()) {
                            thisActivityIndex += 1;
                            if (thisActivityIndex == checkBoxRouteList.size()) {
                                route = RouteString.WorkApplicationInvolveActivity;
                            } else {
                                route = checkBoxRouteList.get(thisActivityIndex);
                            }
                        }
                        if (route.isEmpty()) {
                            ToastUtil.show(mContext, "页面index获取问题");
                        } else {
                            ARouter.getInstance().build(route)
                                    .withObject("checkBoxRouteList", checkBoxRouteList)
                                    .withInt("ActivityIndex", ActivityIndex)
                                    .withInt("applicationId", applicationId)
                                    .withInt("planWorkId", planWorkId)
                                    .navigation();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


    public class Listener {
        public void next() {
            if (checkAllFinish()) {
                submit();
            }
        }
    }
}
