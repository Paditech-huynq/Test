package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.ForgotPasswordContract;

public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordContract.ViewImpl> implements ForgotPasswordContract.Presenter {

    @Override
    public void forgotPass(String username) {
        getView().onForgotPassResult(true, "");
    }
}
