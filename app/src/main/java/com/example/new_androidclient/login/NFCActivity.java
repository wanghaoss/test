package com.example.new_androidclient.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.ToastUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Route(path = RouteString.NFCActivity)
public class NFCActivity extends BaseActivity {
    private NfcAdapter mNfcAdapter;

    @Autowired
    int code;

    //用于区分是否登录刷其余模块卡功能
    //1登录 2巡检区域 3巡检路线
    @Autowired
    int module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_f_c);
        start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String info = "";
        String[] temp = {};
        info = readNFCFromTag(intent);

        if (info.isEmpty()) {
            ToastUtil.show(mContext, "请重新刷卡");
            return;
        }

        if (info.contains("zh") || info.contains("en")) {
            temp = info.split("_");
        }

        if (temp[0].contains("USER")) { //用户卡格式 USER_李鹏军_id_uid
            user(temp);
        } else if (temp[0].contains("INSPECTION")) {
            if(temp[1].contains("AREA")){
                //巡检区域格式 INSPECTION_AREA_DT002
                inspection_area(temp);
            }else if(temp[1].contains("ROUTE")){
                //巡检路线格式 INSPECTION_ROUTE_SL00012
                inspection_route(temp);
            }

        } else {
            ToastUtil.show(mContext, "请将正确的卡片靠近手机");
        }
    }

    private void inspection_route(String[] temp) {
        //巡检区域格式 INSPECTION_ROUTE_SL00012
        if (module != 3) { //3是巡检路线
            ToastUtil.show(mContext, "请将正确的路线卡靠近手机");
            return;
        }
        Intent in = new Intent();
        in.putExtra("route", temp[2]); //区域位号
        setResult(code, in);
        finish();
    }

    private void inspection_area(String[] temp) {
        //巡检区域格式 INSPECTION_AREA_DT002
        if (module != 2) { //2是巡检区域
            ToastUtil.show(mContext, "请将正确的区域卡靠近手机");
            return;
        }
        Intent in = new Intent();
        in.putExtra("area", temp[2]); //区域位号
        setResult(code, in);
        finish();
    }

    private void user(String[] temp) {
        //用户卡格式 USER_李鹏军_id_uid
        if (module != 1) { //1是员工卡
            ToastUtil.show(mContext, "请将正确的员工卡靠近手机");
            return;
        }
        Intent in = new Intent();
        in.putExtra("uid", temp[3]);
        in.putExtra("id",temp[2]);
        in.putExtra("name",temp[1]);
        setResult(code, in);
        finish();
    }

    private void start() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        if (mNfcAdapter == null) {
            Toast.makeText(this, "该设备不支持nfc", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (!mNfcAdapter.isEnabled()) {
            startActivity(new Intent("android.settings.NFC_SETTINGS"));
            Toast.makeText(this, "设备未开启nfc", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //一旦截获NFC消息，就会通过PendingIntent调用窗口
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter[] intentFilters = new IntentFilter[]{};
        //用于打开前台调度（拥有最高的权限），当这个Activity位于前台（前台进程），即可调用这个方法开启前台调度
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    public static String readNFCFromTag(Intent intent) {
        Parcelable[] rawArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawArray != null) {
            NdefMessage mNdefMsg = (NdefMessage) rawArray[0];
            NdefRecord mNdefRecord = mNdefMsg.getRecords()[0];
            if (mNdefRecord != null) {
                String readResult = new String(mNdefRecord.getPayload());
                return readResult;
            }
        }
        return "";
    }

    public static void writeNFCToTag(String data, Intent intent) throws IOException, FormatException {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        NdefRecord ndefRecord = NdefRecord.createTextRecord(null, data);
        NdefRecord[] records = {ndefRecord};
        NdefMessage ndefMessage = new NdefMessage(records);
        ndef.writeNdefMessage(ndefMessage);
    }

}

