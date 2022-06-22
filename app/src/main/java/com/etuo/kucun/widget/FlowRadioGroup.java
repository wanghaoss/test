package com.etuo.kucun.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.etuo.kucun.R;

import java.util.List;

/**
 * Created by Administrator on 2018/6/29.
 */

public class FlowRadioGroup extends RadioGroup {
    //子控件之间距离
    private int childSpaceVertical, childSpaceHorizontal;


    public FlowRadioGroup(Context context) {
        super(context);
    }

    public FlowRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.flowGroup);
        childSpaceVertical = array.getDimensionPixelSize(R.styleable.flowGroup_childSpaceVertical, 0);
        childSpaceHorizontal = array.getDimensionPixelSize(R.styleable.flowGroup_childSpaceHorizontal, 0);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //控件的宽度
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int x = 0;
        int y = 0;
        int row = 1;
        //最大的可使用空间宽度应该减去控件自身的左右padding
        int maxSpaceWidth = maxWidth - getPaddingLeft() - getPaddingRight();
        for (int index = 0; index < childCount; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                // 此处增加onlayout中的换行判断，用于计算所需的高度
                int width = child.getMeasuredWidth() + childSpaceHorizontal;
                int height = child.getMeasuredHeight();
                if (x + width > maxSpaceWidth) {//当前行不足以方向新子控件，需要换行显示
                    if (x != 0)//x不为0，表示当前行已经有一个子控件了，新子控件需要在下一行显示，行数加1
                        row++;
                    x = width;
                } else {
                    x = x + width;
                }
                y = row * (height + childSpaceVertical);
            }
        }
        //容器的高度需要加上 上下方向的padding
        int height = y > childSpaceVertical ? y - childSpaceVertical : 0;
        height += getPaddingTop() + getPaddingBottom();
        // 设置容器所需的宽度和高度
        setMeasuredDimension(maxWidth, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        //最大宽度应该减去控件自身的左右padding
        int paddingLeft = getPaddingLeft();
        int maxWidth = r - l - paddingLeft - getPaddingRight();
        int x = 0;
        int y = getPaddingTop();
        View tempChild;
        for (int i = 0; i < childCount; i++) {
            tempChild = getChildAt(i);
            if (tempChild.getVisibility() == View.GONE)
                continue;
            //获得子控件的宽高
            int width = tempChild.getMeasuredWidth();
            int height = tempChild.getMeasuredHeight();
            if (x + width + childSpaceHorizontal > maxWidth) {
                if (x != 0)//x不为0，表示当前行已有其他的子控件，新子控件需要在下一行显示，行数加1
                    y += height + childSpaceVertical;
                x = 0;
            }
            width = Math.min(width, maxWidth);//如果单个子控件宽度已经超过了父控件的最大宽度，则将子控件宽度控制在最大宽度
            tempChild.layout(x + paddingLeft, y, x + paddingLeft + width, y + height);
            x += width + childSpaceHorizontal;
        }
    }


    public void setChildMargin(int marginHorizontalId, int marginVerticalId) {
        this.childSpaceHorizontal = getContext().getResources().getDimensionPixelOffset(marginHorizontalId);
        this.childSpaceVertical = getContext().getResources().getDimensionPixelOffset(marginVerticalId);
    }

    public void addItemRB(int itemLayoutId, List<String> nameList, OnCheckedChangeListener listener) {
        setOnCheckedChangeListener(listener);
        removeAllViews();
        if (nameList == null || nameList.isEmpty()) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (String name : nameList) {
            RadioButton rb = (RadioButton) inflater.inflate(itemLayoutId, null);
            rb.setText(name);
            addView(rb);
        }
        //默认首个子项被选中
        check(getChildAt(0).getId());
    }
}
