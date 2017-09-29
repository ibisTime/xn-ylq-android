package com.chengdai.ehealthproject.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;


/**
 * 带空页面，错误页面显示的BaseActivity
 */
public abstract class AbsStoreBaseActivity extends BaseStoreActivity {
    protected ViewGroup mErrorView;
    protected ViewGroup mMainView;
    /**
     * 布局文件xml的resId,无需添加标题栏、加载、错误及空页面
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_abs_base_store);
        findViewById(R.id.fram_img_back_store).setOnClickListener(v -> {
            if(canFinish()){
                this.finish();
            }
        });
        mErrorView = (ViewGroup) findViewById(R.id.error_view);
        afterCreate(savedInstanceState);
    }

    /**
     * 能否结束当前页面
     * @return
     */
    protected  boolean canFinish(){

        return true;
    }

    /**
     * 添加要显示的View
     * @param view
     */
    public void addMainView(View view){
        mMainView = (ViewGroup) view;
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.content_view);
        viewGroup.addView(mMainView, 1);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mMainView = (ViewGroup) getLayoutInflater().inflate(layoutResID, null);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.content_view);
        viewGroup.addView(mMainView, 1);
    }

    /**
     * activity的初始化工作
     */
    protected void afterCreate(Bundle savedInstanceState) {
    }

    /**
     * 设置错误显示图标
     * @param resId
     */
    protected void setErrorIcon(int resId) {
        ImageView mIcon = (ImageView) findViewById(R.id.iv_icon_error);
        mIcon.setImageResource(resId);
    }
    /**
     * 设置错误显示文本
     * @param  errer
     */
    protected TextView setErroryText(String errer) {
        TextView mText = (TextView) findViewById( R.id.tv_error);
        mText.setText(errer);
        return mText;
    }


    /**
     * 隐藏除标题栏的所有界面,辅助显示各个页面的
     */
    private void hideAll() {
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }
        if (mMainView != null) {
            mMainView.setVisibility(View.GONE);
        }
    }

    public void showErrorView() {
        hideAll();
        mErrorView.setVisibility(View.VISIBLE);
    }

    public void showContentView() {
        hideAll();
        mMainView.setVisibility(View.VISIBLE);
    }

    public void showErrorView(int imgResId, String error) {
        hideAll();
        mErrorView.setVisibility(View.VISIBLE);
        setErrorIcon(imgResId);
        setErroryText(error);
    }

    public void showErrorView(String error) {
        hideAll();
        mErrorView.setVisibility(View.VISIBLE);
        setErroryText(error);
    }

    public void setTopTitle(int resId) {
        setTopTitle(getString(resId));
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTopTitle(String title) {
        TextView tvTitle = (TextView) findViewById(R.id.tv_top_title_abs);
        tvTitle.setText(title);
    }

    public void hintTitleView() {
      FrameLayout frameLayout= (FrameLayout) findViewById(R.id.fram_title);
        frameLayout.setVisibility(View.GONE);
    }

    /**
     * 设置title right 文本点击事件
     * @param subTitle
     * @param listener
     */
    public void setSubRightTitleAndClick(String subTitle, View.OnClickListener listener) {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fllayout_right);
        TextView tvSubTitle = (TextView) findViewById(R.id.tv_top_right);
        tvSubTitle.setText(subTitle);
        tvSubTitle.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.VISIBLE);
        tvSubTitle.setOnClickListener(listener);
    }

    /**
     * 设置title left 文本点击事件
     * @param subTitle
     */
    public void setSubLeftTitle(String subTitle) {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fram_img_back_store);
        TextView tvSubTitle = (TextView) findViewById(R.id.tv_back);
        tvSubTitle.setText(subTitle);
        tvSubTitle.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.VISIBLE);
    }

    public void setSubTitle(String subTitle) {
        TextView tvSubTitle = (TextView) findViewById(R.id.tv_top_right);
        tvSubTitle.setText(subTitle);
        tvSubTitle.setVisibility(View.VISIBLE);
    }

   public void setSubLeftImgState(boolean isShow) {
       ImageView tvSubTitle = (ImageView) findViewById(R.id.img_back);
       FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fram_img_back_store);
       if(isShow){
           tvSubTitle.setVisibility(View.VISIBLE);
           frameLayout.setVisibility(View.VISIBLE);
       }else{
           tvSubTitle.setVisibility(View.GONE);
           frameLayout.setVisibility(View.GONE);
       }
    }



    /**
     * 设置title right 图片点击事件
     * @param listener
     */
    public void setSubImgAndClick(View.OnClickListener listener) {
        ImageView img= (ImageView) findViewById(R.id.img_right);
        img.setVisibility(View.VISIBLE);
        findViewById(R.id.fllayout_right).setOnClickListener(listener);
    }

}
