package com.etuo.kucun.model;

import java.util.List;

/**
 * Created by yhn on 2020/2/13.
 * 托盘验收列表实体
 *
 */

public class TpCheckListModel {

    /**
     *	 验收单列表
     palletCheckId; //托盘验收单Id
     palletDispatchId; // 托盘发货单id
     palletCheckNo; //验收单号
     orderId; //订单id
     orderNo; //订单号
     status; //验收单状态 0待验收 1验收中 2已验收
     insDate; //创建日期
     insUserId; //创建人id
     updDate; //最后更新日期
     updUserId; //最后更新人id
     createUserName; //创建人姓名
     createUserMobile; //创建人电话号
     haveCheckCnt; //已验收托盘个数
     noCheckCnt; //未验收托盘个数
     repairsCnt; //报修托盘个数
     checkBillPalletInfoVOList; //托盘型号情况列表

     checkBillPalletInfoVOList（型号情况列表）参数：
     modelId; //型号
     orderType; //托盘类型
     quantity; //托盘数量
     palletModel; //托盘型号
     palletName; //托盘名称
     palletSpec; //托盘规格
     structureName; //托盘结构名称
     dynamicLoad; //动载重
     staticLoad; //静载重
     specLength; //托盘长
     doorSpecWidth; //托盘宽
     specHeight; //托盘高
     haveCheckCnt; //已验收托盘个数
     noCheckCnt; //未验收托盘个数
     repairsCnt; //报修托盘个数
     checkBillPalletList; //验收单托盘列表

     */

    private String palletCheckId;
    private String palletDispatchId;
    private String palletCheckNo;
    private String orderId;
    private String orderNo;
    private String status;
    private String checkUserName;
    private String checkUserMobile;
    private String insDate;
    private String insUserId;
    private String updDate;
    private String updUserId;
    private String createUserName;
    private String createUserMobile;
    private int haveCheckCnt;
    private int noCheckCnt;
    private int repairsCnt;
    private List<PalletModelListBean> palletModelList;

    public String getPalletCheckId() {
        return palletCheckId;
    }

    public void setPalletCheckId(String palletCheckId) {
        this.palletCheckId = palletCheckId;
    }

    public String getPalletDispatchId() {
        return palletDispatchId;
    }

    public void setPalletDispatchId(String palletDispatchId) {
        this.palletDispatchId = palletDispatchId;
    }

    public String getPalletCheckNo() {
        return palletCheckNo;
    }

    public void setPalletCheckNo(String palletCheckNo) {
        this.palletCheckNo = palletCheckNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheckUserMobile() {
        return checkUserMobile;
    }

    public void setCheckUserMobile(String checkUserMobile) {
        this.checkUserMobile = checkUserMobile;
    }

    public String getInsDate() {
        return insDate;
    }

    public void setInsDate(String insDate) {
        this.insDate = insDate;
    }

    public String getInsUserId() {
        return insUserId;
    }

    public void setInsUserId(String insUserId) {
        this.insUserId = insUserId;
    }

    public String getUpdDate() {
        return updDate;
    }

    public void setUpdDate(String updDate) {
        this.updDate = updDate;
    }

    public String getUpdUserId() {
        return updUserId;
    }

    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateUserMobile() {
        return createUserMobile;
    }

    public void setCreateUserMobile(String createUserMobile) {
        this.createUserMobile = createUserMobile;
    }

    public int getHaveCheckCnt() {
        return haveCheckCnt;
    }

    public void setHaveCheckCnt(int haveCheckCnt) {
        this.haveCheckCnt = haveCheckCnt;
    }

    public int getNoCheckCnt() {
        return noCheckCnt;
    }

    public void setNoCheckCnt(int noCheckCnt) {
        this.noCheckCnt = noCheckCnt;
    }

    public int getRepairsCnt() {
        return repairsCnt;
    }

    public void setRepairsCnt(int repairsCnt) {
        this.repairsCnt = repairsCnt;
    }

    public List<PalletModelListBean> getPalletModelList() {
        return palletModelList;
    }

    public void setPalletModelList(List<PalletModelListBean> palletModelList) {
        this.palletModelList = palletModelList;
    }

    public static class PalletModelListBean {
        /**
         * modelId : 1567759454274532
         * orderType : null
         * quantity : 27
         * palletModel : ET01
         * palletName : 吹塑九脚平板托盘
         * palletSpec : null
         * staticLoad : 3~6
         * dynamicLoad : 1.2~1.5
         * structureName : 九脚
         * specLength : 1200
         * specWidth : 1000
         * specHeight : 150
         * haveCheckCnt : 1
         * noCheckCnt : 26
         * repairsCnt : 0
         * checkBillPalletList : null
         */

        private String modelId;
        private String orderType;
        private int quantity;
        private String palletModel;
        private String palletName;
        private String palletSpec;
        private String staticLoad;
        private String dynamicLoad;
        private String structureName;
        private String specLength;
        private String specWidth;
        private String specHeight;
        private int haveCheckCnt;
        private int noCheckCnt;
        private int repairsCnt;
        private Object checkBillPalletList;

        private String thumbnail;//图片信息

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getModelId() {
            return modelId;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }

        public Object getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
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

        public Object getPalletSpec() {
            return palletSpec;
        }

        public void setPalletSpec(String palletSpec) {
            this.palletSpec = palletSpec;
        }

        public String getStaticLoad() {
            return staticLoad;
        }

        public void setStaticLoad(String staticLoad) {
            this.staticLoad = staticLoad;
        }

        public String getDynamicLoad() {
            return dynamicLoad;
        }

        public void setDynamicLoad(String dynamicLoad) {
            this.dynamicLoad = dynamicLoad;
        }

        public String getStructureName() {
            return structureName;
        }

        public void setStructureName(String structureName) {
            this.structureName = structureName;
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

        public int getHaveCheckCnt() {
            return haveCheckCnt;
        }

        public void setHaveCheckCnt(int haveCheckCnt) {
            this.haveCheckCnt = haveCheckCnt;
        }

        public int getNoCheckCnt() {
            return noCheckCnt;
        }

        public void setNoCheckCnt(int noCheckCnt) {
            this.noCheckCnt = noCheckCnt;
        }

        public int getRepairsCnt() {
            return repairsCnt;
        }

        public void setRepairsCnt(int repairsCnt) {
            this.repairsCnt = repairsCnt;
        }

        public Object getCheckBillPalletList() {
            return checkBillPalletList;
        }

        public void setCheckBillPalletList(Object checkBillPalletList) {
            this.checkBillPalletList = checkBillPalletList;
        }
    }
}
