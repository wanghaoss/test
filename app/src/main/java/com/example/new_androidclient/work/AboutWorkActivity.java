package com.example.new_androidclient.work;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.NoDataLayout;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.work.Adapter.AboutWorkAdapter;
import com.example.new_androidclient.work.bean.TreeListBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * wh
 *添加作业风险第一层
 */
public class AboutWorkActivity extends BaseActivity {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.nodata)
    NoDataLayout nodata;
    @BindView(R.id.listLayout)
    RelativeLayout listLayout;


    int planWork;
    List<Integer> nameIdList;
    List<TreeListBean> list = new ArrayList();
    AboutWorkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_work);
        ButterKnife.bind(this);

        getView();
        setValue();
    }

    private void setValue() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, RiskIdentifyActivity.class);
                intent.putExtra("treeList", (Serializable) list.get(i).getChildren());
                intent.putExtra("planWork", planWork);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void getView() {
        planWork = getIntent().getIntExtra("planWork", planWork);
        nameIdList = (List<Integer>) getIntent().getSerializableExtra("nameIdList");

        RetrofitUtil.getApi().getWorkTreeList()
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<TreeListBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<TreeListBean> bean) {
                        setView(bean);
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void setView(List<TreeListBean> bean) {
        list.clear();
        if (nameIdList.size()>0 && !nameIdList.isEmpty()){
            for (int i = 0; i < nameIdList.size(); i++) {
                int ii = nameIdList.get(i);

                for (int j = 0; j < bean.size(); j++) {
                    if (ii == bean.get(j).getId()) {
                        list.add(bean.get(j));
                    }
                }
            }
        }
        if(list.size() == 0){
            nodata.setVisibility(View.VISIBLE);
            listLayout.setVisibility(View.GONE);
        }
        adapter = new AboutWorkAdapter(mContext, list);
        listView.setAdapter(adapter);

    }
}
