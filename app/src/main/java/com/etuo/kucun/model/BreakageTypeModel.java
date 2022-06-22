package com.etuo.kucun.model;

/**
 * Created by Administrator on 2018/11/26.
 */

public class BreakageTypeModel {

    private String typeName ;// 报损类型的名称
    private String position_index;//当前的选择的类型

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPosition_index() {
        return position_index;
    }

    public void setPosition_index(String position_index) {
        this.position_index = position_index;
    }
}
