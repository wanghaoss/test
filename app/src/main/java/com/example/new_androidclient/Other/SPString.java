package com.example.new_androidclient.Other;

public class SPString {
    public static final String Token = "userToken";
    public static final String UserName = "userFirstAndLastName";  //管理员
    public static final String TenantId = "tenantId";
    public static final String UserId = "userId";
    public static final String RecordUserName = "userLoginName"; //admin
    public static final String RecordUserName_token = "userLoginName_token"; //为了在Token拦截器刷新Token,所以需要记录用户名密码
    public static final String RecordUserPwd_token = "userLoginPwd_token"; //为了在Token拦截器刷新Token,所以需要记录用户名密码
    public static final String InspectionDeviceList = "InspectionDeviceList";
    public static final String InspectionAreaList = "InspectionAreaList";
    public static final String InspectionAreaPosition = "InspectionAreaPosition"; //扫描区域在区域列表里的下标
    public static final String InspectionAreaRoughPosition = "InspectionAreaRoughPosition"; //设备宏观巡检扫描的区域在区域列表里的下标
    public static final String InspectionPicList = "InspectionPicList";
    public static final String InspectionSpecList = "InspectionSpecList";
    public static final String InspectionRoughList = "InspectionRoughList"; //宏观巡检
    public static final String InspectionRoughPicList = "InspectionRoughPicList";
    public static final String HazardPlanUploadFileList = "HazardPlanUploadFileList";
    public static final String InspectionTaskCode = "InspectionTaskCode"; //通过本字符串获取区域任务
}
