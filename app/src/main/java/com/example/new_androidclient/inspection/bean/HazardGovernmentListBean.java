package com.example.new_androidclient.inspection.bean;

public class HazardGovernmentListBean {


    /**
     * id : 238
     * hazardName : 1414
     * hazardContent : 14141
     * isMajorAccident : 0
     * rectificationResult : null
     * actualFinishedTime : null
     * investigationTime : 2020-09-07 00:00:00
     * rectificationDesc : null
     * rectificationChargePersonName : 刘华
     * rectificationChargePerson : 3
     */

    private int id;
    private String hazardName;
    private String hazardContent;
    private String isMajorAccident;
    private String rectificationResult;
    private String actualFinishedTime;
    private String investigationTime;
    private String rectificationDesc;
    private String rectificationChargePersonName;
    private String rectificationChargePerson;

    public HazardGovernmentListBean() {
    }

    public HazardGovernmentListBean(int id, String hazardName, String hazardContent, String isMajorAccident, String rectificationResult, String actualFinishedTime, String investigationTime, String rectificationDesc, String rectificationChargePersonName, String rectificationChargePerson) {
        this.id = id;
        this.hazardName = hazardName;
        this.hazardContent = hazardContent;
        this.isMajorAccident = isMajorAccident;
        this.rectificationResult = rectificationResult;
        this.actualFinishedTime = actualFinishedTime;
        this.investigationTime = investigationTime;
        this.rectificationDesc = rectificationDesc;
        this.rectificationChargePersonName = rectificationChargePersonName;
        this.rectificationChargePerson = rectificationChargePerson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHazardName() {
        return hazardName;
    }

    public void setHazardName(String hazardName) {
        this.hazardName = hazardName;
    }

    public String getHazardContent() {
        return hazardContent;
    }

    public void setHazardContent(String hazardContent) {
        this.hazardContent = hazardContent;
    }

    public String getIsMajorAccident() {
        return isMajorAccident;
    }

    public void setIsMajorAccident(String isMajorAccident) {
        this.isMajorAccident = isMajorAccident;
    }

    public String getRectificationResult() {
        return rectificationResult;
    }

    public String getInvestigationTime() {
        return investigationTime;
    }

    public void setInvestigationTime(String investigationTime) {
        this.investigationTime = investigationTime;
    }

    public void setRectificationResult(String rectificationResult) {
        this.rectificationResult = rectificationResult;
    }

    public String getActualFinishedTime() {
        return actualFinishedTime;
    }

    public void setActualFinishedTime(String actualFinishedTime) {
        this.actualFinishedTime = actualFinishedTime;
    }

    public void setRectificationDesc(String rectificationDesc) {
        this.rectificationDesc = rectificationDesc;
    }

    public String getRectificationDesc() {
        return rectificationDesc;
    }

    public String getRectificationChargePersonName() {
        return rectificationChargePersonName;
    }

    public void setRectificationChargePersonName(String rectificationChargePersonName) {
        this.rectificationChargePersonName = rectificationChargePersonName;
    }

    public String getRectificationChargePerson() {
        return rectificationChargePerson;
    }

    public void setRectificationChargePerson(String rectificationChargePerson) {
        this.rectificationChargePerson = rectificationChargePerson;
    }
}
