package com.unza.wipro.main.models;


import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.unza.wipro.main.views.fragments.ProfileFragment.TYPE_USER_MANAGER;

public class UserData {
    public User getUserData(){
        UserManager user = null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            user = new UserManager(160,160,"Nguyễn văn a", " dsdsd","sdsdsd", " sdsdsds", "R.drawable.bg_test", TYPE_USER_MANAGER, format.parse("2018/01/02"), 10000, 1000, 3 , new ArrayList<UserEmployee>());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return user;
    }

}
