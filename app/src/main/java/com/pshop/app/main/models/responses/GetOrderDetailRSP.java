package com.pshop.app.main.models.responses;

import com.google.gson.annotations.SerializedName;
import com.pshop.app.main.models.Order;

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
