package com.etuo.kucun.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseActivity;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.callback.DialogCallback;
import com.etuo.kucun.model.OutBoundDetailsModel;
import com.etuo.kucun.model.ScanInfoModel;
import com.etuo.kucun.model.ScanInfoOkModel;
import com.etuo.kucun.model.ScanTpInfoCodeModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.ui.adapter.ScanCodeInfoListAdapter;
import com.etuo.kucun.utils.Convert;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.NetUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.StringUtil;
import com.etuo.kucun.utils.UrlTools;
import com.lzy.okgo.OkGo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class NewMipcaActivityCapture extends BaseActivity implements ScanCodeInfoListAdapter.OnClickBtItem, QRCodeView.Delegate {


    @BindView(R.id.zbarview)
    ZXingView mZBarView;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_hide)
    TextView mTvHide;
    @BindView(R.id.tv_open_flashlight)
    TextView mTvOpenFlashlight;
    @BindView(R.id.my_listview)
    ListView mMyListview;
    @BindView(R.id.tv_scan_status_name)
    TextView mTvScanStatusName;
    @BindView(R.id.tv_scan_status_num)
    TextView mTvScanStatusNum;
    @BindView(R.id.tv_ok)
    TextView mTvOk;
    @BindView(R.id.l_tp_info)
    LinearLayout mLTpInfo;

    private MediaPlayer mediaPlayer;

    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

    private String palletModel = "";
    private String stockType;

    private String fromType = "";

    private String orderType, orderId, queryType, recordId, companyId, orderNum;// 订单类型 订单id, 操作类型 出入库ID 公司 ID, 订单编号

    private List<ScanTpInfoCodeModel> mScanTpInfoCodeModels = new ArrayList<>();
    private List<OutBoundDetailsModel.DataBean> mOutBoundListModel = new ArrayList<>();
    private ScanCodeInfoListAdapter mListAdapter;
    private Map<String, List<String>> tpInfoTypeDataMap = new HashMap<>();

    private boolean isHaveOpenLight;// 是否已经打开闪光灯

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_list_item_new);
        ButterKnife.bind(this);


        // zxing 监听
        mZBarView.setDelegate(this);

        mZBarView.closeFlashlight();
        isHaveOpenLight = false;


        fromType = getIntent().getStringExtra(ExtraConfig.TypeCode.FROM_INTTENT);
        orderType = getIntent().getStringExtra(ExtraConfig.TypeCode.SCAN_ORDER_TYPE);

        if (!StringUtil.isEmpty(fromType) && fromType.equals(ExtraConfig.TypeCode.HOME_FRAGMENT)) {
            mLTpInfo.setVisibility(View.GONE);
        } else if (!StringUtil.isEmpty(fromType) && fromType.equals(ExtraConfig.TypeCode.WEB_INTENT_BY_OUT)) {
            mLTpInfo.setVisibility(View.VISIBLE);

            queryType = getIntent().getStringExtra(ExtraConfig.TypeCode.SCAN_QUERY_TYPE);
            orderId = getIntent().getStringExtra(ExtraConfig.TypeCode.SCAN_OREDER_ID);
            mOutBoundListModel = (List<OutBoundDetailsModel.DataBean>) getIntent().getSerializableExtra("tpDatas");
            recordId = getIntent().getStringExtra(ExtraConfig.TypeCode.SCAN_RECORD_ID);
            companyId = getIntent().getStringExtra(ExtraConfig.TypeCode.SCAN_COMPANYID);
        } else if (!StringUtil.isEmpty(fromType) && fromType.equals(ExtraConfig.TypeCode.WEB_INTENT_BY_IN)) {
            mLTpInfo.setVisibility(View.VISIBLE);

            queryType = getIntent().getStringExtra(ExtraConfig.TypeCode.SCAN_QUERY_TYPE);
            orderId = getIntent().getStringExtra(ExtraConfig.TypeCode.SCAN_OREDER_ID);
            recordId = getIntent().getStringExtra(ExtraConfig.TypeCode.SCAN_RECORD_ID);
            companyId = getIntent().getStringExtra(ExtraConfig.TypeCode.SCAN_COMPANYID);
            mOutBoundListModel = (List<OutBoundDetailsModel.DataBean>) getIntent().getSerializableExtra("tpDatas");
            stockType = getIntent().getStringExtra(ExtraConfig.TypeCode.INTENT_STORK_TYPE);
            palletModel = getIntent().getStringExtra(ExtraConfig.TypeCode.INTENT_PALLET_MODEL);
        } else if (!StringUtil.isEmpty(fromType) && fromType.equals(ExtraConfig.TypeCode.WEB_INTENT_BY_BREAKAGE)) {// 报损
            mLTpInfo.setVisibility(View.GONE);


        } else if (!StringUtil.isEmpty(fromType) && fromType.equals(ExtraConfig.TypeCode.WEB_INTENT_BY_SHOU)) {//收托
            mLTpInfo.setVisibility(View.VISIBLE);

            queryType = getIntent().getStringExtra(ExtraConfig.TypeCode.SCAN_QUERY_TYPE);
            orderId = getIntent().getStringExtra(ExtraConfig.TypeCode.SCAN_OREDER_ID);
            recordId = getIntent().getStringExtra(ExtraConfig.TypeCode.SCAN_RECORD_ID);
            //companyId = getIntent().getStringExtra(ExtraConfig.TypeCode.SCAN_COMPANYID);
        }


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();


        playBeep = true;
        AudioManager audioService = (AudioManager)this.getApplication().getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    protected void onStart() {
        super.onStart();
        mZBarView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别


        mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
    }

    @Override
    protected void onStop() {
        mZBarView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZBarView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
        mZBarView.closeFlashlight();
        isHaveOpenLight = false;
        OkGo.getInstance().cancelTag(this);

    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }


    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;


    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @OnClick({R.id.iv_close, R.id.tv_open_flashlight, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_open_flashlight://打开闪关灯
                openFlashLight();
                break;
            case R.id.tv_ok://确定
                    if (mScanTpInfoCodeModels == null || mScanTpInfoCodeModels.size() == 0) {
                        Toast.makeText(mContext,"请先添加托盘!",Toast.LENGTH_SHORT).show();
                    }else {
                        if (!StringUtil.isEmpty(fromType) && fromType.equals(ExtraConfig.TypeCode.WEB_INTENT_BY_OUT)) {
                            postTpListByOut(orderId, recordId, queryType);

                        } else if (!StringUtil.isEmpty(fromType) && fromType.equals(ExtraConfig.TypeCode.WEB_INTENT_BY_IN)) {

                            postTpListByIn(orderId, recordId, queryType, stockType);
                        } else if (!StringUtil.isEmpty(fromType) && fromType.equals(ExtraConfig.TypeCode.WEB_INTENT_BY_SHOU)) {
                            postTpListByShou(orderId, recordId, queryType, companyId);
                        }else {
                            Toast.makeText(mContext,"当前未扫码!",Toast.LENGTH_SHORT).show();
                        }
                    }
                break;
            default:
                break;
        }
    }

    private Activity getActivity() {
        return NewMipcaActivityCapture.this;
    }

    private void openFlashLight() {
        if (!getActivity().getPackageManager().hasSystemFeature("android.hardware.camera.flash")) {
            showToast("没有摄像头权限");
        } else {

            if (isHaveOpenLight) {
                isHaveOpenLight = false;
                mZBarView.closeFlashlight();
            } else {
                isHaveOpenLight = true;
                mZBarView.openFlashlight();
            }


        }

    }


    /**
     * 根据type 扫描二维码,进行不同的操作
     */


    private void getCodeInfoByType(final String palletNum, final String order_type) {
        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }

        OkGo.get(UrlTools.getInterfaceUrl(UrlTools.PALLET_ID))
                .tag(this)
                .params("token", PreferenceCache.getToken())// token
                .params("palletNum", palletNum)// 二维码结果
                .params("orderType", order_type)// 订单类型（0查看1收2转3还

                .execute(new DialogCallback<LzyResponse<ScanInfoModel>>(getActivity(), "") {
                    @Override
                    public void onSuccess(LzyResponse<ScanInfoModel> responseData, Call call, Response response) {

                        ScanInfoModel scanInfoModel = responseData.data;
                        if (StringUtil.isEmpty(scanInfoModel.getState()) || !scanInfoModel.getState().equals("200")) { // 200 成功时才跳转到下一页
                            ShowToast.toastTime(getActivity(), scanInfoModel.getMessage(), 3);
                            finish();
                            return;
                        }


                        String url1 = "";

                        if (null != scanInfoModel.getOrderType() && scanInfoModel.getOrderType().equals(ExtraConfig.TypeCode.TYPE_SCAN_TYPE1)) {

                            url1 = scanInfoModel.getPalletLink();
                        } else {
                            url1 = scanInfoModel.getOrderDetailsLink();

                        }

                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("LoadingUrl", UrlTools.getAllWebUrl(url1) +
                                "&branchId=" + scanInfoModel.getBranchId() + "&palletModel=" + palletModel);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.toastTime(getActivity(), e.getMessage().toString(), 3);
                    }
                });

    }

    /**
     * 出库盘点查询
     * <p>
     * token
     * palletNum		二维码
     * checkBillId		订单Id
     * recordId		出入库ID
     * inventoryState		扫描类型  1 添加 0 删减
     */
    private void getTpInfoByOut(final String palletNum, final String recordId, final String inventoryState) {
        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }

        OkGo.get(UrlTools.getInterfaceUrl(UrlTools.OUT_BOUND_SCAN_CODE))
                .tag(this)
                .params("token", PreferenceCache.getToken())// token
                .params("palletNum", palletNum)// 二维码结果
                .params("recordId", recordId)// 出入库ID
                .params("inventoryState", inventoryState)// 扫描类型  1 添加 0 删减

                .execute(new DialogCallback<LzyResponse<ScanTpInfoCodeModel>>(getActivity(), "") {
                    @Override
                    public void onSuccess(LzyResponse<ScanTpInfoCodeModel> responseData, Call call, Response response) {

                        ScanTpInfoCodeModel scanInfoModel = responseData.bean;
                        scanInfoModel.setPalletNum(palletNum);
                        getTpInfoByInOrOut(scanInfoModel, inventoryState, scanInfoModel.getMessage(), "1");

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.toastTime(getActivity(), e.getMessage().toString(), 3);
                    }
                });

    }

    private void getTpInfoByInOrOut(ScanTpInfoCodeModel scanInfoModel, final String inventoryState, String message, String type) {

        if (scanInfoModel != null && "1".equals(scanInfoModel.getState())) {
            if (!isHaveInList(scanInfoModel)) {
                List<String> list = new ArrayList<>();
                List<ScanTpInfoCodeModel> tpInfoCodeModels = getTpInfoListByModelType(scanInfoModel.getPalletModel());
                for (int i = 0; i < mOutBoundListModel.size(); i++) {
                    if (mOutBoundListModel.get(i).getPalletModel().equals(scanInfoModel.getPalletModel())) {

                        if (inventoryState.equals("1")) {
                            if (tpInfoCodeModels.size() < Integer.parseInt(mOutBoundListModel.get(i).getQuantity()) - Integer.parseInt(mOutBoundListModel.get(i).getEnterStockQuantity())) {
                                mScanTpInfoCodeModels.add(scanInfoModel);
                                if (mScanTpInfoCodeModels != null) {
                                    mListAdapter = new ScanCodeInfoListAdapter(NewMipcaActivityCapture.this, mScanTpInfoCodeModels);
                                    mMyListview.setAdapter(mListAdapter);
                                    mListAdapter.OnClickBtItem(NewMipcaActivityCapture.this);
                                }
                            } else {
                                if (type.equals("1")) {
                                    ShowToast.toastTime(getActivity(), scanInfoModel.getPalletModel() + "商品数量已超出出库数量", 3);
                                } else if (type.equals("2")) {
                                    ShowToast.toastTime(getActivity(), scanInfoModel.getPalletModel() + "商品数量已超出入库数量", 3);
                                }
                            }
                        } else {
                            mScanTpInfoCodeModels.add(scanInfoModel);
                            if (mScanTpInfoCodeModels != null) {
                                mListAdapter = new ScanCodeInfoListAdapter(NewMipcaActivityCapture.this, mScanTpInfoCodeModels);
                                mMyListview.setAdapter(mListAdapter);
                                mListAdapter.OnClickBtItem(NewMipcaActivityCapture.this);
                            }
                        }
                    }
                }

                tpInfoCodeModels = getTpInfoListByModelType(scanInfoModel.getPalletModel());
                for (ScanTpInfoCodeModel tpInfoCodeModel : tpInfoCodeModels) {
                    list.add(tpInfoCodeModel.getPalletNum());
                }
                tpInfoTypeDataMap.put(scanInfoModel.getModelId(), list);
            } else {
                ShowToast.toastTime(getActivity(), "此商品已添加", 3);
            }
            setTpNumCnt();
        } else if ("2".equals(scanInfoModel.getState()) || "3".equals(scanInfoModel.getState())) {
            ShowToast.toastTime(getActivity(), message, 3);
        } else {
            if (StringUtil.isEmpty(message)) {
                ShowToast.toastTime(getActivity(), "查无此商品", 3);
            } else {
                ShowToast.toastTime(getActivity(), message, 3);
            }

        }

    }

    private List<ScanTpInfoCodeModel> getTpInfoListByModelType(String modelType) {

        List<ScanTpInfoCodeModel> scanTpInfoCodeModels = new ArrayList<>();
        for (ScanTpInfoCodeModel dataBean : mScanTpInfoCodeModels) {

            if (dataBean.getPalletModel().equals(modelType)) {

                scanTpInfoCodeModels.add(dataBean);
            }
        }
        return scanTpInfoCodeModels;

    }

    /**
     * 入库盘点查询
     * <p>
     * token
     * palletNum		二维码
     * checkBillId		订单Id
     * recordId		出入库ID
     * inventoryState		扫描类型  1 添加 0 删减
     * companyId		公司ID
     */
    private void getTpInfoByIn(final String orderId, final String palletNum, final String recordId, final String inventoryState, final String stockType, final String palletModel) {
        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }

        OkGo.get(UrlTools.getInterfaceUrl(UrlTools.IN_BOUND_SCAN_CODE))
                .tag(this)
                .params("token", PreferenceCache.getToken())// token
                .params("checkBillId", orderId)// 订单id
                .params("palletNum", palletNum)// 二维码结果
                .params("recordId", recordId)// 出入库ID
                .params("inventoryState", inventoryState)// 扫描类型  1 添加 0 删减
                .params("stockType", stockType)//0:用户入库1:网点入库
                .params("palletModel", palletModel) // 托盘的型号
                .execute(new DialogCallback<LzyResponse<ScanTpInfoCodeModel>>(getActivity(), "") {
                    @Override
                    public void onSuccess(LzyResponse<ScanTpInfoCodeModel> responseData, Call call, Response response) {

                        ScanTpInfoCodeModel scanInfoModel = responseData.bean;
                        scanInfoModel.setPalletNum(palletNum);
                        getTpInfoByInOrOut(scanInfoModel, inventoryState, scanInfoModel.getMessage(), "2");

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.toastTime(getActivity(), e.getMessage().toString(), 3);
                    }
                });

    }

    /**
     * 收托查询
     * <p>
     * token
     * palletNum		二维码
     * checkBillId		订单Id
     * recordId		出入库ID
     * inventoryState		扫描类型  1 添加 0 删减
     * companyId		公司ID
     */
    private void getTpInfoByOrder(final String orderId, final String palletNum, final String recordId, final String inventoryState, final String companyId) {
        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }

        OkGo.get(UrlTools.getInterfaceUrl(UrlTools.RECEIVE_SCAN_CODE))
                .tag(this)
                .params("token", PreferenceCache.getToken())// token
                .params("checkBillId", orderId)// 订单id
                .params("palletNum", palletNum)// 二维码结果
                .params("inventoryState", inventoryState)// 扫描类型  1 添加 0 删减
                .execute(new DialogCallback<LzyResponse<ScanTpInfoCodeModel>>(getActivity(), "") {
                    @Override
                    public void onSuccess(LzyResponse<ScanTpInfoCodeModel> responseData, Call call, Response response) {

                        ScanTpInfoCodeModel scanInfoModel = responseData.bean;
                        scanInfoModel.setPalletNum(palletNum);

                        if (scanInfoModel != null && !StringUtil.isEmpty(scanInfoModel.getState()) && "1".equals(scanInfoModel.getState())) {
                            if (!isHaveInList(scanInfoModel)) {
                                mScanTpInfoCodeModels.add(scanInfoModel);
                                if (mScanTpInfoCodeModels != null) {
                                    mListAdapter = new ScanCodeInfoListAdapter(NewMipcaActivityCapture.this, mScanTpInfoCodeModels);
                                    mMyListview.setAdapter(mListAdapter);
                                    mListAdapter.OnClickBtItem(NewMipcaActivityCapture.this);
                                }
                            } else {
                                ShowToast.toastTime(getActivity(), "此商品已添加", 3);
                            }


                            setTpNumCnt();
                        } else if (scanInfoModel != null && !StringUtil.isEmpty(scanInfoModel.getState()) && "2".equals(scanInfoModel.getState())) {
                            ShowToast.toastTime(getActivity(), responseData.message, 3);
                        } else if (scanInfoModel != null && !StringUtil.isEmpty(scanInfoModel.getState()) && "3".equals(scanInfoModel.getState())) {
                            ShowToast.toastTime(getActivity(), responseData.message, 3);
                        } else {
                            if (StringUtil.isEmpty(responseData.message)) {
                                ShowToast.toastTime(getActivity(), "查无此商品", 3);
                            } else {
                                ShowToast.toastTime(getActivity(), responseData.message, 3);
                            }

                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.toastTime(getActivity(), e.getMessage().toString(), 3);
                    }
                });

    }


    /**
     * 刷新托盘的总数量
     */
    private void setTpNumCnt() {

        if (mScanTpInfoCodeModels != null) {
            mTvScanStatusNum.setText(mScanTpInfoCodeModels.size() + "");
        }
    }


    /**
     * 删除操作
     *
     * @param position
     * @param palletNum
     */
    @Override
    public void myOrderDeletClick(int position, String palletNum) {


        mScanTpInfoCodeModels.remove(position);
        mListAdapter.upData(mScanTpInfoCodeModels);
        mListAdapter.notifyDataSetChanged();
        setTpNumCnt();
    }

    /**
     * 判断是否在列表里
     *
     * @return
     */
    private boolean isHaveInList(ScanTpInfoCodeModel scanInfoModel) {

        for (int i = 0; i < mScanTpInfoCodeModels.size(); i++) {
            if (mScanTpInfoCodeModels.get(i).getPalletNum().equals(scanInfoModel.getPalletNum())) {
                return true;
            }
        }

        return false;

    }


    /**
     * 出库盘点 确定
     */
    private void postTpListByOut(final String orderId, final String recordId, final String inventoryState) {
        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < mScanTpInfoCodeModels.size(); i++) {
            stringList.add(mScanTpInfoCodeModels.get(i).getPalletNum());
        }
        String codeList = Convert.toJson(tpInfoTypeDataMap);
        OkGo.post(UrlTools.getInterfaceUrl(UrlTools.OUT_BOUND_SCAN_CODE_DETERMINE))
                .tag(this)
                .params("token", PreferenceCache.getToken())// token
                .params("checkBillId", orderId)// 订单编号
                .params("recordId", recordId)// 出入库ID
                .params("inventoryState", inventoryState)// 扫描类型  1 添加 0 删减
                .params("palletMap", codeList)//添加的二维码 code list

                .execute(new DialogCallback<LzyResponse<ScanInfoOkModel>>(getActivity(), "") {
                    @Override
                    public void onSuccess(LzyResponse<ScanInfoOkModel> responseData, Call call, Response response) {


                        ScanInfoOkModel scanInfoModel = responseData.data;

                        NewMipcaActivityCapture.this.setResult(RESULT_OK);

                        NewMipcaActivityCapture.this.finish();


                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.toastTime(getActivity(), e.getMessage().toString(), 3);
                    }
                });

    }


    /**
     * 入库盘点 确定
     */
    private void postTpListByIn(final String orderId, final String recordId, final String inventoryState, final String stockType) {
        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < mScanTpInfoCodeModels.size(); i++) {
            stringList.add(mScanTpInfoCodeModels.get(i).getPalletNum());
        }
        String codeList = Convert.toJson(tpInfoTypeDataMap);
        OkGo.post(UrlTools.getInterfaceUrl(UrlTools.IN_BOUND_SCAN_CODE_DETERMINE))
                .tag(this)
                .params("token", PreferenceCache.getToken())// token
                .params("checkBillId", orderId)// 订单编号
                .params("recordId", recordId)// 出入库ID
                .params("inventoryState", inventoryState)// 扫描类型  1 添加 0 删减
                .params("palletMap", codeList)//添加的二维码 code list
                .params("stockType", stockType)// 0用户入库 1网点入库
                .execute(new DialogCallback<LzyResponse<ScanInfoOkModel>>(getActivity(), "") {
                    @Override
                    public void onSuccess(LzyResponse<ScanInfoOkModel> responseData, Call call, Response response) {


                        ScanInfoOkModel scanInfoModel = responseData.data;

                        NewMipcaActivityCapture.this.setResult(RESULT_OK);

                        NewMipcaActivityCapture.this.finish();


                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.toastTime(getActivity(), e.getMessage().toString(), 3);
                    }
                });

    }

    /**
     * 收托盘点 确定
     */
    private void postTpListByShou(final String orderId, final String recordId, final String inventoryState, final String companyId) {
        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < mScanTpInfoCodeModels.size(); i++) {
            stringList.add(mScanTpInfoCodeModels.get(i).getPalletNum());
        }
        String codeList = Convert.toJson(stringList);
        OkGo.post(UrlTools.getInterfaceUrl(UrlTools.RECEIVE_SCAN_CODE_DETERMINE))
                .tag(this)
                .params("token", PreferenceCache.getToken())// token
                .params("checkBillId", orderId)// 订单编号
                //.params("recordId", recordId)// 出入库ID
                .params("inventoryState", inventoryState)// 扫描类型  1 添加 0 删减
                .params("palletNum", codeList)//添加的二维码 code list
                .execute(new DialogCallback<LzyResponse<ScanInfoOkModel>>(getActivity(), "") {
                    @Override
                    public void onSuccess(LzyResponse<ScanInfoOkModel> responseData, Call call, Response response) {


                        ScanInfoOkModel scanInfoModel = responseData.data;

                        NewMipcaActivityCapture.this.setResult(RESULT_OK);

                        NewMipcaActivityCapture.this.finish();


                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.toastTime(getActivity(), e.getMessage().toString(), 3);
                    }
                });

    }

    /**
     * <p>扫码报损查询
     * token
     * palletNum		二维码
     */
    private void getScanSearch(final String palletNum) {
        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }

        OkGo.post(UrlTools.getInterfaceUrl(UrlTools.DAMAGED_SCAN_SEARCH))
                .tag(this)
                .params("token", PreferenceCache.getToken())// token

                .params("palletNum", palletNum)// 二维码结果

                .execute(new DialogCallback<LzyResponse<ScanTpInfoCodeModel>>(getActivity(), "加载中...") {
                    @Override
                    public void onSuccess(LzyResponse<ScanTpInfoCodeModel> responseData, Call call, Response response) {


                        ScanTpInfoCodeModel scanInfoModel = responseData.bean;


                        if (scanInfoModel != null && !StringUtil.isEmpty(scanInfoModel.getState()) && "1".equals(scanInfoModel.getState())) {
                            Intent intent = new Intent(getActivity(), AddDamagedActivity.class);
                            intent.putExtra("damagedNum", scanInfoModel);
                            intent.putExtra("from", "1");//新增
                            intent.putExtra("tpOrContainer", scanInfoModel.getProductType());
                            startActivity(intent);
                            finish();

                        } else {
                            if (StringUtil.isEmpty(responseData.message)) {
                                ShowToast.toastTime(getActivity(), "查无此托盘", 3);
                            } else {
                                ShowToast.toastTime(getActivity(), responseData.message, 3);
                            }

                        }


                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.toastTime(getActivity(), e.getMessage().toString(), 3);
                    }
                });

    }

    /**
     * 扫描成功的回调
     *
     * @param s 扫描结果
     */

    @Override
    public void onScanQRCodeSuccess(String s) {

        playBeepSoundAndVibrate();
        String resultString = s;

        LogUtil.d("扫描结果 : " + resultString);
        mZBarView.startSpotDelay(1500); // 延迟2秒后开始识别
        // 震动
        vibrate();
        if (!StringUtil.isEmpty(fromType) && fromType.equals(ExtraConfig.TypeCode.HOME_FRAGMENT)) {
            mLTpInfo.setVisibility(View.GONE);
            getCodeInfoByType(resultString, orderType);
        } else if (!StringUtil.isEmpty(fromType) && fromType.equals(ExtraConfig.TypeCode.WEB_INTENT_BY_OUT)) {
            mLTpInfo.setVisibility(View.VISIBLE);

            getTpInfoByOut(resultString, recordId, queryType);

        } else if (!StringUtil.isEmpty(fromType) && fromType.equals(ExtraConfig.TypeCode.WEB_INTENT_BY_IN)) {
            mLTpInfo.setVisibility(View.VISIBLE);

            getTpInfoByIn(orderId, resultString, recordId, queryType, stockType, palletModel);

        } else if (!StringUtil.isEmpty(fromType) && fromType.equals(ExtraConfig.TypeCode.WEB_INTENT_BY_BREAKAGE)) {// 报损
            mLTpInfo.setVisibility(View.GONE);

            getScanSearch(resultString);


        } else if (!StringUtil.isEmpty(fromType) && fromType.equals(ExtraConfig.TypeCode.WEB_INTENT_BY_SHOU)) {//收托
            mLTpInfo.setVisibility(View.VISIBLE);

            getTpInfoByOrder(orderId, resultString, recordId, queryType, companyId);
        }

    }

    /**
     * 感光去的返回值
     *
     * @param b 是否需要打开手电筒
     */
    @Override
    public void onCameraAmbientBrightnessChanged(boolean b) {
//        LogUtil.i(TAG, "灯光:  " + b);

        if (isHaveOpenLight) {
            mTvOpenFlashlight.setVisibility(View.VISIBLE);

            Drawable dra = getResources().getDrawable(R.mipmap.icon_flash_light_open);
            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());

            mTvOpenFlashlight.setCompoundDrawables(null, dra, null, null);
        } else {
            Drawable dra = getResources().getDrawable(R.mipmap.icon_flash_light);
            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
            mTvOpenFlashlight.setCompoundDrawables(null, dra, null, null);
            if (b) {
                mTvOpenFlashlight.setVisibility(View.VISIBLE);
            } else {
                mTvOpenFlashlight.setVisibility(View.GONE);
            }
        }


    }

    /**
     * 相机调取报错
     */
    @Override
    public void onScanQRCodeOpenCameraError() {
        LogUtil.e(TAG, "打开相机出错");
    }
}