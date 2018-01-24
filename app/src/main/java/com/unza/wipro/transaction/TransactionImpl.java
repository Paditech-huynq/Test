package com.unza.wipro.transaction;

import com.unza.wipro.transaction.cart.Cart;

public interface TransactionImpl {
    boolean pay() throws Exception;

    boolean create(int customerId, Cart cart);
}
