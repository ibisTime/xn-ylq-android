package com.chengdai.ehealthproject.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.dialog.LoadingDialog;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Actvity 基类
 *
 */
public abstract class BaseStoreActivity extends AppCompatActivity {

    private CommonDialog commonDialog;
    protected LoadingDialog loadingDialog;
    protected CompositeDisposable mSubscription;

    @Subscribe
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mSubscription = new CompositeDisposable();
        loadingDialog = new LoadingDialog(this);

        EventBus.getDefault().register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (commonDialog != null) {
            commonDialog.closeDialog();
            commonDialog = null;
        }
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if (mSubscription != null && !mSubscription.isDisposed()) {
            mSubscription.dispose();
            mSubscription.clear();
        }
    }



    /**
     * 隐藏Dialog
     */
    public void disMissLoading() {
        if (loadingDialog != null) {
            loadingDialog.closeDialog();
        }
    }

    /**
     * 显示dialog
     */
    public void showLoadingDialog() {
        if (loadingDialog != null ) {
            loadingDialog.showDialog();
        }
    }


    /**
     * Dialog
     *
     * @param str
     */
    public void showSimpleWran(String str) {

        if (isFinishing()) {
            return;
        }

        commonDialog = new CommonDialog(this).builder()
                .setTitle("提示").setContentMsg(str)
                .setNegativeBtn("确定", null);

        commonDialog.show();
    }

    public void showToast(String str){
        ToastUtil.show(this,str);
    }

    /**
     * Dialog
     *
     * @param str
     */
    protected void showRedSimpleWran(String str) {

        if (isFinishing()) {
            return;
        }

        commonDialog = new CommonDialog(this).builder()
                .setTitle("提示").setContentMsg(str)
                .setNegativeBtn("确定", null, true);

        commonDialog.show();
    }
    /**
     * @param str
     * @param onNegativeListener
     */
    public void showWarnListen(String str, CommonDialog.OnNegativeListener onNegativeListener) {

        if (isFinishing()) {
            return;
        }

        commonDialog = new CommonDialog(this).builder()
                .setTitle("提示").setContentMsg(str)
                .setNegativeBtn("确定", onNegativeListener, false);

        commonDialog.show();
    }

    /**
     * @param str
     * @param onNegativeListener
     */
    protected void showRedWarnListen(String str, CommonDialog.OnNegativeListener onNegativeListener) {

        if (isFinishing()) {
            return;
        }

        commonDialog = new CommonDialog(this).builder()
                .setTitle("提示").setContentMsg(str)
                .setNegativeBtn("确定", onNegativeListener, true);

        commonDialog.show();
    }

    protected void showDoubleWarnListen(String str, CommonDialog.OnPositiveListener onPositiveListener) {

        if (isFinishing()) {
            return;
        }

        commonDialog = new CommonDialog(this).builder()
                .setTitle("提示").setContentMsg(str)
                .setPositiveBtn("确定", onPositiveListener)
                .setNegativeBtn("取消", null, false);

        commonDialog.show();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 隐藏键盘
     */
    public void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public void hideKeyboard(Activity activity) {
        if (activity == null || activity.getWindow() == null) {
            return;
        }
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            hideKeyboard(view);
        }
    }

    @Override
    public void finish() {
        hideKeyboard(this);
        super.finish();
    }

    @Subscribe
    public void finishAll(EventBusModel i){
        if("AllFINISH".equals(i.getTag())){
            this.finish();
        }
    }

}
