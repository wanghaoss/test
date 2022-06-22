package com.example.new_androidclient.inspection;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.Other.SPString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.BitmapUtil;
import com.example.new_androidclient.Util.PermissionUtil;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityInspectionTakePhotoBinding;
import com.example.new_androidclient.inspection.adapter.InspectionPicAdapter;
import com.example.new_androidclient.inspection.bean.PicBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Route(path = RouteString.InspectionTakePhotoActivity)
public class InspectionTakePhotoActivity extends BaseActivity {
    private ActivityInspectionTakePhotoBinding binding;

    private List<PicBean> picFileList = new ArrayList<>();
    private final static int REQUEST_CODE_CAMERA = 1;
    private int resultCode = -1;
    private Uri mUri;
    private String path;

    private RecyclerView recyclerView;
    private InspectionPicAdapter adapter;

    private File file;
    private int id;
    private String des;
    private boolean hasPic;

    private String type; //30区域 31设备 32参数 40隐患排查

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_take_photo);
        recyclerView = binding.inspectionPicRecyelerview;
        binding.setListener(new Listener());
        id = getIntent().getIntExtra("id", 0);
        type = getIntent().getStringExtra("type");
        hasPic = getIntent().getBooleanExtra("hasPic", false);
        des = getIntent().getStringExtra("des");
        if (hasPic) {
            Bundle bundle = getIntent().getExtras();
            picFileList = (List<PicBean>) bundle.getSerializable("picList");
        }
        PermissionUtil.checkCameraAndReadPermission(this);
        initView();
    }

    private void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        adapter = new InspectionPicAdapter(picFileList, mContext);
        recyclerView.setAdapter(adapter);
        binding.inspectionPicEdittext.setText(des);
    }

    private void initCamera() {
        path = mContext.getFilesDir() + File.separator + "images" + File.separator;
        file = new File(path, System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //  步骤二：Android 7.0及以上获取文件 Uri
            mUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".inspection.MyFileProvider", file);
        } else {
            //  步骤三：获取文件Uri
            mUri = Uri.fromFile(file);
        }
    }

    private void takePhoto() {
        initCamera();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            File newFile = new File(BitmapUtil.compressImage(file.getPath()));
            //30区域 31设备 32参数 40隐患排查
            picFileList.add(new PicBean(String.valueOf(id), type, newFile));
            adapter.notifyDataSetChanged();
        }
    }

    public class Listener {
        public void pic() {
            takePhoto();
        }

        public void finishPic() {
            String str;
            Intent intent = new Intent();
            str = binding.inspectionPicEdittext.getText().toString();
            if (str.isEmpty()) {
                ToastUtil.show(mContext, "请填写描述");
                return;
            }else{
                intent.putExtra("des", binding.inspectionPicEdittext.getText().toString());
            }
            setResult(resultCode, intent);
            if (picFileList.size() != 0) {
                SPUtil.putListData(SPString.InspectionPicList, picFileList);
                finish();
            } else {
                ToastUtil.show(mContext, "至少拍一张照片");
            }
        }
    }
}

