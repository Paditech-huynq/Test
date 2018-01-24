package com.unza.wipro.transaction;

import com.unza.wipro.transaction.user.CustomerInfo;
import com.unza.wipro.transaction.cart.CartInfo;

public abstract class Transaction implements TransactionImpl {
    private CartInfo cart;
    private CustomerInfo customer;
}
