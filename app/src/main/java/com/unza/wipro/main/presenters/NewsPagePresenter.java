package com.unza.wipro.main.presenters;

import com.paditech.core.common.BaseConstant;
import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.NewsPageContract;
import com.unza.wipro.main.models.responses.GetNewsRSP;
import com.unza.wipro.services.AppClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsPagePresenter extends BasePresenter<NewsPageContract.ViewImpl> implements NewsPageContract.Presenter, AppConstans {
    private int INDEX = 1;
    private int categoryId;
    private boolean isFull = false;
    @Override
    public void onCreate() {
        super.onCreate();
        categoryId = getView().getCategory().getId();
        loadDataFromServer(false);
    }

    public void loadDataFromServer(final boolean isRefresh) {
        if (isFull) {
            return;
        }
        getView().showProgressDialog(true);
        AppClient appClient = AppClient.newInstance();
        appClient.getService().getNews(BaseConstant.EMPTY, categoryId, INDEX, PAGE_SIZE).enqueue(new Callback<GetNewsRSP>() {
            @Override
            public void onResponse(Call<GetNewsRSP> call, Response<GetNewsRSP> response) {
                if (getView() == null) {
                    return;
                }
                getView().showProgressDialog(false);
                if (response != null && response.body() != null && response.body().getNews() != null && response.body().getNews().size() > 0) {
                    if (response.body().getNews().size() < PAGE_SIZE) {
                        isFull = true;
                    }
                    if (isRefresh) {
                        INDEX = 1;
                        getView().refreshList(response.body().getNews());
                    } else {
                        getView().updateItemToList(response.body().getNews());
                        INDEX++;
                    }
                }
            }

            @Override
            public void onFailure(Call<GetNewsRSP> call, Throwable t) {
                if (getView() == null) {
                    return;
                }
                getView().showProgressDialog(false);
            }
        });
    }

    @Override
    public void onLoadMore() {
        loadDataFromServer(false);
    }
}
