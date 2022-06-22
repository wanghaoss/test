package com.example.new_androidclient.main.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class HomeListBean extends BaseObservable {
    public String name;
    public int image;
    public int number;

    @Bindable
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public HomeListBean() {
    }

    public HomeListBean(String name, int image, int number) {
        this.name = name;
        this.image = image;
        this.number = number;
    }
}
