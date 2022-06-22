package com.example.new_androidclient.device_management.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.device_management.Data.OnRecyclerItemClickListener;
import com.example.new_androidclient.device_management.bean.DevicePlanListBean;

import java.util.List;

public class WorkOrderAdapter extends RecyclerView.Adapter<WorkOrderAdapter.ViewHolder>{
    private List<DevicePlanListBean> list;
    private Context mContext;

    //声明自定义的监听接口
    private OnRecyclerItemClickListener monItemClickListener;

    //提供set方法供Activity或Fragment调用
    public void setRecyclerItemClickListener(OnRecyclerItemClickListener listener){
        monItemClickListener=listener;
    }

    public WorkOrderAdapter(List<DevicePlanListBean> lists, Context mContext){
        this.list = lists;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interim_plan, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.deviceValue.setText(list.get(position).getDeviceName());
        holder.workshopValue.setText(list.get(position).getDptName());
        holder.unitValue.setText(list.get(position).getUnitName());

//        if ("待下达".equals(list.get(position).getPlanStatus())){
//            list.remove(position);
//        }else if ("已下达".equals(list.get(position).getPlanStatus())){
            holder.name1.setVisibility(View.VISIBLE);
            holder.name1.setText("处理人：");

            if (list.get(position).getHandleUserName() == null){
                holder.value1.setVisibility(View.GONE);
            }else {
                holder.value1.setVisibility(View.VISIBLE);
                holder.value1.setText(list.get(position).getHandleUserName().toString());
            }

            holder.name2.setVisibility(View.VISIBLE);
            holder.name2.setText("异常/故障现象:");

            if (list.get(position).getProblemDesc() == null){

                holder.value1.setVisibility(View.GONE);
            }else {
                holder.value1.setVisibility(View.VISIBLE);
                holder.value1.setText(list.get(position).getProblemDesc().toString());
            }

            holder.state.setVisibility(View.VISIBLE);
            holder.state.setBackgroundResource(R.drawable.device_green);
            holder.state.setText(list.get(position).getPlanStatus());
//        }else if ("未下达".equals(list.get(position).getPlanStatus())){
//            list.remove(position);
//        }

    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceValue;
        TextView workshopValue;
        TextView unitValue;
        TextView name1;
        TextView value1;
        TextView name2;
        TextView value2;
        TextView state;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceValue = itemView.findViewById(R.id.deviceValue);
            workshopValue = itemView.findViewById(R.id.workshopValue);
            unitValue = itemView.findViewById(R.id.unitValue);
            name1 = itemView.findViewById(R.id.name1);
            value1 = itemView.findViewById(R.id.value1);
            name2 = itemView.findViewById(R.id.name2);
            value2 = itemView.findViewById(R.id.value2);
            state = itemView.findViewById(R.id.state);


            //将监听传递给自定义接口
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (monItemClickListener!=null){

                        monItemClickListener.onItemClick(getAdapterPosition(),list);
                    }
                }
            });
        }
    }
}
