package com.example.new_androidclient.work.bean;

public class WorkIngToDoTwoBean {

    /**
     * id : 170
     * applicationId : 190
     * licenceNo : XK-2020-Z001-014-001-DH
     * workingStartTime : 2020-12-07 00:00:00
     * workingEndTime : 2020-12-29 00:00:00
     * status : 01
     * statusName : 作业申请
     * bigStatusName : 待许可
     * bigStatus : 31
     * workingType : DH
     * instName : 聚合装置
     * areaName : 框架一层
     * deviceName : 水洗罐1
     * tagNo : R-301A
     * tenantId : 13
     * ticketFlag 级别
     */

    private int id;
    private int applicationId;
    private String licenceNo;
    private String workingStartTime;
    private String workingEndTime;
    private String status;
    private String statusName;
    private String bigStatusName;
    private int bigStatus;
    private String workingType;
    private String instName;
    private String areaName;
    private String deviceName;
    private String tagNo;
    private int tenantId;
    private String ticketFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getWorkingStartTime() {
        return workingStartTime;
    }

    public void setWorkingStartTime(String workingStartTime) {
        this.workingStartTime = workingStartTime;
    }

    public String getWorkingEndTime() {
        return workingEndTime;
    }

    public void setWorkingEndTime(String workingEndTime) {
        this.workingEndTime = workingEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getBigStatusName() {
        return bigStatusName;
    }

    public void setBigStatusName(String bigStatusName) {
        this.bigStatusName = bigStatusName;
    }

    public int getBigStatus() {
        return bigStatus;
    }

    public void setBigStatus(int bigStatus) {
        this.bigStatus = bigStatus;
    }

    public String getWorkingType() {
        return workingType;
    }

    public void setWorkingType(String workingType) {
        this.workingType = workingType;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getTicketFlag() {
        return ticketFlag;
    }

    public void setTicketFlag(String ticketFlag) {
        this.ticketFlag = ticketFlag;
    }
}
