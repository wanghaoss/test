package com.etuo.kucun.ui.fragment;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.model.StockUpDetailsModel;
import com.etuo.kucun.model.TpAndGoodInfoModel;
import com.etuo.kucun.ui.adapter.FindTpByRfidInListAdapter;
import com.etuo.kucun.utils.PWUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================
 *
 * @author :yhn
 * @version :
 * @date :2020/02/18/18:08
 * @ProjectNameDescribe : 显示批量扫描的托盘
 * 修订历史：
 * ================================================
 */

public class FindTpByRfidPopwindow extends PWUtil implements View.OnClickListener {


    @BindView(R.id.tv_tpNO)
    TextView mTvTpNO;
    @BindView(R.id.tv_tpOrCon)
    TextView mTvTpOrCon;
    @BindView(R.id.tv_tp_fenqu)
    TextView mTvTpFenqu;
    @BindView(R.id.lv_goodsDetails)
    ListView mLvGoodsDetails;
    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.rl_noData)
    RelativeLayout mRlNoData;
    @BindView(R.id.llfour)
    LinearLayout mLlfour;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_ok)
    TextView mTvOk;
    private Activity mActivity;
    private List<TpAndGoodInfoModel> mInfoListModels;
    private FindTpByRfidInListAdapter mAdapter;

    private OnClickBtItem onClickBtItem;


    public void showPW(Activity activity, List<TpAndGoodInfoModel> viewData, View locationView) {
        if (activity == null || viewData == null || locationView == null) {
            return;
        }
        mActivity = activity;
        if (popupWindow == null) {
            View rootView = createPW(mActivity, R.layout.pop_find_tp_rfid);
            ButterKnife.bind(this, rootView);
        }
        if (popupWindow.isShowing()) {
            return;
        }
        if (viewData == null) {
            return;
        }
        mTvCancel.setOnClickListener(this);
        mTvOk.setOnClickListener(this);
        mInfoListModels = viewData;
        setBackgroundAlpha(mActivity, 0.8f);
        popupWindow.showAtLocation(locationView, Gravity.CENTER, 0, 0);
        setData(viewData);
    }


    //接口回调
    public interface OnClickBtItem {
        void myOrderOkClick();
        void myOrderCancelClick();
    }

    public void OnClickBtItem(OnClickBtItem onClickBtItem) {
        this.onClickBtItem = onClickBtItem;
    }

    private void setData(List<TpAndGoodInfoModel> viewData) {
        if (viewData == null || viewData.size() == 0) {
            mRlNoData.setVisibility(View.VISIBLE);
            mLvGoodsDetails.setVisibility(View.GONE);
            return;
        }
        mAdapter = new FindTpByRfidInListAdapter(mActivity, viewData);
        mLvGoodsDetails.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                onClickBtItem.myOrderCancelClick();
                popupWindow.dismiss();
                break;
            case R.id.tv_ok:
                onClickBtItem.myOrderOkClick();
                popupWindow.dismiss();
                break;
            default:
                break;
        }
    }
}
