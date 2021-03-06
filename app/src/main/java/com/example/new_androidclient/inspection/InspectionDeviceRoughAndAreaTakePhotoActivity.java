package com.example.new_androidclient.inspection;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.Constants;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.BitmapUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityInspectionDeviceRoughTakePhotoBinding;
import com.example.new_androidclient.hazard.adapter.HazardPicAdapter;
import com.example.new_androidclient.hazard.bean.HazardPicBean;
import com.example.new_androidclient.inspection.bean.AreaResultDevice;
import com.example.new_androidclient.inspection.bean.ItemResultDevice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@Route(path = RouteString.InspectionDeviceRoughAndAreaTakePhotoActivity)
public class InspectionDeviceRoughAndAreaTakePhotoActivity extends BaseActivity {
    private ActivityInspectionDeviceRoughTakePhotoBinding binding;
    private List<HazardPicBean> allPicList = new ArrayList<>();
    private List<HazardPicBean> newList = new ArrayList<>();
    private Listener listener = new Listener();

    private HazardPicAdapter adapter;

    private Uri mUri;
    private File file;
    private String path;
    private final static int REQUEST_CODE_CAMERA = 1;
    private int picUploadSuccessNum = 0;

    private int id;
    private String type;

    @Autowired
    ItemResultDevice ItemResultDevice_rough;

    @Autowired
    AreaResultDevice AreaResultDevice_area;

    @Autowired
    int from; //1???????????? 2????????????


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_device_rough_take_photo);
        adapter = new HazardPicAdapter(allPicList, mContext);
        binding.inspectionPicRecyelerview.setLayoutManager(new GridLayoutManager(mContext, 3));
        binding.inspectionPicRecyelerview.setAdapter(adapter);
        if (from == 1) {
            binding.inspectionPicEdittext.setText(AreaResultDevice_area.getResultMsg());
        } else {
            binding.inspectionPicEdittext.setText(ItemResultDevice_rough.getResultMsg());
        }

        binding.setListener(listener);

        if (from == 1) {  //??????
            id = AreaResultDevice_area.getId();
            type = Constants.FileType34;
        } else {  //??????
            id = ItemResultDevice_rough.getId();
            type = Constants.FileType33;
        }
        getPicList();
    }

    private void getPicList() {
        RetrofitUtil.getApi().getPicUrl_inspection(id, type)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardPicBean>>(mContext, true, "????????????????????????") {

                    @Override
                    public void onSuccess(List<HazardPicBean> bean) {
                        allPicList.clear();
                        if (bean.size() > 0) {
                            for (int i = 0; i < bean.size(); i++) {
                                bean.get(i).setFromSystem(true);
                            }
                            allPicList.addAll(bean);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void initCamera() {
        path = mContext.getFilesDir() + File.separator + "images" + File.separator;
        file = new File(path, System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //  ????????????Android 7.0????????????????????? Uri
            mUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".inspection.MyFileProvider", file);
        } else {
            //  ????????????????????????Uri
            mUri = Uri.fromFile(file);
        }
    }

    private void takePhoto() {
        initCamera();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            File newFile = new File(BitmapUtil.compressImage(file.getPath()));
            //30?????? 31?????? 32?????? 40????????????  33????????????-???????????? ??????
            HazardPicBean bean = new HazardPicBean(String.valueOf(id), type, newFile, "", false);
            allPicList.add(bean);
            newList.add(bean);
            adapter.notifyDataSetChanged();
        }
    }

    private void submitPic() {
        if (newList.size() > 0) {
            for (int i = 0; i < newList.size(); i++) {
                submitPic(newList.get(i).getFile(), String.valueOf(id), type);
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
                .subscribe(new DialogObserver<String>(mContext, true, "??????????????????") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("????????????")) {
                            synchronized (this) {
                                picUploadSuccessNum++;
                            }
                            if (newList.size() == picUploadSuccessNum) { //?????????????????????????????????????????????
                                finish();
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

    private void saveRoughDes() {
        List<ItemResultDevice> list = new ArrayList<>();
        list.add(ItemResultDevice_rough);
        RetrofitUtil.getApi().updItemResultDevice_rough(list)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "??????????????????") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("????????????")) {
                            if (newList.size() > 0) {
                                submitPic();
                            } else {
                                finish();
                            }

                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void saveAreaDes() {
        List<AreaResultDevice> list = new ArrayList<>();
        list.add(AreaResultDevice_area);
        RetrofitUtil.getApi().updItemResultDevice_area(list)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "??????????????????") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("????????????")) {
                            if (newList.size() > 0) {
                                submitPic();
                            } else {
                                finish();
                            }

                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private boolean check() {
        if (binding.inspectionPicEdittext.getText().toString().isEmpty()) {
            ToastUtil.show(mContext, "???????????????");
            return false;
        }
        return true;
    }

    public class Listener {
        public void pic() {
            takePhoto();
        }

        public void finishPic() {
            if (check()) {
                if (from == 1) {
                    AreaResultDevice_area.setResultMsg(binding.inspectionPicEdittext.getText().toString());
                    saveAreaDes();
                } else {
                    ItemResultDevice_rough.setResultMsg(binding.inspectionPicEdittext.getText().toString());
                    saveRoughDes();
                }

            }
        }
    }


}
