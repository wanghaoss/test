package com.example.new_androidclient.edition;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.edition.bean.EditionBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *版本更新与
 **/
@Route(path = RouteString.EditionAndPushActivity)
public class EditionAndPushActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.swh_status)
    Switch swhStatus;
    @BindView(R.id.edition)
    RelativeLayout edition;
    @BindView(R.id.edition_name)
    TextView editionName;

    private EditionBean mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition_and_push);
        ButterKnife.bind(this);

        swhStatus.setOnCheckedChangeListener(this);

        getView();
    }

    private void getView() {
        PackageManager manager = mContext.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(mContext.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = info.versionName;
        editionName.setText(versionName);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.swh_status:
                if (compoundButton.isChecked()) {
                    JPushInterface.stopPush(mContext);
                    ToastUtil.show(mContext, "关闭推送通知");
                } else {
                    JPushInterface.resumePush(mContext);
                    ToastUtil.show(mContext, "接收推送通知");
                }
                break;

        }
    }

    @OnClick(R.id.edition)
    public void onViewClicked() {
        new UpDateDownloadManager(mContext, mBean).downApk();
    }


    public void checkUpdate() {
        RetrofitUtil.getApi().selectAppStore("0", "zhuning-isim")
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<EditionBean>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(EditionBean bean) {
                        if (bean != null) {

                            mBean = bean;
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUpdate();
    }
}
