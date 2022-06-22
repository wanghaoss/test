package com.example.new_androidclient.wait_to_handle;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.Constants;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityWaitInspectionDetailBinding;
import com.example.new_androidclient.hazard.adapter.HazardPicAdapter;
import com.example.new_androidclient.wait_to_handle.adapter.WaitInspectionDetailPicAdapter;
import com.example.new_androidclient.wait_to_handle.bean.DefaultPicBean;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouteString.WaitInspectionDetail)
public class WaitInspectionDetail extends BaseActivity {
    private ActivityWaitInspectionDetailBinding binding;
    private WaitInspectionDetailPicAdapter adapter;
    private List<DefaultPicBean> picList = new ArrayList<>();
    @Autowired()
    String des;

    @Autowired()
    int id;

    @Autowired()
    int inspectionType;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wait_inspection_detail);
        if (des == null || des.isEmpty()) {
            des = "无";
        }
        binding.waitInspectionDetailDes.setText(getResources().getString(R.string.wait_inspection_detail_des) + des);
        adapter = new WaitInspectionDetailPicAdapter(picList, mContext);
        binding.waitInspectionDetailPicRecyeler.setLayoutManager(new GridLayoutManager(mContext, 3));
        binding.waitInspectionDetailPicRecyeler.setAdapter(adapter);
        getPicList();
    }

    //1设备  2区域  3宏观
    private void getPicList() {
        String type = "";
        if (inspectionType == 1) {
            type = Constants.FileType32;
        }else if(inspectionType == 2){
            type = Constants.FileType33;
        }else if(inspectionType == 3){
            type = Constants.FileType34;
        }else{
            ToastUtil.show(mContext,"type为空");
            return;
        }
        RetrofitUtil.getApi().getPicUrl_wait(id, type)  //32参数
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<DefaultPicBean>>(mContext, true, "正在获取图片列表") {
                    @Override
                    public void onSuccess(List<DefaultPicBean> bean) {
                        if (bean.size() > 0) {
                            picList.clear();
                            picList.addAll(bean);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


}
