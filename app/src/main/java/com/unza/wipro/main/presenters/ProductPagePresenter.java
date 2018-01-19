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
    private static final int FIRST_PAGE = 1;
    private int page = FIRST_PAGE;
    private boolean isFull;
    private boolean isPending;

    @Override
    public void onCreate() {
        super.onCreate();
        loadProductFromServer(false);
    }

    private void loadProductFromServer(final boolean isRefresh) {
        if (isPending || isFull) {
            return;
        }
        if (isRefresh) {
            resetData();
        }
        isPending = true;
        getView().showProgressDialog(page == FIRST_PAGE);
        AppClient.newInstance().getService().getListProduct(page, PAGE_SIZE,
                ((ProductPageFragment) getView()).getCategoryId(), EMPTY)
                .enqueue(new Callback<GetListProductRSP>() {
                    @Override
                    public void onResponse(Call<GetListProductRSP> call, Response<GetListProductRSP> response) {
                        if (getView() == null) {
                            return;
                        }
                        getView().showProgressDialog(false);
                        GetListProductRSP listProductRSP = response.body();
                        List<Product> productList = listProductRSP.getData();
                        onLoadProductSuccess(isRefresh, productList);
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

    private void resetData() {
        page = FIRST_PAGE;
    }

    @Override
    public void onLoadMore() {
        loadProductFromServer(false);
    }

    @Override
    public void onRefresh(List<Product> products) {
        loadProductFromServer(true);
    }

    private void onLoadProductSuccess(boolean isRefresh, List<Product> productList) {
        page++;
        isPending = false;
        if (productList.size() < PAGE_SIZE) {
            isFull = true;
        }
        if (isRefresh) {
            getView().refreshData(productList);
        } else {
            getView().addItemToList(productList);
        }
    }
}