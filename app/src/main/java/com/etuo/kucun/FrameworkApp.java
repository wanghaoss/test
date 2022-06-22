package com.etuo.kucun;

import android.app.Application;
import android.content.Context;
import android.util.Xml;

import com.etuo.kucun.bean.BrocastButton;
import com.etuo.kucun.bean.ScanSetting;
import com.etuo.kucun.utils.DeviceUtil;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.LogcatHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.zxy.recovery.callback.RecoveryCallback;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/2/20.
 */

public class FrameworkApp extends Application implements RecoveryCallback {

    private static Context mAppContext;
    public static int forgetGesturePwd = 0;
    public static int globalIndex = 0;
    public static final String CACHE_ROOT_DIR = TuoPanConfig.EXT_STORAGE_ROOT + File.separator + TuoPanConfig.CACHE_ROOT_NAME;
    public static final String CACHE_PIC_ROOT_DIR = CACHE_ROOT_DIR + File.separator + TuoPanConfig.CACHE_PIC_ROOT_NAME;
    public static final String CACHE_ROOT_CACHE_DIR = CACHE_ROOT_DIR + File.separator + TuoPanConfig.CACHE_ROOT_CACHE_NAME;

    public static int KEY_SCAN = TuoPanConfig.KEY_SCAN_H600_RIGHT;

    public static int KEY_SEND = TuoPanConfig.KEY_SEND_H600;

    @Override
    public void onCreate() {
        super.onCreate();

        //让软件状态还原的框架
//        Recovery.getInstance()
//                .debug(BuildConfig.DEBUG)
//                .recoverInBackground(false)
//                .recoverStack(true)
//                .mainPage(HomeActivity.class)
//                .callback(this)
//                .init(this);
        //必须调用初始化
        OkGo.init(this);
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()

                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, BuildConfig.DEBUG)

                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3)

                    //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
                    .setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效

                    //可以设置https的证书,以下几种方案根据需要自己设置
                    .setCertificates();                              //方法一：信任所有证书,不安全有风险
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAppContext = getApplicationContext();
        buildCacheDir();

        //极光推送初始化
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
    }

    public static Context getAppContext() {
        return mAppContext;
    }


    public static void buildCacheDir() {
        File rootDir = new File(CACHE_ROOT_DIR);
        if (!rootDir.exists()) {
            rootDir.mkdir();
        }

        File cacheDir = new File(CACHE_ROOT_CACHE_DIR);
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }

        File picRootDir = new File(CACHE_PIC_ROOT_DIR);
        if (!picRootDir.exists()) {
            picRootDir.mkdir();
        }
    }

    @Override
    public void stackTrace(String stackTrace) {

    }

    @Override
    public void cause(String cause) {

    }

    @Override
    public void exception(String throwExceptionType, String throwClassName, String throwMethodName, int throwLineNumber) {

    }

    @Override
    public void throwable(Throwable throwable) {

    }

    private String configPath = "vendor/etc/scanner_config.xml";
    /**
     * 解析存在设备的xml获取配置信息
     * @return
     */
    public ScanSetting xmlJiexi(){
        ScanSetting scanSetting = new ScanSetting();
        BrocastButton brocastButton = null;
        List<BrocastButton> list = new ArrayList<>();
//        XmlResourceParser xml = getResources().getXml(R.xml.scanner_config);
        try {
            File file = new File(configPath);
            FileInputStream inputStream = new FileInputStream(file);
            XmlPullParser xml = Xml.newPullParser();
            xml.setInput(inputStream, "UTF-8");
            int eventType = xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String name = xml.getName();
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if(name.equalsIgnoreCase("property")){
                            String nameSetting = xml.getAttributeValue(0);
                            String value = xml.getAttributeValue(1);
                            LogUtil.i("xml解析数据=="+nameSetting+";attributeNamespace22=="+value);
                            switch (nameSetting){
                                case "model":
                                    scanSetting.setModel(value);
                                    break;
                                case "openService_reboot":
                                    scanSetting.setOpenService_reboot(Boolean.parseBoolean(value));
                                    break;
                                case "SerialPortName":
                                    scanSetting.setSerialPortName(value);
                                    break;
                                case "FileNodePowerOn":
                                    scanSetting.setFileNodePowerOn(value);
                                    break;
                                case "scanVoice":
                                    scanSetting.setScanVoice(Boolean.parseBoolean(value));
                                    break;
                                case "scanVibrate":
                                    scanSetting.setScanVibrate(Boolean.parseBoolean(value));
                                    break;
                                case "HIDChoose":
                                    scanSetting.setHIDChoose(Boolean.parseBoolean(value));
                                    break;
                                case "EnterChoose":
                                    scanSetting.setEnterChoose(Boolean.parseBoolean(value));
                                    break;
                                case "TabChoose":
                                    scanSetting.setTabChoose(value);
                                    break;
                                case "ScanModel":
                                    scanSetting.setScanModel(value);
                                    break;
                                case "FileNodeCloseOpen":
                                    scanSetting.setFileNodeCloseOpen(value);
                                    break;
                                case "ScreenDirection":
                                    scanSetting.setScreenDirection(value);
                                    break;
                                case "ClipBoardChoose":
                                    scanSetting.setClipBoardChoose(Boolean.parseBoolean(value));
                                    break;
                                case "ReaderID":
                                    scanSetting.setReaderID(Integer.parseInt(value));
                                    break;
                                default:
                                    break;
                            }

                        }
                        if(name.equalsIgnoreCase("button")){
                            brocastButton = new BrocastButton();
                            String id = xml.getAttributeValue(0);
                            brocastButton.setId(id);

                        }else if(brocastButton != null){
                            if(name.equalsIgnoreCase("closeBroadcast")){
                                String s = xml.nextText();
                                brocastButton.setCloseBroadcast(s);
                            }

                            if("value".equalsIgnoreCase(name)){
                                String value = xml.nextText();
                                brocastButton.setValue(value);
                            }
                            if("openBroadcast".equalsIgnoreCase(name)){
                                brocastButton.setOpenBroadcast(xml.nextText());
                            }
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        LogUtil.i("结尾的name=="+name);
                        if (name.equals("button") && brocastButton !=null) {
                            list.add(brocastButton);
                            brocastButton = null;
                        }
                        if(name.equalsIgnoreCase("deviceCofing")){
                            scanSetting.setBrocastButtons(list);
                        }

                        break;
                }
                eventType = xml.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            LogUtil.e("解析xml=="+e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("解析xml=="+e.getMessage());
        }
        return scanSetting;
    }

    /**
     * 根据设备初始化实体键值
     */
    public void initKeyValueByMoble(){
        if (TuoPanConfig.MODELTYPE_H600.equals(DeviceUtil.mobileType())){
            KEY_SCAN = TuoPanConfig.KEY_SCAN_H600_RIGHT;
            KEY_SEND = TuoPanConfig.KEY_SEND_H600;
        }else {
            KEY_SCAN = TuoPanConfig.KEY_SCAN_OLD;
            KEY_SEND = TuoPanConfig.KEY_SEND_OLD;
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogcatHelper.getInstance(this).stop();

    }
}
