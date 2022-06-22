package com.example.new_androidclient.work;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.NoDataLayout;
import com.example.new_androidclient.work.Adapter.SpecialWorkAdapter;
import com.example.new_androidclient.work.bean.NameBean;
import com.example.new_androidclient.work.bean.SpecialWorkBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * 项目涉及的特殊作业
 * */
public class SpecialWorkActivity extends BaseActivity {

    @BindView(R.id.define)
    Button define;
    @BindView(R.id.buttonLayout)
    RelativeLayout buttonLayout;
    @BindView(R.id.nodata)
    NoDataLayout nodata;
    @BindView(R.id.listview)
    ListView listview;

    String specialWorkName;
    int specialWorkId;
    SpecialWorkAdapter adapter;
    List<SpecialWorkBean> specialWorkList = new ArrayList<>();

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
                Intent intent = new Intent(mContext, JSAActivity.class);
                intent.putExtra("name", (Serializable) nameList);
                setResult(1, intent);

                finish();
            }
        });
    }

    private void getView() {
        //项目涉及的特殊作业
        RetrofitUtil.getApi().selectSpecialWorking()
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<SpecialWorkBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<SpecialWorkBean> bean) {
                        if (bean != null) {
                            specialWorkList.addAll(bean);
                            adapter = new SpecialWorkAdapter(mContext,specialWorkList);
                            listview.setAdapter(adapter);
                        }else {
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
                specialWorkId = specialWorkList.get(position).getId();
                specialWorkName = specialWorkList.get(position).getName();

                adapter.setSelectPosition(position);
                NameBean newBean = new NameBean();
                newBean.setName(specialWorkName);
                newBean.setNameId(specialWorkId);
                nameList.add(newBean);

            }else {
                for (int i = 0; i < nameList.size(); i++) {
                    if (nameList.get(i).getNameId() == specialWorkList.get(position).getId()){
                        nameList.remove(i);
                        break;
                    }
                }
            }
        });

    }
}
