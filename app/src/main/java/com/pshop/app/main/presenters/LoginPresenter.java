package com.pshop.app.main.presenters;

import android.content.Context;

import com.paditech.core.mvp.BasePresenter;
import com.pshop.app.AppConstans;
import com.pshop.app.main.contracts.LoginContract;
import com.pshop.app.main.models.responses.LoginRSP;
import com.pshop.app.services.AppClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/12/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class LoginPresenter extends BasePresenter<LoginContract.ViewImpl> implements LoginContract.Presenter, AppConstans {

    private static final String GRANT_TYPE = "password";
    private static final String CLIENT_ID = "crmapp";
    private static final String CLIENT_SECRET = "4b9bbd8ccb35e1f7bd6da757cacf7154";

    @Override
    public void login(String username, String password) {
        final Context context = getView().getContext();
        getView().showProgressDialog(true);
        AppClient.newInstance().getService().login(GRANT_TYPE, CLIENT_ID, CLIENT_SECRET, username, password, EMPTY)
                .enqueue(new Callback<LoginRSP>() {
                    @Override
                    public void onResponse(Call<LoginRSP> call, Response<LoginRSP> response) {
                        try {
                            getView().showProgressDialog(false);
                            if (response.body() != null) {
                                getView().onLoginResult(response.body().isSuccess(), response.body().getMessage());
                                if (response.body().isSuccess()) {
                                    app.updateAppState(response.body().getData());
                                }
                            } else {
                                getView().onLoginResult(false, response.body().getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getView().onLoginResult(false, "");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginRSP> call, Throwable t) {
                        try {
                            getView().showProgressDialog(false);
                            getView().onLoginResult(false, "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
