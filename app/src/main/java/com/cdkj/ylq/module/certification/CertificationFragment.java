package com.cdkj.ylq.module.certification;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.FragmentCertificationBinding;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.module.certification.basisinfocert.BasisInfoCertificationActivity;
import com.cdkj.ylq.mpresenter.getUserCertificationInfoListener;
import com.cdkj.ylq.mpresenter.getUserCertificationPresenter;
import com.moxie.client.manager.MoxieSDK;
import com.moxie.client.model.MxParam;
import com.moxie.client.model.TitleParams;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Call;

/**
 * 认证
 * Created by 李先俊 on 2017/8/8.
 */

public class CertificationFragment extends BaseLazyFragment implements getUserCertificationInfoListener {

    private FragmentCertificationBinding mBinding;

    private CerttificationInfoModel mCertData;

    private getUserCertificationPresenter mCertInfoPresenter;


    /**
     * 获得fragment实例
     *
     * @return
     */
    public static CertificationFragment getInstanse() {
        CertificationFragment fragment = new CertificationFragment();
        return fragment;
    }

    private void initListener() {

        //身份认证
        mBinding.layoutIdcardCert.setOnClickListener(v -> {
            if (!SPUtilHelpr.isLogin(mActivity, false)) {
                return;
            }
            IdCardCertificationActivity.open(mActivity);
        });

        //个人信息认证
        mBinding.layoutBasisInfo.setOnClickListener(v -> {
            if (!SPUtilHelpr.isLogin(mActivity, false)) {
                return;
            }

            if (mCertData == null) return;

            if (TextUtils.equals("0", mCertData.getInfoIdentifyFlag())) {
                ToastUtil.show(mActivity, "请进行身份认证");
                return;
            }
            BasisInfoCertificationActivity.open(mActivity);
        });


        //芝麻分数获取
        mBinding.layoutZmCert.setOnClickListener(v -> {
            if (!SPUtilHelpr.isLogin(mActivity, false)) {
                return;
            }
            if (mCertData == null) return;

            if (TextUtils.equals("0", mCertData.getInfoIdentifyFlag())) {
                ToastUtil.show(mActivity, "请进行身份认证");
                return;
            }
            if (TextUtils.equals("0", mCertData.getInfoAntifraudFlag())) {
                ToastUtil.show(mActivity, "请进行个人信息认证");
                return;
            }

            ZMScoreGetActivity.open(mActivity, mCertData.getInfoIdentify());
        });


        //运营商认证
        mBinding.layoutYunyingInfo.setOnClickListener(v -> {
            if (!SPUtilHelpr.isLogin(mActivity, false)) {
                return;
            }
            if (mCertData == null) return;


            if (TextUtils.equals("0", mCertData.getInfoIdentifyFlag())) {
                ToastUtil.show(mActivity, "请进行身份认证");
                return;
            }
            if (TextUtils.equals("0", mCertData.getInfoAntifraudFlag())) {
                ToastUtil.show(mActivity, "请进行个人信息认证");
                return;
            }
            if (TextUtils.equals("0", mCertData.getInfoZMCreditFlag())) {
                ToastUtil.show(mActivity, "请进行芝麻认证");
                return;
            }

            openMoxie();
        });


        mBinding.imgCalcelCert.setOnClickListener(v -> {
            mBinding.layoutTips.setVisibility(View.GONE);
        });

        //通讯录认证
        mBinding.layoutPhoneCert.setOnClickListener(v -> {
            if (!SPUtilHelpr.isLogin(mActivity, false)) {
                return;
            }
            AddressBookCertActivity.open(mActivity);

        });
    }


    @Override
    protected void lazyLoad() {
        if (mBinding != null) {
            if (mCertInfoPresenter != null) {
                mCertInfoPresenter.getCertInfo(false);
            }
        }
    }

    @Override
    protected void onInvisible() {

    }

    public void openMoxie() {
        //合作方系统中的客户ID
        String mUserId = SPUtilHelpr.getUserId();
        //获取任务状态时使用(合作方申请接入后由魔蝎数据提供)
        String mApiKey = "96ee985a972a4685be2bb423588e008f";
        String mBannerTxtContent = "运营商认证"; //SDK里title的文字描述
        String mThemeColor = "#ff6702"; //SDK里页面主色调
        String mAgreementUrl = ""; //SDK里显示的用户使用协议
        MxParam mxParam = new MxParam();
        mxParam.setUserId(mUserId);
        mxParam.setApiKey(mApiKey);
        mxParam.setBannerTxtContent(mBannerTxtContent); // SDK里title的文字描述
        mxParam.setThemeColor(mThemeColor);  // SDK里页面主色调
        mxParam.setAgreementUrl(mAgreementUrl); // SDK里显示的用户使用协议
        mxParam.setFunction(MxParam.PARAM_FUNCTION_CARRIER); // 功能名
        mxParam.setQuitOnFail(MxParam.PARAM_COMMON_NO); // 爬取失败时是否退出SDK(登录阶段之后)
        mxParam.setAgreementEntryText("同意《使用协议》"); // SDK里显示的同意协议描述语
        mxParam.setLoadingViewText("验证过程中不会浪费您任何流量\n请稍等片刻");  //设置导入过程中的自定义提示文案，为居中显示
        mxParam.setQuitDisable(true); //设置导入过程中，触发返回键或者点击actionbar的返回按钮的时候，不执行魔蝎的默认行为

        HashMap<String, String> extendParam = new HashMap<String, String>();
        extendParam.put(MxParam.PARAM_CARRIER_IDCARD, mCertData.getInfoIdentify().getIdNo()); // 身份证
        extendParam.put(MxParam.PARAM_CARRIER_PHONE, SPUtilHelpr.getUserPhoneNum()); // 手机号
        extendParam.put(MxParam.PARAM_CARRIER_NAME, mCertData.getInfoIdentify().getRealName()); // 姓名
//        extendParam.put(MxParam.PARAM_CARRIER_PASSWORD, "123456"); // 密码
        extendParam.put(MxParam.PARAM_CARRIER_EDITABLE, MxParam.PARAM_COMMON_YES); // 是否允许用户修改以上信息
        mxParam.setExtendParams(extendParam);

        //设置title
        TitleParams titleParams = new TitleParams.Builder()
                //不设置此方法会默认使用魔蝎的icon
                .leftNormalImgResId(R.drawable.back_img)
                //用于设置selector，表示按下的效果，不设置默认使用leftNormalImgResId()设置的图片
                .leftPressedImgResId(R.drawable.moxie_client_banner_back_black)
                .titleColor(getContext().getResources().getColor(R.color.white))
//                    .backgroundColor(getContext().getResources().getColor(R.color.colorAccent))
                .backgroundDrawable(R.color.title_bg)
                .rightNormalImgResId(R.drawable.refresh)
                .immersedEnable(true)
                .build();

        mxParam.setTitleParams(titleParams);
        Bundle bundle = new Bundle();
        bundle.putParcelable("param", mxParam);
        Intent intent = new Intent(mActivity, com.moxie.client.MainActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {  //魔蝎回调
            Bundle b = data.getExtras();
            String result = b.getString("result");
            LogUtil.E("启动" + result);
            if (!TextUtils.isEmpty(result)) {

                try {
                    int code = 0;
                    JSONObject jsonObject = new JSONObject(result);
                    code = jsonObject.getInt("code");
                    if (code == 1) {
                        //根据taskType进行对应的处理
                        switch (jsonObject.getString("taskType")) {
                            case MxParam.PARAM_FUNCTION_CARRIER: //成功
                                showLoadingDialog();   //进行认证结果请求之后才会消失
                                mSubscription.add(Observable.timer(6, TimeUnit.SECONDS)//延迟进行请求
                                        .subscribeOn(AndroidSchedulers.mainThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(aLong -> {
                                            getMoxieRequest(jsonObject.getString("taskId"));
                                        }, throwable -> {
                                            disMissLoading();
                                        }));

                                break;
                            default:
                                ToastUtil.show(mActivity, "请重试运营商认证");
                                break;

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.show(mActivity, "请重试运营商认证");
                }
            } else {
                ToastUtil.show(mActivity, "请重试运营商认证");
            }
        } else {
            ToastUtil.show(mActivity, "请重试运营商认证");
        }
    }

    /**
     * 获取用户认证信息
     */
    public void getMoxieRequest(String taskId) {

        if (!SPUtilHelpr.isLoginNoStart() && TextUtils.isEmpty(taskId)) {
            disMissLoading();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("taskId", taskId);
        Call call = RetrofitUtils.getBaseAPiService().stringRequest("623048", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseModelCallBack<String>(mActivity) {
            @Override
            protected void onSuccess(String data, String SucMessage) {
            }

            @Override
            protected void onFinish() {
                if (mCertInfoPresenter != null) {
                    mCertInfoPresenter.getCertInfo(true);  //获取认证结果
                } else {
                    disMissLoading();
                }
            }
        });

    }

    /**
     * 设置数据显示状态
     */
    public void setShowDataState() {
        if (mCertData == null) {
            return;
        }

        if (TextUtils.equals("1", mCertData.getInfoIdentifyFlag())) { //身份认证
            mBinding.tvIdcardState.setText("已认证");
            mBinding.tvIdcardState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_ok));
            mBinding.imgIdcardState.setImageResource(R.drawable.cert_ok);
            mBinding.imgIdcardStateBig.setImageResource(R.drawable.id_cert);

        } else if (TextUtils.equals("2", mCertData.getInfoIdentifyFlag())) {
            mBinding.tvIdcardState.setText("已过期");
            mBinding.imgIdcardState.setImageResource(R.drawable.guoqi);
            mBinding.tvIdcardState.setTextColor(ContextCompat.getColor(mActivity, R.color.guoqi));
            mBinding.imgIdcardStateBig.setImageResource(R.drawable.id_cert_un);
        } else {
            mBinding.tvIdcardState.setText("前往提交");
            mBinding.imgIdcardState.setImageResource(R.drawable.can_submit);
            mBinding.tvIdcardState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_edit));
            mBinding.imgIdcardStateBig.setImageResource(R.drawable.id_cert_un);
        }

        if (TextUtils.equals("1", mCertData.getInfoAntifraudFlag())) { //基本信息认证
            mBinding.tvBasisState.setText("已认证");
            mBinding.tvBasisState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_ok));
            mBinding.imgBasisState.setImageResource(R.drawable.cert_ok);
            mBinding.imgBasisStateBig.setImageResource(R.drawable.myinfocert);

        } else if (TextUtils.equals("2", mCertData.getInfoAntifraudFlag())) {
            mBinding.tvBasisState.setText("已过期");
            mBinding.tvBasisState.setTextColor(ContextCompat.getColor(mActivity, R.color.guoqi));
            mBinding.imgBasisState.setImageResource(R.drawable.guoqi);
            mBinding.imgBasisStateBig.setImageResource(R.drawable.myinfocertun);

        } else {
            mBinding.tvBasisState.setText("前往提交");
            mBinding.tvBasisState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_edit));
            mBinding.imgBasisState.setImageResource(R.drawable.can_submit);
            mBinding.imgBasisStateBig.setImageResource(R.drawable.myinfocertun);
        }

        if (TextUtils.equals("1", mCertData.getInfoZMCreditFlag())) { //芝麻认证
            mBinding.tvZmState.setText("已认证");
            mBinding.tvZmState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_ok));
            mBinding.imgZmState.setImageResource(R.drawable.cert_ok);
            mBinding.imgZmStateBig.setImageResource(R.drawable.zm_cert);

        } else if (TextUtils.equals("2", mCertData.getInfoZMCreditFlag())) {
            mBinding.tvZmState.setText("已过期");
            mBinding.tvZmState.setTextColor(ContextCompat.getColor(mActivity, R.color.guoqi));
            mBinding.imgZmState.setImageResource(R.drawable.guoqi);
            mBinding.imgZmStateBig.setImageResource(R.drawable.zm_cert_un);
        } else {
            mBinding.tvZmState.setText("前往提交");
            mBinding.tvZmState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_edit));
            mBinding.imgZmState.setImageResource(R.drawable.can_submit);
            mBinding.imgZmStateBig.setImageResource(R.drawable.zm_cert_un);
        }

        if (TextUtils.equals("1", mCertData.getInfoCarrierFlag())) { //魔蝎认证
            mBinding.tvMoxieState.setText("已认证");
            mBinding.tvMoxieState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_ok));
            mBinding.imgMoxieState.setImageResource(R.drawable.cert_ok);
            mBinding.imgMoxieStateBig.setImageResource(R.drawable.yuying);

        } else if (TextUtils.equals("2", mCertData.getInfoCarrierFlag())) {
            mBinding.tvMoxieState.setText("已过期");
            mBinding.tvMoxieState.setTextColor(ContextCompat.getColor(mActivity, R.color.guoqi));
            mBinding.imgMoxieState.setImageResource(R.drawable.guoqi);
            mBinding.imgMoxieStateBig.setImageResource(R.drawable.yunying_un);
        } else {
            mBinding.tvMoxieState.setText("前往提交");
            mBinding.tvMoxieState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_edit));
            mBinding.imgMoxieState.setImageResource(R.drawable.can_submit);
            mBinding.imgMoxieStateBig.setImageResource(R.drawable.yunying_un);
        }


        if (TextUtils.equals("1", mCertData.getInfoAddressBookFlag())) { //通讯录认证
            mBinding.tvAddbookState.setText("已认证");
            mBinding.tvAddbookState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_ok));
            mBinding.imgAddbookState.setImageResource(R.drawable.cert_ok);
            mBinding.imgAddBookStateBig.setImageResource(R.drawable.phone_cert);

        } else if (TextUtils.equals("2", mCertData.getInfoAddressBookFlag())) {
            mBinding.tvAddbookState.setText("已过期");
            mBinding.tvAddbookState.setTextColor(ContextCompat.getColor(mActivity, R.color.guoqi));
            mBinding.imgAddbookState.setImageResource(R.drawable.guoqi);
            mBinding.imgAddBookStateBig.setImageResource(R.drawable.phone_cert_un);
        } else {
            mBinding.tvAddbookState.setText("前往提交");
            mBinding.tvAddbookState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_edit));
            mBinding.imgAddbookState.setImageResource(R.drawable.can_submit);
            mBinding.imgAddBookStateBig.setImageResource(R.drawable.phone_cert_un);
        }

    }

    @Override
    public void getInfoSuccess(CerttificationInfoModel userCertInfo, String msg) {
        mCertData = userCertInfo;
        setShowDataState();
    }

    @Override
    public void getInfoFailed(String code, String msg) {
        ToastUtil.show(mActivity, msg);
    }

    @Override
    public void startGetInfo(boolean showDialog) {
        if (showDialog) showLoadingDialog();
    }

    @Override
    public void endGetInfo(boolean showDialog) {
        if (showDialog) disMissLoading();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_certification, null, false);
        initListener();
        mCertInfoPresenter = new getUserCertificationPresenter(this);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && mCertInfoPresenter != null && mBinding != null) {
            mCertInfoPresenter.getCertInfo(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //用来清理数据或解除引用
        MoxieSDK.getInstance().clear();
        if (mCertInfoPresenter != null) {
            mCertInfoPresenter.clear();
        }
    }
}
