package com.example.new_androidclient.edition.bean;

public class EditionBean {
    private Integer id;
    /**
     * 应用名称
     */
    private String aid;
    /**
     * 版本
     */
    private String ver;
    /**
     * 应用类型(0:android,1:ios)
     */
    private String platform;
    /**
     * 状态(0正常，1建议升级，2必须升级，3版本停止)
     */
    private String status;
    /**
     * 应用url
     */
    private String appUrl;
    /**
     * 图表Id
     */
    private Integer imageId;
    /**
     * app文件Id
     */
    private String fileId;
    /**
     * 应用描述
     */
    private String description;
    /**
     * 租户Id
     */
    private Integer tenantId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }
}
