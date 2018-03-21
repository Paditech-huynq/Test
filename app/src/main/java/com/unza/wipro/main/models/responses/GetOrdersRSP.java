package com.unza.wipro.main.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.Order;
import com.unza.wipro.main.models.Product;

import java.util.List;

public class GetOrdersRSP extends BaseRSP {
    @SerializedName("data")
    @Expose
    private List<Order> data;

    public List<Order> getData() {
        return data;
    }
}