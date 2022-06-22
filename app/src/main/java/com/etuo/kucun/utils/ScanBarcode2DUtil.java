package com.etuo.kucun.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zebra.adc.decoder.Barcode2DWithSoft;

import java.io.UnsupportedEncodingException;

/**
 * Created by yhn on 2020/2/20.
 */

public class ScanBarcode2DUtil {



    String TAG="ScanBarcode2DUtil";
    private Context mContext;
    private Barcode2DWithSoft barcode2DWithSoft=null;
    private String seldata="ASCII";

    private scanBarcode2DCallBack mBarcode2DCallBack;
    public ScanBarcode2DUtil (Context context){

        mContext = context;
        initBarcode2D();
    }
    //接口回调
    public interface scanBarcode2DCallBack {

        void scanBarcode2DCallBack(String code);

    }
    /**
     * 注册扫描二维码的接口
     * @param
     */

    public void scanCode(scanBarcode2DCallBack barcode2DCallBack) {
        this.mBarcode2DCallBack = barcode2DCallBack;
    }
    private void initBarcode2D(){
        barcode2DWithSoft=Barcode2DWithSoft.getInstance();
        new InitTask().execute();
    }


    public void ScanBarcode(){
        if(barcode2DWithSoft!=null) {
            LogUtil.d(TAG,"ScanBarcode");

            barcode2DWithSoft.scan();
            barcode2DWithSoft.setScanCallback(ScanBack);
        }
    }

    /**
     * 关闭扫码
     */
    public void stopScan(){
        if(barcode2DWithSoft!=null){
            barcode2DWithSoft.stopScan();
        }

    }

    /**
     * 关闭时 销毁
     */
    public void close(){
        if(barcode2DWithSoft!=null){
            barcode2DWithSoft.stopScan();
            barcode2DWithSoft.close();
        }
    }

    public Barcode2DWithSoft.ScanCallback  ScanBack= new Barcode2DWithSoft.ScanCallback(){
        @Override
        public void onScanComplete(int i, int length, byte[] bytes) {
            if (length < 1) {
                if (length == -1) {

                    LogUtil.d(TAG,"Scan cancel");
                } else if (length == 0) {
                    LogUtil.d(TAG,"Scan TimeOut");
                } else {

                    LogUtil.d(TAG,"Scan fail");
                }
            }else{
                SoundManage.PlaySound(mContext, SoundManage.SoundType.SUCCESS);
             String   barCode="";


                //  String res = new String(dd,"gb2312");
                try {
                    Log.i("Ascii",seldata);
                    barCode = new String(bytes, 0, length, seldata);
                    mBarcode2DCallBack.scanBarcode2DCallBack(barCode);
                }
                catch (UnsupportedEncodingException ex)   {

                    LogUtil.e(TAG,ex.toString());
                }
            }

        }
    };

    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            boolean reuslt=false;
            if(barcode2DWithSoft!=null) {
                reuslt=  barcode2DWithSoft.open(mContext);

                LogUtil.d("open="+reuslt);

            }
            return reuslt;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result){
//                barcode2DWithSoft.setParameter(324, 1);
//                barcode2DWithSoft.setParameter(300, 0); // Snapshot Aiming
//                barcode2DWithSoft.setParameter(361, 0); // Image Capture Illumination

                // interleaved 2 of 5
                barcode2DWithSoft.setParameter(6, 1);
                barcode2DWithSoft.setParameter(22, 0);
                barcode2DWithSoft.setParameter(23, 55);


            }else{
                ShowToast.showShortToast(mContext,"摄像头初始化失败");

            }
//            mypDialog.cancel();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

//            mypDialog = new ProgressDialog(mContext);
//            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            mypDialog.setMessage("init...");
//            mypDialog.setCanceledOnTouchOutside(false);
//            mypDialog.show();
        }

    }

}
