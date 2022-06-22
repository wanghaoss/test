package com.example.new_androidclient.customize_view;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.new_androidclient.R;

public class InspectionEditLineLayout extends LinearLayout {

    /**
     * 上下分割线，默认隐藏上面分割线
     */
    private View dividerTop, dividerBottom;

    /**
     * 最外层容器
     */
    private LinearLayout llRoot;
    /**
     * 最左边的Icon
     */
    private ImageView ivLeftIcon;
    /**
     * 中间的文字内容
     */
    private TextView tvTextContent;

    /**
     * 中间的输入框
     */
    private EditText editContent;

    /**
     * 右边的文字
     */
    private TextView tvRightText;

    /**
     * 右边的icon 通常是箭头
     */
    private ImageView ivRightIcon;

    /**
     * 右边的icon下面的提示，已拍照
     */
    private TextView tvRightIconText;

    /**
     *巡检检查页面中，item的位置，
     */
    private int pos;


    /**
     * 整个一行被点击
     */
    public static interface OnRootClickListener {
        void onRootClick(View view);
    }

    public static interface OnFoucsChangeListener {
        void onFoucsChange();
    }
    public  OnFoucsChangeListener onFoucsChangeListener;

    public void setOnFoucsChangeListener(OnFoucsChangeListener onFoucsChangeListener) {
        this.onFoucsChangeListener = onFoucsChangeListener;
    }


    /**
     * 右边箭头的点击事件
     */
    public interface OnArrowClickListener {
        void onArrowClick(View view);
    }

    public InspectionEditLineLayout(Context context) {
        super(context);
    }

    public InspectionEditLineLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    public InspectionEditLineLayout(Context context, int pos) {
        super(context);
        this.pos = pos;
    }

    /**
     * 我的页面每一行  icon + 文字 + 右箭头（显示/不显示） + 右箭头左边的文字（显示/不显示）+ 下分割线
     *
     * @param iconRes     icon图片
     * @param textContent 文字内容
     */
    public InspectionEditLineLayout Mine(int iconRes, String textContent, String textRight, boolean showArrow) {
        init(iconRes, textContent);
        setRightText(textRight);
        showArrow(showArrow);
        return this;
    }
    /**
     * icon + 文字 + edit + 下分割线
     *
     * @return
     */
    public InspectionEditLineLayout initItemWidthEdit(int iconRes, String textContent, String editHint) {
        init(iconRes, textContent);
        showEdit(true);
        setEditHint(editHint);
        showArrow(false);
        return this;
    }
    /**
     * icon + 文字 + edit +单位+ 下分割线+ 右侧拍照
     *
     * @return
     */
    public InspectionEditLineLayout initItemWidthEdit(int iconRes,String textContent , String textRight, String editHint) {
        init(iconRes, textContent);
        setRightText(textRight);
        showEdit(true);
        setEditHint(editHint);
        setIvRightIcon(R.drawable.takepic);
        foucsChange();
        return this;
    }


    /**
     * 文字 + 箭头
     *
     * @param textContent
     * @return
     */
    public InspectionEditLineLayout init(String textContent) {
        init();
        setTextContent(textContent);
        showEdit(false);
        setRightText("");
        showLeftIcon(false);
        return this;
    }
    /**
     * 初始化各个控件
     */
    public InspectionEditLineLayout init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_inspection_edit_line, this, true);
        llRoot =  findViewById(R.id.ll_root);
        dividerTop = findViewById(R.id.divider_top);
        dividerBottom = findViewById(R.id.divider_bottom);
        ivLeftIcon = findViewById(R.id.iv_left_icon);
        tvTextContent = findViewById(R.id.tv_text_content);
        editContent = findViewById(R.id.edit_content);
        tvRightText = findViewById(R.id.tv_right_text);
        ivRightIcon = findViewById(R.id.iv_right_icon);
        tvRightIconText = findViewById(R.id.iv_right_icon_text);
        return this;
    }

    /**
     * 默认情况下的样子  icon + 文字 + 右箭头 + 下分割线
     *
     * @param iconRes     icon图片
     * @param textContent 文字内容
     */
    public InspectionEditLineLayout init(int iconRes, String textContent) {
        init();
        showDivider(false, true);
        setLeftIcon(iconRes);
        setTextContent(textContent);
        showEdit(false);
        setRightText("");
        showArrow(true);
        return this;
    }


    //---------------------下面是对每个部分的一些设置     上面是应用中常用场景封装-----------------------//



    public int getPos(){
        return pos;
    }

    /**
     * 设置上下分割线的显示情况
     *
     * @return
     */
    public InspectionEditLineLayout showDivider(Boolean showDividerTop, Boolean showDivderBottom) {
        if (showDividerTop) {
            dividerTop.setVisibility(VISIBLE);
        } else {
            dividerTop.setVisibility(GONE);
        }
        if (showDivderBottom) {
            dividerBottom.setVisibility(VISIBLE);
        } else {
            dividerBottom.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置上分割线的颜色
     *
     * @return
     */
    public InspectionEditLineLayout setDividerTopColor(int dividerTopColorRes) {
        dividerTop.setBackgroundColor(getResources().getColor(dividerTopColorRes));
        return this;
    }


    /**
     * 设置下分割线的颜色
     *
     * @return
     */
    public InspectionEditLineLayout setDividerBottomColor(int dividerBottomColorRes) {
        dividerBottom.setBackgroundColor(getResources().getColor(dividerBottomColorRes));
        return this;
    }


    /**
     * 设置左边Icon
     *
     * @param iconRes
     */
    public InspectionEditLineLayout setLeftIcon(int iconRes) {
        if (iconRes==0){
            ivLeftIcon.setVisibility(GONE);
        }else {
            ivLeftIcon.setImageResource(iconRes);
        }

        return this;
    }

    /**
     * 设置左边Icon显示与否
     *
     * @param showLeftIcon
     */
    public InspectionEditLineLayout showLeftIcon(boolean showLeftIcon) {
        if (showLeftIcon) {
            ivLeftIcon.setVisibility(VISIBLE);
        } else {
            ivLeftIcon.setVisibility(GONE);
        }
        return this;
    }


    /**
     * 设置中间的文字内容
     *
     * @param
     * @return
     */
    public String getLeftText() {
        return tvTextContent.getText().toString();
    }

    /**
     * 设置中间的文字内容
     *
     * @param textContent
     * @return
     */
    public InspectionEditLineLayout setTextContent(String textContent) {
        tvTextContent.setText(textContent);
        return this;
    }

    public InspectionEditLineLayout setTextContent(Spanned spanned){
        tvTextContent.setText(spanned);
        return this;
    }

//    public InspectionEditLineLayout setTextContent(SpannableString string){
//        tvTextContent.setText(string);
//        return this;
//    }

    /**
     * 设置中间的文字颜色
     *
     * @return
     */
    public InspectionEditLineLayout setTextContentColor(int colorRes) {
        tvTextContent.setTextColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置中间的文字大小
     *
     * @return
     */
    public InspectionEditLineLayout setTextContentSize(int textSizeSp) {
        tvTextContent.setTextSize(textSizeSp);
        return this;
    }

    /**
     * 设置左边文字内容
     *
     * @return
     */
    public InspectionEditLineLayout setLeftText(String rightText) {
        tvTextContent.setText(rightText);
        return this;
    }

    /**
     * 设置右边文字内容
     *
     * @return
     */
    public InspectionEditLineLayout setRightText(String rightText) {
        tvRightText.setText(rightText);
        return this;
    }
    public EditText getEdit() {
        return editContent;
    }

    public String getEditText() {
        return editContent.getText().toString();
    }
    public String getRightText() {
        return tvRightText.getText().toString();
    }


    /**
     * 设置右边文字颜色
     *
     * @return
     */
    public InspectionEditLineLayout setRightTextColor(int colorRes) {
        tvRightText.setTextColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置右边文字大小
     *
     * @return
     */
    public InspectionEditLineLayout setRightTextSize(int textSize) {
        tvRightText.setTextSize(textSize);
        return this;
    }

    /**
     * 设置右箭头的显示与不显示
     *
     * @param showArrow
     */
    public InspectionEditLineLayout showArrow(boolean showArrow) {
        if (showArrow) {
            ivRightIcon.setVisibility(VISIBLE);
        } else {
            ivRightIcon.setVisibility(INVISIBLE);
        }
        return this;
    }

    /**
     * 获取右边icon
     */
    public InspectionEditLineLayout setIvRightIcon(int iconRes) {

        ivRightIcon.setImageResource(iconRes);

        return this;
    }

    /**
     * 获取右边icon
     */
    public ImageView getIvRightIcon() {

        return ivRightIcon;
    }

    public TextView getTvRightIconText() {
        return tvRightIconText;
    }
    /**
     * 判断有面已拍照文字是否显示
     */
    public InspectionEditLineLayout showRightIconText(boolean show){
        if (show) {
            tvRightIconText.setVisibility(VISIBLE);
        } else {
            tvRightIconText.setVisibility(INVISIBLE);
        }
        return this;
    }

    /**
     * 设置中间的输入框显示与否
     *
     * @param showEdit
     * @return
     */
    public InspectionEditLineLayout showEdit(boolean showEdit) {
        if (showEdit) {
            editContent.setVisibility(VISIBLE);
        } else {
            editContent.setVisibility(GONE);
        }
        return this;
    }


    /**
     * 设置中间的输入框 是否可输入
     *
     * @param editable
     * @return
     */
    public InspectionEditLineLayout setEditable(boolean editable) {
        editContent.setFocusable(editable);
        return this;
    }


    /**
     * 设置中间的输入框hint内容
     *
     * @param showEditHint
     * @return
     */
    public InspectionEditLineLayout setEditHint(String showEditHint) {
        editContent.setHint(showEditHint);
        return this;
    }

    /**
     * 设置中间的输入框内容
     *
     * @param s
     * @return
     */
    public InspectionEditLineLayout setEditContent(String s) {
        editContent.setText(s);
        return this;
    }

    /**
     * 获取Edit输入的内容
     *
     * @param
     * @return
     */
    public String getEditContent() {
        return String.valueOf(editContent.getText());

    }


    /**
     * 设置 edit 颜色
     *
     * @param colorRes
     * @return
     */
    public InspectionEditLineLayout setEditColor(int colorRes) {
        editContent.setTextColor(getResources().getColor(colorRes));
        return this;
    }
    public InspectionEditLineLayout setEditgravityEnd() {
        editContent.setGravity(Gravity.END);
        return this;
    }

    public InspectionEditLineLayout setEditType(int type) {
        editContent.setInputType(type);
        return this;
    }
    public void foucsChange(){
        editContent.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (onFoucsChangeListener!=null){
                    onFoucsChangeListener.onFoucsChange();
                }
            }
        });
//        if (editContent.hasFocus()&&editContent.isFocusable()&&editContent.isFocusableInTouchMode()){
//            return true;
//        }
//        return false;
    }


    public InspectionEditLineLayout setFoucsChangeListener(final OnFoucsChangeListener onFoucsChangeListener) {
        this.onFoucsChangeListener = onFoucsChangeListener;
        return this;
    }
    /**
     * 设置 edit 字体大小
     *
     * @param editSizeSp
     * @return
     */
    public InspectionEditLineLayout setEditSize(int editSizeSp) {
        editContent.setTextSize(editSizeSp);
        return this;
    }
    //----------------------下面是一些点击事件

    public InspectionEditLineLayout setOnRootClickListener(final OnRootClickListener onRootClickListener, final int tag) {
        llRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llRoot.setTag(tag);
                onRootClickListener.onRootClick(llRoot);
            }
        });
        return this;
    }

    public InspectionEditLineLayout setOnArrowClickListener(final OnArrowClickListener onArrowClickListener, final int tag) {

        ivRightIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ivRightIcon.setTag(tag);
                onArrowClickListener.onArrowClick(ivRightIcon);
            }
        });
        return this;
    }

    public InspectionEditLineLayout setEnable(boolean is){
        llRoot.setClickable(is);
        return  this;
    }

}

