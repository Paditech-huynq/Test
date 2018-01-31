package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
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

    @Override
    public void onCreate() {
        super.onCreate();
        loadProductFromServer(false, false);
    }

    @Override
    public void searchByKeyWord() {
        loadProductFromServer(false, true);
    }

    @Override
    public void onLoadMore() {
        loadProductFromServer(false, false);
    }

    @Override
    public void onRefresh() {
        loadProductFromServer(true, false);
    }

    private void loadProductFromServer(final boolean isRefresh, final boolean isSearch) {
        getView().showMessageNoResult(false);
        if (isSearch) {
            resetData();
            isPending = false;
        }
        if ((isPending)) {
            getView().setRefreshing(false);
            return;
        }
        if (isRefresh) {
            resetData();
        }
        if (isFull) {
            getView().setRefreshing(false);
            return;
        }
        isPending = true;
        getView().setRefreshing(isRefresh);
        getView().showProgressDialog(mPage == FIRST_PAGE && !isRefresh);
        final String keyword = getView().getCurrentKeyword();
        AppClient.newInstance().getService().getListProduct(mPage, PAGE_SIZE, EMPTY, keyword)
                .enqueue(new Callback<GetListProductRSP>() {
                    @Override
                    public void onResponse(Call<GetListProductRSP> call, Response<GetListProductRSP> response) {
                        if (!keyword.equals(getView().getCurrentKeyword())) {
                            return;
                        }
                        isPending = false;
                        try {
                            getView().setRefreshing(false);
                            getView().showProgressDialog(false);
                            if (response != null && response.body() != null) {
                                if (mPage == FIRST_PAGE && (response.body().getData() == null || response.body().getData().size() <= 0)) {
                                    getView().showMessageNoResult(true);
                                }
                                if (response.body().getData() != null) {
                                    onLoadProductSuccess(isRefresh, isSearch, response.body().getData());
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetListProductRSP> call, Throwable t) {
                        try {
                            isPending = false;
                            if (getView() == null) {
                                return;
                            }
                            getView().setRefreshing(false);
                            getView().showProgressDialog(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void onLoadProductSuccess(boolean isRefresh, boolean isSearch, List<Product> productList) {
        mPage++;
        isFull = productList.size() < PAGE_SIZE;
        if (isRefresh || isSearch) {
            getView().refreshProductList(productList);
        } else {
            getView().updateItemToList(productList);
        }
    }

    private void resetData() {
        isFull = false;
        mPage = FIRST_PAGE;
    }

    @Override
    public void onViewDisAppear() {
        super.onViewDisAppear();
        resetData();
    }
}