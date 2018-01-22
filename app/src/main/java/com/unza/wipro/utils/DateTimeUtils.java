package com.unza.wipro.utils;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {
    @SuppressLint("SimpleDateFormat")
    public static String getStringMonthYear(Date date) {
        return new SimpleDateFormat("MM/yyyy").format(date);
    }


    @SuppressLint("SimpleDateFormat")
    public static String getStringTimeAll(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd kk:mm").format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getStringDayMonthYear(Date date) {
        Log.e("getStringDayMonthYear: ", new SimpleDateFormat("dd/MM/yyyy hh:MM:ss").format(date) );
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

    @NonNull
    public static String getStringFirstDayInCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getStringDayMonthYear(calendar.getTime());
    }

    @NonNull
    public static String getStringLastDayInCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE));
        return getStringDayMonthYear(calendar.getTime());
    }

    private static int getTheFirstDayInCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeekOfCurrentWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int firstDayInCurrentWeek;
        if (dayOfWeekOfCurrentWeek == Calendar.SUNDAY) {
            firstDayInCurrentWeek = calendar.get(Calendar.DAY_OF_MONTH) - 6;
        } else {
            firstDayInCurrentWeek = calendar.get(Calendar.DAY_OF_MONTH) - (dayOfWeekOfCurrentWeek - 2);
            // -2 because the DAY_OF_WEEK return from 1 -> 7 with SUNDAY -> SATURDAY
        }
        return firstDayInCurrentWeek;
    }

    @NonNull
    public static String getStringFirstDayInCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, getTheFirstDayInCurrentWeek());
        return getStringDayMonthYear(calendar.getTime());
    }

    @NonNull
    public static String getStringLastDayInCurrentWeek() {
        int lastDayInCurrentWeek = getTheFirstDayInCurrentWeek() + 6;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, lastDayInCurrentWeek);
        return getStringDayMonthYear(calendar.getTime());
    }

    @NonNull
    public static String getStringFirstDayInLastWeek() {
        int firstDayInLastWeek = getTheFirstDayInCurrentWeek() - 7;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, firstDayInLastWeek);
        return getStringDayMonthYear(calendar.getTime());
    }

    @NonNull
    public static String getStringLastDayInLastWeek() {
        int lastDayInLastWeek = getTheFirstDayInCurrentWeek() - 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, lastDayInLastWeek);
        return getStringDayMonthYear(calendar.getTime());
    }
}
