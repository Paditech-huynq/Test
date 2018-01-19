package com.unza.wipro.services;

import com.unza.wipro.main.models.responses.GetListCustomerRSP;
import com.unza.wipro.main.models.responses.GetListProductRSP;
import com.unza.wipro.main.models.responses.GetNewsCategoriesRSP;
import com.unza.wipro.main.models.responses.GetNewsDetailRSP;
import com.unza.wipro.main.models.responses.GetNewsRSP;
import com.unza.wipro.main.models.responses.GetProductCategoryRSP;
import com.unza.wipro.main.models.responses.GetProductDetailRSP;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @POST("product/detail")
    @FormUrlEncoded
    Call<GetProductDetailRSP> getProductDetail(@Field("product_id") String productId);

    @POST("product/list")
    Call<GetListProductRSP> getListProduct(@Query("page") int page, @Query("page_size") int pageSize,
                                           @Query("category_id") String categoryID, @Query("key") String key);

    @POST("customer/list")
    Call<GetListCustomerRSP> getListCustomer(@Query("page") int page, @Query("page_size") int pageSize, @Query("key") String key);
}
