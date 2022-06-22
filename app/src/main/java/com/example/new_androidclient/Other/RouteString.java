package com.example.new_androidclient.Other;

public class RouteString {
    public static final String LoginActivity = "/com/LoginActivity";
    public static final String MainActivity = "/com/MainActivity";
    public static final String NFCActivity = "/com/NFCActivity";
    /**
     * 隐患
     **/
    public static final String HazardListActivity = "/Hazard/HazardListActivity"; //排查+验证 列表
    public static final String HazardDetailActivity = "/Hazard/HazardDetailActivity";  //现场检查详情
    public static final String HazardVerificationDetailActivity = "/Hazard/HazardVerificationDetailActivity"; // 验收详情
    public static final String HazardNotificationSignActivity = "/Hazard/HazardNotificationSignActivity"; //整改通知
    public static final String AnalysisActivity = "/Hazard/AnalysisActivity"; //旧版分析， 没有lec和人工两种方式
    public static final String HazardAnalysisDeviceSelectActivity = "/Hazard/HazardAnalysisDeviceSelectActivity"; //分析-搜索设备
    public static final String HazardAnalysisPersonSelectActivity = "/Hazard/HazardAnalysisPersonSelectActivity"; //分析-负责人
    public static final String HazardAnalysisTreeActivity = "/Hazard/HazardAnalysisTreeActivity"; //分析-树形
    public static final String HazardPlanActivity = "/Hazard/HazardPlanActivity";//整改计划编审
    public static final String HazardPlanSignActivity = "/Hazard/HazardPlanSignActivity";//整改计划审批
    public static final String HazardGovernmentActivity = "/Hazard/HazardGovernmentActivity";//整改治理
    public static final String HazardPlanUploadFileActivity = "/Hazard/HazardPlanUploadFileActivity";//整改计划上传文件
    public static final String HazardVerificationListActivity = "/Hazard/HazardVerificationListActivity";//验收列表
    public static final String HazardMainActivity = "/Hazard/HazardMainActivity";//隐患首页
    public static final String HazardTablePlanListActivity = "/Hazard/HazardTablePlanListActivity";//隐患计划看板列表
    public static final String HazardTablePlanDetailActivity = "/Hazard/HazardTablePlanDetailActivity";//隐患计划看板详情
    public static final String HazardAnalysisNewActivity = "/Hazard/HazardAnalysisNewActivity";//新版分析， 有lec和人工两种方式
    //public static final String HazardAnalysisSignActivity = "/Hazard/HazardAnalysisSignActivity";//隐患分析审核 审批
    public static final String HazardMajorHiddenDangerListActivity = "/Hazard/HazardMajorHiddenDangerListActivity";//从推送消息点进来查看重大隐患列表

    /**
     * 巡检
     **/
    public static final String AreaDistinguishActivity = "/Inspection/AreaDistinguishActivity"; // 判断是计划巡检还是随机巡检
    public static final String InspectionDeviceListActivity = "/Inspection/InspectionDeviceListActivity"; // 获取区域里的设备列表
    public static final String InspectionDeviceDetailActivity = "/Inspection/InspectionDeviceDetailActivity"; // 获取区域里的设备列表
    public static final String InspectionSelectSpecActivity = "/Inspection/InspectionSelectSpecActivity"; // 随机巡检，展示参数列表然后选择
    public static final String InspectionStopActivity = "/Inspection/InspectionStopActivity"; // 终止
    public static final String Inspection24ProblemActivity = "/Inspection/Inspection24ProblemActivity"; // 24小时异常列表
    public static final String Inspection24DetailActivity = "/Inspection/Inspection24DetailActivity"; // 24小时异常详情
    public static final String InspectionAreaListActivity = "/Inspection/InspectionAreaListActivity"; // 巡检路线
    public static final String InspectionRoughActivity = "/Inspection/InspectionRoughActivity"; // 宏观巡检
    public static final String InspectionRoughDeviceListActivity = "/Inspection/InspectionRoughDeviceListActivity"; // 宏观巡检-设备列表
    public static final String InspectionDeviceRoughDetailActivity = "/Inspection/InspectionDeviceRoughDetailActivity"; // 计划巡检里的区域巡检
    public static final String InspectionDeviceRoughAndAreaSelectActivity = "/Inspection/InspectionDeviceRoughAndAreaSelectActivity"; // 计划巡检里的宏观巡检/区域巡检-搜索页面
    public static final String InspectionDeviceRoughAndAreaSelectDeviceListActivity = "/Inspection/InspectionDeviceRoughAndAreaSelectDeviceListActivity"; // 计划巡检里的宏观巡检/区域巡检-搜索后跳转到设备页面
    public static final String InspectionDeviceRoughAndAreaTakePhotoActivity = "/Inspection/InspectionDeviceRoughAndAreaTakePhotoActivity"; // 计划巡检里的宏观巡检/区域巡检-关联项拍照
    public static final String InspectionTakePhotoActivity = "/Inspection/InspectionTakePhotoActivity";//拍照
    public static final String InspectionDeviceAreaDetailActivity = "/Inspection/InspectionDeviceAreaDetailActivity";//计划巡检里的宏观巡检
    public static final String InspectionNfcActivity = "/Inspection/InspectionNfcActivity";//nfc

    /**
     * 待办
     **/
    public static final String WaitInspectionListActivity = "/Wait/WaitInspectionListActivity";//巡检待办列表
    public static final String WaitInspectionDetail = "/Wait/WaitInspectionDetail";//巡检待办详情
    public static final String WaitHazardListActivity = "/Wait/WaitHazardListActivity";//待办隐患列表
    public static final String WaitMainActivity = "/Wait/WaitMainActivity";//待办首页列表

    /**
     * 设备管理
     **/
    public static final String DeviceListActivity = "/device_management/DeviceListActivity";//设备管理列表
    public static final String DeviceActivity = "/device_management/DeviceActivity";//设备管理
    public static final String DevicePlanManagementActivity = "/device_management/DevicePlanManagementActivity";//计划管理
    public static final String InterimPlanActivity = "/device_management/InterimPlanActivity";//临时计划列表
    public static final String InterimPlanDetailsActivity = "/device_management/InterimPlanDetailsActivity";//临时计划详情
    public static final String NewActivity = "/device_management/NewActivity";//临时计划详情
    public static final String DeviceSearchActivity = "/device_management/DeviceSearchActivity";//设备管理搜索
    public static final String WorkOrderActivity = "/device_management//device_management/NewActivity";//工单列表
    public static final String SearchActivity = "/device_management/srarch/SearchActivity";//设备搜索

    /**
     * 版本更新与通知
     **/
    public static final String EditionAndPushActivity = "/edition/EditionAndPushActivity";

    /**
     * 作业模块
     **/
    public static final String WorkNeedDoActivity = "/work/WorkNeedDoActivity";//作业待列表
    public static final String WorkInitialActivity = "/work/WorkInitialActivity";//作业待办

    public static final String ReconnaissanceActivity = "/work/ReconnaissanceActivity";//踏勘记录

    //申请
    public static final String WorkApplicationActivity = "/work/WorkApplicationActivity";//作业申请记录
    public static final String WorkApplicationCheckListActivity = "/work/WorkApplicationCheckListActivity";//申请时多选页面
    public static final String WorkApplicationInvolveActivity = "/work/WorkApplicationInvolveActivity";//作业涉及事项选择
    public static final String WorkDKDeviceListActivity = "/work/WorkDKDeviceListActivity";//管线打开申请选择装之下设备
    public static final String WorkDHApplicationActivity = "/work/WorkDHApplicationActivity";//动火申请
    public static final String WorkSXApplicationActivity = "/work/WorkSXApplicationActivity";//受限申请
    public static final String WorkGCApplicationActivity = "/work/WorkGCApplicationActivity";//高处申请
    public static final String WorkDTApplicationActivity = "/work/WorkDTApplicationActivity";//动土申请
    public static final String WorkDZApplicationActivity = "/work/WorkDZApplicationActivity";//吊装申请
    public static final String WorkDKApplicationActivity = "/work/WorkDKApplicationActivity";//管线申请
    public static final String WorkLDApplicationActivity = "/work/WorkLDApplicationActivity";//临时用电申请
    public static final String WorkDLApplicationActivity = "/work/WorkDLApplicationActivity";//断路申请

    //预约
    public static final String WorkAppointmentActivity = "/work/WorkAppointmentActivity";//作业预约
    public static final String WorkBlindPlate = "/work/WorkBlindPlate";//作业预约-工艺盲板表
    public static final String WorkAddBlindPlateActivity = "/work/WorkAddBlindPlateActivity";//作业预约-添加工艺盲板表
    public static final String WorkDHAppointmentDetailActivity = "/work/WorkDHAppointmentDetailActivity";//预约动火详情

    //现场核查
    public static final String SceneCheckActivity = "/work/SceneCheckActivity";//现场核查
    public static final String WorkRegionActivity = "/work/WorkRegionActivity";//设备详情

    public static final String WorkDHAppointmentActivity = "/work/WorkDHAppointmentActivity";//动火作业安全措施方案

    public static final String WorkRiskListActivity = "/work/WorkRiskListActivity";//安全措施列表

    public static final String WorkControlActivity = "/work/WorkControlActivity";//作业监督按钮页

    public static final String WorkCloseActivity = "/work/WorkCloseActivity";//作业关闭

    public static final String WorkAddSignActivity = "/work/WorkAddSignActivity";//作业票添加签字
    public static final String WorkSigntureActivity = "/work/WorkSigntureActivity";//作业票审核审批
    public static final String AnalysisProjectActivity = "/work/AnalysisProjectActivity";//预约人填写分析项目
    public static final String CountersignActivity = "/work/CountersignActivity";//交底会签
    public static final String PermitCountersignActivity = "/work/PermitCountersignActivity";//许可会签

}
