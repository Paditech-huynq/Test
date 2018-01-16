package com.unza.wipro.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUntils {

    public static String getStringMonthYear(Date date){
        return new SimpleDateFormat("MM/yyyy").format(date);
    }


    public static String getStringTimeAll(Date date){
        return new SimpleDateFormat("yyyy-MM-dd kk:mm").format(date);
    }
}
