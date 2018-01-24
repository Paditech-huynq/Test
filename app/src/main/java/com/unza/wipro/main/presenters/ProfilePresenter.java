package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.ProfileContract;
import com.unza.wipro.main.models.LoginClient;
import com.unza.wipro.main.models.UserData;
import com.unza.wipro.main.models.responses.GetOrderDetailRSP;
import com.unza.wipro.main.models.responses.GetUserProfileRSP;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.services.AppService;
import com.unza.wipro.transaction.user.Customer;
import com.unza.wipro.transaction.user.Promoter;
import com.unza.wipro.transaction.user.PromoterLeader;
import com.unza.wipro.transaction.user.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter extends BasePresenter<ProfileContract.ViewImpl> implements ProfileContract.Presenter {

    @Override
    public void getUserDataFromServer() {
        if (AppConstans.app.getCurrentUser() == null ){
            return;
        }
        AppClient.newInstance().getService().getUserProfile(LoginClient.getToken(getView().getContext()),
                LoginClient.getAppKey(getView().getContext())).enqueue(new Callback<GetUserProfileRSP>() {
            @Override
            public void onResponse(Call<GetUserProfileRSP> call, Response<GetUserProfileRSP> response) {
                getView().updateUI(response.body().getUser());
                if (AppConstans.app.getCurrentUser() instanceof Customer) {
                    getView().updateUIForCustomer((Customer) response.body().getUser());
                }
                if (AppConstans.app.getCurrentUser() instanceof Promoter) {
                    getView().updateUIForPromoter((Promoter) response.body().getUser());
                    if (AppConstans.app.getCurrentUser() instanceof PromoterLeader) {
                        getView().updateUIForPromoterLeader((PromoterLeader) response.body().getUser());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserProfileRSP> call, Throwable t) {

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
}
