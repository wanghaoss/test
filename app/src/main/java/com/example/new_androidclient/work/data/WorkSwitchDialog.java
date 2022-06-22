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

public class WorkSwitchDialog extends Dialog {

    @BindView(R.id.switchList)
    FixItemListView switchList;

    WorkConditionAdapter adapter;
    private Context mContext;
    private List<String> list;
    private OnCloseListener listener;
    private WorkSwitchDialog dialog = WorkSwitchDialog.this;
    private String type;
    String nameValue;

    public WorkSwitchDialog(@NonNull Context context, String type,List<String> beanList, OnCloseListener listener) {
        super(context);
        this.mContext = context;
        this.listener = listener;
        this.list = beanList;
        this.type = type;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_dialog);
        ButterKnife.bind(this);

        initView();
        setView();
    }

    private void setView() {
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = display.getWidth();//设置Dialog的宽度为屏幕宽度

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

