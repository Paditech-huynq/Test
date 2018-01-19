package com.unza.wipro.main.presenters;

import android.widget.Toast;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.ProductPageContract;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.models.responses.GetListProductRSP;
import com.unza.wipro.main.views.fragments.ProductPageFragment;
import com.unza.wipro.services.AppClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPagePresenter extends BasePresenter<ProductPageContract.ViewImpl> implements ProductPageContract.Presenter, AppConstans {
    private int page = 1;
    private boolean isFull;

    @Override
    public void onCreate() {
        super.onCreate();
        loadProductFromServer();
    }

    @Override
    public void onViewAppear() {

    }

    @Override
    public void onViewDisAppear() {

    }

    @Override
    public void onDestroy() {

    }

    private void loadProductFromServer() {
        getListProduct(false);
    }

    @Override
    public void onLoadMore() {
        if (!isFull) {
            getListProduct(true);
        }
    }

    private void getListProduct(final boolean isLoadMore) {
        if (!isLoadMore) getView().showProgressDialog(true);
        AppClient.newInstance().getService().getListProduct(page, PAGE_SIZE,
                ((ProductPageFragment) getView()).getCategoryId(), EMPTY)
                .enqueue(new Callback<GetListProductRSP>() {
                    @Override
                    public void onResponse(Call<GetListProductRSP> call, Response<GetListProductRSP> response) {
                        if (getView() == null) {
                            return;
                        }
                        GetListProductRSP listProductRSP = response.body();
                        List<Product> productList = listProductRSP.getData();
                        getView().updateItemToList(productList);
                        getView().showProgressDialog(false);
                        onLoadProductSuccess(productList);
                    }

                    @Override
                    public void onFailure(Call<GetListProductRSP> call, Throwable t) {
                        if (getView() == null) {
                            getView().showProgressDialog(false);
                        }
                        Toast.makeText(getView().getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void onLoadProductSuccess(List<Product> productList) {
        page += 1;
        if (productList.size() < 10) {
            isFull = true;
        }
    }
}