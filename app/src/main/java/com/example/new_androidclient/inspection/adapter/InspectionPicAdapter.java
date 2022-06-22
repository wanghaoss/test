package com.example.new_androidclient.inspection.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.new_androidclient.R;
import com.example.new_androidclient.inspection.bean.PicBean;

import java.io.File;
import java.util.List;

public class InspectionPicAdapter extends RecyclerView.Adapter<InspectionPicAdapter.ViewHolder> {
    private  List<PicBean> list;
    private Context mContext;

    public InspectionPicAdapter(List<PicBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_inspection_take_photo_recyclerview_item, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        File file = list.get(position).getFile();
        Glide.with(mContext).load(file).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.inspection_pic_item);
        }
    }
}
