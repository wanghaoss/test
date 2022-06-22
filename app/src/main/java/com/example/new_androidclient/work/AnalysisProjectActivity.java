package com.example.new_androidclient.work;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.NoDataLayout;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.work.bean.AnalysisBean;
import com.example.new_androidclient.work.bean.NameBean;
import com.example.new_androidclient.work.data.WorkSafeAppointmentLineLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = RouteString.AnalysisProjectActivity)
public class AnalysisProjectActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.nextDevice)
    Button nextDevice;
    @BindView(R.id.nodata)
    NoDataLayout nodata;


    List<WorkSafeAppointmentLineLayout> itemList = new ArrayList<>();
    List<NameBean> analysisList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_project);
        ButterKnife.bind(this);

        getView();


        nextDevice.setOnClickListener(this);
    }

    //获取分析项目
    private void getView() {
        RetrofitUtil.getApi().getChemicalMediumList()
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<AnalysisBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<AnalysisBean> bean) {
                        if (bean.size() > 0) {
                            nodata.setVisibility(View.GONE);
                            for (int i = 0; i < bean.size(); i++) {
                                addView(bean,i);
                            }
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                            scroll.setVisibility(View.GONE);
                            nextDevice.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void addView(List<AnalysisBean> bean, int i) {
        WorkSafeAppointmentLineLayout layout = new WorkSafeAppointmentLineLayout(mContext, i);
        layout.init(0);
        layout.getCheckBox().setText(bean.get(i).getCname());
        layout.getTextView().setVisibility(View.GONE);

        analysisList.clear();

        layout.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    NameBean nameBean = new NameBean();
                    nameBean.setNameId(bean.get(i).getCid());
                    nameBean.setName(bean.get(i).getCname());

                    analysisList.add(nameBean);
                } else {
                    for (int j = 0; j < analysisList.size(); j++) {
                            if (analysisList.get(j).getNameId() == bean.get(i).getCid()){
                                analysisList.remove(j);
                            }
                    }
                }
            }
        });

        linear.addView(layout);
        itemList.add(layout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nextDevice:
                if (analysisList.size() > 0){
                    Intent intent = new Intent();
                    intent.putExtra("analysisList", (Serializable) analysisList);
                    setResult(111,intent);

                    finish();
                }else {
                    ToastUtil.show(mContext,"请至少选择一项");
                }
                break;
        }
    }
}
