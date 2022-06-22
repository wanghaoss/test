package com.example.new_androidclient.hazard;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.DataConverterUtil;
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.customize_view.HazardItemLayout2;
import com.example.new_androidclient.databinding.ActivityHazardAnalysisNewBinding;
import com.example.new_androidclient.hazard.bean.HazardAnalysisBean;
import com.example.new_androidclient.hazard.bean.HazardAnalysisUploadBean;
import com.example.new_androidclient.hazard.bean.ItemBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.POST;

import static com.example.new_androidclient.R2.id.submit;

/**
 * 新版隐患分析, 本页面逻辑处理lec法和人工填写
 */
@Route(path = RouteString.HazardAnalysisNewActivity)
public class HazardAnalysisNewActivity extends BaseActivity {
    private ActivityHazardAnalysisNewBinding binding;
    private Listener listener = new Listener();
    private List<HazardAnalysisBean> allCheckList = new ArrayList<>();
    private List<ItemBean> nameBeanList = new ArrayList<>(); //存储表头+类型
    private List<HazardItemLayout2> itemLayoutList = new ArrayList<>();
    private HazardAnalysisBean currentBean = null;
    private HazardAnalysisUploadBean uploadBean; //最后上传的实体类 转成map， 和HazardAnalysisBean少了许多的字段

    private AlertDialog dialog;

    private int type_0_lec = 0;
    private int type_1_person = 1;
    private int type_2_20 = 2;
    private int analysisType = type_0_lec; //0lec 1人工 220条


    private float L, E, C, D = 0;

    private final int Intent_code_device = 1;
    private final int Intent_code_person = 2;
    private final int Intent_code_tree = 3;

    String[] nameList_lec = {"隐患名称", //0
            "隐患描述",//1
            "L分数值", //2
            "E分数值", //3
            "C分数值",//4
            "D风险值",//5
            "隐患类别", //6
            "一般隐患等级", //7
            "隐患因素",//8
            "关联设备", //9
            "是否上报",//10
            "归属单位", //11
            "分析负责人", //12
            "分析日期"};//13

    String[] nameList_person = {"隐患名称", //0
            "隐患描述",//1
            "隐患类别", //2
            "一般隐患等级", //3
            "隐患因素",//4
            "关联设备",//5
            "是否上报",//6
            "归属单位", //7
            "分析负责人",//8
            "分析日期"}; //9

    //                    0     2     4     6     8     10    12
    int[] typeList_lec = {1, 1, 2, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 1};

    //                       0     2     4     6     8
    int[] typeList_person = {1, 1, 2, 2, 2, 2, 2, 2, 2, 1};

    //                   0     2     4     6     8
    int[] typeList_20 = {1, 1, 1, 1, 2, 2, 2, 2, 2, 1};

    private int currentPosition;  //从列表页跳转进本页面时，当前页面里，显示第current条数据
    private int pre = 0;  //例如当前pre=current=5，往前翻一页， pre=4， 往后翻一页，pre=5
    private String UP = "up";//用户点击上一条
    private String DOWN = "down"; //用户点击下一条
    private String FINISH = "finish"; //用户点击完成巡检
    private String turnPage = DOWN; //用户点击了上一条按钮还是下一条按钮
    private String AGREE = "agree"; //审核同意
    private String UNAGREE = "unagree"; //审核不同意

    private int signFlag = 0;
    private String msg = "";

    @Autowired()
    int planId;

    //是否是分析审核审批 true审核审批页面，审核审批也可以修改数据 在最后一个隐患页面按钮显示 上一条+同意+不同意
    // false 填写分析页面 在最后一个隐患页面按钮显示 上一条+完成分析
    @Autowired()
    boolean isSign = false;  //默认是分析

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hazard_analysis_new);
        binding.setListener(listener);
        getList(true);
    }

    private void getList(boolean isFirst) {  //true找到最新一个没完成的项，false翻页
        RetrofitUtil.getApi().selectAnalysisList(planId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardAnalysisBean>>(mContext, true, "正在获取数据") {

                    @Override
                    public void onSuccess(List<HazardAnalysisBean> bean) {
                        if (bean.size() == 0) {
                            ToastUtil.show(mContext, "数据为空");
                            finish();
                        }
                        if (isFirst) {    //true找到最新一个没完成的项，false翻页
                            if (bean.size() != 0) {
                                allCheckList.clear();
                                allCheckList.addAll(bean);
                                if (isSign) { //审核审批 pre应该是第一个 current应该是最后一个
                                    pre = 0;
                                    currentPosition = allCheckList.size() - 1;
                                } else {
                                    for (int i = 0; i < allCheckList.size(); i++) {
                                        if (allCheckList.get(i).getHazardCategory().isEmpty()) { //如果类别为空，则说明未填写
                                            currentPosition = pre = i;
                                            break;
                                        }
                                        if (i == allCheckList.size() - 1) {
                                            currentPosition = pre = i;
                                        }
                                    }
                                }
                                initList(pre);
                            }
                        } else {
                            if (bean.size() != 0) {
                                allCheckList.clear();
                                allCheckList.addAll(bean);
                            }
                            initList(pre);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void initList(int pre) {
        initView(pre);

        if (allCheckList.get(pre).getHazardCategory().isEmpty() || null == allCheckList.get(pre).getHazardCategory()) {
            uploadBean = new HazardAnalysisUploadBean();
        } else {
            uploadBean = new HazardAnalysisUploadBean();
            HazardAnalysisBean bean = allCheckList.get(pre);
            uploadBean.setRiskL(bean.getRiskL());
            uploadBean.setRiskE(bean.getRiskE());
            uploadBean.setRiskC(bean.getRiskC());
            uploadBean.setRiskD(bean.getRiskD());
            uploadBean.setHazardCategory(bean.getHazardCategory());
            uploadBean.setHazardGrade(bean.getHazardGrade());
            uploadBean.setHazardFactor(bean.getHazardFactor());
            uploadBean.setIsReported(bean.getIsReported());
            uploadBean.setBelongDept(bean.getBelongDept());
            uploadBean.setAnalysisPerson(bean.getAnalysisPerson());
            uploadBean.setAnalysisTime(bean.getAnalysisTime());

            if (bean.getRiskL().isEmpty()) {
                L = 0;
                E = 0;
                C = 0;
                D = 0;
            } else {
                L = Float.parseFloat(bean.getRiskL());
                E = Float.parseFloat(bean.getRiskE());
                C = Float.parseFloat(bean.getRiskC());
                D = Float.parseFloat(bean.getRiskD());
            }

        }

        uploadBean.setId(allCheckList.get(pre).getId());

        String num = "(" + (pre + 1) + "/" + allCheckList.size() + ")";
        binding.title.setName("隐患分析" + num);

        analysisType = checkType(pre);


        if (analysisType == type_2_20) {
            binding.checkLayout.setVisibility(View.GONE);
            addView(type_2_20);
        } else if (analysisType == type_1_person) {
            setCheckBox(type_1_person);
        } else {
            setCheckBox(type_0_lec);
        }

        binding.checkLec.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                addView(type_0_lec);
                setCheckBox(type_0_lec);
                analysisType = type_0_lec;
            }
        });
        binding.checkPerson.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                addView(type_1_person);
                setCheckBox(type_1_person);
                analysisType = type_1_person;
            }
        });
    }

    private void setCheckBox(int check) {
        if (check == type_1_person) {
            binding.checkLayout.setVisibility(View.VISIBLE);
            binding.checkPerson.setChecked(true);
            binding.checkLec.setChecked(false);
            addView(type_1_person);
        } else if (check == type_0_lec) {
            binding.checkLayout.setVisibility(View.VISIBLE);
            binding.checkPerson.setChecked(false);
            binding.checkLec.setChecked(true);
            addView(type_0_lec);
        }
    }

    private void addView(int type) {
        binding.hazardAnalysisLinear.removeAllViews();
        itemLayoutList.clear();

        if (type == type_0_lec) {  //lec
            for (int i = 0; i < nameList_lec.length; i++) {
                addLecItemView(nameList_lec[i], typeList_lec[i], i);
            }
        } else if (type == type_1_person) {  //person
            for (int i = 0; i < nameList_person.length; i++) {
                addPersonItemView(nameList_person[i], typeList_person[i], i);
            }
        } else {  //20
            for (int i = 0; i < nameList_person.length; i++) {
                add20ItemView(nameList_person[i], typeList_20[i], i);
            }
        }
    }

    private void setPersonData(int position, HazardItemLayout2 layout) {
        if (position == 0) layout.setHazardName_one_text(currentBean.getHazardName());
        else if (position == 1) layout.setHazardName_one_text(currentBean.getHazardContent());
        else if (position == 2) layout.setHazardName_one_text(currentBean.getHazardCategoryName());
        else if (position == 3) layout.setHazardName_one_text(currentBean.getHazardGradeName());
        else if (position == 4) layout.setHazardName_one_text(currentBean.getHazardFactorName());
        else if (position == 5) layout.setHazardName_one_text(currentBean.getDeviceName());
        else if (position == 6) layout.setHazardName_one_text(currentBean.getIsReportedName());
        else if (position == 7) layout.setHazardName_one_text(currentBean.getBelongDeptName());
        else if (position == 8) layout.setHazardName_one_text(currentBean.getAnalysisPersonName());
        else if (position == 9) {
            if (currentBean.getAnalysisTime().isEmpty()) {
                layout.setVisibility(View.GONE);
            } else {
                layout.setVisibility(View.VISIBLE);
                String time = currentBean.getAnalysisTime();
                if (time.length() > 9) {
                    time = time.substring(0, 10);
                }
                layout.setHazardName_one_text(time);
            }
        }
    }

    private void set20Data(int position, HazardItemLayout2 layout) {
        if (position == 0) layout.setHazardName_one_text(currentBean.getHazardName());
        else if (position == 1) layout.setHazardName_one_text(currentBean.getHazardContent());
        else if (position == 2) {
            layout.setHazardName_one_text("重大隐患");
            uploadBean.setHazardCategory("11");
        } else if (position == 3) layout.setHazardName_one_text("");
        else if (position == 4) layout.setHazardName_one_text(currentBean.getHazardFactorName());
        else if (position == 5) layout.setHazardName_one_text(currentBean.getDeviceName());
        else if (position == 6) layout.setHazardName_one_text(currentBean.getIsReportedName());
        else if (position == 7) layout.setHazardName_one_text(currentBean.getBelongDeptName());
        else if (position == 8) layout.setHazardName_one_text(currentBean.getAnalysisPersonName());
        else if (position == 9) {
            if (currentBean.getAnalysisTime().isEmpty()) {
                layout.setVisibility(View.GONE);
            } else {
                layout.setVisibility(View.VISIBLE);
                String time = currentBean.getAnalysisTime();
                if (time.length() > 9) {
                    time = time.substring(0, 10);
                }
                layout.setHazardName_one_text(time);
            }
        }
    }

    private void setLecData(int position, HazardItemLayout2 layout) {
        if (position == 0) layout.setHazardName_one_text(currentBean.getHazardName());
        else if (position == 1) layout.setHazardName_one_text(currentBean.getHazardContent());
        else if (position == 5) layout.setHazardName_one_text(currentBean.getRiskD());
        else if (position == 6) layout.setHazardName_one_text(currentBean.getHazardCategoryName());
        else if (position == 7) layout.setHazardName_one_text(currentBean.getHazardGradeName());
        else if (position == 2) layout.setHazardName_one_text(currentBean.getRisklName());
        else if (position == 3) layout.setHazardName_one_text(currentBean.getRiskeName());
        else if (position == 4) layout.setHazardName_one_text(currentBean.getRiskcName());
        else if (position == 8) layout.setHazardName_one_text(currentBean.getHazardFactorName());
        else if (position == 9) layout.setHazardName_one_text(currentBean.getDeviceName());
        else if (position == 10) layout.setHazardName_one_text(currentBean.getIsReportedName());
        else if (position == 11) layout.setHazardName_one_text(currentBean.getBelongDeptName());
        else if (position == 12) layout.setHazardName_one_text(currentBean.getAnalysisPersonName());
        else if (position == 13) {
            if (currentBean.getAnalysisTime().isEmpty()) {
                layout.setVisibility(View.GONE);
            } else {
                layout.setVisibility(View.VISIBLE);
                String time = currentBean.getAnalysisTime();
                if (time.length() > 9) {
                    time = time.substring(0, 10);
                }
                layout.setHazardName_one_text(time);
            }
        }
    }

    private void addLecItemView(String name, int type, int position) {
        HazardItemLayout2 layout = new HazardItemLayout2(mContext, position);
        layout.init(type);
        layout.setHazardName_one(name);

        setLecData(position, layout);

        if (name.equals("关联设备")) {
            if (allCheckList.get(pre).getHazardFactor().equals("21") || allCheckList.get(pre).getHazardFactor().equals("22")) {
                layout.setVisibility(View.GONE);
            }
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lecItemListener(layout, v);
            }
        });


        itemLayoutList.add(layout);
        binding.hazardAnalysisLinear.addView(layout);
    }

    private void lecItemListener(HazardItemLayout2 layout, View v) {
        switch (layout.getPos()) {
            case 2:
                showPopView(R.menu.hazard_analysis_l, v, layout.getPos()); //L
                break;
            case 3:
                showPopView(R.menu.hazard_analysis_e, v, layout.getPos()); //E
                break;
            case 4:
                showPopView(R.menu.hazard_analysis_c, v, layout.getPos()); //C
                break;
            case 6:
                //       showPopView(R.menu.hazard_analysis_category, v, layout.getPos()); //隐患类别 重大一般
                break;
            case 7:
                //       showPopView(R.menu.hazard_analysis_grade, v, layout.getPos()); //一般隐患等级  企业厂级
                break;
            case 8:
                showPopView(R.menu.hazard_analysis_factor, v, layout.getPos()); //因素
                break;
            case 9:
                ARouter.getInstance().build(RouteString.HazardAnalysisDeviceSelectActivity)
                        .navigation(HazardAnalysisNewActivity.this, Intent_code_device); //关联设备
                break;
            case 10:
                showPopView(R.menu.hazard_analysis_report, v, layout.getPos()); //是否上报
                break;
            case 11:
                ARouter.getInstance().build(RouteString.HazardAnalysisTreeActivity)
                        .navigation(HazardAnalysisNewActivity.this, Intent_code_tree); //归属单位
                break;
            case 12:
                ARouter.getInstance().build(RouteString.HazardAnalysisPersonSelectActivity)
                        .navigation(HazardAnalysisNewActivity.this, Intent_code_person); //分析负责人单位
                break;
        }
    }

    private void personItemListener(HazardItemLayout2 layout, View v) {
        switch (layout.getPos()) {
            case 2:
                showPopView(R.menu.hazard_analysis_category, v, layout.getPos()); //隐患类别 重大一般
                break;
            case 3:
                showPopView(R.menu.hazard_analysis_grade, v, layout.getPos()); //一般隐患等级  企业厂级
                break;
            case 4:
                showPopView(R.menu.hazard_analysis_factor, v, layout.getPos()); //因素
                break;
            case 5:
                ARouter.getInstance().build(RouteString.HazardAnalysisDeviceSelectActivity)
                        .navigation(HazardAnalysisNewActivity.this, Intent_code_device); //关联设备
                break;
            case 6:
                showPopView(R.menu.hazard_analysis_report, v, layout.getPos()); //是否上报
                break;
            case 7:
                ARouter.getInstance().build(RouteString.HazardAnalysisTreeActivity)
                        .navigation(HazardAnalysisNewActivity.this, Intent_code_tree); //归属单位
                break;
            case 8:
                ARouter.getInstance().build(RouteString.HazardAnalysisPersonSelectActivity)
                        .navigation(HazardAnalysisNewActivity.this, Intent_code_person); //分析负责人单位
                break;
        }
    }

    private void greatItemListener(HazardItemLayout2 layout, View v) {
        switch (layout.getPos()) {
            case 4:
                showPopView(R.menu.hazard_analysis_factor, v, layout.getPos()); //因素
                break;
            case 5:
                ARouter.getInstance().build(RouteString.HazardAnalysisDeviceSelectActivity)
                        .navigation(HazardAnalysisNewActivity.this, Intent_code_device); //关联设备
                break;
            case 6:
                showPopView(R.menu.hazard_analysis_report, v, layout.getPos()); //是否上报
                break;
            case 7:
                ARouter.getInstance().build(RouteString.HazardAnalysisTreeActivity)
                        .navigation(HazardAnalysisNewActivity.this, Intent_code_tree); //归属单位
                break;
            case 8:
                ARouter.getInstance().build(RouteString.HazardAnalysisPersonSelectActivity)
                        .navigation(HazardAnalysisNewActivity.this, Intent_code_person); //分析负责人单位
                break;
        }
    }

    private void addPersonItemView(String name, int type, int position) {
        HazardItemLayout2 layout = new HazardItemLayout2(mContext, position);
        layout.init(type);
        layout.setHazardName_one(name);

        setPersonData(position, layout);

        if (name.equals("关联设备")) {
            if (allCheckList.get(pre).getHazardFactor().equals("21") || allCheckList.get(pre).getHazardFactor().equals("22")) {
                layout.setVisibility(View.GONE);
            }
        }

        layout.setOnClickListener(v -> personItemListener(layout, v));

        itemLayoutList.add(layout);
        binding.hazardAnalysisLinear.addView(layout);
    }

    private void add20ItemView(String name, int type, int position) {
        HazardItemLayout2 layout = new HazardItemLayout2(mContext, position);
        layout.init(type);
        layout.setHazardName_one(name);

        set20Data(position, layout);

        if (name.equals("关联设备")) {
            if (allCheckList.get(pre).getHazardFactor().equals("21") || allCheckList.get(pre).getHazardFactor().equals("22")) {
                layout.setVisibility(View.GONE);
            }
        }

        layout.setOnClickListener(v -> greatItemListener(layout, v));

        itemLayoutList.add(layout);
        binding.hazardAnalysisLinear.addView(layout);
    }

    private void showPopView(int menuRes, View v, int position) {
        PopupMenu popupMenu = new PopupMenu(HazardAnalysisNewActivity.this, v, Gravity.RIGHT);
        popupMenu.getMenuInflater().inflate(menuRes, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.hazard_analysis_category_great: //重大隐患
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_category_great));
                    uploadBean.setHazardCategory("11");
                    break;
                case R.id.hazard_analysis_category_normal:  //一般隐患
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_category_normal));
                    uploadBean.setHazardCategory("10");
                    break;
                case R.id.analysis_grade_banzu:  //班组
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_grade_banzu));
                    uploadBean.setHazardGrade("0");
                    break;
                case R.id.analysis_grade_chejian:   //车间
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_grade_chejian));
                    uploadBean.setHazardGrade("1");
                    break;
                case R.id.analysis_grade_chang: //厂
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_grade_chang));
                    uploadBean.setHazardGrade("2");
                    break;
                case R.id.analysis_grade_qiye:  //企业
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_grade_qiye));
                    uploadBean.setHazardGrade("3");
                    break;
                case R.id.hazard_analysis_l_10:
                    L = 10;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_l_10));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_l_6:
                    L = 6;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_l_6));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_l_3:
                    L = 3;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_l_3));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_l_1:
                    L = 1;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_l_1));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_l_0_5:
                    L = (float) 0.5;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_l_0_5));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_l_0_2:
                    L = (float) 0.2;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_l_0_2));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_l_0_1:
                    L = (float) 0.1;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_l_0_1));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_e_10:
                    E = 10;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_e_10));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_e_6:
                    E = 6;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_e_6));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_e_3:
                    E = 3;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_e_3));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_e_2:
                    E = 2;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_e_2));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_e_1:
                    E = 1;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_e_1));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_e_0_5:
                    E = (float) 0.5;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_e_0_5));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_c_100:
                    C = 100;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_c_100));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_c_40:
                    C = 40;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_c_40));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_c_15:
                    C = 15;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_c_15));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_c_7:
                    C = 7;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_c_7));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_c_3:
                    C = 3;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_c_3));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_c_1:
                    C = 1;
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_c_1));
                    setLec_category_grade();
                    break;
                case R.id.hazard_analysis_factor_wu:
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_factor_wu));
                    if (analysisType == type_0_lec) {
                        itemLayoutList.get(9).setVisibility(View.VISIBLE);
                    } else {
                        itemLayoutList.get(5).setVisibility(View.VISIBLE);
                    }
                    uploadBean.setHazardFactor("20");
                    break;
                case R.id.analysis_factor_ren:
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_factor_ren));
                    if (analysisType == type_0_lec) {
                        itemLayoutList.get(9).setVisibility(View.GONE);
                    } else {
                        itemLayoutList.get(5).setVisibility(View.GONE);
                    }
                    uploadBean.setHazardFactor("22");
                    break;
                case R.id.analysis_factor_an:
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_factor_an));
                    if (analysisType == type_0_lec) {
                        itemLayoutList.get(9).setVisibility(View.GONE);
                    } else {
                        itemLayoutList.get(5).setVisibility(View.GONE);
                    }
                    uploadBean.setHazardFactor("21");
                    break;
                case R.id.hazard_analysis_report_true:
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_true));
                    uploadBean.setIsReported("1");
                    break;
                case R.id.hazard_analysis_report_false:
                    itemLayoutList.get(position).setHazardName_one_text(getResources().getString(R.string.analysis_false));
                    uploadBean.setIsReported("0");
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    private void setLec_category_grade() {
        if (L == 0 || E == 0 || C == 0) {
            return;
        }
        D = L * E * C;
        itemLayoutList.get(5).setHazardName_one_text(String.valueOf(D));
        uploadBean.setRiskL(String.valueOf(L));
        uploadBean.setRiskE(String.valueOf(E));
        uploadBean.setRiskC(String.valueOf(C));
        uploadBean.setRiskD(String.valueOf(D));

        String category = "";
        if (D >= 320) {
            category = "重大隐患";
            uploadBean.setHazardCategory("11");
        } else {
            category = "一般隐患";
            uploadBean.setHazardCategory("10");
        }
        itemLayoutList.get(6).setHazardName_one_text(category);


        String grade = "";
        if (D < 20) {
            grade = "班组级";
            uploadBean.setHazardGrade("0");
        } else if (D < 70 && D >= 20) {
            grade = "车间级";
            uploadBean.setHazardGrade("1");
        } else if (D <= 160 && D >= 70) {
            grade = "厂级";
            uploadBean.setHazardGrade("2");
        } else if (D <= 320 && D > 160) {
            grade = "企业级";
            uploadBean.setHazardGrade("3");
        } else {
            grade = "";
            uploadBean.setHazardGrade("");
        }
        itemLayoutList.get(7).setHazardName_one_text(grade);
    }

    private int checkType(int pre) {
        currentBean = allCheckList.get(pre);
        if (currentBean.getIsMajorAccident().equals("1")) {  //20条
            return type_2_20;
        } else if (currentBean.getRiskL().isEmpty()
            //               && !currentBean.getHazardCategory().isEmpty()
        ) {
            return type_1_person;
        } else {
            return type_0_lec;
        }
    }

    private void initView(int pre) {
        //假设列表有5条数据，下标是0至4
        if (allCheckList.size() == 1) { //只有一条
            binding.pre.setVisibility(View.GONE);
            binding.next.setVisibility(View.GONE);
            binding.submit.setVisibility(View.VISIBLE);
            if (isSign) {  //审核审批
                binding.submit.setVisibility(View.GONE);
                binding.agree.setVisibility(View.VISIBLE);
                binding.unagree.setVisibility(View.VISIBLE);
            }
        } else if (allCheckList.size() > 1) {
            binding.agree.setVisibility(View.GONE); //先设置gone
            binding.unagree.setVisibility(View.GONE); //先设置gone

            if (pre == currentPosition && currentPosition == 0) {   //cur和pre都是0的时候，只显示下按钮
                binding.pre.setVisibility(View.GONE);
                binding.next.setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.GONE);
            } else if (pre < currentPosition && pre == 0) {  //pre小于cur并且页面显示在列表第一项时，没有上按钮
                binding.pre.setVisibility(View.GONE);
                binding.next.setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.GONE);
            } else if (pre < currentPosition && pre > 0 && pre != allCheckList.size() - 1) { //pre处于0和cur之间并且不是列表最后一条时，上下按钮都显示
                binding.pre.setVisibility(View.VISIBLE);
                binding.next.setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.GONE);
            } else if (pre == currentPosition && currentPosition == allCheckList.size() - 1) { //最后一条数据，显示提交按钮
                if (isSign) { //审核审批
                    binding.pre.setVisibility(View.VISIBLE);
                    binding.next.setVisibility(View.GONE);
                    binding.submit.setVisibility(View.GONE);
                    binding.agree.setVisibility(View.VISIBLE);
                    binding.unagree.setVisibility(View.VISIBLE);
                } else { //填写分析
                    binding.pre.setVisibility(View.VISIBLE);
                    binding.next.setVisibility(View.GONE);
                    binding.submit.setVisibility(View.VISIBLE);
                    binding.agree.setVisibility(View.GONE);
                    binding.unagree.setVisibility(View.GONE);
                }
            } else if (pre == currentPosition) { //显示要检查的内容时，上下都有
                binding.pre.setVisibility(View.VISIBLE);
                binding.next.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Intent_code_device) {
            String deviceId = data.getStringExtra("deviceId");
            String name = data.getStringExtra("name");
            uploadBean.setDeviceId(deviceId);
            if (analysisType == type_0_lec) {
                itemLayoutList.get(9).setHazardName_one_text(name);
            } else {
                itemLayoutList.get(5).setHazardName_one_text(name);
            }
        } else if (resultCode == Intent_code_person) {
            String userId = data.getStringExtra("userId");
            String name = data.getStringExtra("name");
            uploadBean.setAnalysisPerson(userId);
            if (analysisType == type_0_lec) {
                itemLayoutList.get(12).setHazardName_one_text(name);
            } else {
                itemLayoutList.get(8).setHazardName_one_text(name);
            }
        } else if (resultCode == Intent_code_tree) {
            int deptId = data.getIntExtra("deptId", 0);
            String name = data.getStringExtra("name");
            uploadBean.setBelongDept(String.valueOf(deptId));
            if (analysisType == type_0_lec) {
                itemLayoutList.get(11).setHazardName_one_text(name);
            } else {
                itemLayoutList.get(7).setHazardName_one_text(name);
            }
        }//person处理选择人设备部门
    }

    private boolean checkAllFinish() {
        if (analysisType == type_0_lec) {
            for (int i = 0; i < itemLayoutList.size(); i++) {
                if (itemLayoutList.get(i).getVisibility() == View.VISIBLE) {
                    if (itemLayoutList.get(i).getHazardName_one_text_str().isEmpty()) {
                        if (itemLayoutList.get(i).getHazardName_one_str().equals("一般隐患等级")) {
                            continue;
                        } else {
                            ToastUtil.show(mContext, "请填写" + itemLayoutList.get(i).getHazardName_one_str());
                            return false;
                        }
                    }
                }
            }
            if (uploadBean.getHazardFactor().equals("21") || uploadBean.getHazardFactor().equals("22")) {
                uploadBean.setDeviceId("");
            }
        } else if (analysisType == type_1_person) {
            for (int i = 0; i < itemLayoutList.size(); i++) {
                if (itemLayoutList.get(i).getVisibility() == View.VISIBLE) {
                    if (itemLayoutList.get(i).getHazardName_one_text_str().isEmpty()) {
                        ToastUtil.show(mContext, "请填写" + itemLayoutList.get(i).getHazardName_one_str());
                        return false;
                    }
                }
            }
            if (uploadBean.getHazardFactor().equals("21") || uploadBean.getHazardFactor().equals("22")) {
                uploadBean.setDeviceId("");
            }
            uploadBean.setRiskL("0");
            uploadBean.setRiskE("0");
            uploadBean.setRiskC("0");
            uploadBean.setRiskD("0");
        } else if (analysisType == type_2_20) {
            for (int i = 0; i < itemLayoutList.size(); i++) {
                if (itemLayoutList.get(i).getVisibility() == View.VISIBLE) {
                    if (itemLayoutList.get(i).getHazardName_one_text_str().isEmpty()) {
                        if (itemLayoutList.get(i).getHazardName_one_str().equals("一般隐患等级")) {
                            continue;
                        } else {
                            ToastUtil.show(mContext, "请填写" + itemLayoutList.get(i).getHazardName_one_str());
                            return false;
                        }
                    }
                }
            }
            if (uploadBean.getHazardFactor().equals("21") || uploadBean.getHazardFactor().equals("22")) {
                uploadBean.setDeviceId("");
            }
        }
        return true;
    }

    private void updateAnalysis() {
        RetrofitUtil.getApi().updateAnalysis(uploadBean)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String bean) {
                        if (allCheckList.size() == 1) {
                            if (turnPage.equals(AGREE) || turnPage.equals(UNAGREE)) {
                                submitSign(msg, signFlag);
                            }else{
                                updateStatus();
                            }
                        } else if (turnPage.equals(FINISH)) {
                            updateStatus();
                        } else if (turnPage.equals(AGREE) || turnPage.equals(UNAGREE)) {
                            submitSign(msg, signFlag);
                        } else {
                            if (turnPage.equals(DOWN)) {
                                if (currentPosition == pre) {
                                    currentPosition++;
                                }
                                pre++;
                            } else if (turnPage.equals(UP)) {
                                pre--;
                            }
                            getList(false);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void updateStatus() {
        RetrofitUtil.getApi().updateAnalysis(Integer.valueOf(planId))
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "修改状态中") {
                    @Override
                    public void onSuccess(String bean) {
                        ToastUtil.show(mContext, bean);
                        finish();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void showDialog() {
        if (turnPage.equals(AGREE)) { //  1通过  0驳回
            signFlag = 1;
        } else {
            signFlag = 0;
        }
        dialog = new AlertDialog.Builder(this).setPositiveButton("提交", null).setNegativeButton("取消", null).create();
        dialog.setTitle("请填写意见");

        Window win = dialog.getWindow();
        win.getDecorView().setPadding(10, 20, 10, 20);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

        View view = getLayoutInflater().inflate(R.layout.hazard_plan_sign_dialog, null);
        final EditText msg_edit = view.findViewById(R.id.hazard_plan_sign_dialog_edittext);
        dialog.setView(view);//设置login_layout为对话提示框
        dialog.setCancelable(false);//设置为不可取消
        dialog.setOnShowListener(mDialog -> {
            Button positionButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            positionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (msg_edit.getText().toString().trim().isEmpty() && signFlag == 0) {
                        ToastUtil.show(mContext, "请填写意见");
                    } else {
                        //submitSign(msg_edit.getText().toString().trim(), flag);
                        msg = msg_edit.getText().toString().trim();

                        listener.submit();
                        dialog.dismiss();
                    }
                }

            });
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        });
        dialog.show();
    }

    private void submitSign(String msg, int flag) {
        RetrofitUtil.getApi().analysisAct(planId, flag, msg)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "数据提交中") {
                    @Override
                    public void onSuccess(String bean) {
                        ToastUtil.show(mContext, bean);
                        finish();
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }


    public class Listener {

        public void pre() {
            turnPage = UP;
            submit();
        }

        public void next() {
            turnPage = DOWN;
            submit();
        }

        public void submitBtn() {
            turnPage = FINISH;
            submit();
        }

        public void submit() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
            Date date = new Date(System.currentTimeMillis());
            uploadBean.setAnalysisTime(simpleDateFormat.format(date));


            if (checkAllFinish()) {
                //        ToastUtil.show(mContext, uploadBean.getAnalysisPerson());
                updateAnalysis();

            }
        }

        public void agree() {
            turnPage = AGREE;
            showDialog();
        }

        public void unagree() {
            turnPage = UNAGREE;
            showDialog();
        }
    }
}
