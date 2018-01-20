package com.unza.wipro.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.unza.wipro.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {
    private static Calendar calendar = Calendar.getInstance();

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
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getStringDayMonthYear(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
    public static String getStringFirstDayInCurrentMonth(Context context) {
        return getStringDayMonthYear(context.getResources().getString(R.string.display_time_day_month_year,
                1, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR)));
    }

    @NonNull
    public static String getStringLastDayInCurrentMonth(Context context) {
        int numberDayOfMonth = calendar.getActualMaximum(Calendar.DATE);
        return getStringDayMonthYear(context.getResources().getString(R.string.display_time_day_month_year,
                numberDayOfMonth, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR)));
    }

    private static int getTheFirstDayInCurrentWeek() {
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
    public static String getStringFirstDayInCurrentWeek(Context context) {
        int lastDayInCurrentWeek = getTheFirstDayInCurrentWeek();
        return getStringDayMonthYear(context.getResources().getString(R.string.display_time_day_month_year,
                lastDayInCurrentWeek, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR)));
    }

    @NonNull
    public static String getStringLastDayInCurrentWeek(Context context) {
        int lastDayInCurrentWeek = getTheFirstDayInCurrentWeek() + 6;
        return getStringDayMonthYear(context.getResources().getString(R.string.display_time_day_month_year,
                lastDayInCurrentWeek, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR)));
    }

    @NonNull
    public static String getStringFirstDayInLastWeek(Context context) {
        int lastDayInLastWeek = getTheFirstDayInCurrentWeek() - 7;
        return getStringDayMonthYear(context.getResources().getString(R.string.display_time_day_month_year,
                lastDayInLastWeek, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR)));
    }

    @NonNull
    public static String getStringLastDayInLastWeek(Context context) {
        int lastDayInLastWeek = getTheFirstDayInCurrentWeek() - 1;
        return getStringDayMonthYear(context.getResources().getString(R.string.display_time_day_month_year,
                lastDayInLastWeek, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR)));
    }
}
