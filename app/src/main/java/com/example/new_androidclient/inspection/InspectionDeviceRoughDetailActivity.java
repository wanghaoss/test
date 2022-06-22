package com.example.new_androidclient.inspection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.Other.SPString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.InspectionDeviceRoughLineLayout;
import com.example.new_androidclient.databinding.ActivityInspectionDeviceRoughDetailBinding;
import com.example.new_androidclient.inspection.bean.InspectionAreaBean;
import com.example.new_androidclient.inspection.bean.TaskAreaItemResult;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域巡检详情页
 * 注：区域巡检和宏观巡检名字换了
 */
@Route(path = RouteString.InspectionDeviceRoughDetailActivity)
public class InspectionDeviceRoughDetailActivity extends BaseActivity {
    private ActivityInspectionDeviceRoughDetailBinding binding;
    private List<TaskAreaItemResult> allList = new ArrayList<>();
    private List<LinearLayout> itemList = new ArrayList<>(); //item列表
    private Listener listener = new Listener();

    public final String QUALIFIED = "1"; //合格
    public final String UNQUALIFIED = "0"; //不合格

    private String btnText = "下一区域";

    private int REQUEST_QR = 1;


    @Autowired()
    int areaRoughPosition;

    @Autowired()
    int taskId;

    @Autowired()
    int areaId;

    @Autowired()
    boolean finish = false;

    @Autowired
    List<InspectionAreaBean> roughAreaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_device_rough_detail);
        binding.setListener(listener);
        getData(taskId, areaId);
        setButton();
    }

    private void getData(int taskId, int areaId) {
        this.areaId = areaId;
        RetrofitUtil.getApi().findAreaItemListByTaskId(taskId, areaId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<TaskAreaItemResult>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<TaskAreaItemResult> bean) {
                        if (bean.size() > 0) {
                            allList.clear();
                            allList.addAll(bean);
                            binding.inspectionDeviceRoughDetailLinear.removeAllViews();
                            int userId = (int) SPUtil.getData(SPString.UserId, 0);
                            for (int i = 0; i < allList.size(); i++) {
                                allList.get(i).setInspector(userId);

                                addView(i);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                        finish();
                    }
                });
    }

    private void addView(int position) {
        InspectionDeviceRoughLineLayout layout = new InspectionDeviceRoughLineLayout(this, position);
        layout.initItemWidthEdit(allList.get(position).getItemName(), View.INVISIBLE);

        if (allList.get(position).getResultType() == null ||
                allList.get(position).getResultType().equals(QUALIFIED)) { //null或者合格1
            layout.setCheckBox(false); //不选中
            layout.setRightTextVisible(View.INVISIBLE);
        } else {          //不合格
            layout.setCheckBox(true); //选中
            layout.setRightTextVisible(View.VISIBLE);
        }

        if (finish) {  //如果巡检完了，把按钮都隐藏，不能修改
            layout.getCheckBox().setEnabled(false);
            layout.setRightTextVisible(View.GONE);
        } else {   //如果没巡检
            setUncheckData(position);
        }

        layout.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {  //选中 泄漏
                setCheckData(layout.getPos());
                layout.setRightTextVisible(View.VISIBLE);
            } else {   //不选中 合格
                setUncheckData(layout.getPos());
                layout.setRightTextVisible(View.INVISIBLE);
            }
        });

        layout.getRightIconText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouteString.InspectionDeviceRoughAndAreaSelectActivity)
                        .withInt("itemId", allList.get(layout.getPos()).getId())
                        .withInt("areaId", allList.get(layout.getPos()).getAreaId())
                        .withInt("from", 2)  ///1区域巡检 2宏观巡检  宏观巡检和区域巡检交换了，但图片类型⬅️没换
                        .navigation();
            }
        });

        binding.inspectionDeviceRoughDetailLinear.addView(layout);
        itemList.add(layout);
    }

    private void setCheckData(int position) {
        allList.get(position).setResult("不合格");
        allList.get(position).setResultType(UNQUALIFIED);
    }

    private void setUncheckData(int position) {
        allList.get(position).setResult("合格");
        allList.get(position).setResultType(QUALIFIED);
    }


    private void setButton() {
        if (areaRoughPosition == roughAreaList.size() - 1) {
            binding.nextDevice.setText("完成巡检");
        }
        if (finish) {
            binding.nextDevice.setVisibility(View.GONE);
        }
    }

    private void submitData(int areaId) {
//        for (int i = 0; i < allList.size(); i++) {
//            LogUtil.i(allList.get(i).getResultType() + "  " +i);
//        }
        RetrofitUtil.getApi().saveTaskAreaItemResult(taskId, areaId, allList)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("保存成功") || bean.equals("success")) {
                            if (binding.nextDevice.getText().toString().equals(btnText)) {
                                areaRoughPosition += 1;
                                startQR();
                            } else {
                                finish();
                            }

                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void startQR() {
        Intent intent = new Intent(InspectionDeviceRoughDetailActivity.this, CustomCaptureActivity.class);
        startActivityForResult(intent, REQUEST_QR);
        setButton();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_QR) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (result.equals(roughAreaList.get(areaRoughPosition).getAreaCode())) {
                        getData(roughAreaList.get(areaRoughPosition).getTaskId(),
                                roughAreaList.get(areaRoughPosition).getAreaId());
                    } else {
                        ToastUtil.show(mContext, "请扫描正确的区域码");
                        finish();
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.show(this, "解析二维码失败, 请重新扫描");
                }
            } else {
                finish();
            }
        }
    }

    public class Listener {
        public void saveData() {
             LogUtil.i(allList.get(0).getItemName());
            submitData(roughAreaList.get(areaRoughPosition).getAreaId());
        }
    }
}