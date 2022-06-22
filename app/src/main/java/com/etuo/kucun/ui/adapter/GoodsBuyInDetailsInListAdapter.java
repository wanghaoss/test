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
import com.etuo.kucun.model.BuyCheckDetailsModel;
import com.etuo.kucun.model.BuyInDetailsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 采购入库adapter 第二层
 * Created by yhn on 2020/02/07.
 */

public class GoodsBuyInDetailsInListAdapter extends BaseAdapter {
    public static Context mContext;
    private LayoutInflater mInflater;
    private List<BuyInDetailsModel.GatherListBean.DetailListBean> mData;
    private String fromType;

    private OnClickBtItem onClickBtItem;


    public GoodsBuyInDetailsInListAdapter(Context context, List<BuyInDetailsModel.GatherListBean.DetailListBean> data, String fromType) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;
        this.fromType = fromType;
    }

    //接口回调
    public interface OnClickBtItem {
        void myOrderOkClick(int position, BuyInDetailsModel.GatherListBean.DetailListBean singleData);
        void myOrderCancelClick(int position, BuyInDetailsModel.GatherListBean.DetailListBean singleData);
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

        void setData(final int positions, final BuyInDetailsModel.GatherListBean.DetailListBean singleData) {

            if ((positions +1)%2 !=0 ){
                mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg_gray));
            }else {
                mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            mTvNo.setText(getNewNum(positions+1));
            mTvNum.setText(singleData.getPalletNum());
            mTvCargoInfo.setText("数量 : " + singleData.getEndGoodsCnt()+" " + "重量 : "+singleData.getEndGoodsWeight() +"kg" );
            mTvKuquNum.setVisibility(View.GONE);
            //采购验收单状态：0待验收 1通过2 ->不通过

            mTvNo.setTextColor(getColorByStatus(singleData.getStatus()));
            mTvNum.setTextColor(getColorByStatus(singleData.getStatus()));
            mTvCargoInfo.setTextColor(getColorByStatus(singleData.getStatus() ));
            mTvKuquNum.setTextColor(getColorByStatus(singleData.getStatus() ));
//            if (ExtraConfig.IntentType.FROM_INTENT_BY_BEIHUO.equals(fromType)){
                mTvYanshou.setText("入库");
                mTvBaoxiu.setText("取消");
                mTvYanshou.setTextColor(getColorByStatus(singleData.getStatus() ));
                mTvBaoxiu.setTextColor(getColorByStatus(singleData.getStatus() ));
                mTvYanshou.setBackground(getBtBgByStatus(singleData.getStatus()));
                mTvBaoxiu.setBackground(getBtBgByStatus(singleData.getStatus()));
//            }

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
     * @param status  //采购验收单状态：0待验收 1通过2 ->不通过
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
     * @param status  //采购验收单状态：0待验收 1通过2 ->不通过
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
