package com.example.new_androidclient.device_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.example.new_androidclient.databinding.ActivityNewBinding;
import com.example.new_androidclient.device_management.Adapter.ContractorAdapter;
import com.example.new_androidclient.device_management.Adapter.ProjectPersonAdapter;
import com.example.new_androidclient.device_management.Adapter.WorkPeopleAdapter;
import com.example.new_androidclient.device_management.bean.ContractorListBean;
import com.example.new_androidclient.device_management.bean.DeviceAnalysisDeviceListBean;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouteString.NewActivity)
public class NewActivity extends BaseActivity {

    ActivityNewBinding binding;
    private String type;

    List<DeviceAnalysisDeviceListBean> list = new ArrayList<>();

    List<ContractorListBean> listBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_new);
        getView();
        setView();
    }

    private void getView() {
        type = getIntent().getStringExtra("type");
    }

    private void setView() {
        if ("1".equals(type)){
            binding.deviceInterimListRecycler.setVisibility(View.GONE);
            binding.text1.setVisibility(View.VISIBLE);
            binding.text1.setText("抢修");
            binding.text2.setVisibility(View.VISIBLE);
            binding.text2.setText("非抢修");

            binding.text1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("name","抢修");
                    setResult(1,intent);
                    finish();
                }
            });
            binding.text2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("name","未抢修");
                    setResult(2,intent);
                    finish();
                }
            });
        }

        if ("2".equals(type)){
            getAllUserList();
        }

        if ("3".equals(type)){
            getContractorList();
        }

        if ("4".equals(type)){
            getContractorList();
        }
    }


    private void getContractorList() {
        RetrofitUtil.getApi().getContractorList()
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<ContractorListBean>>(mContext,true,"正在获取数据"){

                    @Override
                    public void onSuccess(List<ContractorListBean> bean) {
                        if (bean.size()>0){
                            listBeans.addAll(bean);
                            if ("3".equals(type)){
                                setContractorValue();
                            }else if("4".equals(type)){
                                setWorkPeople();
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext,err);
                    }
                });
    }

    private void setWorkPeople() {
        WorkPeopleAdapter contractorAdapter = new WorkPeopleAdapter(listBeans, BR.work_people, new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String workPeopleName = listBeans.get(position).getEpName();
                int nameId2 = listBeans.get(position).getId();
                Intent intent = new Intent(mContext,InterimPlanDetailsActivity.class);
                intent.putExtra("workPeopleName",workPeopleName);
                intent.putExtra("nameId2",nameId2);
                setResult(5,intent);
                finish();
            }
        });
        binding.deviceInterimListRecycler.setAdapter(contractorAdapter);
        binding.deviceInterimListRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setContractorValue() {
        ContractorAdapter contractorAdapter = new ContractorAdapter(listBeans, BR.contractorList, new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String contractorName = listBeans.get(position).getName();
                Intent intent = new Intent(mContext,InterimPlanDetailsActivity.class);
                intent.putExtra("contractorName",contractorName);
                setResult(4,intent);
                finish();
            }
        });
        binding.deviceInterimListRecycler.setAdapter(contractorAdapter);
        binding.deviceInterimListRecycler.setLayoutManager(new LinearLayoutManager(this));
    }


    private void getAllUserList() {
        RetrofitUtil.getApi().getUserList()
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<DeviceAnalysisDeviceListBean>>(mContext, true, "正在获取数据"){

                    @Override
                    public void onSuccess(List<DeviceAnalysisDeviceListBean> bean) {
                        if (bean.size()>0){
                            list.addAll(bean);
                            setAllUser();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext,err);
                    }
                });
    }

    private void setAllUser() {
        ProjectPersonAdapter adapter = new ProjectPersonAdapter(list, BR.userList, new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String name = list.get(position).getLastName()+list.get(position).getFirstName();
                int nameId = list.get(position).getId();
                Intent intent = new Intent(mContext,InterimPlanDetailsActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("nameId",nameId);
                setResult(3,intent);
                finish();
            }
        });
        binding.deviceInterimListRecycler.setAdapter(adapter);
        binding.deviceInterimListRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
