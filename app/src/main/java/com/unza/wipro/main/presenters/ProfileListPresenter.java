package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.ProfileListContract;
import com.unza.wipro.main.models.Customer;
import com.unza.wipro.main.models.responses.GetListCustomerRSP;
import com.unza.wipro.main.views.fragments.ProfileListFragment;
import com.unza.wipro.services.AppClient;

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
        loadListCustomerFromServer(false);
    }

    private void loadListCustomerFromServer(final boolean isRefresh) {
        if (isFull || isPending) {
            return;
        }
        if (isRefresh) {
            resetData();
        }
        isPending = true;
        getView().showProgressDialog(page == FIRST_PAGE);
        AppClient.newInstance().getService().getListCustomer(page, PAGE_SIZE, EMPTY)
                .enqueue(new Callback<GetListCustomerRSP>() {
                    @Override
                    public void onResponse(Call<GetListCustomerRSP> call, Response<GetListCustomerRSP> response) {
                        if (getView() == null) {
                            return;
                        }
                        getView().showProgressDialog(false);
                        GetListCustomerRSP getListCustomerRSP = response.body();
                        List<Customer> customerList = getListCustomerRSP.getData();
                        oadListCustomerSuccess(isRefresh, customerList);
                    }

                    @Override
                    public void onFailure(Call<GetListCustomerRSP> call, Throwable t) {
                        if (getView() == null) {
                            getView().showProgressDialog(false);
                        }
                    }
                });
    }

    private void resetData() {
        page = FIRST_PAGE;
    }

    private void oadListCustomerSuccess(boolean isRefresh, List<Customer> customerList) {
        page++;
        isPending = false;
        if (customerList.size() < PAGE_SIZE) {
            isFull = true;
        }
        if (isRefresh) {
            getView().refreshData(customerList);
        } else {
            getView().addItemToList(customerList);
        }
    }

    @Override
    public void onLoadMore() {
        loadListCustomerFromServer(false);
    }

    @Override
    public void onRefresh(List<Customer> customerList) {

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
}
