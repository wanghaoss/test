package com.example.new_androidclient.device_management.srarch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.R;
import com.example.new_androidclient.device_management.bean.FindDeviceListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 详情初始
 */
public class DetailsActivity extends BaseActivity implements View.OnClickListener{

    //基本信息
    @BindView(R.id.basicLayout)
    RelativeLayout basicLayout;
    //密封点
    @BindView(R.id.sealingPointLayout)
    RelativeLayout sealingPointLayout;
    //主要参数
    @BindView(R.id.mainParameterLayout)
    RelativeLayout mainParameterLayout;
    //运行参数
    @BindView(R.id.operatingParametersLayout)
    RelativeLayout operatingParametersLayout;
    //附属设备
    @BindView(R.id.subsidiaryLayout)
    RelativeLayout subsidiaryLayout;
    //关联设备
    @BindView(R.id.relationLayout)
    RelativeLayout relationLayout;

    private int devId;
    private String deviceCode;
    private String deviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        getShow();

    }



    public void getShow() {
        devId = getIntent().getIntExtra("devId",devId);
        deviceCode = getIntent().getStringExtra("deviceCode");
        deviceName = getIntent().getStringExtra("deviceName");

        //点击基本信息
        basicLayout.setOnClickListener(this);
        //点击密封点
        sealingPointLayout.setOnClickListener(this);
        //点击主要参数
        mainParameterLayout.setOnClickListener(this);
        //点击运行参数
        operatingParametersLayout.setOnClickListener(this);
        //点击附属设备
        subsidiaryLayout.setOnClickListener(this);
        //点击关联设备
        relationLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.basicLayout:
                firingActivity(BasicActivity.class);
                break;
            case R.id.sealingPointLayout:
                firingActivity(SealingPointActivity.class);
                break;
            case R.id.mainParameterLayout:
                firingActivity(MainParameterActivity.class);
                break;
            case R.id.operatingParametersLayout:
                firingActivity(OperatingParametersActivity.class);
                break;
            case R.id.subsidiaryLayout:
                Intent intent = new Intent(mContext,EquipmentActivity.class);
                intent.putExtra("devId",devId);
                intent.putExtra("deviceCode",deviceCode);
                intent.putExtra("deviceName",deviceName);
                intent.putExtra("type","0");
                startActivity(intent);
                break;
            case R.id.relationLayout:
                Intent intent1 = new Intent(mContext,EquipmentActivity.class);
                intent1.putExtra("devId",devId);
                intent1.putExtra("deviceCode",deviceCode);
                intent1.putExtra("deviceName",deviceName);
                intent1.putExtra("type","1");
                startActivity(intent1);
            break;
        }
    }

    public void firingActivity(Class context){
        Intent intent = new Intent(mContext,context);
        intent.putExtra("devId",devId);
        intent.putExtra("deviceCode",deviceCode);
        intent.putExtra("deviceName",deviceName);
        startActivity(intent);
    }
}
