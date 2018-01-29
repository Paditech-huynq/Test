package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/12/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public interface ChangePasswordContract {
    interface ViewImpl extends BaseViewImpl {
        void onChangePassResult(boolean result, String message);
    }

    interface Presenter extends BasePresenterImpl {
        void changePass(String phone, String otp, String newPassword, String confirmPassword);
    }
}
