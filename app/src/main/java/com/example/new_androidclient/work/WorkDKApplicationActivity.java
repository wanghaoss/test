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
import com.example.new_androidclient.databinding.ActivityWorkDKApplicationBinding;
import com.example.new_androidclient.work.bean.WorkApplicationBaseDetailBean;
import com.example.new_androidclient.work.bean.WorkDKApplicationBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;

import org.jaaksi.pickerview.picker.TimePicker;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.jaaksi.pickerview.picker.TimePicker.TYPE_ALL;

@Route(path = RouteString.WorkDKApplicationActivity)
public class WorkDKApplicationActivity extends BaseActivity {

    private ActivityWorkDKApplicationBinding binding;
    private WorkDKApplicationBean uploadBean;
    private WorkApplicationBaseDetailBean baseDetailBean;

    private List<WorkApplicationLineLayout> itemList = new ArrayList<>();
    private Listener listener = new Listener();

    private List<String> riskIdentifyList = new ArrayList<>();

    private int selectDevice = 1;
    private int workingGuardianName = 2;
    private int riskIdentify = 3;
    private int isSafetyMeans = 4;

    private final String noData = "??????";


    //????????????????????????index???thisActivityIndex???????????????ActivityIndex?????????
    //???????????????????????????thisActivityIndex??????FC????????????????????????index???????????????
    private int thisActivityIndex;

    @Autowired
    int ActivityIndex;

    @Autowired
    int applicationId;

    @Autowired
    int planWorkId;

    @Autowired
    List<String> checkBoxRouteList;

    private String[] NameList = {"???????????????\n????????????", "??????/??????", "??????/????????????",
            "????????????", "????????????",
            "???????????????", "??????????????????", "????????????",
            "??????????????????", "??????????????????"};
    private int[] NameTypeList = {3, 1, 3,
            2, 2,
            2, 2, 2,
            2, 2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_d_k_application);
        binding.setListener(listener);
        getBaseDetail();
    }

    private void getBaseDetail() {
        RetrofitUtil.getApi().selectApplication(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<WorkApplicationBaseDetailBean>(mContext, true, "??????????????????") {
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
        RetrofitUtil.getApi().selectDkByApplicationId(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<WorkDKApplicationBean>(mContext, true, "??????????????????") {
                    @Override
                    public void onSuccess(WorkDKApplicationBean bean) {
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
                layout.setEditText_text(uploadBean.getWorkingContent()); //????????????+??????
                break;
            case 1:
                layout.setNameText_text(baseDetailBean.getAreaName() + " " + baseDetailBean.getTagNo()); //??????/??????
                break;
            case 2:
                layout.setEditText_text(uploadBean.getConduitDevice()); //?????? / ????????????  ?????????????????????????????????????????????????????????????????????????????????????????????????????? WorkDKDeviceListActivity??????????????????????????????????????????????????????
                break;
            case 3:
                layout.setNameText_text(uploadBean.getWorkingType()); //????????????
                break;
            case 4:
                layout.setNameText_text(uploadBean.getOpenMode()); //????????????
                break;
            case 5:
                layout.setNameText_text(uploadBean.getWorkingGuardianName());//???????????????
                break;
            case 6:
                layout.setNameText_text(uploadBean.getRiskIdentify());//??????????????????
                break;
            case 7:
                layout.setNameText_text("?????????");//????????????
                break;
            case 8:
                layout.setNameText_text(uploadBean.getWorkingStartTime());//????????????
                break;
            case 9:
                layout.setNameText_text(uploadBean.getWorkingEndTime());//????????????
                break;
        }

        layout.setOnClickListener(v -> {
            if (layout.getPos() == 5) { //???????????????
                ARouter.getInstance().build(RouteString.HazardAnalysisPersonSelectActivity)
                        .navigation(WorkDKApplicationActivity.this, workingGuardianName);
            }
//            else if (layout.getPos() == 2) {//?????? / ????????????   ?????????????????????????????????????????????????????????????????????????????????????????????????????? WorkDKDeviceListActivity??????????????????????????????????????????????????????
//                selectDevice();
//            }
            else if (layout.getPos() == 3) {//????????????
                workType();
            } else if (layout.getPos() == 4) { //????????????
                if (itemList.get(3).getNameText_text().equals("?????????") ||
                        itemList.get(3).getNameText_text().isEmpty()) {
                    ToastUtil.show(mContext, "????????????????????????");
                    return;
                }
                if (layout.getNameText_text().equals(noData)) {
                    return;
                }
                openMethod();
            } else if (layout.getPos() == 5) {//???????????????
                ARouter.getInstance().build(RouteString.HazardAnalysisPersonSelectActivity)
                        .navigation(WorkDKApplicationActivity.this, workingGuardianName);
            } else if (layout.getPos() == 6) {//??????????????????
                riskIdentify();
            } else if (layout.getPos() == 7) {//????????????
                IsSafetyMeans();
            } else if (layout.getPos() == 8) { //????????????
                pickTime(true, "");
            } else if (layout.getPos() == 9) { //????????????
                if (itemList.get(8).getNameText_text().equals("?????????")) {
                    ToastUtil.show(mContext, "?????????????????????");
                } else {
                    pickTime(false, itemList.get(8).getNameText_text());
                }
            }
        });

        binding.linearLayout.addView(layout);
        itemList.add(layout);
    }

    private void selectDevice() {
        ARouter.getInstance().build(RouteString.WorkDTApplicationActivity)
                .withString("instName", baseDetailBean.getInstName())
                .navigation(this, selectDevice);  //?????????????????????
    }

    private void openMethod() {
        List<String> list = new ArrayList<>();
        list.add("?????????");
        list.add("??????");
        list.add("?????????");
        list.add("????????????");
        list.add("??????");
        WorkConditionDialog workConditionDialog = new WorkConditionDialog(mContext, "0", list, (value, type1, pos, dialog) -> {
            itemList.get(4).setNameText_text(value);
            uploadBean.setOpenMode(value);
            dialog.dismiss();
        });
        workConditionDialog.show();
    }

    private void workType() {
        List<String> list = new ArrayList<>();
        list.add("??????????????????");
        list.add("??????????????????");
        WorkConditionDialog workConditionDialog = new WorkConditionDialog(mContext, "0", list, (value, type1, pos, dialog) -> {
            itemList.get(3).setNameText_text(value);  //0?????? 1??????
            //??????????????????????????????0????????????????????????????????????
            if (pos == 0) {
                setOpenMethodItem(true);
                uploadBean.setWorkingType("0");
            } else {
                setOpenMethodItem(false);
                uploadBean.setWorkingType("1");
            }
            dialog.dismiss();
        });
        workConditionDialog.show();
    }

    private void setOpenMethodItem(boolean isContainer) {
        if (isContainer) { //???????????????????????????
            if (uploadBean.getOpenMode().equals(noData)) {
                uploadBean.setOpenMode(null);
            }
            itemList.get(4).setNameText_text(uploadBean.getOpenMode());
        } else {
            itemList.get(4).setNameText_text(noData);
            uploadBean.setOpenMode(noData);
        }
    }

    private void riskIdentify() {
        riskIdentifyList.clear();
        String[] temp = {"??????", "????????????", "????????????", "??????", "????????????", "??????", "???????????????",
                "????????????", "????????????", "??????", "????????????", "????????????", "??????", "??????????????????", "????????????"};
        Collections.addAll(riskIdentifyList, temp);
        ARouter.getInstance().build(RouteString.WorkApplicationCheckListActivity)
                .withObject("list", riskIdentifyList)
                .withString("checkedStr", uploadBean.getRiskIdentify())
                .withInt("resultCode", riskIdentify)
                .navigation(this, riskIdentify);  //????????????
    }

    private void IsSafetyMeans() {
        ARouter.getInstance().build(RouteString.WorkDHAppointmentActivity)
                .withInt("planWorkId", planWorkId)
                .withInt("applicationId", applicationId)
                .withString("workType", "DK")
                .navigation(this, isSafetyMeans);  //????????????
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == workingGuardianName) {  //???????????????
            String userId = data.getStringExtra("userId");
            String name = data.getStringExtra("name");
            itemList.get(5).setNameText_text(name);
            uploadBean.setWorkingGuardian(Integer.valueOf(userId));
            uploadBean.setWorkingGuardianName(name);
        }
        if (resultCode == riskIdentify) { //????????????
            String str = data.getStringExtra("data");
            str = str.substring(0, str.length() - 1);
            if (str.isEmpty()) {
                ToastUtil.show(mContext, "????????????");
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
        if (isStartTime) { //?????????????????????
            mTimePicker = new TimePicker.Builder(this, TYPE_ALL, (picker, date) -> {
                itemList.get(8).setNameText_text(formatter.format(date));
                uploadBean.setWorkingStartTime(formatter.format(date) + ":00");
                if (!itemList.get(9).getNameText_text().equals("?????????")) {
                    try {
                        if (formatter.parse(itemList.get(9).getNameText_text()).getTime() <= date.getTime()) {
                            itemList.get(9).setNameText_text("?????????");
                            uploadBean.setWorkingEndTime(null);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }).setRangDate(new Date().getTime(), 1893563460000L).create();
        } else { //????????????
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
        String temp = "?????????";

        uploadBean.setWorkingContent(itemList.get(0).getEditText_text());
        uploadBean.setConduitDevice(itemList.get(2).getEditText_text());
        if (uploadBean.getWorkingContent().isEmpty() || uploadBean.getConduitDevice().isEmpty()) {
            ToastUtil.show(mContext, "???????????????");
            return false;
        }

        if (uploadBean.getWorkingGuardianName().equals(temp) ||
                uploadBean.getWorkingContent().equals(temp) ||
                uploadBean.getWorkingType().equals(temp) ||
                uploadBean.getOpenMode().equals(temp) ||
                uploadBean.getConduitDevice().equals(temp) ||
                uploadBean.getRiskIdentify().equals(temp) ||
                uploadBean.getWorkingStartTime().equals(temp) ||
                uploadBean.getWorkingEndTime().equals(temp)) {
            ToastUtil.show(mContext, "???????????????");
            return false;
        }
        return true;
    }

    private void submit() {
        thisActivityIndex = ActivityIndex;
        RetrofitUtil.getApi().addApplicationDk(uploadBean)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "??????????????????") {
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
                            ToastUtil.show(mContext, "??????index????????????");
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

