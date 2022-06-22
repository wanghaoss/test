package com.example.new_androidclient.work.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.work.bean.HotWorkBean;

import java.util.List;

public class HotWorkAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<HotWorkBean> listBeans;
    private Context context;

    public HotWorkAdapter(Context context, List<HotWorkBean> telList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listBeans = telList;
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
        final HotWorkAdapter.ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_hot_work, parent, false);
            holder = new HotWorkAdapter.ViewHolder();
            holder.textView1 = (convertView.findViewById(R.id.text1));
            holder.textView2 = (convertView.findViewById(R.id.text2));
            holder.textView3 = (convertView.findViewById(R.id.text3));
            convertView.setTag(holder);
        } else {
            holder = (HotWorkAdapter.ViewHolder) convertView.getTag();
        }

        notifyDataSetChanged();

        holder.textView1.setText(listBeans.get(position).getSiteName());
        holder.textView2.setText(listBeans.get(position).getAnalysisResult());
        holder.textView3.setText(listBeans.get(position).getAnalysisTime());

        return convertView;
    }

    static class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
    }
}
