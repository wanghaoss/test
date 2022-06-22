package com.example.new_androidclient.work;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.NoDataLayout;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.work.Adapter.WorkRiskThreeAdapter;
import com.example.new_androidclient.work.bean.TreeListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wh
 * 添加作业风险第三层
 * */
public class WorkRiskThreeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.nodata)
    NoDataLayout nodata;
    @BindView(R.id.define)
    Button define;

    List<TreeListBean.ChildrenBeanX.ChildrenBean> listTreeChildren = new ArrayList<>();
    WorkRiskThreeAdapter adapter;
    List<Integer> listId = new ArrayList<>();
    int id;
    int planWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_risk_three);
        ButterKnife.bind(this);

        getView();
        define.setOnClickListener(this);
    }

    private void getView() {
        listTreeChildren = (List<TreeListBean.ChildrenBeanX.ChildrenBean>) getIntent().getSerializableExtra("listTreeChildren");
        planWork = getIntent().getIntExtra("planWork",planWork);

        if (listTreeChildren.size() > 0){
            adapter = new WorkRiskThreeAdapter(mContext, listTreeChildren);
            listView.setAdapter(adapter);
        }else {
            nodata.setVisibility(View.VISIBLE);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
                boolean isChecked = checkedItemPositions.get(i);

                if (isChecked){
                    id = listTreeChildren.get(i).getId();
                    listId.add(id);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.define:
                if (listId.size() <= 0){
                    ToastUtil.show(mContext,"请选择安全防控措施");
                }else {
                    addAnalysic();
                }
                break;
        }
    }

    private void addAnalysic() {
        RetrofitUtil.getApi().addAnalysisContent(planWork,listId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(String show) {
                        ToastUtil.show(mContext,show);
                        finish();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }
}
