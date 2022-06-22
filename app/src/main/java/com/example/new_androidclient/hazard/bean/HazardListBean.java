package com.example.new_androidclient.hazard.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class HazardListBean extends BaseObservable {


    /**
     * id : 87
     * planName : 测试3
     * investigationDeptName : 四车间
     * investigatedDeptName : 四车间
     * investigationTime : 2020-06-17 00:00:00
     * status : 排查中
     */

    private int id;
    private String planName;
    private String investigationDeptName;
    private String investigatedDeptName;
    private String investigationTime;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @Bindable
    public String getInvestigationDeptName() {
        return investigationDeptName;
    }

    public void setInvestigationDeptName(String investigationDeptName) {
        this.investigationDeptName = investigationDeptName;
    }

    @Bindable
    public String getInvestigatedDeptName() {
        return investigatedDeptName;
    }

    public void setInvestigatedDeptName(String investigatedDeptName) {
        this.investigatedDeptName = investigatedDeptName;
    }

    @Bindable
    public String getInvestigationTime() {
        return investigationTime;
    }

    public void setInvestigationTime(String investigationTime) {
        this.investigationTime = investigationTime;
    }

    @Bindable
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
