package com.example.new_androidclient.hazard.bean;

public class HazardAnalysisPersonBean {


    /**
     * delFlag : null
     * createBy : null
     * createTime : null
     * updateBy : null
     * updateTime : null
     * page : 1
     * size : 10
     * sort : null
     * id : 20
     * userName : admin
     * email : admin@zhuningkeji.com
     * password : null
     * firstName : 理员
     * lastName : 管
     * userType : null
     * status : null
     * lastLoginTime : null
     * tenantId : null
     * loginErrCount : null
     * isDeptHead : null
     * roleId : null
     * organId : null
     * jobsId : null
     * organName : 四车间,丹东明珠,海川农化
     * jobsName : 常务副总
     * checkPass : null
     * token : null
     * lastActiveTime : 0
     */


    private int id;
    private String firstName;
    private String lastName;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
