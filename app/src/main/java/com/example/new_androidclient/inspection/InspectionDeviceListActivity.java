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
    private List<InspectionAreaBean> areaList; //设备巡检中使用的区域巡检，数据从AreaDistinguishActivity中获取
    private List<InspectionAreaBean> roughAreaList = new ArrayList<>(); //设备区域巡检中使用的区域列表， 数据从本页面getRoughAreaListData中获取
    private int areaPosition = -1; //扫描的区域在区域列表里的position
    private int devicePosition = -1; //设备在设备列表里的position
    private int REQUEST_QR = 1;//扫描二维码, 当lineSort=0的时候, 设备巡检
    private int REQUEST_ROUGH = 3;//扫描二维码, 当lineSort=1的时候， 宏观巡检
    private int REQUEST_AREA = 4;//扫描二维码, 当lineSort=2的时候， 区域巡检
    private int REQUEST_NFC = 5;
    private int REQUEST_START_INTENT = 2;  //用于判断是否是detail页面返回来，返回来刷新设备列表页面
    private boolean isFirstInspection = true; //是不是第一次巡检
    private int areaRoughPosition = -1; //设备宏观巡检在列表里的position
    private int areaId;
    public int taskId;
    public int taskIdToStopActivity = -1; //终止功能

    @Autowired()
    boolean isRandomInspection = false; //是不是随机巡检

    @Autowired()
    int lineSort;  //0设备巡检  1区域巡检  2宏观巡检

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
        if (lineSort == 1 || lineSort == 2) {  //0设备巡检  1区域巡检  2宏观巡检
            getTaskAreaContent();
        } else {
            selectMethod();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lineSort == 1 || lineSort == 2) {  //0设备巡检  1区域巡检  2宏观巡检
            getTaskAreaContent();
        }
//        if (lineSort == 1 || lineSort == 2) {  //0设备巡检  1区域巡检  2宏观巡检
//            getTaskAreaContent();
//        } else {
//            selectMethod();
//        }
    }

    //扫描区域选择识别区域方式
    private void selectMethod() {
        WorkConditionDialog workConditionDialog = new WorkConditionDialog(mContext, "6", (value, type1, position, dialog) -> {
            if (position == 0) { // (0nfc，1二维码)
                ARouter.getInstance().build(RouteString.NFCActivity)
                        .withInt("code", REQUEST_NFC)
                        .withInt("module", 2) //2巡检区域
                        .navigation(this, REQUEST_NFC);
            } else {
                if (lineSort == 0) {      //0设备巡检  1区域巡检  2宏观巡检
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
        if (lineSort == 0) {     //0设备巡检  1区域巡检  2宏观巡检
            startActivityForResult(intent, REQUEST_QR);
        } else if (lineSort == 1) {
            startActivityForResult(intent, REQUEST_ROUGH);
        } else {
            startActivityForResult(intent, REQUEST_AREA);
        }
    }

    //区域巡检跳转
    private void checkAndJumpToRough(View view, int position) {
        areaRoughPosition = checkInAreaList();
        if (areaRoughPosition == -1) {  //已巡检
            ARouter.getInstance().build(RouteString.InspectionDeviceRoughDetailActivity)
                    .withInt("areaRoughPosition", position)
                    .withInt("taskId", roughAreaList.get(position).getTaskId())
                    .withInt("areaId", roughAreaList.get(position).getAreaId())
                    .withObject("roughAreaList", roughAreaList)
                    .withBoolean("finish", true)
                    .navigation();
        } else if (areaRoughPosition == position) {
//            startQR(lineSort); //0设备巡检  1区域巡检  2宏观巡检
            selectMethod();
        } else {
            ToastUtil.show(mContext, "请按照顺序巡检");
        }
    }


    //设备巡检跳转
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
        if (devicePosition == -1) { //用于判断如果列表里都是已巡检的情况
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
            ToastUtil.show(mContext, "请按照顺序巡检");
        }
    }

    //设备巡检相关代码
    private void toDeviceInspection(String result) {
        areaPosition = check(result);  //判断扫描的区域码是否在巡检列表里并且是否扫描正确
        if (isRandomInspection) {
            getRandomDeviceList(result);
        } else {
            if (areaPosition >= 0) {  //如果扫描的区域在巡检列表里
                if (isFirstInspection) { //是不是第一次巡检，是就拿到区域在列表里的index
                    SPUtil.putData(SPString.InspectionAreaPosition, areaPosition);
                    isFirstInspection = false;
                }
                int currentAreaIndex = (int) SPUtil.getData(SPString.InspectionAreaPosition, -1); //拿到当前正在巡检区域的index
                if (currentAreaIndex >= 0 && (currentAreaIndex == areaPosition)) { //如果当前应该巡检的区域和扫描的区域一致
                    areaId = areaList.get(currentAreaIndex).getAreaId();
                    taskId = areaList.get(currentAreaIndex).getTaskId();
                    binding.title.setName(areaList.get(currentAreaIndex).getAreaName());
                    getDeviceListData();
                } else {  //不一致
                    ToastUtil.show(this, "请扫描正确的二维码");
                }
            } else {
                finish();
            }
        }
    }

    //宏观巡检相关代码
    private void toAreaInspection(String result) {
        if (result.equals(roughAreaList.get(areaRoughPosition).getAreaCode())) {
            ARouter.getInstance().build(RouteString.InspectionDeviceAreaDetailActivity)
                    .withInt("areaRoughPosition", areaRoughPosition)
                    .withInt("taskId", roughAreaList.get(areaRoughPosition).getTaskId())
                    .withInt("areaId", roughAreaList.get(areaRoughPosition).getAreaId())
                    .withObject("roughAreaList", roughAreaList)
                    .navigation();
        } else {
            ToastUtil.show(this, "请扫描正确的区域码");
        }
    }

    //区域巡检相关代码
    private void toRoughInspection(String result) {
        if (result.equals(roughAreaList.get(areaRoughPosition).getAreaCode())) {
            ARouter.getInstance().build(RouteString.InspectionDeviceRoughDetailActivity)
                    .withInt("areaRoughPosition", areaRoughPosition)
                    .withInt("taskId", roughAreaList.get(areaRoughPosition).getTaskId())
                    .withInt("areaId", roughAreaList.get(areaRoughPosition).getAreaId())
                    .withObject("roughAreaList", roughAreaList)
                    .navigation();
        } else {
            ToastUtil.show(this, "请扫描正确的区域码");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_QR) {  //设备巡检
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
                    ToastUtil.show(this, "解析二维码失败, 请重新扫描");
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

        if (requestCode == REQUEST_ROUGH) {  //区域巡检
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    result = bundle.getString(CodeUtils.RESULT_STRING);
                    toRoughInspection(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.show(this, "解析二维码失败, 请重新扫描");
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
            // lineSort;  //0设备巡检  1区域巡检  2宏观巡检
            String area = data.getStringExtra("area");
            if (lineSort == 0) {
                toDeviceInspection(area);
            } else if (lineSort == 1) {
                toRoughInspection(area);
            } else if (lineSort == 2) {
                toAreaInspection(area);
            }
        }

        if (requestCode == REQUEST_AREA) {  //宏观
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    result = bundle.getString(CodeUtils.RESULT_STRING);
                    toAreaInspection(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.show(this, "解析二维码失败, 请重新扫描");
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
            ToastUtil.show(mContext, "获取NFC卡数据失败，请重新扫卡");
            return;
        }
        RetrofitUtil.getApi().getTaskAreaContent(taskCode)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<InspectionAreaBean>>(mContext, true, "正在初始化") {
                    @Override
                    public void onSuccess(List<InspectionAreaBean> bean) {
                        if (bean.size() != 0) {
                            roughAreaList.clear();
                            roughAreaList.addAll(bean);
                            binding.title.setName("巡检区域列表");
                            binding.nodata.setVisibility(View.GONE);
                            binding.deviceRecycler.setVisibility(View.GONE);
                            binding.roughRecycler.setVisibility(View.VISIBLE);
                            deviceRoughAdapter.notifyDataSetChanged();
                            taskIdToStopActivity = bean.get(0).getTaskId();
                        } else {
                            ToastUtil.show(mContext, "获取巡检项异常");
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
                .subscribe(new DialogObserver<List<InspectionAreaBean>>(mContext, true, "正在创建随机巡检") {

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

    //判断扫描的区域是否在登录人巡检范围内
//    private int checkAreaInList(String code) {
//        for (int i = 0; i < areaList.size(); i++) {
//            if (areaList.get(i).getAreaCode().equals(code)) {
//                return i;
//            }
//        }
//        return -1;
//    }

    //如果一共五个区域，12345，已巡检12，应巡检3时，不能扫描12，扫描4或5时提示应巡检3
    //若五个区域都已巡检，则可扫描查看任意区域
    private int check(String code) {
        boolean allFinish = true; //true表示所有区域都巡检完， false表示还有区域未巡检
//        if(areaList.size() == 1){ //巡检只有一个区域
//           if(code!=null && !code.isEmpty() && code.equals(areaList.get(0).getAreaCode())){ //扫描的区域码不为空且等于
//               return 0;
//           }
//        }
        for (int i = 0; i < areaList.size(); i++) {
            if (areaList.get(i).getStatus() == 0) {
                allFinish = false;
                break;
            }
        }
        if (allFinish) { //如果全都巡检完，则扫描直接看结果
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
                        ToastUtil.show(mContext, "应扫描区域码为 " + areaList.get(i).getAreaCode() + " 的区域");
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
                .subscribe(new DialogObserver<List<InspectionDeviceListBean>>(mContext, true, "正在获取数据") {
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
                            } else { //如果不是随机巡检，就要判断列表里按照顺序需要巡检设备的position
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
                        ToastUtil.show(mContext, "数值传递错误，请重新进入巡检模块");
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

    //selectActivity完成巡检后刷新本页面
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
