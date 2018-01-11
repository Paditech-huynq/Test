package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.MainContract;

public class MainPresenter extends BasePresenter<MainContract.ViewImpl> implements MainContract.Presenter {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
