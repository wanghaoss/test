package com.example.new_androidclient.inspection.bean;

public class Inspection24DetailBean {

    /**
     * id : 14923
     * specType : 2
     * userName : admin
     * inspectionTime : 2020-07-30 13:50:24
     * deviceId : 107
     * taskId : 471
     * areaId : 54
     * result : 1
     * otherSpec : null
     * resultType : 0
     * assetspecName : 非风扇侧轴承振动（轴向y）
     * assetspecData : 4.5
     * assetspecUnit : mm/s
     * assetspecConditions : <=
     * pointName : null
     */

    private int id;
    private int specType;
    private String userName;
    private String inspectionTime;
    private int deviceId;
    private int taskId;
    private int areaId;
    private String result;
    private String otherSpec;
    private int resultType;
    private String assetspecName;
    private String assetspecData;
    private String assetspecUnit;
    private String assetspecConditions;
    private String pointName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpecType() {
        return specType;
    }

    public void setSpecType(int specType) {
        this.specType = specType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOtherSpec() {
        return otherSpec;
    }



    public int getResultType() {
        return resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    public String getAssetspecName() {
        return assetspecName;
    }

    public void setAssetspecName(String assetspecName) {
        this.assetspecName = assetspecName;
    }

    public String getAssetspecData() {
        return assetspecData;
    }

    public void setAssetspecData(String assetspecData) {
        this.assetspecData = assetspecData;
    }

    public String getAssetspecUnit() {
        return assetspecUnit;
    }

    public void setAssetspecUnit(String assetspecUnit) {
        this.assetspecUnit = assetspecUnit;
    }

    public String getAssetspecConditions() {
        return assetspecConditions;
    }

    public void setAssetspecConditions(String assetspecConditions) {
        this.assetspecConditions = assetspecConditions;
    }

    public String getPointName() {
        return pointName;
    }

    public void setOtherSpec(String otherSpec) {
        this.otherSpec = otherSpec;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }
}
