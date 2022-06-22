package com.example.new_androidclient.work.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.work.bean.AllowCloseBean;
import com.example.new_androidclient.work.bean.CheckMessageBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckBoxAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<AllowCloseBean> listBeans;
    private Context context;
    private SubClickListener subClickListener;

    public CheckBoxAdapter(Context context, List<AllowCloseBean> telList) {
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

    public void setsubClickListener(SubClickListener topicClickListener) {
        this.subClickListener = topicClickListener;
    }

    public interface SubClickListener {
        void OntopicClickListener(String detail, int position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CheckBoxAdapter.ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_check, parent, false);
            holder = new CheckBoxAdapter.ViewHolder();
            holder.checkBox = (convertView.findViewById(R.id.swh_status));
            convertView.setTag(holder);
        } else {
            holder = (CheckBoxAdapter.ViewHolder) convertView.getTag();
        }

        notifyDataSetChanged();

        holder.checkBox.setText(listBeans.get(position).getItemname());

        if (holder.checkBox.isChecked()){
            subClickListener.OntopicClickListener(listBeans.get(position).getItemname(),position);
        }

        return convertView;
    }

    static class ViewHolder {
        CheckBox checkBox;
    }
}
