package com.example.new_androidclient.work;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.widget.Toast;

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
import com.example.new_androidclient.databinding.ActivityWorkSXApplicationBinding;
import com.example.new_androidclient.work.bean.WorkApplicationBaseDetailBean;
import com.example.new_androidclient.work.bean.WorkSXApplicationBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;

import org.jaaksi.pickerview.picker.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.jaaksi.pickerview.picker.TimePicker.TYPE_ALL;

@Route(path = RouteString.WorkSXApplicationActivity)
public class WorkSXApplicationActivity extends BaseActivity {
    private ActivityWorkSXApplicationBinding binding;

    private WorkSXApplicationBean uploadBean;
    private WorkApplicationBaseDetailBean baseDetailBean;

    private List<WorkApplicationLineLayout> itemList = new ArrayList<>();
    private Listener listener = new Listener();

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

    private String[] NameList = {"作业内容", "具体位置", "设备位号",
            "物料状态", "受限空间原有介质名称", "工作压力",
            "工作温度", "与设备连接\n的管道数量", "安全措施",
            "危险介质管道数量", "危险特性", "风险辨识结果",
            "作业监护人", "计划作业开始时间", "计划作业结束时间"};


    private int[] NameTypeList = {3, 3, 1,
            2, 3, 3,
            3, 3, 2,
            3, 2, 2,
            2, 2, 2};

    private int isSafetyMeans = 1;
    private int workingGuardianName = 2;
    private int riskIdentify = 3;
    private int dangerNature = 4;


    private List<String> dangerNatureList = new ArrayList<>();
    private List<String> riskIdentifyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_s_x_application);
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
        RetrofitUtil.getApi().selectSxByApplicationId(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<WorkSXApplicationBean>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(WorkSXApplicationBean bean) {
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
                layout.setEditText_text(uploadBean.getWorkContent()); //内容
                break;
            case 1:
                layout.setEditText_text(uploadBean.getLocation()); //具体位置
                break;
            case 2:
                layout.setNameText_text(baseDetailBean.getTagNo()); //位号（编号
                break;
            case 3:
                layout.setNameText_text(uploadBean.getMatStatus()); //物料状态
                break;
            case 4:
                layout.setEditText_text(uploadBean.getCsMediumName()); //受限空间内介质
                break;
            case 5:
                layout.setEditText_text(uploadBean.getWorkingMpa()); //工作压力
                break;
            case 6:
                layout.setEditText_text(uploadBean.getWorkingTemp()); //工作温度
                break;
            case 7:
                layout.setEditText_text(uploadBean.getDevicePipeNum()); //与设备连接的管道数量
                layout.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 8:
                layout.setNameText_text("请选择");//安全措施
                break;
            case 9:
                layout.setEditText_text(uploadBean.getDangerPipeNum()); //危险介质管道数量
                layout.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 10:
                layout.setNameText_text(uploadBean.getDangerNature()); //危险特性
                break;
            case 11:
                layout.setNameText_text(uploadBean.getRiskIdentify()); //风险辨识结果
                break;
            case 12:
                layout.setNameText_text(uploadBean.getWorkingGuardianName()); //作业监护人
                break;
            case 13:
                layout.setNameText_text(uploadBean.getWorkingStartTime()); //计划作业开始时间
                break;
            case 14:
                layout.setNameText_text(uploadBean.getWorkingEndTime()); //计划作业结束时间
                break;
        }

        layout.setOnClickListener(v -> {
            if (layout.getPos() == 5) { //作业监护人
//                ARouter.getInstance().build(RouteString.HazardAnalysisPersonSelectActivity)
//                        .navigation(WorkGCApplicationActivity.this, workingGuardianName);
            } else if (layout.getPos() == 3) {//物料状态
                matStatus(3);
            } else if (layout.getPos() == 8) {//安全措施
                IsSafetyMeans();
            } else if (layout.getPos() == 10) {//危险特性
                dangerNature();
            } else if (layout.getPos() == 11) {//风险辨识结果
                riskIdentify();
            } else if (layout.getPos() == 12) { //作业监护人
                ARouter.getInstance().build(RouteString.HazardAnalysisPersonSelectActivity)
                        .navigation(WorkSXApplicationActivity.this, workingGuardianName);
            } else if (layout.getPos() == 13) { //开始时间
                pickTime(true, "");
            } else if (layout.getPos() == 14) { //结束时间
                if (itemList.get(13).getNameText_text().equals("请选择")) {
                    ToastUtil.show(mContext, "请选择开始时间");
                } else {
                    pickTime(false, itemList.get(13).getNameText_text());
                }
            }

        });

        binding.linearLayout.addView(layout);
        itemList.add(layout);
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

    private void dangerNature() {
        dangerNatureList.clear();
        String[] temp = {"易燃", "腐蚀", "有毒", "辐射", "高温", "低温"};
        Collections.addAll(dangerNatureList, temp);
        ARouter.getInstance().build(RouteString.WorkApplicationCheckListActivity)
                .withObject("list", dangerNatureList)
                .withString("checkedStr", uploadBean.getDangerNature())
                .withInt("resultCode", dangerNature)
                .navigation(this, dangerNature);  //危险特性
    }

    private void IsSafetyMeans() {
        ARouter.getInstance().build(RouteString.WorkDHAppointmentActivity)
                .withInt("planWorkId", planWorkId)
                .withInt("applicationId", applicationId)
                .withString("workType", "SX")
                .navigation(this, isSafetyMeans);  //安全措施
    }

    private void matStatus(int pos) {
        List<String> list = new ArrayList<>();
        list.add("气相");
        list.add("液相");
        list.add("混合");
        WorkConditionDialog workConditionDialog = new WorkConditionDialog(mContext, "0", list, (value, type1, position, dialog) -> {
            itemList.get(pos).setNameText_text(value);
            uploadBean.setMatStatus(value);
            dialog.dismiss();
        });
        workConditionDialog.show();
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
            itemList.get(12).setNameText_text(name);
            uploadBean.setWorkingGuardian(Integer.valueOf(userId));
            uploadBean.setWorkingGuardianName(name);
        }
        if (resultCode == riskIdentify) { //风险辨识
            String str = data.getStringExtra("data");
            str = str.substring(0, str.length() - 1);
            if (str.isEmpty()) {
                ToastUtil.show(mContext, "数据为空");
                return;
            }
            itemList.get(11).setNameText_text(str);
            uploadBean.setRiskIdentify(str);
        }
        if (resultCode == dangerNature) { //危险特性
            String str = data.getStringExtra("data");
            str = str.substring(0, str.length() - 1);
            if (str.isEmpty()) {
                ToastUtil.show(mContext, "数据为空");
                return;
            }
            itemList.get(10).setNameText_text(str);
            uploadBean.setDangerNature(str);
        }


    }

    @SuppressLint("SimpleDateFormat")
    private void pickTime(boolean isStartTime, String startTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        TimePicker mTimePicker = null;
        if (isStartTime) { //如果是开始时间
            mTimePicker = new TimePicker.Builder(this, TYPE_ALL, (picker, date) -> {
                itemList.get(13).setNameText_text(formatter.format(date));
                uploadBean.setWorkingStartTime(formatter.format(date) + ":00");
                if (!itemList.get(14).getNameText_text().equals("请选择")) {
                    try {
                        if (formatter.parse(itemList.get(14).getNameText_text()).getTime() <= date.getTime()) {
                            itemList.get(14).setNameText_text("请选择");
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
                    itemList.get(14).setNameText_text(formatter.format(date));
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

        List<Integer> indexList = Arrays.asList(0, 1, 4, 5, 6, 7, 9);
        for (int i = 0; i < itemList.size(); i++) {
            if (indexList.contains(i)) {
                if (itemList.get(i).getEditText_text().isEmpty()) {
                    ToastUtil.show(mContext, "请填写完成");
                    return false;
                } else {
                    String str = itemList.get(i).getEditText_text();
                    switch (i) {
                        case 0:
                            uploadBean.setWorkContent(str);
                            break;
                        case 1:
                            uploadBean.setLocation(str);
                            break;
                        case 4:
                            uploadBean.setCsMediumName(str);
                            break;
                        case 5:
                            uploadBean.setWorkingMpa(str);
                            break;
                        case 6:
                            uploadBean.setWorkingTemp(str);
                            break;
                        case 7:
                            uploadBean.setDevicePipeNum(str);
                            break;
                        case 9:
                            uploadBean.setDangerPipeNum(str);
                            break;
                    }
                }
            }
        }

        if (uploadBean.getWorkingGuardianName().equals(temp) ||
                uploadBean.getMatStatus().equals(temp) ||
                uploadBean.getDangerNature().equals(temp) ||
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
        RetrofitUtil.getApi().addApplicationSx(uploadBean)
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
