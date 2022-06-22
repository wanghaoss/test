package com.example.new_androidclient.inspection;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
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
import com.example.new_androidclient.customize_view.InspectionSelectLineLayout;
import com.example.new_androidclient.databinding.ActivityInspectionSelectBinding;
import com.example.new_androidclient.inspection.bean.InspectionDeviceDetailBean;
import com.example.new_androidclient.inspection.bean.InspectionUploadBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 *  随机巡检，展示参数列表然后选择
 */
@Route(path = RouteString.InspectionSelectSpecActivity)
public class InspectionSelectActivity extends BaseActivity {

    private ActivityInspectionSelectBinding binding;
    private List<InspectionDeviceDetailBean> instrumentList = new ArrayList<>();
    private List<InspectionDeviceDetailBean> deviceDetailList = new ArrayList<>();
    private List<InspectionDeviceDetailBean> checkList = new ArrayList<>();
    private List<InspectionUploadBean> uploadBeanList = new ArrayList<>();
    private Listener listener = new Listener();

    private List<LinearLayout> itemList = new ArrayList<>();

    private int working = 10; //运行
    private int spare = 20; //备用
    private int repair = 30; //维修

    @Autowired()
    int isUsingState;

    @Autowired()
    int areaId;

    @Autowired()
    int taskId;

    @Autowired()
    int deviceId;

    @Autowired()
    int devicePosition;  //现在正在巡检的设备在deviceList里面的下标

    @Autowired()
    int taskDeviceId;

    @Autowired()
    String deviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_select);
        binding.setListener(listener);
        initView(isUsingState);
        binding.inspectionDeviceDetailDeviceName.setText(deviceName);
        getDetail();
    }

    private void initView(int usingStatus) {
        isUsingState = usingStatus;
        if (usingStatus == spare) { //20 备用
            binding.useYun.setChecked(false);
            binding.useBei.setChecked(true);
            binding.useWei.setChecked(false);
            setAllViewGone(false);
            setInstrumentListGone();
            setOverhaulTextVisible(false);
        } else if (usingStatus == repair) { //30 维修
            binding.useYun.setChecked(false);
            binding.useBei.setChecked(false);
            binding.useWei.setChecked(true);
            setAllViewGone(true);
        } else {  //10 运行
            binding.useYun.setChecked(true);
            binding.useBei.setChecked(false);
            binding.useWei.setChecked(false);
            setAllViewGone(false);
            setInstrumentVisible();
            setOverhaulTextVisible(false);
        }
    }

    private void getDetail() {
        RetrofitUtil.getApi().getDeviceSpecList(taskId, areaId, deviceId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<InspectionDeviceDetailBean>>(mContext, true, "正在处理数据") {

                    @Override
                    public void onSuccess(List<InspectionDeviceDetailBean> bean) {
                        deviceDetailList.clear();
                        deviceDetailList.addAll(bean);
                        if (deviceDetailList.size() > 0) {
                            for (int i = 0; i < deviceDetailList.size(); i++) {
                                addItem(i, deviceDetailList.get(i).getSpecType());
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void addItem(int pos, String type) {
        InspectionSelectLineLayout layout = new InspectionSelectLineLayout(InspectionSelectActivity.this, pos);
        String name;
        if (type.equals("1") || type.equals("2")) { // 1工艺 2运行
            name = deviceDetailList.get(pos).getAssetSpecName();
            instrumentList.add(deviceDetailList.get(pos));
        } else if (type.equals("3")) { //密封点
            name = deviceDetailList.get(pos).getPointName();
        } else {  //其他
            name = deviceDetailList.get(pos).getOtherSpec();
        }
        layout.initItemWidthEdit(name);
        layout.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkList.add(deviceDetailList.get(layout.getPos()));
            } else {
                checkList.remove(deviceDetailList.get(layout.getPos()));
            }
        });
        binding.inspectionSelectLinear.addView(layout);
        itemList.add(layout);
    }

    private void setInstrumentListGone() {
        if (instrumentList.size() > 0) {
            if (itemList.get(0).getVisibility() == View.VISIBLE) {
                for (int i = 0; i < instrumentList.size(); i++) {
                    itemList.get(i).setVisibility(View.GONE);
                }
                if (instrumentList.size() == deviceDetailList.size()) {
                    binding.inspectionSelectNoInstrumentText.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setInstrumentVisible() {
        if (instrumentList.size() > 0) {
            if (itemList.get(0).getVisibility() == View.GONE) {
                for (int i = 0; i < instrumentList.size(); i++) {
                    itemList.get(i).setVisibility(View.VISIBLE);
                }
            }
            binding.inspectionSelectNoInstrumentText.setVisibility(View.GONE);
        }
    }

    private void setAllViewGone(boolean visible) {
        if (visible) {
            binding.inspectionSelectLinear.setVisibility(View.GONE);
        } else {
            binding.inspectionSelectLinear.setVisibility(View.VISIBLE);
        }
        setOverhaulTextVisible(visible);
    }

    private void setOverhaulTextVisible(boolean visible) {
        if (visible) {
            binding.inspectionDetailNoInstrumentOverhaulText.setVisibility(View.VISIBLE);
        } else {
            binding.inspectionDetailNoInstrumentOverhaulText.setVisibility(View.GONE);
        }
    }


    public class Listener {
        public void get() {
            if (isUsingState == 30) { //检修状态，直接提交数据
                submitData();
            } else if (isUsingState == 20) {  //备用状态，如果instrumentList和所有列表个数相等，说明列表里只有type为工艺或者运行的参数
                if (instrumentList.size() == deviceDetailList.size()) {
                    submitData();
                } else {  //如果不相等说明所有列表里除了instrumentList之外还有密封点或其他参数，需要查看checkList里面去掉instrumentList之后的check个数
                    if (checkList.size() == 0) { //有可选项但没选
                        ToastUtil.show(mContext, "请选择要巡检的参数");
                    } else { //选择了至少一个
                        List<InspectionDeviceDetailBean> temp = new ArrayList<>();
                        temp.addAll(checkList);
                        for (int i = checkList.size() - 1; i >= 0; i--) {  //把在checkList里面的instrument参数去掉
                            if (checkList.get(i).getSpecType().equals("1") ||
                                    checkList.get(i).getSpecType().equals("2")) {
                                temp.remove(i);
                            }
                        }
                        if (temp.size() == 0) {  //如果剩余的为0，说明没选
                            ToastUtil.show(mContext, "请选择要巡检的参数");
                        } else {
                            toDetailActivity(temp);
                        }
                    }
                }
            } else {  //运行状态
                if (checkList.size() == 0) {
                    ToastUtil.show(mContext, "请选择要巡检的参数");
                } else {
                    toDetailActivity(checkList);
                }
            }
        }

        private void toDetailActivity(List<InspectionDeviceDetailBean> list) {
            SPUtil.putListData(SPString.InspectionSpecList, list);
            ARouter.getInstance().build(RouteString.InspectionDeviceDetailActivity)
                    .withInt("areaId", areaId)
                    .withInt("taskId", taskId)
                    .withInt("deviceId", deviceId)
                    .withInt("isUsingStateFromSelectActivity", isUsingState)
                    .withInt("devicePosition", devicePosition)
                    .withBoolean("isRandomInspection", true)
                    .navigation();
            finish();
        }

        private void submitData() {
            RetrofitUtil.getApi().submitDeviceData(taskDeviceId, isUsingState, uploadBeanList)
                    .compose(new SchedulerTransformer<>())
                    .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                        @Override
                        public void onSuccess(String bean) {
                            if (bean.equals("success")) {
                                EventBus.getDefault().post(EventBusMessage.getInstance());
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(String err) {
                            ToastUtil.show(mContext, err);
                        }
                    });
        }

        public void setCheckButton(int usingStatus) {
            initView(usingStatus);
        }
    }
}
