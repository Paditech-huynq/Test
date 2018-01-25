package com.unza.wipro.transaction.user;

import com.google.gson.annotations.SerializedName;

public class Promoter extends User {
    @SerializedName("customers")
    private String numberCustomers;

    public String getNumberCustomers() {
        return numberCustomers;
    }

    public void setNumberCustomers(String numberCustomers) {
        this.numberCustomers = numberCustomers;
    }
}