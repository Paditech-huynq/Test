package com.unza.wipro.services;

import com.unza.wipro.main.models.responses.GetNewsCategoriesRSP;
import com.unza.wipro.main.models.responses.GetNewsDetailRSP;
import com.unza.wipro.main.models.responses.GetNewsRSP;
import com.unza.wipro.main.models.responses.GetProductCategoryRSP;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AppService {

    @POST("news/categories")
    @FormUrlEncoded
    Call<GetNewsCategoriesRSP> getNewsCategories(@Field("key") String key, @Field("category_id") Integer categoryId,
                                                 @Field("page") Integer page, @Field("page_size") Integer pageSize);

    @POST("news/posts")
    @FormUrlEncoded
    Call<GetNewsRSP> getNews(@Field("key") String key, @Field("category_id") Integer categoryId,
                             @Field("page") Integer page, @Field("page_size") Integer pageSize);

    @POST("news/post/detail")
    @FormUrlEncoded
    Call<GetNewsDetailRSP> getNewsDetail(@Field("post_id") String postId);

    @POST("product/category")
    Call<GetProductCategoryRSP> getProductCategory();
}
