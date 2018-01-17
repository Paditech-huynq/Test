package com.unza.wipro.services;

import com.unza.wipro.main.models.responses.GetNewsDetailRSP;
import com.unza.wipro.main.models.responses.GetNewsRSP;
import com.unza.wipro.main.models.responses.GetPostsRSP;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AppService {

    @POST("news/posts")
    @FormUrlEncoded
    Call<GetNewsRSP> getNews(@Field("key") String key, @Field("category_id") int categoryId,
                             @Field("page") int page, @Field("page_size") int pageSize);

    @POST("news/post/detail")
    @FormUrlEncoded
    Call<GetNewsDetailRSP> getNewsDetail(@Field("post_id") String postId);

    @POST("news/posts")
    @FormUrlEncoded
    Call<GetPostsRSP> getPosts();
}
