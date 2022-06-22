package com.etuo.kucun.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseActivity;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.callback.DialogCallback;
import com.etuo.kucun.model.OrderCommitModel;
import com.etuo.kucun.model.TpCheckDetailsModel;
import com.etuo.kucun.model.TpInStorageModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.ui.adapter.TpCheckDetailsAdapter;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.NetUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.UrlTools;
import com.etuo.kucun.widget.PromptHintCancel;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lzy.okgo.OkGo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.etuo.kucun.utils.UrlTools.CHECK_BILL_CHECK;
import static com.etuo.kucun.utils.UrlTools.RECEIVE_NO_CONFRIM;
import static com.etuo.kucun.utils.UrlTools.TP_CHECK_INFO;
import static com.etuo.kucun.utils.UrlTools.TP_PALLET_CHECK;

/**
 * ================================================
 *
 * @author :haining
 * @version :
 * @date :2020/02/08
 * @ProjectNameDescribe : 收托盘
 * 修订历史：
 * ================================================
 */

public class TpCheckDetailsActivity extends BaseActivity implements TpCheckDetailsAdapter.OnClickBtItem {
    @BindView(R.id.btn_prev)
    ImageView btnPrev;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.llfirst)
    LinearLayout llFirst;
    @BindView(R.id.tv_orderNum)
    TextView tvOrderNum;
    @BindView(R.id.rl_general_header_bar)
    RelativeLayout rlGeneralHeaderBar;
    @BindView(R.id.lv_outList)
    PullToRefreshListView lvOutList;
    @BindView(R.id.ll_bt)
    LinearLayout llBt;
    @BindView(R.id.ll_scan)
    LinearLayout llScan;
    @BindView(R.id.tv_orderNum_text)
    TextView tvOrderNumText;
    @BindView(R.id.tv_ok)
    Button tvOk;
    @BindView(R.id.rb_one)
    RadioButton mRbOne;
    @BindView(R.id.rb_more)
    RadioButton mRbMore;
    @BindView(R.id.rb_scan)
    RadioButton mRbScan;
    @BindView(R.id.my_radio_group)
    RadioGroup mMyRadioGroup;

    private boolean end = false;
    private int pageIndex = 0;
    private List<TpCheckDetailsModel.PalletModelListBean> mOutBoundListModel = new ArrayList<>();
    private TpCheckDetailsAdapter mAdapter;
    private TpCheckDetailsModel mOutBoundModel;
    String checkBillId;

    String orderNum;
    private String checkTypeScanTp  = ONE_TYPE;//
    public static String ONE_TYPE = "one";
    public static String MORE_TYPE = "more";
    public static String SCAN_TYPE = "scan";
    private String fromType;//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outbounddetatils);
        ButterKnife.bind(this);
        checkBillId = getIntent().getStringExtra(ExtraConfig.TypeCode.INTENT_ORDER_ID);//验货单id
        orderNum = getIntent().getStringExtra(ExtraConfig.TypeCode.INTENT_ORDER_NUM);//清单单号
        fromType = getIntent().getStringExtra(ExtraConfig.TypeCode.FROM_INTTENT);//从哪个界面跳转

        tvHeaderTitle.setText("托盘验收");

        initView();
        getData(true, true);
        mAdapter = new TpCheckDetailsAdapter(this, fromType);
        lvOutList.getRefreshableView().setAdapter(mAdapter);
        mAdapter.OnClickBtItem(this);
        initPtrFrame();
    }

    /**
     * 初始化布局
     */
    private void initView() {

        mMyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId){
                    case R.id.rb_one:
                        checkTypeScanTp = ONE_TYPE;
                        break;
                    case R.id.rb_more:
                        checkTypeScanTp = MORE_TYPE;
                        break;
                    case R.id.rb_scan:
                        checkTypeScanTp = SCAN_TYPE;
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(true, true);
    }

    /**
     * 刷新 和 加载
     */
    private void initPtrFrame() {

        lvOutList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex = 0;
                if (mOutBoundListModel != null) {
                    mOutBoundListModel.clear();
                    mAdapter.removeAll();
                    LogUtil.d("清除成功!!");
                }
                getData(false, true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getData(false, false);
            }
        });


    }

    /**
     * 获取收托详情
     */
    public void getData(final boolean first, final boolean isPullDown) {

        if (NetUtil.getNetWorkState(this) == -1) {

            if (lvOutList != null && lvOutList.isRefreshing()) {
                lvOutList.onRefreshComplete();
            }
            ShowToast.toastTime(this, this.getResources().getString(R.string.net_error), 3);

            return;
        }

        String firstIdx = "0";
        String maxCount = String.valueOf(ExtraConfig.DEFAULT_PAGE_COUNT);
        if (!isPullDown) {
            if (end) {
                setRefreshList(isPullDown, new ArrayList<TpCheckDetailsModel.PalletModelListBean>());
                return;
            } else {
                firstIdx = String.valueOf(pageIndex);
            }
        }

        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.get(UrlTools.getInterfaceUrl(TP_CHECK_INFO))
                .tag(this)
                .params("token", PreferenceCache.getToken())

                .params("checkBillId", checkBillId)


                .execute(new DialogCallback<LzyResponse<TpCheckDetailsModel>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<TpCheckDetailsModel> responseData, Call call, Response response) {

                        if (lvOutList != null && lvOutList.isRefreshing()) {
                            lvOutList.onRefreshComplete();
                        }
                        if (null != responseData) {
                            mOutBoundModel = responseData.bean;

                            tvOrderNum.setText(mOutBoundModel.getOrderNo());
                            LogUtil.d(mOutBoundListModel);
                            setRefreshList(isPullDown, responseData.bean.getPalletModelList());

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (lvOutList != null && lvOutList.isRefreshing()) {
                            lvOutList.onRefreshComplete();
                        }
                        ShowToast.tCustom(TpCheckDetailsActivity.this, e.getMessage());
                    }
                });

    }

    /**
     * 设置数据
     *
     * @param isPullDown
     * @param result
     */
    private void setRefreshList(boolean isPullDown, List<TpCheckDetailsModel.PalletModelListBean> result) {
        if (result.size() < ExtraConfig.DEFAULT_PAGE_COUNT) {
            end = true;
            if (isPullDown && result.size() == 0) {

            } else {

            }

            lvOutList.setPullLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);
            lvOutList.setReleaseLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);
            lvOutList.setRefreshingLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);

        } else {
            end = false;
            lvOutList.setPullLabel("上拉刷新", PullToRefreshBase.Mode.PULL_FROM_END);
            lvOutList.setReleaseLabel("放开以刷新", PullToRefreshBase.Mode.PULL_FROM_END);
            lvOutList.setRefreshingLabel("正在载入", PullToRefreshBase.Mode.PULL_FROM_END);
        }
        if (isPullDown) {
            pageIndex = 0;
            mAdapter.removeAll();
        }
        pageIndex++;

        for (int i = 0; i < result.size(); i++) {
            mOutBoundListModel.add(result.get(i));
        }
        mAdapter.updata(mOutBoundListModel);
        mAdapter.notifyDataSetChanged();
        lvOutList.onRefreshComplete();


    }




    @OnClick({R.id.btn_prev, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_prev:
                PromptHintCancel p = new PromptHintCancel(this) {
                    @Override
                    protected void onOk() {
//                        returnData();
                        finish();
                    }
                };
                p.show(ExtraConfig.TypeCode.DIALOG_CONFIRM_TO_EXIT);
                break;
            case R.id.tv_ok:
                if (mOutBoundModel != null) {
                    if (!canCommit() ) {
                        showToast("当前有未验收的托盘,无法提交");
                    } else {
                        upLoadOrderComfrim();
                    }
                }
                break;
//            case R.id.rl_scan:
//                Intent intent = new Intent(this, NewMipcaActivityCapture.class);
//                intent.putExtra(ExtraConfig.TypeCode.FROM_INTTENT, ExtraConfig.TypeCode.WEB_INTENT_BY_SHOU);
//                intent.putExtra(ExtraConfig.TypeCode.SCAN_QUERY_TYPE, "0");
//                intent.putExtra(ExtraConfig.TypeCode.SCAN_OREDER_ID, checkBillId);
//                intent.putExtra(ExtraConfig.TypeCode.SCAN_RECORD_ID, recordId);
//                startActivity(intent);
//                break;

            default:
                break;
        }
    }

    public String NullToStr(Object o) {
        if (null == o || null == o.toString() || JSONObject.NULL == o) {
            return "";
        }
        return o.toString();
    }

    /**
     * 确定按钮
     */
    public void upLoadOrderComfrim() {
        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.post(UrlTools.getInterfaceUrl(CHECK_BILL_CHECK))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("checkBillId", NullToStr(checkBillId))
                .params("orderId", mOutBoundModel.getOrderId())
                .params("status", "2") //验收状态(必传) 2验收通过 3报修
                .execute(new DialogCallback<LzyResponse<String>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {
                        finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.tCustom(TpCheckDetailsActivity.this, NullToStr(e.getMessage()));
                    }
                });
    }

    /**
     * 批量提交验收托盘的接口
     *   "checkBillId"：验收单id（必传,当palletNum和modelId都不传时，将更新当前验收单所有托盘）
     "palletNum"：托盘编号（非必传，传入时只更新传入的托盘）
     "modelId"：托盘型号Id（非必传，只传入modelId时会更新当前验收单传入型号的所有托盘）
     "batchFlg"：是否批量验收（非必传，为 1 时为批量验收，此时即使传入托盘编号或者托盘型号也不会执行单个或某种型号的验收）
     "palletList"：托盘列表，只需要托盘编号，以数组形式传入（非必传，批量验收时必传，此时只会执行列表中托盘的验收）
     "status"：验收状态(必传) 2验收通过 3报修
     "opEquipment"：操作设备 1手持设备 2门设备 3人工
     */
    public void commitListBing(String palletNum,String modelId,String batchFlg,String palletList,String status,String opEquipment) {
        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.post(UrlTools.getInterfaceUrl(TP_PALLET_CHECK))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("checkBillId", NullToStr(checkBillId))
                .params("palletNum", palletNum)
                .params("modelId", modelId)
                .params("batchFlg", batchFlg)
                .params("palletList", palletList)
                .params("status", status)
                .params("opEquipment", opEquipment)
                .execute(new DialogCallback<LzyResponse<String>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {
                        getData(true,true);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        ShowToast.tCustom(TpCheckDetailsActivity.this, e.getMessage().toString());
                    }
                });
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            PromptHintCancel p = new PromptHintCancel(this) {
                @Override
                protected void onOk() {
//                    returnData();
                    finish();
                }
            };
            p.show(ExtraConfig.TypeCode.DIALOG_CONFIRM_TO_EXIT);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    /**
     *
     * @param position
     * @param singleData
     */
    @Override
    public void myOrderOkClick(int position, final TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean singleData) {


        if (!"2".equals(singleData.getStatus())){
            PromptHintCancel p = new PromptHintCancel(this) {
                @Override
                protected void onOk() {
                    List<TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean> listBeen = new ArrayList<>();
                    listBeen.add(singleData);
                    commitListBing("","","1",getCommitJosn(listBeen,"2"),"","");
                }
            };
            p.show(ExtraConfig.TypeCode.DIALOG_CAOZUO_TO_EXIT);
        }
    }

    /**
     *
     * @param position
     * @param singleData
     */
    @Override
    public void myOrderCancelClick(int position, final  TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean singleData) {

        if (!"3".equals(singleData.getStatus())){
            PromptHintCancel p = new PromptHintCancel(this) {
                @Override
                protected void onOk() {
                    List<TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean> listBeen = new ArrayList<>();
                    listBeen.add(singleData);

                    commitListBing("","","1",getCommitJosn(listBeen,"3"),"","");
                }
            };
            p.show(ExtraConfig.TypeCode.DIALOG_CANCEL_TO_EXIT);
        }
    }

    /**
     * 单个型号全部验收
     * @param position
     * @param singleData
     */
    @Override
    public void myOrderPassAllByModelClick(int position, final TpCheckDetailsModel.PalletModelListBean singleData) {

        if (!isOkByAllStatus(singleData.getCheckBillPalletList(),"2")){
            PromptHintCancel p = new PromptHintCancel(this) {
                @Override
                protected void onOk() {


                    commitListBing("",singleData.getModelId(),"","","2","1");
                }
            };
            p.show("确认对型号为" + singleData.getPalletModel()+"的所有托盘进行验收操作吗?");
        }else {
            ShowToast.tCustom(mContext,"所有托盘已是验收状态,请勿多次操作");
        }

    }

    /**
     *
     * @return 获取提交数据json 串
     *
     */

    private String getCommitJosn(List<TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean> singleData,String status){

        List<OrderCommitModel>   mOrderCommitModels = new ArrayList<>();

        for (int i = 0;i<singleData.size();i++){

            OrderCommitModel commitModel = new OrderCommitModel();
            commitModel.setPalletCheckDetailId(singleData.get(i).getPalletCheckDetailId());
            commitModel.setOpEquipment("1");//操作设备 1手持设备 2门设备 3人工
            commitModel.setStatus(status);//货物备货单状态：0待验收 2验收通过 3报修
            mOrderCommitModels.add(commitModel);
        }

        String json=new Gson().toJson(mOrderCommitModels);


        LogUtil.d("tojson : " + json);
        return json;
    }


    /**
     * 判断是否可以提交
     * @return
     */
    private boolean canCommit(){
        int waitCnt = 0;
        for (int i = 0; i< mOutBoundListModel.size() ; i++){
            waitCnt = waitCnt + mOutBoundListModel.get(i).getNoCheckCnt() ;

            if (waitCnt > 0){
                return false;
            }
        }

        if (waitCnt > 0){
            return false;
        }else {
            return true;
        }
    }


    /**
     * 判断是否可以提交
     * @return
     */
    private boolean isOkByAllStatus(List<TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean> listBeen , String status){
        for (int i = 0; i< listBeen.size() ; i++){

            if (!status.equals(listBeen.get(i).getStatus())){
                return false;
            }

        }

        return  true;
    }

}
