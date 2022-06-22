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
import com.etuo.kucun.loader.GlideImageLoader;
import com.etuo.kucun.model.BOMListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yhn on 202/02/10.
 * BOM 备货等列表 最里面的数据
 */
public class BomListItemInfoAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<BOMListModel.GatherListBean> mData;

    private GlideImageLoader mImageLoader;


    private View locationView;


    public BomListItemInfoAdapter(Context context, List<BOMListModel.GatherListBean> data) {
        mContext = context;

        mInflater = LayoutInflater.from(context);
        mData = data;
        mImageLoader = new GlideImageLoader();

    }

    public void updata(List<BOMListModel.GatherListBean> data) {
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
            convertView = mInflater.inflate(R.layout.item_tp_bom_order_info_list, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.setData(mData.get(position));


        return convertView;
    }



    class ViewHolder {
        @BindView(R.id.iv_tuopan_kind)
        ImageView mIvTuopanKind;
        @BindView(R.id.tv_tuopan_name)
        TextView mTvTuopanName;
        @BindView(R.id.tv_tuopan_type)
        TextView mTvTuopanType;
        @BindView(R.id.tv_tuopan_num)
        TextView mTvTuopanNum;
        @BindView(R.id.rltwo)
        LinearLayout mRltwo;
        @BindView(R.id.iv_tuopan_kind_cargo)
        ImageView mIvTuopanKindCargo;
        @BindView(R.id.tv_tuopan_name_cargo)
        TextView mTvTuopanNameCargo;
        @BindView(R.id.tv_cargo_num)
        TextView mTvCargoNum;
        @BindView(R.id.tv_cargo_weight)
        TextView mTvCargoWeight;
        @BindView(R.id.tv_kuqu_num)
        TextView mTvKuquNum;
        @BindView(R.id.rlthree)
        LinearLayout mRlthree;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setData(final BOMListModel.GatherListBean singleData) {


            final String tmpImageUrl = singleData.getPalletImgPath1();


            mImageLoader.displayImage(mContext, tmpImageUrl, mIvTuopanKind, R.mipmap.icon_de_kind);

            mTvTuopanName.setText(singleData.getPalletName());//托盘名称

            mTvTuopanNum.setText("x" + singleData.getPalletCnt());//托盘数量
            mTvTuopanType.setText(singleData.getPalletModel());
            if (null != singleData.getGoods()) {
                mRlthree.setVisibility(View.VISIBLE);

                //货物
                final String tmpCargoImageUrl = singleData.getGoods().getGoodsImgUrl1();

                mImageLoader.displayImage(mContext, tmpCargoImageUrl, mIvTuopanKindCargo, R.mipmap.icon_de_kind);
                mTvTuopanNameCargo.setText(singleData.getGoods().getGoodsName() + " " + singleData.getGoods().getGoodsModel());//
                mTvCargoNum.setText("x" + singleData.getGoodsCnt());//货物数量
                mTvCargoWeight.setText(singleData.getGoodsWeight() + "kg");//货物重量
                mTvKuquNum.setText("库区 : "+singleData.getStorageAreaName());

            } else {
                mRlthree.setVisibility(View.GONE);


            }


        }
    }
}
