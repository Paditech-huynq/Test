package com.unza.wipro.main.models;


import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.unza.wipro.main.views.fragments.ProfileFragment.TYPE_USER_MANAGER;

public class UserManager extends UserEmployee {
    private int numberCustom;
    private List<UserEmployee> members = new ArrayList<>();

    UserManager getDummyData(){
        UserManager userManager = new UserManager();
        userManager.setTypeUse(TYPE_USER_MANAGER);
        userManager.setName("Nguyễn văn a");
        userManager.setAddress("dsdssd");
        userManager.setEmail("dfsdfd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            userManager.setDateStart(format.parse("2018/01/02"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userManager.setNumberCustom(160);
        userManager.setNumberPoint(160);
        userManager.setNumberSales(160);
        userManager.setPhoneNumber("fdfdfd");
        userManager.setSaleWant(10000);
        userManager.setSaleHave(1000);
        return userManager;
    }

    public List<UserEmployee> getMembers() {
        return members;
    }

    public void setMembers(List<UserEmployee> members) {
        this.members = members;
    }

    public int getNumberCustom() {
        return numberCustom;
    }

    public void setNumberCustom(int numberCustom) {
        this.numberCustom = numberCustom;
    }
}
