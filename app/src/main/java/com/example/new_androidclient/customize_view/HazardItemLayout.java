package com.example.new_androidclient.customize_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.new_androidclient.R;

/**
 * 隐患模块，除分析页面外都是用本页面的layout
 */
public class HazardItemLayout extends RelativeLayout {
    int pos;
    TextView hazardName_one;
    TextView hazardName_one_text;
    TextView hazardName_one_edit;
    TextView des;

    public HazardItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HazardItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HazardItemLayout(Context context, int pos) {
        super(context);
        this.pos = pos;
    }

    public HazardItemLayout init(int type) {
        switch (type) {
            case 1: //textView
                LayoutInflater.from(getContext()).inflate(R.layout.activity_hazard_item_one, this, true);
                hazardName_one = findViewById(R.id.hazard_item_one_name);
                hazardName_one_text = findViewById(R.id.hazard_item_one_name_text);
                break;
            case 2: //select
                LayoutInflater.from(getContext()).inflate(R.layout.activity_hazard_item_two, this, true);
                hazardName_one = findViewById(R.id.hazard_item_two_name);
                hazardName_one_text = findViewById(R.id.hazard_item_two_name_text);
                break;
            case 3: //editText
                LayoutInflater.from(getContext()).inflate(R.layout.activity_hazard_item_three, this, true);
                hazardName_one = findViewById(R.id.hazard_item_three_name);
                hazardName_one_edit = findViewById(R.id.hazard_item_three_name_text);
                break;
        }
        return this;
    }

    public String getHazardName_one_edit_str() {
        return hazardName_one_edit.getText().toString();
    }

    public void setHazardName_one_edit(String hazardName_one_edit) {
        this.hazardName_one_edit.setText(hazardName_one_edit);
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setHazardName_one(String hazardName_one) {
        this.hazardName_one.setText(hazardName_one);
    }

    public TextView getHazardName_one_text() {
        return hazardName_one_text;
    }

    public String getHazardName_one_text_str() {
        return hazardName_one_text.getText().toString();
    }

    public void setHazardName_one_text(String hazardName_one_text) {
        this.hazardName_one_text.setText(hazardName_one_text);
    }

    public TextView getDes() {
        return des;
    }

    public void setDes(TextView des) {
        this.des = des;
    }
}

