package com.example.new_androidclient.customize_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 *
 */
@SuppressLint("AppCompatCustomView")
public class WorkApplicationTypeCheckBox extends CheckBox {
    int pos;
    String code;

    public WorkApplicationTypeCheckBox(Context context, int pos, String code) {
        super(context);
        this.pos = pos;
        this.code = code;
    }

    public WorkApplicationTypeCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getCode() {
        if(code == null){
            code = "";
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
