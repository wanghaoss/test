package com.example.new_androidclient.inspection.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;

public class InspectionDeviceDetailBean extends BaseObservable {


    /**
     * id : 8857
     * taskId : 304
     * areaId : 53
     * deviceId : 8
     * specId : 281
     * specType : 3
     * resultType : null
     * result : null
     * resultDescription : null
     * schema : null
     * inspector : null
     * inspectionTime : null
     * warning : 1
     * lineId : 28
     * otherSpec : null
     * status : 0
     * pushStage : null
     * isRemove : null
     * pointName : 物料入口法兰
     * assetSpecName : null
     * assetSpecUnit : null
     * deviceCode : null
     * assetspecData : null
     * assetspecConditions : null
     * inspectorPer : null
     */

    private int id;
    private int taskId;
    private int areaId;
    private int deviceId;
    private int specId;
    private String specType;
    private Integer resultType;
    private String result;
    private String resultDescription;
    private String schema;
    private String inspector;
    private String inspectionTime;
    private String warning;
    private int lineId;
    private String otherSpec;
    private String status;
    private int pushStage;
    private int isRemove;
    private String pointName;
    private String assetSpecName;
    private String assetSpecUnit;
    private String deviceCode;
    private String assetspecData;
    private String assetspecConditions;
    private String inspectorPer;

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

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public String getSpecType() {
        return specType;
    }

    public void setSpecType(String specType) {
        this.specType = specType;
    }

    @Bindable
    public Integer getResultType() {
        return resultType;
    }


    public Object getSchema() {
        return schema;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    @Bindable
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Bindable
    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getOtherSpec() {
        return otherSpec;
    }

    public void setOtherSpec(String otherSpec) {
        this.otherSpec = otherSpec;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPushStage() {
        return pushStage;
    }

    public void setPushStage(int pushStage) {
        this.pushStage = pushStage;
    }

    public int getIsRemove() {
        return isRemove;
    }

    public void setIsRemove(int isRemove) {
        this.isRemove = isRemove;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getAssetSpecName() {
        return assetSpecName;
    }

    public void setAssetSpecName(String assetSpecName) {
        this.assetSpecName = assetSpecName;
    }

    public String getAssetSpecUnit() {
        return assetSpecUnit;
    }

    public void setAssetSpecUnit(String assetSpecUnit) {
        this.assetSpecUnit = assetSpecUnit;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getAssetspecData() {
        return assetspecData;
    }

    public void setAssetspecData(String assetspecData) {
        this.assetspecData = assetspecData;
    }

    public String getAssetspecConditions() {
        return assetspecConditions;
    }

    public void setAssetspecConditions(String assetspecConditions) {
        this.assetspecConditions = assetspecConditions;
    }

    public String getInspectorPer() {
        return inspectorPer;
    }

    public void setInspectorPer(String inspectorPer) {
        this.inspectorPer = inspectorPer;
    }
}