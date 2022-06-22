package com.example.new_androidclient.work.bean;

import java.util.List;
import java.util.Map;

public class WorkDHAppointmentBean {

    /**
     * delFlag : 0
     * createBy : 20
     * createTime : 2020-11-17 15:04:20
     * updateBy : null
     * updateTime : null
     * page : 1
     * size : 10
     * sort : null
     * id : 1231
     * applicationId : 141
     * workingType : DH
     * name : 动火设备内部构件清理干净，蒸汽吹扫或水洗合格，达到动火条件
     * itemName : 动火设备内部构件清理干净，蒸汽吹扫或水洗合格，达到动火条件
     * isRefer : null
     * confirmer : null
     * confirmTime : 2020-11-17 15:04:20
     * type : 2
     * itemType : 0
     * extraData : {"工作区域警戒":"0","空气流通":"0","安全带":"0","火花收集":"0","便携式检测仪":"0"}
     */

    private String delFlag;
    private int createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private int page;
    private int size;
    private String sort;
    private int id;
    private int applicationId;
    private String workingType;
    private String name;
    private String itemName;
    private String isRefer;
    private String confirmer;
    private String confirmTime;
    private int type;
    private String itemType;
    private Map<String, String> extraData;
    private String checkConfirmer;
    private String checkResult;


    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateBy() {
        return updateBy;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Object getSort() {
        return sort;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getWorkingType() {
        return workingType;
    }

    public void setWorkingType(String workingType) {
        this.workingType = workingType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemName() {
        if(itemName.isEmpty() && type == 2){
            return "若选择下列项请先选择本层级";
        }
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Object getIsRefer() {
        return isRefer;
    }


    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setIsRefer(String isRefer) {
        this.isRefer = isRefer;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }

    public String getCheckConfirmer() {
        return checkConfirmer;
    }

    public void setCheckConfirmer(String checkConfirmer) {
        this.checkConfirmer = checkConfirmer;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    //    public List<ExtraDataBean> getExtraData() {
//        return extraData;
//    }
//
//    public void setExtraData(List<ExtraDataBean> extraData) {
//        this.extraData = extraData;
//    }


    public Map<String, String> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, String> extraData) {
        this.extraData = extraData;
    }

    public class ExtraDataBean {
        /**
         * 工作区域警戒 : 0
         * 空气流通 : 0
         * 安全带 : 0
         * 火花收集 : 0
         * 便携式检测仪 : 0
         */

        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}