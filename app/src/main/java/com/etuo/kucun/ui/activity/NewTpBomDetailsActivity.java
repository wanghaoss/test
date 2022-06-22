package com.etuo.kucun.ui.activity;

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
import com.etuo.kucun.model.BomDetailsModel;
import com.etuo.kucun.model.OrderCommitModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.ui.adapter.NewTpBomDetailsAdapter;
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

import static com.etuo.kucun.utils.UrlTools.GOODS_STORAGEOUT_UPDATEBILL;
import static com.etuo.kucun.utils.UrlTools.GOODS_STORAGE_OUT_GETBILL;
import static com.etuo.kucun.utils.UrlTools.MOBILE_GOODS_STORAGEOUT;

/**
 * ================================================
 *
 * @author :haining
 * @version :
 * @date :2020/02/08
 * @ProjectNameDescribe :  BOM 备货 等详情操作界面
 * 修订历史：
 * ================================================
 */

public class NewTpBomDetailsActivity extends BaseActivity implements NewTpBomDetailsAdapter.OnClickBtItem {
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
    private List<BomDetailsModel.GatherListBean> mOutBoundListModel = new ArrayList<>();
    private NewTpBomDetailsAdapter mAdapter;
    private BomDetailsModel mOutBoundModel;
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
    String checkBillId;

    private List<BomDetailsModel.GatherListBean.DetailListBean> detailListBeanList = new ArrayList<>();//批量操作是缓存的list ,只有在按下确定键开始添加,松开停止 然后提交数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_detatils);
        ButterKnife.bind(this);
        orderId = getIntent().getStringExtra(ExtraConfig.TypeCode.INTENT_ORDER_ID);//订单id
        fromType = getIntent().getStringExtra(ExtraConfig.TypeCode.FROM_INTTENT);//从哪个界面跳转
        checkBillId = getIntent().getStringExtra(ExtraConfig.TypeCode.INTENT_ORDER_ID);//验货单id
        orderNum = getIntent().getStringExtra(ExtraConfig.TypeCode.INTENT_ORDER_NUM);//清单单号
        if (ExtraConfig.IntentType.FROM_INTENT_BOM.equals(fromType)) {
            tvHeaderTitle.setText("BOM领料");
            tvOrderNumText.setText("出库单号: ");
        }else if (ExtraConfig.IntentType.FROM_INTENT_MARKET_OUT.equals(fromType)) {
            tvHeaderTitle.setText("销售出库");
            tvOrderNumText.setText("出库单号: ");

        }
        initView();

        mAdapter = new NewTpBomDetailsAdapter(this, fromType,getContentView(this));
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
                LogUtil.i("childCount=" + view.getChildCount());
                if (firstView != null) {
                    top = firstView.getTop();
                }
                LogUtil.i("firstVisibleItem=" + firstVisibleItem + ",firstVisiblePosition=" + firstVisiblePosition + ",firstView=" + firstView + ",top=" + top);

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
     * 获取备货详情
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
        OkGo.get(UrlTools.getInterfaceUrl(GOODS_STORAGE_OUT_GETBILL))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("storageOutId", orderId)


                .execute(new DialogCallback<LzyResponse<BomDetailsModel>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<BomDetailsModel> responseData, Call call, Response response) {

//                        if (lvOutList != null && lvOutList.isRefreshing()) {
//                            lvOutList.onRefreshComplete();
//                        }

//
                        if (null != responseData) {
                            mOutBoundModel = responseData.bean;
                            buttonFlg = responseData.buttonFlg;

                            orderNum = mOutBoundModel.getDispatchNo();
                            tvOrderNum.setText(orderNum);

                            LogUtil.d(mOutBoundListModel);


                            setRefreshList(isPullDown, getNewRankList(responseData.bean.getGatherList()));
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        if (lvOutList != null && lvOutList.isRefreshing()) {
//                            lvOutList.onRefreshComplete();
//                        }
                        ShowToast.tCustom(NewTpBomDetailsActivity.this, e.getMessage());
                    }
                });

    }

    /**
     * 按 状态 1->2->0 排序
     *
     * 2 -> 3 ->0
     *
     * @param listBeen
     * @return
     */
    private List<BomDetailsModel.GatherListBean> getNewRankList(List<BomDetailsModel.GatherListBean> listBeen) {

        List<BomDetailsModel.GatherListBean> listBeanList = new ArrayList<>();
        for (BomDetailsModel.GatherListBean listBean : listBeen) {
            List<BomDetailsModel.GatherListBean.DetailListBean> list = listBean.getDetailList();

            Collections.sort(list, new Comparator<BomDetailsModel.GatherListBean.DetailListBean>() {
                @Override
                public int compare(BomDetailsModel.GatherListBean.DetailListBean o1, BomDetailsModel.GatherListBean.DetailListBean o2) {

                    LogUtil.d("排序 :  o1 : " + o1.getStatus() + "   o2 : " + o2.getStatus());
                    int diff = StringUtil.String2int(o1.getStatus().equals("0") ? "3" : o1.getStatus()) - StringUtil.String2int(o2.getStatus().equals("0") ? "3" : o2.getStatus());

                    if (diff > 0) {
                        return 1;
                    } else if (diff < 0) {
                        return -1;
                    }
                    return 0; //相等为0
                }
            });

            listBean.setDetailList(list);
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
    private void setRefreshList(boolean isPullDown, List<BomDetailsModel.GatherListBean> result) {

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


        if ("1".equals(buttonFlg)) {

            tvOk.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_blue));
        } else {
            tvOk.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_gray));

        }


    }

    /**
     * @param
     * @return 最后更新的数据的位置(排序之后的)
     */
    private int[] getUpPosition(String palletNo) {

        int[] ints = new int[2];
        for (int i = 0; i < mOutBoundListModel.size(); i++) {
            BomDetailsModel.GatherListBean gatherListBean = mOutBoundListModel.get(i);
            List<BomDetailsModel.GatherListBean.DetailListBean> list = mOutBoundListModel.get(i).getDetailList();
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
                gatherListBean.setDetailList(list);
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

                if ("1".equals(buttonFlg)) {
                    upLoadOrderComfrim();
                } else {
                    showToast("当前有未备货的托盘,无法提交");

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
        OkGo.post(UrlTools.getInterfaceUrl(GOODS_STORAGEOUT_UPDATEBILL))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("storageOutId", NullToStr(orderId))
                .params("status", "2") //0待备货 1备货中 2已备货
                .execute(new DialogCallback<LzyResponse<String>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {
                        finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.tCustom(NewTpBomDetailsActivity.this, NullToStr(e.getMessage()));
                    }
                });
    }


    /**
     * 批量提交的接口
     */
    public void commitListBing(String bindingParamJson, final String type, final String palletNum) {
        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.post(UrlTools.getInterfaceUrl(MOBILE_GOODS_STORAGEOUT))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("storageOutParam", bindingParamJson)

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

                        ShowToast.tCustom(NewTpBomDetailsActivity.this, e.getMessage().toString());
                    }
                });
    }

    /**
     *
     * @param position
     * @param singleData
     */
    @Override
    public void myOrderOkClick(int position, final BomDetailsModel.GatherListBean.DetailListBean singleData) {
        if (!"1".equals(singleData.getStatus())){

//            PromptHintCancel p = new PromptHintCancel(this) {
//                @Override
//                protected void onOk() {
                    List<BomDetailsModel.GatherListBean.DetailListBean> listBeen = new ArrayList<>();
                    listBeen.add(singleData);
//
//                    if (mRfidOperationUtils.getReadTagByEpc(listBeen.get(0).getPalletNum())){
//                        commitListBing(getCommitJosn(listBeen,"1"),"",singleData.getPalletNum());
//
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
    public void myOrderCancelClick(int position, final  BomDetailsModel.GatherListBean.DetailListBean singleData) {

        if (!"2".equals(singleData.getStatus())){
//
//            PromptHintCancel p = new PromptHintCancel(this) {
//                @Override
//                protected void onOk() {
                    List<BomDetailsModel.GatherListBean.DetailListBean> listBeen = new ArrayList<>();
                    listBeen.add(singleData);
//                    if (mRfidOperationUtils.getReadTagByEpc(listBeen.get(0).getPalletNum())){
                        commitListBing(getCommitJosn(listBeen,"2"),"",singleData.getPalletNum());

//                    }
//                }
//            };
//            p.show(ExtraConfig.TypeCode.DIALOG_CANCEL_TO_EXIT);
        }

    }

    /**
     *
     * @return 获取提交数据json 串
     *
     */

    private String getCommitJosn(List<BomDetailsModel.GatherListBean.DetailListBean> singleData,String status){

        List<OrderCommitModel>   mOrderCommitModels = new ArrayList<>();

        for (int i = 0;i<singleData.size();i++){

            OrderCommitModel commitModel = new OrderCommitModel();
            commitModel.setStorageOutDetailId(singleData.get(i).getStorageOutDetailId());
            commitModel.setOpEquipment("1");//操作设备 1手持设备 2门设备 3人工
            commitModel.setStatus(status);//货物备货单状态：0待验收 2验收通过 3报修
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
    private BomDetailsModel.GatherListBean.DetailListBean getCheckBean(String palletId, String status) {

        for (BomDetailsModel.GatherListBean listBean : mOutBoundListModel) {
            for (BomDetailsModel.GatherListBean.DetailListBean detailListBean : listBean.getDetailList()) {
                if (detailListBean.getPalletNum().equals(palletId) && !isInList(palletId) && !status.equals(detailListBean.getStatus())) {

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
            for (BomDetailsModel.GatherListBean.DetailListBean listBean : detailListBeanList) {
                if (listBean.getPalletNum().equals(palletId)) {
                    return true;
                }
            }

        }

        return false;

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
                    commitListBing(getCommitJosn(detailListBeanList,"1"),"",TYPE_BY_COMMIT_LIST);
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
        BomDetailsModel.GatherListBean.DetailListBean singleData = getCheckBean(code, status);

        if (null != singleData) {
            List<BomDetailsModel.GatherListBean.DetailListBean> listBeen = new ArrayList<>();
            listBeen.add(singleData);
//            if (mRfidOperationUtils.getReadTagByEpc(listBeen.get(0).getPalletNum())){
//
//                commitListBing(getCommitJosn(listBeen,status),"",singleData.getPalletNum());
//
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

                BomDetailsModel.GatherListBean.DetailListBean  singleData = getCheckBean(strEpc, "1");

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




}
