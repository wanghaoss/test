package com.example.new_androidclient.wait_to_handle.bean;

public class DefaultPicBean {

    /**
     * docId : 691
     * docName : ee2bbadd-2858-49fc-8eec-5dbdb964378b.jpg
     * businessId : 147
     * description : 1597997125474.jpg
     * bucketName : mobile
     * docUrl : http://39.99.33.184:9000/mobile/ee2bbadd-2858-49fc-8eec-5dbdb964378b.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20200821%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20200821T080537Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&X-Amz-Signature=57fb5586ebcd925845a2b673ad8f247e6e4a5ae0ee9c1c7807c324aed598eab9
     * createTime : 2020-08-21 00:00:00
     * updateTime : null
     * status : active
     * tenantId : 13
     * businessType : 41
     * size : 38113
     * fileTypeList : null
     * createBy : 20
     * placeForStorage : null
     * createUserName : 管理员
     */

    private int docId;
    private String docName;
    private int businessId;
    private String description;
    private String bucketName;
    private String docUrl;
    private String createTime;
    private String updateTime;
    private String status;
    private int tenantId;
    private String businessType;
    private String size;
    private String fileTypeList;
    private int createBy;
    private Object placeForStorage;
    private String createUserName;

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setFileTypeList(String fileTypeList) {
        this.fileTypeList = fileTypeList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Object getFileTypeList() {
        return fileTypeList;
    }



    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public Object getPlaceForStorage() {
        return placeForStorage;
    }

    public void setPlaceForStorage(Object placeForStorage) {
        this.placeForStorage = placeForStorage;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
