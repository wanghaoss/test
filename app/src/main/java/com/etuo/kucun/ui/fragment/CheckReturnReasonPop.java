package com.etuo.kucun.ui.fragment;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.model.TpAndGoodInfoModel;
import com.etuo.kucun.model.common.ReturnReasonListBean;
import com.etuo.kucun.ui.adapter.CheckReturnListAdapter;
import com.etuo.kucun.ui.adapter.FindTpByRfidInListAdapter;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.PWUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.StringUtil;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================
 *
 * @author :yhn
 * @version :
 * @date :2020/02/18/18:08
 * @ProjectNameDescribe : 选择
 * 修订历史：
 * ================================================
 */

public class CheckReturnReasonPop extends PWUtil implements View.OnClickListener, CheckReturnListAdapter.OnClickBtItem {


    @BindView(R.id.lv_goodsDetails)
    ListView mLvGoodsDetails;
    @BindView(R.id.llfour)
    LinearLayout mLlfour;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_ok)
    TextView mTvOk;
    private Activity mActivity;
    private List<ReturnReasonListBean> mInfoListModels;
    private CheckReturnListAdapter mAdapter;

    private OnClickBtItem onClickBtItem;

    private String checkId;// 拒绝理由id

    public void showPW(Activity activity, List<ReturnReasonListBean> viewData, View locationView) {
        if (activity == null || viewData == null || locationView == null) {
            return;
        }
        mActivity = activity;
        if (popupWindow == null) {
            View rootView = createPW(mActivity, R.layout.check_return_reason_popuwindow);
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
        setData(mInfoListModels);
    }

    @Override
    public void myItemClick(int position, ReturnReasonListBean singleData) {
//        checkId = singleData.getValue();
        checkId = mInfoListModels.get(position).getValue();
        LogUtil.d("点击了 : " +  mInfoListModels.get(position).getName()  + "    " + checkId);
        if ( mInfoListModels != null){
            for (int i= 0; i< mInfoListModels.size(); i++ ){

                ReturnReasonListBean listBean = new ReturnReasonListBean();
                listBean = mInfoListModels.get(i);

                if (i == position){
                    listBean.setCheck(true);
                }else {
                    listBean.setCheck(false);
                }
                mInfoListModels.set(i,listBean);

            }
            LogUtil.d("修改后的数据 : " + new Gson().toJson(mInfoListModels));
            mAdapter.updata(mInfoListModels);
            //刷新界面
            mAdapter.notifyDataSetChanged();
        }
    }


    //接口回调
    public interface OnClickBtItem {
        void myOrderOkClick(String checkId);

        void myOrderCancelClick();
    }

    public void OnClickBtItem(OnClickBtItem onClickBtItem) {
        this.onClickBtItem = onClickBtItem;
    }

    private void setData(List<ReturnReasonListBean> viewData) {
        if (viewData == null || viewData.size() == 0) {
            mLvGoodsDetails.setVisibility(View.GONE);
            return;
        }
        mAdapter = new CheckReturnListAdapter(mActivity, viewData);
        mAdapter.OnClickBtItem(this);
        mLvGoodsDetails.setAdapter(mAdapter);
//        mLvGoodsDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//
//                checkId = mInfoListModels.get(position).getValue();
//                LogUtil.d("点击了 : " +  mInfoListModels.get(position).getName()  + "    " + checkId);
//                if ( mInfoListModels != null){
//                    for (int i= 0; i< mInfoListModels.size(); i++ ){
//                        if (i == position){
//                            mInfoListModels.get(position).setCheck(true);
//                        }else {
//                            mInfoListModels.get(position).setCheck(false);
//                        }
//                    }
//                    mAdapter.updata(mInfoListModels);
//                    //刷新界面
//                    mAdapter.notifyDataSetChanged();
//                }
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                onClickBtItem.myOrderCancelClick();
                popupWindow.dismiss();
                break;
            case R.id.tv_ok:
                if (!StringUtil.isEmpty(checkId)){
                    onClickBtItem.myOrderOkClick(checkId);
                    popupWindow.dismiss();
                }else {
                    ShowToast.showShortToast(mActivity,"请先选择不通过原因");
                }
                break;
            default:
                break;
        }
    }
}
