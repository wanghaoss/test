package com.example.new_androidclient.hazard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

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
import com.example.new_androidclient.Util.BitmapUtil;
import com.example.new_androidclient.Util.DataConverterUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.HazardItemLayout;
import com.example.new_androidclient.databinding.ActivityHazardGovernmentBinding;
import com.example.new_androidclient.hazard.adapter.HazardPicAdapter;
import com.example.new_androidclient.hazard.bean.HazardPicBean;
import com.example.new_androidclient.inspection.bean.HazardGovernmentListBean;

import org.jaaksi.pickerview.picker.TimePicker;

import java.io.File;
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

import static org.jaaksi.pickerview.picker.TimePicker.TYPE_DATE;

/**
 * zq
 * 整改治理
 **/

@Route(path = RouteString.HazardGovernmentActivity)
public class HazardGovernmentActivity extends BaseActivity implements View.OnClickListener {
    private ActivityHazardGovernmentBinding binding;
    private Listener listener = new Listener();
    private List<HazardGovernmentListBean> list = new ArrayList<>();
    private List<HazardItemLayout> itemLayoutList = new ArrayList<>();
    private HazardGovernmentListBean uploadBean = new HazardGovernmentListBean();
    private Map<String, String> resultMap = new HashMap<>();
    private int Intent_code_person = 2; //和隐患分析共用同一个分析人，所以是2

    private int currentPosition;  //从列表页跳转进本页面时，当前页面里，显示第current条数据
    private int pre = 0;  //例如当前pre=current=5，往前翻一页， pre=4， 往后翻一页，pre=5
    private String UP = "up";//用户点击上一条
    private String DOWN = "down"; //用户点击下一条
    private String FINISH = "finish"; //用户点击完成巡检
    private String turnPage = DOWN; //用户点击了上一条按钮还是下一条按钮

    private Uri mUri;
    private String path;
    private File file;
    private int picUploadSuccessNum;
    private HazardPicAdapter adapter;
    private final static int REQUEST_CODE_CAMERA = 1;
    private List<HazardPicBean> newPicFileList = new ArrayList<>();//用户新拍的
    private List<HazardPicBean> allPicFileList = new ArrayList<>();//用户新拍的和接口获取的放在一起，在页面显示

    String[] nameList = {
            "隐患名称",//0
            "隐患描述",//1
            "整改结果",//2
            "隐患排查日期",//3
            "整改实际完成时间",//4
            "整改说明",//5
            "整改负责人",//6
    };
    int[] typeList = {1, 1, 3, 1, 2, 3, 2};


    @Autowired()
    int planId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hazard_government);
        binding.setListener(listener);
        binding.title.getHazard_plan_take_pic().setOnClickListener(this);
        adapter = new HazardPicAdapter(allPicFileList, HazardGovernmentActivity.this);
        binding.hazardGovernmentRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        binding.hazardGovernmentRecycler.setAdapter(adapter);
        getList();
    }

    private void getList() {
        RetrofitUtil.getApi().selectHazardGovernment(planId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardGovernmentListBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<HazardGovernmentListBean> bean) {
                        if (bean.size() > 0) {
                            list.clear();
                            list.addAll(bean);
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getRectificationResult() == null ||
                                        list.get(i).getRectificationResult().isEmpty()) {
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


    private void getPicUrl() {
        RetrofitUtil.getApi().getPicUrl(list.get(pre).getId(), Constants.FileType44)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardPicBean>>(mContext, false) {

                    @Override
                    public void onSuccess(List<HazardPicBean> bean) {
                        if (bean.size() > 0) {
                            for (int i = 0; i < bean.size(); i++) {
                                bean.get(i).setFromSystem(true);
                            }
                            synchronized (this) {
                                allPicFileList.addAll(bean);
                            }
                            adapter.notifyDataSetChanged();
                            // getBitmap();
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

        getPicUrl();

        uploadBean.setId(list.get(pre).getId());

        String num = "(" + (pre + 1) + "/" + list.size() + ")";
        binding.title.setName("整改治理" + num);

        HazardGovernmentListBean currentBean = checkNull(list.get(pre));
        for (int i = 0; i < nameList.length; i++) {
            addView(i, typeList[i], currentBean);
        }
    }

    private void addView(int position, int type, HazardGovernmentListBean currentBean) {
        HazardItemLayout layout = new HazardItemLayout(mContext, position);
        layout.init(type);
        layout.setHazardName_one(nameList[position]);
        if (position == 0) {
            layout.setHazardName_one_text(currentBean.getHazardName());
        } else if (position == 1) {
            layout.setHazardName_one_text(currentBean.getHazardContent());
        } else if (position == 2) {
            layout.setHazardName_one_edit(currentBean.getRectificationResult());
        } else if (position == 3) {
            if (currentBean.getInvestigationTime().length() > 10) {
                layout.setHazardName_one_text(currentBean.getInvestigationTime().substring(0, 10));
            } else {
                layout.setHazardName_one_text(currentBean.getInvestigationTime());
            }
        } else if (position == 4) {
            if (currentBean.getActualFinishedTime().length() > 10) {
                layout.setHazardName_one_text(currentBean.getActualFinishedTime().substring(0, 10));
            } else {
                layout.setHazardName_one_text(currentBean.getActualFinishedTime());
            }
        } else if (position == 5) {
            layout.setHazardName_one_edit(currentBean.getRectificationDesc());
        } else if (position == 6) {
            layout.setHazardName_one_text(currentBean.getRectificationChargePersonName());
        }

        layout.setOnClickListener(v -> {
            switch (layout.getPos()) {
                case 6:
                    ARouter.getInstance().build(RouteString.HazardAnalysisPersonSelectActivity)
                            .navigation(HazardGovernmentActivity.this, Intent_code_person); //分析负责人单位
                    break;
                case 4:
                    timePicker();
                    break;
            }
        });

        binding.hazardGovernmentLinear.addView(layout);
        itemLayoutList.add(layout);
    }

    private boolean checkAllFinish() {
        AtomicBoolean allFinish = new AtomicBoolean(true);
        try {
            BeanToMap.reflect(uploadBean, resultMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resultMap.remove("isMajorAccident");
        resultMap.remove("hazardName");
        resultMap.remove("hazardContent");
        resultMap.remove("investigationTime");
        resultMap.remove("rectificationChargePersonName");

        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            if (entry.getValue() == "null" || entry.getValue().isEmpty()) {
                allFinish.set(false);
                break;
            }
        }
        return allFinish.get();
    }

    private void updateStatus() {
        RetrofitUtil.getApi().updateStatus(planId, "44") //44已整改
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "修改状态中") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("保存成功")) {
                            ToastUtil.show(mContext, bean);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void updateGovernment() {
        RetrofitUtil.getApi().updateRectification(DataConverterUtil.map_to_body(resultMap))
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String bean) {

                        if (list.size() == 1) {
                            updateStatus();
                            return;
                        }
                        if (turnPage.equals(FINISH)) {
                            updateStatus();
                            return;
                        }
                        if (bean.equals("保存成功")) {
                            HazardGovernmentListBean hazardGovernmentListBean = new HazardGovernmentListBean(
                                    Integer.parseInt(resultMap.get("id")),
                                    itemLayoutList.get(0).getHazardName_one_text_str(),
                                    itemLayoutList.get(1).getHazardName_one_text_str(),
                                    list.get(pre).getIsMajorAccident(),
                                    itemLayoutList.get(2).getHazardName_one_edit_str(),
                                    resultMap.get("actualFinishedTime"),
                                    list.get(pre).getInvestigationTime(),
                                    resultMap.get("rectificationDesc"),
                                    itemLayoutList.get(6).getHazardName_one_text_str(),
                                    uploadBean.getRectificationChargePerson()
                            );
                            binding.hazardGovernmentLinear.removeAllViews();
                            list.set(pre, hazardGovernmentListBean);
                            allPicFileList.clear();
                            newPicFileList.clear();
                            picUploadSuccessNum = 0;
                            adapter.notifyDataSetChanged();
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
                            ToastUtil.show(mContext, "第" + pre + "条数据" + bean);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        initCamera();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    private void initCamera() {
        path = mContext.getFilesDir() + File.separator + "images" + File.separator;
        file = new File(path, System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //  步骤二：Android 7.0及以上获取文件 Uri
            mUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".inspection.MyFileProvider", file);
        } else {
            //  步骤三：获取文件Uri
            mUri = Uri.fromFile(file);
        }
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
            uploadBean.setRectificationResult(itemLayoutList.get(2).getHazardName_one_edit_str());
            uploadBean.setRectificationDesc(itemLayoutList.get(5).getHazardName_one_edit_str());
            if (checkAllFinish()) {
                if (newPicFileList.size() == 0) {
                    updateGovernment();
                } else {
                    for (int i = 0; i < newPicFileList.size(); i++) {
                        submitPic(newPicFileList.get(i).getFile(),
                                newPicFileList.get(i).getId(),
                                Constants.FileType44);
                    }
                }
            } else {
                ToastUtil.show(mContext, "请填写完成");
            }

        }
    }

    private void submitPic(File file, String id, String type) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part MultipartFile = MultipartBody.Part.createFormData("files", file.getName(), requestFile);

        RequestBody requestBody_id = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestBody_type = RequestBody.create(MediaType.parse("text/plain"), type);

        RetrofitUtil.getApi().uploadFile(requestBody_id, requestBody_type, MultipartFile)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在上传图片") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("上传成功")) {
                            synchronized (this) {
                                picUploadSuccessNum++;
                            }
                            if (newPicFileList.size() == picUploadSuccessNum) { //如果图片都上传成功，则上传数据
                                updateGovernment();
                                ToastUtil.show(mContext, "postData");
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        picUploadSuccessNum = 0;
                        ToastUtil.show(mContext, err);
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Intent_code_person) {
            String userId = data.getStringExtra("userId");
            String name = data.getStringExtra("name");
            uploadBean.setRectificationChargePerson(userId);
            itemLayoutList.get(6).setHazardName_one_text(name);
        }
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            File newFile = new File(BitmapUtil.compressImage(file.getPath()));
            HazardPicBean bean = new HazardPicBean(String.valueOf(list.get(pre).getId()), Constants.FileType44, newFile, "", false);
            newPicFileList.add(bean);
            synchronized (this) {
                allPicFileList.add(bean);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private HazardGovernmentListBean checkNull(HazardGovernmentListBean bean) {
        if (bean.getRectificationResult() == null) {
            // String str = "请填写";
            bean.setRectificationResult("");
            bean.setActualFinishedTime("");
            bean.setRectificationDesc("");
            uploadBean.setRectificationChargePersonName(bean.getRectificationChargePersonName());
            uploadBean.setRectificationChargePerson(bean.getRectificationChargePerson());
        } else {
            uploadBean.setRectificationResult(bean.getRectificationResult());
            uploadBean.setActualFinishedTime(bean.getActualFinishedTime());
            uploadBean.setRectificationDesc(bean.getRectificationDesc());
            uploadBean.setRectificationChargePerson(bean.getRectificationChargePerson());
            uploadBean.setRectificationChargePersonName(bean.getRectificationChargePersonName());
        }
        return bean;
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

    public void timePicker() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimePicker mTimePicker = null;
        try {
            mTimePicker = new TimePicker.Builder(HazardGovernmentActivity.this, TYPE_DATE, (picker, date) -> {
                uploadBean.setActualFinishedTime(formatter.format(date));
                itemLayoutList.get(4).setHazardName_one_text(formatter.format(date).substring(0, 10));
                //     LogUtil.i(formatter.format(date));
            }).setRangDate(formatter.parse(list.get(pre).getInvestigationTime()).getTime(), 1893563460000L)
                    .create();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mTimePicker.show();
    }
}
