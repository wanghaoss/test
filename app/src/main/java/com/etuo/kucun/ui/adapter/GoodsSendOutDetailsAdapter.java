package com.etuo.kucun.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.loader.GlideImageLoader;
import com.etuo.kucun.model.BomDetailsModel;
import com.etuo.kucun.ui.activity.WebServiceViewActivity;
import com.etuo.kucun.utils.UrlTools;
import com.etuo.kucun.view.CustomListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发货详情 第一层
 * Created by yhn on 2020/02/07.
 */

public class GoodsSendOutDetailsAdapter extends BaseAdapter {

    public static Context mContext;
    private LayoutInflater mInflater;
    private List<BomDetailsModel.GatherListBean> mData;
    private GlideImageLoader mImageLoader;
    public static GoodsSendOutDetailsInListAdapter mAdapter ;
    private String mType;
    private OnClickBtItem onClickBtItem;


    public GoodsSendOutDetailsAdapter(Context context, String type) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mImageLoader = new GlideImageLoader();
        mType = type;
    }

    //接口回调
    public interface OnClickBtItem {
        void myOrderOkClick(int position, BomDetailsModel.GatherListBean.DetailListBean singleData);
        void myOrderCancelClick(int position, BomDetailsModel.GatherListBean.DetailListBean singleData);
    }

    public void OnClickBtItem(OnClickBtItem onClickBtItem) {
        this.onClickBtItem = onClickBtItem;
    }
    public void updata(List<BomDetailsModel.GatherListBean> data) {
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
            convertView = mInflater.inflate(R.layout.item_goods_send_out, null);
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

        void setData(final int positions, final BomDetailsModel.GatherListBean singleData) {

            final String tmpImageUrl = singleData.getPalletImgPath1();


            mImageLoader.displayImage(mContext, tmpImageUrl, mIvTuopanKind, R.mipmap.icon_de_kind);

            mTvTuopanName.setText(singleData.getPalletName() + singleData.getPalletSpec());//托盘名称
            mTvTuopanJingzai.setText("静载: " + singleData.getStaticLoad() + "T");//静载重量
            mTvTuopanDongzai.setText("动载: " + singleData.getDynamicLoad() + "T");//动载重量
            mTvTuopanNum.setText("x" + singleData.getPalletCnt());//托盘数量
            mTvTuopanType.setText(singleData.getPalletModel());

                mTvOutNameByNum.setText("发货数量: ");
                mTvTuopanBaoxiuName.setText("异常托数: ");
                mTvTpOutNumName.setText("发货托数: ");

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

                mTvOutNum.setText(singleData.getEndGoodsCnt()+"");
                mTvOutWeight.setText(singleData.getEndGoodsWeight()  +"kg");
                //点击进入查看货物详情
                mRlthree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, WebServiceViewActivity.class);
                        intent.putExtra("LoadingUrl", UrlTools.getInterfaceUrl(UrlTools.getGoodsInfo(singleData.getGoodsId())));
                        intent.putExtra("title", "货物详情");
                        mContext.startActivity(intent);
                    }
                });

            } else {
                mRlthree.setVisibility(View.GONE);


            }

            mAdapter = new GoodsSendOutDetailsInListAdapter(mContext, mData.get(positions).getDetailList(),mType);
            mLvTpNumList.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mAdapter.OnClickBtItem(new GoodsSendOutDetailsInListAdapter.OnClickBtItem() {
                @Override
                public void myOrderOkClick(int position, BomDetailsModel.GatherListBean.DetailListBean singleData) {
                 onClickBtItem.myOrderOkClick(position,singleData);
                }

                @Override
                public void myOrderCancelClick(int position, BomDetailsModel.GatherListBean.DetailListBean singleData) {
                    onClickBtItem.myOrderCancelClick(position,singleData);
                }
            });

        }
    }
}