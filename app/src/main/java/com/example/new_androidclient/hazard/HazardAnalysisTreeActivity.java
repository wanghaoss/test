package com.example.new_androidclient.hazard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.HazardAnalysisTree.Dept;
import com.example.new_androidclient.Other.HazardAnalysisTree.Node;
import com.example.new_androidclient.Other.HazardAnalysisTree.NodeHelper;
import com.example.new_androidclient.Other.HazardAnalysisTree.NodeTreeAdapter;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.hazard.bean.HazardAnalysisTreeBean;
import com.example.new_androidclient.hazard.bean.HazardAnalysisTreeSubClassBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Route(path = RouteString.HazardAnalysisTreeActivity)
public class HazardAnalysisTreeActivity extends BaseActivity {

    private ListView mListView;
    private NodeTreeAdapter mAdapter;
    private LinkedList<Node> mLinkedList = new LinkedList<>();
    List<HazardAnalysisTreeSubClassBean> childrenList = new ArrayList<>();
    List<Node> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazard_analysis_tree);
        mListView = findViewById(R.id.id_tree);
        mAdapter = new NodeTreeAdapter(this, mListView, mLinkedList, new NodeTreeAdapter.selectListener() {
            @Override
            public void select(int id, String label) {
                Intent intent = new Intent();
                intent.putExtra("deptId", id);
                intent.putExtra("name", label);
                setResult(3, intent);
                finish();
            }
        });
        mListView.setAdapter(mAdapter);
        getList();
    }

    private void initView() {
        initData(childrenList);
        mLinkedList.addAll(NodeHelper.sortNodes(data));
        mAdapter.expandOrCollapse(0);
        mAdapter.notifyDataSetChanged();
    }

    private void initData(List<HazardAnalysisTreeSubClassBean> childrenList) {
        for (int i = 0; i < childrenList.size(); i++) {
            data.add(new Dept(childrenList.get(i).getId(), childrenList.get(i).getParentId(), childrenList.get(i).getLabel()));

            if (childrenList.get(i).getChildren() != null) {
                initData(childrenList.get(i).getChildren());
            }
        }
    }

    private void getList() {
        RetrofitUtil.getApi().getTreeList()
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<HazardAnalysisTreeBean>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(HazardAnalysisTreeBean bean) {
                        if (bean != null) {
                            childrenList = bean.getTreeData();
                            initView();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }
}
