package com.etuo.kucun.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.etuo.kucun.IntentConstant;
import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseFragment;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.callback.DialogCallback;
import com.etuo.kucun.model.DamagedModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.ui.activity.AddDamagedActivity;
import com.etuo.kucun.ui.activity.NewMipcaActivityCapture;
import com.etuo.kucun.ui.adapter.DamagedListAdapter;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.NetUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.StringUtils;
import com.etuo.kucun.utils.UrlTools;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

import static com.etuo.kucun.utils.UrlTools.DAMAGED_LIST;


/**
 * ================================================
 *
 * @author :xfy
 * @version :V 1.0.0
 * @date :2018/11/20/11:56
 * @ProjectNameDescribe :DamagedFragment 报损界面(报损清单Fragment下的切换的两个fragment)
 * 修订历史：
 * ================================================
 */

@SuppressLint("ValidFragment")
public class DamagedFragment extends BaseFragment implements DamagedListAdapter.OnClickBtItem {
    @BindView(R.id.lv_list)
    PullToRefreshListView lvList;
    @BindView(R.id.im_icon_no_data)
    ImageView imIconNoData;
    @BindView(R.id.tv_massage)
    TextView tvMassage;
    @BindView(R.id.bt_add_damaged)
    Button btAddDamaged;
    @BindView(R.id.im_no_date)
    LinearLayout imNoDate;
    @BindView(R.id.bt_add_damaged_bottom)
    Button btAddDamagedBottom;
    Unbinder unbinder;
    private String flagFrom;
    private boolean end = false;
    private int pageIndex = 0;
    private List<DamagedModel> mDamagedModels = new ArrayList<>();
    private DamagedListAdapter mAdapter;

    public static Fragment newInstance(String fromIntentFlag) {
        Fragment fragment = new DamagedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IntentConstant.FROMINTENTFLAG, fromIntentFlag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int bindView() {
        return R.layout.fragment_damaged;
    }

    @Override
    protected void initView() {
        if (getArguments() == null) {
            return;
        } else {
            flagFrom = getArguments().getString(IntentConstant.FROMINTENTFLAG);
            if (flagFrom != null) {
                switch (flagFrom) {
                    case "0":
                        btAddDamagedBottom.setVisibility(View.VISIBLE);
                        break;
                    case "1":
                        btAddDamaged.setVisibility(View.GONE);
                        btAddDamagedBottom.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
                mAdapter = new DamagedListAdapter(mContext, flagFrom);
                lvList.getRefreshableView().setAdapter(mAdapter);
                mAdapter.OnClickBtItem(this);
            }
        }

    }

    @Override
    protected void initData() {
        initPtrFrame();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (flagFrom != null) {
            getData(true, true);
        }
    }

    /**
     * 刷新 和 加载
     */
    private void initPtrFrame() {
        lvList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex = 0;
                if (mDamagedModels != null) {
                    mDamagedModels.clear();
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
     * 报损列表请求
     **/
    public void getData(final boolean first, final boolean isPullDown) {
        if (NetUtil.getNetWorkState(getActivity()) == -1) {
            if (lvList != null && lvList.isRefreshing()) {
                lvList.onRefreshComplete();
            }
            ShowToast.toastTime(getActivity(), getActivity().getResources().getString(R.string.net_error), 3);
            return;
        }

        String firstIdx = "0";
        String maxCount = String.valueOf(ExtraConfig.DEFAULT_PAGE_COUNT);
        if (!isPullDown) {
            if (end) {
                setRefreshList(isPullDown, new ArrayList<DamagedModel>());
                return;
            } else {
                firstIdx = String.valueOf(pageIndex);
            }
        }

        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.get(UrlTools.getInterfaceUrl(DAMAGED_LIST))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("status", flagFrom)
                .params("start", firstIdx)
                .params("length", maxCount)

                .execute(new DialogCallback<LzyResponse<List<DamagedModel>>>(getActivity(), "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<List<DamagedModel>> responseData, Call call, Response response) {
                        if (lvList != null && lvList.isRefreshing()) {
                            lvList.onRefreshComplete();
                        }
                        if (null != responseData) {
                            LogUtil.d(responseData.data);
                            setRefreshList(isPullDown, responseData.data);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (lvList != null && lvList.isRefreshing()) {
                            lvList.onRefreshComplete();
                        }
                        ShowToast.tCustom(getActivity(), StringUtils.NullToStr(e.getMessage()));
                    }
                });

    }

    /**
     * 设置数据
     *
     * @param isPullDown
     * @param result
     */
    private void setRefreshList(boolean isPullDown, List<DamagedModel> result) {
        if (result.size() < ExtraConfig.DEFAULT_PAGE_COUNT) {
            end = true;
            if (isPullDown && result.size() == 0) {
                imNoDate.setVisibility(View.VISIBLE);
                switch (flagFrom) {
                    case "1":
                        btAddDamaged.setVisibility(View.GONE);
                        break;
                    default:
                        btAddDamaged.setVisibility(View.VISIBLE);
                        break;
                }
            } else {
                imNoDate.setVisibility(View.GONE);
            }

            lvList.setPullLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);
            lvList.setReleaseLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);
            lvList.setRefreshingLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);

        } else {
            end = false;
            lvList.setPullLabel("上拉刷新", PullToRefreshBase.Mode.PULL_FROM_END);
            lvList.setReleaseLabel("放开以刷新", PullToRefreshBase.Mode.PULL_FROM_END);
            lvList.setRefreshingLabel("正在载入", PullToRefreshBase.Mode.PULL_FROM_END);

        }
        if (isPullDown) {
            pageIndex = 0;
            mAdapter.removeAll();
        }
        pageIndex++;

        for (int i = 0; i < result.size(); i++) {
            mDamagedModels.add(result.get(i));

        }
        mAdapter.updata(mDamagedModels);
        mAdapter.notifyDataSetChanged();
        lvList.onRefreshComplete();


    }

    @Override
    public void myDeleteClick(int position, String id) {
        //跳转报损详情
        Intent intent = new Intent(mContext, AddDamagedActivity.class);
        intent.putExtra(ExtraConfig.TypeCode.INTENT_ID, id);
        intent.putExtra(ExtraConfig.TypeCode.INTENT_DAMAGED_FROM, "0");
        startActivity(intent);
    }

    @OnClick(R.id.bt_add_damaged)
    public void onBtAddDamagedClicked() {
        //跳转新增报损
        Intent intent = new Intent(mContext, NewMipcaActivityCapture.class);
        intent.putExtra(ExtraConfig.TypeCode.INTENT_FROM_INTENT, "web_intent_breakage");
        startActivity(intent);
    }

    @OnClick(R.id.bt_add_damaged_bottom)
    public void onBtAddDamagedBottomClicked() {
        //跳转新增报损
        Intent intent = new Intent(mContext, NewMipcaActivityCapture.class);
        intent.putExtra(ExtraConfig.TypeCode.INTENT_FROM_INTENT, "web_intent_breakage");
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkGo.getInstance().cancelTag(this);
    }
}
