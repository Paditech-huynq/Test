package com.pshop.app.transaction;

import android.util.SparseArray;

import com.google.gson.Gson;
import com.pshop.app.AppConstans;
import com.pshop.app.main.models.OrderData;
import com.pshop.app.main.models.Product;
import com.pshop.app.main.models.responses.CreateOrderRSP;
import com.pshop.app.transaction.cart.CartInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public abstract class Transaction implements TransactionImpl, AppConstans {
    public enum PaymentMethod {
        COD("Cod"), CreditCard("CreditCard");

        private final String value;

        PaymentMethod(String value) {
            this.value = value;
        }
    }

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
        void onSuccess(Transaction transaction, OrderData data, String message);

        void onFailure(Transaction transaction, Throwable e);
    }

    void onPaymentSuccess(TransactionCallback callback, Response<CreateOrderRSP> response) {
        if (callback != null) {
            if (response.isSuccessful() && response.body() != null) {
                callback.onSuccess(this, response.body().getData(), response.body().getMessage());
            } else {
                onPaymentFailure(callback, new Exception(Error.UNKNOWn));
            }
        }
    }

    void onPaymentFailure(TransactionCallback callback, Throwable t) {
        if (callback != null) {
            callback.onFailure(this, t);
        }
    }

    String getProductByJsonString() {
        SparseArray<Product> cartProductList = getCart().getProducts();
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < cartProductList.size(); i++) {
            int key = cartProductList.keyAt(i);
            products.add(cartProductList.get(key));
        }

        return new Gson().toJson(products);
    }
}
