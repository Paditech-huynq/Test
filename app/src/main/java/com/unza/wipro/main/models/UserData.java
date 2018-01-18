package com.unza.wipro.main.models;


import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.unza.wipro.main.views.fragments.ProfileFragment.TYPE_USER_MANAGER;

public class UserData {
    public User getUserData(){
        UserManager userManager = new UserManager();
        return userManager.getDummyData();
    }

}
