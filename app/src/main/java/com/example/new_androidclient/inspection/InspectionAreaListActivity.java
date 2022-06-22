package com.example.new_androidclient.inspection;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityInspectionAreaListBinding;
import com.example.new_androidclient.inspection.adapter.InspectionAreaListAdapter;
import com.example.new_androidclient.inspection.bean.InspectionAreaBean;

import java.util.ArrayList;
import java.util.List;

import static com.example.new_androidclient.Other.SPString.InspectionAreaList;

/**
 * 三个点里，获取巡检路线
 */
@Route(path = RouteString.InspectionAreaListActivity)
public class InspectionAreaListActivity extends BaseActivity {

    private List<InspectionAreaBean> list = new ArrayList<>();
    private ActivityInspectionAreaListBinding binding;
    private InspectionAreaListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_area_list);
        adapter = new InspectionAreaListAdapter(list, BR.inspection_area_list_bean, (v, i) -> {});
        binding.inspectionAreaListRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.inspectionAreaListRecycler.setAdapter(adapter);
 //       getInspectionList();
    }

    //2020.11.4 因为获取区域列表需要刷卡获取taskCode，并且三个点里的功能只要终止，所以注释掉以下代码
//    private void getInspectionList() {
//        RetrofitUtil.getApi().getTaskAreaContent()
//                .compose(new SchedulerTransformer<>())
//                .subscribe(new DialogObserver<List<InspectionAreaBean>>(mContext, true, "正在初始化") {
//                    @Override
//                    public void onSuccess(List<InspectionAreaBean> bean) {
//                        if (bean.size() != 0) {
//                            list.addAll(bean);
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onFailure(String err) {
//                        ToastUtil.show(mContext, err);
//                        finish();
//                    }
//                });
//    }

}
