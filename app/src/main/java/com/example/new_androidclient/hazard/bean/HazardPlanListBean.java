package com.example.new_androidclient.hazard.bean;

public class HazardPlanListBean  {

    /**
     * id : 235
     * hazardName : 4
     * hazardContent : 4
     * isMajorAccident : null
     * hazardCategory : 一般隐患
     * hazardGrade : 班组级
     * rectificationPlan : null
     * rectificationPlanDoc : null
     * funding : null
     * fundingName : null
     * planFinishedDate : null
     * unfinishedReason : null
     * investigationTime : 2020-09-08 00:00:00
     * limitRectifyTime : 2020-09-09 00:00:00
     * rectificationChargePerson : null
     * rectificationChargePersonName :
     */

    private int id;
    private String hazardName;
    private String hazardContent;
    private String isMajorAccident;
    private String hazardCategory;
    private String hazardGrade;
    private String rectificationPlan;
    private String rectificationPlanDoc;
    private String funding;
    private String fundingName;
    private String planFinishedDate;
    private String unfinishedReason;
    private String investigationTime;
    private String limitRectifyTime;
    private String rectificationChargePerson;
    private String rectificationChargePersonName;

    public HazardPlanListBean() {
    }


    public HazardPlanListBean(int id, String hazardName, String hazardContent, String isMajorAccident, String hazardCategory, String hazardGrade, String rectificationPlan, String rectificationPlanDoc, String funding, String fundingName, String planFinishedDate, String unfinishedReason, String investigationTime, String limitRectifyTime, String rectificationChargePerson, String rectificationChargePersonName) {
        this.id = id;
        this.hazardName = hazardName;
        this.hazardContent = hazardContent;
        this.isMajorAccident = isMajorAccident;
        this.hazardCategory = hazardCategory;
        this.hazardGrade = hazardGrade;
        this.rectificationPlan = rectificationPlan;
        this.rectificationPlanDoc = rectificationPlanDoc;
        this.funding = funding;
        this.fundingName = fundingName;
        this.planFinishedDate = planFinishedDate;
        this.unfinishedReason = unfinishedReason;
        this.investigationTime = investigationTime;
        this.limitRectifyTime = limitRectifyTime;
        this.rectificationChargePerson = rectificationChargePerson;
        this.rectificationChargePersonName = rectificationChargePersonName;
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

    public String getHazardCategory() {
        return hazardCategory;
    }

    public void setHazardCategory(String hazardCategory) {
        this.hazardCategory = hazardCategory;
    }

    public String getHazardGrade() {
        return hazardGrade;
    }

    public void setHazardGrade(String hazardGrade) {
        this.hazardGrade = hazardGrade;
    }

    public String getRectificationPlan() {
        return rectificationPlan;
    }

    public void setRectificationPlan(String rectificationPlan) {
        this.rectificationPlan = rectificationPlan;
    }

    public String getRectificationPlanDoc() {
        return rectificationPlanDoc;
    }

    public void setRectificationPlanDoc(String rectificationPlanDoc) {
        this.rectificationPlanDoc = rectificationPlanDoc;
    }

    public String getFunding() {
        return funding;
    }

    public void setFunding(String funding) {
        this.funding = funding;
    }

    public String getFundingName() {
        return fundingName;
    }

    public void setFundingName(String fundingName) {
        this.fundingName = fundingName;
    }

    public String getPlanFinishedDate() {
        return planFinishedDate;
    }

    public void setPlanFinishedDate(String planFinishedDate) {
        this.planFinishedDate = planFinishedDate;
    }

    public String getUnfinishedReason() {
        return unfinishedReason;
    }

    public void setUnfinishedReason(String unfinishedReason) {
        this.unfinishedReason = unfinishedReason;
    }

    public String getInvestigationTime() {
        return investigationTime;
    }

    public void setInvestigationTime(String investigationTime) {
        this.investigationTime = investigationTime;
    }

    public String getLimitRectifyTime() {
        return limitRectifyTime;
    }

    public void setLimitRectifyTime(String limitRectifyTime) {
        this.limitRectifyTime = limitRectifyTime;
    }

    public String getRectificationChargePerson() {
        return rectificationChargePerson;
    }

    public void setRectificationChargePerson(String rectificationChargePerson) {
        this.rectificationChargePerson = rectificationChargePerson;
    }

    public String getRectificationChargePersonName() {
        return rectificationChargePersonName;
    }

    public void setRectificationChargePersonName(String rectificationChargePersonName) {
        this.rectificationChargePersonName = rectificationChargePersonName;
    }
}
