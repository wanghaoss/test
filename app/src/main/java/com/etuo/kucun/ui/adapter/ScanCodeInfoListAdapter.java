package com.etuo.kucun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.model.ScanTpInfoCodeModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class ScanCodeInfoListAdapter extends BaseAdapter {

    private List<ScanTpInfoCodeModel> mScanTpInfoCodeModels;
    private LayoutInflater inflater;

    private OnClickBtItem onClickBtItem;
    public ScanCodeInfoListAdapter(Context mContext, List<ScanTpInfoCodeModel> cityEntities) {
        this.mScanTpInfoCodeModels = cityEntities;
        inflater = LayoutInflater.from(mContext);

    }

    public void  upData(List<ScanTpInfoCodeModel> cityEntities){
        this.mScanTpInfoCodeModels = cityEntities;
    }



    //接口回调
    public interface OnClickBtItem {
        void myOrderDeletClick(int position, String palletNum);
    }

    public void OnClickBtItem(OnClickBtItem onClickBtItem) {
        this.onClickBtItem = onClickBtItem;
    }
    @Override
    public int getCount() {
        return mScanTpInfoCodeModels == null ? 0 : mScanTpInfoCodeModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mScanTpInfoCodeModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {

            convertView = inflater.inflate(R.layout.item_scan_code_info, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
       final ScanTpInfoCodeModel cityEntity = mScanTpInfoCodeModels.get(position);
        holder.mTvKindType.setText(cityEntity.getPalletModel());
        holder.mTvBatchNum.setText(cityEntity.getPalletNum());

        holder.mTvSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtItem.myOrderDeletClick(position,cityEntity.getPalletNum());
            }
        });

        return convertView;
    }



    class ViewHolder {
        @BindView(R.id.tv_subtract)
        TextView mTvSubtract;
        @BindView(R.id.tv_kind_type)
        TextView mTvKindType;
        @BindView(R.id.tv_batch_num)
        TextView mTvBatchNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

