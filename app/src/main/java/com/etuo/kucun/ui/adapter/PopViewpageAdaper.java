package com.etuo.kucun.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhn on 2018/9/6.
 * pop 弹出框下的 viewPage 适配器
 */

public class PopViewpageAdaper  extends FragmentPagerAdapter {
    //存储所有的fragment
    private List<Fragment> fragments;

    public PopViewpageAdaper(FragmentManager fm, ArrayList<Fragment> list){
        super(fm);
        this.fragments = list;
    }


    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);

    }

    @Override
    public int getCount() {
        return fragments.size();
    }



}

