package com.chengdai.ehealthproject.uitls;

import com.chengdai.ehealthproject.model.common.model.TimeModel;
import com.chengdai.ehealthproject.uitls.LogUtil;

import java.util.Calendar;
import java.util.List;

/**
 * 获取日历数据
 * Created by Administrator on 2016/6/28.
 */
public class TimeUtil {

    private static List<TimeModel> mDaysList;

    public static void geDay(List<TimeModel> days) {
        if (days == null) {
            return;
        }
        days.clear();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int currMonth = c.get(Calendar.MONTH) + 1;
        int maxMonth = currMonth + 10;
        int month = 0;
        LogUtil.I("currMonth : " + currMonth + "maxMonth : " + maxMonth);

        int mDays = 0;
        for (int i = currMonth; i <= maxMonth; i++) {
            if (i > 12) {
                month = i % 12;
                if (month == 1) {
                    year++;
                }
                mDays = getDaysmonth(month);
            } else {
                month = i;
                mDays = getDaysmonth(month);
            }
            for (int j = 1; j <= mDays; j++) {
                days.add(new TimeModel(year, month, j));
            }
        }

        mDaysList = days;
    }

    //如果日期为i < 10的话加上0
    private static String formatTime(int i) {
        if (i < 10) {
            return new StringBuffer().append("0").append(i).toString();
        }

        return String.valueOf(i);
    }

    //获取小时，hourMode为12或24
    public static void getHours(List<String> hours, int hourMode) {
        if (hours == null) {
            return;
        }

        hours.clear();
        for (int i = 0; i < hourMode; i++) {
            hours.add(new StringBuffer().append(formatTime(i)).append("点").toString());
        }
    }

    //获取小时，hourMode为12或24(不带点)
    public static void getHoursNoStr(List<String> hours, int hourMode) {
        if (hours == null) {
            return;
        }

        hours.clear();
        for (int i = 0; i < hourMode; i++) {
            hours.add(new StringBuffer().append(formatTime(i)).toString());
        }
    }

    //获取分钟列表（不带分）
    public static void getMonutionsNoStr(List<String> monutions, int unit) {
        if (monutions == null) {
            return;
        }

        monutions.clear();
        int unitNumber = 60 / unit;
        for (int i = 0; i < unitNumber; i++) {
            monutions.add(new StringBuffer().append(formatTime(i * unit)).toString());
        }
    }

    //获取分钟列表
    public static void getMonutions(List<String> monutions, int unit) {
        if (monutions == null) {
            return;
        }

        monutions.clear();
        int unitNumber = 60 / unit;
        for (int i = 0; i < unitNumber; i++) {
            monutions.add(new StringBuffer().append(formatTime(i * unit)).append("分").toString());
        }
    }

    //根据日期获取天数
    public static int getDaysFormDate(int year, int month, int day) {

        if (mDaysList == null || mDaysList.size() <= 0) {
            return day;
        }

        for (TimeModel timeModel : mDaysList) {
            if (timeModel.getYear() == year && timeModel.getMonth() == month && timeModel.getDay() == day) {
                return mDaysList.indexOf(timeModel);
            }
        }

        return day;
    }

    public static int getHourDate(int hour, int hourMode) {
        int index = hour;
        if (index < 0 || index >= hourMode) {
            return 0;
        }

        return index;
    }

    public static int getMonitorDate(int monitor, int unit) {
        int index = monitor / unit;
        int unitNumber = 60 / unit;
        if (index < 0 || index >= unitNumber) {
            return 0;
        }
        return index;
    }

    //根据月获取天数
    private static int getDaysmonth(int i) {
        switch (i) {
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return 29;
        }

        return 31;
    }
}
