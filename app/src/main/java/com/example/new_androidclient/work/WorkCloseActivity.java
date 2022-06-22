package com.example.new_androidclient.work;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.new_androidclient.customize_view.WorkApplicationTypeCheckBox;
import com.example.new_androidclient.work.Adapter.CheckBoxAdapter;
import com.example.new_androidclient.work.bean.AllowCloseBean;
import com.example.new_androidclient.work.bean.WorkDHAppointmentBean;
import com.example.new_androidclient.work.bean.WorkNeedDoBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;
import com.example.new_androidclient.work.data.WorkSafeAppointmentLineLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * 作业许可关闭
 * 1.21 zq 验收选项内容删除，功能核减；栏下会签人“作业监护人”删除
 */
@Route(path = RouteString.WorkCloseActivity)
public class WorkCloseActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.but1)
    RadioButton but1;
    @BindView(R.id.but2)
    RadioButton but2;
    @BindView(R.id.but33)
    RadioButton but3;
    @BindView(R.id.radioGroup_gender)
    RadioGroup radioGroupGender;
    @BindView(R.id.radioLayout)
    LinearLayout radioLayout;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.nextDevice)
    Button nextDevice;

    String value;
    List<String> listValue = new ArrayList<>();
    String condition;
    int applicationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_close);
        ButterKnife.bind(this);

        radioGroupGender.setOnCheckedChangeListener(this);
        applicationId = getIntent().getIntExtra("applicationId", applicationId);
        clickListener();
    }

    private void clickListener() {
        nextDevice.setOnClickListener(view -> {
            if (value == null) {
                ToastUtil.show(mContext, "请选择关闭条件");
            } else {
                if (value.equals("作业未完，许可证到期，同意关闭")) {
                    showDialog("09");
                } else if (value.equals("作业已完，同意关闭")) {
                    showDialog("08");
                } else {
                    showDialog("11");
                }
            }
        });
    }

    private void showDialog(String type) {
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

        cancel.setOnClickListener(view1 -> {
            setSubmit(type);
            dialog.dismiss();
        });
        sure.setOnClickListener(view12 -> dialog.dismiss());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //得到用户选中的 RadioButton 对象
        RadioButton radioButton_checked = (RadioButton) group.findViewById(checkedId);
        value = radioButton_checked.getText().toString();
    }


    //保存关闭条件
    private void setHold(String status) {
              RetrofitUtil.getApi().cancelReason(null, applicationId, "DH", null)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(String show) {
                        ToastUtil.show(mContext, "提交成功，请签字");
                        ARouter.getInstance().build(RouteString.WorkAddSignActivity)
                                .withInt("applicationId", applicationId)
                                .withString("status", status)  //workAddSignActivity需要, 判断几个人签字
                                .withString("type", "null")
                                .navigation();

                        finish();
//                        setState("41");
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }
//
//    private void setState(String state) {
////修改工单状态
//        RetrofitUtil.getApi().updateApplicationStatus(applicationId, state)
//                .compose(new SchedulerTransformer<>())
//                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {
//
//                    @Override
//                    public void onSuccess(String show) {
//                        finish();
//                        DestroyActivityUtil.destoryActivity("WorkControlActivity");
//                    }
//
//                    @Override
//                    public void onFailure(String err) {
//                        ToastUtil.show(mContext, err);
//                    }
//                });
//    }

    //监督提交
    private void setSubmit(String status) {
        RetrofitUtil.getApi().updateProcessInspectStatus(status, applicationId, "DH")
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(String show) {
                        ToastUtil.show(mContext, show);
                        setHold(status);
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

}
