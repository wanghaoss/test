package com.example.new_androidclient.work.bean;

public class WorkSXApplicationBean extends WorkApplicationBaseBean {
    /**
     * workContent : null
     * csName : null
     * csDept : null
     * csDeptName : null
     * location : null
     * tagNo : null
     * matStatus : null
     * workingMpa : null
     * workingTemp : null
     * csMediumName : null
     * devicePipeNum : null
     * dangerPipeNum : null
     * dangerNature : null
     * energyIsolation : null
     * riskIdentify : null
     * csNature : null
     * isOxy : null
     * oxyRichFlag : null
     * oxyStdValue : null
     * isToxic : null
     * toxicMedium : null
     * isLel : null
     * lelMedium : null
     * isUpgradeMgt : null
     * workingPerson : null
     */

    private String workContent;
    private String csName;
    private String csDept;
    private String csDeptName;
    private String location;
    private String tagNo;
    private String matStatus;
    private String workingMpa;
    private String workingTemp;
    private String csMediumName;
    private String devicePipeNum;
    private String dangerPipeNum;
    private String dangerNature;
    private String energyIsolation;
    private String csNature;
    private String isOxy;
    private String oxyRichFlag;
    private String oxyStdValue;
    private String isToxic;
    private String toxicMedium;
    private String isLel;
    private String lelMedium;
    private String isUpgradeMgt;
    private String workingPerson;

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getCsName() {
        return csName;
    }

    public void setCsName(String csName) {
        this.csName = csName;
    }

    public String getCsDept() {
        return csDept;
    }

    public void setCsDept(String csDept) {
        this.csDept = csDept;
    }

    public String getCsDeptName() {
        return csDeptName;
    }

    public void setCsDeptName(String csDeptName) {
        this.csDeptName = csDeptName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public String getMatStatus() {
        if (matStatus == null) {
            return "请选择";
        }
        return matStatus;
    }

    public void setMatStatus(String matStatus) {
        this.matStatus = matStatus;
    }

    public String getWorkingMpa() {
        return workingMpa;
    }

    public void setWorkingMpa(String workingMpa) {
        this.workingMpa = workingMpa;
    }

    public String getWorkingTemp() {
        return workingTemp;
    }

    public void setWorkingTemp(String workingTemp) {
        this.workingTemp = workingTemp;
    }

    public String getCsMediumName() {
        return csMediumName;
    }

    public void setCsMediumName(String csMediumName) {
        this.csMediumName = csMediumName;
    }

    public String getDevicePipeNum() {
        return devicePipeNum;
    }

    public void setDevicePipeNum(String devicePipeNum) {
        this.devicePipeNum = devicePipeNum;
    }

    public String getDangerPipeNum() {
        return dangerPipeNum;
    }

    public void setDangerPipeNum(String dangerPipeNum) {
        this.dangerPipeNum = dangerPipeNum;
    }

    public String getDangerNature() {
        if (dangerNature == null) {
            return "请选择";
        }
        return dangerNature;
    }

    public void setDangerNature(String dangerNature) {
        this.dangerNature = dangerNature;
    }

    public String getEnergyIsolation() {
        return energyIsolation;
    }

    public void setEnergyIsolation(String energyIsolation) {
        this.energyIsolation = energyIsolation;
    }

    public String getCsNature() {
        return csNature;
    }

    public void setCsNature(String csNature) {
        this.csNature = csNature;
    }

    public String getIsOxy() {
        return isOxy;
    }

    public void setIsOxy(String isOxy) {
        this.isOxy = isOxy;
    }

    public String getOxyRichFlag() {
        return oxyRichFlag;
    }

    public void setOxyRichFlag(String oxyRichFlag) {
        this.oxyRichFlag = oxyRichFlag;
    }

    public String getOxyStdValue() {
        return oxyStdValue;
    }

    public void setOxyStdValue(String oxyStdValue) {
        this.oxyStdValue = oxyStdValue;
    }

    public String getIsToxic() {
        return isToxic;
    }

    public void setIsToxic(String isToxic) {
        this.isToxic = isToxic;
    }

    public String getToxicMedium() {
        return toxicMedium;
    }

    public void setToxicMedium(String toxicMedium) {
        this.toxicMedium = toxicMedium;
    }

    public String getIsLel() {
        return isLel;
    }

    public void setIsLel(String isLel) {
        this.isLel = isLel;
    }

    public String getLelMedium() {
        return lelMedium;
    }

    public void setLelMedium(String lelMedium) {
        this.lelMedium = lelMedium;
    }

    public String getIsUpgradeMgt() {
        return isUpgradeMgt;
    }

    public void setIsUpgradeMgt(String isUpgradeMgt) {
        this.isUpgradeMgt = isUpgradeMgt;
    }

    public String getWorkingPerson() {
        return workingPerson;
    }

    public void setWorkingPerson(String workingPerson) {
        this.workingPerson = workingPerson;
    }
}
