package com.example.new_androidclient.customize_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.new_androidclient.R;

public class WorkApplicationCheckListItemLayout extends RelativeLayout {

    int pos;
    CheckBox checkBox;
    EditText editText;

    public WorkApplicationCheckListItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkApplicationCheckListItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WorkApplicationCheckListItemLayout(Context context, int pos) {
        super(context);
        this.pos = pos;
    }

    public WorkApplicationCheckListItemLayout init() {
        LayoutInflater.from(getContext()).inflate(R.layout.work_application_checklist_item, this, true);
        checkBox = findViewById(R.id.work_application_checklist_checkbox);
        editText = findViewById(R.id.work_application_checklist_edittext);
        return this;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public void setCheckBoxStr(String str) {
        this.checkBox.setText(str);
    }

    public EditText getEditText() {
        return editText;
    }

    public String getEditTextStr() {
        return editText.getText().toString();
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public void setEditTextStr(String editText) {
        this.editText.setText(editText);
    }
}
