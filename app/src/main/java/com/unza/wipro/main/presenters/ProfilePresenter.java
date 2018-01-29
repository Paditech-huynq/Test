package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.ProfileContract;
import com.unza.wipro.main.models.responses.GetUserProfileRSP;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.transaction.user.Customer;
import com.unza.wipro.transaction.user.Promoter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter extends BasePresenter<ProfileContract.ViewImpl> implements ProfileContract.Presenter, AppConstans {

    @Override
    public void onCreatFragment() {
        getView().startUI();
    }

    @Override
    public void getUserDataFromServer() {
        if (AppConstans.app.getCurrentUser() == null) {
            return;
        }
        getView().showProgressDialog(true);
        AppClient.newInstance().getService().getUserProfile(app.getToken(),
                app.getAppKey()).enqueue(new Callback<GetUserProfileRSP>() {
            @Override
            public void onResponse(Call<GetUserProfileRSP> call, Response<GetUserProfileRSP> response) {
                AppConstans.app.updateCurrentUser(response.body().getUser());
                updateUi();
                getView().showProgressDialog(false);
            }

            @Override
            public void onFailure(Call<GetUserProfileRSP> call, Throwable t) {
                getView().showProgressDialog(false);
            }
        });
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

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        getView().startUI();
        updateUi();
    }

    private void updateUi() {
        getView().updateUI();
        if (AppConstans.app.getCurrentUser() instanceof Customer) {
            getView().updateUIForCustomer();
        }
        if (AppConstans.app.getCurrentUser() instanceof Promoter) {
            getView().updateUIForPromoter();
        }
    }
}
