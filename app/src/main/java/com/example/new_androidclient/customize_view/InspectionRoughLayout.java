package com.example.new_androidclient.customize_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.new_androidclient.R;

public class InspectionRoughLayout extends LinearLayout {
    int position;
    //宏观巡检内容
    TextView textView;
    //宏观巡检结果
    EditText editText;
    // 拍照
    Button take;
    //删除
    Button delete;

    public InspectionRoughLayout(Context context) {
        super(context);
    }

    public InspectionRoughLayout(Context context, int position) {
        super(context);
        this.position = position;
    }


    public InspectionRoughLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InspectionRoughLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InspectionRoughLayout initItem(String name, boolean deleteVisible) {
        init();
        if (deleteVisible) { //设备
            setDeleteVisible(VISIBLE);
            setDeviceEditHint();
        } else {  //区域
            setDeleteVisible(GONE);
        }
        setTextViewContent(name);
        return this;
    }


    public InspectionRoughLayout init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_inspection_rough_item, this, true);
        textView = findViewById(R.id.inspection_area_detail_name);
        editText = findViewById(R.id.inspection_area_detail_edit);
        take = findViewById(R.id.inspection_area_detail_take_pic);
        delete = findViewById(R.id.inspection_area_detail_delete);
        return this;
    }


    public void setDeviceEditHint(){
        this.getEditText().setHint("请输入设备巡检结果");
    }

    public void setDeleteVisible(int visible) {
        this.delete.setVisibility(visible);
    }

    public int getPosition() {
        return position;
    }

    public void setTextViewContent(String str) {
        textView.setText(str);
    }

    public String getTextViewContent(){ return textView.getText().toString();}

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public Button getTake() {
        return take;
    }

    public void setTake(Button take) {
        this.take = take;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}

