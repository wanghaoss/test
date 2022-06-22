package com.etuo.kucun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.model.common.ReturnReasonListBean;
import com.etuo.kucun.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * pop里不通过原因列表
 * Created by yhn on 2020/02/07.
 */

public class CheckReturnListAdapter extends BaseAdapter {
    public static Context mContext;
    private LayoutInflater mInflater;
    private List<ReturnReasonListBean> mData;

    private OnClickBtItem onClickBtItem;

    public CheckReturnListAdapter(Context context, List<ReturnReasonListBean> data) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;

    }
    public void updata(List<ReturnReasonListBean> data) {
        mData = data;
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
            convertView = mInflater.inflate(R.layout.check_return_item, null);
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
        void myItemClick(int position, ReturnReasonListBean singleData);

    }

    public void OnClickBtItem(OnClickBtItem onClickBtItem) {
        this.onClickBtItem = onClickBtItem;
    }


    class ViewHolder {
        @BindView(R.id.tv_text)
        TextView mTvText;
        @BindView(R.id.is_check)
        CheckBox mIsCheck;
        @BindView(R.id.ll_item)
        LinearLayout mLlItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setData(final int positions, final ReturnReasonListBean singleData) {

            mTvText.setText(singleData.getName());
            if (singleData.isCheck()) {
                mIsCheck.setChecked(true);
            } else {
                mIsCheck.setChecked(false);

            }
            mLlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LogUtil.d("dianji  " + positions);
                    onClickBtItem.myItemClick(positions,singleData);
                }
            });


        }
    }
}
