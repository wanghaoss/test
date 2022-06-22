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

public class AboutWorkAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<TreeListBean> listBeans;
    private Context context;

    public AboutWorkAdapter(Context context, List<TreeListBean> treeList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listBeans = treeList;
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
        final AboutWorkAdapter.ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_about_work, parent, false);
            holder = new AboutWorkAdapter.ViewHolder();
            holder.textView = (convertView.findViewById(R.id.tv_content));
            convertView.setTag(holder);
        } else {
            holder = (AboutWorkAdapter.ViewHolder) convertView.getTag();
        }

        notifyDataSetChanged();

        holder.textView.setText(listBeans.get(position).getLabel());


        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}
