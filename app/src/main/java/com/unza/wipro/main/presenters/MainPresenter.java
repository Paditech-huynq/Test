package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.MainContract;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.services.AppService;

public class MainPresenter extends BasePresenter<MainContract.ViewImpl> implements MainContract.Presenter {
    private AppService service;

    @Override
    public void onCreate() {
        super.onCreate();
        service = AppClient.newInstance().getService();
    }

}
