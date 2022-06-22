package com.example.new_androidclient.inspection.bean;

import java.io.File;
import java.io.Serializable;

public class PicBean implements Serializable {

    String id;
    String type;  //30区域 31设备 32参数 40隐患排查
    File file;

    public PicBean(String id,  String type, File file) {
        this.id = id;
        this.type = type;
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


}
