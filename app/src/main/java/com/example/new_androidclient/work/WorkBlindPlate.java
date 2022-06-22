package com.example.new_androidclient.work;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityWorkBlindPlateBinding;
import com.example.new_androidclient.work.Adapter.WorkBlindPlateAdapter;
import com.example.new_androidclient.work.bean.WorkBlindPlateBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽堵盲板清单
 */
@Route(path = RouteString.WorkBlindPlate)
public class WorkBlindPlate extends BaseActivity {
    private Listener listener = new Listener();
    private ActivityWorkBlindPlateBinding binding;
    private WorkBlindPlateAdapter adapter;
    private List<WorkBlindPlateBean> list = new ArrayList<>();

    @Autowired
    int applicationId;

    @Autowired
    String type;

    @Autowired
    boolean isSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_blind_plate);
        binding.setListener(listener);
        adapter = new WorkBlindPlateAdapter(list, BR.work_blind_plate_item, (view, position) -> { });
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);
        binding.title.getLinearLayout_back().setVisibility(View.GONE);
        if(isSign){
            binding.agree.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();  getList();

    }

    private void getList() {
        RetrofitUtil.getApi().selectBlindPlate(applicationId, type)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<WorkBlindPlateBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<WorkBlindPlateBean> bean) {
                        if (bean.size() > 0) {
                            binding.recycler.setVisibility(View.VISIBLE);
                            binding.nodata.setVisibility(View.GONE);
                            list.clear();
                            list.addAll(bean);
                            adapter.notifyDataSetChanged();
                        } else {
                            binding.recycler.setVisibility(View.GONE);
                            binding.nodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(list.size() > 0){
            setResult(2);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public class Listener {
        public void add() {
            ARouter.getInstance().build(RouteString.WorkAddBlindPlateActivity)
                    .withInt("applicationId", applicationId).navigation();
        }
    }
}
