package com.example.new_androidclient.hazard.bean;

public class HazardTablePlanListBean {
//    year 年度
//    id id
//    corpname 企业名称
//    status 状态（0新建，1审批中 2审核，3完成）

    String year;
    int id;
    String corpname;
    String status;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorpname() {
        return corpname;
    }

    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }

    public String getStatus() {
        return status;
    }
    public String getStatusString(){
        if(status.equals("0")){
            return "新建";
        }else if(status.equals("1")){
            return "审批中";
        }else if(status.equals("2")){
            return "审核中";
        }else if(status.equals("3")){
            return "完成";
        }


        return "";
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
