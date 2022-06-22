package com.example.new_androidclient.work.data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.new_androidclient.R;

public class WorkSafeAppointmentLineLayout extends RelativeLayout {
    int pos;
    int type;
    CheckBox checkBox;
    EditText editText;
    TextView textView;
    LinearLayout linearLayout;

    public WorkSafeAppointmentLineLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkSafeAppointmentLineLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WorkSafeAppointmentLineLayout(Context context, int pos) {
        super(context);
        this.pos = pos;
    }

    public WorkSafeAppointmentLineLayout init(int type) {
        this.type = type;
        switch (type) {
            case 0: //textView
                LayoutInflater.from(getContext()).inflate(R.layout.work_safe_appointment_item_one, this, true);
                checkBox = findViewById(R.id.work_appointment_item_one_checkbox);
                textView = findViewById(R.id.textView_one);
                break;
            case 1: //editText
                LayoutInflater.from(getContext()).inflate(R.layout.work_safe_appointment_item_two, this, true);
                checkBox = findViewById(R.id.work_appointment_item_two_checkbox);
                editText = findViewById(R.id.work_appointment_item_two_edit);
                textView = findViewById(R.id.textView_two);
                break;
            case 2: //text + check
                LayoutInflater.from(getContext()).inflate(R.layout.work_safe_appointment_item_three, this, true);
                checkBox = findViewById(R.id.work_appointment_item_three_checkbox);
                linearLayout = findViewById(R.id.work_appointment_item_three_Linear);
                textView = findViewById(R.id.textView_three);
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}





