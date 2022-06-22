package com.example.new_androidclient.work;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.NoDataLayout;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.work.Adapter.GasAnalysisAdapter;
import com.example.new_androidclient.work.Adapter.HotWorkAdapter;
import com.example.new_androidclient.work.bean.GasAnalysisBean;
import com.example.new_androidclient.work.bean.HotWorkBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * 气体分析
 * */
public class GasAnalysisActivity extends BaseActivity implements View.OnClickListener {

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

    int applicationId;
    List<GasAnalysisBean> beanList = new ArrayList<>();
    GasAnalysisAdapter gasAnalysisAdapter;
    int hotWorkId;
    int hotWorkApplicationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_analysis);
        ButterKnife.bind(this);

        addTo.setOnClickListener(this);
        nextDevice.setOnClickListener(this);
    }

    private void getView() {
        applicationId = getIntent().getIntExtra("applicationId", applicationId);
        RetrofitUtil.getApi().selectWorkingGasDetection(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<GasAnalysisBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<GasAnalysisBean> beans) {
                        if (beans != null) {

                            beanList.addAll(beans);
                            gasAnalysisAdapter = new GasAnalysisAdapter(mContext,beans);
                            gasList.setAdapter(gasAnalysisAdapter);
                        }else {
                            nodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });

        gasList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String siteName = beanList.get(i).getSiteName();
//                String analysisValue = beanList.get(i).getAnalysisValue();
                String analysisUser = beanList.get(i).getAnalysisUser();
                hotWorkId = beanList.get(i).getId();
                hotWorkApplicationId = beanList.get(i).getApplicationId();

                Intent intent = new Intent(mContext, IncreaseHotWorkActivity.class);
//                intent.putExtra("siteName",siteName);
//                intent.putExtra("analysisValue",analysisValue);
                intent.putExtra("hotWorkId",hotWorkId);
                intent.putExtra("hotWorkApplicationId",hotWorkApplicationId);
                intent.putExtra("analysisUser",analysisUser);
                intent.putExtra("type","1");
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //添加
            case R.id.addTo:
                Intent intent = new Intent(mContext, IncreaseHotWorkActivity.class);
                intent.putExtra("type","2");
                intent.putExtra("applicationId",applicationId);
                startActivityForResult(intent,2);
                break;
                //保存
            case R.id.nextDevice:
                setHold();
                break;
        }
    }

    //保存
    private void setHold() {
        RetrofitUtil.getApi().updApplicationTime(applicationId,"DH")
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(String show) {
                        finish();
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
