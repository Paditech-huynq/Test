package com.pshop.app.main.presenters;

import android.widget.Toast;

import com.paditech.core.mvp.BasePresenter;
import com.pshop.app.AppConstans;
import com.pshop.app.main.contracts.ProductPageContract;
import com.pshop.app.main.models.Product;
import com.pshop.app.main.models.responses.GetListProductRSP;
import com.pshop.app.main.views.fragments.ProductPageFragment;
import com.pshop.app.services.AppClient;

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
        if (isPending) {
            getView().setRefreshing(false);
            return;
        }
        if (isRefresh) {
            resetData();
            getView().showProgressDialog(true);
        }
        if (isFull) {
            getView().setRefreshing(false);
            return;
        }
        isPending = true;
        getView().showProgressDialog(page == FIRST_PAGE && !isRefresh);
        AppClient.newInstance().getService().getListProduct(page, PAGE_SIZE,
                ((ProductPageFragment) getView()).getCategoryId(), EMPTY)
                .enqueue(new Callback<GetListProductRSP>() {
                    @Override
                    public void onResponse(Call<GetListProductRSP> call, Response<GetListProductRSP> response) {
                        isPending = false;
                        try {
                            getView().showProgressDialog(false);
                            getView().setRefreshing(false);
                            if (response.body() != null) {
                                GetListProductRSP listProductRSP = response.body();
                                List<Product> productList = listProductRSP.getData();
                                onLoadProductSuccess(isRefresh, productList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetListProductRSP> call, Throwable t) {
                        try {
                            isPending = false;
                            if (getView() != null) {
                                getView().showProgressDialog(false);
                                getView().setRefreshing(false);
                                Toast.makeText(getView().getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void resetData() {
        page = FIRST_PAGE;
        isFull = false;
    }

    @Override
    public void onLoadMore() {
        loadProductFromServer(false);
    }

    @Override
    public void onRefresh() {
        loadProductFromServer(true);
    }

    private void onLoadProductSuccess(boolean isRefresh, List<Product> productList) {
        page++;
        if (productList.size() < PAGE_SIZE) {
            isFull = true;
        }
        if (isRefresh) {
            getView().scrollToTop();
            getView().refreshData(productList);
        } else {
            getView().addItemToList(productList);
        }
    }
}