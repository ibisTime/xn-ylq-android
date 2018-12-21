package com.cdkj.ylq.module.user.userinfo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.BitmapUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.PermissionHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityShellBinding;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

/**
 * 分享二维码展示和下载到本地
 */
public class ShellActivity extends AbsBaseActivity {
    private ActivityShellBinding mBinding;
    private String shellUrl;
    private Bitmap qrImg;
    private PermissionHelper mPermissionHelper;

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
        mPermissionHelper = new PermissionHelper(this);
        initData();
        initLenesr();
    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "domainUrl");
//        map.put("systemCode", MyConfig.COMPANYCODE);
//        map.put("companyCode", MyConfig.SYSTEMCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("companyCode", MyConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("623917", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(this) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                shellUrl = data.getCvalue() + "/user/register.html?companyCode=" + MyConfig.COMPANYCODE + "&userRefereeKind=C&userReferee=" + SPUtilHelpr.getUserId();
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
            CopyActivity.open(this, shellUrl);
        });
        mBinding.llSaveLoad.setOnClickListener(v -> {
//            if (qrImg != null) {
//                BitmapUtils.saveImageToGallery(this, qrImg, "qr.jpg");
//                ToastUtil.show(this, "成功保存至相册");
//            } else {
//                ToastUtil.show(this, "数据获取失败,请重新获取");
//            }
            permissionRequestAndSaveBitmap();

        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null) {
            mPermissionHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 申请权限
     */
    private void permissionRequestAndSaveBitmap() {
        mPermissionHelper.requestPermissions(new PermissionHelper.PermissionListener() {
            @Override
            public void doAfterGrand(String... permission) {
                saveBitmapToAlbum();
            }

            @Override
            public void doAfterDenied(String... permission) {

                ToastUtil.show(ShellActivity.this, "权限申请失败");
            }

        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    /**
     * 保存图片到相册
     */
    private void saveBitmapToAlbum() {
//        getBitmapByView(mBinding.llRoot);

        //                        if (qrImg != null) {
//                            BitmapUtils.saveImageToGallery(this, qrImg, "qr.jpg");
//                            ToastUtil.show(this, "成功保存至相册");
//                        } else {
//                            ToastUtil.show(this, "数据获取失败,请重新获取");
//                        }

        mBinding.llRoot.post(() -> {
            showLoadingDialog();
            mBinding.llCopy.setVisibility(View.GONE);
            mBinding.llSaveLoad.setVisibility(View.GONE);
            mSubscription.add(Observable.just("")
                    .observeOn(AndroidSchedulers.mainThread())  //创建
                    .map(o -> getBitmapByView(mBinding.llRoot))
                    .observeOn(Schedulers.newThread())  //创建
//                    .map(bitmap-> BitmapUtils.saveBitmapFile(bitmap, ""))
                    .map(new Function<Bitmap, String>() {
                        @Override
                        public String apply(Bitmap bitmap) throws Exception {
                            if (bitmap != null) {
                                BitmapUtils.saveImageToGallery(ShellActivity.this, bitmap, "qr");
//                                ToastUtil.show(ShellActivity.this, "成功保存至相册");
                            } else {
                                ToastUtil.show(ShellActivity.this, "数据获取失败,请重新获取");
                            }
                            return "";
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(path -> {
                        mBinding.llSaveLoad.setVisibility(View.VISIBLE);
                        mBinding.llCopy.setVisibility(View.VISIBLE);
                        ShellActivity.this.disMissLoading();
                        ToastUtil.show(ShellActivity.this, "成功保存至相册");

                    }, throwable -> {
                        mBinding.llSaveLoad.setVisibility(View.VISIBLE);
                        mBinding.llCopy.setVisibility(View.VISIBLE);
                        disMissLoading();
                        ToastUtil.show(ShellActivity.this, "图片保存失败");
                        LogUtil.E("图片保存失败" + throwable);
                    }));
        });
    }


    /**
     * 截取scrollview的生产bitmap
     *
     * @param scrollView
     * @return
     */
    public Bitmap getBitmapByView(LinearLayout scrollView) {

        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
//        for (int i = 0; i < scrollView.getChildCount(); i++) {
//            if (scrollView.getChildAt(i).getVisibility() != View.VISIBLE) {
//                continue;
//            }
//            h += scrollView.getChildAt(i).getHeight();
//        }
//        scrollView.measure(1, 1);
        h = scrollView.getMeasuredHeight();
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);

        return bitmap;
    }

}
