package com.example.new_androidclient.customize_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.new_androidclient.R;


/**
 * 隐患模块，分析页面用本layout，有取消选择的功能，用于处理lec和人工填写的问题
 * 看本页面代码时，可与HazardItemLayout对比看
 */
public class HazardItemLayout2 extends RelativeLayout {
    int pos;
    TextView hazardName_one;
    TextView hazardName_one_text;
    RelativeLayout image;
    ImageView clear;
    ImageView right;
    TextView des;
    int type;

    public interface clearListener {
        void clear();
    }

    public clearListener clearListener;

    public HazardItemLayout2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HazardItemLayout2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HazardItemLayout2(Context context, int pos) {
        super(context);
        this.pos = pos;
    }

    public HazardItemLayout2 init(int type) {
        this.type = type;
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
        }
        return this;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getHazardName_one_str() {
        return hazardName_one.getText().toString();
    }

    public TextView getDes() {
        return des;
    }

    public void setDes(TextView des) {
        this.des = des;
    }
}




