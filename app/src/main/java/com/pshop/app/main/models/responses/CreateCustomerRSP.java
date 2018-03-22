package com.pshop.app.main.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pshop.app.main.models.UserData;

public class CreateCustomerRSP extends BaseRSP {
    @SerializedName("data")
    @Expose
    private UserData customer;

    public UserData getCustomer() {
        return customer;
    }

    public void setCustomer(UserData customer) {
        this.customer = customer;
    }
}
