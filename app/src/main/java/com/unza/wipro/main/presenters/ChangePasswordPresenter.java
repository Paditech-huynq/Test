package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.ChangePasswordContract;
import com.unza.wipro.main.contracts.LoginContract;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/12/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class ChangePasswordPresenter extends BasePresenter<ChangePasswordContract.ViewImpl> implements ChangePasswordContract.Presenter {

    @Override
    public void changePass(String newPassword, String confirmPassword) {
        getView().onChangePassResult(true, "");
    }
}
