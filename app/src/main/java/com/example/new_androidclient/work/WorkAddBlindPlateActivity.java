package com.example.new_androidclient.work;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.WorkApplicationLineLayout;
import com.example.new_androidclient.databinding.ActivityWorkAddBlindPlateBinding;
import com.example.new_androidclient.work.bean.WorkBlindPlateBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加抽堵盲板
 */
@Route(path = RouteString.WorkAddBlindPlateActivity)
public class WorkAddBlindPlateActivity extends BaseActivity {
    private ActivityWorkAddBlindPlateBinding binding;
    private Listener listener = new Listener();
    private List<WorkApplicationLineLayout> itemList = new ArrayList<>();

    private WorkBlindPlateBean bean = new WorkBlindPlateBean();

    @Autowired
    int applicationId;


    private String[] NameList = {"管道/设备名称",
            "盲板材质", "隔离点符号", "盲板规格", "介质", "温度", "压力", "隔离方法"};
    private int[] NameTypeList = {3, 3, 3, 3, 3, 3, 3, 2};

    private String[] MethodList = {"加堵盲版，上锁挂牌", "上锁挂牌", "抽除盲板，解锁撤牌", "解锁撤牌"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_add_blind_plate);
        binding.setListener(listener);
        for (int i = 0; i < NameList.length; i++) {
            addView(i);
        }
    }

    private void addView(int pos) {
        WorkApplicationLineLayout layout = new WorkApplicationLineLayout(mContext, pos);
        layout.init(NameTypeList[pos]);
        layout.setNameText(NameList[pos]);

        if (NameTypeList[pos] == 2) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < MethodList.length; i++) {
                        list.add(MethodList[i]);
                    }
                    new WorkConditionDialog(mContext, "0", list, (value, type1, position, dialog) -> {
                        itemList.get(7).setNameText_text(value);
                        bean.setIsolateMethod(value);
                        dialog.dismiss();
                    }).show();
                }
            });
        }


        itemList.add(layout);
        binding.linearlayout.addView(layout);
    }

    private boolean allFinish() {
        for (int i = 0; i < itemList.size(); i++) {
            if (i < itemList.size() - 1) {
                if (itemList.get(i).getEditText_text().isEmpty()) {
                    ToastUtil.show(mContext, "请填写完整");
                    return false;
                }
            } else {
                if (itemList.get(i).getNameText_text().isEmpty()) {
                    ToastUtil.show(mContext, "请填写完整");
                    return false;
                }
            }
        }
        return true;
    }

    private void setData() {
        bean.setApplicationId(applicationId);
        bean.setWorkingType("DH");
        for (int i = 0; i < itemList.size(); i++) {
            if (i == 0) {
                bean.setDeviceName(itemList.get(i).getEditText_text());
            } else if (i == 1) {
                bean.setMaterial(itemList.get(i).getEditText_text());
            } else if (i == 2) {
                bean.setIsolatePoint(itemList.get(i).getEditText_text());
            } else if (i == 3) {
                bean.setSpec(itemList.get(i).getEditText_text());
            } else if (i == 4) {
                bean.setMedium(itemList.get(i).getEditText_text());
            } else if (i == 5) {
                bean.setTemperature(itemList.get(i).getEditText_text());
            } else if (i == 6) {
                bean.setPressure(itemList.get(i).getEditText_text());
            } else if (i == 7) {
                bean.setIsolateMethod(itemList.get(i).getNameText_text());
            }
        }
    }

    private void submit() {
        RetrofitUtil.getApi().preservationBlindPlate(bean)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String bean) {
                        finish();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    public class Listener {
        public void add() {
            if (allFinish()) {
                setData();
                submit();
            }
        }
    }
}
