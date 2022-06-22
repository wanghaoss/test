package com.etuo.kucun.model;

/**
 * Created by yhn on 2018/9/20.
 *
 */

public class StockDetailsListModel {

   private String recordId;//		订单ID

    private String    img;//		图片

    private String   COMPANY_NAME;//		型号名称

    private String specLength;//		长

    private String specWidth;//	宽

    private String specHeight;//		高

    private String   palletModel;//		型号

    private String bond;//		保证金

    private String dailyrents;//		日租金

    private String quantity;//		数量

    private String palletName;//托盘 名称
    private String state;// 状态值

    private String description;	// 描述

    private String imgPath1	;// 图1
    private String insDate	;//    作成日

    private String  modelId	;// 托盘型号主键ID

    private String  orderId	;//订单ID

    private String orderNum	 ;// 订单单号

    private String palletCount;//    盘点数

    private String thumbnail;//	 缩略图

    private String updDate	;// 更新日

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    private String productType;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath1() {
        return imgPath1;
    }

    public void setImgPath1(String imgPath1) {
        this.imgPath1 = imgPath1;
    }

    public String getInsDate() {
        return insDate;
    }

    public void setInsDate(String insDate) {
        this.insDate = insDate;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPalletCount() {
        return palletCount;
    }

    public void setPalletCount(String palletCount) {
        this.palletCount = palletCount;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUpdDate() {
        return updDate;
    }

    public void setUpdDate(String updDate) {
        this.updDate = updDate;
    }

    public String getPalletName() {
        return palletName;
    }

    public void setPalletName(String palletName) {
        this.palletName = palletName;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getimg() {
        return img;
    }

    public void setimg(String img) {
        this.img = img;
    }

    public String getCOMPANY_NAME() {
        return COMPANY_NAME;
    }

    public void setCOMPANY_NAME(String COMPANY_NAME) {
        this.COMPANY_NAME = COMPANY_NAME;
    }

    public String getSpecLength() {
        return specLength;
    }

    public void setSpecLength(String specLength) {
        this.specLength = specLength;
    }

    public String getSpecWidth() {
        return specWidth;
    }

    public void setSpecWidth(String specWidth) {
        this.specWidth = specWidth;
    }

    public String getSpecHeight() {
        return specHeight;
    }

    public void setSpecHeight(String specHeight) {
        this.specHeight = specHeight;
    }

    public String getPalletModel() {
        return palletModel;
    }

    public void setPalletModel(String palletModel) {
        this.palletModel = palletModel;
    }

    public String getBond() {
        return bond;
    }

    public void setBond(String bond) {
        this.bond = bond;
    }

    public String getDailyrents() {
        return dailyrents;
    }

    public void setDailyrents(String dailyrents) {
        this.dailyrents = dailyrents;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
