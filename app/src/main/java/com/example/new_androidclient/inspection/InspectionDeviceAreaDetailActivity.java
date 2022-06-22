package com.example.new_androidclient.inspection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.BaseResponse;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.DataConverterUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityInspectionDeviceAreaDetailBinding;
import com.example.new_androidclient.inspection.bean.InspectionAreaBean;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 宏观巡检详情页
 * 注：区域巡检和宏观巡检名字换了
 */
@Route(path = RouteString.InspectionDeviceAreaDetailActivity)
public class InspectionDeviceAreaDetailActivity extends BaseActivity {
    private ActivityInspectionDeviceAreaDetailBinding binding;
    private Listener listener = new Listener();
    private int resultType = -1;
    private int qualify = 1;
    private int un_qualify = 0;

    private final String btnText = "下一区域";

    private int REQUEST_QR = 1;

    @Autowired()
    int areaRoughPosition;

    @Autowired()
    int taskId;

    @Autowired()
    int areaId;

    @Autowired
    List<InspectionAreaBean> roughAreaList;

    @Autowired()
    boolean finish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_device_area_detail);
        binding.setListener(listener);
        binding.leftRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showSelectDeviceButton(false);
                    binding.rightRadioButton.setChecked(false);
                } else {
                    showSelectDeviceButton(true);
                    binding.rightRadioButton.setChecked(true);
                }
            }
        });
        binding.rightRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showSelectDeviceButton(true);
                    binding.leftRadioButton.setChecked(false);
                } else {
                    showSelectDeviceButton(false);
                    binding.leftRadioButton.setChecked(false);
                }
            }
        });
        setBtn();
    }

    private void setBtn() {
        if (areaRoughPosition == roughAreaList.size() - 1) {
            binding.nextDevice.setText("完成巡检");
        }
        if (finish) {
            binding.nextDevice.setVisibility(View.GONE);
        }

    }

    private void showSelectDeviceButton(boolean show) {
        if (show) { //不合格
            binding.selectDevice.setVisibility(View.VISIBLE);
            resultType = un_qualify;
        } else {  //合格
            binding.selectDevice.setVisibility(View.GONE);
            resultType = qualify;
        }
    }

    private void submitData() {
//        Map map = new HashMap();
//        map.put("resultContent", binding.inspectionPicEdittext.getText().toString());
//        map.put("resultType", String.valueOf(resultType));
        RetrofitUtil.getApi().updItemResultDevice(roughAreaList.get(areaRoughPosition).getId(),
                binding.inspectionPicEdittext.getText().toString(),
                String.valueOf(resultType))
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<BaseResponse>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(BaseResponse bean) {
                        if (bean.getData().equals("保存成功") || bean.getMsg().equals("success")) {
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
        setBtn();
        Intent intent = new Intent(InspectionDeviceAreaDetailActivity.this, CustomCaptureActivity.class);
        startActivityForResult(intent, REQUEST_QR);

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
                        binding.leftRadioButton.setChecked(false);
                        binding.rightRadioButton.setChecked(false);
                        binding.inspectionPicEdittext.setText("");
                        binding.selectDevice.setVisibility(View.GONE);
                        resultType = -1;
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


    private boolean check() {
        if (resultType == -1) {
            ToastUtil.show(mContext, "请选择巡检结果");
            return false;
        } else if (resultType == un_qualify && binding.inspectionPicEdittext.getText().toString().isEmpty()) {
            ToastUtil.show(mContext, "请填写巡检描述");
            return false;
        }
        return true;
    }

    public class Listener {
        public void selectDevice() {
            ARouter.getInstance().build(RouteString.InspectionDeviceRoughAndAreaSelectActivity)
                    .withInt("itemId", roughAreaList.get(areaRoughPosition).getId())
                    .withInt("areaId", roughAreaList.get(areaRoughPosition).getAreaId())
                    .withInt("from", 1)  //1区域巡检 2宏观巡检  宏观巡检和区域巡检交换了，但图片类型⬅️没换
                    .navigation();
        }

        public void saveData() {
            if (check()) {
                submitData();
            }
        }
    }
}
