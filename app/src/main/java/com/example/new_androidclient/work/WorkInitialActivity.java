package com.example.new_androidclient.work;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.EventBusMessage.EventBusMessage;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.work.bean.WorkNeedDoBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * 作业待办初始页
 * */
@Route(path = RouteString.WorkInitialActivity)
public class WorkInitialActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.layout1)
    RelativeLayout layout1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.layout2)
    RelativeLayout layout2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.layout3)
    RelativeLayout layout3;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.layout4)
    RelativeLayout layout4;
    @BindView(R.id.text5)
    TextView text5;
    @BindView(R.id.layout5)
    RelativeLayout layout5;
    @BindView(R.id.text6)
    TextView text6;
    @BindView(R.id.layout6)
    RelativeLayout layout6;
    @BindView(R.id.text7)
    TextView text7;
    @BindView(R.id.layout7)
    RelativeLayout layout7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_work_initial);
        ButterKnife.bind(this);

        //点击事件
        setOnclick();
    }

    private void getView() {
        RetrofitUtil.getApi().selectTotal()
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<Map<String, Integer>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(Map<String, Integer> bean) {
                        if (bean.size() > 0) {

                            setView(bean);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //作业待办页设置待办个数
    private void setView(Map<String, Integer> bean) {
        int tk = bean.get("踏勘");
        int gb = bean.get("关闭");
        int hc = bean.get("核查");
        int sq = bean.get("申请");
        int jd = bean.get("监督");
        int xk = bean.get("许可");
        int yy = bean.get("预约");

        String tkString = String.valueOf(tk);
        String sqString = String.valueOf(sq);
        String gbString = String.valueOf(gb);
        String hcString = String.valueOf(hc);
        String jdString = String.valueOf(jd);
        String xkString = String.valueOf(xk);
        String yyString = String.valueOf(yy);

        if (tk > 0){
            text1.setVisibility(View.VISIBLE);
            text1.setText(tkString);
        }else {
            text1.setVisibility(View.GONE);
        }
        if (sq > 0){
            text2.setVisibility(View.VISIBLE);
            text2.setText(sqString);
        }else {
            text2.setVisibility(View.GONE);
        }
        if (yy > 0){
            text3.setVisibility(View.VISIBLE);
            text3.setText(yyString);
        }else {
            text3.setVisibility(View.GONE);
        }
        if (hc > 0){
            text4.setVisibility(View.VISIBLE);
            text4.setText(hcString);
        }else {
            text4.setVisibility(View.GONE);
        }
        if (xk > 0){
            text5.setVisibility(View.VISIBLE);
            text5.setText(xkString);
        }else {
            text5.setVisibility(View.GONE);
        }
        if (jd > 0){
            text6.setVisibility(View.VISIBLE);
            text6.setText(jdString);
        }else {
            text6.setVisibility(View.GONE);
        }
        if (gb > 0){
            text7.setVisibility(View.VISIBLE);
            text7.setText(gbString);
        }else {
            text7.setVisibility(View.GONE);
        }
    }

    private void setOnclick() {
        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);
        layout4.setOnClickListener(this);
        layout5.setOnClickListener(this);
        layout6.setOnClickListener(this);
        layout7.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout1:
                skip(1);
                break;
            case R.id.layout2:
                skip(2);
                break;
            case R.id.layout3:
                skip(3);
                break;
            case R.id.layout4:
                skip(4);
                break;
            case R.id.layout5:
                skip(5);
                break;
            case R.id.layout6:
                skip(6);
                break;
            case R.id.layout7:
                skip(7);
                break;
        }
    }

    public void skip(Integer type){
        Intent intent = new Intent(mContext, WorkNeedDoActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    //完成作业状态修改，刷新接口
    @Subscribe()
    public void onEvent(EventBusMessage message) {
        getView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
