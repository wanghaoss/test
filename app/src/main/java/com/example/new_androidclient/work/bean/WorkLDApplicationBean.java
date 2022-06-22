package com.example.new_androidclient.work.bean;

public class WorkLDApplicationBean extends WorkApplicationBaseBean {

    /**
     * workingContent : null
     * workingSite : null
     * workingVoltage : null
     * powerAccessPoint : null
     * permitKw : null
     * elecEquipKw : null
     * riskIdentify : null
     * isFireAnalysis : null
     * lelMedium : null
     * isUpgradeMgt : null
     * approveResult : null
     */

    private String workingContent;
    private String workingSite;
    private String workingVoltage;
    private String powerAccessPoint;
    private String permitKw;
    private String elecEquipKw;
    private String isFireAnalysis;
    private String lelMedium;
    private String isUpgradeMgt;
    private String approveResult;

    public String getWorkingContent() {
        return workingContent;
    }

    public void setWorkingContent(String workingContent) {
        this.workingContent = workingContent;
    }

    public String getWorkingSite() {
        return workingSite;
    }

    public void setWorkingSite(String workingSite) {
        this.workingSite = workingSite;
    }

    public String getWorkingVoltage() {
        if (workingVoltage == null) {
            return "请选择";
        }
        return workingVoltage;
    }

    public void setWorkingVoltage(String workingVoltage) {
        this.workingVoltage = workingVoltage;
    }

    public String getPowerAccessPoint() {
        return powerAccessPoint;
    }

    public void setPowerAccessPoint(String powerAccessPoint) {
        this.powerAccessPoint = powerAccessPoint;
    }

    public String getPermitKw() {
        return permitKw;
    }

    public void setPermitKw(String permitKw) {
        this.permitKw = permitKw;
    }

    public String getElecEquipKw() {
        return elecEquipKw;
    }

    public void setElecEquipKw(String elecEquipKw) {
        this.elecEquipKw = elecEquipKw;
    }

    public String getIsFireAnalysis() {
        return isFireAnalysis;
    }

    public void setIsFireAnalysis(String isFireAnalysis) {
        this.isFireAnalysis = isFireAnalysis;
    }

    public String getLelMedium() {
        return lelMedium;
    }

    public void setLelMedium(String lelMedium) {
        this.lelMedium = lelMedium;
    }

    public String getIsUpgradeMgt() {
        return isUpgradeMgt;
    }

    public void setIsUpgradeMgt(String isUpgradeMgt) {
        this.isUpgradeMgt = isUpgradeMgt;
    }

    public String getApproveResult() {
        return approveResult;
    }

    public void setApproveResult(String approveResult) {
        this.approveResult = approveResult;
    }
}
