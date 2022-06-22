package com.example.new_androidclient.Util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class NfcUtil {

    public static NfcAdapter mNfcAdapter;
    public static IntentFilter[] mIntentFilter = null;
    public static PendingIntent mPendingIntent = null;

    public NfcUtil(Activity activity) {
        checkNfc(activity);
        init(activity);
    }

    private static NfcAdapter checkNfc(Activity activity) {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (mNfcAdapter == null) {
            ToastUtil.show(activity, "该设备不支持nfc");
            return null;
        }
        if (!mNfcAdapter.isEnabled()) {
            activity.startActivity(new Intent("android.settings.NFC_SETTINGS"));
            ToastUtil.show(activity, "设备未开启nfc");
        }
        return mNfcAdapter;
    }

    public static void init(Activity activity) {
        //一旦截获NFC消息，就会通过PendingIntent调用窗口
        mPendingIntent = PendingIntent.getActivity(activity, 0, new Intent(activity,
                NfcUtil.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        mIntentFilter = new IntentFilter[]{};
        //用于打开前台调度（拥有最高的权限），当这个Activity位于前台（前台进程），即可调用这个方法开启前台调度
        // mNfcAdapter.enableForegroundDispatch(activity, mPendingIntent, mIntentFilter, null);
    }

    public static String readNFC(Intent intent) throws UnsupportedEncodingException {
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

    public static void writeNFC(String data, Intent intent) throws IOException, FormatException {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        NdefRecord ndefRecord = NdefRecord.createTextRecord(null, data);
        NdefRecord[] records = {ndefRecord};
        NdefMessage ndefMessage = new NdefMessage(records);
        ndef.writeNdefMessage(ndefMessage);
    }


}

