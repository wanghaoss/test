package com.etuo.kucun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.loader.GlideImageLoader;
import com.etuo.kucun.model.TpCheckDetailsModel;
import com.etuo.kucun.view.CustomListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 托盘验收adapter
 * Created by yhn on 2020/02/07.
 */

public class TpCheckDetailsAdapter extends BaseAdapter {

    public static Context mContext;
    private LayoutInflater mInflater;
    private List<TpCheckDetailsModel.PalletModelListBean> mData;
    private GlideImageLoader mImageLoader;
    private TpCheckDetailsInListAdapter mAdapter;
    private String mType;
    private OnClickBtItem onClickBtItem;
    public TpCheckDetailsAdapter(Context context, String type) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mImageLoader = new GlideImageLoader();
        mType = type;
    }

    public void updata(List<TpCheckDetailsModel.PalletModelListBean> data) {
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
            convertView = mInflater.inflate(R.layout.item_check_tp_details, null);
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

        void myOrderPassAllByModelClick(int position, TpCheckDetailsModel.PalletModelListBean singleData);
    }

    public void OnClickBtItem(OnClickBtItem onClickBtItem) {
        this.onClickBtItem = onClickBtItem;
    }


    class ViewHolder {
        @BindView(R.id.iv_tuopan_kind)
        ImageView mIvTuopanKind;
        @BindView(R.id.tv_tuopan_name)
        TextView mTvTuopanName;
        @BindView(R.id.tv_tuopan_jingzai)
        TextView mTvTuopanJingzai;
        @BindView(R.id.tv_ruku_name)
        TextView mTvRukuName;
        @BindView(R.id.tv_tuopan_yanshou)
        TextView mTvTuopanYanshou;
        @BindView(R.id.tv_tuopan_baoxiu_name)
        TextView mTvTuopanBaoxiuName;
        @BindView(R.id.tv_tuopan_baoxiu)
        TextView mTvTuopanBaoxiu;
        @BindView(R.id.tv_tuopan_dongzai)
        TextView mTvTuopanDongzai;
        @BindView(R.id.tv_tuopan_type)
        TextView mTvTuopanType;
        @BindView(R.id.tv_tuopan_num)
        TextView mTvTuopanNum;
        @BindView(R.id.rltwo)
        LinearLayout mRltwo;
        @BindView(R.id.tv_tpNO)
        TextView mTvTpNO;
        @BindView(R.id.tv_tpOrCon)
        TextView mTvTpOrCon;
        @BindView(R.id.tv_all_pass)
        TextView mTvAllPass;
        @BindView(R.id.lv_tpNumList)
        CustomListView mLvTpNumList;
        @BindView(R.id.llfour)
        LinearLayout mLlfour;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setData(final int positions, final TpCheckDetailsModel.PalletModelListBean singleData) {
            final String tmpImageUrl = singleData.getThumbnail();
            mImageLoader.displayImage(mContext, tmpImageUrl, mIvTuopanKind, R.mipmap.icon_de_kind);

            mTvTuopanName.setText(singleData.getPalletName() + singleData.getPalletSpec());//托盘名称

            mTvTuopanNum.setText("x" + singleData.getQuantity());//托盘数量
            mTvTuopanType.setText(singleData.getPalletModel());//托盘型号
            mTvTuopanJingzai.setText("静载: " + singleData.getStaticLoad() + "T");//静载重量
            mTvTuopanDongzai.setText("动载: " + singleData.getDynamicLoad() + "T");//动载重量
            if (ExtraConfig.IntentType.FROM_INTENT_BY_SHOU.equals(mType)) {
                mTvRukuName.setText("已入库: ");
                mTvTuopanBaoxiuName.setText("保修: ");
            }
            mTvTuopanYanshou.setText(singleData.getHaveCheckCnt() + "");
            mTvTuopanBaoxiu.setText(singleData.getRepairsCnt() + "");

            mAdapter = new TpCheckDetailsInListAdapter(mContext, mData.get(positions).getCheckBillPalletList(),mType);
            mLvTpNumList.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            mAdapter.OnClickBtItem(new TpCheckDetailsInListAdapter.OnClickBtItem() {
                @Override
                public void myOrderOkClick(int position, TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean singleData) {
                    onClickBtItem.myOrderOkClick(position,singleData);
                }

                @Override
                public void myOrderCancelClick(int position, TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean singleData) {
                    onClickBtItem.myOrderCancelClick(position,singleData);
                }
            });

            //单型号全部验收
            mTvAllPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onClickBtItem.myOrderPassAllByModelClick(positions,singleData);


                }
            });

        }
    }
}
