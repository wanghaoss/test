package com.etuo.kucun.model;

/**
 * Created by yhn on 2018/9/4.
 * 托盘信息
 */

public class PalletListModel {
    private String SHOPPING_CART_ID;//购物车id

    private String SHOPPING_CART_DETAIL_ID;//购物车详情id
    private String name;//
    private boolean isChoosed;

    private String THUMBNAIL;//托盘图片链接地址

    private String PALLET_NAME;//托盘名称
    private double price;
    private double DAILYRENTS;//日租金
    private int postion;
    private int QUANTITY; //托盘数量
    private String PALLET_MODEL;//托盘型号

    private String MODEL_ID;//型号id
    private String size;
    private int goodsImg;

    public String getMODEL_ID() {
        return MODEL_ID;
    }

    public void setMODEL_ID(String MODEL_ID) {
        this.MODEL_ID = MODEL_ID;
    }

    public String getSHOPPING_CART_DETAIL_ID() {
        return SHOPPING_CART_DETAIL_ID;
    }

    public void setSHOPPING_CART_DETAIL_ID(String SHOPPING_CART_DETAIL_ID) {
        this.SHOPPING_CART_DETAIL_ID = SHOPPING_CART_DETAIL_ID;
    }

    public String getSHOPPING_CART_ID() {
        return SHOPPING_CART_ID;
    }

    public void setSHOPPING_CART_ID(String SHOPPING_CART_ID) {
        this.SHOPPING_CART_ID = SHOPPING_CART_ID;
    }

    public PalletListModel(String modelId, String palletName, String desc, double price, double prime_price,
                           String palletModel, String size, int goodsImg, int count) {
        this.SHOPPING_CART_ID = modelId;
        this.name = palletName;
        this.PALLET_NAME = desc;
        this.price = price;
        this.DAILYRENTS = prime_price;
        this.QUANTITY = count;
        this.PALLET_MODEL = palletModel;
        this.size = size;
        this.goodsImg = goodsImg;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public String getTHUMBNAIL() {
        return THUMBNAIL;
    }

    public void setTHUMBNAIL(String THUMBNAIL) {
        this.THUMBNAIL = THUMBNAIL;
    }

    public String getPALLET_NAME() {
        return PALLET_NAME;
    }

    public void setPALLET_NAME(String PALLET_NAME) {
        this.PALLET_NAME = PALLET_NAME;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDAILYRENTS() {
        return DAILYRENTS;
    }

    public void setDAILYRENTS(double DAILYRENTS) {
        this.DAILYRENTS = DAILYRENTS;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public int getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(int QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getPALLET_MODEL() {
        return PALLET_MODEL;
    }

    public void setPALLET_MODEL(String PALLET_MODEL) {
        this.PALLET_MODEL = PALLET_MODEL;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(int goodsImg) {
        this.goodsImg = goodsImg;
    }
}
