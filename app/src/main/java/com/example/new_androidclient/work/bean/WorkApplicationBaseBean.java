package com.example.new_androidclient.work.bean;

public class WorkApplicationBaseBean {

    /**
     * delFlag : null
     * createBy : null
     * createTime : null
     * updateBy : null
     * updateTime : null
     * page : 1
     * size : 10
     * sort : null
     * id : null
     * applicationId : null
     * applicationNo : null
     * workingName : null
     * workingGuardian : null
     * workingStartTime : null
     * workingEndTime : null
     * licenceNo : null
     * status : null
     * approveUser : null
     * approveOpinion : null
     * approveTime : null
     * cancelReason : null
     * confirmItem : null
     * confirmStatus : null
     * tenantId : null
     * workingGuardianName : null
     * currentStatus : null
     * riskIdentify : null
     */

    private String delFlag;
    private Integer createBy;
    private String createTime;
    private Integer updateBy;
    private String updateTime;
    private Integer page;
    private Integer size;
    private String sort;
    private Integer id;
    private Integer applicationId;
    private String applicationNo;
    private String workingName;
    private Integer workingGuardian;
    private String workingStartTime;
    private String workingEndTime;
    private String licenceNo;
    private String status;
    private Integer approveUser;
    private String approveOpinion;
    private String approveTime;
    private String cancelReason;
    private String confirmItem;
    private String confirmStatus;
    private Integer tenantId;
    private String workingGuardianName;
    private String currentStatus;
    private String riskIdentify;

    public String getRiskIdentify() {
        if (null == riskIdentify) {
            return "请选择";
        } else {
            return riskIdentify;
        }
    }

    public void setRiskIdentify(String riskIdentify) {
        this.riskIdentify = riskIdentify;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

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

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getWorkingName() {
        return workingName;
    }

    public void setWorkingName(String workingName) {
        this.workingName = workingName;
    }

    public Integer getWorkingGuardian() {
        return workingGuardian;
    }

    public void setWorkingGuardian(Integer workingGuardian) {
        this.workingGuardian = workingGuardian;
    }

    public String getWorkingStartTime() {
        if (workingStartTime == null) {
            return "请选择";
        }
        return workingStartTime.substring(0, 16);
    }

    public void setWorkingStartTime(String workingStartTime) {
        this.workingStartTime = workingStartTime;
    }

    public String getWorkingEndTime() {
        if (workingEndTime == null) {
            return "请选择";
        }
        return workingEndTime.substring(0, 16);
    }

    public void setWorkingEndTime(String workingEndTime) {
        this.workingEndTime = workingEndTime;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(Integer approveUser) {
        this.approveUser = approveUser;
    }

    public String getApproveOpinion() {
        return approveOpinion;
    }

    public void setApproveOpinion(String approveOpinion) {
        this.approveOpinion = approveOpinion;
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getConfirmItem() {
        return confirmItem;
    }

    public void setConfirmItem(String confirmItem) {
        this.confirmItem = confirmItem;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getWorkingGuardianName() {
        if (null == workingGuardianName) {
            return "请选择";
        } else {
            return workingGuardianName;
        }
    }

    public void setWorkingGuardianName(String workingGuardianName) {
        this.workingGuardianName = workingGuardianName;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}
