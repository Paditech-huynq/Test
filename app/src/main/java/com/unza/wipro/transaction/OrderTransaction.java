package com.unza.wipro.transaction;

import com.unza.wipro.transaction.cart.Cart;

import java.util.Date;

public class OrderTransaction extends Transaction {
    private Date receiveTime;

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


    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

}
