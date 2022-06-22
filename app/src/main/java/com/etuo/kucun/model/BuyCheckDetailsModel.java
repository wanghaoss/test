package com.etuo.kucun.model;

import java.util.List;

/**
 * Created by yhn on 2020/2/14.
 *
 * 采购验收
 */

public class BuyCheckDetailsModel {


    /**
     * primaryKey : null
     * findKeywords : null
     * findStatus : null
     * pageIndex : 0
     * pageSize : 0
     * updUserId : 1568864483843781
     * dateFrom : null
     * dateTo : null
     * sortField : null
     * sort : null
     * browseCnt : 0
     * goodsCheckId : 144656
     * companyId : 1568864483844387
     * goodsCheckNo : 184846516548
     * goodsDispatchId : null
     * goodsDispatchNo : 754896516546
     * status : 2
     * createStoreFlg : 1
     * insDate : 2020/02/07 13:57:08
     * insUserId : 1569298692125053
     * updDate : 2020-02-12 09:35:45.0
     * mobile : 18454302857
     * userName : 邵洪祥
     * goodsCnt : 6
     * goodsWeight : 600
     * palletCnt : 2
     * detailList : [{"modelId":"1567759454274532","palletName":"吹塑九脚平板托盘","palletModel":"ET01","palletThumbnail":"http://192.168.1.185/upload/modelPhoto/1567759454274532/1567759454285081.png","palletImgPath1":"http://192.168.1.185/upload/modelPhoto/1567759454274532/1567759454290069.png","palletImgPath2":"","palletImgPath3":"","palletCnt":2,"goodsId":"1581237406925089","goods":{"goodsId":"1581237406925089","companyId":"1568864483844387","goodsName":"白酒","goodsModel":"55°1箱4瓶","goodsCnt":4,"goodsWeight":20,"goodsImgPath1":"http://192.168.1.185/upload/20200209/f26225bde6a250894a04db4c53ea03d0163602525.png","goodsImgUrl1":null,"goodsImgPath2":null,"goodsImgUrl2":null,"goodsImgPath3":null,"goodsImgUrl3":null,"delFlg":0,"insDate":"2020-02-09 16:36:46","insUserId":"1568864483843781","updDate":"2020-02-09 21:10:01","updUserId":"1568864483843781"},"goodsCnt":6,"goodsWeight":600,"specLength":"1200","specWidth":"1000","specHeight":"150","forkingMode":"双向进叉","billDetailList":[{"primaryKey":null,"findKeywords":null,"findStatus":null,"pageIndex":0,"pageSize":0,"updUserId":"1568864483843781","dateFrom":null,"dateTo":null,"sortField":null,"sort":null,"browseCnt":0,"goodsCheckDetailid":"45641184661513","goodsCheckId":"144656","palletNum":"14896511318","modelId":"1567759454274532","goodsId":"1581237406925089","goods":null,"goodsCnt":3,"goodsWeight":300,"status":"1","opUser":"管理员","opEquipment":"3","opTime":"2020-02-11 13:40:45","insDate":"2020-02-09 12:28:14","insUserId":"456489486","updDate":"2020-02-11 13:40:45"},{"primaryKey":null,"findKeywords":null,"findStatus":null,"pageIndex":0,"pageSize":0,"updUserId":"1568864483843781","dateFrom":null,"dateTo":null,"sortField":null,"sort":null,"browseCnt":0,"goodsCheckDetailid":"45641184661515","goodsCheckId":"144656","palletNum":"14896511319","modelId":"1567759454274532","goodsId":"1581237406925089","goods":null,"goodsCnt":3,"goodsWeight":300,"status":"1","opUser":"管理员","opEquipment":"3","opTime":"2020-02-11 13:40:45","insDate":"2020-02-09 12:28:14","insUserId":"456489486","updDate":"2020-02-11 13:40:45"}]}]
     */

    private Object primaryKey;
    private Object findKeywords;
    private Object findStatus;
    private int pageIndex;
    private int pageSize;
    private String updUserId;
    private Object dateFrom;
    private Object dateTo;
    private Object sortField;
    private Object sort;
    private int browseCnt;
    private String goodsCheckId;
    private String companyId;
    private String goodsCheckNo;
    private Object goodsDispatchId;
    private String goodsDispatchNo;
    private String status;
    private String createStoreFlg;
    private String insDate;
    private String insUserId;
    private String updDate;
    private String mobile;
    private String userName;
    private String goodsCnt;
    private int goodsWeight;
    private String palletCnt;
    private List<DetailListBean> detailList;

    private String buttonFlg;//0  不能提交  1 提交

    public String getButtonFlg() {
        return buttonFlg;
    }

    public void setButtonFlg(String buttonFlg) {
        this.buttonFlg = buttonFlg;
    }

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

    public String getUpdUserId() {
        return updUserId;
    }

    public void setUpdUserId(String updUserId) {
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

    public String getGoodsCheckId() {
        return goodsCheckId;
    }

    public void setGoodsCheckId(String goodsCheckId) {
        this.goodsCheckId = goodsCheckId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getGoodsCheckNo() {
        return goodsCheckNo;
    }

    public void setGoodsCheckNo(String goodsCheckNo) {
        this.goodsCheckNo = goodsCheckNo;
    }

    public Object getGoodsDispatchId() {
        return goodsDispatchId;
    }

    public void setGoodsDispatchId(Object goodsDispatchId) {
        this.goodsDispatchId = goodsDispatchId;
    }

    public String getGoodsDispatchNo() {
        return goodsDispatchNo;
    }

    public void setGoodsDispatchNo(String goodsDispatchNo) {
        this.goodsDispatchNo = goodsDispatchNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateStoreFlg() {
        return createStoreFlg;
    }

    public void setCreateStoreFlg(String createStoreFlg) {
        this.createStoreFlg = createStoreFlg;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodsCnt() {
        return goodsCnt;
    }

    public void setGoodsCnt(String goodsCnt) {
        this.goodsCnt = goodsCnt;
    }

    public int getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(int goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getPalletCnt() {
        return palletCnt;
    }

    public void setPalletCnt(String palletCnt) {
        this.palletCnt = palletCnt;
    }

    public List<DetailListBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListBean> detailList) {
        this.detailList = detailList;
    }

    public static class DetailListBean {
        /**
         * modelId : 1567759454274532
         * palletName : 吹塑九脚平板托盘
         * palletModel : ET01
         * palletThumbnail : http://192.168.1.185/upload/modelPhoto/1567759454274532/1567759454285081.png
         * palletImgPath1 : http://192.168.1.185/upload/modelPhoto/1567759454274532/1567759454290069.png
         * palletImgPath2 :
         * palletImgPath3 :
         * palletCnt : 2
         * goodsId : 1581237406925089
         * goods : {"goodsId":"1581237406925089","companyId":"1568864483844387","goodsName":"白酒","goodsModel":"55°1箱4瓶","goodsCnt":4,"goodsWeight":20,"goodsImgPath1":"http://192.168.1.185/upload/20200209/f26225bde6a250894a04db4c53ea03d0163602525.png","goodsImgUrl1":null,"goodsImgPath2":null,"goodsImgUrl2":null,"goodsImgPath3":null,"goodsImgUrl3":null,"delFlg":0,"insDate":"2020-02-09 16:36:46","insUserId":"1568864483843781","updDate":"2020-02-09 21:10:01","updUserId":"1568864483843781"}
         * goodsCnt : 6
         * goodsWeight : 600
         * specLength : 1200
         * specWidth : 1000
         * specHeight : 150
         * forkingMode : 双向进叉
         * billDetailList : [{"primaryKey":null,"findKeywords":null,"findStatus":null,"pageIndex":0,"pageSize":0,"updUserId":"1568864483843781","dateFrom":null,"dateTo":null,"sortField":null,"sort":null,"browseCnt":0,"goodsCheckDetailid":"45641184661513","goodsCheckId":"144656","palletNum":"14896511318","modelId":"1567759454274532","goodsId":"1581237406925089","goods":null,"goodsCnt":3,"goodsWeight":300,"status":"1","opUser":"管理员","opEquipment":"3","opTime":"2020-02-11 13:40:45","insDate":"2020-02-09 12:28:14","insUserId":"456489486","updDate":"2020-02-11 13:40:45"},{"primaryKey":null,"findKeywords":null,"findStatus":null,"pageIndex":0,"pageSize":0,"updUserId":"1568864483843781","dateFrom":null,"dateTo":null,"sortField":null,"sort":null,"browseCnt":0,"goodsCheckDetailid":"45641184661515","goodsCheckId":"144656","palletNum":"14896511319","modelId":"1567759454274532","goodsId":"1581237406925089","goods":null,"goodsCnt":3,"goodsWeight":300,"status":"1","opUser":"管理员","opEquipment":"3","opTime":"2020-02-11 13:40:45","insDate":"2020-02-09 12:28:14","insUserId":"456489486","updDate":"2020-02-11 13:40:45"}]
         */

        private String modelId;
        private String palletName;
        private String palletModel;
        private String palletThumbnail;
        private String palletImgPath1;
        private String palletImgPath2;
        private String palletImgPath3;
        private int palletCnt;
        private String goodsId;
        private GoodsBean goods;
        private int goodsCnt;
        private int goodsWeight;
        private String specLength;
        private String specWidth;
        private String specHeight;
        private String forkingMode;
        private List<BillDetailListBean> billDetailList;

        private String staticLoad;
        private String dynamicLoad;

        private int waitcnt = 0;
        private int endCnt = 0;
        private int unusualCnt = 0;

        private int endGoodsCnt = 0;//已选货物数量
        private float endGoodsWeight = 0;//已选货物重量

        public int getEndGoodsCnt() {
            return endGoodsCnt;
        }

        public void setEndGoodsCnt(int endGoodsCnt) {
            this.endGoodsCnt = endGoodsCnt;
        }

        public float getEndGoodsWeight() {
            return endGoodsWeight;
        }

        public void setEndGoodsWeight(float endGoodsWeight) {
            this.endGoodsWeight = endGoodsWeight;
        }

        public int getWaitcnt() {
            return waitcnt;
        }

        public void setWaitcnt(int waitcnt) {
            this.waitcnt = waitcnt;
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

        public int getEndCnt() {
            return endCnt;
        }

        public void setEndCnt(int endCnt) {
            this.endCnt = endCnt;
        }

        public int getUnusualCnt() {
            return unusualCnt;
        }

        public void setUnusualCnt(int unusualCnt) {
            this.unusualCnt = unusualCnt;
        }

        public String getModelId() {
            return modelId;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }

        public String getPalletName() {
            return palletName;
        }

        public void setPalletName(String palletName) {
            this.palletName = palletName;
        }

        public String getPalletModel() {
            return palletModel;
        }

        public void setPalletModel(String palletModel) {
            this.palletModel = palletModel;
        }

        public String getPalletThumbnail() {
            return palletThumbnail;
        }

        public void setPalletThumbnail(String palletThumbnail) {
            this.palletThumbnail = palletThumbnail;
        }

        public String getPalletImgPath1() {
            return palletImgPath1;
        }

        public void setPalletImgPath1(String palletImgPath1) {
            this.palletImgPath1 = palletImgPath1;
        }

        public String getPalletImgPath2() {
            return palletImgPath2;
        }

        public void setPalletImgPath2(String palletImgPath2) {
            this.palletImgPath2 = palletImgPath2;
        }

        public String getPalletImgPath3() {
            return palletImgPath3;
        }

        public void setPalletImgPath3(String palletImgPath3) {
            this.palletImgPath3 = palletImgPath3;
        }

        public int getPalletCnt() {
            return palletCnt;
        }

        public void setPalletCnt(int palletCnt) {
            this.palletCnt = palletCnt;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
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

        public String getForkingMode() {
            return forkingMode;
        }

        public void setForkingMode(String forkingMode) {
            this.forkingMode = forkingMode;
        }

        public List<BillDetailListBean> getBillDetailList() {
            return billDetailList;
        }

        public void setBillDetailList(List<BillDetailListBean> billDetailList) {
            this.billDetailList = billDetailList;
        }

        public static class GoodsBean {
            /**
             * goodsId : 1581237406925089
             * companyId : 1568864483844387
             * goodsName : 白酒
             * goodsModel : 55°1箱4瓶
             * goodsCnt : 4
             * goodsWeight : 20
             * goodsImgPath1 : http://192.168.1.185/upload/20200209/f26225bde6a250894a04db4c53ea03d0163602525.png
             * goodsImgUrl1 : null
             * goodsImgPath2 : null
             * goodsImgUrl2 : null
             * goodsImgPath3 : null
             * goodsImgUrl3 : null
             * delFlg : 0
             * insDate : 2020-02-09 16:36:46
             * insUserId : 1568864483843781
             * updDate : 2020-02-09 21:10:01
             * updUserId : 1568864483843781
             */

            private String goodsId;
            private String companyId;
            private String goodsName;
            private String goodsModel;
            private int goodsCnt;
            private int goodsWeight;
            private String goodsImgPath1;
            private Object goodsImgUrl1;
            private Object goodsImgPath2;
            private Object goodsImgUrl2;
            private Object goodsImgPath3;
            private Object goodsImgUrl3;
            private int delFlg;
            private String insDate;
            private String insUserId;
            private String updDate;
            private String updUserId;

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getCompanyId() {
                return companyId;
            }

            public void setCompanyId(String companyId) {
                this.companyId = companyId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsModel() {
                return goodsModel;
            }

            public void setGoodsModel(String goodsModel) {
                this.goodsModel = goodsModel;
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

            public String getGoodsImgPath1() {
                return goodsImgPath1;
            }

            public void setGoodsImgPath1(String goodsImgPath1) {
                this.goodsImgPath1 = goodsImgPath1;
            }

            public Object getGoodsImgUrl1() {
                return goodsImgUrl1;
            }

            public void setGoodsImgUrl1(Object goodsImgUrl1) {
                this.goodsImgUrl1 = goodsImgUrl1;
            }

            public Object getGoodsImgPath2() {
                return goodsImgPath2;
            }

            public void setGoodsImgPath2(Object goodsImgPath2) {
                this.goodsImgPath2 = goodsImgPath2;
            }

            public Object getGoodsImgUrl2() {
                return goodsImgUrl2;
            }

            public void setGoodsImgUrl2(Object goodsImgUrl2) {
                this.goodsImgUrl2 = goodsImgUrl2;
            }

            public Object getGoodsImgPath3() {
                return goodsImgPath3;
            }

            public void setGoodsImgPath3(Object goodsImgPath3) {
                this.goodsImgPath3 = goodsImgPath3;
            }

            public Object getGoodsImgUrl3() {
                return goodsImgUrl3;
            }

            public void setGoodsImgUrl3(Object goodsImgUrl3) {
                this.goodsImgUrl3 = goodsImgUrl3;
            }

            public int getDelFlg() {
                return delFlg;
            }

            public void setDelFlg(int delFlg) {
                this.delFlg = delFlg;
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
        }

        public static class BillDetailListBean {
            /**
             * primaryKey : null
             * findKeywords : null
             * findStatus : null
             * pageIndex : 0
             * pageSize : 0
             * updUserId : 1568864483843781
             * dateFrom : null
             * dateTo : null
             * sortField : null
             * sort : null
             * browseCnt : 0
             * goodsCheckDetailid : 45641184661513
             * goodsCheckId : 144656
             * palletNum : 14896511318
             * modelId : 1567759454274532
             * goodsId : 1581237406925089
             * goods : null
             * goodsCnt : 3
             * goodsWeight : 300
             * status : 1
             * opUser : 管理员
             * opEquipment : 3
             * opTime : 2020-02-11 13:40:45
             * insDate : 2020-02-09 12:28:14
             * insUserId : 456489486
             * updDate : 2020-02-11 13:40:45
             */

            private Object primaryKey;
            private Object findKeywords;
            private Object findStatus;
            private int pageIndex;
            private int pageSize;
            private String updUserId;
            private Object dateFrom;
            private Object dateTo;
            private Object sortField;
            private Object sort;
            private int browseCnt;
            private String goodsCheckDetailid;
            private String goodsCheckId;
            private String palletNum;
            private String modelId;
            private String goodsId;
            private Object goods;
            private int goodsCnt;
            private int goodsWeight;
            private String status;
            private String opUser;
            private String opEquipment;
            private String opTime;
            private String insDate;
            private String insUserId;
            private String updDate;

            private String goodsModel;

            public String getGoodsModel() {
                return goodsModel;
            }

            public void setGoodsModel(String goodsModel) {
                this.goodsModel = goodsModel;
            }

            private boolean isLastCheck = false;

            public boolean isLastCheck() {
                return isLastCheck;
            }

            public void setLastCheck(boolean lastCheck) {
                isLastCheck = lastCheck;
            }

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

            public String getUpdUserId() {
                return updUserId;
            }

            public void setUpdUserId(String updUserId) {
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

            public String getGoodsCheckDetailid() {
                return goodsCheckDetailid;
            }

            public void setGoodsCheckDetailid(String goodsCheckDetailid) {
                this.goodsCheckDetailid = goodsCheckDetailid;
            }

            public String getGoodsCheckId() {
                return goodsCheckId;
            }

            public void setGoodsCheckId(String goodsCheckId) {
                this.goodsCheckId = goodsCheckId;
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

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public Object getGoods() {
                return goods;
            }

            public void setGoods(Object goods) {
                this.goods = goods;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getOpUser() {
                return opUser;
            }

            public void setOpUser(String opUser) {
                this.opUser = opUser;
            }

            public String getOpEquipment() {
                return opEquipment;
            }

            public void setOpEquipment(String opEquipment) {
                this.opEquipment = opEquipment;
            }

            public String getOpTime() {
                return opTime;
            }

            public void setOpTime(String opTime) {
                this.opTime = opTime;
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
        }
    }
}
