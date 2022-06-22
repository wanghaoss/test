package com.etuo.kucun.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.etuo.kucun.utils.QuickSetParcelableUtil;

/**
 * Created by yhn on 2018/9/13.
 */

public class ScanTpInfoCodeModel   implements Parcelable {


    private String palletModel;//型号
    private String orderType;//订单类型（0查看1收2转3还)
    private String palletNum;//托盘编号
    private String state;// 托盘状态值  0 成功  1 查无此托盘

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    private String productType;//1、托盘 2、集装箱
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    private String modelId;

   public String message;// 错误时的提示框

    private String materialName;//	材质
    private String structureName;//		结构
    private String typeName	;//	类型
    private String specLength;//		尺寸长
    private String specWidth;//		尺寸宽
    private String branchId	;//	网点ID

    private String  palletId;//	托盘ID
    private String thumbNail;// 图片链接

    private String  admagedLink;//  跳转页面	 + 参数全部传回来

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPalletId() {
        return palletId;
    }

    public void setPalletId(String palletId) {
        this.palletId = palletId;
    }

    public String getAdmagedLink() {
        return admagedLink;
    }

    public void setAdmagedLink(String admagedLink) {
        this.admagedLink = admagedLink;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPalletNum() {
        return palletNum;
    }

    public void setPalletNum(String palletNum) {
        this.palletNum = palletNum;
    }

    public String getPalletModel() {
        return palletModel;
    }

    public void setPalletModel(String palletModel) {
        this.palletModel = palletModel;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        QuickSetParcelableUtil.write(dest, this);
    }


    public static final Creator<ScanTpInfoCodeModel> CREATOR = new Creator<ScanTpInfoCodeModel>() {

        @Override
        public ScanTpInfoCodeModel createFromParcel(Parcel source) {
            ScanTpInfoCodeModel obj = (ScanTpInfoCodeModel) QuickSetParcelableUtil
                    .read(source, ScanTpInfoCodeModel.class);
            return obj;
        }

        @Override
        public ScanTpInfoCodeModel[] newArray(int size) {
            return new ScanTpInfoCodeModel[size];
        }

    };
}
