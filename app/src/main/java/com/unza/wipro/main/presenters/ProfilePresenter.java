package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.ProfileContract;
import com.unza.wipro.main.models.UserData;

public class ProfilePresenter extends BasePresenter<ProfileContract.ViewImpl> implements ProfileContract.Presenter {

    @Override
    public void getUserDataFromServer() {
        getView().updateUI(UserData.getDummyData());
    }

    @Override
    public void onChangePassClick() {
        getView().goToChangePassFragment();
    }

    @Override
    public void onListOrderClick() {
        getView().goToOrderFragment();
    }

    @Override
    public void onManagerSalesClick() {
        getView().goToListProfileFragment();
    }

    @Override
    public void onLogOutClick() {
        getView().goToHomeProfile();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getUserDataFromServer();
    }
}
