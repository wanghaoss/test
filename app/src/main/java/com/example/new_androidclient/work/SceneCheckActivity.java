package com.example.new_androidclient.work;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
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
import com.example.new_androidclient.device_management.srarch.DetailsActivity;
import com.example.new_androidclient.inspection.CustomCaptureActivity;
import com.example.new_androidclient.work.bean.WorkApplicationBaseDetailBean;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * 现场核查
 **/
@Route(path = RouteString.SceneCheckActivity)
public class SceneCheckActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.text5)
    TextView text5;
    @BindView(R.id.text6)
    TextView text6;
    @BindView(R.id.text7)
    TextView text7;
    @BindView(R.id.text8)
    TextView text8;
    @BindView(R.id.text9)
    TextView text9;
    @BindView(R.id.agree)
    Button agree;
    @BindView(R.id.buttonLayout)
    RelativeLayout buttonLayout;
    @BindView(R.id.scoutingLayout)
    LinearLayout scoutingLayout;
    @BindView(R.id.nodata)
    NoDataLayout nodata;

    int applicationId;
    int planWork;
    String startTime;
    String endTime;
    String status;

    int devId;
    String deviceCode;
    String deviceName;
    String deviceValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_check);
        ButterKnife.bind(this);

//        Intent intent1 = new Intent(mContext, CustomCaptureActivity.class);
//        startActivityForResult(intent1, 111);

        agree.setOnClickListener(this);

        deviceValue = getIntent().getStringExtra("value");
        if (deviceValue.equals("yes")){
            title.getLinearLayout_work_switch().setVisibility(View.VISIBLE);
        }else {
            title.getLinearLayout_work_switch().setVisibility(View.GONE);
        }

        getView();

        DestroyActivityUtil.addDestoryActivityToMap(SceneCheckActivity.this,"SceneCheckActivity");
        setOnClick();

    }

    private void setOnClick() {
        title.getLinearLayout_work_switch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDevice();
            }
        });
    }

    //查看设备详情
    private void setDevice() {
        devId = getIntent().getIntExtra("devId",devId);
        deviceCode = getIntent().getStringExtra("deviceCode");
        deviceName = getIntent().getStringExtra("deviceName");

        Intent intent = new Intent(mContext, DetailsActivity.class);
        intent.putExtra("devId",devId);
        intent.putExtra("deviceCode",deviceCode);
        intent.putExtra("deviceName",deviceName);
        startActivity(intent);
    }

    //现场核查，作业信息接口
    private void getView() {
        applicationId = getIntent().getIntExtra("applicationId",applicationId);
        planWork = getIntent().getIntExtra("planWork",planWork);
        status = getIntent().getStringExtra("dhStatus");

        RetrofitUtil.getApi().selectApplication(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<WorkApplicationBaseDetailBean>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(WorkApplicationBaseDetailBean bean) {
                        setItemView(bean);
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //现场核查作业信息赋值
    private void setItemView(WorkApplicationBaseDetailBean bean) {
        //装置单元
        String DeviceUnit = bean.getInstName() + bean.getUnitName();

        //区域位号
        String tagNumber = bean.getAreaName() + bean.getTagNo();

        //计划时间
        if ( "".equals(bean.getPlanStartDate()) || null == bean.getPlanStartDate()){
            startTime = "-";
            text6.setText("-");
        }else {
            startTime = bean.getPlanStartDate();
            startTime = startTime.substring(0, 10);
            text6.setText(bean.getPlanStartDate().toString());
        }
        if ("".equals(bean.getPlanEndDate()) || null == bean.getPlanEndDate()){
            endTime = "-";
        }else {
            endTime = bean.getPlanEndDate();
            endTime = endTime.substring(0, 10);
        }

        String planTime = startTime + "-" + endTime;

        text1.setText(bean.getSheetName());

        text2.setText(bean.getDeptName());

        if (bean.getWorkLeaderName() == null || bean.getWorkLeaderName().isEmpty()){
            text3.setText("-");
        }else{
            text3.setText(bean.getWorkLeaderName());
        }

        text4.setText(DeviceUnit);

        text5.setText(tagNumber);


        text7.setText(planTime);

        if (bean.getProjectLeaderName().isEmpty()){
            text8.setText("-");
        }else {
            text8.setText(bean.getProjectLeaderName());
        }

        if (bean.getWorkersNum().isEmpty()){
            text9.setText("-");
        }else {
            text9.setText(bean.getWorkersNum());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.agree:
                Intent intent = new Intent(mContext,CheckMessageActivity.class);
                intent.putExtra("applicationId",applicationId);
                intent.putExtra("planWork",planWork);
                intent.putExtra("status",status);
                startActivity(intent);

                break;
        }
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
        }else{
            finish();
            return;
        }

        if (requestCode == 111) {
            Bundle bundle = data.getExtras();
            String scanResult = "";
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                scanResult = bundle.getString(CodeUtils.RESULT_STRING);
            }
        }
    }
}
