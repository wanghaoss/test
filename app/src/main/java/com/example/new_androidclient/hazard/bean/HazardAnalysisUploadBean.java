package com.example.new_androidclient.hazard.bean;

public class HazardAnalysisUploadBean {

    private int id;
    private String riskL;
    private String riskE;
    private String riskC;
    private String riskD;
    private String hazardCategory;
    private String hazardGrade;
    private String hazardFactor;
    private String isReported;
    private String belongDept;
    private String analysisPerson;
    private String analysisTime;
    private String deviceId;
//    private String IsMajorAccident;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRiskD() {
        return riskD;
    }

//    public void setIsMajorAccident(String isMajorAccident) {
//        IsMajorAccident = isMajorAccident;
//    }

    public void setRiskL(String riskL) {
        this.riskL = riskL;
    }

    public void setRiskE(String riskE) {
        this.riskE = riskE;
    }

    public void setRiskC(String riskC) {
        this.riskC = riskC;
    }

    public void setRiskD(String riskD) {
        this.riskD = riskD;
    }

    public void setHazardCategory(String hazardCategory) {
        this.hazardCategory = hazardCategory;
    }

    public void setHazardGrade(String hazardGrade) {
        this.hazardGrade = hazardGrade;
    }

    public void setHazardFactor(String hazardFactor) {
        this.hazardFactor = hazardFactor;
    }

    public void setIsReported(String isReported) {
        this.isReported = isReported;
    }

    public void setBelongDept(String belongDept) {
        this.belongDept = belongDept;
    }

    public void setAnalysisPerson(String analysisPerson) {
        this.analysisPerson = analysisPerson;
    }

    public void setAnalysisTime(String analysisTime) {
        this.analysisTime = analysisTime;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getRiskL() {
        return riskL;
    }

    public String getRiskE() {
        return riskE;
    }

    public String getRiskC() {
        return riskC;
    }

    public String getHazardCategory() {
        return hazardCategory;
    }

    public String getHazardGrade() {
        return hazardGrade;
    }

    public String getHazardFactor() {
        return hazardFactor;
    }

    public String getIsReported() {
        return isReported;
    }

    public String getBelongDept() {
        return belongDept;
    }

    public String getAnalysisPerson() {
        return analysisPerson;
    }

    public String getAnalysisTime() {
        return analysisTime;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
