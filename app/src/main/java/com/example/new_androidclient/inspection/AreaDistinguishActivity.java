package com.example.new_androidclient.inspection;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.clj.fastble.data.BleDevice;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.EventBusMessage.InspectionDetailActivityEventBusMessage;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.Other.SPString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.InspectionDKBleUtils;
import com.example.new_androidclient.Util.PermissionUtil;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityInspectionAreaDistinguishBinding;
import com.example.new_androidclient.databinding.ItemBleListBinding;
import com.example.new_androidclient.inspection.bean.InspectionAreaBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;
import com.ronds.eam.lib_sensor.BleClient;
import com.ronds.eam.lib_sensor.BleInterfaces;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.example.new_androidclient.Other.SPString.InspectionAreaList;
import static com.example.new_androidclient.Other.SPString.InspectionRoughList;

/**
 * ??????????????????????????????????????????????????????
 */
@Route(path = RouteString.AreaDistinguishActivity)
public class AreaDistinguishActivity extends BaseActivity {
    private List<InspectionAreaBean> list = new ArrayList<>();
    private ActivityInspectionAreaDistinguishBinding binding;
    private Listener listener = new Listener();

    private BleListAdapter adapter;
    private List<BleDevice> bleList = new ArrayList<>();
    private boolean isScan = false;
    public static boolean bConnect = false;

    private boolean isMeasureTemperature = false;


    private ProgressDialog progressDialog;


    private String taskCode;

    private int GPS_REQUEST_CODE = 1;
    private int NFC = 2;
    private int QR = 3;

    private int Acc = 1;//

    // ???????????????
    private static final float EMI = 0.97f;
    // ????????????, 4 ????????????, ?????? K, ???????????? 256K
    private static final int LEN = 2;
    // ????????????, 4 ????????????, ?????? Hz, ???????????? 40000Hz
    private static final int FREQ = 2000;
    private double[] measData;
    private double[] acc;
    private double[] speed;
    private double[] disp;
    private double[] accSpectrum;
    private double[] speedSpectrum;
    private double[] dispSpectrum;


    @Autowired()
    boolean isRough = false; //??????????????????????????????????????????????????????????????????

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_area_distinguish);
        binding.setListener(listener);
        binding.inspectionAreaDistinguishTitleLayout.setBlueToothText("???????????????");
        super.onCreate(savedInstanceState);
        if (!PermissionUtil.checkCameraPermission(this)) {
            finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkBlueToothPermission();
    }

    private void checkBlueToothPermission() {
        if (PermissionUtil.checkBlueToothPermission(this)) {
            openGPS();
        } else {
            ToastUtil.show(mContext, "?????????????????????????????????????????????????????????");
        }
    }

    private boolean checkGpsIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    private void openGPS() {
        if (checkGpsIsOpen()) {
            init();
            findBlueToothDeviceList();
        } else {
            new AlertDialog.Builder(this).setTitle("????????????????????????????????????")
                    //  ????????????
                    .setNegativeButton("?????????????????????", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        binding.inspectionAreaDistinguishRecycler.setVisibility(View.GONE);
                    })
                    //  ????????????
                    .setPositiveButton("????????????", (dialogInterface, i) -> {
                        //?????????????????????????????????
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, GPS_REQUEST_CODE);
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    private void findBlueToothDeviceList() {
//        if (isScan) {
//            BleClient.getInstance().stopScan();
//            isScan = false;
//            showLoading(true);
//        } else {
//            scan();
//        }
        showLoading(true);
        scan();
    }

    private void scan() {
        if (InspectionDKBleUtils.checkBle(this)) {
      /*
      ???????????????????????????-BleClient??????aar???????????????????????????????????????????????????
       */
            BleClient.getInstance().scan(new BleInterfaces.ScanCallback() {
                /*
                ????????????
                 */
                @Override
                public void onScanStart() {
                    isScan = true;
                }

                /*
                ????????????
                 */
                @Override
                public void onScanEnd() {
                    isScan = false;
                }


                /*
                ????????????????????????????????????
                 */
                @Override
                public void onScanResult(List<BleDevice> list) {
                    if (list.size() > 0) {
                        adapter.submitList(list);
                        adapter.notifyDataSetChanged();
                        showLoading(false);
                    }
                }
            });
        }
    }

    private void init() {
        BleClient.getInstance().initOptions().init(getApplication());
        adapter = new BleListAdapter();
        binding.inspectionAreaDistinguishRecycler.setAdapter(adapter);

        // ??????????????????
        BleClient.getInstance().isBluetoothEnabled();

        // ??????????????????
        BleClient.getInstance().isLocationEnable(getApplicationContext());

        // ??????????????????
        BleClient.getInstance().isSupportBle();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            openGPS();
        }
        if (requestCode == NFC) {
            if (data != null) {
                taskCode = data.getStringExtra("route");
                if (taskCode.length() > 0) {
                    getInspectionList();
                    // ToastUtil.show(mContext, taskCode + "");
                }
            }
        }

        if (requestCode == QR) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    taskCode = bundle.getString(CodeUtils.RESULT_STRING);
                    if (taskCode.length() > 0) {
                        getInspectionList();
                        // ToastUtil.show(mContext, taskCode + "");
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.show(this, "?????????????????????, ???????????????");
                }
            }
        }
    }

    //  ???????????????????????????????????????????????????????????????????????????????????????
    private void getInspectionList() {
        SPUtil.putData(SPString.InspectionTaskCode, taskCode);
        RetrofitUtil.getApi().getTaskAreaContent(taskCode)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<InspectionAreaBean>>(mContext, true, "???????????????") {
                    @Override
                    public void onSuccess(List<InspectionAreaBean> bean) {
                        if (bean.size() != 0) {
                            list.clear();
                            list.addAll(bean);
                        }
                        if (isRough) { //???????????????
                            SPUtil.putListData(InspectionRoughList, list);
                            ARouter.getInstance().build(RouteString.InspectionRoughActivity).navigation();
                            finish();
                        } else {   //???????????????
                            SPUtil.putListData(InspectionAreaList, list);
                            //???????????????????????????-????????????
                            //???????????????????????????-????????????
                            //???????????????????????????-??????????????????+????????????+????????????
                            if (bean.get(0).getLineSort().equals("0")) {  //0????????????  1????????????  2????????????
                                ARouter.getInstance().build(RouteString.InspectionDeviceListActivity)
                                        .withInt("lineSort", 0)
                                        .navigation();//0????????????  1???????????? 2????????????

                            } else if (bean.get(0).getLineSort().equals("1")) {
                                ARouter.getInstance().build(RouteString.InspectionDeviceListActivity)
                                        .withInt("lineSort", 1) //0????????????  1????????????  2????????????
                                        .navigation();
                            } else {
                                ARouter.getInstance().build(RouteString.InspectionDeviceListActivity)
                                        .withInt("lineSort", 2) //0????????????  1????????????  2????????????
                                        .navigation();
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void showLoading(boolean show) {
        if (show) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(AreaDistinguishActivity.this);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setTitle("??????????????????????????????????????????");
                progressDialog.show();
            }
        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
    }

    private void connectBle(BleDevice ble) {
        BleClient.getInstance().connect(ble, new BleInterfaces.ConnectStatusCallback() {
            @Override
            public void onConnectStart() {
                ToastUtil.show(mContext, "????????????");
            }

            @Override
            public void onDisconnected(BleDevice bleDevice) {
                ToastUtil.show(mContext, "????????????");
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, String s) {
                ToastUtil.show(mContext, "????????????");
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice) {
                ToastUtil.show(mContext, "????????????");
                bConnect = true;
                adapter.notifyDataSetChanged();
            }
        });


    }

    class BleListAdapter extends ListAdapter<BleDevice, VH> {
        Context context;

        public BleListAdapter() {
            super(new Diff());
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            final ItemBleListBinding b = ItemBleListBinding.inflate(LayoutInflater.from(context), parent, false);
            return new VH(b);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            final BleDevice item = getItem(position);
            holder.itemBind.tvMac.setText("MAC?????????" + item.getMac());
            holder.itemBind.tvName.setText("???????????????" + item.getName());
            final boolean con = BleClient.getInstance().isConnected(item.getMac());
            holder.itemBind.tvStatus.setText("???????????????" + (con ? "?????????" : "?????????"));
            holder.itemBind.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean connect = BleClient.getInstance().isConnected(item.getMac());
                    if (connect) {
                        ToastUtil.show(AreaDistinguishActivity.this, "???????????????");
                    } else {
                        connectBle(item);

                    }
                }
            });
        }
    }

    static class VH extends RecyclerView.ViewHolder {
        ItemBleListBinding itemBind;

        VH(ItemBleListBinding binding) {
            super(binding.getRoot());
            this.itemBind = binding;
        }
    }

    static class Diff extends DiffUtil.ItemCallback<BleDevice> {
        @Override
        public boolean areItemsTheSame(@NonNull BleDevice oldItem, @NonNull BleDevice newItem) {
            return oldItem.getMac().equals(newItem.getMac());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull BleDevice oldItem, @NonNull BleDevice newItem) {
            return oldItem == newItem;
        }
    }


    public class Listener {
        public void planInspection() {
            new WorkConditionDialog(mContext, "6", (value, type1, position, dialog) -> {
                if (position == 0) { // (0nfc???1?????????)
                    ARouter.getInstance().build(RouteString.NFCActivity)
                            .withInt("code", NFC)
                            .withInt("module", 3) //3????????????
                            .navigation(AreaDistinguishActivity.this, NFC);
                } else {
                    Intent intent = new Intent(AreaDistinguishActivity.this, CustomCaptureActivity.class);
                    startActivityForResult(intent, QR);
                }
                dialog.setCanceledOnTouchOutside(true);
                dialog.dismiss();
            }).show();
        }

        public void randomInspection() {
            ARouter.getInstance().build(RouteString.InspectionDeviceListActivity)
                    .withBoolean("isRandomInspection", true)
                    .navigation();
        }
    }

    private void disConnect() {
        if (BleClient.getInstance().isConnect()) {
            BleClient.getInstance().disconnectAllDevices(new BleInterfaces.DisconnectCallback() {
                @Override
                public void onDisconnectStart() {
                    //??????????????????
                    bConnect = false;
                }

                @Override
                public void onDisconnectEnd() {
                    ToastUtil.show(mContext, "??????????????????");
                    //??????????????????
                }
            });
        }
    }

    @Override
    protected void onPause() {
        if (isScan) {
            BleClient.getInstance().stopScan();
        }
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        disConnect();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}

