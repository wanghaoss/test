package com.etuo.kucun.model;

import android.support.annotation.NonNull;

/**
 * Created by yhn on 2018/8/7.
 * 首页网点列表
 */

public class BranchInfoListModel  implements Comparable {

   private String branchId;//	网点编号
    private String  branchName;//网点名称
    private String  telephone;//	网点电话
    private String  address	;//网点地址
    private String province;// 省
    private String  city;//	网点所在城市
    private String  longitude;//	网点经度
    private String  latitude;//	网点纬度
    private String branchPicPath;//	托盘图片链接地址
    private String workTime;//	网点营业时间
    private double distance ;// 网点离当前位置的距离 (m)

    private String palletListLink;// 查看网点信息的web 链接

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPalletListLink() {
        return palletListLink;
    }

    public void setPalletListLink(String palletListLink) {
        this.palletListLink = palletListLink;
    }

    public String getBranchPicPath() {
        return branchPicPath;
    }

    public void setBranchPicPath(String branchPicPath) {
        this.branchPicPath = branchPicPath;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }



    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        BranchInfoListModel s = (BranchInfoListModel) o;
        if (this.distance > s.distance) {
            return 1;
        }
        else  {
            return -1;
        }
    }
}
