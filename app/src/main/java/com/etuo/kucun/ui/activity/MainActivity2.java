package com.etuo.kucun.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseActivity;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.callback.DialogCallback;
import com.etuo.kucun.model.FTListModel;
import com.etuo.kucun.model.HomeModel;
import com.etuo.kucun.model.STListModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.NetUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.UrlTools;
import com.etuo.kucun.utils.jpush.ExampleUtil;
import com.etuo.kucun.utils.jpush.LocalBroadcastManager;
import com.etuo.kucun.utils.permission.EasyPermissions;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Response;

import static com.etuo.kucun.utils.UrlTools.FATSJ;
import static com.etuo.kucun.utils.UrlTools.HOMEDATA;
import static com.etuo.kucun.utils.UrlTools.SATSJ;

/**
 * ================================================
 *
 * @author：haining.yang 版    本：V 1.1.0
 * 创建日期：2020/2/4
 * 描    述：主页面
 * 修订历史：
 * ================================================
 */
public class MainActivity2 extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_shou_message_num)
    TextView tvShouMessageNum;
    @BindView(R.id.rl_shou)
    RelativeLayout rlShou;
    @BindView(R.id.tv_huan_message_num)
    TextView tvHuanMessageNum;
    @BindView(R.id.rl_huan)
    RelativeLayout rlHuan;
    @BindView(R.id.tv_ruku_message_num)
    TextView tvRukuMessageNum;
    @BindView(R.id.rl_ruku)
    RelativeLayout rlRuku;

    @BindView(R.id.tv_market_out_message_num)
    TextView tvMarketOutMessageNum;
    @BindView(R.id.rl_market_out)
    RelativeLayout rlMarketOut;

    @BindView(R.id.tv_tpzk_message_num)
    TextView TV_TPZK_MESSAGE_NUM;

    @BindView(R.id.pull_scrollview)
    PullToRefreshScrollView mScrollView;
    private int requestNum = 0;
    /**
     * 权限申请自定义码
     **/
    private final static int GET_PERMISSION_REQUEST = 200;

    private HomeModel mHomeModel;
    private String ftorderNo;
    private String storderNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main2);
        ButterKnife.bind(this);

        initView();

        //TODO 2019/2/27 改方法 配合退出清除 爱丽丝
        JPushInterface.setAlias(this, 100, JPushInterface.getRegistrationID(this));

        registerMessageReceiver();

    }

    private void initView() {

        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {

                getFTData();
                getSTData();
//                getData();//刷新数据
            }
        });


    }

    private Activity getActivity() {
        return MainActivity2.this;
    }



    private void getFTData() {
        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }
        LogUtil.d("token :" + PreferenceCache.getToken());

        OkGo.get(UrlTools.getInterfaceUrl(FATSJ))
                .tag(this)
                .params("palletStatus", "5")
                .params("companyId", PreferenceCache.getCompanyId())
                .params("openId", "olVoO5DmxFRKaZWeNKGyDrRRYPeU")
                .execute(new DialogCallback<LzyResponse<List<FTListModel>>>(getActivity(), "加载中") {
                    @Override
                    public void onSuccess(LzyResponse<List<FTListModel>> responseData, Call call, Response response) {
                        if (responseData.data != null){
                            for (int i = 0; i < responseData.data.size(); i++) {
                                if (responseData.data.get(i).getScanCodeType().equals("0")){
                                    ftorderNo = responseData.data.get(i).getOrderNo();
                                    return;
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mScrollView.onRefreshComplete();
                        ShowToast.tCustom(getActivity(), e.getMessage());
                    }
                });

    }


    private void getSTData() {
        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }
        LogUtil.d("token :" + PreferenceCache.getToken());

        OkGo.get(UrlTools.getInterfaceUrl(SATSJ))
                .tag(this)
                .params("palletStatus", "5")
                .params("companyId", PreferenceCache.getCompanyId())
                .params("other", "1")
                .params("length", "10")
                .params("start", "0")
                .params("orderStatus", "1")
                .params("openId", "olVoO5DmxFRKaZWeNKGyDrRRYPeU")
                .execute(new DialogCallback<LzyResponse<List<STListModel>>>(getActivity(), "加载中") {
                    @Override
                    public void onSuccess(LzyResponse<List<STListModel>> responseData, Call call, Response response) {

                        if (responseData.data != null){
                            for (int i = 0; i < responseData.data.size(); i++) {
                                if (responseData.data.get(i).getScanCodeType().equals("1")){
                                    storderNo = responseData.data.get(i).getBranchPalletDispatchId();
                                    return;
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mScrollView.onRefreshComplete();
                        ShowToast.tCustom(getActivity(), e.getMessage());
                    }
                });

    }



//    private void getSTData() {
//        if (NetUtil.getNetWorkState(getActivity()) == -1) {
//            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
//            return;
//        }
//        LogUtil.d("token :" + PreferenceCache.getToken());
//
//        OkGo.get(UrlTools.getInterfaceUrl(SHOUTSJ))
//                .tag(this)
//                .params("palletStatus", "5")
//                .params("companyId", PreferenceCache.getCompanyId())
//                .params("openId", "olVoO5DmxFRKaZWeNKGyDrRRYPeU")
//                .execute(new DialogCallback<LzyResponse<HomeModel>>(getActivity(), "加载中") {
//                    @Override
//                    public void onSuccess(LzyResponse<HomeModel> responseData, Call call, Response response) {
//                        mScrollView.onRefreshComplete();
//                        if (responseData != null) {
//                            mHomeModel = responseData.bean;
//                            setData(mHomeModel);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        mScrollView.onRefreshComplete();
//                        ShowToast.tCustom(getActivity(), e.getMessage());
//                    }
//                });
//
//    }

    /**

     * @param view
     */

    @OnClick({R.id.rl_shou, R.id.rl_huan, R.id.rl_ruku,
            R.id.rl_tpzk, R.id.rl_market_out,R.id.pandian})
    public void onClick(View view) {
        Intent intent;
        String orderId;
        switch (view.getId()) {

            case R.id.rl_market_out:
                //发托
                if (ftorderNo != null){
                    intent = new Intent(this, FTActivity.class);
                    intent.putExtra("orderNo",ftorderNo);
                    startActivity(intent);
                }
                break;
            case R.id.rl_shou:
                //收托
                if (storderNo != null){
                    intent = new Intent(this, STActivity.class);
                    intent.putExtra("orderNo",storderNo);
                    startActivity(intent);
                }

                break;
            case R.id.pandian:
                //盘点
//                if (ftorderNo != null){
                    intent = new Intent(this, PDActivity.class);
                    intent.putExtra("orderNo",ftorderNo);
                    startActivity(intent);
//                }
                break;




            case R.id.rl_tpzk:
                //托盘转库
                if (null != mHomeModel.getPalletTransferBillList() && mHomeModel.getPalletTransferBillList().size() >0){

                    orderId = mHomeModel.getPalletTransferBillList().get(0).getGoodsTransferId();
                    intent = new Intent(this, NewChangeAreaDetailsActivity.class);
                    intent.putExtra(ExtraConfig.TypeCode.FROM_INTTENT, ExtraConfig.IntentType.FROM_INTENT_CHANGE_AREA_BY_TP);
                    intent.putExtra(ExtraConfig.TypeCode.INTENT_ORDER_ID,orderId);
                    startActivity(intent);
                }else {
                    ShowToast.tCustom(this,"暂无托盘转库单");
                }

                break;
            case R.id.rl_huan:
                //还托
                if (null != mHomeModel.getReturnPalletBillList() && mHomeModel.getReturnPalletBillList().size() >0){

                    orderId = mHomeModel.getReturnPalletBillList().get(0).getRevertOrderId();
                    intent = new Intent(this, NewTpReceiveDetailsActivity.class);
                    intent.putExtra(ExtraConfig.TypeCode.FROM_INTTENT, ExtraConfig.IntentType.FROM_INTENT_BY_HUAN);
                    intent.putExtra(ExtraConfig.TypeCode.INTENT_ORDER_ID,orderId);
                    startActivity(intent);
                }else {
                    ShowToast.tCustom(this,"暂无托盘验收单");
                }
                break;
            case R.id.rl_ruku:
                //托盘入库
                if (null != mHomeModel.getPalletStorageInBillList() && mHomeModel.getPalletStorageInBillList().size() >0){
                     orderId = mHomeModel.getPalletStorageInBillList().get(0).getPalletStorageInId();
                    intent = new Intent(this, NewTpInStorageDetailsActivity.class);
                    intent.putExtra(ExtraConfig.TypeCode.INTENT_ORDER_ID,orderId);
                    startActivity(intent);
                }else {
                    ShowToast.tCustom(this,"暂无托盘入库单");
                }
                break;
            case R.id.rl_send_goods:
                //托盘发货
                if (null != mHomeModel.getSaleStorageOutBillList() && mHomeModel.getSaleStorageOutBillList().size() >0){
                    orderId = mHomeModel.getSaleStorageOutBillList().get(0).getStorageOutId();
                    intent = new Intent(this, FTActivity.class);
                    intent.putExtra(ExtraConfig.TypeCode.INTENT_ORDER_ID,orderId);
                    intent.putExtra(ExtraConfig.TypeCode.FROM_INTTENT, ExtraConfig.IntentType.FROM_INTENT_MARKET_OUT);
                    startActivity(intent);
                }else {
                    ShowToast.tCustom(this,"暂无托盘发货单");
                }
                break;
        }
    }
    /**
     * 获取摄像头权限
     */
    private void getPermissions() {

        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            LogUtil.d("调用 pop scan");
            Intent intent = new Intent(this, NewMipcaActivityCapture.class);
            intent.putExtra(ExtraConfig.TypeCode.FROM_INTTENT, ExtraConfig.TypeCode.HOME_FRAGMENT);
            startActivity(intent);
        } else {
            EasyPermissions.requestPermissions(this, "使用摄像头进行扫描", GET_PERMISSION_REQUEST, Manifest.permission.CAMERA);
        }
    }

    /**
     * 请求权限成功。
     * 可以弹窗显示结果，也可执行具体需要的逻辑操作
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == GET_PERMISSION_REQUEST) {
            getPermissions();

        }

    }

    /**
     * 请求权限失败
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == GET_PERMISSION_REQUEST) {
            requestNum++;
            if (requestNum < 5) {
                getPermissions();
            } else {
                showToast("摄像头权限请求失败,无法使用扫码功能,如需开启,请进入设置界面开启");
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        getData();
        getFTData();
        getSTData();

    }

    private void getData() {
        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }
        LogUtil.d("token :" + PreferenceCache.getToken());

        OkGo.get(UrlTools.getInterfaceUrl(HOMEDATA))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("start", "0")
                .params("length", "1")
                .execute(new DialogCallback<LzyResponse<HomeModel>>(getActivity(), "加载中") {
                    @Override
                    public void onSuccess(LzyResponse<HomeModel> responseData, Call call, Response response) {
                        mScrollView.onRefreshComplete();
                        if (responseData != null) {
                            mHomeModel = responseData.bean;
                            setData(mHomeModel);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mScrollView.onRefreshComplete();
                        ShowToast.tCustom(getActivity(), e.getMessage());
                    }
                });

    }



    /**
     * 格式化数据
     *
     * @param data
     */
    @SuppressLint("SetTextI18n")
    private void setData(HomeModel data) {

        setMessageNum(tvShouMessageNum,data.getPalletCheckBillTotal());//托盘验收

        setMessageNum(tvRukuMessageNum,data.getPalletStorageInBillTotal());//托盘入库

        //转库
        setMessageNum(TV_TPZK_MESSAGE_NUM,data.getPalletTransferBillTotal());//托盘转库
        setMessageNum(tvMarketOutMessageNum,data.getSaleStorageOutBillTotal());//销售出库


    }

    private void setMessageNum (TextView msgText,int msgNum){

        if (msgNum > 0){
            msgText.setVisibility(View.VISIBLE);
        }else {
            msgText.setVisibility(View.GONE);
        }
        msgText.setText(msgNum +"");

    }


    //for receive customer msg from jpush server
    public static boolean isForeground = false;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }



    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    //setCostomMsg(showMsg.toString());
                }
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

}
