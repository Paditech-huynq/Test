package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/25/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public interface DeliveryInfoContract {
    interface ViewImpl extends BaseViewImpl {
        void onResult(boolean result, String message);
    }

    interface Presenter extends BasePresenterImpl {
        void submit(String name, String phone, String address, String date, String note);
    }
}
