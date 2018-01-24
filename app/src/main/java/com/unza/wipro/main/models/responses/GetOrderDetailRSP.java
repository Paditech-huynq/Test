package com.unza.wipro.main.models.responses;

import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.Order;
import com.unza.wipro.main.models.Product;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/17/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class GetOrderDetailRSP extends BaseRSP {

    @SerializedName("data")
    Order order;

    public Order getOrder() {
        return order;
    }
}
