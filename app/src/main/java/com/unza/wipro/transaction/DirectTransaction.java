package com.unza.wipro.transaction;

import android.support.annotation.NonNull;

import com.unza.wipro.main.models.responses.CreateOrderRSP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectTransaction extends Transaction {

    @Override
    public boolean pay(@NonNull final TransactionCallback callback) {
        try {
            app.getService().doCreatOrderForPromoter(app.getToken(), app.getAppKey(), getCustomerId(), getProductByJsonString()).enqueue(new Callback<CreateOrderRSP>() {
                @Override
                public void onResponse(Call<CreateOrderRSP> call, Response<CreateOrderRSP> response) {
                    onPaymentSuccess(callback, response);
                }

                @Override
                public void onFailure(Call<CreateOrderRSP> call, Throwable t) {
                    onPaymentFailure(callback,t);
                }
            });
            return true;
        } catch (Exception e) {
            onPaymentFailure(callback,e);
            e.printStackTrace();
        }
        return false;
    }
}
