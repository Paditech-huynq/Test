package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.LoginContract;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/12/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class LoginPresenter extends BasePresenter<LoginContract.ViewImpl> implements LoginContract.Presenter {

    @Override
    public void login(String username, String password) {
//        getView().showProgressDialog(true);
//        getView().showProgressDialog(false);
        // TODO: call login api
        getView().onLoginResult();
    }
}
