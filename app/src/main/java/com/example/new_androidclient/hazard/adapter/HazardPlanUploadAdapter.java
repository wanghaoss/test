package com.example.new_androidclient.hazard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.hazard.bean.HazardPlanUploadFileBean;

import java.util.List;

public class HazardPlanUploadAdapter extends RecyclerView.Adapter<HazardPlanUploadAdapter.ViewHolder> {
    private List<HazardPlanUploadFileBean> list;

    public HazardPlanUploadAdapter(List<HazardPlanUploadFileBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_hazard_upload_file_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HazardPlanUploadFileBean bean = list.get(position);
        holder.name.setText(bean.getName());
        holder.type.setText(bean.getTypeStr());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hazard_upload_file_item_name);
            type  = itemView.findViewById(R.id.hazard_upload_file_item_type);
        }
    }
}
