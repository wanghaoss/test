package com.example.new_androidclient.device_management.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.device_management.bean.FindDeviceListBean;
import com.example.new_androidclient.device_management.bean.SearchBean;

import java.util.List;

public class DeviceInfoAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<FindDeviceListBean> listBeans;
    private Context context;
    private int location;

    public DeviceInfoAdapter(Context context, List<FindDeviceListBean> telList) {
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

    public void setSelectPosition(int location) {
        this.location = location;
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
        final DeviceInfoAdapter.ViewHolder holder;
        location = position;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.device_info_item, parent, false);
            holder = new DeviceInfoAdapter.ViewHolder();
            holder.textView1 = (convertView.findViewById(R.id.item1));
            holder.textView2 = (convertView.findViewById(R.id.item2));
            holder.textView3 = (convertView.findViewById(R.id.item3));
            holder.textView4 = (convertView.findViewById(R.id.item4));
            holder.textView5 = (convertView.findViewById(R.id.item5));
            convertView.setTag(holder);
        } else {
            holder = (DeviceInfoAdapter.ViewHolder) convertView.getTag();
        }

            notifyDataSetChanged();
        if (listBeans != null){
            holder.textView1.setText(listBeans.get(position).getDptName());
            holder.textView2.setText(listBeans.get(position).getInstName());
            holder.textView3.setText(listBeans.get(position).getDeviceNo());
            if (listBeans.get(position).getDeviceCatName() != null){
                holder.textView4.setText(listBeans.get(position).getDeviceCatName());
            }else {
                holder.textView4.setText("-");
            }

            holder.textView5.setText(listBeans.get(position).getDeviceName());

        }

        return convertView;
    }

    static class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
    }
}
