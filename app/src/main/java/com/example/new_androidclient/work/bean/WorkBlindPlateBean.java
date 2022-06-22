package com.example.new_androidclient.work.bean;

public class WorkBlindPlateBean {
    private int id;
    private int applicationId;//作业申请Id
    private String workingType;//作业类型
    private String workingTypeName;//作业类型名字
    private String deviceName;//管道/设备名称
    private String material;//盲板材质
    private String isolatePoint;//隔离点符号
    private String spec;//盲板规格
    private String medium;//介质
    private String temperature;//温度
    private String pressure;//压力
    private String isolateMethod;//隔离方法
    private String blindPlateNo;//盲板编号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getWorkingType() {
        return workingType;
    }

    public void setWorkingType(String workingType) {
        this.workingType = workingType;
    }

    public String getWorkingTypeName() {
        return workingTypeName;
    }

    public void setWorkingTypeName(String workingTypeName) {
        this.workingTypeName = workingTypeName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getIsolatePoint() {
        return isolatePoint;
    }

    public void setIsolatePoint(String isolatePoint) {
        this.isolatePoint = isolatePoint;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getIsolateMethod() {
        return isolateMethod;
    }

    public void setIsolateMethod(String isolateMethod) {
        this.isolateMethod = isolateMethod;
    }

    public String getBlindPlateNo() {
        return blindPlateNo;
    }

    public void setBlindPlateNo(String blindPlateNo) {
        this.blindPlateNo = blindPlateNo;
    }
}
