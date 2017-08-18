package com.cdkj.baselibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.baselibrary.dialog.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;


/**
 * Actvity 基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected LoadingDialog loadingDialog;
    private List<Call> mCallList;
    protected CompositeDisposable mSubscription;

    @Subscribe
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mSubscription = new CompositeDisposable();
        loadingDialog = new LoadingDialog(this);
        EventBus.getDefault().register(this);
        mCallList = new ArrayList<>();
    }


    protected void addCall(Call call) {
        mCallList.add(call);
    }

    protected void clearCall() {

        for (Call call : mCallList) {
            if (call == null) {
                continue;
            }
            call.cancel();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        clearCall();

        if (mSubscription != null){
            mSubscription.dispose();
            mSubscription.clear();
        }

        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        EventBus.getDefault().unregister(this);
    }


    /**
     * 隐藏Dialog
     */
    public void disMissLoading() {

        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.closeDialog();
        }
    }

    /**
     * 显示dialog
     */
    public void showLoadingDialog() {
        if(loadingDialog==null){
            loadingDialog=new LoadingDialog(this);
        }
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.showDialog();
        }
    }


    public void showToast(String str) {
        ToastUtil.show(this, str);
    }

    protected void showDoubleWarnListen(String str, CommonDialog.OnPositiveListener onPositiveListener) {

        if (isFinishing()) {
            return;
        }

        CommonDialog commonDialog = new CommonDialog(this).builder()
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
    public void finishAll(String i) {
        if (TextUtils.equals(EventTags.AllFINISH, i)) {
            if (canEvenFinish()) {
                this.finish();
            }
        }
    }

    /**
     * 能否通过 EventBUS事件结束
     * @return
     */
    protected boolean canEvenFinish() {
        return true;
    }

    /**
     * 设置状态栏颜色（5.0以上系统）
     *
     * @param stateTitleColor
     */
    public void setStateTitleColor(int stateTitleColor) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上系统实现状态栏颜色改变
            try {
                Window window = getWindow();
                //取消设置透明状态栏,使 ContentView 内容不再沉浸到状态栏下
                if (window != null) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    //设置状态栏颜色
                    window.setStatusBarColor(stateTitleColor);
                }
            } catch (Exception e) {

            }
        }
    }
}
