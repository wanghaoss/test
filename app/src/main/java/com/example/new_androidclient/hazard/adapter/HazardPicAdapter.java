package com.example.new_androidclient.hazard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.new_androidclient.R;
import com.example.new_androidclient.hazard.bean.HazardPicBean;


import java.util.List;

public class HazardPicAdapter extends  RecyclerView.Adapter<HazardPicAdapter.ViewHolder> {
    private List<HazardPicBean> list;
    private Context mContext;


    public HazardPicAdapter(List<HazardPicBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_hazard_pic_item, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HazardPicBean bean = list.get(position);
        if(bean.isFromSystem()){
            Glide.with(mContext).load(bean.getDocUrl()).placeholder(R.drawable.default_pic).into(holder.imageView);
        }else{
            Glide.with(mContext).load(bean.getFile()).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.hazard_pic_item_image);
        }
    }
}

