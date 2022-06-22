package com.example.new_androidclient.work.bean;

public class WorkApplicationBaseDetailBean {

    /**
     * id : 124
     * planWorkId : 57
     * devId : 1235
     * sheetCat : 030
     * sheetNo : GD-2020-Z921-002
     * surveyNo : TK-2020-Z921-002
     * sheetName : 工单
     * projectLeaderName : 冷东城
     * workLeaderName : null
     * jsaNo : JSA-2020-Z921-002
     * deptName : 四车间
     * instName : 聚合装置111
     * unitName : 999单元
     * areaName : 999
     * tagNo : 9
     * planStartDate : null
     * planEndDate : null
     * workersNum : 0
     * specialEquipWork :
     * isSpecialWork : 1
     * specialWorkType :
     * workType : DH,SX
     * workTypeName : 动火作业,受限空间作业
     * energyIsolation 是否能量隔离 1是0否
     */

    private int id;
    private int planWorkId;
    private int devId;
    private String sheetCat;
    private String sheetNo;
    private String surveyNo;
    private String sheetName;
    private String projectLeaderName;
    private String workLeaderName;
    private String jsaNo;
    private String deptName;
    private String instName;
    private String unitName;
    private String areaName;
    private String tagNo;
    private String planStartDate;
    private String planEndDate;
    private String workersNum;
    private String specialEquipWork;
    private String isSpecialWork;
    private String specialWorkType;
    private String workType;
    private String workTypeName;
    private String safetyOfficer;
    private String safetyOfficerName;
    private String applicationDept;
    private String energyIsolation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanWorkId() {
        return planWorkId;
    }

    public void setPlanWorkId(int planWorkId) {
        this.planWorkId = planWorkId;
    }

    public int getDevId() {
        return devId;
    }

    public void setDevId(int devId) {
        this.devId = devId;
    }

    public String getSheetCat() {
        return sheetCat;
    }

    public void setSheetCat(String sheetCat) {
        this.sheetCat = sheetCat;
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(String surveyNo) {
        this.surveyNo = surveyNo;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getProjectLeaderName() {
        return projectLeaderName;
    }

    public void setProjectLeaderName(String projectLeaderName) {
        this.projectLeaderName = projectLeaderName;
    }

    public String getWorkLeaderName() {
        return workLeaderName;
    }

    public String getJsaNo() {
        return jsaNo;
    }

    public void setJsaNo(String jsaNo) {
        this.jsaNo = jsaNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setWorkLeaderName(String workLeaderName) {
        this.workLeaderName = workLeaderName;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getWorkersNum() {
        if (workersNum == null) {
            workersNum = "";
        }
        if (workersNum.length() == 0) {
            return "0";
        } else {
            return workersNum;
        }
    }

    public void setWorkersNum(String workersNum) {
        this.workersNum = workersNum;
    }

    public String getSpecialEquipWork() {
        return specialEquipWork;
    }

    public void setSpecialEquipWork(String specialEquipWork) {
        this.specialEquipWork = specialEquipWork;
    }

    public String getIsSpecialWork() {
        return isSpecialWork;
    }

    public void setIsSpecialWork(String isSpecialWork) {
        this.isSpecialWork = isSpecialWork;
    }

    public String getSpecialWorkType() {
        return specialWorkType;
    }

    public void setSpecialWorkType(String specialWorkType) {
        this.specialWorkType = specialWorkType;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public String getSafetyOfficer() {
        if (null == safetyOfficer) {
            safetyOfficer = "0";
        }
        return safetyOfficer;
    }

    public void setSafetyOfficer(String safetyOfficer) {
        this.safetyOfficer = safetyOfficer;
    }

    public String getSafetyOfficerName() {
        if (null == safetyOfficerName || safetyOfficerName.isEmpty()) {
            return "请选择";
        }
        return safetyOfficerName;
    }

    public void setSafetyOfficerName(String safetyOfficerName) {
        this.safetyOfficerName = safetyOfficerName;
    }

    public String getApplicationDept() {
        return applicationDept;
    }

    public void setApplicationDept(String applicationDept) {
        this.applicationDept = applicationDept;
    }

    public String getEnergyIsolation() {
        if (null == energyIsolation || energyIsolation.isEmpty()) {
            return "请选择";
        }

        if (energyIsolation.equals("1")) {
            return "是";
        } else {
            return "否";
        }
    }

    public void setEnergyIsolation(String energyIsolation) {
        this.energyIsolation = energyIsolation;
    }
}
