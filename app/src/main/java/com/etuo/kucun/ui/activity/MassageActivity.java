package com.etuo.kucun.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseHeaderBarActivity;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.callback.DialogCallback;
import com.etuo.kucun.model.MassageListModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.ui.adapter.MassageListAdapter;
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

import static com.etuo.kucun.utils.UrlTools.MASSAGE_LIST;

/**
 * 消息列表页面
 * Created by xfy on 2018/11/19.
 */

public class MassageActivity extends BaseHeaderBarActivity {
    @BindView(R.id.lv_massagelist)
    PullToRefreshListView lvMassagelist;
    @BindView(R.id.im_no_date)
    LinearLayout imNoDate;
    private MassageListAdapter mAdapter;
    private List<MassageListModel> mMassageListModel = new ArrayList<>();

    private boolean end = false;
    private int pageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massage);
        ButterKnife.bind(this);
        setHeaderTitle("我的消息");
        getData(true, true);
        mAdapter = new MassageListAdapter(this);
        lvMassagelist.getRefreshableView().setAdapter(mAdapter);
        initPtrFrame();
    }

    /**
     * 刷新 和 加载
     */
    private void initPtrFrame() {

        lvMassagelist.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex = 0;
                if (mMassageListModel != null) {
                    mMassageListModel.clear();
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
                setRefreshList(isPullDown, new ArrayList<MassageListModel>());
                return;
            } else {
                firstIdx = String.valueOf(pageIndex);
            }
        }

        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.get(UrlTools.getInterfaceUrl(MASSAGE_LIST))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("start", firstIdx)
                .params("length", maxCount)

                .execute(new DialogCallback<LzyResponse<List<MassageListModel>>>(this, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<List<MassageListModel>> responseData, Call call, Response response) {

                        if (lvMassagelist != null && lvMassagelist.isRefreshing()) {
                            lvMassagelist.onRefreshComplete();
                        }
                        if (null != responseData) {

                            //mMassageListModel = responseData.data;
                            LogUtil.d(mMassageListModel);
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
                        ShowToast.tCustom(MassageActivity.this, e.getMessage().toString());
                    }
                });
    }

    /**
     * 设置数据
     *
     * @param isPullDown
     * @param result
     */
    private void setRefreshList(boolean isPullDown, List<MassageListModel> result) {
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
            mMassageListModel.add(result.get(i));
        }
        mAdapter.updata(mMassageListModel);
        mAdapter.notifyDataSetChanged();
        lvMassagelist.onRefreshComplete();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

}
