package com.chengdai.ehealthproject.weigit.popwindows;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.weigit.views.ScrollPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间弹框(分钟)
 * Created by Administrator on 2016-04-04.
 */
public class SelectMinutesTimePopup extends BasePopupWindow implements View.OnClickListener {

    private TextView tv_cancel, tv_sure;
    private ScrollPicker sn_hour, sn_monitor;
    private View view_emty;
    private SureOnClick sureOnClick;

    public SelectMinutesTimePopup setSureOnClick(SureOnClick sureOnClick) {
        this.sureOnClick = sureOnClick;
        return this;
    }

    public SelectMinutesTimePopup(Activity context) {
        super(context);
    }

    public SelectMinutesTimePopup(Activity context, int w, int h) {
        super(context, w, h);
    }

    @Override
    protected Animation getShowAnimation() {
        return getDefaultScaleAnimation();
    }

    @Override
    protected View getClickToDismissView() {
        return mPopupView.findViewById(R.id.tv_cancel);
    }

    public View getPopupView() {
        return getPopupViewById(R.layout.popup_select_minutes_time);
    }

    @Override
    protected AnimatorSet getDefaultSlideFromBottomAnimationSet() {
        return super.getDefaultSlideFromBottomAnimationSet();
    }

    @Override
    public View getAnimaView() {
        return null;
    }

    @Override
    protected void initLayout() {
        super.initLayout();
        tv_cancel = (TextView) mPopupView.findViewById(R.id.tv_cancel);
        tv_sure = (TextView) mPopupView.findViewById(R.id.tv_sure);
        sn_hour = (ScrollPicker) mPopupView.findViewById(R.id.sn_hour);
        sn_monitor = (ScrollPicker) mPopupView.findViewById(R.id.sn_monitor);
        view_emty = mPopupView.findViewById(R.id.view_emty);

        setAnimotionStyle(R.style.popwin_anim_style);
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        view_emty.setOnClickListener(this);

        initDate();
    }

    private void initDate() {
        ArrayList<String> hourData = new ArrayList<>();
        ArrayList<String> monitorData = new ArrayList<>();


        for(int i=0;i<24;i++){
            hourData.add(StringUtils.frontCompWithZoreString(i,2));
        }
       for(int i=0;i<60;i++){
           monitorData.add(StringUtils.frontCompWithZoreString(i,2));
        }

        sn_hour.setData(hourData);
        sn_monitor.setData(monitorData);

    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
        sn_hour.setSelectedPosition(0);
        sn_monitor.setSelectedPosition(0);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.view_emty || i == R.id.tv_cancel) {
            dismiss();

        } else if (i == R.id.tv_sure) {
            sureOnClick.OnSureClick(sn_hour.getSelectedItem() + ":" + sn_monitor.getSelectedItem());
            dismiss();
            dismiss();

        }
    }

    public interface SureOnClick {
        public void OnSureClick(String value);
    }
}