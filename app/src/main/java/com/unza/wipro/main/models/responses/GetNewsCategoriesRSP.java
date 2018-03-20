package com.unza.wipro.main.models.responses;

import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.News;
import com.unza.wipro.main.models.NewsCategory;

import java.util.List;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/17/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class GetNewsCategoriesRSP extends BaseRSP {

    @SerializedName("data")
    List<NewsCategory> categories;

    public List<NewsCategory> getCategories() {
        return categories;
    }
}
