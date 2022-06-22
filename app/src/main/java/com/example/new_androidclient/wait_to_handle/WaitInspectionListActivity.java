package com.example.new_androidclient.wait_to_handle;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.Other.SPString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.DataConverterUtil;
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.WaitInspectionListItemLayout;
import com.example.new_androidclient.databinding.ActivityWaitInspectionBinding;
import com.example.new_androidclient.wait_to_handle.bean.WaitInspectionListBean;
import com.example.new_androidclient.work.data.WorkConditionDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待办-巡检列表
 */
@Route(path = RouteString.WaitInspectionListActivity)
public class WaitInspectionListActivity extends BaseActivity {
    private ActivityWaitInspectionBinding binding;
    private Listener listener = new Listener();
    private List<WaitInspectionListBean> list = new ArrayList<>();
    private List<WaitInspectionListBean> checkList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wait_inspection);
        binding.setListener(listener);

        getList();
    }

    private void getList() {
        checkList.clear();
        setBtnVisibility(false, false, false);
        binding.waitInspectionListLinear.removeAllViews();
        RetrofitUtil.getApi().findExceptionHandling(1, 100, 0)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<WaitInspectionListBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<WaitInspectionListBean> bean) {

                        if (bean.size() > 0) {
                            list.clear();
                            list.addAll(bean);
                            binding.waitInspectionListBtnLinear.setVisibility(View.VISIBLE);
                            binding.waitInspectionListScroll.setVisibility(View.VISIBLE);
                            binding.waitInspectionListNodata.setVisibility(View.GONE);
                            for (int i = 0; i < list.size(); i++) {
                                addView(i);
                            }
                        } else {
                            binding.waitInspectionListBtnLinear.setVisibility(View.GONE);
                            binding.waitInspectionListScroll.setVisibility(View.GONE);
                            binding.waitInspectionListNodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void addView(int position) {
        WaitInspectionListItemLayout layout = new WaitInspectionListItemLayout(mContext, position);
        layout.init();
        //1设备  2区域  3宏观
        if (list.get(position).getInspectionType() == 1) { //设备
            String str;
            String description;
            if (list.get(position).getDisposeScheme() == null) {
                description = "";
            } else {
                description = "处置状态：" + list.get(position).getDisposeDescription();
            }
            str = "设备位号：" + list.get(position).getTagNo() + "\n" +
                    "参数名：" + list.get(position).getSpecName() + "\n" +
                    "类型：" + list.get(position).getExceptionType_str() + "\n" + description;
            layout.setText(str);
        } else if (list.get(position).getInspectionType() == 2 || list.get(position).getInspectionType() == 3) {  //2区域  3宏观
            //  layout.setText(list.get(position).getTagNo() + "  " + list.get(position).getDeviceName() + "异常");
            String str;
            String description;
            if (list.get(position).getDisposeScheme() == null) {
                description = "";
            } else {
                description = "处置状态：" + list.get(position).getDisposeDescription();
            }
            str = "设备位号：" + list.get(position).getTagNo() + "\n" +
                    "设备名：" + list.get(position).getDeviceName() + "\n" +
                    "类型：" + list.get(position).getExceptionType_str() + "\n" + description;
            layout.setText(str);
        }

        binding.waitInspectionListLinear.addView(layout);
        layout.setOnClickListener(v -> ARouter.getInstance().build(RouteString.WaitInspectionDetail)
                .withString("des", list.get(layout.getPos()).getResultDescription())
                .withInt("id", list.get(layout.getPos()).getResultId())
                .withInt("inspectionType", list.get(layout.getPos()).getInspectionType())
                .navigation());

        //异常类型 默认0需要判断类型 1设备 2生产
        layout.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (checkList.size() == 0) {
                    checkList.add(list.get(layout.getPos()));
                    setBtnView(list.get(layout.getPos()));
                } else {
                    if (list.get(layout.getPos()).getExceptionType() != checkList.get(0).getExceptionType() ||
                            list.get(layout.getPos()).getDisposeDescription() != checkList.get(0).getDisposeDescription()) {

                        ToastUtil.show(mContext, "非同类型数据");
                        layout.getCheckBox().setChecked(false);
                    } else {
                        checkList.add(list.get(layout.getPos()));
                    }


                }
            } else {
                checkList.remove(list.get(layout.getPos()));
                if (checkList.size() == 0) {
                    setBtnVisibility(false, false, false);
                }
            }
        });
    }

    //one 判断异常类型 two是否可以处置 three异常消除
    private void setBtnView(WaitInspectionListBean bean) {
        if (bean.getExceptionType() == 0) { //0未判断类型 1设备 2生产
            setBtnVisibility(true, false, false);
        } else if (bean.getDisposeDescription() != null && bean.getDisposeDescription().equals("处置中")) {
            setBtnVisibility(false, false, true);
        } else {
            setBtnVisibility(false, true, false);
        }
    }

    private void setBtnVisibility(boolean one, boolean two, boolean three) {
        if (one) {
            binding.one.setVisibility(View.VISIBLE);
        } else {
            binding.one.setVisibility(View.GONE);
        }
        if (two) {
            binding.two.setVisibility(View.VISIBLE);
        } else {
            binding.two.setVisibility(View.GONE);
        }
        if (three) {
            binding.three.setVisibility(View.VISIBLE);
        } else {
            binding.three.setVisibility(View.GONE);
        }
    }

    //    private boolean checkList() {
//        if (checkList.size() == 0) {
//            ToastUtil.show(mContext, "请选择至少一项");
//            return false;
//        }
//        return true;
//    }
    private void exType(Integer type) {//判断异常类型
        String ids = "";
        for (int i = 0; i < checkList.size(); i++) {
            ids += (checkList.get(i).getId() + ",");
        }
        RetrofitUtil.getApi().exType(ids.substring(0, ids.length() - 1), type)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(String bean) {
                        ToastUtil.show(mContext, "处理成功");
                        getList();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void dispose(Integer type) {//是否处置
        String ids = "";
        for (int i = 0; i < checkList.size(); i++) {
            ids += (checkList.get(i).getId() + ",");
        }
        RetrofitUtil.getApi().dispose(ids.substring(0, ids.length() - 1), type)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(String bean) {
                        ToastUtil.show(mContext, "处理成功");
                        getList();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void exceptionHandlingFinish() { //消除
        String ids = "";
        for (int i = 0; i < checkList.size(); i++) {
            ids += (checkList.get(i).getId() + ",");
        }
        RetrofitUtil.getApi().exceptionHandlingFinish(ids.substring(0, ids.length() - 1))
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(String bean) {
                        ToastUtil.show(mContext, "处理成功");
                        getList();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void notTrouble() {
        String ids = "";
        for (int i = 0; i < checkList.size(); i++) {
            ids += (checkList.get(i).getId() + ",");
        }
        RetrofitUtil.getApi().exceptionHandlingFinish(ids.substring(0, ids.length() - 1))
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(String bean) {
                        ToastUtil.show(mContext, "处理成功");
                        getList();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    public class Listener {
        public void one() { //判断异常类型
            new WorkConditionDialog(WaitInspectionListActivity.this, "8", (value, type1, pos, dialog) -> {
                if (pos == 0) { // 生产异常
                    exType(2);
                } else {  //设备异常
                    exType(1);
                }
                dialog.dismiss();
            }).show();
        }

        public void two() {  //是否处置
            new WorkConditionDialog(WaitInspectionListActivity.this, "9", (value, type1, pos, dialog) -> {
                if (pos == 0) { // 无法处置
                    dispose(0);
                } else {  //可以处置
                    dispose(1);
                }
                dialog.dismiss();
            }).show();
        }

        public void three() {  //是否处置
            exceptionHandlingFinish();
        }

        public void four() {  //非异常
            notTrouble();
        }

    }
}
