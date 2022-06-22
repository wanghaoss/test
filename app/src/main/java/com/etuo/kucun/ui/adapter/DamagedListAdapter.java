package com.etuo.kucun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.loader.GlideImageLoader;
import com.etuo.kucun.model.DamagedModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 报损列表adapter
 * Created by xfy on 2018/11/22.
 */

public class DamagedListAdapter extends BaseAdapter {
    public static Context mContext;
    private LayoutInflater mInflater;
    private List<DamagedModel> mData;
    private OnClickBtItem onClickBtItem;
    private GlideImageLoader mImageLoader;
    private String Flag_from; // 0 未处理  1  已处理

    public DamagedListAdapter(Context context, String Flag_from) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.Flag_from = Flag_from;
        mImageLoader = new GlideImageLoader();
    }

    public void updata(List<DamagedModel> data) {
        mData = data;
    }


    // 移除所有数据
    public void removeAll() {
        if (mData != null && mData.size() > 0) {
            for (int i = mData.size() - 1; i >= 0; i--) {
                mData.remove(i);
            }
        }

    }

    //接口回调
    public interface OnClickBtItem {

        void myDeleteClick(int position, String id);
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
            convertView = mInflater.inflate(R.layout.item_damaged, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.setData(position, mData.get(position));
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.im_tp_icon)
        ImageView imTpIcon;
        @BindView(R.id.tv_tp_num)
        TextView tvTpNum;
        @BindView(R.id.tv_damaged_time)
        TextView tvDamagedTime;
        @BindView(R.id.ll_base)
        LinearLayout llBase;
        @BindView(R.id.iv_right_more)
        ImageView ivRightMore;
        @BindView(R.id.tv_tp_damaged_status)
        TextView tvTpDamagedStatus;
        @BindView(R.id.rl_group)
        RelativeLayout rlGroup;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setData(final int positions, final DamagedModel singleData) {
            if (Flag_from.equals("0")) {//未处理
                mImageLoader.displayImage(mContext, singleData.getImgPath(), imTpIcon, R.mipmap.icon_de_kind);
                if (singleData.getProductType().equals("1")) {
                    tvTpNum.setText("托盘编号: " + singleData.getPalletNum());
                } else if (singleData.getProductType().equals("2")) {
                    tvTpNum.setText("集装箱编号: " + singleData.getPalletNum());
                }
                tvDamagedTime.setText("报损日期: " + singleData.getDamagedDate());
                tvTpDamagedStatus.setVisibility(View.GONE);
                ivRightMore.setVisibility(View.VISIBLE);
                rlGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickBtItem.myDeleteClick(positions, singleData.getId());
                    }
                });
            } else if (Flag_from.equals("1")) {//已处理
                mImageLoader.displayImage(mContext, singleData.getImgPath(), imTpIcon, R.mipmap.icon_de_kind);
                if (singleData.getProductType().equals("1")) {
                    tvTpNum.setText("托盘编号: " + singleData.getPalletNum());
                } else if (singleData.getProductType().equals("2")) {
                    tvTpNum.setText("集装箱编号: " + singleData.getPalletNum());
                }
                tvDamagedTime.setText("处理日期: " + singleData.getDamagedDate());
                ivRightMore.setVisibility(View.INVISIBLE);
                tvTpDamagedStatus.setVisibility(View.VISIBLE);
                if (singleData.getPalletState() != null && singleData.getPalletState().equals("已报废")) {
                    tvTpDamagedStatus.setText("已报废");
                    tvTpDamagedStatus.setTextColor(mContext.getResources().getColor(R.color.red));
                } else if (singleData.getPalletState() != null && singleData.getPalletState().equals("已修复")) {
                    tvTpDamagedStatus.setText("已修复");
                    tvTpDamagedStatus.setTextColor(mContext.getResources().getColor(R.color.green_text));

                }
            }

        }
    }
}
