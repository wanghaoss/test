package com.example.new_androidclient.device_management.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.device_management.bean.DeviceSpecBean;

import java.util.List;

public class OperatingParametersAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<DeviceSpecBean.Parm1Bean> listBeans;
    private Context context;
    private int location;

    public OperatingParametersAdapter(Context context, List<DeviceSpecBean.Parm1Bean> telList) {
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
        final OperatingParametersAdapter.ViewHolder holder;
        location = position;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.operating_parameters_item, parent, false);
            holder = new OperatingParametersAdapter.ViewHolder();
            holder.textView1 = (convertView.findViewById(R.id.nameText));
            holder.textView2 = (convertView.findViewById(R.id.unitText));
            holder.textView3 = (convertView.findViewById(R.id.conditionText));
            holder.textView4 = (convertView.findViewById(R.id.pipText));
            convertView.setTag(holder);
        } else {
            holder = (OperatingParametersAdapter.ViewHolder) convertView.getTag();
        }

        notifyDataSetChanged();
        if (listBeans != null){
            if (listBeans.get(position).getAssetspecName() == null || TextUtils.isEmpty(listBeans.get(position).getAssetspecName())){
                holder.textView1.setText("-");
            }else {
                holder.textView1.setText(listBeans.get(position).getAssetspecName());
            }

            if (listBeans.get(position).getAssetspecUnit() == null || TextUtils.isEmpty(listBeans.get(position).getAssetspecUnit())){
                holder.textView2.setText("-");
            }else {
                holder.textView2.setText(listBeans.get(position).getAssetspecUnit());
            }

            if (listBeans.get(position).getAssetspecConditions() == null || TextUtils.isEmpty(listBeans.get(position).getAssetspecConditions().toString())){
                holder.textView3.setText("-");
            }else {
                holder.textView3.setText(listBeans.get(position).getAssetspecConditions().toString());
            }

            if (listBeans.get(position).getAssetspecData() == null || TextUtils.isEmpty(listBeans.get(position).getAssetspecData().toString())){
                holder.textView4.setText("-");
            }else {
                holder.textView4.setText(listBeans.get(position).getAssetspecData().toString());
            }

        }

        return convertView;
    }

    static class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
    }
}
