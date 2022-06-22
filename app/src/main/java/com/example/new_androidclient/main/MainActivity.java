package com.example.new_androidclient.main;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.AccountSecurityUnBindDialog;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.edition.UpDateDownloadManager;
import com.example.new_androidclient.edition.bean.EditionBean;
import com.example.new_androidclient.main.bean.HomeListBean;

import java.util.ArrayList;
import java.util.List;

import static com.example.new_androidclient.Other.SPString.UserName;

@Route(path = RouteString.MainActivity)
public class MainActivity extends BaseActivity {

    List<HomeListBean> listBeans = new ArrayList<>();
    RecyclerView recyclerView;
    TitleLayout titleLayout;

    private BluetoothAdapter bluetoothAdapter;
    private String versionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.main_activity_recycler_view);
        String name = (String) SPUtil.getData(UserName, "");
        titleLayout = findViewById(R.id.activity_main_title);
        titleLayout.setName(name);
        initList();
        openBlueTooth();
        MainActivityAdapter activityAdapter = new MainActivityAdapter(listBeans,
                BR.homeListBean, (view, position) -> onItemClick(position));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(activityAdapter);

        //     checkUpdate();  东科先不设置更新

    }


    private void initList() {
//        String[] name = {"设备管理", "巡检管理", "危险源管理", "作业管理", "承包商管理",
//                "事故事件管理", "生产管理", "培训管理", "变更管理", "应急管理", "隐患管理"};
//        int[] image = {R.drawable.dl_home_shebei,
//                R.drawable.dl_home_xunjian,
//                R.drawable.dl_home_weixian,
//                R.drawable.dl_home_zuoye,
//                R.drawable.dl_home_chengbao,
//                R.drawable.dl_home_shigu,
//                R.drawable.dl_home_shengchan,
//                R.drawable.dl_home_peixun,
//                R.drawable.dl_home_biangeng,
//                R.drawable.dl_home_yingji,
//                R.drawable.dl_home_yingji};

        String[] name = {"巡检管理",
                "隐患管理",
                "流程待办管理",
                "设备管理",
                    "系统更新",
                    "作业模块"
        };
        int[] image = {
                R.drawable.dl_home_xunjian,
                R.drawable.dl_home_yingji,
                R.drawable.dl_home_biangeng,
                R.drawable.device,
                     R.drawable.qwe,
                     R.drawable.work_order
        };

        for (int i = 0; i < name.length; i++) {
            HomeListBean homeListBean = new HomeListBean();
            homeListBean.name = name[i];
            homeListBean.image = image[i];
            listBeans.add(homeListBean);
        }
    }

    private void openBlueTooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
    }

    public void onItemClick(int position) {
        String str = "";
        switch (position) {
            case 0:
                str = RouteString.AreaDistinguishActivity; //巡检
                break;
            case 1:
                str = RouteString.HazardMainActivity; //隐患
                break;
            case 2:
                str = RouteString.WaitMainActivity; //流程
                break;
            case 3:
                //str = RouteString.DevicePlanManagementActivity; //设备
                str = RouteString.SearchActivity; //直接到搜索模块，另外两个还没有经过测试
                break;
            case 4:
                str = RouteString.EditionAndPushActivity;//系统更新
                break;
            case 5:
                str = RouteString.WorkInitialActivity;//作业
                break;
//            default:
//                ToastUtil.show(mContext, "暂未开发");
//                break;
        }
        if (str != "") {
            ARouter.getInstance().build(str).navigation();
        }
    }

    //版本更新接口
    private void checkUpdate() {
        RetrofitUtil.getApi().selectAppStore("0", "zhuning-isim")
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<EditionBean>(mContext, false) {

                    @Override
                    public void onSuccess(EditionBean bean) {
                        if (bean != null) {
                            isUpdate(bean);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //版本更新，判断需不需要更新
    protected void isUpdate(EditionBean mBean) {
        PackageManager manager = mContext.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            versionName = info.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (mBean != null) {
            Double value = Double.valueOf(mBean.getVer());
            int nameValue = value.intValue();
            Double versionValue = Double.valueOf(versionName);
            int versionNameValue = versionValue.intValue();

            String version = String.valueOf(versionName);
            if (!version.equals(mBean.getVer()) && nameValue > versionNameValue) {
                new UpDateDownloadManager(mContext, mBean).downApk();
            }
        }
    }

}
