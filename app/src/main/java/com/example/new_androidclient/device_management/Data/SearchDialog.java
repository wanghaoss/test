package com.example.new_androidclient.device_management.Data;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import com.example.new_androidclient.EventBusMessage.EventBusMessage;
import com.example.new_androidclient.R;
import com.example.new_androidclient.device_management.Adapter.SearchDialogAdapter;
import com.example.new_androidclient.device_management.bean.SearchBean;
import com.example.new_androidclient.device_management.srarch.SearchActivity;
import com.google.gson.internal.$Gson$Types;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;

public class SearchDialog extends Dialog{

        @BindView(R.id.switchList)
        FixItemListView switchList;

        SearchDialogAdapter adapter;
        private Context mContext;
        private List<SearchBean> list;
        private OnCloseListener listener;
        private SearchDialog dialog = SearchDialog.this;
        private String type;
        String nameValue;

        public SearchDialog(@NonNull Context context, String type, List<SearchBean> list, OnCloseListener listener) {
            super(context);
            this.mContext = context;
            this.listener = listener;
            this.list = list;
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
            WindowManager windowManager=getWindow().getWindowManager();
            Display display= windowManager.getDefaultDisplay();
            WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
            layoutParams.width=display.getWidth();//设置Dialog的宽度为屏幕宽度

            if (list != null){
                adapter = new SearchDialogAdapter(mContext, list,type);
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

                    nameValue = list.get(position).getName();
                    int nameId = list.get(position).getId();
                    listener.onBottomClick(nameId,nameValue,type,position,dialog);
                    dismiss();
                }
            });


        }

    public interface OnCloseListener {
        void onBottomClick(int nameId,String value, String type, int position,Dialog dialog);
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
            SearchDialogAdapter listAdapter = (SearchDialogAdapter) listview.getAdapter();

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

