package com.example.new_androidclient.work;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.WorkApplicationLineLayout;
import com.example.new_androidclient.databinding.ActivityWorkDHApplicationBinding;
import com.example.new_androidclient.work.bean.WorkDHApplicationBean;

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
 * 动火申请
 */
@Route(path = RouteString.WorkDHApplicationActivity)
public class WorkDHApplicationActivity extends BaseActivity {
    private ActivityWorkDHApplicationBinding binding;
    private List<WorkApplicationLineLayout> itemList = new ArrayList<>();
    private WorkDHApplicationBean uploadBean = new WorkDHApplicationBean();

    private Listener listener = new Listener();

    //用于处理本页面的index，thisActivityIndex最开始时和ActivityIndex相等，
    //若下一页操作则使用thisActivityIndex，用于处理返回页面时index异常的问题
    private int thisActivityIndex;

    private int FireMode = 3;
    private int FirePosition = 4;
    private int riskIdentify = 1;
    private int isSafetyMeans = 7;

    @Autowired
    int ActivityIndex;

    @Autowired
    List<String> checkBoxRouteList;

    @Autowired
    int applicationId;

    @Autowired
    int planWorkId;


    private String[] NameList = {"作业内容", "动火地点", "动火部位", "动火方式", "作业监护人", "许可证安全措施项", "风险辨识结果", "作业计划开始时间", "作业计划结束时间"};
    private int[] NameTypeList = {3, 3, 2, 2, 2, 2, 2, 2, 2};

    private List<String> FireModeList = new ArrayList<>();
    private List<String> firePositionList = new ArrayList<>();
    private List<String> riskIdentifyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_d_h_application);
        binding.setListener(listener);
        getDetail();
    }


    private void getDetail() {
        RetrofitUtil.getApi().selectApplicationDh(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<WorkDHApplicationBean>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(WorkDHApplicationBean bean) {
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
        if (position == 0) {
            layout.setEditText_text(uploadBean.getWorkContent()); //名称
        } else if (position == 1) {
            layout.setEditText_text(uploadBean.getFireSite()); //地点
        } else if (position == 2) {
            layout.setNameText_text(uploadBean.getFirePosition()); //部位
        } else if (position == 3) {
            layout.setNameText_text(uploadBean.getFireMode()); //方式
        } else if (position == 4) {
            layout.setNameText_text(uploadBean.getWorkingGuardianName()); //监护人
        } else if (position == 5) {
            layout.setNameText_text("请选择");//安措
        } else if (position == 6) {
            layout.setNameText_text(uploadBean.getRiskIdentify());//风险
        } else if (position == 7) {
            layout.setNameText_text(uploadBean.getWorkingStartTime());//开始时间
        } else if (position == 8) {
            layout.setNameText_text(uploadBean.getWorkingEndTime());//结束时间
        }

        layout.setOnClickListener(v -> {
            if (layout.getPos() == 4) {//作业监护人
                ARouter.getInstance().build(RouteString.HazardAnalysisPersonSelectActivity)
                        .navigation(WorkDHApplicationActivity.this, 2);
            } else if (layout.getPos() == 3) {//动火方式
                FireMode();
            } else if (layout.getPos() == 2) { //部位
                FirePosition();
            } else if (layout.getPos() == 5) { //安全措施项
                IsSafetyMeans();
            } else if (layout.getPos() == 6) { //风险识别结果
                RiskIdentify();
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
                .withString("workType", "DH")
                .navigation(this, isSafetyMeans);  //安全措施
    }


    private void RiskIdentify() {
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

    private void FirePosition() {
        firePositionList.clear();
        String[] temp = {"设备内部", "设备外部", "设备外壁"};
        Collections.addAll(firePositionList, temp);
        ARouter.getInstance().build(RouteString.WorkApplicationCheckListActivity)
                .withObject("list", firePositionList)
                .withString("checkedStr", uploadBean.getFirePosition())
                .withInt("resultCode", FirePosition)
                .navigation(this, FirePosition);  //动火部位
    }

    private void FireMode() {
        FireModeList.clear();
        String[] temp = {"焊接", "切割", "电钻", "打磨", "敲击", "临时用电", "喷灯", "爆破", "车辆伤害", "其它"};
        Collections.addAll(FireModeList, temp);
        ARouter.getInstance().build(RouteString.WorkApplicationCheckListActivity)
                .withObject("list", FireModeList)
                .withString("checkedStr", uploadBean.getFireMode())
                .withInt("resultCode", FireMode)
                .navigation(this, FireMode);  //动火方式
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FireMode) { //动火方式
            String str = data.getStringExtra("data");
            str = str.substring(0, str.length() - 1);
            if (str.isEmpty()) {
                ToastUtil.show(mContext, "数据为空");
                return;
            }
            itemList.get(3).setNameText_text(str);
            uploadBean.setFireMode(str);
        }

        if (resultCode == FirePosition) { //动火部位
            String str = data.getStringExtra("data");
            str = str.substring(0, str.length() - 1);
            if (str.isEmpty()) {
                ToastUtil.show(mContext, "数据为空");
                return;
            }
            itemList.get(2).setNameText_text(str);
            uploadBean.setFirePosition(str);
        }

        if (resultCode == riskIdentify) { //风险识别
            String str = data.getStringExtra("data");
            str = str.substring(0, str.length() - 1);
            if (str.isEmpty()) {
                ToastUtil.show(mContext, "数据为空");
                return;
            }
            itemList.get(6).setNameText_text(str);
            uploadBean.setRiskIdentify(str);
        }
        if (resultCode == 2) {  //作业监护人
            String userId = data.getStringExtra("userId");
            String name = data.getStringExtra("name");
            itemList.get(4).setNameText_text(name);
            uploadBean.setWorkingGuardian(Integer.valueOf(userId));
            uploadBean.setWorkingGuardianName(name);
        }
    }

    private boolean checkAllFinish() {
        String temp = "请选择";
        uploadBean.setWorkContent(itemList.get(0).getEditText_text());
        uploadBean.setFireSite(itemList.get(1).getEditText_text());

        if (uploadBean.getFireSite().isEmpty() || uploadBean.getWorkContent().isEmpty()) {
            ToastUtil.show(mContext, "请填写完成");
            return false;
        }

        if (uploadBean.getWorkingGuardianName().equals(temp) ||
                uploadBean.getFireMode().equals(temp) ||
                uploadBean.getFirePosition().equals(temp) ||
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
        RetrofitUtil.getApi().addApplicationDh(uploadBean)
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
                                    .withInt("ActivityIndex", thisActivityIndex)
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
