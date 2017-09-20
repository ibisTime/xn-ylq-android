package com.chengdai.ehealthproject.model.other;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cdkj.baselibrary.appmanager.ARouteConfig;
import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.utils.AppUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseStoreActivity;
import com.chengdai.ehealthproject.databinding.ActivityMainStoreBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.IntroductionInfoModel;
import com.chengdai.ehealthproject.model.dataadapters.ViewPagerAdapter;
import com.chengdai.ehealthproject.model.healthstore.HealthStoreFragment;
import com.chengdai.ehealthproject.model.paycar.MyPayCarFragment;
import com.chengdai.ehealthproject.model.tabmy.MyFragment;
import com.chengdai.ehealthproject.model.user.LoginActivity;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页面
 */
@Route(path = ARouteConfig.StoreMain)
public class MainActivity extends BaseStoreActivity {

    private ActivityMainStoreBinding mainBinding;

    private int mTabIndex = 1;//记录用户点击下标 用于未登录时恢复状态

    private int mShowIndex = 1;//显示相应页面
    private CommonDialog commonDialog;


    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, int select) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MainActivity.class);

        intent.putExtra("select", select);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_store);
//        mainBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main, null, false);;
//
//        addMainView(mainBinding.getRoot());

//        hintTitleView();


        if (getIntent() != null) {
            mTabIndex = getIntent().getIntExtra("select", 1);
            mShowIndex = mTabIndex - 1;
        }

        initViewState();

//        getUpdateReqeust();

    }

    /**
     * 初始化View状态
     */
    private void initViewState() {
        mainBinding.pagerMain.setPagingEnabled(false);//禁止左右切换

        List<Fragment> fragments = new ArrayList<>(); //设置fragment数据
        fragments.add(new HealthStoreFragment());
        fragments.add(new MyPayCarFragment());
        fragments.add(new MyFragment());

        mainBinding.pagerMain.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));

        mainBinding.pagerMain.setOffscreenPageLimit(fragments.size());

        mainBinding.layoutMainButtomStore.radioMainTabStore.setOnClickListener(v -> {
            mainBinding.pagerMain.setCurrentItem(0, false);
            mTabIndex = 1;
        });
        mainBinding.layoutMainButtomStore.radioMainTabCar.setOnClickListener(v -> {
            mainBinding.pagerMain.setCurrentItem(1, false);
            mTabIndex = 2;
        });


        mainBinding.layoutMainButtomStore.radioMainTabMy.setOnClickListener(v -> {
            if (!SPUtilHelpr.isLoginNoStart()) {

                setTabIndex();

                LoginActivity.open(this, false);
            } else {
                mTabIndex = 3;
                mainBinding.pagerMain.setCurrentItem(2, false);
            }
        });
        setTabIndex();
        mainBinding.pagerMain.setCurrentItem(mShowIndex, false);


/*       mSubscription.add( RxRadioGroup.checkedChanges(mainBinding.layoutMainButtom.radiogroup) //点击切换
               .subscribe(integer -> {
                   switch (integer){
                       case R.id.radio_main_tab_1:
                           mainBinding.pagerMain.setCurrentItem(0,false);
                           break;
                       case R.id.radio_main_tab_2:
                           mainBinding.pagerMain.setCurrentItem(1,false);
                           break;
                       case R.id.radio_main_tab_3:
                           mainBinding.pagerMain.setCurrentItem(2,false);
                           break;
                       case R.id.radio_main_tab_4:
                           mainBinding.pagerMain.setCurrentItem(3,false);
                           break;
                       case R.id.radio_main_tab_5:

                           if(!SPUtilHelpr.isLogin(this)){
                               LoginActivity.open(this,false);
                           }else{
                               mainBinding.pagerMain.setCurrentItem(4,false);
                           }


                           break;
                   }

          },Throwable::printStackTrace));*/

    }

    private void setTabIndex() {
        switch (mTabIndex) {
            case 1:
                mainBinding.layoutMainButtomStore.radioMainTabStore.setChecked(true);
                break;
            case 2:
                mainBinding.layoutMainButtomStore.radioMainTabCar.setChecked(true);
                break;
            case 3:
                mainBinding.layoutMainButtomStore.radioMainTabMy.setChecked(true);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!isFinishing()) {
            new CommonDialog(this).builder().setPositiveBtn("确认", (view) -> {
                logOut();
            }).setNegativeBtn("取消", null).setTitle("提示").setContentMsg("确认退出九州宝？").show();
        } else {
            logOut();
        }
    }

    public void logOut() {
        EventBusModel eventBusModel = new EventBusModel();
        eventBusModel.setTag("AllFINISH");
        EventBus.getDefault().post(eventBusModel); //结束掉所有界面

        EventBusModel eventBusMode2 = new EventBusModel();
        eventBusMode2.setTag("MainActivityFinish");
        EventBus.getDefault().post(eventBusMode2); //结束掉所有界面

        SPUtilHelpr.saveLocationInfo("");             //重置定位数据
        SPUtilHelpr.saveRestLocationInfo("");

        finish();
    }

    /**
     * 1-4设置显示位置
     *
     * @param eventBus
     */
    @Subscribe
    public void MainActivityEvent(EventBusModel eventBus) {
        if (eventBus == null) return;

        if (TextUtils.equals(eventBus.getTag(), "MainSetIndex")) {
            mTabIndex = eventBus.getEvInt();
            mShowIndex = eventBus.getEvInt() - 1;
            mainBinding.pagerMain.setCurrentItem(mShowIndex, false);
            setTabIndex();
        } else if (TextUtils.equals(eventBus.getTag(), "MainActivityFinish")) {
            finish();
        }
    }

    protected void showUpdateDialog(String str, Boolean isFouceUpdate, CommonDialog.OnPositiveListener onPositiveListener) {

        if (isFinishing()) {
            return;
        }
        if (isFouceUpdate) {
            commonDialog = new CommonDialog(this).builder()
                    .setTitle("更新提醒").setContentMsg(str)
                    .setPositiveBtn("立刻升级", onPositiveListener);

            commonDialog.show();
            return;
        }
        commonDialog = new CommonDialog(this).builder()
                .setTitle("更新提醒").setContentMsg(str)
                .setPositiveBtn("立刻升级", onPositiveListener)
                .setNegativeBtn("稍后提醒", null, false);

        commonDialog.show();
    }


    public void getUpdateReqeust() {

        Map<String, String> map = new HashMap<>();
        map.put("type", "3");
        map.put("systemCode", MyConfigStore.SYSTEMCODE);
        map.put("companyCode", MyConfigStore.COMPANYCODE);
        map.put("start", "1");
        map.put("limit", "30");

        RetrofitUtils.getLoaderServer().getInfoByUpdate("807715", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .filter(introductionInfoModels -> introductionInfoModels != null &&
                        introductionInfoModels.getList() != null && introductionInfoModels.getList().size() > 0)
                .subscribe(s -> {
                    showUpdateState(s.getList());
                }, Throwable::printStackTrace);
    }

    /**
     * 根据参数判断是否需要更新
     */
    private void showUpdateState(List<IntroductionInfoModel> datas) {
        String loadURL = "";
        String loademark = "";
        Boolean isLoad = false;
        Boolean isforceUpdate = false;

        for (IntroductionInfoModel model : datas) {
            if (model == null) continue;

            if (TextUtils.equals(model.getCkey(), "androidDownload")) {//获取下载连接

                loadURL = model.getCvalue();

            } else if (TextUtils.equals(model.getCkey(), "androidNote")) {//获取更新说明

                loademark = model.getCvalue();

            } else if (TextUtils.equals(model.getCkey(), "androidForceUpdate")) {//是否强制更新

                isforceUpdate = TextUtils.equals(model.getCvalue(), "1");

            } else if (TextUtils.equals(model.getCkey(), "androidVersion")) {//是否更新

                isLoad = !TextUtils.equals(model.getCvalue(), AppUtils.getAppVersionName(MainActivity.this));

            }
        }

        if (isLoad) {
            String finalLoadURL = loadURL;
            Boolean finalIsforceUpdate = isforceUpdate;
            showUpdateDialog(loademark, isforceUpdate, view -> {
                AppUtils.startWeb(this, finalLoadURL);
                if (finalIsforceUpdate) {
                    finish();
                }
            });
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtilHelpr.saveLocationInfo("");
        if (commonDialog != null) {
            commonDialog.closeDialog();
            commonDialog = null;
        }
    }
}
