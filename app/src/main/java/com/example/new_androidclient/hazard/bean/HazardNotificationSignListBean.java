package com.example.new_androidclient.hazard.bean;

public class HazardNotificationSignListBean {

    /**
     * id : 271
     * planId : 97
     * hazardName : jia
     * hazardContent : 222
     * investigationUser : 宁晓光,王义成
     * investigationTime : 2020-08-04 11:22:54
     * limitRectifyTime : 2020-08-15 00:00:00
     */

    private int id;
    private int planId;
    private String hazardName;
    private String hazardContent;
    private String investigationUser;
    private String investigationTime;
    private String limitRectifyTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
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

    public String getInvestigationUser() {
        return investigationUser;
    }

    public void setInvestigationUser(String investigationUser) {
        this.investigationUser = investigationUser;
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
}
