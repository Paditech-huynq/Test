package com.pshop.app.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/12/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public interface OtpContract {
    interface ViewImpl extends BaseViewImpl {
        void onConfirmOtpResult(boolean result, String otp, String message);
    }

    interface Presenter extends BasePresenterImpl {
        void confirmOtp(String otp);
    }
}
