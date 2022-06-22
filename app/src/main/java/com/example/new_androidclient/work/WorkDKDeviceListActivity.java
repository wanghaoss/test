package com.example.new_androidclient.work;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityWorkDKDeviceListBinding;
import com.example.new_androidclient.device_management.bean.FindDeviceListBean;
import com.example.new_androidclient.work.bean.WorkApplicationBaseDetailBean;

import java.util.ArrayList;
import java.util.List;
/**
 * zq
 * 管线打开作业 申请 管线设备名称选择列表页面 因为回显问题 在申请时暂时先填写
**/
@Route(path = RouteString.WorkDKDeviceListActivity)
public class WorkDKDeviceListActivity extends BaseActivity {
    @Autowired
    String instName;

    @Autowired
    String selectStr;

    private ActivityWorkDKDeviceListBinding binding;

    private List<FindDeviceListBean> deviceList = new ArrayList<>();
    LinearLayoutManager layoutManager;
    SelectAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_d_k_device_list);
        layoutManager = new LinearLayoutManager(this);
        binding.recycler.setLayoutManager(layoutManager);
        mAdapter = new SelectAdapter(deviceList);
        binding.recycler.setAdapter(mAdapter);

        if (selectStr == null) {
            selectStr = "";
        }
        setItemDecoration();
        getListData();
    }

    private void getListData() {
        RetrofitUtil.getApi().getFindDeviceList(1, 1000, null, null, null, null, null, null,
                null, null, null, instName)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<FindDeviceListBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<FindDeviceListBean> bean) {
                        deviceList.clear();
                        deviceList.addAll(bean);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //设置分割线
    private void setItemDecoration() {
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.recycler.addItemDecoration(itemDecoration);
    }

    public class SelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<FindDeviceListBean> mList = new ArrayList<>();

        private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
        private boolean mIsSelectable = false;


        public SelectAdapter(List<FindDeviceListBean> list) {
            if (list == null) {
                throw new IllegalArgumentException("model Data must not be null");
            }
            mList = list;
        }

        //获得选中条目的结果
        public ArrayList<String> getSelectedItem() {
            ArrayList<String> selectList = new ArrayList<>();
            for (int i = 0; i < mList.size(); i++) {
                if (isItemChecked(i)) {
                    selectList.add(mList.get(i).getDeviceName() + " " + mList.get(i).getTagNo() + ";");
                }
            }
            return selectList;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.work_dk_application_device_list_item, viewGroup, false);
            return new ListItemViewHolder(itemView);
        }

        //设置给定位置条目的选择状态
        private void setItemChecked(int position, boolean isChecked) {
            mSelectedPositions.put(position, isChecked);
        }

        //根据位置判断条目是否选中
        private boolean isItemChecked(int position) {
            return mSelectedPositions.get(position);

        }

        //根据位置判断条目是否可选
        private boolean isSelectable() {
            return mIsSelectable;
        }

        //设置给定位置条目的可选与否的状态
        private void setSelectable(boolean selectable) {
            mIsSelectable = selectable;
        }

        //绑定界面，设置监听
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int i) {
            //设置条目状态
            ((ListItemViewHolder) holder).mainTitle.setText(mList.get(i).getDeviceName());
            if (selectStr.contains(mList.get(i).getTagNo())) {
                mSelectedPositions.put(i, true);
            }

            ((ListItemViewHolder) holder).checkBox.setChecked(isItemChecked(i));

            //checkBox的监听
            ((ListItemViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isItemChecked(i)) {
                        setItemChecked(i, false);
                    } else {
                        setItemChecked(i, true);
                    }
                    binding.title.setName("已选择" + getSelectedItem().size() + "项");
                }
            });

            //条目view的监听
            ((ListItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isItemChecked(i)) {
                        setItemChecked(i, false);
                    } else {
                        setItemChecked(i, true);
                    }
                    notifyItemChanged(i);
                    binding.title.setName("已选择" + getSelectedItem().size() + "项");
                }
            });


        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        public class ListItemViewHolder extends RecyclerView.ViewHolder {
            //ViewHolder
            CheckBox checkBox;
            TextView mainTitle;

            ListItemViewHolder(View view) {
                super(view);
                this.mainTitle = view.findViewById(R.id.work_dk_application_device_list_item_text);
                this.checkBox = view.findViewById(R.id.work_dk_application_device_list_item_checkbox);
            }
        }
    }

    public class Listener{
        public void next(){
            ArrayList<String> list = mAdapter.getSelectedItem();
            for (int i = 0; i < mAdapter.getSelectedItem().size(); i++) {
               LogUtil.i(list.get(i));
            }
        }
    }
}