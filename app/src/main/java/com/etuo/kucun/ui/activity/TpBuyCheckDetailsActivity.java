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
import com.etuo.kucun.model.BuyCheckDetailsModel;
import com.etuo.kucun.model.OrderCommitModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.ui.adapter.TpBuyCheckDetailsAdapter;
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

import static com.etuo.kucun.utils.UrlTools.BATCH_ACCEPTANCE;
import static com.etuo.kucun.utils.UrlTools.GOODS_BUY_CHECK_GETBILL;
import static com.etuo.kucun.utils.UrlTools.RECEIVE_NO_CONFRIM;
import static com.etuo.kucun.utils.UrlTools.UPD_CHECK_BILL;

/**
 * ================================================
 *
 * @author :haining
 * @version :
 * @date :2020/02/08
 * @ProjectNameDescribe :采购验收 等详情操作界面
 * 修订历史：
 * ================================================
 */

public class TpBuyCheckDetailsActivity extends BaseActivity implements  TpBuyCheckDetailsAdapter.OnClickBtItem {
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
    @BindView(R.id.ll_scan)
    LinearLayout mLlScan;

    private boolean end = false;
    private int pageIndex = 0;
    private List<BuyCheckDetailsModel.DetailListBean> mOutBoundListModel = new ArrayList<>();
    private TpBuyCheckDetailsAdapter mAdapter;
    private BuyCheckDetailsModel mOutBoundModel;
    String orderId;
    String recordId;
    String orderBatchId;
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
        orderId = getIntent().getStringExtra(ExtraConfig.TypeCode.INTENT_ORDER_ID);//订单id
        fromType = getIntent().getStringExtra(ExtraConfig.TypeCode.FROM_INTTENT);//从哪个界面跳转

        tvHeaderTitle.setText("采购验收");
        tvOrderNumText.setText("验收单号: ");

        initView();
        getData(true, true);
        mAdapter = new TpBuyCheckDetailsAdapter(this, fromType);
        mAdapter.OnClickBtItem(this);
        lvOutList.getRefreshableView().setAdapter(mAdapter);
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
     * 获取详情
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
                setRefreshList(isPullDown, new ArrayList<BuyCheckDetailsModel.DetailListBean>());
                return;
            } else {
                firstIdx = String.valueOf(pageIndex);
            }
        }

        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.get(UrlTools.getInterfaceUrl(GOODS_BUY_CHECK_GETBILL))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("goodsCheckId", orderId)
//                .params("start", firstIdx)
//                .params("length", maxCount)

                .execute(new DialogCallback<LzyResponse<BuyCheckDetailsModel>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<BuyCheckDetailsModel> responseData, Call call, Response response) {

                        if (lvOutList != null && lvOutList.isRefreshing()) {
                            lvOutList.onRefreshComplete();
                        }
                        if (null != responseData) {
                            mOutBoundModel = responseData.bean;

                            orderNum = mOutBoundModel.getGoodsCheckNo();
                            tvOrderNum.setText(orderNum);



                            setRefreshList(isPullDown, responseData.bean.getDetailList());

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (lvOutList != null && lvOutList.isRefreshing()) {
                            lvOutList.onRefreshComplete();
                        }
                        ShowToast.tCustom(TpBuyCheckDetailsActivity.this, e.getMessage());
                    }
                });

    }

    /**
     * 设置数据
     *
     * @param isPullDown
     * @param result
     */
    private void setRefreshList(boolean isPullDown, List<BuyCheckDetailsModel.DetailListBean> result) {
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
                    if (!canCommit()) {
                        showToast("当前有未验收的货物,无法提交");
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
        OkGo.post(UrlTools.getInterfaceUrl(UPD_CHECK_BILL))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("goodsCheckId", NullToStr(orderId))

                .execute(new DialogCallback<LzyResponse<String>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {
                        finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.tCustom(TpBuyCheckDetailsActivity.this, NullToStr(e.getMessage()));
                    }
                });
    }

    /**
     * 批量提交绑定货物接口
     */
    public void commitListBing(String bindingParamJson) {
        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.post(UrlTools.getInterfaceUrl(BATCH_ACCEPTANCE))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("bindingParam", bindingParamJson)

                .execute(new DialogCallback<LzyResponse<String>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {
                        getData(true,true);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        ShowToast.tCustom(TpBuyCheckDetailsActivity.this, e.getMessage().toString());
                    }
                });
    }

    /**
     * 未提交接口
     */
    public void returnData() {
        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.post(UrlTools.getInterfaceUrl(RECEIVE_NO_CONFRIM))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("checkBillId", orderId)

                .execute(new DialogCallback<LzyResponse<String>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {
                        finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        ShowToast.tCustom(TpBuyCheckDetailsActivity.this, e.getMessage().toString());
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
    public void myOrderOkClick(int position, final BuyCheckDetailsModel.DetailListBean.BillDetailListBean singleData) {

        if (!"1".equals(singleData.getStatus())){
            PromptHintCancel p = new PromptHintCancel(this) {
                @Override
                protected void onOk() {
                    List<BuyCheckDetailsModel.DetailListBean.BillDetailListBean> listBeen = new ArrayList<>();
                    listBeen.add(singleData);

                    commitListBing(getCommitJosn(listBeen,"1"));
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
    public void myOrderCancelClick(int position, final  BuyCheckDetailsModel.DetailListBean.BillDetailListBean singleData) {


        if (!"2".equals(singleData.getStatus())){
            PromptHintCancel p = new PromptHintCancel(this) {
                @Override
                protected void onOk() {
                    List<BuyCheckDetailsModel.DetailListBean.BillDetailListBean> listBeen = new ArrayList<>();
                    listBeen.add(singleData);
                    commitListBing(getCommitJosn(listBeen,"2"));
                }
            };
            p.show(ExtraConfig.TypeCode.DIALOG_CANCEL_TO_EXIT);
        }



    }

    /**
     *
     * @return 获取提交数据json 串
     *
     */

    private List<OrderCommitModel> mOrderCommitModels ;
    private String getCommitJosn(List<BuyCheckDetailsModel.DetailListBean.BillDetailListBean> singleData,String status){

        mOrderCommitModels = new ArrayList<>();


       for (int i = 0;i<singleData.size();i++){
           OrderCommitModel commitModel = new OrderCommitModel();
           commitModel.setGoodsCheckDetailId(singleData.get(i).getGoodsCheckDetailid());
           commitModel.setOpEquipment("1");//操作设备 1手持设备 2门设备 3人工
           commitModel.setStatus(status);//货物备货单状态：0未操作 1通过2不通过
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
            waitCnt = waitCnt + mOutBoundListModel.get(i).getWaitcnt() ;

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



}
