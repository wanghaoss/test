package com.example.new_androidclient.work.bean;

public class GasAnalysisBean {
    private Integer id;

    private Integer applicationId;

    /**
     * 有毒有害介质
     */
    private String ppmName;

    /**
     * 可燃气体%LEL
     */
    private String lelValue;

    /**
     * 氧含量%
     */
    private String oxygenValue;

    /**
     * 取样时间
     */
    private String samplingTime;

    /**
     * 取样部位
     */
    private String samplingPosition;

    /**
     * 分析项目
     */
    private String analysisItem;

    /**
     * 分析人
     */
    private String analysisUser;

    /**
     * 分析结论
     */
    private String analysisResult;

    /**
     * 分析时间
     */
    private String analysisTime;

    /**
     * 确认人
     */
    private String confirmUser;

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

    public String getPpmName() {
        return ppmName;
    }

    public void setPpmName(String ppmName) {
        this.ppmName = ppmName;
    }

    public String getLelValue() {
        return lelValue;
    }

    public void setLelValue(String lelValue) {
        this.lelValue = lelValue;
    }

    public String getOxygenValue() {
        return oxygenValue;
    }

    public void setOxygenValue(String oxygenValue) {
        this.oxygenValue = oxygenValue;
    }

    public String getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(String samplingTime) {
        this.samplingTime = samplingTime;
    }

    public String getSamplingPosition() {
        return samplingPosition;
    }

    public void setSamplingPosition(String samplingPosition) {
        this.samplingPosition = samplingPosition;
    }

    public String getAnalysisItem() {
        return analysisItem;
    }

    public void setAnalysisItem(String analysisItem) {
        this.analysisItem = analysisItem;
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

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }
}
