package com.example.new_androidclient.inspection.bean;

import androidx.databinding.BaseObservable;

import java.io.Serializable;

public class InspectionAreaBean extends BaseObservable {

    /**
     * areaName : 三层框架
     * areaCode : AR-20000
     * taskId : 564
     * inspectionTime : null
     * startTime : null
     * endTime : null
     * taskName : 框架三层设备巡检-09:00:00
     * areaId : 51
     * areaContent : null
     * status : 0
     * serialNumber : 1
     * result : null
     * id : 974
     * lineSort
     */

    private String areaName;
    private String areaCode;
    private int taskId;
    private String inspectionTime;
    private String startTime;
    private String endTime;
    private String taskName;
    private int areaId;
    private String areaContent;
    private int status;
    private int serialNumber;
    private String result;
    private int id;
    private String lineSort;

    public String getLineSort() {
        return lineSort;
    }

    public void setLineSort(String lineSort) {
        this.lineSort = lineSort;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaContent() {
        return areaContent;
    }

    public void setAreaContent(String areaContent) {
        this.areaContent = areaContent;
    }

    public int getStatus() {
        return status;
    }
    public String getStringStatus(){
        if(status == 0){
            return "未巡检";
        }else{
            return "已巡检";
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
