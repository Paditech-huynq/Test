package com.pshop.app.transaction.user;

import com.google.gson.annotations.SerializedName;

public class Customer extends User implements CustomerInfo {
    @SerializedName("point")
    private String point;

    public Customer() {
        role = TYPE_CUSTOMER;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String numberCustomers) {
        this.point = numberCustomers;
    }

    @Override
    public String getCustomerId() {
        return getId();
    }
}