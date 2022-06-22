package com.etuo.kucun.model.common;

/**
 * Created by yhn on 2020/3/6.
 * 货物验收
 */

public class ReturnReasonListBean {


    /**
     * name : 数量不符
     * value : 1
     * prefix :
     * suffix :
     * onClickJs :
     */

    private String name;
    private String value;
    private String prefix;
    private String suffix;
    private String onClickJs;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getOnClickJs() {
        return onClickJs;
    }

    public void setOnClickJs(String onClickJs) {
        this.onClickJs = onClickJs;
    }
}
