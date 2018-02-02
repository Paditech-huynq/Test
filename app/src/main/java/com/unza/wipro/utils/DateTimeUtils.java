package com.unza.wipro.utils;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {
    private static long ONE_DAY = 1000*60*60*24;
    private static long ONE_SECOND = 1000;
    @SuppressLint("SimpleDateFormat")
    public static String getStringMonthYear(Date date) {
        return new SimpleDateFormat("MM/yyyy").format(date);
    }


    @SuppressLint("SimpleDateFormat")
    public static String getStringTimeAll(Date date) {
        return new SimpleDateFormat("yyyy/MM/dd - kk:mm").format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getStringDayMonthYear(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromStringDayMonthYear(String dateString) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromServerDayMonthYear(String dateString) {
        Long longDate = Long.parseLong(dateString)*ONE_SECOND;
        return new Date(longDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static Date getEndOfeDateFromStringDayMonthYear(String dateString) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
            date.setTime(date.getTime()+ONE_DAY);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @NonNull
    public static String getStringFirstDayInCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getStringDayMonthYear(calendar.getTime());
    }

    private static Date getTheFirstDayInCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfCurrentWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int firstDayInCurrentWeek;
        if (dayOfCurrentWeek == Calendar.SUNDAY) {
            firstDayInCurrentWeek = calendar.get(Calendar.DAY_OF_MONTH) - 6;
        } else {
            firstDayInCurrentWeek = calendar.get(Calendar.DAY_OF_MONTH) - (dayOfCurrentWeek - 2);
            // -2 because the DAY_OF_WEEK return from 1 -> 7 with SUNDAY -> SATURDAY
        }
        calendar.set(Calendar.DAY_OF_MONTH, firstDayInCurrentWeek);
        return calendar.getTime();
    }

    @NonNull
    public static String getStringFirstDayInCurrentWeek() {
        return getStringDayMonthYear(getTheFirstDayInCurrentWeek());
    }

    @NonNull
    public static String getStringFirstDayInLastWeek() {
        return getStringDayMonthYear(new Date(getTheFirstDayInCurrentWeek().getTime() - 7*ONE_DAY));
    }

    @NonNull
    public static String getStringLastDayInLastWeek() {
        return getStringDayMonthYear(new Date(getTheFirstDayInCurrentWeek().getTime() - ONE_DAY));
    }
}
