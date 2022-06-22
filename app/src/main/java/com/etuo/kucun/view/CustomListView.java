package com.etuo.kucun.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Hming on 2018/11/26.
 */

public class CustomListView extends ListView {
    DataChangedListener dataChangedListener;


    @Override
    protected void handleDataChanged() {
        super.handleDataChanged();

        if (null != dataChangedListener)
        dataChangedListener.onSuccess();
    }



    public void setDataChangedListener(DataChangedListener dataChangedListener) {
        this.dataChangedListener = dataChangedListener;
    }

    public interface DataChangedListener{
        public void onSuccess();
    }

    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重寫onMeasure方法决定了组件显示的高度与宽度； makeMeasureSpec函数中第一个參數决定布局空间的大小，第二个参数是布局模式
     * MeasureSpec.AT_MOST的意思就是子控件需要多大的控件就扩展到多大的空间
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
