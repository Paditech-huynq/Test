package com.unza.wipro.main.models.responses;


import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.News;

import java.util.List;

public class GetPostsRSP extends BaseRSP {
    @SerializedName("data")
    List<News> posts;
    public List<News> getPosts() {
        return posts;
    }
}
