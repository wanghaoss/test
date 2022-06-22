package com.etuo.kucun.base;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etuo.kucun.R;


/**
 * 布局内置了header的Activity
 *
 * @author lenovo
 */
public class BaseHeaderBarActivity extends BaseActivity implements OnClickListener {

    private View btnPrev;
    private TextView tvHeaderTitle;
    private TextView tvRight;// 右侧
    private OnClickBtItem onClickBtItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_system_header);
//        BarUtils.setColor(this,getResources().getColor(R.color.bg_blue),0);
        ViewGroup container = (ViewGroup) findViewById(R.id.fl_content_view);
        View.inflate(this, layoutResID, container);
        btnPrev = findViewById(R.id.btn_prev);
        tvHeaderTitle = (TextView) findViewById(R.id.tv_header_title);
        btnPrev.setOnClickListener(this);
        tvRight = (TextView) findViewById(R.id.tv_right);


    }

    public void setHeaderTitle(int resId) {
        tvHeaderTitle.setText(resId);
    }

    public void setHeaderTitle(String title) {
        tvHeaderTitle.setText(title);
    }

    public void setRightName(String name) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(this);
        tvRight.setText(name);
    }

    public interface OnClickBtItem {
        void onClickRight();

    }
    public void OnClickBtItem(OnClickBtItem onClickBtItem) {
        this.onClickBtItem = onClickBtItem;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_prev:
                finish();
                break;
            case R.id.tv_right:
                onClickBtItem.onClickRight();
                break;
            default:
                break;
        }
    }
}
