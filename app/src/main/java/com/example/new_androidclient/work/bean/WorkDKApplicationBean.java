package com.example.new_androidclient.work.bean;

public class WorkDKApplicationBean extends WorkApplicationBaseBean {

    /**
     * areaTag : null
     * workingType : null
     * openMode : null
     * isSpecialWork : null
     * specialWorkItem : null
     * isSpecialEquip : null
     * specialEquipType : null
     * specialEquipItem : null
     * isCraftIsolate : null
     * riskIdentify : null
     * workingCommander : null
     * safetyMedium : null
     * workingLevel : null
     * isUpgradeMgt : null
     * conduitDevice
     * workingContent
     */

    private String areaTag;
    private String workingType;
    private String openMode;
    private String isSpecialWork;
    private String specialWorkItem;
    private String isSpecialEquip;
    private String specialEquipType;
    private String specialEquipItem;
    private String isCraftIsolate;
    private Integer workingCommander;
    private String safetyMedium;
    private String workingLevel;
    private String isUpgradeMgt;
    private String conduitDevice;
    private String workingContent;

    public String getWorkingContent() {
        return workingContent;
    }

    public void setWorkingContent(String workingContent) {
        this.workingContent = workingContent;
    }

    public String getConduitDevice() {
//        if (conduitDevice == null) {
//            return "请选择";
//        }
        return conduitDevice;
    }

    public void setConduitDevice(String conduitDevice) {
        this.conduitDevice = conduitDevice;
    }

    public String getAreaTag() {
        return areaTag;
    }

    public void setAreaTag(String areaTag) {
        this.areaTag = areaTag;
    }

    public String getWorkingType() {
        if (workingType == null) {
            return "请选择";
        }
        switch (workingType) {
            case "0":
                return "管线容器打开";
            case "1":
                return "管线设备打开";

        }
        return workingType;
    }

    public void setWorkingType(String workingType) {
        this.workingType = workingType;
    }

    public String getOpenMode() {
        if (openMode == null) {
            return "请选择";
        }
        return openMode;
    }

    public void setOpenMode(String openMode) {
        this.openMode = openMode;
    }

    public String getIsSpecialWork() {
        return isSpecialWork;
    }

    public void setIsSpecialWork(String isSpecialWork) {
        this.isSpecialWork = isSpecialWork;
    }

    public String getSpecialWorkItem() {
        return specialWorkItem;
    }

    public void setSpecialWorkItem(String specialWorkItem) {
        this.specialWorkItem = specialWorkItem;
    }

    public String getIsSpecialEquip() {
        return isSpecialEquip;
    }

    public void setIsSpecialEquip(String isSpecialEquip) {
        this.isSpecialEquip = isSpecialEquip;
    }

    public String getSpecialEquipType() {
        return specialEquipType;
    }

    public void setSpecialEquipType(String specialEquipType) {
        this.specialEquipType = specialEquipType;
    }

    public String getSpecialEquipItem() {
        return specialEquipItem;
    }

    public void setSpecialEquipItem(String specialEquipItem) {
        this.specialEquipItem = specialEquipItem;
    }

    public String getIsCraftIsolate() {
        return isCraftIsolate;
    }

    public void setIsCraftIsolate(String isCraftIsolate) {
        this.isCraftIsolate = isCraftIsolate;
    }

    public Integer getWorkingCommander() {
        return workingCommander;
    }

    public void setWorkingCommander(Integer workingCommander) {
        this.workingCommander = workingCommander;
    }

    public String getSafetyMedium() {
        return safetyMedium;
    }

    public void setSafetyMedium(String safetyMedium) {
        this.safetyMedium = safetyMedium;
    }

    public String getWorkingLevel() {
        return workingLevel;
    }

    public void setWorkingLevel(String workingLevel) {
        this.workingLevel = workingLevel;
    }

    public String getIsUpgradeMgt() {
        return isUpgradeMgt;
    }

    public void setIsUpgradeMgt(String isUpgradeMgt) {
        this.isUpgradeMgt = isUpgradeMgt;
    }
}
