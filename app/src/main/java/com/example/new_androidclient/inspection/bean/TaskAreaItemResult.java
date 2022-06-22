package com.example.new_androidclient.inspection.bean;

import java.io.Serializable;

public class TaskAreaItemResult implements Serializable {
    private int id;

    private int taskId;

    private int areaId;

    private String itemName;

    private String itemType;

    private int itemId;

    private Integer serialNumber;

    private String result;

    private String resultType;

    private String resultDescription;

    private String schema;

    private int inspector;

    private String inspectionTime;

    private String inspStatus;

    private String pushStage;

    private int isRemove;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public int getInspector() {
        return inspector;
    }

    public void setInspector(int inspector) {
        this.inspector = inspector;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getInspStatus() {
        return inspStatus;
    }

    public void setInspStatus(String inspStatus) {
        this.inspStatus = inspStatus;
    }

    public String getPushStage() {
        return pushStage;
    }

    public void setPushStage(String pushStage) {
        this.pushStage = pushStage;
    }

    public int getIsRemove() {
        return isRemove;
    }

    public void setIsRemove(int isRemove) {
        this.isRemove = isRemove;
    }
}