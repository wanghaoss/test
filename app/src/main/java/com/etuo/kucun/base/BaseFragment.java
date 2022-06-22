package com.etuo.kucun.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.etuo.kucun.widget.CustomClipHintLoading;
import com.etuo.kucun.widget.CustomClipLoading;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    protected Context mContext;

    private CustomClipHintLoading dialog;// 加载

    protected Unbinder unbinder;

    public View bindedView;


    /** Fragment当前状态是否可见 */
    protected boolean isVisible;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindedView = inflater.inflate(bindView(), container, false);

        unbinder = ButterKnife.bind(this, bindedView);

        return bindedView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    protected View mStatusBarView;

    private void addStatusBar(View mView) {
        if (mStatusBarView == null) {
            mStatusBarView = new View(getContext());
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
//            int statusBarHeight = getStatusBarHeight(getActivity());
            int statusBarHeight = getStatusBarHeight();
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(screenWidth, statusBarHeight);
            mStatusBarView.setLayoutParams(params);
            mStatusBarView.requestLayout();
            if (mView != null)
                ((ViewGroup)mView).addView(mStatusBarView, 0);
        }
    }



    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
//                View decorView = window.getDecorView(); //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
//                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//                decorView.setSystemUiVisibility(option);
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

                window.setStatusBarColor(Color.TRANSPARENT); //导航栏颜色也可以正常设置
                //                                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();

                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
                // attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

//


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) unbinder.unbind();

    }

    public <T extends View> T findId(@IdRes int resId) {
        return (T) getView().findViewById(resId);
    }

    protected abstract int bindView();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 显示进度条
     */
    protected void showLoadingDialog(String hint) {
        if (dialog == null) {
            dialog = new CustomClipHintLoading(getActivity(),hint);
        }
        if (!getActivity().isFinishing()) {
            dialog.show();
        }
    }

    /**
     * 隐藏进度条
     */
    protected void hideLoadingDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    protected boolean isLoadingDialogShowing(){
        if (dialog != null) {
            return dialog.isShowing();
        } else {
            return false;
        }
    }


    /**************判断 fragment 是否可见********************/
    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {


    }


    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
}