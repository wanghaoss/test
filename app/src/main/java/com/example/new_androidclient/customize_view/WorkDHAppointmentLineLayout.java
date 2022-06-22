package com.example.new_androidclient.customize_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.new_androidclient.R;

import java.util.ArrayList;
import java.util.List;

public class WorkDHAppointmentLineLayout extends RelativeLayout {
    int pos;
    int type;
    CheckBox checkBox;
    EditText editText;
    LinearLayout linearLayout;
    List<CheckBox> checkBoxList;
    String editText_text;

    public WorkDHAppointmentLineLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkDHAppointmentLineLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WorkDHAppointmentLineLayout(Context context, int pos) {
        super(context);
        this.pos = pos;
    }

    public WorkDHAppointmentLineLayout init(int type) {
        this.type = type;
        switch (type) {
            case 0: //textView
                LayoutInflater.from(getContext()).inflate(R.layout.work_appointment_item_one, this, true);
                checkBox = findViewById(R.id.work_appointment_item_one_checkbox);
                break;
            case 1: //editText
                LayoutInflater.from(getContext()).inflate(R.layout.work_appointment_item_two, this, true);
                checkBox = findViewById(R.id.work_appointment_item_two_checkbox);
                editText = findViewById(R.id.work_appointment_item_two_edit);
                break;
            case 2: //text + check
                LayoutInflater.from(getContext()).inflate(R.layout.work_appointment_item_three, this, true);
                checkBox = findViewById(R.id.work_appointment_item_three_checkbox);
                linearLayout = findViewById(R.id.work_appointment_item_three_Linear);
                checkBoxList = new ArrayList<>();
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

    public List<CheckBox> getCheckBoxList() {
        return checkBoxList;
    }

    public void setCheckBoxList(List<CheckBox> checkBoxList) {
        this.checkBoxList = checkBoxList;
    }
    public void addCheckBox(CheckBox box){
        this.checkBoxList.add(box);
    }

    public String getEditText_text() {
        return editText_text;
    }

    public void setEditText_text(String editText_text) {
        this.editText_text = editText_text;
    }
}





