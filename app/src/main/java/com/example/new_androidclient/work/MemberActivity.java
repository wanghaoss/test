package com.example.new_androidclient.work;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.NoDataLayout;
import com.example.new_androidclient.device_management.bean.DeviceAnalysisDeviceListBean;
import com.example.new_androidclient.work.Adapter.MemberAdapter;
import com.example.new_androidclient.work.bean.NameBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * 成员选择
 * */
public class MemberActivity extends BaseActivity {

    @BindView(R.id.define)
    Button define;
    @BindView(R.id.buttonLayout)
    RelativeLayout buttonLayout;
    @BindView(R.id.nodata)
    NoDataLayout nodata;
    @BindView(R.id.listview)
    ListView listview;

    String name;
    int nameId;
    MemberAdapter adapter;
    List<DeviceAnalysisDeviceListBean> mNameList = new ArrayList<>();

    List<NameBean> nameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        ButterKnife.bind(this);

        getView();
        initView();
        setValue();
    }

    private void setValue() {
        define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ReconnaissanceActivity.class);
                intent.putExtra("name", (Serializable) nameList);
                setResult(1, intent);

                finish();
            }
        });
    }

    private void getView() {
        RetrofitUtil.getApi().getUserList()
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<DeviceAnalysisDeviceListBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<DeviceAnalysisDeviceListBean> bean) {
                        if (bean.size() > 0) {

                            mNameList.addAll(bean);
                            adapter = new MemberAdapter(mContext,mNameList);
                            listview.setAdapter(adapter);
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void initView() {

        nameList.clear();

        listview.setOnItemClickListener((adapterView, view, position, l) -> {

            SparseBooleanArray checkedItemPositions = listview.getCheckedItemPositions();
            boolean isChecked = checkedItemPositions.get(position);

            if (isChecked == true){
                nameId = mNameList.get(position).getId();
                String name1 = mNameList.get(position).getLastName();
                String name2 = mNameList.get(position).getFirstName();
                name = name1+name2;

                NameBean newBean = new NameBean();
                newBean.setName(name);
                newBean.setNameId(nameId);
                nameList.add(newBean);
            }else {
//
                for (int i = 0; i < nameList.size(); i++) {
                    if (nameList.get(i).getNameId() == mNameList.get(position).getId()){

                        nameList.remove(i);
//                        break;
                    }
                }
            }
        });

    }
}
