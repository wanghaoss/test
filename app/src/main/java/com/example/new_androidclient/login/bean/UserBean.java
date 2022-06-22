package com.example.new_androidclient.login.bean;

import java.util.List;

public class UserBean {

    /**
     * firstName : 管
     * lastName : 理员
     * tenantId : 13
     * userId : 20
     * email : dandong@zhuningkeji.com
     * token : eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI0Nzc0NGY1MjJjNzBkN2Y0NzhjMmMyNWQ1MDg4NGQ1NCIsImV4cCI6MTU5MTA5NDEwMH0.lWQeQfv9F1g7oYOjFbEuK1aKjENAlCucM0fZrlRt99I
     * jobFlag
     */


    private String firstName;
    private String lastName;
    private int tenantId;
    private int userId;
    private String email;
    private String token;
    private List<Integer> jobFlag;

    public List<Integer> getJobFlag() {
        return jobFlag;
    }

    public void setJobFlag(List<Integer> jobFlag) {
        this.jobFlag = jobFlag;
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

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


