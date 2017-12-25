package com.cdkj.ylq.module.certification;

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
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.FragmentCertificationBinding;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.model.IsBorrowFlagModel;
import com.cdkj.ylq.module.TdOperatorCertActivity;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.certification.basisinfocert.BasisInfoCertificationActivity;
import com.cdkj.ylq.module.certification.review.HumanReviewActivity;
import com.cdkj.ylq.mpresenter.GetUserCertificationInfoListener;
import com.cdkj.ylq.mpresenter.GetUserCertificationPresenter;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 认证
 * Created by 李先俊 on 2017/8/8.
 */

public class CertificationFragment extends BaseLazyFragment implements GetUserCertificationInfoListener {

    private FragmentCertificationBinding mBinding;

    private CerttificationInfoModel mCertData;//认证结果数据

    private GetUserCertificationPresenter mCertInfoPresenter;//获取认证结果接口


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

            TdOperatorCertActivity.open(mActivity);

        });

        mBinding.imgCalcelCert.setOnClickListener(v -> {
            mBinding.layoutTips.setVisibility(View.GONE);
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

            mBinding.tvMoxieState.setText("已认证");
            mBinding.tvMoxieState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_ok));
            mBinding.imgMoxieState.setImageResource(R.drawable.cert_ok);
            mBinding.imgMoxieStateBig.setImageResource(R.drawable.yuying);

        } else if (TextUtils.equals("2", mCertData.getInfoCarrierFlag())) {
            mBinding.tvMoxieState.setText("已过期");
            mBinding.tvMoxieState.setTextColor(ContextCompat.getColor(mActivity, R.color.guoqi));
            mBinding.imgMoxieState.setImageResource(R.drawable.guoqi);
            mBinding.imgMoxieStateBig.setImageResource(R.drawable.yunying_un);
        } else if (TextUtils.equals("3", mCertData.getInfoCarrierFlag())) {
            mBinding.tvMoxieState.setText("认证中");
            mBinding.tvMoxieState.setTextColor(ContextCompat.getColor(mActivity, R.color.cert_state_edit));
            mBinding.imgMoxieState.setImageResource(R.drawable.can_submit);
            mBinding.imgMoxieStateBig.setImageResource(R.drawable.yunying_un);

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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_certification, null, false);
        initListener();
        mCertInfoPresenter = new GetUserCertificationPresenter(this);
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
        if (mCertInfoPresenter != null) {
            mCertInfoPresenter.clear();
        }
    }
}
