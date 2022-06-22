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
import com.etuo.kucun.model.OrderCommitModel;
import com.etuo.kucun.model.TpInStorageModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.ui.adapter.NewTpInStorageDetailsAdapter;
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

import static com.etuo.kucun.utils.UrlTools.PALLET_BATCH_OPERATION;
import static com.etuo.kucun.utils.UrlTools.TP_IN_STORAGE_GETBILL;
import static com.etuo.kucun.utils.UrlTools.UPDATE_BILL;

/**
 * ================================================
 *
 * @author :haining
 * @version :
 * @date :2020/02/08
 * @ProjectNameDescribe : BOM 备货 等详情操作界面
 * 修订历史：
 * ================================================
 */

public class NewTpInStorageDetailsActivity extends BaseActivity implements NewTpInStorageDetailsAdapter.OnClickBtItem {
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
    private List<TpInStorageModel.GatherListBean> mOutBoundListModel = new ArrayList<>();
    private NewTpInStorageDetailsAdapter mAdapter;
    private TpInStorageModel mOutBoundModel;
    String orderId;

    String orderNum;
    private String buttonFlg;//能否操作数据  0 不能  1能
    private String operationType;

    public static String WRITE_TYPE = "write";
    public static String CLEAR_TYPE = "clear";

    private String checkTypeScanTp = ONE_TYPE;//
    public static String ONE_TYPE = "one";
    public static String MORE_TYPE = "more";
    public static String SCAN_TYPE = "scan";

    public static String TYPE_BY_COMMIT_LIST = "commitList";
    private String fromType;//

    private String checkOnePalletNum = "";

    private List<TpInStorageModel.GatherListBean.DetailListBean> detailListBeanList = new ArrayList<>();//批量操作是缓存的list ,只有在按下确定键开始添加,松开停止 然后提交数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_detatils);
        ButterKnife.bind(this);
        orderId = getIntent().getStringExtra(ExtraConfig.TypeCode.INTENT_ORDER_ID);//订单id
        fromType = getIntent().getStringExtra(ExtraConfig.TypeCode.FROM_INTTENT);//从哪个界面跳转

        tvHeaderTitle.setText("托盘入库");
        tvOrderNumText.setText("入库单号: ");
        initView();

        mAdapter = new NewTpInStorageDetailsAdapter(this, fromType,getContentView(this));
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
     * 获取详情
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
        OkGo.get(UrlTools.getInterfaceUrl(TP_IN_STORAGE_GETBILL))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("palletStorageInId", orderId)

                .execute(new DialogCallback<LzyResponse<TpInStorageModel>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<TpInStorageModel> responseData, Call call, Response response) {

//                        if (lvOutList != null && lvOutList.isRefreshing()) {
//                            lvOutList.onRefreshComplete();
//                        }

//
                        if (null != responseData) {
                            mOutBoundModel = responseData.bean;
                            buttonFlg = responseData.buttonFlg;

                            orderNum = mOutBoundModel.getPalletStorageInNo();
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
                        ShowToast.tCustom(NewTpInStorageDetailsActivity.this, e.getMessage());
                    }
                });

    }

    /**
     * 按 状态 1->2->0 排序
     *
     * @param listBeen
     * @return
     */
    private List<TpInStorageModel.GatherListBean> getNewRankList(List<TpInStorageModel.GatherListBean> listBeen) {

        List<TpInStorageModel.GatherListBean> listBeanList = new ArrayList<>();
        for (TpInStorageModel.GatherListBean listBean : listBeen) {
            List<TpInStorageModel.GatherListBean.DetailListBean> list = listBean.getDetailList();

            Collections.sort(list, new Comparator<TpInStorageModel.GatherListBean.DetailListBean>() {
                @Override
                public int compare(TpInStorageModel.GatherListBean.DetailListBean o1, TpInStorageModel.GatherListBean.DetailListBean o2) {

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
    private void setRefreshList(boolean isPullDown, List<TpInStorageModel.GatherListBean> result) {

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
            TpInStorageModel.GatherListBean gatherListBean = mOutBoundListModel.get(i);
            List<TpInStorageModel.GatherListBean.DetailListBean> list = mOutBoundListModel.get(i).getDetailList();
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
        OkGo.post(UrlTools.getInterfaceUrl(UPDATE_BILL))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("prepareId", NullToStr(orderId))
                .params("status", "2") //0待备货 1备货中 2已备货
                .execute(new DialogCallback<LzyResponse<String>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {
                        finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.tCustom(NewTpInStorageDetailsActivity.this, NullToStr(e.getMessage()));
                    }
                });
    }

    /**
     * 批量提交绑定货物接口
     */
    public void commitListBing(String bindingParamJson, final String type, final String palletNum) {
        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.post(UrlTools.getInterfaceUrl(PALLET_BATCH_OPERATION))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("storageInParam", bindingParamJson)

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

                        ShowToast.tCustom(NewTpInStorageDetailsActivity.this, e.getMessage().toString());
                    }
                });
    }


    /**
     * @param position
     * @param singleData
     */
    @Override
    public void myOrderOkClick(int position, final TpInStorageModel.GatherListBean.DetailListBean singleData) {
        if (!"1".equals(singleData.getStatus())) {
//
//            PromptHintCancel p = new PromptHintCancel(this) {
//                @Override
//                protected void onOk() {
                    String wrDatas = StringUtil.getWriteData(singleData.getAreaName(), "", "", "","");
//                    if (mRfidOperationUtils.eraseDataByWrite(singleData.getPalletNum())) {
//                        if (mRfidOperationUtils.write(singleData.getPalletNum(), wrDatas)) {
//                            List<TpInStorageModel.GatherListBean.DetailListBean> listBeen = new ArrayList<>();
//                            listBeen.add(singleData);
//
//                            commitListBing(getCommitJosn(listBeen, "1"), "", singleData.getPalletNum());
//                        }
//                    }


//                }
//            };
//            p.show(ExtraConfig.TypeCode.DIALOG_CAOZUO_TO_EXIT);
        }


    }

    /**
     * @param position
     * @param singleData
     */
    @Override
    public void myOrderCancelClick(int position, final TpInStorageModel.GatherListBean.DetailListBean singleData) {

        if (!"2".equals(singleData.getStatus())) {

//            PromptHintCancel p = new PromptHintCancel(this) {
//                @Override
//                protected void onOk() {

//                        if (mRfidOperationUtils.eraseDataByWrite(singleData.getPalletNum())) {
//                            List<TpInStorageModel.GatherListBean.DetailListBean> listBeen = new ArrayList<>();
//                            listBeen.add(singleData);
//                            commitListBing(getCommitJosn(listBeen, "2"), "", singleData.getPalletNum());
//                        }



//                }
//            };
//            p.show(ExtraConfig.TypeCode.DIALOG_CANCEL_TO_EXIT);
        }


    }
    @Override
    public void myAllOrderOkClick(int Fposition, final TpInStorageModel.GatherListBean singleData) {

        if (!isOkByAllStatus(singleData.getDetailList(),"1")){
            PromptHintCancel p = new PromptHintCancel(this) {
                @Override
                protected void onOk() {

                    getCanWriteOrClearList(singleData.getDetailList(),"1");
                    operationType = WRITE_TYPE;
                    new WriteDataTask().execute();
//                    commitListBing(getCommitJosn(singleData.getDetailList(),"1"),TYPE_BY_COMMIT_LIST,"");
                }
            };
            p.show("确认对型号为" + singleData.getPalletModel()+"的所有托盘进行入库操作吗?");
        }else {
            ShowToast.tCustom(mContext,"所有托盘已是入库状态,请勿多次操作");
        }
    }

    @Override
    public void myAllOrderCancelClick(int Fposition, final TpInStorageModel.GatherListBean singleData) {
        if (!isOkByAllStatus(singleData.getDetailList(),"2")){
            PromptHintCancel p = new PromptHintCancel(this) {
                @Override
                protected void onOk() {

                    getCanWriteOrClearList(singleData.getDetailList(),"2");
                    operationType = CLEAR_TYPE;
                    new WriteDataTask().execute();

//                    commitListBing(getCommitJosn(singleData.getDetailList(),"2"),TYPE_BY_COMMIT_LIST,"");
                }
            };
            p.show("确认对型号为" + singleData.getPalletModel()+"的所有托盘进行清除操作吗?");
        }else {
            ShowToast.tCustom(mContext,"所有托盘已是清除状态,请勿多次操作");
        }
    }


    /**
     * @param singleData 待提交托盘list
     * @param status     待提交更改状态
     * @return list->json
     */
    private String getCommitJosn(List<TpInStorageModel.GatherListBean.DetailListBean> singleData, String status) {


        List<OrderCommitModel> mOrderCommitModels = new ArrayList<>();

        for (int i = 0; i < singleData.size(); i++) {
            OrderCommitModel commitModel = new OrderCommitModel();
            commitModel.setPalletStorageInDetailId(singleData.get(i).getPalletStorageInDetailId());
            commitModel.setOpEquipment("1");//操作设备 1手持设备 2门设备 3人工
            commitModel.setStatus(status);//货物备货单状态：0未备货 1已备货 2解绑;
            mOrderCommitModels.add(commitModel);
        }

        String json = new Gson().toJson(mOrderCommitModels);


        LogUtil.d("tojson : " + json);
        return json;
    }


    /**
     * 手动批量提交时
     * @param listBeen
     * @param status
     */
    private void getCanWriteOrClearList(List<TpInStorageModel.GatherListBean.DetailListBean > listBeen,String status){
        detailListBeanList = new ArrayList<>();
        for (TpInStorageModel.GatherListBean.DetailListBean detailListBean : listBeen) {
            if (!status.equals(detailListBean.getStatus())) {

                LogUtil.d("遍历提交的托盘 : " + detailListBean.getPalletNum()  );
                detailListBeanList.add(detailListBean);
            }
        }
    }

    /**
     * @param palletId 托盘编号
     * @param status   托盘状态
     * @return 扫描托盘是不是可以提交
     */
    private TpInStorageModel.GatherListBean.DetailListBean getCheckBean(String palletId, String status) {

        for (TpInStorageModel.GatherListBean listBean : mOutBoundListModel) {
            for (TpInStorageModel.GatherListBean.DetailListBean detailListBean : listBean.getDetailList()) {
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
            for (TpInStorageModel.GatherListBean.DetailListBean listBean : detailListBeanList) {
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
            waitCnt = waitCnt + mOutBoundListModel.get(i).getWaitCnt() ;

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
    private boolean isOkByAllStatus(List<TpInStorageModel.GatherListBean.DetailListBean> listBeen ,String status){
        for (int i = 0; i< listBeen.size() ; i++){

            if (!status.equals(listBeen.get(i).getStatus())){
                return false;
            }

        }

        return  true;
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
                    operationType = WRITE_TYPE;
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
        TpInStorageModel.GatherListBean.DetailListBean singleData = getCheckBean(code, status);

        if (null != singleData) {
            List<TpInStorageModel.GatherListBean.DetailListBean> listBeen = new ArrayList<>();
            listBeen.add(singleData);

            String wrDatas = StringUtil.getWriteData(singleData.getAreaName(), "", "", "", "");
//            if (mRfidOperationUtils.eraseDataByWrite(code)) {
//                boolean write = mRfidOperationUtils.write(code, wrDatas);
//                if (write) {
//                    commitListBing(getCommitJosn(listBeen, status), "", singleData.getPalletNum());
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

                TpInStorageModel.GatherListBean.DetailListBean singleData = getCheckBean(strEpc, "1");

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
     * 异步写数据
     *
     * @author yhn
     */
    public class WriteDataTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        List<TpInStorageModel.GatherListBean.DetailListBean> newDetailListBeen;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            LogUtil.d("WriteDataTask doInBackground");
            Boolean aBoolean = false;
            newDetailListBeen = new ArrayList<>();

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

//                    if (mRfidOperationUtils.eraseDataByWrite(detailListBeanList.get(i).getPalletNum())) {
//
//                        //判断读写模式
//                        if (WRITE_TYPE.equals(operationType)){
//                            String wrDatas = StringUtil.getWriteData(detailListBeanList.get(i).getAreaName(), "", "", "", "");
//
//                            boolean write = mRfidOperationUtils.write(detailListBeanList.get(i).getPalletNum(), wrDatas);
//                            if (write) {
//                                newDetailListBeen.add(detailListBeanList.get(i));
//
//                            }
//                        }else {
//
//                            newDetailListBeen.add(detailListBeanList.get(i));
//                        }
//
//                    }
                }

                hideLoadingDialog();
                LogUtil.d("开始写数据完成,开始提交数据" + System.currentTimeMillis());
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
                if (WRITE_TYPE.equals(operationType)){
                    commitListBing(getCommitJosn(newDetailListBeen, "1"), TYPE_BY_COMMIT_LIST, "");

                }else {
                    commitListBing(getCommitJosn(newDetailListBeen, "2"), TYPE_BY_COMMIT_LIST, "");
                }
            } else {
                ShowToast.tCustom(mContext, "暂时未找到入库单里的托盘,请重新扫描");
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
                mypDialog.setMessage("正在写数据,请勿移走手持设备...");
                mypDialog.setCanceledOnTouchOutside(false);
            }

            if (!mypDialog.isShowing()) {
                mypDialog.show();

            }

        }
    }


}