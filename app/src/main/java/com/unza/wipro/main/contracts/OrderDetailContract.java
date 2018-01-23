package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.Order;
import com.unza.wipro.main.models.Product;

public interface OrderDetailContract {
    interface ViewImpl extends BaseViewImpl {
        Order getOrder();
        void showOrderDetail(Order order);
    }

    interface Presenter extends BasePresenterImpl {
        void loadData();
    }
}