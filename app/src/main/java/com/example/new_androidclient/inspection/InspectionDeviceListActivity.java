package com.example.new_androidclient.inspection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.EventBusMessage.EventBusMessage;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.Other.SPString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityInspectionDeviceListBinding;
import com.example.new_androidclient.inspection.adapter.InspectionDeviceListAdapter;
import com.example.new_androidclient.inspection.adapter.InspectionDeviceRoughListAdapter;
import com.example.new_androidclient.inspection.bean.InspectionAreaBean;
import com.example.new_androidclient.inspection.bean.InspectionDeviceListBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;

import static com.example.new_androidclient.Other.SPString.InspectionAreaList;
import static com.example.new_androidclient.Other.SPString.InspectionDeviceList;
import static com.example.new_androidclient.Other.SPString.InspectionTaskCode;

@Route(path = RouteString.InspectionDeviceListActivity)
public class InspectionDeviceListActivity extends BaseActivity {
    private ActivityInspectionDeviceListBinding binding;
    private InspectionDeviceListAdapter adapter;
    private InspectionDeviceRoughListAdapter deviceRoughAdapter;
    private String result;
    private List<InspectionDeviceListBean> deviceList = new ArrayList<>();
    private List<InspectionAreaBean> areaList; //????????????????????????????????????????????????AreaDistinguishActivity?????????
    private List<InspectionAreaBean> roughAreaList = new ArrayList<>(); //????????????????????????????????????????????? ??????????????????getRoughAreaListData?????????
    private int areaPosition = -1; //????????????????????????????????????position
    private int devicePosition = -1; //???????????????????????????position
    private int REQUEST_QR = 1;//???????????????, ???lineSort=0?????????, ????????????
    private int REQUEST_ROUGH = 3;//???????????????, ???lineSort=1???????????? ????????????
    private int REQUEST_AREA = 4;//???????????????, ???lineSort=2???????????? ????????????
    private int REQUEST_NFC = 5;
    private int REQUEST_START_INTENT = 2;  //?????????????????????detail???????????????????????????????????????????????????
    private boolean isFirstInspection = true; //????????????????????????
    private int areaRoughPosition = -1; //?????????????????????????????????position
    private int areaId;
    public int taskId;
    public int taskIdToStopActivity = -1; //????????????

    @Autowired()
    boolean isRandomInspection = false; //?????????????????????

    @Autowired()
    int lineSort;  //0????????????  1????????????  2????????????

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_device_list);
        areaList = SPUtil.getListData(InspectionAreaList, InspectionAreaBean.class);
        adapter = new InspectionDeviceListAdapter(deviceList, BR.InspectionDeviceListBean, this::checkAndJump);
        deviceRoughAdapter = new InspectionDeviceRoughListAdapter(roughAreaList, BR.InspectionDeviceRoughListBean, this::checkAndJumpToRough);
        binding.roughRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.roughRecycler.setAdapter(deviceRoughAdapter);
        binding.deviceRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.deviceRecycler.setAdapter(adapter);
        binding.title.getLinearLayout_more().setOnClickListener(v -> showMorePopMenu(v));
        if (lineSort == 1 || lineSort == 2) {  //0????????????  1????????????  2????????????
            getTaskAreaContent();
        } else {
            selectMethod();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lineSort == 1 || lineSort == 2) {  //0????????????  1????????????  2????????????
            getTaskAreaContent();
        }
//        if (lineSort == 1 || lineSort == 2) {  //0????????????  1????????????  2????????????
//            getTaskAreaContent();
//        } else {
//            selectMethod();
//        }
    }

    //????????????????????????????????????
    private void selectMethod() {
        WorkConditionDialog workConditionDialog = new WorkConditionDialog(mContext, "6", (value, type1, position, dialog) -> {
            if (position == 0) { // (0nfc???1?????????)
                ARouter.getInstance().build(RouteString.NFCActivity)
                        .withInt("code", REQUEST_NFC)
                        .withInt("module", 2) //2????????????
                        .navigation(this, REQUEST_NFC);
            } else {
                if (lineSort == 0) {      //0????????????  1????????????  2????????????
                    startQR(lineSort);
                    taskIdToStopActivity = areaList.get(0).getTaskId();
                }
                if (lineSort == 1) {
                    startQR(lineSort);
                }
                if (lineSort == 2) {
                    startQR(lineSort);
                }
            }
            dialog.dismiss();
        });
        //    workConditionDialog.setCanceledOnTouchOutside(false);
        workConditionDialog.show();
    }

    private void startQR(int lineSort) {
        Intent intent = new Intent(InspectionDeviceListActivity.this, CustomCaptureActivity.class);
        if (lineSort == 0) {     //0????????????  1????????????  2????????????
            startActivityForResult(intent, REQUEST_QR);
        } else if (lineSort == 1) {
            startActivityForResult(intent, REQUEST_ROUGH);
        } else {
            startActivityForResult(intent, REQUEST_AREA);
        }
    }

    //??????????????????
    private void checkAndJumpToRough(View view, int position) {
        areaRoughPosition = checkInAreaList();
        if (areaRoughPosition == -1) {  //?????????
            ARouter.getInstance().build(RouteString.InspectionDeviceRoughDetailActivity)
                    .withInt("areaRoughPosition", position)
                    .withInt("taskId", roughAreaList.get(position).getTaskId())
                    .withInt("areaId", roughAreaList.get(position).getAreaId())
                    .withObject("roughAreaList", roughAreaList)
                    .withBoolean("finish", true)
                    .navigation();
        } else if (areaRoughPosition == position) {
//            startQR(lineSort); //0????????????  1????????????  2????????????
            selectMethod();
        } else {
            ToastUtil.show(mContext, "?????????????????????");
        }
    }


    //??????????????????
    private void checkAndJump(View view, int position) {
        SPUtil.putListData(InspectionDeviceList, deviceList);
        if (isRandomInspection) {
            ARouter.getInstance().build(RouteString.InspectionSelectSpecActivity)
                    .withInt("areaId", areaId)
                    .withInt("taskId", taskId)
                    .withInt("deviceId", deviceList.get(position).getDeviceId())
                    .withInt("InspectionAreaPosition", position)
                    .withBoolean("isDeviceCheckFinish", true)
                    .withBoolean("isRandomInspection", true)
                    .withInt("isUsingState", deviceList.get(position).getIsUsingStatus())
                    .withInt("taskDeviceId", deviceList.get(position).getId())
                    .withString("deviceName", deviceList.get(position).getDeviceName())
                    .navigation();
            return;
        }
        if (devicePosition == -1) { //???????????????????????????????????????????????????
            ARouter.getInstance().build(RouteString.InspectionDeviceDetailActivity)
                    .withInt("areaId", areaId)
                    .withInt("taskId", taskId)
                    .withInt("deviceId", deviceList.get(position).getDeviceId())
                    .withInt("devicePosition", position)
                    .withBoolean("isDeviceCheckFinish", true)
                    .withBoolean("isRandomInspection", isRandomInspection)
                    .navigation(InspectionDeviceListActivity.this, REQUEST_START_INTENT);
        } else if (devicePosition == position) {
            ARouter.getInstance().build(RouteString.InspectionDeviceDetailActivity)
                    .withInt("areaId", areaId)
                    .withInt("taskId", taskId)
                    .withInt("deviceId", deviceList.get(position).getDeviceId())
                    .withInt("devicePosition", devicePosition)
                    .navigation(InspectionDeviceListActivity.this, REQUEST_START_INTENT);
        } else {
            ToastUtil.show(mContext, "?????????????????????");
        }
    }

    //????????????????????????
    private void toDeviceInspection(String result) {
        areaPosition = check(result);  //????????????????????????????????????????????????????????????????????????
        if (isRandomInspection) {
            getRandomDeviceList(result);
        } else {
            if (areaPosition >= 0) {  //???????????????????????????????????????
                if (isFirstInspection) { //????????????????????????????????????????????????????????????index
                    SPUtil.putData(SPString.InspectionAreaPosition, areaPosition);
                    isFirstInspection = false;
                }
                int currentAreaIndex = (int) SPUtil.getData(SPString.InspectionAreaPosition, -1); //?????????????????????????????????index
                if (currentAreaIndex >= 0 && (currentAreaIndex == areaPosition)) { //?????????????????????????????????????????????????????????
                    areaId = areaList.get(currentAreaIndex).getAreaId();
                    taskId = areaList.get(currentAreaIndex).getTaskId();
                    binding.title.setName(areaList.get(currentAreaIndex).getAreaName());
                    getDeviceListData();
                } else {  //?????????
                    ToastUtil.show(this, "???????????????????????????");
                }
            } else {
                finish();
            }
        }
    }

    //????????????????????????
    private void toAreaInspection(String result) {
        if (result.equals(roughAreaList.get(areaRoughPosition).getAreaCode())) {
            ARouter.getInstance().build(RouteString.InspectionDeviceAreaDetailActivity)
                    .withInt("areaRoughPosition", areaRoughPosition)
                    .withInt("taskId", roughAreaList.get(areaRoughPosition).getTaskId())
                    .withInt("areaId", roughAreaList.get(areaRoughPosition).getAreaId())
                    .withObject("roughAreaList", roughAreaList)
                    .navigation();
        } else {
            ToastUtil.show(this, "???????????????????????????");
        }
    }

    //????????????????????????
    private void toRoughInspection(String result) {
        if (result.equals(roughAreaList.get(areaRoughPosition).getAreaCode())) {
            ARouter.getInstance().build(RouteString.InspectionDeviceRoughDetailActivity)
                    .withInt("areaRoughPosition", areaRoughPosition)
                    .withInt("taskId", roughAreaList.get(areaRoughPosition).getTaskId())
                    .withInt("areaId", roughAreaList.get(areaRoughPosition).getAreaId())
                    .withObject("roughAreaList", roughAreaList)
                    .navigation();
        } else {
            ToastUtil.show(this, "???????????????????????????");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_QR) {  //????????????
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    result = bundle.getString(CodeUtils.RESULT_STRING);
                    areaList = SPUtil.getListData(InspectionAreaList, InspectionAreaBean.class);
                    toDeviceInspection(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.show(this, "?????????????????????, ???????????????");
                    // startQR(0);
                    finish();
                }
            } else {
                finish();
            }
        }

        if (requestCode == REQUEST_START_INTENT) {
            devicePosition = -1;
            getDeviceListData();
        }

        if (requestCode == REQUEST_ROUGH) {  //????????????
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    result = bundle.getString(CodeUtils.RESULT_STRING);
                    toRoughInspection(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.show(this, "?????????????????????, ???????????????");
                    startQR(0);
                }
            } else {
                finish();
            }
        }

        if (requestCode == REQUEST_NFC) { //nfc
            if (data == null) {
                finish();
                return;
            }
            // lineSort;  //0????????????  1????????????  2????????????
            String area = data.getStringExtra("area");
            if (lineSort == 0) {
                toDeviceInspection(area);
            } else if (lineSort == 1) {
                toRoughInspection(area);
            } else if (lineSort == 2) {
                toAreaInspection(area);
            }
        }

        if (requestCode == REQUEST_AREA) {  //??????
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    result = bundle.getString(CodeUtils.RESULT_STRING);
                    toAreaInspection(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.show(this, "?????????????????????, ???????????????");
                }
            } else {
                finish();
            }
        }
    }

    private int checkInAreaList() {
        for (int i = 0; i < roughAreaList.size(); i++) {
            if (roughAreaList.get(i).getStatus() == 0) {
                return i;
            }
        }
        return -1;
    }

    private void getTaskAreaContent() {
        String taskCode = (String) SPUtil.getData(InspectionTaskCode, "");
        if (taskCode.length() == 0) {
            ToastUtil.show(mContext, "??????NFC?????????????????????????????????");
            return;
        }
        RetrofitUtil.getApi().getTaskAreaContent(taskCode)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<InspectionAreaBean>>(mContext, true, "???????????????") {
                    @Override
                    public void onSuccess(List<InspectionAreaBean> bean) {
                        if (bean.size() != 0) {
                            roughAreaList.clear();
                            roughAreaList.addAll(bean);
                            binding.title.setName("??????????????????");
                            binding.nodata.setVisibility(View.GONE);
                            binding.deviceRecycler.setVisibility(View.GONE);
                            binding.roughRecycler.setVisibility(View.VISIBLE);
                            deviceRoughAdapter.notifyDataSetChanged();
                            taskIdToStopActivity = bean.get(0).getTaskId();
                        } else {
                            ToastUtil.show(mContext, "?????????????????????");
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                        finish();
                    }
                });
    }

    private void getRandomDeviceList(String areaCode) {
        RetrofitUtil.getApi().randomInspection(areaCode)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<InspectionAreaBean>>(mContext, true, "????????????????????????") {

                    @Override
                    public void onSuccess(List<InspectionAreaBean> bean) {
                        if (bean.size() != 0) {
                            areaId = bean.get(0).getAreaId();
                            taskId = bean.get(0).getTaskId();
                            getDeviceListData();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //??????????????????????????????????????????????????????
//    private int checkAreaInList(String code) {
//        for (int i = 0; i < areaList.size(); i++) {
//            if (areaList.get(i).getAreaCode().equals(code)) {
//                return i;
//            }
//        }
//        return -1;
//    }

    //???????????????????????????12345????????????12????????????3??????????????????12?????????4???5??????????????????3
    //????????????????????????????????????????????????????????????
    private int check(String code) {
        boolean allFinish = true; //true????????????????????????????????? false???????????????????????????
//        if(areaList.size() == 1){ //????????????????????????
//           if(code!=null && !code.isEmpty() && code.equals(areaList.get(0).getAreaCode())){ //????????????????????????????????????
//               return 0;
//           }
//        }
        for (int i = 0; i < areaList.size(); i++) {
            if (areaList.get(i).getStatus() == 0) {
                allFinish = false;
                break;
            }
        }
        if (allFinish) { //????????????????????????????????????????????????
            for (int i = 0; i < areaList.size(); i++) {
                if (areaList.get(i).getAreaCode().equals(code)) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < areaList.size(); i++) {
                if (areaList.get(i).getStatus() == 0) {
                    if (areaList.get(i).getAreaCode().equals(code)) {
                        return i;
                    } else {
                        ToastUtil.show(mContext, "????????????????????? " + areaList.get(i).getAreaCode() + " ?????????");
                        return -1;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        startQR(0);
    }


    private void getDeviceListData() {
        RetrofitUtil.getApi().getTaskDeviceList(taskId, areaId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<InspectionDeviceListBean>>(mContext, true, "??????????????????") {
                    @Override
                    public void onSuccess(List<InspectionDeviceListBean> bean) {
                        deviceList.clear();
                        deviceList.addAll(bean);
                        binding.roughRecycler.setVisibility(View.GONE);
                        if (deviceList.size() == 0) {
                            binding.deviceRecycler.setVisibility(View.GONE);
                            binding.nodata.setVisibility(View.VISIBLE);
                        } else {
                            adapter.notifyDataSetChanged();
                            if (isRandomInspection) {
                                devicePosition = -1;
                            } else { //?????????????????????????????????????????????????????????????????????????????????position
                                for (int i = 0; i < deviceList.size(); i++) {
                                    if (deviceList.get(i).getStatus() == 0) {
                                        devicePosition = i;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                        finish();
                    }
                });
    }

    private void showMorePopMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(mContext, v);
        popupMenu.getMenuInflater().inflate(R.menu.inspection_option, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.inspection_stop:
                    if (taskIdToStopActivity < 0) {
                        ToastUtil.show(mContext, "????????????????????????????????????????????????");
                    } else {
                        ARouter.getInstance().build(RouteString.InspectionStopActivity)
                                .withInt("taskId", taskIdToStopActivity)
                                .navigation();
                    }
                    return true;
            }
            return true;
        });
        popupMenu.show();
    }

    //selectActivity??????????????????????????????
    @Subscribe()
    public void refreshList(EventBusMessage message) {
        getDeviceListData();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
