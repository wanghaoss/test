package com.example.new_androidclient.work;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.work.bean.AllowCloseBean;
import com.example.new_androidclient.work.bean.PermitCancelBean;
import com.example.new_androidclient.work.data.WorkSafeAppointmentLineLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * 作业取消
 * */
public class PermitCancelActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.nextDevice)
    Button nextDevice;

    List<WorkSafeAppointmentLineLayout> itemList = new ArrayList<>();
    List<PermitCancelBean> list = new ArrayList<>();
    List<String> listValue = new ArrayList<>();
    int applicationId;
    String condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit_cancel);
        ButterKnife.bind(this);

        getValue();
        applicationId = getIntent().getIntExtra("applicationId",applicationId);
        nextDevice.setOnClickListener(this);
    }

    private void getValue() {
            RetrofitUtil.getApi().getWorkingPermitCancel()
                    .compose(new SchedulerTransformer<>())
                    .subscribe(new DialogObserver<List<PermitCancelBean>>(mContext, true, "正在获取数据") {

                        @Override
                        public void onSuccess(List<PermitCancelBean> bean) {
                            if (bean.size() > 0) {
                                list.addAll(bean);
                                for (int i = 0; i < bean.size(); i++) {
                                    addView(i);
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
        WorkSafeAppointmentLineLayout layout = new WorkSafeAppointmentLineLayout(mContext, pos);
        layout.init(0);
        layout.getCheckBox().setText(list.get(pos).getItemname());
        layout.getTextView().setVisibility(View.GONE);

        layout.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    listValue.add(list.get(pos).getItemname());
                }else {
                    listValue.remove(list.get(pos));
                }
            }
        });

        linearLayout.addView(layout);
        itemList.add(layout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nextDevice:
                showDialog();
                break;
        }
    }

    //是否取消作业
    private void showDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_account_security_unbind, null);
        TextView cancel =view.findViewById(R.id.confirmTView);
        TextView sure =view.findViewById(R.id.closeTView);
        final EditText edittext =view.findViewById(R.id.contentEdit);
        TextView titleTView = view.findViewById(R.id.titleTView);
        final Dialog dialog= builder.create();

        titleTView.setText("是否确认取消");
        edittext.setVisibility(View.GONE);
        dialog.show();
        dialog.getWindow().setContentView(view);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showManageDialog();
                dialog.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void showManageDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_account_security_unbind, null);
        TextView cancel =view.findViewById(R.id.confirmTView);
        TextView sure =view.findViewById(R.id.closeTView);
        final EditText edittext =view.findViewById(R.id.contentEdit);
        TextView titleTView = view.findViewById(R.id.titleTView);
        final Dialog dialog= builder.create();

        titleTView.setText("是否现场处置");
        edittext.setVisibility(View.GONE);
        dialog.show();
        dialog.getWindow().setContentView(view);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                setHold();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void setHold() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < listValue.size(); i++) {
            buf.append(listValue.get(i)).append(",");
        }
        if (buf.length() > 0) {
            condition = buf.substring(0, buf.length() - 1);
        }

        RetrofitUtil.getApi().cancelReason(condition,applicationId,"DH",null)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(String show) {
                        ToastUtil.show(mContext, "提交成功，请签字");
                        ARouter.getInstance().build(RouteString.WorkAddSignActivity)
                                .withInt("applicationId", applicationId)
                                .withString("status", "11")  //workAddSignActivity需要, 判断几个人签字
                                .navigation();
                        finish();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }
}
