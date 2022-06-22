package com.example.new_androidclient.inspection;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.databinding.ActivityInspection24DetailBinding;
import com.example.new_androidclient.inspection.adapter.Inspection24DetailAdapter;
import com.example.new_androidclient.inspection.bean.Inspection24DetailBean;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouteString.Inspection24DetailActivity)
public class Inspection24ProblemDetailActivity extends BaseActivity {
    private List<DetailBean> list = new ArrayList<>();
    private ActivityInspection24DetailBinding binding;
    private Inspection24DetailAdapter adapter;

    @Autowired()
    int areaId;

    @Autowired()
    int deviceId;

    @Autowired()
    int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection24_detail);
        adapter = new Inspection24DetailAdapter(list, BR.inspection_24_detail_bean, (view, position) -> {});
        binding.inspection24DetailRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.inspection24DetailRecycler.setAdapter(adapter);
        getData();
    }

    private void getData(){
        RetrofitUtil.getApi().get24DeviceDetail(areaId, deviceId, taskId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<Inspection24DetailBean>>(mContext, true) {

                    @Override
                    public void onSuccess(List<Inspection24DetailBean> bean) {
                        if(bean.size() > 0){
                            for (int i = 0; i < bean.size(); i++) {
                                DetailBean newBean = new DetailBean();
                                newBean.result = bean.get(i).getResult();
                                newBean.unit = bean.get(i).getAssetspecUnit();
                                switch (bean.get(i).getSpecType()) {
                                    case 1:
                                    case 2:
                                        newBean.name = bean.get(i).getAssetspecName();
                                        break;
                                    case 3:
                                        newBean.name = bean.get(i).getPointName();
                                        break;
                                    case 4:
                                        newBean.name = bean.get(i).getOtherSpec();
                                        break;
                                }
                                list.add(newBean);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(String err) {

                    }
                });
    }

    public class DetailBean extends BaseObservable {
        private String result;
        private String name;
        private String unit;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
