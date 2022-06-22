package com.example.new_androidclient.hazard.bean;

import android.graphics.Bitmap;

import com.example.new_androidclient.inspection.bean.PicBean;

import java.io.File;

public class HazardPicBean extends PicBean {
    public String docUrl;
    boolean fromSystem; //true就显示docurl， false就显示file
    Bitmap bitmap;

    public HazardPicBean(String id, String type, File file, String docUrl, boolean fromSystem) {
        super(id, type, file);
        this.docUrl = docUrl;
        this.fromSystem = fromSystem;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public boolean isFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(boolean fromSystem) {
        this.fromSystem = fromSystem;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
