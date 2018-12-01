package com.cdkj.ylq.module.user.userinfo;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.BitmapUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityShellBinding;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 分享二维码展示和下载到本地
 */
public class ShellActivity extends AbsBaseActivity {
    private ActivityShellBinding mBinding;
    private String shellUrl;
    private Bitmap qrImg;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ShellActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shell, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setSubLeftImgState(true);
        setTopTitle("推荐");
        initData();
        initLenesr();
    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "domainUrl");
        map.put("systemCode",  MyConfig.COMPANYCODE);
        map.put("companyCode",MyConfig.SYSTEMCODE);

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("623917", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(this) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                shellUrl = data.getCvalue() + "?companyCode=" + MyConfig.SYSTEMCODE + "&userRefereeKind=C&userReferee=" + SPUtilHelpr.getUserId();
                qrImg = CodeUtils.createImage(shellUrl, 400, 400, null);
                mBinding.ivQr.setImageBitmap(qrImg);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


//        NetUtils.getSystemParameter(this, "domainUrl", true, new NetUtils.OnSuccessSystemInterface() {
//
//            @Override
//            public void onSuccessSystem(IntroductionInfoModel data) {
//                // http://m.yiliangqian.com/user/register.html?companyCode=GS2018112119133810071833&userRefereeKind=C&userReferee=U2018120117574407235926
//
//                shellUrl = data.getCvalue() + "?companyCode=" + MyConfig.SYSTEMCODE + "&userRefereeKind=C&userReferee=" + SPUtilHelpr.getUserId();
//                qrImg = CodeUtils.createImage(shellUrl, 400, 400, null);
//                mBinding.ivQr.setImageBitmap(qrImg);
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });

    }

    private void initLenesr() {


        mBinding.tvCopy.setOnClickListener(view -> {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            if (!TextUtils.isEmpty(shellUrl)) {
                cm.setText(shellUrl);
                ToastUtil.show(this, "复制成功，可以发给朋友们了。");
            } else {
                ToastUtil.show(this, "获取失败请重新推荐");
            }

        });
        mBinding.llSaveLoad.setOnClickListener(v -> {
            if (qrImg != null) {
                BitmapUtils.saveImageToGallery(this, qrImg, "qr.jpg");
                ToastUtil.show(this, "成功保存至相册");
            } else {
                ToastUtil.show(this, "数据获取失败,请重新获取");
            }

        });

    }

}
