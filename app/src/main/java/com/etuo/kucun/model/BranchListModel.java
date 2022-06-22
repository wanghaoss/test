package com.etuo.kucun.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/26.
 * 店铺信息
 */

public class BranchListModel {
    private String branchId;//网点id
    private String branchName;//网点名称

    private boolean isChoosed;
    private boolean isEditor; //自己对该组的编辑状态
    private boolean ActionBarEditor;// 全局对该组的编辑状态
    private int flag;


    public BranchListModel(String branchId, String branchName) {
        this.branchId = branchId;
        this.branchName = branchName;
    }



    private List<PalletListModel> ShoppingCarDetaiList;//托盘信息列表

    public List<PalletListModel> getShoppingCarDetaiList() {
        return ShoppingCarDetaiList;
    }

    public void setShoppingCarDetaiList(List<PalletListModel> shoppingCarDetaiList) {
        this.ShoppingCarDetaiList = shoppingCarDetaiList;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }



    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public boolean isEditor() {
        return isEditor;
    }

    public void setEditor(boolean editor) {
        isEditor = editor;
    }
    public boolean isActionBarEditor() {
        return ActionBarEditor;
    }

    public void setActionBarEditor(boolean actionBarEditor) {
        ActionBarEditor = actionBarEditor;
    }
}
