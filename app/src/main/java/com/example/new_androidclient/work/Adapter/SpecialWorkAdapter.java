package com.example.new_androidclient.work.Adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.device_management.bean.DeviceAnalysisDeviceListBean;
import com.example.new_androidclient.work.bean.SpecialWorkBean;

import java.util.List;

public class SpecialWorkAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<SpecialWorkBean> listBeans;
    private Context context;

    private int location;

    public SpecialWorkAdapter(Context context, List<SpecialWorkBean> telList) {
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
        final SpecialWorkAdapter.ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_lv_multiple_choice, parent, false);
            holder = new SpecialWorkAdapter.ViewHolder();
            holder.textView = (convertView.findViewById(R.id.tv_content));
            convertView.setTag(holder);
        } else {
            holder = (SpecialWorkAdapter.ViewHolder) convertView.getTag();
        }

        notifyDataSetChanged();
        String name1 = listBeans.get(position).getName();

        holder.textView.setText(name1);

        if (location == position){

        }

        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}
