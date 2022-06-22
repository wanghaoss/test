package com.etuo.kucun.model;

/**
 * Created by yhn on 2018/9/12.
 * 扫描
 */

public class ScanInfoModel {

    private String  orderType	;//	订单类型（0查看1收2转3还）
    private String   branchId	;//	网点ID
    private String   branchName	;//	网点名称
    private String   palletNum	;//	托盘ID
    private String   PalletLink	;//	URL //托盘产品详情页面
    private String  orderId	;//	订单ID
    private String  OrderDetailsLink;//		URL

    private String state;// 200 成功 ,其它扫码失败

    public String message;// 错误时的提示框

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPalletNum() {
        return palletNum;
    }

    public void setPalletNum(String palletNum) {
        this.palletNum = palletNum;
    }

    public String getPalletLink() {
        return PalletLink;
    }

    public void setPalletLink(String palletLink) {
        PalletLink = palletLink;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDetailsLink() {
        return OrderDetailsLink;
    }

    public void setOrderDetailsLink(String orderDetailsLink) {
        OrderDetailsLink = orderDetailsLink;
    }
}
