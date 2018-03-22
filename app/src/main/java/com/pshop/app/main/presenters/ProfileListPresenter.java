package com.pshop.app.main.presenters;

import android.util.Log;

import com.paditech.core.mvp.BasePresenter;
import com.pshop.app.AppConstans;
import com.pshop.app.main.contracts.ProfileListContract;
import com.pshop.app.main.models.responses.GetListCustomerRSP;
import com.pshop.app.main.views.fragments.ProfileListFragment;
import com.pshop.app.services.AppClient;
import com.pshop.app.transaction.user.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileListPresenter extends BasePresenter<ProfileListFragment> implements ProfileListContract.Presenter, AppConstans {
    private static final int FIRST_PAGE = 1;
    private int page = FIRST_PAGE;
    private boolean isFull;
    private boolean isPending;

    @Override
    public void onCreate() {
        super.onCreate();
        loadListCustomerFromServer(false, false);
    }

    private void loadListCustomerFromServer(final boolean isRefresh, final boolean isSearch) {
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
        getView().showProgressDialog(page == FIRST_PAGE && !isRefresh);
        final String keyWord = getView().getCurrentKeyWord();
        AppClient.newInstance().getService().getListCustomer(
                app.getToken(),
                app.getAppKey(),
                page, PAGE_SIZE, keyWord)
                .enqueue(new Callback<GetListCustomerRSP>() {
                    @Override
                    public void onResponse(Call<GetListCustomerRSP> call, Response<GetListCustomerRSP> response) {
                        try {
                            Log.e("testgetListCustomer", String.valueOf(response.code()));
                            if (!keyWord.equals(getView().getCurrentKeyWord())) {
                                return;
                            }
                            isPending = false;
                            getView().showProgressDialog(false);
                            getView().setRefreshing(false);
                            if (response != null && response.body() != null) {
                                if (page == FIRST_PAGE && (response.body().getData() == null || response.body().getData().size() <= 0)) {
                                    getView().showMessageNoResult(true);
                                }
                                if (response.body().getData() != null) {
                                    GetListCustomerRSP getListCustomerRSP = response.body();
                                    List<Customer> customerList = getListCustomerRSP.getData();
                                    loadListCustomerSuccess(isRefresh, isSearch, customerList);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetListCustomerRSP> call, Throwable t) {
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

    private void resetData() {
        page = FIRST_PAGE;
        isFull = false;
    }

    private void loadListCustomerSuccess(boolean isRefresh, boolean isSearch, List<Customer> customerList) {
        page++;
        isFull = customerList.size() < PAGE_SIZE;
        if (isRefresh || isSearch) {
            getView().scrollToTop();
            getView().refreshData(customerList);
        } else {
            getView().addItemToList(customerList);
        }
    }

    @Override
    public void onLoadMore() {
        loadListCustomerFromServer(false, false);
    }

    @Override
    public void onRefresh() {
        loadListCustomerFromServer(true, false);
    }

    @Override
    public void searchByKeyWord() {
        resetData();
        loadListCustomerFromServer(false, true);
    }
}
