package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.ProductDetailContract;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.models.responses.GetProductDetailRSP;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.transaction.cart.Cart;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailPresenter extends BasePresenter<ProductDetailContract.ViewImpl> implements ProductDetailContract.Presenter, AppConstans,  Cart.CartChangeListener {

    @Override
    public void onCreate() {
        super.onCreate();
        getProductDetail();
        app.addCartChangeListener(this);
    }

    @Override
    public void loadData() {
        getProductDetail();
    }

    @Override
    public void onCartUpdate() {
        getView().updateCartCount();
    }

    private void getProductDetail() {
        Product product = getView().getProduct();
        if (product == null || product.getId() < 0) return;
        getView().showProductDetail(product);
//        getView().showProgressDialog(true);
//        AppClient.newInstance().getService().getProductDetail(String.valueOf(product.getId()))
//                .enqueue(new Callback<GetProductDetailRSP>() {
//                    @Override
//                    public void onResponse(Call<GetProductDetailRSP> call, Response<GetProductDetailRSP> response) {
//                        try {
//                            getView().showProgressDialog(false);
//                            if (response.body() != null) {
//                                getView().showProductDetail(response.body().getProduct());
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<GetProductDetailRSP> call, Throwable t) {
//                        getView().showProgressDialog(false);
//                        getView().showToast(t.getLocalizedMessage());
//                    }
//                });
    }
}
