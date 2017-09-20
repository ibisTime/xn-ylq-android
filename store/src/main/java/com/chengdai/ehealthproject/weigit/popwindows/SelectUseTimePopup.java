package com.chengdai.ehealthproject.weigit.popwindows;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.common.model.TimeModel;
import com.chengdai.ehealthproject.uitls.TimeUtil;
import com.chengdai.ehealthproject.weigit.popwindows.BasePopupWindow;
import com.chengdai.ehealthproject.weigit.views.MyScrollPicker;
import com.chengdai.ehealthproject.weigit.views.ScrollPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间弹框
 * Created by Administrator on 2016-04-04.
 */
public class SelectUseTimePopup extends BasePopupWindow implements View.OnClickListener {

    private TextView tv_cancel, tv_sure;
    private ScrollPicker sn_hour, sn_monitor;
    private MyScrollPicker sn_day;
    private View view_emty;
    private SureOnClick sureOnClick;

    public void setSureOnClick(SureOnClick sureOnClick) {
        this.sureOnClick = sureOnClick;
    }

    public SelectUseTimePopup(Activity context) {
        super(context);
    }

    public SelectUseTimePopup(Activity context, int w, int h) {
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
        return getPopupViewById(R.layout.popup_select_mouth_day);
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
        sn_day = (MyScrollPicker) mPopupView.findViewById(R.id.sn_day);
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
        ArrayList<TimeModel> dayData = new ArrayList<>();
        ArrayList<String> hourData = new ArrayList<>();
        ArrayList<String> monitorData = new ArrayList<>();

        TimeUtil.geDay(dayData);//获取
        TimeUtil.getHours(hourData, 24);//24小时
        TimeUtil.getMonutions(monitorData, 5);//5分钟为一个刻度

        sn_day.setData(dayData);
        sn_hour.setData(hourData);
        sn_monitor.setData(monitorData);

        setCurrTime();
    }

    public TimeModel getTimeModel(){
        return sn_day.getSelectedItem();
    }
    public int getHourTimeModel(){
        return  Integer.parseInt(spliteDate(sn_hour.getSelectedItem()).get(0));
    }

    public int getMiniusTimeModel(){
        return  Integer.parseInt(spliteDate(sn_monitor.getSelectedItem()).get(0));
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.view_emty || i == R.id.tv_cancel) {
            dismiss();

        } else if (i == R.id.tv_sure) {
            if (sureOnClick != null) {
                TimeModel dayList = sn_day.getSelectedItem();

                if (dayList == null) {
                    dismiss();
                    return;
                }

                List<String> hourList = spliteDate(sn_hour.getSelectedItem());
                List<String> monitorList = spliteDate(sn_monitor.getSelectedItem());

                StringBuilder builder = new StringBuilder();
                builder.append(dayList.getYear())
                        .append("-")
                        .append(dayList.getMonth())
                        .append("-")
                        .append(dayList.getDay())
                        .append(" ")
                        .append(hourList.get(0))
                        .append(":")
                        .append(monitorList.get(0));
                String value = builder.toString();

                String showSelected = builder.append(" ")
                        .append(dayList.getWeek())
                        .toString();

                sureOnClick.OnSureClick(value, showSelected);
                dismiss();
            }
            dismiss();

        }
    }

    /**
     * 如果第一次选择时间，可以通过这个方法，设置原来时间在选择器中显示相应位置
     *
     * @param date
     */
    public void setSelected(final String date) {
        if (TextUtils.isEmpty(date)) {
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date data = null;
        try {
            data = format.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(data);
            int dayIndex = TimeUtil.getDaysFormDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
            sn_day.setSelectedPosition(dayIndex);
            int hourIndex = TimeUtil.getHourDate(c.get(Calendar.HOUR_OF_DAY), 24);
            sn_hour.setSelectedPosition(hourIndex);
            int miniuteIndex = TimeUtil.getMonitorDate(c.get(Calendar.MINUTE), 5);
            sn_monitor.setSelectedPosition(miniuteIndex);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setCurrTime(){
        final Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        final String retStrFormatNowDate = sdFormatter.format(curDate);
        setSelected(retStrFormatNowDate);
    }

    private List<String> spliteDate(String str) {
        String str2 = "";
        List<String> strList = new ArrayList<>();
        if (str != null && !"".equals(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    str2 += str.charAt(i);
                } else {
                    strList.add(str2);
                    str2 = "";
                }
            }

        }

        return strList;
    }

    public interface SureOnClick {
        public void OnSureClick(String value, String selected);
    }
}