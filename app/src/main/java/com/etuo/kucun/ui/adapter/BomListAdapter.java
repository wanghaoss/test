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
import com.etuo.kucun.model.BOMListModel;
import com.etuo.kucun.ui.activity.TpStockUpDetailsActivity;
import com.etuo.kucun.view.NoScrollListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yhn on 2020/02/10
 * 托盘 BOM 备货等列表 第一层
 */
public class BomListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<BOMListModel> mData;

    private GlideImageLoader mImageLoader;
    private BomListItemInfoAdapter adapter;


    private View locationView;
    private String fromType;


    public BomListAdapter(Context context, List<BOMListModel> data,String fromType) {
        mContext = context;

        mInflater = LayoutInflater.from(context);
        mData = data;
        mImageLoader = new GlideImageLoader();
        this.fromType = fromType;

    }

    public void updata(List<BOMListModel> data) {
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
            convertView = mInflater.inflate(R.layout.item_order_list, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.setData(mData.get(position));


        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.tv_order_time)
        TextView tv_order_time;
        @BindView(R.id.iv_status_by_order)
        TextView ivStatusByOrder;
        @BindView(R.id.my_no_listview)
        NoScrollListView myNoListview;
        @BindView(R.id.tv_order_serial_number)
        TextView tvOrderSerialNumber;
        @BindView(R.id.bt_order_status)
        TextView btOrderStatus;
        @BindView(R.id.l_item_info)
        LinearLayout lItemInfo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setData(final BOMListModel singleData) {



            ivStatusByOrder.setText(getStatus(singleData.getStatus()));//状态
            tv_order_time.setText(singleData.getUpdDate());

            if ("0".equals(singleData.getStatus())){

                btOrderStatus.setVisibility(View.VISIBLE);
            }else {
                btOrderStatus.setVisibility(View.GONE);
            }

            if (ExtraConfig.IntentType.FROM_INTENT_BY_BEIHUO.equals(fromType)){
                tvOrderSerialNumber.setText("备货单号: "+singleData.getPrepareNo());
                btOrderStatus.setText("备货");
            }


            btOrderStatus.setOnClickListener(new View.OnClickListener() {//点击按钮
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, TpStockUpDetailsActivity.class);
                    intent.putExtra(ExtraConfig.TypeCode.INTENT_ORDER_ID,singleData.getPrepareId());

                    intent.putExtra(ExtraConfig.TypeCode.FROM_INTTENT,fromType);
                    mContext.startActivity(intent);
                }
            });


            adapter = new BomListItemInfoAdapter(mContext,singleData.getGatherList());
            myNoListview.setAdapter(adapter);


        }
    }

    /**
     * 0待备货 1备货中 2已备货
     * @param status
     * @return
     */
    private String getStatus(String status){
        if ("0".equals(status)){

            return "待备货";
        }else if ("1".equals(status)){
            return "备货中";
        }else if ("2".equals(status)){
            return "已备货";
        }
        return "- -";
    }
}
