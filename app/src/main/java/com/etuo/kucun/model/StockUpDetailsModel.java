package com.etuo.kucun.model;

import com.etuo.kucun.utils.ScreenUtils;
import com.etuo.kucun.utils.StringUtil;
import com.etuo.kucun.utils.StringUtils;

import java.util.Comparator;
import java.util.List;

/**
 * Created by yhn on 2020/2/11.
 * 备货详情
 */

public class StockUpDetailsModel {
    /**
     * {  //数据对象
     * "prepareId": "1",//货物备货单ID
     * "companyId": "1568864483844387",//企业ID
     * "prepareNo": "2",//货物备货单号
     * "status": "0",//货物备货单状态，0待备货 1备货中 2已备货
     * "insDate": "2020-02-08 10:16:17",//创建时间
     * "insUserId": "1568864483843781",//创建人ID
     * "updDate": "2020-02-08 10:16:22",//最后一次更新时间
     * "updUserId": "1568864483843781",//最后更新人ID
     * "userName": "管理员",//创建人姓名
     * "mobile": "13834095894",//创建人联系电话
     * "gatherList": [{  //汇总列表
     * "modelId": "1567759454274532", //托盘型号ID
     * "palletName": "吹塑九脚平板托盘", //托盘名称
     * "palletModel": "ET01", //托盘型号
     * "palletImgPath1":"http://localhost/upload/modelPhoto/1567759454274532
     * /1567759454290069.png", //托盘图1
     * "palletImgPath2": "", //托盘图2
     * "palletImgPath3": "",//托盘图3
     * "palletCnt": 3, //托盘数量
     * "goodsId": "",//货物ID
     * "goods": {  //货物对象
     * "goodsId": "1",//货物ID
     * "companyId": "1568864483844387",//企业ID
     * "goodsName": "炭黑",//货物名称
     * "goodsModel": "TA01",//货物规格
     * "goodsCnt": 1, //单个托盘货物数量
     * "goodsWeight": 20, //单个托盘货物重量
     * "goodsImgUrl1": "http://localhost/upload/modelPhoto/1567759454274532
     * /1567759454290069.png",//货物图1
     * "goodsImgUrl2": null, //货物图2
     * "goodsImgUrl3": null//货物图3
     * },
     * "goodsCnt": 0,// 货物数量
     * "goodsWeight": 0,// 货物重量
     * "storageAreaId": "1581043835529048",//库区ID
     * "storageAreaName": "A01"//库区名称
     * "detailList": [{ //托盘列表
     * "palletNum": "211061101814201909100002",//托盘编号
     * "prepareDetailId": "2",//货物备货单详情ID
     * "prepareId": "1",//货物备货单ID
     * "status": "0",//货物备货单状态：0待备货 1已备货
     * }]
     * }]
     * }
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
    private int palletCnt;
    private int goodsCnt;
    private int goodsWeight;
    private Object detailList;
    private String buttonFlg;//0  不能提交  1 提交
    private List<GatherListBean> gatherList;

    public String getButtonFlg() {
        return buttonFlg;
    }

    public void setButtonFlg(String buttonFlg) {
        this.buttonFlg = buttonFlg;
    }

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

    public int getPalletCnt() {
        return palletCnt;
    }

    public void setPalletCnt(int palletCnt) {
        this.palletCnt = palletCnt;
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

    public Object getDetailList() {
        return detailList;
    }

    public void setDetailList(Object detailList) {
        this.detailList = detailList;
    }

    public List<GatherListBean> getGatherList() {
        return gatherList;
    }

    public void setGatherList(List<GatherListBean> gatherList) {
        this.gatherList = gatherList;
    }

    public static class GatherListBean  {
        /**
         * modelId : 1567759454274532
         * palletName : 吹塑九脚平板托盘
         * palletModel : ET01
         * staticLoad : 3~6
         * dynamicLoad : 1.2~1.5
         * palletImgPath1 : http://192.168.1.185/upload/modelPhoto/1567759454274532/1567759454290069.png
         * palletImgPath2 :
         * palletImgPath3 :
         * palletCnt : 2
         * goodsId : 1581237406925089
         * goods : {"goodsId":"1581237406925089","companyId":"1568864483844387","goodsName":"白酒","goodsModel":"55°1箱4瓶","goodsCnt":4,"goodsWeight":20,"goodsImgPath1":"http://192.168.1.185/upl25.png","goodsImgUrl1":null,"goodsImgPath2":null,"goodsImgUrl2":null,"goodsImgPath3":null,"goodsImgUrl3":null,"delFlg":0,"insDate":"2020-02-09 16:36:46","insUserId":"1568864483843781","updDate":"2020-02-09 21:10:01","updUserId":"1568864483843781"}
         * goodsCnt : 8
         * goodsWeight : 40
         * storageAreaId : 1581043835529048
         * storageAreaName : A01
         * detailList : [{"palletNum":"211061101814201909100003","modelId":"1567759454274532","goodsId":"1581237406925089","goodsCnt":4,"goodsWeight":20,"storageAreaId":"1581044035258810","storageAreaName":"A0102","opUser":null,"opTime":null,"opEquipment":null,"insDate":null,"insUserId":null,"updDate":null,"updUserId":null,"prepareDetailId":"3","prepareId":"1","status":"0","palletStorageInNo":null},{"palletNum":"211061101814201909100004","modelId":"1567759454274532","goodsId":"1581237406925089","goodsCnt":4,"goodsWeight":20,"storageAreaId":"1581044035258810","storageAreaName":"A0102","opUser":null,"opTime":null,"opEquipment":null,"insDate":null,"insUserId":null,"updDate":null,"updUserId":null,"prepareDetailId":"4","prepareId":"1","status":"0","palletStorageInNo":null}]
         * waitCnt : null
         * endCnt : null
         * unusualCnt : null
         */

        private String modelId;
        private String palletName;
        private String palletModel;
        private String staticLoad;
        private String dynamicLoad;
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
        private int waitCnt;
        private int endCnt;
        private int unusualCnt;

        private String palletSpec;
        private List<DetailListBean> detailList;

        private int haveStockUpNum = 0;
        private int unusualNum = 0;

        private int endGoodsCnt = 0;//已选货物数量
        private float endGoodsWeight = 0;//已选货物重量

        public String getPalletSpec() {
            return palletSpec;
        }

        public void setPalletSpec(String palletSpec) {
            this.palletSpec = palletSpec;
        }

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

        public int getHaveStockUpNum() {
            return haveStockUpNum;
        }

        public void setHaveStockUpNum(int haveStockUpNum) {
            this.haveStockUpNum = haveStockUpNum;
        }

        public int getUnusualNum() {
            return unusualNum;
        }

        public void setUnusualNum(int unusualNum) {
            this.unusualNum = unusualNum;
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

        public int getWaitCnt() {
            return waitCnt;
        }

        public void setWaitCnt(int waitCnt) {
            this.waitCnt = waitCnt;
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

        public List<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DetailListBean> detailList) {
            this.detailList = detailList;
        }




        public static class GoodsBean {
            /**
             * goodsId : 1581237406925089
             * companyId : 1568864483844387
             * goodsName : 白酒
             * goodsModel : 55°1箱4瓶
             * goodsCnt : 4
             * goodsWeight : 20
             * goodsImgPath1 : http://192.168.1.185/upl25.png
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

            private int haveStockUpNum = 0;
            private int haveStockGoodsWeight = 0;

            public int getHaveStockUpNum() {
                return haveStockUpNum;
            }

            public void setHaveStockUpNum(int haveStockUpNum) {
                this.haveStockUpNum = haveStockUpNum;
            }

            public int getHaveStockGoodsWeight() {
                return haveStockGoodsWeight;
            }

            public void setHaveStockGoodsWeight(int haveStockGoodsWeight) {
                this.haveStockGoodsWeight = haveStockGoodsWeight;
            }

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

        public static class DetailListBean implements Comparator<DetailListBean> {
            /**
             * palletNum : 211061101814201909100003
             * modelId : 1567759454274532
             * goodsId : 1581237406925089
             * goodsCnt : 4
             * goodsWeight : 20
             * storageAreaId : 1581044035258810
             * storageAreaName : A0102
             * opUser : null
             * opTime : null
             * opEquipment : null
             * insDate : null
             * insUserId : null
             * updDate : null
             * updUserId : null
             * prepareDetailId : 3
             * prepareId : 1
             * status : 0
             * palletStorageInNo : null
             */

            private String palletNum;
            private String modelId;
            private String goodsId;
            private int goodsCnt;
            private int goodsWeight;
            private String storageAreaId;
            private String storageAreaName;
            private Object opUser;
            private Object opTime;
            private Object opEquipment;
            private Object insDate;
            private Object insUserId;
            private Object updDate;
            private Object updUserId;
            private String prepareDetailId;
            private String prepareId;
            private String status;
            private Object palletStorageInNo;
            private String goodsModel;//货物规格规格
            private boolean isLastCheck = false;

            public boolean isLastCheck() {
                return isLastCheck;
            }

            public void setLastCheck(boolean lastCheck) {
                isLastCheck = lastCheck;
            }

            public String getGoodsModel() {
                return goodsModel;
            }

            public void setGoodsModel(String goodsModel) {
                this.goodsModel = goodsModel;
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

            public Object getOpUser() {
                return opUser;
            }

            public void setOpUser(Object opUser) {
                this.opUser = opUser;
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

            public Object getPalletStorageInNo() {
                return palletStorageInNo;
            }

            public void setPalletStorageInNo(Object palletStorageInNo) {
                this.palletStorageInNo = palletStorageInNo;
            }

            @Override
            public int compare(DetailListBean o1, DetailListBean o2) {
                int diff = StringUtil.String2int(o1.getStatus()) - StringUtil.String2int(o2.getStatus().equals("0") ? "3" : o2.getStatus());

                if (diff > 0) {
                    return 1;
                } else if (diff < 0) {
                    return -1;
                }
                return 0; //相等为0
            }

            @Override
            public String toString() {
                return super.toString();
            }
        }
    }


}
