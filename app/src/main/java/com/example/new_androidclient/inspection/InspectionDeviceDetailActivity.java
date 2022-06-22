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
 * 设备巡检
 **/
@Route(path = RouteString.InspectionDeviceDetailActivity)
public class InspectionDeviceDetailActivity extends BaseActivity {
    private List<InspectionUploadBean> uploadBeanList = new ArrayList<>(); //上传数据的list
    private List<PicBean> allPicFileList = new ArrayList<>(); //所有参数密封点其它的照片list
    private ActivityInspectionDeviceDetailBinding binding;
    private int currentAreaIndex; //当前区域的索引，用于判断本页面按钮是否显示下一区域
    private Listener listener;
    //isUsingState是从上一个页面里存SPUtil的列表里的状态，是本页面设备在未巡检之前的状态。当用户选择状态后，修改本变量。
    //isUsingState可能值有null、10、20、30
    //isUsingState是计划巡检设备列表里的状态
    public static int isUsingState;

    private int working = 10; //运行
    private int spare = 20; //备用
    private int repair = 30; //维修

    public final int QUALIFIED = 1; //合格
    public final int UNQUALIFIED = 0; //不合格

    private int picUploadSuccessNum = 0;
    private int request_code = 1;

    private int TOBTO_position; //使用检测仪获取焦点的editLayout的position

    private List<InspectionDeviceDetailBean> allList = new ArrayList<>(); //设备所有参数列表

    private List<InspectionDeviceListBean> deviceList;  //设备列表
    private List<InspectionAreaBean> areaList; //区域列表
    private List<InspectionDeviceDetailBean> instrumentList = new ArrayList<>();//用来判断提交数据时，是否所有的参数都填写了
    private List<LinearLayout> itemList = new ArrayList<>(); //item列表
    private Map<Integer, List<PicBean>> picMap = new HashMap<>(); //用于存放每个参数的图片，key是参数的id，value是key对应参数的照片列表

    private String nextDevice = "下一设备";
    private String nextArea = "下一区域";
    private String finishInspection = "完成巡检";

    private String closeOrOpen = "开、关";

    private ProgressDialog progressDialog;

    private boolean isMeasureTemperature = false;
    private AlertDialog.Builder dialogBuilderForTemp;
    private AlertDialog alertDialogForVibra;

    private static final float EMI = 0.97f;    // 测温发射率
    private static final int LEN = 2;   // 采集长度, 4 字节整型, 单位 K, 不要超过 256K
    private static final int FREQ = 2000;    // 分析频率, 4 字节整型, 单位 Hz, 不要超过 40000Hz

    @Autowired()
    int areaId;

    @Autowired()
    int taskId;

    @Autowired()
    int deviceId;

    @Autowired()
    int devicePosition;  //现在正在巡检的设备在deviceList里面的下标

    @Autowired()
    boolean isDeviceCheckFinish = false;  //用于判断当前设备是否已巡检，true是已巡检

    @Autowired()
    boolean isRandomInspection;  //用于判断是否是随机巡检

    @Autowired()
    int isUsingStateFromSelectActivity; //随机巡检，从selectActivity过来的isUsingState状态


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_device_detail);
        listener = new Listener();
        binding.setListener(listener);
        areaList = SPUtil.getListData(InspectionAreaList, InspectionAreaBean.class);
        currentAreaIndex = (int) SPUtil.getData(SPString.InspectionAreaPosition, 0);
        if (isRandomInspection) { //是随机巡检，显示用户选择的设备参数
            initData();
            allList.clear();
            allList.addAll(SPUtil.getListData(SPString.InspectionSpecList, InspectionDeviceDetailBean.class));
            initItem();
            binding.checkRl.setVisibility(View.GONE);
        } else {   //是计划巡检
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
                .subscribe(new DialogObserver<List<InspectionDeviceDetailBean>>(mContext, true, "正在处理数据") {

                    @Override
                    public void onSuccess(List<InspectionDeviceDetailBean> bean) {
                        allList.clear();
                        allList.addAll(bean);
                        if (allList.size() > 0) {
                            initItem();
                        } else {
                            setCheckButton(isUsingState);
                        }
                        if (isDeviceCheckFinish) { //已巡检后再扫码进来把提交按钮隐藏
                            binding.nextDevice.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //按照类型添加item 1、2工艺运行 3密封点 4其他
    private void initItem() {
        instrumentList.clear();
        itemList.clear();
        allPicFileList.clear();
        picMap.clear();
        binding.inspectionDetailLinear.removeAllViews();
        String type;
        for (int i = 0; i < allList.size(); i++) {
            type = allList.get(i).getSpecType();
            if (type.equals("1") || type.equals("2")) { // 1工艺 2运行
                addInstrumentItemView(i, allList.get(i));
                instrumentList.add(allList.get(i));
            } else if (type.equals("3") || type.equals("4")) { // 3密封点 4其他
                if (!isDeviceCheckFinish) { //如果设备未巡检，初始化
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
            if (allList.get(position).getResult().equals("开")) {
                layout.getCheckBox_open().setChecked(true);
                layout.getCheckBox_close().setChecked(false);
            } else if (allList.get(position).getResult().equals("关")) {
                layout.getCheckBox_open().setChecked(false);
                layout.getCheckBox_close().setChecked(true);
            }
        }


        layout.getCheckBox_open().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layout.getCheckBox_close().setChecked(false);
                    //   uploadBeanList.get(layout.getPos()).setResult("开");
                    allList.get(layout.getPos()).setResult("开");
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
                    //uploadBeanList.get(layout.getPos()).setResult("关");
                    allList.get(layout.getPos()).setResult("关");
                } else {
                    layout.getCheckBox_open().setChecked(true);
                }
            }
        });


        if (allList.get(position).getResultType() == null ||
                allList.get(position).getResultType() == QUALIFIED) { //null或者合格
            layout.setCheckBox(false); //不选中
            layout.setTakePicVisible(View.INVISIBLE);
        } else {          //不合格
            layout.setCheckBox(true); //选中
            layout.setTakePicVisible(View.VISIBLE);
        }
        layout.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {  //选中 泄漏
                setCheckData(layout.getPos());
                layout.setTakePicVisible(View.VISIBLE);
            } else {   //不选中 合格
                setUncheckData(layout.getPos());
                layout.setTakePicVisible(View.INVISIBLE);
            }
        });
        //如果已巡检，不能操作
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
        editText.initItemWidthEdit(0, currentBean.getAssetSpecName(), currentBean.getAssetSpecUnit(), "请输入");
        editText.showArrow(false);
        if (currentBean.getResultDescription() == null || currentBean.getResultDescription().isEmpty()) {  //第一次时数据是空的
            editText.setTextContent(currentBean.getAssetSpecName());
            editText.setEditContent(currentBean.getResult());
        } else {
            if (currentBean.getResultDescription().equals("正常")) {  //第二次以后就有数据了，判断是否合格，如果合格，则直接显示
                editText.setTextContent(currentBean.getAssetSpecName());
                editText.setEditContent(currentBean.getResult());
            } else {                                          //要是不合格就显示描述字段的结果
                editText.setTextContent(Html.fromHtml(currentBean.getAssetSpecName() + "<font color=\"#ff0000\">  " + currentBean.getResultDescription() + "</font>"));
                editText.showArrow(true);
                editText.setEditContent(currentBean.getResult());
            }
        }
        //如果已巡检，不能操作
        if (isDeviceCheckFinish) {
            editText.getEdit().setFocusable(false);
        }
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TOBTO_position = editText.getPos();//position下面设置需要，不能注释
                if (AreaDistinguishActivity.bConnect) {
                    if (TextUtils.equals(allList.get(editText.getPos()).getAssetSpecUnit(), "℃")) {
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
                    if (result.equals("正常")) {  //比对结果正常
                        allList.get(currentPosition).setResultType(QUALIFIED); //1是正常
                        ((InspectionEditLineLayout) itemList.get(editText.getPos())).setTextContent(allList.get(editText.getPos()).getAssetSpecName());
                        allList.get(currentPosition).setResultDescription(result);
                        editText.showArrow(false);
                    } else {                        //异常
                        allList.get(currentPosition).setResultType(UNQUALIFIED);//0是异常
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
            intent.putExtra("from", allList.get(editText.getPos()).getSpecType()); //参数是3，密封点1，其它2
            intent.putExtra("type", Constants.FileType32);
            intent.putExtra("des", allList.get(editText.getPos()).getResultDescription());
            Bundle bundle = new Bundle();
            if (picMap.containsKey(allList.get(editText.getPos()).getId())) { //如果照片里有点击参数的图片，则传到拍照页面
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
     * 测振
     */
    public void sampleVib(final int len, final int freq) {
        LogUtil.i("zq", "sampleVib");
        isMeasureTemperature = true;
        BleClient.getInstance().sampleVib(len, freq, new SampleVibCallbackImpl());
    }

    private class SampleVibCallbackImpl implements BleInterfaces.SampleVibCallback {
        @Override
        public void onFail(@androidx.annotation.Nullable String msg) {
            ToastUtil.show(mContext, "测振失败" + msg);
        }

        @Override
        public void onReceiveVibData(short[] vibData, float coe) {
            //上面为传感器采集的原始加速度速度，根据不同应用场景和要求，可以进行转换
            final short[] vData = vibData;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Convert c = new Convert();
                        Convert.MeasureData md = c.waveData(shortArrayToList(vData), FREQ);
                        //   md.getMeasValueSpeed();//取速度有效值
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
     * 测温
     *
     * @param emi 测温发射率
     */
    private void sampleTemp(float emi) {
        BleClient.getInstance().sampleTemp(emi, new BleInterfaces.SampleTempCallback() {
            @Override
            public void onReceiveTemp(float v) {
                LogUtil.i("zq onReceiveTemp", String.valueOf(v).substring(0, 4));
                isMeasureTemperature = true; //正在测量温度 = true
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
                Toast.makeText(InspectionDeviceDetailActivity.this, "测温失败: " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 停止测温
     */
    private void stopSampleTemp() {
        BleClient.getInstance().stopSampleTemp(new BleInterfaces.ActionCallback() {
            @Override
            public void onSuccess() {
                isMeasureTemperature = false; //正在测量温度 = false
                LogUtil.i("zq", "停止测温");
            }

            @Override
            public void onFail(String s) {
                Toast.makeText(InspectionDeviceDetailActivity.this, "停止测温失败: " + s, Toast.LENGTH_SHORT).show();
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
            if (picList.size() != 0) {  //如果返回的图片列表不为空
                id = picList.get(0).getId();  //获取到图片的参数id
                picMap.put(Integer.parseInt(id), picList);
                for (int i = 0; i < allList.size(); i++) {
                    if (allList.get(i).getId() == Integer.parseInt(id)) {  //在list里找到与图片对应的参数
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
        allList.get(position).setResult("泄漏");
        allList.get(position).setResultType(UNQUALIFIED);
    }

    private void setUncheckData(int position) {
        allList.get(position).setResult("正常");
        allList.get(position).setResultType(QUALIFIED);
    }

    //state可能是4个值，null、10、20、30。
    private void initView(int state) {
        isUsingState = state;
        setCheckButton(state);  //设置运行、备用、维修
        binding.nextDevice.setText(nextDevice);

        if (devicePosition == deviceList.size() - 1) {  //判断是不是设备列表里最后一个设备，如果是，则按钮文字为 下一区域
            binding.nextDevice.setText(nextArea);

            if (areaList.size() == 1) {
                binding.nextDevice.setText(finishInspection);
            }

            int a = (currentAreaIndex + 1) % areaList.size();  //判断是不是区域列表里最后一个区域，如果是，则按钮文字是 完成巡检
            if (areaList.get(a).getStatus() == 1) {
                binding.nextDevice.setText(finishInspection);
            }
        }

        if (isRandomInspection) {  //如果是随机巡检
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

    //设置运行、备用、维修按钮
    private void setCheckButton(int usingStatus) {
        isUsingState = usingStatus;
        if (usingStatus == spare) { //20 备用
            binding.useYun.setChecked(false);
            binding.useBei.setChecked(true);
            binding.useWei.setChecked(false);
            setAllViewGone(false);
            setInstrumentListGone();
            setOverhaulTextVisible(false);
        } else if (usingStatus == repair) { //30 维修
            binding.useYun.setChecked(false);
            binding.useBei.setChecked(false);
            binding.useWei.setChecked(true);
            setAllViewGone(true);
        } else {  //10 运行
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
                .subscribe(new DialogObserver<String>(mContext, true, "正在上传图片") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("上传成功")) {
                            synchronized (this) {
                                picUploadSuccessNum++;
                            }
                            if (allPicFileList.size() == picUploadSuccessNum) { //如果图片都上传成功，则上传数据
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
        //说明运行状态是null
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
                .compose(new SchedulerTransformer<>())  //联网提交数据
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交参数") { //联网提交数据
                    @Override
                    public void onSuccess(String bean) {  //如果提交成功
                        String btnStr = binding.nextDevice.getText().toString();  //获取到按钮的字
                        if (btnStr.equals(nextDevice)) { //如果按钮的字是下一设备，则下一设备
                            devicePosition += 1;  //把现在设备在设备列表里index+1
                            if (devicePosition < deviceList.size()) {  //如果现在index小于设备列表
                                getData(deviceList.get(devicePosition).getDeviceId());  //获取新的index的查询设备数据
                            }
                        } else if (btnStr.equals(nextArea)) { //如果按钮的字是下一区域，则下一区域
                            areaList.get(currentAreaIndex).setStatus(1);
                            currentAreaIndex += 1;
                            if (currentAreaIndex == areaList.size()) {
                                currentAreaIndex %= areaList.size();
                            }
                            SPUtil.putData(InspectionAreaList, areaList);
                            SPUtil.putData(SPString.InspectionAreaPosition, currentAreaIndex);
                            ARouter.getInstance().build(RouteString.InspectionDeviceListActivity).navigation();
                        } else if (btnStr.equals(finishInspection)) { //如果按钮的字是完成巡检，则结束
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

        //检查是不是所有的参数都写了
        public boolean checkDataAllWrite() {
            if (isUsingState == 10) {    //需要检查参数
                if (instrumentList != null && instrumentList.size() > 0) {
                    for (int i = 0; i < instrumentList.size(); i++) {
                        if (instrumentList.get(i).getResult() == null ||
                                instrumentList.get(i).getResult().isEmpty() ||
                                instrumentList.get(i).getResult().equals("")) {
                            ToastUtil.show(mContext, "请把参数填写完整");
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        public void getPic() {
            hideSoftKeyboard(InspectionDeviceDetailActivity.this);
            if (isUsingState == 30) {  //如果是检修状态，不需要上传图片
                getAndHandleFragmentData();

                return;
            }

            //检查参数是否都填写
            if (checkDataAllWrite()) {

            } else {
                return;
            }

            //设备状态是20或者30时，把参数的照片删掉
            if (isUsingState != 10) {
                for (int i = 0; i < instrumentList.size(); i++) {
                    if (picMap.containsKey(instrumentList.get(i).getId())) {
                        picMap.remove(instrumentList.get(i).getId());
                    }
                }
            }

            //判断异常是否拍照
            int checkPic = 0;
            List<InspectionDeviceDetailBean> tempList = new ArrayList<>();
            tempList.addAll(allList);
            if (isUsingState != 10) {  //如果是备用和检修状态
                tempList.removeAll(instrumentList);
            }
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i).getResultType().equals(UNQUALIFIED)) {
                    if (picMap.containsKey(tempList.get(i).getId())) {

                    } else {
                        ToastUtil.show(mContext, "异常数据请拍照");
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
                if (isUsingState == 30) {  //如果是检修，直接提交，没有数据
                    submitData();
                } else {
                    List<InspectionDeviceDetailBean> newAllList = new ArrayList<>();//如果在备用状态下，没有参数，需要把提交参数在列表里去掉
                    newAllList = allList;
                    if (isUsingState == 20) {
                        newAllList.removeAll(instrumentList);
                    }
                    for (int i = 0; i < newAllList.size(); i++) {
                        InspectionUploadBean bean = new InspectionUploadBean();
                        if (newAllList.get(i).getSpecType().equals("4") && newAllList.get(i).getOtherSpec().contains(closeOrOpen)) {
                            if (newAllList.get(i).getResult().equals("开") || newAllList.get(i).getResult().equals("关")) {
                                bean.setResult(allList.get(i).getResult());
                            } else {
                                ToastUtil.show(mContext, "请选择开关状态");
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
// 测量温度的dialog
//    private void showBleMeasureTemperatureLoading() {
//        dialogBuilderForTemp = new androidx.appcompat.app.AlertDialog.Builder(mContext);
//        dialogBuilderForTemp.setTitle("正在测量温度");
//        dialogBuilderForTemp.setMessage("点击确定按钮停止测温");
//        dialogBuilderForTemp.setCancelable(false);  //设置为false，则点击back键或者弹窗外区域，弹窗不消去
//        //使用了匿名内部类
//        dialogBuilderForTemp.setPositiveButton("确定", (dialog, which) -> {
//                    //加入逻辑代码
//                    //对00000话框消失的方法
//                    stopSampleTemp();
//                    dialog.dismiss();
//                }
//        );
//        //使用对话框创建器来创建一个对话框对象
//        AlertDialog alertDialog = dialogBuilderForTemp.create();
//        //将对话框显示出来
//        alertDialog.show();
//    }

    private void showBleMeasureVibrationLoading() {
        AlertDialog.Builder dialogBuilderForVibra = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        dialogBuilderForVibra.setTitle("正在测量振动");
        dialogBuilderForVibra.setMessage("请稍等...");
        dialogBuilderForVibra.setCancelable(true);  //设置为false，则点击back键或者弹窗外区域，弹窗不消去

        //使用对话框创建器来创建一个对话框对象
        alertDialogForVibra = dialogBuilderForVibra.create();
        //将对话框显示出来
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
