package com.example.new_androidclient.work.bean;

public class WorkDZApplicationBean extends WorkApplicationBaseBean {
    /**
     * currentStatus : null
     * workingContent : null
     * dzSite : null
     * singleMaxWeight : null
     * ratedWeight : null
     * equipType : null
     * workingLevel : null
     * isUpgradeMgt : null
     * riskIdentify : null
     */

    private String workingContent;
    private String dzSite;
    private String singleMaxWeight;
    private Float ratedWeight;
    private String equipType;
    private String workingLevel;
    private String isUpgradeMgt;

    public String getWorkingContent() {
        return workingContent;
    }

    public void setWorkingContent(String workingContent) {
        this.workingContent = workingContent;
    }

    public String getDzSite() {
        return dzSite;
    }

    public void setDzSite(String dzSite) {
        this.dzSite = dzSite;
    }

    public String getSingleMaxWeight() {
        return singleMaxWeight;
    }

    public void setSingleMaxWeight(String singleMaxWeight) {
        this.singleMaxWeight = singleMaxWeight;
    }

    public Float getRatedWeight() {
        return ratedWeight;
    }

    public void setRatedWeight(Float ratedWeight) {
        this.ratedWeight = ratedWeight;
    }

    public String getEquipType() {
        if (equipType == null) {
            return "请选择";
        }
        return equipType;
    }

    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    public String getWorkingLevel() {
        if (workingLevel == null) {
            return "请选择";
        }
        switch (workingLevel) {
            case "01":
                return "一级";
            case "02":
                return "二级";
            case "03":
                return "三级";
            case "04":
                return "四级";
            default:
                return "";
        }
    }

    public void setWorkingLevel(String workingLevel) {
        this.workingLevel = workingLevel;
    }

    public String getIsUpgradeMgt() {
        return isUpgradeMgt;
    }

    public void setIsUpgradeMgt(String isUpgradeMgt) {
        this.isUpgradeMgt = isUpgradeMgt;
    }
}
