package com.example.new_androidclient.customize_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.new_androidclient.R;

import org.w3c.dom.Text;

public class WorkApplicationInvolveItemLayout extends RelativeLayout {
    int pos;
    RadioButton left;
    RadioButton right;
    TextView name;


    public WorkApplicationInvolveItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkApplicationInvolveItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WorkApplicationInvolveItemLayout(Context context, int pos) {
        super(context);
        this.pos = pos;
    }

    public WorkApplicationInvolveItemLayout init() {
        LayoutInflater.from(getContext()).inflate(R.layout.activity_application_involve_item_layout, this, true);
        left = findViewById(R.id.involve_item_layout_left);
        right = findViewById(R.id.involve_item_layout_right);
        name = findViewById(R.id.involve_item_layout_name);
        return this;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }


    public RadioButton getLeftButton() {
        return left;
    }

    public void setLeft(RadioButton left) {
        this.left = left;
    }

    public RadioButton getRightButton() {
        return right;
    }

    public void setRight(RadioButton right) {
        this.right = right;
    }

    public TextView getName() {
        return name;
    }

    public void setNameStr(String name) {
        this.name.setText(name);
    }
}

