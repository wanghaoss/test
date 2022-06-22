package com.example.new_androidclient.hazard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Base.BaseAdapter;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityHazardAnalysisDeviceSelectBinding;
import com.example.new_androidclient.hazard.adapter.HazardAnalysisDeviceListAdapter;
import com.example.new_androidclient.hazard.bean.HazardAnalysisDeviceListBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;

@Route(path = RouteString.HazardAnalysisDeviceSelectActivity)
public class HazardAnalysisDeviceSelectActivity extends BaseActivity {
    private ActivityHazardAnalysisDeviceSelectBinding binding;
    private Listener listener = new Listener();
    private HazardAnalysisDeviceListAdapter adapter;
    private List<HazardAnalysisDeviceListBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hazard_analysis_device_select);
        binding.setListener(listener);
        adapter = new HazardAnalysisDeviceListAdapter(list, BR.hazard_analysis_device_list_item, new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("deviceId", String.valueOf(list.get(position).getDevId()));
                intent.putExtra("name", list.get(position).getDeviceName());
                setResult(1, intent);
                finish();
            }
        });
        binding.hazardAnalysisSelectDeviceRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.hazardAnalysisSelectDeviceRecycler.setAdapter(adapter);
    }
    private void findDeviceList(String name, String tagNo){
        RetrofitUtil.getApi().findDeviceList(1, 100, name, tagNo)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardAnalysisDeviceListBean>>(mContext, true, "正在搜索") {

                    @Override
                    public void onSuccess(List<HazardAnalysisDeviceListBean> bean) {
                        hideSoftKeyboard(HazardAnalysisDeviceSelectActivity.this);
                        if (bean.size() > 0) {
                            list.clear();
                            list.addAll(bean);
                            binding.hazardAnalysisSelectDeviceRecycler.setVisibility(View.VISIBLE);
                            binding.hazardAnalysisSelectDeviceNodata.setVisibility(View.GONE);
                        } else {
                            binding.hazardAnalysisSelectDeviceRecycler.setVisibility(View.GONE);
                            binding.hazardAnalysisSelectDeviceNodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    public class Listener {
        public void selectDevice() {
            String name = binding.hazardAnalysisSelectDeviceName.getText().toString().trim();
            String tagNo = binding.hazardAnalysisSelectDeviceTagNo.getText().toString().trim();
            if(TextUtils.isEmpty(name)){
                if((TextUtils.isEmpty(tagNo))){
                    ToastUtil.show(mContext, "请至少填写一项查询条件");
                }else{
                    findDeviceList(name, tagNo);
                }
            }else{
                findDeviceList(name, tagNo);
            }
        }
    }
    public void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
