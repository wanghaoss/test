package com.example.new_androidclient.work.bean;

public class WorkDHApplicationBean extends WorkApplicationBaseBean {


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
     *
     * workContent : null
     * fireSite : null
     * fireMode : null
     * firePosition : null
     * fireLevel : null
     * lelMedium : null
     * isUpgradeMgt : null
     **/


    private String workContent;
    private String fireSite;
    private String fireMode;
    private String firePosition;
    private String fireLevel;
    private String lelMedium;
    private String isUpgradeMgt;

    public String getFireSite() {
        if (null == fireSite) {
            return "";
        } else {
            return fireSite;
        }
    }

    public void setFireSite(String fireSite) {
        this.fireSite = fireSite;
    }

    public String getFireMode() {
        if (null == fireMode) {
            return "请选择";
        } else {
            return fireMode;
        }
    }

    public void setFireMode(String fireMode) {
        this.fireMode = fireMode;
    }

    public String getFirePosition() {
        if (null == firePosition) {
            return "请选择";
        } else {
            return firePosition;
        }
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public void setFirePosition(String firePosition) {
        this.firePosition = firePosition;
    }


    public String getFireLevel() {
        if (fireLevel == null) {
            return "请选择";
        }
        return fireLevel;
    }

    public void setFireLevel(String fireLevel) {
        this.fireLevel = fireLevel;
    }

    public String getLelMedium() {
        return lelMedium;
    }

    public void setLelMedium(String lelMedium) {
        this.lelMedium = lelMedium;
    }

    public String getIsUpgradeMgt() {
        if (isUpgradeMgt == null) {
            return "请选择";
        }
        if (isUpgradeMgt.equals("1")) {
            return "是";
        } else {
            return "否";
        }

    }

    public void setIsUpgradeMgt(String isUpgradeMgt) {
        this.isUpgradeMgt = isUpgradeMgt;
    }
}
