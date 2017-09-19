package com.chengdai.ehealthproject.uitls;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 获取日历数据
 * Created by Administrator on 2016/6/28.
 */
public class OrderDateUtil {

    private static OrderDateUtil timeUtil;

    private List<String> years, months;

    public OrderDateUtil(boolean isMoreYear) {
        years = new ArrayList<>();
        months = new ArrayList<>(12);

        if (isMoreYear) {
            getMoreYear(years);
        } else {
            getYear(years);
        }

        getMonthsAndDays(months);
    }

    public OrderDateUtil(boolean isMoreYear, int sum) {
        years = new ArrayList<>();
        months = new ArrayList<>(12);

        if (isMoreYear) {
            getMoreYear(years, sum);
        } else {
            getYear(years, sum);
        }

        getMonthsAndDays(months);
    }

    public static OrderDateUtil newInstance(boolean isMoreYear) {
        if (timeUtil == null) {
            timeUtil = new OrderDateUtil(isMoreYear);
        }

        return timeUtil;
    }

    public static OrderDateUtil newInstance(boolean isMoreYear, int sum) {
        if (timeUtil == null) {
            timeUtil = new OrderDateUtil(isMoreYear, sum);
        }

        return timeUtil;
    }

    private void getMoreYear(List<String> years) {
        Calendar c = Calendar.getInstance();
        int currYear = c.get(Calendar.YEAR);
        int minYear = 1970;
        int maxYear = currYear;
        years.clear();
        for (int i = minYear; i <= maxYear; i++) {
            years.add(String.valueOf(i));
        }
    }

    /**
     * 新增从今年往后推sum年
     *
     * @param years
     * @param sum
     */
    private void getMoreYear(List<String> years, int sum) {
        Calendar c = Calendar.getInstance();
        int currYear = c.get(Calendar.YEAR);
        int minYear = 1970;
        int maxYear = currYear + sum;
        years.clear();
        for (int i = minYear; i <= maxYear; i++) {
            years.add(String.valueOf(i));
        }
    }

        /**
     * 新增从今年往后推sum年
     *
     * @param sum
     */
    public   List<String> getMoreYear(int sum) {
        List<String> years=new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int currYear = c.get(Calendar.YEAR);
        int minYear = c.get(Calendar.YEAR)-1;
        int maxYear = currYear + sum;
        for (int i = minYear; i <= maxYear; i++) {
            years.add(String.valueOf(i));
        }
        return years;
    }




    private void getYear(List<String> years) {
        Calendar c = Calendar.getInstance();
        int currYear = c.get(Calendar.YEAR);
        years.add(String.valueOf(currYear));
 /*       int minYear = currYear - 1;
        int maxYear = currYear + 1;
        years.clear();
        for (int i = minYear; i <= maxYear; i++) {
            years.add(String.valueOf(i));
        }*/
    }

    /**
     * 自定义多少个年份
     *
     * @param years
     */
    private void getYear(List<String> years, int sum) {
        Calendar c = Calendar.getInstance();
        int currYear = c.get(Calendar.YEAR);
        int minYear = currYear;
        int maxYear = currYear + sum;
        years.clear();
        for (int i = minYear; i <= maxYear; i++) {
            years.add(String.valueOf(i));
        }
    }


    private void getMonthsAndDays(List<String> months) {
        months.clear();
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                months.add("0" + i);
            } else {
                months.add(String.valueOf(i));
            }

        }
    }

    public void getDaysFormMonth(int month, List<String> days) {
        days.clear();
        int maxDays = getDaysmonth(month);
        for (int j = 1; j <= maxDays; j++) {
            if (j < 10) {
                days.add("0" + String.valueOf(j));
            } else {
                days.add(String.valueOf(j));
            }

        }
    }

    public void getDaysFormMonth(String month, List<String> days) {
        if (TextUtils.isEmpty(month)) {
            getDaysFormMonth(7, days);
        } else {
              getDaysFormMonth(Integer.valueOf(month), days);
//            getDaysFormMonth(StringUtil.getIntger(), days);
        }
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

    public int getCurrYear(String year) {
        if (TextUtils.isEmpty(year)) return 1;
        int size = years.size();
        for (int i = 0; i < size; i++) {
            if (year.equals(years.get(i))) {
                return i;
            }
        }

        return 1;
    }

    public int getCurrMonth(String month) {
        if (TextUtils.isEmpty(month)) return 0;
        int size = months.size();
        for (int i = 0; i < size; i++) {
            if (month.equals(months.get(i))) {
                return i;
            }
        }

        return 0;
    }

    public int getCurrDays(String day, List<String> days) {
        if (TextUtils.isEmpty(day)) return 0;
        int size = days.size();
        for (int i = 0; i < size; i++) {
            if (day.equals(days.get(i))) {
                return i;
            }
        }

        return 0;
    }

    public List<String> getYears() {
        return years;
    }

    public List<String> getMonths() {
        return months;
    }
}
