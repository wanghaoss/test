package com.example.new_androidclient.hazard.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class HazardVerificationDetailBean extends BaseObservable {
    private Integer id;
    private String hazardName; //隐患名
    private String hazardContent; //隐患内容
    private String completion; //整改完成情况 0未完成 1完成
    private String acceptanceDesc; //验收说明
    private String acceptanceDoc; //验收文档
    private String acceptanceTime;//验收时间
    private String acceptancePerson;//验收负责人
    private String planFinishedDate;//整改完成时间
    private String status;//状态

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Bindable
    public String getHazardName() {
        return hazardName;
    }

    public void setHazardName(String hazardName) {
        this.hazardName = hazardName;
    }

    @Bindable
    public String getHazardContent() {
        return hazardContent;
    }

    public void setHazardContent(String hazardContent) {
        this.hazardContent = hazardContent;
    }

    @Bindable
    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    @Bindable
    public String getAcceptanceDesc() {
        return acceptanceDesc;
    }

    public void setAcceptanceDesc(String acceptanceDesc) {
        this.acceptanceDesc = acceptanceDesc;
    }

    @Bindable
    public String getAcceptanceDoc() {
        return acceptanceDoc;
    }

    public void setAcceptanceDoc(String acceptanceDoc) {
        this.acceptanceDoc = acceptanceDoc;
    }

    @Bindable
    public String getAcceptanceTime() {
        return acceptanceTime;
    }

    public void setAcceptanceTime(String acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
    }

    @Bindable
    public String getAcceptancePerson() {
        return acceptancePerson;
    }

    public void setAcceptancePerson(String acceptancePerson) {
        this.acceptancePerson = acceptancePerson;
    }

    @Bindable
    public String getPlanFinishedDate() {

        return planFinishedDate;
    }

    public void setPlanFinishedDate(String planFinishedDate) {
        this.planFinishedDate = planFinishedDate;
    }

    public void updateData(){
        notifyChange();
    }
}
