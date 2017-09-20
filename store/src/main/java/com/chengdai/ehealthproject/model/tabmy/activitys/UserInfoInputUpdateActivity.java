package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityUserinfoUpdateInputBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**我的信息内容输入修改
 * Created by 李先俊 on 2017/6/16.
 */

public class UserInfoInputUpdateActivity extends AbsStoreBaseActivity {

    private ActivityUserinfoUpdateInputBinding mBinding;

    private int mType;

    public static final int TYPE_NAME=1;//昵称
    public static final int TYPE_EMAIL=2;//邮箱


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, String info,int opentype ){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,UserInfoInputUpdateActivity.class);

        intent.putExtra("info",info);
        intent.putExtra("opentype",opentype);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_userinfo_update_input, null, false);
        addMainView(mBinding.getRoot());

        setSubLeftImgState(true);

        if(getIntent()!=null){
            mBinding.edit.setText(getIntent().getStringExtra("info"));
            mType=getIntent().getIntExtra("opentype",TYPE_NAME);
        }

        if(mType == TYPE_EMAIL){
            setTopTitle("修改邮箱");
        }else{
            setTopTitle("修改昵称");
        }

        setSubRightTitleAndClick("确定",v -> {

            if (TextUtils.isEmpty(mBinding.edit.getText().toString())){
                showToast("请输入内容");
                return;
            }

            if(mType == TYPE_EMAIL){

                if(!StringUtils.isEmail(mBinding.edit.getText().toString())){
                    showToast("请输入正确的邮箱格式");
                    return;
                }

                MyInfoRefeshUserEmailIfo();
                finish();
            }else{
                updateUserInfo();
            }
        });

    }

    /**
     * 修改用户昵称
     */
    public void updateUserInfo(){
        Map<String,String> map=new HashMap<>();
        map.put("userId",SPUtilHelpr.getUserId());
        map.put("nickname",mBinding.edit.getText().toString());
        map.put("token",SPUtilHelpr.getUserToken());

        mSubscription.add( RetrofitUtils.getLoaderServer().updateUserLogo("805075", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .filter(isSuccessModes -> isSuccessModes!=null && isSuccessModes.isSuccess())
                .subscribe(isSuccessModes -> {

                    MyFragmentRefeshUserIfo();
                    MyInfoRefeshUserIfo();

                    finish();

                },Throwable::printStackTrace));

    }

    //刷新用户数据
    public void MyFragmentRefeshUserIfo(){
        EventBusModel e=new EventBusModel();
        e.setTag("MyFragmentRefeshUserIfo");
        EventBus.getDefault().post(e);
    }
    //刷新用户数据
    public void MyInfoRefeshUserIfo(){
        EventBusModel e=new EventBusModel();
        e.setTag("MyInfoRefeshName");
        e.setEvInfo(mBinding.edit.getText().toString());
        EventBus.getDefault().post(e);
    }
   //刷新用户数据
    public void MyInfoRefeshUserEmailIfo(){
        EventBusModel e=new EventBusModel();
        e.setTag("MyInfoRefeshEmail");
        e.setEvInfo(mBinding.edit.getText().toString());
        EventBus.getDefault().post(e);
    }

}
