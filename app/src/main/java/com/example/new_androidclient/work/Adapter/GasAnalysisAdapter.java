package com.example.new_androidclient.work.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.work.bean.GasAnalysisBean;
import com.example.new_androidclient.work.bean.HotWorkBean;

import java.util.List;

public class GasAnalysisAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<GasAnalysisBean> listBeans;
    private Context context;

    public GasAnalysisAdapter(Context context, List<GasAnalysisBean> telList) {
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
        final GasAnalysisAdapter.ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_gas_analysis, parent, false);
            holder = new GasAnalysisAdapter.ViewHolder();
            holder.textView1 = (convertView.findViewById(R.id.text1));
            holder.textView2 = (convertView.findViewById(R.id.text2));
            holder.textView3 = (convertView.findViewById(R.id.text3));
            holder.textView4 = (convertView.findViewById(R.id.text4));
            convertView.setTag(holder);
        } else {
            holder = (GasAnalysisAdapter.ViewHolder) convertView.getTag();
        }

        notifyDataSetChanged();

//        holder.textView1.setText(listBeans.get(position).getSiteName());
//        holder.textView2.setText(listBeans.get(position).getAnalysisValue());
        holder.textView3.setText(listBeans.get(position).getAnalysisTime().toString());
        holder.textView4.setText(listBeans.get(position).getAnalysisUser());

        return convertView;
    }

    static class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        TextView textView7;
    }
}
