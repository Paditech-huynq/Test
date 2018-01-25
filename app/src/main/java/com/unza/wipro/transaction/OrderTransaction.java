package com.unza.wipro.transaction;

import com.unza.wipro.transaction.cart.Cart;
import com.unza.wipro.transaction.user.DeliveryInfo;

import java.util.Date;

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
    public boolean pay() throws Exception {
        throw new Exception("Order transaction must have a payment method");
    }

    @Override
    public boolean create(int customerId, Cart cart) {
        return false;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }
}
