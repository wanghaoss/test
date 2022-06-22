package com.etuo.kucun.model;

/**
 * Created by yhn on 2018/9/5.
 * 根据时间轴 获取租转换的状态
 */

public class PalletListByProcessModel {

   private String palletId;//	托盘id
    private String palletNo	;//托盘编号
    private String status;//	托盘状态


    public String getPalletId() {
        return palletId;
    }

    public void setPalletId(String palletId) {
        this.palletId = palletId;
    }

    public String getPalletNo() {
        return palletNo;
    }

    public void setPalletNo(String palletNo) {
        this.palletNo = palletNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
