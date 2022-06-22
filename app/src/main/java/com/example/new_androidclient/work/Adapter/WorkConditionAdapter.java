package com.example.new_androidclient.work.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.device_management.bean.SearchBean;

import java.util.List;

public class WorkConditionAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List listBeans;
    private Context context;
    private String type;

    public WorkConditionAdapter(Context context, List telList, String type) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listBeans = telList;
        this.type = type;
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
        final WorkConditionAdapter.ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_search_dialog, parent, false);
            holder = new WorkConditionAdapter.ViewHolder();
            //   holder.textView = (convertView.findViewById(R.id.textTime));
            holder.textView = (convertView.findViewById(R.id.telItem));
            convertView.setTag(holder);
        } else {
            holder = (WorkConditionAdapter.ViewHolder) convertView.getTag();
        }
        notifyDataSetChanged();
        if (listBeans != null) {
            holder.textView.setText(listBeans.get(position).toString());
        }

        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}
