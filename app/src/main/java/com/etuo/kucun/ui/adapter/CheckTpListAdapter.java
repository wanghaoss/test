package com.etuo.kucun.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.loader.GlideImageLoader;
import com.etuo.kucun.model.TpCheckListModel;
import com.etuo.kucun.ui.activity.TpCheckDetailsActivity;
import com.etuo.kucun.view.NoScrollListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yhn on 2020/02/10
 * 托盘验收等列表 第一层
 */
public class CheckTpListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<TpCheckListModel> mData;

    private GlideImageLoader mImageLoader;
    private CheckTpListItemInfoAdapter adapter;

    private String fromType;


    public CheckTpListAdapter(Context context, List<TpCheckListModel> data, String fromType) {
        mContext = context;

        mInflater = LayoutInflater.from(context);
        mData = data;
        mImageLoader = new GlideImageLoader();
        this.fromType = fromType;

    }

    public void updata(List<TpCheckListModel> data) {
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
            convertView = mInflater.inflate(R.layout.item_check_tp_list, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.setData(mData.get(position));


        return convertView;
    }


    /**
     * 验收单状态 0待验收 1验收中 2已验收
     *
     * @param status
     * @return
     */
    private String getStatus(String status) {
        if ("0".equals(status)) {

            return "待验收";
        } else if ("1".equals(status)) {
            return "验收中";
        } else if ("2".equals(status)) {
            return "已验收";
        }
        return "- -";
    }

    class ViewHolder {
        @BindView(R.id.tv_order_branch_name)
        TextView mTvOrderBranchName;
        @BindView(R.id.iv_status_by_order)
        TextView mIvStatusByOrder;
        @BindView(R.id.my_no_listview)
        NoScrollListView mMyNoListview;
        @BindView(R.id.tv_order_serial_number)
        TextView mTvOrderSerialNumber;
        @BindView(R.id.bt_order_status)
        TextView mBtOrderStatus;
        @BindView(R.id.l_item_info)
        LinearLayout mLItemInfo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setData(final TpCheckListModel singleData) {


            mIvStatusByOrder.setText(getStatus(singleData.getStatus()));//状态

            if ("0".equals(singleData.getStatus())) {

                mBtOrderStatus.setVisibility(View.VISIBLE);
            } else {
                mBtOrderStatus.setVisibility(View.GONE);
            }

            if (ExtraConfig.IntentType.FROM_INTENT_BY_SHOU.equals(fromType)) {
                mTvOrderSerialNumber.setText("订单编号: " + singleData.getOrderNo());
                mBtOrderStatus.setText("验收");
            }


            mBtOrderStatus.setOnClickListener(new View.OnClickListener() {//点击按钮
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, TpCheckDetailsActivity.class);
                    intent.putExtra(ExtraConfig.TypeCode.INTENT_ORDER_ID, singleData.getPalletCheckId());
                    intent.putExtra(ExtraConfig.TypeCode.INTENT_ORDER_NUM, singleData.getOrderNo());
                    intent.putExtra(ExtraConfig.TypeCode.FROM_INTTENT, fromType);
                    mContext.startActivity(intent);
                }
            });


            adapter = new CheckTpListItemInfoAdapter(mContext, singleData.getPalletModelList(), fromType);
            mMyNoListview.setAdapter(adapter);


        }
    }
}
