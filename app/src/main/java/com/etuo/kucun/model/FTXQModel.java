package com.etuo.kucun.model;

import java.util.List;

public class FTXQModel {

    /**
     * trailList : []
     * receiptImgPath : http://192.168.1.66:8080/boot/upload
     * deliveryTime : 2022-06-17 19:39:00.0
     * sendWeight : 0
     * goodsList :
     * logisticsOrderInformationVO : {"primaryKey":null,"findKeywords":null,"findStatus":null,"pageIndex":0,"pageSize":0,"updUserId":null,"dateFrom":null,"dateTo":null,"sortField":null,"sort":null,"browseCnt":0,"logisticsInformationId":"1655466120990710","logisticsOrderId":"F220617193933K6N","deliverOidUserId":null,"logisticsOrderNum":"F220617193933K6N","orderNum":null,"driverId":null,"driverName":"张张张","driverPhone":"18511334002","logisticsCompanyName":"个人","driverVehicleNo":"晋A12345","driverVehicleType":null,"origin":null,"destination":null,"goToOriginTime":" ","goToOriginCreateLgt":null,"goToOriginCreateLat":null,"arriveOriginTime":" ","arriveOriginCreateLgt":null,"arriveOriginCreateLat":null,"loadingCompletedTime":" ","loadingCompletedQuantity":0,"officialReceiptsQuantity":null,"differenceValue":null,"departureTime":" ","arriveDestinationTime":" ","freight":null,"arriveDestinationCreateLgt":null,"arriveDestinationCreateLat":null,"logisticsStatus":null,"originCompany":"上海运联科技股份有限公司","originProvince":null,"originCity":null,"originArea":null,"originAddress":"山东省青岛市即墨区金口镇店集镇西里村173号","originContacts":"张善","originContactsPhone":"17301804048","destinationCompany":"山西三强新能源科技有限公司","destinationProvince":null,"destinationCity":null,"destinationArea":null,"destinationAddress":"山西省太原市清徐县中高白村","destinationContacts":"张志鹏","destinationContactsPhone":"13889466009","destinationContactsId":null,"palletModel":null,"quantity":null,"driveTime":"2022-06-18 19:36:46","ask":null,"logisticsCompanyId":"1606039135613856","logisticsOrderStatus":"1","insUserId":null,"insDate":"2022-06-17 19:36:32","updDate":null,"dispatchId":"F220617193933K6N","memo":"","distance":"10","days":"0.5","estimatedTime":"2022-06-17 19:41:41","status":"1","evaluate":null,"comment":null,"userType":null,"deliveryTime":null,"yearMonth":null,"cardId":"","cardIds":"","driverLicense":"","driverLicenses":"","drivingLicense":"","drivingLicenses":"","environmentProtection":"","other":"","receiptProvince":null,"receiptCity":null,"receiptArea":null,"deliverProvince":null,"deliverCity":null,"deliverArea":null,"deliverType":null,"logisticsAdminOidUserId":"","receiverId":null,"deliverUser":null,"deliverPhone":null,"receiptUser":null,"receiptPhone":null,"driverEndDate":null,"driverBeginDate":null,"inOutType":null,"deliverManagerName":null,"receiptManagerName":null,"receiptTime":null,"receiptImgPath":null,"logisticsFreight":null,"freightReceivedAmount":null,"freightPayStatus":null,"freightPayDate":null,"orderStatus":null,"orderBatchId":null,"proxiesPdfPath":null,"approveRemark":null,"approveStatus":null,"receiptComapnyShortName":null,"palletModelList":null,"detailList":null,"billDto":null,"goodsList":null,"dynamicImageDtoList":[],"dynamicImageList":[],"urlPathList":null,"scanCodeType":null,"chipType":null,"mangerImgPath":null,"orderId":null}
     * orderStatus : 配送中
     * memo : 测试
     * acceptanceCheckImgPath :
     * deliverCompany : 1603765222832372
     * receiptsWeight : 0
     * palletList : []
     * checkStatus : 3
     * carNo :
     * receiveCompany : 山西三强新能源科技有限公司
     * driverMobile :
     * deliveryDate : 2022-06-18 19:36:46
     * signMemo :
     * deliverUser : 张善
     * orderNo : F220617193933K6N
     * receiver : 张志鹏
     * deliverPhone : 17301804048
     * insDate : 2022-06-17 19:36:32
     * receiveAddress : 山西省太原市清徐县中高白村
     * receiptCountInfo : {"transMemo":"","deliveryList":"","inspectionReportImgPath":"","checkStatus":"3","receiptPalletList":[{"primaryKey":null,"findKeywords":null,"findStatus":null,"pageIndex":0,"pageSize":0,"updUserId":null,"dateFrom":null,"dateTo":null,"sortField":null,"sort":null,"browseCnt":0,"detailId":null,"dispatchId":null,"modelId":"1648718128914686","quantity":0,"palletName":null,"palletModel":null,"specLength":null,"specWidth":null,"specHeight":null,"palletSpec":null,"oidUserId":null,"insDate":null,"insUserId":null,"insUserName":null,"updDate":null,"delFlg":null,"palletStatus":"1","fullPalletCount":null,"breakagePalletCount":null,"checkStatus":null,"breakagePalletImgPath":null,"companyId":null,"companyName":null,"palletInfo":null,"receiptCount":null,"deliverCount":null,"deliverUpCount":null,"deliverDownCount":null,"residueCount":null,"damageCount":null,"issuedAmount":null,"unissuedAmount":null,"signDescription":null,"companyType":null,"fullPalletQuantity":"6","sendQUANTITY":"0","officialReceiptsQuantity":"0","breakagePalletQuantity":"0","receiptCountInfo":null,"differenceValue":"0","inAndOutTime":null,"receiveLoad":null,"deliverLoad":null,"siteFee":null,"sortFee":null,"managementExpense":null,"goodsName":null,"goodsQuantity":"0","residueEstimateCount":null,"receiptCompanyId":null,"deliverCompany":null,"inOutType":null,"typeDriverType":null,"materialName":null,"thumbnail":null,"imgPath1":null,"goodsWeight":null,"weight":null,"dynamicImageDtoList":null,"dynamicImageList":null}],"shippingInformation":"","signDescription":"","breakagePalletImgPath":""}
     * receiverMobile : 13889466009
     * deliveryType : 0
     * receiveDate : null
     * differenceWeight : 0.0
     * deliverOidUserId : 1635501557456456
     * driverName :
     * scanCodeType : 0
     * chipType : 1
     * status : 1
     */

    private String receiptImgPath;
    private String deliveryTime;
    private String sendWeight;
    private String goodsList;
    private LogisticsOrderInformationVOBean logisticsOrderInformationVO;
    private String orderStatus;
    private String memo;
    private String acceptanceCheckImgPath;
    private String deliverCompany;
    private String receiptsWeight;
    private String checkStatus;
    private String carNo;
    private String receiveCompany;
    private String driverMobile;
    private String deliveryDate;
    private String signMemo;
    private String deliverUser;
    private String orderNo;
    private String receiver;
    private String deliverPhone;
    private String insDate;
    private String receiveAddress;
    private ReceiptCountInfoBean receiptCountInfo;
    private String receiverMobile;
    private String deliveryType;
    private Object receiveDate;
    private double differenceWeight;
    private String deliverOidUserId;
    private String driverName;
    private String scanCodeType;
    private String chipType;
    private String status;
    private List<?> trailList;
    private List<?> palletList;

    public String getReceiptImgPath() {
        return receiptImgPath;
    }

    public void setReceiptImgPath(String receiptImgPath) {
        this.receiptImgPath = receiptImgPath;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getSendWeight() {
        return sendWeight;
    }

    public void setSendWeight(String sendWeight) {
        this.sendWeight = sendWeight;
    }

    public String getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(String goodsList) {
        this.goodsList = goodsList;
    }

    public LogisticsOrderInformationVOBean getLogisticsOrderInformationVO() {
        return logisticsOrderInformationVO;
    }

    public void setLogisticsOrderInformationVO(LogisticsOrderInformationVOBean logisticsOrderInformationVO) {
        this.logisticsOrderInformationVO = logisticsOrderInformationVO;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAcceptanceCheckImgPath() {
        return acceptanceCheckImgPath;
    }

    public void setAcceptanceCheckImgPath(String acceptanceCheckImgPath) {
        this.acceptanceCheckImgPath = acceptanceCheckImgPath;
    }

    public String getDeliverCompany() {
        return deliverCompany;
    }

    public void setDeliverCompany(String deliverCompany) {
        this.deliverCompany = deliverCompany;
    }

    public String getReceiptsWeight() {
        return receiptsWeight;
    }

    public void setReceiptsWeight(String receiptsWeight) {
        this.receiptsWeight = receiptsWeight;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getReceiveCompany() {
        return receiveCompany;
    }

    public void setReceiveCompany(String receiveCompany) {
        this.receiveCompany = receiveCompany;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getSignMemo() {
        return signMemo;
    }

    public void setSignMemo(String signMemo) {
        this.signMemo = signMemo;
    }

    public String getDeliverUser() {
        return deliverUser;
    }

    public void setDeliverUser(String deliverUser) {
        this.deliverUser = deliverUser;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDeliverPhone() {
        return deliverPhone;
    }

    public void setDeliverPhone(String deliverPhone) {
        this.deliverPhone = deliverPhone;
    }

    public String getInsDate() {
        return insDate;
    }

    public void setInsDate(String insDate) {
        this.insDate = insDate;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public ReceiptCountInfoBean getReceiptCountInfo() {
        return receiptCountInfo;
    }

    public void setReceiptCountInfo(ReceiptCountInfoBean receiptCountInfo) {
        this.receiptCountInfo = receiptCountInfo;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Object getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Object receiveDate) {
        this.receiveDate = receiveDate;
    }

    public double getDifferenceWeight() {
        return differenceWeight;
    }

    public void setDifferenceWeight(double differenceWeight) {
        this.differenceWeight = differenceWeight;
    }

    public String getDeliverOidUserId() {
        return deliverOidUserId;
    }

    public void setDeliverOidUserId(String deliverOidUserId) {
        this.deliverOidUserId = deliverOidUserId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getScanCodeType() {
        return scanCodeType;
    }

    public void setScanCodeType(String scanCodeType) {
        this.scanCodeType = scanCodeType;
    }

    public String getChipType() {
        return chipType;
    }

    public void setChipType(String chipType) {
        this.chipType = chipType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<?> getTrailList() {
        return trailList;
    }

    public void setTrailList(List<?> trailList) {
        this.trailList = trailList;
    }

    public List<?> getPalletList() {
        return palletList;
    }

    public void setPalletList(List<?> palletList) {
        this.palletList = palletList;
    }

    public static class LogisticsOrderInformationVOBean {
        /**
         * primaryKey : null
         * findKeywords : null
         * findStatus : null
         * pageIndex : 0
         * pageSize : 0
         * updUserId : null
         * dateFrom : null
         * dateTo : null
         * sortField : null
         * sort : null
         * browseCnt : 0
         * logisticsInformationId : 1655466120990710
         * logisticsOrderId : F220617193933K6N
         * deliverOidUserId : null
         * logisticsOrderNum : F220617193933K6N
         * orderNum : null
         * driverId : null
         * driverName : 张张张
         * driverPhone : 18511334002
         * logisticsCompanyName : 个人
         * driverVehicleNo : 晋A12345
         * driverVehicleType : null
         * origin : null
         * destination : null
         * goToOriginTime :
         * goToOriginCreateLgt : null
         * goToOriginCreateLat : null
         * arriveOriginTime :
         * arriveOriginCreateLgt : null
         * arriveOriginCreateLat : null
         * loadingCompletedTime :
         * loadingCompletedQuantity : 0
         * officialReceiptsQuantity : null
         * differenceValue : null
         * departureTime :
         * arriveDestinationTime :
         * freight : null
         * arriveDestinationCreateLgt : null
         * arriveDestinationCreateLat : null
         * logisticsStatus : null
         * originCompany : 上海运联科技股份有限公司
         * originProvince : null
         * originCity : null
         * originArea : null
         * originAddress : 山东省青岛市即墨区金口镇店集镇西里村173号
         * originContacts : 张善
         * originContactsPhone : 17301804048
         * destinationCompany : 山西三强新能源科技有限公司
         * destinationProvince : null
         * destinationCity : null
         * destinationArea : null
         * destinationAddress : 山西省太原市清徐县中高白村
         * destinationContacts : 张志鹏
         * destinationContactsPhone : 13889466009
         * destinationContactsId : null
         * palletModel : null
         * quantity : null
         * driveTime : 2022-06-18 19:36:46
         * ask : null
         * logisticsCompanyId : 1606039135613856
         * logisticsOrderStatus : 1
         * insUserId : null
         * insDate : 2022-06-17 19:36:32
         * updDate : null
         * dispatchId : F220617193933K6N
         * memo :
         * distance : 10
         * days : 0.5
         * estimatedTime : 2022-06-17 19:41:41
         * status : 1
         * evaluate : null
         * comment : null
         * userType : null
         * deliveryTime : null
         * yearMonth : null
         * cardId :
         * cardIds :
         * driverLicense :
         * driverLicenses :
         * drivingLicense :
         * drivingLicenses :
         * environmentProtection :
         * other :
         * receiptProvince : null
         * receiptCity : null
         * receiptArea : null
         * deliverProvince : null
         * deliverCity : null
         * deliverArea : null
         * deliverType : null
         * logisticsAdminOidUserId :
         * receiverId : null
         * deliverUser : null
         * deliverPhone : null
         * receiptUser : null
         * receiptPhone : null
         * driverEndDate : null
         * driverBeginDate : null
         * inOutType : null
         * deliverManagerName : null
         * receiptManagerName : null
         * receiptTime : null
         * receiptImgPath : null
         * logisticsFreight : null
         * freightReceivedAmount : null
         * freightPayStatus : null
         * freightPayDate : null
         * orderStatus : null
         * orderBatchId : null
         * proxiesPdfPath : null
         * approveRemark : null
         * approveStatus : null
         * receiptComapnyShortName : null
         * palletModelList : null
         * detailList : null
         * billDto : null
         * goodsList : null
         * dynamicImageDtoList : []
         * dynamicImageList : []
         * urlPathList : null
         * scanCodeType : null
         * chipType : null
         * mangerImgPath : null
         * orderId : null
         */

        private Object primaryKey;
        private Object findKeywords;
        private Object findStatus;
        private int pageIndex;
        private int pageSize;
        private Object updUserId;
        private Object dateFrom;
        private Object dateTo;
        private Object sortField;
        private Object sort;
        private int browseCnt;
        private String logisticsInformationId;
        private String logisticsOrderId;
        private Object deliverOidUserId;
        private String logisticsOrderNum;
        private Object orderNum;
        private Object driverId;
        private String driverName;
        private String driverPhone;
        private String logisticsCompanyName;
        private String driverVehicleNo;
        private Object driverVehicleType;
        private Object origin;
        private Object destination;
        private String goToOriginTime;
        private Object goToOriginCreateLgt;
        private Object goToOriginCreateLat;
        private String arriveOriginTime;
        private Object arriveOriginCreateLgt;
        private Object arriveOriginCreateLat;
        private String loadingCompletedTime;
        private int loadingCompletedQuantity;
        private Object officialReceiptsQuantity;
        private Object differenceValue;
        private String departureTime;
        private String arriveDestinationTime;
        private Object freight;
        private Object arriveDestinationCreateLgt;
        private Object arriveDestinationCreateLat;
        private Object logisticsStatus;
        private String originCompany;
        private Object originProvince;
        private Object originCity;
        private Object originArea;
        private String originAddress;
        private String originContacts;
        private String originContactsPhone;
        private String destinationCompany;
        private Object destinationProvince;
        private Object destinationCity;
        private Object destinationArea;
        private String destinationAddress;
        private String destinationContacts;
        private String destinationContactsPhone;
        private Object destinationContactsId;
        private Object palletModel;
        private Object quantity;
        private String driveTime;
        private Object ask;
        private String logisticsCompanyId;
        private String logisticsOrderStatus;
        private Object insUserId;
        private String insDate;
        private Object updDate;
        private String dispatchId;
        private String memo;
        private String distance;
        private String days;
        private String estimatedTime;
        private String status;
        private Object evaluate;
        private Object comment;
        private Object userType;
        private Object deliveryTime;
        private Object yearMonth;
        private String cardId;
        private String cardIds;
        private String driverLicense;
        private String driverLicenses;
        private String drivingLicense;
        private String drivingLicenses;
        private String environmentProtection;
        private String other;
        private Object receiptProvince;
        private Object receiptCity;
        private Object receiptArea;
        private Object deliverProvince;
        private Object deliverCity;
        private Object deliverArea;
        private Object deliverType;
        private String logisticsAdminOidUserId;
        private Object receiverId;
        private Object deliverUser;
        private Object deliverPhone;
        private Object receiptUser;
        private Object receiptPhone;
        private Object driverEndDate;
        private Object driverBeginDate;
        private Object inOutType;
        private Object deliverManagerName;
        private Object receiptManagerName;
        private Object receiptTime;
        private Object receiptImgPath;
        private Object logisticsFreight;
        private Object freightReceivedAmount;
        private Object freightPayStatus;
        private Object freightPayDate;
        private Object orderStatus;
        private Object orderBatchId;
        private Object proxiesPdfPath;
        private Object approveRemark;
        private Object approveStatus;
        private Object receiptComapnyShortName;
        private Object palletModelList;
        private Object detailList;
        private Object billDto;
        private Object goodsList;
        private Object urlPathList;
        private Object scanCodeType;
        private Object chipType;
        private Object mangerImgPath;
        private Object orderId;
        private List<?> dynamicImageDtoList;
        private List<?> dynamicImageList;

        public Object getPrimaryKey() {
            return primaryKey;
        }

        public void setPrimaryKey(Object primaryKey) {
            this.primaryKey = primaryKey;
        }

        public Object getFindKeywords() {
            return findKeywords;
        }

        public void setFindKeywords(Object findKeywords) {
            this.findKeywords = findKeywords;
        }

        public Object getFindStatus() {
            return findStatus;
        }

        public void setFindStatus(Object findStatus) {
            this.findStatus = findStatus;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public Object getUpdUserId() {
            return updUserId;
        }

        public void setUpdUserId(Object updUserId) {
            this.updUserId = updUserId;
        }

        public Object getDateFrom() {
            return dateFrom;
        }

        public void setDateFrom(Object dateFrom) {
            this.dateFrom = dateFrom;
        }

        public Object getDateTo() {
            return dateTo;
        }

        public void setDateTo(Object dateTo) {
            this.dateTo = dateTo;
        }

        public Object getSortField() {
            return sortField;
        }

        public void setSortField(Object sortField) {
            this.sortField = sortField;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public int getBrowseCnt() {
            return browseCnt;
        }

        public void setBrowseCnt(int browseCnt) {
            this.browseCnt = browseCnt;
        }

        public String getLogisticsInformationId() {
            return logisticsInformationId;
        }

        public void setLogisticsInformationId(String logisticsInformationId) {
            this.logisticsInformationId = logisticsInformationId;
        }

        public String getLogisticsOrderId() {
            return logisticsOrderId;
        }

        public void setLogisticsOrderId(String logisticsOrderId) {
            this.logisticsOrderId = logisticsOrderId;
        }

        public Object getDeliverOidUserId() {
            return deliverOidUserId;
        }

        public void setDeliverOidUserId(Object deliverOidUserId) {
            this.deliverOidUserId = deliverOidUserId;
        }

        public String getLogisticsOrderNum() {
            return logisticsOrderNum;
        }

        public void setLogisticsOrderNum(String logisticsOrderNum) {
            this.logisticsOrderNum = logisticsOrderNum;
        }

        public Object getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(Object orderNum) {
            this.orderNum = orderNum;
        }

        public Object getDriverId() {
            return driverId;
        }

        public void setDriverId(Object driverId) {
            this.driverId = driverId;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getDriverPhone() {
            return driverPhone;
        }

        public void setDriverPhone(String driverPhone) {
            this.driverPhone = driverPhone;
        }

        public String getLogisticsCompanyName() {
            return logisticsCompanyName;
        }

        public void setLogisticsCompanyName(String logisticsCompanyName) {
            this.logisticsCompanyName = logisticsCompanyName;
        }

        public String getDriverVehicleNo() {
            return driverVehicleNo;
        }

        public void setDriverVehicleNo(String driverVehicleNo) {
            this.driverVehicleNo = driverVehicleNo;
        }

        public Object getDriverVehicleType() {
            return driverVehicleType;
        }

        public void setDriverVehicleType(Object driverVehicleType) {
            this.driverVehicleType = driverVehicleType;
        }

        public Object getOrigin() {
            return origin;
        }

        public void setOrigin(Object origin) {
            this.origin = origin;
        }

        public Object getDestination() {
            return destination;
        }

        public void setDestination(Object destination) {
            this.destination = destination;
        }

        public String getGoToOriginTime() {
            return goToOriginTime;
        }

        public void setGoToOriginTime(String goToOriginTime) {
            this.goToOriginTime = goToOriginTime;
        }

        public Object getGoToOriginCreateLgt() {
            return goToOriginCreateLgt;
        }

        public void setGoToOriginCreateLgt(Object goToOriginCreateLgt) {
            this.goToOriginCreateLgt = goToOriginCreateLgt;
        }

        public Object getGoToOriginCreateLat() {
            return goToOriginCreateLat;
        }

        public void setGoToOriginCreateLat(Object goToOriginCreateLat) {
            this.goToOriginCreateLat = goToOriginCreateLat;
        }

        public String getArriveOriginTime() {
            return arriveOriginTime;
        }

        public void setArriveOriginTime(String arriveOriginTime) {
            this.arriveOriginTime = arriveOriginTime;
        }

        public Object getArriveOriginCreateLgt() {
            return arriveOriginCreateLgt;
        }

        public void setArriveOriginCreateLgt(Object arriveOriginCreateLgt) {
            this.arriveOriginCreateLgt = arriveOriginCreateLgt;
        }

        public Object getArriveOriginCreateLat() {
            return arriveOriginCreateLat;
        }

        public void setArriveOriginCreateLat(Object arriveOriginCreateLat) {
            this.arriveOriginCreateLat = arriveOriginCreateLat;
        }

        public String getLoadingCompletedTime() {
            return loadingCompletedTime;
        }

        public void setLoadingCompletedTime(String loadingCompletedTime) {
            this.loadingCompletedTime = loadingCompletedTime;
        }

        public int getLoadingCompletedQuantity() {
            return loadingCompletedQuantity;
        }

        public void setLoadingCompletedQuantity(int loadingCompletedQuantity) {
            this.loadingCompletedQuantity = loadingCompletedQuantity;
        }

        public Object getOfficialReceiptsQuantity() {
            return officialReceiptsQuantity;
        }

        public void setOfficialReceiptsQuantity(Object officialReceiptsQuantity) {
            this.officialReceiptsQuantity = officialReceiptsQuantity;
        }

        public Object getDifferenceValue() {
            return differenceValue;
        }

        public void setDifferenceValue(Object differenceValue) {
            this.differenceValue = differenceValue;
        }

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public String getArriveDestinationTime() {
            return arriveDestinationTime;
        }

        public void setArriveDestinationTime(String arriveDestinationTime) {
            this.arriveDestinationTime = arriveDestinationTime;
        }

        public Object getFreight() {
            return freight;
        }

        public void setFreight(Object freight) {
            this.freight = freight;
        }

        public Object getArriveDestinationCreateLgt() {
            return arriveDestinationCreateLgt;
        }

        public void setArriveDestinationCreateLgt(Object arriveDestinationCreateLgt) {
            this.arriveDestinationCreateLgt = arriveDestinationCreateLgt;
        }

        public Object getArriveDestinationCreateLat() {
            return arriveDestinationCreateLat;
        }

        public void setArriveDestinationCreateLat(Object arriveDestinationCreateLat) {
            this.arriveDestinationCreateLat = arriveDestinationCreateLat;
        }

        public Object getLogisticsStatus() {
            return logisticsStatus;
        }

        public void setLogisticsStatus(Object logisticsStatus) {
            this.logisticsStatus = logisticsStatus;
        }

        public String getOriginCompany() {
            return originCompany;
        }

        public void setOriginCompany(String originCompany) {
            this.originCompany = originCompany;
        }

        public Object getOriginProvince() {
            return originProvince;
        }

        public void setOriginProvince(Object originProvince) {
            this.originProvince = originProvince;
        }

        public Object getOriginCity() {
            return originCity;
        }

        public void setOriginCity(Object originCity) {
            this.originCity = originCity;
        }

        public Object getOriginArea() {
            return originArea;
        }

        public void setOriginArea(Object originArea) {
            this.originArea = originArea;
        }

        public String getOriginAddress() {
            return originAddress;
        }

        public void setOriginAddress(String originAddress) {
            this.originAddress = originAddress;
        }

        public String getOriginContacts() {
            return originContacts;
        }

        public void setOriginContacts(String originContacts) {
            this.originContacts = originContacts;
        }

        public String getOriginContactsPhone() {
            return originContactsPhone;
        }

        public void setOriginContactsPhone(String originContactsPhone) {
            this.originContactsPhone = originContactsPhone;
        }

        public String getDestinationCompany() {
            return destinationCompany;
        }

        public void setDestinationCompany(String destinationCompany) {
            this.destinationCompany = destinationCompany;
        }

        public Object getDestinationProvince() {
            return destinationProvince;
        }

        public void setDestinationProvince(Object destinationProvince) {
            this.destinationProvince = destinationProvince;
        }

        public Object getDestinationCity() {
            return destinationCity;
        }

        public void setDestinationCity(Object destinationCity) {
            this.destinationCity = destinationCity;
        }

        public Object getDestinationArea() {
            return destinationArea;
        }

        public void setDestinationArea(Object destinationArea) {
            this.destinationArea = destinationArea;
        }

        public String getDestinationAddress() {
            return destinationAddress;
        }

        public void setDestinationAddress(String destinationAddress) {
            this.destinationAddress = destinationAddress;
        }

        public String getDestinationContacts() {
            return destinationContacts;
        }

        public void setDestinationContacts(String destinationContacts) {
            this.destinationContacts = destinationContacts;
        }

        public String getDestinationContactsPhone() {
            return destinationContactsPhone;
        }

        public void setDestinationContactsPhone(String destinationContactsPhone) {
            this.destinationContactsPhone = destinationContactsPhone;
        }

        public Object getDestinationContactsId() {
            return destinationContactsId;
        }

        public void setDestinationContactsId(Object destinationContactsId) {
            this.destinationContactsId = destinationContactsId;
        }

        public Object getPalletModel() {
            return palletModel;
        }

        public void setPalletModel(Object palletModel) {
            this.palletModel = palletModel;
        }

        public Object getQuantity() {
            return quantity;
        }

        public void setQuantity(Object quantity) {
            this.quantity = quantity;
        }

        public String getDriveTime() {
            return driveTime;
        }

        public void setDriveTime(String driveTime) {
            this.driveTime = driveTime;
        }

        public Object getAsk() {
            return ask;
        }

        public void setAsk(Object ask) {
            this.ask = ask;
        }

        public String getLogisticsCompanyId() {
            return logisticsCompanyId;
        }

        public void setLogisticsCompanyId(String logisticsCompanyId) {
            this.logisticsCompanyId = logisticsCompanyId;
        }

        public String getLogisticsOrderStatus() {
            return logisticsOrderStatus;
        }

        public void setLogisticsOrderStatus(String logisticsOrderStatus) {
            this.logisticsOrderStatus = logisticsOrderStatus;
        }

        public Object getInsUserId() {
            return insUserId;
        }

        public void setInsUserId(Object insUserId) {
            this.insUserId = insUserId;
        }

        public String getInsDate() {
            return insDate;
        }

        public void setInsDate(String insDate) {
            this.insDate = insDate;
        }

        public Object getUpdDate() {
            return updDate;
        }

        public void setUpdDate(Object updDate) {
            this.updDate = updDate;
        }

        public String getDispatchId() {
            return dispatchId;
        }

        public void setDispatchId(String dispatchId) {
            this.dispatchId = dispatchId;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getEstimatedTime() {
            return estimatedTime;
        }

        public void setEstimatedTime(String estimatedTime) {
            this.estimatedTime = estimatedTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(Object evaluate) {
            this.evaluate = evaluate;
        }

        public Object getComment() {
            return comment;
        }

        public void setComment(Object comment) {
            this.comment = comment;
        }

        public Object getUserType() {
            return userType;
        }

        public void setUserType(Object userType) {
            this.userType = userType;
        }

        public Object getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(Object deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public Object getYearMonth() {
            return yearMonth;
        }

        public void setYearMonth(Object yearMonth) {
            this.yearMonth = yearMonth;
        }

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        public String getCardIds() {
            return cardIds;
        }

        public void setCardIds(String cardIds) {
            this.cardIds = cardIds;
        }

        public String getDriverLicense() {
            return driverLicense;
        }

        public void setDriverLicense(String driverLicense) {
            this.driverLicense = driverLicense;
        }

        public String getDriverLicenses() {
            return driverLicenses;
        }

        public void setDriverLicenses(String driverLicenses) {
            this.driverLicenses = driverLicenses;
        }

        public String getDrivingLicense() {
            return drivingLicense;
        }

        public void setDrivingLicense(String drivingLicense) {
            this.drivingLicense = drivingLicense;
        }

        public String getDrivingLicenses() {
            return drivingLicenses;
        }

        public void setDrivingLicenses(String drivingLicenses) {
            this.drivingLicenses = drivingLicenses;
        }

        public String getEnvironmentProtection() {
            return environmentProtection;
        }

        public void setEnvironmentProtection(String environmentProtection) {
            this.environmentProtection = environmentProtection;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public Object getReceiptProvince() {
            return receiptProvince;
        }

        public void setReceiptProvince(Object receiptProvince) {
            this.receiptProvince = receiptProvince;
        }

        public Object getReceiptCity() {
            return receiptCity;
        }

        public void setReceiptCity(Object receiptCity) {
            this.receiptCity = receiptCity;
        }

        public Object getReceiptArea() {
            return receiptArea;
        }

        public void setReceiptArea(Object receiptArea) {
            this.receiptArea = receiptArea;
        }

        public Object getDeliverProvince() {
            return deliverProvince;
        }

        public void setDeliverProvince(Object deliverProvince) {
            this.deliverProvince = deliverProvince;
        }

        public Object getDeliverCity() {
            return deliverCity;
        }

        public void setDeliverCity(Object deliverCity) {
            this.deliverCity = deliverCity;
        }

        public Object getDeliverArea() {
            return deliverArea;
        }

        public void setDeliverArea(Object deliverArea) {
            this.deliverArea = deliverArea;
        }

        public Object getDeliverType() {
            return deliverType;
        }

        public void setDeliverType(Object deliverType) {
            this.deliverType = deliverType;
        }

        public String getLogisticsAdminOidUserId() {
            return logisticsAdminOidUserId;
        }

        public void setLogisticsAdminOidUserId(String logisticsAdminOidUserId) {
            this.logisticsAdminOidUserId = logisticsAdminOidUserId;
        }

        public Object getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(Object receiverId) {
            this.receiverId = receiverId;
        }

        public Object getDeliverUser() {
            return deliverUser;
        }

        public void setDeliverUser(Object deliverUser) {
            this.deliverUser = deliverUser;
        }

        public Object getDeliverPhone() {
            return deliverPhone;
        }

        public void setDeliverPhone(Object deliverPhone) {
            this.deliverPhone = deliverPhone;
        }

        public Object getReceiptUser() {
            return receiptUser;
        }

        public void setReceiptUser(Object receiptUser) {
            this.receiptUser = receiptUser;
        }

        public Object getReceiptPhone() {
            return receiptPhone;
        }

        public void setReceiptPhone(Object receiptPhone) {
            this.receiptPhone = receiptPhone;
        }

        public Object getDriverEndDate() {
            return driverEndDate;
        }

        public void setDriverEndDate(Object driverEndDate) {
            this.driverEndDate = driverEndDate;
        }

        public Object getDriverBeginDate() {
            return driverBeginDate;
        }

        public void setDriverBeginDate(Object driverBeginDate) {
            this.driverBeginDate = driverBeginDate;
        }

        public Object getInOutType() {
            return inOutType;
        }

        public void setInOutType(Object inOutType) {
            this.inOutType = inOutType;
        }

        public Object getDeliverManagerName() {
            return deliverManagerName;
        }

        public void setDeliverManagerName(Object deliverManagerName) {
            this.deliverManagerName = deliverManagerName;
        }

        public Object getReceiptManagerName() {
            return receiptManagerName;
        }

        public void setReceiptManagerName(Object receiptManagerName) {
            this.receiptManagerName = receiptManagerName;
        }

        public Object getReceiptTime() {
            return receiptTime;
        }

        public void setReceiptTime(Object receiptTime) {
            this.receiptTime = receiptTime;
        }

        public Object getReceiptImgPath() {
            return receiptImgPath;
        }

        public void setReceiptImgPath(Object receiptImgPath) {
            this.receiptImgPath = receiptImgPath;
        }

        public Object getLogisticsFreight() {
            return logisticsFreight;
        }

        public void setLogisticsFreight(Object logisticsFreight) {
            this.logisticsFreight = logisticsFreight;
        }

        public Object getFreightReceivedAmount() {
            return freightReceivedAmount;
        }

        public void setFreightReceivedAmount(Object freightReceivedAmount) {
            this.freightReceivedAmount = freightReceivedAmount;
        }

        public Object getFreightPayStatus() {
            return freightPayStatus;
        }

        public void setFreightPayStatus(Object freightPayStatus) {
            this.freightPayStatus = freightPayStatus;
        }

        public Object getFreightPayDate() {
            return freightPayDate;
        }

        public void setFreightPayDate(Object freightPayDate) {
            this.freightPayDate = freightPayDate;
        }

        public Object getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Object orderStatus) {
            this.orderStatus = orderStatus;
        }

        public Object getOrderBatchId() {
            return orderBatchId;
        }

        public void setOrderBatchId(Object orderBatchId) {
            this.orderBatchId = orderBatchId;
        }

        public Object getProxiesPdfPath() {
            return proxiesPdfPath;
        }

        public void setProxiesPdfPath(Object proxiesPdfPath) {
            this.proxiesPdfPath = proxiesPdfPath;
        }

        public Object getApproveRemark() {
            return approveRemark;
        }

        public void setApproveRemark(Object approveRemark) {
            this.approveRemark = approveRemark;
        }

        public Object getApproveStatus() {
            return approveStatus;
        }

        public void setApproveStatus(Object approveStatus) {
            this.approveStatus = approveStatus;
        }

        public Object getReceiptComapnyShortName() {
            return receiptComapnyShortName;
        }

        public void setReceiptComapnyShortName(Object receiptComapnyShortName) {
            this.receiptComapnyShortName = receiptComapnyShortName;
        }

        public Object getPalletModelList() {
            return palletModelList;
        }

        public void setPalletModelList(Object palletModelList) {
            this.palletModelList = palletModelList;
        }

        public Object getDetailList() {
            return detailList;
        }

        public void setDetailList(Object detailList) {
            this.detailList = detailList;
        }

        public Object getBillDto() {
            return billDto;
        }

        public void setBillDto(Object billDto) {
            this.billDto = billDto;
        }

        public Object getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(Object goodsList) {
            this.goodsList = goodsList;
        }

        public Object getUrlPathList() {
            return urlPathList;
        }

        public void setUrlPathList(Object urlPathList) {
            this.urlPathList = urlPathList;
        }

        public Object getScanCodeType() {
            return scanCodeType;
        }

        public void setScanCodeType(Object scanCodeType) {
            this.scanCodeType = scanCodeType;
        }

        public Object getChipType() {
            return chipType;
        }

        public void setChipType(Object chipType) {
            this.chipType = chipType;
        }

        public Object getMangerImgPath() {
            return mangerImgPath;
        }

        public void setMangerImgPath(Object mangerImgPath) {
            this.mangerImgPath = mangerImgPath;
        }

        public Object getOrderId() {
            return orderId;
        }

        public void setOrderId(Object orderId) {
            this.orderId = orderId;
        }

        public List<?> getDynamicImageDtoList() {
            return dynamicImageDtoList;
        }

        public void setDynamicImageDtoList(List<?> dynamicImageDtoList) {
            this.dynamicImageDtoList = dynamicImageDtoList;
        }

        public List<?> getDynamicImageList() {
            return dynamicImageList;
        }

        public void setDynamicImageList(List<?> dynamicImageList) {
            this.dynamicImageList = dynamicImageList;
        }
    }

    public static class ReceiptCountInfoBean {
        /**
         * transMemo :
         * deliveryList :
         * inspectionReportImgPath :
         * checkStatus : 3
         * receiptPalletList : [{"primaryKey":null,"findKeywords":null,"findStatus":null,"pageIndex":0,"pageSize":0,"updUserId":null,"dateFrom":null,"dateTo":null,"sortField":null,"sort":null,"browseCnt":0,"detailId":null,"dispatchId":null,"modelId":"1648718128914686","quantity":0,"palletName":null,"palletModel":null,"specLength":null,"specWidth":null,"specHeight":null,"palletSpec":null,"oidUserId":null,"insDate":null,"insUserId":null,"insUserName":null,"updDate":null,"delFlg":null,"palletStatus":"1","fullPalletCount":null,"breakagePalletCount":null,"checkStatus":null,"breakagePalletImgPath":null,"companyId":null,"companyName":null,"palletInfo":null,"receiptCount":null,"deliverCount":null,"deliverUpCount":null,"deliverDownCount":null,"residueCount":null,"damageCount":null,"issuedAmount":null,"unissuedAmount":null,"signDescription":null,"companyType":null,"fullPalletQuantity":"6","sendQUANTITY":"0","officialReceiptsQuantity":"0","breakagePalletQuantity":"0","receiptCountInfo":null,"differenceValue":"0","inAndOutTime":null,"receiveLoad":null,"deliverLoad":null,"siteFee":null,"sortFee":null,"managementExpense":null,"goodsName":null,"goodsQuantity":"0","residueEstimateCount":null,"receiptCompanyId":null,"deliverCompany":null,"inOutType":null,"typeDriverType":null,"materialName":null,"thumbnail":null,"imgPath1":null,"goodsWeight":null,"weight":null,"dynamicImageDtoList":null,"dynamicImageList":null}]
         * shippingInformation :
         * signDescription :
         * breakagePalletImgPath :
         */

        private String transMemo;
        private String deliveryList;
        private String inspectionReportImgPath;
        private String checkStatus;
        private String shippingInformation;
        private String signDescription;
        private String breakagePalletImgPath;
        private List<ReceiptPalletListBean> receiptPalletList;

        public String getTransMemo() {
            return transMemo;
        }

        public void setTransMemo(String transMemo) {
            this.transMemo = transMemo;
        }

        public String getDeliveryList() {
            return deliveryList;
        }

        public void setDeliveryList(String deliveryList) {
            this.deliveryList = deliveryList;
        }

        public String getInspectionReportImgPath() {
            return inspectionReportImgPath;
        }

        public void setInspectionReportImgPath(String inspectionReportImgPath) {
            this.inspectionReportImgPath = inspectionReportImgPath;
        }

        public String getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(String checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getShippingInformation() {
            return shippingInformation;
        }

        public void setShippingInformation(String shippingInformation) {
            this.shippingInformation = shippingInformation;
        }

        public String getSignDescription() {
            return signDescription;
        }

        public void setSignDescription(String signDescription) {
            this.signDescription = signDescription;
        }

        public String getBreakagePalletImgPath() {
            return breakagePalletImgPath;
        }

        public void setBreakagePalletImgPath(String breakagePalletImgPath) {
            this.breakagePalletImgPath = breakagePalletImgPath;
        }

        public List<ReceiptPalletListBean> getReceiptPalletList() {
            return receiptPalletList;
        }

        public void setReceiptPalletList(List<ReceiptPalletListBean> receiptPalletList) {
            this.receiptPalletList = receiptPalletList;
        }

        public static class ReceiptPalletListBean {
            /**
             * primaryKey : null
             * findKeywords : null
             * findStatus : null
             * pageIndex : 0
             * pageSize : 0
             * updUserId : null
             * dateFrom : null
             * dateTo : null
             * sortField : null
             * sort : null
             * browseCnt : 0
             * detailId : null
             * dispatchId : null
             * modelId : 1648718128914686
             * quantity : 0
             * palletName : null
             * palletModel : null
             * specLength : null
             * specWidth : null
             * specHeight : null
             * palletSpec : null
             * oidUserId : null
             * insDate : null
             * insUserId : null
             * insUserName : null
             * updDate : null
             * delFlg : null
             * palletStatus : 1
             * fullPalletCount : null
             * breakagePalletCount : null
             * checkStatus : null
             * breakagePalletImgPath : null
             * companyId : null
             * companyName : null
             * palletInfo : null
             * receiptCount : null
             * deliverCount : null
             * deliverUpCount : null
             * deliverDownCount : null
             * residueCount : null
             * damageCount : null
             * issuedAmount : null
             * unissuedAmount : null
             * signDescription : null
             * companyType : null
             * fullPalletQuantity : 6
             * sendQUANTITY : 0
             * officialReceiptsQuantity : 0
             * breakagePalletQuantity : 0
             * receiptCountInfo : null
             * differenceValue : 0
             * inAndOutTime : null
             * receiveLoad : null
             * deliverLoad : null
             * siteFee : null
             * sortFee : null
             * managementExpense : null
             * goodsName : null
             * goodsQuantity : 0
             * residueEstimateCount : null
             * receiptCompanyId : null
             * deliverCompany : null
             * inOutType : null
             * typeDriverType : null
             * materialName : null
             * thumbnail : null
             * imgPath1 : null
             * goodsWeight : null
             * weight : null
             * dynamicImageDtoList : null
             * dynamicImageList : null
             */

            private Object primaryKey;
            private Object findKeywords;
            private Object findStatus;
            private int pageIndex;
            private int pageSize;
            private Object updUserId;
            private Object dateFrom;
            private Object dateTo;
            private Object sortField;
            private Object sort;
            private int browseCnt;
            private Object detailId;
            private Object dispatchId;
            private String modelId;
            private int quantity;
            private Object palletName;
            private Object palletModel;
            private Object specLength;
            private Object specWidth;
            private Object specHeight;
            private Object palletSpec;
            private Object oidUserId;
            private Object insDate;
            private Object insUserId;
            private Object insUserName;
            private Object updDate;
            private Object delFlg;
            private String palletStatus;
            private Object fullPalletCount;
            private Object breakagePalletCount;
            private Object checkStatus;
            private Object breakagePalletImgPath;
            private Object companyId;
            private Object companyName;
            private Object palletInfo;
            private Object receiptCount;
            private Object deliverCount;
            private Object deliverUpCount;
            private Object deliverDownCount;
            private Object residueCount;
            private Object damageCount;
            private Object issuedAmount;
            private Object unissuedAmount;
            private Object signDescription;
            private Object companyType;
            private String fullPalletQuantity;
            private String sendQUANTITY;
            private String officialReceiptsQuantity;
            private String breakagePalletQuantity;
            private Object receiptCountInfo;
            private String differenceValue;
            private Object inAndOutTime;
            private Object receiveLoad;
            private Object deliverLoad;
            private Object siteFee;
            private Object sortFee;
            private Object managementExpense;
            private Object goodsName;
            private String goodsQuantity;
            private Object residueEstimateCount;
            private Object receiptCompanyId;
            private Object deliverCompany;
            private Object inOutType;
            private Object typeDriverType;
            private Object materialName;
            private Object thumbnail;
            private Object imgPath1;
            private Object goodsWeight;
            private Object weight;
            private Object dynamicImageDtoList;
            private Object dynamicImageList;

            public Object getPrimaryKey() {
                return primaryKey;
            }

            public void setPrimaryKey(Object primaryKey) {
                this.primaryKey = primaryKey;
            }

            public Object getFindKeywords() {
                return findKeywords;
            }

            public void setFindKeywords(Object findKeywords) {
                this.findKeywords = findKeywords;
            }

            public Object getFindStatus() {
                return findStatus;
            }

            public void setFindStatus(Object findStatus) {
                this.findStatus = findStatus;
            }

            public int getPageIndex() {
                return pageIndex;
            }

            public void setPageIndex(int pageIndex) {
                this.pageIndex = pageIndex;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public Object getUpdUserId() {
                return updUserId;
            }

            public void setUpdUserId(Object updUserId) {
                this.updUserId = updUserId;
            }

            public Object getDateFrom() {
                return dateFrom;
            }

            public void setDateFrom(Object dateFrom) {
                this.dateFrom = dateFrom;
            }

            public Object getDateTo() {
                return dateTo;
            }

            public void setDateTo(Object dateTo) {
                this.dateTo = dateTo;
            }

            public Object getSortField() {
                return sortField;
            }

            public void setSortField(Object sortField) {
                this.sortField = sortField;
            }

            public Object getSort() {
                return sort;
            }

            public void setSort(Object sort) {
                this.sort = sort;
            }

            public int getBrowseCnt() {
                return browseCnt;
            }

            public void setBrowseCnt(int browseCnt) {
                this.browseCnt = browseCnt;
            }

            public Object getDetailId() {
                return detailId;
            }

            public void setDetailId(Object detailId) {
                this.detailId = detailId;
            }

            public Object getDispatchId() {
                return dispatchId;
            }

            public void setDispatchId(Object dispatchId) {
                this.dispatchId = dispatchId;
            }

            public String getModelId() {
                return modelId;
            }

            public void setModelId(String modelId) {
                this.modelId = modelId;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public Object getPalletName() {
                return palletName;
            }

            public void setPalletName(Object palletName) {
                this.palletName = palletName;
            }

            public Object getPalletModel() {
                return palletModel;
            }

            public void setPalletModel(Object palletModel) {
                this.palletModel = palletModel;
            }

            public Object getSpecLength() {
                return specLength;
            }

            public void setSpecLength(Object specLength) {
                this.specLength = specLength;
            }

            public Object getSpecWidth() {
                return specWidth;
            }

            public void setSpecWidth(Object specWidth) {
                this.specWidth = specWidth;
            }

            public Object getSpecHeight() {
                return specHeight;
            }

            public void setSpecHeight(Object specHeight) {
                this.specHeight = specHeight;
            }

            public Object getPalletSpec() {
                return palletSpec;
            }

            public void setPalletSpec(Object palletSpec) {
                this.palletSpec = palletSpec;
            }

            public Object getOidUserId() {
                return oidUserId;
            }

            public void setOidUserId(Object oidUserId) {
                this.oidUserId = oidUserId;
            }

            public Object getInsDate() {
                return insDate;
            }

            public void setInsDate(Object insDate) {
                this.insDate = insDate;
            }

            public Object getInsUserId() {
                return insUserId;
            }

            public void setInsUserId(Object insUserId) {
                this.insUserId = insUserId;
            }

            public Object getInsUserName() {
                return insUserName;
            }

            public void setInsUserName(Object insUserName) {
                this.insUserName = insUserName;
            }

            public Object getUpdDate() {
                return updDate;
            }

            public void setUpdDate(Object updDate) {
                this.updDate = updDate;
            }

            public Object getDelFlg() {
                return delFlg;
            }

            public void setDelFlg(Object delFlg) {
                this.delFlg = delFlg;
            }

            public String getPalletStatus() {
                return palletStatus;
            }

            public void setPalletStatus(String palletStatus) {
                this.palletStatus = palletStatus;
            }

            public Object getFullPalletCount() {
                return fullPalletCount;
            }

            public void setFullPalletCount(Object fullPalletCount) {
                this.fullPalletCount = fullPalletCount;
            }

            public Object getBreakagePalletCount() {
                return breakagePalletCount;
            }

            public void setBreakagePalletCount(Object breakagePalletCount) {
                this.breakagePalletCount = breakagePalletCount;
            }

            public Object getCheckStatus() {
                return checkStatus;
            }

            public void setCheckStatus(Object checkStatus) {
                this.checkStatus = checkStatus;
            }

            public Object getBreakagePalletImgPath() {
                return breakagePalletImgPath;
            }

            public void setBreakagePalletImgPath(Object breakagePalletImgPath) {
                this.breakagePalletImgPath = breakagePalletImgPath;
            }

            public Object getCompanyId() {
                return companyId;
            }

            public void setCompanyId(Object companyId) {
                this.companyId = companyId;
            }

            public Object getCompanyName() {
                return companyName;
            }

            public void setCompanyName(Object companyName) {
                this.companyName = companyName;
            }

            public Object getPalletInfo() {
                return palletInfo;
            }

            public void setPalletInfo(Object palletInfo) {
                this.palletInfo = palletInfo;
            }

            public Object getReceiptCount() {
                return receiptCount;
            }

            public void setReceiptCount(Object receiptCount) {
                this.receiptCount = receiptCount;
            }

            public Object getDeliverCount() {
                return deliverCount;
            }

            public void setDeliverCount(Object deliverCount) {
                this.deliverCount = deliverCount;
            }

            public Object getDeliverUpCount() {
                return deliverUpCount;
            }

            public void setDeliverUpCount(Object deliverUpCount) {
                this.deliverUpCount = deliverUpCount;
            }

            public Object getDeliverDownCount() {
                return deliverDownCount;
            }

            public void setDeliverDownCount(Object deliverDownCount) {
                this.deliverDownCount = deliverDownCount;
            }

            public Object getResidueCount() {
                return residueCount;
            }

            public void setResidueCount(Object residueCount) {
                this.residueCount = residueCount;
            }

            public Object getDamageCount() {
                return damageCount;
            }

            public void setDamageCount(Object damageCount) {
                this.damageCount = damageCount;
            }

            public Object getIssuedAmount() {
                return issuedAmount;
            }

            public void setIssuedAmount(Object issuedAmount) {
                this.issuedAmount = issuedAmount;
            }

            public Object getUnissuedAmount() {
                return unissuedAmount;
            }

            public void setUnissuedAmount(Object unissuedAmount) {
                this.unissuedAmount = unissuedAmount;
            }

            public Object getSignDescription() {
                return signDescription;
            }

            public void setSignDescription(Object signDescription) {
                this.signDescription = signDescription;
            }

            public Object getCompanyType() {
                return companyType;
            }

            public void setCompanyType(Object companyType) {
                this.companyType = companyType;
            }

            public String getFullPalletQuantity() {
                return fullPalletQuantity;
            }

            public void setFullPalletQuantity(String fullPalletQuantity) {
                this.fullPalletQuantity = fullPalletQuantity;
            }

            public String getSendQUANTITY() {
                return sendQUANTITY;
            }

            public void setSendQUANTITY(String sendQUANTITY) {
                this.sendQUANTITY = sendQUANTITY;
            }

            public String getOfficialReceiptsQuantity() {
                return officialReceiptsQuantity;
            }

            public void setOfficialReceiptsQuantity(String officialReceiptsQuantity) {
                this.officialReceiptsQuantity = officialReceiptsQuantity;
            }

            public String getBreakagePalletQuantity() {
                return breakagePalletQuantity;
            }

            public void setBreakagePalletQuantity(String breakagePalletQuantity) {
                this.breakagePalletQuantity = breakagePalletQuantity;
            }

            public Object getReceiptCountInfo() {
                return receiptCountInfo;
            }

            public void setReceiptCountInfo(Object receiptCountInfo) {
                this.receiptCountInfo = receiptCountInfo;
            }

            public String getDifferenceValue() {
                return differenceValue;
            }

            public void setDifferenceValue(String differenceValue) {
                this.differenceValue = differenceValue;
            }

            public Object getInAndOutTime() {
                return inAndOutTime;
            }

            public void setInAndOutTime(Object inAndOutTime) {
                this.inAndOutTime = inAndOutTime;
            }

            public Object getReceiveLoad() {
                return receiveLoad;
            }

            public void setReceiveLoad(Object receiveLoad) {
                this.receiveLoad = receiveLoad;
            }

            public Object getDeliverLoad() {
                return deliverLoad;
            }

            public void setDeliverLoad(Object deliverLoad) {
                this.deliverLoad = deliverLoad;
            }

            public Object getSiteFee() {
                return siteFee;
            }

            public void setSiteFee(Object siteFee) {
                this.siteFee = siteFee;
            }

            public Object getSortFee() {
                return sortFee;
            }

            public void setSortFee(Object sortFee) {
                this.sortFee = sortFee;
            }

            public Object getManagementExpense() {
                return managementExpense;
            }

            public void setManagementExpense(Object managementExpense) {
                this.managementExpense = managementExpense;
            }

            public Object getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(Object goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsQuantity() {
                return goodsQuantity;
            }

            public void setGoodsQuantity(String goodsQuantity) {
                this.goodsQuantity = goodsQuantity;
            }

            public Object getResidueEstimateCount() {
                return residueEstimateCount;
            }

            public void setResidueEstimateCount(Object residueEstimateCount) {
                this.residueEstimateCount = residueEstimateCount;
            }

            public Object getReceiptCompanyId() {
                return receiptCompanyId;
            }

            public void setReceiptCompanyId(Object receiptCompanyId) {
                this.receiptCompanyId = receiptCompanyId;
            }

            public Object getDeliverCompany() {
                return deliverCompany;
            }

            public void setDeliverCompany(Object deliverCompany) {
                this.deliverCompany = deliverCompany;
            }

            public Object getInOutType() {
                return inOutType;
            }

            public void setInOutType(Object inOutType) {
                this.inOutType = inOutType;
            }

            public Object getTypeDriverType() {
                return typeDriverType;
            }

            public void setTypeDriverType(Object typeDriverType) {
                this.typeDriverType = typeDriverType;
            }

            public Object getMaterialName() {
                return materialName;
            }

            public void setMaterialName(Object materialName) {
                this.materialName = materialName;
            }

            public Object getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(Object thumbnail) {
                this.thumbnail = thumbnail;
            }

            public Object getImgPath1() {
                return imgPath1;
            }

            public void setImgPath1(Object imgPath1) {
                this.imgPath1 = imgPath1;
            }

            public Object getGoodsWeight() {
                return goodsWeight;
            }

            public void setGoodsWeight(Object goodsWeight) {
                this.goodsWeight = goodsWeight;
            }

            public Object getWeight() {
                return weight;
            }

            public void setWeight(Object weight) {
                this.weight = weight;
            }

            public Object getDynamicImageDtoList() {
                return dynamicImageDtoList;
            }

            public void setDynamicImageDtoList(Object dynamicImageDtoList) {
                this.dynamicImageDtoList = dynamicImageDtoList;
            }

            public Object getDynamicImageList() {
                return dynamicImageList;
            }

            public void setDynamicImageList(Object dynamicImageList) {
                this.dynamicImageList = dynamicImageList;
            }
        }
    }
}
