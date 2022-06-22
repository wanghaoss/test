package com.example.new_androidclient.hazard.bean;

import androidx.databinding.Bindable;
import androidx.recyclerview.widget.RecyclerView;

public class HazardAnalysisDeviceListBean{

    /**
     * devId : 1220
     * dptName : 四车间
     * instName : 聚合装置
     * deviceName : asd
     * deviceNo : S20176
     * tagNo : asd
     * deviceCatName : 炉
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
