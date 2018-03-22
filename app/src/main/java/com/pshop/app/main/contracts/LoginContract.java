package com.pshop.app.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;

public interface LoginContract {
    interface ViewImpl extends BaseViewImpl {
        void onLoginResult(boolean result, String message);
    }

    interface Presenter extends BasePresenterImpl {
        void login(String username, String password);
    }
}
