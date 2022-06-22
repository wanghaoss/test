package com.etuo.kucun.model;

/**
 * Created by yhn on 2018/8/6.
 * 托盘详情内容列表
 */

public class PalletInfoListModel {


    private String   adressId;//地址id
    private String   batchNum;// 批次数
    private String   bond	;//   保证金
    private String  branchId	;// 网点ID
    private String   branchVehicleId	;//   车辆id
    private String  companyId	;//公司ID
    private String   dailyrents	;//  日租金
    private String   deliveryMode	;// 提货方式（0:用户自提 1:网点配送）
    private String    deposit	;//  押金金额
    private String    description	;//  描述
    private String   freight	;//   运费金额
    private String   getPalletTime	;//  取托时间
    private String   img	;//   缩略图
    private String   imgPath1;// 图1
    private String   leaseDays;//    估算租期
    private String  leaseOrderStatus;//

   // 租托订单状态（0:已取消 1:待确认 2:待支付 3:待出库 4:待配送 5:待收托 7:待评价 8:已完成 9:已删除 10:已支付 11:付款待确认 )

    private String  modelId	;// 托盘型号主键ID
    private String   oidUserId	;//   用户ID
    private String  orderBatchId;// 订单批次id
    private String  orderDate	;// 订单日期
    private String  orderDetailsId	;//    订单详情id
    private String   orderId	;//  订单ID
    private String  orderNum	;//   订单单号
    private String   orderSource	;// 订单来源(0:网点定制 1:厂家提供)
    private String  orderType	;//   托盘类型
    private String   palletModel	;  //托盘型号
    private String   palletName	;//  托盘名称
    private String   payMethod	;//

    //付款方式(0:企业付款 1:个人微信付款 2:个人支付宝付款 3:账户付款 4:个人微信+账户抵扣 5:个人支付宝+账户抵扣 6:公司+账户抵扣)

    private String   payNumber	;// 订单支付单号
    private String   quantity	;//   托盘数量
    private String   specHeight	;//托盘高
    private String  specLength	;//  托盘长
    private String   specWidth	;//   托盘宽
    private String   structureName	;// 结构名称
    private String   thumbnail	;//  缩略图

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    private String productType;


    public String getAdressId() {
        return adressId;
    }

    public void setAdressId(String adressId) {
        this.adressId = adressId;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public String getBond() {
        return bond;
    }

    public void setBond(String bond) {
        this.bond = bond;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchVehicleId() {
        return branchVehicleId;
    }

    public void setBranchVehicleId(String branchVehicleId) {
        this.branchVehicleId = branchVehicleId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDailyrents() {
        return dailyrents;
    }

    public void setDailyrents(String dailyrents) {
        this.dailyrents = dailyrents;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getGetPalletTime() {
        return getPalletTime;
    }

    public void setGetPalletTime(String getPalletTime) {
        this.getPalletTime = getPalletTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgPath1() {
        return imgPath1;
    }

    public void setImgPath1(String imgPath1) {
        this.imgPath1 = imgPath1;
    }

    public String getLeaseDays() {
        return leaseDays;
    }

    public void setLeaseDays(String leaseDays) {
        this.leaseDays = leaseDays;
    }

    public String getLeaseOrderStatus() {
        return leaseOrderStatus;
    }

    public void setLeaseOrderStatus(String leaseOrderStatus) {
        this.leaseOrderStatus = leaseOrderStatus;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getOidUserId() {
        return oidUserId;
    }

    public void setOidUserId(String oidUserId) {
        this.oidUserId = oidUserId;
    }

    public String getOrderBatchId() {
        return orderBatchId;
    }

    public void setOrderBatchId(String orderBatchId) {
        this.orderBatchId = orderBatchId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(String orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
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

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPalletModel() {
        return palletModel;
    }

    public void setPalletModel(String palletModel) {
        this.palletModel = palletModel;
    }

    public String getPalletName() {
        return palletName;
    }

    public void setPalletName(String palletName) {
        this.palletName = palletName;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSpecHeight() {
        return specHeight;
    }

    public void setSpecHeight(String specHeight) {
        this.specHeight = specHeight;
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

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
