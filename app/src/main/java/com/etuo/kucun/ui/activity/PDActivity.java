package com.etuo.kucun.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etuo.kucun.FrameworkApp;
import com.etuo.kucun.R;
import com.etuo.kucun.base.BaseActivity;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.callback.DialogCallback;
import com.etuo.kucun.model.BomDetailsModel;
import com.etuo.kucun.model.OrderCommitModel;
import com.etuo.kucun.model.PDSLModel;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.storage.PreferenceCache;
import com.etuo.kucun.utils.LogUtil;
import com.etuo.kucun.utils.RfidOperationUtils;
import com.etuo.kucun.utils.ScanBarcode2DUtil;
import com.etuo.kucun.utils.ShowToast;
import com.etuo.kucun.utils.UrlTools;
import com.etuo.kucun.widget.PromptHintCancel;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.etuo.kucun.utils.UrlTools.PDBC;
import static com.etuo.kucun.utils.UrlTools.PDSL;


/**
 * ================================================
 *
 * @author :haining
 * @version :
 * @date :2020/02/08
 * @ProjectNameDescribe :  BOM 备货 等详情操作界面
 * 修订历史：
 * ================================================
 */

public class PDActivity extends BaseActivity {
    @BindView(R.id.btn_prev)
    ImageView btnPrev;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.llfirst)
    LinearLayout llFirst;
    @BindView(R.id.tv_orderNum)
    TextView tvOrderNum;
    @BindView(R.id.rl_general_header_bar)
    RelativeLayout rlGeneralHeaderBar;
    @BindView(R.id.lv_outList)
    ListView lvOutList;
    @BindView(R.id.ll_bt)
    LinearLayout llBt;
    @BindView(R.id.tv_orderNum_text)
    TextView tvOrderNumText;
    @BindView(R.id.tv_ok)
    Button tvOk;
    @BindView(R.id.rb_one)
    RadioButton mRbOne;
    @BindView(R.id.rb_more)
    RadioButton mRbMore;
    @BindView(R.id.rb_scan)
    RadioButton mRbScan;
    @BindView(R.id.my_radio_group)
    RadioGroup mMyRadioGroup;
    @BindView(R.id.ll_scan)
    LinearLayout mLlScan;
    @BindView(R.id.shijain)
    TextView shijain;
    @BindView(R.id.shul)
    TextView shul;


    private String checkTypeScanTp = ONE_TYPE;//
    public static String ONE_TYPE = "one";
    public static String MORE_TYPE = "more";
    public static String SCAN_TYPE = "scan";

    List<String> bqlist = new ArrayList<>();
    List<String> submitList = new ArrayList<>();
    List<String> listView = new ArrayList<>();

    private List<BomDetailsModel.GatherListBean.DetailListBean> detailListBeanList = new ArrayList<>();//批量操作是缓存的list ,只有在按下确定键开始添加,松开停止 然后提交数据
    private String orderNo;
    private int sl;
    private String listValue;
    private String[] aa;
    private String ddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_detatilsss);
        ButterKnife.bind(this);

        orderNo = getIntent().getStringExtra("orderNo");

        getPDSL();

        initView();

        //读写扫描相关
        initRfid();
        initScan();

        buttonSetUp();

    }

    private void buttonSetUp() {
        if (submitList.size() > 0){
            tvOk.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_blue));
            tvOk.setEnabled(true);
        } else {
            tvOk.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_gray));
            tvOk.setEnabled(false);
        }

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialog();
            }
        });
    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(PDActivity.this);
        normalDialog.setCancelable(false);
        normalDialog.setTitle("盘点");
        normalDialog.setMessage("托盘型号:网格田字托盘 1050*1050*150,系统在库数量:" +sl + ",实际在库数量:" + submitList.size() + ",差异:"+ String.valueOf(sl-submitList.size()));
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ftSubmit();
                        dialog.dismiss();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        // 显示
        normalDialog.show();
    }

    private void ftSubmit() {

        String cy = String.valueOf(sl - submitList.size());

        LogUtil.d("token :" + PreferenceCache.getToken());
        HashMap<String, String> params = new HashMap<>();
        params.put("companyId", PreferenceCache.getCompanyId());
        params.put("modelId","orderNo");
        params.put("quantity", String.valueOf(sl));
        params.put("yardQuantity",String.valueOf(submitList.size()));
        params.put("libraryTime",ddate);
        params.put("yardDifQuantity",cy);
        params.put("description","无");

        JSONObject  jsonObject = new JSONObject (params);

        OkGo.post(UrlTools.getInterfaceUrl(PDBC))
                .tag(this)
                .upJson(jsonObject)
//                .params("openId", "olVoO5DmxFRKaZWeNKGyDrRRYPeU")
                .execute(new DialogCallback<LzyResponse<String>>(getActivity(), "登录中...") {
                    @Override
                    public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {

                        ShowToast.tCustom(mContext, "操作成功");
                        finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.tCustom(getActivity(), e.getMessage());
                    }
                });
    }

    private Activity getActivity() {
        return PDActivity.this;
    }

    private void getPDSL() {
        LogUtil.d("token :" + PreferenceCache.getToken());
        OkGo.get(UrlTools.getInterfaceUrl(PDSL))
                .tag(this)
                .params("companyId", PreferenceCache.getCompanyId())
                .params("modelId", "1648718128914686")
                .execute(new DialogCallback<LzyResponse<PDSLModel>>((Activity) mContext, "加载中") {
                    @Override
                    public void onSuccess(LzyResponse<PDSLModel> responseData, Call call, Response response) {
                        if (responseData.bean != null){
//                            tvOrderNum.setText(responseData.data.getOrderNo());
                            sl = responseData.bean.getTotalQuantity();
                            shul.setText(submitList.size() + "/" + sl);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ShowToast.tCustom(getApplicationContext(), e.getMessage());
                    }
                });
    }

    /**
     * 初始化布局
     */
    private void initView() {

        //展示当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        ddate = simpleDateFormat.format(date);

        shijain.setText(ddate);

        mMyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {
                    case R.id.rb_one:
                        checkTypeScanTp = ONE_TYPE;
                        break;
                    case R.id.rb_more:
                        checkTypeScanTp = MORE_TYPE;
                        break;
                    case R.id.rb_scan:
                        checkTypeScanTp = SCAN_TYPE;
                        break;
                }
            }
        });
    }


    public String NullToStr(Object o) {
        if (null == o || null == o.toString() || JSONObject.NULL == o) {
            return "";
        }
        return o.toString();
    }

    /**
     * 确定按钮
     */
    public void upLoadOrderComfrim() {
//        LogUtil.d("token : " + PreferenceCache.getToken());
//        OkGo.post(UrlTools.getInterfaceUrl(GOODS_STORAGEOUT_UPDATEBILL))
//                .tag(this)
//                .params("token", PreferenceCache.getToken())
//                .params("storageOutId", NullToStr(orderId))
//                .params("status", "2") //0待备货 1备货中 2已备货
//                .execute(new DialogCallback<LzyResponse<String>>(this, "加载中..") {
//                    @Override
//                    public void onSuccess(LzyResponse<String> responseData, Call call, Response response) {
//                        finish();
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        ShowToast.tCustom(NewTpBomDetailsActivity.this, NullToStr(e.getMessage()));
//                    }
//                });
    }


    /**
     *
     * @return 获取提交数据json 串
     *
     */

    private String getCommitJosn(List<String> singleData){

        String json=new Gson().toJson(singleData);

        json = json.replace("[","");
        json = json.replace("]","");
        json = json.replace("\"","");
        json = json.replace("/",",");

        LogUtil.d("tojson : " + json);
        return json;
    }

    /**
     *
     * @return 获取提交数据json 串
     *
     */

    private String getCommitJosn(List<BomDetailsModel.GatherListBean.DetailListBean> singleData,String status){

        List<OrderCommitModel>   mOrderCommitModels = new ArrayList<>();

        for (int i = 0;i<singleData.size();i++){

            OrderCommitModel commitModel = new OrderCommitModel();
            commitModel.setStorageOutDetailId(singleData.get(i).getStorageOutDetailId());
            commitModel.setOpEquipment("1");//操作设备 1手持设备 2门设备 3人工
            commitModel.setStatus(status);//货物备货单状态：0待验收 2验收通过 3报修
            mOrderCommitModels.add(commitModel);
        }

        String json=new Gson().toJson(mOrderCommitModels);


        LogUtil.d("tojson : " + json);
        return json;
    }



    /**
     * 实体键按下
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            PromptHintCancel p = new PromptHintCancel(this) {
                @Override
                protected void onOk() {

                    finish();
                }
            };
            p.show(ExtraConfig.TypeCode.DIALOG_CONFIRM_TO_EXIT);
            return true;
        } else if ((keyCode == FrameworkApp.KEY_SEND && event.getRepeatCount() == 0) || (keyCode == 280 && event.getRepeatCount() == 0)) {//点击了 扳机

            //点击后创建list

            if (SCAN_TYPE.equals(checkTypeScanTp)) {//扫描
                LogUtil.d("点击了 扳机 ->扫描模式");
                mBarcode2DUtil.ScanBarcode();
            } else {
                LogUtil.d("点击了 扳机 ->读写模式");
                detailListBeanList.clear();
                mRfidOperationUtils.readTag(checkTypeScanTp);
            }

            return true;
        } else if (keyCode == 139 && event.getRepeatCount() == 0) {//快捷键scan
            LogUtil.d("点击了 快捷扫描键");
            mBarcode2DUtil.ScanBarcode();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 实体键松开
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if ((keyCode == FrameworkApp.KEY_SEND && event.getRepeatCount() == 0) || (keyCode == 280 && event.getRepeatCount() == 0)) {//点击了 扳机
            if (MORE_TYPE.equals(checkTypeScanTp)) {

                //关闭循环扫描标签
                mRfidOperationUtils.stopInventory();
                LogUtil.d(" 扳机 松开 开始写数据  " + System.currentTimeMillis());
                if (null != bqlist && bqlist.size() > 0) {
//                    ShowToast.showShortToast(this, "dddddd");
                } else {
                    ShowToast.showShortToast(this, "未找到可操作的托盘,请重新扫描");
                }


                return true;
            }

        }


        return super.onKeyUp(keyCode, event);

    }

    /**
     * 单步rfid 和扫描结果查询提交
     *
     * @param code
     * @param status
     */

    private void commitTpByScanId(String code, String status) {



        if (bqlist.size() == 0){
            bqlist.add(code);
        }else {
            for (int i = 0; i < bqlist.size(); i++) {
                if (bqlist.get(i).equals(code)){
                    break;
                }else {
                    bqlist.add(code);
                }
            }
        }
    }


    /****************************UHF读写相关***************************************/


//    private String wrData = "A000012020022112345678202002211234001001";

    private RfidOperationUtils mRfidOperationUtils;


    private void initRfid() {
        mRfidOperationUtils = new RfidOperationUtils(mContext);
        /**
         * 读标签返回值
         * 单步的时候直接提交
         */
        mRfidOperationUtils.readTagCallBack(new RfidOperationUtils.readTagCallBack() {
            @Override
            public void OneStepReadTagCallBack(String strEpc) {
                if (strEpc.length() > 9){
                    strEpc = strEpc.substring(2,strEpc.length());
                    //执行写的操作
                    if (bqlist.size() == 0){
                        bqlist.add(strEpc);
                    }else {
                        if (!bqlist.contains(strEpc)){
                            bqlist.add(strEpc);
                        }
                    }

                    setListV(strEpc);
                }

            }

            /**
             * 批量的时候等到松开手之后提交
             * @param strTid
             * @param strEpc
             */
            @Override
            public void MoreStepReadTagCallBack(String strTid, String strEpc) {
                if (strEpc.length() > 9){
                    strEpc = strEpc.substring(2,strEpc.length());
                    //执行写的操作
                    if (bqlist.size() == 0){
                        bqlist.add(strEpc);
                    }else {
                        if (!bqlist.contains(strEpc)){
                            bqlist.add(strEpc);
                        }
                    }

                    setListV(strEpc);
                }

            }
        });
        /**
         * 读数据返回值
         */
        mRfidOperationUtils.readDataCallBack(new RfidOperationUtils.readDataCallBack() {
            @Override
            public void OneStepReadDataCallBack(String data) {

            }

            @Override
            public void MoreStepReadDataCallBack(String[] datas) {

            }
        });
    }


    public String listToString3(List list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        return sb.toString();
    }

//    private void setListV(String listBeans) {
//
//        for (int i = 0; i < listView.size(); i++) {
//            listValue = listView.get(i);
//        }
//
//         if (listValue != null){
//             aa = listValue.split("/");
//        }
//
//        String a = listBeans.substring(listBeans.length() - 1);
//        if (a.equals("1") || a.equals("2")){
//            if (listView.size()==0){
//                listView.add(listBeans.substring(0,listBeans.length() - 1 )+"1" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"2");
//            }else {
//                if (!listToString3(listView).contains(listBeans)){
//                    listView.add(listBeans.substring(0,listBeans.length() - 1 )+"1" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"2");
//                }else {
//                    if (!aa[0].contains(listBeans) && !aa[1].contains(listBeans)){
//                        listView.add(listBeans.substring(0,listBeans.length() - 1 )+"1" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"2");
//                    }
//                }
//            }
//        }
//        if (a.equals("3") || a.equals("4")){
//            if (listView.size()==0){
//                listView.add(listBeans.substring(0,listBeans.length() - 1 )+"3" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"4");
//            }else {
//                if (!listToString3(listView).contains(listBeans)){
//                    listView.add(listBeans.substring(0,listBeans.length() - 1 )+"3" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"4");
//                }else {
//                    if (!aa[0].contains(listBeans) && !aa[1].contains(listBeans)){
//                        listView.add(listBeans.substring(0,listBeans.length() - 1 )+"3" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"4");
//                    }
//                }
//            }
//        }
//        if (a.equals("5") || a.equals("6")){
//            if (listView.size()==0){
//                listView.add(listBeans.substring(0,listBeans.length() - 1 )+"5" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"6");
//            }else {
//                if (!listToString3(listView).contains(listBeans)){
//                    listView.add(listBeans.substring(0,listBeans.length() - 1 )+"5" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"6");
//                }else {
//                    if (!aa[0].contains(listBeans) && !aa[1].contains(listBeans)){
//                        listView.add(listBeans.substring(0,listBeans.length() - 1 )+"5" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"6");
//                    }
//                }
//            }
//        }
//        if (a.equals("7") || a.equals("8")){
//            if (listView.size()==0){
//                listView.add(listBeans.substring(0,listBeans.length() - 1 )+"7" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"8");
//            }else {
//                if (!listToString3(listView).contains(listBeans)){
//                    listView.add(listBeans.substring(0,listBeans.length() - 1 )+"7" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"8");
//                }else {
//                    if (!aa[0].contains(listBeans) && !aa[1].contains(listBeans)){
//                        listView.add(listBeans.substring(0,listBeans.length() - 1 )+"7" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"8");
//                    }
//                }
//            }
//        }
//        if (a.equals("9") || a.equals("0")){
//            if (listView.size()==0){
//                listView.add(listBeans.substring(0,listBeans.length() - 1 )+"9" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"0");
//            }else {
//                if (!listToString3(listView).contains(listBeans)){
//                    listView.add(listBeans.substring(0,listBeans.length() - 1 )+"9" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"0");
//                }else {
//                    if (!aa[0].contains(listBeans) && !aa[1].contains(listBeans)){
//                        listView.add(listBeans.substring(0,listBeans.length() - 1 )+"9" + "/" + listBeans.substring(0,listBeans.length() - 1 )+"0");
//                    }
//                }
//            }
//        }
//
//        AboutWorkAdapter aboutWorkAdapter = new AboutWorkAdapter(mContext,listView);
//        lvOutList.setAdapter(aboutWorkAdapter);
//
//    }

    private void setListV(String listBeans) {

        for (int i = 0; i < listView.size(); i++) {
            listValue = listView.get(i);
        }

        if (listValue != null){
            String[] aa = listValue.split("/");
        }

        String a = listBeans.substring(listBeans.length() - 1);
        if (a.equals("1") || a.equals("2")){
            String e = listBeans.substring(0, listBeans.length() - 1) + "1" + "/" + listBeans.substring(0, listBeans.length() - 1) + "2";
            if (listView.size()==0){
                listView.add(e);
            }else {
                if (!listToString3(listView).contains(listBeans)){
                    listView.add(e);
                }
            }
        }
        if (a.equals("3") || a.equals("4")){
            String e = listBeans.substring(0, listBeans.length() - 1) + "3" + "/" + listBeans.substring(0, listBeans.length() - 1) + "4";
            if (listView.size()==0){
                listView.add(e);
            }else {
                if (!listToString3(listView).contains(listBeans)){
                    listView.add(e);
                }
            }
        }
        if (a.equals("5") || a.equals("6")){
            String e = listBeans.substring(0, listBeans.length() - 1) + "5" + "/" + listBeans.substring(0, listBeans.length() - 1) + "6";
            if (listView.size()==0){
                listView.add(e);
            }else {
                if (!listToString3(listView).contains(listBeans)){
                    listView.add(e);
                }
            }
        }
        if (a.equals("7") || a.equals("8")){
            String e = listBeans.substring(0, listBeans.length() - 1) + "7" + "/" + listBeans.substring(0, listBeans.length() - 1) + "8";
            if (listView.size()==0){
                listView.add(e);
            }else {
                if (!listToString3(listView).contains(listBeans)){
                    listView.add(e);
                }
            }
        }
        if (a.equals("9") || a.equals("0")){
            String e = listBeans.substring(0, listBeans.length() - 1) + "9" + "/" + listBeans.substring(0, listBeans.length() - 1) + "0";
            if (listView.size()==0){
                listView.add(e);
            }else {
                if (!listToString3(listView).contains(listBeans)){
                    listView.add(e);
                }
            }
        }

        AboutWorkAdapter aboutWorkAdapter = new AboutWorkAdapter(mContext,listView);
        lvOutList.setAdapter(aboutWorkAdapter);

    }


    public class AboutWorkAdapter extends BaseAdapter {
        protected LayoutInflater mInflater;
        private List<String> listBeans;
        private Context context;
        private String name;

        public AboutWorkAdapter(Context context, List<String> treeList) {
            this.mInflater = LayoutInflater.from(context);
            this.context = context;
            this.listBeans = treeList;

        }


        @Override
        public int getCount() {
            int size = listBeans.size();
            if (size > 0) {
                return size >= 1000 ? 1000 : size;
            } else {
                return listBeans != null ? listBeans.size() : 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return listBeans != null ? listBeans.get(position) : "";
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final PDActivity.ViewHolder holder;
            notifyDataSetChanged();
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_check_box, parent, false);
                holder = new ViewHolder();
                holder.check = (convertView.findViewById(R.id.check));
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.check.setChecked(true);

            holder.check.setText(listView.get(position));
            if (submitList.size() == 0){
                submitList.add(listView.get(position));
            }else if (!submitList.contains(listView.get(position))){
                submitList.add(listView.get(position));
            }
            holder.check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!holder.check.isChecked()){
                        submitList.remove(listView.get(position));
                    }else {
                        submitList.add(listView.get(position));
                    }
                }
            });
            buttonSetUp();

            shul.setText(submitList.size() + "/" + sl);

            return convertView;
        }


    }
    class ViewHolder {
        CheckBox check;
    }





    @Override
    public void onPause() {
        super.onPause();

        if (mRfidOperationUtils != null) {
            // 停止识别
            mRfidOperationUtils.stopInventory();
        }
        if (null != mBarcode2DUtil) {
            mBarcode2DUtil.stopScan();
        }


    }

    @Override
    protected void onDestroy() {


        super.onDestroy();
        //关闭
        if (null != mRfidOperationUtils) {
            mRfidOperationUtils.closeInventory();
            mRfidOperationUtils.stopInventory();
        }
        if (null != mBarcode2DUtil) {
            mBarcode2DUtil.close();
        }
        OkGo.getInstance().cancelTag(this);


    }

    /**********************二维码扫描***************************/
    private ScanBarcode2DUtil mBarcode2DUtil;

    private void initScan() {
        mBarcode2DUtil = new ScanBarcode2DUtil(this);
        /**
         * 二维码扫描返回值
         */
        mBarcode2DUtil.scanCode(new ScanBarcode2DUtil.scanBarcode2DCallBack() {
            @Override
            public void scanBarcode2DCallBack(String code) {
                LogUtil.d("点击了 快捷键scan code :" + code);
                commitTpByScanId(code, "1");
            }
        });

    }
}