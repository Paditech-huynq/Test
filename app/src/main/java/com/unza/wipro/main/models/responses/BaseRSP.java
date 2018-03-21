package com.unza.wipro.main.models.responses;

import com.google.gson.annotations.SerializedName;
import com.unza.wipro.AppConstans;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/17/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class BaseRSP {
    @SerializedName("result")
    int result;
    @SerializedName("current_time")
    long time;
    @SerializedName("message")
    String message;

    public int getResult() {
        return result;
    }

    public long getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() { return result == AppConstans.Api.Success;}
}
