package com.pshop.app.transaction;

import android.support.annotation.NonNull;

import com.pshop.app.main.models.responses.CreateOrderRSP;
import com.pshop.app.transaction.user.DeliveryInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderTransaction extends Transaction {
    private DeliveryInfo deliveryInfo;
    private PaymentMethod paymentMethod;

    @Override
    public boolean pay(@NonNull final TransactionCallback callback) {
        try {
            if (paymentMethod == null) {
                throw new Exception("Payment method must not be null!");
            }

            if (deliveryInfo == null) {
                throw new Exception("Deliver info must not be null!");
            }
            app.getService().doCreatOrderForCustomer(app.getToken(), app.getAppKey(), getCustomerId(), getProductByJsonString(), deliveryInfo.getName(), deliveryInfo.getDate(), deliveryInfo.getPhone(), deliveryInfo.getNote(), deliveryInfo.getAddress()).enqueue(new Callback<CreateOrderRSP>() {
                @Override
                public void onResponse(Call<CreateOrderRSP> call, Response<CreateOrderRSP> response) {
                    onPaymentSuccess(callback, response);
                }

                @Override
                public void onFailure(Call<CreateOrderRSP> call, Throwable t) {
                    onPaymentFailure(callback, t);
                }
            });
            return true;
        } catch (Exception e) {
            onPaymentFailure(callback, e);
            return false;
        }
    }

    public OrderTransaction setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
        return this;
    }

    public OrderTransaction setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }
}
