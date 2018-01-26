package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.AppState;
import com.unza.wipro.main.contracts.ProfileListContract;
import com.unza.wipro.main.models.responses.GetListCustomerRSP;
import com.unza.wipro.main.views.fragments.ProfileListFragment;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.transaction.user.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileListPresenter extends BasePresenter<ProfileListFragment> implements ProfileListContract.Presenter, AppConstans {
    private static final int FIRST_PAGE = 1;
    private int page = FIRST_PAGE;
    private boolean isFull;
    private boolean isPending;
    private String lastKeyWord = "";

    @Override
    public void onCreate() {
        super.onCreate();
        loadListPromoterFromServer(false);
    }

    private void loadListPromoterFromServer(final boolean isRefresh) {
        if ((isFull || isPending) && !lastKeyWord.equals(getView().getCurrentKeyWord())) {
            getView().setRefreshing(false);
            return;
        }
        if (isRefresh) {
            resetData();
            getView().setRefreshing(true);
        }
        if (isFull) {
            getView().setRefreshing(false);
            return;
        }
        isPending = true;
        final String keyWord = getView().getCurrentKeyWord();
        getView().showProgressDialog(page == FIRST_PAGE && !isRefresh);
        AppClient.newInstance().getService().getListCustomer(
                AppState.getInstance().getToken(),
                AppState.getInstance().getAppKey(),
                page, PAGE_SIZE, keyWord)
                .enqueue(new Callback<GetListCustomerRSP>() {
                    @Override
                    public void onResponse(Call<GetListCustomerRSP> call, Response<GetListCustomerRSP> response) {
                        if (!keyWord.equals(getView().getCurrentKeyWord())) {
                            lastKeyWord = keyWord;
                            return;
                        }
                        isPending = false;
                        if (getView() == null) {
                            return;
                        }
                        getView().showProgressDialog(false);
                        getView().setRefreshing(false);
                        GetListCustomerRSP getListCustomerRSP = response.body();
                        List<Customer> customerList = getListCustomerRSP.getData();
                        loadListCustomerSuccess(isRefresh, customerList);
                    }

                    @Override
                    public void onFailure(Call<GetListCustomerRSP> call, Throwable t) {
                        isPending = false;
                        if (getView() == null) {
                            return;
                        }
                        getView().setRefreshing(false);
                        getView().showProgressDialog(false);
                    }
                });
    }

    private void resetData() {
        page = FIRST_PAGE;
        isFull = false;
    }

    private void loadListCustomerSuccess(boolean isRefresh, List<Customer> customerList) {
        page++;
        isFull = customerList.size() < PAGE_SIZE;
        if (isRefresh || !lastKeyWord.equals(getView().getCurrentKeyWord())) {
            getView().refreshData(customerList);
        } else {
            getView().addItemToList(customerList);
        }
        lastKeyWord = getView().getCurrentKeyWord();
    }

    @Override
    public void onLoadMore() {
        loadListPromoterFromServer(false);
    }

    @Override
    public void onRefresh() {
        loadListPromoterFromServer(true);
    }

    @Override
    public void searchByKeyWord() {
        resetData();
        loadListPromoterFromServer(false);
    }
}
