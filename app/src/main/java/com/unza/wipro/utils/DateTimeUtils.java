package com.unza.wipro.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    @SuppressLint("SimpleDateFormat")
    public static String getStringMonthYear(Date date){
        return new SimpleDateFormat("MM/yyyy").format(date);
    }


    @SuppressLint("SimpleDateFormat")
    public static String getStringTimeAll(Date date){
        return new SimpleDateFormat("yyyy-MM-dd kk:mm").format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getStringDayMonthYear(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getStringDayMonthYear(String dateString){
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
}
