package com.example.new_androidclient.work;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Other.DestroyActivityUtil;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.work.Adapter.WorkControlAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * wh
 * 作业监督
 */
@Route(path = RouteString.WorkControlActivity)
public class WorkControlActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.but1)
    Button but1;
//    @BindView(R.id.but2)
//    Button but2;
//    @BindView(R.id.but33)
//    Button but33;
    @BindView(R.id.but4)
    Button but4;
//    @BindView(R.id.but5)
//    Button but5;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.timeBut)
    Button timeBut;

    List<String> timeList = new ArrayList<>();
    WorkControlAdapter adapter;
    int applicationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_control);
        ButterKnife.bind(this);



        setOnClick();
        getView();
        DestroyActivityUtil.addDestoryActivityToMap(WorkControlActivity.this,"WorkControlActivity");
    }

    private void getView() {
        applicationId = getIntent().getIntExtra("applicationId",applicationId);

    }

    private void setOnClick() {
        //作业许可取消
        but1.setOnClickListener(this);
        ////受限空间进出登记表
//        but33.setOnClickListener(this);
        //气体检测
//        but4.setOnClickListener(this);
        timeBut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but1:
                Intent intent = new Intent(mContext,PermitCancelActivity.class);
                intent.putExtra("applicationId",applicationId);
                startActivity(intent);
                break;
//            case R.id.but2:
//                Intent intent1 = new Intent(mContext,WorkCloseActivity.class);
//                intent1.putExtra("applicationId",applicationId);
//                startActivity(intent1);
//                break;
//            case R.id.but33:
//                break;
            case R.id.but4:
                Intent intent2 = new Intent(mContext,GasAnalysisActivity.class);
                intent2.putExtra("applicationId",applicationId);
                startActivity(intent2);
                break;
            case R.id.timeBut:
                setTimeAdapter();
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTimeAdapter() {
        //获取系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date();

        String date = formatter.format(curDate);

        timeList.add(date);

        adapter = new WorkControlAdapter(mContext,timeList);
        list.setAdapter(adapter);

        list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        list.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        list.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }
}
