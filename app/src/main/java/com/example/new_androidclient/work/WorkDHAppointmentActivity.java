package com.example.new_androidclient.work;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.Other.SPString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.WorkApplicationTypeCheckBox;
import com.example.new_androidclient.customize_view.WorkDHAppointmentLineLayout;
import com.example.new_androidclient.databinding.ActivityWorkDHAppointmentBinding;
import com.example.new_androidclient.inspection.CustomCaptureActivity;
import com.example.new_androidclient.inspection.InspectionDeviceListActivity;
import com.example.new_androidclient.work.bean.WorkDHApplicationBean;
import com.example.new_androidclient.work.bean.WorkDHAppointmentBean;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * zq
 * 动火作业安全措施方案
 * 页面跳转顺序 WorkAppointmentActivity - WorkDHAppointmentDetailActivity - WorkDHAppointmentActivity
 */
@Route(path = RouteString.WorkDHAppointmentActivity)
public class WorkDHAppointmentActivity extends BaseActivity {
    private List<WorkDHAppointmentBean> detailList = new ArrayList<>();
    private List<WorkDHAppointmentLineLayout> itemList = new ArrayList<>();
    private ActivityWorkDHAppointmentBinding binding;
    private Listener listener = new Listener();
    private String loginName = "";

    private int toNext = 7;

    @Autowired
    int applicationId;

    @Autowired
    String workType; //作业类型

    @Autowired
    Boolean isSign = false;  //是不是审核

    @Autowired
    int status;//0申请 1预约


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_d_h_appointment);
        binding.setListener(listener);
        binding.linear.setOnTouchListener((v, event) -> {
            binding.linear.setFocusable(true);
            binding.linear.setFocusableInTouchMode(true);
            binding.linear.requestFocus();
            return false;
        });
        loginName = (String) SPUtil.getData(SPString.UserName, "");
        getDetail();
        if (isSign) {
            binding.nextDevice.setVisibility(View.GONE);
        }
    }

    private void getDetail() {
        RetrofitUtil.getApi().selectAppointment(workType, applicationId)  //1是动火
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<WorkDHAppointmentBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<WorkDHAppointmentBean> bean) {
                        binding.linear.removeAllViews();
                        itemList.clear();
                        detailList.clear();
                        if (bean.size() > 0) {
                            detailList = bean;
                            for (int i = 0; i < detailList.size(); i++) {
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
        WorkDHAppointmentBean bean = detailList.get(pos);
        WorkDHAppointmentLineLayout layout = new WorkDHAppointmentLineLayout(mContext, pos);
        int type = detailList.get(pos).getType();
        layout.init(type);
        if (null == bean.getIsRefer()) {
            bean.setIsRefer("0");
        }
        if (type == 0) {  //checkbox
            layout.getCheckBox().setText(bean.getItemName());
            if (bean.getConfirmer() != null) {
                layout.getCheckBox().setChecked(true);
                //  checkList.add(detailList.get(layout.getPos()));
                if (status == 1) {  //0申请 1预约
                    //   在预约时需要处理申请时选中的不能修改
                    layout.getCheckBox().setClickable(false);
                }
            }
            if (isSign) {
                layout.getCheckBox().setClickable(false);
            }
        } else if (type == 1) {  //edit
            if (bean.getConfirmer() != null) {
                layout.getCheckBox().setChecked(true);
                if (status == 1) {  //0申请 1预约
                    //   在预约时需要处理申请时选中的不能修改
                    layout.getCheckBox().setClickable(false);
                }
            }

            if (isSign) {
                layout.getCheckBox().setClickable(false);
                layout.getEditText().setClickable(false);
            }

            layout.setEditText_text(bean.getItemName());
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
                    itemList.get(layout.getPos()).getCheckBox().setChecked(true);
                }
            });
        } else {
            if (bean.getConfirmer() != null) {
                layout.getCheckBox().setChecked(true);
                if (status == 1) {  //0申请 1预约
                    //   在预约时需要处理申请时选中的不能修改
                    layout.getCheckBox().setClickable(false);
                }
            }
            if (isSign) {
                layout.getCheckBox().setClickable(false);
            }
            layout.getCheckBox().setText(bean.getItemName());
            Map<String, String> map = bean.getExtraData();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                //下面checkbox的pos用的是外面那层layout的pos，用于处理里面checkbox选择后自动选择外层checkbox
                WorkApplicationTypeCheckBox checkBox = new WorkApplicationTypeCheckBox(mContext, layout.getPos(), entry.getKey());
                checkBox.setText(entry.getKey());

                if ((status == 1 && null != bean.getConfirmer()) || isSign) {  //0申请 1预约
                    //   在预约时需要处理申请时选中的不能修改
                    checkBox.setClickable(false);
                }

                if (entry.getValue().equals("1")) {  //选中
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        itemList.get(checkBox.getPos()).getCheckBox().setChecked(true);
                        map.put(checkBox.getCode(), "1");
                    } else {
                        map.put(checkBox.getCode(), "0");
                    }
                });
                layout.getLinearLayout().addView(checkBox);
                layout.addCheckBox(checkBox);
            }
        }

        layout.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                detailList.get(layout.getPos()).setIsRefer("1");
                //  checkList.add(detailList.get(layout.getPos()));
                if (loginName.isEmpty()) {
                    ToastUtil.show(mContext, "请重新登录");
                    return;
                }
                detailList.get(layout.getPos()).setConfirmer(loginName);
            } else {
                if (layout.getType() == 2) {
                    for (int i = 0; i < layout.getCheckBoxList().size(); i++) {
                        layout.getCheckBoxList().get(i).setChecked(false);
                    }
                }
                detailList.get(layout.getPos()).setIsRefer("0");
                detailList.get(layout.getPos()).setConfirmer(null);
            }
        });

        binding.linear.addView(layout);
        itemList.add(layout);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == QR) {
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);   //刷卡人
//                    for (int i = 0; i < itemList.size(); i++) {
//                        if (itemList.get(i).getCheckBox().isChecked()) {
//                            detailList.get(i).setConfirmer(result);
//                        }
//                    }
//                }
//                submitData();
//            }
//        }
//    }

    private void submitData() {
        RetrofitUtil.getApi().updAppointment(detailList, applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(String bean) {
                        //更新成功
                        ToastUtil.show(mContext, "提交成功");
                        setResult(toNext);
                        finish();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    public class Listener {
        public void submit() {
            int temp = 0;
            boolean allFinish = true;
            for (int i = 0; i < itemList.size(); i++) {
                if (!itemList.get(i).getCheckBox().isChecked()) {
                    temp++;
                }

            }
            if (temp == itemList.size()) {
                ToastUtil.show(mContext, "至少选择一项");
                return;
            }

            for (int i = 0; i < detailList.size(); i++) {
                if (itemList.get(i).getCheckBox().isChecked() && detailList.get(i).getType() == 2) {
                    Map<String, String> map = detailList.get(i).getExtraData();
                    int num = 0; //用于记录子checkbox里面没打勾的项，与子checkbox数量做对比
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        if (entry.getValue().equals("0")) {
                            num += 1;
                        }
                    }
                    if (map.size() == num) {
                        allFinish = false;
                        ToastUtil.show(mContext, "请选择完整");
                        return;
                    }
                }
            }
            submitData();
//            Intent intent = new Intent(WorkDHAppointmentActivity.this, CustomCaptureActivity.class);
//            startActivityForResult(intent, QR);
        }
    }
}
