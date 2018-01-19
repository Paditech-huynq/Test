package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.LookupContract;
import com.unza.wipro.main.models.responses.GetListProductRSP;
import com.unza.wipro.services.AppClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LookupPresent extends BasePresenter<LookupContract.ViewImpl> implements LookupContract.Presenter, AppConstans {
    private int INDEX = 1;
    private String textSearch;
    private boolean isFull;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void searchByText() {
        isFull = false;
        INDEX = 1;
        textSearch = getView().getTextSearch();
        searchFromServer();
    }

    @Override
    public void loadMore() {
        textSearch = getView().getTextSearch();
        searchFromServer();
    }

    private void searchFromServer() {
        if (isFull) {
            return;
        }
        getView().showProgressDialog(true);
        AppClient appClient = AppClient.newInstance();
        appClient.getService().getListProduct(INDEX, PAGE_SIZE, "", textSearch)
                .enqueue(new Callback<GetListProductRSP>() {
                    @Override
                    public void onResponse(Call<GetListProductRSP> call, Response<GetListProductRSP> response) {
                        if (getView() == null) {
                            return;
                        }
                        getView().showProgressDialog(false);
                        if (response == null || response.body()==null) {
                            return;
                        }
                        if (response.body().getData().size() < PAGE_SIZE) {
                            isFull = true;
                        } else {
                            INDEX++;
                        }
                        getView().updateToListItem(response.body().getData());
                    }

                    @Override
                    public void onFailure(Call<GetListProductRSP> call, Throwable t) {
                        if (getView() == null) {
                            return;
                        }
                        getView().showProgressDialog(false);
                    }
                });
    }
}
