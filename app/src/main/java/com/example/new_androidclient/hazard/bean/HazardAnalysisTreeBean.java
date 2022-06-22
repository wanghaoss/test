package com.example.new_androidclient.hazard.bean;

import java.util.List;

public class HazardAnalysisTreeBean {


    private List<Integer> expandedKeys;
    private List<HazardAnalysisTreeSubClassBean> treeData;

    public List<Integer> getExpandedKeys() {
        return expandedKeys;
    }

    public void setExpandedKeys(List<Integer> expandedKeys) {
        this.expandedKeys = expandedKeys;
    }

    public List<HazardAnalysisTreeSubClassBean> getTreeData() {
        return treeData;
    }

    public void setTreeData(List<HazardAnalysisTreeSubClassBean> treeData) {
        this.treeData = treeData;
    }


}
