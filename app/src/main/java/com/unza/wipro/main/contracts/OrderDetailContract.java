package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.Order;
import com.unza.wipro.transaction.Transaction;
import com.unza.wipro.transaction.user.User;

public interface OrderDetailContract {
    interface ViewImpl extends BaseViewImpl {
        int getOrderId();

        void showOrderDetail(Order order);

        void setUser(User user);
    }

    interface Presenter extends BasePresenterImpl {
        void loadData();

        void submitOrder(Transaction transaction);
    }
}