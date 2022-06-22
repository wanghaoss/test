package com.etuo.kucun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.loader.GlideImageLoader;
import com.etuo.kucun.model.StockDetailsListModel;
import com.etuo.kucun.utils.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yhn on 2018/8/7.
 * 托盘列表adapte 出库 入库
 */
public class PalletInfoListByStockPlanAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<StockDetailsListModel> mData;

    private GlideImageLoader mImageLoader;


    private View locationView;


    public PalletInfoListByStockPlanAdapter(Context context, List<StockDetailsListModel> data) {
        mContext = context;

        mInflater = LayoutInflater.from(context);
        mData = data;
        mImageLoader = new GlideImageLoader();

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
            convertView = mInflater.inflate(R.layout.item_tuopan_info_num, null);
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
        @BindView(R.id.tv_tuopan_kind)
        TextView mTvTuopanKind;
        @BindView(R.id.tv_tuopan_Deposit)
        TextView mTvTuopanDeposit;
        @BindView(R.id.tv_tuopan_rent)
        TextView mTvTuopanRent;
        @BindView(R.id.tv_tuopan_num)
        TextView mTvTuopanNum;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setData(final StockDetailsListModel singleData) {


            final String tmpImageUrl = singleData.getimg();


            mImageLoader.displayImage(mContext, tmpImageUrl, mIvTuopanKind, R.mipmap.icon_de_kind);

            mTvTuopanName.setText(singleData.getPalletName());//托盘名称
            mTvTuopanKind.setText("型号: " + singleData.getPalletModel());//托盘类型
            mTvTuopanDeposit.setText("押金: " + singleData.getBond() + StringUtil.getSecurityMoney(singleData.getProductType()));
            mTvTuopanRent.setText("租金: " + singleData.getDailyrents() + StringUtil.getRentMoney(singleData.getProductType()));
            mTvTuopanNum.setText("x" + singleData.getQuantity());//托盘数量


        }
    }
}
