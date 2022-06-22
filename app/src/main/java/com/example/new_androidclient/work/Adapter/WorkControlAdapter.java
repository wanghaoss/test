package com.example.new_androidclient.work.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.work.bean.GasAnalysisBean;

import java.util.List;

public class WorkControlAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<String> listBeans;
    private Context context;

    public WorkControlAdapter(Context context, List<String> telList) {
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
        final WorkControlAdapter.ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_work_control, parent, false);
            holder = new WorkControlAdapter.ViewHolder();
            holder.textView1 = (convertView.findViewById(R.id.textTime));
            convertView.setTag(holder);
        } else {
            holder = (WorkControlAdapter.ViewHolder) convertView.getTag();
        }

        notifyDataSetChanged();

        holder.textView1.setText(listBeans.get(position));

        return convertView;
    }

    static class ViewHolder {
        TextView textView1;
    }
}
