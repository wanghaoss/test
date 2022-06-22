package com.example.new_androidclient.work.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.work.bean.WorkNeedDoBean;
import com.example.new_androidclient.work.bean.WorkingToDoBean;
import com.example.new_androidclient.work.data.OnRecyclerItemClickListener;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.List;

public class WorkNeedDoOneAdapter extends RecyclerView.Adapter<WorkNeedDoOneAdapter.ViewHolder>{

    private List<WorkingToDoBean> list;
    private Context mContext;
    private Integer type;

    //声明自定义的监听接口
    private OnRecyclerItemClickListener monItemClickListener;

    //提供set方法供Activity或Fragment调用
    public void setRecyclerItemClickListener(OnRecyclerItemClickListener listener){
        monItemClickListener=listener;
    }

    public WorkNeedDoOneAdapter(List<WorkingToDoBean> lists, Integer type,Context mContext){
        this.list = lists;
        this.type = type;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public WorkNeedDoOneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_need_do, parent,false);
        WorkNeedDoOneAdapter.ViewHolder holder = new WorkNeedDoOneAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkNeedDoOneAdapter.ViewHolder holder, int position) {
        if (list.size() > 0){
            //待踏勘
            String stateValue = list.get(position).getStatusName();
            //起始时间
            if (list.get(position).getPlanStartDate() == null || list.get(position).getPlanStartDate().equals("")){
                holder.startTimeText.setText("-");
            }else {
                String startTime = list.get(position).getPlanStartDate();
                startTime = startTime.substring(0,10);
                holder.startTimeText.setText(startTime);
            }
            //完成时间
            if (list.get(position).getPlanFinishDate() == null || list.get(position).getPlanFinishDate().equals("")){
                holder.finishTimeText.setText("-");
            }else {
                String finishTime = String.valueOf(list.get(position).getPlanFinishDate());
                finishTime = finishTime.substring(0,10);
                holder.finishTimeText.setText(finishTime);
            }
           //项目名称
              String sheetName = String.valueOf(list.get(position).getSheetName());
            //工单类别
              String sheetCat = String.valueOf(list.get(position).getSheetCat());
            //工单编号
              String sheetNo = String.valueOf(list.get(position).getSheetNo());

            if (sheetName == null || TextUtils.isEmpty(sheetName)){
                holder.workNameText.setText("-");
            }else {
                holder.workNameText.setText(sheetName);
            }

            if (sheetNo == null || TextUtils.isEmpty(sheetNo)){
                holder.workOrderNoText.setText("-");
            }else {
                holder.workOrderNoText.setText(sheetNo);
            }

            if (sheetCat == null || TextUtils.isEmpty(sheetCat)){
                holder.workFamilyText.setText("-");
            }else {
                holder.workFamilyText.setText(sheetCat);
            }
//
            if (stateValue.equals("待审核")){
                holder.state.setBackgroundResource(R.drawable.device_blue);
            }
            if (stateValue.equals("待许可")){
                holder.state.setBackgroundResource(R.drawable.device_yellow);
            }
            if (stateValue.equals("待申请")){
                holder.state.setBackgroundResource(R.drawable.device_green);
            }
            if (stateValue.equals("待踏勘")){
                holder.state.setBackgroundResource(R.drawable.device_purple);
            }
            if (stateValue.equals("待审批")){
                holder.state.setBackgroundResource(R.drawable.device_red);
            }
            if (stateValue.equals("已注销")){
                holder.state.setBackgroundResource(R.drawable.device_bbffff);
            }
            if (stateValue.equals("审核拒绝")){
                holder.state.setBackgroundResource(R.drawable.device_c67171);
            }
            if (stateValue.equals("审批拒绝")){
                holder.state.setBackgroundResource(R.drawable.device_bcee68);
            }
            if (stateValue.equals("已撤销")){
                holder.state.setBackgroundResource(R.drawable.device_d1d1d1);
            }
            if (stateValue.equals("待核查")){
                holder.state.setBackgroundResource(R.drawable.device_cdb79e);
            }
            if (stateValue.equals("许可拒绝")){
                holder.state.setBackgroundResource(R.drawable.device_daa520);
            }
            if (stateValue.equals("作业监督")){
                holder.state.setBackgroundResource(R.drawable.device_33cccc);
            }
            if (stateValue.equals("许可关闭")){
                holder.state.setBackgroundResource(R.drawable.device_66cc00);
            }
            if (stateValue.equals("许可取消")){
                holder.state.setBackgroundResource(R.drawable.device_ff3399);
            }
                holder.state.setText(stateValue);
        }

        String id = String.valueOf(list.get(position).getId());
        holder.item.setText(id);

    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView workNameText;
        TextView workOrderNoText;
        TextView workFamilyText;
        TextView startTimeText;
        TextView finishTimeText;
        TextView state;
        TextView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            workNameText = itemView.findViewById(R.id.workNameText);
            workOrderNoText = itemView.findViewById(R.id.workOrderNoText);
            workFamilyText = itemView.findViewById(R.id.workFamilyText);
            startTimeText = itemView.findViewById(R.id.startTimeText);
            finishTimeText = itemView.findViewById(R.id.finishTimeText);
            state = itemView.findViewById(R.id.state);
            item = itemView.findViewById(R.id.item1);

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
