package com.example.new_androidclient.NetWork;

import com.example.new_androidclient.device_management.bean.BasicBean;
import com.example.new_androidclient.device_management.bean.CategoryBean;
import com.example.new_androidclient.device_management.bean.ContractorListBean;
import com.example.new_androidclient.device_management.bean.DeviceAnalysisDeviceListBean;
import com.example.new_androidclient.device_management.bean.DevicePlanListBean;
import com.example.new_androidclient.device_management.bean.DeviceSpecBean;
import com.example.new_androidclient.device_management.bean.FacilityBean;
import com.example.new_androidclient.device_management.bean.FindDeviceListBean;
import com.example.new_androidclient.device_management.bean.InterimPlanDetailsBean;
import com.example.new_androidclient.device_management.bean.SealPointBean;
import com.example.new_androidclient.device_management.bean.SearchBean;
import com.example.new_androidclient.device_management.bean.UpDataPlanBean;
import com.example.new_androidclient.edition.bean.EditionBean;
import com.example.new_androidclient.hazard.bean.HazardAnalysisBean;
import com.example.new_androidclient.hazard.bean.HazardAnalysisDeviceListBean;
import com.example.new_androidclient.hazard.bean.HazardAnalysisPersonBean;
import com.example.new_androidclient.hazard.bean.HazardAnalysisTreeBean;
import com.example.new_androidclient.hazard.bean.HazardAnalysisUploadBean;
import com.example.new_androidclient.hazard.bean.HazardDetailBean;
import com.example.new_androidclient.hazard.bean.HazardListBean;
import com.example.new_androidclient.hazard.bean.HazardMajorHiddenDangerListBean;
import com.example.new_androidclient.hazard.bean.HazardNotificationSignInfoBean;
import com.example.new_androidclient.hazard.bean.HazardNotificationSignListBean;
import com.example.new_androidclient.hazard.bean.HazardPicBean;
import com.example.new_androidclient.hazard.bean.HazardPlanListBean;
import com.example.new_androidclient.hazard.bean.HazardTablePlanDetailBean;
import com.example.new_androidclient.hazard.bean.HazardTablePlanListBean;
import com.example.new_androidclient.hazard.bean.HazardVerificationDetailBean;
import com.example.new_androidclient.inspection.bean.AreaResultDevice;
import com.example.new_androidclient.inspection.bean.HazardGovernmentListBean;
import com.example.new_androidclient.inspection.bean.Inspection24Bean;
import com.example.new_androidclient.inspection.bean.Inspection24DetailBean;
import com.example.new_androidclient.inspection.bean.InspectionAreaBean;
import com.example.new_androidclient.inspection.bean.InspectionDeviceDetailBean;
import com.example.new_androidclient.inspection.bean.InspectionDeviceListBean;
import com.example.new_androidclient.inspection.bean.InspectionDeviceRoughSelectDeviceListBean;
import com.example.new_androidclient.inspection.bean.InspectionSpecCheckBean;
import com.example.new_androidclient.inspection.bean.InspectionTaskAreaResultBean;
import com.example.new_androidclient.inspection.bean.InspectionUploadBean;
import com.example.new_androidclient.inspection.bean.ItemResultDevice;
import com.example.new_androidclient.inspection.bean.TaskAreaItemResult;
import com.example.new_androidclient.login.bean.UserBean;
import com.example.new_androidclient.wait_to_handle.bean.DefaultPicBean;
import com.example.new_androidclient.wait_to_handle.bean.WaitHazardListBean;
import com.example.new_androidclient.wait_to_handle.bean.WaitInspectionListBean;
import com.example.new_androidclient.work.bean.AnalysisBean;
import com.example.new_androidclient.work.bean.CheckMessageBean;
import com.example.new_androidclient.work.bean.CheckOnsiteBean;
import com.example.new_androidclient.work.bean.GasAnalysisBean;
import com.example.new_androidclient.work.bean.HoldBean;
import com.example.new_androidclient.work.bean.HotWorkBean;
import com.example.new_androidclient.work.bean.JSABean;
import com.example.new_androidclient.work.bean.JSANewsBean;
import com.example.new_androidclient.work.bean.PermitCancelBean;
import com.example.new_androidclient.work.bean.SpecialWorkBean;
import com.example.new_androidclient.work.bean.SurveyBean;
import com.example.new_androidclient.work.bean.TreeListBean;
import com.example.new_androidclient.work.bean.WorkApplicationBaseDetailBean;
import com.example.new_androidclient.work.bean.WorkApplicationBean;
import com.example.new_androidclient.work.bean.WorkApplicationInvolveListBean;
import com.example.new_androidclient.work.bean.WorkApplicationTypeBean;
import com.example.new_androidclient.work.bean.WorkBlindPlateBean;
import com.example.new_androidclient.work.bean.WorkCheckOnsite;
import com.example.new_androidclient.work.bean.WorkDHApplicationBean;
import com.example.new_androidclient.work.bean.WorkDHAppointmentBean;
import com.example.new_androidclient.work.bean.WorkDKApplicationBean;
import com.example.new_androidclient.work.bean.WorkDLApplicationBean;
import com.example.new_androidclient.work.bean.WorkDTApplicationBean;
import com.example.new_androidclient.work.bean.WorkDZApplicationBean;
import com.example.new_androidclient.work.bean.WorkGCApplicationBean;
import com.example.new_androidclient.work.bean.WorkIngToDoTwoBean;
import com.example.new_androidclient.work.bean.WorkLDApplicationBean;
import com.example.new_androidclient.work.bean.WorkSXApplicationBean;
import com.example.new_androidclient.work.bean.WorkSignBean;
import com.example.new_androidclient.work.bean.WorkingToDoBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Api {
    // String BASE_URL = "http://39.99.37.28:9080";  //???????????? ???158?????????????????? ?????????
    String Demo_dk = "http://39.99.58.158:9080"; //?????? ??????
    String yun = "http://192.168.20.213:9080";
    String fu = "http://192.168.20.117:9080";
    String gao = "http://192.168.20.90:9080";
    String gao2 = "http://192.168.20.129:9080";
    String local = "http://192.168.20.104:9080";

    //??????
    @POST("/mobile/api/user/login")
    Observable<BaseResponse<UserBean>> Login(@Body RequestBody body);

    @POST("/mobile/api/user/login")
    Call<BaseResponse<UserBean>> Login_token(@Body RequestBody body);

    /**
     * ??????
     */
    //??????????????????
    @GET("/mobile/api/inspectionAndroid/exceptionHandling/list")
    Observable<BaseResponse<List<WaitInspectionListBean>>> findExceptionHandling(@Query("page") int page, @Query("size") int size, @Query("solveFlag") int solveFlay);

    //??????????????? ?????????
    //@POST("/mobile/api/inspectionAndroid/exceptionHandling/allocation")
    //Observable<BaseResponse<String>> allocation(@Body RequestBody body);

    //??????????????? ?????????
    //@GET("/mobile/api/inspectionAndroid/exceptionHandling/remove")
    //Observable<BaseResponse<String>> exceptionRemove(@Query("ids") String ids);

    //??????????????? ?????????
    //@GET("/mobile/api/inspectionAndroid/exceptionHandling/generate")
    //Observable<BaseResponse<String>> generate(@Query("ids") String ids);

    //????????????
    @GET("/mobile/api/inspectionAndroid/exceptionHandling/exType")
    Observable<BaseResponse<String>> exType(@Query("ids") String ids, @Query("type") Integer type);

    //????????????
    @GET("/mobile/api/inspectionAndroid/exceptionHandling/dispose")
    Observable<BaseResponse<String>> dispose(@Query("ids") String ids, @Query("disposeFlag") Integer type);

    //????????????
    @GET("/mobile/api/inspectionAndroid/exceptionHandling/finish")
    Observable<BaseResponse<String>> exceptionHandlingFinish(@Query("ids") String ids);

    //?????????
    @GET("/mobile/api/inspectionAndroid/exceptionHandling/remove")
    Observable<BaseResponse<String>> remove(@Query("ids") String ids);


    //??????????????????
    @GET("/mobile/api/common/minio/find/{businessId}/{businessType}")
    Observable<BaseResponse<List<DefaultPicBean>>> getPicUrl_wait(@Path("businessId") int businessId, @Path("businessType") String businessType);

    /**
     * ??????
     */
    //??????????????????????????????????????????
    @GET("/mobile/api/inspectionAndroid/taskAreaContent")
    Observable<BaseResponse<List<InspectionAreaBean>>> getTaskAreaContent(@Query("taskCode") String taskCode);

    //???????????????????????? lineSort=0
    @GET("/mobile/api/inspectionAndroid/{taskId}/taskAreaContent/{areaId}/taskDevice?page=1&size=1000")
    Observable<BaseResponse<List<InspectionDeviceListBean>>> getTaskDeviceList(@Path("taskId") int taskId, @Path("areaId") int areaId);

    //????????????????????????(????????????id?????????????????????) lineSort=1
    @GET("/mobile/api/inspectionAndroid/{taskId}/taskAreaContent/{areaId}/areaItem")
    Observable<BaseResponse<List<TaskAreaItemResult>>> findAreaItemListByTaskId(@Path("taskId") int taskId, @Path("areaId") int areaId);

    //???????????????????????????????????????
    @PATCH("/mobile/api/inspectionAndroid/{taskId}/taskAreaContent/{areaId}/areaItem")
    Observable<BaseResponse<String>> saveTaskAreaItemResult(@Path("taskId") int taskId,
                                                            @Path("areaId") int areaId,
                                                            @Body List<TaskAreaItemResult> list);

    //?????????????????????
    @GET("/mobile/api/inspectionAndroid/{taskId}/taskAreaContent/{areaId}/taskDevice/{deviceId}/spec")
    Observable<BaseResponse<List<InspectionDeviceDetailBean>>> getDeviceSpecList(@Path("taskId") int taskId, @Path("areaId") int areaId, @Path("deviceId") int deviceId);

    //??????????????????????????????????????????
    @POST("/mobile/api/inspectionAndroid/taskDeviceSpec/{id}/match/{specNumber}")
    Observable<BaseResponse<InspectionSpecCheckBean>> match(@Path("id") int id, @Path("specNumber") double specNumber);

    //??????????????????
    @PATCH("/mobile/api/inspectionAndroid/{taskDeviceId}/taskDeviceSpec/{deviceStatus}")
    Observable<BaseResponse<String>> submitDeviceData(@Path("taskDeviceId") int taskDeviceId, @Path("deviceStatus") int deviceStatus, @Body List<InspectionUploadBean> list);

    //????????????
    @Multipart
    @POST("/mobile/api/common/minio/upload")
    Observable<BaseResponse<String>> uploadFile(@Part("businessId") RequestBody businessId, @Part("businessType") RequestBody businessType, @Part MultipartBody.Part files);


    //??????????????????
    @GET("/mobile/api/inspectionAndroid/randomInspection")
    Observable<BaseResponse<List<InspectionAreaBean>>> randomInspection(@Query("areaCode") String areaCode);

    //????????????
    @GET("/mobile/api/inspectionAndroid/{taskId}/shopTask")
    Observable<BaseResponse<BaseResponse>> stopTask(@Path("taskId") int taskId, @Query("reason") String reason);

    //24??????????????????
    @GET("/mobile/api/inspectionAndroid/taskDevice?page=1&size=1000")
    Observable<BaseResponse<List<Inspection24Bean>>> get24DeviceList();

    //24??????????????????
    @GET("/mobile/api/inspectionAndroid/taskDeviceSpec")
    Observable<BaseResponse<List<Inspection24DetailBean>>> get24DeviceDetail(@Query("areaId") int areaId, @Query("deviceId") int deviceId, @Query("taskId") int taskId);

    //????????????????????????
    @PATCH("/mobile/api/inspectionAndroid/{id}/taskAreaResult/{resultContent}")
    Observable<BaseResponse<BaseResponse>> taskAreaResult(@Path("id") int id, @Path("resultContent") String resultContent, @Body List<InspectionTaskAreaResultBean> list);

    //????????????-???????????? ????????????????????????
    @GET("/mobile/api/inspectionAndroid/itemResultDevice")
    Observable<BaseResponse<List<ItemResultDevice>>> findItemResultDevice_rough(@Query("itemResultId") int itemResultId);

    //????????????-???????????? ????????????????????????
    @GET("/mobile/api/inspectionAndroid/areaResultDevice")
    Observable<BaseResponse<List<AreaResultDevice>>> findItemResultDevice_area(@Query("areaResultId") int areaResultId);

    //????????????-???????????? ????????????????????????
    @GET("/mobile/api/inspectionAndroid/itemResultDevice/device")
    Observable<BaseResponse<List<InspectionDeviceRoughSelectDeviceListBean>>> findAreaDevice(@Query("page") int page, @Query("size") int size, @Query("areaId") int areaId, @Query("deviceName") String deviceName, @Query("tagNo") String tagNo);

    //????????????-???????????? ??????????????????
    @POST("/mobile/api/inspectionAndroid/itemResultDevice/{itemResultId}")
    Observable<BaseResponse<String>> instItemResultDevice_rough(@Path("itemResultId") int itemResultId, @Query("deviceIds") String deviceIds);

    //????????????-???????????? ??????????????????
    @POST("/mobile/api/inspectionAndroid/areaResultDevice/{areaResultId}")
    Observable<BaseResponse<String>> instItemResultDevice_area(@Path("areaResultId") int areaResultId, @Query("deviceIds") String deviceIds);

    //??????????????????
    @GET("/mobile/api/common/minio/find/{businessId}/{businessType}")
    Observable<BaseResponse<List<HazardPicBean>>> getPicUrl_inspection(@Path("businessId") int businessId, @Path("businessType") String businessType);

    //????????????-???????????? ??????????????????
    @PATCH("/mobile/api/inspectionAndroid/itemResultDevice")
    Observable<BaseResponse<String>> updItemResultDevice_rough(@Body List<ItemResultDevice> bean);

    //????????????-???????????? ??????????????????
    @PATCH("/mobile/api/inspectionAndroid/areaResultDevice")
    Observable<BaseResponse<String>> updItemResultDevice_area(@Body List<AreaResultDevice> bean);

    //????????????-???????????? ????????????????????????
    @PATCH("/mobile/api/inspectionAndroid/{id}/taskAreaResult")
    Observable<BaseResponse<BaseResponse>> updItemResultDevice(@Path("id") int id, @Query("resultContent") String resultContent, @Query("resultType") String resultType);


    /**
     * ??????
     */
    //??????????????????
    @GET("/mobile/api/hazard/selectHazardPlanRecord")
    Observable<BaseResponse<List<HazardListBean>>> getHazardList();

    //????????????????????????
    @GET("/mobile/api/hazard/selectHazardPlanRecordById")
    Observable<BaseResponse<List<HazardDetailBean>>> getHazardDetailById(@Query("planId") int planId);

    //????????????????????????
    @POST("/mobile/api/hazard/updHazardPlanContent")
    Observable<BaseResponse<String>> updHazardPlanContent(@Body RequestBody body);

    //????????????????????????
    @POST("/mobile/api/hazard/updhazardPlanRecordStatus")
    Observable<BaseResponse<String>> submitHazardPlan(@Query("planId") int planId);

    //?????????????????? ?????????????????????????????????
    @GET("/mobile/api/hazard/getHiRectifyPlanByplanId")
    Observable<BaseResponse<List<HazardVerificationDetailBean>>> getVerHazardDetailById_a(@Query("planId") int planId);

    //?????????????????? ???????????????????????????????????????????????????bean
    @GET("/mobile/api/hazard/getHiRectifyPlanByplanId")
    Observable<BaseResponse<List<HazardVerificationDetailBean>>> getVerHazardDetailById_b(@Query("rectifyPlanId") int rectifyPlanId);

    //??????????????????
    @POST("/mobile/api/hazard/updHazardRectifyPlan")
    Observable<BaseResponse<String>> updHazardRectifyPlan(@Body RequestBody body);

    //??????????????????
    @POST("/mobile/api/hazard/hiRectifyPlanCheckAndAcceptSubmit")
    Observable<BaseResponse<String>> hiRectifyPlanCheckAndAcceptSubmit(@Query("id") int id);

    //????????????????????????
    @POST("/mobile/api/hazard/hiRectifyPlanCheckAndAcceptRe2Rectify2Act")
    Observable<BaseResponse<String>> hiRectifyPlanCheckAndAcceptRe2Rectify2Act(@Query("id") int id, @Query("flag") int flag, @Query("msg") String msg);

    //????????????????????????
    @GET("/mobile/api/hazard/selectHazardEditor")
    Observable<BaseResponse<List<HazardPlanListBean>>> selectHazardEditor(@Query("planId") int planId);

    //?????????????????? + ???????????? ??????
    @PATCH("/mobile/api/hazard/updateRectification")
    Observable<BaseResponse<String>> updateRectification(@Body RequestBody body);

    //?????????????????? ??????
    @POST("/mobile/api/hazard/hiRectifyPlanReadAndEditStartAct/{id}")
    Observable<BaseResponse<String>> rectifyPlanReadAndEditStartAct(@Path("id") int id);

    //?????????????????? ?????? flag  1??????  0??????
    @POST("/mobile/api/hazard/hiRectifyPlanAuditAct")
    Observable<BaseResponse<String>> hiRectifyPlanAuditAct(@Query("id") int id, @Query("flag") int flag, @Query("msg") String msg);

    //???????????????????????? ?????? flag  1??????  0??????
    @POST("/mobile/api/hazard/analysisAct")
    Observable<BaseResponse<String>> analysisAct(@Query("planId") int planId, @Query("ideaFlag") int flag, @Query("message") String msg);

    //????????????????????????
    @GET("/mobile/api/hazard/selectHazardGovernment")
    Observable<BaseResponse<List<HazardGovernmentListBean>>> selectHazardGovernment(@Query("planId") int planId);


    //??????????????????
    @GET("/mobile/api/common/minio/find/{businessId}/{businessType}")
    Observable<BaseResponse<List<HazardPicBean>>> getPicUrl(@Path("businessId") int businessId, @Path("businessType") String businessType);

    //?????????????????????????????????
    @GET("/mobile/api/hazard/selectNoticeList")
    Observable<BaseResponse<List<HazardNotificationSignListBean>>> selectNoticeList(@Query("planId") int planId);

    //?????????????????????????????????
    @GET("/mobile/api/hazard/selectNoticeInfo")
    Observable<BaseResponse<HazardNotificationSignInfoBean>> selectNoticeDetail(@Query("planId") int planId);

    //???????????????????????????
    @PATCH("/mobile/api/hazard/updateNoticeSign")
    Observable<BaseResponse<String>> updateNoticeSign(@Body RequestBody body);

    //???????????????????????????????????????????????????
    @PATCH("/mobile/api/hazard/updateStatus")
    Observable<BaseResponse<String>> updateStatus(@Query("planId") int planId, @Query("status") String status);

    //??????????????????
    @GET("/mobile/api/hazard/selectAnalysisList")
    Observable<BaseResponse<List<HazardAnalysisBean>>> selectAnalysisList(@Query("planId") int planId);

    //??????????????????
    @GET("/mobile/api/hazard/majorHiddenDanger")
    Observable<BaseResponse<List<HazardMajorHiddenDangerListBean>>> majorHiddenDanger(@Query("planId") int planId);

    //???????????? HazardAnalysisActivity??????
    @PATCH("/mobile/api/hazard/updateAnalysis")
    Observable<BaseResponse<String>> updateAnalysis(@Body RequestBody body);

    //????????????  HazardAnalysisNewActivity??????
    @PATCH("/mobile/api/hazard/updateAnalysis")
    Observable<BaseResponse<String>> updateAnalysis(@Body HazardAnalysisUploadBean body);

    //???????????? HazardAnalysisNewActivity??????
    @POST("/mobile/api/hazard/analysisSubmit/{id}")
    Observable<BaseResponse<String>> updateAnalysis(@Path("id") Integer id);

    //????????????
    @GET("/mobile/api/deviceAndroid/list")
    Observable<BaseResponse<List<HazardAnalysisDeviceListBean>>> findDeviceList(@Query("page") int page, @Query("size") int size, @Query("deviceName") String deviceName, @Query("tagNo") String tagNo);

    //???????????????
    @GET("/mobile/api/user/tenant/find")
    Observable<BaseResponse<List<HazardAnalysisPersonBean>>> getAllUserList();

    //????????????
    @GET("/mobile/api/hazard/tree/get")
    Observable<BaseResponse<HazardAnalysisTreeBean>> getTreeList();

    //????????????????????????
    @GET("/mobile/api/hazard/user/tasks")
    Observable<BaseResponse<List<WaitHazardListBean>>> getUserTasks();

    //????????????????????????????????????
    @GET("/mobile/api/hazard/selectInspectPlan")
    Observable<BaseResponse<List<HazardTablePlanListBean>>> selectInspectPlan();

    //????????????????????????????????????
    @GET("/mobile/api/hazard/selectInspectPlanInfo")
    Observable<BaseResponse<List<HazardTablePlanDetailBean>>> selectInspectPlanInfo(@Query("planId") int planId);

    //????????????
    @POST("/mobile/api/hazard/processing")
    Observable<BaseResponse<String>> processing(@Query("planId") int planId, @Query("ideaFlag") int ideaFlag, @Query("message") String message);


    /**
     * ??????
     */
    //????????????
    @GET("/mobile/api/deviceAndroid/list")
    Observable<BaseResponse<List<HazardAnalysisDeviceListBean>>> obtainDeviceList(@Query("page") int page, @Query("size") int size);

    //????????????????????????
    @GET("/mobile/api/devicePlan/devicePlanList")
    Observable<BaseResponse<List<DevicePlanListBean>>> findDevicePlanList();

    //??????????????????
    @GET("/mobile/api/devicePlan/selectTemporaryPlanInfo")
    Observable<BaseResponse<InterimPlanDetailsBean>> selectTemporaryPlanInfo(@Query("temporaryId") int temporaryId);

    //????????????
    @PATCH("/mobile/api/devicePlan/updatePlanStatus")
    Observable<BaseResponse<String>> updatePlanStatus(@Query("temporaryId") int temporaryId, @Query("rejectReason") String rejectReason);

    //??????????????????
    @GET("/mobile/api/user/tenant/find")
    Observable<BaseResponse<List<DeviceAnalysisDeviceListBean>>> getUserList();

    //??????????????????
    @GET("/mobile/api/contractor/list")
    Observable<BaseResponse<List<ContractorListBean>>> getContractorList();

    //??????????????????
    @PATCH("/mobile/api/devicePlan/updatePlan")
    Observable<BaseResponse<String>> upDatePlan(@Body UpDataPlanBean bean);

    //???????????? ??????
    @POST("/mobile/api/devicePlan/addWork")
    Observable<BaseResponse<String>> addWork(@Query("temporaryId") int temporaryId, @Query("dptId") int dptId);

    //????????????
    @GET("/mobile/api/user/selectAppStore")
    Observable<BaseResponse<EditionBean>> selectAppStore(@Query("platform") String platform, @Query("aid") String aid);

    //????????????

    //????????????????????????
    @GET("/mobile/api/deviceAndroid/selectOrganizationTree")
    Observable<BaseResponse<List<SearchBean>>> selectOrganizationTree();

    //???????????????????????????
    @GET("/mobile/api/deviceAndroid/type/{type}")
    Observable<BaseResponse<List<CategoryBean>>> getDictByType(@Path("type") String type);

    @GET("/mobile/api/deviceAndroid/list")
    Observable<BaseResponse<List<FindDeviceListBean>>> getFindDeviceList(@Query("page") int page, @Query("size") int size,
                                                                         @Query("deviceName") String deviceName,
                                                                         @Query("tagNo") String tagNo, @Query("deviceNo") String deviceNo,
                                                                         @Query("factoryId") Integer factoryId,
                                                                         @Query("dptId") Integer dptId, @Query("instId") Integer instId,
                                                                         @Query("professionalCat") String professionalCat,
                                                                         @Query("deviceCatExt") String deviceCatExt,
                                                                         @Query("areaName") String areaName,
                                                                         @Query("instName") String instName);

    //????????????
    @GET("/mobile/api/deviceAndroid/selectDeviceInfo")
    Observable<BaseResponse<BasicBean>> selectDeviceInfo(@Query("devId") int devId);

    //?????????
    @GET("/mobile/api/deviceAndroid/selectSealPoint")
    Observable<BaseResponse<List<SealPointBean>>> selectSealPoint(@Query("devId") int devId);

    //??????
    @GET("/mobile/api/deviceAndroid/selectDeviceSpec")
    Observable<BaseResponse<DeviceSpecBean>> selectDeviceSpec(@Query("deviceCode") String deviceCode);

    //????????????
    @GET("/mobile/api/deviceAndroid/selectDeviceAffiliated")
    Observable<BaseResponse<List<FacilityBean>>> selectDeviceAffiliated(@Query("devId") int devId);

    //????????????
    @GET("/mobile/api/deviceAndroid/selectDeviceAssociated")
    Observable<BaseResponse<List<FacilityBean>>> selectDeviceAssociated(@Query("devId") int devId);

    /**
     * ????????????
     **/
    @GET("/mobile/api/working/list")
    Observable<BaseResponse<List<WorkingToDoBean>>> selectWorkingToDo(@Query("status") Integer status);//??????????????????

    @GET("/mobile/api/working/list")
    Observable<BaseResponse<List<WorkIngToDoTwoBean>>> selectWorkingToDoTwo(@Query("status") Integer status);//??????????????????

    @GET("/mobile/api/working/total")
    Observable<BaseResponse<Map<String, Integer>>> selectTotal();//??????????????????

    @GET("/mobile/api/working/survey")
    Observable<BaseResponse<SurveyBean>> selectSurvey(@Query("planWorkId") int planWorkId);//????????????

    @GET("/mobile/api/working/selectWorkingType")
    Observable<BaseResponse<List<SpecialWorkBean>>> selectSpecialWorking();//???????????????????????????

    //????????????????????????  zq
    @GET("/mobile/api/working/selectApplication")
    Observable<BaseResponse<WorkApplicationBaseDetailBean>> selectApplication(@Query("applicationId") int applicationId);

    //?????????????????????????????????
    @GET("/mobile/api/working/selectWorkingType")
    Observable<BaseResponse<List<WorkApplicationTypeBean>>> selectWorkingType();

    //????????????????????????  zq
    @POST("/mobile/api/working/addApplication")
    Observable<BaseResponse<String>> addApplication(@Body WorkApplicationBean bean);

    //????????????????????????  zq
    @POST("/mobile/api/working/addApplicationDh")
    Observable<BaseResponse<String>> addApplicationDh(@Body WorkDHApplicationBean bean);

    //????????????????????????  zq
    @POST("/mobile/api/working/addApplicationGc")
    Observable<BaseResponse<String>> addApplicationGc(@Body WorkGCApplicationBean bean);

    //????????????????????????  zq
    @POST("/mobile/api/working/addApplicationDz")
    Observable<BaseResponse<String>> addApplicationDz(@Body WorkDZApplicationBean bean);

    //????????????????????????  zq
    @POST("/mobile/api/working/addApplicationDl")
    Observable<BaseResponse<String>> addApplicationDl(@Body WorkDLApplicationBean bean);

    //????????????????????????  zq
    @POST("/mobile/api/working/addApplicationSx")
    Observable<BaseResponse<String>> addApplicationSx(@Body WorkSXApplicationBean bean);

    //??????????????????????????????  zq
    @POST("/mobile/api/working/addApplicationDk")
    Observable<BaseResponse<String>> addApplicationDk(@Body WorkDKApplicationBean bean);

    //??????????????????????????????  zq
    @POST("/mobile/api/working/addApplicationLd")
    Observable<BaseResponse<String>> addApplicationLd(@Body WorkLDApplicationBean bean);

    //????????????????????????  zq
    @POST("/mobile/api/working/addApplicationDt")
    Observable<BaseResponse<String>> addApplicationDt(@Body WorkDTApplicationBean bean);

    //????????????????????????????????????  zq
    @POST("/mobile/api/working/addCheckOnsite")
    Observable<BaseResponse<String>> addCheckOnsite(@Body List<WorkCheckOnsite> bean);

    //?????????????????? zq
    @GET("/mobile/api/working/selectApplicationDh")
    Observable<BaseResponse<WorkDHApplicationBean>> selectApplicationDh(@Query("applicationId") int applicationId);

    //?????????????????? zq
    @GET("/mobile/api/working/selectGcByApplicationId")
    Observable<BaseResponse<WorkGCApplicationBean>> selectGcByApplicationId(@Query("applicationId") int applicationId);

    //?????????????????? zq
    @GET("/mobile/api/working/selectDkByApplicationId")
    Observable<BaseResponse<WorkDKApplicationBean>> selectDkByApplicationId(@Query("applicationId") int applicationId);

    //???????????????????????? zq
    @GET("/mobile/api/working/selectLdByApplicationId")
    Observable<BaseResponse<WorkLDApplicationBean>> selectLdByApplicationId(@Query("applicationId") int applicationId);

    //?????????????????? zq
    @GET("/mobile/api/working/selectDzByApplicationId")
    Observable<BaseResponse<WorkDZApplicationBean>> selectDzByApplicationId(@Query("applicationId") int applicationId);

    //?????????????????? zq
    @GET("/mobile/api/working/selectDlByApplicationId")
    Observable<BaseResponse<WorkDLApplicationBean>> selectDlByApplicationId(@Query("applicationId") int applicationId);

    //?????????????????? zq
    @GET("/mobile/api/working/selectDtByApplicationId")
    Observable<BaseResponse<WorkDTApplicationBean>> selectDtByApplicationId(@Query("applicationId") int applicationId);

    //?????????????????? zq
    @GET("/mobile/api/working/selectSxByApplicationId")
    Observable<BaseResponse<WorkSXApplicationBean>> selectSxByApplicationId(@Query("applicationId") int applicationId);

    //?????????????????? zq
    @GET("/mobile/api/working/selectCheckOnsiteConfig")
    Observable<BaseResponse<List<WorkApplicationInvolveListBean>>> selectCheckOnsiteConfig(@Query("applicationId") int applicationId);

    //??????Application?????? zq
    @POST("/mobile/api/working/updateApplicationStatus")
    Observable<BaseResponse<String>> updateApplicationStatus(@Query("id") int id, @Query("status") String status);

    //?????????????????? ?????? ?????? ?????? zq ??????????????????msg??????????????????????????????????????????
    @POST("/mobile/api/working/audit")
    Observable<BaseResponse<String>> audit(@Query("applicationId") int applicationId, @Query("status") String status, @Query("msg") String msg);

    //???????????? ?????????????????? zq
    @POST("/mobile/api/working/updAppointment")
    Observable<BaseResponse<String>> updAppointment(@Body List<WorkDHAppointmentBean> list, @Query("applicationId") int applicationId);

    //??????????????????-????????????????????? zq
    @GET("/mobile/api/working/selectAppointment")
    Observable<BaseResponse<List<WorkDHAppointmentBean>>> selectAppointment(@Query("workType") String workType, @Query("applicationId") int applicationId);

    //????????????????????????????????????
    @GET("/mobile/api/working/selectRiskList")
    Observable<BaseResponse<String>> selectRiskList(@Query("jsaId") Integer jsaId, @Query("planWorkId") Integer planWorkId, @Query("code") String code);

    //??????????????????
    @POST("/mobile/api/working/addSurvey")
    Observable<BaseResponse<Integer>> addSurvey(@Body HoldBean holdBean);

    //???????????????????????????????????????
    @GET("/mobile/api/working/getTreeList")
    Observable<BaseResponse<List<TreeListBean>>> getWorkTreeList();

    //?????????????????????????????????
    @POST("/mobile/api/working/addAnalysisContent")
    Observable<BaseResponse<String>> addAnalysisContent(@Query("planWorkId") int planWorkId, @Body List<Integer> ids);

    //??????JSA
    @POST("/mobile/api/working/addJsa")
    Observable<BaseResponse<Integer>> addJsa(@Body JSABean jsaBean);

    //JSA????????????
    @GET("/mobile/api/working/selectJsa")
    Observable<BaseResponse<JSANewsBean>> selectJsa(@Query("planWorkId") int planWorkId);

    //?????????????????????
    @POST("/mobile/api/working/updateSheetStatus")
    Observable<BaseResponse<String>> updateSheetStatus(@Query("planWorkId") int planWorkId, @Query("status") String status, @Query("applicationId") int applicationId);

    //????????????????????????
    @GET("/mobile/api/working/selectWorkingCheckOnsite")
    Observable<BaseResponse<List<CheckMessageBean>>> selectWorkingCheckOnsite(@Query("applicationId") int applicationId);

    //???????????????
    @POST("/mobile/api/working/saveWorkingCheckOnsite")
    Observable<BaseResponse<String>> saveWorkingCheckOnsite(@Body List<CheckOnsiteBean> list);

    //????????????
    @GET("/mobile/api/working/selectWorkingDhAnalysis")
    Observable<BaseResponse<List<HotWorkBean>>> selectWorkingDhAnalysis(@Query("applicationId") int applicationId);

    //??????????????????
    @POST("/mobile/api/working/addDhAnalysis")
    Observable<BaseResponse<String>> addDhAnalysis(@Body List<HotWorkBean> bean);

    //??????????????????
    @DELETE("/mobile/api/working/delDhAnalysis")
    Observable<BaseResponse<String>> delDhAnalysis(@Body List<Integer> bean);

    //??????????????????
    @POST("/mobile/api/working/updApplicationTime")
    Observable<BaseResponse<String>> updApplicationTime(@Query("applicationId") int applicationId, @Query("status") String status);

    //????????????
    @GET("/mobile/api/working/selectBlindPlate")
    Observable<BaseResponse<List<GasAnalysisBean>>> selectWorkingGasDetection(@Query("applicationId") int applicationId);


    //??????DH????????????
    @POST("/mobile/api/working/cancelReason")
    Observable<BaseResponse<String>> cancelReason(@Query("cancelReason") String cancelReason, @Query("applicationId") int applicationId,
                                                  @Query("code") String code, @Query("confirmItem") String confirmItem);

    //?????? ??????
    @POST("/mobile/api/working/updateProcessInspectStatus")
    Observable<BaseResponse<String>> updateProcessInspectStatus(@Query("status") String status, @Query("applicationId") int applicationId,
                                                                @Query("code") String code);

    //??????????????????????????????
    @GET("/mobile/api/working/getWorkingCancelReansonConfig")
    Observable<BaseResponse<List<PermitCancelBean>>> getWorkingPermitCancel();

    //????????????????????????????????? zq
    @GET("/mobile/api/working/selectBlindPlate")
    Observable<BaseResponse<List<WorkBlindPlateBean>>> selectBlindPlate(@Query("applicationId") int applicationId, @Query("workingType") String workingType);

    //??????????????????--?????? zq
    @GET("/mobile/api/common/minio/find/{businessId}/{businessType}")
    Observable<BaseResponse<List<DefaultPicBean>>> findDocListByBusinessIdAndType(@Path("businessId") int businessId, @Path("businessType") String businessType, @Query("fileTypeList") String fileTypeList);

    //????????????????????? ??????
    @POST("/mobile/api/working/preservationBlindPlate")
    Observable<BaseResponse<String>> preservationBlindPlate(@Body WorkBlindPlateBean bean);

    //???????????? zq
    @Multipart
    @POST("/mobile/api/common/minio/upload")
    Observable<BaseResponse<String>> uploadFile_work(@Part("businessId") RequestBody businessId, @Part("userId") RequestBody userId, @Part("businessType") RequestBody businessType, @Part("fileTypeList") RequestBody fileTypeList, @Part MultipartBody.Part files);

    //?????????5???????????? zq
    @POST("/mobile/api/working/signatureWork")
    Observable<BaseResponse<String>> signatureWork(@Query("type") String type, @Query("id") int id, @Query("personnel") String personnel, @Query("smCheck") int smCheck);

    //?????????5?????????????????? zq
    @POST("/mobile/api/working/selectSignatureWork")
    Observable<BaseResponse<WorkSignBean>> selectSignatureWork(@Query("type") String type, @Query("id") int id);

//    //?????????5???????????? zq
//    @POST("/mobile/api/working/act/submit/{id}/type/{type}")
//    Observable<BaseResponse<WorkSignBean>> actSubmit(@Path("id") int id, @Path("type") String type);

    //??????????????????????????? zq
    @POST("/mobile/api/working/act/audit/submit/{id}/type/{type}")
    Observable<BaseResponse<String>> actSubmit(@Path("id") int id, @Path("type") String type, @Query("flag") int flag, @Query("msg") String msg);

    //???????????????????????? zq
    @POST("/mobile/api/working/appointmentUpdate")
    Observable<BaseResponse<String>> appointmentUpdate(@Path("applicationId") int applicationId, @Path("energyIsolation") String energyIsolation, @Query("isUpgradeMgt") String isUpgradeMgt);

    //?????????????????????????????????
    @GET("/mobile/api/working/getChemicalMediumList")
    Observable<BaseResponse<List<AnalysisBean>>> getChemicalMediumList();

    //????????????  ??????????????????
    @POST("/mobile/api/working/addApplicationDh")
    Observable<BaseResponse<String>> addApplicationDhAnalysis(@Body WorkDHApplicationBean bean, @Query("mediumIdList") String mediumIdList);

    //?????????????????????
    @GET("/mobile/api/working/countersignApprove/users")
    Observable<BaseResponse<Map<String, String>>> getCountersignApproveUsers(@Query("applicationId") Integer applicationId, @Query("workingType") String workingType);

    //?????????????????????
    @POST("/mobile/api/working/countersignApprove")
    Observable<BaseResponse<String>> getCountersignApprove(@Query("applicationId") Integer applicationId, @Query("workingType") String workingType,
                                                           @Query("processRole") String processRole, @Query("processUser") Integer processUser,
                                                           @Query("processOpinion") String processOpinion, @Query("processResult") String processResult);
}
