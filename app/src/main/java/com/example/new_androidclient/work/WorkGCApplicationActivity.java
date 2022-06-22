package com.example.new_androidclient.work;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

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
import com.example.new_androidclient.databinding.ActivityWorkGCApplicationBinding;
import com.example.new_androidclient.work.bean.WorkApplicationBaseDetailBean;
import com.example.new_androidclient.work.bean.WorkGCApplicationBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;

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
 * 高处申请页面
 **/
@Route(path = RouteString.WorkGCApplicationActivity)
public class WorkGCApplicationActivity extends BaseActivity {
    private ActivityWorkGCApplicationBinding binding;
    private WorkGCApplicationBean uploadBean;
    private WorkApplicationBaseDetailBean baseDetailBean;

    private List<WorkApplicationLineLayout> itemList = new ArrayList<>();
    private Listener listener = new Listener();

    private List<String> riskFactorList = new ArrayList<>();
    private List<String> riskIdentifyList = new ArrayList<>();

    private int riskFactor = 1;
    private int workingGuardianName = 2;
    private int riskIdentify = 3;
    private int isSafetyMeans = 4;

    private final String lessThan5 = "2m ≤ h ≤ 5m";
    private final String lessThan15 = "5m ＜ h ≤ 15m";
    private final String lessThan30 = "l5m ＜ h ≤ 30m";
    private final String greaterThan30 = "h ＞ 30m";

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

    private String[] NameList = {"作业内容", "区域/位号", "作业高度", "引起坠落客\n观危险因素", "管理级别",
            "作业监护人", "风险辨识结果", "安全措施", "作业开始时间", "作业结束时间"};
    private int[] NameTypeList = {3, 1, 2, 2, 1, 2, 2, 2, 2, 2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_g_c_application);
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
        RetrofitUtil.getApi().selectGcByApplicationId(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<WorkGCApplicationBean>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(WorkGCApplicationBean bean) {
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
                layout.setEditText_text(uploadBean.getWorkingContent()); //内容
                break;
            case 1:
                layout.setNameText_text(baseDetailBean.getAreaName() + " " + baseDetailBean.getTagNo()); //区域/位号
                break;
            case 2:
                layout.setNameText_text(uploadBean.getWorkingHeight()); //作业高度
                break;
            case 3:
                layout.setNameText_text(uploadBean.getRiskFactor()); //引起坠落客观危险因素
                break;
            case 4:
                layout.setNameText_text(uploadBean.getMgtLevel()); //管理级别
                break;
            case 5:
                layout.setNameText_text(uploadBean.getWorkingGuardianName());//作业监护人
                break;
            case 6:
                layout.setNameText_text(uploadBean.getRiskIdentify());//风险辨识结果
                break;
            case 7:
                layout.setNameText_text("请选择");//安全措施
                break;
            case 8:
                layout.setNameText_text(uploadBean.getWorkingStartTime());//开始时间
                break;
            case 9:
                layout.setNameText_text(uploadBean.getWorkingEndTime());//结束时间
                break;
        }

        layout.setOnClickListener(v -> {
            if (layout.getPos() == 5) { //作业监护人
                ARouter.getInstance().build(RouteString.HazardAnalysisPersonSelectActivity)
                        .navigation(WorkGCApplicationActivity.this, workingGuardianName);
            } else if (layout.getPos() == 2) {//作业高度
                workingHeight();
            } else if (layout.getPos() == 3) {//危险因素
                riskFactor();
            } else if (layout.getPos() == 6) { //风险辨识
                riskIdentify();
            } else if (layout.getPos() == 7) { //安全措施
                IsSafetyMeans();
            } else if (layout.getPos() == 8) { //开始时间
                pickTime(true, "");
            } else if (layout.getPos() == 9) { //结束时间
                if (itemList.get(8).getNameText_text().equals("请选择")) {
                    ToastUtil.show(mContext, "请选择开始时间");
                } else {
                    pickTime(false, itemList.get(8).getNameText_text());
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
                .withString("workType", "GC")
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

    private void riskFactor() {
        riskFactorList.clear();
        String[] temp = {"阵风风力五级以上", "作业环境平均气温5℃及以下", "接触冷水温度12℃及以下的作业", "作业场地有冰、雪、霜、油、水等易滑物",
                "作业场所光线不足或能见度差", "作业活动范围与危险电压带电体距离小于安全距离", "摆动，立足处不是平面或只有很小",
                "可能引起灾害事故的作业环境和抢险作业", "室外完全采用人工照明进行的夜间作业", "在无牢靠立足点条件下进行的悬空作业", "其他"};
        Collections.addAll(riskFactorList, temp);
        ARouter.getInstance().build(RouteString.WorkApplicationCheckListActivity)
                .withObject("list", riskFactorList)
                .withString("checkedStr", uploadBean.getRiskFactor())
                .withInt("resultCode", riskFactor)
                .withBoolean("showNoChoiceBtn", true)
                .navigation(this, riskFactor);  //危险因素
    }

    private void workingHeight() {
        WorkConditionDialog workConditionDialog = new WorkConditionDialog(mContext, "10", (value, type1, position, dialog) -> {
            itemList.get(2).setNameText_text(value);
            uploadBean.setWorkingHeight(value);
            setMgtLevel();
            dialog.dismiss();
        });
        workConditionDialog.show();
    }

    private void setMgtLevel() {
        if (!itemList.get(2).getNameText_text().equals("请选择") &&
                !itemList.get(3).getNameText_text().equals("请选择")) {
            String height = itemList.get(2).getNameText_text();
            String factor = itemList.get(3).getNameText_text();
            switch (height) {
                case lessThan5:
                    if (factor.equals("无")) {  //A级
                        itemList.get(4).setNameText_text("Ⅰ级");
                    } else {  //B级
                        itemList.get(4).setNameText_text("Ⅱ级");
                    }
                    break;
                case lessThan15:
                    if (factor.equals("无")) {  //A级
                        itemList.get(4).setNameText_text("Ⅱ级");
                    } else {  //B级
                        itemList.get(4).setNameText_text("Ⅲ级");
                    }
                    break;
                case lessThan30:
                    if (factor.equals("无")) {  //A级
                        itemList.get(4).setNameText_text("Ⅲ级");
                    } else {  //B级
                        itemList.get(4).setNameText_text("Ⅳ级");
                    }
                    break;
                case greaterThan30:
                    itemList.get(4).setNameText_text("Ⅳ级");
                    break;
            }
            uploadBean.setMgtLevel(itemList.get(4).getNameText_text());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == riskFactor) { //危险因素
            String str = data.getStringExtra("data");
            if ("无".equals(str)) {
                itemList.get(3).setNameText_text(str);
                uploadBean.setRiskFactor(str);
                setMgtLevel();
                return;
            }
            str = str.substring(0, str.length() - 1);
            if (str.isEmpty()) {
                ToastUtil.show(mContext, "数据为空");
                return;
            }
            itemList.get(3).setNameText_text(str);
            uploadBean.setRiskFactor(str);
            setMgtLevel();
        }
        if (resultCode == workingGuardianName) {  //作业监护人
            String userId = data.getStringExtra("userId");
            String name = data.getStringExtra("name");
            itemList.get(5).setNameText_text(name);
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
            itemList.get(6).setNameText_text(str);
            uploadBean.setRiskIdentify(str);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void pickTime(boolean isStartTime, String startTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        TimePicker mTimePicker = null;
        if (isStartTime) { //如果是开始时间
            mTimePicker = new TimePicker.Builder(this, TYPE_ALL, (picker, date) -> {
                itemList.get(8).setNameText_text(formatter.format(date));
                uploadBean.setWorkingStartTime(formatter.format(date) + ":00");
                if (!itemList.get(9).getNameText_text().equals("请选择")) {
                    try {
                        if (formatter.parse(itemList.get(9).getNameText_text()).getTime() <= date.getTime()) {
                            itemList.get(9).setNameText_text("请选择");
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
                    itemList.get(9).setNameText_text(formatter.format(date));
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
        uploadBean.setWorkingContent(itemList.get(0).getEditText_text());

        if (uploadBean.getWorkingContent() == null || uploadBean.getWorkingContent().isEmpty()) {
            ToastUtil.show(mContext, "请填写完成");
            return false;
        }

        if (uploadBean.getWorkingGuardianName().equals(temp) ||
                uploadBean.getWorkingHeight().equals(temp) ||
                uploadBean.getRiskFactor().equals(temp) ||
                uploadBean.getMgtLevel().equals(temp) ||
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
        RetrofitUtil.getApi().addApplicationGc(uploadBean)
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
