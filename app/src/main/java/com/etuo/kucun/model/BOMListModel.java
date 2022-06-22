package com.etuo.kucun.model;

import java.util.List;

/**
 * Created by yhn on 2020/2/10.
 */

public class BOMListModel {


    /**
     * prepareId : 1
     * companyId : 1568864483844387
     * prepareNo : 2
     * status : 0
     * insDate : 2020-02-08 10:16:17
     * insUserId : 1568864483843781
     * updDate : 2020-02-08 10:16:22
     * updUserId : 1568864483843781
     * userName : 管理员
     * mobile : 13834095894
     * gatherList : [{"modelId":"1567759454274532","palletName":"吹塑九脚平板托盘","palletModel":"ET01","palletImgPath1":"http://localhost/upload/modelPhoto/1567759454274532/1567759454290069.png","palletImgPath2":"","palletImgPath3":"","palletCnt":3,"goodsId":"","goods":{"goodsId":"1","companyId":"1568864483844387","goodsName":"炭黑","goodsModel":"TA01","goodsCnt":1,"goodsWeight":20,"goodsImgUrl1":"http://localhost/upload/modelPhoto/1567759454274532/1567759454290069.png","goodsImgUrl2":null,"goodsImgUrl3":null},"goodsCnt":0,"goodsWeight":0,"storageAreaId":"1581043835529048","storageAreaName":"A01"}]
     */

    private String prepareId;
    private String companyId;
    private String prepareNo;
    private String status;
    private String insDate;
    private String insUserId;
    private String updDate;
    private String updUserId;
    private String userName;
    private String mobile;
    private List<GatherListBean> gatherList;

    public String getPrepareId() {
        return prepareId;
    }

    public void setPrepareId(String prepareId) {
        this.prepareId = prepareId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPrepareNo() {
        return prepareNo;
    }

    public void setPrepareNo(String prepareNo) {
        this.prepareNo = prepareNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<GatherListBean> getGatherList() {
        return gatherList;
    }

    public void setGatherList(List<GatherListBean> gatherList) {
        this.gatherList = gatherList;
    }

    public static class GatherListBean {
        /**
         * modelId : 1567759454274532
         * palletName : 吹塑九脚平板托盘
         * palletModel : ET01
         * palletImgPath1 : http://localhost/upload/modelPhoto/1567759454274532/1567759454290069.png
         * palletImgPath2 :
         * palletImgPath3 :
         * palletCnt : 3
         * goodsId :
         * goods : {"goodsId":"1","companyId":"1568864483844387","goodsName":"炭黑","goodsModel":"TA01","goodsCnt":1,"goodsWeight":20,"goodsImgUrl1":"http://localhost/upload/modelPhoto/1567759454274532/1567759454290069.png","goodsImgUrl2":null,"goodsImgUrl3":null}
         * goodsCnt : 0
         * goodsWeight : 0
         * storageAreaId : 1581043835529048
         * storageAreaName : A01
         */

        private String modelId;
        private String palletName;
        private String palletModel;
        private String palletImgPath1;
        private String palletImgPath2;
        private String palletImgPath3;
        private int palletCnt;
        private String goodsId;
        private GoodsBean goods;
        private int goodsCnt;
        private int goodsWeight;
        private String storageAreaId;
        private String storageAreaName;
//        "staticLoad": "0.00", //静载重量
//                "dynamicLoad": "0.00", //动载重量

        private String staticLoad;//静载
        private String dynamicLoad;//动载

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

        public String getStorageAreaId() {
            return storageAreaId;
        }

        public void setStorageAreaId(String storageAreaId) {
            this.storageAreaId = storageAreaId;
        }

        public String getStorageAreaName() {
            return storageAreaName;
        }

        public void setStorageAreaName(String storageAreaName) {
            this.storageAreaName = storageAreaName;
        }

        public static class GoodsBean {
            /**
             * goodsId : 1
             * companyId : 1568864483844387
             * goodsName : 炭黑
             * goodsModel : TA01
             * goodsCnt : 1
             * goodsWeight : 20
             * goodsImgUrl1 : http://localhost/upload/modelPhoto/1567759454274532/1567759454290069.png
             * goodsImgUrl2 : null
             * goodsImgUrl3 : null
             */

            private String goodsId;
            private String companyId;
            private String goodsName;
            private String goodsModel;
            private int goodsCnt;
            private int goodsWeight;
            private String goodsImgUrl1;
            private Object goodsImgUrl2;
            private Object goodsImgUrl3;

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

            public String getGoodsImgUrl1() {
                return goodsImgUrl1;
            }

            public void setGoodsImgUrl1(String goodsImgUrl1) {
                this.goodsImgUrl1 = goodsImgUrl1;
            }

            public Object getGoodsImgUrl2() {
                return goodsImgUrl2;
            }

            public void setGoodsImgUrl2(Object goodsImgUrl2) {
                this.goodsImgUrl2 = goodsImgUrl2;
            }

            public Object getGoodsImgUrl3() {
                return goodsImgUrl3;
            }

            public void setGoodsImgUrl3(Object goodsImgUrl3) {
                this.goodsImgUrl3 = goodsImgUrl3;
            }
        }
    }
}
