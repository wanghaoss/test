package com.etuo.kucun.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.androidkun.xtablayout.XTabLayout;
import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseHeaderBarActivity;
import com.etuo.kucun.ui.adapter.TablayoutAdapter;
import com.etuo.kucun.ui.fragment.DamagedFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 报损
 * Created by xfy on 2018/11/20.
 */

public class DamagedTabActivity extends BaseHeaderBarActivity {
    @BindView(R.id.tablayout)
    XTabLayout tablayout;
    @BindView(R.id.ling_gray)
    View lingGray;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private TablayoutAdapter adapter;
    private String[] titles = {"未处理", "已处理"};
    private ArrayList<Fragment> fragments;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damagedtab);
        ButterKnife.bind(this);
        setHeaderTitle("报损清单");
        initView();
    }
    public void initView(){
        //如果是 fragment 的话,getChildFragmentManager()
        adapter = new TablayoutAdapter(getSupportFragmentManager(), titles);

        viewPager.setAdapter(adapter);
        //必须在 setAdapter()
        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabsFromPagerAdapter(adapter);


        initFragment();
    }
    /**
     * 添加fragment
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        //未处理
        Fragment noFragment = DamagedFragment.newInstance("0");
        //已处理
        Fragment yesFragment = DamagedFragment.newInstance("1");
        fragments.add(noFragment);
        fragments.add(yesFragment);

//        fragments.add(new DamagedFragment("0"));// 未处理
//        fragments.add(new DamagedFragment("1"));// 已处理


        adapter.setData(fragments);
    }

}
