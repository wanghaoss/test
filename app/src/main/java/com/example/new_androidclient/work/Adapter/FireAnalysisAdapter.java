package com.example.new_androidclient.work.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.work.bean.HotWorkBean;
import com.example.new_androidclient.work.data.OnRecyclerItemClickListenerHotWork;
import com.example.new_androidclient.work.data.OnRecyclerItemClickListenerTwo;

import java.util.List;

public class FireAnalysisAdapter extends BaseAdapter {

    List<HotWorkBean> listBeans;
    protected LayoutInflater mInflater;

    //声明自定义的监听接口

    private OnRecyclerItemClickListenerHotWork monItemClickListener;

    //提供set方法供Activity或Fragment调用
    public void setRecyclerItemClickListener(OnRecyclerItemClickListenerHotWork listener){
        monItemClickListener=listener;
    }

    public FireAnalysisAdapter(Context context, List<HotWorkBean> telList) {

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
        final FireAnalysisAdapter.ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_hot_work, parent, false);
            holder = new FireAnalysisAdapter.ViewHolder();
            holder.textView1 = (convertView.findViewById(R.id.text1));
            holder.textView2 = (convertView.findViewById(R.id.text2));
            holder.textView3 = (convertView.findViewById(R.id.text3));
            holder.textView4 = (convertView.findViewById(R.id.text4));
            holder.textView5 = (convertView.findViewById(R.id.text5));
            holder.textView6 = (convertView.findViewById(R.id.text6));
            holder.textView7 = (convertView.findViewById(R.id.text7));
            convertView.setTag(holder);
        } else {
            holder = (FireAnalysisAdapter.ViewHolder) convertView.getTag();
        }

        notifyDataSetChanged();

        holder.textView1.setText(listBeans.get(position).getSiteName());
        holder.textView2.setText(listBeans.get(position).getAnalysisValue());
        holder.textView3.setText(listBeans.get(position).getTestMode());
        holder.textView4.setText(listBeans.get(position).getAnalysisItem());
        holder.textView5.setText(listBeans.get(position).getAnalysisResult());
        holder.textView6.setText(listBeans.get(position).getAnalysisUser());
        holder.textView7.setText(listBeans.get(position).getAnalysisTime());

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
