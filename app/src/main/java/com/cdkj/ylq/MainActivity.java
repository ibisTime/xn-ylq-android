package com.cdkj.ylq;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.cdkj.baselibrary.adapters.ViewPagerAdapter;
import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.EventBusModel;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.update.UpdateManager;
import com.cdkj.ylq.databinding.ActivityMainBinding;
import com.cdkj.ylq.module.borrowmoney.BorrowMoneyFragment;
import com.cdkj.ylq.module.credit.CreditFragment;
import com.cdkj.ylq.module.user.userinfo.MyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.EventTags.MAINCHANGESHOWINDEX;
import static com.cdkj.baselibrary.appmanager.EventTags.MAINFINISH;

public class MainActivity extends AbsBaseActivity{

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
//        updateManager.checkNewApp(this);//这个检查更新的接口还会报错
        if (TextUtils.isEmpty(SPUtilHelpr.getQiNiuUrl())) {
            getQiNiuUrl();
        }
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
            if (!SPUtilHelpr.isLogin(this, false)) {
                setTabIndex();
                return;
            }
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
                mBinding.layoutMainButtom.radioMainTabMoney.setChecked(true);
                break;
            case 1:
                mBinding.layoutMainButtom.radioMainTabCertification.setChecked(true);
                break;
            case 2:
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
//        fragments.add(CertificationFragment.getInstanse());
        fragments.add(CreditFragment.getInstanse());
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
        if (updateManager != null) {
            updateManager.clear();
            updateManager = null;
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (TextUtils.isEmpty(SPUtilHelpr.getUserId())) {
//            UITipDialog.showSuccess(this, "请先登陆", dialog -> {
//                setTabIndex();
//                SPUtilHelpr.isLogin(this, false);
//            });
//        }
//    }


    //获取七牛地址
    public void getQiNiuUrl() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "qiniu_domain");
        map.put("systemCode", "CD-YLQ000014");
        map.put("companyCode", "CD-YLQ000014");

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("623917", StringUtils.getJsonToString(map));

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(MainActivity.this) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                Log.e("pppppp", "onSuccess: 图片地址" + data.getCvalue());
                if (!TextUtils.isEmpty(data.getCvalue())) {
                    SPUtilHelpr.saveQiNiuUrl("http://" + data.getCvalue() + "/");
                }
            }

            @Override
            protected void onReqFailure(int errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                showToast("图片地址获取失败");
            }

            @Override
            protected void onFinish() {

            }
        });

    }

    @Override
    public void onBackPressed() {
        showDoubleWarnListen("确认退出" + getString(R.string.app_name) + "？", view -> {
            EventBus.getDefault().post(EventTags.AllFINISH);
            finish();
        });
    }


}
