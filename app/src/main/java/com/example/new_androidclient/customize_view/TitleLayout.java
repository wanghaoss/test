package com.example.new_androidclient.customize_view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.Other.SPString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.inspection.InspectionDeviceListActivity;
import com.example.new_androidclient.wait_to_handle.WaitInspectionListActivity;

import static com.example.new_androidclient.Other.SPString.RecordUserName_token;
import static com.example.new_androidclient.Other.SPString.RecordUserPwd_token;

public class TitleLayout extends RelativeLayout {
    TextView name;
    TextView blueText;
    LinearLayout linearLayout_back;
    LinearLayout linearLayout_more;
    LinearLayout linearLayout_bluetooth;
    LinearLayout linearLayout_logout;

    LinearLayout linearLayout_search;

    LinearLayout linearLayout_hazard_plan_upload;
    LinearLayout hazard_plan_take_pic;

    LinearLayout edition_notice;

    LinearLayout linearLayout_work_switch;

    Context context;

    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);
        name = findViewById(R.id.toolbar_name);
        blueText = findViewById(R.id.bluetooth_text);
        linearLayout_back = findViewById(R.id.back);
        linearLayout_more = findViewById(R.id.more);
        linearLayout_bluetooth = findViewById(R.id.bluetooth);
        linearLayout_logout = findViewById(R.id.logout_linear);

        linearLayout_search = findViewById(R.id.device_management_tack_pic);

        linearLayout_hazard_plan_upload = findViewById(R.id.hazard_plan_upload_linear);
        hazard_plan_take_pic = findViewById(R.id.hazard_plan_take_pic);

        edition_notice = findViewById(R.id.edition_notice);

        linearLayout_work_switch = findViewById(R.id.work_switch_layout);

        init(context, attrs);
        this.context = context;
    }

    public LinearLayout getLinearLayout_back() {
        return linearLayout_back;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setBlueToothText(String str) {
        this.blueText.setText(str);
    }

    public String getBlueToothText() {
        return this.blueText.getText().toString();
    }

    public LinearLayout getHazard_plan_take_pic() {
        return hazard_plan_take_pic;
    }

    public LinearLayout getLinearLayout_hazard_plan_upload() {
        return linearLayout_hazard_plan_upload;
    }
    public LinearLayout getLinearLayout_work_switch(){
        return linearLayout_work_switch;
    }

    public LinearLayout getLinearLayout_more() {
        return linearLayout_more;
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TitleLayout);
        if (typedArray != null) {
            String name = typedArray.getString(R.styleable.TitleLayout_name); //中间标题
            boolean showMorePic = typedArray.getBoolean(R.styleable.TitleLayout_showMorePic, false);//右侧三个点，巡检模块功能,
            int getModule = typedArray.getInt(R.styleable.TitleLayout_moduleName, 0);//根据模块判断三个点显示menu内容
            boolean showBlueTooth = typedArray.getBoolean(R.styleable.TitleLayout_showBlueTooth, false);//巡检模块显示蓝牙状态
            boolean showLogOut = typedArray.getBoolean(R.styleable.TitleLayout_showLogout, false);//巡检模块显示退出登录
            boolean search = typedArray.getBoolean(R.styleable.TitleLayout_search, false);//设备模块 搜索功能
            boolean showHazardPic = typedArray.getBoolean(R.styleable.TitleLayout_showHazardPic, false);//隐患模块显示拍照按钮
            boolean showHazardUploadFile = typedArray.getBoolean(R.styleable.TitleLayout_showHazardUploadFile, false);//隐患模块计划编审上传文件
            boolean showEditionAddNotice = typedArray.getBoolean(R.styleable.TitleLayout_showEditionAddNotice, false);//接收通知与版本更新
            boolean showWorkSwitch = typedArray.getBoolean(R.styleable.TitleLayout_work_switch,false);//作业模块，踏勘切换
            if (name != null) {  //中间标题
                this.name.setText(name);
            }

            if (showMorePic) {  //右侧三个点，巡检模块功能
                linearLayout_more.setVisibility(VISIBLE);
            } else {
                linearLayout_more.setVisibility(GONE);
            }

            if (showBlueTooth) {  //巡检模块显示蓝牙状态
                linearLayout_bluetooth.setVisibility(VISIBLE);
            } else {
                linearLayout_bluetooth.setVisibility(GONE);
            }

            if (showLogOut) {
                linearLayout_logout.setVisibility(VISIBLE);
            } else {
                linearLayout_logout.setVisibility(GONE);
            }
            if (showHazardUploadFile) {
                linearLayout_hazard_plan_upload.setVisibility(VISIBLE);
            } else {
                linearLayout_hazard_plan_upload.setVisibility(GONE);
            }
            if (showHazardPic) {
                hazard_plan_take_pic.setVisibility(VISIBLE);
            } else {
                hazard_plan_take_pic.setVisibility(GONE);
            }

            if (search) {
                linearLayout_search.setVisibility(VISIBLE);
            } else {
                linearLayout_search.setVisibility(GONE);
            }

            if (showEditionAddNotice) {
                edition_notice.setVisibility(VISIBLE);
            } else {
                edition_notice.setVisibility(GONE);
            }

            if (showWorkSwitch){
                linearLayout_work_switch.setVisibility(VISIBLE);
            }else {
                linearLayout_work_switch.setVisibility(GONE);
            }

            if (getModule > 0) {  //根据模块判断三个点显示menu内容
                switch (getModule) {
                    case 1:  //巡检模块
                    //    linearLayout_more.setOnClickListener(inspectionListener);
                        linearLayout_bluetooth.setOnClickListener(blueToothListener);
                        break;
                    case 3:
                        linearLayout_logout.setOnClickListener(mainListener);
                        break;
//                    case 5:  //隐患整改治理防范措施、整改方案上传文件，为了回现，把监听放在了HazardPlanActivity
//                        linearLayout_hazard_plan_upload.setOnClickListener(hazardPlanUploadFileListener);
//                        break;
                }
            }
        }
        linearLayout_back.setOnClickListener(v -> ((Activity) getContext()).finish());
        linearLayout_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        edition_notice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(RouteString.EditionAndPushActivity).navigation();
            }
        });
    }


//    View.OnClickListener inspectionListener = v -> {
//        PopupMenu popupMenu = new PopupMenu(getContext(), v);
//        popupMenu.getMenuInflater().inflate(R.menu.inspection_option, popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(item -> {
//            switch (item.getItemId()) {
//                /**
//                 * 已经在菜单里把除了终止之外的项注释了，需要修改下面taskId的传值
//                 */
//                case R.id.inspection_stop:
//                    if (taskId == 0) {
//                        ToastUtil.show(getContext(), "数值传递错误，请重新进入巡检模块");
//                    } else {
//                        ARouter.getInstance().build(RouteString.InspectionStopActivity)
//                                .withInt("taskId", taskId)
//                                .navigation();
//                        //没改完
//                    }
//                    return true;
////                case R.id.inspection_problem:
////                    ARouter.getInstance().build(RouteString.Inspection24ProblemActivity).navigation();
////                    return true;
////                case R.id.inspection_route:
////                    ARouter.getInstance().build(RouteString.InspectionAreaListActivity).navigation();
////                    return true;
////                case R.id.inspection_menu_area_rough:
////                    ARouter.getInstance().build(RouteString.AreaDistinguishActivity)
////                            .withBoolean("isRough", true).navigation();
////                    return true;
//            }
//            return true;
//        });
//        popupMenu.show();
//    };

    View.OnClickListener blueToothListener = v -> {
        //      ToastUtil.show(getContext(), "sss");
    };

    View.OnClickListener mainListener = v -> {
        SPUtil.putData(SPString.Token, "");
        SPUtil.putData(RecordUserPwd_token, "");
        SPUtil.putData(RecordUserName_token, "");
        ARouter.getInstance().build(RouteString.LoginActivity).navigation(context, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                ((Activity) getContext()).finish();
            }
        });
    };

//隐患整改治理防范措施、整改方案上传文件，为了回现，把监听放在了HazardPlanActivity
//    View.OnClickListener hazardPlanUploadFileListener = v -> {
//        ARouter.getInstance().build(RouteString.HazardPlanUploadFileActivity)
//                .navigation((Activity) context,3);
//    };
}
