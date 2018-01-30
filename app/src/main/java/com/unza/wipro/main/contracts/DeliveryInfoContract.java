package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.transaction.user.DeliveryInfo;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/25/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public interface DeliveryInfoContract {
    interface ViewImpl extends BaseViewImpl {
        String getCustomerId();

        DeliveryInfo getDeliverInfo();

        void backToHomeScreen();
    }

    interface Presenter extends BasePresenterImpl {
        void onSubmitButtonClick();
    }
}
