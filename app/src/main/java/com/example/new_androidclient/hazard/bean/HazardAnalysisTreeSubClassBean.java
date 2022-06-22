package com.example.new_androidclient.hazard.bean;

import java.util.List;

public class HazardAnalysisTreeSubClassBean {
    int sorted;
    List<HazardAnalysisTreeSubClassBean> children;
    int id;
    String label;
    String type;
    int parentId;
    String desc;

    public int getSorted() {
        return sorted;
    }

    public void setSorted(int sorted) {
        this.sorted = sorted;
    }

    public List<HazardAnalysisTreeSubClassBean> getChildren() {
        return children;
    }

    public void setChildren(List<HazardAnalysisTreeSubClassBean> children) {
        this.children = children;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
