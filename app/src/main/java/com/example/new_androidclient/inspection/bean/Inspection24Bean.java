package com.example.new_androidclient.inspection.bean;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Inspection24Bean extends BaseObservable {

    /**
     * deviceNo : S000106
     * deviceName : 聚合2搅拌器电机
     * inspectionTime : 2020-07-29 17:16:03
     * userName : admin
     * taskId : 459
     * areaId : 54
     * deviceId : 106
     */

    private String deviceNo;
    private String deviceName;
    private String inspectionTime;
    private String userName;
    private int taskId;
    private int areaId;
    private int deviceId;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    @Bindable
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Bindable
    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
}
