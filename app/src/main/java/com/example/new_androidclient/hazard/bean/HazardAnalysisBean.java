package com.example.new_androidclient.hazard.bean;

public class HazardAnalysisBean {

    /**
     * id : 333
     * hazardName : 1
     * hazardContent : 1
     * isMajorAccident : 0
     * riskL : 10
     * risklName : 完全可以预料
     * riskE : 10
     * riskeName : 连续暴露
     * riskC : 7
     * riskcName : 严重
     * riskD : 700
     * hazardCategory : 11
     * hazardCategoryName : 重大隐患
     * hazardGrade : 4
     * hazardGradeName : null
     * hazardFactor : 21
     * hazardFactorName : 安全管理缺陷
     * isReported : 0
     * isReportedName : null
     * belongDeptName : 海川农化
     * belongDept : 3
     * analysisPersonName : 张伟
     * analysisPerson : 10
     * analysisTime : 2020-07-28 00:00:00
     * deviceName : null
     * deviceId : null
     */

    private int id;
    private String hazardName;
    private String hazardContent;
    private String isMajorAccident;
    private String riskL;
    private String risklName;
    private String riskE;
    private String riskeName;
    private String riskC;
    private String riskcName;
    private String riskD;
    private String hazardCategory;
    private String hazardCategoryName;
    private String hazardGrade;
    private String hazardGradeName;
    private String hazardFactor;
    private String hazardFactorName;
    private String isReported;
    private String isReportedName;
    private String belongDeptName;
    private String belongDept;
    private String analysisPersonName;
    private String analysisPerson;
    private String analysisTime;
    private String deviceName;
    private String deviceId;

    public HazardAnalysisBean() {
    }

    public HazardAnalysisBean(int id, String hazardName, String hazardContent, String isMajorAccident, String riskL, String risklName, String riskE, String riskeName, String riskC, String riskcName, String riskD, String hazardCategory, String hazardCategoryName, String hazardGrade, String hazardGradeName, String hazardFactor, String hazardFactorName, String isReported, String isReportedName, String belongDeptName, String belongDept, String analysisPersonName, String analysisPerson, String analysisTime, String deviceName, String deviceId) {

        this.id = id;
        this.hazardName = hazardName;
        this.hazardContent = hazardContent;
        this.isMajorAccident = isMajorAccident;
        this.riskL = riskL;
        this.risklName = risklName;
        this.riskE = riskE;
        this.riskeName = riskeName;
        this.riskC = riskC;
        this.riskcName = riskcName;
        this.riskD = riskD;
        this.hazardCategory = hazardCategory;
        this.hazardCategoryName = hazardCategoryName;
        this.hazardGrade = hazardGrade;
        this.hazardGradeName = hazardGradeName;
        this.hazardFactor = hazardFactor;
        this.hazardFactorName = hazardFactorName;
        this.isReported = isReported;
        this.isReportedName = isReportedName;
        this.belongDeptName = belongDeptName;
        this.belongDept = belongDept;
        this.analysisPersonName = analysisPersonName;
        this.analysisPerson = analysisPerson;
        this.analysisTime = analysisTime;
        this.deviceName = deviceName;
        this.deviceId = deviceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getIsMajorAccident() {
        return isMajorAccident;
    }

    public void setIsMajorAccident(String isMajorAccident) {
        this.isMajorAccident = isMajorAccident;
    }

    public String getRiskL() {
        if (null == riskL) {
            return "";
        } else {
            return riskL;
        }
    }

    public void setRiskL(String riskL) {
        this.riskL = riskL;
    }

    public String getRisklName() {
        if (null == risklName) {
            return "";
        } else {
            return risklName;
        }
    }

    public void setRisklName(String risklName) {
        this.risklName = risklName;
    }

    public String getRiskE() {
        if (null == riskE) {
            return "";
        } else {
            return riskE;
        }
    }

    public void setRiskE(String riskE) {
        this.riskE = riskE;
    }

    public String getRiskeName() {
        if (null == riskeName) {
            return "";
        } else {
            return riskeName;
        }
    }

    public void setRiskeName(String riskeName) {
        this.riskeName = riskeName;
    }

    public String getRiskC() {
        if (null == riskC) {
            return "";
        } else {
            return riskC;
        }
    }

    public void setRiskC(String riskC) {
        this.riskC = riskC;
    }

    public String getRiskcName() {
        if (null == riskcName) {
            return "";
        } else {
            return riskcName;
        }
    }

    public void setRiskcName(String riskcName) {
        this.riskcName = riskcName;
    }

    public String getRiskD() {
        if (null == riskD) {
            return "";
        } else {
            return riskD;
        }
    }

    public void setRiskD(String riskD) {
        this.riskD = riskD;
    }

    public String getHazardCategory() {
        if (null == hazardCategory) {
            return "";
        } else {
            return hazardCategory;
        }
    }

    public void setHazardCategory(String hazardCategory) {
        this.hazardCategory = hazardCategory;
    }

    public String getHazardCategoryName() {
        if (null == hazardCategoryName) {
            return "";
        } else {
            return hazardCategoryName;
        }
    }

    public void setHazardCategoryName(String hazardCategoryName) {
        this.hazardCategoryName = hazardCategoryName;
    }

    public String getHazardGrade() {
        if (null == hazardGrade) {
            return "";
        } else {
            return hazardGrade;
        }
    }

    public void setHazardGrade(String hazardGrade) {
        this.hazardGrade = hazardGrade;
    }

    public String getHazardGradeName() {
        if (null == hazardGradeName) {
            return "";
        } else {
            return hazardGradeName;
        }
    }


    public String getHazardFactor() {
        if (null == hazardFactor) {
            return "";
        } else {
            return hazardFactor;
        }
    }

    public void setHazardFactor(String hazardFactor) {
        this.hazardFactor = hazardFactor;
    }

    public String getHazardFactorName() {
        if (null == hazardFactorName) {
            return "";
        } else {
            return hazardFactorName;
        }
    }

    public void setHazardFactorName(String hazardFactorName) {
        this.hazardFactorName = hazardFactorName;
    }

    public String getIsReported() {
        if (null == isReported) {
            return "";
        } else {
            return isReported;
        }
    }

    public void setIsReported(String isReported) {
        this.isReported = isReported;
    }

    public String getIsReportedName() {
        if (null == isReportedName) {
            return "";
        } else {
            return isReportedName;
        }
    }

    public void setHazardGradeName(String hazardGradeName) {
        this.hazardGradeName = hazardGradeName;
    }

    public void setIsReportedName(String isReportedName) {
        this.isReportedName = isReportedName;
    }

    public String getBelongDeptName() {
        if (null == belongDeptName) {
            return "";
        } else {
            return belongDeptName;
        }
    }

    public void setBelongDeptName(String belongDeptName) {
        this.belongDeptName = belongDeptName;
    }

    public String getBelongDept() {
        if (null == belongDept) {
            return "";
        } else {
            return belongDept;
        }
    }

    public void setBelongDept(String belongDept) {
        this.belongDept = belongDept;
    }

    public String getAnalysisPersonName() {
        if (null == analysisPersonName) {
            return "";
        } else {
            return analysisPersonName;
        }
    }

    public void setAnalysisPersonName(String analysisPersonName) {
        this.analysisPersonName = analysisPersonName;
    }

    public String getAnalysisPerson() {
        if (null == analysisPerson) {
            return "";
        } else {
            return analysisPerson;
        }
    }

    public void setAnalysisPerson(String analysisPerson) {
        this.analysisPerson = analysisPerson;
    }

    public String getAnalysisTime() {
        if (null == analysisTime) {
            return "";
        } else {
            return analysisTime;
        }
    }

    public void setAnalysisTime(String analysisTime) {
        this.analysisTime = analysisTime;
    }

    public String getDeviceName() {
        if (null == deviceName) {
            return "";
        } else {
            return deviceName;
        }
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}