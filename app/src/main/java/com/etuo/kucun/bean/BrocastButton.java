package com.etuo.kucun.bean;

/**
 * Created by UglyB
 * 0n 2019/5/7
 */
public class BrocastButton {
    private String id;
    private String closeBroadcast;
    private String openBroadcast;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCloseBroadcast() {
        return closeBroadcast;
    }

    public void setCloseBroadcast(String closeBroadcast) {
        this.closeBroadcast = closeBroadcast;
    }

    public String getOpenBroadcast() {
        return openBroadcast;
    }

    public void setOpenBroadcast(String openBroadcast) {
        this.openBroadcast = openBroadcast;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BrocastButton{" +
                "id='" + id + '\'' +
                ", closeBroadcast='" + closeBroadcast + '\'' +
                ", openBroadcast='" + openBroadcast + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
