package com.example.new_androidclient.hazard;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityHazardAnalysisPersonSelectBinding;
import com.example.new_androidclient.hazard.adapter.HazardAnalysisPersonListAdapter;
import com.example.new_androidclient.hazard.bean.HazardAnalysisPersonBean;
import com.example.new_androidclient.login.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouteString.HazardAnalysisPersonSelectActivity)
public class HazardAnalysisPersonSelectActivity extends BaseActivity {
    private ActivityHazardAnalysisPersonSelectBinding binding;
    private HazardAnalysisPersonListAdapter adapter;
    private List<HazardAnalysisPersonBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hazard_analysis_person_select);
        binding.hazardAnalysisPersonRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HazardAnalysisPersonListAdapter(list, BR.hazard_analysis_person_list, new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("userId", String.valueOf(list.get(position).getId()));
                intent.putExtra("name", list.get(position).getLastName()+list.get(position).getFirstName());
                setResult(2, intent);
                finish();
            }
        });
        binding.hazardAnalysisPersonRecycler.setAdapter(adapter);
        getList();
    }

    private void getList(){
        RetrofitUtil.getApi().getAllUserList()
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardAnalysisPersonBean>>(mContext, true, "正在获取列表") {
                    @Override
                    public void onSuccess(List<HazardAnalysisPersonBean> bean) {
                        if(bean.size() > 0){
                            list.addAll(bean);
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
