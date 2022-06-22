package com.example.new_androidclient.inspection;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityInspectionDeviceRoughSelectBinding;
import com.example.new_androidclient.inspection.adapter.InspectionDeviceAreaSelectedDeviceListAdapter;
import com.example.new_androidclient.inspection.adapter.InspectionDeviceRoughSelectedDeviceListAdapter;
import com.example.new_androidclient.inspection.bean.AreaResultDevice;
import com.example.new_androidclient.inspection.bean.ItemResultDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * 计划巡检-宏观巡检/区域巡检，项与设备关联中间页
 */

@Route(path = RouteString.InspectionDeviceRoughAndAreaSelectActivity)
public class InspectionDeviceRoughAndAreaSelectActivity extends BaseActivity {
    private ActivityInspectionDeviceRoughSelectBinding binding;
    private List<ItemResultDevice> deviceList = new ArrayList<>(); //宏观巡检关联列表
    private List<AreaResultDevice> areaList = new ArrayList<>(); //区域巡检关联列表
    private InspectionDeviceRoughSelectedDeviceListAdapter device_adapter;
    private InspectionDeviceAreaSelectedDeviceListAdapter area_adapter;
    private Listener listener = new Listener();

    private int ROUGH = 2;
    private int AREA = 1;

    private int REQUEST_ROUGH = ROUGH;
    private int REQUEST_AREA = AREA;

    @Autowired
    int itemId;
    @Autowired
    int areaId;

    @Autowired
    int from; //1宏观巡检 2区域巡检


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_device_rough_select);
        device_adapter = new InspectionDeviceRoughSelectedDeviceListAdapter(deviceList, BR.inspection_device_rough_selected_list_item_bean, new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ARouter.getInstance().build(RouteString.InspectionDeviceRoughAndAreaTakePhotoActivity)
                        .withObject("ItemResultDevice_rough", deviceList.get(position))
                        .withInt("from", from)
                        .navigation();
            }
        });
        area_adapter = new InspectionDeviceAreaSelectedDeviceListAdapter(areaList, BR.inspection_device_area_selected_list_item_bean, new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ARouter.getInstance().build(RouteString.InspectionDeviceRoughAndAreaTakePhotoActivity)
                        .withObject("AreaResultDevice_area", areaList.get(position))
                        .withInt("from", from)
                        .navigation();
            }
        });
        binding.setListener(listener);
        binding.inspectionDeviceRoughSelectRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.inspectionDeviceAreaSelectRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.inspectionDeviceRoughSelectRecycler.setAdapter(device_adapter);
        binding.inspectionDeviceAreaSelectRecycler.setAdapter(area_adapter);
        binding.title.getLinearLayout_back().setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (from == ROUGH) {  // 1宏观巡检 2区域巡检
            getDeviceList(); //计划巡检-区域巡检 查询项关联的设备
        } else {
            getAreaList(); //计划巡检-宏观巡检 查询项关联的设备
        }
    }


    //计划巡检-宏观巡检 查询项关联的设备
    private void getAreaList() {
        RetrofitUtil.getApi().findItemResultDevice_area(itemId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<AreaResultDevice>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<AreaResultDevice> bean) {
                        binding.inspectionDeviceRoughSelectRecycler.setVisibility(View.GONE);
                        binding.inspectionDeviceAreaSelectRecycler.setVisibility(View.VISIBLE);
                        areaList.clear();
                        if (bean.size() > 0) {
                            areaList.addAll(bean);
                            area_adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //计划巡检-区域巡检 查询项关联的设备
    private void getDeviceList() {
        RetrofitUtil.getApi().findItemResultDevice_rough(itemId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<ItemResultDevice>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<ItemResultDevice> bean) {
                        binding.inspectionDeviceRoughSelectRecycler.setVisibility(View.VISIBLE);
                        binding.inspectionDeviceAreaSelectRecycler.setVisibility(View.GONE);
                        deviceList.clear();
                        if (bean.size() > 0) {
                            deviceList.addAll(bean);
                            device_adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_ROUGH) {
            if (data.getExtras() != null) {
                int deviceId = data.getIntExtra("deviceId", 0);
                if (deviceId > 0) {
                    //计划巡检-区域巡检 给项添加设备
                    InstItemResultDevice_rough(String.valueOf(deviceId));
                }
            }
        }

        if (resultCode == REQUEST_AREA) {
            if (data.getExtras() != null) {
                int deviceId = data.getIntExtra("deviceId", 0);
                if (deviceId > 0) {
                    //计划巡检-宏观巡检 给项添加设备
                    InstItemResultDevice_area(String.valueOf(deviceId));
                }
            }
        }
    }

    //计划巡检-宏观巡检 给项添加设备
    private void InstItemResultDevice_area(String deviceId) {
        RetrofitUtil.getApi().instItemResultDevice_area(itemId, deviceId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("添加成功")) {
                            getAreaList();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


    //计划巡检-区域巡检 给项添加设备
    private void InstItemResultDevice_rough(String deviceId) {
        RetrofitUtil.getApi().instItemResultDevice_rough(itemId, deviceId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("添加成功")) {
                            getDeviceList();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void selectDevice() {
        String name = binding.inspectionDeviceRoughSelectDeviceName.getText().toString();
        String tag = binding.inspectionDeviceRoughSelectDeviceTag.getText().toString();
        if (binding.inspectionDeviceRoughSelectDeviceName.getText().toString().isEmpty() &&
                binding.inspectionDeviceRoughSelectDeviceTag.getText().toString().isEmpty()) {
            ToastUtil.show(mContext, "请至少填写一项");
        } else {
            int temp;
            if (from == AREA) {   // 1区域巡检 2宏观巡检
                temp = REQUEST_AREA;
            } else {
                temp = REQUEST_ROUGH;
            }
            ARouter.getInstance().build(RouteString.InspectionDeviceRoughAndAreaSelectDeviceListActivity)
                    .withString("name", name)
                    .withString("tag", tag)
                    .withInt("areaId", areaId)
                    .withInt("from", from)
                    .navigation(this, temp);
        }
    }

    public class Listener {
        public void select() {
            selectDevice();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (from == 1) {  //宏观巡检
            for (int i = 0; i < areaList.size(); i++) {
                if (areaList.get(i).getResultMsg() == null || areaList.get(i).getResultMsg().isEmpty()) {
                    ToastUtil.show(mContext, "已关联设备请拍照并填写描述");
                    return false;
                }
            }
        } else {  //区域巡检
            for (int i = 0; i < deviceList.size(); i++) {
                if (deviceList.get(i).getResultMsg() == null || deviceList.get(i).getResultMsg().isEmpty()) {
                    ToastUtil.show(mContext, "已关联设备请拍照并填写描述");
                    return false;
                }
            }
        }
          return super.onKeyDown(keyCode, event);
    }
}
