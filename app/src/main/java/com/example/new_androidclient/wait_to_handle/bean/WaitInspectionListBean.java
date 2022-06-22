package com.example.new_androidclient.wait_to_handle.bean;

public class WaitInspectionListBean {

    /**
     * id : 1
     * deviceName : 水洗罐4
     * tagNo : R-301D
     * specName : 上封头
     * specType : 3
     * otherSpec : null
     * result : 泄漏
     * resultDescription :
     * disposeDescription : null
     * inspector : admin
     * private Integer exceptionType;
     * private String disposeScheme;
     */

    private Integer id;
    private String deviceName;
    private String tagNo;
    private String specName;
    private Integer specType;
    private String otherSpec;
    private String result;
    private String resultDescription;
    private String disposeDescription;
    private String inspector;
    private Integer resultId;
    private Integer inspectionType;
    private Integer exceptionType; //异常类型 默认0需要判断类型 1设备 2生产
    private String disposeScheme;


    public String getDisposeScheme() {
        return disposeScheme;
    }

    public void setDisposeScheme(String disposeScheme) {
        this.disposeScheme = disposeScheme;
    }

    public Integer getExceptionType() {
        return exceptionType;
    }
    public String getExceptionType_str() {
        if(exceptionType == 1){
            return "设备异常";
        }else if(exceptionType == 2){
            return "生产异常";
        }else{
            return "未分类异常";
        }
    }

    public void setExceptionType(Integer exceptionType) {
        this.exceptionType = exceptionType;
    }

    public int getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(int inspectionType) {
        this.inspectionType = inspectionType;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
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

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public int getSpecType() {
        return specType;
    }

    public void setSpecType(int specType) {
        this.specType = specType;
    }

    public Object getOtherSpec() {
        return otherSpec;
    }



    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getDisposeDescription() {
        return disposeDescription;
    }

    public void setOtherSpec(String otherSpec) {
        this.otherSpec = otherSpec;
    }

    public void setDisposeDescription(String disposeDescription) {
        this.disposeDescription = disposeDescription;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }
}
