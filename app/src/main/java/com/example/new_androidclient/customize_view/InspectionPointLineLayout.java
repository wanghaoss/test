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

public class InspectionPointLineLayout extends LinearLayout {
    private TextView textView;
    private CheckBox checkBox_point;
    private ImageView imageView;
    private TextView rightIconText;
    private CheckBox checkBox_open;
    private CheckBox checkBox_close;
    private int pos;
    private String type;

    public InspectionPointLineLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InspectionPointLineLayout(Context context, int pos, String type) {
        super(context);
        this.pos = pos;
        this.type = type;
    }

    /**
     * 初始化各个控件
     */
    public InspectionPointLineLayout init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_inspection_checkbox_line, this, true);
        textView = findViewById(R.id.point_line_text_content);
        checkBox_point = findViewById(R.id.point_line_right_checkbox);
        checkBox_open = findViewById(R.id.point_line_right_checkbox_open);
        checkBox_close = findViewById(R.id.point_line_right_checkbox_close);
        if (type.equals("4")) {
            setCheckBoxStr("不合格");
        }
        imageView = findViewById(R.id.point_line_right_icon);
        rightIconText = findViewById(R.id.point_right_icon_text);
        return this;
    }

    /**
     * icon + 文字 + edit +单位+ 下分割线+ 右侧拍照
     *
     * @return
     */
    public InspectionPointLineLayout initItemWidthEdit(String textContent, int visible) {
        init();
        setText(textContent);
        setTakePicVisible(visible);
        return this;
    }

    public void setShowOpenOrClose(boolean show) {
        if (show) {
            checkBox_open.setVisibility(VISIBLE);
            checkBox_close.setVisibility(VISIBLE);
            checkBox_point.setVisibility(GONE);
        } else {
            checkBox_open.setVisibility(GONE);
            checkBox_close.setVisibility(GONE);
            checkBox_point.setVisibility(VISIBLE);
        }
    }

    public void setCheckBoxStr(String text) {
        checkBox_point.setText(text);
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public InspectionPointLineLayout setText(String str) {
        textView.setText(str);
        return this;
    }


    public InspectionPointLineLayout setCheckBox(boolean is) {
        checkBox_point.setChecked(is);
        return this;
    }


    public InspectionPointLineLayout setImageView(int iv) {
        imageView.setImageResource(iv);
        return this;
    }

    public CheckBox getCheckBox() {
        return checkBox_point;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public InspectionPointLineLayout setTakePicVisible(int visible) {
        imageView.setVisibility(visible);
        return this;
    }

    public InspectionPointLineLayout setIconTextVisible(boolean show) {
        if (show) {
            rightIconText.setVisibility(VISIBLE);
        } else {
            rightIconText.setVisibility(INVISIBLE);
        }
        return this;
    }

    public CheckBox getCheckBox_open() {
        return checkBox_open;
    }

    public void setCheckBox_open(CheckBox checkBox_open) {
        this.checkBox_open = checkBox_open;
    }

    public CheckBox getCheckBox_close() {
        return checkBox_close;
    }

    public void setCheckBox_close(CheckBox checkBox_close) {
        this.checkBox_close = checkBox_close;
    }
}
