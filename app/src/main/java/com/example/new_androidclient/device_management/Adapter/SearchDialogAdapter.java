package com.example.new_androidclient.device_management.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.device_management.bean.SearchBean;

import java.util.ArrayList;
import java.util.List;

public class SearchDialogAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<SearchBean> listBeans;
    private Context context;
    private String type;

    public SearchDialogAdapter(Context context, List<SearchBean> telList, String type) {
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
        final SearchDialogAdapter.ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_search_dialog, parent, false);
            holder = new SearchDialogAdapter.ViewHolder();
            holder.textView = (convertView.findViewById(R.id.telItem));
            convertView.setTag(holder);
        } else {
            holder = (SearchDialogAdapter.ViewHolder) convertView.getTag();
        }

            notifyDataSetChanged();
        if (listBeans != null){
            if (type.equals("1")){

                holder.textView.setText(listBeans.get(position).getName());
            }
            if (type.equals("2")){
                holder.textView.setText(listBeans.get(position).getName());
            }
            if (type.equals("3")){
                holder.textView.setText(listBeans.get(position).getName());
            }
        }

        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}
