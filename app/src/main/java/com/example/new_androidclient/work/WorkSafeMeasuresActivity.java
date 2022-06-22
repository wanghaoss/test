package com.example.new_androidclient.work;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.EventBusMessage.EventBusMessage;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.DestroyActivityUtil;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.WorkApplicationTypeCheckBox;
import com.example.new_androidclient.customize_view.WorkDHAppointmentLineLayout;
import com.example.new_androidclient.databinding.ActivityWorkDHAppointmentBinding;
import com.example.new_androidclient.databinding.ActivityWorkSafeAppointmentBinding;
import com.example.new_androidclient.inspection.CustomCaptureActivity;
import com.example.new_androidclient.work.bean.WorkDHAppointmentBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;
import com.example.new_androidclient.work.data.WorkSafeAppointmentLineLayout;
import com.example.new_androidclient.work.data.WorkSwitchDialog;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * wh
 * 现场核查查看作业风险安全措施
 */
public class WorkSafeMeasuresActivity extends BaseActivity {
    private List<WorkDHAppointmentBean> detailList = new ArrayList<>();
    private List<WorkSafeAppointmentLineLayout> itemList = new ArrayList<>();
    private List<String> valueList = new ArrayList<>();
    private List<WorkDHAppointmentBean> checkList = new ArrayList<>();  //选中的list
    private WorkDHAppointmentBean bean;
    private ActivityWorkSafeAppointmentBinding binding;

    private int QR = 1;

    int applicationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_safe_appointment);

        getDetail();
        binding.nextDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judgeView();
            }
        });
    }

    private void judgeView() {
        for (int i = 0; i < itemList.size(); i++) {
                valueList.add(itemList.get(i).getTextView().getText().toString());
        }
        Set set = new HashSet(valueList);
        if (set.size() == 1){
            submit();
        }else {
            showDialog();
        }
//        if (!itemList.get(i).getTextView().getText().equals("已刷卡")){
//            showDialog();
//            break;
//        } else {
//            submit();
//        }
    }

    private void getDetail() {
        checkList.clear();
        applicationId = getIntent().getIntExtra("applicationId", applicationId);

        RetrofitUtil.getApi().selectAppointment("DH", applicationId)  //wh1是固定类型  zq原来是1，改成DH, 因为所有作业都要用本接口
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<WorkDHAppointmentBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<WorkDHAppointmentBean> bean) {
                        binding.linear.removeAllViews();
                        itemList.clear();
                        detailList.clear();
                        if (bean.size() > 0) {
                            for (int i = 0; i < bean.size(); i++) {
                                detailList.add(bean.get(i));
                                if(detailList.get(i).getConfirmer() != null){
                                    addView(i);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void addView(int pos) {
        checkList.clear();
        WorkDHAppointmentBean bean = detailList.get(pos);
        bean.setIsRefer("1");
        WorkSafeAppointmentLineLayout layout = new WorkSafeAppointmentLineLayout(mContext, pos);
        int type = detailList.get(pos).getType();
        layout.init(type);
        if (type == 0) {
            layout.getCheckBox().setText(bean.getItemName());
        } else if (type == 1) {
            layout.getEditText().setText(bean.getItemName());
            layout.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    detailList.get(layout.getPos()).setItemName(s + "");
                }
            });
        } else {
            layout.getCheckBox().setText(bean.getItemName());
            Map<String, String> map = bean.getExtraData();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                WorkApplicationTypeCheckBox checkBox = new WorkApplicationTypeCheckBox(mContext, 0, entry.getKey());
                checkBox.setText(entry.getKey());
                if (entry.getValue().equals("1")) {  //选中
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            map.put(checkBox.getCode(), "1");
                        } else {
                            map.put(checkBox.getCode(), "0");
                        }
                    }
                });
                layout.getLinearLayout().addView(checkBox);

                if (detailList.get(pos).getConfirmer() != null && entry.getValue().equals("1")) {
                    layout.getCheckBox().setChecked(true);
                    layout.getCheckBox().setClickable(false);



                    layout.getTextView().setVisibility(View.VISIBLE);
                } else {
                    layout.getTextView().setVisibility(View.GONE);
                    layout.getCheckBox().setChecked(false);
                    layout.getCheckBox().setClickable(true);
                }
            }
        }

        layout.getTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new WorkConditionDialog(mContext, "4", new WorkConditionDialog.OnCloseListener() {
                    @Override
                    public void onBottomClick(String value, String type, int position, Dialog dialog) {
                        layout.getTextView().setText(value);
                        if (value.equals("是")) {

                            //刷卡
                            //更换刷卡
                            ARouter.getInstance().build(RouteString.NFCActivity)
                                    .withInt("code", 6060)
                                    .withInt("module", 1) //1人员
                                    .navigation(WorkSafeMeasuresActivity.this, 6060);

//                            Intent intent1 = new Intent(mContext, CustomCaptureActivity.class);
//                            startActivityForResult(intent1, 111);

                            bean.setCheckResult("1");
                        } else {
                            bean.setCheckResult("0");
                        }
                        checkList.add(bean);
                    }
                }).show();

            }
        });

        binding.linear.addView(layout);
        itemList.add(layout);


        if (detailList.get(pos).getConfirmer() != null) {
            layout.getCheckBox().setChecked(true);
            layout.getCheckBox().setClickable(false);
            layout.getTextView().setVisibility(View.VISIBLE);
        } else {
            layout.getTextView().setVisibility(View.GONE);
            layout.getCheckBox().setChecked(false);
            layout.getCheckBox().setClickable(true);
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

        if (requestCode == 6060) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                    String result = data.getStringExtra("name");   //刷卡人
                    for (int i = 0; i < checkList.size(); i++) {
                        checkList.get(i).setCheckConfirmer(result);
                        itemList.get(i).getTextView().setText("已刷卡");
                    }
            }
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_account_security_unbind, null);
        TextView cancel = view.findViewById(R.id.confirmTView);
        TextView sure = view.findViewById(R.id.closeTView);
        final EditText edittext = view.findViewById(R.id.contentEdit);
        TextView titleTView = view.findViewById(R.id.titleTView);
        final Dialog dialog = builder.create();

        titleTView.setText("是否确认关闭");
        edittext.setVisibility(View.GONE);
        dialog.show();
        dialog.getWindow().setContentView(view);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                setState("32");
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
                        DestroyActivityUtil.destoryActivity("CheckMessageActivity");
                        DestroyActivityUtil.destoryActivity("SceneCheckActivity");
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void submit() {
        RetrofitUtil.getApi().updAppointment(checkList, applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(String bean) {
                        //更新成功
                        if (bean.equals("更新成功")) {
                            ToastUtil.show(mContext, "提交成功");


                            Intent intent = new Intent(mContext, CheckMessageActivity.class);
                            intent.putExtra("type", "1");
                            setResult(22, intent);

                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }
}
