package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.ProfileContract;
import com.unza.wipro.main.models.News;
import com.unza.wipro.main.models.responses.GetUserProfileRSP;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.transaction.user.Customer;
import com.unza.wipro.transaction.user.Promoter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter extends BasePresenter<ProfileContract.ViewImpl> implements ProfileContract.Presenter, AppConstans {

    public static final String URL_POLICY_PERMIS = "http://wipro.crm.admin.paditech.com/app/policy.html";
    public static final String URL_FAQ = "http://wipro.crm.admin.paditech.com/app/faq.html";
    @Override
    public void onCreatFragment() {
        getView().startUI();
    }

    @Override
    public void onPolicyPermisClick() {
        News news = new News();
        news.setContent(URL_POLICY_PERMIS);
        news.setTitle(getView().getContext().getResources().getString(R.string.title_policy_permis));
        getView().goToPolicyPermisWeb(news);
    }

    @Override
    public void onQuestionClick() {
        News news = new News();
        news.setContent(URL_FAQ);
        news.setTitle(getView().getContext().getResources().getString(R.string.title_question));
        getView().goToQuestionWeb(news);
    }

    @Override
    public void getUserDataFromServer() {
        if (AppConstans.app.getCurrentUser() == null) {
            return;
        }
        AppClient.newInstance().getService().getUserProfile(app.getToken(),
                app.getAppKey()).enqueue(new Callback<GetUserProfileRSP>() {
            @Override
            public void onResponse(Call<GetUserProfileRSP> call, Response<GetUserProfileRSP> response) {
                try {
                    AppConstans.app.updateCurrentUser(response.body().getUser());
                    updateUi();
                    getView().showProgressDialog(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetUserProfileRSP> call, Throwable t) {
                try {
                    getView().showProgressDialog(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        app.logout();
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
