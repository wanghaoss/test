package com.example.new_androidclient.work;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.EventBusMessage.EventBusMessage;
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
import com.example.new_androidclient.inspection.AreaDistinguishActivity;
import com.example.new_androidclient.inspection.CustomCaptureActivity;
import com.example.new_androidclient.login.NFCActivity;
import com.example.new_androidclient.work.bean.HoldBean;
import com.example.new_androidclient.work.bean.JSABean;
import com.example.new_androidclient.work.bean.JSANewsBean;
import com.example.new_androidclient.work.bean.NameBean;
import com.example.new_androidclient.work.bean.SurveyBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * 踏勘与JSA
 **/
@Route(path = RouteString.ReconnaissanceActivity)
public class ReconnaissanceActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.tkText1)
    TextView tkText1;
    @BindView(R.id.tkText2)
    TextView tkText2;
    @BindView(R.id.tkText3)
    TextView tkText3;
    @BindView(R.id.tkText4)
    TextView tkText4;
    @BindView(R.id.tkText5)
    TextView tkText5;
    @BindView(R.id.tkText6)
    TextView tkText6;
    @BindView(R.id.tkText11)
    TextView tkText11;
    @BindView(R.id.tkProjectLayout)
    RelativeLayout tkProjectLayout;
    @BindView(R.id.tkText12)
    TextView tkText12;
    @BindView(R.id.tkWorkLayout)
    RelativeLayout tkWorkLayout;
    @BindView(R.id.tkText13)
    TextView tkText13;
    @BindView(R.id.tkButton)
    TextView tkButton;
    @BindView(R.id.tkText14)
    EditText tkText14;
    @BindView(R.id.tkText15)
    EditText tkText15;
    @BindView(R.id.tkText16)
    EditText tkText16;
    @BindView(R.id.tkLayout)
    LinearLayout tkLayout;
    @BindView(R.id.jsaText1)
    TextView jsaText1;
    @BindView(R.id.jsaText2)
    TextView jsaText2;
    @BindView(R.id.jsaText3)
    TextView jsaText3;
    @BindView(R.id.jsaText4)
    TextView jsaText4;
    @BindView(R.id.jsaText5)
    TextView jsaText5;
    @BindView(R.id.jsaText6)
    TextView jsaText6;
    @BindView(R.id.jsaText7)
    TextView jsaText7;
    @BindView(R.id.jsaText8)
    TextView jsaText8;
    @BindView(R.id.jsaText9)
    TextView jsaText9;
    @BindView(R.id.jsaText10)
    TextView jsaText10;
    @BindView(R.id.jsaProjectLayout)
    RelativeLayout jsaProjectLayout;
    @BindView(R.id.jsaText11)
    TextView jsaText11;
    @BindView(R.id.jsaText12)
    TextView jsaText12;
    @BindView(R.id.jsaButton1)
    TextView jsaButton1;
    @BindView(R.id.jsaButton2)
    TextView jsaButton2;
    @BindView(R.id.jsaLayout)
    LinearLayout jsaLayout;
    @BindView(R.id.scrollviewLayout)
    LinearLayout scrollviewLayout;
    @BindView(R.id.nodata)
    NoDataLayout nodata;
    @BindView(R.id.tkHold)
    Button tkHold;
    @BindView(R.id.tkSubmit)
    Button tkSubmit;
    @BindView(R.id.jsaHold)
    Button jsaHold;
    @BindView(R.id.jsaSubmit)
    Button jsaSubmit;
    @BindView(R.id.tkSwitch)
    Button tkSwitch;
    @BindView(R.id.jsaSwitch)
    Button jsaSwitch;
    @BindView(R.id.jsaElseText)
    TextView jsaElseText;


    int planWork;
    int applicationId;
    String memberName;
    String tkName; //踏勘成员
    String jsaInvolveWork;
    List<NameBean> nameNewList;
    List<Integer> nameIdList = new ArrayList<>();
    JSABean jsaBean = new JSABean();
    int id;
    String startTime;
    String endTime;
    String nameIdValue;
    String projectSize;
    String manName;
    String itemName;
    HoldBean holdBean = new HoldBean();;
    JSANewsBean JSANewsBean = new JSANewsBean();
    int devId;
    String deviceCode;
    String deviceName;
    String deviceValue;
    private int NFC = 1;
    int jsaWorkProject;
    int tkWorkProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconnaissance);
        ButterKnife.bind(this);


        planWork = getIntent().getIntExtra("planWorkId", planWork);
        applicationId = getIntent().getIntExtra("applicationId",applicationId);

        holdBean.setApplicationId(applicationId);
        holdBean.setPlanWorkId(planWork);

        jsaBean.setPlanWorkId(planWork);
        jsaBean.setApplicationId(applicationId);

        getTKView();
        getJSAView();
        setClickListener();

        deviceValue = getIntent().getStringExtra("value");
        if (deviceValue.equals("yes")){
            title.getLinearLayout_work_switch().setVisibility(View.VISIBLE);
        }else {
            title.getLinearLayout_work_switch().setVisibility(View.GONE);
        }
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


    //踏勘获取基本信息
    private void getTKView() {
        RetrofitUtil.getApi().selectSurvey(planWork)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<SurveyBean>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(SurveyBean bean) {
                        if (bean != null) {
                            setTKScoutingValue(bean);
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                            tkLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //设置踏勘基本信息
    private void setTKScoutingValue(SurveyBean bean) {

        if (bean.getId() == null){
            getHold("tk","");
        }else {
            holdBean.setId(bean.getId());
        }

        //装置单元
        String DeviceUnit = bean.getInstName() + bean.getUnitName();
        //计划时间
        if ("".equals(bean.getPlanStartDate()) || null == bean.getPlanStartDate()) {
            startTime = "-";
        } else {
            startTime = bean.getPlanStartDate();
            startTime = startTime.substring(0, 10);
        }

        if ("".equals(bean.getPlanEndDate()) || null == bean.getPlanEndDate()) {
            endTime = "-";
        } else {
            endTime = bean.getPlanEndDate();
            endTime = endTime.substring(0, 10);
        }

        String planTime = startTime + "-" + endTime;
        //区域位号
        String tagNumber = bean.getAreaName() + bean.getTagNo();

        holdBean.setPlanWorkId(planWork);

        tkText1.setText(bean.getSheetName());
        if (bean.getDeptName() == null || bean.getDeptName().isEmpty()) {
            tkText2.setText("");
        } else {
            tkText2.setText(bean.getDeptName());
        }
        tkText3.setText(DeviceUnit);
        tkText4.setText(planTime);
        tkText5.setText(bean.getSheetNo());
        tkText6.setText(tagNumber);


        //数据重写
        //项目负责人
        if (bean.getProjectLeader() == null || bean.getProjectLeader().isEmpty() || bean.getProjectLeader().equals("")) {
            tkText11.setText("刷卡签字");
        } else {
            tkText11.setText(bean.getProjectLeader());
        }
        //作业负责人
        if (bean.getWorkLeaderName() == null || bean.getWorkLeaderName().isEmpty() || bean.getWorkLeaderName().equals("")) {
            tkText12.setText("刷卡签字");
        } else {
            tkText12.setText(bean.getWorkLeaderName());
        }
        //踏勘成员
        if (bean.getSurveyMember() == null) {
            tkText13.setText("");
        } else {
            tkText13.setText(bean.getSurveyMember());
        }
        //作业内容现场核查
        if (bean == null) {
            tkText14.setText("");
        } else {
            tkText14.setText(bean.getReviewContent());
        }
        //涉及的地下隐蔽工程
        if (bean == null) {
            tkText15.setText("");
        } else {
            tkText15.setText(bean.getProjectInvolved());
        }
        //受交叉额影响作业
        if (bean == null) {
            tkText16.setText("");
        } else {
            tkText16.setText(bean.getAffectedCrossWork());
        }

    }

    //提交方法
    private void setHoldValue() {
        String reviewContent = tkText14.getText().toString();//作业内容现场复核
        String projectInvolved = tkText15.getText().toString();//涉及的地下隐藏工程
        String affectedCrossWork = tkText16.getText().toString();//受影响的交叉作业
        String wcMedium = jsaText7.getText().toString();//介质
        String wcTemperature = jsaText8.getText().toString();//温度
        String wcPressure = jsaText9.getText().toString();//压力
        String involvedSpecialWork = nameIdValue;//项目涉及特殊作业
        String jsaMenmber = jsaText12.getText().toString();//jsa成员
        String projectLeader = jsaText10.getText().toString();//项目负责人


        //jsa判断
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

        if (jsaText11.getText().toString().isEmpty()) {
            jsaBean.setInvolvedSpecialWork("");
        } else {
            jsaBean.setInvolvedSpecialWork(involvedSpecialWork);
        }

        if (jsaMenmber.isEmpty()) {
            jsaBean.setJsaMenmber("");
        } else {
            jsaBean.setJsaMenmber(jsaMenmber);
        }

        if (projectLeader.equals("刷卡签字") || projectLeader.isEmpty()){
            jsaBean.setProjectLeader(null);
        }else {
            jsaBean.setProjectLeader(jsaWorkProject);
        }

        if (jsaElseText.getText().toString().isEmpty()){
            jsaBean.setWcOther(null);
        }else {
            jsaBean.setWcOther(jsaElseText.getText().toString());
        }


        if (reviewContent.isEmpty()) {
            holdBean.setReviewContent("");
        } else {
            holdBean.setReviewContent(reviewContent);
        }

        if (tkText13.getText().toString().isEmpty()) {
            holdBean.setSurveyMember("");
        } else {
            holdBean.setSurveyMember(tkName);
        }

        if (tkText12.getText().toString().isEmpty() || tkText12.getText().toString().equals("刷卡签字")) {
            holdBean.setWorkLeader(null);
        } else {
            holdBean.setWorkLeader(tkWorkProject);
        }


        holdBean.setProjectInvolved(projectInvolved);
        holdBean.setAffectedCrossWork(affectedCrossWork);

        if (tkText13.getText().toString().isEmpty() || tkText14.getText().toString().isEmpty() ||
                tkText11.getText().toString().equals("刷卡签字") || tkText12.getText().toString().equals("刷卡签字") ||
                wcMedium.isEmpty() || wcTemperature.isEmpty() || wcPressure.isEmpty() || jsaMenmber.isEmpty() || projectLeader.isEmpty() ||
                projectLeader.equals("项目负责人") || jsaText11.getText().toString().isEmpty()) {
            if (tkText13.getText().toString().isEmpty()) {
                ToastUtil.show(mContext, "踏勘成员不能为空");
            }
            if (tkText14.getText().toString().isEmpty()) {
                ToastUtil.show(mContext, "工程量交底不能为空");
            }
            if (tkText11.getText().equals("刷卡签字")) {
                ToastUtil.show(mContext, "踏勘项目负责人不能为空");
            }
            if (tkText12.getText().equals("刷卡签字")) {
                ToastUtil.show(mContext, "踏勘作业负责人不能为空");
            }
            if (wcMedium.isEmpty()) {
                ToastUtil.show(mContext, "JSA介质不能为空");
            }
            if (wcTemperature.isEmpty()) {
                ToastUtil.show(mContext, "JSA温度不能为空");
            }
            if (wcPressure.isEmpty()) {
                ToastUtil.show(mContext, "JSA压力不能为空");
            }
            if (jsaMenmber.isEmpty()) {
                ToastUtil.show(mContext, "JSA成员不能为空");
            }
            if (projectLeader.isEmpty() || projectLeader.equals("刷卡签字")){
                ToastUtil.show(mContext, "JSA项目负责人不能为空");
            }
            if (projectLeader.isEmpty() || projectLeader.equals("刷卡签字")){
                ToastUtil.show(mContext, "JSA项目负责人不能为空");
            }
            if (jsaText11.getText().toString().isEmpty()){
                ToastUtil.show(mContext, "JSA项目设置特殊作业不能为空");
            }
        } else {
            getHold("jsa","no");
            getHold("tk","confirm");
        }

    }

    private void setClickListener() {
        DestroyActivityUtil.addDestoryActivityToMap(ReconnaissanceActivity.this, "ReconnaissanceActivity");
        tkButton.setOnClickListener(this);
        tkHold.setOnClickListener(this);
        tkSubmit.setOnClickListener(this);
//        tkProjectLayout.setOnClickListener(this);
        tkWorkLayout.setOnClickListener(this);
        tkSwitch.setOnClickListener(this);


        jsaSwitch.setOnClickListener(this);
        jsaText7.setOnClickListener(this);
        jsaText8.setOnClickListener(this);
        jsaText9.setOnClickListener(this);
        jsaProjectLayout.setOnClickListener(this);
        jsaText11.setOnClickListener(this);
        jsaButton1.setOnClickListener(this);
        jsaButton2.setOnClickListener(this);
        jsaSubmit.setOnClickListener(this);
        jsaHold.setOnClickListener(this);

        title.getLinearLayout_work_switch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDevice();
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //踏勘保存
            case R.id.tkHold:
                getHold("tk","no");
                break;
            //踏勘提交
            case R.id.tkSubmit:
            case R.id.jsaSubmit://保存
                setHoldValue();
                break;
            //切换jsa
            case R.id.tkSwitch:
                tkLayout.setVisibility(View.GONE);
                jsaLayout.setVisibility(View.VISIBLE);
                title.setName("作业安全分析（JSA）");
                break;
            //查找踏勘成员
            case R.id.tkButton:
                Intent intent = new Intent(mContext, MemberActivity.class);
                startActivityForResult(intent, 1010);
                tkText13.setText("");
                break;
            //项目负责人刷卡签字
            case R.id.tkProjectLayout:
                Intent intent1 = new Intent(mContext, CustomCaptureActivity.class);
                startActivityForResult(intent1, 111);
                break;
            //作业负责人刷卡签字
            case R.id.tkWorkLayout:

                //更换刷卡
                ARouter.getInstance().build(RouteString.NFCActivity)
                        .withInt("code", 2020)
                        .withInt("module", 1) //1人员
                        .navigation(ReconnaissanceActivity.this, 2020);
//                Intent intent2 = new Intent(mContext, NFCActivity.class);
//                startActivityForResult(intent2, 222);
                break;


                //jsa
            //jsa切换
            case R.id.jsaSwitch:
                jsaLayout.setVisibility(View.GONE);
                tkLayout.setVisibility(View.VISIBLE);
                if (deviceValue.equals("yes")){
                    title.getLinearLayout_work_switch().setVisibility(View.VISIBLE);
                }else {
                    title.getLinearLayout_work_switch().setVisibility(View.GONE);
                }
                title.setName("踏勘交底记录");
                break;
            //介质
            case R.id.jsaText7:
                setValueDialog("1");
                break;
            //温度
            case R.id.jsaText8:
                setValueDialog("2");
                break;
            //压力
            case R.id.jsaText9:
                setValueDialog("3");
                break;
            case R.id.jsaText11://选中项目涉及的特殊作业
                Intent intent3 = new Intent(mContext, SpecialWorkActivity.class);
                startActivityForResult(intent3, 11);
                break;
            case R.id.jsaButton1://添加JSA成员
                Intent intent4 = new Intent(mContext, MemberActivity.class);
                startActivityForResult(intent4, 2);
                jsaText3.setText("");
                break;
            case R.id.jsaButton2://添加作业风险及安全措施
                getHold("jsa","no");
                if (id > 0) {
                    Intent intent5 = new Intent(mContext, AboutWorkActivity.class);
                    intent5.putExtra("nameIdList", (Serializable) nameIdList);
                    intent5.putExtra("planWork", planWork);
                    startActivityForResult(intent5, 2222);
                } else {
                    getKeep();
                }
                break;
            case R.id.jsaProjectLayout:
//                Intent intent6 = new Intent(mContext, CustomCaptureActivity.class);
//                startActivityForResult(intent6, 333);
                ARouter.getInstance().build(RouteString.NFCActivity)
                        .withInt("code", 3030)
                        .withInt("module", 1) //1人员
                        .navigation(ReconnaissanceActivity.this, 3030);
                break;
            case R.id.jsaHold:
                getHold("jsa","no");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (null != data) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
        } else {
            return;
        }

        if (requestCode == 1010 && resultCode == 1) {
            nameNewList = (List<NameBean>) data.getSerializableExtra("name");
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < nameNewList.size(); i++) {
                tkText13.setText("");
                memberName = nameNewList.get(i).getName();
                buf.append(memberName).append(",");
                if (buf.length() > 0) {
                    tkName = buf.substring(0, buf.length() - 1);
                }
            }
            tkText13.setText(tkName);
        }

        if (requestCode == 111) {
            Bundle bundle = data.getExtras();
            String scanResult = "";
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                scanResult = bundle.getString(CodeUtils.RESULT_STRING);
            }
            tkText11.setText(scanResult);
        }

        if (requestCode == 2020) {
            String uid = data.getStringExtra("uid");
            String tkWorkProjectId = data.getStringExtra("id");
            tkWorkProject = Integer.parseInt(tkWorkProjectId);
            String name = data.getStringExtra("name");
            //将扫描出的信息显示出来
            tkText12.setText(name);
        }

        if (requestCode == 11 && resultCode == 1) {

            nameIdList.clear();
            nameNewList = (List<NameBean>) data.getSerializableExtra("name");
            StringBuffer buf = new StringBuffer();
            StringBuffer bufId = new StringBuffer();
            for (int i = 0; i < nameNewList.size(); i++) {
                jsaText11.setText("");
                itemName = nameNewList.get(i).getName();
                int nameId = nameNewList.get(i).getNameId();

                nameIdList.add(nameId);

                buf.append(itemName).append(",");
                if (buf.length() > 0) {
                    jsaInvolveWork = buf.substring(0, buf.length() - 1);
                }
                bufId.append(nameId).append(",");
                if (bufId.length() > 0) {
                    nameIdValue = bufId.substring(0, bufId.length() - 1);
                }
            }
            jsaText11.setText(jsaInvolveWork);
        }

        if (requestCode == 2) {
            if (data != null) {
                nameNewList = (List<NameBean>) data.getSerializableExtra("name");
                StringBuffer buf = new StringBuffer();
                for (int i = 0; i < nameNewList.size(); i++) {
                    jsaText12.setText("");
                    memberName = nameNewList.get(i).getName();
                    buf.append(memberName).append(",");
                    if (buf.length() > 0) {
                        manName = buf.substring(0, buf.length() - 1);
                    }
                }
                jsaText12.setText(manName);
            }
        }

//        if (requestCode == 202020){
//            Bundle bundle = data.getExtras();
//            String scanResult = bundle.getString(CodeUtils.RESULT_STRING);
//            List<String> list = new ArrayList<>();
//            list.clear();
//            list.add(scanResult);
//            StringBuffer buf = new StringBuffer();
//            for (int i = 0; i < list.size(); i++) {
//                String value;
//                value = list.get(i);
//                buf.append(value).append(",");
//                if (buf.length() > 0) {
//                    projectSize = buf.substring(0, buf.length() - 1);
//                }
//            }
//            //将扫描出的信息显示出来
//            jsaText10.setText(projectSize);
//        }

        if (requestCode == 3030) {
            String uid = data.getStringExtra("uid");
            String jsaWorkProjectId = data.getStringExtra("id");
            jsaWorkProject = Integer.parseInt(jsaWorkProjectId);
            String name = data.getStringExtra("name");
            //将扫描出的信息显示出来
            jsaText10.setText(name);
        }

    }

    //没有保存前调添加安全作业措施辨识
    private void getKeep() {
        RetrofitUtil.getApi().addJsa(jsaBean)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<Integer>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(Integer show) {
                        jsaBean.setId(show);
                        //不显示保存成功
                        Intent intent2 = new Intent(mContext, AboutWorkActivity.class);
                        intent2.putExtra("nameIdList", (Serializable) nameIdList);
                        intent2.putExtra("planWork", planWork);
                        startActivityForResult(intent2, 2222);
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


    //踏勘保存
    private void getHold(String type,String modify) {
        if (type.equals("tk")){
            RetrofitUtil.getApi().addSurvey(holdBean)
                    .compose(new SchedulerTransformer<>())
                    .subscribe(new DialogObserver<Integer>(mContext, true, "正在获取数据") {

                        @Override
                        public void onSuccess(Integer show) {
//                            ToastUtil.show(mContext, "保存成功");
                            holdBean.setId(show);
                            if (modify.equals("confirm")){
                                reviseState();
                            }
                        }

                        @Override
                        public void onFailure(String err) {
                            ToastUtil.show(mContext, err);
                        }
                    });
        }
        if (type.equals("jsa")){
            RetrofitUtil.getApi().addJsa(jsaBean)
                    .compose(new SchedulerTransformer<>())
                    .subscribe(new DialogObserver<Integer>(mContext, true, "正在获取数据") {

                        @Override
                        public void onSuccess(Integer show) {
                            jsaBean.setId(show);
//                            ToastUtil.show(mContext, "保存成功");
                        }

                        @Override
                        public void onFailure(String err) {
                            ToastUtil.show(mContext, err);
                        }
                    });
        }
    }





    //jsa基本信息接口
    private void getJSAView() {

        //基本信息
        RetrofitUtil.getApi().selectJsa(planWork)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<JSANewsBean>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(JSANewsBean bean) {
                        if (bean != null) {
                            JSANewsBean = bean;
                            setJSAScoutingValue(bean);
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                            jsaLayout.setVisibility(View.GONE);
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

        if (bean.getId() == null){
            getHold("jsa","");
        }else {
            jsaBean.setId(bean.getId());
        }

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

        jsaText1.setText(bean.getSheetName());

        if (bean.getDeptName() == null || bean.getDeptName().isEmpty()){
            jsaText2.setText("");
        }else {
            jsaText2.setText(bean.getDeptName());
        }
        jsaText3.setText(DeviceUnit);
        jsaText4.setText(planTime);
        jsaText5.setText(bean.getSheetNo());
        jsaText6.setText(tagNumber);


        //内容重写
        //介质
        if (bean.getWcMedium() == null || bean.getWcMedium().isEmpty()){
            jsaText7.setText("");
        }else {
            jsaText7.setText(bean.getWcMedium());
        }
        //温度
        if (bean.getWcTemperature() == null || bean.getWcTemperature().isEmpty()){
            jsaText8.setText("");
        }else {
            jsaText8.setText(bean.getWcTemperature());
        }
        //压力
        if (bean.getWcPressure() == null || bean.getWcPressure().isEmpty()){
            jsaText9.setText("");
        }else {
            jsaText9.setText(bean.getWcPressure());
        }
        //项目负责人
        if (bean.getProjectLeaderName() == null || bean.getProjectLeaderName().isEmpty() || bean.getProjectLeaderName().equals("")){
            jsaText10.setText("刷卡签字");
        }else {
            jsaText10.setText(bean.getProjectLeaderName());
        }
        //jsa成员
        if (bean.getJsaMenmber() == null || bean.getJsaMenmber().isEmpty()){
            jsaText12.setText("");
        }else {
            jsaText12.setText(bean.getJsaMenmber());
        }
        //其他
        if (bean.getWcOther() == null || bean.getWcOther().isEmpty()){
            jsaElseText.setText("");
        }else {
            jsaElseText.setText(bean.getWcOther());
        }
    }

    //dialog选择
    private void setValueDialog(String type) {
        new WorkConditionDialog(mContext, type, (value, type1, position, dialog) -> {
            if (type1 != null) {
                if (type1.equals("1")) {
                    jsaText7.setText(value);
                    dialog.dismiss();
                }
                if (type1.equals("2")) {
                    jsaText8.setText(value);
                    dialog.dismiss();
                }
                if (type1.equals("3")) {
                    jsaText9.setText(value);
                    dialog.dismiss();
                }
            }
        }).show();
    }


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
                        DestroyActivityUtil.destoryActivity("WorkRegionActivity");
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

}
