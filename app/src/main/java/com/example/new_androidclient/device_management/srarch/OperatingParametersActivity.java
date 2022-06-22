package com.example.new_androidclient.device_management.srarch;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.NoDataLayout;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.device_management.Adapter.MainParameterAdapter;
import com.example.new_androidclient.device_management.Adapter.OperatingParametersAdapter;
import com.example.new_androidclient.device_management.bean.DeviceSpecBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 运行参数详情
 */
public class OperatingParametersActivity extends BaseActivity {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.sealPointList)
    ListView sealPointList;
    @BindView(R.id.nodata)
    NoDataLayout nodata;

    private String deviceCode;
    private List<DeviceSpecBean.Parm1Bean> list;
    private OperatingParametersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operating_parameters);
        ButterKnife.bind(this);

        String deviceName = getIntent().getStringExtra("deviceName");
        title.setName(deviceName);

        getValue();
    }

    private void getValue() {
        deviceCode = getIntent().getStringExtra("deviceCode");

        RetrofitUtil.getApi().selectDeviceSpec(deviceCode)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<DeviceSpecBean>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(DeviceSpecBean bean) {
                        if (bean != null){
                            list = new ArrayList<>();
                            if (bean.getParm1() != null){
                                list.addAll(bean.getParm1());
                            }else {
                                nodata.setVisibility(View.VISIBLE);
                            }
                            setBasicValue(list);
                        }else {
                            nodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void setBasicValue(List<DeviceSpecBean.Parm1Bean> bean) {
        adapter = new OperatingParametersAdapter(mContext, bean);
        sealPointList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
