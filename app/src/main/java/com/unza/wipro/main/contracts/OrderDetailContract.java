package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.Order;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.transaction.Transaction;

public interface OrderDetailContract {
    interface ViewImpl extends BaseViewImpl {
        int getOrderId();
        void showOrderDetail(Order order);
    }

    interface Presenter extends BasePresenterImpl {
        void loadData();
        void  submitOrder(Transaction transaction);
    }
}