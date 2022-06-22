package com.example.new_androidclient.inspection.bean;

public class InspectionTaskAreaResultBean {
    String deviceResult;
    int id;
    String deviceName;

    public String getContent() {
        return deviceResult;
    }

    public void setContent(String content) {
        this.deviceResult = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
