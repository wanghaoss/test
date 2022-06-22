package com.example.new_androidclient.work.bean;

public class WorkGCApplicationBean extends WorkApplicationBaseBean {

    /**
     * workingPlace : null
     * areaTag : null
     * workingHeight : null
     * riskFactor : null
     * mgtLevel : null
     * workingContent : null
     * riskIdentifiy : null
     * isSpecialWork : null
     * specialWorkType : null
     * isSpecialEquipWork : null
     * specialEquipWork : null
     * workingPerson : null
     * classification : null
     */

    private String workingPlace;
    private String areaTag;
    private String workingHeight;
    private String riskFactor; //引起坠落客观危险因素
    private String mgtLevel;  //管理级别
    private String workingContent;
    private String isSpecialWork;  //是否特种作业
    private String specialWorkType; //特种作业类型
    private String isSpecialEquipWork; //是否是特种设备 0否1是
    private String specialEquipWork;  //特种设备
    private String workingPerson;  //作业人
    private String classification; //分类法


    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getAreaTag() {
        return areaTag;
    }

    public void setAreaTag(String areaTag) {
        this.areaTag = areaTag;
    }

    public String getWorkingHeight() {
        if(workingHeight == null){
            return "请选择";
        }
        return workingHeight;
    }

    public void setWorkingHeight(String workingHeight) {
        this.workingHeight = workingHeight;
    }

    public String getRiskFactor() {
        if(riskFactor == null){
            return "请选择";
        }
        return riskFactor;
    }

    public void setRiskFactor(String riskFactor) {
        this.riskFactor = riskFactor;
    }

    public String getMgtLevel() {
        if(mgtLevel == null){
            return "";
        }
        return mgtLevel;
    }

    public void setMgtLevel(String mgtLevel) {
        this.mgtLevel = mgtLevel;
    }

    public String getWorkingContent() {
        return workingContent;
    }

    public void setWorkingContent(String workingContent) {
        this.workingContent = workingContent;
    }

    public String getIsSpecialWork() {
        return isSpecialWork;
    }

    public void setIsSpecialWork(String isSpecialWork) {
        this.isSpecialWork = isSpecialWork;
    }

    public String getSpecialWorkType() {
        return specialWorkType;
    }

    public void setSpecialWorkType(String specialWorkType) {
        this.specialWorkType = specialWorkType;
    }

    public String getIsSpecialEquipWork() {
        return isSpecialEquipWork;
    }

    public void setIsSpecialEquipWork(String isSpecialEquipWork) {
        this.isSpecialEquipWork = isSpecialEquipWork;
    }

    public String getSpecialEquipWork() {
        return specialEquipWork;
    }

    public void setSpecialEquipWork(String specialEquipWork) {
        this.specialEquipWork = specialEquipWork;
    }

    public String getWorkingPerson() {
        return workingPerson;
    }

    public void setWorkingPerson(String workingPerson) {
        this.workingPerson = workingPerson;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
