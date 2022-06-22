package com.example.new_androidclient.device_management.srarch;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.NoDataLayout;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.device_management.bean.BasicBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 基本信息详情
 **/
public class BasicActivity extends BaseActivity {

    @BindView(R.id.device1)
    TextView device1;
    @BindView(R.id.device2)
    TextView device2;
    @BindView(R.id.device3)
    TextView device3;
    @BindView(R.id.device4)
    TextView device4;
    @BindView(R.id.device5)
    TextView device5;
    @BindView(R.id.device6)
    TextView device6;
    @BindView(R.id.device7)
    TextView device7;
    @BindView(R.id.device8)
    TextView device8;
    @BindView(R.id.device9)
    TextView device9;
    @BindView(R.id.device10)
    TextView device10;
    @BindView(R.id.device11)
    TextView device11;
    @BindView(R.id.device12)
    TextView device12;
    @BindView(R.id.device13)
    TextView device13;
    @BindView(R.id.device14)
    TextView device14;
    @BindView(R.id.device15)
    TextView device15;
    @BindView(R.id.device16)
    TextView device16;
    @BindView(R.id.device17)
    TextView device17;
    @BindView(R.id.device18)
    TextView device18;
    @BindView(R.id.nodata)
    NoDataLayout nodata;
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.textValue)
    LinearLayout textValue;

    private int devId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        ButterKnife.bind(this);

        getValue();
    }

    private void getValue() {
        devId = getIntent().getIntExtra("devId", devId);
        RetrofitUtil.getApi().selectDeviceInfo(devId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<BasicBean>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(BasicBean bean) {
                        if (bean != null) {
                            setBasicValue(bean);
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                            textValue.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //基本信息设置值
    private void setBasicValue(BasicBean bean) {
        String deviceName = getIntent().getStringExtra("deviceName");
        title.setName(deviceName);

        if (bean.getDeviceNo() != null) {
            device1.setText(bean.getDeviceNo());
        } else {
            device1.setText("-");
        }

        if (bean.getDeviceName() != null) {
            device2.setText(bean.getDeviceName());
        } else {
            device2.setText("-");
        }

        if (bean.getTagNo() != null) {
            device3.setText(bean.getTagNo());
        } else {
            device3.setText("-");
        }

        if (bean.getFactoryName() != null) {
            device4.setText(bean.getFactoryName());
        } else {
            device4.setText("-");
        }

        if (bean.getDptName() != null) {
            device5.setText(bean.getDptName());
        } else {
            device5.setText("-");
        }

        if (bean.getInstName() != null) {
            device6.setText(bean.getInstName());
        } else {
            device6.setText("-");
        }

        if (bean.getModel() != null) {
            device7.setText(bean.getModel().toString());
        } else {
            device7.setText("-");
        }

        if (bean.getSpec() != null) {
            device8.setText(bean.getSpec().toString());
        } else {
            device8.setText("-");
        }

        if (bean.getMedium() != null) {
            device9.setText(bean.getMedium().toString());
        } else {
            device9.setText("-");
        }

        if (bean.getDeviceClassName() != null) {
            device10.setText(bean.getDeviceClassName().toString());
        } else {
            device10.setText("-");
        }

        if (bean.getDeviceCatExtName() != null) {
            device11.setText(bean.getDeviceCatExtName().toString());
        } else {
            device11.setText("-");
        }

        if (bean.getProfessionalCatName() != null) {
            device12.setText(bean.getProfessionalCatName().toString());
        } else {
            device12.setText("-");
        }

        if (bean.getDeviceMgtCatName() != null) {
            device13.setText(bean.getDeviceMgtCatName().toString());
        } else {
            device13.setText("-");
        }

        if (bean.getCircuitNo() != null) {
            device14.setText(bean.getCircuitNo().toString());
        } else {
            device14.setText("-");
        }

        if (bean.getCircuitType() != null) {
            if (bean.getCircuitType().equals("1")) {
                device15.setText("测量回路");
            } else {
                device15.setText("控制回路");
            }
        } else {
            device15.setText("-");
        }

        if (bean.getDesignProviderName() != null) {
            device16.setText(bean.getDesignProviderName().toString());
        } else {
            device16.setText("-");
        }

        if (bean.getDesignDate() != null) {
            device17.setText(bean.getDesignDate().toString());
        } else {
            device17.setText("-");
        }

        if (bean.getConstructProviderName() != null) {
            device18.setText(bean.getConstructProviderName().toString());
        } else {
            device18.setText("-");
        }

    }
}
