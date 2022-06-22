package com.example.new_androidclient.device_management.srarch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.R;
import com.example.new_androidclient.customize_view.NoDataLayout;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.device_management.Adapter.DeviceInfoAdapter;
import com.example.new_androidclient.device_management.bean.FindDeviceListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *查询全部初始页
 **/
public class DeviceInfoActivity extends BaseActivity {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.deviceInfoList)
    ListView deviceInfoList;

    List<FindDeviceListBean> listBeans;
    @BindView(R.id.nodata)
    NoDataLayout nodata;

    private int devId;
    private String deviceCode;
    private DeviceInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        ButterKnife.bind(this);

        judgeSize();
    }

    private void judgeSize() {
        listBeans = (List<FindDeviceListBean>) getIntent().getSerializableExtra("FindDeviceList");
        if (listBeans != null && listBeans.size()>0){
            deviceInfoList.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);
            getValue();
        }else {
            deviceInfoList.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
        }
    }

    private void getValue() {
        adapter = new DeviceInfoAdapter(mContext, listBeans);
        deviceInfoList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        deviceInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setSelectPosition(i);
                devId = listBeans.get(i).getDevId();
                deviceCode = listBeans.get(i).getDeviceNo();
                String deviceName = listBeans.get(i).getDeviceName();

                Intent intent = new Intent(mContext,DetailsActivity.class);
                intent.putExtra("devId",devId);
                intent.putExtra("deviceCode",deviceCode);
                intent.putExtra("deviceName",deviceName);
                startActivity(intent);
            }
        });
    }
}
