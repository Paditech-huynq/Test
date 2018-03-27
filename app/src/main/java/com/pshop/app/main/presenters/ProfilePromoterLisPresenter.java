package com.pshop.app.main.presenters;


import com.paditech.core.mvp.BasePresenter;
import com.pshop.app.AppConstans;
import com.pshop.app.main.contracts.ProfilePromoterListContract;
import com.pshop.app.main.models.responses.GetListPromoterInGroupRSP;
import com.pshop.app.services.AppClient;
import com.pshop.app.transaction.user.Promoter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.pshop.app.AppConstans.PAGE_SIZE;

public class ProfilePromoterLisPresenter extends BasePresenter<ProfilePromoterListContract.ViewImpl> implements ProfilePromoterListContract.Presenter {
    private static final int FIRST_PAGE = 1;
    private int page = FIRST_PAGE;
    private boolean isFull;
    private boolean isPending;

    @Override
    public void onCreate() {
        super.onCreate();
        loadListPromoterFromServer(false, false);
    }

    private void loadListPromoterFromServer(final boolean isRefresh, final boolean isSearch) {
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
        AppClient.newInstance().getService().getListPromoter(
                AppConstans.app.getToken(),
                AppConstans.app.getAppKey(),
                page, PAGE_SIZE, keyWord)
                .enqueue(new Callback<GetListPromoterInGroupRSP>() {
                    @Override
                    public void onResponse(Call<GetListPromoterInGroupRSP> call, Response<GetListPromoterInGroupRSP> response) {
                        try {
                            if (!keyWord.equals(getView().getCurrentKeyWord())) {
                                return;
                            }
                            isPending = false;
                            if (getView() == null) {
                                return;
                            }
                            getView().showProgressDialog(false);
                            getView().setRefreshing(false);
                            GetListPromoterInGroupRSP getListCustomerRSP = response.body();
                            List<Promoter> promoterList = getListCustomerRSP.getData();
                            if (promoterList != null) {
                                loadListCustomerSuccess(isRefresh, isSearch, promoterList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetListPromoterInGroupRSP> call, Throwable t) {
                        try {
                            isPending = false;
                            if (getView() == null) {
                                return;
                            }
                            getView().showProgressDialog(false);
                            getView().setRefreshing(false);
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

    private void loadListCustomerSuccess(boolean isRefresh, boolean isSearch, List<Promoter> promoterList) {
        page++;
        if (promoterList.size() < PAGE_SIZE) {
            isFull = true;
        }
        if (isRefresh || isSearch) {
            getView().scrollToTop();
            getView().refreshData(promoterList);
        } else {
            getView().addItemToList(promoterList);
        }
    }

    @Override
    public void onLoadMore() {
        loadListPromoterFromServer(false, false);
    }

    @Override
    public void onRefresh() {
        loadListPromoterFromServer(true, false);
    }

    @Override
    public void searchByKeyWord() {
        resetData();
        loadListPromoterFromServer(false, true);
    }
}
