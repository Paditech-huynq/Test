package com.pshop.app.main.models.responses;

import com.google.gson.annotations.SerializedName;
import com.pshop.app.main.models.News;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/17/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class GetNewsDetailRSP extends BaseRSP {

    @SerializedName("data")
    News news;

    public News getNews() {
        return news;
    }
}
