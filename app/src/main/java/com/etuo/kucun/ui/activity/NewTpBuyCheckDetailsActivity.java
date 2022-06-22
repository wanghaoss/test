package com.etuo.kucun.ui.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.etuo.kucun.model.common.ReturnReasonListBean;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.ui.adapter.NewTpBuyCheckDetailsAdapter;
import com.etuo.kucun.ui.fragment.CheckReturnReasonPop;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.NetUtil;
import com.etuo.kucun.utils.RfidOperationUtils;
import com.etuo.kucun.utils.ScanBarcode2DUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.StringUtil;
import com.etuo.kucun.utils.UrlTools;
import com.etuo.kucun.widget.PromptHintCancel;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.etuo.kucun.utils.UrlTools.BATCH_ACCEPTANCE;
import static com.etuo.kucun.utils.UrlTools.GOODS_BUY_CHECK_GETBILL;
import static com.etuo.kucun.utils.UrlTools.UPD_CHECK_BILL;

/**
 * ================================================
 *
 * @author :haining
 * @version :
 * @date :2020/02/08
 * @ProjectNameDescribe : 采购验收 等详情操作界面
 * 修订历史：
 * ================================================
 */

public class NewTpBuyCheckDetailsActivity extends BaseActivity implements NewTpBuyCheckDetailsAdapter.OnClickBtItem {
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
    ExpandableListView lvOutList;
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
    private NewTpBuyCheckDetailsAdapter mAdapter;
    private BuyCheckDetailsModel mOutBoundModel;
    String orderId;

    String orderNum;
    private String buttonFlg;//能否操作数据  0 不能  1能
    private String operationType;

    public static String READ_TYPE = "read";
    public static String CLEAR_TYPE = "clear";

    private String checkTypeScanTp = ONE_TYPE;//
    public static String ONE_TYPE = "one";
    public static String MORE_TYPE = "more";
    public static String SCAN_TYPE = "scan";

    public static String TYPE_BY_COMMIT_LIST = "commitList";
    private String fromType;//

    private String checkOnePalletNum = "";
    private List<ReturnReasonListBean> mReturnReasonListBeen = new ArrayList<>();


    private List<BuyCheckDetailsModel.DetailListBean.BillDetailListBean> detailListBeanList = new ArrayList<>();//批量操作是缓存的list ,只有在按下确定键开始添加,松开停止 然后提交数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_detatils);
        ButterKnife.bind(this);
        orderId = getIntent().getStringExtra(ExtraConfig.TypeCode.INTENT_ORDER_ID);//订单id
        fromType = getIntent().getStringExtra(ExtraConfig.TypeCode.FROM_INTTENT);//从哪个界面跳转

        tvHeaderTitle.setText("采购验收");
        tvOrderNumText.setText("验收单号: ");
        initView();

        mAdapter = new NewTpBuyCheckDetailsAdapter(this, fromType,getContentView(this));
        mAdapter.OnClickBtItem(this);
//        lvOutList.getRefreshableView().setGroupIndicator(null);
//        lvOutList.getRefreshableView().setAdapter(mAdapter);
        lvOutList.setGroupIndicator(null);
        lvOutList.setAdapter(mAdapter);

        initPtrFrame();

        //读写扫描相关
        initRfid();
        initScan();


    }

    /**
     * 初始化布局
     */
    private void initView() {

        mMyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {
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

    private void initEvents() {
        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
            //关键步骤4:初始化，将ExpandableListView以展开的方式显示
//            lvOutList.getRefreshableView().expandGroup(i);
            lvOutList.expandGroup(i);
        }
        lvOutList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int firstVisiblePosition = view.getFirstVisiblePosition();
                int top = -1;
                View firstView = view.getChildAt(firstVisibleItem);
                //返回的是显示层面上的所包含的子view的个数
//                LogUtil.i("childCount=" + view.getChildCount());
                if (firstView != null) {
                    top = firstView.getTop();
                }
//                LogUtil.i("firstVisibleItem=" + firstVisibleItem + ",firstVisiblePosition=" + firstVisiblePosition + ",firstView=" + firstView + ",top=" + top);

            }
        });

        lvOutList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // TODO Auto-generated method stub
                //设置点击不关闭（不收回）
                v.setClickable(true);
                return true;
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


//        lvOutList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ExpandableListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
//                pageIndex = 0;
//                if (mOutBoundListModel != null) {
//                    mOutBoundListModel.clear();
//                    mAdapter.removeAll();
//                    LogUtil.d("清除成功!!");
//                }
//                getData(false, true);
//            }
//        });



    }

    /**
     * 获取采购验收详情
     */
    public void getData(final boolean first, final boolean isPullDown) {

        if (NetUtil.getNetWorkState(this) == -1) {

//            if (lvOutList != null && lvOutList.isRefreshing()) {
//                lvOutList.onRefreshComplete();
//            }
            ShowToast.toastTime(this, this.getResources().getString(R.string.net_error), 3);

            return;
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

//                        if (lvOutList != null && lvOutList.isRefreshing()) {
//                            lvOutList.onRefreshComplete();
//                        }

//
                        if (null != responseData) {
                            mOutBoundModel = responseData.bean;
                            buttonFlg = responseData.buttonFlg;

                            orderNum = mOutBoundModel.getGoodsCheckNo();
                            mReturnReasonListBeen = responseData.commonList;
                            tvOrderNum.setText(orderNum);

                            LogUtil.d(mOutBoundListModel);


                            setRefreshList(isPullDown, getNewRankList(responseData.bean.getDetailList()));
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        if (lvOutList != null && lvOutList.isRefreshing()) {
//                            lvOutList.onRefreshComplete();
//                        }
                        ShowToast.tCustom(NewTpBuyCheckDetailsActivity.this, e.getMessage());
                    }
                });

    }

    /**
     * 按 状态 1->2->0 排序
     *
     * @param listBeen
     * @return
     */
    private List<BuyCheckDetailsModel.DetailListBean> getNewRankList(List<BuyCheckDetailsModel.DetailListBean> listBeen) {

        List<BuyCheckDetailsModel.DetailListBean> listBeanList = new ArrayList<>();
        for (BuyCheckDetailsModel.DetailListBean listBean : listBeen) {
            List<BuyCheckDetailsModel.DetailListBean.BillDetailListBean> list = listBean.getBillDetailList();

            Collections.sort(list, new Comparator<BuyCheckDetailsModel.DetailListBean.BillDetailListBean>() {
                @Override
                public int compare(BuyCheckDetailsModel.DetailListBean.BillDetailListBean o1, BuyCheckDetailsModel.DetailListBean.BillDetailListBean o2) {

                    LogUtil.d("排序 :  o1 : " + o1.getStatus() + "   o2 : " + o2.getStatus());
                    int diff = StringUtil.String2int(o1.getStatus().equals("0") ? "4" : o1.getStatus()) - StringUtil.String2int(o2.getStatus().equals("0") ? "4" : o2.getStatus());

                    if (diff > 0) {
                        return 1;
                    } else if (diff < 0) {
                        return -1;
                    }
                    return 0; //相等为0
                }
            });

            listBean.setBillDetailList(list);
            listBeanList.add(listBean);
        }


        String json = new Gson().toJson(listBeanList);
        LogUtil.d("新的排序 : " + json);

        return listBeanList;

    }

    /**
     * 设置数据
     *
     * @param isPullDown
     * @param result
     */
    private void setRefreshList(boolean isPullDown, List<BuyCheckDetailsModel.DetailListBean> result) {

        if (isPullDown) {
            pageIndex = 0;
            mAdapter.removeAll();
        }
        pageIndex++;

        for (int i = 0; i < result.size(); i++) {
            mOutBoundListModel.add(result.get(i));
        }

        if (!StringUtil.isEmpty(checkOnePalletNum)) {//选中
            int[] ints = getUpPosition(checkOnePalletNum);
            mAdapter.updata(mOutBoundListModel);

            mAdapter.notifyDataSetChanged();
//            lvOutList.getRefreshableView().setSelectedChild(ints[0],ints[1],false);

            lvOutList.setSelectedChild(ints[0], ints[1], true);

        } else {
            mAdapter.updata(mOutBoundListModel);
            mAdapter.notifyDataSetChanged();
        }

        initEvents();


        if (mOutBoundModel != null) {
            if (!canCommit()) {
                tvOk.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_gray));
            } else {
                tvOk.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_blue));
            }
        }


    }

    /**
     * @param
     * @return 最后更新的数据的位置(排序之后的)
     */
    private int[] getUpPosition(String palletNo) {

        int[] ints = new int[2];
        for (int i = 0; i < mOutBoundListModel.size(); i++) {
            BuyCheckDetailsModel.DetailListBean gatherListBean = mOutBoundListModel.get(i);
            List<BuyCheckDetailsModel.DetailListBean.BillDetailListBean> list = mOutBoundListModel.get(i).getBillDetailList();
            if (null != list && list.size() > 0) {
                for (int j = 0; j < list.size(); j++) {
                    if (palletNo.equals(list.get(j).getPalletNum())) {
                        ints[0] = i;
                        ints[1] = j;
                        list.get(j).setLastCheck(true);


                    } else {
                        list.get(j).setLastCheck(false);
                    }

                }
                gatherListBean.setBillDetailList(list);
            }
            mOutBoundListModel.set(i, gatherListBean);
        }

        return ints;

    }


    @OnClick({R.id.btn_prev, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_prev:
                PromptHintCancel p = new PromptHintCancel(this) {
                    @Override
                    protected void onOk() {

                        finish();
                    }
                };
                p.show(ExtraConfig.TypeCode.DIALOG_CONFIRM_TO_EXIT);
                break;
            case R.id.tv_ok:

                if (mOutBoundModel != null) {
                    if (!canCommit()) {
                        showToast("当前有未操作的托盘,无法提交");
                    } else {
                        upLoadOrderComfrim();
                    }
                }

                break;


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
                        ShowToast.tCustom(NewTpBuyCheckDetailsActivity.this, NullToStr(e.getMessage()));
                    }
                });
    }

    /**
     * 批量提交绑定货物接口
     */
    public void commitListBing(String bindingParamJson, final String type, final String palletNum,final  String returnReason) {
        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.post(UrlTools.getInterfaceUrl(BATCH_ACCEPTANCE))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("bindingParam", bindingParamJson)
                .params("returnReason",returnReason)

                .execute(new DialogCallback<LzyResponse<String>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {

                        if (TYPE_BY_COMMIT_LIST.equals(type)) {//批量提交
                            detailListBeanList.clear();
                        }

                        checkOnePalletNum = palletNum;
                        getData(true, true);

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        ShowToast.tCustom(NewTpBuyCheckDetailsActivity.this, e.getMessage().toString());
                    }
                });
    }


    /**
     *
     * @param position
     * @param singleData
     */
    @Override
    public void myOrderOkClick(int position, final BuyCheckDetailsModel.DetailListBean.BillDetailListBean singleData) {

        if (!"1".equals(singleData.getStatus())){
//            PromptHintCancel p = new PromptHintCancel(this) {
//                @Override
//                protected void onOk() {

                    final   List<BuyCheckDetailsModel.DetailListBean.BillDetailListBean> listBeen = new ArrayList<>();
                    listBeen.add(singleData);

                    //此处应该是读取USER分区数据进行匹配  现在只进行标签识别
//                    String readData= mRfidOperationUtils.readFilterByEpc(singleData.getPalletNum());

//                    if (null != readData){//能读出来数据  不匹配的才写不通过
//                        boolean  isTrue =  StringUtil.isTrueBuyCheck(singleData.getGoodsId(), singleData.getGoodsModel(),
//                                singleData.getGoodsCnt() + "", singleData.getGoodsWeight() + "" ,readData );
//                        if (isTrue){ //匹配
//                            commitListBing(getCommitJosn(listBeen, "1"), "", singleData.getPalletNum(),"");
//                        }else {//不匹配
//                            ShowToast.showShortToast(this,"此托盘货物信息不匹配");
////                            PromptHintCancel p = new PromptHintCancel(this) {
////                                @Override
////                                protected void onOk() {
////
////                                    CheckReturnReasonPop checkReturnReasonPop = new CheckReturnReasonPop();
////
////                                    checkReturnReasonPop.showPW(NewTpBuyCheckDetailsActivity.this,mReturnReasonListBeen,getContentView(NewTpBuyCheckDetailsActivity.this));
////                                    checkReturnReasonPop.OnClickBtItem(new CheckReturnReasonPop.OnClickBtItem() {
////                                        @Override
////                                        public void myOrderOkClick(String checkId) {
////                                            List<BuyCheckDetailsModel.DetailListBean.BillDetailListBean> listBeen = new ArrayList<>();
////                                            listBeen.add(singleData);
////                                            commitListBing(getCommitJosn(listBeen, "2"), "", singleData.getPalletNum(),checkId);
////
////                                        }
////
////                                        @Override
////                                        public void myOrderCancelClick() {
////
////                                        }
////                                    });
////
////                                }
////                            };
////                            p.show("此托盘货物信息不匹配,是否设置为不通过?");
//
//                        }
//                    }
//                }
//            };
//            p.show(ExtraConfig.TypeCode.DIALOG_CAOZUO_TO_EXIT);
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

            CheckReturnReasonPop checkReturnReasonPop = new CheckReturnReasonPop();

            checkReturnReasonPop.showPW(this,mReturnReasonListBeen,getContentView(this));
            checkReturnReasonPop.OnClickBtItem(new CheckReturnReasonPop.OnClickBtItem() {
                @Override
                public void myOrderOkClick(String checkId) {
                    List<BuyCheckDetailsModel.DetailListBean.BillDetailListBean> listBeen = new ArrayList<>();
                    listBeen.add(singleData);

                    commitListBing(getCommitJosn(listBeen, "2"), "", singleData.getPalletNum(),checkId);
                }

                @Override
                public void myOrderCancelClick() {

                }
            });



        }


    }

    /**
     *
     * @param position
     * @param singleData
     */
    @Override
    public void myOrderTpPassClick(int position, final  BuyCheckDetailsModel.DetailListBean.BillDetailListBean singleData) {


//        if (!"3".equals(singleData.getStatus())){
////            PromptHintCancel p = new PromptHintCancel(this) {
////                @Override
////                protected void onOk() {
//            //此处应该是读取USER分区数据进行匹配  现在只进行标签识别
////                    String readData= mRfidOperationUtils.readFilterByEpc(singleData.getPalletNum());
//
////                    if (mRfidOperationUtils.getReadTagByEpc(singleData.getPalletNum())) {
//            List<BuyCheckDetailsModel.DetailListBean.BillDetailListBean> listBeen = new ArrayList<>();
//            listBeen.add(singleData);
//
//            commitListBing(getCommitJosn(listBeen, "3"), "", singleData.getPalletNum());
////                    }
//
////                }
////            };
////            p.show(ExtraConfig.TypeCode.DIALOG_CANCEL_TO_EXIT);
//        }


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
     * @param palletId 托盘编号
     * @param status   托盘状态
     * @return 扫描托盘是不是可以提交
     */
    private BuyCheckDetailsModel.DetailListBean.BillDetailListBean getCheckBean(String palletId, String status) {

        for (BuyCheckDetailsModel.DetailListBean listBean : mOutBoundListModel) {
            for (BuyCheckDetailsModel.DetailListBean.BillDetailListBean detailListBean : listBean.getBillDetailList()) {
                if (detailListBean.getPalletNum().equals(palletId) && !isInList(palletId) && !status.equals(detailListBean.getStatus())) {
                    detailListBean.setGoodsModel(listBean.getGoods() == null ? "" : listBean.getGoods().getGoodsModel());
                    LogUtil.d("epc扫描到一个可以提交的托盘 : " + detailListBean.getPalletNum() + "  找到的托盘 : " + palletId);
                    return detailListBean;
                }
            }
        }

        return null;

    }

    /**
     * @param palletId 托盘编号
     * @return 判断托盘是不是已经在批量扫描的列表里
     */
    private boolean isInList(String palletId) {

        if (null != detailListBeanList && detailListBeanList.size() > 0) {
        }
        {
            for (BuyCheckDetailsModel.DetailListBean.BillDetailListBean listBean : detailListBeanList) {
                if (listBean.getPalletNum().equals(palletId)) {
                    return true;
                }
            }

        }

        return false;

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



    /**
     * 实体键按下
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            PromptHintCancel p = new PromptHintCancel(this) {
                @Override
                protected void onOk() {

                    finish();
                }
            };
            p.show(ExtraConfig.TypeCode.DIALOG_CONFIRM_TO_EXIT);
            return true;
        } else if (keyCode == 280 && event.getRepeatCount() == 0) {//点击了 扳机

            //点击后创建list

            if (SCAN_TYPE.equals(checkTypeScanTp)) {//扫描
                LogUtil.d("点击了 扳机 ->扫描模式");
                mBarcode2DUtil.ScanBarcode();
            } else {
                LogUtil.d("点击了 扳机 ->读写模式");
                detailListBeanList.clear();
                mRfidOperationUtils.readTag(checkTypeScanTp);

            }


            return true;
        } else if (keyCode == 139 && event.getRepeatCount() == 0) {//快捷键scan
            LogUtil.d("点击了 快捷扫描键");
            mBarcode2DUtil.ScanBarcode();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 实体键松开
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == 280 && event.getRepeatCount() == 0) {//点击了 扳机
            if (MORE_TYPE.equals(checkTypeScanTp)) {

                //关闭循环扫描标签
                mRfidOperationUtils.stopInventory();
                LogUtil.d(" 扳机 松开 开始写数据  " + System.currentTimeMillis());

                if (null != detailListBeanList && detailListBeanList.size() > 0) {
                    operationType = READ_TYPE;
                    new WriteDataTask().execute();
                } else {
                    ShowToast.showShortToast(this, "未找到可操作的托盘,请重新扫描");
                }


                return true;
            }

        }


        return super.onKeyUp(keyCode, event);

    }

    /**
     * 单步rfid 和扫描结果查询提交
     *
     * @param code
     * @param status
     */

    private void commitTpByScanId(String code, String status) {
       final BuyCheckDetailsModel.DetailListBean.BillDetailListBean singleData = getCheckBean(code, status);

        if (null != singleData) {
            final    List<BuyCheckDetailsModel.DetailListBean.BillDetailListBean> listBeen = new ArrayList<>();
            listBeen.add(singleData);

            //此处应该是读取USER分区数据进行匹配  现在只进行标签识别
//            String readData= mRfidOperationUtils.readFilterByEpc(singleData.getPalletNum());

//            if (null != readData){//能读出来数据  不匹配的才写不通过
//                boolean  isTrue =  StringUtil.isTrueBuyCheck(singleData.getGoodsId(), singleData.getGoodsModel(),
//                        singleData.getGoodsCnt() + "", singleData.getGoodsWeight() + "" ,StringUtil.hexStringToString(readData) );
//                if (isTrue){ //匹配
//                    commitListBing(getCommitJosn(listBeen, status), "", singleData.getPalletNum(),"");
//                }else {//不匹配
//
//
//                    ShowToast.showShortToast(this,"此托盘货物信息不匹配");
////                    PromptHintCancel p = new PromptHintCancel(this) {
////                        @Override
////                        protected void onOk() {
////
////                            CheckReturnReasonPop checkReturnReasonPop = new CheckReturnReasonPop();
////
////                            checkReturnReasonPop.showPW(NewTpBuyCheckDetailsActivity.this,mReturnReasonListBeen,getContentView(NewTpBuyCheckDetailsActivity.this));
////                            checkReturnReasonPop.OnClickBtItem(new CheckReturnReasonPop.OnClickBtItem() {
////                                @Override
////                                public void myOrderOkClick(String checkId) {
////                                    List<BuyCheckDetailsModel.DetailListBean.BillDetailListBean> listBeen = new ArrayList<>();
////                                    listBeen.add(singleData);
////                                    commitListBing(getCommitJosn(listBeen, "2"), "", singleData.getPalletNum(),checkId);
////
////                                }
////
////                                @Override
////                                public void myOrderCancelClick() {
////
////                                }
////                            });
////
////                        }
////                    };
////                    p.show("此托盘货物信息不匹配,是否设置为不通过?");
//
//                }
//            }

        } else {
            ShowToast.tCustom(mContext, "查无次托盘或此托盘已操作");
        }
    }


    /****************************UHF读写相关***************************************/


//    private String wrData = "A000012020022112345678202002211234001001";

    private RfidOperationUtils mRfidOperationUtils;


    private void initRfid() {
        mRfidOperationUtils = new RfidOperationUtils(mContext);
        /**
         * 读标签返回值
         * 单步的时候直接提交
         */
        mRfidOperationUtils.readTagCallBack(new RfidOperationUtils.readTagCallBack() {
            @Override
            public void OneStepReadTagCallBack(String strEpc) {

                //执行写的操作

                commitTpByScanId(strEpc, "1");


            }

            /**
             * 批量的时候等到松开手之后提交
             * @param strTid
             * @param strEpc
             */
            @Override
            public void MoreStepReadTagCallBack(String strTid, String strEpc) {

                BuyCheckDetailsModel.DetailListBean.BillDetailListBean singleData = getCheckBean(strEpc, "1");

                if (null != singleData) {

                    detailListBeanList.add(singleData);


                }


            }
        });
        /**
         * 读数据返回值
         */
        mRfidOperationUtils.readDataCallBack(new RfidOperationUtils.readDataCallBack() {
            @Override
            public void OneStepReadDataCallBack(String data) {

            }

            @Override
            public void MoreStepReadDataCallBack(String[] datas) {

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();


        if (mRfidOperationUtils != null) {
            // 停止识别
            mRfidOperationUtils.stopInventory();
        }
        if (null != mBarcode2DUtil) {
            mBarcode2DUtil.stopScan();
        }


    }

    @Override
    protected void onDestroy() {


        super.onDestroy();
        //关闭
        if (null != mRfidOperationUtils) {
            mRfidOperationUtils.closeInventory();

        }
        if (null != mBarcode2DUtil) {
            mBarcode2DUtil.close();
        }
        OkGo.getInstance().cancelTag(this);


    }

    /**********************二维码扫描***************************/
    private ScanBarcode2DUtil mBarcode2DUtil;

    private void initScan() {
        mBarcode2DUtil = new ScanBarcode2DUtil(this);
        /**
         * 二维码扫描返回值
         */
        mBarcode2DUtil.scanCode(new ScanBarcode2DUtil.scanBarcode2DCallBack() {
            @Override
            public void scanBarcode2DCallBack(String code) {
                LogUtil.d("点击了 快捷键scan code :" + code);
                commitTpByScanId(code, "1");
            }
        });

    }

    /**
     * 异步读数据
     *
     * @author yhn
     */
    public class WriteDataTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        List<BuyCheckDetailsModel.DetailListBean.BillDetailListBean> newDetailListBeen;
        List<BuyCheckDetailsModel.DetailListBean.BillDetailListBean> newFailDetailListBeen;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            LogUtil.d("WriteDataTask doInBackground");
            Boolean aBoolean = false;
            newDetailListBeen = new ArrayList<>();
            newFailDetailListBeen = new ArrayList<>();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if (null != detailListBeanList && detailListBeanList.size() > 0) {

                for (int i = 0; i < detailListBeanList.size(); i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                        //判断读写模式
                        if (READ_TYPE.equals(operationType)){

//                            String readData = mRfidOperationUtils.readFilterByEpc(detailListBeanList.get(i).getPalletNum());

//                            if (null != readData){//能读出来数据  不匹配的才写不通过
//                           boolean  isTrue =  StringUtil.isTrueBuyCheck(detailListBeanList.get(i).getGoodsId(), detailListBeanList.get(i).getGoodsModel(),
//                                        detailListBeanList.get(i).getGoodsCnt() + "", detailListBeanList.get(i).getGoodsWeight() + "" ,readData );
//                                if (isTrue){
//                                    newDetailListBeen.add(detailListBeanList.get(i));
//                                }else {
//                                    newFailDetailListBeen.add(detailListBeanList.get(i));
//                                }
//                            }
                        }

                }

                hideLoadingDialog();
                LogUtil.d("开始写读数据完成,开始提交数据" + System.currentTimeMillis());
                if (newDetailListBeen != null && newDetailListBeen.size() > 0) {

                    aBoolean = true;
                }

            } else {
                aBoolean = false;
            }
            return aBoolean;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            LogUtil.d("WriteDataTask onPostExecute" + "  result : " + result);
            mypDialog.cancel();
//            hideLoadingDialog();

            if (result) {
                if (READ_TYPE.equals(operationType)){
                    commitListBing(getCommitJosn(newDetailListBeen, "1"), TYPE_BY_COMMIT_LIST, "","");

                }
            } else {
                ShowToast.tCustom(mContext, "暂时未找到对应的托盘,请重新扫描");
            }

            if (newFailDetailListBeen != null && newFailDetailListBeen.size()>0 ){
                ShowToast.tCustom(mContext, "有" + newFailDetailListBeen.size() +"的托盘货物信息不匹配");
//                commitListBing(getCommitJosn(newDetailListBeen, "2"), TYPE_BY_COMMIT_LIST, "");
            }
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
            LogUtil.d("WriteDataTask onCancelled" + "  result : " + aBoolean);

            mypDialog.cancel();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            LogUtil.d("WriteDataTask onPreExecute");
//            showLoadingDialog("");
            if (mypDialog == null) {
                mypDialog = new ProgressDialog(mContext);
                mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mypDialog.setMessage("正在读数据,请勿移走手持设备...");
                mypDialog.setCanceledOnTouchOutside(false);
            }

            if (!mypDialog.isShowing()) {
                mypDialog.show();

            }

        }
    }


}
