package com.example.new_androidclient.work.bean;

import java.util.Date;

public class HoldBean {
    private Integer id; //踏勘Id
    private Integer planWorkId;
    private Integer applicationId;
    private String surveyMember; //踏勘成员
    private Integer workLeader;
    private String reviewContent; //作业内容现场复核
    private String projectInvolved;//涉及的地下隐藏工程
    private String affectedCrossWork;//受影响的交叉作业

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

    public String getSurveyMember() {
        return surveyMember;
    }

    public void setSurveyMember(String surveyMember) {
        this.surveyMember = surveyMember;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getProjectInvolved() {
        return projectInvolved;
    }

    public void setProjectInvolved(String projectInvolved) {
        this.projectInvolved = projectInvolved;
    }

    public String getAffectedCrossWork() {
        return affectedCrossWork;
    }

    public void setAffectedCrossWork(String affectedCrossWork) {
        this.affectedCrossWork = affectedCrossWork;
    }

    public Integer getWorkLeader() {
        return workLeader;
    }

    public void setWorkLeader(Integer workLeader) {
        this.workLeader = workLeader;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }
}
