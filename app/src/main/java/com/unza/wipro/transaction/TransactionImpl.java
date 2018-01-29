package com.unza.wipro.transaction;

import com.unza.wipro.transaction.cart.CartInfo;

public interface TransactionImpl {
    boolean pay(Transaction.TransactionCallback callback);

    boolean create(String customerId, CartInfo cart);
}
