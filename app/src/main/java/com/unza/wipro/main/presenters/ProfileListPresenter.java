package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.ProfileListContract;
import com.unza.wipro.main.models.Customer;
import com.unza.wipro.main.views.fragments.ProfileListFragment;

import java.util.List;

public class ProfileListPresenter extends BasePresenter<ProfileListFragment> implements ProfileListContract.Presenter {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh(List<Customer> customerList) {

    }

    @Override
    public void onViewAppear() {

    }

    @Override
    public void onViewDisAppear() {

    }

    @Override
    public void onDestroy() {

    }
}
