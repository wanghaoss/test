package com.example.new_androidclient.inspection.bean;

public class ItemResultDevice {

    private Integer id; //项和设备关联的id

    private Integer itemResultId;//巡检结果id

    private Integer deviceId;

    private String description;

    private String resultMsg;

    private String deviceName;

    private String deviceNo;

    private String tagNo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemResultId() {
        return itemResultId;
    }

    public void setItemResultId(Integer itemResultId) {
        this.itemResultId = itemResultId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
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
}
