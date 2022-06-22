package com.example.new_androidclient.work;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.king.signature.PaintActivity;
import android.king.signature.config.PenConfig;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
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
import com.example.new_androidclient.customize_view.TitleLayout;
import com.example.new_androidclient.customize_view.WorkAddSignLineLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * wh
 * 许可会签
 */

@Route(path = RouteString.PermitCountersignActivity)
public class PermitCountersignActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title)
    TitleLayout title;
    @BindView(R.id.tjBut1)
    Button tjBut1;
    @BindView(R.id.tjBut2)
    Button tjBut2;
    @BindView(R.id.tjBut3)
    Button tjBut3;
    @BindView(R.id.tjBut4)
    Button tjBut4;
    @BindView(R.id.tjLayout)
    LinearLayout tjLayout;
    @BindView(R.id.erjBut1)
    Button erjBut1;
    @BindView(R.id.erjBut2)
    Button erjBut2;
    @BindView(R.id.erjBut3)
    Button erjBut3;
    @BindView(R.id.erjLayout)
    LinearLayout erjLayout;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.textView)
    TextView textView;

    private List<WorkAddSignLineLayout> itemList = new ArrayList<>();
    int userId;
    String peopleId;
    Map<String, String> map = new HashMap<>();

    @Autowired
    int applicationId;

    @Autowired
    String ticketFlag; //动火级别

    @Autowired
    String status; //01+02开作业票签字   08+09+10关闭  11取消

    private AlertDialog alertDialog;

    String opinion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permit_countersign);
        ButterKnife.bind(this);
        userId = (int) SPUtil.getData(SPString.UserId, 1);
        peopleId = String.valueOf(userId);

        title.setName("许可会签");
        selectBlindPlate();
        textView.setOnClickListener(this);
    }

    private void setERJOnClick() {
        if (map.containsKey("01")) {
            erjBut1.setEnabled(false);
            erjBut1.setText("已审核");
        } else {
            erjBut1.setOnClickListener(this);
        }
        if (map.containsKey("02")) {
            erjBut2.setEnabled(false);
            erjBut2.setText("已审核");
        } else {
            erjBut2.setOnClickListener(this);
        }
        if (map.containsKey("04")) {
            erjBut3.setEnabled(false);
            erjBut3.setText("已审批");
        } else {
            erjBut3.setOnClickListener(this);
        }
    }

    private void setTJOnClick() {
        if (map.containsKey("01")) {
            tjBut1.setEnabled(false);
            tjBut1.setText("已审核");
        } else {
            tjBut1.setOnClickListener(this);
        }
        if (map.containsKey("02")) {
            tjBut2.setEnabled(false);
            tjBut2.setText("已审核");
        } else {
            tjBut2.setOnClickListener(this);
        }
        if (map.containsKey("03")) {
            tjBut3.setEnabled(false);
            tjBut3.setText("已审核");
        } else {
            tjBut3.setOnClickListener(this);
        }
        if (map.containsKey("04")) {
            tjBut4.setEnabled(false);
            tjBut4.setText("已审批");
        } else {
            tjBut4.setOnClickListener(this);
        }


    }

    private void addCancelAndCloseView(int type) {
        Intent intent = new Intent(PermitCountersignActivity.this, PaintActivity.class);
//        intent.putExtra("background", Color.WHITE);//画布背景色，默认透明，也是最终生成图片的背景色
//        intent.putExtra("width", 800); //画布宽度，最大值3000，默认占满布局
//        intent.putExtra("height", 800);//画布高度，最大值3000，默认占满布局
        intent.putExtra("crop", false);   //最终的图片是否只截取文字区域
        intent.putExtra("format", PenConfig.FORMAT_PNG); //图片格式
//        intent.putExtra("image", imagePath); //初始图片
        intent.putExtra("resultCode", type);
        startActivityForResult(intent, type);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tjBut1:
            case R.id.erjBut1:
                sign(1);
                break;
            case R.id.tjBut2:
            case R.id.erjBut2:
                sign(2);
                break;
            case R.id.tjBut3:
                sign(3);
                break;
            case R.id.tjBut4:
                if (tjBut1.getText().equals("已审核") && tjBut2.getText().equals("已审核") || tjBut3.getText().equals("已审核")) {
                    sign(4);
                } else {
                    ToastUtil.show(mContext, "请先完成审核");
                }
                break;
            case R.id.erjBut3:
                if (erjBut1.getText().equals("已审核") && erjBut2.getText().equals("已审核")) {
                    sign(4);
                } else {
                    ToastUtil.show(mContext, "请先完成审核");
                }
                break;
            case R.id.textView:
                Intent intent = new Intent(mContext,WorkSigntureActivity.class);
                intent.putExtra("applicationId",applicationId);
                startActivity(intent);
                break;
        }
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
        String savePath = data.getStringExtra(PenConfig.SAVE_PATH);
        LogUtil.i(savePath);
        if (requestCode == 1) {
            signatureWork("01", savePath,opinion,"0");
        }
        if (requestCode == 2) {
            signatureWork("02", savePath,opinion,"0");
        }
        if (requestCode == 3) {
            signatureWork("03", savePath,opinion,"0");
        }
        if (requestCode == 4) {
            signatureWork("04", savePath,opinion,"0");
        }

    }

    private void selectBlindPlate() {
        RetrofitUtil.getApi().getCountersignApproveUsers(applicationId, "DH")
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<Map<String, String>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(Map<String, String> bean) {
                            map.putAll(bean);
                            if (ticketFlag.equals("特级") || ticketFlag.equals("一级")) {
                                if (map.containsKey("01") && map.containsKey("02") && map.containsKey("04") && map.containsKey("03")) {
                                    finish();
                                }else {
                                    tjLayout.setVisibility(View.VISIBLE);
                                    setTJOnClick();
                                }
                            }
                            if (ticketFlag.equals("二级")) {
                                if (map.containsKey("01") && map.containsKey("02") && map.containsKey("04")) {
                                    finish();
                                }else {
                                    erjLayout.setVisibility(View.VISIBLE);
                                    setERJOnClick();
                                }
                            }
                        }
                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


    private void signatureWork(String smCheck, String savePath,String opinion,String result) {
        //processOPinion 处理意见   processRole  角色  processResult  处理结果  processUser  角色ID
        RetrofitUtil.getApi().getCountersignApprove(applicationId, "DH", smCheck, userId, opinion, result)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在上传数据") {
                    @Override
                    public void onSuccess(String bean) {
                        if (result.equals("0")){
                            String prefix;
                            prefix = "DH-A-";
                            submitPic(new File(savePath), String.valueOf(applicationId), Constants.FileType72, prefix, smCheck);
                        }else {
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void submitPic(File file, String id, String type, String prefix, String fileList) {
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


    //填写审核与审批意见Dialog
    public void sign(int type) {
        alertDialog = new AlertDialog.Builder(PermitCountersignActivity.this).setPositiveButton("同意", null).setNegativeButton("拒绝", null).create();
        alertDialog.setTitle("请填写意见");

        Window win = alertDialog.getWindow();
        win.getDecorView().setPadding(10, 20, 10, 20);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

        View view = getLayoutInflater().inflate(R.layout.hazard_plan_sign_dialog, null);
        final EditText msg_edit = view.findViewById(R.id.hazard_plan_sign_dialog_edittext);
        alertDialog.setView(view);//设置login_layout为对话提示框
        alertDialog.setCancelable(true);//设置为不可取消
        alertDialog.setOnShowListener(mDialog -> {
            Button positionButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            positionButton.setOnClickListener(v -> {
                opinion = msg_edit.getText().toString().trim();
                addCancelAndCloseView(type);
                alertDialog.dismiss();
            });
            negativeButton.setOnClickListener(v -> {
                if (msg_edit.getText().toString().trim().isEmpty() || msg_edit.getText().toString().equals("")) {
                    ToastUtil.show(mContext, "请填写意见");
                } else {
                    String value = msg_edit.getText().toString().trim();
                    showCancelDialog(type,value);
                }
            });
        });
        alertDialog.show();
    }

    //确认dialog
    public void showCancelDialog(int type,String value) {
        AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_account_security_unbind, null);
        TextView cancel =view.findViewById(R.id.confirmTView);
        TextView sure =view.findViewById(R.id.closeTView);
        final EditText edittext =view.findViewById(R.id.contentEdit);
        TextView titleTView = view.findViewById(R.id.titleTView);
        final Dialog dialog= builder.create();

        titleTView.setText("是否确认取消");
        edittext.setVisibility(View.GONE);
        dialog.show();
        dialog.getWindow().setContentView(view);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1){
                    signatureWork("01","",value,"1");
                }
                if (type == 2){
                    signatureWork("02","",value,"1");
                }
                if (type == 3){
                    signatureWork("03","",value,"1");
                }
                if (type == 4){
                    signatureWork("04","",value,"1");
                }
                alertDialog.dismiss();
                dialog.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}

