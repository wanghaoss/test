package com.example.new_androidclient.work;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.work.Adapter.RiskIdentifyAdapter;
import com.example.new_androidclient.work.bean.TreeListBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * 添加作业风险辨识与安全措施第二层
 * */
public class RiskIdentifyActivity extends BaseActivity {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.nodata)
    NoDataLayout nodata;

    int planWork;
    List<Integer> nameIdList;
    List<TreeListBean.ChildrenBeanX> list;
    RiskIdentifyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_identify);
        ButterKnife.bind(this);

        getView();
    }

    private void getView() {
        list = (List<TreeListBean.ChildrenBeanX>) getIntent().getSerializableExtra("treeList");
        planWork = getIntent().getIntExtra("planWork",planWork);

        if (list.size() > 0){
            setView(list);
        }else {
            nodata.setVisibility(View.VISIBLE);
        }
    }

    private void setView(List<TreeListBean.ChildrenBeanX> bean) {
        adapter = new RiskIdentifyAdapter(mContext, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                List<TreeListBean.ChildrenBeanX.ChildrenBean> listTreeChildren = new ArrayList<>();
                listTreeChildren.addAll(list.get(i).getChildren());

                Intent intent = new Intent(mContext,WorkRiskThreeActivity.class);
                intent.putExtra("listTreeChildren",(Serializable) listTreeChildren);
                intent.putExtra("planWork",planWork);
                startActivityForResult(intent,1);
            }
        });
    }


}
