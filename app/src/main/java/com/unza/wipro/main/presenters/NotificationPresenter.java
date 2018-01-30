package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.NotificationContract;
import com.unza.wipro.main.models.Notice;
import com.unza.wipro.main.models.responses.GetNotificationsRSP;
import com.unza.wipro.main.models.responses.ReadNotificationRSP;
import com.unza.wipro.services.AppClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/18/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class NotificationPresenter extends BasePresenter<NotificationContract.ViewImpl> implements NotificationContract.Presenter, AppConstans {

    private static final int NOTIFICATION_PAGE_SIZE = 100;
    private boolean isFull;
    private int mPage = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        loadData(false);
    }

    @Override
    public void loadData(boolean isRefresh) {
        getNotifications(isRefresh);
    }

    private void getNotifications(final boolean isRefresh) {
        if (isFull || !app.isLogin()) {
            getView().setRefreshing(false);
            return;
        }
        mPage = isRefresh ? 1 : mPage;
        getView().showProgressDialog(mPage == 1 && !isRefresh);
        AppClient.newInstance().getService().getNotifications(app.getToken(),
                app.getAppKey(), mPage, NOTIFICATION_PAGE_SIZE).enqueue(new Callback<GetNotificationsRSP>() {
            @Override
            public void onResponse(Call<GetNotificationsRSP> call, Response<GetNotificationsRSP> response) {
                try {
                    getView().showProgressDialog(false);
                    getView().setRefreshing(false);
                    if (response.body() != null) {
                        if (response.body().getNotices() != null && response.body().getNotices().size() > 0) {
                            mPage++;
                            isFull = response.body().getNotices().size() < PAGE_SIZE;
                            if (isRefresh) {
                                isFull = false;
                                getView().showData(response.body().getNotices());
                            } else {
                                getView().addData(response.body().getNotices());
                            }
                        }

                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<GetNotificationsRSP> call, Throwable t) {
                getView().showProgressDialog(false);
                getView().setRefreshing(false);
            }
        });
    }

    @Override
    public void read(Notice notice) {
        if (!app.isLogin()) return;
        AppClient.newInstance().getService().readNotification(app.getToken(),
                app.getAppKey(), notice.getId()).enqueue(new Callback<ReadNotificationRSP>() {
            @Override
            public void onResponse(Call<ReadNotificationRSP> call, Response<ReadNotificationRSP> response) {
                try {
                    getView().showProgressDialog(false);
                    if (response.body() != null && response.body().getNotice() != null) {
                        getView().updateView(response.body().getNotice());
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<ReadNotificationRSP> call, Throwable t) {
            }
        });
    }
}
