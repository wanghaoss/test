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
import com.etuo.kucun.model.TpInStorageModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 托盘入库的状态adapter
 * Created by yhn on 2020/02/07.
 */

public class TpInStorageDetailsInListAdapter extends BaseAdapter {
    public static Context mContext;
    private LayoutInflater mInflater;
    private List<TpInStorageModel.GatherListBean.DetailListBean> mData;

    private String fromType;
    private OnClickBtItem onClickBtItem;

    public TpInStorageDetailsInListAdapter(Context context, List<TpInStorageModel.GatherListBean.DetailListBean> data, String fromType) {
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
            convertView = mInflater.inflate(R.layout.item_tp_in_storage_list_status, null);
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
        void myOrderOkClick(int position, TpInStorageModel.GatherListBean.DetailListBean singleData);

        void myOrderCancelClick(int position, TpInStorageModel.GatherListBean.DetailListBean singleData);
    }

    public void OnClickBtItem(OnClickBtItem onClickBtItem) {
        this.onClickBtItem = onClickBtItem;
    }



    private String getNewNum(int num) {

        if (num <= 9) {
            return "0" + num;
        } else {
            return num + "";
        }

    }

    /**
     * @param status //货物备货单状态：0待验收 2已验收 4已报损
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

    /**
     * @param status //货物备货单状态：0待验收 2已验收 4已报损
     * @return 根据状态选择不同的颜色
     */
    private Drawable getBtBgByStatus(String status) {

        if ("0".equals(status)) {
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_black_bg_white);
        } else if ("1".equals(status)) {
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_green_bg_white);
        } else {
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_red_bg_white);
        }


    }

    class ViewHolder {
        @BindView(R.id.tv_No)
        TextView mTvNo;
        @BindView(R.id.tv_num)
        TextView mTvNum;
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


        void setData(final int positions, final TpInStorageModel.GatherListBean.DetailListBean singleData) {


            if ((positions + 1) % 2 != 0) {
                mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg_gray));
            } else {
                mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            mTvNo.setText(getNewNum(positions + 1));
            mTvNum.setText(singleData.getPalletNum());
            //0待验收 1已验收 2已报损

            mTvKuquNum.setText(singleData.getStorageAreaName());//库区分区

            mTvNo.setTextColor(getColorByStatus(singleData.getStatus()));
            mTvNum.setTextColor(getColorByStatus(singleData.getStatus()));
            mTvKuquNum.setTextColor(getColorByStatus(singleData.getStatus()));


                mTvYanshou.setText("入库");
                mTvBaoxiu.setText("清除");
                mTvYanshou.setTextColor(getColorByStatus(singleData.getStatus()));
                mTvBaoxiu.setTextColor(getColorByStatus(singleData.getStatus()));
                mTvYanshou.setBackground(getBtBgByStatus(singleData.getStatus()));
                mTvBaoxiu.setBackground(getBtBgByStatus(singleData.getStatus()));

            mTvYanshou.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBtItem.myOrderOkClick(positions, singleData);
                }
            });
            mTvBaoxiu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBtItem.myOrderCancelClick(positions, singleData);
                }
            });
        }
    }
}
