package com.pshop.app.main.models.responses;

import com.google.gson.annotations.SerializedName;
import com.pshop.app.main.models.Notice;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/26/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class ReadNotificationRSP extends BaseRSP {

    @SerializedName("data")
    Notice notice;

    public Notice getNotice() {
        return notice;
    }
}
