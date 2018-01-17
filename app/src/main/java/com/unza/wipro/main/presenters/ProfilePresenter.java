package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.ProfileContract;
import com.unza.wipro.main.models.UserData;

public class ProfilePresenter extends BasePresenter<ProfileContract.ViewImpl> implements ProfileContract.Presenter {

    @Override
    public void getUserDataFromServer() {
        UserData userData = new UserData();
        getView().updateUI(userData.getUserData());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getUserDataFromServer();
    }
}
