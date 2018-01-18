package com.unza.wipro.main.models;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserManager extends UserEmployee {
    private int numberCustom;
    private List<UserEmployee> members = new ArrayList<>();


    UserManager(int number_sales, int number_point, String name, String phoneNumber, String email, String address, String avar, int type_use, Date dateStart, long saleWant, long saleHave, int numberCustom, List<UserEmployee> members) {
        super(number_sales, number_point, name, phoneNumber, email, address, avar, type_use, dateStart, saleWant, saleHave);
        this.numberCustom = numberCustom;
        this.members = members;
    }

    public List<UserEmployee> getMembers() {
        return members;
    }

    public void setMembers(List<UserEmployee> members) {
        this.members = members;
    }

    public UserManager(int typeUse) {
        super(typeUse);
    }

    public int getNumberCustom() {
        return numberCustom;
    }

    public void setNumberCustom(int numberCustom) {
        this.numberCustom = numberCustom;
    }
}
