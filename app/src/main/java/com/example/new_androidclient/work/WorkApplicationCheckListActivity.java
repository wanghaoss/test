package com.example.new_androidclient.work;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.CloseKeyboardUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.WorkApplicationCheckListItemLayout;
import com.example.new_androidclient.databinding.ActivityWorkApplicationCheckListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * zq
 * 动火作业申请时，复选列表页
 */

@Route(path = RouteString.WorkApplicationCheckListActivity)
public class WorkApplicationCheckListActivity extends BaseActivity {
    private ActivityWorkApplicationCheckListBinding binding;
    private List<WorkApplicationCheckListItemLayout> itemList = new ArrayList<>();
    //  private WorkApplicationCheckListAdapter adapter;

    @Autowired
    int resultCode;

    @Autowired
    List<String> list;  //从WorkDHApplicationActivity传过来的项

    @Autowired
    String checkedStr; //从本页面返回到从WorkDHApplicationActivity的字符串，用于本页面回显

    @Autowired
    boolean showNoChoiceBtn = false; //是否显示无以上选项按钮，高处申请需要true

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_application_check_list);
        // CheckedStr = checkedStr.split(",");
        binding.linear.setOnTouchListener((v, event) -> {
            binding.linear.setFocusable(true);
            binding.linear.setFocusableInTouchMode(true);
            binding.linear.requestFocus();
            return false;
        });
        if (null == checkedStr) {
            checkedStr = "";
        }
        for (int i = 0; i < list.size(); i++) {
            addView(i);
        }
        if (showNoChoiceBtn) {
            binding.noChoiceBtn.setVisibility(View.VISIBLE);
            binding.noChoiceBtn.setOnClickListener(v -> {
                String str = "无";
                Intent intent = new Intent();
                intent.putExtra("data", str);
                setResult(resultCode, intent);
                finish();
            });
        } else {
            binding.noChoiceBtn.setVisibility(View.GONE);
        }
        //  adapter = new WorkApplicationCheckListAdapter(list, checkedStr);
        //  binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        //  binding.recycler.setAdapter(adapter);
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleData();
            }
        });
    }

    private void addView(int pos) {
        WorkApplicationCheckListItemLayout layout = new WorkApplicationCheckListItemLayout(this, pos);
        layout.init();
        layout.getCheckBox().setText(list.get(pos));
        if (checkedStr.contains(list.get(pos))) {
            layout.getCheckBox().setChecked(true);
        }

        if (list.get(pos).contains("其他") || list.get(pos).contains("其它")) {
            String[] checked = checkedStr.split(",");
            for (int i = 0; i < checked.length; i++) {
                if (checked[i].contains(":")) {
                    String[] temp = checked[i].split(":");
                    layout.getEditText().setText(temp[1]);
                    break;
                }
            }

            layout.getEditText().setVisibility(View.VISIBLE);
            layout.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (layout.getEditText().getText().toString().length() > 0) {
                        layout.getCheckBox().setChecked(true);
                    }
                }
            });
        }
        binding.linear.addView(layout);
        itemList.add(layout);
    }

    private void handleData() {
        String str = "";
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getCheckBox().isChecked()) {  //如果勾选了
                if (itemList.get(i).getCheckBox().getText().toString().contains("其它") ||
                        itemList.get(i).getCheckBox().getText().toString().contains("其他")) {
                    if (itemList.get(i).getEditText().getText().toString().isEmpty()) {
                        ToastUtil.show(mContext, "请填写完整");
                        return;
                    } else {
                        str += (list.get(i) + ":" + itemList.get(i).getEditText().getText().toString() + ",");
                    }
                } else {
                    str += (list.get(i) + ",");
                }
            }
        }
        if (str.isEmpty()) {
            ToastUtil.show(mContext, "请至少选择一项");
            return;
        }

        CloseKeyboardUtil.hideKeyboard(this);
        Intent intent = new Intent();
        intent.putExtra("data", str);
        setResult(resultCode, intent);
        finish();
    }
}
