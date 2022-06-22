package com.example.new_androidclient.work.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.work.bean.CheckMessageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckMessageAdapter extends BaseAdapter {

    protected LayoutInflater mInflater;
    private List<CheckMessageBean> listBeans;
    private Context context;
    private Map<Integer,Boolean> map=new HashMap<>();

    public CheckMessageAdapter(Context context, List<CheckMessageBean> telList) {
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

//    public Switch getSwitch(int location){
//        return list.get(location);
//    }

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
        final CheckMessageAdapter.ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_check_message, parent, false);
            holder = new CheckMessageAdapter.ViewHolder();
            holder.textView = (convertView.findViewById(R.id.work_item));
            holder.checkBox = (convertView.findViewById(R.id.swh_status));
            convertView.setTag(holder);
        } else {
            holder = (CheckMessageAdapter.ViewHolder) convertView.getTag();
        }

        notifyDataSetChanged();

        holder.textView.setText(listBeans.get(position).getItemName());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    map.put(position,true);
                    ToastUtil.show(context,"ffff");
                }else {
                    map.remove(position);
                    ToastUtil.show(context,"1111");
                }
            }
        });

        if(map!=null&&map.containsKey(position)){
            holder.checkBox.setChecked(true);
        }else {
            holder.checkBox.setChecked(false);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView textView;
        Switch checkBox;
    }
}
