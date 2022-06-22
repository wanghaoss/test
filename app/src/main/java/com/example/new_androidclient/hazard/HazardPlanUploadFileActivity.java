package com.example.new_androidclient.hazard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.Other.SPString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.BitmapUtil;
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.PermissionUtil;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityHazardPlanUploadFileBinding;
import com.example.new_androidclient.hazard.adapter.HazardPlanUploadAdapter;
import com.example.new_androidclient.hazard.bean.HazardPlanUploadFileBean;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.new_androidclient.Other.SPString.HazardPlanUploadFileList;

@Route(path = RouteString.HazardPlanUploadFileActivity)
public class HazardPlanUploadFileActivity extends BaseActivity {
    private Listener listener = new Listener();
    private ActivityHazardPlanUploadFileBinding binding;
    private List<HazardPlanUploadFileBean> fileList = new ArrayList<>();
    private HazardPlanUploadAdapter adapter;
    private int type; //1防范措施  2整改治理方案
    private String path;

    private Uri uri;

    private boolean hasPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hazard_plan_upload_file);
        binding.setListener(listener);
        //从HazardPlanActivity回现处理
//        fileList.clear();
//        fileList.addAll(SPUtil.getListData(HazardPlanUploadFileList, HazardPlanUploadFileBean.class));
        hasPic = getIntent().getBooleanExtra("hasPic", false);
        if (hasPic) {
            Bundle bundle = getIntent().getExtras();
            fileList = (List<HazardPlanUploadFileBean>) bundle.getSerializable("picList");
        }

        adapter = new HazardPlanUploadAdapter(fileList);
        binding.hazardPlanUploadRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.hazardPlanUploadRecycler.setAdapter(adapter);
        initView();
        if (!PermissionUtil.checkHazardPlanUploadFilePermission(HazardPlanUploadFileActivity.this)) {
            ToastUtil.show(mContext, "权限被拒绝");
            finish();
        }
    }

    private void initView() {
        if (fileList.size() > 0) {
            binding.hazardPlanUploadRecycler.setVisibility(View.VISIBLE);
            binding.nodata.setVisibility(View.GONE);
        } else {
            binding.hazardPlanUploadRecycler.setVisibility(View.GONE);
            binding.nodata.setVisibility(View.VISIBLE);
        }
    }

    public void pickFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        this.startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;    // 用户未选择任何文件，直接返回
        }
        uri = data.getData(); // 获取用户选择文件的URI
        ContentResolver resolver = this.getContentResolver(); // 通过ContentProvider查询文件路径
        Cursor cursor = resolver.query(uri, null, null, null, null);
        if (cursor == null) {
            path = uri.getPath();   // 未查询到，说明为普通文件，可直接通过URI获取文件路径
            addList(path, path);
            return;
        }
        if (cursor.moveToFirst()) {
            path = cursor.getString(cursor.getColumnIndex("_data"));    // 多媒体文件，从数据库中获取文件的真实路径

            String[] name = path.split("/");
            addList(name[name.length - 1], path);
        }
        cursor.close();
    }

    private void addList(String name, String path) {
        HazardPlanUploadFileBean bean = new HazardPlanUploadFileBean();
        String newPath;
        File newFile;
        if (name.substring(name.length() - 3).equals("jpg")) {
            newPath = BitmapUtil.compressImage(path);
        } else {
            newPath = path;
        }
        newFile = new File(newPath);
        bean.setFile(newFile);
        bean.setName(name);
        bean.setType(type);
        fileList.add(bean);

        this.path = "";
        initView();
        adapter.notifyDataSetChanged();
    }

    public class Listener {
        //措施
        public void rectificationPlan() {
            type = 1;
            pickFile();
        }

        //方案
        public void rectificationPlanDoc() {
            type = 2;
            pickFile();
        }

        public void submit() {
//            for (int i = 0; i < fileList.size(); i++) {
//                LogUtil.i(fileList.get(i).getName() + "  " + fileList.get(i).getPath());
//            }
            SPUtil.putListData(HazardPlanUploadFileList, fileList);
//            Intent intent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("picList", (Serializable) fileList);
            setResult(3);
            finish();
        }
    }
}
