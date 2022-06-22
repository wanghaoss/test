package com.etuo.kucun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 *
 * @author Liujinxin
 * @version 2.0.0
 * @since 2018/03/8
 */

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager guideVP;
    private int resIds[] = new int[]{R.drawable.image_duide_first, R.drawable.image_guide_second, R.drawable.image_guide_third};
    private List<View> imageViews;
    private BannerPagerAdapter adapter;
    private Button guide_button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        imageViews = new ArrayList<>();
        initView();
    }

    private void initView() {
        imageViews.clear();
        guideVP = (ViewPager) findViewById(R.id.guideVP);
        guide_button = (Button) findViewById(R.id.guide_button);
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        // 初始化引导图片列表
        for (int i = 0; i < resIds.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(resIds[i]);
            imageViews.add(iv);
        }
        guideVP.setOnPageChangeListener(this);
        adapter = new BannerPagerAdapter();
        guideVP.setAdapter(adapter);
        guide_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 2) {
            guide_button.setVisibility(View.VISIBLE);
        } else {
            guide_button.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class BannerPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            View view = imageViews.get(position);
            return view;
        }
    }
}
