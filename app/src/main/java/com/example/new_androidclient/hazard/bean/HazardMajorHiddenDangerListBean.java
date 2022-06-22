package com.example.new_androidclient.hazard.bean;

public class HazardMajorHiddenDangerListBean {

    /**
     *
     *investigationTime 检查时间
     * investigationUser 检查人
     * hazardName 隐患名称.
     * hazardContent 隐患内容
    **/
    private String investigationTime;
    private String investigationUser;
    private String hazardName;
    private String hazardContent;

    public String getInvestigationTime() {
        return investigationTime;
    }

    public void setInvestigationTime(String investigationTime) {
        this.investigationTime = investigationTime;
    }

    public String getInvestigationUser() {
        return investigationUser;
    }

    public void setInvestigationUser(String investigationUser) {
        this.investigationUser = investigationUser;
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
}
