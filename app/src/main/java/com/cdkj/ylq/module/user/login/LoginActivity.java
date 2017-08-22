package com.cdkj.ylq.module.user.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cdkj.baselibrary.activitys.FindPwdActivity;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.interfaces.LoginInterface;
import com.cdkj.baselibrary.interfaces.LoginPresenter;
import com.cdkj.baselibrary.model.EventBusModel;
import com.cdkj.baselibrary.model.UserLoginModel;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.MainActivity;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityLoginBinding;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;

import static com.cdkj.baselibrary.appmanager.EventTags.LOGINREFRESH;

/**
 * Created by 李先俊 on 2017/8/8.
 */
@Route(path = "/user/login")
public class LoginActivity extends AbsBaseActivity implements LoginInterface {

    private LoginPresenter mPresenter;
    private ActivityLoginBinding mBinding;
    private boolean canOpenMain;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context,boolean canOpenMain) {
        if (context == null) {
            return;
        }
        Intent intent= new Intent(context, LoginActivity.class);
        intent.putExtra("canOpenMain",canOpenMain);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_login, null, false);
        return mBinding.getRoot();
    }
    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setTopTitle("登录");
        setSubLeftImgState(true);
        mPresenter = new LoginPresenter(this);

        if(getIntent()!=null){
            canOpenMain=getIntent().getBooleanExtra("canOpenMain",false);
        }

        //登录
        mBinding.btnLogin.setOnClickListener(v -> {
            mPresenter.login(mBinding.editUsername.getText().toString(), mBinding.editUserpass.getText().toString(), this);
        });
        //注册
        mBinding.tvStartRegistr.setOnClickListener(v -> {
            RegisterActivity.open(this);
        });
        //找回密码
        mBinding.tvFindPwd.setOnClickListener(v -> {
            FindPwdActivity.open(LoginActivity.this, "");
        });
    }

    @Override
    public void LoginSuccess(UserLoginModel user, String msg) {
        SPUtilHelpr.saveUserId(user.getUserId());
        SPUtilHelpr.saveUserToken(user.getToken());
        if(canOpenMain){
            MainActivity.open(this);
        }else{
            EventBus.getDefault().post(LOGINREFRESH);
        }
        finish();
    }

    @Override
    public void LoginFailed(String code, String msg) {
        showToast(msg);
    }

    @Override
    public void StartLogin() {
        showLoadingDialog();
    }

    @Override
    public void EndLogin() {
        disMissLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.clear();
            mPresenter = null;
        }
    }

    @Override
    protected boolean canFinish() {
        if(canOpenMain){
            MainActivity.open(this);
            finish();
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onBackPressed() {

        if(canOpenMain){
            MainActivity.open(this);
            finish();
        }else{
            super.onBackPressed();
        }

    }
}
