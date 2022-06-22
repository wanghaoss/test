package com.example.new_androidclient.customize_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.new_androidclient.R;

public class InspectionDeviceRoughLineLayout extends LinearLayout {
    private TextView textView;
    private CheckBox checkBox;
    //  private ImageView imageView;
    private TextView rightIconText;
    private int pos;

    public InspectionDeviceRoughLineLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InspectionDeviceRoughLineLayout(Context context, int pos) {
        super(context);
        this.pos = pos;
    }

    /**
     * 初始化各个控件
     */
    public InspectionDeviceRoughLineLayout init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_inspection_device_rough_line, this, true);
        textView = findViewById(R.id.inspection_device_rough_line_text_content);
        checkBox = findViewById(R.id.inspection_device_rough_line_checkbox);
        //      imageView = findViewById(R.id.point_line_right_icon);
        rightIconText = findViewById(R.id.inspection_device_rough_line_right_text);
        return this;
    }

    /**
     * icon + 文字 + edit +单位+ 下分割线+ 右侧拍照
     *
     * @return
     */
    public InspectionDeviceRoughLineLayout initItemWidthEdit(String textContent, int visible) {
        init();
        setText(textContent);
        setRightTextVisible(visible);
        return this;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public InspectionDeviceRoughLineLayout setText(String str) {
        textView.setText(str);
        return this;
    }

    public TextView getRightIconText() {
        return rightIconText;
    }

    public InspectionDeviceRoughLineLayout setCheckBox(boolean is) {
        checkBox.setChecked(is);
        return this;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public InspectionDeviceRoughLineLayout setRightTextVisible(int visible) {
        rightIconText.setVisibility(visible);
        return this;
    }

}
