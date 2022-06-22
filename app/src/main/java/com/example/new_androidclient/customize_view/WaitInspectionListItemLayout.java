package com.example.new_androidclient.customize_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.new_androidclient.R;


public class WaitInspectionListItemLayout extends LinearLayout {
    private CheckBox checkBox;
    private int pos;

    public WaitInspectionListItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaitInspectionListItemLayout(Context context, int pos) {
        super(context);
        this.pos = pos;
    }

    /**
     * 初始化各个控件
     */
    public WaitInspectionListItemLayout init() {
        LayoutInflater.from(getContext()).inflate(R.layout.activity_wait_inspection_list_item, this, true);
        checkBox = findViewById(R.id.wait_inspection_list_item_checkbox);
        return this;
    }

    /**
     * icon + 文字 + edit +单位+ 下分割线+ 右侧拍照
     *
     * @return
     */
    public WaitInspectionListItemLayout initItemWidthEdit(String textContent) {
        init();
        setText(textContent);
        return this;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public WaitInspectionListItemLayout setText(String str) {
        checkBox.setText(str);
        return this;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }


    public WaitInspectionListItemLayout setCheckBox(boolean is) {
        checkBox.setChecked(is);
        return this;
    }


}

