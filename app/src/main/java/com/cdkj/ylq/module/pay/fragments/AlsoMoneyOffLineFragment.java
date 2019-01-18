package com.cdkj.ylq.module.pay.fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseFragment;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.BitmapUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.FragmentOfflineAlsomoneyBinding;
import com.cdkj.ylq.model.RepaymentQRBean;
import com.cdkj.ylq.model.UseMoneyRecordModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.utils.IsInstallWeChatOrAliPay;
import com.cdkj.ylq.utils.ZxingUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 还钱 线下
 * Created by 李先俊 on 2017/9/6.
 */

public class AlsoMoneyOffLineFragment extends BaseFragment {

    private FragmentOfflineAlsomoneyBinding mBinding;
    private String mCode;
    private UseMoneyRecordModel.ListBean.Info info;

    /**
     * @param code  还款编号
     * @param money 还款金额
     * @return
     */
    public static AlsoMoneyOffLineFragment getInstanse(String code, String money) {
        AlsoMoneyOffLineFragment fragment = new AlsoMoneyOffLineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("money", money);
        bundle.putString("code", code);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static AlsoMoneyOffLineFragment getInstanse(UseMoneyRecordModel.ListBean.Info info) {
        AlsoMoneyOffLineFragment fragment = new AlsoMoneyOffLineFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", info);
        fragment.setArguments(bundle);
        return fragment;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_offline_alsomoney, null, false);

//        getKeyData();

        if (getArguments() != null) {
            info = (UseMoneyRecordModel.ListBean.Info) getArguments().getSerializable("info");
            if (info != null) {
//                mBinding.llQs.setVisibility(View.VISIBLE);
//                mBinding.tvQs.setText(info.getRemark());
                mBinding.tvPayMoney.setText("¥" + MoneyUtils.showPrice(info.getAmount()));
            } else {
                mCode = getArguments().getString("code");
                mBinding.tvPayMoney.setText("¥" + getArguments().getString("money"));
            }

        }
        initListener();
        return mBinding.getRoot();
    }

    private void initListener() {
        mBinding.ivBack.setOnClickListener(v -> mActivity.finish());

        mBinding.btnSure.setOnClickListener(v -> {

            boolean isInstallZFB = IsInstallWeChatOrAliPay.checkAliPayInstalled(mActivity);
            if (isInstallZFB) {
                getQRImage();
            } else {
                UITipDialog.showFall(mActivity, "请先安装支付宝");
            }
//            CommonDialog commonDialog = new CommonDialog(mActivity).builder()
//                    .setTitle("提示").setContentMsg("确认已经进行打款？")
//                    .setPositiveBtn("确定", view -> {
//
////                        getQRImage();
////                        scannedQR();
////                        payRequest();
//                    })
//                    .setNegativeBtn("取消", null, false);
//            commonDialog.show();
        });
    }

    /**
     * 线下还款   (意思就是  私底下转给放款人,这个app就是调用一下接口,申请一下)
     * <p>
     * 分为  分期和不分期  调用接口不同  根据是否有分期的对象来判断是否该进行分期
     */
    private void payRequest(Bitmap bitmap) {
        Map<String, String> map = new HashMap<>();
        String code;
        if (!TextUtils.isEmpty(mCode)) {
            code = "623180";
            map.put("orderCode", mCode);
        } else if (info != null) {
            code = "623182";
            map.put("stagingCode", info.getStageCode());
        } else {
            return;
        }
        Call call = RetrofitUtils.getBaseAPiService().stringRequest(code, StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<String>(mActivity) {
            @Override
            protected void onSuccess(String data, String SucMessage) {
                EventBus.getDefault().post(EventTags.ALSOOFFLINE);
                scannedQR(bitmap);
            }

//            @Override
//            protected void onReqFailure(int errorCode, String errorMessage) {
//                //报错也不提示  toast
//                super.onReqFailure(errorCode, errorMessage);
//
//            }

            @Override
            protected void onBuinessFailure(String code, String error) {
                //即便是有一条订单了  也不提示
//                super.onBuinessFailure(code, error);
                if (info != null) {
                    //如果是分期  没到还款日   不能进行还款
                    if ("此次分期还未到开始还款日期，无法提前还款".equals(error)) {
                        UITipDialog.showFall(mActivity, error);
                    } else {
                        scannedQR(bitmap);
                    }
                } else {
                    scannedQR(bitmap);
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
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
        repaymentQR.enqueue(new BaseResponseModelCallBack<RepaymentQRBean>(mActivity) {
            @Override
            protected void onSuccess(RepaymentQRBean data, String SucMessage) {
                BitmapUtils.getImage(mActivity, SPUtilHelpr.getQiNiuUrl() + data.getPict(), new BitmapUtils.HttpCallBackListener() {
                    @Override
                    public void onFinish(Bitmap bitmap) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                payRequest(bitmap);
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        UITipDialog.showFall(mActivity, "获取还款信息失败");
                    }
                });
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    /**
     * 扫描获取到的图片
     *
     * @param bitmap
     */
    public void scannedQR(Bitmap bitmap) {

//        Bitmap bitmap = BitmapUtils.drawable2Bitmap(getResources().getDrawable(R.drawable.pay_qr));
        ZxingUtils.analyzeBitmap(bitmap, new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                LogUtil.E("扫描结果:" + result);

//                HTTPS://QR.ALIPAY.COM/FKX06440RVTBLYZNHGSHC3?t=1547606011183
//                HTTPS://QR.ALIPAY.COM/FKX06440RVTBLYZNHGSHC3
                //扫描结果可能是  这两种情况

                if (TextUtils.isEmpty(result)) {
                    UITipDialog.showFall(mActivity, "后台配置出错,请联系管理员");
                    return;
                }
                if (result.contains("HTTPS://QR.ALIPAY.COM/")) {
                    String ailiID = result.substring("HTTPS://QR.ALIPAY.COM/".length() - 1, result.contains("?") ? result.indexOf("?") : result.length());
                    startZFB(ailiID);
                }
                if (result.contains("https://qr.alipay.com/")) {
                    String ailiID = result.substring("https://qr.alipay.com/".length() - 1, result.contains("?") ? result.indexOf("?") : result.length());
                    startZFB(ailiID);
                }
                //截取出来支付宝用户的id

            }

            @Override
            public void onAnalyzeFailed() {
                LogUtil.E("扫描失败:");
            }
        });
    }

    /**
     * 跳转支付宝
     *
     * @param urlCode
     */
    public void startZFB(String urlCode) {
        String URL_FORMAT = "intent://platformapi/startapp?saId=10000007&" +
                "clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F{urlCode}%3F_s" +
                "%3Dweb-other&_t=1472443966571#Intent;" + "scheme=alipayqr;package=com.eg.android.AlipayGphone;end";
        IsInstallWeChatOrAliPay.startZFBIntentUrl(mActivity, URL_FORMAT.replace("{urlCode}", urlCode));
    }


    /**
     * 获取配置数据
     * 之前这里显示的是富文本  支付宝付款码
     */
//    public void getKeyData() {
//
//        Map<String, String> map = new HashMap<>();
//        map.put("key", "repayOfflineAccount");
//        map.put("systemCode", MyConfig.SYSTEMCODE);
//        map.put("companyCode", MyConfig.COMPANYCODE);
//
//        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("623917", StringUtils.getJsonToString(map));
//        addCall(call);
//
//        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(mActivity) {
//            @Override
//            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
//                if (TextUtils.isEmpty(data.getCvalue())) {
//                    mBinding.webView.loadData("信息获取失败,请重新获取", "text/html;charset=UTF-8", "UTF-8");
//                    return;
//                }
//                mBinding.webView.loadData(data.getCvalue(), "text/html;charset=UTF-8", "UTF-8");
//            }
//
//            @Override
//            protected void onReqFailure(int errorCode, String errorMessage) {
//                super.onReqFailure(errorCode, errorMessage);
//                mBinding.webView.loadData("信息获取失败,请重新获取", "text/html;charset=UTF-8", "UTF-8");
//            }
//
//            @Override
//            protected void onBuinessFailure(String code, String error) {
//                super.onBuinessFailure(code, error);
//                mBinding.webView.loadData("信息获取失败,请重新获取", "text/html;charset=UTF-8", "UTF-8");
//            }
//
//            @Override
//            protected void onFinish() {
//
//            }
//        });
//    }
}
