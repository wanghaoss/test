package com.example.new_androidclient.device_management.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.device_management.bean.FindDeviceListBean;
import com.example.new_androidclient.device_management.bean.SealPointBean;

import java.util.List;

public class SealPointAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<SealPointBean> listBeans;
    private Context context;
    private int location;

    public SealPointAdapter(Context context, List<SealPointBean> telList) {
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
        final SealPointAdapter.ViewHolder holder;
        location = position;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.seal_point_item, parent, false);
            holder = new SealPointAdapter.ViewHolder();
            holder.textView1 = (convertView.findViewById(R.id.numberText));
            holder.textView2 = (convertView.findViewById(R.id.nameText));
            holder.textView3 = (convertView.findViewById(R.id.typeText));
            holder.textView4 = (convertView.findViewById(R.id.genreText));
            convertView.setTag(holder);
        } else {
            holder = (SealPointAdapter.ViewHolder) convertView.getTag();
        }

            notifyDataSetChanged();
        if (listBeans != null){
            if (listBeans.get(position).getPointNo().equals("null")){
                holder.textView1.setText("-");
            }else {
                holder.textView1.setText(listBeans.get(position).getPointNo());
            }

            if (listBeans.get(position).getPointName().equals("null")){
                holder.textView2.setText("-");
            }else {
                holder.textView2.setText(listBeans.get(position).getPointName());
            }

            if (listBeans.get(position).getPointType().equals("null")){
                holder.textView3.setText("-");
            }else {
                if (listBeans.get(position).getPointType().equals("0")){
                    holder.textView3.setText("静密封");
                }else {
                    holder.textView3.setText("动密封");
                }
            }

            if (listBeans.get(position).getPointStructure() == null){
                holder.textView4.setText("-");
            }else {
                if (listBeans.get(position).getPointStructure().equals("2")){
                    holder.textView4.setText("突面");
                }
                if (listBeans.get(position).getPointStructure().equals("3")){
                    holder.textView4.setText("榫面");
                }
                if (listBeans.get(position).getPointStructure().equals("4")){
                    holder.textView4.setText("槽面");
                }
                if (listBeans.get(position).getPointStructure().equals("5")){
                    holder.textView4.setText("凸面");
                }
                if (listBeans.get(position).getPointStructure().equals("6")){
                    holder.textView4.setText("凹面");
                }
                if (listBeans.get(position).getPointStructure().equals("7")){
                    holder.textView4.setText("环连接面");
                }
                if (listBeans.get(position).getPointStructure().equals("8")){
                    holder.textView4.setText("丝扣");
                }
                if (listBeans.get(position).getPointStructure().equals("9")){
                    holder.textView4.setText("角法兰");
                }
                if (listBeans.get(position).getPointStructure().equals("10")){
                    holder.textView4.setText("格兰");
                }
                if (listBeans.get(position).getPointStructure().equals("11")){
                    holder.textView4.setText("卡套");
                }
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
