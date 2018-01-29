package com.unza.wipro.services;

import com.unza.wipro.main.models.responses.BaseRSP;
import com.unza.wipro.main.models.responses.CommonRSP;
import com.unza.wipro.main.models.responses.CreateCustomerRSP;
import com.unza.wipro.main.models.responses.CreateOrderRSP;
import com.unza.wipro.main.models.responses.GetListCustomerRSP;
import com.unza.wipro.main.models.responses.GetListProductRSP;
import com.unza.wipro.main.models.responses.GetListPromoterInGroupRSP;
import com.unza.wipro.main.models.responses.GetNewsCategoriesRSP;
import com.unza.wipro.main.models.responses.GetNewsDetailRSP;
import com.unza.wipro.main.models.responses.GetNewsRSP;
import com.unza.wipro.main.models.responses.GetNotificationsRSP;
import com.unza.wipro.main.models.responses.GetOrderDetailRSP;
import com.unza.wipro.main.models.responses.GetOrdersRSP;
import com.unza.wipro.main.models.responses.GetProductCategoryRSP;
import com.unza.wipro.main.models.responses.GetProductDetailRSP;
import com.unza.wipro.main.models.responses.GetUserProfileRSP;
import com.unza.wipro.main.models.responses.LoginRSP;
import com.unza.wipro.main.models.responses.ReadNotificationRSP;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @POST("member/login")
    @FormUrlEncoded
    Call<LoginRSP> login(@Field("grant_type") String grant_type,
                         @Field("client_id") String client_id,
                         @Field("client_secret") String client_secret,
                         @Field("phone") String phone,
                         @Field("password") String password,
                         @Field("device_token") String device_token);

    @POST("customer/list")
    Call<GetListCustomerRSP> getListCustomer(@Header("Authorization") String token, @Header("AppKey") String appKey,
                                             @Query("page") int page, @Query("page_size") int pageSize,
                                             @Query("key") String key);

    @POST("member/groupList")
    Call<GetListPromoterInGroupRSP> getListPromoter(@Header("Authorization") String token, @Header("AppKey") String appKey,
                                                    @Query("page") int page, @Query("page_size") int pageSize,
                                                    @Query("key") String key);

    @Multipart
    @POST("customer/create")
    Call<CreateCustomerRSP> createCustomer(@Header("Authorization") String token, @Header("AppKey") String appKey,
                                           @Part("name") RequestBody name, @Part("phone") RequestBody phone, @Part("email") RequestBody email,
                                           @Part("address") RequestBody address, @Part MultipartBody.Part avatar);

    @POST("order/list")
    @FormUrlEncoded
    Call<GetOrdersRSP> getOrders(@Header("Authorization") String token, @Header("AppKey") String appKey,
                                 @Field("from") Long from, @Field("to") Long to,
                                 @Field("page") Integer page, @Field("page_size") Integer pageSize);

    @POST("order/detail")
    @FormUrlEncoded
    Call<GetOrderDetailRSP> getOrderDetail(@Header("Authorization") String token, @Header("AppKey") String appKey, @Field("order_id") int orderId);

    @POST("member/detail")
    Call<GetUserProfileRSP> getUserProfile(@Header("Authorization") String token, @Header("AppKey") String appKey);

    @POST("order/create")
    @FormUrlEncoded
    Call<BaseRSP> createOrder(@Header("Authorization") String token, @Header("AppKey") String appKey,
                              @Field("customer_id") String customerId, @Field("products") String products,
                              @Field("billing_name") String name, @Field("billing_phone") String phone,
                              @Field("billing_address") String address, @Field("billing_date") String date,
                              @Field("billing_note") String note);

    @POST("notifications/list")
    Call<GetNotificationsRSP> getNotifications(@Header("Authorization") String token, @Header("AppKey") String appKey);

    @POST("notifications/read")
    @FormUrlEncoded
    Call<ReadNotificationRSP> readNotification(@Header("Authorization") String token, @Header("AppKey") String appKey, @Field("notification_id") int notificationId);

    @POST("member/forgot")
    @FormUrlEncoded
    Call<CommonRSP> forgotPassword(@Field("phone") String phone);

    @POST("member/otp")
    @FormUrlEncoded
    Call<CommonRSP> resetPassword(@Field("phone") String phone, @Field("otp") String otp, @Field("new_password") String newPass,
                                @Field("re_new_password") String confirmPass);

    @POST("order/create")
    @FormUrlEncoded
    Call<CreateOrderRSP> doCreatOrderForPromoter(@Header("Authorization") String token, @Header("AppKey") String appKey, @Field("customer_id") String customerId, @Field("products") String productsList);

    @POST("order/create")
    @FormUrlEncoded
    Call<CreateOrderRSP> doCreatOrderForCustomer(@Header("Authorization") String token, @Header("AppKey") String appKey, @Field("customer_id") String customerId, @Field("products") String productsList,
                                                 @Field("billing_name") String billingName,@Field("billing_date") String billingDate,@Field("billing_phone") String billingPhone,@Field("billing_note") String billingNote);
}
