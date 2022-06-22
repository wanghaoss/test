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
import com.etuo.kucun.model.BuyCheckDetailsModel;
import com.etuo.kucun.model.BuyInDetailsModel;
import com.etuo.kucun.view.CustomListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 采购入库详情 第一层
 * Created by yhn on 2020/02/07.
 */

public class GoodsBuyInDetailsAdapter extends BaseAdapter {

    public static Context mContext;
    private LayoutInflater mInflater;
    private List<BuyInDetailsModel.GatherListBean> mData;
    private GlideImageLoader mImageLoader;
    public static GoodsBuyInDetailsInListAdapter mAdapter ;
    private String mType;
    private OnClickBtItem onClickBtItem;


    public GoodsBuyInDetailsAdapter(Context context, String type) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mImageLoader = new GlideImageLoader();
        mType = type;
    }

    //接口回调
    public interface OnClickBtItem {
        void myOrderOkClick(int position, BuyInDetailsModel.GatherListBean.DetailListBean singleData);
        void myOrderCancelClick(int position, BuyInDetailsModel.GatherListBean.DetailListBean singleData);
    }

    public void OnClickBtItem(OnClickBtItem onClickBtItem) {
        this.onClickBtItem = onClickBtItem;
    }
    public void updata(List<BuyInDetailsModel.GatherListBean> data) {
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
            convertView = mInflater.inflate(R.layout.item_tp_bom, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.setData(position, mData.get(position));
        return convertView;
    }




    class ViewHolder {
        @BindView(R.id.iv_tuopan_kind)
        ImageView mIvTuopanKind;
        @BindView(R.id.tv_tuopan_name)
        TextView mTvTuopanName;
        @BindView(R.id.tv_tuopan_jingzai)
        TextView mTvTuopanJingzai;
        @BindView(R.id.tv_tp_out_num_name)
        TextView mTvTpOutNumName;
        @BindView(R.id.tv_tuopan_yanshou)
        TextView mTvTuopanYanshou;
        @BindView(R.id.tv_tuopan_dongzai)
        TextView mTvTuopanDongzai;
        @BindView(R.id.tv_tuopan_baoxiu_name)
        TextView mTvTuopanBaoxiuName;
        @BindView(R.id.tv_tuopan_baoxiu)
        TextView mTvTuopanBaoxiu;
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
        @BindView(R.id.tv_out_name_by_num)
        TextView mTvOutNameByNum;
        @BindView(R.id.tv_out_num)
        TextView mTvOutNum;
        @BindView(R.id.tv_out_name_by_weight)
        TextView mTvOutNameByWeight;
        @BindView(R.id.tv_out_weight)
        TextView mTvOutWeight;
        @BindView(R.id.tv_cargo_num)
        TextView mTvCargoNum;
        @BindView(R.id.tv_cargo_weight)
        TextView mTvCargoWeight;
        @BindView(R.id.rlthree)
        LinearLayout mRlthree;
        @BindView(R.id.tv_tpNO)
        TextView mTvTpNO;
        @BindView(R.id.tv_tpOrCon)
        TextView mTvTpOrCon;
        @BindView(R.id.tv_tp_fenqu)
        TextView mTvTpFenqu;
        @BindView(R.id.lv_tpNumList)
        CustomListView mLvTpNumList;
        @BindView(R.id.llfour)
        LinearLayout mLlfour;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setData(final int positions, final  BuyInDetailsModel.GatherListBean singleData) {

            final String tmpImageUrl = singleData.getPalletImgPath1();


            mImageLoader.displayImage(mContext, tmpImageUrl, mIvTuopanKind, R.mipmap.icon_de_kind);

            mTvTuopanName.setText(singleData.getPalletName());//托盘名称
            mTvTuopanJingzai.setText("静载: " + singleData.getStaticLoad() + "T");//静载重量
            mTvTuopanDongzai.setText("动载: " + singleData.getDynamicLoad() + "T");//动载重量
            mTvTuopanNum.setText("x" + singleData.getPalletCnt());//托盘数量
            mTvTuopanType.setText(singleData.getPalletModel());
            mTvTpFenqu.setVisibility(View.GONE);
            mTvTpOutNumName.setText("入库托数: ");
                mTvOutNameByNum.setText("验收数量: ");
                mTvTuopanBaoxiuName.setText("异常托数: ");

            mTvTuopanYanshou.setText(singleData.getEndCnt() +"");
            mTvTuopanBaoxiu.setText(singleData.getUnusualCnt() +"");


            if (null != singleData.getGoods()) {
                mRlthree.setVisibility(View.VISIBLE);
                //货物
                final String tmpCargoImageUrl = singleData.getGoods().getGoodsImgPath1();
                mImageLoader.displayImage(mContext, tmpCargoImageUrl, mIvTuopanKindCargo, R.mipmap.icon_de_kind);
                mTvTuopanNameCargo.setText(singleData.getGoods().getGoodsName() + " " + singleData.getGoods().getGoodsModel());//
                mTvCargoNum.setText("x" + singleData.getGoodsCnt());//货物数量
                mTvCargoWeight.setText(singleData.getGoodsWeight() + "kg");//货物重量


//                mTvOutNum.setText(singleData.getChooseGoodsCnt()+"");
//                mTvOutWeight.setText(singleData.getChooseGoodsWeight()  +"kg");

                mTvOutNum.setText(singleData.getEndGoodsCnt()+"");
                mTvOutWeight.setText(singleData.getEndGoodsWeight()  +"kg");

            } else {
                mRlthree.setVisibility(View.GONE);


            }

            mAdapter = new GoodsBuyInDetailsInListAdapter(mContext, mData.get(positions).getDetailList(),mType);
            mLvTpNumList.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();





            mAdapter.OnClickBtItem(new GoodsBuyInDetailsInListAdapter.OnClickBtItem() {
                @Override
                public void myOrderOkClick(int position, BuyInDetailsModel.GatherListBean.DetailListBean singleData) {
                    onClickBtItem.myOrderOkClick(positions,singleData);
                }

                @Override
                public void myOrderCancelClick(int position, BuyInDetailsModel.GatherListBean.DetailListBean singleData) {
                    onClickBtItem.myOrderCancelClick(positions,singleData);
                }
            });


        }
    }
}
