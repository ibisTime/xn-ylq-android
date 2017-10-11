package com.cdkj.ylq.module.certification;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.FragmentCertificationBinding;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.model.IsBorrowFlagModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.certification.basisinfocert.BasisInfoCertificationActivity;
import com.cdkj.ylq.module.certification.review.HumanReviewActivity;
import com.cdkj.ylq.mpresenter.GetUserCertificationInfoListener;
import com.cdkj.ylq.mpresenter.GetUserCertificationPresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.moxie.client.manager.MoxieSDK;
import com.moxie.client.model.MxParam;
import com.moxie.client.model.TitleParams;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import retrofit2.Call;

/**
 * 认证
 * Created by 李先俊 on 2017/8/8.
 */
//TODO 通讯录认证移动到个人信息认证  代码、布局只是注释 没删
public class CertificationFragment extends BaseLazyFragment implements GetUserCertificationInfoListener {

    private FragmentCertificationBinding mBinding;

    private CerttificationInfoModel mCertData;//认证结果数据

    private GetUserCertificationPresenter mCertInfoPresenter;//获取认证结果接口

    private boolean isMoxieCallback;//是否执行过魔蝎回调 用户魔蝎回调成功弹框提醒
    private boolean isResumeShow;//用户魔蝎回调认证中弹框提醒

    private CompositeDisposable mMoxieSubscription;//用于魔蝎认证结果轮询 viewPage切换、页面进入后台时停止魔蝎认证结果轮询

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
            if (mCertData == null) return;
            IdCardCertificationActivity.open(mActivity);
        });

        //个人信息认证
        mBinding.layoutBasisInfo.setOnClickListener(v -> {
            if (!SPUtilHelpr.isLogin(mActivity, false)) {
                return;
            }

            if (mCertData == null) return;

            if (!TextUtils.equals("1", mCertData.getInfoIdentifyFlag())) {
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

            if (!TextUtils.equals("1", mCertData.getInfoIdentifyFlag())) {
                ToastUtil.show(mActivity, "请进行身份认证");
                return;
            }
            if (!TextUtils.equals("1", mCertData.getInfoAntifraudFlag())) {
                ToastUtil.show(mActivity, "请进行个人信息认证");
                return;
            }

            ZMScoreGetActivity.open(mActivity, mCertData.getInfoIdentify(), TextUtils.equals("1", mCertData.getInfoZMCreditFlag()));
        });


        //运营商认证
        mBinding.layoutYunyingInfo.setOnClickListener(v -> {
            if (!SPUtilHelpr.isLogin(mActivity, false)) {
                return;
            }
            if (mCertData == null) return;

            if (!TextUtils.equals("1", mCertData.getInfoIdentifyFlag())) {
                ToastUtil.show(mActivity, "请进行身份认证");
                return;
            }
            if (!TextUtils.equals("1", mCertData.getInfoAntifraudFlag())) {
                ToastUtil.show(mActivity, "请进行个人信息认证");
                return;
            }
            if (!TextUtils.equals("1", mCertData.getInfoZMCreditFlag())) {
                ToastUtil.show(mActivity, "请进行芝麻认证");
                return;
            }
            if (TextUtils.equals("1", mCertData.getInfoCarrierFlag())) {
                ToastUtil.show(mActivity, "运营商已经认证");
                return;
            }

            if (TextUtils.equals("3", mCertData.getInfoCarrierFlag())) {
                ToastUtil.show(mActivity, "运营商信息正在认证中,请稍后");
                return;
            }

            getMxApiKEy();
        });


        mBinding.imgCalcelCert.setOnClickListener(v -> {
            mBinding.layoutTips.setVisibility(View.GONE);
        });

        //通讯录认证
//        mBinding.layoutPhoneCert.setOnClickListener(v -> {
//            if (!SPUtilHelpr.isLogin(mActivity, false)) {
//                return;
//            }
//            AddressBookCertActivity.open(mActivity);
//        });
    }

    /**
     * 获取魔蝎apikey
     */
    public void getMxApiKEy() {

        Map<String, String> map = new HashMap<>();
        map.put("key", "mxApiKey");
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("companyCode", MyConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("623917", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(mActivity) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                if (TextUtils.isEmpty(data.getCvalue())) {
                    return;
                }
                openMoxie(data.getCvalue());
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    public void openMoxie(String mApiKey) {
        //合作方系统中的客户ID
        String mUserId = SPUtilHelpr.getUserId();
        //获取任务状态时使用(合作方申请接入后由魔蝎数据提供)
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

        if (mCertData != null && mCertData.getInfoIdentify() != null) {
            HashMap<String, String> extendParam = new HashMap<String, String>();
            extendParam.put(MxParam.PARAM_CARRIER_IDCARD, mCertData.getInfoIdentify().getIdNo()); // 身份证
            extendParam.put(MxParam.PARAM_CARRIER_PHONE, SPUtilHelpr.getUserPhoneNum()); // 手机号
            extendParam.put(MxParam.PARAM_CARRIER_NAME, mCertData.getInfoIdentify().getRealName()); // 姓名
            extendParam.put(MxParam.PARAM_CARRIER_EDITABLE, MxParam.PARAM_COMMON_NO); // 是否允许用户修改以上信息
            mxParam.setExtendParams(extendParam);
        }
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
        if (resultCode != Activity.RESULT_OK) {  //魔蝎回调 获取认证结果
            ToastUtil.show(mActivity, "请重试运营商认证");
            return;
        }
        Bundle b = data.getExtras();
        String result = b.getString("result");

        if (TextUtils.isEmpty(result)) {
            ToastUtil.show(mActivity, "请重试运营商认证");
            return;
        }

        try {
            int code = 0;
            JSONObject jsonObject = new JSONObject(result);
            code = jsonObject.getInt("code");
            if (code == 1) {
                //根据taskType进行对应的处理
                switch (jsonObject.getString("taskType")) {
                    case MxParam.PARAM_FUNCTION_CARRIER: //成功
                        moXieCallBack();
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

    }

    /**
     * 魔蝎认证回调处理
     */
    private void moXieCallBack() {
        showLoadingDialog();   //进行认证结果请求之后才会消失
        mSubscription.add(Observable.timer(6, TimeUnit.SECONDS)//延迟进行请求
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    // getMoxieRequest(jsonObject.getString("taskId"));
                    if (mCertInfoPresenter != null) {      //获取认证结果
                        isMoxieCallback = true;
                        isResumeShow = true;
                        mCertInfoPresenter.getCertInfo(true);
                    } else {
                        disMissLoading();
                    }

                }, throwable -> {
                    disMissLoading();
                }));
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
                    isMoxieCallback = true;
                    isResumeShow = true;
                    mCertInfoPresenter.getCertInfo(true);  //获取认证结果
                } else {
                    disMissLoading();
                }
            }
        });

    }

    /**
     * 设置认证状态数据显示
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

            mMoxieSubscription.dispose();//认证成功停止结果查询轮询

            mBinding.tvMoxieState.setText("已认证");
            mBinding.tvMoxieState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_ok));
            mBinding.imgMoxieState.setImageResource(R.drawable.cert_ok);
            mBinding.imgMoxieStateBig.setImageResource(R.drawable.yuying);

            if (isMoxieCallback) { //如果是魔蝎回调结果查询
                isMoxieCallback = false;//重置
                if (mActivity == null || mActivity.isFinishing()) {
                    return;
                }
                CommonDialog commonDialog = new CommonDialog(mActivity).builder()
                        .setTitle("提示").setContentMsg("运营商认证成功")
                        .setPositiveBtn("确定", view -> {
                            getIsBorrowFlag();
                        });
                commonDialog.getContentView().setGravity(Gravity.CENTER);
                commonDialog.show();
            }

        } else if (TextUtils.equals("2", mCertData.getInfoCarrierFlag())) {

            mMoxieSubscription.dispose();//认证已过期停止结果查询轮询

            mBinding.tvMoxieState.setText("已过期");
            mBinding.tvMoxieState.setTextColor(ContextCompat.getColor(mActivity, R.color.guoqi));
            mBinding.imgMoxieState.setImageResource(R.drawable.guoqi);
            mBinding.imgMoxieStateBig.setImageResource(R.drawable.yunying_un);
        } else if (TextUtils.equals("3", mCertData.getInfoCarrierFlag())) {
            mBinding.tvMoxieState.setText("认证中");
            mBinding.tvMoxieState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_edit));
            mBinding.imgMoxieState.setImageResource(R.drawable.can_submit);
            mBinding.imgMoxieStateBig.setImageResource(R.drawable.yunying_un);
            if (isResumeShow) {
                ToastUtil.show(mActivity, "运营商数据正在认证，请稍后");
                isResumeShow = false;
            }

            mMoxieSubscription.dispose();//结果查询轮询
            mMoxieSubscription = new CompositeDisposable();
            mMoxieSubscription.add(Observable.interval(5, TimeUnit.SECONDS)    // 轮询
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        isMoxieCallback = true;
                        mCertInfoPresenter.getCertInfo(false);
                        LogUtil.E("魔蝎认证结果轮询");
                    }, Throwable::printStackTrace));

        } else {
            mBinding.tvMoxieState.setText("前往提交");
            mBinding.tvMoxieState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_edit));
            mBinding.imgMoxieState.setImageResource(R.drawable.can_submit);
            mBinding.imgMoxieStateBig.setImageResource(R.drawable.yunying_un);
        }


//        if (TextUtils.equals("1", mCertData.getInfoAddressBookFlag())) { //通讯录认证
//            mBinding.tvAddbookState.setText("已认证");
//            mBinding.tvAddbookState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_ok));
//            mBinding.imgAddbookState.setImageResource(R.drawable.cert_ok);
//            mBinding.imgAddBookStateBig.setImageResource(R.drawable.phone_cert);
//
//        } else if (TextUtils.equals("2", mCertData.getInfoAddressBookFlag())) {
//            mBinding.tvAddbookState.setText("已过期");
//            mBinding.tvAddbookState.setTextColor(ContextCompat.getColor(mActivity, R.color.guoqi));
//            mBinding.imgAddbookState.setImageResource(R.drawable.guoqi);
//            mBinding.imgAddBookStateBig.setImageResource(R.drawable.phone_cert_un);
//        } else {
//            mBinding.tvAddbookState.setText("前往提交");
//            mBinding.tvAddbookState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_edit));
//            mBinding.imgAddbookState.setImageResource(R.drawable.can_submit);
//            mBinding.imgAddBookStateBig.setImageResource(R.drawable.phone_cert_un);
//        }

    }


    public void getIsBorrowFlag() {
        if (!SPUtilHelpr.isLoginNoStart()) {
            return;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", SPUtilHelpr.getUserId());
        Call call = RetrofitUtils.createApi(MyApiServer.class).getIsBorrowFlag("623091", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<IsBorrowFlagModel>(mActivity) {
            @Override
            protected void onSuccess(IsBorrowFlagModel data, String SucMessage) {
                if (TextUtils.equals("1", data.getIsBorrowFlag())) {
                    HumanReviewActivity.open(mActivity);
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
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
        mCertInfoPresenter = new GetUserCertificationPresenter(this);
        mMoxieSubscription = new CompositeDisposable();
        return mBinding.getRoot();
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
        isResumeShow = false;
        if (mMoxieSubscription != null) {
            mMoxieSubscription.dispose();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && mCertInfoPresenter != null && mBinding != null) {
            isResumeShow = true;
            mCertInfoPresenter.getCertInfo(false);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMoxieSubscription != null) {
            mMoxieSubscription.dispose();
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
