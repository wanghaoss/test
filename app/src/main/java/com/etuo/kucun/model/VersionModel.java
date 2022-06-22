package com.etuo.kucun.model;

/**
 * Created by Administrator on 2017/5/17.
 */

public class VersionModel {



    // 安卓内部版本号,判断是否升级
    private String androidCode;
    //版本
    private String android;
    //apk下载路径
    private String androidDownloadLink;
    //更新提示
    private String androidUpdateMessage;
    //是否强制更新
    private String androidForceUpdate; // 0，不强制更新   1，强制更新

    public String getAndroidCode() {
        return androidCode;
    }

    public void setAndroidCode(String androidCode) {
        this.androidCode = androidCode;
    }

    public String getAndroidForceUpdate() {
        return androidForceUpdate;
    }


    public void setAndroidForceUpdate(String androidForceUpdate) {
        this.androidForceUpdate = androidForceUpdate;
    }

    public String getAndroid() {
        return android;
    }

    public void setAndroid(String android) {
        this.android = android;
    }

    public String getAndroidDownloadLink() {
        return androidDownloadLink;
    }

    public void setAndroidDownloadLink(String androidDownloadLink) {
        this.androidDownloadLink = androidDownloadLink;
    }

    public String getAndroidUpdateMessage() {
        return androidUpdateMessage;
    }

    public void setAndroidUpdateMessage(String androidUpdateMessage) {
        this.androidUpdateMessage = androidUpdateMessage;
    }
}
