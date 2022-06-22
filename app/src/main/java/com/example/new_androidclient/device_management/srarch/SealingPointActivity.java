package com.example.new_androidclient.device_management.srarch;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.NoDataLayout;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.device_management.Adapter.SealPointAdapter;
import com.example.new_androidclient.device_management.bean.SealPointBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 密封点详情
 **/
public class SealingPointActivity extends BaseActivity {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.sealPointList)
    ListView sealPointList;
    @BindView(R.id.nodata)
    NoDataLayout nodata;

    private int devId;
    private SealPointAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sealing_point);
        ButterKnife.bind(this);

        String deviceName = getIntent().getStringExtra("deviceName");
        title.setName(deviceName);

        getValue();
    }

    private void getValue() {
        devId = getIntent().getIntExtra("devId", devId);
        RetrofitUtil.getApi().selectSealPoint(devId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<SealPointBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<SealPointBean> bean) {
                        if (bean != null && bean.size() > 0) {
                            setBasicValue(bean);
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

    private void setBasicValue(List<SealPointBean> bean) {
        adapter = new SealPointAdapter(mContext, bean);
        sealPointList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
