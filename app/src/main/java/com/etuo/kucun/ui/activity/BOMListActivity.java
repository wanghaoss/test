package com.etuo.kucun.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseHeaderBarActivity;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.callback.DialogCallback;
import com.etuo.kucun.model.BOMListModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.ui.adapter.BomListAdapter;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.NetUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.UrlTools;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;


/**
 * BOM列表 备货列表
 * Created by xfy on 2020/02/10.
 */

public class BOMListActivity extends BaseHeaderBarActivity {
    @BindView(R.id.lv_massagelist)
    PullToRefreshListView lvMassagelist;
    @BindView(R.id.im_no_date)
    LinearLayout imNoDate;
    private BomListAdapter mAdapter;
    private List<BOMListModel> listModels = new ArrayList<>();

    private boolean end = false;
    private int pageIndex = 0;
    private String branchId ;
    private String FromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massage);
        ButterKnife.bind(this);
        FromIntent = getIntent().getStringExtra(ExtraConfig.TypeCode.FROM_INTTENT);

        if (ExtraConfig.IntentType.FROM_INTENT_BY_BEIHUO.equals(FromIntent)){
            setHeaderTitle("备货列表");

        }
        mAdapter = new BomListAdapter(this,listModels,FromIntent);
        lvMassagelist.getRefreshableView().setAdapter(mAdapter);
        initPtrFrame();
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

        lvMassagelist.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex = 0;
                if (listModels != null) {
                    listModels.clear();
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
     *   prepareNo 查询条件：货物备货单号，可选
     modelId 查询条件：托盘型号ID
     goodsName 查询条件：货物名称
     storageAreaId1 查询条件：一级库区ID
     storageAreaId2 查询条件：二级库区ID
     status 查询条件：货物备货单状态，0待备货 1备货中 2已备货
     start 分页：起始页
     length 分页：每页条数
     */
    public void getData(final boolean first, final boolean isPullDown) {
        if (NetUtil.getNetWorkState(this) == -1) {

            if (lvMassagelist != null && lvMassagelist.isRefreshing()) {
                lvMassagelist.onRefreshComplete();
            }
            ShowToast.toastTime(this, this.getResources().getString(R.string.net_error), 3);

            return;
        }

        String firstIdx = "0";
        String maxCount = String.valueOf(ExtraConfig.DEFAULT_PAGE_COUNT);
        if (!isPullDown) {
            if (end) {
                setRefreshList(isPullDown, new ArrayList<BOMListModel>());
                return;
            } else {
                firstIdx = String.valueOf(pageIndex);
            }
        }


        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.get(UrlTools.getInterfaceUrl(UrlTools.BILL_LIST))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("start", firstIdx)
                .params("length", maxCount)
                .params("status","")
                .params("modelId","")
                .params("storageAreaId1","")
                .params("storageAreaId2","")

                .execute(new DialogCallback<LzyResponse<List<BOMListModel>>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<List<BOMListModel>> responseData, Call call, Response response) {

                        if (lvMassagelist != null && lvMassagelist.isRefreshing()) {
                            lvMassagelist.onRefreshComplete();
                        }
                        if (null != responseData) {


                            //branchName = responseData.commonFiled;
                            setRefreshList(isPullDown, responseData.data);

                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (lvMassagelist != null && lvMassagelist.isRefreshing()) {
                            lvMassagelist.onRefreshComplete();
                        }
                        ShowToast.tCustom(BOMListActivity.this, e.getMessage().toString());
                    }
                });
    }

    /**
     * 设置数据
     *
     * @param isPullDown
     * @param result
     */
    private void setRefreshList(boolean isPullDown, List<BOMListModel> result) {
        if (result.size() < ExtraConfig.DEFAULT_PAGE_COUNT) {
            end = true;
            if (isPullDown && result.size() == 0) {
                imNoDate.setVisibility(View.VISIBLE);
            } else {
                imNoDate.setVisibility(View.GONE);
            }

            lvMassagelist.setPullLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);
            lvMassagelist.setReleaseLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);
            lvMassagelist.setRefreshingLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);

        } else {
            end = false;
            lvMassagelist.setPullLabel("上拉刷新", PullToRefreshBase.Mode.PULL_FROM_END);
            lvMassagelist.setReleaseLabel("放开以刷新", PullToRefreshBase.Mode.PULL_FROM_END);
            lvMassagelist.setRefreshingLabel("正在载入", PullToRefreshBase.Mode.PULL_FROM_END);

        }
        if (isPullDown) {
            pageIndex = 0;
            mAdapter.removeAll();
        }
        pageIndex++;

        for (int i = 0; i < result.size(); i++) {
            listModels.add(result.get(i));
        }
        mAdapter.updata(listModels);
        mAdapter.notifyDataSetChanged();
        lvMassagelist.onRefreshComplete();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

}
