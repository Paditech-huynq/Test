package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.ProductDetailContract;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.transaction.cart.Cart;

public class ProductDetailPresenter extends BasePresenter<ProductDetailContract.ViewImpl> implements ProductDetailContract.Presenter, AppConstans, Cart.CartChangeListener {

    @Override
    public void onCreate() {
        super.onCreate();
        app.addCartChangeListener(this);
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        getProductDetail();
    }

    @Override
    public void loadData() {
        getProductDetail();
    }

    @Override
    public void onCartUpdate() {
        if (getView() != null) getView().updateCartCount();
    }

    private void getProductDetail() {
        try {
            Product product = getView().getProduct();
            if (product == null || product.getId() < 0) return;
            getView().showProductDetail(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
