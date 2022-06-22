package com.example.new_androidclient.device_management.bean;

public class DevicePlanListBean {


    /**
     * temporaryId : 240
     * deviceName : 聚合1
     * dptName : 四车间
     * factoryName : 丹东明珠
     * problemDesc : null 问题描述
     * handleUserName : null
     * planStatus : 未下达
     * unitName : 聚合单元
     * rejectReason : null  拒绝原因
     */

    private int temporaryId;
    private String deviceName;
    private String dptName;
    private String factoryName;
    private Object problemDesc;
    private Object handleUserName;
    private String planStatus;
    private String unitName;
    private Object rejectReason;

    public int getTemporaryId() {
        return temporaryId;
    }

    public void setTemporaryId(int temporaryId) {
        this.temporaryId = temporaryId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDptName() {
        return dptName;
    }

    public void setDptName(String dptName) {
        this.dptName = dptName;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public Object getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(Object problemDesc) {
        this.problemDesc = problemDesc;
    }

    public Object getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(Object handleUserName) {
        this.handleUserName = handleUserName;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Object getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(Object rejectReason) {
        this.rejectReason = rejectReason;
    }
}
