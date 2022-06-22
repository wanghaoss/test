package com.example.new_androidclient.inspection.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.alibaba.android.arouter.facade.service.SerializationService;

import java.io.Serializable;

public class InspectionDeviceListBean extends BaseObservable  {

    /**
     * delFlag : null
     * createBy : null
     * createTime : null
     * updateBy : null
     * updateTime : null
     * page : 1
     * size : 10
     * sort : null
     * id : 4948
     * taskId : 298
     * areaId : 53
     * deviceId : 8
     * status : 0
     * serialNumber : null
     * warning : 1
     * isUsingStatus : 20
     * deviceNo : S000008
     * deviceName : 水洗罐1
     * tagNo : R-301A
     * useStatus : 023
     * lineName : null
     * areaName : null
     */

    private String delFlag;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private int page;
    private int size;
    private int sort;
    private int id;
    private int taskId;
    private int areaId;
    private int deviceId;
    private int status;
    private int serialNumber;
    private int warning;
    private int isUsingStatus;
    private String deviceNo;
    private String deviceName;
    private String tagNo;
    private String useStatus;
    private String lineName;
    private String areaName;

    public InspectionDeviceListBean() { }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Bindable
    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    @Bindable
    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    @Bindable
    public int getStatus() {
      return status;
    }

    @Bindable
    public String getStatusString(){
        if(status == 0){
            return "未巡检";
        }
        return "已巡检";
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Bindable
    public int getWarning() {
        return warning;
    }

    public void setWarning(int warning) {
        this.warning = warning;
    }

    @Bindable
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Bindable
    public String getUseStatus() {
        if(useStatus.equals("")){
            return null;
        }
        if(useStatus.equals("023")){
            return "在用";
        }else if(useStatus.equals("024")){
            return "闲置";
        }else{
            return "停用";
        }

    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    @Bindable
    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    @Bindable
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Bindable
    public int getIsUsingStatus() {
        return isUsingStatus;
    }

    public void setIsUsingStatus(int isUsingStatus) {
        this.isUsingStatus = isUsingStatus;
    }
}