package com.example.new_androidclient.wait_to_handle.bean;

public class WaitHazardListBean {

    /**
     * formId : 149
     * modelName : 隐患排查表单
     * path : /hazard/hiPlanRecordInfo
     * taskName : 安全部长审核
     * taskTime : 2020-09-02 09:39:40
     */

    private int formId;
    private String modelName;
    private String path;
    private String taskName;
    private String taskTime;

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }
}
