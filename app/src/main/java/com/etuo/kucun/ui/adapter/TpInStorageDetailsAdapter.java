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
import com.etuo.kucun.model.TpInStorageModel;
import com.etuo.kucun.view.CustomListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 托盘验收adapter
 * Created by yhn on 2020/02/07.
 */

public class TpInStorageDetailsAdapter extends BaseAdapter {

    public static Context mContext;
    private LayoutInflater mInflater;
    private List<TpInStorageModel.GatherListBean> mData;
    private GlideImageLoader mImageLoader;
    private TpInStorageDetailsInListAdapter mAdapter;
    private String mType;
    private OnClickBtItem onClickBtItem;

    public TpInStorageDetailsAdapter(Context context, String type) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mImageLoader = new GlideImageLoader();
        mType = type;
    }

    public void updata(List<TpInStorageModel.GatherListBean> data) {
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
            convertView = mInflater.inflate(R.layout.item_tp_in_storage, null);
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

        void myAllOrderOkClick(int Fposition, TpInStorageModel.GatherListBean singleData);

        void myAllOrderCancelClick(int Fposition, TpInStorageModel.GatherListBean singleData);
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
        @BindView(R.id.tv_tuopan_yanshou)
        TextView mTvTuopanYanshou;
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
        @BindView(R.id.tv_tp_fenqu)
        TextView mTvTpFenqu;
        @BindView(R.id.tv_yanshou)
        TextView mTvYanshou;
        @BindView(R.id.tv_baoxiu)
        TextView mTvBaoxiu;
        @BindView(R.id.lv_tpNumList)
        CustomListView mLvTpNumList;
        @BindView(R.id.llfour)
        LinearLayout mLlfour;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setData(final int positions, final TpInStorageModel.GatherListBean singleData) {
            final String tmpImageUrl = singleData.getPalletImgPath1();
            mImageLoader.displayImage(mContext, tmpImageUrl, mIvTuopanKind, R.mipmap.icon_de_kind);

            mTvTuopanName.setText(singleData.getPalletName() + singleData.getPalletName());//托盘名称

            mTvTuopanNum.setText("x" + singleData.getPalletCnt());//托盘数量
            mTvTuopanType.setText(singleData.getPalletModel());//托盘型号
            mTvTuopanJingzai.setText("静载: " + singleData.getStaticLoad() + "T");//静载重量
            mTvTuopanDongzai.setText("动载: " + singleData.getDynamicLoad() + "T");//动载重量

            mTvTuopanYanshou.setText(singleData.getEndCnt() +"");
            mTvTuopanBaoxiu.setText(singleData.getUnusualCnt() +"");



            mAdapter = new TpInStorageDetailsInListAdapter(mContext, mData.get(positions).getDetailList(), mType);
            mLvTpNumList.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();



            mAdapter.OnClickBtItem(new TpInStorageDetailsInListAdapter.OnClickBtItem() {
                @Override
                public void myOrderOkClick(int position, TpInStorageModel.GatherListBean.DetailListBean singleData) {
                    onClickBtItem.myOrderOkClick(position, singleData);
                }

                @Override
                public void myOrderCancelClick(int position, TpInStorageModel.GatherListBean.DetailListBean singleData) {
                    onClickBtItem.myOrderCancelClick(position, singleData);
                }
            });

            /**
             * 托盘验收
             */
            mTvYanshou.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBtItem.myAllOrderOkClick(positions, singleData);
                }
            });

            /**
             * 托盘删除
             */

            mTvBaoxiu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBtItem.myAllOrderCancelClick(positions, singleData);
                }
            });



        }
    }
}
