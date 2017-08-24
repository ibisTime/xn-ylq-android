package com.cdkj.ylq;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.adapters.ViewPagerAdapter;
import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.EventBusModel;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.update.UpdateManager;
import com.cdkj.ylq.databinding.ActivityMainBinding;
import com.cdkj.ylq.module.borrowmoney.BorrowMoneyFragment;
import com.cdkj.ylq.module.certification.CertificationFragment;
import com.cdkj.ylq.module.user.userinfo.MyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.cdkj.baselibrary.appmanager.EventTags.MAINCHANGESHOWINDEX;
import static com.cdkj.baselibrary.appmanager.EventTags.MAINFINISH;

public class MainActivity extends AbsBaseActivity {

    private ActivityMainBinding mBinding;

    private int mShowIndex = 0;//显示相应页面

    public static final int SHOWMONEYPRODUCT = 0;//显示借款界面
    public static final int SHOWCERT = 1;//显示认证界面
    public static final int SHOWMY = 2;//显示我的界面
    private List<Fragment> fragments;
    private UpdateManager updateManager;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        context.startActivity(new Intent(context, MainActivity.class));
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main, null, false);
        return mBinding.getRoot();
    }

    @Override
    protected boolean canLoadTopTitleView() {
        return false;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        initViewPager();

        initListener();

        updateManager = new UpdateManager(getString(R.string.app_name));
        updateManager.checkNewApp(this);
    }

    /**
     * 初始化事件
     */
    private void initListener() {
        //借款
        mBinding.layoutMainButtom.radioMainTabMoney.setOnClickListener(v -> {
            setShowIndex(0);
        });
        //认证
        mBinding.layoutMainButtom.radioMainTabCertification.setOnClickListener(v -> {
            setShowIndex(1);
        });
        //我的
        mBinding.layoutMainButtom.radioMainTabMy.setOnClickListener(v -> {
            if (!SPUtilHelpr.isLogin(this, false)) {
                setTabIndex();
                return;
            }
            setShowIndex(2);
        });
    }


    public void setTabIndex() {

        switch (mShowIndex) {
            case 0:
//                setStateTitleColor(ContextCompat.getColor(this,R.color.white));
                mBinding.layoutMainButtom.radioMainTabMoney.setChecked(true);
                break;
            case 1:
//                setStateTitleColor(ContextCompat.getColor(this,R.color.activity_bg));
                mBinding.layoutMainButtom.radioMainTabCertification.setChecked(true);
                break;
            case 2:
//                setStateTitleColor(ContextCompat.getColor(this,R.color.activity_bg));
                mBinding.layoutMainButtom.radioMainTabMy.setChecked(true);
                break;
        }

    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        mBinding.pagerMain.setPagingEnabled(false);//禁止左右切换

        //设置fragment数据
        fragments = new ArrayList<>();

        fragments.add(BorrowMoneyFragment.getInstanse());
        fragments.add(CertificationFragment.getInstanse());
        fragments.add(MyFragment.getInstanse());

        mBinding.pagerMain.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        mBinding.pagerMain.setOffscreenPageLimit(fragments.size());
    }


    /**
     * 设置要显示的界面
     *
     * @param index
     */
    private void setShowIndex(int index) {
        if (index < 0 && index >= fragments.size()) {
            return;
        }
        mBinding.pagerMain.setCurrentItem(index, false);
        mShowIndex = index;
        setTabIndex();
    }


    @Subscribe
    public void MainEventBus(EventBusModel eventBusModel) {
        if (eventBusModel == null) {
            return;
        }
        if (TextUtils.equals(eventBusModel.getTag(), MAINCHANGESHOWINDEX)) {
            setShowIndex(eventBusModel.getEvInt());
        }
    }

    @Subscribe
    public void MainEventBusFinish(String evbusTag) {

        if (TextUtils.equals(evbusTag, MAINFINISH)) { //结束主页
            finish();
        }
    }
    @Override
    protected boolean canEvenFinish() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(updateManager!=null){
            updateManager.clear();
            updateManager=null;
        }
    }

    @Override
    public void onBackPressed() {
        showDoubleWarnListen("确认退出？",view -> {
            EventBus.getDefault().post(EventTags.AllFINISH);
            finish();
        });
    }
}
