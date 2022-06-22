package com.example.new_androidclient.hazard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.new_androidclient.BR;
import com.example.new_androidclient.Base.BaseActivity;
import com.example.new_androidclient.NetWork.DialogObserver;
import com.example.new_androidclient.NetWork.RetrofitUtil;
import com.example.new_androidclient.NetWork.SchedulerTransformer;
import com.example.new_androidclient.Other.Constants;
import com.example.new_androidclient.Other.RouteString;
import com.example.new_androidclient.Other.SPString;
import com.example.new_androidclient.R;
import com.example.new_androidclient.Util.BitmapUtil;
import com.example.new_androidclient.Util.DataConverterUtil;
import com.example.new_androidclient.Util.LogUtil;
import com.example.new_androidclient.Util.SPUtil;
import com.example.new_androidclient.Util.ToastUtil;
import com.example.new_androidclient.databinding.ActivityHazardDetailBinding;
import com.example.new_androidclient.hazard.adapter.HazardPicAdapter;
import com.example.new_androidclient.hazard.bean.HazardDetailBean;
import com.example.new_androidclient.hazard.bean.HazardPicBean;

import org.jaaksi.pickerview.picker.TimePicker;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static org.jaaksi.pickerview.picker.TimePicker.TYPE_DATE;

/**
 * 状态是排查中的详情页面
 */

@Route(path = RouteString.HazardDetailActivity)
public class HazardDetailActivity extends BaseActivity {
    public List<HazardDetailBean> list = new ArrayList<>();
    private ActivityHazardDetailBinding binding;
    public HazardDetailBean bean = new HazardDetailBean();
    public Listener listener = new Listener();
    private Map<String, String> map = new HashMap<>();
    private final String DEFAULT = "0"; // 默认不选
    private final String QUALIFIED = "1";  //合格
    private final String UNQUALIFIED = "2"; //不合格
    private String check = DEFAULT;  //是否合格
    private int current = 0;  //从列表页跳转进本页面时，当前页面里，显示第current条数据
    private int pre = 0;  //例如当前pre=current=5，往前翻一页， pre=4， 往后翻一页，pre=5
    private String count; //用于显示（pre/count）
    private String UP = "up";//用户点击上一条
    private String DOWN = "down"; //用户点击下一条
    private String FINISH = "finish"; //用户点击完成巡检
    private String turnPage = DOWN; //用户点击了上一条按钮还是下一条按钮

    private Uri mUri;
    private String path;
    private File file;
    private int picUploadSuccessNum;
    private HazardPicAdapter adapter;
    private final static int REQUEST_CODE_CAMERA = 1;
    private List<HazardPicBean> newPicFileList = new ArrayList<>();//用户新拍的
    private List<HazardPicBean> allPicFileList = new ArrayList<>();//用户新拍的和接口获取的放在一起，在页面显示

    @Autowired()
    int planId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hazard_detail);
        binding.setListener(listener);
        binding.setDetailBean(bean);
        adapter = new HazardPicAdapter(allPicFileList, HazardDetailActivity.this);
        binding.hazardPicRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        binding.hazardPicRecycler.setAdapter(adapter);
        binding.timepicker.setOnClickListener(v -> listener.timePicker());
        getDetail();
    }

    //获取要检查的数据列表
    private void getDetail() {
        RetrofitUtil.getApi().getHazardDetailById(planId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardDetailBean>>(mContext, true, "正在获取计划详情") {
                    @Override
                    public void onSuccess(List<HazardDetailBean> bean) {
                        list.clear();
                        if (bean.size() > 0) {
                            list.addAll(bean);
                            initData();
                            //   getPicUrl();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void initData() {
        //列表里n条数据，判断从第几条开始是未排查的，显示在页面上
        String result;
        for (int i = 0; i < list.size(); i++) {
            result = list.get(i).getInvestigationResut();
            if (result == null || result.equals(DEFAULT)) {
                current = pre = i;
                break;
            }
            if (i == list.size() - 1) {
                current = pre = i;
            }
        }
        setDataToContentAndCountAndButtonAndChecked(current);
    }

    private void setDataToContentAndCountAndButtonAndChecked(int num) {
        getPicUrl();//通过本页数据获取本页面计划的照片

        //把（n/m）中的n和m显示在页面上
        count = "(" + (num + 1) + "/" + list.size() + ")";
        listener.setCount(count);

        //设置radiobutton是否选中
        if (list.get(num).getInvestigationResut() == null ||
                list.get(num).getInvestigationResut().equals(DEFAULT)) {  //如果result为空，则都不选，隐藏下面填写部分
            setQualifiedCheckState(false); //都不选
            setUnqualifiedCheckState(false);
            check = DEFAULT;
            binding.hazard.setVisibility(View.GONE);
        } else if (list.get(num).getInvestigationResut().equals(QUALIFIED)) {  //如果当前页面的bean类里result是合格，设置合格按钮选中，隐藏下面填写部分
            listener.check_qualified(); //选中合格
        } else if (list.get(num).getInvestigationResut().equals(UNQUALIFIED)) { //如果当前页面的bean类里result是不合格，设置不合格按钮选中，显示下面填写部分
            listener.check_unqualified();//选中不合格
        }

        //设置检查的具体内容文字, 因为有可能是检查一部分后跳出页面再点进来接着检查，所以需要设置所有的数据，这样用户点击上一条时才会有数据
        bean.setId(list.get(num).getId());
        bean.setInvestigationContent(list.get(num).getInvestigationContent());
        bean.setHazardContent(list.get(num).getHazardContent());
        bean.setHazardName(list.get(num).getHazardName());
        if (list.get(num).getInvestigationUser() == null) {  //判断是否有检查人员，如果没有默认为登录人
            bean.setInvestigationUser(SPUtil.getData(SPString.UserName, "").toString());
        } else {
            bean.setInvestigationUser(list.get(num).getInvestigationUser());
        }

        if (list.get(num).getLimitRectifyTime() == null || list.get(num).getLimitRectifyTime().equals("")) {
            bean.setLimitRectifyTime("点击选择时间");

        } else {
            bean.setLimitRectifyTime(list.get(num).getLimitRectifyTime().substring(0, 11));
        }
        bean.updateData();

        //判断按钮是否显示
        showButton();
    }

    private void setQualifiedCheckState(boolean state) {
        binding.qualified.setChecked(state);
    }

    private void setUnqualifiedCheckState(boolean state) {
        binding.unqualified.setChecked(state);
    }

    //判断是否显示上下两个按钮
    private void showButton() {
        //假设列表有5条数据，下标是0至4
        if (list.size() == 1) {
            binding.pre.setVisibility(View.GONE);
            binding.next.setVisibility(View.GONE);
            binding.submit.setVisibility(View.VISIBLE);
        } else if (list.size() > 1) {
            if (pre == current && current == 0) {   //cur和pre都是0的时候，只显示下按钮
                binding.pre.setVisibility(View.GONE);
                binding.next.setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.GONE);
            } else if (pre < current && pre == 0) {  //pre小于cur并且页面显示在列表第一项时，没有上按钮
                binding.pre.setVisibility(View.GONE);
                binding.next.setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.GONE);
            } else if (pre < current && pre > 0 && pre != list.size() - 1) { //pre处于0和cur之间并且不是列表最后一条时，上下按钮都显示
                binding.pre.setVisibility(View.VISIBLE);
                binding.next.setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.GONE);
            } else if (pre == current && current == list.size() - 1) { //最后一条数据，显示提交按钮
                binding.pre.setVisibility(View.VISIBLE);
                binding.next.setVisibility(View.GONE);
                binding.submit.setVisibility(View.VISIBLE);
            } else if (pre == current) { //显示要检查的内容时，上下都有
                binding.pre.setVisibility(View.VISIBLE);
                binding.next.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getPicUrl() {
        RetrofitUtil.getApi().getPicUrl(list.get(pre).getId(), Constants.FileType40)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardPicBean>>(mContext, false) {

                    @Override
                    public void onSuccess(List<HazardPicBean> bean) {
                        if (bean.size() > 0) {
                            for (int i = 0; i < bean.size(); i++) {
                                bean.get(i).setFromSystem(true);
                            }
                            synchronized (this) {
                                allPicFileList.addAll(bean);
                            }
                            //     adapter.notifyDataSetChanged();
                            getBitmap();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    //异步把docUrl转成Bitmap,然后添加到列表里显示出来
    private void getBitmap() {
        final int[] num = {0};
        for (int i = 0; i < allPicFileList.size(); i++) {
            Observable.just(allPicFileList.get(i))
                    .map(hazardPicBean -> BitmapUtil.getBitmap(hazardPicBean.getDocUrl()))
                    .compose(new SchedulerTransformer<>())
                    .subscribe(new HazardPicObserver(mContext, true, "正在处理图片") {
                                   @Override
                                   public void onSuccess(Bitmap bean) {
                                       allPicFileList.get(num[0]).setBitmap(bean);
                                       num[0]++;
                                       adapter.notifyDataSetChanged();
                                   }

                                   @Override
                                   public void onFailure(String err) {
                                       ToastUtil.show(mContext, err);
                                   }
                               }
                    );
        }
    }

    private void submitPic(File file, String id, String type) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part MultipartFile = MultipartBody.Part.createFormData("files", file.getName(), requestFile);

        RequestBody requestBody_id = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestBody_type = RequestBody.create(MediaType.parse("text/plain"), type);

        RetrofitUtil.getApi().uploadFile(requestBody_id, requestBody_type, MultipartFile)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在上传图片") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("上传成功")) {
                            synchronized (this) {
                                picUploadSuccessNum++;
                            }
                            if (newPicFileList.size() == picUploadSuccessNum) { //如果图片都上传成功，则上传数据
                                postData(map);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void postData(Map<String, String> map) {
        RetrofitUtil.getApi().updHazardPlanContent(DataConverterUtil.map_to_body(map))
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String str) {
                        if (list.size() == 1) {
                            submitData();
                            return;
                        }
                        if (turnPage.equals(FINISH)) {
                            submitData();
                            return;
                        }
                        allPicFileList.clear();
                        newPicFileList.clear();
                        picUploadSuccessNum = 0;
                        adapter.notifyDataSetChanged();
                        if (str.equals("保存成功")) {
                            HazardDetailBean hazardDetailBean = new HazardDetailBean();
                            hazardDetailBean.setId(Integer.parseInt(map.get("id")));
                            hazardDetailBean.setLimitRectifyTime(map.get("limitRectifyTime"));
                            hazardDetailBean.setHazardName(map.get("hazardName"));
                            hazardDetailBean.setHazardContent(map.get("hazardContent"));
                            hazardDetailBean.setInvestigationUser(map.get("investigationUser"));
                            hazardDetailBean.setInvestigationContent(map.get("investigationContent"));
                            hazardDetailBean.setInvestigationResut(map.get("investigationResut"));
                            list.set(pre, hazardDetailBean);
                            bean.updateData();
                            map.clear();
                            ToastUtil.show(mContext, "第" + (pre + 1) + "条数据" + str);
                            if (turnPage.equals(UP)) {
                                pre--;
                            } else {
                                if (pre == current) {
                                    current++;
                                }
                                pre++;
                            }
                            setDataToContentAndCountAndButtonAndChecked(pre);
                        } else {
                            ToastUtil.show(mContext, "第" + pre + "条数据" + str);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private void submitData() {
        RetrofitUtil.getApi().submitHazardPlan(planId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<String>(mContext, true, "正在提交数据") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("提交成功")) {
                            ToastUtil.show(mContext, bean);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        ToastUtil.show(mContext, err);
                    }
                });
    }

    private boolean checkData() {
        map.clear();
        if (check.equals(DEFAULT)) {
            ToastUtil.show(mContext, "请选择是否合格");
            return false;
        } else if (check.equals(QUALIFIED)) {
            if (bean.getInvestigationUser() == null ||
                    bean.getInvestigationUser().isEmpty()) {
                ToastUtil.show(mContext, "请填写完整");
                return false;
            } else {
                map.put("hazardContent", "");
                map.put("hazardName", "");
                map.put("limitRectifyTime", "");
                map.put("investigationResut", check);
            }
        } else if (check.equals(UNQUALIFIED)) {
            if (bean.getHazardContent() == null ||
                    bean.getHazardContent().isEmpty() ||
                    bean.getHazardName() == null ||
                    bean.getHazardName().isEmpty() ||
                    bean.getInvestigationUser() == null ||
                    bean.getInvestigationUser().isEmpty() ||
                    bean.getLimitRectifyTime() == null ||
                    bean.getLimitRectifyTime().equals("点击选择时间")) {
                ToastUtil.show(mContext, "请填写完整");
                return false;
            } else {
                map.put("investigationResut", check);
                map.put("hazardContent", bean.getHazardContent());
                map.put("hazardName", bean.getHazardName());
                map.put("limitRectifyTime", bean.getLimitRectifyTime() + " 00:00:00");
            }
        }
        map.put("id", String.valueOf(bean.getId()));
        map.put("investigationUser", bean.getInvestigationUser());
        map.put("investigationContent", bean.getInvestigationContent());

        return true;
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
            HazardPicBean bean = new HazardPicBean(String.valueOf(list.get(pre).getId()), Constants.FileType40, newFile, "", false);
            newPicFileList.add(bean);
            synchronized (this) {
                allPicFileList.add(bean);
            }
            adapter.notifyDataSetChanged();
//            String str = BitmapUtil.compressImage(file.getPath());
//            file.
//              Bitmap bit2 = BitmapFactory.decodeFile(str);


        }
    }

    public class Listener extends BaseObservable {
        public void update() {
            if (checkData()) {
                if (newPicFileList.size() == 0) {
                    if (allPicFileList.size() > 0) {
                        postData(map);
                    } else if (map.get("investigationResut").equals(UNQUALIFIED)) { //如果不合格
                        ToastUtil.show(mContext, "请拍照");
                    } else {
                        postData(map);
                    }
                } else {
                    for (int i = 0; i < newPicFileList.size(); i++) {
                        submitPic(newPicFileList.get(i).getFile(),
                                newPicFileList.get(i).getId(),
                                Constants.FileType40);
                    }
                }
            }
        }

        public void takepic() {
            takePhoto();
        }

        public void submit() {
            turnPage = FINISH;
            update();
        }

        public void check_qualified() {
            check = QUALIFIED;
            setQualifiedCheckState(true);
            setUnqualifiedCheckState(false);
            binding.hazard.setVisibility(View.GONE);
        }

        public void check_unqualified() {
            check = UNQUALIFIED;
            setQualifiedCheckState(false);
            setUnqualifiedCheckState(true);
            binding.hazard.setVisibility(View.VISIBLE);
        }

        public void pre() {
            turnPage = UP;
            update();
        }

        public void next() {
            turnPage = DOWN;
            update();
        }

        private String count;

        @Bindable
        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
            notifyPropertyChanged(BR.count);
        }

        public void timePicker() {
            TimePicker mTimePicker = new TimePicker.Builder(HazardDetailActivity.this, TYPE_DATE, (picker, date) -> {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                bean.setLimitRectifyTime(formatter.format(date));
                LogUtil.i(formatter.format(date));
            }).setRangDate(System.currentTimeMillis(), 1893563460000L)
                    .create();
            mTimePicker.show();
        }
    }

    @Override
    protected void onDestroy() {
        check = DEFAULT;
        pre = current = 0;
        count = null;
        super.onDestroy();
    }

}
