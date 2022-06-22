package com.etuo.kucun.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 流式布局RelativeLayout
 * Created by wzb on 2016/12/16.
 */

public class FlowRelativeLayout extends ViewGroup {
    //子控件之间距离
    private final int DEFAULT_CHILD_MARGIN = 15;//子控件间距默认值
    private int childMarginVertical = DEFAULT_CHILD_MARGIN;
    private int childMarginHorizontal = DEFAULT_CHILD_MARGIN;

    public FlowRelativeLayout(Context context) {
        super(context);
    }

    public FlowRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
                int width = child.getMeasuredWidth() + childMarginHorizontal;
                int height = child.getMeasuredHeight();
                if (x + width > maxSpaceWidth) {//当前行不足以方向新子控件，需要换行显示
                    if (x != 0)//x不为0，表示当前行已经有一个子控件了，新子控件需要在下一行显示，行数加1
                        row++;
                    x = width;
                } else {
                    x = x + width;
                }
                y = row * (height + childMarginVertical);
            }
        }
        //容器的高度需要加上 上下方向的padding
        int height = y > childMarginVertical ? y - childMarginVertical : 0;
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
            if (x + width + childMarginHorizontal > maxWidth) {
                if (x != 0)//x不为0，表示当前行已有其他的子控件，新子控件需要在下一行显示，行数加1
                    y += height + childMarginVertical;
                x = 0;
            }
            width = Math.min(width, maxWidth);//如果单个子控件宽度已经超过了父控件的最大宽度，则将子控件宽度控制在最大宽度
            tempChild.layout(x + paddingLeft, y, x + paddingLeft + width, y + height);
            x += width + childMarginHorizontal;
        }
    }


    public void setChildMargin(int marginVerticalId, int marginHorizontalId) {
        this.childMarginVertical = getContext().getResources().getDimensionPixelOffset(marginVerticalId);
        this.childMarginHorizontal = getContext().getResources().getDimensionPixelOffset(marginHorizontalId);
    }
}
