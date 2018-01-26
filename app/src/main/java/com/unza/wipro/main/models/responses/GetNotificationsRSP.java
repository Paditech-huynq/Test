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

public class GetNotificationsRSP extends BaseRSP {

    @SerializedName("data")
    List<Notice> notices;

    public List<Notice> getNotices() {
        return notices;
    }
}
