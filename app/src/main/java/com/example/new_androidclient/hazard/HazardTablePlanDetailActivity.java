package com.example.new_androidclient.hazard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityHazardTablePlanBinding;
import com.example.new_androidclient.hazard.adapter.HazardTablePlanDetailAdapter;
import com.example.new_androidclient.hazard.bean.HazardTablePlanDetailBean;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouteString.HazardTablePlanDetailActivity)
public class HazardTablePlanDetailActivity extends BaseActivity {
    private ActivityHazardTablePlanBinding binding;
    private HazardTablePlanDetailAdapter adapter;
    private List<HazardTablePlanDetailBean> list = new ArrayList<>();
    private int flag = -1;
    private AlertDialog dialog;
    private Listener listener = new Listener();

    @Autowired()
    int planId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hazard_table_plan);
        binding.setListener(listener);
        adapter = new HazardTablePlanDetailAdapter(list, BR.hazard_table_plan_detail, new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);
        getList();
    }

    private void getList() {
        RetrofitUtil.getApi().selectInspectPlanInfo(planId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardTablePlanDetailBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<HazardTablePlanDetailBean> bean) {
                        list.clear();
                        if(bean.size() > 0){
                            for (int i = 0; i < bean.size(); i++) {
                                bean.get(i).setIndex(i+1);
                            }
                            list.addAll(bean);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void submit(String msg) {
        RetrofitUtil.getApi().processing(planId, flag, msg)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "数据提交中") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("提交成功") || bean.equals("审批成功") || bean.equals("审核成功")) {
                            ToastUtil.show(mContext, bean);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void showDialog() {
        dialog = new AlertDialog.Builder(this).setPositiveButton("提交", null).setNegativeButton("取消", null).create();
        dialog.setTitle("请填写意见");

        Window win = dialog.getWindow();
        win.getDecorView().setPadding(10, 20, 10, 20);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

        View view = getLayoutInflater().inflate(R.layout.hazard_plan_sign_dialog, null);
        final EditText msg_edit = view.findViewById(R.id.hazard_plan_sign_dialog_edittext);
        dialog.setView(view);//设置login_layout为对话提示框
        dialog.setCancelable(false);//设置为不可取消
        dialog.setOnShowListener(mDialog -> {
            Button positionButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            positionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (msg_edit.getText().toString().trim().isEmpty() && flag == 0) {
                        ToastUtil.show(mContext, "请填写意见");
                    } else {
                        submit(msg_edit.getText().toString().trim());
                        //   ToastUtil.show(mContext, msg_edit.getText().toString().trim());
                        dialog.dismiss();
                    }
                }

            });
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        });
        dialog.show();
    }

    public class Listener {
        public void agree() {
            flag = 1;
            showDialog();
        }

        public void unAgree() {
            flag = 0;
            showDialog();
        }
    }
}
