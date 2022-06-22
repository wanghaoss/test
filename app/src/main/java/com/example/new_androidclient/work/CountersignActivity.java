package com.example.new_androidclient.work;

import android.content.Intent;
import android.king.signature.PaintActivity;
import android.king.signature.config.PenConfig;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.Constants;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.Other.SPString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.WorkAddSignLineLayout;
import com.example.new_androidclient.databinding.ActivityDiscloseAddSignBinding;
import com.example.new_androidclient.work.bean.WorkSignBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * wh
 * 交底人与接收人 交底会签
 */

@Route(path = RouteString.CountersignActivity)
public class CountersignActivity extends BaseActivity {
    private ActivityDiscloseAddSignBinding binding;
    private List<WorkAddSignLineLayout> itemList = new ArrayList<>();
    private String[] nameList = {"交底人"}; //交底会签
    private int[] typeList = {10,11};//预约签字
    int userId;
    String peopleId;
    private WorkSignBean signBean;
    private String type;
    List<String> list = new ArrayList<>();
    String receiver;

    @Autowired
    int applicationId;

    @Autowired
    String ticketFlag;

    @Autowired
    String status; //03交底人会签


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_disclose_add_sign);
        userId = (int) SPUtil.getData(SPString.UserId, 1);
        peopleId = String.valueOf(userId);
        type = getIntent().getStringExtra("type");

        binding.discloseAddSignBtn.setFocusableInTouchMode(false);

        selectBlindPlate();

        setTitleValue();
    }

    private void setTitleValue() {
            binding.title.setName("交底会签");
    }

    private void addApplicationView(int pos) {
        WorkAddSignLineLayout layout = new WorkAddSignLineLayout(mContext, pos);
        layout.init(1);
        layout.setNameText(nameList[pos]);

        if (pos == 0) {
            if (signBean.getSafetyUser() != null) {
                setButton(layout);
            }
        }

        layout.getBtn().setOnClickListener(v -> {
            Intent intent = new Intent(CountersignActivity.this, PaintActivity.class);
//        intent.putExtra("background", Color.WHITE);//画布背景色，默认透明，也是最终生成图片的背景色
//        intent.putExtra("width", 800); //画布宽度，最大值3000，默认占满布局
//        intent.putExtra("height", 800);//画布高度，最大值3000，默认占满布局
            intent.putExtra("crop", false);   //最终的图片是否只截取文字区域
            intent.putExtra("format", PenConfig.FORMAT_PNG); //图片格式
//        intent.putExtra("image", imagePath); //初始图片
            intent.putExtra("resultCode", typeList[layout.getPos()]);
            startActivityForResult(intent, typeList[layout.getPos()]);
        });

        binding.linear.addView(layout);
        itemList.add(layout);
    }

    private void setButtonMethod() {
        binding.discloseAddSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(RouteString.NFCActivity)
                        .withInt("code", 4040)
                        .withInt("module", 1) //1人员
                        .navigation(CountersignActivity.this, 4040);
            }
        });

        String value = "";
        for (int i = 0; i < itemList.size(); i++) {
            value = itemList.get(i).getBtn().getText().toString();
        }
        if (value.equals("已签字")){
            binding.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!binding.discloseAddSignBtn.getText().toString().equals("点击刷卡")){
                        signatureWork(11, null,receiver);
                    }
                }
            });
        }
    }

    private void setButton(WorkAddSignLineLayout layout) {
        layout.getBtn().setEnabled(false);
        layout.getBtn().setText("已签字");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (data == null) {
            return;
        }
        if (requestCode == 4040){
                String name = data.getStringExtra("name");
                list.add(name);
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                String itemName = list.get(i);

                buf.append(itemName).append(",");
                if (buf.length() > 0) {
                    receiver = buf.substring(0, buf.length() - 1);
                }
                binding.discloseAddSignBtn.setText(receiver);
            }
        }

        if (requestCode == 10){
            String savePath = data.getStringExtra(PenConfig.SAVE_PATH);
            LogUtil.i(savePath);
            signatureWork(10, savePath,peopleId);
        }


    }

    private void selectBlindPlate() {
        RetrofitUtil.getApi().selectSignatureWork("DH", applicationId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<WorkSignBean>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(WorkSignBean bean) {
                        if (bean != null) {
                            signBean = bean;
                        }
                        if ("03".equals(status)) {
                            assert bean != null;
                            if (bean.getSafetyUser() != null &&
                                    bean.getAcceptUser() != null) {
                                ToastUtil.show(mContext, "签字都已完成");
                                ARouter.getInstance().build(RouteString.PermitCountersignActivity)
                                        .withInt("applicationId", applicationId)
                                        .withString("ticketFlag",ticketFlag)
                                        .withString("status", status)
                                        .navigation();
                                finish();
                            }
                            binding.linear.removeAllViews();
                            itemList.clear();
                            for (int i = 0; i < nameList.length; i++) {
                                addApplicationView(i);
                            }
                            setButtonMethod();
                        }

                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void signatureWork(int smCheck, String savePath,String people) {
        RetrofitUtil.getApi().signatureWork("DH", applicationId, people, smCheck)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在上传数据") {
                    @Override
                    public void onSuccess(String bean) {
                        if (savePath == null){
                            ToastUtil.show(mContext,"上传成功");
                            finish();
                        }else {
                            String prefix;
                            if (status.equals("03")) {  //01+02 申请签字 08+09+10关闭签字 11取消签字
                                prefix = "DH-";
                                submitPic(new File(savePath), String.valueOf(applicationId), Constants.FileType72, prefix, 10);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }
                          //图片路径    对应id     type       固定前缀       固定类型
    private void submitPic(File file, String id, String type, String prefix, int fileList) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part MultipartFile = MultipartBody.Part.createFormData("files", file.getName(), requestFile);

        RequestBody requestBody_id = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestBody_type = RequestBody.create(MediaType.parse("text/plain"), type);
        RequestBody fileTypeList = RequestBody.create(MediaType.parse("text/plain"), prefix + fileList);  //DH需要变成动态

        RequestBody requestBody_userId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));

        RetrofitUtil.getApi().uploadFile_work(requestBody_id, requestBody_userId, requestBody_type, fileTypeList, MultipartFile)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在上传图片") {
                    @Override
                    public void onSuccess(String bean) {
                        ToastUtil.show(mContext, "上传成功");
                        selectBlindPlate();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


}

