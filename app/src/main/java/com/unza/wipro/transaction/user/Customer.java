package com.unza.wipro.transaction.user;

import com.google.gson.annotations.SerializedName;
import com.paditech.core.helper.StringUtil;

public class Customer extends User implements CustomerInfo {
    @SerializedName("point")
    private String point;

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

    public Customer()
    {
        role = TYPE_CUSTOMER;
    }
}