package com.pshop.app.transaction;

import com.pshop.app.transaction.cart.CartInfo;

public interface TransactionImpl {
    boolean pay(Transaction.TransactionCallback callback);

    boolean create(String customerId, CartInfo cart);
}
