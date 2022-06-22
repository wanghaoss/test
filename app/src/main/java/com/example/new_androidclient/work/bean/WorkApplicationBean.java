package com.example.new_androidclient.work.bean;

import java.util.Date;

public class WorkApplicationBean {

    private Integer id;

    // 计划工单Id
    private Integer planWorkId;

    // 是否特种设备作业
    private String isSpecialEquipWork;

    // 特种设备作业
    private String specialEquipWork;

    // 是否特种作业
    private String isSpecialWork;

    // 特种作业类型
    private String specialWorkType;

    // 作业类型
    private String workType;

    // 预计作业人数
    private Integer workersNum;

    // 计划作业时间
    private Date planWorkTime;

    // 申请时间
    private Date appkicationStartTime;

    // 申请结束时间
    private Date appkicationEndTime;

    // 总协调人
    private String coordinator;

    // 申请人
    private Integer applicant;

    private Integer tenantId;

    private String auditUser;

    private Date auditTime;

    private String auditOpinion;

    private String approveUser;

    private Date approveTime;

    private String approveOpinion;

    private Integer territoryLeader;

    private Date dhAnalysisTime;

    private Date gasDetectionTime;

    private String isConstructionApproved;

    private String isSafetyApproved;

    private String constructionRemark;

    private String safetyRemark;

    private Integer territoryGuardian;

    private Integer safetyOfficer;// 质安环部安全员

    private Integer safetyLeader;// 质安环部负责人

    private String safetyOfficerName; //质安环部安全员名称

    private String energyIsolation;; //是否能量隔离


    public String getEnergyIsolation() {
        return energyIsolation;
    }

    public void setEnergyIsolation(String energyIsolation) {
        this.energyIsolation = energyIsolation;
    }

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

    public String getIsSpecialEquipWork() {
        return isSpecialEquipWork;
    }

    public void setIsSpecialEquipWork(String isSpecialEquipWork) {
        this.isSpecialEquipWork = isSpecialEquipWork;
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

    public Integer getWorkersNum() {
        return workersNum;
    }

    public void setWorkersNum(Integer workersNum) {
        this.workersNum = workersNum;
    }

    public Date getPlanWorkTime() {
        return planWorkTime;
    }

    public void setPlanWorkTime(Date planWorkTime) {
        this.planWorkTime = planWorkTime;
    }

    public Date getAppkicationStartTime() {
        return appkicationStartTime;
    }

    public void setAppkicationStartTime(Date appkicationStartTime) {
        this.appkicationStartTime = appkicationStartTime;
    }

    public Date getAppkicationEndTime() {
        return appkicationEndTime;
    }

    public void setAppkicationEndTime(Date appkicationEndTime) {
        this.appkicationEndTime = appkicationEndTime;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    public Integer getApplicant() {
        return applicant;
    }

    public void setApplicant(Integer applicant) {
        this.applicant = applicant;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveOpinion() {
        return approveOpinion;
    }

    public void setApproveOpinion(String approveOpinion) {
        this.approveOpinion = approveOpinion;
    }

    public Integer getTerritoryLeader() {
        return territoryLeader;
    }

    public void setTerritoryLeader(Integer territoryLeader) {
        this.territoryLeader = territoryLeader;
    }

    public Date getDhAnalysisTime() {
        return dhAnalysisTime;
    }

    public void setDhAnalysisTime(Date dhAnalysisTime) {
        this.dhAnalysisTime = dhAnalysisTime;
    }

    public Date getGasDetectionTime() {
        return gasDetectionTime;
    }

    public void setGasDetectionTime(Date gasDetectionTime) {
        this.gasDetectionTime = gasDetectionTime;
    }

    public String getIsConstructionApproved() {
        return isConstructionApproved;
    }

    public void setIsConstructionApproved(String isConstructionApproved) {
        this.isConstructionApproved = isConstructionApproved;
    }

    public String getIsSafetyApproved() {
        return isSafetyApproved;
    }

    public void setIsSafetyApproved(String isSafetyApproved) {
        this.isSafetyApproved = isSafetyApproved;
    }

    public String getConstructionRemark() {
        return constructionRemark;
    }

    public void setConstructionRemark(String constructionRemark) {
        this.constructionRemark = constructionRemark;
    }

    public String getSafetyRemark() {
        return safetyRemark;
    }

    public void setSafetyRemark(String safetyRemark) {
        this.safetyRemark = safetyRemark;
    }

    public Integer getTerritoryGuardian() {
        return territoryGuardian;
    }

    public void setTerritoryGuardian(Integer territoryGuardian) {
        this.territoryGuardian = territoryGuardian;
    }

    public Integer getSafetyOfficer() {
        return safetyOfficer;
    }



    public Integer getSafetyLeader() {
        return safetyLeader;
    }

    public void setSafetyOfficer(Integer safetyOfficer) {
        this.safetyOfficer = safetyOfficer;
    }

    public void setSafetyLeader(Integer safetyLeader) {
        this.safetyLeader = safetyLeader;
    }

    public String getSafetyOfficerName() {
        return safetyOfficerName;
    }

    public void setSafetyOfficerName(String safetyOfficerName) {
        this.safetyOfficerName = safetyOfficerName;
    }
}
