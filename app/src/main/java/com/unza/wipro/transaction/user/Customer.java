package com.unza.wipro.transaction.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer extends User implements CustomerInfo {
    @SerializedName("phone")
    @Expose
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getCustomerId() {
        return getId();
    }
}