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
 * ?????????????????????????????????
 */

@Route(path = RouteString.HazardDetailActivity)
public class HazardDetailActivity extends BaseActivity {
    public List<HazardDetailBean> list = new ArrayList<>();
    private ActivityHazardDetailBinding binding;
    public HazardDetailBean bean = new HazardDetailBean();
    public Listener listener = new Listener();
    private Map<String, String> map = new HashMap<>();
    private final String DEFAULT = "0"; // ????????????
    private final String QUALIFIED = "1";  //??????
    private final String UNQUALIFIED = "2"; //?????????
    private String check = DEFAULT;  //????????????
    private int current = 0;  //???????????????????????????????????????????????????????????????current?????????
    private int pre = 0;  //????????????pre=current=5????????????????????? pre=4??? ??????????????????pre=5
    private String count; //???????????????pre/count???
    private String UP = "up";//?????????????????????
    private String DOWN = "down"; //?????????????????????
    private String FINISH = "finish"; //????????????????????????
    private String turnPage = DOWN; //???????????????????????????????????????????????????

    private Uri mUri;
    private String path;
    private File file;
    private int picUploadSuccessNum;
    private HazardPicAdapter adapter;
    private final static int REQUEST_CODE_CAMERA = 1;
    private List<HazardPicBean> newPicFileList = new ArrayList<>();//???????????????
    private List<HazardPicBean> allPicFileList = new ArrayList<>();//???????????????????????????????????????????????????????????????

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

    //??????????????????????????????
    private void getDetail() {
        RetrofitUtil.getApi().getHazardDetailById(planId)
                .compose(new SchedulerTransformer<>())
                .subscribe(new DialogObserver<List<HazardDetailBean>>(mContext, true, "????????????????????????") {
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
        //?????????n????????????????????????????????????????????????????????????????????????
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
        getPicUrl();//????????????????????????????????????????????????

        //??????n/m?????????n???m??????????????????
        count = "(" + (num + 1) + "/" + list.size() + ")";
        listener.setCount(count);

        //??????radiobutton????????????
        if (list.get(num).getInvestigationResut() == null ||
                list.get(num).getInvestigationResut().equals(DEFAULT)) {  //??????result????????????????????????????????????????????????
            setQualifiedCheckState(false); //?????????
            setUnqualifiedCheckState(false);
            check = DEFAULT;
            binding.hazard.setVisibility(View.GONE);
        } else if (list.get(num).getInvestigationResut().equals(QUALIFIED)) {  //?????????????????????bean??????result???????????????????????????????????????????????????????????????
            listener.check_qualified(); //????????????
        } else if (list.get(num).getInvestigationResut().equals(UNQUALIFIED)) { //?????????????????????bean??????result?????????????????????????????????????????????????????????????????????
            listener.check_unqualified();//???????????????
        }

        //?????????????????????????????????, ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        bean.setId(list.get(num).getId());
        bean.setInvestigationContent(list.get(num).getInvestigationContent());
        bean.setHazardContent(list.get(num).getHazardContent());
        bean.setHazardName(list.get(num).getHazardName());
        if (list.get(num).getInvestigationUser() == null) {  //????????????????????????????????????????????????????????????
            bean.setInvestigationUser(SPUtil.getData(SPString.UserName, "").toString());
        } else {
            bean.setInvestigationUser(list.get(num).getInvestigationUser());
        }

        if (list.get(num).getLimitRectifyTime() == null || list.get(num).getLimitRectifyTime().equals("")) {
            bean.setLimitRectifyTime("??????????????????");

        } else {
            bean.setLimitRectifyTime(list.get(num).getLimitRectifyTime().substring(0, 11));
        }
        bean.updateData();

        //????????????????????????
        showButton();
    }

    private void setQualifiedCheckState(boolean state) {
        binding.qualified.setChecked(state);
    }

    private void setUnqualifiedCheckState(boolean state) {
        binding.unqualified.setChecked(state);
    }

    //????????????????????????????????????
    private void showButton() {
        //???????????????5?????????????????????0???4
        if (list.size() == 1) {
            binding.pre.setVisibility(View.GONE);
            binding.next.setVisibility(View.GONE);
            binding.submit.setVisibility(View.VISIBLE);
        } else if (list.size() > 1) {
            if (pre == current && current == 0) {   //cur???pre??????0??????????????????????????????
                binding.pre.setVisibility(View.GONE);
                binding.next.setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.GONE);
            } else if (pre < current && pre == 0) {  //pre??????cur?????????????????????????????????????????????????????????
                binding.pre.setVisibility(View.GONE);
                binding.next.setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.GONE);
            } else if (pre < current && pre > 0 && pre != list.size() - 1) { //pre??????0???cur???????????????????????????????????????????????????????????????
                binding.pre.setVisibility(View.VISIBLE);
                binding.next.setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.GONE);
            } else if (pre == current && current == list.size() - 1) { //???????????????????????????????????????
                binding.pre.setVisibility(View.VISIBLE);
                binding.next.setVisibility(View.GONE);
                binding.submit.setVisibility(View.VISIBLE);
            } else if (pre == current) { //??????????????????????????????????????????
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

    //?????????docUrl??????Bitmap,????????????????????????????????????
    private void getBitmap() {
        final int[] num = {0};
        for (int i = 0; i < allPicFileList.size(); i++) {
            Observable.just(allPicFileList.get(i))
                    .map(hazardPicBean -> BitmapUtil.getBitmap(hazardPicBean.getDocUrl()))
                    .compose(new SchedulerTransformer<>())
                    .subscribe(new HazardPicObserver(mContext, true, "??????????????????") {
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
                .subscribe(new DialogObserver<String>(mContext, true, "??????????????????") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("????????????")) {
                            synchronized (this) {
                                picUploadSuccessNum++;
                            }
                            if (newPicFileList.size() == picUploadSuccessNum) { //?????????????????????????????????????????????
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
                .subscribe(new DialogObserver<String>(mContext, true, "??????????????????") {
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
                        if (str.equals("????????????")) {
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
                            ToastUtil.show(mContext, "???" + (pre + 1) + "?????????" + str);
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
                            ToastUtil.show(mContext, "???" + pre + "?????????" + str);
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
                .subscribe(new DialogObserver<String>(mContext, true, "??????????????????") {
                    @Override
                    public void onSuccess(String bean) {
                        if (bean.equals("????????????")) {
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
            ToastUtil.show(mContext, "?????????????????????");
            return false;
        } else if (check.equals(QUALIFIED)) {
            if (bean.getInvestigationUser() == null ||
                    bean.getInvestigationUser().isEmpty()) {
                ToastUtil.show(mContext, "???????????????");
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
                    bean.getLimitRectifyTime().equals("??????????????????")) {
                ToastUtil.show(mContext, "???????????????");
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
            //  ????????????Android 7.0????????????????????? Uri
            mUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".inspection.MyFileProvider", file);
        } else {
            //  ????????????????????????Uri
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
                    } else if (map.get("investigationResut").equals(UNQUALIFIED)) { //???????????????
                        ToastUtil.show(mContext, "?????????");
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
