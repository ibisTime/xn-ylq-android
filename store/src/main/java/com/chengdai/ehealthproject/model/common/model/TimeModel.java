package com.chengdai.ehealthproject.model.common.model;

import com.chengdai.ehealthproject.uitls.DateUtil;

/**
 * Created by Administrator on 2016/8/29.
 */
public class TimeModel {
    private int year;
    private int month;
    private int day;
    private String week;

    public TimeModel(int year, int month, int day) {
        this.day = day;
        this.year = year;
        this.month = month;
        week = DateUtil.getWeekOfDate(year+"年"+toString());
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getWeek() {
        return week;
    }

    @Override
    public String toString() {
        return month+"月"+day+"日 ";
    }
}
