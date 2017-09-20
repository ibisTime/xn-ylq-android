package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.DatePicker;

import com.cdkj.baselibrary.dialog.CommonDialog;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityMyInfoBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.UserInfoModel;
import com.chengdai.ehealthproject.model.common.model.activitys.ImageSelectActivity;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.LogUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.qiniu.QiNiuUtil;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.qiniu.android.http.ResponseInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**我的信息
 * Created by 李先俊 on 2017/6/16.
 */

public class MyInfoActivity extends AbsStoreBaseActivity {

    private ActivityMyInfoBinding mBinding;

    private UserInfoModel mData;

    public final  int PHOTOFLAG=110;

    public String mGender;//性别类型；


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, UserInfoModel data){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,MyInfoActivity.class);

        intent.putExtra("data",data);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_my_info, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("编辑资料");
        setSubLeftImgState(true);

        if(getIntent()!=null){
            mData=getIntent().getParcelableExtra("data");
        }

        setSubRightTitleAndClick("保存",v -> {
            updateUserInfoRequest();
        });

        setShowData();

        setListener();

    }

    private void setListener() {
        //修改头像
        mBinding.linLogo.setOnClickListener(v -> {
            ImageSelectActivity.launch(this,PHOTOFLAG);
        });
        //修改昵称
        mBinding.linName.setOnClickListener(v -> {
            UserInfoInputUpdateActivity.open(this,mBinding.tvName.getText().toString(),UserInfoInputUpdateActivity.TYPE_NAME);
        });
        //修改性别
        mBinding.linGender.setOnClickListener(v -> {

            CommonDialog commonDialog = new CommonDialog(this).builder()
                    .setTitle("提示").setContentMsg("请选择性别")
                    .setPositiveBtn("女", view -> {
                        mGender=MyConfig.GENDERWOMAN;
                        mBinding.tvGender.setText("女");
                    })
                    .setNegativeBtn("男", view -> {
                        mGender=MyConfig.GENDERMAN;
                        mBinding.tvGender.setText("男");
                    }, false);

            commonDialog.show();
        });
        //修改邮箱
        mBinding.linEmail.setOnClickListener(v -> {
            UserInfoInputUpdateActivity.open(this,mBinding.tvEmail.getText().toString(),UserInfoInputUpdateActivity.TYPE_EMAIL);
        });
        //修改生日
        mBinding.linBirthday.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();
            int   year = calendar.get(Calendar.YEAR);
            int  monthOfYear = calendar.get(Calendar.MONTH);
            int   dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth)
                {
                    mBinding.tvBirthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                }
            }, year, monthOfYear, dayOfMonth);

            datePickerDialog.show();

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == PHOTOFLAG) {
            String path = data.getStringExtra(ImageSelectActivity.staticPath);
            LogUtil.E("图片xxxxxxx"+path);
            new QiNiuUtil(this).getQiniuURL(new QiNiuUtil.QiNiuCallBack() {
                @Override
                public void onSuccess(String key, ResponseInfo info, JSONObject res) {

                    Map<String,String> map=new HashMap<String, String>();
                    map.put("userId",SPUtilHelpr.getUserId());
                    map.put("photo",key);
                    map.put("token",SPUtilHelpr.getUserToken());

                   mSubscription.add( RetrofitUtils.getLoaderServer().updateUserLogo("805077", StringUtils.getJsonToString(map))
                            .compose(RxTransformerHelper.applySchedulerResult(MyInfoActivity.this))
                            .filter(isSuccessModes -> isSuccessModes!=null && isSuccessModes.isSuccess())
                            .subscribe(isSuccessModes -> {
                                ImgUtils.loadImgLogo(MyInfoActivity.this,MyConfig.IMGURL+key,mBinding.imgLogo);
                                MyFragmentRefeshUserIfo();
                            },Throwable::printStackTrace));
                }

                @Override
                public void onFal(String info) {

                }
            },path);

        }

    }


    private void setShowData() {
        if(mData == null){
            return;
        }

        ImgUtils.loadImgLogo(this, MyConfig.IMGURL+mData.getUserExt().getPhoto(),mBinding.imgLogo);
        mBinding.tvName.setText(mData.getNickname());
        if(mData.getUserExt()!=null){
        mBinding.tvBirthday.setText(mData.getUserExt().getBirthday());
        if(MyConfig.GENDERMAN.equals(mData.getUserExt().getGender())){
            mGender=MyConfig.GENDERMAN;
         mBinding.tvGender.setText("男");
        }else if (MyConfig.GENDERWOMAN.equals(mData.getUserExt().getGender())){
            mBinding.tvGender.setText("女");
            mGender=MyConfig.GENDERWOMAN;
        }
         mBinding.tvEmail.setText(mData.getUserExt().getEmail());
            mBinding.edit.setText(mData.getUserExt().getIntroduce());
        }
        if(mData.getReferrer()!=null){
            mBinding.tvTjName.setText(mData.getReferrer().getMobile());
            mBinding.tvTjId.setText(mData.getReferrer().getUserId());

            mBinding.tvTjType.setText(StringUtils.getTjTypebyCode(this,mData.getReferrer().getKind()));
        }

    }


    public void updateUserInfoRequest(){

        if(TextUtils.isEmpty(mBinding.tvBirthday.getText().toString())){
            showToast("请选择生日");
            return;
        }

       if(TextUtils.isEmpty(mGender)){
            showToast("请选择性别");
            return;
        }
        if(TextUtils.isEmpty(mBinding.tvEmail.getText().toString())){
            showToast("请输入邮箱");
            return;
        }
        Map<String,String> map=new HashMap<>();
        map.put("userId",SPUtilHelpr.getUserId());
        map.put("token",SPUtilHelpr.getUserToken());
        map.put("birthday",mBinding.tvBirthday.getText().toString());
        map.put("gender",mGender);
        map.put("email",mBinding.tvEmail.getText().toString());
        map.put("introduce",mBinding.edit.getText().toString());

       mSubscription.add(RetrofitUtils.getLoaderServer().updateUserLogo("805156",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .filter(isSuccessModes -> isSuccessModes!=null && isSuccessModes.isSuccess())
                .subscribe(isSuccessModes -> {
                    MyFragmentRefeshUserIfo();
                    finish();
                },Throwable::printStackTrace));


    }


    //刷新用户数据
    public void MyFragmentRefeshUserIfo(){
        EventBusModel e=new EventBusModel();
        e.setTag("MyFragmentRefeshUserIfo");

        EventBus.getDefault().post(e);
    }


    @Subscribe
    public void refeshEvent(EventBusModel e){
        if(e==null){
            return;
        }
        if(TextUtils.equals("MyInfoRefeshName",e.getTag()))//刷新用户数据
        {
           mData.setNickname(e.getEvInfo());
            mBinding.tvName.setText(e.getEvInfo());
        }
        if(TextUtils.equals("MyInfoRefeshEmail",e.getTag()))//刷新用户数据
        {
            mData.getUserExt().setEmail(e.getEvInfo());
            mBinding.tvEmail.setText(e.getEvInfo());
        }
    }
}
