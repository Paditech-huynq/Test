package com.unza.wipro.transaction;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.models.responses.CreateOrderRSP;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectTransaction extends Transaction {

    @Override
    public boolean pay(@NonNull final TransactionCallback callback) {
        try {
            SparseArray<Product> cartProductList = getCart().getProducts();
            List<Product> products = new ArrayList<>();
            for (int i = 0; i < cartProductList.size(); i++) {
                int key = cartProductList.keyAt(i);
                products.add(cartProductList.get(key));
            }
            app.getService().doCreatOrderForPromoter(app.getToken(), app.getAppKey(), getCustomerId(), new Gson().toJson(products)).enqueue(new Callback<CreateOrderRSP>() {
                @Override
                public void onResponse(Call<CreateOrderRSP> call, Response<CreateOrderRSP> response) {
                    onPaymentSuccess(callback, response);
                }

                @Override
                public void onFailure(Call<CreateOrderRSP> call, Throwable t) {
                    onPaymentFailure(callback,t);
                }
            });
        } catch (Exception e) {
            onPaymentFailure(callback,e);
            e.printStackTrace();
        }
        return false;
    }
}
