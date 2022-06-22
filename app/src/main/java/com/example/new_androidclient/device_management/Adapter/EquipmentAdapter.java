package com.example.new_androidclient.device_management.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.device_management.bean.FacilityBean;
import com.example.new_androidclient.device_management.bean.FindDeviceListBean;

import java.util.List;

public class EquipmentAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<FacilityBean> listBeans;
    private Context context;
    private int location;
    private String type;

    public EquipmentAdapter(Context context, List<FacilityBean> telList,String type) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.type = type;
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
        final EquipmentAdapter.ViewHolder holder;
        location = position;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.equipment_item, parent, false);
            holder = new EquipmentAdapter.ViewHolder();
            holder.textView1 = (convertView.findViewById(R.id.text));
            convertView.setTag(holder);
        } else {
            holder = (EquipmentAdapter.ViewHolder) convertView.getTag();
        }

            notifyDataSetChanged();

        if (listBeans != null){
            if (type.equals("0")){
                holder.textView1.setText(listBeans.get(position).getAffiliatedName());
            }
            if (type.equals("1")){
                holder.textView1.setText(listBeans.get(position).getAssociatedName().toString());
            }
        }

        return convertView;
    }

    static class ViewHolder {
        TextView textView1;
    }
}
