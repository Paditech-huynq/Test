package com.unza.wipro.main.models.responses;

import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.News;

import java.util.List;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/17/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class GetNewsRSP extends BaseRSP {

    @SerializedName("data")
    List<News> news;

    public List<News> getNews() {
        return news;
    }
}
