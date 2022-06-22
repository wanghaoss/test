package com.etuo.kucun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.model.MassageListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 消息列表adapter
 * Created by xfy on 2018/11/21.
 */

public class MassageListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<MassageListModel> mData;

    public MassageListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void updata(List<MassageListModel> data) {
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
            convertView = mInflater.inflate(R.layout.item_massage, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.setData(position, mData.get(position));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_TZ)
        TextView tvTZ;
        @BindView(R.id.tv_contents)
        TextView tvContents;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setData(final int positions, final MassageListModel singleData) {
            tvTime.setText(singleData.getInsDate());
            tvContents.setText(singleData.getContents());
            tvTZ.setText(singleData.getSubject());
        }
    }
}
