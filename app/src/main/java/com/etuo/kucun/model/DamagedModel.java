package com.etuo.kucun.model;

/**
 * 报损列表model
 * Created by xfy on 2018/11/22.
 */

public class DamagedModel {

    /**
     * browseCnt : 0
     * compensation : 0
     * damagedDate : string
     * dateFrom : string
     * dateTo : string
     * degree : string
     * delFlg : string
     * description : string
     * findKeywords : string
     * findStatus : string
     * id : string
     * imgPath : string
     * insDate : {"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}
     * insUserId : string
     * pageIndex : 0
     * pageSize : 0
     * palletNum : string
     * position : string
     * primaryKey : string
     * sort : string
     * sortField : string
     * state : string
     * updDate : {"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}
     * updUserId : string
     */

    private int browseCnt;
    private int compensation;
    private String damagedDate;//报损日期
    private String dateFrom;
    private String dateTo;
    private String degree;
    private String delFlg;
    private String description;
    private String findKeywords;
    private String findStatus;
    private String id;
    private String imgPath;
    private InsDateBean insDate;
    private String insUserId;
    private int pageIndex;
    private int pageSize;
    private String palletNum;//托盘编码
    private String position;
    private String primaryKey;
    private String sort;
    private String sortField;
    private String state;//状态（0：未处理 1：已处理）
    private UpdDateBean updDate;
    private String updUserId;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    private String productType;//1、托盘 2、集装箱

    public String getPalletState() {
        return palletState;
    }

    public void setPalletState(String palletState) {
        this.palletState = palletState;
    }

    private String palletState;//状态

    public int getBrowseCnt() {
        return browseCnt;
    }

    public void setBrowseCnt(int browseCnt) {
        this.browseCnt = browseCnt;
    }

    public int getCompensation() {
        return compensation;
    }

    public void setCompensation(int compensation) {
        this.compensation = compensation;
    }

    public String getDamagedDate() {
        return damagedDate;
    }

    public void setDamagedDate(String damagedDate) {
        this.damagedDate = damagedDate;
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

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public InsDateBean getInsDate() {
        return insDate;
    }

    public void setInsDate(InsDateBean insDate) {
        this.insDate = insDate;
    }

    public String getInsUserId() {
        return insUserId;
    }

    public void setInsUserId(String insUserId) {
        this.insUserId = insUserId;
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

    public String getPalletNum() {
        return palletNum;
    }

    public void setPalletNum(String palletNum) {
        this.palletNum = palletNum;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public UpdDateBean getUpdDate() {
        return updDate;
    }

    public void setUpdDate(UpdDateBean updDate) {
        this.updDate = updDate;
    }

    public String getUpdUserId() {
        return updUserId;
    }

    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }

    public static class InsDateBean {
        /**
         * date : 0
         * day : 0
         * hours : 0
         * minutes : 0
         * month : 0
         * nanos : 0
         * seconds : 0
         * time : 0
         * timezoneOffset : 0
         * year : 0
         */

        private int date;
        private int day;
        private int hours;
        private int minutes;
        private int month;
        private int nanos;
        private int seconds;
        private int time;
        private int timezoneOffset;
        private int year;

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getNanos() {
            return nanos;
        }

        public void setNanos(int nanos) {
            this.nanos = nanos;
        }

        public int getSeconds() {
            return seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getTimezoneOffset() {
            return timezoneOffset;
        }

        public void setTimezoneOffset(int timezoneOffset) {
            this.timezoneOffset = timezoneOffset;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    public static class UpdDateBean {
        /**
         * date : 0
         * day : 0
         * hours : 0
         * minutes : 0
         * month : 0
         * nanos : 0
         * seconds : 0
         * time : 0
         * timezoneOffset : 0
         * year : 0
         */

        private int date;
        private int day;
        private int hours;
        private int minutes;
        private int month;
        private int nanos;
        private int seconds;
        private int time;
        private int timezoneOffset;
        private int year;

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getNanos() {
            return nanos;
        }

        public void setNanos(int nanos) {
            this.nanos = nanos;
        }

        public int getSeconds() {
            return seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getTimezoneOffset() {
            return timezoneOffset;
        }

        public void setTimezoneOffset(int timezoneOffset) {
            this.timezoneOffset = timezoneOffset;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }
}
