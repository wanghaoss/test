package com.example.new_androidclient.inspection;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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
import com.example.new_androidclient.Util.ToastUtil;

/**
 * 终止巡检
 */
@Route(path = RouteString.InspectionStopActivity)
public class InspectionStopActivity extends BaseActivity {
    private EditText editText;
    private Button button;

    @Autowired()
    int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_stop);
        editText = findViewById(R.id.inspection_stop_edittext);
        button = findViewById(R.id.inspection_stop_btn);
        button.setOnClickListener(v -> {
            String str = editText.getText().toString();
            if (str.isEmpty()) {
                ToastUtil.show(mContext, "请填写终止原因");
            } else {
                RetrofitUtil.getApi().stopTask(taskId, str)
                        .compose(new SchedulerTransformer<>())
                        .subscribe(new DialogObserver<BaseResponse>(mContext, true, "正在提交") {

                            @Override
                            public void onSuccess(BaseResponse bean) {
                                if (bean.getMsg().equals("success")) {
                                    ToastUtil.show(mContext, "已终止巡检");
                              //      ARouter.getInstance().build(RouteString.AreaDistinguishActivity).navigation();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(String err) {
                                ToastUtil.show(mContext, "终止失败，失败原因： " + err);

                            }
                        });
            }
        });
    }

}
