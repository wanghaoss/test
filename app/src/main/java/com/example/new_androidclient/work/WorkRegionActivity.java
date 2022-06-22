package com.example.new_androidclient.work;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.DestroyActivityUtil;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.NoDataLayout;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.device_management.Adapter.DeviceInfoAdapter;
import com.example.new_androidclient.device_management.bean.FindDeviceListBean;
import com.example.new_androidclient.device_management.srarch.DeviceInfoActivity;
import com.example.new_androidclient.inspection.CustomCaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * 作业模块设备详情
 * */
@Route(path = RouteString.WorkRegionActivity)
public class WorkRegionActivity extends BaseActivity {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.deviceList)
    ListView deviceList;
    @BindView(R.id.listLayout)
    LinearLayout listLayout;
    @BindView(R.id.addTo)
    Button addTo;
    @BindView(R.id.nodata)
    NoDataLayout nodata;

    DeviceInfoAdapter adapter;
    List<FindDeviceListBean> listBeans = new ArrayList<>();
    int devId;
    String deviceCode;
    int planWorkId;
    int applicationId;
    String type;
    String dhStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_region);
        ButterKnife.bind(this);

        DestroyActivityUtil.addDestoryActivityToMap(WorkRegionActivity.this,"WorkRegionActivity");
        planWorkId = getIntent().getIntExtra("planWorkId",planWorkId);
        applicationId = getIntent().getIntExtra("applicationId",applicationId);
        type = getIntent().getStringExtra("type");
        dhStatus = getIntent().getStringExtra("dhStatus");

        getView();

        addTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("tk")){
                    Intent intent = new Intent(mContext,ReconnaissanceActivity.class);
                    intent.putExtra("value","no");
                    intent.putExtra("planWorkId",planWorkId);
                    intent.putExtra("applicationId",applicationId);
                    startActivity(intent);
                }

                if (type.equals("hc")){
                    Intent intent = new Intent(mContext,SceneCheckActivity.class);
                    intent.putExtra("value","no");
                    intent.putExtra("dhStatus",dhStatus);
                    intent.putExtra("planWorkId",planWorkId);
                    intent.putExtra("applicationId",applicationId);
                    startActivity(intent);
                }
            }
        });
    }

    private void setSearch(String scanResult) {
        RetrofitUtil.getApi().getFindDeviceList(1, 1000, null, null, null, null, null,
                null, null, null, scanResult, null)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<FindDeviceListBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<FindDeviceListBean> bean) {
                        if (bean != null || bean.size()>0) {
                            listBeans = bean;
                            adapter = new DeviceInfoAdapter(mContext, bean);
                            deviceList.setAdapter(adapter);

                            setView();
                            nodata.setVisibility(View.GONE);
                            listLayout.setVisibility(View.VISIBLE);
                        }else {
                            nodata.setVisibility(View.VISIBLE);
                            listLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    public void setView(){
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setSelectPosition(i);
                devId = listBeans.get(i).getDevId();
                deviceCode = listBeans.get(i).getDeviceNo();
                String deviceName = listBeans.get(i).getDeviceName();

                if (type.equals("tk")){
                    Intent intent = new Intent(mContext,ReconnaissanceActivity.class);
                    intent.putExtra("devId",devId);
                    intent.putExtra("deviceCode",deviceCode);
                    intent.putExtra("deviceName",deviceName);
                    intent.putExtra("value","yes");
                    intent.putExtra("planWorkId",planWorkId);
                    intent.putExtra("applicationId",applicationId);
                    startActivity(intent);
                }

                if (type.equals("hc")){
                    Intent intent = new Intent(mContext,SceneCheckActivity.class);
                    intent.putExtra("devId",devId);
                    intent.putExtra("deviceCode",deviceCode);
                    intent.putExtra("deviceName",deviceName);
                    intent.putExtra("value","yes");
                    intent.putExtra("dhStatus",dhStatus);
                    intent.putExtra("planWorkId",planWorkId);
                    intent.putExtra("applicationId",applicationId);
                    startActivity(intent);
                }
            }
        });
    }

    private void getView() {
//        Intent intent2 = new Intent(mContext, CustomCaptureActivity.class);
//        startActivityForResult(intent2, 222);
        ARouter.getInstance().build(RouteString.NFCActivity)
                .withInt("code", 3030)
                .withInt("module", 2) //1人员
                .navigation(WorkRegionActivity.this, 3030);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (null != data) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                finish();
                return;
            }
        } else {
            finish();
            return;
        }

        if (requestCode == 3030) {
            String area = data.getStringExtra("area");
            //将扫描出的信息显示出来
            setSearch(area);
        }
    }
}
