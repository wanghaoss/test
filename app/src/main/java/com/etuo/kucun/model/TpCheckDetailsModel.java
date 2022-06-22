package com.etuo.kucun.model;

import java.util.List;

/**
 * Created by yhn on 2020/2/13.
 */

public class TpCheckDetailsModel {


    /**
     * palletCheckId : 1581489987162033
     * palletDispatchId : 1568864483844663
     * palletCheckNo : 2020021214462745
     * orderId : 1570956037935461
     * orderNo : Z191013164037LMB
     * status : 0
     * checkUserName : null
     * checkUserMobile : null
     * insDate : 2020-02-12 14:02:11
     * insUserId : 1570873142684671
     * updDate : null
     * updUserId : null
     * createUserName : 管理员
     * createUserMobile : 18997939740
     * haveCheckCnt : 0
     * noCheckCnt : 0
     * repairsCnt : 0
     * palletModelList : [{"modelId":"1567759454274532","orderType":null,"quantity":27,"palletModel":"ET01","palletName":"吹塑九脚平板托盘","palletSpec":"1200*1000*150","staticLoad":"3~6","dynamicLoad":"1.2~1.5","structureName":"九脚","specLength":"1200","specWidth":"1000","specHeight":"150","haveCheckCnt":1,"noCheckCnt":26,"repairsCnt":0,"thumbnail":"http://192.168.1.185/upload/modelPhoto/1567759454274532/1567759454285081.png","checkBillPalletList":[{"palletNum":"211061101814201909100078","modelId":"1567759454274532","goodsId":null,"goodsCnt":0,"goodsWeight":0,"storageAreaId":null,"storageAreaName":null,"opUser":null,"opUserName":null,"opTime":null,"opEquipment":null,"insDate":null,"insUserId":null,"updDate":null,"updUserId":null,"palletCheckDetailId":"1581489987172031","palletCheckId":"1581489987162033","status":"2"},{"palletNum":"211061101814201909100104","modelId":"1567759454274532","goodsId":null,"goodsCnt":0,"goodsWeight":0,"storageAreaId":null,"storageAreaName":null,"opUser":null,"opUserName":null,"opTime":null,"opEquipment":null,"insDate":null,"insUserId":null,"updDate":null,"updUserId":null,"palletCheckDetailId":"1581489987224755","palletCheckId":"1581489987162033","status":"0"}]}]
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

    public Object getCheckUserMobile() {
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

    public Object getUpdDate() {
        return updDate;
    }

    public void setUpdDate(String updDate) {
        this.updDate = updDate;
    }

    public Object getUpdUserId() {
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
         * palletSpec : 1200*1000*150
         * staticLoad : 3~6
         * dynamicLoad : 1.2~1.5
         * structureName : 九脚
         * specLength : 1200
         * specWidth : 1000
         * specHeight : 150
         * haveCheckCnt : 1
         * noCheckCnt : 26
         * repairsCnt : 0
         * thumbnail : http://192.168.1.185/upload/modelPhoto/1567759454274532/1567759454285081.png
         * checkBillPalletList : [{"palletNum":"211061101814201909100078","modelId":"1567759454274532","goodsId":null,"goodsCnt":0,"goodsWeight":0,"storageAreaId":null,"storageAreaName":null,"opUser":null,"opUserName":null,"opTime":null,"opEquipment":null,"insDate":null,"insUserId":null,"updDate":null,"updUserId":null,"palletCheckDetailId":"1581489987172031","palletCheckId":"1581489987162033","status":"2"},{"palletNum":"211061101814201909100104","modelId":"1567759454274532","goodsId":null,"goodsCnt":0,"goodsWeight":0,"storageAreaId":null,"storageAreaName":null,"opUser":null,"opUserName":null,"opTime":null,"opEquipment":null,"insDate":null,"insUserId":null,"updDate":null,"updUserId":null,"palletCheckDetailId":"1581489987224755","palletCheckId":"1581489987162033","status":"0"}]
         */

        private String modelId;
        private Object orderType;
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
        private String thumbnail;
        private List<CheckBillPalletListBean> checkBillPalletList;

        public String getModelId() {
            return modelId;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }

        public Object getOrderType() {
            return orderType;
        }

        public void setOrderType(Object orderType) {
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

        public String getPalletSpec() {
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

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public List<CheckBillPalletListBean> getCheckBillPalletList() {
            return checkBillPalletList;
        }

        public void setCheckBillPalletList(List<CheckBillPalletListBean> checkBillPalletList) {
            this.checkBillPalletList = checkBillPalletList;
        }

        public static class CheckBillPalletListBean {
            /**
             * palletNum : 211061101814201909100078
             * modelId : 1567759454274532
             * goodsId : null
             * goodsCnt : 0
             * goodsWeight : 0
             * storageAreaId : null
             * storageAreaName : null
             * opUser : null
             * opUserName : null
             * opTime : null
             * opEquipment : null
             * insDate : null
             * insUserId : null
             * updDate : null
             * updUserId : null
             * palletCheckDetailId : 1581489987172031
             * palletCheckId : 1581489987162033
             * status : 2
             */

            private String palletNum;
            private String modelId;
            private Object goodsId;
            private int goodsCnt;
            private int goodsWeight;
            private Object storageAreaId;
            private String storageAreaName;
            private Object opUser;
            private Object opUserName;
            private Object opTime;
            private Object opEquipment;
            private Object insDate;
            private Object insUserId;
            private Object updDate;
            private Object updUserId;
            private String palletCheckDetailId;
            private String palletCheckId;
            private String status;
            private boolean isLastCheck = false;

            public boolean isLastCheck() {
                return isLastCheck;
            }

            public void setLastCheck(boolean lastCheck) {
                isLastCheck = lastCheck;
            }

            public String getPalletNum() {
                return palletNum;
            }

            public void setPalletNum(String palletNum) {
                this.palletNum = palletNum;
            }

            public String getModelId() {
                return modelId;
            }

            public void setModelId(String modelId) {
                this.modelId = modelId;
            }

            public Object getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(Object goodsId) {
                this.goodsId = goodsId;
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

            public Object getStorageAreaId() {
                return storageAreaId;
            }

            public void setStorageAreaId(Object storageAreaId) {
                this.storageAreaId = storageAreaId;
            }

            public String getStorageAreaName() {
                return storageAreaName;
            }

            public void setStorageAreaName(String storageAreaName) {
                this.storageAreaName = storageAreaName;
            }

            public Object getOpUser() {
                return opUser;
            }

            public void setOpUser(Object opUser) {
                this.opUser = opUser;
            }

            public Object getOpUserName() {
                return opUserName;
            }

            public void setOpUserName(Object opUserName) {
                this.opUserName = opUserName;
            }

            public Object getOpTime() {
                return opTime;
            }

            public void setOpTime(Object opTime) {
                this.opTime = opTime;
            }

            public Object getOpEquipment() {
                return opEquipment;
            }

            public void setOpEquipment(Object opEquipment) {
                this.opEquipment = opEquipment;
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

            public Object getUpdDate() {
                return updDate;
            }

            public void setUpdDate(Object updDate) {
                this.updDate = updDate;
            }

            public Object getUpdUserId() {
                return updUserId;
            }

            public void setUpdUserId(Object updUserId) {
                this.updUserId = updUserId;
            }

            public String getPalletCheckDetailId() {
                return palletCheckDetailId;
            }

            public void setPalletCheckDetailId(String palletCheckDetailId) {
                this.palletCheckDetailId = palletCheckDetailId;
            }

            public String getPalletCheckId() {
                return palletCheckId;
            }

            public void setPalletCheckId(String palletCheckId) {
                this.palletCheckId = palletCheckId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
