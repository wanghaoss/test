package com.etuo.kucun.model;

import java.io.Serializable;
import java.util.List;

/**
 * 出库详情model
 * Created by xfy on 2018/11/23.
 */

public class OutBoundDetailsModel implements Serializable{


    /**
     * orderNum : R181025161546LYW
     * buttonFlg : 1
     * data : [{"thumbnail":"http://192.168.1.211:8080/upload/images","palletName":"","palletModel":"","bond":"元/片","dailyrents":"元/片/次","quantity":"5","enterStockQuantity":null,"stockDetailList":[{"inventoryState":"已扫","palletNum":"8102510018201809290010"},{"inventoryState":"已扫","palletNum":"8102510018201809290009"},{"inventoryState":"已扫","palletNum":"8102510018201809290008"},{"inventoryState":"已扫","palletNum":"8102510018201809290007"},{"inventoryState":"已扫","palletNum":"8102510018201809290006"}]}]
     */

    private String orderNum;
    private String buttonFlg;
    private List<DataBean> data;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getButtonFlg() {
        return buttonFlg;
    }

    public void setButtonFlg(String buttonFlg) {
        this.buttonFlg = buttonFlg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * thumbnail : http://192.168.1.211:8080/upload/images
         * palletName : 
         * palletModel : 
         * bond : 元/片
         * dailyrents : 元/片/次
         * quantity : 5
         * enterStockQuantity : null
         * stockDetailList : [{"inventoryState":"已扫","palletNum":"8102510018201809290010"},{"inventoryState":"已扫","palletNum":"8102510018201809290009"},{"inventoryState":"已扫","palletNum":"8102510018201809290008"},{"inventoryState":"已扫","palletNum":"8102510018201809290007"},{"inventoryState":"已扫","palletNum":"8102510018201809290006"}]
         */

        private String thumbnail;
        private String palletName;
        private String palletModel;
        private String bond;
        private String dailyrents;
        private String quantity;
        private String enterStockQuantity;

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        private String productType;
        private List<StockDetailListBean> stockDetailList;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
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

        public String getEnterStockQuantity() {
            return enterStockQuantity;
        }

        public void setEnterStockQuantity(String enterStockQuantity) {
            this.enterStockQuantity = enterStockQuantity;
        }

        public List<StockDetailListBean> getStockDetailList() {
            return stockDetailList;
        }

        public void setStockDetailList(List<StockDetailListBean> stockDetailList) {
            this.stockDetailList = stockDetailList;
        }

        public static class StockDetailListBean implements Serializable{
            /**
             * inventoryState : 已扫
             * palletNum : 8102510018201809290010
             */

            private String inventoryState;
            private String palletNum;

            public String getInventoryState() {
                return inventoryState;
            }

            public void setInventoryState(String inventoryState) {
                this.inventoryState = inventoryState;
            }

            public String getPalletNum() {
                return palletNum;
            }

            public void setPalletNum(String palletNum) {
                this.palletNum = palletNum;
            }
        }
    }
}
