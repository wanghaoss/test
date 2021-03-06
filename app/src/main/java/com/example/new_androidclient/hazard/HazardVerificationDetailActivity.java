package com.example.new_androidclient.hazard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.Constants;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.Other.SPString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.BitmapUtil;
import com.example.new_androidclient.Util.DataConverterUtil;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityHazardVerificationDetailBinding;
import com.example.new_androidclient.hazard.adapter.HazardPicAdapter;
import com.example.new_androidclient.hazard.bean.HazardPicBean;
import com.example.new_androidclient.hazard.bean.HazardVerificationDetailBean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Query;

import static com.example.new_androidclient.Other.SPString.UserId;
import static com.example.new_androidclient.Other.SPString.UserName;

/**
 * ????????????
 */
@Route(path = RouteString.HazardVerificationDetailActivity)
public class HazardVerificationDetailActivity extends BaseActivity {
    private ActivityHazardVerificationDetailBinding binding;
    private Listener listener = new Listener();
    private HazardVerificationDetailBean bean;
    private List<HazardVerificationDetailBean> list = new ArrayList<>();
    private Map<String, String> uploadMap = new HashMap<>();
    private final String DEFAULT = "2"; // ????????????
    private final String QUALIFIED = "1";  //??????
    private final String UNQUALIFIED = "0"; //?????????
    private String check = DEFAULT;  //????????????

    private AlertDialog dialog;
    private int flag = -1;
    private String msg;

    private String time;

    private Uri mUri;
    private String path;
    private File file;
    private int picUploadSuccessNum;
    private HazardPicAdapter adapter;
    private final static int REQUEST_CODE_CAMERA = 1;
    private List<HazardPicBean> newPicFileList = new ArrayList<>();//???????????????
    private List<HazardPicBean> allPicFileList = new ArrayList<>();//???????????????????????????????????????????????????????????????

    @Autowired()
    HazardVerificationDetailBean object;  //????????????bean?????????HazardVerificationList?????????

    @Autowired
    boolean isSign = false; //?????????????????????

    @Autowired
    boolean fromPush = false; //????????????????????????????????????????????????????????? object?????????????????????????????????????????????bean

    @Autowired
    int idFromPush; //????????????????????????????????????????????????id?????????

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hazard_verification_detail);
        binding.setListener(listener);
        adapter = new HazardPicAdapter(allPicFileList, HazardVerificationDetailActivity.this);
        binding.hazardPicRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        binding.hazardPicRecycler.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fromPush) {  //?????????????????????????????????bean
            getList();
        } else {  //?????????????????????
            bean = object;
            binding.setDetailBean(bean);
            setDataToContentAndCountAndButtonAndChecked();
        }
    }

    private void getList() {
        RetrofitUtil.getApi().getVerHazardDetailById_b(idFromPush)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardVerificationDetailBean>>(mContext, true, "????????????????????????") {
                    @Override
                    public void onSuccess(List<HazardVerificationDetailBean> list) {
                        if (list != null) {
                            bean = object = list.get(0);
                            binding.setDetailBean(bean);
                            setDataToContentAndCountAndButtonAndChecked();
                        } else {
                            ToastUtil.show(mContext, "??????????????????");
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


    private void setDataToContentAndCountAndButtonAndChecked() {
        getPicUrl();
        if (object.getCompletion() == null) {

        } else if (object.getCompletion().equals("0")) { //?????????????????? 0????????? 1??????
            binding.verUnqualified.setChecked(true);
            check = UNQUALIFIED;
        } else if (object.getCompletion().equals("1")) {
            binding.verQualified.setChecked(true);
            check = QUALIFIED;
        }

        //?????????????????????????????????, ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        bean.setId(object.getId());
        bean.setHazardContent(object.getHazardContent());
        bean.setHazardName(object.getHazardName());
        bean.setPlanFinishedDate(object.getPlanFinishedDate());
        bean.setAcceptanceDesc(object.getAcceptanceDesc());
        if (object.getAcceptancePerson() == null || object.getAcceptancePerson().isEmpty()) {  //????????????????????????????????????????????????????????????
            bean.setAcceptancePerson(SPUtil.getData(SPString.UserName, "").toString());
        } else {
            bean.setAcceptancePerson(object.getAcceptancePerson());
        }

        bean.updateData();

        //????????????????????????
        showButton();
    }

    private void showButton() {
        switch (bean.getStatus()) {
            case "44":  //?????????????????????
            case "49":  //??????????????????
                binding.submit.setVisibility(View.VISIBLE);
                binding.agree.setVisibility(View.GONE);
                binding.unagree.setVisibility(View.GONE);
                break;
            case "50":  //?????????????????????
            case "51":  //?????????????????????
                binding.submit.setVisibility(View.GONE);
                binding.agree.setVisibility(View.VISIBLE);
                binding.unagree.setVisibility(View.VISIBLE);
                break;

        }
    }

    private void getPicUrl() {
        RetrofitUtil.getApi().getPicUrl(bean.getId(), Constants.FileType41)
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
                            if (newPicFileList.size() == picUploadSuccessNum) { //?????????????????????????????????????????????
                                postData(uploadMap);
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

    private void postData(Map<String, String> map) {
        RetrofitUtil.getApi().updHazardRectifyPlan(DataConverterUtil.map_to_body(map))
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "??????????????????") {
                    @Override
                    public void onSuccess(String str) {
                        if (isSign) {  //?????????????????????
                            sign(msg, flag);
                        } else if (object.getStatus().equals("49")) { //?????????????????????????????????????????????
                            sign("", 1);
                        } else {
                            ToastUtil.show(mContext, str);
                            finish();
                        }

                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


    private void setQualifiedCheckState(boolean state) {
        binding.verQualified.setChecked(state);
    }

    private void setUnqualifiedCheckState(boolean state) {
        binding.verUnqualified.setChecked(state);
    }

    private boolean checkData() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//??????????????????
        uploadMap.clear();
        if (check.equals(DEFAULT)) {
            ToastUtil.show(mContext, "?????????????????????");
            return false;
        }
        if (bean.getAcceptancePerson() == null ||
                bean.getAcceptancePerson().isEmpty() ||
                bean.getAcceptanceDesc() == null ||
                bean.getAcceptanceDesc().isEmpty()) {
            ToastUtil.show(mContext, "???????????????");
            return false;
        } else {
            uploadMap.put("completion", check);
        }
        uploadMap.put("id", String.valueOf(bean.getId()));
        uploadMap.put("acceptanceDesc", bean.getAcceptanceDesc());
        time = df.format(new Date());
        uploadMap.put("acceptanceTime", time);
        uploadMap.put("acceptancePerson", String.valueOf(SPUtil.getData(UserId, 0)));
        return true;
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
            HazardPicBean picBean = new HazardPicBean(String.valueOf(this.bean.getId()), Constants.FileType41, newFile, "", false);
            newPicFileList.add(picBean);
            synchronized (this) {
                allPicFileList.add(picBean);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void showDialog() {
        dialog = new AlertDialog.Builder(this).setPositiveButton("??????", null).setNegativeButton("??????", null).create();
        dialog.setTitle("???????????????");

        Window win = dialog.getWindow();
        win.getDecorView().setPadding(10, 20, 10, 20);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

        View view = getLayoutInflater().inflate(R.layout.hazard_plan_sign_dialog, null);
        final EditText msg_edit = view.findViewById(R.id.hazard_plan_sign_dialog_edittext);
        dialog.setView(view);//??????login_layout??????????????????
        dialog.setCancelable(false);//?????????????????????
        dialog.setOnShowListener(mDialog -> {
            Button positionButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            positionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (msg_edit.getText().toString().trim().isEmpty() && flag == 0) {
                        ToastUtil.show(mContext, "???????????????");
                    } else {
                        //       sign(msg_edit.getText().toString().trim());
                        msg = msg_edit.getText().toString().trim();
                        listener.update();
                        dialog.dismiss();
                    }
                }

            });
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        });
        dialog.show();
    }

    //????????????????????????
    private void sign(String msg, int flag) {
        RetrofitUtil.getApi().hiRectifyPlanCheckAndAcceptRe2Rectify2Act(object.getId(), flag, msg)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "???????????????") {
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

    public class Listener extends BaseObservable {
        public void update() {
            if (checkData()) {
                if (newPicFileList.size() == 0) {
                    if (uploadMap.get("completion").equals(UNQUALIFIED)) {
                        ToastUtil.show(mContext, "?????????");
                    } else {
                        postData(uploadMap);
                    }
                } else {
                    for (int i = 0; i < newPicFileList.size(); i++) {
                        submitPic(newPicFileList.get(i).getFile(),
                                newPicFileList.get(i).getId(),
                                Constants.FileType41);
                    }
                }
            }
        }

        public void submit() {
            update();
        }

        public void agree() {
            flag = 1;
            showDialog();
        }

        public void unagree() {
            flag = 0;
            showDialog();
        }

        public void takepic() {
            takePhoto();
        }

        public void check_qualified() {
            check = QUALIFIED;
            setQualifiedCheckState(true);
            setUnqualifiedCheckState(false);

        }

        public void check_unqualified() {
            check = UNQUALIFIED;
            setQualifiedCheckState(false);
            setUnqualifiedCheckState(true);

        }
    }

}
