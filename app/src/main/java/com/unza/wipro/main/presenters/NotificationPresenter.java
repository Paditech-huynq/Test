package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppState;
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

public class NotificationPresenter extends BasePresenter<NotificationContract.ViewImpl> implements NotificationContract.Presenter {

    int mPage;

    @Override
    public void onCreate() {
        super.onCreate();
        loadData(true);
    }

    @Override
    public void loadData(boolean isRefresh) {
        getNotifications();
    }

    private void getNotifications() {
        if (!AppState.getInstance().isLogin()) return;
        getView().showProgressDialog(true);
        AppClient.newInstance().getService().getNotifications(AppState.getInstance().getToken(),
                AppState.getInstance().getAppKey()).enqueue(new Callback<GetNotificationsRSP>() {
            @Override
            public void onResponse(Call<GetNotificationsRSP> call, Response<GetNotificationsRSP> response) {
                try {
                    getView().showProgressDialog(false);
                    if (response.body() != null) {
                        getView().showData(response.body().getNotices());
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<GetNotificationsRSP> call, Throwable t) {
                getView().showProgressDialog(false);
            }
        });
    }

    @Override
    public void read(Notice notice) {
        if (!AppState.getInstance().isLogin()) return;
        AppClient.newInstance().getService().readNotification(AppState.getInstance().getToken(),
                AppState.getInstance().getAppKey(), notice.getId()).enqueue(new Callback<ReadNotificationRSP>() {
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
