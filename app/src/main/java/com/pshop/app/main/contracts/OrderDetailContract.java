package com.pshop.app.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.pshop.app.main.models.Order;
import com.pshop.app.transaction.user.Customer;

public interface OrderDetailContract {
    interface ViewImpl extends BaseViewImpl {
        int getOrderId();

        void showOrderDetail(Order order);

        void setCustomer(Customer customer);

        Customer getCustomer();

        void backToLastScreen();
    }

    interface Presenter extends BasePresenterImpl {
        void onSubmitTransactionButtonClick();
    }
}