package com.unza.wipro.transaction;

import com.unza.wipro.transaction.cart.Cart;

public class DirectTransaction extends Transaction {

    @Override
    public boolean pay() {
        return false;
    }

    @Override
    public boolean create(int customerId, Cart cart) {
        return false;
    }
}
