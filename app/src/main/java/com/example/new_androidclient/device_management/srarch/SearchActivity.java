package com.example.new_androidclient.device_management.srarch;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.device_management.Data.CategoryDialog;
import com.example.new_androidclient.device_management.Data.SearchDialog;
import com.example.new_androidclient.device_management.bean.CategoryBean;
import com.example.new_androidclient.device_management.bean.FindDeviceListBean;
import com.example.new_androidclient.device_management.bean.SearchBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = RouteString.SearchActivity)
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.spinner1)
    TextView spinner1;
    @BindView(R.id.spinner2)
    TextView spinner2;
    @BindView(R.id.spinner3)
    TextView spinner3;
    @BindView(R.id.spinner4)
    TextView spinner4;
    @BindView(R.id.spinner5)
    TextView spinner5;
    @BindView(R.id.agree)
    Button agree;
    @BindView(R.id.spinner1Layout)
    LinearLayout spinner1Layout;
    @BindView(R.id.spinner2Layout)
    LinearLayout spinner2Layout;
    @BindView(R.id.spinner3Layout)
    LinearLayout spinner3Layout;
    @BindView(R.id.spinner4Layout)
    LinearLayout spinner4Layout;
    @BindView(R.id.spinner5Layout)
    LinearLayout spinner5Layout;
    @BindView(R.id.deviceName)
    EditText deviceNameValue;
    @BindView(R.id.tagNo)
    EditText tagNoValue;
    @BindView(R.id.deviceNo)
    EditText deviceNoValue;

    List<SearchBean> searchBeans = new ArrayList<>();
    //分厂
    List<SearchBean> branchName = new ArrayList<>();
    //车间
    List<SearchBean> workshopName = new ArrayList<>();
    //装置
    List<SearchBean> deviceName = new ArrayList<>();

    private int positionsOne;
    private int positionsTwo;
    private Integer factoryId;     //所属分厂Id
    private Integer dptId;         //所属车间Id
    private Integer instId;        //所属装置Id
    String professionalCat;
    String deviceCatExt;
    String deviceNamePrice;
    String tagNo;
    String deviceNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        selectOrganizationTree();

        setNameSize();
    }

    private void setNameSize() {
        spinner1Layout.setOnClickListener(this);
        spinner2Layout.setOnClickListener(this);
        spinner3Layout.setOnClickListener(this);
        spinner4Layout.setOnClickListener(this);
        spinner5Layout.setOnClickListener(this);

        agree.setOnClickListener(this);

        spinner1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!spinner2.getText().equals("请选择所属车间")) {
                    spinner2.setText("请选择所属车间");
                }
                if (!spinner3.getText().equals("请选择所属装置")) {
                    spinner3.setText("请选择所属装置");
                }
            }
        });
        spinner2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!spinner3.getText().equals("请选择所属装置")) {
                    spinner3.setText("请选择所属装置");
                }
            }
        });
    }

    /**
     * 分厂车间查询
     **/
    public void selectOrganizationTree() {
        RetrofitUtil.getApi().selectOrganizationTree()
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<SearchBean>>(mContext, true, "正在获取数据") {
                    @Override
                    public void onSuccess(List<SearchBean> bean) {
                        if (bean != null) {
                            searchBeans = bean;
//                            obtain(searchBeans);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    /**
     * 类别查询
     **/
    public void getDictByType(String type) {
        RetrofitUtil.getApi().getDictByType(type)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<CategoryBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<CategoryBean> bean) {
                        if (bean != null) {
                            if (type.equals("deviceZy")) {
                                setCategoryShow("1", bean);
                            }
                            if (type.equals("deviceKz")) {
                                setExtendShow("2", bean);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //扩展类别Dialog
    private void setExtendShow(String type, List<CategoryBean> bean) {
        new CategoryDialog(mContext, bean, type, new CategoryDialog.OnCloseListener() {
            @Override
            public void onBottomClick(String value, Dialog dialog) {
                spinner5.setText(value);
                dialog.dismiss();
            }
        }).show();
    }

    //专业类别Dialog
    private void setCategoryShow(String type, List<CategoryBean> categoryBeanList) {
        new CategoryDialog(mContext, categoryBeanList, type, new CategoryDialog.OnCloseListener() {
            @Override
            public void onBottomClick(String value, Dialog dialog) {
                spinner4.setText(value);
                dialog.dismiss();
            }
        }).show();
    }

    //设置车间，装置的List值
    public void obtain(String type, List<SearchBean> bean) {

        if (type.equals("2")) {
            workshopName.clear();
            if (null != bean.get(positionsOne).getChildren()) {
                workshopName.addAll(bean.get(positionsOne).getChildren());
            }
        }
        if (type.equals("3")) {
            deviceName.clear();
            if (null != bean.get(positionsOne).getChildren().get(positionsTwo).getChildren()) {
                deviceName.addAll(bean.get(positionsOne).getChildren().get(positionsTwo).getChildren());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.spinner1Layout:
                getShow("1", searchBeans);
                break;
            case R.id.spinner2Layout:
                if (!spinner1.getText().toString().equals("请选择所属分厂") && !TextUtils.isEmpty(spinner1.toString())) {
                    obtain("2", searchBeans);
                    getShow("2", workshopName);
                } else {
                    ToastUtil.show(mContext, "请先选择所属分厂");
                }
                break;
            case R.id.spinner3Layout:
                if (!spinner2.getText().toString().equals("请选择所属车间") && !TextUtils.isEmpty(spinner2.toString())) {
                    obtain("3", searchBeans);
                    getShow("3", deviceName);
                } else {
                    ToastUtil.show(mContext, "请先选择所属车间");
                }
                break;
            case R.id.spinner4Layout:
                getDictByType("deviceZy");
                break;
            case R.id.spinner5Layout:
                getDictByType("deviceKz");
                break;
            case R.id.agree:
                if (!("请选择所属分厂").equals(spinner1.getText().toString()) || !("请选择专业类别").equals(spinner4.getText().toString())
                        || !("请选择扩展类别").equals(spinner5.getText().toString()) || !("").equals(deviceNameValue.getText().toString()) ||
                        !("").equals(deviceNoValue.getText().toString()) || !("").equals(tagNoValue.getText().toString())) {
                    getFindDeviceList();
                } else {
                    ToastUtil.show(mContext, "必须添加一项搜索条件");
                }
                break;
        }
    }

    //获取设备列表

    /**
     * "page"
     * "size"
     * "deviceName"  设备名称
     * "tagNo"       设备位号
     * "deviceNo"    设备编码
     * "factoryId"   所属分厂Id
     * "dptId"       所属车间Id
     * "instId"      所属装置Id
     * "professionalCat"  专业类别
     * "deviceCatExt"     扩展类别
     **/
    private void getFindDeviceList() {
        if (spinner4.getText().equals("请选择专业类别") || TextUtils.isEmpty(spinner4.getText())) {
            professionalCat = "null";
        } else {
            professionalCat = spinner4.getText().toString();
        }

        if (spinner5.getText().equals("请选择扩展类别") || TextUtils.isEmpty(spinner5.getText())) {
            deviceCatExt = "null";
        } else {
            deviceCatExt = spinner5.getText().toString();
        }

        if (deviceNameValue.getText().equals("") || TextUtils.isEmpty(deviceNameValue.getText())) {
            deviceNamePrice = "null";
        } else {
            deviceNamePrice = deviceNameValue.getText().toString();
        }

        if (tagNoValue.getText().equals("") || TextUtils.isEmpty(tagNoValue.getText())) {
            tagNo = "null";
        } else {
            tagNo = tagNoValue.getText().toString();

        }

        if (deviceNoValue.getText().equals("") || TextUtils.isEmpty(deviceNoValue.getText())) {
            deviceNo = "null";
        } else {
            deviceNo = deviceNoValue.getText().toString();
        }


        RetrofitUtil.getApi().getFindDeviceList(1, 1000, deviceNamePrice, deviceNo, tagNo, factoryId
                , dptId, instId, professionalCat, deviceCatExt, null, null)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<FindDeviceListBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<FindDeviceListBean> bean) {
                        if (bean != null) {
                            Intent intent = new Intent(mContext, DeviceInfoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("FindDeviceList", (Serializable) bean);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //设置分厂，车间，装置的Dialog
    private void getShow(String type, List<SearchBean> list) {
        new SearchDialog(mContext, type, list, new SearchDialog.OnCloseListener() {

            @Override
            public void onBottomClick(int nameId, String value, String type, int position, Dialog dialog) {
                if (type != null) {
                    if (type.equals("1")) {
                        spinner1.setText(value);
                        positionsOne = position;
                        if (spinner1.getText().equals("请选择所属分厂") || TextUtils.isEmpty(spinner1.getText())) {
                            factoryId = null;
                        } else {
                            factoryId = nameId;
                        }
                        dialog.dismiss();
                    }
                    if (type.equals("2")) {
                        spinner2.setText(value);
                        positionsTwo = position;
                        if (spinner2.getText().equals("请选择所属车间") || TextUtils.isEmpty(spinner2.getText())) {
                            dptId = null;
                        } else {
                            dptId = nameId;
                        }
                        dialog.dismiss();
                    }
                    if (type.equals("3")) {
                        spinner3.setText(value);
                        if (spinner3.getText().equals("请选择所属装置") || TextUtils.isEmpty(spinner3.getText())) {
                            instId = null;
                        } else {
                            instId = nameId;
                        }
                        dialog.dismiss();
                    }
                }
            }
        }).show();
    }
}
