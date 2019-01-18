package com.cdkj.ylq;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.cdkj.baselibrary.adapters.ViewPagerAdapter;
import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.EventBusModel;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.BitmapUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.update.UpdateManager;
import com.cdkj.ylq.databinding.ActivityMainBinding;
import com.cdkj.ylq.model.RepaymentQRBean;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.borrowmoney.BorrowMoneyFragment;
import com.cdkj.ylq.module.credit.CreditFragment;
import com.cdkj.ylq.module.user.userinfo.MyFragment;
import com.cdkj.ylq.utils.IsInstallWeChatOrAliPay;
import com.cdkj.ylq.utils.ZxingUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

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
    private String isJT;
    private String isFK;

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
        isJT = SPUtilHelpr.getIsJT();
        isFK = SPUtilHelpr.getIsFK();


        initListener();
        initViewPager();
        updateManager = new UpdateManager(getString(R.string.app_name));
//        updateManager.checkNewApp(this);//这个检查更新接口还会报错
//        if (TextUtils.isEmpty(SPUtilHelpr.getQiNiuUrl())) {
//            getQiNiuUrl();
//        }
//        getQRImage();
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

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        mBinding.pagerMain.setPagingEnabled(false);//禁止左右切换
        //设置fragment数据
        fragments = new ArrayList<>();

        //下面添加的空的 fragment  是避免了  加载正常的  fragment会调用接口  可能会报错
        if (TextUtils.equals("0", isJT) && TextUtils.equals("0", isFK)) {
            mShowIndex = 2;
            //两个都不要了
            fragments.add(new Fragment());
            fragments.add(new Fragment());
            mBinding.layoutMainButtom.radioMainTabMoney.setVisibility(View.GONE);
            mBinding.layoutMainButtom.radioMainTabCertification.setVisibility(View.GONE);

        } else if (TextUtils.equals("1", isJT) && TextUtils.equals("1", isFK)) {
            mShowIndex = 0;
            fragments.add(BorrowMoneyFragment.getInstanse());
            fragments.add(CreditFragment.getInstanse());

        } else if (TextUtils.equals("0", isJT) && TextUtils.equals("1", isFK)) {
            mShowIndex = 1;
            fragments.add(new Fragment());
            fragments.add(CreditFragment.getInstanse());
            mBinding.layoutMainButtom.radioMainTabMoney.setVisibility(View.GONE);

        } else if (TextUtils.equals("1", isJT) && TextUtils.equals("0", isFK)) {
            mShowIndex = 0;
            fragments.add(BorrowMoneyFragment.getInstanse());
            fragments.add(new Fragment());
            mBinding.layoutMainButtom.radioMainTabCertification.setVisibility(View.GONE);
        }

        fragments.add(MyFragment.getInstanse());

        mBinding.pagerMain.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        mBinding.pagerMain.setOffscreenPageLimit(fragments.size());
        setShowIndex(mShowIndex);

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

    @Override
    protected void onResume() {
        super.onResume();
        //如果这个是  没有借条功能的话  就判断  有没有登陆 没有登陆直接就让去登陆
        if (!TextUtils.equals("1", isJT)) {
            if (!SPUtilHelpr.isLogin(this, false)) {
                setTabIndex();
                UITipDialog.showSuccess(this, "请先登陆", dialog -> {
                    SPUtilHelpr.isLogin(this, false);
                });
                return;
            }
        }
    }


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


    /**
     * 获取支付宝的付款码信息
     */
    private void getQRImage() {
        HashMap<String, String> map = new HashMap<>();
        map.put("companyCode", MyConfig.COMPANYCODE);
        Call<BaseResponseModel<RepaymentQRBean>> repaymentQR = RetrofitUtils.createApi(MyApiServer.class).getRepaymentQR("623218", StringUtils.getJsonToString(map));
        addCall(repaymentQR);
        showLoadingDialog();
        repaymentQR.enqueue(new BaseResponseModelCallBack<RepaymentQRBean>(MainActivity.this) {
            @Override
            protected void onSuccess(RepaymentQRBean data, String SucMessage) {

                BitmapUtils.getImage(MainActivity.this,SPUtilHelpr.getQiNiuUrl() + data.getPict(), new BitmapUtils.HttpCallBackListener() {
                    @Override
                    public void onFinish(Bitmap bitmap) {
                        scannedQR(bitmap);
                    }

                    @Override
                    public void onError(Exception e) {
                        UITipDialog.showFall(MainActivity.this, "获取还款信息失败");
                    }
                });
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    public void scannedQR(Bitmap bitmap) {
//        Bitmap mBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
//        Bitmap bitmap = BitmapUtils.drawable2Bitmap(getResources().getDrawable(R.drawable.pay_qr));
        ZxingUtils.analyzeBitmap(bitmap, new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                LogUtil.E("扫描结果:" + result);
                //扫描结果可能是如下的 两种  一个是收款码,一个是普通的个人信息二维码
                //HTTPS://QR.ALIPAY.COM/FKX06440RVTBLYZNHGSHC3?t=1547606011183
                //HTTPS://QR.ALIPAY.COM/FKX06440RVTBLYZNHGSHC3
                if (TextUtils.isEmpty(result) || !result.contains("HTTPS://QR.ALIPAY.COM/")) {
                    UITipDialog.showFall(MainActivity.this, "后台配置出错,请联系管理员");
                    return;
                }
                //截取出来支付宝用户的id
                String ailiID = result.substring("HTTPS://QR.ALIPAY.COM/".length() - 1, result.contains("?") ? result.indexOf("?") : result.length());
                startZFB(ailiID);
            }

            @Override
            public void onAnalyzeFailed() {
                LogUtil.E("扫描失败:");
            }
        });
    }

    public void startZFB(String urlCode) {
        String URL_FORMAT =
                "intent://platformapi/startapp?saId=10000007&" +
                        "clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F{urlCode}%3F_s" +
                        "%3Dweb-other&_t=1472443966571#Intent;" + "scheme=alipayqr;package=com.eg.android.AlipayGphone;end";
        IsInstallWeChatOrAliPay.startZFBIntentUrl(this, URL_FORMAT.replace("{urlCode}", urlCode));
    }

}
