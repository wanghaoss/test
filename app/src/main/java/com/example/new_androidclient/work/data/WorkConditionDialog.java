package com.example.new_androidclient.work.data;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.new_androidclient.R;
import com.example.new_androidclient.device_management.Data.FixItemListView;
import com.example.new_androidclient.work.Adapter.WorkConditionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkConditionDialog extends Dialog {

    @BindView(R.id.switchList)
    FixItemListView switchList;

    WorkConditionAdapter adapter;
    private Context mContext;
    private List list;
    private OnCloseListener listener;
    private WorkConditionDialog dialog = WorkConditionDialog.this;
    private String type;
    String nameValue;

    public WorkConditionDialog(@NonNull Context context, String type, OnCloseListener listener) {
        super(context);
        this.mContext = context;
        this.listener = listener;
        this.type = type;
    }

    public WorkConditionDialog(@NonNull Context context, String type, List<String> list, OnCloseListener listener) {
        super(context);
        this.mContext = context;
        this.listener = listener;
        this.type = type;
        this.list = list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_dialog);
        ButterKnife.bind(this);
        if (!type.equals("0")) {  //如果传入type=0，可从构造方法里传入list
            list = new ArrayList();
        }

        switch (type) {
            case "1":
                list.add("有毒有害");
                list.add("易燃易爆");
                list.add("惰性气体");
                break;
            case "2":
                list.add("高温");
                list.add("低温");
                break;
            case "3":
                list.add("低压");
                list.add("中压");
                list.add("高压");
                break;
            case "4":
                list.add("是");
                list.add("否");
                break;
            case "5":
                list.add("特级");
                list.add("一级");
                list.add("二级");
                break;
            case "6":
                list.add("NFC识别");
                list.add("扫描二维码识别");
                break;
            case "7":
                list.add("合格");
                list.add("不合格");
                break;
            case "8":
                list.add("生产异常");
                list.add("设备异常");
                break;
            case "9":
                list.add("无法处置");
                list.add("可以处置");
                break;
            case "10":
                list.add("2m ≤ h ≤ 5m");
                list.add("5m ＜ h ≤ 15m");
                list.add("l5m ＜ h ≤ 30m");
                list.add("h ＞ 30m");
                break;
            default:
                break;
        }
        initView();
        setView();
    }

    private void setView() {
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = display.getWidth();//设置Dialog的宽度为屏幕宽度
        // dialog.setCanceledOnTouchOutside(false);

        if (list != null) {
            adapter = new WorkConditionAdapter(mContext, list, type);
            switchList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        setListViewHegihtBasedOnChildren(switchList);
        switchList.setFixItemCount(5);
    }

    private void initView() {

        switchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                nameValue = list.get(position).toString();
                listener.onBottomClick(nameValue, type, position, dialog);
                dismiss();
            }
        });


    }

    public interface OnCloseListener {
        void onBottomClick(String value, String type, int position, Dialog dialog);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //listView自适应高度
    public void setListViewHegihtBasedOnChildren(ListView listview) {
        WorkConditionAdapter listAdapter = (WorkConditionAdapter) listview.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listview);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listview.getLayoutParams();

        params.height = totalHeight + (listview.getDividerHeight() * (listAdapter.getCount() - 1));

        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除

        listview.setLayoutParams(params);
    }

}

