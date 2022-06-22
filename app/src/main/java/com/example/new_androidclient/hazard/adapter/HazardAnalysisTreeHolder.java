package com.example.new_androidclient.hazard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.new_androidclient.R;
import com.unnamed.b.atv.model.TreeNode;

public class HazardAnalysisTreeHolder extends TreeNode.BaseNodeViewHolder<HazardAnalysisTreeHolder.IconTreeItem> {


    public HazardAnalysisTreeHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.hazard_analysis_tree_item, null, false);
        TextView tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.text);

        return view;
    }

    public static class IconTreeItem {
        public int icon;
        public String text;

        public IconTreeItem(String text) {
            this.text = text;
        }
    }
}
