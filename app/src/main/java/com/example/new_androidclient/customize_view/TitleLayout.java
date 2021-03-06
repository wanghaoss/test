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
            String name = typedArray.getString(R.styleable.TitleLayout_name); //????????????
            boolean showMorePic = typedArray.getBoolean(R.styleable.TitleLayout_showMorePic, false);//????????????????????????????????????,
            int getModule = typedArray.getInt(R.styleable.TitleLayout_moduleName, 0);//?????????????????????????????????menu??????
            boolean showBlueTooth = typedArray.getBoolean(R.styleable.TitleLayout_showBlueTooth, false);//??????????????????????????????
            boolean showLogOut = typedArray.getBoolean(R.styleable.TitleLayout_showLogout, false);//??????????????????????????????
            boolean search = typedArray.getBoolean(R.styleable.TitleLayout_search, false);//???????????? ????????????
            boolean showHazardPic = typedArray.getBoolean(R.styleable.TitleLayout_showHazardPic, false);//??????????????????????????????
            boolean showHazardUploadFile = typedArray.getBoolean(R.styleable.TitleLayout_showHazardUploadFile, false);//????????????????????????????????????
            boolean showEditionAddNotice = typedArray.getBoolean(R.styleable.TitleLayout_showEditionAddNotice, false);//???????????????????????????
            boolean showWorkSwitch = typedArray.getBoolean(R.styleable.TitleLayout_work_switch,false);//???????????????????????????
            if (name != null) {  //????????????
                this.name.setText(name);
            }

            if (showMorePic) {  //????????????????????????????????????
                linearLayout_more.setVisibility(VISIBLE);
            } else {
                linearLayout_more.setVisibility(GONE);
            }

            if (showBlueTooth) {  //??????????????????????????????
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

            if (getModule > 0) {  //?????????????????????????????????menu??????
                switch (getModule) {
                    case 1:  //????????????
                    //    linearLayout_more.setOnClickListener(inspectionListener);
                        linearLayout_bluetooth.setOnClickListener(blueToothListener);
                        break;
                    case 3:
                        linearLayout_logout.setOnClickListener(mainListener);
                        break;
//                    case 5:  //?????????????????????????????????????????????????????????????????????????????????????????????HazardPlanActivity
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
//                 * ???????????????????????????????????????????????????????????????????????????taskId?????????
//                 */
//                case R.id.inspection_stop:
//                    if (taskId == 0) {
//                        ToastUtil.show(getContext(), "????????????????????????????????????????????????");
//                    } else {
//                        ARouter.getInstance().build(RouteString.InspectionStopActivity)
//                                .withInt("taskId", taskId)
//                                .navigation();
//                        //?????????
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

//?????????????????????????????????????????????????????????????????????????????????????????????HazardPlanActivity
//    View.OnClickListener hazardPlanUploadFileListener = v -> {
//        ARouter.getInstance().build(RouteString.HazardPlanUploadFileActivity)
//                .navigation((Activity) context,3);
//    };
}
