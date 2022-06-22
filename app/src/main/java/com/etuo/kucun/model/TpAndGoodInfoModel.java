package com.etuo.kucun.model;

/**
 * Created by yhn on 2020/2/18.
 * 弹出框用来显示扫描到的托盘信息
 */

public class TpAndGoodInfoModel {


    private String palletNum;//托盘编号
    private String  palletId;//托盘ID

    private int goodsCnt;
    private int goodsWeight;

    private String storageAreaName;

    private String prepareDetailId;
    private String prepareId;
    private String status;

    public String getPalletNum() {
        return palletNum;
    }

    public void setPalletNum(String palletNum) {
        this.palletNum = palletNum;
    }

    public String getPalletId() {
        return palletId;
    }

    public void setPalletId(String palletId) {
        this.palletId = palletId;
    }

    public int getGoodsCnt() {
        return goodsCnt;
    }

    public void setGoodsCnt(int goodsCnt) {
        this.goodsCnt = goodsCnt;
    }

    public int getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(int goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getStorageAreaName() {
        return storageAreaName;
    }

    public void setStorageAreaName(String storageAreaName) {
        this.storageAreaName = storageAreaName;
    }

    public String getPrepareDetailId() {
        return prepareDetailId;
    }

    public void setPrepareDetailId(String prepareDetailId) {
        this.prepareDetailId = prepareDetailId;
    }

    public String getPrepareId() {
        return prepareId;
    }

    public void setPrepareId(String prepareId) {
        this.prepareId = prepareId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
