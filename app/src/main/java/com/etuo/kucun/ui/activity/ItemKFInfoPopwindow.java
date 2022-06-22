package com.etuo.kucun.ui.activity;


import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseFragment;
import com.etuo.kucun.model.OrderListModel;
import com.etuo.kucun.utils.PWUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemKFInfoPopwindow extends PWUtil implements View.OnClickListener {



    @BindView(R.id.tv_branches_num)
    TextView mTvBranchesNum;
    @BindView(R.id.iv_close_pop)
    ImageView mIvClosePop;
    private BaseFragment mActivity;
    private OrderListModel mInfoListModels;


    public void showPW(BaseFragment activity, OrderListModel viewData, View locationView) {
        if (activity == null || viewData == null || locationView == null)
            return;

        mActivity = activity;

        if (popupWindow == null) {
            View rootView = createPW(mActivity.getActivity(), R.layout.pop_order_by_kf);
            ButterKnife.bind(this, rootView);

        }
        mIvClosePop.setOnClickListener(this);

        if (popupWindow.isShowing())
            return;
        if (viewData == null)
            return;
        mInfoListModels = viewData;
        setBackgroundAlpha(mActivity.getActivity(), 0.8f);
        popupWindow.showAtLocation(locationView, Gravity.CENTER, 0, 0);

        setData();


    }

    private void setData() {

        mTvBranchesNum.setText(mInfoListModels.getReceiveMobile());

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_pop:
                popupWindow.dismiss();
                break;

        }
    }


}
