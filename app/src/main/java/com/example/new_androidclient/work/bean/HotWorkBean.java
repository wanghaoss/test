package com.example.new_androidclient.work.bean;

import java.io.Serializable;
import java.util.Date;

public class HotWorkBean implements Serializable {
    private Integer id;

    private Integer applicationId;

    private Integer mediumId;

    /**
     * 分析点名称
     */
    private String siteName;

    /**
     * 检测方式
     */
    private String testMode;

    /**
     * 分析项目
     */
    private String analysisItem;

    /**
     * 合格标准
     */
    private String standardValue;

    /**
     * 分析数据
     */
    private String analysisValue;

    /**
     * 分析人
     */
    private String analysisUser;

    /**
     * 分析结论
     */
    private String analysisResult;

    /**
     * 动火分析时间
     */
    private String analysisTime;

    /**
     * 备注说明
     */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getTestMode() {
        return testMode;
    }

    public void setTestMode(String testMode) {
        this.testMode = testMode;
    }

    public String getAnalysisItem() {
        return analysisItem;
    }

    public void setAnalysisItem(String analysisItem) {
        this.analysisItem = analysisItem;
    }

    public String getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(String standardValue) {
        this.standardValue = standardValue;
    }

    public String getAnalysisValue() {
        return analysisValue;
    }

    public void setAnalysisValue(String analysisValue) {
        this.analysisValue = analysisValue;
    }

    public String getAnalysisUser() {
        return analysisUser;
    }

    public void setAnalysisUser(String analysisUser) {
        this.analysisUser = analysisUser;
    }

    public String getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(String analysisResult) {
        this.analysisResult = analysisResult;
    }

    public String getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(String analysisTime) {
        this.analysisTime = analysisTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getMediumId() {
        return mediumId;
    }

    public void setMediumId(Integer mediumId) {
        this.mediumId = mediumId;
    }
}
