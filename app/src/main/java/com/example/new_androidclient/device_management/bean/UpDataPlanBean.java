package com.example.new_androidclient.device_management.bean;

import java.util.Date;

public class UpDataPlanBean {
    /**
     * 计划名称
     */
    private String temporaryName;
    /**
     * 工单类别（抢修/未抢修）
     */
    private String planExtType;
    /**
     * 描述
     */
    private String description;
    /**
     * 起始时间
     */
    private String planStartTime;
    /**
     * 结束时间
     */
    private String planEndTime;
    /**
     * 项目负责人
     */
    private Integer projectLeader;
    /**
     * 作业承包商
     */
    private Integer workContractor;
    /**
     * 作业负责人
     */
    private String workLeaderName;

    /**
     * 预算值
     */
    private float budget;

    private int temporaryId;

    public int getTemporaryId() {
        return temporaryId;
    }

    public void setTemporaryId(int temporaryId) {
        this.temporaryId = temporaryId;
    }

    public String getTemporaryName() {
        return temporaryName;
    }

    public void setTemporaryName(String temporaryName) {
        this.temporaryName = temporaryName;
    }

    public String getPlanExtType() {
        return planExtType;
    }

    public void setPlanExtType(String planExtType) {
        this.planExtType = planExtType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public Integer getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(Integer projectLeader) {
        this.projectLeader = projectLeader;
    }

    public Integer getWorkContractor() {
        return workContractor;
    }

    public void setWorkContractor(Integer workContractor) {
        this.workContractor = workContractor;
    }

    public String getWorkLeaderName() {
        return workLeaderName;
    }

    public void setWorkLeaderName(String workLeaderName) {
        this.workLeaderName = workLeaderName;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }
}
