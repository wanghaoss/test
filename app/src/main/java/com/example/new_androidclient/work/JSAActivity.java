package com.example.new_androidclient.work;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.EventBusMessage.EventBusMessage;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.DestroyActivityUtil;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.NoDataLayout;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.inspection.CustomCaptureActivity;
import com.example.new_androidclient.work.bean.JSABean;
import com.example.new_androidclient.work.bean.JSANewsBean;
import com.example.new_androidclient.work.bean.NameBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * */
public class JSAActivity extends BaseActivity implements View.OnClickListener {

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
    //    @BindView(R.id.text10)
//    EditText text10;
    @BindView(R.id.jsaText1)
    TextView jsaText1;
    @BindView(R.id.jsaText2)
    TextView jsaText2;
    @BindView(R.id.jsaText3)
    TextView jsaText3;
    @BindView(R.id.jsaButton1)
    TextView jsaButton1;
    @BindView(R.id.jsaButton2)
    TextView jsaButton2;
    @BindView(R.id.jsaLayout)
    LinearLayout jsaLayout;
    @BindView(R.id.agree)
    Button agree;
    @BindView(R.id.buttonLayout)
    RelativeLayout buttonLayout;
    @BindView(R.id.scoutingLayout)
    LinearLayout scoutingLayout;
    @BindView(R.id.nodata)
    NoDataLayout nodata;
//    @BindView(R.id.projectLayout)
//    RelativeLayout projectLayout;

    int planWork;
    int applicationId;
    String name;
    String manName;
    String itemName;
    int id;
    List<NameBean> nameNewList;
    List<Integer> nameIdList = new ArrayList<>();
    String nameIdValue;
    String memberName;
    JSABean jsaBean;
    String projectSize;
    String startTime;
    String endTime;
    JSANewsBean JSANewsBean = new JSANewsBean();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsa);
        ButterKnife.bind(this);

        getView();
        setClickListener();

    }

    //jsa提交方法
    private void setJSAHoldValue(String type) {
        jsaBean = new JSABean();

        String wcMedium = text7.getText().toString();
        String wcTemperature = text8.getText().toString();
        String wcPressure = text9.getText().toString();
        String involvedSpecialWork = nameIdValue;
        String jsaMenmber = memberName;

        jsaBean.setId(id);
        jsaBean.setPlanWorkId(planWork);
        if (wcMedium.isEmpty()) {
            jsaBean.setWcMedium("");
        } else {
            jsaBean.setWcMedium(wcMedium);
        }

        if (wcTemperature.isEmpty()) {
            jsaBean.setWcTemperature("");
        } else {
            jsaBean.setWcTemperature(wcTemperature);
        }

        if (wcPressure.isEmpty()) {
            jsaBean.setWcPressure("");
        } else {
            jsaBean.setWcPressure(wcPressure);
        }

        if (jsaText2.getText().toString().isEmpty()) {
            jsaBean.setInvolvedSpecialWork("");
        } else {
            jsaBean.setInvolvedSpecialWork(involvedSpecialWork);
        }

        if (jsaText3.getText().toString().isEmpty()) {
            jsaBean.setJsaMenmber("");
        } else {
            jsaBean.setJsaMenmber(jsaMenmber);
        }

        if (jsaText1.getText().toString().equals("刷卡签字") || jsaText1.getText().toString().isEmpty()){
            jsaBean.setProjectLeader(null);
        }else {
            jsaBean.setProjectLeader(20);
        }

        jsaBean.setApplicationId(applicationId);

        if (type.equals("1")){
            if (text7.getText().toString().isEmpty() || text7.getText().toString().equals("") || text8.getText().toString().isEmpty() ||
                    text8.getText().toString().equals("") || text9.getText().toString().isEmpty() || text9.getText().toString().equals("") ||
//                    text10.getText().toString().isEmpty() || text10.getText().toString().equals("") || jsaText1.getText().toString().isEmpty() ||
                    jsaText1.getText().toString().equals("刷卡签字") || jsaText2.getText().toString().isEmpty() || jsaText2.getText().toString().equals("") ||
                    jsaText3.getText().toString().isEmpty() || jsaText3.getText().toString().equals("")) {

                ToastUtil.show(mContext, "填写内容不能为空");
            } else {

//                getHold();
            }
        }
    }


    //修改工单状态
    private void reviseState() {

        //修改工单状态
        RetrofitUtil.getApi().updateSheetStatus(planWork, "11",applicationId)
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


    private void setClickListener() {
        text7.setOnClickListener(this);
        text8.setOnClickListener(this);
        text9.setOnClickListener(this);
        jsaText2.setOnClickListener(this);
        jsaButton1.setOnClickListener(this);
        jsaButton2.setOnClickListener(this);
        agree.setOnClickListener(this);
//        projectLayout.setOnClickListener(this);
        title.getLinearLayout_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                againReturn();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        againReturn();
        return super.onKeyDown(keyCode, event);
    }


    public void againReturn(){
        Intent intent = new Intent(mContext,ReconnaissanceActivity.class);
        intent.putExtra("type","1");
        setResult(22,intent);
    }

    //jsa基本信息接口
    private void getView() {
        planWork = getIntent().getIntExtra("planWork", planWork);
        applicationId = getIntent().getIntExtra("applicationId",applicationId);

        //基本信息
        RetrofitUtil.getApi().selectJsa(planWork)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<JSANewsBean>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(JSANewsBean bean) {
                        if (bean != null) {
                            JSANewsBean = bean;
//                            setScoutingValue(bean);
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                            scoutingLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


    //设置jsa基本信息
    private void setJSAScoutingValue(JSANewsBean bean) {
        id = bean.getId();

        //装置单元
        String DeviceUnit = bean.getInstName() + bean.getUnitName();
        //计划时间
        if("".equals(bean.getPlanStartDate()) || null == bean.getPlanStartDate()){
            startTime = "-";
        }else {
            startTime = bean.getPlanStartDate();
            startTime = startTime.substring(0, 10);
        }

        if("".equals(bean.getPlanEndDate()) || null == bean.getPlanEndDate()){
            endTime = "-";
        }else {
            endTime = bean.getPlanEndDate();
            endTime = endTime.substring(0, 10);
        }

        String planTime = startTime + "-" + endTime;
        //区域位号
        String tagNumber = bean.getAreaName() + bean.getDeviceNo();

        text1.setText(bean.getSheetName());

        if (bean.getDeptName() == null || bean.getDeptName().isEmpty()){
            text2.setText("");
        }else {
            text2.setText(bean.getDeptName());
        }
        text3.setText(DeviceUnit);
//        text4.setText("planTime");
        text4.setText(planTime);
        text5.setText(bean.getSheetNo());
//        text6.setText("tagNumber");
        text6.setText(tagNumber);


        //内容重写
        //介质
        if (bean.getWcMedium() == null || bean.getWcMedium().isEmpty()){
            text7.setText("");
        }else {
            text7.setText(bean.getWcMedium());
        }
        //温度
        if (bean.getWcTemperature() == null || bean.getWcTemperature().isEmpty()){
            text8.setText("");
        }else {
            text8.setText(bean.getWcTemperature());
        }
        //压力
        if (bean.getWcPressure() == null || bean.getWcPressure().isEmpty()){
            text9.setText("");
        }else {
            text9.setText(bean.getWcPressure());
        }
        //其他
        if (bean.getWcOther() == null || bean.getWcOther().isEmpty()){
//            text10.setText("");
        }else {
//            text10.setText(bean.getWcOther());
        }
        //项目负责人
        if (bean.getProjectLeaderName() == null || bean.getProjectLeaderName().isEmpty() || bean.getProjectLeaderName().equals("")){
            jsaText1.setText("刷卡签字");
        }else {
            jsaText1.setText(bean.getProjectLeaderName());
        }
        //jsa成员
        //特殊作业
        if (bean.getJsaMenmber() == null || bean.getJsaMenmber().isEmpty()){
            jsaText3.setText("");
        }else {
            jsaText3.setText(bean.getJsaMenmber());
        }

        if (bean.getInvolvedSpecialWorkName() == null || bean.getInvolvedSpecialWorkName().isEmpty()){
            jsaText2.setText("");
        }else {
            jsaText2.setText(bean.getInvolvedSpecialWorkName());
        }

        //特殊作业Id
        if (bean.getInvolvedSpecialWork() != null && !bean.getInvolvedSpecialWork().equals("")){
            String aa = bean.getInvolvedSpecialWork();
            int cc = Integer.parseInt(aa);
            nameIdList.add(cc);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text7:
                setValueDialog("1");
                break;
            case R.id.text8:
                setValueDialog("2");
                break;
            case R.id.text9:
                setValueDialog("3");
                break;
            case R.id.jsaText2://选中项目涉及的特殊作业
                Intent intent = new Intent(mContext, SpecialWorkActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.jsaButton1://添加JSA成员
                Intent intent1 = new Intent(mContext, MemberActivity.class);
                startActivityForResult(intent1, 2);
                jsaText3.setText("");
                break;
            case R.id.jsaButton2://添加作业风险及安全措施
//                setHoldValue("2");
                if (id > 0) {
                    Intent intent2 = new Intent(mContext, AboutWorkActivity.class);
                    intent2.putExtra("nameIdList", (Serializable) nameIdList);
                    intent2.putExtra("planWork", planWork);
                    startActivityForResult(intent2, 2);
                } else {
//                    getKeep();
                }
                break;
            case R.id.agree://保存
//                setHoldValue("1");
                break;
            case R.id.jsaProjectLayout:
                Intent intent3 = new Intent(mContext, CustomCaptureActivity.class);
                startActivityForResult(intent3, 333);
                break;
        }
    }

    //没有保存前调添加安全作业措施辨识
//    private void getKeep() {
//        RetrofitUtil.getApi().addJsa(jsaBean)
//                .compose(new SchedulerTransformer<>())
//                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {
//
//                    @Override
//                    public void onSuccess(String show) {
//                        getView();
//                        //不显示保存成功
//                        Intent intent2 = new Intent(mContext, AboutWorkActivity.class);
//                        intent2.putExtra("nameIdList", (Serializable) nameIdList);
//                        intent2.putExtra("planWork", planWork);
//                        startActivityForResult(intent2, 2);
//                    }
//
//                    @Override
//                    public void onFailure(String err) {
//                        ToastUtil.show(mContext, err);
//                    }
//                });
//    }

    //dialog选择
    private void setValueDialog(String type) {
        new WorkConditionDialog(mContext, type, (value, type1, position, dialog) -> {
            if (type1 != null) {
                if (type1.equals("1")) {
                    text7.setText(value);
                    dialog.dismiss();
                }
                if (type1.equals("2")) {
                    text8.setText(value);
                    dialog.dismiss();
                }
                if (type1.equals("3")) {
                    text9.setText(value);
                    dialog.dismiss();
                }
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == 1) {

            nameIdList.clear();
            nameNewList = (List<NameBean>) data.getSerializableExtra("name");
            StringBuffer buf = new StringBuffer();
            StringBuffer bufId = new StringBuffer();
            for (int i = 0; i < nameNewList.size(); i++) {
                jsaText2.setText("");
                itemName = nameNewList.get(i).getName();
                int nameId = nameNewList.get(i).getNameId();

                nameIdList.add(nameId);

                buf.append(itemName).append(",");
                if (buf.length() > 0) {
                    name = buf.substring(0, buf.length() - 1);
                }
                bufId.append(nameId).append(",");
                if (bufId.length() > 0) {
                    nameIdValue = bufId.substring(0, bufId.length() - 1);
                }
            }
            jsaText2.setText(name);
        }

        if (requestCode == 2) {
            if (data != null) {
                nameNewList = (List<NameBean>) data.getSerializableExtra("name");
                StringBuffer buf = new StringBuffer();
                for (int i = 0; i < nameNewList.size(); i++) {
                    jsaText3.setText("");
                    memberName = nameNewList.get(i).getName();
                    buf.append(memberName).append(",");
                    if (buf.length() > 0) {
                        manName = buf.substring(0, buf.length() - 1);
                    }
                }
                jsaText3.setText(manName);
            }
        }

        if (requestCode == 333){
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(CodeUtils.RESULT_STRING);
            List<String> list = new ArrayList<>();
            list.clear();
            list.add(scanResult);
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                String value;
                value = list.get(i);
                buf.append(value).append(",");
                if (buf.length() > 0) {
                    projectSize = buf.substring(0, buf.length() - 1);
                }
            }
            //将扫描出的信息显示出来
            jsaText1.setText(projectSize);
        }
    }

    //保存JSA
//    private void getHold() {
//        RetrofitUtil.getApi().addJsa(jsaBean)
//                .compose(new SchedulerTransformer<>())
//                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {
//
//                    @Override
//                    public void onSuccess(String show) {
//                        ToastUtil.show(mContext, show);
//                        reviseState();
//                    }
//
//                    @Override
//                    public void onFailure(String err) {
//                        ToastUtil.show(mContext, err);
//                    }
//                });
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

