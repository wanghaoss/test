package com.etuo.kucun.model;

/**
 * Created by yhn on 2018/9/12.
 * 扫描 点击确定的返回值
 */

public class ScanInfoOkModel {

  private String  palletId	;//	托盘ID
    private String   palletModel;//		型号
    private String   materialName;//		材质
    private String  structureName;//		结构
    private String   typeName;//		类型
    private String   specLength	;//	尺寸长
    private String   specWidth;//		尺寸宽
    private String   branchId;//		网点ID
    private String   admagedLink;//		跳转页面	 + branchId 传回来
    private String   palletCnt;//		盘点数量
    private String   imgPate;//		图片路径

    public String getPalletId() {
        return palletId;
    }

    public void setPalletId(String palletId) {
        this.palletId = palletId;
    }

    public String getPalletModel() {
        return palletModel;
    }

    public void setPalletModel(String palletModel) {
        this.palletModel = palletModel;
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

    public String getAdmagedLink() {
        return admagedLink;
    }

    public void setAdmagedLink(String admagedLink) {
        this.admagedLink = admagedLink;
    }

    public String getPalletCnt() {
        return palletCnt;
    }

    public void setPalletCnt(String palletCnt) {
        this.palletCnt = palletCnt;
    }

    public String getImgPate() {
        return imgPate;
    }

    public void setImgPate(String imgPate) {
        this.imgPate = imgPate;
    }
}
