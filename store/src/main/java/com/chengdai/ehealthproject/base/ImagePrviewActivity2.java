package com.chengdai.ehealthproject.base;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.dialog.LoadingDialog;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.common.model.activitys.ImageSelectActivity;
import com.chengdai.ehealthproject.model.common.model.adapters.ImagePreviewAdapter2;
import com.chengdai.ehealthproject.uitls.AppUtils;
import com.chengdai.ehealthproject.uitls.LogUtil;
import com.chengdai.ehealthproject.uitls.PermissionHelper;
import com.chengdai.ehealthproject.uitls.ToastUtil;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.lzy.ninegrid.ImageInfo;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class ImagePrviewActivity2 extends Activity implements ViewTreeObserver.OnPreDrawListener {

    public static final String IMAGE_INFO = "IMAGE_INFO";
    public static final String CURRENT_ITEM = "CURRENT_ITEM";
    public static final int ANIMATE_DURATION = 200;

    private LinearLayout rootView;

    private ImagePreviewAdapter2 imagePreviewAdapter;
    private List<ImageInfo> imageInfo;
    private int currentItem;
    private int imageHeight;
    private int imageWidth;
    private int screenWidth;
    private int screenHeight;

    private PermissionHelper mHelper;

    protected CompositeDisposable mSubscription;

    public static void open(Activity context, List<String> imageUrls, int index) {

        List<ImageInfo> imageInfo = new ArrayList<ImageInfo>();
        for (String s : imageUrls) {
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl(s);
            info.setBigImageUrl(s);
            imageInfo.add(info);
        }

        Intent intent = new Intent(context, ImagePrviewActivity2.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagePrviewActivity2.IMAGE_INFO, (Serializable) imageInfo);
        bundle.putInt(ImagePrviewActivity2.CURRENT_ITEM, index);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_preview2);
        mHelper = new PermissionHelper(this);
        mSubscription = new CompositeDisposable();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        final TextView tv_pager = (TextView) findViewById(R.id.tv_pager);
        final TextView tv_save = (TextView) findViewById(R.id.tv_save);
        rootView = (LinearLayout) findViewById(R.id.rootView);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;
        screenHeight = metric.heightPixels;

        Intent intent = getIntent();
        imageInfo = (List<ImageInfo>) intent.getSerializableExtra(IMAGE_INFO);
        currentItem = intent.getIntExtra(CURRENT_ITEM, 0);

        imagePreviewAdapter = new ImagePreviewAdapter2(this, imageInfo);
        viewPager.setAdapter(imagePreviewAdapter);
        viewPager.setCurrentItem(currentItem);
        viewPager.getViewTreeObserver().addOnPreDrawListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                tv_pager.setText(String.format(getString(R.string.select), currentItem + 1, imageInfo.size()));
            }
        });
        tv_pager.setText(String.format(getString(R.string.select), currentItem + 1, imageInfo.size()));

        tv_save.setOnClickListener(v -> {       //保存图片

            //手机读写权限申请
            mHelper.requestPermissions(new PermissionHelper.PermissionListener() {
                @Override
                public void doAfterGrand(String... permission) {

                    LoadingDialog loadingDialog = new LoadingDialog(ImagePrviewActivity2.this);
                    loadingDialog.showDialog();
                    ToastUtil.show(ImagePrviewActivity2.this, "图片保存中");
                    mSubscription.add(Observable.just(imageInfo.get(currentItem).getBigImageUrl())
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .observeOn(Schedulers.newThread())
                            .map(s ->
                                    Glide.with(ImagePrviewActivity2.this)
                                            .load(s)
                                            .asBitmap()
                                            .into(400, 400)
                                            .get()
                            )
                            .observeOn(Schedulers.io())
                            .map(bitmap -> AppUtils.saveFile(bitmap, "luntan"))
                            .observeOn(AndroidSchedulers.mainThread())
                            .doFinally(() -> {
                                loadingDialog.closeDialog();
                            })
                            .subscribe(s -> {
                                ToastUtil.show(ImagePrviewActivity2.this, "图片保存成功");
                                // 最后通知图库更新
                                try {
                                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                            Uri.fromFile(new File(s))));
                                } catch (Exception e) {
                                }

                            }, throwable -> {
                                ToastUtil.show(ImagePrviewActivity2.this, "图片保存失败");
                                LogUtil.E("保存图片错误" + throwable);
                            }));
                }

                @Override
                public void doAfterDenied(String... permission) {
                    new CommonDialog(ImagePrviewActivity2.this).builder()
                            .setTitle("系统提示")
                            .setContentMsg("未取得您的存储空间使用权限，图片无法保存,请授予存储权限")
                            .setPositiveBtn("我知道了", null).show();
                }
            }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);

        });

    }

    //权限处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onBackPressed() {
        finishActivityAnim();
    }

    @Override
    protected void onDestroy() {
        if(mSubscription!=null){
            mSubscription.clear();
            mSubscription.dispose();
        }
        super.onDestroy();
    }

    /**
     * 绘制前开始动画
     */
    @Override
    public boolean onPreDraw() {
        rootView.getViewTreeObserver().removeOnPreDrawListener(this);
        final View view = imagePreviewAdapter.getPrimaryItem();
        final ImageView imageView = imagePreviewAdapter.getPrimaryImageView();
        computeImageWidthAndHeight(imageView);

        final ImageInfo imageData = imageInfo.get(currentItem);
        final float vx = imageData.imageViewWidth * 1.0f / imageWidth;
        final float vy = imageData.imageViewHeight * 1.0f / imageHeight;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                long duration = animation.getDuration();
                long playTime = animation.getCurrentPlayTime();
                float fraction = duration > 0 ? (float) playTime / duration : 1f;
                if (fraction > 1) fraction = 1;
                view.setTranslationX(evaluateInt(fraction, imageData.imageViewX + imageData.imageViewWidth / 2 - imageView.getWidth() / 2, 0));
                view.setTranslationY(evaluateInt(fraction, imageData.imageViewY + imageData.imageViewHeight / 2 - imageView.getHeight() / 2, 0));
                view.setScaleX(evaluateFloat(fraction, vx, 1));
                view.setScaleY(evaluateFloat(fraction, vy, 1));
                view.setAlpha(fraction);
                rootView.setBackgroundColor(evaluateArgb(fraction, Color.TRANSPARENT, Color.BLACK));
            }
        });
        addIntoListener(valueAnimator);
        valueAnimator.setDuration(ANIMATE_DURATION);
        valueAnimator.start();
        return true;
    }

    /**
     * activity的退场动画
     */
    public void finishActivityAnim() {
        final View view = imagePreviewAdapter.getPrimaryItem();
        final ImageView imageView = imagePreviewAdapter.getPrimaryImageView();
        computeImageWidthAndHeight(imageView);

        final ImageInfo imageData = imageInfo.get(currentItem);
        final float vx = imageData.imageViewWidth * 1.0f / imageWidth;
        final float vy = imageData.imageViewHeight * 1.0f / imageHeight;
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                long duration = animation.getDuration();
                long playTime = animation.getCurrentPlayTime();
                float fraction = duration > 0 ? (float) playTime / duration : 1f;
                if (fraction > 1) fraction = 1;
                view.setTranslationX(evaluateInt(fraction, 0, imageData.imageViewX + imageData.imageViewWidth / 2 - imageView.getWidth() / 2));
                view.setTranslationY(evaluateInt(fraction, 0, imageData.imageViewY + imageData.imageViewHeight / 2 - imageView.getHeight() / 2));
                view.setScaleX(evaluateFloat(fraction, 1, vx));
                view.setScaleY(evaluateFloat(fraction, 1, vy));
                view.setAlpha(1 - fraction);
                rootView.setBackgroundColor(evaluateArgb(fraction, Color.BLACK, Color.TRANSPARENT));
            }
        });
        addOutListener(valueAnimator);
        valueAnimator.setDuration(ANIMATE_DURATION);
        valueAnimator.start();
    }

    /**
     * 计算图片的宽高
     */
    private void computeImageWidthAndHeight(ImageView imageView) {

        // 获取真实大小
        Drawable drawable = imageView.getDrawable();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        // 计算出与屏幕的比例，用于比较以宽的比例为准还是高的比例为准，因为很多时候不是高度没充满，就是宽度没充满
        float h = screenHeight * 1.0f / intrinsicHeight;
        float w = screenWidth * 1.0f / intrinsicWidth;
        if (h > w) h = w;
        else w = h;

        // 得出当宽高至少有一个充满的时候图片对应的宽高
        imageHeight = (int) (intrinsicHeight * h);
        imageWidth = (int) (intrinsicWidth * w);
    }

    /**
     * 进场动画过程监听
     */
    private void addIntoListener(ValueAnimator valueAnimator) {
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                rootView.setBackgroundColor(0x0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    /**
     * 退场动画过程监听
     */
    private void addOutListener(ValueAnimator valueAnimator) {
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                rootView.setBackgroundColor(0x0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    /**
     * Integer 估值器
     */
    public Integer evaluateInt(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (startInt + fraction * (endValue - startInt));
    }

    /**
     * Float 估值器
     */
    public Float evaluateFloat(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }

    /**
     * Argb 估值器
     */
    public int evaluateArgb(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return (startA + (int) (fraction * (endA - startA))) << 24//
                | (startR + (int) (fraction * (endR - startR))) << 16//
                | (startG + (int) (fraction * (endG - startG))) << 8//
                | (startB + (int) (fraction * (endB - startB)));
    }
}
