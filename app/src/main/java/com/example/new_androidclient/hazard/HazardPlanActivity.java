package com.example.new_androidclient.hazard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.Constants;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.BeanToMap;
import com.example.new_androidclient.Util.DataConverterUtil;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.HazardItemLayout;
import com.example.new_androidclient.databinding.ActivityHazardPlanBinding;
import com.example.new_androidclient.hazard.bean.HazardPlanListBean;
import com.example.new_androidclient.hazard.bean.HazardPlanUploadFileBean;

import org.jaaksi.pickerview.picker.TimePicker;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.example.new_androidclient.Other.SPString.HazardPlanUploadFileList;
import static org.jaaksi.pickerview.picker.TimePicker.TYPE_DATE;

/**
 * 填写整改计划
 */
@Route(path = RouteString.HazardPlanActivity)
public class HazardPlanActivity extends BaseActivity {
    private ActivityHazardPlanBinding binding;
    private Listener listener = new Listener();
    private List<HazardPlanListBean> list = new ArrayList<>();
    private List<HazardItemLayout> itemLayoutList = new ArrayList<>();
    private HazardPlanListBean uploadBean = new HazardPlanListBean();
    private Map<String, String> resultMap = new HashMap<>();
    private int Intent_code_person = 2; //和隐患分析共用同一个分析人，所以是2

    private int currentPosition;  //从列表页跳转进本页面时，当前页面里，显示第current条数据
    private int pre = 0;  //例如当前pre=current=5，往前翻一页， pre=4， 往后翻一页，pre=5
    private String UP = "up";//用户点击上一条
    private String DOWN = "down"; //用户点击下一条
    private String FINISH = "finish"; //用户点击完成巡检
    private String turnPage = DOWN; //用户点击了上一条按钮还是下一条按钮

    private int picUploadSuccessNum;
    private List<HazardPlanUploadFileBean> fileList = new ArrayList<>();
    private Map<Integer, HazardPlanUploadFileBean> fileMap = new HashMap<>();

    String[] nameList = {
            "隐患名称",//0
            "隐患描述",
            "隐患类别", //2
            "一般隐患等级",//3
            "防范措施",
            "整改治理方案",//5
            "资金来源是否是专款专用",
            "隐患排查日期",//7
            "限期整改日期",
            "不能完成原因",//9
            "整改负责人",
            "整改完成时间"//11
    };
    int[] typeList = {1, 1, 1, 1, 3, 3, 2, 1, 1, 3, 2, 2};

    @Autowired()
    int planId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hazard_plan);
        binding.setListener(listener);
        binding.title.getLinearLayout_hazard_plan_upload().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ARouter.getInstance().build(RouteString.HazardPlanUploadFileActivity)
//                        .navigation(HazardPlanActivity.this, 3);
                Intent intent = new Intent(HazardPlanActivity.this, HazardPlanUploadFileActivity.class);
                Bundle bundle = new Bundle();
                if (fileMap.size() > 0) {
                    fileList.clear();
                    for (Map.Entry<Integer, HazardPlanUploadFileBean> en : fileMap.entrySet()) {
                        //   System.out.println(en.getValue());
                        fileList.add(en.getValue());
                    }
                    bundle.putSerializable("picList", (Serializable) fileList);
                    intent.putExtras(bundle);
                    intent.putExtra("hasPic", true);
                } else {
                    intent.putExtra("hasPic", false);
                }
                startActivityForResult(intent, 3);
            }
        });
        getList();
    }

    private void getList() {
        RetrofitUtil.getApi().selectHazardEditor(planId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardPlanListBean>>(mContext, true, Constants.Loading) {
                    @Override
                    public void onSuccess(List<HazardPlanListBean> bean) {
                        if (bean.size() > 0) {
                            list.clear();
                            list.addAll(bean);
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getRectificationPlan() == null ||
                                        list.get(i).getRectificationPlan().isEmpty()) {
                                    currentPosition = pre = i;
                                    break;
                                }
                                if (i == list.size() - 1) {
                                    currentPosition = pre = i;
                                }
                            }
                            initList(pre);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void initList(int pre) {
        initView(pre);

        uploadBean.setId(list.get(pre).getId());

        String num = "(" + (pre + 1) + "/" + list.size() + ")";
        binding.title.setName("整改计划" + num);

        HazardPlanListBean currentBean = checkNull(list.get(pre));
        for (int i = 0; i < nameList.length; i++) {
            addView(i, typeList[i], currentBean);
        }
    }

    private void initView(int pre) {
        //假设列表有5条数据，下标是0至4
        if (list.size() == 1) {
            binding.pre.setVisibility(View.GONE);
            binding.next.setVisibility(View.GONE);
            binding.submit.setVisibility(View.VISIBLE);
        } else if (list.size() > 1) {
            if (pre == currentPosition && currentPosition == 0) {   //cur和pre都是0的时候，只显示下按钮
                binding.pre.setVisibility(View.GONE);
                binding.next.setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.GONE);
            } else if (pre < currentPosition && pre == 0) {  //pre小于cur并且页面显示在列表第一项时，没有上按钮
                binding.pre.setVisibility(View.GONE);
                binding.next.setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.GONE);
            } else if (pre < currentPosition && pre > 0 && pre != list.size() - 1) { //pre处于0和cur之间并且不是列表最后一条时，上下按钮都显示
                binding.pre.setVisibility(View.VISIBLE);
                binding.next.setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.GONE);
            } else if (pre == currentPosition && currentPosition == list.size() - 1) { //最后一条数据，显示提交按钮
                binding.pre.setVisibility(View.VISIBLE);
                binding.next.setVisibility(View.GONE);
                binding.submit.setVisibility(View.VISIBLE);
            } else if (pre == currentPosition) { //显示要检查的内容时，上下都有
                binding.pre.setVisibility(View.VISIBLE);
                binding.next.setVisibility(View.VISIBLE);
            }
        }
    }

    private void addView(int position, int type, HazardPlanListBean currentBean) {
        HazardItemLayout layout = new HazardItemLayout(mContext, position);
        layout.init(type);
        layout.setHazardName_one(nameList[position]);
        if (position == 0) {
            layout.setHazardName_one_text(currentBean.getHazardName());
        } else if (position == 1) {
            layout.setHazardName_one_text(currentBean.getHazardContent());
        } else if (position == 2) {
            layout.setHazardName_one_text(currentBean.getHazardCategory());
        } else if (position == 3) {
            layout.setHazardName_one_text(currentBean.getHazardGrade());
        } else if (position == 4) {
            layout.setHazardName_one_edit(currentBean.getRectificationPlan());
        } else if (position == 5) {
            layout.setHazardName_one_edit(currentBean.getRectificationPlanDoc());
        } else if (position == 6) {
            layout.setHazardName_one_text(currentBean.getFundingName());
        } else if (position == 7) {
            layout.setHazardName_one_text(currentBean.getInvestigationTime().substring(0, 10));
        } else if (position == 8) {
            if (currentBean.getLimitRectifyTime().isEmpty()) {
                layout.setHazardName_one_text("");
            } else {
                layout.setHazardName_one_text(currentBean.getLimitRectifyTime().substring(0, 10));
            }
        } else if (position == 9) {
            layout.setHazardName_one_edit(currentBean.getUnfinishedReason());
        } else if (position == 10) {
            layout.setHazardName_one_text(currentBean.getRectificationChargePersonName());
        } else if (position == 11) {
            String time = currentBean.getPlanFinishedDate();
            if (currentBean.getPlanFinishedDate() != null && !currentBean.getPlanFinishedDate().isEmpty()) {
                time = currentBean.getPlanFinishedDate().substring(0, 10);
            }
            layout.setHazardName_one_text(time);
        }

        layout.setOnClickListener(v -> {
            switch (layout.getPos()) {
                case 6:
                    showPopView(R.menu.hazard_analysis_report, v, layout.getPos()); //资金来源
                    break;
                case 10:
                    ARouter.getInstance().build(RouteString.HazardAnalysisPersonSelectActivity)
                            .navigation(HazardPlanActivity.this, Intent_code_person); //分析负责人单位
                    break;
                case 11:
                    timePicker();
                    break;
            }
        });

        binding.hazardPlanLinear.addView(layout);
        itemLayoutList.add(layout);
    }

    private void showPopView(int menuRes, View v, int position) {
        PopupMenu popupMenu = new PopupMenu(HazardPlanActivity.this, v, Gravity.RIGHT);
        popupMenu.getMenuInflater().inflate(menuRes, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.hazard_analysis_report_true:
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_true));
                    uploadBean.setFunding("1");
                    break;
                case R.id.hazard_analysis_report_false:
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_false));
                    uploadBean.setFunding("0");
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Intent_code_person) {
            String userId = data.getStringExtra("userId");
            String name = data.getStringExtra("name");
            uploadBean.setRectificationChargePerson(userId);
            itemLayoutList.get(10).setHazardName_one_text(name);
        }
        if (resultCode == 3) {
            List<HazardPlanUploadFileBean> list = SPUtil.getListData(HazardPlanUploadFileList, HazardPlanUploadFileBean.class);
            for (int i = 0; i < list.size(); i++) {
                fileMap.put(i, list.get(i));
            }
            //  fileList = SPUtil.getListData(HazardPlanUploadFileList, HazardPlanUploadFileBean.class);
        }
    }

    //检查当前页是否已完成
    private HazardPlanListBean checkNull(HazardPlanListBean bean) {
        if (bean.getRectificationPlan() == null) {
            // String str = "请填写";
            bean.setRectificationPlan("");
            bean.setRectificationPlanDoc("");
            bean.setFundingName("");
            bean.setUnfinishedReason("");
            bean.setRectificationChargePersonName("");
            bean.setPlanFinishedDate("");
        } else {
            uploadBean.setRectificationPlan(bean.getRectificationPlan());
            uploadBean.setRectificationPlanDoc(bean.getRectificationPlanDoc());
            uploadBean.setFundingName(bean.getFundingName());
            uploadBean.setUnfinishedReason(bean.getUnfinishedReason());
            uploadBean.setRectificationChargePerson(bean.getRectificationChargePerson());
            uploadBean.setRectificationChargePersonName(bean.getRectificationChargePersonName());
            uploadBean.setPlanFinishedDate(bean.getPlanFinishedDate());
        }
        return bean;
    }

    private boolean checkAllFinish() {
        AtomicBoolean allFinish = new AtomicBoolean(true);
        try {
            BeanToMap.reflect(uploadBean, resultMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultMap.remove("fundingName");
        resultMap.remove("hazardCategory");
        resultMap.remove("hazardContent");
        resultMap.remove("hazardGrade");
        resultMap.remove("hazardName");
        resultMap.remove("isMajorAccident");
        resultMap.remove("limitRectifyTime");
        resultMap.remove("investigationTime");
        resultMap.remove("rectificationChargePersonName");

        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            if (entry.getKey().equals("unfinishedReason")) {
                if (resultMap.get("planFinishedDate") == "" || resultMap.get("planFinishedDate").isEmpty()) {
                    allFinish.set(false);
                    break;
                }
                // 大于0说明左侧日期大于右侧日期
                if (resultMap.get("unfinishedReason").isEmpty() &&
                        (list.get(pre).getLimitRectifyTime().substring(0, 10).compareTo(resultMap.get("planFinishedDate").substring(0, 10)) < 0)) {
                    allFinish.set(false);
                    break;
                } else {
                    continue;
                }
            }
            if (entry.getValue() == "null" || entry.getValue().isEmpty()) {
                allFinish.set(false);
                break;
            }

        }
        return allFinish.get();
    }

    private void rectifyPlanReadAndEditStartAct() {
        RetrofitUtil.getApi().rectifyPlanReadAndEditStartAct(planId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "修改状态中") {
                    @Override
                    public void onSuccess(String bean) {
                        ToastUtil.show(mContext, bean);
                        finish();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void uploadFile(File file, String id, String type) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part MultipartFile = MultipartBody.Part.createFormData("files", file.getName(), requestFile);

        RequestBody requestBody_id = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestBody_type = RequestBody.create(MediaType.parse("text/plain"), type);

        RetrofitUtil.getApi().uploadFile(requestBody_id, requestBody_type, MultipartFile)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在上传") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("上传成功")) {
                            synchronized (this) {
                                picUploadSuccessNum++;
                            }
                            if (fileMap.size() == picUploadSuccessNum) { //如果图片都上传成功，则上传数据
                                updatePlan();
                                ToastUtil.show(mContext, "postData");
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void updatePlan() {
        RetrofitUtil.getApi().updateRectification(DataConverterUtil.map_to_body(resultMap))
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String bean) {

                        if (list.size() == 1) {
                            rectifyPlanReadAndEditStartAct();
                            return;
                        }
                        if (turnPage.equals(FINISH)) {
                            rectifyPlanReadAndEditStartAct();
                            return;
                        }
                        fileMap.clear();
                        picUploadSuccessNum = 0;
                        if (bean.equals("保存成功")) {
                            HazardPlanListBean hazardDetailBean = new HazardPlanListBean(
                                    Integer.parseInt(resultMap.get("id")),
                                    list.get(pre).getHazardName(),
                                    list.get(pre).getHazardContent(),
                                    list.get(pre).getIsMajorAccident(),
                                    list.get(pre).getHazardCategory(),
                                    list.get(pre).getHazardGrade(),
                                    itemLayoutList.get(4).getHazardName_one_edit_str(),
                                    itemLayoutList.get(5).getHazardName_one_edit_str(),
                                    resultMap.get("funding"),
                                    itemLayoutList.get(6).getHazardName_one_text_str(),
                                    resultMap.get("planFinishedDate"),
                                    resultMap.get("unfinishedReason"),
                                    list.get(pre).getInvestigationTime(),
                                    list.get(pre).getLimitRectifyTime(),
                                    resultMap.get("rectificationChargePerson"),
                                    itemLayoutList.get(10).getHazardName_one_text_str());
                            binding.hazardPlanLinear.removeAllViews();
                            list.set(pre, hazardDetailBean);

                            resultMap.clear();
                            itemLayoutList.clear();
                            ToastUtil.show(mContext, "第" + (pre + 1) + "条数据" + bean);
                            if (turnPage.equals(UP)) {
                                pre--;
                            } else {
                                if (pre == currentPosition) {
                                    currentPosition++;
                                }
                                pre++;
                            }
                            initList(pre);
                        } else {
                            picUploadSuccessNum = 0;
                            ToastUtil.show(mContext, "第" + pre + "条数据" + bean);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    public class Listener {
        public void pre() {
            turnPage = UP;
            submit();
        }

        public void next() {
            turnPage = DOWN;
            submit();
        }

        public void submitBtn() {
            turnPage = FINISH;
            submit();
        }

        public void submit() {
            uploadBean.setRectificationPlan(itemLayoutList.get(4).getHazardName_one_edit_str());
            uploadBean.setRectificationPlanDoc(itemLayoutList.get(5).getHazardName_one_edit_str());
            uploadBean.setUnfinishedReason(itemLayoutList.get(9).getHazardName_one_edit_str());
            if (itemLayoutList.get(6).getHazardName_one_text_str().equals("是")) {
                uploadBean.setFunding("1");
            } else {
                uploadBean.setFunding("0");
            }
            if (checkAllFinish()) {
                if (fileMap.size() > 0) {
                    submitPic();
                } else {
                    updatePlan();
                }
                //   updatePlan();
            } else {
                ToastUtil.show(mContext, "请填写完成");
            }
        }
    }

    private void submitPic() {
        for (int i = 0; i < fileMap.size(); i++) {
            if (fileMap.get(i).getType() == 1) { //隐患-整改计划-防范措施
                uploadFile(fileMap.get(i).getFile(), String.valueOf(list.get(pre).getId()), Constants.FileType42);
            } else { //隐患-整改计划-整改方案
                uploadFile(fileMap.get(i).getFile(), String.valueOf(list.get(pre).getId()), Constants.FileType43);
            }
        }
    }

    public void timePicker() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimePicker mTimePicker = null;
        try {
            mTimePicker = new TimePicker.Builder(HazardPlanActivity.this, TYPE_DATE, (picker, date) -> {
                uploadBean.setPlanFinishedDate(formatter.format(date));
                itemLayoutList.get(11).setHazardName_one_text(formatter.format(date).substring(0, 10));
            }).setRangDate(formatter.parse(list.get(pre).getInvestigationTime()).getTime(), 1893563460000L)
                    .create();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mTimePicker.show();
    }
}
