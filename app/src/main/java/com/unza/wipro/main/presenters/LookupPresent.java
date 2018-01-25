package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.LookupContract;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.models.responses.GetListProductRSP;
import com.unza.wipro.services.AppClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LookupPresent extends BasePresenter<LookupContract.ViewImpl> implements LookupContract.Presenter, AppConstans {
    private static final int FIRST_PAGE = 1;
    private int mPage = 1;
    private boolean isFull;
    private boolean isPending;
    private String lastKeyWord = "";

    @Override
    public void onCreate() {
        super.onCreate();
        loadProductFromServer(false);
    }

    @Override
    public void searchByKeyWord() {
        loadProductFromServer(false);
    }

    @Override
    public void onLoadMore() {
        loadProductFromServer(false);
    }

    @Override
    public void onRefresh() {
        loadProductFromServer(true);
    }

    private void loadProductFromServer(final boolean isRefresh) {
        if (!lastKeyWord.equals(getView().getCurrentKeyword())) {
            resetData();
            isPending = false;
        }
        if ((isPending || isFull)) {
            getView().setRefreshing(false);
            return;
        }
        if (isRefresh) {
            resetData();
            getView().setRefreshing(true);
        }
        isPending = true;
        getView().showProgressDialog(mPage == FIRST_PAGE && !isRefresh);
        final String keyword = getView().getCurrentKeyword();
        AppClient.newInstance().getService().getListProduct(mPage, PAGE_SIZE, EMPTY, keyword)
                .enqueue(new Callback<GetListProductRSP>() {
                    @Override
                    public void onResponse(Call<GetListProductRSP> call, Response<GetListProductRSP> response) {
                        if (!keyword.equals(getView().getCurrentKeyword())) {
                            lastKeyWord = getView().getCurrentKeyword();
                            return;
                        }
                        isPending = false;
                        if (getView() == null) {
                            return;
                        }
                        getView().setRefreshing(false);
                        getView().showProgressDialog(false);
                        if (response == null || response.body() == null) {
                            return;
                        }
                        onLoadProductSuccess(isRefresh, response.body().getData());
                    }

                    @Override
                    public void onFailure(Call<GetListProductRSP> call, Throwable t) {
                        if (getView() == null) {
                            isPending = false;
                            return;
                        }
                        getView().setRefreshing(false);
                        getView().showProgressDialog(false);
                    }
                });
    }

    private void onLoadProductSuccess(boolean isRefresh, List<Product> productList) {
        mPage++;
        isFull = productList.size() < PAGE_SIZE;
        if (isRefresh || !lastKeyWord.equals(getView().getCurrentKeyword())) {
            getView().refreshProductList(productList);
        } else {
            getView().updateItemToList(productList);
        }
        lastKeyWord = getView().getCurrentKeyword();
    }

    private void resetData() {
        isFull = false;
        mPage = FIRST_PAGE;
    }
}