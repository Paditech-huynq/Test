package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/12/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public interface ForgotPasswordContract {
    interface ViewImpl extends BaseViewImpl {
        void onForgotPassResult(boolean result, String phone, String message);
    }

    interface Presenter extends BasePresenterImpl {
        void forgotPass(String username);
    }
}
