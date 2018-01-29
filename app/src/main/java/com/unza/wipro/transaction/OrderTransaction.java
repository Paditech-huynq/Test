package com.unza.wipro.transaction;

import com.unza.wipro.transaction.cart.CartInfo;
import com.unza.wipro.transaction.user.DeliveryInfo;

public class OrderTransaction extends Transaction {
    private DeliveryInfo deliveryInfo;

    enum PaymentMethod {
        COD("Cod"), CreditCard("CreditCard");

        private final String value;

        PaymentMethod(String value) {
            this.value = value;
        }
    }

    public boolean pay(PaymentMethod paymentMethod) {
        //todo: implement payment for oder transaction here
        return false;
    }

    @Override
    public boolean pay(TransactionCallback callback) throws Exception {
        throw new Exception("Order transaction must have a payment method");
    }

    @Override
    public boolean create(String customerId, CartInfo cart) {
        return false;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }
}
