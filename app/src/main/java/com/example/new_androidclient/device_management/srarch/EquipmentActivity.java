package com.example.new_androidclient.device_management.srarch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.NoDataLayout;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.device_management.Adapter.EquipmentAdapter;
import com.example.new_androidclient.device_management.Adapter.OperatingParametersAdapter;
import com.example.new_androidclient.device_management.bean.DeviceSpecBean;
import com.example.new_androidclient.device_management.bean.FacilityBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设备详情
 */
public class EquipmentActivity extends BaseActivity {


    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.facilityList)
    ListView facilityList;
    @BindView(R.id.nodata)
    NoDataLayout nodata;

    private int devId;

    private int deviceId1;
    private int deviceId2;

    private String deviceCode;
    private List<FacilityBean> list;
    private EquipmentAdapter adapter;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        ButterKnife.bind(this);

        devId = getIntent().getIntExtra("devId", devId);

        String deviceName = getIntent().getStringExtra("deviceName");
        title.setName(deviceName);

        judge();
    }

    private void judge(){
        type = getIntent().getStringExtra("type");

        if (type.equals("0")){
            getSubsidiaryValue();
        }

        if (type.equals("1")){
            getFunctionValue();
        }

        getShow();
    }
    private void getSubsidiaryValue() {
        RetrofitUtil.getApi().selectDeviceAffiliated(devId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<FacilityBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<FacilityBean> bean) {
                        if (bean.size() > 0) {
                            list = new ArrayList<>();
                            list.addAll(bean);

                            adapter = new EquipmentAdapter(mContext, bean,"0");
                            facilityList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                        }

                    }
                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void getFunctionValue() {
        RetrofitUtil.getApi().selectDeviceAssociated(devId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<FacilityBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<FacilityBean> bean) {
                        if (bean.size() > 0) {
                            list = new ArrayList<>();
                            list.addAll(bean);

                            adapter = new EquipmentAdapter(mContext, bean,"1");
                            facilityList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                        }

                    }
                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    public void getShow(){
        facilityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setSelectPosition(i);

                deviceId1 = list.get(i).getAffiliatedId();
                deviceId2 = list.get(i).getAssociatedId();
                deviceCode = list.get(i).getDeviceNo();
                String deviceName = list.get(i).getDeviceName();

                Intent intent = new Intent(mContext,DetailsActivity.class);
                if (type.equals("0")){
                    intent.putExtra("devId",deviceId1);
                }
                if (type.equals("1")){
                    intent.putExtra("devId",deviceId2);
                }
                intent.putExtra("deviceCode",deviceCode);
                intent.putExtra("deviceName",deviceName);
                startActivity(intent);
            }
        });
    }
}
