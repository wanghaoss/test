package com.example.new_androidclient.hazard.bean;

import java.io.File;
import java.io.Serializable;

public class HazardPlanUploadFileBean implements Serializable {
    String name;
    int type;//1防范措施  2整改治理方案
    File file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getTypeStr() {
        if (type == 1) {
            return "防范措施";
        } else {
            return "整改治理方案";
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
