package com.example.new_androidclient.work.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.work.bean.TreeListBean;

import java.util.List;

public class WorkRiskThreeAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<TreeListBean.ChildrenBeanX.ChildrenBean> listBeans;
    private Context context;

    private int location;

    public WorkRiskThreeAdapter(Context context, List<TreeListBean.ChildrenBeanX.ChildrenBean> telList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listBeans = telList;
    }

    public void setSelectPosition(int location) {
        this.location = location;
    }

    @Override
    public int getCount() {
        int size = listBeans.size();
        if (size > 0) {
            return size >= 1000 ? 1000 : size;
        } else {
            return listBeans != null ? listBeans.size() : 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return listBeans != null ? listBeans.get(position) : "";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final WorkRiskThreeAdapter.ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_lv_multiple_choice, parent, false);
            holder = new WorkRiskThreeAdapter.ViewHolder();
            holder.textView = (convertView.findViewById(R.id.tv_content));
            convertView.setTag(holder);
        } else {
            holder = (WorkRiskThreeAdapter.ViewHolder) convertView.getTag();
        }

        notifyDataSetChanged();
        String name1 = listBeans.get(position).getLabel();

        holder.textView.setText(name1);

        if (location == position){

        }

        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}
