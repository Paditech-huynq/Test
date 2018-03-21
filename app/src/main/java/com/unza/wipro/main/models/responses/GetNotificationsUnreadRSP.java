package com.unza.wipro.main.models.responses;

import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.Notice;

import java.util.List;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/26/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class GetNotificationsUnreadRSP extends BaseRSP {

    @SerializedName("data")
    private Data data;

    public int count() {
        return data != null ? data.count : 0;
    }

    private class Data {
        @SerializedName("quantity")
        int count;
    }
}
