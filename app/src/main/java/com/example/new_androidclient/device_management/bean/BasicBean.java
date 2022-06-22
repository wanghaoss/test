package com.example.new_androidclient.device_management.bean;

public class BasicBean {

    /**
     * 设备
    private String model;///型号
    private String spec;///规格
    private String medium;//介质
    private String deviceClassName;///设别类别
    private String deviceCatExtName;///扩展类别
    private String professionalCatName;///专业类别
    private String deviceMgtCatName;///管理类别
    private String circuitNo;///回路编号
    private String circuitType;///回路类型   测量回路==1 控制回路==2
    private String designProviderName;///设计单位
    private String designDate;///设计时间
    private String constructProviderName;///制造单位
    private String installProviderName;///安装单位
    private String installDate;///最后安装时间
    private String serialNo;///出厂编号
    private String manufactureDate;///出厂时间
    private String enableTime;//投用时间
    private String deviceNo;//设备编码/;
    private String deviceName;//设备名称
    private String tagNo;//设备位号
    private String factoryName;///所属分厂
    private String dptName;///所属车间
    private String instName;///所属装置
    private String unitName;//所属单元
    private String areaName;//所属区域
    private String useStatus;///使用状态
    private String remark; //备注

    密封点
    private String pointNo; //密封点编号
    private String pointName;//名称
    private String pointType;//类别 0=静密封 1=动密封
    private String pointStructure;//密封面结构类别
//    { key: "2", pointStructure: "突面" },
//    { key: "3", pointStructure: "榫面" },
//    { key: "4", pointStructure: "槽面" },
//    { key: "5", pointStructure: "凸面" },
//    { key: "6", pointStructure: "凹面" },
//    { key: "7", pointStructure: "环连接面"},
//    { key: "8", pointStructure: "丝扣" },
//    { key: "9", pointStructure: "角法兰" },
//    { key: "10", pointStructure: "格兰" },
//    { key: "11", pointStructure: "卡套" }

    参数
    private String assetspecName;//参数名称
    private String assetspecUnit;//参数单位
    private String assetspecData;//参数值
    private String assetspecConditions;//参数条件
    private String assetspecGroup;//1运行参数0主要



    附属设备
    private String affiliatedName;//附属设备名称
    private Integer affiliatedId;//附属设备 id
    private String mainName;// 主设备名称

    关联设备
    private String associatedName;//关联 设备名称
    private Integer associatedId;//关联设备 id
     */

    private Object model;
    private Object spec;
    private Object medium;
    private Object deviceClassName;
    private Object deviceCatExtName;
    private Object professionalCatName;
    private Object deviceMgtCatName;
    private Object circuitNo;
    private Object circuitType;
    private Object designProviderName;
    private Object designDate;
    private Object constructProviderName;
    private Object installProviderName;
    private Object installDate;
    private Object serialNo;
    private Object manufactureDate;
    private Object enableTime;
    private String deviceNo;
    private String deviceName;
    private String tagNo;
    private String factoryName;
    private String dptName;
    private String instName;
    private Object unitName;
    private String areaName;
    private String useStatus;
    private String remark;
    private Object pointNo;
    private Object pointName;
    private Object pointType;
    private Object pointStructure;
    private Object assetspecName;
    private Object assetspecUnit;
    private Object assetspecData;
    private Object assetspecConditions;
    private Object assetspecGroup;
    private Object affiliatedName;
    private Object affiliatedId;
    private Object mainName;
    private Object associatedName;
    private Object associatedId;

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public Object getSpec() {
        return spec;
    }

    public void setSpec(Object spec) {
        this.spec = spec;
    }

    public Object getMedium() {
        return medium;
    }

    public void setMedium(Object medium) {
        this.medium = medium;
    }

    public Object getDeviceClassName() {
        return deviceClassName;
    }

    public void setDeviceClassName(Object deviceClassName) {
        this.deviceClassName = deviceClassName;
    }

    public Object getDeviceCatExtName() {
        return deviceCatExtName;
    }

    public void setDeviceCatExtName(Object deviceCatExtName) {
        this.deviceCatExtName = deviceCatExtName;
    }

    public Object getProfessionalCatName() {
        return professionalCatName;
    }

    public void setProfessionalCatName(Object professionalCatName) {
        this.professionalCatName = professionalCatName;
    }

    public Object getDeviceMgtCatName() {
        return deviceMgtCatName;
    }

    public void setDeviceMgtCatName(Object deviceMgtCatName) {
        this.deviceMgtCatName = deviceMgtCatName;
    }

    public Object getCircuitNo() {
        return circuitNo;
    }

    public void setCircuitNo(Object circuitNo) {
        this.circuitNo = circuitNo;
    }

    public Object getCircuitType() {
        return circuitType;
    }

    public void setCircuitType(Object circuitType) {
        this.circuitType = circuitType;
    }

    public Object getDesignProviderName() {
        return designProviderName;
    }

    public void setDesignProviderName(Object designProviderName) {
        this.designProviderName = designProviderName;
    }

    public Object getDesignDate() {
        return designDate;
    }

    public void setDesignDate(Object designDate) {
        this.designDate = designDate;
    }

    public Object getConstructProviderName() {
        return constructProviderName;
    }

    public void setConstructProviderName(Object constructProviderName) {
        this.constructProviderName = constructProviderName;
    }

    public Object getInstallProviderName() {
        return installProviderName;
    }

    public void setInstallProviderName(Object installProviderName) {
        this.installProviderName = installProviderName;
    }

    public Object getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Object installDate) {
        this.installDate = installDate;
    }

    public Object getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Object serialNo) {
        this.serialNo = serialNo;
    }

    public Object getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(Object manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public Object getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(Object enableTime) {
        this.enableTime = enableTime;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getDptName() {
        return dptName;
    }

    public void setDptName(String dptName) {
        this.dptName = dptName;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public Object getUnitName() {
        return unitName;
    }

    public void setUnitName(Object unitName) {
        this.unitName = unitName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getPointNo() {
        return pointNo;
    }

    public void setPointNo(Object pointNo) {
        this.pointNo = pointNo;
    }

    public Object getPointName() {
        return pointName;
    }

    public void setPointName(Object pointName) {
        this.pointName = pointName;
    }

    public Object getPointType() {
        return pointType;
    }

    public void setPointType(Object pointType) {
        this.pointType = pointType;
    }

    public Object getPointStructure() {
        return pointStructure;
    }

    public void setPointStructure(Object pointStructure) {
        this.pointStructure = pointStructure;
    }

    public Object getAssetspecName() {
        return assetspecName;
    }

    public void setAssetspecName(Object assetspecName) {
        this.assetspecName = assetspecName;
    }

    public Object getAssetspecUnit() {
        return assetspecUnit;
    }

    public void setAssetspecUnit(Object assetspecUnit) {
        this.assetspecUnit = assetspecUnit;
    }

    public Object getAssetspecData() {
        return assetspecData;
    }

    public void setAssetspecData(Object assetspecData) {
        this.assetspecData = assetspecData;
    }

    public Object getAssetspecConditions() {
        return assetspecConditions;
    }

    public void setAssetspecConditions(Object assetspecConditions) {
        this.assetspecConditions = assetspecConditions;
    }

    public Object getAssetspecGroup() {
        return assetspecGroup;
    }

    public void setAssetspecGroup(Object assetspecGroup) {
        this.assetspecGroup = assetspecGroup;
    }

    public Object getAffiliatedName() {
        return affiliatedName;
    }

    public void setAffiliatedName(Object affiliatedName) {
        this.affiliatedName = affiliatedName;
    }

    public Object getAffiliatedId() {
        return affiliatedId;
    }

    public void setAffiliatedId(Object affiliatedId) {
        this.affiliatedId = affiliatedId;
    }

    public Object getMainName() {
        return mainName;
    }

    public void setMainName(Object mainName) {
        this.mainName = mainName;
    }

    public Object getAssociatedName() {
        return associatedName;
    }

    public void setAssociatedName(Object associatedName) {
        this.associatedName = associatedName;
    }

    public Object getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(Object associatedId) {
        this.associatedId = associatedId;
    }
}
