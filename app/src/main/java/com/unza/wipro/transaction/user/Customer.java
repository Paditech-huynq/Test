package com.unza.wipro.transaction.user;

import com.google.gson.annotations.SerializedName;

public class Customer extends User implements CustomerInfo {
    @SerializedName("point")
    private String numberOrders;

    @Override
    public String getNumberOrders() {
        return numberOrders;
    }

    @Override
    public void setNumberOrders(String numberOrders) {
        this.numberOrders = numberOrders;
    }

    @Override
    public String getCustomerId() {
        return getId();
    }
}