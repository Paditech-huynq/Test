package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;

public interface LoginContract {
    interface ViewImpl extends BaseViewImpl {
        void onLoginResult();
    }

    interface Presenter extends BasePresenterImpl {
        void login(String username, String password);
    }
}
