package com.etuo.kucun.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.etuo.kucun.FrameworkApp;
import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseHeaderBarActivity;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.callback.DialogCallback;
import com.etuo.kucun.loader.GlideImageLoader;
import com.etuo.kucun.model.BreakageTypeModel;
import com.etuo.kucun.model.DamagedDetailsModel;
import com.etuo.kucun.model.ScanTpInfoCodeModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.ui.adapter.BreakageTypeAdapter;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.StringUtil;
import com.etuo.kucun.utils.UrlTools;
import com.etuo.kucun.view.ScrollWithGridView;
import com.etuo.kucun.widget.photoByCamera.GetPhotoFromPhotoAlbum;
import com.etuo.kucun.widget.photoByCamera.GetPicByCamera;
import com.etuo.kucun.widget.photoByCamera.PhotoBitmapUtils;
import com.etuo.kucun.widget.photoByCamera.PhotoUtils;
import com.lzy.okgo.OkGo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.etuo.kucun.utils.UrlTools.ADD_DAMAGED;
import static com.etuo.kucun.utils.UrlTools.DAMAGED_DETAILS;
import static com.etuo.kucun.utils.UrlTools.EDIT_DAMAGED_DETAILS;
import static com.etuo.kucun.utils.UrlTools.IMAGE_UPLOAD;

/**
 * 新增报损和报损详情
 * Created by xfy on 2018/11/20.
 */

public class AddDamagedActivity extends BaseHeaderBarActivity implements BreakageTypeAdapter.ProjectStatusCallBack {
    @BindView(R.id.im_tp_icon)
    ImageView imTpIcon;
    @BindView(R.id.tv_tp_num)
    TextView tvTpNum;
    @BindView(R.id.tv_damaged_time)
    TextView tvDamagedTime;
    @BindView(R.id.ll_base)
    LinearLayout llBase;

    @BindView(R.id.iv_icon_upload)
    ImageView ivIconUpload;
    @BindView(R.id.et_fault_description)
    EditText etFaultDescription;
    @BindView(R.id.tv_wor_count)
    TextView tvWordCount;
    @BindView(R.id.tv_service)
    RadioButton tvService;
    @BindView(R.id.tv_scrapped)
    RadioButton tvScrapped;
    @BindView(R.id.ll_clfs)
    LinearLayout llClfs;
    @BindView(R.id.bt_add_upload)
    Button btAddUpload;

    @BindView(R.id.gridview)
    ScrollWithGridView mGridview;
    private String id;
    private GlideImageLoader mImageLoader;
    private String damagedId;//报损id
    private String paletteNum;//编号
    String damagedStatus;
    String num;

    //1:整体 2:板条 3:垫块 4:纵梁 5:托盘钉 6:二维码
    private String[] breakageType = {"整体", "板条", "垫块", "纵梁", "托盘订", "二维码"};
    private String[] breakageTypeByContainer = {"箱门", "箱侧板", "箱立柱", "箱顶", "箱底", "其他部位"};
    private List<BreakageTypeModel> mTypeModels;

    private BreakageTypeAdapter mBreakageTypeAdapter;

    private String productType = "";


    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;

    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;  // 相机
    private Uri cropImageUri;//相册
    private String imagePath, getImagePathUrl;
    private ScanTpInfoCodeModel mModel;
    private String tpOrContainer;
    private File cameraSavePath;//拍照照片路径;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_damaged);
        ButterKnife.bind(this);
        mImageLoader = new GlideImageLoader();
        damagedStatus = getIntent().getStringExtra(ExtraConfig.TypeCode.INTENT_DAMAGED_FROM);
        initBreakageTypeData();
        if ("0".equals(damagedStatus)) {
            setHeaderTitle("报损详情");
            id = getIntent().getStringExtra(ExtraConfig.TypeCode.INTENT_ID);
            getDetailsData();
            ivIconUpload.setClickable(false);

            // 设置成不可编辑 只可查看
            etFaultDescription.setCursorVisible(false);
            etFaultDescription.setFocusable(false);
            etFaultDescription.setFocusableInTouchMode(false);

        } else if ("1".equals(damagedStatus)) {
            setHeaderTitle("新增报损");
            llClfs.setVisibility(View.GONE);
            tpOrContainer = getIntent().getStringExtra(ExtraConfig.TypeCode.INTENT_DAMAGED_TPORCN);
            mModel = getIntent().getParcelableExtra(ExtraConfig.TypeCode.INTENT_DAMAGED_TPMODEL);
            num = mModel.getPalletNum();
            tvTpNum.setText("型号: " + mModel.getPalletModel());
            tvDamagedTime.setText("托盘编号: " + num);
            mImageLoader.displayImage(AddDamagedActivity.this,
                    mModel.getThumbNail(), imTpIcon, R.mipmap.icon_de_kind);
            ivIconUpload.setClickable(true);
            initBreakageTypeData();
        }
        listenerEdit();

    }

    /**
     * 初始化数据
     */
    private void initBreakageTypeData() {
        mTypeModels = new ArrayList<>();
        if (!StringUtil.isEmpty(tpOrContainer) && "1".equals(tpOrContainer)) {
            for (int i = 0; i < breakageType.length; i++) {
                BreakageTypeModel breakageTypeModel = new BreakageTypeModel();
                breakageTypeModel.setTypeName(breakageType[i]);
                breakageTypeModel.setPosition_index(i + 1 + "");
                mTypeModels.add(breakageTypeModel);
            }
        } else if (!StringUtil.isEmpty(tpOrContainer) && "2".equals(tpOrContainer)) {
            for (int i = 0; i < breakageTypeByContainer.length; i++) {
                BreakageTypeModel breakageTypeModel = new BreakageTypeModel();
                breakageTypeModel.setTypeName(breakageTypeByContainer[i]);
                breakageTypeModel.setPosition_index(i + 1 + "");
                mTypeModels.add(breakageTypeModel);
            }
        } else {
            for (int i = 0; i < breakageType.length; i++) {
                BreakageTypeModel breakageTypeModel = new BreakageTypeModel();
                breakageTypeModel.setTypeName(breakageType[i]);
                breakageTypeModel.setPosition_index(i + 1 + "");
                mTypeModels.add(breakageTypeModel);
            }
        }
        mBreakageTypeAdapter = new BreakageTypeAdapter(mTypeModels, productType, AddDamagedActivity.this, damagedStatus);

        mGridview.setAdapter(mBreakageTypeAdapter);
        mBreakageTypeAdapter.setCallBack(this);
    }

    /**
     * 报损详情
     */

    public void getDetailsData() {
        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.get(UrlTools.getInterfaceUrl(DAMAGED_DETAILS))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("id", id)
                .execute(new DialogCallback<LzyResponse<DamagedDetailsModel>>(this, "加载中..") {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(LzyResponse<DamagedDetailsModel> responseData, Call call, Response response) {
                        if (responseData.bean != null) {
                            mImageLoader.displayImage(AddDamagedActivity.this,
                                    responseData.bean.getThumbNail(), imTpIcon, R.mipmap.icon_de_kind);
                            tvTpNum.setText("型号: " + responseData.bean.getPalletModel());
                            if ("1".equals(responseData.bean.getProductType())) {
                                tvDamagedTime.setText("托盘编号: " + responseData.bean.getPalletNum());
                            } else if ("2".equals(responseData.bean.getProductType())) {
                                tvDamagedTime.setText("集装箱编号: " + responseData.bean.getPalletNum());
                            }
                            mImageLoader.displayImage(AddDamagedActivity.this,
                                    responseData.bean.getImgPath(), ivIconUpload, R.mipmap.icon_de_kind);
                            etFaultDescription.setText(responseData.bean.getDescription());

                            tpOrContainer = responseData.bean.getProductType();

                            damagedId = responseData.bean.getId();
                            paletteNum = responseData.bean.getPalletNum();
                            initBreakageTypeData();
                            productType = responseData.bean.getPosition();
                            mBreakageTypeAdapter.setPosition(productType);
                            mBreakageTypeAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        ShowToast.tCustom(AddDamagedActivity.this, e.getMessage());
                    }
                });
    }


    /**
     * 新增报损 - 添加图片
     */
    public void upImageUpload(File file) {
        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.post(UrlTools.getInterfaceUrl(IMAGE_UPLOAD))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("file", file)//编号
                .params("type", "2")

                .execute(new DialogCallback<LzyResponse<String>>(this, "正在上传..") {
                    @Override
                    public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {


                        if (responseData != null) {
                            LogUtil.d(responseData.message);
                            if (!StringUtil.isEmpty(responseData.commonFiled)) {
                                upAddData(num, productType, responseData.commonFiled);
                            }

                        }


                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        ShowToast.tCustom(AddDamagedActivity.this, e.getMessage());
                    }
                });
    }

    /**
     * 新增报损
     */
    public void upAddData(String num, String postion, String imgPathUrl) {
        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.post(UrlTools.getInterfaceUrl(ADD_DAMAGED))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("palletNum", num)//编号
                .params("description", etFaultDescription.getText().toString())//描述
                .params("imgPath", imgPathUrl)//图片地址
                .params("position", postion)//损坏部位
                .params("productType", tpOrContainer)//1:托盘  2:集装箱

                .execute(new DialogCallback<LzyResponse<String>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {

                        showToast(responseData.message);
                        finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        ShowToast.tCustom(AddDamagedActivity.this, e.getMessage());
                    }
                });
    }

    /**
     * 修改详情
     * <p>
     * (query)
     * 处理方式:7、维修 8、报废
     */
    public void editDetails(String type) {
        LogUtil.d("token : " + PreferenceCache.getToken());

        if ("维修".equals(type)) {
            type = "7";
        } else if ("报废".equals(type)) {
            type = "8";
        }
        OkGo.post(UrlTools.getInterfaceUrl(EDIT_DAMAGED_DETAILS))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("palletNum", paletteNum)//编号
                .params("id", damagedId)
                .params("dealType", type)

                .execute(new DialogCallback<LzyResponse<String>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {

                        showToast("操作成功");
                        finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        ShowToast.tCustom(AddDamagedActivity.this, e.getMessage());
                    }
                });
    }

    @OnClick(R.id.bt_add_upload)
    public void onViewClicked() {

    }

    @OnClick({R.id.tv_service, R.id.tv_scrapped, R.id.iv_icon_upload, R.id.bt_add_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_service:
                    if (tvService.isChecked()) {
                        tvService.setBackgroundResource(R.drawable.bg_stork_blue);
                        tvScrapped.setBackgroundResource(R.drawable.bg_stork_gray_white);
                        tvService.setTextColor(getResources().getColor(R.color.white));
                        tvScrapped.setTextColor(getResources().getColor(R.color.font_black));
                    }
                break;
            case R.id.tv_scrapped:
                    if (tvScrapped.isChecked()) {
                        tvScrapped.setBackgroundResource(R.drawable.bg_stork_blue);
                        tvService.setBackgroundResource(R.drawable.bg_stork_gray_white);
                        tvScrapped.setTextColor(getResources().getColor(R.color.white));
                        tvService.setTextColor(getResources().getColor(R.color.font_black));
                    }
                break;

            case R.id.iv_icon_upload:
                    autoObtainCameraPermission();
                break;
            case R.id.bt_add_upload:
                    if ("0".equals(damagedStatus)) {
                        if (tvService.isChecked()) {
                            editDetails("维修");
                        } else if (tvScrapped.isChecked()) {
                            editDetails("报废");
                        } else {
                            showToast("请选择处理方式");
                        }
                        //新增
                    } else if ("1".equals(damagedStatus)) {
                        if (StringUtil.isEmpty(productType)) {
                            showToast("请选择报损部位");
                        } else if (imagePath == null || "".equals(imagePath)) {
                            showToast("请上传图片");
                        } else {
                            upImageUpload(new File(imagePath));
                        }
                    }
                break;
            default:
                break;


        }
    }


    public void listenerEdit() {
        etFaultDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String inPut = etFaultDescription.getText().toString();
                tvWordCount.setText(inPut.length() + "");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private Activity getActivity() {
        return AddDamagedActivity.this;
    }


    /**
     * 选择器的回调
     *
     * @param position
     * @param parent
     */
    @Override
    public void onProjectStatus(String position, ViewGroup parent) {


        productType = position;
        LogUtil.d("选择了: " + productType);
    }


    /*******************选择相册和调用摄像头**************************/

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                cameraSavePath = GetPicByCamera.getPhotoFilePath();
                imageUri = GetPicByCamera.initpositivepop(this, CODE_CAMERA_REQUEST, cameraSavePath);

            } else {

                showToast("设备没有SD卡！");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        cameraSavePath = GetPicByCamera.getPhotoFilePath();
                        imageUri = GetPicByCamera.initpositivepop(this, CODE_CAMERA_REQUEST, cameraSavePath);

                    } else {
                        showToast("设备没有SD卡！");
                    }
                } else {
                    showToast("请允许打开相机！");

                }
                break;

            }
            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调 或者 图库
                    if (data == null) { //相机
                        // 这里是针对文件路径处理
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            imagePath = PhotoBitmapUtils.amendRotatePhoto(String.valueOf(cameraSavePath), FrameworkApp.getAppContext());
                        } else {

                            imagePath = PhotoBitmapUtils.amendRotatePhoto(imageUri.getEncodedPath(), FrameworkApp.getAppContext());
                        }

                        showImages(imagePath);
                    } else {//图库
                        // 以指定图像存储路径的方式调起相机，成功后返回data为空
                        imagePath = PhotoBitmapUtils.amendRotatePhoto(GetPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData()), FrameworkApp.getAppContext());
                        showImages(imagePath);
                    }
                    break;
                default:
                    break;

            }
        }
    }

    /**
     * 自动获取sdk权限
     */

    private void showImages(String path) {

        if (!StringUtil.isEmpty(path)) {
            Bitmap bitmap = PhotoUtils.getBitmapFromUri(Uri.fromFile(new File(path)), this);
            if (bitmap != null) {
                ivIconUpload.setImageBitmap(bitmap);

            }
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

}



