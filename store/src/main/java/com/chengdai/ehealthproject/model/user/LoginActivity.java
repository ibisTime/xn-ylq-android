package com.chengdai.ehealthproject.model.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityLoginStorBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.other.MainActivity;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by 李先俊 on 2017/6/8.
 */

public class LoginActivity extends AbsStoreBaseActivity {

    private ActivityLoginStorBinding mBinding;

    private boolean isStartMain;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,boolean isStartMain){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,LoginActivity.class);
        intent.putExtra("isStartMain",isStartMain);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_login_stor, null, false);
        addMainView(mBinding.getRoot());

        setTopTitle(getString(R.string.txt_login));

        if(getIntent()!=null){
            isStartMain=getIntent().getBooleanExtra("isStartMain",true);
        }

        initViews();

    }

    private void initViews() {
        mBinding.tvStartRegistr.setOnClickListener(v -> {
//            RegisterActivity.open(this);
        });

        mBinding.btnLogin.setOnClickListener(v -> {

            if(TextUtils.isEmpty(mBinding.editUsername.getText().toString())){
                showToast("请输入账号");
                return;
            }

            if(TextUtils.isEmpty(mBinding.editUserpass.getText().toString())){
                showToast("请输入密码");
                return;
            }

            loginRequest();

        });

        mBinding.tvFindPwd.setOnClickListener(v -> {
            FindPassWordActivity.open(this,true);
        });

    }


    /**
     * 登录请求
     */
    private void loginRequest() {
        HashMap<String,String> hashMap=new HashMap<>();

        hashMap.put("loginName",mBinding.editUsername.getText().toString());
        hashMap.put("loginPwd",mBinding.editUserpass.getText().toString());
        hashMap.put("kind","f1");
        hashMap.put("systemCode",MyConfig.SYSTEMCODE);

        mSubscription.add(RetrofitUtils.getLoaderServer().UserLogin("805043", StringUtils.getJsonToString(hashMap) )
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .filter( data -> data!=null)
                .subscribe(data -> {
                        SPUtilHelpr.saveUserToken(data.getToken());
                        SPUtilHelpr.saveUserId(data.getUserId());

                        if(!TextUtils.isEmpty(data.getToken()) && !TextUtils.isEmpty(data.getUserId())){ //token 和 UserId不为空时
                            if(isStartMain){
                                MainActivity.open(this,1);
                            }

                            EventBusModel eventBusModel=new EventBusModel();
                            eventBusModel.setTag("LOGINSTATEREFHSH"); //登录状态刷新
                            eventBusModel.setEvBoolean(true);
                            EventBus.getDefault().post(eventBusModel);

                            finish();
                        }

                },Throwable::printStackTrace));


    }
    @Override
    public void onBackPressed() {
        if (isStartMain) {
            MainActivity.open(this,1);
        }
        finish();
    }
}
