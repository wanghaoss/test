package com.example.new_androidclient.work.bean;

public class JSABean {
    private Integer id; //jsaId
    private Integer planWorkId;
    private Integer applicationId;
    private Integer projectLeader;
    private String jsaMenmber; //JSA成员
    private String wcMedium;//介质
    private String wcTemperature;//温度
    private String wcPressure;//压力
    private String wcOther;//其他
    private String involvedSpecialWork;//项目涉及的特殊作业

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanWorkId() {
        return planWorkId;
    }

    public void setPlanWorkId(Integer planWorkId) {
        this.planWorkId = planWorkId;
    }

    public String getJsaMenmber() {
        return jsaMenmber;
    }

    public void setJsaMenmber(String jsaMenmber) {
        this.jsaMenmber = jsaMenmber;
    }

    public String getWcMedium() {
        return wcMedium;
    }

    public void setWcMedium(String wcMedium) {
        this.wcMedium = wcMedium;
    }

    public String getWcTemperature() {
        return wcTemperature;
    }

    public void setWcTemperature(String wcTemperature) {
        this.wcTemperature = wcTemperature;
    }

    public String getWcPressure() {
        return wcPressure;
    }

    public void setWcPressure(String wcPressure) {
        this.wcPressure = wcPressure;
    }

    public String getWcOther() {
        return wcOther;
    }

    public void setWcOther(String wcOther) {
        this.wcOther = wcOther;
    }

    public String getInvolvedSpecialWork() {
        return involvedSpecialWork;
    }

    public void setInvolvedSpecialWork(String involvedSpecialWork) {
        this.involvedSpecialWork = involvedSpecialWork;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(Integer projectLeader) {
        this.projectLeader = projectLeader;
    }
}
