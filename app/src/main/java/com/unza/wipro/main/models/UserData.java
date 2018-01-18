package com.unza.wipro.main.models;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.unza.wipro.main.views.fragments.ProfileFragment.TYPE_USER_MANAGER;

public class UserData {
    public static User getDummyData(){
        User user = new User();
        user.setTypeUse(TYPE_USER_MANAGER);
        user.setName("Nguyễn văn a");
        user.setAddress("dsdssd");
        user.setEmail("dfsdfd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            user.setDateStart(format.parse("2018/01/02"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setNumberCustom(160);
        user.setNumberPoint(160);
        user.setNumberSales(160);
        user.setPhoneNumber("fdfdfd");
        user.setSaleWant(10000);
        user.setSaleHave(1000);
        return user;
    }

}
