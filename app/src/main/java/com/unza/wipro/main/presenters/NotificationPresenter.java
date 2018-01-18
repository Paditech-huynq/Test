package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.NotificationContract;

import java.util.ArrayList;

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
        getView().showData(new ArrayList());
    }
}
