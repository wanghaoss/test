package com.example.new_androidclient.customize_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.new_androidclient.R;

public class WorkAddSignLineLayout extends RelativeLayout {
    int pos;
    TextView name;
    ImageView imageView;
    Button btn;
    int type;


    public WorkAddSignLineLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkAddSignLineLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WorkAddSignLineLayout(Context context, int pos) {
        super(context);
        this.pos = pos;
    }

    public WorkAddSignLineLayout init(int type) {
        this.type = type;
        switch (type) {
            case 1: //textView
                LayoutInflater.from(getContext()).inflate(R.layout.work_add_sign_item, this, true);
                name = findViewById(R.id.work_add_sign_name);
                btn = findViewById(R.id.work_add_sign_btn);
                imageView = findViewById(R.id.work_add_sign_image);
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

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public void setNameText(String str) {
        this.name.setText(str);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}





