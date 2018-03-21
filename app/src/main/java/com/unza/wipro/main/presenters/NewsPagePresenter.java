package com.unza.wipro.main.presenters;

import android.util.Log;

import com.paditech.core.common.BaseConstant;
import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.NewsPageContract;
import com.unza.wipro.main.models.News;
import com.unza.wipro.main.models.responses.GetNewsRSP;
import com.unza.wipro.services.AppClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsPagePresenter extends BasePresenter<NewsPageContract.ViewImpl> implements NewsPageContract.Presenter, AppConstans {
    private static final int FIRST_PAGE = 1;
    private int page = FIRST_PAGE;
    private int categoryId;
    private boolean isFull;
    private boolean isPending;

    @Override
    public void onCreate() {
        super.onCreate();
        categoryId = getView().getCategory().getId();
        loadDataFromServer(false);
    }

    private void loadDataFromServer(final boolean isRefresh) {
        if (isPending) {
            getView().setRefreshing(false);
            return;
        }
        if (isRefresh) {
            resetData();
            getView().setRefreshing(true);
        }
        if (isFull) {
            getView().showProgressDialog(false);
            return;
        }
        isPending = true;
        getView().showProgressDialog(page == FIRST_PAGE && !isRefresh);
        AppClient appClient = AppClient.newInstance();
        appClient.getService().getNews(BaseConstant.EMPTY, categoryId, page, PAGE_SIZE).enqueue(new Callback<GetNewsRSP>() {
            @Override
            public void onResponse(Call<GetNewsRSP> call, Response<GetNewsRSP> response) {
                isPending = false;
                try {
                    Log.e("testgetNews", String.valueOf(response.code()));
                    getView().setRefreshing(false);
                    getView().showProgressDialog(false);
                    if (response.body() != null && response.body().getNews() != null && response.body().getNews().size() > 0) {
                        List<News> newsList = response.body().getNews();
                        onLoadDataSuccess(isRefresh, newsList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetNewsRSP> call, Throwable t) {
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

    private void onLoadDataSuccess(boolean isRefresh, List<News> newsList) {
        page++;
        isFull = newsList.size() < PAGE_SIZE;
        if (isRefresh){
            getView().scrollToTop();
            getView().refreshList(newsList);
        }else {
            getView().updateItemToList(newsList);
        }
    }

    private void resetData() {
        isFull = false;
        page = FIRST_PAGE;
    }

    @Override
    public void onLoadMore() {
        loadDataFromServer(false);
    }

    @Override
    public void onRefresh() {
        loadDataFromServer(true);
    }
}
