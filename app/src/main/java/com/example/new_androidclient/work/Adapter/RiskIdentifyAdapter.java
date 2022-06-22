package com.example.new_androidclient.work.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.example.new_androidclient.work.bean.TreeListBean;

import java.util.List;

public class RiskIdentifyAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    private List<TreeListBean.ChildrenBeanX> listBeans;
    private Context context;


    public RiskIdentifyAdapter(Context context, List<TreeListBean.ChildrenBeanX> treeList) {
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
        final RiskIdentifyAdapter.ViewHolder holder;
        notifyDataSetChanged();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_tree_list, parent, false);
            holder = new RiskIdentifyAdapter.ViewHolder();
            holder.textView = (convertView.findViewById(R.id.tv_content));
            convertView.setTag(holder);
        } else {
            holder = (RiskIdentifyAdapter.ViewHolder) convertView.getTag();
        }

        notifyDataSetChanged();

        holder.textView.setText(listBeans.get(position).getLabel());
//        List<TreeListBean.ChildrenBeanX.ChildrenBean> list = listBeans.get(position).getChildren();

//        SafetyMeasuresAdapter adapter = new SafetyMeasuresAdapter(context,list);
//        holder.treeList2.setAdapter(adapter);

//        setListViewHeightBasedOnChildren(holder.treeList2);

        return convertView;

    }

    static class ViewHolder {
        TextView textView;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
