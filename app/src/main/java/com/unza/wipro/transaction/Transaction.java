package com.unza.wipro.transaction;

import com.unza.wipro.AppConstans;
import com.unza.wipro.main.models.responses.CreateOrderRSP;
import com.unza.wipro.main.models.OrderData;
import com.unza.wipro.transaction.cart.CartInfo;

import retrofit2.Response;

public abstract class Transaction implements TransactionImpl, AppConstans {
    private CartInfo cart;

    CartInfo getCart() {
        return cart;
    }

    String getCustomerId() {
        return customerId;
    }

    private String customerId;

    @Override
    public boolean create(String customerId, CartInfo cart) {
        if (customerId != null && cart != null) {
            this.customerId = customerId;
            this.cart = cart;
            return true;
        }
        return false;
    }

    public interface TransactionCallback {
        void onSuccess(OrderData data);

        void onFailure(Throwable e);
    }

    void onPaymentSuccess(TransactionCallback callback, Response<CreateOrderRSP> response) {
        if (callback != null) {
            if (response.isSuccessful() && response.body() != null) {
                callback.onSuccess(response.body().getData());
            } else {
                onPaymentFailure(callback, new Exception(Error.UNKNOWn));
            }
        }
    }

    void onPaymentFailure(TransactionCallback callback, Throwable t) {
        if (callback != null) {
            callback.onFailure(t);
        }
    }
}
