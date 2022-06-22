package com.etuo.kucun.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.etuo.kucun.R;
import com.etuo.kucun.callback.DialogCallback;
import com.etuo.kucun.model.BigPicModel;
import com.etuo.kucun.model.TpAndGoodInfoModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.ui.activity.NewTpBomDetailsActivity;
import com.etuo.kucun.ui.adapter.FindTpByRfidInListAdapter;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.PWUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.StringUtil;
import com.etuo.kucun.utils.UrlTools;
import com.lzy.okgo.OkGo;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.etuo.kucun.utils.UrlTools.GET_TP_BIG_PIC;
import static com.etuo.kucun.utils.UrlTools.MOBILE_GOODS_STORAGEOUT;

/**
 * ================================================
 *
 * @author :yhn
 * @version :
 * @date :2020/02/18/18:08
 * @ProjectNameDescribe : 显示大图
 * 修订历史：
 * ================================================
 */

public class BigPicPopwindow extends PWUtil implements View.OnClickListener {


    @BindView(R.id.myBannerPic)
    Banner mMyBannerPic;
    @BindView(R.id.iv_close_pop)
    ImageView mIvClosePop;
    private Activity mActivity;
    private List<BigPicModel> mInfoListModels;
    /**
     * banner集合
     **/
    private List<String> mBannerData = new ArrayList<>();


    public void showPW(Activity activity, String modelId,String type, View locationView) {
        if (activity == null || StringUtil.isEmpty(modelId)|| locationView == null) {
            return;
        }
        mActivity = activity;
        if (popupWindow == null) {
            View rootView = createPW(mActivity, R.layout.pop_big_pic);
            ButterKnife.bind(this, rootView);
        }
        if (popupWindow.isShowing()) {
            return;
        }

        mIvClosePop.setOnClickListener(this);

        setBackgroundAlpha(mActivity, 0.8f);
        popupWindow.showAtLocation(locationView, Gravity.CENTER, 0, 0);

        if (type.equals("TP")){
            getImgByTp(modelId);
        }else {

        }

    }



    /**
     * 获取托盘大图
     */
    public void getImgByTp(String modelId) {
        LogUtil.d("token : " + PreferenceCache.getToken());
        OkGo.get(UrlTools.getInterfaceUrl(GET_TP_BIG_PIC))
                .tag(this)
                .params("token", PreferenceCache.getToken())
                .params("modelId", modelId)

                .execute(new DialogCallback<LzyResponse<List<BigPicModel>>>(mActivity, "加载中..") {
                    @Override
                    public void onSuccess(LzyResponse<List<BigPicModel>> responseData, Call call, Response response) {
                        //c测试
//                    List<BigPicModel> bigPicModels = new ArrayList<BigPicModel>();
//
//                    BigPicModel picModel = new BigPicModel();
//                    picModel.setImgPath("http://www.cczdgm.cn/upfile/isClass/pic/20150826002801-9164960822090506.JPG");
//                    bigPicModels.add(picModel);
//                    bigPicModels.add(picModel);
//                    bigPicModels.add(picModel);
                        setData(responseData.data);
//                        setData(bigPicModels);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        ShowToast.tCustom(mActivity, e.getMessage().toString());
                    }
                });
    }

    private void setData(List<BigPicModel> viewData) {

        setBanner(viewData);

    }

    /**
     * 设置轮播图
     */
    private void setBanner(List<BigPicModel> imgListBeen) {
        for (int i = 0; i < imgListBeen.size(); i++) {
            mBannerData.add(imgListBeen.get(i).getImgPath());
        }
        //设置banner属性
        mMyBannerPic.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mMyBannerPic.setImageLoader(new MyLoader());
        mMyBannerPic.setImages(mBannerData);
        mMyBannerPic.setBannerAnimation(Transformer.Default);
        mMyBannerPic.isAutoPlay(false);
        mMyBannerPic.setDelayTime(3000);
        mMyBannerPic.setIndicatorGravity(BannerConfig.CENTER);
        mMyBannerPic.start();

    }
    /**
     * 自定义的图片加载器
     **/
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).placeholder(R.mipmap.icon_de_kind).into(imageView);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_pop:

                popupWindow.dismiss();
                break;

            default:
                break;
        }
    }
}
