package com.example.new_androidclient.work.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.work.bean.TreeListBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SafetyMeasuresAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<TreeListBean.ChildrenBeanX.ChildrenBean> listBeans;
    private Context context;

    private Map<Integer,Boolean> map=new HashMap<>();// 存放已被选中的CheckBox

    public SafetyMeasuresAdapter(Context context, List<TreeListBean.ChildrenBeanX.ChildrenBean> treeList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listBeans = treeList;
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
        final SafetyMeasuresAdapter.ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_safety_measures, parent, false);
            holder = new SafetyMeasuresAdapter.ViewHolder();
            holder.textView = (convertView.findViewById(R.id.tv_content));
            holder.checkBox = (convertView.findViewById(R.id.checkBox));
            convertView.setTag(holder);
        } else {
            holder = (SafetyMeasuresAdapter.ViewHolder) convertView.getTag();
        }

        notifyDataSetChanged();

        holder.textView.setText(listBeans.get(position).getLabel());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    map.put(position,true);
                }else {
                    map.remove(position);
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
        CheckBox checkBox;
    }
}
