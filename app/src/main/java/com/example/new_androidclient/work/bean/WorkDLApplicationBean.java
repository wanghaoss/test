package com.example.new_androidclient.work.bean;

public class WorkDLApplicationBean extends WorkApplicationBaseBean {

    /**
     * workingContent : null
     * workingSite : null
     * referDept : null
     * dlReason : null
     * instructions : null
     * isUpgradeMgt : null
     * riskIdentify : null
     */

    private String workingContent;
    private String workingSite;
    private String referDept;
    private String dlReason;
    private String instructions;
    private String isUpgradeMgt;

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

    public String getReferDept() {
        return referDept;
    }

    public void setReferDept(String referDept) {
        this.referDept = referDept;
    }

    public String getDlReason() {
        return dlReason;
    }

    public void setDlReason(String dlReason) {
        this.dlReason = dlReason;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getIsUpgradeMgt() {
        return isUpgradeMgt;
    }

    public void setIsUpgradeMgt(String isUpgradeMgt) {
        this.isUpgradeMgt = isUpgradeMgt;
    }
}
