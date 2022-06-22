package com.etuo.kucun.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.model.StockUpDetailsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * bom 备货等的状态adapter 第二层
 * Created by yhn on 2020/02/07.
 */

public class TpStockUpDetailsInListAdapter extends BaseAdapter {
    public static Context mContext;
    private LayoutInflater mInflater;
    private List<StockUpDetailsModel.GatherListBean.DetailListBean> mData;
    private String fromType;

    private OnClickBtItem onClickBtItem;


    public TpStockUpDetailsInListAdapter(Context context, List<StockUpDetailsModel.GatherListBean.DetailListBean> data, String fromType) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;
        this.fromType = fromType;
    }

    //接口回调
    public interface OnClickBtItem {
        void myOrderOkClick(int position,StockUpDetailsModel.GatherListBean.DetailListBean singleData);
        void myOrderCancelClick(int position, StockUpDetailsModel.GatherListBean.DetailListBean singleData);
    }

    public void OnClickBtItem(OnClickBtItem onClickBtItem) {
        this.onClickBtItem = onClickBtItem;
    }
    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_tp_bom_list_status, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.setData(position, mData.get(position));
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_No)
        TextView mTvNo;
        @BindView(R.id.tv_num)
        TextView mTvNum;
        @BindView(R.id.tv_cargo_info)
        TextView mTvCargoInfo;
        @BindView(R.id.tv_kuqu_num)
        TextView mTvKuquNum;
        @BindView(R.id.tv_yanshou)
        TextView mTvYanshou;
        @BindView(R.id.tv_baoxiu)
        TextView mTvBaoxiu;
        @BindView(R.id.ll_bg)
        LinearLayout mLlBg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setData(final int positions, final StockUpDetailsModel.GatherListBean.DetailListBean singleData) {

            if ((positions +1)%2 !=0 ){
                if (singleData.isLastCheck()){
                    mLlBg.setBackground(mContext.getResources().getDrawable(R.drawable.bg_check_orange_gray));
                }else {
                    mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg_gray));
                }
            }else {
                if (singleData.isLastCheck()){
                    mLlBg.setBackground(mContext.getResources().getDrawable(R.drawable.bg_check_orange_white));
                }else {
                    mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }

            }
            mTvNo.setText(getNewNum(positions+1));
            mTvNum.setText(singleData.getPalletNum());
            mTvCargoInfo.setText("数量 : " + singleData.getGoodsCnt()+" " + "重量 : "+singleData.getGoodsWeight() +"kg" );
            mTvKuquNum.setText(singleData.getStorageAreaName());
            //货物备货单状态：0待备货 1已备货

            mTvNo.setTextColor(getColorByStatus(singleData.getStatus()));
            mTvNum.setTextColor(getColorByStatus(singleData.getStatus()));
            mTvCargoInfo.setTextColor(getColorByStatus(singleData.getStatus() ));
            mTvKuquNum.setTextColor(getColorByStatus(singleData.getStatus() ));
            if (ExtraConfig.IntentType.FROM_INTENT_BY_BEIHUO.equals(fromType)){
                mTvYanshou.setText("确定");
                mTvBaoxiu.setText("取消");
                mTvYanshou.setTextColor(getColorByStatus(singleData.getStatus() ));
                mTvBaoxiu.setTextColor(getColorByStatus(singleData.getStatus() ));
                mTvYanshou.setBackground(getBtBgByStatus(singleData.getStatus()));
                mTvBaoxiu.setBackground(getBtBgByStatus(singleData.getStatus()));
            }

            mTvYanshou.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBtItem.myOrderOkClick(positions,singleData);
                }
            });
            mTvBaoxiu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBtItem.myOrderCancelClick(positions,singleData);
                }
            });

        }
    }


    private String getNewNum(int num){

        if (num <= 9){
            return "0"+num;
        }else {
            return num +"";
        }

    }

    /**
     *
     * @param status  //货物备货单状态：0待备货 1已备货 2 ->解绑
     * @return 根据状态选择不同的颜色
     */
    private int getColorByStatus(String status){

        if ("0".equals(status)){
            return mContext.getResources().getColor(R.color.font_black);
        }else if ("1".equals(status)){
            return mContext.getResources().getColor(R.color.green_text);
        }else {
            return mContext.getResources().getColor(R.color.red);
        }


    }

    /**
     *
     * @param status  //货物备货单状态：0待备货 1已备货2 ->解绑
     * @return 根据状态选择不同的颜色
     */
    private Drawable getBtBgByStatus(String status){

        if ("0".equals(status)){
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_black_bg_white);
        }else if ("1".equals(status)){
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_green_bg_white);
        }else {
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_red_bg_white);
        }


    }
}
