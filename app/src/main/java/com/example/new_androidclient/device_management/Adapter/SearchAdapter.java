package com.example.new_androidclient.device_management.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.new_androidclient.R;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private ArrayList list;
    private String type;

    public SearchAdapter(Context context, ArrayList list,String type){
        this.context = context;
        this.list = list;
        this.type = type;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
//        @SuppressLint("ViewHolder") View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_adapter, viewGroup,false);
//        TextView txt_name = item.findViewById(R.id.txt_name);
//        if (type.equals("1")){
//            txt_name.setText((CharSequence) list.get(position));
//        }
//        return item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.search_adapter, null);
        if(convertView!=null){
            TextView textView1 = convertView.findViewById(R.id.txt_name);
            if (type.equals("1")){
                textView1.setText(list.get(position).toString());
            }
        }
        return convertView;
    }
}
