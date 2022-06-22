package com.example.new_androidclient.customize_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.new_androidclient.R;

public class NoDataLayout extends LinearLayout {

    public NoDataLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater. from(context).inflate(R.layout.no_data, this);
    }
}
