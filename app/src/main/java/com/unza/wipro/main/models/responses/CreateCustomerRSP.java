package com.unza.wipro.main.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.Customer;

public class CreateCustomerRSP extends BaseRSP {
    @SerializedName("data")
    @Expose
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
