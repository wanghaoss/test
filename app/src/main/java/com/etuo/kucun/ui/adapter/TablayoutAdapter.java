package com.etuo.kucun.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by yhn on 2018/06/27.
 * tablayout适配器
 */
public class TablayoutAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private List<Fragment> fragments;


    public TablayoutAdapter(FragmentManager fragmentManager, String[] titles) {
        super(fragmentManager);
        this.titles = titles;
    }


    public void setData(List<Fragment> data){
        fragments = data;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);

    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles[position];
    }

}
