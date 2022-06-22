package com.example.testmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.testmodule.utils.StatusBarUtil;

public class MainActivity extends AppCompatActivity {

    private static int STATUS_BAR_COLOR = 0x660099FF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setStatusBarColor(this, STATUS_BAR_COLOR);
    }
}