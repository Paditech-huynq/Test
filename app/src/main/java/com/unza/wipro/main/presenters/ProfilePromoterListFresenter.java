package com.unza.wipro.main.presenters;


import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppState;
import com.unza.wipro.main.contracts.ProfilePromoterListContract;
import com.unza.wipro.main.models.responses.GetListCustomerRSP;
import com.unza.wipro.main.models.responses.GetListPromoterInGroupRSP;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.transaction.user.Customer;
import com.unza.wipro.transaction.user.Promoter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.unza.wipro.AppConstans.PAGE_SIZE;

public class ProfilePromoterListFresenter extends BasePresenter<ProfilePromoterListContract.ViewImpl> implements ProfilePromoterListContract.Presenter {
    private static final int FIRST_PAGE = 1;
    private int page = FIRST_PAGE;
    private boolean isFull;
    private boolean isPending;
    private String lastKeyWord = "";

    @Override
    public void onCreate() {
        super.onCreate();
        loadListCustomerFromServer(false);
    }

    private void loadListCustomerFromServer(final boolean isRefresh) {
        if ((isFull || isPending) && !lastKeyWord.equals(getView().getCurrentKeyWord())) {
            getView().setRefreshing(false);
            return;
        }
        if (isRefresh) {
            getView().setRefreshing(true);
            resetData();
        }
        isPending = true;
        final String keyWord = getView().getCurrentKeyWord();
        getView().showProgressDialog(page == FIRST_PAGE && !isRefresh);
        AppClient.newInstance().getService().getListPromoter(
                AppState.getInstance().getToken(),
                AppState.getInstance().getAppKey(),
                page, PAGE_SIZE, keyWord)
                .enqueue(new Callback<GetListPromoterInGroupRSP>() {
                    @Override
                    public void onResponse(Call<GetListPromoterInGroupRSP> call, Response<GetListPromoterInGroupRSP> response) {
                        isPending = false;
                        if (!keyWord.equals(getView().getCurrentKeyWord())) {
                            return;
                        }
                        if (getView() == null) {
                            return;
                        }
                        getView().showProgressDialog(false);
                        getView().setRefreshing(false);
                        GetListPromoterInGroupRSP getListCustomerRSP = response.body();
                        List<Promoter> promoterList = getListCustomerRSP.getData();
                        loadListCustomerSuccess(isRefresh, promoterList);
                    }

                    @Override
                    public void onFailure(Call<GetListPromoterInGroupRSP> call, Throwable t) {
                        isPending = false;
                        if (getView() == null) {
                            return;
                        }
                        getView().showProgressDialog(false);
                        getView().setRefreshing(false);
                    }
                });
    }

    private void resetData() {
        page = FIRST_PAGE;
        isFull = false;
    }

    private void loadListCustomerSuccess(boolean isRefresh, List<Promoter> promoterList) {
        page++;
        if (promoterList.size() < PAGE_SIZE) {
            isFull = true;
        }
        if (isRefresh || !lastKeyWord.equals(getView().getCurrentKeyWord())) {
            getView().refreshData(promoterList);
        } else {
            getView().addItemToList(promoterList);
        }
        lastKeyWord = getView().getCurrentKeyWord();
    }

    @Override
    public void onLoadMore() {
        loadListCustomerFromServer(false);
    }

    @Override
    public void onRefresh() {
        loadListCustomerFromServer(true);
    }

    @Override
    public void searchByKeyWord() {
        resetData();
        loadListCustomerFromServer(false);
    }
}
