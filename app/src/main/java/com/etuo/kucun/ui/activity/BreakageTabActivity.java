package com.etuo.kucun.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseHeaderBarActivity;
import com.etuo.kucun.ui.adapter.TablayoutAdapter;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/31.
 */

public class BreakageTabActivity extends BaseHeaderBarActivity {


    @BindView(R.id.tablayout)
    XTabLayout mTablayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private String[] titles = {"未处理", "已处理"};
    private ArrayList<Fragment> fragments;
    private TablayoutAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_check_tab);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {


        setHeaderTitle("托盘报损");


        //如果是 fragment 的话,getChildFragmentManager()
        adapter = new TablayoutAdapter(getSupportFragmentManager(), titles);

        mViewPager.setAdapter(adapter);
        //必须在 setAdapter()
        mTablayout.setupWithViewPager(mViewPager);
        mTablayout.setTabsFromPagerAdapter(adapter);


        initFragment();
    }



    /**
     * 添加fragment
     */
    private void initFragment() {
        fragments = new ArrayList<>();
//        fragments.add(new RentOrderNewFragment("1"));
//        fragments.add(new RentOrderNewFragment("2"));


        adapter.setData(fragments);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

}
