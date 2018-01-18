package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.NewsPageContract;
import com.unza.wipro.main.models.responses.GetNewsRSP;
import com.unza.wipro.services.AppClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 1/17/18.
 */

public class NewsPagePresenter extends BasePresenter<NewsPageContract.ViewImpl> implements NewsPageContract.Presenter {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void loadData(String key, int categoryID, final int page, int pageSize) {
        AppClient appClient = AppClient.newInstance();
        appClient.getService().getNews(key, categoryID, page, 10).enqueue(new Callback<GetNewsRSP>() {
            @Override
            public void onResponse(Call<GetNewsRSP> call, Response<GetNewsRSP> response) {
                getView().onGetData(response.body().getNews(), page != 1);
            }

            @Override
            public void onFailure(Call<GetNewsRSP> call, Throwable t) {
            }
        });
    }
}
