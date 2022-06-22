package com.example.new_androidclient.Base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//T是bean类，TBinding是item的Binding类
abstract public class BaseAdapter<T, TBinding extends ViewDataBinding>
        extends RecyclerView.Adapter<BaseAdapter<T, TBinding>.ViewHolder> {
    private List<T> list;
    private int brId;
    private Context mContext;
    private onItemClickListener listener;

    public BaseAdapter(List<T> list, int brId, onItemClickListener listener) {
        this.list = list;
        this.brId = brId;
        this.listener = listener;
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        TBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        TBinding getBinding() {
            return binding;
        }

        void setBinding(TBinding binding) {
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        TBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutId(), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding.getRoot());
        viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getBinding().setVariable(brId, list.get(position));
        holder.getBinding().executePendingBindings(); //立即执行绑定，在对view变化时效敏感的地方常用，不加这句有可能出现itemView更新滞后、闪烁等问题
        holder.itemView.setOnClickListener(v -> listener.onItemClick(v, position));
    }

    @Override
    public int getItemCount() {
        return Math.max(list.size(), 0);
    }

    protected abstract int getLayoutId();


    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }
}
