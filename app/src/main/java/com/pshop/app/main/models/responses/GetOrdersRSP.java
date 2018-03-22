package com.pshop.app.main.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pshop.app.main.models.Order;

import java.util.List;

public class GetOrdersRSP extends BaseRSP {
    @SerializedName("data")
    @Expose
    private List<Order> data;

    public List<Order> getData() {
        return data;
    }
}