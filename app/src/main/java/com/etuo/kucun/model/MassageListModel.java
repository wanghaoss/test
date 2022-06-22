package com.etuo.kucun.model;

/**
 * 消息列表
 * Created by xfy on 2018/11/21.
 */

public class MassageListModel {

    /**
     * browseCnt : 0
     * contents : string
     * dateFrom : string
     * dateTo : string
     * delFlg : string
     * findKeywords : string
     * findStatus : string
     * getFlatSms : true
     * id : string
     * insDate : string
     * msgType : string
     * openFlg : string
     * pageIndex : 0
     * pageSize : 0
     * play : true
     * primaryKey : string
     * receiver : string
     * receiverDelFlg : string
     * replyFlg : string
     * sender : string
     * senderDelFlg : string
     * sort : string
     * sortField : string
     * subject : string
     * tenantId : string
     * topFlg : string
     * updDate : string
     * updUserId : string
     */

    private int browseCnt;
    private String contents;//消息内容
    private String dateFrom;
    private String dateTo;
    private String delFlg;
    private String findKeywords;
    private String findStatus;
    private boolean getFlatSms;
    private String id;
    private String insDate;
    private String msgType;
    private String openFlg;
    private int pageIndex;
    private int pageSize;
    private boolean play;
    private String primaryKey;
    private String receiver;
    private String receiverDelFlg;
    private String replyFlg;
    private String sender;
    private String senderDelFlg;
    private String sort;
    private String sortField;
    private String subject;
    private String tenantId;
    private String topFlg;
    private String updDate;//时间
    private String updUserId;

    public int getBrowseCnt() {
        return browseCnt;
    }

    public void setBrowseCnt(int browseCnt) {
        this.browseCnt = browseCnt;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getFindKeywords() {
        return findKeywords;
    }

    public void setFindKeywords(String findKeywords) {
        this.findKeywords = findKeywords;
    }

    public String getFindStatus() {
        return findStatus;
    }

    public void setFindStatus(String findStatus) {
        this.findStatus = findStatus;
    }

    public boolean isGetFlatSms() {
        return getFlatSms;
    }

    public void setGetFlatSms(boolean getFlatSms) {
        this.getFlatSms = getFlatSms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInsDate() {
        return insDate;
    }

    public void setInsDate(String insDate) {
        this.insDate = insDate;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getOpenFlg() {
        return openFlg;
    }

    public void setOpenFlg(String openFlg) {
        this.openFlg = openFlg;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverDelFlg() {
        return receiverDelFlg;
    }

    public void setReceiverDelFlg(String receiverDelFlg) {
        this.receiverDelFlg = receiverDelFlg;
    }

    public String getReplyFlg() {
        return replyFlg;
    }

    public void setReplyFlg(String replyFlg) {
        this.replyFlg = replyFlg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderDelFlg() {
        return senderDelFlg;
    }

    public void setSenderDelFlg(String senderDelFlg) {
        this.senderDelFlg = senderDelFlg;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTopFlg() {
        return topFlg;
    }

    public void setTopFlg(String topFlg) {
        this.topFlg = topFlg;
    }

    public String getUpdDate() {
        return updDate;
    }

    public void setUpdDate(String updDate) {
        this.updDate = updDate;
    }

    public String getUpdUserId() {
        return updUserId;
    }

    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }
}
