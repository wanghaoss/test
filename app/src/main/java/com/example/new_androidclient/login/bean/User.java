package com.example.new_androidclient.login.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

//登录页面， 账号名+密码的databinding类
public class User extends BaseObservable {
    String name;
    String pwd;

    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public User() {
        this("","");
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
