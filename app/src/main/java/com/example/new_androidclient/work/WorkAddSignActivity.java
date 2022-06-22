package com.example.new_androidclient.work;

import android.content.Intent;
import android.king.signature.PaintActivity;
import android.king.signature.config.PenConfig;
import android.os.Bundle;

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
import com.example.new_androidclient.databinding.ActivityWorkAddSignBinding;
import com.example.new_androidclient.work.bean.WorkSignBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * zq
 * 添加作业票+取消作业+关闭作业签字
 */

@Route(path = RouteString.WorkAddSignActivity)
public class WorkAddSignActivity extends BaseActivity {
    private ActivityWorkAddSignBinding binding;
    private List<WorkAddSignLineLayout> itemList = new ArrayList<>();
    private String[] nameList = {"作业申请人", "作业监护人", "属地监护人", "属地责任人", "质安环部安全员"}; //预约签字
    private String[] closeList = {"作业负责人", "作业监护人", "属地监护人", "作业批准人"}; //取消关闭签字
    private int[] typeList = {1, 2, 3, 4, 5};//预约签字
    private int[] closeTypeList = {6, 7, 8, 9};//取消关闭签字
    int userId;
    String peopleId;
    private WorkSignBean signBean;
    private String type;

    @Autowired
    int applicationId;

    @Autowired
    String status; //01+02开作业票签字   08+09+10关闭  11取消


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_add_sign);
        userId = (int) SPUtil.getData(SPString.UserId, 1);
        peopleId = String.valueOf(userId);
        type = getIntent().getStringExtra("type");
        selectBlindPlate();

        setTitleValue();
    }

    private void setTitleValue() {
        if (status != null && status.equals("11")) {
            binding.title.setName("作业许可取消会签");
        } else if (type != null && type.equals("check")) {
            binding.title.setName("安全作业措施确认");
        } else {
            binding.title.setName("");
        }
    }

    private void addApplicationView(int pos) {
        WorkAddSignLineLayout layout = new WorkAddSignLineLayout(mContext, pos);
        layout.init(1);
        layout.setNameText(nameList[pos]);

        if (pos == 0) {
            if (signBean.getSmCheck1() != null) {
                setButton(layout);
            }
        } else if (pos == 1) {
            if (signBean.getSmCheck2() != null) {
                setButton(layout);
            }
        } else if (pos == 2) {
            if (signBean.getSmCheck3() != null) {
                setButton(layout);
            }
        } else if (pos == 3) {
            if (signBean.getSmCheck4() != null) {
                setButton(layout);
            }
        } else if (pos == 4) {
            if (signBean.getSmCheck5() != null) {
                setButton(layout);
            }
        }

        layout.getBtn().setOnClickListener(v -> {
            Intent intent = new Intent(WorkAddSignActivity.this, PaintActivity.class);
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

    private void addCancelAndCloseView(int pos) {
        WorkAddSignLineLayout layout = new WorkAddSignLineLayout(mContext, pos);
        layout.init(1);
        layout.setNameText(closeList[pos]);

        if (pos == 0) {
            if (signBean.getCcSign1() != null) {
                setButton(layout);
            }
        } else if (pos == 1) {
            if (signBean.getCcSign2() != null) {
                setButton(layout);
            }
        } else if (pos == 2) {
            if (signBean.getCcSign3() != null) {
                setButton(layout);
            }
        } else if (pos == 3) {
            if (signBean.getCcSign4() != null) {
                setButton(layout);
            }
        }

        layout.getBtn().setOnClickListener(v -> {
            Intent intent = new Intent(WorkAddSignActivity.this, PaintActivity.class);
            intent.putExtra("crop", false);   //最终的图片是否只截取文字区域
            intent.putExtra("format", PenConfig.FORMAT_PNG); //图片格式
            intent.putExtra("resultCode", closeTypeList[layout.getPos()]);
            startActivityForResult(intent, closeTypeList[layout.getPos()]);
        });
        binding.linear.addView(layout);
        itemList.add(layout);
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
        String savePath = data.getStringExtra(PenConfig.SAVE_PATH);
        LogUtil.i(savePath);
//            tvResult.setText(savePath);
//            Bitmap bitmap = BitmapFactory.decodeFile(savePath);
//            if (bitmap != null) {
//                ivShow.setImageBitmap(bitmap);
//            }
        // submitPic(new File(savePath), String.valueOf(applicationId), "72");

        signatureWork(resultCode, savePath);
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
                        switch (status) {
                            case "01":
                            case "02":
                                if (bean.getSmCheck1() != null &&
                                        bean.getSmCheck2() != null &&
                                        bean.getSmCheck3() != null &&
                                        bean.getSmCheck4() != null &&
                                        bean.getSmCheck5() != null) {
                                    ToastUtil.show(mContext, "签字都已完成");
                                    ARouter.getInstance().build(RouteString.WorkNeedDoActivity).navigation();
                                }
                                binding.linear.removeAllViews();
                                itemList.clear();
                                for (int i = 0; i < nameList.length; i++) {
                                    addApplicationView(i);
                                }
                                break;
                            case "08":
                            case "09":
                            case "10":
                            case "11":
                            case "12":
                                if (bean.getCcSign1() != null &&
                                        bean.getCcSign2() != null &&
                                        bean.getCcSign3() != null &&
                                        bean.getCcSign4() != null) {
                                    ToastUtil.show(mContext, "签字都已完成");
                                    ARouter.getInstance().build(RouteString.WorkNeedDoActivity).navigation();
                                }
                                binding.linear.removeAllViews();
                                itemList.clear();
                                for (int i = 0; i < closeList.length; i++) {
                                    addCancelAndCloseView(i);
                                }
                                break;
                        }

                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void signatureWork(int smCheck, String savePath) {
        RetrofitUtil.getApi().signatureWork("DH", applicationId, peopleId, smCheck)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在上传数据") {
                    @Override
                    public void onSuccess(String bean) {
                        String prefix;
                        if (status.equals("01") || status.equals("02")) {  //01+02 申请签字 08+09+10关闭签字 11取消签字
                            prefix = "DH-";
                            submitPic(new File(savePath), String.valueOf(applicationId), Constants.FileType72, prefix, smCheck);
                        } else {
                            prefix = "DH-C";
                            submitPic(new File(savePath), String.valueOf(applicationId), Constants.FileType72, prefix, smCheck % 5);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

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

