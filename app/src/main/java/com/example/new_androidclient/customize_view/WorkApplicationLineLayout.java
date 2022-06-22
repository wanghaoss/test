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


/**
 *
 */

public class WorkApplicationLineLayout extends RelativeLayout {
    int pos;
    TextView name;
    TextView nameText;
    EditText editText;
    RadioButton left;
    RadioButton right;
    int type;


    public WorkApplicationLineLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkApplicationLineLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WorkApplicationLineLayout(Context context, int pos) {
        super(context);
        this.pos = pos;
    }

    public WorkApplicationLineLayout init(int type) {
        this.type = type;
        switch (type) {
            case 1: //textView
                LayoutInflater.from(getContext()).inflate(R.layout.work_application_item_one, this, true);
                name = findViewById(R.id.work_item_one_name);
                nameText = findViewById(R.id.work_item_one_name_text);
                break;
            case 2: //select
                LayoutInflater.from(getContext()).inflate(R.layout.work_application_item_two, this, true);
                name = findViewById(R.id.work_item_two_name);
                nameText = findViewById(R.id.work_item_two_name_text);
                break;
            case 3: //editText
                LayoutInflater.from(getContext()).inflate(R.layout.activity_hazard_item_three, this, true);
                name = findViewById(R.id.hazard_item_three_name);
                editText = findViewById(R.id.hazard_item_three_name_text);
                break;
//            case 4: //checkbox是否
//                LayoutInflater.from(getContext()).inflate(R.layout.activity_application_involve_item_layout, this, true);
//                left = findViewById(R.id.involve_item_layout_left);
//                right = findViewById(R.id.involve_item_layout_right);
//                name = findViewById(R.id.involve_item_layout_name);
//                break;
        }
        return this;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public void setNameText(String str) {
        this.name.setText(str);
    }

    public String getNameText_text() {
        return nameText.getText().toString();
    }

    public void setNameText_text(String str) {
        this.nameText.setText(str);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public TextView getNameText() {
        return nameText;
    }

    public void setNameText(TextView nameText) {
        this.nameText = nameText;
    }

    public String getEditText_text() {
        return editText.getText().toString();
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText_text(String str) {
        this.editText.setText(str);
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }


    public RadioButton getLeftBtn() {
        return left;
    }

    public void setLeft(RadioButton left) {
        this.left = left;
    }

    public RadioButton getRightBtn() {
        return right;
    }

    public void setRight(RadioButton right) {
        this.right = right;
    }
}




