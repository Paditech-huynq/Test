package com.unza.wipro.main.contracts;

import android.location.Location;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.Product;

public interface ProductDetailContract {
    interface ViewImpl extends BaseViewImpl {
        Product getProduct();

        void showProductDetail(Product product);

        void updateCartCount();

        void requestGrantPermission();

        void updateViewWithCurrentLocation(Location location);
    }

    interface Presenter extends BasePresenterImpl {
    }
}