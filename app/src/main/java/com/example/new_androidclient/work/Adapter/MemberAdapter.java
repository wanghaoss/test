package com.example.new_androidclient.work.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.device_management.bean.DeviceAnalysisDeviceListBean;

import java.util.List;

public class MemberAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<DeviceAnalysisDeviceListBean> listBeans;
    private Context context;

    public MemberAdapter(Context context, List<DeviceAnalysisDeviceListBean> telList) {
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
        final MemberAdapter.ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_lv_multiple_choice, parent, false);
            holder = new MemberAdapter.ViewHolder();
            holder.textView = (convertView.findViewById(R.id.tv_content));
            convertView.setTag(holder);
        } else {
            holder = (MemberAdapter.ViewHolder) convertView.getTag();
        }

        notifyDataSetChanged();
        String name1 = listBeans.get(position).getLastName();
        String name2 = listBeans.get(position).getFirstName();
        String name = name1+name2;

        holder.textView.setText(name);

        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}
