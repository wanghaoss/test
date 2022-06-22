package com.example.new_androidclient.hazard;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.main.MainActivityAdapter;
import com.example.new_androidclient.main.bean.HomeListBean;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouteString.HazardMainActivity)
public class HazardMainActivity extends BaseActivity {

    List<HomeListBean> listBeans = new ArrayList<>();
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazard_main);
        recyclerView = findViewById(R.id.hazard_main_activity_recycler_view);
        initList();
        MainActivityAdapter activityAdapter = new MainActivityAdapter(listBeans, BR.homeListBean, (view, position) -> onItemClick(position));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(activityAdapter);


    }

    private void initList() {
        String[] name = {"隐患待办", "隐患排查计划"};
        int[] image = {
                R.drawable.interim_plan,
                R.drawable.work_order};

        for (int i = 0; i < name.length; i++) {
            HomeListBean homeListBean = new HomeListBean();
            homeListBean.name = name[i];
            homeListBean.image = image[i];
            listBeans.add(homeListBean);
        }
    }



    public void onItemClick(int position) {
        String str = "";
        switch (position) {
            case 0:
                str = RouteString.HazardListActivity;
                break;
            case 1:
                str = RouteString.HazardTablePlanListActivity;
                break;
        }
        if (str != "") {
            ARouter.getInstance().build(str).navigation();
        }
    }

}
