package com.example.new_androidclient.Other;

import android.app.Activity;
import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ScreenAdapter;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.BuildConfig;

import static com.example.new_androidclient.Util.ScreenAdapter.MATCH_BASE_WIDTH;
import static com.example.new_androidclient.Util.ScreenAdapter.MATCH_UNIT_DP;

public class App extends Application {
    private List<Activity> activityList = new ArrayList<>();
    private static App app;
    private boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        ScreenAdapter.setup(this);  //新机 620*1280
        ScreenAdapter.register(this, 360, MATCH_BASE_WIDTH, MATCH_UNIT_DP);

        ARouter.init(this);
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }

        ZXingLibrary.initDisplayOpinion(this);

        SPUtil.getInstance(app, "");

        LogUtil.debug(isDebug);

        JPushInterface.setDebugMode(isDebug);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
    }

    public static App getInstance() {
        return app;
    }

    public void finishActivity(Class<? extends Activity> list) {
        if (activityList != null) {
            for (Activity activity : activityList) {
                if (activity.getClass().equals(list) && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }
}
