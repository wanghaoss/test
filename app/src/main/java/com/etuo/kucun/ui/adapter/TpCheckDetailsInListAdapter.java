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

import com.etuo.kucun.model.TpCheckDetailsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 托盘验收的状态adapter
 * Created by yhn on 2020/02/07.
 */

public class TpCheckDetailsInListAdapter extends BaseAdapter {
    public static Context mContext;
    private LayoutInflater mInflater;
    private List<TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean> mData;

    private String fromType;
    private OnClickBtItem onClickBtItem;
    public TpCheckDetailsInListAdapter(Context context, List<TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean> data,String fromType) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;
        this.fromType = fromType;
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
            convertView = mInflater.inflate(R.layout.item_tp_yanshou_list_status, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.setData(position, mData.get(position));
        return convertView;
    }

    //接口回调
    public interface OnClickBtItem {
        void myOrderOkClick(int position,TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean singleData);
        void myOrderCancelClick(int position, TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean singleData);
    }

    public void OnClickBtItem(OnClickBtItem onClickBtItem) {
        this.onClickBtItem = onClickBtItem;
    }

    class ViewHolder {
        @BindView(R.id.tv_No)
        TextView mTvNo;
        @BindView(R.id.tv_num)
        TextView mTvNum;
        @BindView(R.id.tv_yanshou)
        TextView mTvYanshou;
        @BindView(R.id.tv_baoxiu)
        TextView mTvBaoxiu;
        @BindView(R.id.ll_bg)
        LinearLayout mLlBg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


        void setData(final int positions, final TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean singleData) {


            if ((positions +1)%2 !=0 ){
                mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg_gray));
            }else {
                mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            mTvNo.setText(getNewNum(positions+1));
            mTvNum.setText(singleData.getPalletNum());
            //0待验收 2已验收 4已报损

            mTvNo.setTextColor(getColorByStatus(singleData.getStatus()));
            mTvNum.setTextColor(getColorByStatus(singleData.getStatus()));


            if ("0".equals(singleData.getStatus())){
                mTvYanshou.setVisibility(View.VISIBLE);
            }else if ("2".equals(singleData.getStatus())){
                mTvYanshou.setVisibility(View.INVISIBLE);
            }else {
                mTvYanshou.setVisibility(View.INVISIBLE);
            }

                mTvYanshou.setText("验收");
                mTvBaoxiu.setText("报修");
                mTvYanshou.setTextColor(getColorByStatus(singleData.getStatus() ));
                mTvBaoxiu.setTextColor(getColorByStatus(singleData.getStatus() ));
                mTvYanshou.setBackground(getBtBgByStatus(singleData.getStatus()));
                mTvBaoxiu.setBackground(getBtBgByStatus(singleData.getStatus()));


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
     * @param status  //货物备货单状态：0待验收 2已验收 4已报损
     * @return 根据状态选择不同的颜色
     */
    private int getColorByStatus(String status){

        if ("0".equals(status)){
            return mContext.getResources().getColor(R.color.font_black);
        }else if ("2".equals(status)){
            return mContext.getResources().getColor(R.color.green_text);
        }else {
            return mContext.getResources().getColor(R.color.red);
        }


    }

    /**
     *
     * @param status  //货物备货单状态：0待验收 2已验收 4已报损
     * @return 根据状态选择不同的颜色
     */
    private Drawable getBtBgByStatus(String status){

        if ("0".equals(status)){
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_black_bg_white);
        }else if ("2".equals(status)){
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_green_bg_white);
        }else {
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_red_bg_white);
        }


    }
}
