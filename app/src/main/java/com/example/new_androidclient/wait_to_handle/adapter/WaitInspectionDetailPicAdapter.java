package com.example.new_androidclient.wait_to_handle.adapter;

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
import com.example.new_androidclient.wait_to_handle.bean.DefaultPicBean;

import java.util.List;

public class WaitInspectionDetailPicAdapter extends  RecyclerView.Adapter<WaitInspectionDetailPicAdapter.ViewHolder> {
    private List<DefaultPicBean> list;  //借用下隐患模块的
    private Context mContext;

    public WaitInspectionDetailPicAdapter(List<DefaultPicBean> list, Context mContext) {
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
        DefaultPicBean bean = list.get(position);
            Glide.with(mContext).load(bean.getDocUrl()).placeholder(R.drawable.default_pic).into(holder.imageView);
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


