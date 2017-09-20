package com.chengdai.ehealthproject.weigit.popwindows;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.LogUtil;
import com.chengdai.ehealthproject.uitls.OrderDateUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.weigit.views.ScrollPicker;

import java.util.ArrayList;
import java.util.List;

/**时间弹框选择
 * Created by Administrator on 2016-04-04.
 */
public class SelectUseDayPopup extends BasePopupWindow implements View.OnClickListener{

    private TextView tv_cancel, tv_sure;
    private ScrollPicker sn_year, sn_month, sn_day;
    private View view_emty;
    private SureOnClick sureOnClick;
    private OrderDateUtil dateUtil;
    private boolean isMoreYear = false;
    private List<String> dayData;

    public SelectUseDayPopup setSureOnClick(SureOnClick sureOnClick) {
        this.sureOnClick = sureOnClick;
        return this;
    }

    public SelectUseDayPopup(Activity context, boolean isMoreYear) {
        super(context);
        this.isMoreYear = isMoreYear;
        initLocalLayout();
    }

    public SelectUseDayPopup(Activity context, int w, int h) {
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
        return getPopupViewById(R.layout.popup_select_mouth_day_2);
    }

    @Override
    protected AnimatorSet getDefaultSlideFromBottomAnimationSet() {
        return super.getDefaultSlideFromBottomAnimationSet();
    }

    @Override
    public View getAnimaView() {
        return null;
    }

    protected void initLocalLayout() {
        tv_cancel = (TextView) mPopupView.findViewById(R.id.tv_cancel);
        tv_sure = (TextView) mPopupView.findViewById(R.id.tv_sure);
        sn_day = (ScrollPicker) mPopupView.findViewById(R.id.sn_day);
        sn_year = (ScrollPicker) mPopupView.findViewById(R.id.sn_year);
        sn_month = (ScrollPicker) mPopupView.findViewById(R.id.sn_month);
        view_emty = mPopupView.findViewById(R.id.view_emty);

        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        view_emty.setOnClickListener(this);

        dateUtil = OrderDateUtil.newInstance(isMoreYear);
        initDate();
    }

    @Override
    protected void initLayout() {
        super.initLayout();
        setAnimotionStyle(R.style.popwin_anim_style);
    }

    private void initDate(){
        List<String> yearsData = dateUtil.getYears();
        List<String> monthData = dateUtil.getMonths();
        dayData = new ArrayList<>(31);

        sn_year.setData(yearsData);
        sn_month.setData(monthData);
        sn_month.setOnSelectedListener(new ScrollPicker.OnSelectedListener() {
            @Override
            public void onSelected(List<String> data, int position) {
                int month = Integer.valueOf(data.get(position));
//                int month = StringUtil.getIntger(data.get(position));
                dateUtil.getDaysFormMonth(month, dayData);
                sn_day.setData(dayData);
                sn_day.setSelectedPosition(0);
            }
        });
//        if( isMoreYear ){
//            sn_year.setSelectedPosition(yearsData.size()-1);
//        } else {
        setCurrentDate();
//        }
    }

    /**
     * 如果第一次选择时间，可以通过这个方法，设置原来时间在选择器中显示相应位置
     * @param date
     */
    public void setSelected( String date ){
        if(TextUtils.isEmpty(date)){
            return;
        }
        String[] strings = date.split("-");

        if( strings != null && strings.length == 3 ){
            sn_year.setSelectedPosition(dateUtil.getCurrYear(strings[0]));
            sn_month.setSelectedPosition(dateUtil.getCurrMonth(strings[1]));
            OrderDateUtil.newInstance(isMoreYear).getDaysFormMonth(strings[1], dayData);
            sn_day.setData(dayData);
            sn_day.setSelectedPosition(dateUtil.getCurrDays(strings[2], dayData));
        }
    }

    public void setCurrentDate(){
        String currDate = DateUtil.getCurrentDate();
        LogUtil.I(currDate);
        setSelected(currDate);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.view_emty || i == R.id.tv_cancel) {
            dismiss();

        } else if (i == R.id.tv_sure) {
            if (sureOnClick != null) {
                sureOnClick.OnSureClick(new StringBuffer()
                        .append(sn_year.getSelectedItem())
                        .append("-")
                        .append(StringUtils.frontCompWithZoreString(sn_month.getSelectedItem(), 2))
                        .append("-")
                        .append(StringUtils.frontCompWithZoreString(sn_day.getSelectedItem(), 2))
                        .toString(), new StringBuffer()
                        .append(StringUtils.frontCompWithZoreString(sn_month.getSelectedItem(), 2))
                        .append("月")
                        .append(StringUtils.frontCompWithZoreString(sn_day.getSelectedItem(), 2))
                        .append("日")
                        .toString());
                dismiss();
            }
            dismiss();

        }
    }

    public interface SureOnClick{
        void OnSureClick(String selected,String showData);
    }
}