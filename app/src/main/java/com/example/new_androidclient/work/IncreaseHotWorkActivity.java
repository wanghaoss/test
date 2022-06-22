package com.example.new_androidclient.work;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.EventBusMessage.EventBusMessage;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.DestroyActivityUtil;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.work.bean.HotWorkBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;
import com.example.new_androidclient.work.data.WorkSwitchLineLayout;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * 添加动火分析
 */
public class IncreaseHotWorkActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.editText1)
    EditText editText1;
    @BindView(R.id.editText3)
    EditText editText3;
    @BindView(R.id.editText5)
    TextView editText5;
    @BindView(R.id.editText6)
    EditText editText6;
    @BindView(R.id.layout4)
    LinearLayout layout4;
    @BindView(R.id.layout)
    LinearLayout hotLayout;
    @BindView(R.id.layout5)
    LinearLayout layout5;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.hold)
    Button hold;

    int applicationId;
    int hotWorkId;
    int hotWorkApplicationId;
    List<HotWorkBean> beanList = new ArrayList<>();
    List<HotWorkBean> beans = new ArrayList<>();
    HotWorkBean hotWorkBean = new HotWorkBean();;
    String type;
    String date;
    List list;
    //    List<HotWorkBean> beanList;
    List<WorkSwitchLineLayout> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_increase_hot_work);
        ButterKnife.bind(this);

        type = getIntent().getStringExtra("type");
        getDeliver();

        delete.setOnClickListener(this);
        hold.setOnClickListener(this);
        editText5.setOnClickListener(this);
        beanList.clear();
        beanList = (List<HotWorkBean>) getIntent().getSerializableExtra("beanList");

        assert beanList != null;

        for (int i = 0; i < beanList.size(); i++) {
            addView(i,beanList.get(i).getAnalysisItem(),beanList.get(i).getAnalysisValue());
        }
    }

    private void addView(int i, String analysisItem, String analysisValue) {

        beans.clear();
        WorkSwitchLineLayout layout = new WorkSwitchLineLayout(mContext, i);
        layout.init(3);
        layout.setNameText(analysisItem);

        if (type.equals("1")){
            layout.getEditText().setFocusable(false);
            if (analysisValue == null || analysisValue.isEmpty()){
                layout.setEditText_text("");
            }else {
                layout.setEditText_text(analysisValue);
            }
        }

        layout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                hotWorkBean = new HotWorkBean();
                beanList.get(i).setAnalysisItem(analysisItem);
                beanList.get(i).setAnalysisValue(layout.getEditText_text());
                beans.addAll(beanList);
            }
        });



        itemList.add(layout);
        hotLayout.addView(layout);
    }



    private void getDeliver() {

        applicationId = getIntent().getIntExtra("applicationId", applicationId);

        //type = 1   设置值
        if (type.equals("1")) {
            editText1.setFocusable(false);
            editText3.setFocusable(false);
            editText5.setFocusable(false);
            editText6.setFocusable(false);


            //分析点名称
            String siteName = getIntent().getStringExtra("siteName");
            //分析人
            String analysisUser = getIntent().getStringExtra("analysisUser");
            hotWorkId = getIntent().getIntExtra("hotWorkId", hotWorkId);
            hotWorkApplicationId = getIntent().getIntExtra("hotWorkApplicationId", hotWorkApplicationId);
            //检查方式
            String testMode = getIntent().getStringExtra("testMode");
            //分析结论
            String analysisResult = getIntent().getStringExtra("analysisResult");
            editText1.setText(siteName);
            editText3.setText(testMode);
            editText5.setText(analysisResult);
            editText6.setText(analysisUser);
            delete.setVisibility(View.VISIBLE);
            hold.setVisibility(View.GONE);


            list = new ArrayList<>();
            list.add(hotWorkId);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete:
                setDelete(list);
                break;
            case R.id.hold:
                if (editText5.getText().toString().equals("合格")){
                    try {
                        setHold();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else {
                    setView();
                }
                break;
            case R.id.editText5:
                setValueDialog("7");
                break;
        }
    }

    //检测不合格，
    private void setView() {
        AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_work_hot, null);
        TextView cancel =view.findViewById(R.id.confirmTView);
        TextView sure =view.findViewById(R.id.closeTView);
        final EditText edittext =view.findViewById(R.id.contentEdit);
        TextView titleTView = view.findViewById(R.id.titleTView);
        final Dialog dialog= builder.create();

        titleTView.setText("是否确认注销");
        edittext.setVisibility(View.GONE);
        dialog.show();
        dialog.getWindow().setContentView(view);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                for (int i = 0; i < itemList.size(); i++) {
                    itemList.get(i).setEditText_text("");
                }
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                setState("24");
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
                        DestroyActivityUtil.destoryActivity("SceneCheckActivity");
                        DestroyActivityUtil.destoryActivity("CheckMessageActivity");
                        DestroyActivityUtil.destoryActivity("HotWorkActivity");
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void setHold() throws ParseException {

        //获取系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date();

        date = formatter.format(curDate);

        if (editText1.getText().toString().isEmpty()
                || editText3.getText().toString().isEmpty() || editText5.getText().toString().isEmpty()
                || editText6.getText().toString().isEmpty()) {

            ToastUtil.show(mContext, "填写数据不能为空");
        } else {

            if (type.equals("2")) {
                hotWorkBean = new HotWorkBean();
                for (int i = 0; i < beanList.size(); i++) {
                    beanList.get(i).setApplicationId(applicationId);
                    beanList.get(i).setSiteName(editText1.getText().toString());
                    beanList.get(i).setTestMode(editText3.getText().toString());
                    beanList.get(i).setAnalysisResult(editText5.getText().toString());
                    beanList.get(i).setAnalysisUser(editText6.getText().toString());
                    beanList.get(i).setAnalysisTime(date);
                }

                keep(beanList);
            }
        }
    }




    public void keep(List<HotWorkBean> bean) {
        RetrofitUtil.getApi().addDhAnalysis(bean)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(String show) {
                        ToastUtil.show(mContext, show);
                        setResult(1);
                        finish();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    public void setDelete(List<Integer> list) {
        RetrofitUtil.getApi().delDhAnalysis(list)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(String show) {
                        ToastUtil.show(mContext, show);
                        setResult(2);
                        finish();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


    //dialog选择
    private void setValueDialog(String type) {
        new WorkConditionDialog(mContext, type, (value, type1, position, dialog) -> {
            if (type1 != null) {
                if (type1.equals("7")) {
                    editText5.setText(value);
                    dialog.dismiss();
                }
            }
        }).show();
    }
}
