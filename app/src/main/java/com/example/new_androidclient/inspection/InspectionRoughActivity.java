package com.example.new_androidclient.inspection;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.BaseResponse;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.Constants;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.Other.SPString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.InspectionRoughLayout;
import com.example.new_androidclient.databinding.ActivityInspectionRoughBinding;
import com.example.new_androidclient.inspection.bean.InspectionAreaBean;
import com.example.new_androidclient.inspection.bean.InspectionTaskAreaResultBean;
import com.example.new_androidclient.inspection.bean.PicBean;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.example.new_androidclient.Other.SPString.InspectionRoughList;

/**
 * 原宏观巡检（扫码后右上角里的功能
 */
@Route(path = RouteString.InspectionRoughActivity)
public class InspectionRoughActivity extends BaseActivity {
    private ActivityInspectionRoughBinding binding;
    private Listener listener = new Listener();
    private int REQUEST_QR = 1;//扫描二维码
    private int REQUEST_LIST = 2;//跳转到设备列表页面
    private int REQUEST_CODE_CAMERA = 3;//跳转到拍照
    private String result;
    private Map<Integer, List<PicBean>> picMap = new HashMap<>();
    private List<PicBean> allPicFileList = new ArrayList<>();
    private int picUploadSuccessNum = 0;

    private List<InspectionAreaBean> list = new ArrayList<>();
    private List<InspectionTaskAreaResultBean> beanList = new ArrayList<>();
    private List<InspectionRoughLayout> layoutList = new ArrayList<>();

    private LinearLayout inspection_rough_linear;
    private int id;//用于接口上传的区域id
    private int areaId; //用于获取设备列表
    private int taskId; //用于获取设备列表
    private String resultContentText;//区域巡检内容
    private int pos = 0; //页面里的填写部分是一个一个view拼接成的，为了数据和view对应上，添加view时把pos传进去


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_rough);
        binding.setListener(listener);
        inspection_rough_linear = findViewById(R.id.inspection_rough_linear);
        list = SPUtil.getListData(InspectionRoughList, InspectionAreaBean.class);
        startQR();
    }

    private void startQR() {
        Intent intent = new Intent(InspectionRoughActivity.this, CustomCaptureActivity.class);
        startActivityForResult(intent, REQUEST_QR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_QR) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                //扫描二维码
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (result != null) { //如果结果不为空
                        boolean isExist = false;
                        for (int i = 0; i < list.size(); i++) {  //遍历在区域列表里找到扫描的区域，保存区域id和区域巡检内容
                            if (result.equals(list.get(i).getAreaCode())) {
                                id = list.get(i).getId();
                                areaId = list.get(i).getAreaId();
                                taskId = list.get(i).getTaskId();
                                resultContentText = list.get(i).getAreaContent();
                                addView(pos);
                                isExist = true;
                                break;
                            }
                        }
                        if (!isExist) {
                            ToastUtil.show(mContext, "请扫描巡检范围内区域");
                            startQR();
                        }
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.show(this, "解析二维码失败");
                    finish();
                }
            } else {
                finish();
            }
        }
        //选择设备后返回
        if (requestCode == REQUEST_LIST) {
            InspectionTaskAreaResultBean bean = new InspectionTaskAreaResultBean();
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    int id = data.getIntExtra("id", 0);
                    boolean isExist = false;
                    for (int i = 0; i < beanList.size(); i++) {
                        if (id == beanList.get(i).getId()) {
                            isExist = true;
                            break;
                        }
                    }
                    if (isExist) {
                        ToastUtil.show(mContext, "设备已存在");
                    } else {
                        bean.setId(id);
                        bean.setDeviceName(data.getStringExtra("deviceName"));
                        beanList.add(bean);
                        addView(pos);
                    }
                } else {
                    return;
                }
            }
        }
        //拍照后
        if (requestCode == REQUEST_CODE_CAMERA) {
            String picId;
            List<PicBean> picList = SPUtil.getListData(SPString.InspectionRoughPicList, PicBean.class);
            if (picList.size() != 0) {
                picId = picList.get(0).getId();
                picMap.put(Integer.parseInt(picId), picList);
            }
        }
    }

    private void addView(int position) {
        InspectionRoughLayout layout = new InspectionRoughLayout(mContext, position);
        if (position == 0) { //第一个是区域的，其余是设备的
            layout.initItem("宏观巡检内容：" + resultContentText, false);
        } else {
            layout.initItem("设备名称：" + beanList.get(position - 1).getDeviceName(), true);
        }
        layout.getDelete().setOnClickListener(v -> {
            inspection_rough_linear.removeView(layout);
            for (int i = 0; i < beanList.size(); i++) {
                if (("设备名称：" + beanList.get(i).getDeviceName()).equals(layout.getTextViewContent())) {
                    beanList.remove(i);

                }
            }
            pos -= 1;
        });
        layout.getTake().setOnClickListener(v -> {
            Intent intent = new Intent(InspectionRoughActivity.this, InspectionTakePhotoActivity.class);
            intent.putExtra("from", 4); //参数是3，密封点1，其它2,4宏观
            Integer picId;  //用来存储是区域还是设备的id
            if (layout.getPosition() == 0) {
                intent.putExtra("id", id);
                intent.putExtra("type", Constants.FileType30);
                picId = id;
            } else {
                intent.putExtra("id", beanList.get(layout.getPosition() - 1).getId());
                intent.putExtra("type", Constants.FileType31);
                picId = beanList.get(layout.getPosition() - 1).getId();
            }
            Bundle bundle = new Bundle();
            if (picMap.containsKey(picId)) {
                bundle.putSerializable("picList", (Serializable) picMap.get(picId));
                intent.putExtras(bundle);
                intent.putExtra("hasPic", true);
            } else {
                intent.putExtra("hasPic", false);
            }
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        });
        inspection_rough_linear.addView(layout);
        layoutList.add(layout);
        pos += 1;
    }

    private void submitData(String content) {
        RetrofitUtil.getApi().taskAreaResult(id, content, beanList)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<BaseResponse>(mContext, true, "正在上传数据") {

                    @Override
                    public void onSuccess(BaseResponse bean) {
                        if (bean.getData().equals("添加成功")) {
                            ToastUtil.show(mContext, bean.getData().toString());
                            finish();
                        } else {
                            ToastUtil.show(mContext, "添加失败");
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void submitPic(File file, String id) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part MultipartFile = MultipartBody.Part.createFormData("files", file.getName(), requestFile);

        RequestBody requestBody_id = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestBody_type = RequestBody.create(MediaType.parse("text/plain"),  Constants.FileType32);

        RetrofitUtil.getApi().uploadFile(requestBody_id, requestBody_type, MultipartFile)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在上传图片") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("上传成功")) {
                            synchronized (this) {
                                picUploadSuccessNum++;
                            }
                            if (allPicFileList.size() == picUploadSuccessNum) { //如果图片都上传成功，则上传数据
                                listener.finishRough();
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }
    public class Listener {
        public void addDevice() {
            ARouter.getInstance().build(RouteString.InspectionRoughDeviceListActivity)
                    .withInt("areaId", areaId)
                    .withInt("taskId", taskId)
                    .navigation(InspectionRoughActivity.this, REQUEST_LIST);
        }

        public void finishRough() {
            for (int i = 1; i < layoutList.size(); i++) {
                if(layoutList.get(i).getEditText().getText().toString().isEmpty()){
                    ToastUtil.show(mContext, "请填写设备巡检结果");
                    return;
                }else{
                    beanList.get(i - 1).setContent(layoutList.get(i).getEditText().getText().toString());
                }
            }
            if (layoutList.get(0).getEditText().getText().toString().isEmpty()) {
                ToastUtil.show(mContext, "请填写宏观巡检结果");
            } else {
                getPic();
            }
        }

        public void getPic(){
            for (Map.Entry<Integer, List<PicBean>> entry : picMap.entrySet()) {
                List<PicBean> list = entry.getValue();
                allPicFileList.addAll(list);
            }
            if (allPicFileList.size() > 0) {
                for (int i = 0; i < allPicFileList.size(); i++) {
                    submitPic(allPicFileList.get(i).getFile(), allPicFileList.get(i).getId());
                }
            } else {
                submitData(layoutList.get(0).getEditText().getText().toString());
            }
        }
    }
}
