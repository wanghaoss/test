package com.example.new_androidclient.device_management.Data;

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
import com.example.new_androidclient.device_management.Adapter.CategoryDialogAdapter;
import com.example.new_androidclient.device_management.Adapter.SearchDialogAdapter;
import com.example.new_androidclient.device_management.bean.CategoryBean;
import com.example.new_androidclient.device_management.bean.SearchBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryDialog extends Dialog{

        @BindView(R.id.switchList)
        FixItemListView switchList;

        CategoryDialogAdapter adapter;
        private Context mContext;
        private List<CategoryBean> list;
        private OnCloseListener listener;
        private String type;
        private CategoryDialog dialog = CategoryDialog.this;
        String nameValue;

        public CategoryDialog(@NonNull Context context, List<CategoryBean> list, String type,OnCloseListener listener) {
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
            layoutParams.width=display.getWidth();//??????Dialog????????????????????????

            if (list != null){
                adapter = new CategoryDialogAdapter(mContext, list);
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
                    listener.onBottomClick(nameValue,dialog);
                    dismiss();
                }
            });


        }

    public interface OnCloseListener {
        void onBottomClick(String value,Dialog dialog);
    }

        @Override
        public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {

                return true;
            }
            return super.onKeyDown(keyCode, event);
        }

        //listView???????????????
        public void setListViewHegihtBasedOnChildren(ListView listview) {
            CategoryDialogAdapter listAdapter = (CategoryDialogAdapter) listview.getAdapter();

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

            ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10); // ?????????

            listview.setLayoutParams(params);
        }

    }

