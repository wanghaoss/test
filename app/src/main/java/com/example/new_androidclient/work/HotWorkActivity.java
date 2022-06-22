package com.example.new_androidclient.work;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.annotation.Nullable;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.EventBusMessage.EventBusMessage;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.DestroyActivityUtil;
import com.example.new_androidclient.R;
import com.example.new_androidclient.R2;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.NoDataLayout;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.work.Adapter.HotWorkAdapter;
import com.example.new_androidclient.work.bean.HotWorkBean;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * 动火分析
 * */
public class HotWorkActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.gasList)
    ListView gasList;
    @BindView(R.id.addTo)
    Button addTo;
    @BindView(R.id.nextDevice)
    Button nextDevice;
    @BindView(R.id.nodata)
    NoDataLayout nodata;
    @BindView(R.id.listLayout)
    LinearLayout listLayout;

    int applicationId;
    List<HotWorkBean> beanList = new ArrayList<>();
    HotWorkAdapter analyseAdapter;
    int hotWorkId;
    int hotWorkApplicationId;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_work);
        ButterKnife.bind(this);

        addTo.setOnClickListener(this);
        nextDevice.setOnClickListener(this);
        DestroyActivityUtil.addDestoryActivityToMap(HotWorkActivity.this, "HotWorkActivity");
        applicationId = getIntent().getIntExtra("applicationId", applicationId);
        status = getIntent().getStringExtra("status");

    }

    private void getView() {
        RetrofitUtil.getApi().selectWorkingDhAnalysis(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HotWorkBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<HotWorkBean> beans) {
                        beanList.clear();
                        if (beans != null && beans.size() > 0) {
                            beanList.addAll(beans);
                            setView(beans);
                        } else {
                            listLayout.setVisibility(View.GONE);
                            nodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });

        //列表点击事件
//        gasList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String siteName = beanList.get(i).getSiteName();
//                String analysisValue = beanList.get(i).getAnalysisValue();
//                String analysisUser = beanList.get(i).getAnalysisUser();
//                hotWorkId = beanList.get(i).getId();
//                hotWorkApplicationId = beanList.get(i).getApplicationId();
//                String testMode = beanList.get(i).getTestMode();
//                String analysisItem = beanList.get(i).getAnalysisItem();
//                String analysisResult = beanList.get(i).getAnalysisResult();
//
//                Intent intent = new Intent(mContext, IncreaseHotWorkActivity.class);
//                intent.putExtra("siteName", siteName);
//                intent.putExtra("analysisValue", analysisValue);
//                intent.putExtra("hotWorkId", hotWorkId);
//                intent.putExtra("hotWorkApplicationId", hotWorkApplicationId);
//                intent.putExtra("analysisUser", analysisUser);
//                intent.putExtra("testMode", testMode);
//                intent.putExtra("analysisItem", analysisItem);
//                intent.putExtra("analysisResult", analysisResult);
//                intent.putExtra("type", "1");
//                intent.putExtra("beanList", (Serializable) beanList);
//                startActivityForResult(intent, 1);
//            }
//        });
    }

    private void setView(List<HotWorkBean> beans) {
        String analysisTime = null;
        List<HotWorkBean> hotWorkBeanList = new ArrayList<>();
        for (int i = 0; i < beans.size(); i++) {
            if (beans.get(i).getAnalysisTime() != null){
                analysisTime = beans.get(i).getAnalysisTime();
            }else {
                analysisTime = null;
                beans.remove(i);
            }
        }

//        for (int i = 0; i < beans.size(); i++) {
//            if (beans.get(i).getAnalysisTime() != null){
//                for (int j = 0; j < hotWorkBeanList.size(); j++) {
//                    if (beans.get(i).getAnalysisTime().equals(beans.get(i).getAnalysisTime())){
//                        break;
//                    }
//                }
//            }
//            continue;
//        }

        if (analysisTime == null || analysisTime.isEmpty()){
            listLayout.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
            addTo.setVisibility(View.VISIBLE);
        }else {
            listLayout.setVisibility(View.VISIBLE);
            addTo.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            analyseAdapter = new HotWorkAdapter(mContext, beans);
            gasList.setAdapter(analyseAdapter);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTo:
                Intent intent = new Intent(mContext, IncreaseHotWorkActivity.class);
                intent.putExtra("type", "2");
                intent.putExtra("beanList", (Serializable) beanList);
                intent.putExtra("applicationId", applicationId);
                startActivityForResult(intent, 2);
                break;
            //保存按钮
            case R.id.nextDevice:
                if (beanList.size() > 0){
                    setHold();
                }else {
                    ToastUtil.show(mContext,"请至少添加一项");
                }
//                Intent intent1 = new Intent(mContext,GasAnalysisActivity.class);
//                intent1.putExtra("applicationId",applicationId);
//                startActivity(intent1);
                break;
        }
    }

    //动火分析保存
    private void setHold() {
        RetrofitUtil.getApi().updApplicationTime(applicationId, "DH")
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(String show) {
                        setState("31");
                        ToastUtil.show(mContext, show);
                        finish();
                        //保存成功，到签字页面
                        Intent intent = new Intent(mContext,WorkAddSignActivity.class);
                        intent.putExtra("applicationId",applicationId);
                        intent.putExtra("type","check");
                        intent.putExtra("status",status);
                        startActivity(intent);
                        DestroyActivityUtil.destoryActivity("SceneCheckActivity");
                        DestroyActivityUtil.destoryActivity("CheckMessageActivity");
                        DestroyActivityUtil.destoryActivity("WorkRegionActivity");
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void setState(String state) {
//修改工单状态
        RetrofitUtil.getApi().updateApplicationStatus(applicationId, state)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(String show) {
                        ToastUtil.show(mContext, show);
                        EventBus.getDefault().post(EventBusMessage.getInstance());
                        finish();
                        DestroyActivityUtil.destoryActivity("ReconnaissanceActivity");
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

        getView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getView();
    }
}
