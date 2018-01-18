package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.Product;

public interface ProductDetailContract {
    interface ViewImpl extends BaseViewImpl {
        Product getProduct();
        void showProductDetail(Product product);
    }

    interface Presenter extends BasePresenterImpl {
        void loadData();
    }
}