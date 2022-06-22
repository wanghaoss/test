package com.etuo.kucun.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.serialport.DeviceControlSpd;
import android.text.TextUtils;

import com.etuo.kucun.R;
import com.etuo.kucun.TuoPanConfig;
import com.rscja.deviceapi.RFIDWithUHF;
import com.rscja.utility.StringUtility;

import java.io.IOException;
import java.util.HashMap;

import cn.com.example.rfid.driver.Driver;
import cn.com.example.rfid.driver.RfidDriver;

import static android.content.Context.AUDIO_SERVICE;
import static android.serialport.DeviceControlSpd.PowerType.EXPAND;

//import android.serialport.DeviceControlSpd;


/**
 * Created by yhn on 2020/2/20.
 * rfid操作相关 封装
 */

public class RfidOperationUtils {

    private Context mContext;
    private RFIDWithUHF mReader;

    private boolean loopFlag = false;
    Handler handler;
    HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
    private SoundPool soundPool;
    private float volumnRatio;
    private AudioManager am;
    Handler mHandler;

    public static  final  int MAX_SET_POWER = 30;//最大功率

    public static  final  int NORMAL_POWER = 20;//正常使用功率

    private DeviceControlSpd newUHFDeviceControl;

   private String pwdStr = "00000000";//设置读取数据的密码
    private String TAG = "RfidOperationUtils";

public static String ONE_TYPE = "one";
    public static String MORE_TYPE = "more";
    public static String SCAN_TYPE = "scan";

    public readTagCallBack mReadTagCallBack;

    public readDataCallBack mReadDataCallBack;

    public  initCallBack mInitCallBack;

    public static RfidOperationUtils mRfidOperationUtils;

    /*****************H600相关************************/

    private Driver driver_H600 ;

    public RfidOperationUtils(Context context){
        mContext = context;



//        if (DeviceUtil.isHaveFRID()){
            initSound();


            initUHF();
//        }

//        if (TuoPanConfig.MODELTYPE_H600.equals(DeviceUtil.mobileType())){
//
//        }else {
//
//        }


    }

    public static RfidOperationUtils getRfidOperationUtils(Context context){

        if (null == mRfidOperationUtils){
            mRfidOperationUtils = new RfidOperationUtils(context);
        }

        return  mRfidOperationUtils;
    }
    //接口回调
    public interface initCallBack {
        void initCallBack();

    }
    //接口回调
    public interface readTagCallBack {
        void OneStepReadTagCallBack(String strEpc);

        void MoreStepReadTagCallBack(String strTid, String strEpc);


    }

    //接口回调
    public interface readDataCallBack {


        void OneStepReadDataCallBack(String data);

        void MoreStepReadDataCallBack(String[] datas);

    }

    /**
     * 初始化
     * @param callBack
     */
    public void initCallBack(initCallBack callBack) {
        this.mInitCallBack = callBack;
    }
    /**
     * 注册接收标签识别的接口
     * @param readTagCallBack
     */

    public void readTagCallBack(readTagCallBack readTagCallBack) {
        this.mReadTagCallBack = readTagCallBack;
    }

    /**
     * 注册接收数据的接口
     * @param readDataCallBack
     */
    public void readDataCallBack(readDataCallBack readDataCallBack) {
        this.mReadDataCallBack = readDataCallBack;
    }

    public boolean setPower (int power){

        if (TuoPanConfig.MODELTYPE_H600.equals(DeviceUtil.mobileType())){
            return driver_H600.setTxPowerOnce(power) == 1;
        }else {
            return mReader.setPower(power);
        }


    }

    public int getPower(){


        if (TuoPanConfig.MODELTYPE_H600.equals(DeviceUtil.mobileType())){

            LogUtil.d("功率 : " +  driver_H600.GetTxPower());
            return driver_H600.GetTxPower();
        }else {
            return mReader.getPower();
        }
    }

    /**
     * 初始化声音
     */
    private void initSound(){
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        soundMap.put(1, soundPool.load(mContext, R.raw.barcodebeep, 1));
        soundMap.put(2, soundPool.load(mContext, R.raw.serror, 1));
        am = (AudioManager) mContext.getSystemService(AUDIO_SERVICE);// 实例化AudioManager对象
    }

    /**
     * 初始化UHF模块
     */
    public void initUHF() {


        if (TuoPanConfig.MODELTYPE_H600.equals(DeviceUtil.mobileType())){
            driver_H600 = new RfidDriver();
            int[] gpios = {9, 14};
            try {
                newUHFDeviceControl = new DeviceControlSpd(EXPAND, gpios);
                newUHFDeviceControl.PowerOnDevice();
            } catch (IOException e) {

                LogUtil.e("初始化失败 : " + e.toString());
                e.printStackTrace();
            }
            if (null != mInitCallBack){
                mInitCallBack.initCallBack();

            }
            if (driver_H600 != null) {
                new InitTask().execute();
            }
        }else {
            try {
                mReader = RFIDWithUHF.getInstance();
            } catch (Exception ex) {

                ShowToast.showShortToast(mContext,ex.getMessage());

                return;
            }

            if (mReader != null) {
                new InitTask().execute();
            }
        }



        /**
         * 通过handle接收扫描到
         */

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String result = msg.obj + "";
                String epcHex = "";
                if (result.contains("@")){
                    String[] strs = result.split("@");

                    String getTidHexStr = strs[0];

                    epcHex = strs[1];
                }else {
                    epcHex = result;
                }


                String epc = StringUtil.hexStringToString(epcHex);

                LogUtil.d(" epc " + epcHex    + "   转化后数据 : " + epc);

                if (!StringUtil.isEmpty(epc)){
                    //回调给主界面
                    mReadTagCallBack.MoreStepReadTagCallBack("",epcHex);
                    playSound(1);
                }





            }
        };

         mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //这里写你的Toast代码

                String message = msg.obj +"";
                ShowToast.showShortToast(mContext,message);
            }
        };

    }

    /**
     *异步读标签 初始化
     *
     * @author yhn
     */
    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            if (TuoPanConfig.MODELTYPE_H600.equals(DeviceUtil.mobileType())){
                int Status = driver_H600.initRFID("/dev/ttyMT0");

                LogUtil.d("初始化 : "  + Status);
                if (1000 != Status){
                    return false;
                }else {

                    return true;

                }

            }else {
                return mReader.init();
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            mypDialog.cancel();

            LogUtil.d("初始化成功? " + result);
            if (!result) {

                ShowToast.showShortToast(mContext,"初始化失败");

            }else {//成功
                if (null != mInitCallBack){
                    mInitCallBack.initCallBack();

                }
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mypDialog = new ProgressDialog(mContext);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("设备加载中..");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }
    }


    /**
     * 停止识别
     */
    public void stopInventory() {
        LogUtil.d("开始关闭循环 ");

        if (TuoPanConfig.MODELTYPE_H600.equals(DeviceUtil.mobileType())){
            if (loopFlag) {
                LogUtil.d("开始关闭循环 ");

                loopFlag = false;
                driver_H600.stopRead();
                    LogUtil.d("停止成功 ");
            }
        }else {
            if (loopFlag) {
                LogUtil.d("开始关闭循环 ");

                loopFlag = false;
                if (mReader.stopInventory()) {
                    LogUtil.d("停止成功 ");


                } else {
                    ShowToast.showShortToast(mContext,
                            "Stop failure");
                }

            }
        }



    }


    /**
     * 关闭
     */
    public void closeInventory() {
        if (TuoPanConfig.MODELTYPE_H600.equals(DeviceUtil.mobileType())){

            if (driver_H600 != null){
                loopFlag = false;
                driver_H600.stopRead();
            }
//
            try {
                newUHFDeviceControl.PowerOffDevice();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            if (mReader != null) {//当界面关闭时,关闭UHF
                mReader.free();
            }
        }


    }

    /**
     * 根据标签过滤识别
     * @param decFilter
     * @return
     */
//    public boolean getReadTagByEpc(String decFilter){
//        //读 USER 分区
//        if (!StringUtil.isEmpty(decFilter)){
//            //十六进制数据
//            String hexFilter = StringUtil.string2HexString(decFilter);
//
//            LogUtil.d("过滤内容 16i进制 : " + hexFilter   + "十进制数 : " + decFilter);
//            //过滤分区位置
//            String filterBank = storageBank[0];
//
//            LogUtil.d("参数 :BankEnum :  " + RFIDWithUHF.BankEnum.valueOf(filterBank) + "   intPtr : " +StringUtil.getStartBitByBank(filterBank)
//            +"  getBitLengthFilter :  " +   StringUtil.getBitLengthFilter(hexFilter)  + "   hexFilter : " + hexFilter);
//           boolean setFilterIsOk  =  mReader.setFilter( RFIDWithUHF.BankEnum.valueOf(filterBank), StringUtil.getStartBitByBank(filterBank),
//                    StringUtil.getBitLengthFilter(hexFilter) ,hexFilter,false);
//
//            if (setFilterIsOk){
//                String strUII = mReader.inventorySingleTag();
//                LogUtil.d(" inventorySingleTag :" + strUII);
//                if (!TextUtils.isEmpty(strUII)) {
//                    //Uii 转EPC
//                    String strHexEPC = mReader.convertUiiToEPC(strUII);
//
//                    String getDecEpc = StringUtil.hexStringToString(strHexEPC);
//
//                    LogUtil.d(" 过滤识别 strHexEPC :" + strHexEPC  + "   转化后数据十进制数 : " + getDecEpc);
//
//
//                    if (decFilter.equals(getDecEpc)){
//                        playSound(1);
//                        return true;
//                    }
//
//                } else {
//                    ShowToast.toastLongTime(mContext,R.string.uhf_msg_inventory_fail);
//					   playSound(2);
//                }
//            }
//
//
//
//        }
//
//        return false;
//
//    }

    /**
     * 清除过滤信息
     * @return
     */
    private boolean isClearSetFilter(){
//        if (TuoPanConfig.MODELTYPE_H600.equals(DeviceUtil.mobileType())){
            return true;
//        }else {
//
//            return mReader.setFilter( RFIDWithUHF.BankEnum.valueOf("UII"), 0,0, "",false)&
//                    mReader.setFilter(RFIDWithUHF.BankEnum.valueOf("TID"), 0,0, "",false)&
//                    mReader.setFilter(RFIDWithUHF.BankEnum.valueOf("USER"), 0,0, "",false);
//        }

    }

    /**
     * 单步扫描和循环扫描 不加过滤
     * @param checkTypeScanTp
     */

    public void readTag(String checkTypeScanTp) {

        if (!isClearSetFilter()){ //清除失败

            LogUtil.d("清除过滤失败");
            return;
        }
            switch (checkTypeScanTp) {
                case "one":// 单步
                {

                    if (TuoPanConfig.MODELTYPE_H600.equals(DeviceUtil.mobileType())){
                        OneReadByOldDeviceH600(checkTypeScanTp);

                    }else {
                        OneReadByOldDevice();

                    }

                }
                break;
                case "more":// 单标签循环
                {
                    if (TuoPanConfig.MODELTYPE_H600.equals(DeviceUtil.mobileType())){
                        MoreReadByOldDeviceH600(checkTypeScanTp);
                    }else {
//                        MoreReadByOldDevice();

                    }
                }
                break;

                default:
                    break;
            }

    }

    /**
     * 单步
     */
    private void OneReadByOldDevice(){

//单步读取标签
        String strUII = mReader.inventorySingleTag();
        LogUtil.d(" inventorySingleTag :" + strUII);
        if (!TextUtils.isEmpty(strUII)) {
            //Uii 转EPC
            String strHexEPC = mReader.convertUiiToEPC(strUII);

            String getDecEpc = StringUtil.hexStringToString(strHexEPC);

            LogUtil.d(" strHexEPC :" + strHexEPC  + "   转化后数据十进制数 : " + getDecEpc);

            if (!StringUtil.isEmpty(getDecEpc)){
                //回调给主界面
                playSound(1);
                mReadTagCallBack.OneStepReadTagCallBack(strHexEPC);
            }


        } else {
            ShowToast.toastLongTime(mContext,R.string.uhf_msg_inventory_fail);
            playSound(2);
        }
    }

    /**
     * 循环读标签
     */
//    private void MoreReadByOldDevice(){
//        //打开同时支持读取EPC和TID模式 仅R2000模块有效
//        mReader.setEPCTIDMode(true);
//        /**
//         * 启动识别Tag循环，只是开启识别Tag循环，之后将识别到的标签号上传到缓冲区， readTagFromBuffer()
//         用于从缓冲区读取一个标签号数据，开启循环识别之后，模块只能响应stopInventory() 函数，
//         stopInventory() 停止识别循环。
//         */
//        if (mReader.startInventoryTag((byte) 0, (byte) 0)) {
//
//            loopFlag = true;
//
//            new TagThread().start();
//        } else {
//            mReader.stopInventory();
//            ShowToast.toastLongTime(mContext,
//                    R.string.uhf_msg_inventory_open_fail);
////					mContext.playSound(2);
//        }
//    }

    /*******************H600读标签*****************************/

    /**
     * 单步
     */
    private void OneReadByOldDeviceH600(String checkTypeScanTp){

//单步读取标签

        String strEpc =  driver_H600.SingleRead(100);
        LogUtil.d(" strEpc :" + strEpc  );
        if (!StringUtility.isEmpty(strEpc)) {
            String getDecEpc = StringUtil.hexStringToString(strEpc);
            LogUtil.d(" strHexEPC :" + strEpc  + "   转化后数据十进制数 : " + getDecEpc);

            if (!StringUtil.isEmpty(getDecEpc)){
                //回调给主界面
                playSound(1);
                mReadTagCallBack.OneStepReadTagCallBack(strEpc);
            }
        }else {
            ShowToast.toastLongTime(mContext,R.string.uhf_msg_inventory_fail);
            playSound(2);
        }
    }


    /**
     * 循环读标签 H600
     */
    private void MoreReadByOldDeviceH600(String checkTypeScanTp){

        driver_H600.readMore();

            loopFlag = true;

            new TagH600Thread().start();

    }
    class TagH600Thread extends Thread {
        public void run() {

            while (loopFlag) {


                String strEpc = driver_H600.GetBufData();

                LogUtil.d("getEpc : " + strEpc);
                if (!StringUtility.isEmpty(strEpc)) {

                    Message msg = handler.obtainMessage();
                    msg.obj = getNewEpcByH600(strEpc) ;

                    handler.sendMessage(msg);

                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    class TagThread extends Thread {
//        public void run() {
//            String strTid;
//            String strResult;
//            String[] res = null;
//            while (loopFlag) {
//
//                res = mReader.readTagFromBuffer();
//                if (res != null) {
//                    strTid = res[0];
//                    if (!strTid.equals("0000000000000000")&&!strTid.equals("000000000000000000000000")) {
//                        strResult =  strTid ;
//                    } else {
//                        strResult = "";
//                    }
//
//                    String Epc = mReader.convertUiiToEPC(res[1]);
//
//                    LogUtil.d("Tid : " + strResult + "   EPC : " + Epc);
//                    Message msg = handler.obtainMessage();
//                    msg.obj = strResult + "@"  + Epc ;
//
//
//                    handler.sendMessage(msg);
//
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }

    /**
     * 播放提示音
     *
     * @param id 成功1，失败2
     */
    public void playSound(int id) {

        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // 返回当前AudioManager对象的最大音量值
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);// 返回当前AudioManager对象的音量值
        volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        try {
            soundPool.play(soundMap.get(id), volumnRatio, // 左声道音量
                    volumnRatio, // 右声道音量
                    1, // 优先级，0为最低
                    0, // 循环次数，0无不循环，-1无永远循环
                    1 // 回放速度 ，该值在0.5-2.0之间，1为正常速度
            );
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /******************************读数据*************************************/

    public String storageBank[] ={"UII","TID","USER"};

    public   int UserBankWordLen = 32;
    /**
     * 读数据按 EPC分区值进行过滤匹配
     *
     */
//
//    public  String readFilterByEpc(String decFilter) {
//        boolean  result = false;
//        String data = "";
//        //读 USER 分区
//        String BankRead = storageBank[2];
//        if (!StringUtil.isEmpty(decFilter)){
//            //十六进制数据
//            String hexFilter = StringUtil.string2HexString(decFilter);
//            //过滤分区位置
//            String filterBank = storageBank[0];
//
//
//         data = mReader.readData(
//                    pwdStr,
//                    RFIDWithUHF.BankEnum.valueOf(filterBank),
//                    StringUtil.getStartBitByBank(filterBank),
//                    StringUtil.getBitLengthFilter(hexFilter),
//                    hexFilter,
//                    RFIDWithUHF.BankEnum.valueOf(BankRead),
//                    StringUtil.getStartWordByBank(BankRead),
//                 UserBankWordLen
//            );
//
//            if(!StringUtil.isEmpty(data)){
//                result=true;
//
//            }else {
//                result=false;
//            }
//        }else {
//            SimpleRFIDEntity entity = mReader.readData(BankRead,
//                        RFIDWithUHF.BankEnum.valueOf(BankRead),
//                        StringUtil.getStartWordByBank(BankRead),
//                    UserBankWordLen);
//
//            if (entity != null) {
//                result = true;
//
//                data =entity.getData();
//
//            } else {
//                result = false;
//
//            }
//        }
//
//
//            if(result){//读取成功
//                LogUtil.d("读取 十六进制数据 : " + data   + "     转换后的数据 : "+ StringUtil.hexStringToString(data));
//
//
////                mReadDataCallBack.OneStepReadDataCallBack(StringUtil.hexStringToString(data));
//                playSound(1);
//
//            }else {
//                playSound(2);
//                Message message = new Message();
//                message.obj = "编号为 : " +decFilter +"的托盘未找到或者读取信息失败";
//                mHandler.sendMessage(message);
////                ShowToast.showShortToast(mContext,"编号为 : " +decFilter +"的托盘未找到或者读取信息失败");
//            }
//
//            return StringUtil.hexStringToString(data);
//
//
//    }

    /******************************写数据*************************************/
    /**
     * 写数据按 EPC分区值进行过滤匹配
     *
     */
//    public boolean write(String decFilter,String strData){
//
//        if (StringUtil.isEmpty(strData)){
//            Looper.prepare();
//            ShowToast.showShortToast(mContext,"写入数据不能为空");
//            Looper.loop();
//            return false;
//        }
//
//
//
//        setPower(MAX_SET_POWER);//设置最大功率
//        boolean  result = false;
//        //写 USER 分区
//        String BankRead = storageBank[2];
//        String hexWriteDate = StringUtil.string2HexString(strData);
//        if (!StringUtil.isEmpty(decFilter)){//条件过滤
//            //十六进制数据
//            String hexFilter = StringUtil.string2HexString(decFilter);
//
//
//            LogUtil.d(TAG," 过滤条件 十六进制数 : " + hexFilter   + "   十进制数是 : " + decFilter);
//
//            LogUtil.d(TAG," 写入信息 十六进制数 : " + hexWriteDate   + "   十进制数是 : " + strData + "十进制长度 : "+StringUtil.getWordLength(strData));
//            //过滤分区位置
//            String filterBank = storageBank[0];
//            result=mReader.writeData(
//                    pwdStr,
//                    RFIDWithUHF.BankEnum.valueOf(filterBank),
//                    StringUtil.getStartBitByBank(filterBank),
//                    StringUtil.getBitLengthFilter(hexFilter),
//                    hexFilter,
//                    RFIDWithUHF.BankEnum.valueOf(BankRead),
//                    StringUtil.getStartWordByBank(BankRead),
////                    StringUtil.getWordLength(strData), //根据写入数据的长度进行判定
//                    UserBankWordLen,
//                    hexWriteDate
//            );
//        }else {//不添加过滤
//
//            boolean r = mReader.writeData_Ex(pwdStr,
//                        RFIDWithUHF.BankEnum.valueOf(BankRead),
//                        StringUtil.getStartWordByBank(BankRead),
//                    StringUtil.getWordLength(strData),
//                    hexWriteDate);// 返回的UII
//
//
//            if (r) {
//                result=true;
//            } else {
//                result =false;
//            }
//
//        }
//
//        if(result){
//            playSound(1);
//        }else{
//           playSound(2);
//
//            LogUtil.d(TAG,"编号为 : " +decFilter +"的托盘未找到或者写入信息失败");
//
//            Message message = new Message();
//            message.obj = "编号为 : " +decFilter +"的托盘未找到或者写入信息失败";
//            mHandler.sendMessage(message);
//
//
//        }
//        setPower(PreferenceCache.getPowerValue());//设置用户设置的功率
//        return result;
//    }

    /******************************擦除数据*************************************/

    /**
     * 数据擦除 (向相关区域写0)
     *
     */
//    public boolean eraseDataByWrite(String decFilter){
//
//
//
//        if (!StringUtil.isEmpty(decFilter)){
//            //暂时不擦除数据了
//
//            return true;
//        }
//        boolean  result = false;
//        //写 USER 分区
//        String BankRead = storageBank[2];
//        if (!StringUtil.isEmpty(decFilter)){//条件过滤
//            //十六进制数据
//            String hexFilter = StringUtil.string2HexString(decFilter);
//
//
//            LogUtil.d(TAG," 过滤条件 十六进制数 : " + hexFilter   + "   十进制数是 : " + decFilter);
//
//            //过滤分区位置
//            String filterBank = storageBank[0];
//            result=mReader.writeData(
//                    pwdStr,
//                    RFIDWithUHF.BankEnum.valueOf(filterBank),
//                    StringUtil.getStartBitByBank(filterBank),
//                    StringUtil.getBitLengthFilter(hexFilter),
//                    hexFilter,
//                    RFIDWithUHF.BankEnum.valueOf(BankRead),
//                    StringUtil.getStartWordByBank(BankRead),
//                    UserBankWordLen,
//                    "0000"
//            );
//        }else {//不添加过滤
//
//            boolean r = mReader.writeData_Ex(pwdStr,
//                    RFIDWithUHF.BankEnum.valueOf(BankRead),
//                    StringUtil.getStartWordByBank(BankRead),
//                    UserBankWordLen,
//                    "0000");// 返回的UII
//
//
//            if (r) {
//                result=true;
//            } else {
//                result =false;
//            }
//
//        }
//
//        if(result){
//            playSound(1);
//        }else{
//            playSound(2);
//
//            LogUtil.d(TAG,"编号为 : " +decFilter +"的托盘未找到或者擦除信息失败");
//
//            Message message = new Message();
//            message.obj = "编号为 : " +decFilter +"的托盘未找到或者擦除信息失败";
//            mHandler.sendMessage(message);
//
//
//        }
//        return result;
//
//    }

    /***
     * 写数据按 EPC分区值进行过滤匹配
     * @param decFilter 值为空时,不过滤
     */

//    public boolean eraseDatas(String decFilter){
//
//        boolean  result = false;
//        //写 USER 分区
//        String BankRead = storageBank[2];
//        if (!StringUtil.isEmpty(decFilter)){//条件过滤,指定Epc 数据
//            //十六进制数据
//            String hexFilter = StringUtil.string2HexString(decFilter);
//            //过滤分区位置
//            String filterBank = storageBank[0];
//            result=mReader.eraseData(
//                    pwdStr,
//                    RFIDWithUHF.BankEnum.valueOf(filterBank),
//                    StringUtil.getStartBitByBank(filterBank),
//                    StringUtil.getBitLengthFilter(hexFilter),
//                    hexFilter,
//                    RFIDWithUHF.BankEnum.valueOf(BankRead),
//                    StringUtil.getStartWordByBank(BankRead),
//                    UserBankWordLen
//            );
//
//            if (result){
//                LogUtil.d(TAG,"编号为 : " +decFilter +"的托盘擦除信息成功");
//            }else {
//                Message message = new Message();
//                message.obj = "编号为 : " +decFilter +"的托盘未找到或者擦除信息失败";
//                mHandler.sendMessage(message);
//                LogUtil.d(TAG,"编号为 : " +decFilter +"的托盘未找到或者擦除信息失败");
//
//
//            }
//        }else {//不添加过滤
//
//            String Uii = mReader.eraseData(
//                    pwdStr,
//                    RFIDWithUHF.BankEnum.valueOf(BankRead),
//                    StringUtil.getStartWordByBank(BankRead),
//                    UserBankWordLen);// 返回的UII
//
//            if (!StringUtil.isEmpty(Uii)) {
//                Message message = new Message();
//                message.obj = "编号为 : " +StringUtil.hexStringToString(mReader.convertUiiToEPC(Uii)) +"的托盘数据擦除成功";
//                mHandler.sendMessage(message);
//
//                LogUtil.d(TAG,"编号为 : " +StringUtil.hexStringToString(mReader.convertUiiToEPC(Uii)) + "的托盘数据擦除成功");
//                result=true;
//            } else {
//                result =false;
//            }
//
//        }
//
//        if(result){
//            playSound(1);
//        }else{
//            playSound(2);
//        }
//
//        return  result;
//
//    }
//


    /*************H600 截取可用的Epc段数据*******************/

    private String getNewEpcByH600(String getEpc){
        int Hb = 0;
        int Lb = 0;
        int rssi = 0;
        String[] tmp = new String[3];
        HashMap<String, String> temp = new HashMap<>();
        String text = getEpc.substring(4);
        String len = getEpc.substring(0, 2);
        int epclen = (Integer.parseInt(len, 16) / 8) * 4;
        tmp[0] = text.substring(epclen, text.length() - 6);
        tmp[1] = text.substring(0, epclen);

        if (!StringUtil.isEmpty(tmp[1])){

            LogUtil.d("newEpc : " + tmp[1]);
            return tmp[1];
        }
        return "";
    }
}
