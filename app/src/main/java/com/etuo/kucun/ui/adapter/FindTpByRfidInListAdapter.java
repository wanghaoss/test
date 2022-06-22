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
import com.etuo.kucun.model.TpAndGoodInfoModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * pop里显示托盘信息的
 * Created by yhn on 2020/02/07.
 */

public class FindTpByRfidInListAdapter extends BaseAdapter {
    public static Context mContext;
    private LayoutInflater mInflater;
    private List<TpAndGoodInfoModel> mData;
    private String fromType;



    public FindTpByRfidInListAdapter(Context context, List<TpAndGoodInfoModel> data) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;

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
            convertView = mInflater.inflate(R.layout.item_tp_pop_list_status, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.setData(position, mData.get(position));
        return convertView;
    }


    private String getNewNum(int num) {

        if (num <= 9) {
            return "0" + num;
        } else {
            return num + "";
        }

    }

    /**
     * @param status //货物备货单状态：0待备货 1已备货 2 ->解绑
     * @return 根据状态选择不同的颜色
     */
    private int getColorByStatus(String status) {

        if ("0".equals(status)) {
            return mContext.getResources().getColor(R.color.font_black);
        } else if ("1".equals(status)) {
            return mContext.getResources().getColor(R.color.green_text);
        } else {
            return mContext.getResources().getColor(R.color.red);
        }


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
        @BindView(R.id.ll_bg)
        LinearLayout mLlBg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        void setData(final int positions, final TpAndGoodInfoModel singleData) {

            if ((positions + 1) % 2 != 0) {
                mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg_gray));
            } else {
                mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            mTvNo.setText(getNewNum(positions + 1));
            mTvNum.setText(singleData.getPalletNum());
            mTvCargoInfo.setText("数量 : " + singleData.getGoodsCnt() + " " + "重量 : " + singleData.getGoodsWeight() + "kg");
            mTvKuquNum.setText(singleData.getStorageAreaName());
            //货物备货单状态：0待备货 1已备货

            mTvNo.setTextColor(getColorByStatus(singleData.getStatus()));
            mTvNum.setTextColor(getColorByStatus(singleData.getStatus()));
            mTvCargoInfo.setTextColor(getColorByStatus(singleData.getStatus()));
            mTvKuquNum.setTextColor(getColorByStatus(singleData.getStatus()));



        }

    }
}
