package com.example.new_androidclient.inspection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.EventBusMessage.EventBusMessage;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.Constants;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.Other.SPString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.InspectionMatchUtil;
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.InspectionEditLineLayout;
import com.example.new_androidclient.customize_view.InspectionPointLineLayout;
import com.example.new_androidclient.databinding.ActivityInspectionDeviceDetailBinding;
import com.example.new_androidclient.inspection.bean.InspectionAreaBean;
import com.example.new_androidclient.inspection.bean.InspectionDeviceDetailBean;
import com.example.new_androidclient.inspection.bean.InspectionDeviceListBean;
import com.example.new_androidclient.inspection.bean.InspectionUploadBean;
import com.example.new_androidclient.inspection.bean.PicBean;
import com.example.new_androidclient.inspection.dkble.Convert;
import com.ronds.eam.lib_sensor.BleClient;
import com.ronds.eam.lib_sensor.BleInterfaces;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.example.new_androidclient.Other.SPString.InspectionAreaList;
import static com.example.new_androidclient.Other.SPString.InspectionDeviceList;

/**
 * zq
 * ????????????
 **/
@Route(path = RouteString.InspectionDeviceDetailActivity)
public class InspectionDeviceDetailActivity extends BaseActivity {
    private List<InspectionUploadBean> uploadBeanList = new ArrayList<>(); //???????????????list
    private List<PicBean> allPicFileList = new ArrayList<>(); //????????????????????????????????????list
    private ActivityInspectionDeviceDetailBinding binding;
    private int currentAreaIndex; //???????????????????????????????????????????????????????????????????????????
    private Listener listener;
    //isUsingState???????????????????????????SPUtil?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
    //isUsingState????????????null???10???20???30
    //isUsingState???????????????????????????????????????
    public static int isUsingState;

    private int working = 10; //??????
    private int spare = 20; //??????
    private int repair = 30; //??????

    public final int QUALIFIED = 1; //??????
    public final int UNQUALIFIED = 0; //?????????

    private int picUploadSuccessNum = 0;
    private int request_code = 1;

    private int TOBTO_position; //??????????????????????????????editLayout???position

    private List<InspectionDeviceDetailBean> allList = new ArrayList<>(); //????????????????????????

    private List<InspectionDeviceListBean> deviceList;  //????????????
    private List<InspectionAreaBean> areaList; //????????????
    private List<InspectionDeviceDetailBean> instrumentList = new ArrayList<>();//???????????????????????????????????????????????????????????????
    private List<LinearLayout> itemList = new ArrayList<>(); //item??????
    private Map<Integer, List<PicBean>> picMap = new HashMap<>(); //????????????????????????????????????key????????????id???value???key???????????????????????????

    private String nextDevice = "????????????";
    private String nextArea = "????????????";
    private String finishInspection = "????????????";

    private String closeOrOpen = "?????????";

    private ProgressDialog progressDialog;

    private boolean isMeasureTemperature = false;
    private AlertDialog.Builder dialogBuilderForTemp;
    private AlertDialog alertDialogForVibra;

    private static final float EMI = 0.97f;    // ???????????????
    private static final int LEN = 2;   // ????????????, 4 ????????????, ?????? K, ???????????? 256K
    private static final int FREQ = 2000;    // ????????????, 4 ????????????, ?????? Hz, ???????????? 40000Hz

    @Autowired()
    int areaId;

    @Autowired()
    int taskId;

    @Autowired()
    int deviceId;

    @Autowired()
    int devicePosition;  //??????????????????????????????deviceList???????????????

    @Autowired()
    boolean isDeviceCheckFinish = false;  //??????????????????????????????????????????true????????????

    @Autowired()
    boolean isRandomInspection;  //?????????????????????????????????

    @Autowired()
    int isUsingStateFromSelectActivity; //??????????????????selectActivity?????????isUsingState??????


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_device_detail);
        listener = new Listener();
        binding.setListener(listener);
        areaList = SPUtil.getListData(InspectionAreaList, InspectionAreaBean.class);
        currentAreaIndex = (int) SPUtil.getData(SPString.InspectionAreaPosition, 0);
        if (isRandomInspection) { //???????????????????????????????????????????????????
            initData();
            allList.clear();
            allList.addAll(SPUtil.getListData(SPString.InspectionSpecList, InspectionDeviceDetailBean.class));
            initItem();
            binding.checkRl.setVisibility(View.GONE);
        } else {   //???????????????
            getData(deviceId);
        }
    }

    private void initData() {
        deviceList = SPUtil.getListData(InspectionDeviceList, InspectionDeviceListBean.class);
        isUsingState = deviceList.get(devicePosition).getIsUsingStatus();
        binding.inspectionDeviceDetailDeviceName.setText(deviceList.get(devicePosition).getDeviceName());
    }

    private void getData(int deviceId) {
        initData();
        RetrofitUtil.getApi().getDeviceSpecList(taskId, areaId, deviceId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<InspectionDeviceDetailBean>>(mContext, true, "??????????????????") {

                    @Override
                    public void onSuccess(List<InspectionDeviceDetailBean> bean) {
                        allList.clear();
                        allList.addAll(bean);
                        if (allList.size() > 0) {
                            initItem();
                        } else {
                            setCheckButton(isUsingState);
                        }
                        if (isDeviceCheckFinish) { //????????????????????????????????????????????????
                            binding.nextDevice.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //??????????????????item 1???2???????????? 3????????? 4??????
    private void initItem() {
        instrumentList.clear();
        itemList.clear();
        allPicFileList.clear();
        picMap.clear();
        binding.inspectionDetailLinear.removeAllViews();
        String type;
        for (int i = 0; i < allList.size(); i++) {
            type = allList.get(i).getSpecType();
            if (type.equals("1") || type.equals("2")) { // 1?????? 2??????
                addInstrumentItemView(i, allList.get(i));
                instrumentList.add(allList.get(i));
            } else if (type.equals("3") || type.equals("4")) { // 3????????? 4??????
                if (!isDeviceCheckFinish) { //?????????????????????????????????
                    setUncheckData(i);
                }
                addPointOrOtherItemView(i, type);
            }
        }

        initView(isUsingState);
    }

    private void addPointOrOtherItemView(int position, String type) {
        InspectionPointLineLayout layout = new InspectionPointLineLayout(InspectionDeviceDetailActivity.this, position, type);
        String name;
        if (allList.get(position).getSpecType().equals("3")) {
            name = allList.get(position).getPointName();
        } else {
            name = allList.get(position).getOtherSpec();

        }
        layout.initItemWidthEdit(name, View.INVISIBLE);

        if (name.contains(closeOrOpen)) {
            layout.setShowOpenOrClose(true);
        } else {
            layout.setShowOpenOrClose(false);
        }

        if (name.contains(closeOrOpen)) {
            if (allList.get(position).getResult().equals("???")) {
                layout.getCheckBox_open().setChecked(true);
                layout.getCheckBox_close().setChecked(false);
            } else if (allList.get(position).getResult().equals("???")) {
                layout.getCheckBox_open().setChecked(false);
                layout.getCheckBox_close().setChecked(true);
            }
        }


        layout.getCheckBox_open().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layout.getCheckBox_close().setChecked(false);
                    //   uploadBeanList.get(layout.getPos()).setResult("???");
                    allList.get(layout.getPos()).setResult("???");
                } else {
                    layout.getCheckBox_close().setChecked(true);
                }
            }
        });

        layout.getCheckBox_close().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layout.getCheckBox_open().setChecked(false);
                    //uploadBeanList.get(layout.getPos()).setResult("???");
                    allList.get(layout.getPos()).setResult("???");
                } else {
                    layout.getCheckBox_open().setChecked(true);
                }
            }
        });


        if (allList.get(position).getResultType() == null ||
                allList.get(position).getResultType() == QUALIFIED) { //null????????????
            layout.setCheckBox(false); //?????????
            layout.setTakePicVisible(View.INVISIBLE);
        } else {          //?????????
            layout.setCheckBox(true); //??????
            layout.setTakePicVisible(View.VISIBLE);
        }
        layout.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {  //?????? ??????
                setCheckData(layout.getPos());
                layout.setTakePicVisible(View.VISIBLE);
            } else {   //????????? ??????
                setUncheckData(layout.getPos());
                layout.setTakePicVisible(View.INVISIBLE);
            }
        });
        //??????????????????????????????
        if (isDeviceCheckFinish) {
            layout.getCheckBox().setEnabled(false);
            layout.setTakePicVisible(View.GONE);
            if (name.contains(closeOrOpen)) {
                layout.getCheckBox_open().setEnabled(false);
                layout.getCheckBox_close().setEnabled(false);
            }
        }
        layout.getImageView().setOnClickListener(v -> {
            Intent intent = new Intent(InspectionDeviceDetailActivity.this, InspectionTakePhotoActivity.class);
            intent.putExtra("id", allList.get(layout.getPos()).getId());
            intent.putExtra("type", Constants.FileType32);
            intent.putExtra("des", allList.get(layout.getPos()).getResultDescription());
            Bundle bundle = new Bundle();
            if (picMap.containsKey(allList.get(layout.getPos()).getId())) {
                bundle.putSerializable("picList", (Serializable) picMap.get(allList.get(layout.getPos()).getId()));
                intent.putExtras(bundle);
                intent.putExtra("hasPic", true);
            } else {
                intent.putExtra("hasPic", false);
            }
            startActivityForResult(intent, request_code);
        });
        binding.inspectionDetailLinear.addView(layout);
        itemList.add(layout);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addInstrumentItemView(int pos, InspectionDeviceDetailBean bean) {
        InspectionDeviceDetailBean currentBean = bean;
        InspectionEditLineLayout editText = new InspectionEditLineLayout(mContext, pos);
        editText.initItemWidthEdit(0, currentBean.getAssetSpecName(), currentBean.getAssetSpecUnit(), "?????????");
        editText.showArrow(false);
        if (currentBean.getResultDescription() == null || currentBean.getResultDescription().isEmpty()) {  //???????????????????????????
            editText.setTextContent(currentBean.getAssetSpecName());
            editText.setEditContent(currentBean.getResult());
        } else {
            if (currentBean.getResultDescription().equals("??????")) {  //????????????????????????????????????????????????????????????????????????????????????
                editText.setTextContent(currentBean.getAssetSpecName());
                editText.setEditContent(currentBean.getResult());
            } else {                                          //?????????????????????????????????????????????
                editText.setTextContent(Html.fromHtml(currentBean.getAssetSpecName() + "<font color=\"#ff0000\">  " + currentBean.getResultDescription() + "</font>"));
                editText.showArrow(true);
                editText.setEditContent(currentBean.getResult());
            }
        }
        //??????????????????????????????
        if (isDeviceCheckFinish) {
            editText.getEdit().setFocusable(false);
        }
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TOBTO_position = editText.getPos();//position?????????????????????????????????
                if (AreaDistinguishActivity.bConnect) {
                    if (TextUtils.equals(allList.get(editText.getPos()).getAssetSpecUnit(), "???")) {
                        //   showBleMeasureTemperatureLoading();
                        sampleTemp(EMI);
                    }
                    if (TextUtils.equals(allList.get(editText.getPos()).getAssetSpecUnit(), "mm/s")) {
                        showBleMeasureVibrationLoading();
                        sampleVib(LEN, FREQ);
                    }
                }
            }
        });

        editText.getEdit().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    int currentPosition = editText.getPos();
                    String result = InspectionMatchUtil.match(editText.getPos(), s.toString(), allList.get(currentPosition).getAssetspecConditions(), allList);
                    if (s.toString().substring(0, 1).contains(".")) {
                        ((InspectionEditLineLayout) itemList.get(editText.getPos())).setEditContent("0" + s);
                        ((InspectionEditLineLayout) itemList.get(editText.getPos())).getEdit().setSelection(s.length() + 1);
                    }
                    allList.get(currentPosition).setResult(s.toString());
                    if (result.equals("??????")) {  //??????????????????
                        allList.get(currentPosition).setResultType(QUALIFIED); //1?????????
                        ((InspectionEditLineLayout) itemList.get(editText.getPos())).setTextContent(allList.get(editText.getPos()).getAssetSpecName());
                        allList.get(currentPosition).setResultDescription(result);
                        editText.showArrow(false);
                    } else {                        //??????
                        allList.get(currentPosition).setResultType(UNQUALIFIED);//0?????????
                        ((InspectionEditLineLayout) itemList.get(currentPosition)).setIvRightIcon(R.drawable.takepic);
                        allList.get(currentPosition).setResultDescription(result);
                        ((InspectionEditLineLayout) itemList.get(editText.getPos())).setTextContent(Html.fromHtml(allList.get(editText.getPos()).getAssetSpecName() + "<font color=\"#ff0000\">  " + result + "</font>"));
                        editText.showArrow(true);
                    }
                } else {
                    ((InspectionEditLineLayout) itemList.get(editText.getPos())).setTextContent(allList.get(editText.getPos()).getAssetSpecName());
                    editText.showArrow(false);
                }
            }
        });

        editText.getIvRightIcon().setOnClickListener(v -> {
            Intent intent = new Intent(InspectionDeviceDetailActivity.this, InspectionTakePhotoActivity.class);
            intent.putExtra("id", allList.get(editText.getPos()).getId());
            intent.putExtra("from", allList.get(editText.getPos()).getSpecType()); //?????????3????????????1?????????2
            intent.putExtra("type", Constants.FileType32);
            intent.putExtra("des", allList.get(editText.getPos()).getResultDescription());
            Bundle bundle = new Bundle();
            if (picMap.containsKey(allList.get(editText.getPos()).getId())) { //???????????????????????????????????????????????????????????????
                bundle.putSerializable("picList", (Serializable) picMap.get(allList.get(editText.getPos()).getId()));
                intent.putExtras(bundle);
                intent.putExtra("hasPic", true);
            } else {
                intent.putExtra("hasPic", false);
            }
            startActivityForResult(intent, request_code);
        });
        itemList.add(pos, editText);
        binding.inspectionDetailLinear.addView(editText);
    }

    /**
     * ??????
     */
    public void sampleVib(final int len, final int freq) {
        LogUtil.i("zq", "sampleVib");
        isMeasureTemperature = true;
        BleClient.getInstance().sampleVib(len, freq, new SampleVibCallbackImpl());
    }

    private class SampleVibCallbackImpl implements BleInterfaces.SampleVibCallback {
        @Override
        public void onFail(@androidx.annotation.Nullable String msg) {
            ToastUtil.show(mContext, "????????????" + msg);
        }

        @Override
        public void onReceiveVibData(short[] vibData, float coe) {
            //?????????????????????????????????????????????????????????????????????????????????????????????????????????
            final short[] vData = vibData;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Convert c = new Convert();
                        Convert.MeasureData md = c.waveData(shortArrayToList(vData), FREQ);
                        //   md.getMeasValueSpeed();//??????????????????
                        String temp = "";
                        String vib = String.valueOf(md.getMeasValueSpeed());
                        if (vib.length() > 4) {
                            temp = vib.substring(0, 4);
                        } else {
                            temp = vib;
                        }
                        String finalTemp = temp;
                        InspectionDeviceDetailActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setData(finalTemp);
                            }
                        });
                        if (alertDialogForVibra.isShowing()) {
                            alertDialogForVibra.dismiss();
                        }
                    } catch (Exception ex) {
                        ToastUtil.show(mContext, ex.getMessage());
                    }
                }
            }).start();

        }
    }

    List<Short> shortArrayToList(short[] shorts) {
        List<Short> shortList = new ArrayList<>(shorts.length);
        for (short anInt : shorts) {
            shortList.add(anInt);
        }
        return shortList;
    }


    /**
     * ??????
     *
     * @param emi ???????????????
     */
    private void sampleTemp(float emi) {
        BleClient.getInstance().sampleTemp(emi, new BleInterfaces.SampleTempCallback() {
            @Override
            public void onReceiveTemp(float v) {
                LogUtil.i("zq onReceiveTemp", String.valueOf(v).substring(0, 4));
                isMeasureTemperature = true; //?????????????????? = true
                String temp = "";
                String vib = String.valueOf(v);
                if (vib.length() > 4) {
                    temp = vib.substring(0, 4);
                } else {
                    temp = vib;
                }

                String finalTemp = temp;
                InspectionDeviceDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setData(finalTemp);
                    }
                });
                stopSampleTemp();
            }

            @Override
            public void onFail(String s) {
                Toast.makeText(InspectionDeviceDetailActivity.this, "????????????: " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * ????????????
     */
    private void stopSampleTemp() {
        BleClient.getInstance().stopSampleTemp(new BleInterfaces.ActionCallback() {
            @Override
            public void onSuccess() {
                isMeasureTemperature = false; //?????????????????? = false
                LogUtil.i("zq", "????????????");
            }

            @Override
            public void onFail(String s) {
                Toast.makeText(InspectionDeviceDetailActivity.this, "??????????????????: " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request_code && resultCode == RESULT_OK) {
            String des;
            if (data.getExtras() != null) {
                des = data.getStringExtra("des");
            } else {
                des = "";
            }
            String id;
            List<PicBean> picList = SPUtil.getListData(SPString.InspectionPicList, PicBean.class);
            if (picList.size() != 0) {  //????????????????????????????????????
                id = picList.get(0).getId();  //????????????????????????id
                picMap.put(Integer.parseInt(id), picList);
                for (int i = 0; i < allList.size(); i++) {
                    if (allList.get(i).getId() == Integer.parseInt(id)) {  //???list?????????????????????????????????
                        des = des.trim();
                        if (!des.isEmpty()) {
                            allList.get(i).setResultDescription(des);
                        }
                        if (allList.get(i).getSpecType().equals("1") || allList.get(i).getSpecType().equals("2")) {
                            ((InspectionEditLineLayout) itemList.get(i)).showRightIconText(true);
                        } else {
                            ((InspectionPointLineLayout) itemList.get(i)).setIconTextVisible(true);
                        }
                        break;
                    }
                }
            }
        }
    }

    private void setCheckData(int position) {
        allList.get(position).setResult("??????");
        allList.get(position).setResultType(UNQUALIFIED);
    }

    private void setUncheckData(int position) {
        allList.get(position).setResult("??????");
        allList.get(position).setResultType(QUALIFIED);
    }

    //state?????????4?????????null???10???20???30???
    private void initView(int state) {
        isUsingState = state;
        setCheckButton(state);  //??????????????????????????????
        binding.nextDevice.setText(nextDevice);

        if (devicePosition == deviceList.size() - 1) {  //????????????????????????????????????????????????????????????????????????????????? ????????????
            binding.nextDevice.setText(nextArea);

            if (areaList.size() == 1) {
                binding.nextDevice.setText(finishInspection);
            }

            int a = (currentAreaIndex + 1) % areaList.size();  //????????????????????????????????????????????????????????????????????????????????? ????????????
            if (areaList.get(a).getStatus() == 1) {
                binding.nextDevice.setText(finishInspection);
            }
        }

        if (isRandomInspection) {  //?????????????????????
            binding.nextDevice.setText(finishInspection);
        }
    }

    private void setInstrumentListGone() {
        if (instrumentList.size() > 0) {
            if (itemList.get(0).getVisibility() == View.VISIBLE) {
                for (int i = 0; i < instrumentList.size(); i++) {
                    itemList.get(i).setVisibility(View.GONE);
                }
                if (instrumentList.size() == allList.size()) {
                    binding.inspectionDetailNoInstrumentText.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setInstrumentVisible() {
        if (instrumentList.size() > 0) {
            if (itemList.get(0).getVisibility() == View.GONE) {
                for (int i = 0; i < instrumentList.size(); i++) {
                    itemList.get(i).setVisibility(View.VISIBLE);
                }
            }
            if (binding.inspectionDetailNoInstrumentText.getVisibility() == View.VISIBLE) {
                binding.inspectionDetailNoInstrumentText.setVisibility(View.GONE);
            }
        }
    }

    private void setAllViewGone(boolean visible) {
        if (visible) {
            binding.inspectionDetailLinear.setVisibility(View.GONE);
        } else {
            binding.inspectionDetailLinear.setVisibility(View.VISIBLE);
        }
        setOverhaulTextVisible(visible);
    }

    private void setOverhaulTextVisible(boolean visible) {
        if (visible) {
            binding.inspectionDetailNoInstrumentOverhaulText.setVisibility(View.VISIBLE);
        } else {
            binding.inspectionDetailNoInstrumentOverhaulText.setVisibility(View.GONE);
        }
    }

    //????????????????????????????????????
    private void setCheckButton(int usingStatus) {
        isUsingState = usingStatus;
        if (usingStatus == spare) { //20 ??????
            binding.useYun.setChecked(false);
            binding.useBei.setChecked(true);
            binding.useWei.setChecked(false);
            setAllViewGone(false);
            setInstrumentListGone();
            setOverhaulTextVisible(false);
        } else if (usingStatus == repair) { //30 ??????
            binding.useYun.setChecked(false);
            binding.useBei.setChecked(false);
            binding.useWei.setChecked(true);
            setAllViewGone(true);
        } else {  //10 ??????
            binding.useYun.setChecked(true);
            binding.useBei.setChecked(false);
            binding.useWei.setChecked(false);
            setAllViewGone(false);
            setInstrumentVisible();
            setOverhaulTextVisible(false);
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
                            if (allPicFileList.size() == picUploadSuccessNum) { //?????????????????????????????????????????????
                                listener.getAndHandleFragmentData();
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

    private void submitData() {
        //?????????????????????null
        if (isUsingState == 0) {
            isUsingState = 10;
        }

        int tempIsUsingState;
        if (isRandomInspection) {
            tempIsUsingState = isUsingStateFromSelectActivity;
        } else {
            tempIsUsingState = isUsingState;
        }

        RetrofitUtil.getApi().submitDeviceData(deviceList.get(devicePosition).getId(), tempIsUsingState, uploadBeanList)
                .compose(new SchedulerTransformer<>())  //??????????????????
                .subscribe(new DialogObserver<String>(mContext, true, "??????????????????") { //??????????????????
                    @Override
                    public void onSuccess(String bean) {  //??????????????????
                        String btnStr = binding.nextDevice.getText().toString();  //?????????????????????
                        if (btnStr.equals(nextDevice)) { //???????????????????????????????????????????????????
                            devicePosition += 1;  //?????????????????????????????????index+1
                            if (devicePosition < deviceList.size()) {  //????????????index??????????????????
                                getData(deviceList.get(devicePosition).getDeviceId());  //????????????index?????????????????????
                            }
                        } else if (btnStr.equals(nextArea)) { //???????????????????????????????????????????????????
                            areaList.get(currentAreaIndex).setStatus(1);
                            currentAreaIndex += 1;
                            if (currentAreaIndex == areaList.size()) {
                                currentAreaIndex %= areaList.size();
                            }
                            SPUtil.putData(InspectionAreaList, areaList);
                            SPUtil.putData(SPString.InspectionAreaPosition, currentAreaIndex);
                            ARouter.getInstance().build(RouteString.InspectionDeviceListActivity).navigation();
                        } else if (btnStr.equals(finishInspection)) { //?????????????????????????????????????????????
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        picUploadSuccessNum = 0;
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    public void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public class Listener {
        public void setCheck(int state) {
            isUsingState = state;
            initView(state);
        }

        //???????????????????????????????????????
        public boolean checkDataAllWrite() {
            if (isUsingState == 10) {    //??????????????????
                if (instrumentList != null && instrumentList.size() > 0) {
                    for (int i = 0; i < instrumentList.size(); i++) {
                        if (instrumentList.get(i).getResult() == null ||
                                instrumentList.get(i).getResult().isEmpty() ||
                                instrumentList.get(i).getResult().equals("")) {
                            ToastUtil.show(mContext, "????????????????????????");
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        public void getPic() {
            hideSoftKeyboard(InspectionDeviceDetailActivity.this);
            if (isUsingState == 30) {  //?????????????????????????????????????????????
                getAndHandleFragmentData();

                return;
            }

            //???????????????????????????
            if (checkDataAllWrite()) {

            } else {
                return;
            }

            //???????????????20??????30??????????????????????????????
            if (isUsingState != 10) {
                for (int i = 0; i < instrumentList.size(); i++) {
                    if (picMap.containsKey(instrumentList.get(i).getId())) {
                        picMap.remove(instrumentList.get(i).getId());
                    }
                }
            }

            //????????????????????????
            int checkPic = 0;
            List<InspectionDeviceDetailBean> tempList = new ArrayList<>();
            tempList.addAll(allList);
            if (isUsingState != 10) {  //??????????????????????????????
                tempList.removeAll(instrumentList);
            }
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i).getResultType().equals(UNQUALIFIED)) {
                    if (picMap.containsKey(tempList.get(i).getId())) {

                    } else {
                        ToastUtil.show(mContext, "?????????????????????");
                        checkPic = 1;
                        break;
                    }
                }
            }

            if (checkPic > 0) {
                return;
            }

            allPicFileList.clear();
            if (picMap != null) {
                for (Map.Entry<Integer, List<PicBean>> entry : picMap.entrySet()) {
                    allPicFileList.addAll(entry.getValue());
                }
            }

            if (allPicFileList.size() > 0) {
                picUploadSuccessNum = 0;
                for (int i = 0; i < allPicFileList.size(); i++) {
                    submitPic(allPicFileList.get(i).getFile(),
                            allPicFileList.get(i).getId(), Constants.FileType32);
                }
            } else {
                getAndHandleFragmentData();
            }
        }

        public void getAndHandleFragmentData() {
            if (checkDataAllWrite()) {
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);

                int userId = (int) SPUtil.getData(SPString.UserId, 1);

                uploadBeanList.clear();
                if (isUsingState == 30) {  //?????????????????????????????????????????????
                    submitData();
                } else {
                    List<InspectionDeviceDetailBean> newAllList = new ArrayList<>();//?????????????????????????????????????????????????????????????????????????????????
                    newAllList = allList;
                    if (isUsingState == 20) {
                        newAllList.removeAll(instrumentList);
                    }
                    for (int i = 0; i < newAllList.size(); i++) {
                        InspectionUploadBean bean = new InspectionUploadBean();
                        if (newAllList.get(i).getSpecType().equals("4") && newAllList.get(i).getOtherSpec().contains(closeOrOpen)) {
                            if (newAllList.get(i).getResult().equals("???") || newAllList.get(i).getResult().equals("???")) {
                                bean.setResult(allList.get(i).getResult());
                            } else {
                                ToastUtil.show(mContext, "?????????????????????");
                                return;
                            }
                        } else {
                            bean.setResult(newAllList.get(i).getResult());
                        }
                        bean.setId(newAllList.get(i).getId());
                        bean.setInspectionTime(time);
                        bean.setResultType(newAllList.get(i).getResultType());
                        bean.setResultDescription(newAllList.get(i).getResultDescription());
                        bean.setInspector(userId);
                        bean.setStatus(1);
                        if (newAllList.get(i).getSpecType().equals("1") || newAllList.get(i).getSpecType().equals("2")) {
                            bean.setSpecName(newAllList.get(i).getAssetSpecName());
                        } else if (newAllList.get(i).getSpecType().equals("3")) {
                            bean.setSpecName(newAllList.get(i).getPointName());
                        } else if (newAllList.get(i).getSpecType().equals("4")) {
                            bean.setSpecName(newAllList.get(i).getOtherSpec());
                        }
                        uploadBeanList.add(bean);
                    }
                    submitData();
                }
            }
        }
    }


    public void setData(String value) {
        Log.i("zq onEvent", "setTOBTOData : " + value);
        ((InspectionEditLineLayout) itemList.get(TOBTO_position)).setEditContent(value);
        ((InspectionEditLineLayout) itemList.get(TOBTO_position)).getEdit().setSelection(value.length());

    }
// ???????????????dialog
//    private void showBleMeasureTemperatureLoading() {
//        dialogBuilderForTemp = new androidx.appcompat.app.AlertDialog.Builder(mContext);
//        dialogBuilderForTemp.setTitle("??????????????????");
//        dialogBuilderForTemp.setMessage("??????????????????????????????");
//        dialogBuilderForTemp.setCancelable(false);  //?????????false????????????back??????????????????????????????????????????
//        //????????????????????????
//        dialogBuilderForTemp.setPositiveButton("??????", (dialog, which) -> {
//                    //??????????????????
//                    //???00000?????????????????????
//                    stopSampleTemp();
//                    dialog.dismiss();
//                }
//        );
//        //??????????????????????????????????????????????????????
//        AlertDialog alertDialog = dialogBuilderForTemp.create();
//        //????????????????????????
//        alertDialog.show();
//    }

    private void showBleMeasureVibrationLoading() {
        AlertDialog.Builder dialogBuilderForVibra = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        dialogBuilderForVibra.setTitle("??????????????????");
        dialogBuilderForVibra.setMessage("?????????...");
        dialogBuilderForVibra.setCancelable(true);  //?????????false????????????back??????????????????????????????????????????

        //??????????????????????????????????????????????????????
        alertDialogForVibra = dialogBuilderForVibra.create();
        //????????????????????????
        alertDialogForVibra.show();
    }

    @Override
    protected void onDestroy() {
        int resultCode = 2;
        setResult(resultCode);
        finish();
        super.onDestroy();
    }
}
