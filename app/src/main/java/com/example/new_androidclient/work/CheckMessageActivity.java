package com.example.new_androidclient.work;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
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
import com.example.new_androidclient.work.Adapter.CheckMessageAdapter;
import com.example.new_androidclient.work.bean.CheckMessageBean;
import com.example.new_androidclient.work.bean.CheckOnsiteBean;
import com.example.new_androidclient.work.data.WorkSwitchDialog;
import com.example.new_androidclient.work.data.WorkSwitchLineLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * 核查信息
 * */
public class CheckMessageActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.title)
    TitleLayout title;
    //    @BindView(R.id.listView)
//    ListView listView;
    @BindView(R.id.nodata)
    NoDataLayout nodata;

    CheckMessageAdapter adapter;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.nextDevice)
    Button nextDevice;
    @BindView(R.id.swh_status)
    Switch swhStatus;


    String type;
    String status;
    int applicationId;
    List<WorkSwitchLineLayout> itemList = new ArrayList<>();
    List<CheckMessageBean> beanList = new ArrayList<>();
    List<String> bean = new ArrayList<>();
    String price;
    String itemLable;
    ArrayList ss;
    List<String> defineList = new ArrayList<>();
    List<String> valueList = new ArrayList<>();
    int success1 = 0; //成功为1  不成功为0
    int success2 = 0; //成功为1  不成功为0
    boolean success;
    boolean isSuccess;
    int planWork;
    CheckOnsiteBean checkOnsiteBean;
    List<CheckOnsiteBean> checkOnsiteBeans = new ArrayList<>();
    Set<List<Integer>> set = new HashSet<List<Integer>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_message);
        ButterKnife.bind(this);

        getView();

        nextDevice.setOnClickListener(this);
        DestroyActivityUtil.addDestoryActivityToMap(CheckMessageActivity.this, "CheckMessageActivity");

    }

    private void getView() {
        applicationId = getIntent().getIntExtra("applicationId", applicationId);
        planWork = getIntent().getIntExtra("planWork", planWork);
        status = getIntent().getStringExtra("status");

        RetrofitUtil.getApi().selectWorkingCheckOnsite(applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<CheckMessageBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<CheckMessageBean> bean) {
                        if (bean.size() > 0) {
                            beanList.addAll(bean);

                            for (int i = 0; i < bean.size(); i++) {
                                addView(i, bean.get(i).getItemName(), bean.get(i).getItemLable());
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


    private void addView(int i, String bean, String type) {
        WorkSwitchLineLayout layout = new WorkSwitchLineLayout(mContext, i);
        String[] all = type.split(",");


        if (all.length > 2) {
            ss = new ArrayList();
            for (int j = 0; j < all.length; j++) {
                ss.add(all[j]);
            }

            layout.init(1);
            layout.setNameText_text("请选择");

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new WorkSwitchDialog(mContext, "1", ss, new WorkSwitchDialog.OnCloseListener() {
                        @Override
                        public void onBottomClick(String value, String type, int position, Dialog dialog) {
                            checkOnsiteBean = new CheckOnsiteBean();
                            itemList.get(i).setNameText_text(value);
                            if (position == 0 || position == 2) {
                                success = true;
                                checkOnsiteBean.setId(beanList.get(i).getId());
                                checkOnsiteBean.setApplicaitonId(applicationId);
                                checkOnsiteBean.setItemName(beanList.get(i).getItemName());
                                checkOnsiteBean.setItemId(beanList.get(i).getItemId());
                                checkOnsiteBean.setItemType(beanList.get(i).getItemType());
                                checkOnsiteBean.setConfirmer(beanList.get(i).getConfirmer());
                                checkOnsiteBean.setConfimTime(beanList.get(i).getConfimTime());
                                checkOnsiteBean.setItemLable(beanList.get(i).getItemLable());
                                checkOnsiteBean.setItemResult(value);

                                checkOnsiteBeans.add(checkOnsiteBean);

                                if (itemList.size() == checkOnsiteBeans.size() && !value.equals(all[1])) {
                                    swhStatus.setChecked(true);
                                }else {
                                    swhStatus.setChecked(false);
                                }
                            } else {
                                success = false;
                                checkOnsiteBean.setId(beanList.get(i).getId());
                                checkOnsiteBean.setApplicaitonId(applicationId);
                                checkOnsiteBean.setItemName(beanList.get(i).getItemName());
                                checkOnsiteBean.setItemId(beanList.get(i).getItemId());
                                checkOnsiteBean.setItemType(beanList.get(i).getItemType());
                                checkOnsiteBean.setConfirmer(beanList.get(i).getConfirmer());
                                checkOnsiteBean.setConfimTime(beanList.get(i).getConfimTime());
                                checkOnsiteBean.setItemLable(beanList.get(i).getItemLable());
                                checkOnsiteBean.setItemResult(value);

                                for (int j = 0; j < beanList.size(); j++) {
                                    if (bean.equals(beanList.get(i).getItemName())){

                                        checkOnsiteBeans.remove(j);
                                        break;
                                    }
                                }
                                swhStatus.setChecked(false);
                            }
                            itemLable = value;
                        }
                    }).show();
                }
            });


        } else {
            success1 = success1 + 1;

            layout.init(4);

            layout.getaSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    checkOnsiteBean = new CheckOnsiteBean();
                    if (b) {
                        isSuccess = true;
                        checkOnsiteBean.setId(beanList.get(i).getId());
                        checkOnsiteBean.setApplicaitonId(applicationId);
                        checkOnsiteBean.setItemName(beanList.get(i).getItemName());
                        checkOnsiteBean.setItemId(beanList.get(i).getItemId());
                        checkOnsiteBean.setItemType(beanList.get(i).getItemType());
                        checkOnsiteBean.setConfirmer(beanList.get(i).getConfirmer());
                        checkOnsiteBean.setConfimTime(beanList.get(i).getConfimTime());
                        checkOnsiteBean.setItemLable(beanList.get(i).getItemLable());
                        checkOnsiteBean.setItemResult(all[0]);


                        checkOnsiteBeans.add(checkOnsiteBean);
                        if (itemList.size() == checkOnsiteBeans.size() && b) {
                            swhStatus.setChecked(true);
                        }else {
                            swhStatus.setChecked(false);
                        }
                    } else {
                        isSuccess = false;
                        checkOnsiteBean.setId(beanList.get(i).getId());
                        checkOnsiteBean.setItemName(beanList.get(i).getItemName());
                        checkOnsiteBean.setApplicaitonId(applicationId);
                        checkOnsiteBean.setItemId(beanList.get(i).getItemId());
                        checkOnsiteBean.setItemType(beanList.get(i).getItemType());
                        checkOnsiteBean.setConfirmer(beanList.get(i).getConfirmer());
                        checkOnsiteBean.setConfimTime(beanList.get(i).getConfimTime());
                        checkOnsiteBean.setItemLable(beanList.get(i).getItemLable());
                        if (all.length >= 2){

                            checkOnsiteBean.setItemResult(all[1]);
                        }

                        for (int j = 0; j < beanList.size(); j++) {
                            if (bean.equals(beanList.get(i).getItemName())){

                                checkOnsiteBeans.remove(j);
                                break;
                            }
                        }

                        swhStatus.setChecked(false);
                    }
                }
            });
        }

            layout.setNameText(bean);


            if (bean.equals("安全作业方案措施落实")){
                layout.init(1);
                layout.setNameText_text("查看");

                layout.getaSwitch().setVisibility(View.GONE);

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,WorkSafeMeasuresActivity.class);
                        intent.putExtra("applicationId",applicationId);
                        startActivityForResult(intent,22);
                    }
                });
            }


        if (success1 > 0) {
            success = true;
        }



        itemList.add(layout);
        linearLayout.addView(layout);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextDevice:

                String value = "";
                for (int i = 0; i < itemList.size(); i++) {
                    itemList.get(i).init(1);
                    value = itemList.get(i).getNameText_text();
                    if (value.equals("已查看")){
                        break;
                    }
                }
                if (value.equals("已查看")){
                    if (itemList.size() == checkOnsiteBeans.size() && success && isSuccess) {
                        setValue(11);
                    } else {
                        showDialog();
                    }
                    break;
                }else {
                    ToastUtil.show(mContext,"请先查看安全措施");
                }
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_account_security_unbind, null);
        TextView cancel =view.findViewById(R.id.confirmTView);
        TextView sure =view.findViewById(R.id.closeTView);
        final EditText edittext =view.findViewById(R.id.contentEdit);
        TextView titleTView = view.findViewById(R.id.titleTView);
        final Dialog dialog= builder.create();

        titleTView.setText("是否确认关闭");
        edittext.setVisibility(View.GONE);
        dialog.show();
        dialog.getWindow().setContentView(view);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                setValue(22);
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void setValue(int type) {
        RetrofitUtil.getApi().saveWorkingCheckOnsite(checkOnsiteBeans)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(String show) {
                        ToastUtil.show(mContext, show);
                        if (type == 11) {
                            Intent intent = new Intent(mContext, HotWorkActivity.class);
                            intent.putExtra("applicationId", applicationId);
                            intent.putExtra("status",status);
                            startActivity(intent);
                        } else if (type == 22) {
                            setState("32");
                            finish();
                            DestroyActivityUtil.destoryActivity("SceneCheckActivity");
                        }
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
        if (requestCode == 22){
            if (data != null){
                type = data.getStringExtra("type");

                if (type.equals("1")){
                    checkOnsiteBean = new CheckOnsiteBean();
                    for (int i = 0; i < beanList.size(); i++) {
                        if (beanList.get(i).getItemName().equals("安全作业方案措施落实")){

                            itemList.get(i).init(1);
                            itemList.get(i).setNameText_text("已查看");
                            checkOnsiteBean.setId(beanList.get(i).getId());
                            checkOnsiteBean.setItemName(beanList.get(i).getItemName());
                            checkOnsiteBean.setApplicaitonId(applicationId);
                            checkOnsiteBean.setItemId(beanList.get(i).getItemId());
                            checkOnsiteBean.setItemType(beanList.get(i).getItemType());
                            checkOnsiteBean.setConfirmer(beanList.get(i).getConfirmer());
                            checkOnsiteBean.setConfimTime(beanList.get(i).getConfimTime());
                            checkOnsiteBean.setItemLable(beanList.get(i).getItemLable());
                            String[] all = beanList.get(i).getItemLable().split(",");
                            if (all.length >= 2){
                                checkOnsiteBean.setItemResult(all[0]);
                            }

                            swhStatus.setChecked(true);
                            checkOnsiteBeans.add(checkOnsiteBean);
                            itemList.get(i).getaSwitch().setVisibility(View.GONE);

                            break;
                        }
                    }
                }else {
                    for (int i = 0; i < beanList.size(); i++) {
                        if (beanList.get(i).getItemName().equals("安全作业方案措施落实")) {

                            itemList.get(i).init(1);
                            itemList.get(i).setNameText_text("查看");
                        }
                    }
                    swhStatus.setChecked(false);
                }
            }else {
                ToastUtil.show(mContext,"请正确核查安全作业方案措施落实");
            }


        }
    }
}
