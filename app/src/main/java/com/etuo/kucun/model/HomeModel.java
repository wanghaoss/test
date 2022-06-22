package com.etuo.kucun.model;

import java.util.List;

/**
 * Created by yhn on 2018/8/7.
 */

public class HomeModel {


    /**
     * goodsDispatchBillList : [{"dispatchId":"1581576206080171"}]
     * goodsDispatchBillTotal : 1
     * goodsStorageOutBillList : [{"storageOutId":"1581568693781443"}]
     * goodsStorageOutBillTotal : 2
     * saleStorageOutBillList : [{"storageOutId":"1581568693781443"}]
     * saleStorageOutBillTotal : 2
     * palletStorageInBillList : [{"palletStorageInId":"1581568693781443"}]
     * palletStorageInBillTotal : 0
     * goodsCheckBillList : [{"goodsCheckId":"144656"}]
     * goodsCheckBillTotal : 1
     * goodsPrepareBillList : [{"prepareId":"1"}]
     * goodsPrepareBillTotal : 1
     * goodsStorageInBillList : [{"goodsStorageInId":"1"}]
     * goodsStorageInBillTotal : 1
     */

    private int goodsDispatchBillTotal;
    private int goodsStorageOutBillTotal;
    private int saleStorageOutBillTotal;
    private int palletStorageInBillTotal;
    private int goodsCheckBillTotal;
    private int goodsPrepareBillTotal;
    private int goodsStorageInBillTotal;
    private int palletCheckBillTotal;
    private int returnPalletBillTotal;
    private int productBillTotal;//生产领料待转库数量
    private int goodsTransferBillTotal;//货物待转库数量
    private int palletTransferBillTotal;//托盘待转库数量
    private List<GoodsDispatchBillListBean> goodsDispatchBillList;
    private List<GoodsStorageOutBillListBean> goodsStorageOutBillList;
    private List<SaleStorageOutBillListBean> saleStorageOutBillList;
    private List<PalletStorageInBillListBean> palletStorageInBillList;
    private List<GoodsCheckBillListBean> goodsCheckBillList;
    private List<GoodsPrepareBillListBean> goodsPrepareBillList;
    private List<GoodsStorageInBillListBean> goodsStorageInBillList;
    private List<PalletCheckBillListBean> palletCheckBillList;
    private List<ReturnPalletBillListBean> returnPalletBillList;
    private List<ProductBillListBean> productBillList;//生产领料转库列表
    private List<ProductBillListBean> goodsTransferBillList;//货物转库列表
    private List<ProductBillListBean> palletTransferBillList;//

    public int getProductBillTotal() {
        return productBillTotal;
    }

    public void setProductBillTotal(int productBillTotal) {
        this.productBillTotal = productBillTotal;
    }

    public int getGoodsTransferBillTotal() {
        return goodsTransferBillTotal;
    }

    public void setGoodsTransferBillTotal(int goodsTransferBillTotal) {
        this.goodsTransferBillTotal = goodsTransferBillTotal;
    }

    public int getPalletTransferBillTotal() {
        return palletTransferBillTotal;
    }

    public void setPalletTransferBillTotal(int palletTransferBillTotal) {
        this.palletTransferBillTotal = palletTransferBillTotal;
    }

    public List<ProductBillListBean> getProductBillList() {
        return productBillList;
    }

    public void setProductBillList(List<ProductBillListBean> productBillList) {
        this.productBillList = productBillList;
    }

    public List<ProductBillListBean> getGoodsTransferBillList() {
        return goodsTransferBillList;
    }

    public void setGoodsTransferBillList(List<ProductBillListBean> goodsTransferBillList) {
        this.goodsTransferBillList = goodsTransferBillList;
    }

    public List<ProductBillListBean> getPalletTransferBillList() {
        return palletTransferBillList;
    }

    public void setPalletTransferBillList(List<ProductBillListBean> palletTransferBillList) {
        this.palletTransferBillList = palletTransferBillList;
    }

    public List<ReturnPalletBillListBean> getReturnPalletBillList() {
        return returnPalletBillList;
    }

    public void setReturnPalletBillList(List<ReturnPalletBillListBean> returnPalletBillList) {
        this.returnPalletBillList = returnPalletBillList;
    }

    public int getReturnPalletBillTotal() {
        return returnPalletBillTotal;
    }

    public void setReturnPalletBillTotal(int returnPalletBillTotal) {
        this.returnPalletBillTotal = returnPalletBillTotal;
    }

    public int getPalletCheckBillTotal() {
        return palletCheckBillTotal;
    }

    public void setPalletCheckBillTotal(int palletCheckBillTotal) {
        this.palletCheckBillTotal = palletCheckBillTotal;
    }

    public List<PalletCheckBillListBean> getPalletCheckBillList() {
        return palletCheckBillList;
    }

    public void setPalletCheckBillList(List<PalletCheckBillListBean> palletCheckBillList) {
        this.palletCheckBillList = palletCheckBillList;
    }

    public int getGoodsDispatchBillTotal() {
        return goodsDispatchBillTotal;
    }

    public void setGoodsDispatchBillTotal(int goodsDispatchBillTotal) {
        this.goodsDispatchBillTotal = goodsDispatchBillTotal;
    }

    public int getGoodsStorageOutBillTotal() {
        return goodsStorageOutBillTotal;
    }

    public void setGoodsStorageOutBillTotal(int goodsStorageOutBillTotal) {
        this.goodsStorageOutBillTotal = goodsStorageOutBillTotal;
    }

    public int getSaleStorageOutBillTotal() {
        return saleStorageOutBillTotal;
    }

    public void setSaleStorageOutBillTotal(int saleStorageOutBillTotal) {
        this.saleStorageOutBillTotal = saleStorageOutBillTotal;
    }

    public int getPalletStorageInBillTotal() {
        return palletStorageInBillTotal;
    }

    public void setPalletStorageInBillTotal(int palletStorageInBillTotal) {
        this.palletStorageInBillTotal = palletStorageInBillTotal;
    }

    public int getGoodsCheckBillTotal() {
        return goodsCheckBillTotal;
    }

    public void setGoodsCheckBillTotal(int goodsCheckBillTotal) {
        this.goodsCheckBillTotal = goodsCheckBillTotal;
    }

    public int getGoodsPrepareBillTotal() {
        return goodsPrepareBillTotal;
    }

    public void setGoodsPrepareBillTotal(int goodsPrepareBillTotal) {
        this.goodsPrepareBillTotal = goodsPrepareBillTotal;
    }

    public int getGoodsStorageInBillTotal() {
        return goodsStorageInBillTotal;
    }

    public void setGoodsStorageInBillTotal(int goodsStorageInBillTotal) {
        this.goodsStorageInBillTotal = goodsStorageInBillTotal;
    }

    public List<GoodsDispatchBillListBean> getGoodsDispatchBillList() {
        return goodsDispatchBillList;
    }

    public void setGoodsDispatchBillList(List<GoodsDispatchBillListBean> goodsDispatchBillList) {
        this.goodsDispatchBillList = goodsDispatchBillList;
    }

    public List<GoodsStorageOutBillListBean> getGoodsStorageOutBillList() {
        return goodsStorageOutBillList;
    }

    public void setGoodsStorageOutBillList(List<GoodsStorageOutBillListBean> goodsStorageOutBillList) {
        this.goodsStorageOutBillList = goodsStorageOutBillList;
    }

    public List<SaleStorageOutBillListBean> getSaleStorageOutBillList() {
        return saleStorageOutBillList;
    }

    public void setSaleStorageOutBillList(List<SaleStorageOutBillListBean> saleStorageOutBillList) {
        this.saleStorageOutBillList = saleStorageOutBillList;
    }

    public List<PalletStorageInBillListBean> getPalletStorageInBillList() {
        return palletStorageInBillList;
    }

    public void setPalletStorageInBillList(List<PalletStorageInBillListBean> palletStorageInBillList) {
        this.palletStorageInBillList = palletStorageInBillList;
    }

    public List<GoodsCheckBillListBean> getGoodsCheckBillList() {
        return goodsCheckBillList;
    }

    public void setGoodsCheckBillList(List<GoodsCheckBillListBean> goodsCheckBillList) {
        this.goodsCheckBillList = goodsCheckBillList;
    }

    public List<GoodsPrepareBillListBean> getGoodsPrepareBillList() {
        return goodsPrepareBillList;
    }

    public void setGoodsPrepareBillList(List<GoodsPrepareBillListBean> goodsPrepareBillList) {
        this.goodsPrepareBillList = goodsPrepareBillList;
    }

    public List<GoodsStorageInBillListBean> getGoodsStorageInBillList() {
        return goodsStorageInBillList;
    }

    public void setGoodsStorageInBillList(List<GoodsStorageInBillListBean> goodsStorageInBillList) {
        this.goodsStorageInBillList = goodsStorageInBillList;
    }

    public static class GoodsDispatchBillListBean {
        /**
         * dispatchId : 1581576206080171
         */

        private String dispatchId;

        public String getDispatchId() {
            return dispatchId;
        }

        public void setDispatchId(String dispatchId) {
            this.dispatchId = dispatchId;
        }
    }

    public static class GoodsStorageOutBillListBean {
        /**
         * storageOutId : 1581568693781443
         */

        private String storageOutId;

        public String getStorageOutId() {
            return storageOutId;
        }

        public void setStorageOutId(String storageOutId) {
            this.storageOutId = storageOutId;
        }
    }

    public static class SaleStorageOutBillListBean {
        /**
         * storageOutId : 1581568693781443
         */

        private String storageOutId;

        public String getStorageOutId() {
            return storageOutId;
        }

        public void setStorageOutId(String storageOutId) {
            this.storageOutId = storageOutId;
        }
    }

    public static class PalletStorageInBillListBean {
        /**
         * palletStorageInId : 1581568693781443
         */

        private String palletStorageInId;

        public String getPalletStorageInId() {
            return palletStorageInId;
        }

        public void setPalletStorageInId(String palletStorageInId) {
            this.palletStorageInId = palletStorageInId;
        }
    }

    public static class GoodsCheckBillListBean {
        /**
         * goodsCheckId : 144656
         */

        private String goodsCheckId;

        public String getGoodsCheckId() {
            return goodsCheckId;
        }

        public void setGoodsCheckId(String goodsCheckId) {
            this.goodsCheckId = goodsCheckId;
        }
    }

    public static class GoodsPrepareBillListBean {
        /**
         * prepareId : 1
         */

        private String prepareId;

        public String getPrepareId() {
            return prepareId;
        }

        public void setPrepareId(String prepareId) {
            this.prepareId = prepareId;
        }
    }

    public static class GoodsStorageInBillListBean {
        /**
         * goodsStorageInId : 1
         */

        private String goodsStorageInId;

        public String getGoodsStorageInId() {
            return goodsStorageInId;
        }

        public void setGoodsStorageInId(String goodsStorageInId) {
            this.goodsStorageInId = goodsStorageInId;
        }

    }

    public static  class PalletCheckBillListBean{

        private String palletCheckId;//

        public String getPalletCheckId() {
            return palletCheckId;
        }

        public void setPalletCheckId(String palletCheckId) {
            this.palletCheckId = palletCheckId;
        }
    }

    public static class ReturnPalletBillListBean{
        private String revertOrderId;

        public String getRevertOrderId() {
            return revertOrderId;
        }

        public void setRevertOrderId(String revertOrderId) {
            this.revertOrderId = revertOrderId;
        }
    }

    public static class ProductBillListBean{
        private String goodsTransferId;

        public String getGoodsTransferId() {
            return goodsTransferId;
        }

        public void setGoodsTransferId(String goodsTransferId) {
            this.goodsTransferId = goodsTransferId;
        }
    }
}
