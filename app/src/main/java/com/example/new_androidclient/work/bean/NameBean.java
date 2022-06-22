package com.example.new_androidclient.work.bean;

import java.io.Serializable;

public class NameBean implements Serializable {
    private int nameId;
    private String name;

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
