package com.example.new_androidclient.device_management.bean;

import java.io.Serializable;

public class FindDeviceListBean implements Serializable {

    /**
     * devId : 1160
     * dptName : 三车间
     * instName : 氯气仓库
     * deviceName : 4
     * deviceNo : S20030
     * tagNo :
     * deviceCatName : null
     */

    private int devId;
    private String dptName;
    private String instName;
    private String deviceName;
    private String deviceNo;
    private String tagNo;
    private String deviceCatName;

    public int getDevId() {
        return devId;
    }

    public void setDevId(int devId) {
        this.devId = devId;
    }

    public String getDptName() {
        return dptName;
    }

    public void setDptName(String dptName) {
        this.dptName = dptName;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public String getDeviceCatName() {
        return deviceCatName;
    }

    public void setDeviceCatName(String deviceCatName) {
        this.deviceCatName = deviceCatName;
    }
}
