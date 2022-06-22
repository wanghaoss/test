package com.etuo.kucun.model;

/**
 * Created by yhn on 2020/2/12.
 */

public class OrderCommitModel {

    /**
     * "prepareDetailId": "2",
     "status": "0",//货物备货单状态：0未备货 1已备货 2解绑
     "opEquipment": "1"//操作设备 1手持设备 2门设备 3人工
     */

    private String prepareDetailId;//备货
    private String status;
    private String opEquipment;
    private String goodsCheckDetailId;//货物验收

    private String palletNum;//托盘编号

    private String storageOutDetailId;//出库
    private String palletStorageInDetailId;// 托盘入库

    private String goodsStorageInDetailId;//货物入库

    private String palletCheckDetailId;//托盘验收

    private String transferDetailId;// 转库

    public String getTransferDetailId() {
        return transferDetailId;
    }

    public void setTransferDetailId(String transferDetailId) {
        this.transferDetailId = transferDetailId;
    }

    public String getPalletNum() {
        return palletNum;
    }

    public void setPalletNum(String palletNum) {
        this.palletNum = palletNum;
    }

    public String getPalletCheckDetailId() {
        return palletCheckDetailId;
    }

    public void setPalletCheckDetailId(String palletCheckDetailId) {
        this.palletCheckDetailId = palletCheckDetailId;
    }

    public String getGoodsStorageInDetailId() {
        return goodsStorageInDetailId;
    }

    public void setGoodsStorageInDetailId(String goodsStorageInDetailId) {
        this.goodsStorageInDetailId = goodsStorageInDetailId;
    }

    public String getPalletStorageInDetailId() {
        return palletStorageInDetailId;
    }

    public void setPalletStorageInDetailId(String palletStorageInDetailId) {
        this.palletStorageInDetailId = palletStorageInDetailId;
    }

    public String getStorageOutDetailId() {
        return storageOutDetailId;
    }

    public void setStorageOutDetailId(String storageOutDetailId) {
        this.storageOutDetailId = storageOutDetailId;
    }

    public String getGoodsCheckDetailId() {
        return goodsCheckDetailId;
    }

    public void setGoodsCheckDetailId(String goodsCheckDetailId) {
        this.goodsCheckDetailId = goodsCheckDetailId;
    }

    public String getPrepareDetailId() {

        return prepareDetailId;
    }

    public void setPrepareDetailId(String prepareDetailId) {
        this.prepareDetailId = prepareDetailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpEquipment() {
        return opEquipment;
    }

    public void setOpEquipment(String opEquipment) {
        this.opEquipment = opEquipment;
    }
}
