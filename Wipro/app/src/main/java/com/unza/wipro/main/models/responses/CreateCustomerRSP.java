package com.unza.wipro.main.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.UserData;

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
