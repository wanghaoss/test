package com.etuo.kucun.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.etuo.kucun.utils.QuickSetParcelableUtil;

import java.util.List;

/**
 * Created by yhn on 2018/8/6.
 * 订单中心列表 第一层
 */

public class OrderListModel implements Parcelable {

    private String companyName;//			公司名称
    private String receiveName;//			联系人
    private String receiveMobile;//			联系电话
    private String receiveAddress;//			配送地址
    private String orderId;//		订单号
    private String link;//			收托跳转
    private String carLink;//		车辆跳转
    private String orderNum;//		订单单号

    private String orderBatchId;//   配车的id ???
    private String date;//		预计收托盘日期

    private String batchNum;//			批次

    private String batch;//			第几批次

    private String deliveryMode;//  0:显示联系客户   1:显示联系司机
    private String branchVehicleMobile;//  司机电话
    private String insDate; //下单时间

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    private String shortName;//公司简称
    public String getRealFlg() {
        return realFlg;
    }

    public void setRealFlg(String realFlg) {
        this.realFlg = realFlg;
    }

    public String getLeaseOrderStatus() {
        return leaseOrderStatus;
    }

    public void setLeaseOrderStatus(String leaseOrderStatus) {
        this.leaseOrderStatus = leaseOrderStatus;
    }

    private String realFlg;//为1时取state,为0时取leaseOrderStatus
    private String leaseOrderStatus;//
/// //租托订单状态（0:已取消 1:待确认 2:待支付 3:待出库 4:待配送 5:待收托 7:待评价 8:已完成 9:已删除 10:已支付 11:付款待确认 )

    private List<PalletInfoListModel> stockDetailList;// 托盘型号

    private String state;//状态（0:已取消 1:待配送 2:待交托 3:已交托 4:待入库 5 :已入库 )

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getBranchVehicleMobile() {
        return branchVehicleMobile;
    }

    public void setBranchVehicleMobile(String branchVehicleMobile) {
        this.branchVehicleMobile = branchVehicleMobile;
    }

    public String getInsDate() {
        return insDate;
    }

    public void setInsDate(String insDate) {
        this.insDate = insDate;
    }

    public String getOrderBatchId() {
        return orderBatchId;
    }

    public void setOrderBatchId(String orderBatchId) {
        this.orderBatchId = orderBatchId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCarLink() {
        return carLink;
    }

    public void setCarLink(String carLink) {
        this.carLink = carLink;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public List<PalletInfoListModel> getStockDetailList() {
        return stockDetailList;
    }

    public void setStockDetailList(List<PalletInfoListModel> stockDetailList) {
        this.stockDetailList = stockDetailList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        QuickSetParcelableUtil.write(dest, this);
    }

    public static final Creator<OrderListModel> CREATOR = new Creator<OrderListModel>() {

        @Override
        public OrderListModel createFromParcel(Parcel source) {
            OrderListModel obj = (OrderListModel) QuickSetParcelableUtil
                    .read(source, OrderListModel.class);
            return obj;
        }

        @Override
        public OrderListModel[] newArray(int size) {
            return new OrderListModel[size];
        }

    };
}
