package com.example.new_androidclient.work.bean;

public class PermitCancelBean {

    /**
     * itemtype : 0
     * itemname : 作业环境和条件发生变化（只适用动火、受限空间、临时用电许可证）；
     * id : 1
     */

    private String itemtype;
    private String itemname;
    private int id;

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
